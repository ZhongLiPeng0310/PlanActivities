package com.plan.pc.tbPlanStore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "tb_plan_store")
public class PlanStoreEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "store_introduce")
    private String storeIntroduce;

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
