package com.plan.pc.tbPictureShow.repository;

import com.plan.pc.tbPictureShow.entity.TbPictureShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zlp
 */
public interface TbPictureShowRepository extends JpaRepository<TbPictureShowEntity,String> {
    TbPictureShowEntity findById(String id);
}
