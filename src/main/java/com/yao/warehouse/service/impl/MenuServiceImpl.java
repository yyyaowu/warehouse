package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.Dto.MenuDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Menu;
import com.yao.warehouse.domain.RoleMenu;
import com.yao.warehouse.mapper.RoleMapper;
import com.yao.warehouse.service.MenuService;
import com.yao.warehouse.mapper.MenuMapper;
import com.yao.warehouse.service.RoleMenuService;
import com.yao.warehouse.utils.TreeNodeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* @author Lenovo
* @description 针对表【menu】的数据库操作Service实现
* @createDate 2022-05-31 10:22:57
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

	@Autowired
	private RoleMenuService roleMenuService;

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public R<String> savePermission(Menu menu) {
		this.save(menu);
		return R.success("成功");
	}

	@Override
	public R<List<MenuDto>> permission() {
		LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.orderByAsc(Menu::getId);

		List<Menu> menus = menuMapper.selectList(lambdaQueryWrapper);

		List<MenuDto> list = new LinkedList<>();

    	for (Menu menu : menus) {
			MenuDto menuDto = new MenuDto();
			BeanUtils.copyProperties(menu,menuDto);
			menuDto.setSelect(true);
			list.add(menuDto);
    	}
		list = TreeNodeUtil.transformData(list, 0L);

		return R.success(list);
	}

	@Override
	public R<String> updateInfo(Menu menu) {


		this.updateById(menu);
		return R.success("成功");
	}

	@Override
	@Transactional
	public R<List<MenuDto>> toAssign(Long id) {
		/* 得到所有权限列表*/
		LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.orderByAsc(Menu::getId);
		List<Menu> menus = menuMapper.selectList(lambdaQueryWrapper);
		/*dto类封装了权限列表*/
		List<MenuDto> list = new LinkedList<>();

		/*得到角色Id对应的所有权限id*/
		LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(RoleMenu::getRoleId,id);
		List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
		List<Long> MenuIds = new ArrayList<>();
		for (RoleMenu roleMenu : roleMenus) {
			MenuIds.add(roleMenu.getMenuId());
		}
		/*拷贝*/
		for (Menu menu : menus) {
			MenuDto menuDto = new MenuDto();
			BeanUtils.copyProperties(menu,menuDto);
			if (MenuIds.contains(menu.getId())){
				menuDto.setSelect(true);
			}else {
				menuDto.setSelect(false);
			}
			list.add(menuDto);
		}
		list = TreeNodeUtil.transformData(list, 0L);

		return R.success(list);

		//return null;
	}

}




