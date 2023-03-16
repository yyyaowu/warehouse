package com.yao.warehouse.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName article_number
 */
@TableName(value ="article_number")
@Data
public class ArticleNumber implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
   // private String describe;


    //private String picture;


    private String articleNo;


    private double price;


   // private int status;

    private int size;

    private String colour;


    @TableLogic
    private int deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}