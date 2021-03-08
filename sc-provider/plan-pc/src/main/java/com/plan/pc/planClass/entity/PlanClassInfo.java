package com.plan.pc.planClass.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 12533
 */
@Data
public class PlanClassInfo {

    private String id;

    private String className;

    private String parentId;

    private String isParent;

    private String remark;

    private Integer isDeleted;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    private String updateName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;

    private Integer version;
}
