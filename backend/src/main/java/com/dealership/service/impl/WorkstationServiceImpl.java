package com.dealership.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.entity.Workstation;
import com.dealership.mapper.WorkstationMapper;
import com.dealership.service.BookingService;
import com.dealership.service.WorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class WorkstationServiceImpl extends ServiceImpl<WorkstationMapper, Workstation> implements WorkstationService {

    @Autowired
    private BookingService bookingService;

    @Override
    public List<Workstation> listEnabled() {
        LambdaQueryWrapper<Workstation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Workstation::getStatus, 1);
        return list(wrapper);
    }

    @Override
    public List<Workstation> listAll() {
        LambdaQueryWrapper<Workstation> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Workstation::getId);
        return list(wrapper);
    }

    @Override
    public Map<LocalDate, List<BookingService.TimeSlot>> getAvailableSlots(Long workstationId, LocalDate startDate, int days) {
        return bookingService.getAvailableSlots(workstationId, startDate, days);
    }
}
