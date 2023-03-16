package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.StockDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Stock;
import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: StockController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/20 8:21
 * @version: 1.0
 */
@RestController
@RequestMapping("/inventory")
public class StockController {
	@Autowired
	private StockService stockService;


	@GetMapping("/page/{page}/{pageSize}")
	public R<Page<StockDto>> getPage(@PathVariable int page, @PathVariable int pageSize, String warehouseName){
		Page<StockDto> pageinfo = stockService.getPage(page,pageSize,warehouseName);
		return R.success(pageinfo);
	}







}
