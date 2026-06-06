package com.dealership.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dealership.entity.RepairOrder;

import java.util.List;
import java.util.Map;

public interface RepairOrderService extends IService<RepairOrder> {

    Map<String, Object> createOrder(Map<String, Object> payload);

    Map<String, Object> getOrderDetail(Long orderId);

    List<RepairOrder> listByCustomerId(Long customerId);

    void payOrder(Long orderId, Integer payMethod);
}
