package com.plan.pc.tbPlanStore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 12533
 */
@Data
public class PlanStoreInfo {
    private String id;

    private String storeName;

    private String planId;

    private String storeIntroduce;

    private Integer isDeleted;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    private String updateName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;

    private Integer version;
}
