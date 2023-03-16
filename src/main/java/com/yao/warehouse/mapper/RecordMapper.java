package com.yao.warehouse.mapper;

import com.yao.warehouse.domain.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【record】的数据库操作Mapper
* @createDate 2022-06-08 17:10:06
* @Entity com.yao.warehouse.domain.Record
*/
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

}




