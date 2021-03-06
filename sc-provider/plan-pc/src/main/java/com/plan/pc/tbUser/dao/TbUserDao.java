package com.plan.pc.tbUser.dao;

import com.plan.pc.tbUser.entity.TbUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 12533
 */
@Mapper
public interface TbUserDao {

    /**
     * 分页
     * @param userInfo
     * @return
     */
    List<TbUserInfo> listUsersByPage(TbUserInfo userInfo);

}
