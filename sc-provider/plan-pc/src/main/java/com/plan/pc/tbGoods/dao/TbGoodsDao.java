package com.plan.pc.tbGoods.dao;

import com.plan.pc.tbGoods.entity.TbGoodsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 12533
 */
@Mapper
public interface TbGoodsDao {
    List<TbGoodsInfo> listGoodsByPage(TbGoodsInfo tbGoodsInfo);
}
