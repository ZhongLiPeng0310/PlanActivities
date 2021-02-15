package com.plan.app.homePage.dao;

import com.plan.app.homePage.entity.HomePageInfo;
import com.plan.app.homePage.entity.VillageGoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName homePageDao
 * @Description homePage
 * @author jintian
 * @date 2020-08-31
 */
@Mapper
public interface HomePageDao {
    /**
     * 查询轮播图
     * @param
     * @return
     */
    List<HomePageInfo> getPictureShow();
    /**
     * 查询展示数量
     * @param
     * @return
     */
    int getShowNumber();
    /**
     * 查询热门商品
     * @param showNumber
     * @return
     */
    List<HomePageInfo> getHotVillage(@Param("showNumber")int showNumber);

    /**
     * 查询乡村和商品
     * @param villageCode
     * @return
     */
    List<VillageGoodsInfo> getVillageAndGoods(@Param("villageCode") String villageCode);
}
