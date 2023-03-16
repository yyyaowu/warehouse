package com.yao.warehouse.mapper;

import com.yao.warehouse.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【role】的数据库操作Mapper
* @createDate 2022-05-31 10:22:57
* @Entity com/yao/warehouse.domain.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




