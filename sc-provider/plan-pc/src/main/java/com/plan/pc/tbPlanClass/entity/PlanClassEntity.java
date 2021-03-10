package com.plan.pc.tbPlanClass.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author 12533
 */
@Data
@Entity
@Table(name = "tb_plan_class")
public class PlanClassEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "is_parent")
    private String isParent;

    @Column(name = "remark")
    private String remark;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "create_name")
    private String createName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_name")
    private String updateName;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "version")
    private Integer version;

}
