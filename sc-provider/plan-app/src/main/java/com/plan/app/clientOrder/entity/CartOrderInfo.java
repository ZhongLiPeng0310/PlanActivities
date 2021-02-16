package com.plan.app.clientOrder.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CartOrderInfo {
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 付款时间
     */
    private Date createTime;
    /**
     * 订单详情编码
     */
    private String detailCode;
    /**
     * 购物车编码
     */
    private String cartCode;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 订单总价
     */
    private float orderMoney;
    /**
     * 选择商品的数量
     */
    private int sumOrder;
    /**
     * 购买数量
     */
    private int orderSum;
    /**
     * 订单状态  订单状态 0取消订单 1到货 2取消到货 3已取货 4取消已取货 5已完成（已完成未评价） 6取消已完成 7已付款 8已完成已评价
     */
    private int orderState;
    /**
     * 该订单购买是商品总数量
     */
    private int sumGoods;
    /**
     * 门店编码
     */
    private String storeCode;
    /**
     * 下单人
     */
    private String userName;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 当前商品的库存
     */
    private int stock;
    /**
     * 销售量
     */
    private int sumSale;
    /**
     * 商品售价
     */
    private float salePrice;
    /**
     * 商品成本价
     */
    private float costPrice;
    /**
     * 当前登录人id
     */
    private String userId;
    /**
     * 地址编码
     */
    private String addressCode;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创建人
     */
    private String createName;
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
    private int version;
}
