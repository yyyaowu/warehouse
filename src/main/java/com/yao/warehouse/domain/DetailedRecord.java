package com.yao.warehouse.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName datailed_record
 */
@TableName(value ="datailed_record")
@Data
public class DetailedRecord implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */

    private Long recordId;

    /**
     * 
     */
    private Long articleId;


    private int number;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 
     */
    @TableLogic
    private Integer deteted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}