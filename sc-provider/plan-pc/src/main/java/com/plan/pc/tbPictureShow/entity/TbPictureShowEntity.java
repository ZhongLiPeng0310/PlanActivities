package com.plan.pc.tbPictureShow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_picture_show")
public class TbPictureShowEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "sort_no")
    private Integer sortNo;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "show_image_url")
    private String showImageUrl;

    @Column(name = "state")
    private String state;

    @Column(name = "begin_time")
    private Date beginTime;

    @Column(name = "end_time")
    private Date endTime;

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
