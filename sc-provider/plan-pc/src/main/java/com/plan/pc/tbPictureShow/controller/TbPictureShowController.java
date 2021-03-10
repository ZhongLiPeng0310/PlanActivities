package com.plan.pc.tbPictureShow.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.plan.pc.tbPictureShow.entity.TbPictureShowInfo;
import com.plan.pc.tbPictureShow.entity.TbPictureShowVo;
import com.plan.pc.tbPictureShow.service.TbPictureShowService;
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
@RequestMapping("/tbPictureShow")
public class TbPictureShowController {
    private static final Logger logger = LoggerFactory.getLogger(TbPictureShowController.class);

    @Resource
    private TbPictureShowService tbPictureShowService;

    /**
     * saveTbPictureShow 保存轮播图
     * @param tbPictureShowInfo
     * @return AppResponse
     * @author zhong
     * @Date 2021.03.17
     */
    @PostMapping("saveTbPictureShow")
    public AppResponse saveTbPictureShow(TbPictureShowInfo tbPictureShowInfo) {
        try {
            AppResponse appResponse = tbPictureShowService.saveTbPictureShow(tbPictureShowInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("轮播图新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listTbPictureShowByPage 轮播图列表（分页）
     * @param tbPictureShowVo
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "listTbPictureShowByPage")
    public AppResponse listTbPictureShowByPage(TbPictureShowVo tbPictureShowVo){
        try{
            return tbPictureShowService.listTbPictureShowByPage(tbPictureShowVo);
        }catch (Exception e){
            logger.error("查询轮播图列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getTbPictureShowByTbPictureShowCode 查询轮播图详情
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "getTbPictureShowById")
    public AppResponse getTbPictureShowById(String id){
        try{
            return tbPictureShowService.getTbPictureShowById(id);
        }catch (Exception e){
            logger.error("轮播图查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deleteTbPictureShow 删除轮播图
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @PostMapping("deleteTbPictureShow")
    public AppResponse deleteTbPictureShow(String id){
        try{
            //获取轮播图id
            String userId = SecurityUtils.getCurrentUserId();
            return tbPictureShowService.deleteTbPictureShow(id,userId);
        }catch (Exception e){
            logger.error("轮播图删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
