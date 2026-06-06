package com.dealership.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dealership.entity.Workstation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface WorkstationService extends IService<Workstation> {

    List<Workstation> listEnabled();

    Map<LocalDate, List<BookingService.TimeSlot>> getAvailableSlots(Long workstationId, LocalDate startDate, int days);
}
