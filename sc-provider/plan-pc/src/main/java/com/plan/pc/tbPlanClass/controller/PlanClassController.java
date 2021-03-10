package com.plan.pc.tbPlanClass.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.plan.pc.tbPlanClass.entity.PlanClassInfo;
import com.plan.pc.tbPlanClass.entity.PlanClassVo;
import com.plan.pc.tbPlanClass.service.PlanClassService;
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
@RequestMapping("/planClass")
public class PlanClassController {
    private static final Logger logger = LoggerFactory.getLogger(com.plan.pc.tbPlanClass.controller.PlanClassController.class);

    @Resource
    private PlanClassService planClassService;

    /**
     * savePlanClass 保存活动分类
     * @param planClassInfo
     * @return AppResponse
     * @author zhong
     * @Date 2021.03.17
     */
    @PostMapping("savePlanClass")
    public AppResponse savePlanClass(PlanClassInfo planClassInfo) {
        try {
            AppResponse appResponse = planClassService.savePlanClass(planClassInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("活动新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listPlanClassByPage 活动列表（分页）
     * @param planClassVo
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "listPlanClassByPage")
    public AppResponse listPlanClassByPage(PlanClassVo planClassVo){
        try{
            return planClassService.listPlanClassByPage(planClassVo);
        }catch (Exception e){
            logger.error("查询活动列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getPlanClassByPlanClassCode 查询活动详情
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "getPlanClassById")
    public AppResponse getPlanClassById(String id){
        try{
            return planClassService.getPlanClassById(id);
        }catch (Exception e){
            logger.error("活动查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deletePlanClass 删除活动
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @PostMapping("deletePlanClass")
    public AppResponse deletePlanClass(String id){
        try{
            //获取活动id
            String userId = SecurityUtils.getCurrentUserId();
            return planClassService.deletePlanClass(id,userId);
        }catch (Exception e){
            logger.error("活动删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
