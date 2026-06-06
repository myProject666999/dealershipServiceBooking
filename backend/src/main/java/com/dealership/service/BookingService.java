package com.dealership.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dealership.entity.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface BookingService extends IService<Booking> {

    Booking createBooking(Booking booking);

    List<Booking> listByCustomerId(Long customerId);

    Map<LocalDate, List<TimeSlot>> getAvailableSlots(Long workstationId, LocalDate startDate, int days);

    void cancelBooking(Long id, String reason);

    void updateBookingStatus(Long id, Integer status);

    class TimeSlot {
        private LocalTime startTime;
        private LocalTime endTime;
        private Boolean available;

        public TimeSlot() {}

        public TimeSlot(LocalTime startTime, LocalTime endTime, Boolean available) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.available = available;
        }

        public LocalTime getStartTime() { return startTime; }
        public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
        public LocalTime getEndTime() { return endTime; }
        public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
        public Boolean getAvailable() { return available; }
        public void setAvailable(Boolean available) { this.available = available; }
    }
}
