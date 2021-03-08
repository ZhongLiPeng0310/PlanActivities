package com.plan.pc.tbPlanStore.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;

import com.plan.pc.tbPlanStore.entity.PlanStoreVo;
import com.plan.pc.tbPlanStore.entity.PlanStoreInfo;
import com.plan.pc.tbPlanStore.service.PlanStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 12533
 */
@RestController
@RequestMapping("/planStore")
public class PlanStoreController {
    private static final Logger logger = LoggerFactory.getLogger(PlanStoreController.class);

    @Resource
    private PlanStoreService planStoreService;

    /**
     * savePlanStore 保存策划门店
     * @param planStoreInfo
     * @return AppResponse
     * @author zhong
     * @Date 2021.03.17
     */
    @PostMapping("savePlanStore")
    public AppResponse savePlanStore(PlanStoreInfo planStoreInfo) {
        try {
            AppResponse appResponse = planStoreService.savePlanStore(planStoreInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("策划门店新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listPlanStoreByPage 策划门店列表（分页）
     * @param planStoreVo
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "listPlanStoreByPage")
    public AppResponse listPlanStoreByPage(PlanStoreVo planStoreVo){
        try{
            return planStoreService.listPlanStoreByPage(planStoreVo);
        }catch (Exception e){
            logger.error("查询策划门店列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getPlanStoreByPlanStoreCode 查询策划门店详情
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "getPlanStoreById")
    public AppResponse getPlanStoreById(String id){
        try{
            return planStoreService.getPlanStoreById(id);
        }catch (Exception e){
            logger.error("策划门店查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deletePlanStore 删除策划门店
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @PostMapping("deletePlanStore")
    public AppResponse deletePlanStore(String id){
        try{
            //获取策划门店id
            String userId = SecurityUtils.getCurrentUserId();
            return planStoreService.deletePlanStore(id,userId);
        }catch (Exception e){
            logger.error("策划门店删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
