package com.plan.pc.planClass.repository;

import com.plan.pc.planClass.entity.PlanClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanClassRepository extends JpaRepository<PlanClassEntity,String> {
    PlanClassEntity findById(String id);
}
