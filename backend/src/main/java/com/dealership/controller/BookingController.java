package com.dealership.controller;

import com.dealership.common.Result;
import com.dealership.entity.Booking;
import com.dealership.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public Result<Booking> create(@RequestBody Booking booking) {
        return Result.success(bookingService.createBooking(booking));
    }

    @GetMapping("/listByCustomer/{customerId}")
    public Result<List<Booking>> listByCustomer(@PathVariable Long customerId) {
        return Result.success(bookingService.listByCustomerId(customerId));
    }

    @GetMapping("/{id}")
    public Result<Booking> getById(@PathVariable Long id) {
        return Result.success(bookingService.getById(id));
    }

    @PutMapping("/status")
    public Result<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
        bookingService.updateBookingStatus(id, status);
        return Result.success();
    }

    @PostMapping("/cancel/{id}")
    public Result<Void> cancel(@PathVariable Long id, @RequestParam(required = false) String cancelReason) {
        bookingService.cancelBooking(id, cancelReason);
        return Result.success();
    }
}
