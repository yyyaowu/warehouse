package com.yao.warehouse.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yao.warehouse.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Lenovo
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2022-05-31 10:22:57
* @Entity com/yao/warehouse.domain.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	List<Long> selectIdByUserId(@Param("userId") Long userId);




}




