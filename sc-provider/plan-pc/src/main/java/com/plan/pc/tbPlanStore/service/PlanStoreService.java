package com.plan.pc.tbPlanStore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbPlanStore.dao.PlanStoreDao;
import com.plan.pc.tbPlanStore.entity.PlanStoreEntity;
import com.plan.pc.tbPlanStore.entity.PlanStoreInfo;
import com.plan.pc.tbPlanStore.entity.PlanStoreVo;
import com.plan.pc.tbPlanStore.repository.PlanStoreRespository;
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
public class PlanStoreService {

    @Resource
    private PlanStoreRespository planStoreRespository;

    @Resource
    private PlanStoreDao planStoreDao;


    /**
     * 保存
     * @param planStoreInfo
     * @return
     */
    public AppResponse savePlanStore(PlanStoreInfo planStoreInfo) {
        PlanStoreEntity entity = new PlanStoreEntity();
        BeanUtils.copyProperties(planStoreInfo,entity);
        if (entity.getId() == null){
            entity.setId(StringUtil.getCommonCode(2));
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(userId);
            entity.setCreateTime(new Date());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }else {
            PlanStoreEntity oldEntity = planStoreRespository.findById(planStoreInfo.getId());
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
        planStoreRespository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 分页查询
     * @param planStoreVo
     * @return
     */
    public AppResponse listPlanStoreByPage(PlanStoreVo planStoreVo) {
        PageHelper.startPage(planStoreVo.getPageNum(),planStoreVo.getPageSize());
        List<PlanStoreVo> planVoList = planStoreDao.listPlanStoreByPage(planStoreVo);
        //包装对象
        PageInfo<PlanStoreVo> pageData = new PageInfo<PlanStoreVo>(planVoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    public AppResponse getPlanStoreById(String id) {
        PlanStoreEntity planEntity = planStoreRespository.findById(id);
        PlanStoreVo vo = new PlanStoreVo();
        BeanUtils.copyProperties(planEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 批量删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deletePlanStore(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<PlanStoreEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            PlanStoreEntity oldEntity = planStoreRespository.findById(strings);
            oldList.add(oldEntity);
        }
        for (PlanStoreEntity entitys : oldList){
            PlanStoreEntity entity = new PlanStoreEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            planStoreRespository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }
}
