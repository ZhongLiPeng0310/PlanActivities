package com.plan.pc.customer.dao;

import com.plan.pc.customer.entity.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerDao {
    /**
     * 获取所有客户信息
     * @param customerInfo
     * @return 所有客户信息
     */
    List<CustomerInfo> listCustomerByPage(CustomerInfo customerInfo);


}
