package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Role;
import com.yao.warehouse.domain.RoleMenu;
import com.yao.warehouse.service.RoleMenuService;
import com.yao.warehouse.service.RoleService;
import com.yao.warehouse.mapper.RoleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【role】的数据库操作Service实现
* @createDate 2022-05-31 10:22:57
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{
	@Autowired
	private RoleMenuService roleMenuService;


	@Override
	public R<String> saveRole(Role role) {

		role.setRemark("仓库后台管理" + role.getRoleName());

		this.save(role);

		return R.success("成功");
	}

	@Override
	public R<String> doAssign(Long roleId, List<Long> menuIds) {
		LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(RoleMenu::getRoleId,roleId);
		roleMenuService.remove(lambdaQueryWrapper);

    	for (Long menuId : menuIds) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);
			roleMenuService.save(roleMenu);
    	}

		return R.success("成功");
	}

	@Override
	public R<Page<Role>> pageinfo(int page, int pageSize, String name) {
		Page<Role> rolePage = new Page<>(page,pageSize);
		LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.like(StringUtils.isNotBlank(name),Role::getRoleName,name);
		this.page(rolePage,lambdaQueryWrapper);
		return R.success(rolePage);
	}


}




