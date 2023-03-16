package com.yao.warehouse.mapper;

import com.yao.warehouse.domain.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2022-05-31 10:22:57
* @Entity com/yao/warehouse.domain.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	List<String> selectPermsByUserId(Long id);

	List<String> selectPermsByUserIdAndTypeMenu(Long id);

	List<String> selectPermsByUserIdAndTypeBt(Long id);
}




