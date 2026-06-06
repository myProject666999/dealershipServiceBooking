package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.Technician;
import com.dealership.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technician")
public class TechnicianController {

    @Autowired
    private TechnicianService technicianService;

    @GetMapping("/list")
    public Result<List<Technician>> list() {
        return Result.success(technicianService.list());
    }

    @GetMapping("/{id}")
    public Result<Technician> getById(@PathVariable Long id) {
        return Result.success(technicianService.getById(id));
    }

    @PostMapping("/")
    public Result<Void> save(@RequestBody Technician technician) {
        technicianService.save(technician);
        return Result.success();
    }

    @PutMapping("/")
    public Result<Void> update(@RequestBody Technician technician) {
        technicianService.updateById(technician);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        technicianService.removeById(id);
        return Result.success();
    }
}
