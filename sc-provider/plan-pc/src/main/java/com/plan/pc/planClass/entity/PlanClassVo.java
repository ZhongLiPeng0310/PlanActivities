package com.plan.pc.planClass.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 12533
 */
@Data
public class PlanClassVo {
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;

    private String id;

    private String className;

    private String parentId;

    private String isParent;

    private String remark;

    private Integer isDeleted;

    private String createName;

    private Date createTime;

    private String updateName;

    private Date updateTime;
}
