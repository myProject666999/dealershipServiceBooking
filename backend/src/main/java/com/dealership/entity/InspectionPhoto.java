package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("inspection_photo")
public class InspectionPhoto implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long reportId;

    private String category;

    private String photoUrl;

    private String description;

    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
