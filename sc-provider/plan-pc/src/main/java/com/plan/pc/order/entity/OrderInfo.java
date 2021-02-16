package com.plan.pc.order.entity;

import lombok.Data;

import java.util.List;

/**
 * 订单实体类
 * @author zhong
 * @date 2021-01-04
 */
@Data
public class OrderInfo {
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 订单总价
     */
    private String orderMoney;
    /**
     * 订单状态  订单状态 0取消订单 1到货 2取消到货 3已取货 4取消已取货 5已完成（已完成未评价） 6取消已完成 7已付款 8已完成已评价
     */
    private String orderState;
    /**
     * 商品库存
     */
    private int stock;
    /**
     * 门店编码
     */
    private String storeCode;

    /**
     * 下单人
     */
    private String userName;
    /**
     * 下单人手机号码
     */
    private String phone;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 购买数量
     */
    private String orderSum;
    /**
     * 商品售价
     */
    private String salePrice;
    /**
     * 商品成本价
     */
    private String costPrice;
    /**
     * 付款时间
     */
    private String createTime;
    /**
     * 付款时间区间
     */
    private String startPayTime;
    private String endPayTime;
    /**
     * 角色 1管理员  2客户 3店长 4司机
     */
    private int role;
    /**
     * 店铺邀请码
     */
    private String inviteCode;
    /**
     * 当前登录人id
     */
    private String userId;
    /**
     *购买商品的总数
     */
    private String sumGoods;
    /**
     * 地址
     */
    private String address;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
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
    /**
     * 商品集合
     * @return
     */
    private List<GoodsInfo> goodsList;
}
