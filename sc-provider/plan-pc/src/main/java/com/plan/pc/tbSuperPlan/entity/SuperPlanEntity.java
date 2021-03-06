package com.plan.pc.tbSuperPlan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author zlp
 */
@Data
@Entity
@Table(name = "tb_super_plan")
public class SuperPlanEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "create_name")
    private String createName;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "update_name")
    private String updateName;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Column(name = "version")
    private Integer version;

}
