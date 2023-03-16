package com.yao.warehouse.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName warehouse
 */
@TableName(value ="warehouse")
@Data
public class Warehouse implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String warehouseName;

    /**
     * 
     */
    private Integer tatalCapacity;

    /**
     * 
     */
    private Integer availableCapacity;


    private Integer status;


    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}