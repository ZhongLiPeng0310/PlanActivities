package com.plan.pc.tbGoods.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.plan.pc.tbGoods.entity.TbGoodsEntity;
import com.plan.pc.tbGoods.entity.TbGoodsInfo;
import com.plan.pc.tbGoods.service.TbGoodsService;
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
@RequestMapping("/tbGoods")
public class TbGoodsController {

    private static final Logger logger = LoggerFactory.getLogger(com.plan.pc.tbGoods.controller.TbGoodsController.class);

    @Resource
    private TbGoodsService tbGoodsService;

    /**
     * saveGoods 保存物资
     * @param tbGoodsInfo
     * @return AppResponse
     * @author zhong
     * @Date 2021.03.17
     */
    @PostMapping("saveTbGoods")
    public AppResponse saveTbGoods(TbGoodsInfo tbGoodsInfo) {
        try {
            AppResponse appResponse = tbGoodsService.saveGoods(tbGoodsInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("物资新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * listGoodsByPage 物资列表（分页）
     * @param userInfo
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "listGoodsByPage")
    public AppResponse listGoodsByPage(TbGoodsInfo userInfo){
        try{
            return tbGoodsService.listGoodsByPage(userInfo);
        }catch (Exception e){
            logger.error("查询物资列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * getGoodsByGoodsCode 查询物资详情
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @RequestMapping(value = "getGoodsById")
    public AppResponse findGoodsById(String id){
        try{
            return tbGoodsService.getGoodsById(id);
        }catch (Exception e){
            logger.error("物资查询错误",e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * deleteGoods 删除物资
     * @param id
     * @return AppResponse
     * @author zhonglipeng
     * @Date 2021-03-17
     */
    @PostMapping("deleteGoods")
    public AppResponse deleteGoods(String id){
        try{
            //获取物资id
            String userId = SecurityUtils.getCurrentUserId();
            return tbGoodsService.deleteGoods(id,userId);
        }catch (Exception e){
            logger.error("物资删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

}
