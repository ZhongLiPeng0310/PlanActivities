package com.plan.pc.tbUser.controller;

/**
 * @author 12533
 */

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.plan.pc.tbUser.entity.TbUserInfo;
import com.plan.pc.tbUser.entity.UserEntity;
import com.plan.pc.tbUser.service.TbUserService;
import com.plan.pc.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 用户管理
 * @Author chenchaotao
 * @Date 2020-08-22
 */
@RestController
@RequestMapping("/tbUser")
public class TbUserController {
    private static final Logger logger = LoggerFactory.getLogger(com.plan.pc.tbUser.controller.TbUserController.class);

    @Resource
    private TbUserService tbUserService;

    /**
     * saveUser 保存用户
     * @param userInfo
     * @return AppResponse
     * @author chenchaotao
     * @Date 2020-03-24
     */
    @PostMapping("saveUser")
    public AppResponse saveUser(TbUserInfo userInfo) {
        try {
            AppResponse appResponse = tbUserService.saveUser(userInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("用户新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listUsersByPage 用户列表（分页）
     * @param userInfo
     * @return AppReesponse
     * @author chenchaotao
     * @Date 2020-08-22
     */
    @RequestMapping(value = "listUsersByPage")
    public AppResponse listUsersByPage(TbUserInfo userInfo){
        try{
            return tbUserService.listUsersByPage(userInfo);
        }catch (Exception e){
            logger.error("查询用户列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getUserByUserCode 查询用户详情
     * @param id
     * @return AppResponse
     * @author chenchaotao
     * @Date 2020-08-22
     */
    @RequestMapping(value = "getUserByUserCode")
    public AppResponse findUserById(String id){
        try{
            return tbUserService.getUserByUserCode(id);
        }catch (Exception e){
            logger.error("用户查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deleteUser 删除用户
     * @param id
     * @return AppResponse
     * @author chenchaotao
     * @Date 2020-08-22
     */
    @PostMapping("deleteUser")
    public AppResponse deleteUser(String id){
        try{
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            return tbUserService.deleteUser(id,userId);
        }catch (Exception e){
            logger.error("用户删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * 获取顶部导航栏
     * @author zhong
     * @date 2020-04-16
     * @param userInfo
     * @return
     */
    @PostMapping(value ="getUserUrl")
    public AppResponse getUserUrl(UserInfo userInfo){
        try{
            String userId = SecurityUtils.getCurrentUserId();
            userInfo.setUserCode(userId);
            return tbUserService.getUserUrl(userInfo);
        }catch (Exception e){
            logger.error("导航栏查询失败",e);
            System.out.println(e.toString());
            throw e;
        }
    }
}

