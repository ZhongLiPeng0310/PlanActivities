package com.plan.app.goods.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoodsEvaluates {
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
    /**
     * 评价编号
     */
    private String appraiseCode;
    /**
     * 客户编号
     */
    private String userCode;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 商品等级(差评按钮传值为1，中评为3，好评为5)
     */
    private int appraiseLevel;
    /**
     * 用户账号
     */
    private String userAcct;
    /**
     * 评价内容
     */
    private String appraiseDetai;
    /**
     * 评价图片列表
     */
    private List<EvaluateImage> imageInfoList;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新时间
     */
    private String gmtModified;
    /**
     * 更新者
     */
    private String lastModifiedBy;
    /**
     * 版本号
     */
    private String version;
}