package com.plan.pc.tbUser.repository;

import com.plan.pc.tbUser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author 12533
 */
public interface UserRepository extends JpaRepository<UserEntity,String>{
    UserEntity findById(String id);

    UserEntity findByUserAcct(String id);
}
