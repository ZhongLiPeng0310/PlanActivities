package com.plan.pc.tbGoods.repository;

import com.plan.pc.tbGoods.entity.TbGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 12533
 */
public interface TbGoodsRepository extends JpaRepository<TbGoodsEntity,String> {
    TbGoodsEntity findById(String id);
}
