package com.dealership.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("technician")
public class Technician implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;

    private String phone;

    private Integer level;

    private String specialty;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
