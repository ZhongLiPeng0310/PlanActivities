package com.plan.pc.tbPlan.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.plan.pc.tbPlan.entity.PlanInfo;
import com.plan.pc.tbPlan.entity.PlanVo;
import com.plan.pc.tbPlan.service.PlanService;
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
@RequestMapping("/plan")
public class PlanController {
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Resource
    private PlanService planService;

    /**
     * savePlan 保存活动
     * @param planInfo
     * @return AppResponse
     * @author zhong
     * @Date 2021.03.17
     */
    @PostMapping("savePlan")
    public AppResponse savePlan(PlanInfo planInfo) {
        try {
            AppResponse appResponse = planService.savePlan(planInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("活动新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listPlanByPage 活动列表（分页）
     * @param planVo
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "listPlanByPage")
    public AppResponse listPlanByPage(PlanVo planVo){
        try{
            return planService.listPlanByPage(planVo);
        }catch (Exception e){
            logger.error("查询活动列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getPlanByPlanCode 查询活动详情
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "getPlanById")
    public AppResponse getPlanById(String id){
        try{
            return planService.getPlanById(id);
        }catch (Exception e){
            logger.error("活动查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deletePlan 删除活动
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @PostMapping("deletePlan")
    public AppResponse deletePlan(String id){
        try{
            //获取活动id
            String userId = SecurityUtils.getCurrentUserId();
            return planService.deletePlan(id,userId);
        }catch (Exception e){
            logger.error("活动删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
