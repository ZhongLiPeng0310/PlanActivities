package com.plan.app.goods.entity;

import lombok.Data;

@Data
public class GoodsInfo {
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
     * 销售量
     */
    private String sumSale;
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
     * 评价星级
     */
    private String appraiseLevel;
    /**
     * 库存
     */
    private String stock;
    /**
     * 购买数量
     */
    private String orderSum;
    /**
     * 版本号
     */
    private String version;
}
