package com.dealership.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.entity.ServiceItem;
import com.dealership.mapper.ServiceItemMapper;
import com.dealership.service.ServiceItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemServiceImpl extends ServiceImpl<ServiceItemMapper, ServiceItem> implements ServiceItemService {

    @Override
    public List<ServiceItem> listOnSale() {
        LambdaQueryWrapper<ServiceItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ServiceItem::getStatus, 1);
        wrapper.orderByAsc(ServiceItem::getSort);
        return list(wrapper);
    }
}
