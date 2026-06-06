package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.RepairOrder;
import com.dealership.service.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repairOrder")
public class RepairOrderController {

    @Autowired
    private RepairOrderService repairOrderService;

    @PostMapping("/create")
    public Result<Map<String, Object>> create(@RequestBody Map<String, Object> payload) {
        return Result.success(repairOrderService.createOrder(payload));
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> getById(@PathVariable Long id) {
        return Result.success(repairOrderService.getOrderDetail(id));
    }

    @GetMapping("/listByCustomer/{customerId}")
    public Result<List<RepairOrder>> listByCustomer(@PathVariable Long customerId) {
        return Result.success(repairOrderService.listByCustomerId(customerId));
    }

    @PostMapping("/pay/{id}")
    public Result<Void> pay(@PathVariable Long id, @RequestParam Integer payMethod) {
        repairOrderService.payOrder(id, payMethod);
        return Result.success();
    }
}
