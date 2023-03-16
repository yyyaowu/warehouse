package com.yao.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.RolesVo;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Role;
import com.yao.warehouse.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2022-05-31 10:22:57
*/
public interface SysUserService extends IService<SysUser> {
	Page<SysUser> getPage(int page, int pageSize, String userName);

	String updateInfo(SysUser sysUser);

	R<RolesVo> toAssign(Long id);

	R<String> setRole(Long userid, List<Long> roleList);

//	String add(SysUser user);

//	SysUser getByUsername(String username);
//
//	String getUserAuthorityInfo(Long userId);

	//List<String> selectPermsByUserId(Long id);
}
