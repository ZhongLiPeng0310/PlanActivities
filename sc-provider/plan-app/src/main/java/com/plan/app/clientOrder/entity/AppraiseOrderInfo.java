package com.plan.app.clientOrder.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AppraiseOrderInfo implements Serializable {
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 商品的平均星级
     */
    private String avgLevel;
    /**
     * 评价信息
     */
    private List<AppraiseInfo> appraiseInfoList;
    /**
     * 商品信息
     */
    private List<GoodsInfo> goodsInfoList;
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
