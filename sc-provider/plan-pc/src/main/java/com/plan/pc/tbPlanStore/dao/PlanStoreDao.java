package com.plan.pc.tbPlanStore.dao;

import com.plan.pc.tbPlanStore.entity.PlanStoreVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 12533
 */
@Mapper
public interface PlanStoreDao {

    List<PlanStoreVo> listPlanStoreByPage(PlanStoreVo planStoreVo);

}
