package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.Part;
import com.dealership.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/part")
public class PartController {

    @Autowired
    private PartService partService;

    @GetMapping("/list")
    public Result<List<Part>> list() {
        return Result.success(partService.list());
    }

    @GetMapping("/{id}")
    public Result<Part> getById(@PathVariable Long id) {
        return Result.success(partService.getById(id));
    }

    @PostMapping("/")
    public Result<Void> save(@RequestBody Part part) {
        partService.save(part);
        return Result.success();
    }

    @PutMapping("/")
    public Result<Void> update(@RequestBody Part part) {
        partService.updateById(part);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        partService.removeById(id);
        return Result.success();
    }
}
