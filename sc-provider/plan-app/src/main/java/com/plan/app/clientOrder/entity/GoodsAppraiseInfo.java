package com.plan.app.clientOrder.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品评价实体类
 * @author zhong
 * @date 2021-01-05
 */
@Data
public class GoodsAppraiseInfo {
    /**
     * 评价编码
     */
    private String appraiseCode;
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 用户账号
     */
    private String userAcct;
    /**
     * 当前登录人id
     */
    private String userId;
    /**
     * 评价内容
     */
    private String appraiseDetail;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 图片编码
     */
    private String imageCode;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 图片序号
     */
    private int imageNum;
    /**
     * 评价等级(1-2星差评 3-4星中评 5好评)
     */
    private String appraiseLevel;
    /**
     * 评价评价星级
     */
    private String avgLevel;
    /**
     * 创建时间
     * @return
     */
    private Date createTime;
    /**
     * 图片集合
     */
    private List<ImageInfo> imageInfoList;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创建者
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
