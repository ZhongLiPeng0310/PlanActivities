package com.plan.pc.tbSuperPlan.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.plan.pc.tbSuperPlan.entity.SuperPlanInfo;
import com.plan.pc.tbSuperPlan.entity.SuperPlanVo;
import com.plan.pc.tbSuperPlan.service.SuperPlanService;
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
@RequestMapping("/superPlan")
public class SuperPlanController {
    private static final Logger logger = LoggerFactory.getLogger(SuperPlanController.class);

    @Resource
    private SuperPlanService superPlanService;

    /**
     * saveSuperPlan 保存精选案例
     * @param superPlanInfo
     * @return AppResponse
     * @author zhong
     * @Date 2021.03.17
     */
    @PostMapping("saveSuperPlan")
    public AppResponse saveSuperPlan(SuperPlanInfo superPlanInfo) {
        try {
            AppResponse appResponse = superPlanService.saveSuperPlan(superPlanInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("精选案例新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listSuperPlanByPage 精选案例列表（分页）
     * @param superPlanVo
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "listSuperPlanByPage")
    public AppResponse listSuperPlanByPage(SuperPlanVo superPlanVo){
        try{
            return superPlanService.listSuperPlanByPage(superPlanVo);
        }catch (Exception e){
            logger.error("查询精选案例列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getSuperPlanBySuperPlanCode 查询精选案例详情
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "getSuperPlanById")
    public AppResponse getSuperPlanById(String id){
        try{
            return superPlanService.getSuperPlanById(id);
        }catch (Exception e){
            logger.error("精选案例查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deleteSuperPlan 删除精选案例
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @PostMapping("deleteSuperPlan")
    public AppResponse deleteSuperPlan(String id){
        try{
            //获取精选案例id
            String userId = SecurityUtils.getCurrentUserId();
            return superPlanService.deleteSuperPlan(id,userId);
        }catch (Exception e){
            logger.error("精选案例删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
