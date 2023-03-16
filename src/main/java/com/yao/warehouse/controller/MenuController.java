package com.yao.warehouse.controller;

import com.yao.warehouse.Dto.MenuDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Menu;
import com.yao.warehouse.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: MenuController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/8 19:52
 * @version: 1.0
 */

@RestController
@RequestMapping("/permission")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping("/save")
	public R<String> savePermission(@RequestBody Menu menu) {

		return menuService.savePermission(menu);

	}
	@GetMapping
	public R<List<MenuDto>> permission(){

		return menuService.permission();
	}

	@PutMapping("/update")
	public R<String> updateInfo(@RequestBody Menu menu){


		return menuService.updateInfo(menu);

	}

	@DeleteMapping("/remove/{id}")
	public R<String> removeById(@PathVariable Long id){

		menuService.removeById(id);

		return R.success("成功");
	}

	@GetMapping("/toAssign/{id}")
	public R<List<MenuDto>> toAssign(@PathVariable Long id){

		return menuService.toAssign(id);
	}






}
