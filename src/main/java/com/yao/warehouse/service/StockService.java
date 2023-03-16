package com.yao.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.StockDto;
import com.yao.warehouse.domain.Stock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.warehouse.domain.Warehouse;

/**
* @author Lenovo
* @description 针对表【stock】的数据库操作Service
* @createDate 2022-06-16 10:18:56
*/
public interface StockService extends IService<Stock> {

	boolean outRecord(Long articleId, Long warehouseId,int number);
	boolean inRecord(Long articleId, Long warehouseId,int number);

	Page<StockDto> getPage(int page, int pageSize, String warehouseName);
}
