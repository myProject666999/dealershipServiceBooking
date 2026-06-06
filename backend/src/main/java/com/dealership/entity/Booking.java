package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@TableName("booking")
public class Booking implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String bookingNo;

    private Long customerId;

    private Long vehicleId;

    private Long workstationId;

    private Long serviceItemId;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer currentMileage;

    private String customerRemark;

    private Integer status;

    private String cancelReason;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
