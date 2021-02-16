package com.plan.app.cart.entity;

import lombok.Data;

/**
 * 购物车实体类
 * @author zhong
 * @date 2021-01-05
 */
@Data
public class CartInfo {
    /**
     * 购物车编码
     */
    private String cartCode;
    /**
     * 购物车序号
     */
    private String sortNo;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 当前登录人编码
     */
    private String userId;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品介绍
     */
    private String goodsIntroduce;
    /**
     * 站点编码
     */
    private String storeCode;
    /**
     * 售价
     */
    private float salePrice;
    /**
     * 购买数量
     */
    private int orderSum;
    /**
     * 新加的数量
     */
    private int NewOrderSum;
    /**
     * 商品图片
     */
    private String imagePath;

    /**
     * 总金额
     */
    private float orderMoney;
    /**
     * 作废标记  0保留  1删除
     */
    private int isDeleted;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新人
     */
    private String updateName;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 版本号
     */
    private String version;
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
}
