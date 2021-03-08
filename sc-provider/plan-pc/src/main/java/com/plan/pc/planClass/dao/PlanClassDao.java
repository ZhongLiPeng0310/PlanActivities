package com.plan.pc.planClass.dao;

import com.plan.pc.planClass.entity.PlanClassVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 12533
 */
@Mapper
public interface PlanClassDao {
    List<PlanClassVo> listPlanClassByPage(PlanClassVo planClassVo);
}
