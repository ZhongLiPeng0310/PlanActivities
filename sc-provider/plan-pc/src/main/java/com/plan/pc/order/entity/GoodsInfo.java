package com.plan.pc.order.entity;

import lombok.Data;

/**
 * 商品实体类
 * 2021-01-04
 * zhong
 * @author 12533
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
     * 成本价
     */
    private float costPrice;
    /**
     * 售价
     */
    private float salePrice;
    /**
     * 销售量
     */
    private int sumSale;
    /**
     * 商家名称
     */
    private String supplierName;
    /**
     * 分类编码
     */
    private String classCode;
    /**
     * 分类名称
     */
    private String className;
    /**
     * 一级分类编码
     */
    private String classFirstCode;
    /**
     * 一级分类名称
     */
    private String classFirstName;
    /**
     * 二级分类编码
     */
    private String classSecondCode;
    /**
     * 二级分类名称
     */
    private String classSecondName;
    /**
     * 广告词
     */
    private String advertisement;
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
     * 购买数量
     */
    private String orderSum;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创建者
     */
    private String createName;
    /**
     * 创建时间
     * @return
     */
    private String createTime;
    /**
     * 更新人
     * @return
     */
    private String updateName;
    /**
     * 更新时间
     * @return
     */
    private String updateTime;
    /**
     * 版本号
     * @return
     */
    private String version;

    /**
     * 页码
     * @return
     */
    private int pageSize;

    /**
     * 页数
     * @return
     */
    private int pageNum;

}
