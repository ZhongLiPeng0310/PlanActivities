package com.plan.pc.pictureShow.entity;

import lombok.Data;
import org.junit.Test;

/**
 * 轮播图实体类
 * @author zhong
 * @date 2021-01-04
 */
@Data
public class PictureShowInfo {
    /**
     *轮播图编码
     */
    private String showCode;
    /**
     * 轮播图排序号
     */
    private int sortNo;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 轮播图状态  1启用 2禁用
     */
    private String showState;
    /**
     *有效期开始
     */

    private String beginTime;
    /**
     * 有效期停止
     */

    private String endTime;
    /**
     * 作废标记 0保留  1删除
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
    private String version;
    /**
     * 页码
     */
    private int pageSize;
    /**
     * 页数
     */
    private int pageNum;
}
