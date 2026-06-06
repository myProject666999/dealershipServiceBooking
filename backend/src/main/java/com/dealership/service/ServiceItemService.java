package com.dealership.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dealership.entity.ServiceItem;

import java.util.List;

public interface ServiceItemService extends IService<ServiceItem> {

    List<ServiceItem> listOnSale();
}
