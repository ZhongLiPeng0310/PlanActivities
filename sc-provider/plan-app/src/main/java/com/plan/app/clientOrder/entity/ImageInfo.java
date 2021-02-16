package com.plan.app.clientOrder.entity;

import java.util.List;

/**
 * 图片实体类
 * @author zhong
 * @date 2021-01-05
 */
public class ImageInfo {
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
     * 商品编码
     */
    private String goodsCode;
    /**
     * 订单编码
     */
    private String orderCode;
    /**
     * 当前登录人的id
     */
    private String userId;
    /**
     * 图片集合
     */
    private List<GoodsAppraiseInfo> goodsAppraiseInfos;
    /**
     * 用户编码
     */
    private String userCode;
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
}
