package com.plan.app.customer.entity;

import lombok.Data;
import org.junit.Test;

/**
 * 用户实体类
 * @author zhong
 * @date 2021-01-05
 */
@Data
public class UserInfo {
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 用户账号
     */
    private String userAcct;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户新密码
     */
    private String userNewPassword;
    /**
     * 当前登录人的id
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 性别 0女 1男
     */
    private int sex;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份证
     */
    private String idCard;
    /**
     *角色 默认注册为  1 用户
     */
    private int role;
    /**
     * 用户头像路径
     */
    private String imagePath;
    /**
     * 店铺邀请码
     */
    private String inviteCode;
    /**
     * 门店编码
     */
    private String storeCode;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 区名称
     */
    private String areaName;
    /**
     * 门店地址
     */
    private String address;

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
