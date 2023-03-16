package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: WarehouseController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/8 10:52
 * @version: 1.0
 */
@RestController
@RequestMapping("/warehouse")
@Slf4j
public class WarehouseController {
	@Autowired
	private WarehouseService warehouseService;

  /***
   * @param warehouse:
    a * @return R<String>
   * @author Lenovo
   * @description 添加仓库
   * @date 2022/6/8 11:08 */
    @PostMapping("/save")
    public R<String> add(@RequestBody Warehouse warehouse) {

		return warehouseService.add(warehouse);

	}

	@GetMapping("/updateStatus/{id}")
	public R<String> setStatus(@PathVariable Long id){

		//log.info("-----------------{}",id);

		return warehouseService.setStatus(id);
	}


	@GetMapping("/del")
	public R<String> del(List<Long> ids){
		return warehouseService.del(ids);

	}

	@GetMapping("/page/{page}/{pageSize}")
	public R<Page<Warehouse>> getPage(@PathVariable int page,@PathVariable int pageSize,String warehouseName){
		Page<Warehouse> pageinfo = warehouseService.getPage(page,pageSize,warehouseName);
		return R.success(pageinfo);
	}



	@GetMapping("/getlist")
	public R<List<Warehouse>> getlist(){
		return warehouseService.getlist();
	}

	@DeleteMapping("/remove/{id}")
	public R<String> removeOne(@PathVariable Long id) {
		boolean result = warehouseService.removeOne(id);
		if (result == true) {
			return R.success("删除成功");
		}else {
			return R.error("仓库处于启用状态或仓库内还有货物");
		}

	}



}
