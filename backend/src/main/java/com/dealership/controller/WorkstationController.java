package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.Workstation;
import com.dealership.service.BookingService;
import com.dealership.service.WorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workstation")
public class WorkstationController {

    @Autowired
    private WorkstationService workstationService;

    @GetMapping("/list")
    public Result<List<Workstation>> list() {
        return Result.success(workstationService.listEnabled());
    }

    @GetMapping("/availableSlots")
    public Result<Map<LocalDate, List<BookingService.TimeSlot>>> getAvailableSlots(
            @RequestParam Long workstationId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam int days) {
        return Result.success(workstationService.getAvailableSlots(workstationId, startDate, days));
    }

    @GetMapping("/{id}")
    public Result<Workstation> getById(@PathVariable Long id) {
        return Result.success(workstationService.getById(id));
    }

    @PostMapping("/")
    public Result<Void> save(@RequestBody Workstation workstation) {
        workstationService.save(workstation);
        return Result.success();
    }

    @PutMapping("/")
    public Result<Void> update(@RequestBody Workstation workstation) {
        workstationService.updateById(workstation);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workstationService.removeById(id);
        return Result.success();
    }
}
