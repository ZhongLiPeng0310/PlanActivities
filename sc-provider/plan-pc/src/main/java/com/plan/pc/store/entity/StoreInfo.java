package com.plan.pc.store.entity;

import lombok.Data;

@Data
public class StoreInfo {
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
    /**
     * 乡村编码
     */
    private String villageCode;
    /**
     * 站点编号
     */
    private String storeCode;
    /**
     * 站点名称
     */
    private String storeName;
    /**
     * 站长姓名
     */
    private String userName;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 营业执照编码
     */
    private String licenseCode;

    /**
     * 站长账号
     */
    private String userAcct;
    /**
     * 电话
     */
    private String phone;
    /**
     * 省编号
     */
    private String provinceCode;
    /**
     * 市编号
     */
    private String cityCode;
    /**
     * 区编号
     */
    private String areaCode;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 市名称
     */
    private String cityName;
    /**
     * 区名称
     */
    private String areaName;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创建时间
     */
    private String gmtCreate;
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

    /**
     * 分类id
     */
    private String cateId;

    /**
     * 角色 0管理员 1站长
     */
    private int role;

    /**
     * 父级编码
     */
    private String parentCode;
}
