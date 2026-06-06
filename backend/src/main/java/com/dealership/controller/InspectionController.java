package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.InspectionReport;
import com.dealership.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inspection")
public class InspectionController {

    @Autowired
    private InspectionService inspectionService;

    @PostMapping("/createReport")
    public Result<Map<String, Object>> createReport(@RequestBody Map<String, Object> payload) {
        return Result.success(inspectionService.createReport(payload));
    }

    @GetMapping("/report/{id}")
    public Result<Map<String, Object>> getReportDetail(@PathVariable Long id) {
        return Result.success(inspectionService.getReportDetail(id));
    }

    @GetMapping("/listByVehicle/{vehicleId}")
    public Result<List<InspectionReport>> listByVehicle(@PathVariable Long vehicleId) {
        return Result.success(inspectionService.listByVehicleId(vehicleId));
    }
}
