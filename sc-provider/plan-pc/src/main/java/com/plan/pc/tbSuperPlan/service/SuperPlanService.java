package com.plan.pc.tbSuperPlan.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbSuperPlan.dao.SuperPlanDao;
import com.plan.pc.tbSuperPlan.entity.SuperPlanEntity;
import com.plan.pc.tbSuperPlan.entity.SuperPlanInfo;
import com.plan.pc.tbSuperPlan.entity.SuperPlanVo;
import com.plan.pc.tbSuperPlan.repository.SuperPlanRepository;
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
public class SuperPlanService {
    @Resource
    private SuperPlanDao superPlanDao;

    @Resource
    private SuperPlanRepository superPlanRepository;
    /**
     * 保存
     * @param superPlanInfo
     * @return
     */
    public AppResponse saveSuperPlan(SuperPlanInfo superPlanInfo) {
        SuperPlanEntity entity = new SuperPlanEntity();
        SuperPlanEntity oldEntity = superPlanRepository.findById(superPlanInfo.getId());
        BeanUtils.copyProperties(superPlanInfo,entity);
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
        // 新增轮播图
        superPlanRepository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 分页查询
     * @param superPlanVo
     * @return
     */
    public AppResponse listSuperPlanByPage(SuperPlanVo superPlanVo) {
        PageHelper.startPage(superPlanVo.getPageNum(),superPlanVo.getPageSize());
        List<SuperPlanVo> superPlanVoList = superPlanDao.listSuperPlanByPage(superPlanVo);
        //包装对象
        PageInfo<SuperPlanVo> pageData = new PageInfo<SuperPlanVo>(superPlanVoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    public AppResponse getSuperPlanById(String id) {
        SuperPlanEntity superPlanEntity = superPlanRepository.findById(id);
        SuperPlanVo vo = new SuperPlanVo();
        BeanUtils.copyProperties(superPlanEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 批量删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deleteSuperPlan(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<SuperPlanEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            SuperPlanEntity oldEntity = superPlanRepository.findById(strings);
            oldList.add(oldEntity);
        }
        for (SuperPlanEntity entitys : oldList){
            SuperPlanEntity entity = new SuperPlanEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            superPlanRepository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }
}
