package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("repair_order")
public class RepairOrder implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;

    private Long bookingId;

    private Long inspectionReportId;

    private Long customerId;

    private Long vehicleId;

    private Long technicianId;

    private Integer currentMileage;

    private Integer orderType;

    private BigDecimal laborFee;

    private BigDecimal partsFee;

    private BigDecimal totalAmount;

    private BigDecimal discountAmount;

    private BigDecimal payAmount;

    private Integer status;

    private Integer payStatus;

    private LocalDateTime payTime;

    private Integer payMethod;

    private LocalDateTime inTime;

    private LocalDateTime outTime;

    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
