package com.plan.app.goods.entity;

import lombok.Data;

@Data
public class EvaluateImage {
    /**
     * 图片编号
     */
    private String imageCode;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 商品编号
     */
    private String pictureGoodsCode;
    /**
     * 用户编号
     */
    private String userId;
}
