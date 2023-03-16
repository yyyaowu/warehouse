package com.yao.warehouse.mapper;

import com.yao.warehouse.domain.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【stock】的数据库操作Mapper
* @createDate 2022-06-16 10:18:56
* @Entity com.yao.warehouse.domain.Stock
*/
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}




