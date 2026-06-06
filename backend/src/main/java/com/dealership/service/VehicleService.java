package com.dealership.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dealership.entity.Vehicle;

import java.util.List;

public interface VehicleService extends IService<Vehicle> {

    List<Vehicle> listByCustomerId(Long customerId);

    Vehicle bindVehicle(Vehicle vehicle);

    String getMaintenanceSuggestion(Long vehicleId);
}
