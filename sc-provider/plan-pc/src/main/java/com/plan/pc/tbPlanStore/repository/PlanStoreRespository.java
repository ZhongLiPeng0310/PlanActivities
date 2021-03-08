package com.plan.pc.tbPlanStore.repository;

import com.plan.pc.tbPlanStore.entity.PlanStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 12533
 */
public interface PlanStoreRespository extends JpaRepository<PlanStoreEntity,String> {
    PlanStoreEntity findById(String id);
}
