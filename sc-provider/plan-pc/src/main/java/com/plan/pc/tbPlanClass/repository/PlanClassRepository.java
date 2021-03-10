package com.plan.pc.tbPlanClass.repository;

import com.plan.pc.tbPlanClass.entity.PlanClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanClassRepository extends JpaRepository<PlanClassEntity,String> {
    PlanClassEntity findById(String id);
}
