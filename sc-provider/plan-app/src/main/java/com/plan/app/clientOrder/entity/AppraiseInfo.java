package com.plan.app.clientOrder.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppraiseInfo implements Serializable {
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 评价编码
     */
    private String appraiseCode;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 评价星级
     */
    private String appraiseLevel;
    /**
     * 评价内容
     */
    private String appraiseDetail;
    /**
     * 商品平均星级
     */
    private String avgLevel;
    /**
     * 评价星级
     */
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
