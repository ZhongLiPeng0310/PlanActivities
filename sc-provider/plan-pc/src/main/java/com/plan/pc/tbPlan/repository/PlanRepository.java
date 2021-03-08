package com.plan.pc.tbPlan.repository;

import com.plan.pc.tbPlan.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 12533
 */
public interface PlanRepository extends JpaRepository<PlanEntity,String> {
    PlanEntity findById(String id);
}
