package com.plan.pc.tbSuperPlan.repository;

import com.plan.pc.tbSuperPlan.entity.SuperPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperPlanRepository extends JpaRepository<SuperPlanEntity,String> {
    SuperPlanEntity findById(String id);

    List<SuperPlanEntity> findByIsDeleted(Integer code);
}
