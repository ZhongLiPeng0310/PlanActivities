package com.plan.pc.customer.controller;


import com.neusoft.core.restful.AppResponse;
import com.plan.pc.customer.entity.CustomerInfo;
import com.plan.pc.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 客户管理
 * @author chenchaotao
 * @time 2020-08-22
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Resource
    private CustomerService customerService;

    /**
     * listCustomerByPage 客户列表
     * @param customerInfo
     * @return AppResponse
     * @author chenchaotao
     * @Date 2020-08-22
     */
    @RequestMapping(value = "listCustomerByPage")
    public AppResponse listCustomerByPage(CustomerInfo customerInfo){
        try{
            return customerService.listCustomerByPage(customerInfo);
        }catch (Exception e){
            logger.error("查询客户列表异常",e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
