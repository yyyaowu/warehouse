package com.yao.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【role】的数据库操作Service
* @createDate 2022-05-31 10:22:57
*/
public interface RoleService extends IService<Role> {

	R<String> saveRole(Role role);

	R<String> doAssign(Long roleId, List<Long> menuIds);

	R<Page<Role>> pageinfo(int page, int pageSize, String name);
}
