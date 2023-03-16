package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.RolesVo;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.SysUser;
import com.yao.warehouse.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: SysUserControler
 * @author: yao
 * @description: TODO
 * @date: 2022/6/2 8:19
 * @version: 1.0
 */

@RestController
@RequestMapping("/user")

public class SysUserControler {
	@Autowired
	private SysUserService sysUserService;


	@Autowired
	private PasswordEncoder passwordEncoder;

	@PreAuthorize("hasAnyAuthority('User')")
	@GetMapping("/page/{page}/{pageSize}")
	public R<Page<SysUser>> page(@PathVariable int page,@PathVariable int pageSize,@RequestParam String username){

		Page<SysUser> pageinfo = sysUserService.getPage(page,pageSize,username);

		return R.success(pageinfo);
	}

	@PreAuthorize("hasAnyAuthority('btn.User.add')")
	@PostMapping("/save")
	public R<String> add(@RequestBody SysUser user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		sysUserService.save(user);
		return R.success("新增用户成功");
	}

	@PostMapping("/update")
	public R<String> updateInfo(SysUser sysUser){
		String result = sysUserService.updateInfo(sysUser);
		return R.success(result);
	}
	@PostMapping("/doAssign")
	public R<String> setRole(Long userId, @RequestParam List<Long> roleId){

		return sysUserService.setRole(userId,roleId);

	}
	@GetMapping("/toAssign/{id}")
	public R<RolesVo> toAssign(@PathVariable Long id){

		return sysUserService.toAssign(id);

	}

	@DeleteMapping("/remove/{id}")
	public R<String> removeOne(@PathVariable Long id) {
		sysUserService.removeById(id);
		return R.success("删除成功");
	}
	@DeleteMapping("/batchRemove")
	public R<String> batchRemove(@RequestBody List<Long> ids) {

		sysUserService.removeByIds(ids);
		return R.success("批量删除成功");
	}




}
