package com.dealership.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.entity.Customer;
import com.dealership.mapper.CustomerMapper;
import com.dealership.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
}
