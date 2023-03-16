package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.Dto.RolesVo;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Role;
import com.yao.warehouse.domain.SysUser;
import com.yao.warehouse.domain.UserRole;
import com.yao.warehouse.mapper.MenuMapper;
import com.yao.warehouse.mapper.RoleMapper;
import com.yao.warehouse.mapper.UserRoleMapper;
import com.yao.warehouse.service.RoleService;
import com.yao.warehouse.service.SysUserService;
import com.yao.warehouse.mapper.SysUserMapper;
import com.yao.warehouse.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
* @author Lenovo
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2022-05-31 10:22:57
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

	@Autowired
	private UserRoleMapper userRoleMapper;

//	@Autowired
//	private UserRoleService userRoleService;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleService roleService;


	@Override
	public Page<SysUser> getPage(int page, int pageSize, String userName) {
		Page<SysUser> pageinfo = new Page<>(page,pageSize);

		LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();

		lambdaQueryWrapper.like(StringUtils.isNotBlank(userName),SysUser::getUserName,userName)
			.orderByDesc(SysUser::getUpdateTime);

		this.page(pageinfo,lambdaQueryWrapper);


		return pageinfo;
	}

	@Override
	public String updateInfo(SysUser sysUser) {
		//this.update(sysUser)

		boolean result = this.updateById(sysUser);

		if (result == false){
			throw new RuntimeException("修改失败");
		}

		return "修改成功";
	}

	@Override
	public R<RolesVo> toAssign(Long id) {
		/* 查寻用户拥有的角色id */
		List<Long> ids = userRoleMapper.selectIdByUserId(id);

		List<Role> roles = new LinkedList<>();
		List<Role> allRoles = new LinkedList<>();

		/* 得到用户拥有的角色列表 */
		if (ids.size() > 0){

			roles = roleService.listByIds(ids);
		}

		RolesVo rolesVo = new RolesVo();
		rolesVo.setAssignRoles(roles);
		/* 全部角色 */
		allRoles = roleService.list();
		rolesVo.setAllRolesList(allRoles);
		return R.success(rolesVo);


	}

	@Override
	public R<String> setRole(Long userid, List<Long> roleList) {

		/* 先删除 */
		LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
		userRoleLambdaQueryWrapper.eq(UserRole::getUserId,userid);
		userRoleMapper.delete(userRoleLambdaQueryWrapper);

		/* 再重新设置 */

		if (roleList.size() > 0) {
        	for (Long role : roleList) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(role);
				userRole.setUserId(userid);
				userRoleMapper.insert(userRole);
			}
		}
		return R.success("设置角色成功");
	}


}




