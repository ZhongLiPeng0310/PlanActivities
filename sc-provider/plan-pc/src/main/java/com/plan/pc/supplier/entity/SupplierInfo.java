package com.plan.pc.supplier.entity;

import lombok.Data;
import org.junit.Test;

/**
 * @Description 供应商实体类
 * @Author zhong
 * @date 2021-01-04
 */
@Data
public class SupplierInfo {
    /**
     * 供应商编号
     */
    private String userCode;
    /**
     * 供应商姓名
     */
    private String userName;
    /**
     * 角色
     */
    private String role;
    /**
     * 角色编号
     */
    private String roleCode;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 用户账号
     */
    private String userAcct;
    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 身份证号码
     */
    private String idCard;
    /**
     * 省编号
     */
    private String provinceCode;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 市编号
     */
    private String cityCode;
    /**
     * 市名称
     */
    private String cityName;
    /**
     * 区编号
     */
    private String areaCode;
    /**
     * 区名称
     */
    private String areaName;
    /**
     * 作废标记 0为存在，1为作废
     */
    private int isDeleted;
    /**
     * 创造时间
     */
    private String createTime;
    /**
     * 创建者
     */
    private String createName;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新者
     */
    private String updateName;
    /**
     * 版本号(用户表)
     */
    private String userVersion;
    /**
     * 版本号（供应商表）
     */
    private String supplierVersion;
}
