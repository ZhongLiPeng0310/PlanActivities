package com.plan.pc.tbPlan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbPlan.dao.PlanDao;
import com.plan.pc.tbPlan.entity.PlanEntity;
import com.plan.pc.tbPlan.entity.PlanInfo;
import com.plan.pc.tbPlan.entity.PlanVo;
import com.plan.pc.tbPlan.repository.PlanRepository;
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
public class PlanService {
    @Resource
    private PlanDao planDao;

    @Resource
    private PlanRepository planRepository;
    /**
     * 保存
     * @param planInfo
     * @return
     */
    public AppResponse savePlan(PlanInfo planInfo) {
        PlanEntity entity = new PlanEntity();
        BeanUtils.copyProperties(planInfo,entity);
        if (entity.getId() == null){
            entity.setId(StringUtil.getCommonCode(2));
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(userId);
            entity.setCreateTime(new Date());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }else {
            PlanEntity oldEntity = planRepository.findById(planInfo.getId());
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
        // 新增活动
        planRepository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 分页查询
     * @param planVo
     * @return
     */
    public AppResponse listPlanByPage(PlanVo planVo) {
        PageHelper.startPage(planVo.getPageNum(),planVo.getPageSize());
        List<PlanVo> planVoList = planDao.listPlanByPage(planVo);
        //包装对象
        PageInfo<PlanVo> pageData = new PageInfo<PlanVo>(planVoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    public AppResponse getPlanById(String id) {
        PlanEntity planEntity = planRepository.findById(id);
        PlanVo vo = new PlanVo();
        BeanUtils.copyProperties(planEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 批量删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deletePlan(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<PlanEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            PlanEntity oldEntity = planRepository.findById(strings);
            oldList.add(oldEntity);
        }
        for (PlanEntity entitys : oldList){
            PlanEntity entity = new PlanEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            planRepository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }
}
