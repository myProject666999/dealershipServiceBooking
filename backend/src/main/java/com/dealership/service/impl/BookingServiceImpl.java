package com.dealership.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dealership.common.BusinessException;
import com.dealership.entity.Booking;
import com.dealership.mapper.BookingMapper;
import com.dealership.service.BookingService;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {

    private static final LocalTime WORK_START = LocalTime.of(8, 30);
    private static final LocalTime WORK_END = LocalTime.of(18, 0);
    private static final int SLOT_MINUTES = 60;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Booking createBooking(Booking booking) {
        if (booking.getWorkstationId() == null || booking.getBookingDate() == null
                || booking.getStartTime() == null || booking.getEndTime() == null) {
            throw new BusinessException("请选择工位和预约时段");
        }
        if (booking.getStartTime().isAfter(booking.getEndTime()) || booking.getStartTime().equals(booking.getEndTime())) {
            throw new BusinessException("预约时间不合法");
        }
        List<Booking> conflicts = bookingMapper.findConflictingBookings(
                booking.getWorkstationId(),
                booking.getBookingDate(),
                booking.getStartTime(),
                booking.getEndTime()
        );
        if (!conflicts.isEmpty()) {
            throw new BusinessException("该工位时段已被预约，请选择其他时间");
        }
        booking.setBookingNo("BK" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN));
        if (booking.getStatus() == null) {
            booking.setStatus(1);
        }
        save(booking);
        return booking;
    }

    @Override
    public List<Booking> listByCustomerId(Long customerId) {
        return list(new LambdaQueryWrapper<Booking>()
                .eq(Booking::getCustomerId, customerId)
                .orderByDesc(Booking::getCreateTime));
    }

    @Override
    public Map<LocalDate, List<TimeSlot>> getAvailableSlots(Long workstationId, LocalDate startDate, int days) {
        Map<LocalDate, List<TimeSlot>> result = new LinkedHashMap<>();
        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.plusDays(i);
            List<TimeSlot> slots = new ArrayList<>();
            LocalTime cursor = WORK_START;
            while (cursor.plusMinutes(SLOT_MINUTES).compareTo(WORK_END) <= 0) {
                LocalTime slotStart = cursor;
                LocalTime slotEnd = cursor.plusMinutes(SLOT_MINUTES);
                List<Booking> conflicts = bookingMapper.findConflictingBookings(workstationId, date, slotStart, slotEnd);
                slots.add(new TimeSlot(slotStart, slotEnd, conflicts.isEmpty()));
                cursor = slotEnd;
            }
            result.put(date, slots);
        }
        return result;
    }

    @Override
    public void cancelBooking(Long id, String reason) {
        Booking booking = getById(id);
        if (booking == null) {
            throw new BusinessException("预约不存在");
        }
        booking.setStatus(5);
        booking.setCancelReason(reason);
        updateById(booking);
    }

    @Override
    public void updateBookingStatus(Long id, Integer status) {
        Booking booking = getById(id);
        if (booking == null) {
            throw new BusinessException("预约不存在");
        }
        booking.setStatus(status);
        updateById(booking);
    }
}
