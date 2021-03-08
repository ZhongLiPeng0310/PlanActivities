package com.plan.pc.tbPlan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author 12533
 */
@Data
public class PlanVo {
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;

    private String id;

    private String planName;

    private String classId;

    private String planerId;

    private Double startPrice;

    private String goodsId;

    private Integer isDeleted;

    private String remark;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    private String updateName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;

    private Integer version;
}
