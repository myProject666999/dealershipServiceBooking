package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("inspection_issue")
public class InspectionIssue implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long reportId;

    private String category;

    private String itemName;

    private Integer severity;

    private String description;

    private String suggestAction;

    private BigDecimal estimatedPrice;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
