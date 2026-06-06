package com.dealership.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.entity.Part;
import com.dealership.mapper.PartMapper;
import com.dealership.service.PartService;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl extends ServiceImpl<PartMapper, Part> implements PartService {
}
