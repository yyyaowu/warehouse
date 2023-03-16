package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.ArticleNumber;

import com.yao.warehouse.domain.Stock;
import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.service.ArticleNumberService;

import com.yao.warehouse.service.StockService;
import com.yao.warehouse.service.WarehouseService;
import com.yao.warehouse.mapper.WarehouseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【warehouse】的数据库操作Service实现
* @createDate 2022-06-01 20:37:11
*/
@Service
@Slf4j
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse>
    implements WarehouseService{
	@Autowired
	private WarehouseMapper warehouseMapper;

	@Autowired
	private StockService stockService;

	@Override
	public R<String> add(Warehouse warehouse) {
		/* 初始容量为总容量 */
		warehouse.setAvailableCapacity(warehouse.getTatalCapacity());
		this.save(warehouse);
		return R.success("添加仓库成功");
	}

	@Override
	public R<String> setStatus(Long warehouseId) {
		/* 通过id查询*/
		LambdaQueryWrapper<Warehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Warehouse::getId,warehouseId);
		Warehouse one = this.getOne(lambdaQueryWrapper);
		/* 修改状态 */
		int status = one.getStatus();
		one.setStatus(status == 0 ? 1 : 0);
		warehouseMapper.updateById(one);
		return R.success("修改状态成功");
	}

	@Override
	public R<String> del(List<Long> ids) {
		LambdaQueryWrapper<Warehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(Warehouse::getId,ids).eq(Warehouse::getStatus,1);
		/*判断仓库状态是否为启用状态，启用状态不能删除*/
		int count = this.count(lambdaQueryWrapper);
		if (count > 0){
			throw new RuntimeException("仓库处于启用状态，不能删除");
		}

		/*删除库存表对应数据*/
		LambdaQueryWrapper<Stock> stockLambdaQueryWrapper = new LambdaQueryWrapper<>();
		stockLambdaQueryWrapper.in(Stock::getWarehouseId,ids);
		stockService.remove(stockLambdaQueryWrapper);

		this.removeByIds(ids);
		return R.success("删除成功");

	}

	@Override
	public Page<Warehouse> getPage(int page, int pageSize, String name) {
		/* 分页查询 */
		Page<Warehouse> warehousePage = new Page<>(page,pageSize);
		LambdaQueryWrapper<Warehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.like(StringUtils.isNotBlank(name),Warehouse::getWarehouseName,name);
		this.page(warehousePage,lambdaQueryWrapper);
		return warehousePage;
	}

	/* 提供给出入库单的接口,返回启用状态的list集合 */
	@Override
	public R<List<Warehouse>> getlist() {
		LambdaQueryWrapper<Warehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(Warehouse::getStatus,1);
		List<Warehouse> list = this.list();
		return R.success(list);

	}

	@Override
	public boolean removeOne(Long id) {
		Warehouse warehouse = this.getById(id);
		if (warehouse.getStatus() == 1){
			return false;
		}
		LambdaQueryWrapper<Stock> stockLambdaQueryWrapper = new LambdaQueryWrapper<>();
		stockLambdaQueryWrapper.eq(Stock::getWarehouseId,id);
		int count = stockService.count(stockLambdaQueryWrapper);
		if (count > 0){
			return false;
		}
		this.removeById(id);
		return true;
	}
}




