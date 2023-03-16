package com.yao.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【warehouse】的数据库操作Service
* @createDate 2022-06-01 20:37:11
*/
public interface WarehouseService extends IService<Warehouse> {

	R<String> add(Warehouse warehouse);

	R<String> setStatus(Long warehouseId);

	R<String> del(List<Long> ids);

	Page<Warehouse> getPage(int page, int pageSize, String name);

	R<List<Warehouse>> getlist();

	boolean removeOne(Long id);
}
