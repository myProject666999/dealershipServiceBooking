package com.dealership.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.entity.Technician;
import com.dealership.mapper.TechnicianMapper;
import com.dealership.service.TechnicianService;
import org.springframework.stereotype.Service;

@Service
public class TechnicianServiceImpl extends ServiceImpl<TechnicianMapper, Technician> implements TechnicianService {
}
