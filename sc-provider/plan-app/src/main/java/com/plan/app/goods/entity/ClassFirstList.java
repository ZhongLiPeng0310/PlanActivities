package com.plan.app.goods.entity;

import lombok.Data;

@Data
public class ClassFirstList {
    /**
     * 商品分类实体类
     * @author zhong
     * @date 2021-01-05
     */
    /**
     * 分类编码
     */
    private String classCode;
    /**
     * 分类名称
     */
    private String className;
    /**
     * 一级分类编码
     */
    private String classFirstCode;
    /**
     * 一级分类名称
     */
    private String classFirstName;
    /**
     * 二级分类编码
     */
    private String classSecondCode;
    /**
     * 二级分类名称
     */
    private String classSecondName;
    /**
     * 上一级分类编码
     */
    private String lastClassCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 分类等级 1为一级  2为二级
     */
    private int classLevel;
    /**
     * 作废标记 0不删除 1删除
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
    private int version;

    /**
     *页码
     */
    private int pageSize;

    /**
     * 页数
     */
    private int pageNum;
}
