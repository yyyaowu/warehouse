package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Role;
import com.yao.warehouse.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: RoleController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/9 10:11
 * @version: 1.0
 */

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

	@Autowired
	private RoleService roleService;


	@PostMapping("/save")
	public R<String> saveRole(@RequestBody Role role){
		log.info("------------{}",role.getRoleName());
		return roleService.saveRole(role);

	}


	@PostMapping("/doAssign")
	public R<String> doAssign(Long roleId, @RequestParam List<Long> permissionId){

		return roleService.doAssign(roleId,permissionId);

		//return null;

	}

	@GetMapping("/page/{page}/{pageSize}")
	public R<Page<Role>> pageinfo(@PathVariable int page,@PathVariable int pageSize,@RequestParam String roleName){

		return roleService.pageinfo(page,pageSize,roleName);
	}

	@DeleteMapping("/remove/{id}")
	public R<String> removeOne(@PathVariable Long id) {
		roleService.removeById(id);
		return R.success("删除成功");
	}
	@DeleteMapping("/batchRemove")
	public R<String> batchRemove(@RequestBody List<Long> ids) {

		roleService.removeByIds(ids);
		return R.success("批量删除成功");
	}




}
