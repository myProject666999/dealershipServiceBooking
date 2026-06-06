package com.dealership.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.common.BusinessException;
import com.dealership.entity.Part;
import com.dealership.entity.RepairOrder;
import com.dealership.entity.RepairOrderItem;
import com.dealership.entity.RepairOrderPart;
import com.dealership.mapper.PartMapper;
import com.dealership.mapper.RepairOrderItemMapper;
import com.dealership.mapper.RepairOrderMapper;
import com.dealership.mapper.RepairOrderPartMapper;
import com.dealership.service.RepairOrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairOrderServiceImpl extends ServiceImpl<RepairOrderMapper, RepairOrder> implements RepairOrderService {

    @Autowired
    private RepairOrderItemMapper repairOrderItemMapper;

    @Autowired
    private RepairOrderPartMapper repairOrderPartMapper;

    @Autowired
    private PartMapper partMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createOrder(Map<String, Object> payload) {
        Map<String, Object> orderMap = (Map<String, Object>) payload.get("order");
        List<Map<String, Object>> itemsList = (List<Map<String, Object>>) payload.get("items");
        List<Map<String, Object>> partsList = (List<Map<String, Object>>) payload.get("parts");

        RepairOrder order = objectMapper.convertValue(orderMap, RepairOrder.class);
        List<RepairOrderItem> items = null;
        if (itemsList != null) {
            items = objectMapper.convertValue(itemsList, new TypeReference<List<RepairOrderItem>>() {});
        }
        List<RepairOrderPart> parts = null;
        if (partsList != null) {
            parts = objectMapper.convertValue(partsList, new TypeReference<List<RepairOrderPart>>() {});
        }

        String orderNo = "WO" + System.currentTimeMillis();
        order.setOrderNo(orderNo);
        if (order.getStatus() == null) {
            order.setStatus(1);
        }
        if (order.getPayStatus() == null) {
            order.setPayStatus(0);
        }
        save(order);

        BigDecimal laborFee = BigDecimal.ZERO;
        if (items != null && !items.isEmpty()) {
            for (RepairOrderItem item : items) {
                if (item.getSubtotal() != null) {
                    laborFee = laborFee.add(item.getSubtotal());
                }
            }
        }

        BigDecimal partsFee = BigDecimal.ZERO;
        if (parts != null && !parts.isEmpty()) {
            for (RepairOrderPart part : parts) {
                if (part.getSubtotal() != null) {
                    partsFee = partsFee.add(part.getSubtotal());
                }
            }
        }

        order.setLaborFee(laborFee);
        order.setPartsFee(partsFee);
        order.setTotalAmount(laborFee.add(partsFee));
        updateById(order);

        if (items != null && !items.isEmpty()) {
            for (RepairOrderItem item : items) {
                item.setOrderId(order.getId());
                repairOrderItemMapper.insert(item);
            }
        }

        if (parts != null && !parts.isEmpty()) {
            for (RepairOrderPart part : parts) {
                part.setOrderId(order.getId());
                repairOrderPartMapper.insert(part);

                Part dbPart = partMapper.selectById(part.getPartId());
                if (dbPart == null) {
                    throw new BusinessException("配件不存在：" + part.getPartName());
                }
                int qty = part.getQuantity() != null ? part.getQuantity() : 0;
                if (dbPart.getStockQuantity() < qty) {
                    throw new BusinessException("配件库存不足：" + dbPart.getName());
                }
                dbPart.setStockQuantity(dbPart.getStockQuantity() - qty);
                partMapper.updateById(dbPart);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        return result;
    }

    @Override
    public Map<String, Object> getOrderDetail(Long orderId) {
        RepairOrder order = getById(orderId);
        if (order == null) {
            throw new BusinessException("工单不存在");
        }
        List<RepairOrderItem> items = repairOrderItemMapper.selectList(
                new LambdaQueryWrapper<RepairOrderItem>().eq(RepairOrderItem::getOrderId, orderId));
        List<RepairOrderPart> parts = repairOrderPartMapper.selectList(
                new LambdaQueryWrapper<RepairOrderPart>().eq(RepairOrderPart::getOrderId, orderId));

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("items", items);
        result.put("parts", parts);
        return result;
    }

    @Override
    public List<RepairOrder> listByCustomerId(Long customerId) {
        return list(new LambdaQueryWrapper<RepairOrder>()
                .eq(RepairOrder::getCustomerId, customerId)
                .orderByDesc(RepairOrder::getCreateTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(Long orderId, Integer payMethod) {
        RepairOrder order = getById(orderId);
        if (order == null) {
            throw new BusinessException("工单不存在");
        }
        if (order.getPayStatus() != null && order.getPayStatus() == 1) {
            throw new BusinessException("工单已支付，请勿重复结算");
        }
        order.setPayStatus(1);
        order.setPayMethod(payMethod);
        order.setPayTime(LocalDateTime.now());
        order.setStatus(4);
        order.setOutTime(LocalDateTime.now());
        updateById(order);
    }
}
