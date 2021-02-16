package com.plan.app.storer.entity;

import lombok.Data;

@Data
public class StorerInfo {
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 站点编号
     */
    private String storeCode;
    /**
     * 站点名称
     */
    private String storeName;
    /**
     * 站点地址
     */
    private String address;

}
