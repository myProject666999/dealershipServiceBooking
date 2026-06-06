package com.dealership.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.common.BusinessException;
import com.dealership.entity.Vehicle;
import com.dealership.mapper.VehicleMapper;
import com.dealership.service.VehicleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements VehicleService {

    @Override
    public List<Vehicle> listByCustomerId(Long customerId) {
        return list(new LambdaQueryWrapper<Vehicle>().eq(Vehicle::getCustomerId, customerId));
    }

    @Override
    public Vehicle bindVehicle(Vehicle vehicle) {
        if (vehicle.getLicensePlate() == null && vehicle.getVin() == null) {
            throw new BusinessException("车牌号和车架号不能同时为空");
        }
        if (vehicle.getLicensePlate() != null) {
            Long count = count(new LambdaQueryWrapper<Vehicle>().eq(Vehicle::getLicensePlate, vehicle.getLicensePlate()));
            if (count > 0) {
                throw new BusinessException("该车牌号已被绑定");
            }
        }
        if (vehicle.getVin() != null) {
            Long count = count(new LambdaQueryWrapper<Vehicle>().eq(Vehicle::getVin, vehicle.getVin()));
            if (count > 0) {
                throw new BusinessException("该车架号已被绑定");
            }
        }
        save(vehicle);
        return vehicle;
    }

    @Override
    public String getMaintenanceSuggestion(Long vehicleId) {
        Vehicle vehicle = getById(vehicleId);
        if (vehicle == null) {
            throw new BusinessException("车辆不存在");
        }
        int currentMileage = vehicle.getCurrentMileage() == null ? 0 : vehicle.getCurrentMileage();
        int lastMileage = vehicle.getLastServiceMileage() == null ? 0 : vehicle.getLastServiceMileage();
        int mileageSince = currentMileage - lastMileage;

        StringBuilder sb = new StringBuilder();
        if (lastMileage == 0 && vehicle.getLastServiceTime() == null) {
            sb.append("您的爱车尚未有保养记录，建议进行首次小保养。");
        } else {
            sb.append("您的爱车上次保养在").append(mileageSince).append(" km之前");
            LocalDateTime lastTime = vehicle.getLastServiceTime();
            if (lastTime != null) {
                long days = ChronoUnit.DAYS.between(lastTime, LocalDateTime.now());
                sb.append("（约").append(days).append("天前）");
            }
            if (mileageSince >= 5000) {
                sb.append("，已达到小保养里程，建议本次做小保养");
            } else if (mileageSince >= 3000) {
                sb.append("，接近保养里程，建议近期做小保养");
            } else {
                sb.append("，暂未到常规保养里程");
            }
            if (mileageSince >= 20000 || currentMileage >= 20000) {
                sb.append("；当前里程已达").append(currentMileage).append("km，建议进行大保养");
            }
            sb.append("。");
        }
        return sb.toString();
    }
}
