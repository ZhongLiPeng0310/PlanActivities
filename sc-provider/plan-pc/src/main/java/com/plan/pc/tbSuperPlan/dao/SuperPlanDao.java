package com.plan.pc.tbSuperPlan.dao;

import com.plan.pc.tbSuperPlan.entity.SuperPlanVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zlp
 */
@Mapper
public interface SuperPlanDao {
    List<SuperPlanVo> listSuperPlanByPage(SuperPlanVo superPlanVo);
}
