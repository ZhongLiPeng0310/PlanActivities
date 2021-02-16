package com.plan.app.homePage.entity;

import lombok.Data;

import java.util.List;

/**
 * @description app首页实体类
 * @author zhong
 * @date 2021-01-05
 */
@Data
public class HomePageInfo {
    /**
     * 轮播图路径
     */
    private String chartRoute;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 乡村编码
     */
    private String villageCode;
    /**
     * 乡村名称
     */
    private String villageName;
    /**
     * 乡村介绍
     */
    private String villageIntroduce;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 展示数量
     */
    private int showNumber;
    /**
     * 版本号
     */
    private String version;

    /**
     * List
     * @param chartRoute
     */
    private List<HomePageInfo> homePageInfoList;
}
