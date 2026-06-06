package com.dealership.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dealership.entity.Booking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {

    @Select("SELECT * FROM booking WHERE workstation_id = #{workstationId} AND booking_date = #{bookingDate} AND status != 5 AND deleted = 0 AND " +
            "((start_time < #{endTime} AND end_time > #{startTime}))")
    List<Booking> findConflictingBookings(@Param("workstationId") Long workstationId,
                                           @Param("bookingDate") LocalDate bookingDate,
                                           @Param("startTime") LocalTime startTime,
                                           @Param("endTime") LocalTime endTime);

    @Select("SELECT * FROM booking WHERE workstation_id = #{workstationId} AND booking_date = #{bookingDate} AND status != 5 AND deleted = 0 AND id != #{excludeId} AND " +
            "((start_time < #{endTime} AND end_time > #{startTime}))")
    List<Booking> findConflictingBookingsExcludeSelf(@Param("workstationId") Long workstationId,
                                                      @Param("bookingDate") LocalDate bookingDate,
                                                      @Param("startTime") LocalTime startTime,
                                                      @Param("endTime") LocalTime endTime,
                                                      @Param("excludeId") Long excludeId);
}
