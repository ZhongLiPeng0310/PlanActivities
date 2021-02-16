package com.plan.app.clientOrder.entity;

import lombok.Data;

/**
 * 商品实体类
 * 2021-01-05
 * zhong
 */
@Data
public class GoodsInfo {
    /**
     * 商品编码
    */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品平均星级
     */
    private String avgLevel;
    /**
     * 成本价
     */
    private float costPrice;
    /**
     * 售价
     */
    private float salePrice;
    /**
     * 购买数量
     */
    private int orderSum;
    /**
     * 销售量
     */
    private int sumSale;
    /**
     * 分类编码
     */
    private String classCode;
    /**
     * 分类名称
     */
    private String className;
    /**
     * 商品介绍
     */
    private String goodsIntroduce;

    /**
     * 商品状态 1下架  2上架（在售） 3未发布
     */
    private int goodsState ;
    /**
     * 上架时间
     */
    private String upTime;
    /**
     * 浏览量
     */
    private int browse;
    /**
     * 门店编码
     */
    private String storeCode;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 库存
     */
    private int stock;
    /**
     * 书号ISBN
     */
    private String isbn;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;

}
