package com.plan.pc.tbPlanClass.dao;

import com.plan.pc.tbPlanClass.entity.PlanClassVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 12533
 */
@Mapper
public interface PlanClassDao {
    List<PlanClassVo> listPlanClassByPage(PlanClassVo planClassVo);
}
