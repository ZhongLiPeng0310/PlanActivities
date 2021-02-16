package com.plan.pc.customer.entity;

import lombok.Data;

/**
 * @author 12533
 */
@Data
public class CustomerInfo {
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
    /**
     * 客户编号
     */
    private String customerCode;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 客户账号
     */
    private String userAcct;
    /**
     * 客户姓名
     */
    private String userName;
    /**
     * 性别 0男1女
     */
    private int sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 身份证
     */
    private String idCard;

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
     * 角色 0管理员 1店长
     */
    private int role;
    /**
     * 邀请码
     */
    private String invitationCode;

}
