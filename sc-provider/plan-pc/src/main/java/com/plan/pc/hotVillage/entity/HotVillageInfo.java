package com.plan.pc.hotVillage.entity;

import lombok.Data;

/**
 * @Description 热门乡村实体类
 * @author zhong
 * @date 2021-01-04
 */
@Data
public class HotVillageInfo {
    /**
     * 热门乡村编号
     */
    private String hotCode;
    /**
     * 乡村编号
     */
    private String villageCode;
    /**
     * 乡村名称
     */
    private String villageName;
    /**
     * 乡村介绍
     */
    private String villageIntroduce;
    /**
     * 排序序号
     */
    private int hotId;
    /**
     * 展示数量
     */
    private int showNo;
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
     * 版本号
     */
    private String version;
}
