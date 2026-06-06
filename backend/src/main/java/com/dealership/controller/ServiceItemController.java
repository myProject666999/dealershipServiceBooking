package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.ServiceItem;
import com.dealership.service.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serviceItem")
public class ServiceItemController {

    @Autowired
    private ServiceItemService serviceItemService;

    @GetMapping("/list")
    public Result<List<ServiceItem>> list() {
        return Result.success(serviceItemService.listOnSale());
    }

    @GetMapping("/{id}")
    public Result<ServiceItem> getById(@PathVariable Long id) {
        return Result.success(serviceItemService.getById(id));
    }

    @PostMapping("/")
    public Result<Void> save(@RequestBody ServiceItem serviceItem) {
        serviceItemService.save(serviceItem);
        return Result.success();
    }

    @PutMapping("/")
    public Result<Void> update(@RequestBody ServiceItem serviceItem) {
        serviceItemService.updateById(serviceItem);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        serviceItemService.removeById(id);
        return Result.success();
    }
}
