package com.plan.pc.tbUser.entity;

import lombok.Data;

/**
 * @author 12533
 */
@Data
public class TbUserInfo {
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户账号
     */
    private String userAcct;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户性别 0男1女
     */
    private int sex;
    /**
     * 手机号
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
     * 角色（1管理员 2客户 3站长 4供应商）
     */
    private String role;
    /**
     * 用户头像路径
     */
    private String imagePath;
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
     */
    private String createTime;
    /**
     * 更新者
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

}
