package com.yao.warehouse.service;

import com.yao.warehouse.Dto.MenuDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【menu】的数据库操作Service
* @createDate 2022-05-31 10:22:57
*/
public interface MenuService extends IService<Menu> {

	R<String> savePermission(Menu menu);

	R<List<MenuDto>> permission();

	R<String> updateInfo(Menu menu);

	R<List<MenuDto>> toAssign(Long id);
}
