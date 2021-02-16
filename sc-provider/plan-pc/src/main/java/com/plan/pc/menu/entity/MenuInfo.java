package com.plan.pc.menu.entity;

import lombok.Data;

/**
 * 菜单实体类
 * @author zhong
 * @date 2021-01-04
 */
@Data
public class MenuInfo {
    /**
     * 菜单编码
     */
    private String menuCode;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路径
     */
    private String route;
    /**
     * 菜单备注
     */
    private String remarks;
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


}
