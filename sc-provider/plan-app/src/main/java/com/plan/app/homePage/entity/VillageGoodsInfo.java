package com.plan.app.homePage.entity;

import lombok.Data;

@Data
public class VillageGoodsInfo {
    /**
     * 产品编号
     */
    private String goodsCode;
    /**
     * 产品名称
     */
    private String goodsName;
    /**
     * 产品介绍
     */
    private String goodsIntroduce;
    /**
     * 站点编码
     */
    private String storeCode;
    /**
     * 站点名称
     */
    private String storeName;
    /**
     * 售价
     */
    private String salePrice;
    /**
     * 产品图片路径
     */
    private String imagePath;
    /**
     * 销售量
     */
    private String sumSale;
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
     * 版本号
     */
    private String version;
}
