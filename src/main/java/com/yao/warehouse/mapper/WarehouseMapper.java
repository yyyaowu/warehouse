package com.yao.warehouse.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yao.warehouse.domain.Warehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【warehouse】的数据库操作Mapper
* @createDate 2022-06-01 20:37:11
* @Entity com.yao.warehouse.domain.Warehouse
*/
@Mapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {


	List<Long> getIdByWarehouseName(@Param("warehouseName") String warehouseName);


}




