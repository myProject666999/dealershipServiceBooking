package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.Vehicle;
import com.dealership.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/listByCustomer/{customerId}")
    public Result<List<Vehicle>> listByCustomer(@PathVariable Long customerId) {
        return Result.success(vehicleService.listByCustomerId(customerId));
    }

    @GetMapping("/{id}")
    public Result<Vehicle> getById(@PathVariable Long id) {
        return Result.success(vehicleService.getById(id));
    }

    @PostMapping("/bind")
    public Result<Vehicle> bind(@RequestBody Vehicle vehicle) {
        return Result.success(vehicleService.bindVehicle(vehicle));
    }

    @PutMapping("/")
    public Result<Void> update(@RequestBody Vehicle vehicle) {
        vehicleService.updateById(vehicle);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        vehicleService.removeById(id);
        return Result.success();
    }

    @GetMapping("/suggestion/{vehicleId}")
    public Result<String> getSuggestion(@PathVariable Long vehicleId) {
        return Result.success(vehicleService.getMaintenanceSuggestion(vehicleId));
    }
}
