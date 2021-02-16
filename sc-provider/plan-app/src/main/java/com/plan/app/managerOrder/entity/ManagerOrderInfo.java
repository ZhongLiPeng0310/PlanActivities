package com.plan.app.managerOrder.entity;

import lombok.Data;

import java.util.List;

@Data
public class ManagerOrderInfo {
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 付款时间
     */
    private String createTime;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 订单总价
     */
    private float orderMoney;
    /**
     * 购买数量
     */
    private int orderSum;
    /**
     * 该订单购买是商品总数量
     */
    private int sumGoods;
    /**
     * 订单状态  订单状态 0取消订单 1到货 2取消到货 3已取货 4取消已取货 5已完成（已完成未评价） 6取消已完成 7已付款 8已完成已评价
     */
    private int orderState;
    /**
     * 门店编码
     */
    private String storeCode;
    /**
     * 门店名称
     */
    private String storeName;
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
     * 详情地址
     */
    private String address;
    /**
     * 商品集合
     */
    private List<GoodsInfo> goodsList;

    /**
     * 商品售价
     */
    private float salePrice;
    /**
     * 商品成本价
     */
    private float costPrice;

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
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 商品图片
     */
    private String imagePath;

    /**
     * 评价内容
     */
    private String appraiseDetail;
    /**
     * 评价星级
     */
    private int appraiseLevel;
    /**
     * 图片顺序
     */
    private int imageNum;
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
