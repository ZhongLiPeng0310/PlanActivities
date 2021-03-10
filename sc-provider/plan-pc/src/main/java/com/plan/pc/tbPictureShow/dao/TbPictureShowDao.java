package com.plan.pc.tbPictureShow.dao;

import com.plan.pc.tbPictureShow.entity.TbPictureShowVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbPictureShowDao {
    List<TbPictureShowVo> listTbPictureShowByPage(TbPictureShowVo tbPictureShowVo);
}
