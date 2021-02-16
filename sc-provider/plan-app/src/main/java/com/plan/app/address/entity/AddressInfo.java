package com.plan.app.address.entity;

import lombok.Data;

/**
 * 购物车实体类
 * @author zhong
 * @date 2021-01-05
 */
@Data
public class AddressInfo {
    /**
     * 购物车编码
     */
    private String addressCode;
    /**
     * 客户编码
     */
    private String userCode;
    /**
     * 当前登录人编码
     */
    private String userId;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 区编码
     */
    private String areaCode;
    /**
     * 区名称
     */
    private String areaName;
    /**
     * 地址
     */
    private String address;
    /**
     * 收件人名称
     */
    private String getName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 默认
     */
    private int betrue;
    /**
     * 作废标记  0保留  1删除
     */
    private int isDeleted;
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
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
}
