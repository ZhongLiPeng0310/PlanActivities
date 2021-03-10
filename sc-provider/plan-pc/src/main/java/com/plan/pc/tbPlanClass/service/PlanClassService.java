package com.plan.pc.tbPlanClass.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbPlanClass.dao.PlanClassDao;
import com.plan.pc.tbPlanClass.entity.PlanClassEntity;
import com.plan.pc.tbPlanClass.entity.PlanClassInfo;
import com.plan.pc.tbPlanClass.entity.PlanClassVo;
import com.plan.pc.tbPlanClass.repository.PlanClassRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 12533
 */
@Service
public class PlanClassService {
    @Resource
    private PlanClassDao planClassDao;

    @Resource
    private PlanClassRepository planClassRepository;
    /**
     * 保存
     * @param planClassInfo
     * @return
     */
    public AppResponse savePlanClass(PlanClassInfo planClassInfo) {
        PlanClassEntity entity = new PlanClassEntity();
        PlanClassEntity oldEntity = planClassRepository.findById(planClassInfo.getId());
        BeanUtils.copyProperties(planClassInfo,entity);
        if (entity.getId() == null){
            entity.setId(StringUtil.getCommonCode(2));
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(userId);
            entity.setCreateTime(new Date());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }else {
            entity.setId(entity.getId());
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(oldEntity.getCreateName());
            entity.setCreateTime(oldEntity.getCreateTime());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }
        entity.setIsDeleted(1);
        entity.setVersion(0);
        // 新增活动分类
        planClassRepository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 分页查询
     * @param planClassVo
     * @return
     */
    public AppResponse listPlanClassByPage(PlanClassVo planClassVo) {
        PageHelper.startPage(planClassVo.getPageNum(),planClassVo.getPageSize());
        List<PlanClassVo> planClassVoList = planClassDao.listPlanClassByPage(planClassVo);
        //包装对象
        PageInfo<PlanClassVo> pageData = new PageInfo<PlanClassVo>(planClassVoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    public AppResponse getPlanClassById(String id) {
        PlanClassEntity planClassEntity = planClassRepository.findById(id);
        PlanClassVo vo = new PlanClassVo();
        BeanUtils.copyProperties(planClassEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 批量删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deletePlanClass(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<PlanClassEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            PlanClassEntity oldEntity = planClassRepository.findById(strings);
            oldList.add(oldEntity);
        }
        for (PlanClassEntity entitys : oldList){
            PlanClassEntity entity = new PlanClassEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            planClassRepository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }
}
