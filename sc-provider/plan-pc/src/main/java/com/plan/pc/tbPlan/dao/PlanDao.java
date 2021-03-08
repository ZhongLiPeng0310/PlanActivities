package com.plan.pc.tbPlan.dao;

import com.plan.pc.planClass.entity.PlanClassVo;
import com.plan.pc.tbPlan.entity.PlanVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 12533
 */
@Mapper
public interface PlanDao {
    List<PlanVo> listPlanByPage(PlanVo planVo);
}
