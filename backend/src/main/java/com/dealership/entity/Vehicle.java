package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("vehicle")
public class Vehicle implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long customerId;

    private String licensePlate;

    private String vin;

    private String brand;

    private String model;

    private String color;

    private Integer currentMileage;

    private Integer lastServiceMileage;

    private LocalDateTime lastServiceTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
