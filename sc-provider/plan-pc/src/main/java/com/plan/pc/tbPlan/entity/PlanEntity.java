package com.plan.pc.tbPlan.entity;

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
@Table(name = "tb_plan")
public class PlanEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "planer_id")
    private String planerId;

    @Column(name = "start_price")
    private Double startPrice;

    @Column(name = "goods_id")
    private String goodsId;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "remark")
    private String remark;

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
