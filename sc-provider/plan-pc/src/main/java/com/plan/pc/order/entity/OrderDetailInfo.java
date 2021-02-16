package com.plan.pc.order.entity;

import lombok.Data;

@Data
public class OrderDetailInfo {
    /**
     * 订单详情编号
     */
    private String orderDetailCode;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 购买数量
     */
    private int orderSum;
    /**
     * 售价
     */
    private double salePrice;
    /**
     * 总金额
     */
    private double orderMoney;
    /**
     * 手机
     */
    private String phone;
    /**
     * 付款时间
     */
    private String  payTime;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新者
     */
    private String updateName;
    /**
     * 版本号
     */
    private String version;
}
