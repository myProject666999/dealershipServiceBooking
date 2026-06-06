package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("inspection_report")
public class InspectionReport implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String reportNo;

    private Long bookingId;

    private Long vehicleId;

    private Long technicianId;

    private LocalDateTime inspectionTime;

    private Integer currentMileage;

    private Integer appearanceStatus;

    private Integer tireStatus;

    private Integer lightStatus;

    private Integer chassisStatus;

    private Integer batteryStatus;

    private Integer overallStatus;

    private String description;

    private String suggestion;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
