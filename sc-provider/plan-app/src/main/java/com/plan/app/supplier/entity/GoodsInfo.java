package com.plan.app.supplier.entity;

import lombok.Data;

/**
 * 产品的对象
 * @author zhong
 * @date 2021-01-05
 *
 */
@Data
public class GoodsInfo {
    /**
     * 产品名称
     */
    private String goodsName;
    /**
     * 产品编码
     */
    private String goodsCode;
    /**
     * 一级分类编码
     */
    private String classFirstCode;
    /**
     * 二级分类编码
     */
    private String classSecondCode;
    /**
     * 一级分类名称
     */
    private String classFirstName;
    /**
     * 二级分类名称
     */
    private String classSecondName;
    /**
     * 广告
     */
    private String advertisement;
    /**
     * 产品介绍
     */
    private String goodsIntroduce;
    /**
     * 供应商编码
     */
    private String userCode;
    /**
     * 供应商名字
     */
    private String userName;
    /**
     * 库存
     */
    private String stock;
    /**
     * 成本价
     */
    private String costPrice;
    /**
     * 售价
     */
    private String salePrice;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 保质期
     */
    private String qualityDate;
    /**
     * 出品地址
     */
    private String goodsAddress;
    /**
     * 站点编码
     */
    private String storeCode;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 站点名称
     */
    private String storeName;
    /**
     * 产品的状态
     */
    private int goodsState;
    /**
     * 审核状态
     */
    private int audit;
    /**
     * 作废标记 0保留 1删除
     */
    private int isDeleted;
    /**
     * 用户角色标记
     */
    private int role;
    /**
     * 当前登录人id
     */
    private String userId;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新人
     */
    private String updateName;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 版本号
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
