package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.Dto.StockDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.ArticleNumber;
import com.yao.warehouse.domain.Stock;
import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.mapper.WarehouseMapper;
import com.yao.warehouse.service.ArticleNumberService;
import com.yao.warehouse.service.StockService;
import com.yao.warehouse.mapper.StockMapper;
import com.yao.warehouse.service.WarehouseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Lenovo
* @description 针对表【stock】的数据库操作Service实现
* @createDate 2022-06-16 10:18:56
*/
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock>
    implements StockService{
	@Autowired
	private WarehouseMapper warehouseMapper;

	@Autowired
	private ArticleNumberService articleNumberService;



	@Autowired
	@Lazy
	private WarehouseService warehouseService;

	/* 此方法用于处理保存出库单对应的库存表逻辑
		接收参数为 货号id,仓库id
	 */
	@Override
	@Transactional
	public boolean outRecord(Long articleId, Long warehouseId,int number) {
		/* 1.判断表中是否有articleId，warehouseId记录 */
		LambdaQueryWrapper<Stock> stockLambdaQueryWrapper = new LambdaQueryWrapper<>();
		stockLambdaQueryWrapper.eq(Stock::getArticleId,articleId)
			.eq(Stock::getWarehouseId,warehouseId);
		Stock stock = this.getOne(stockLambdaQueryWrapper);
		/* 没找到 说明仓库没有id为articleId的货物 */
		if (stock == null) {
			return false;
		}
		int result = stock.getInventory() - number;
		/* 数量不足抛出异常 */
		if (result < 0) {
			return false;
		}

		stock.setInventory(result);
		this.updateById(stock);
		/* 对仓库处理 */
		Warehouse warehouse = warehouseService.getById(warehouseId);
		warehouse.setAvailableCapacity(warehouse.getAvailableCapacity() - number);
		warehouseService.updateById(warehouse);

		return true;
	}

	@Transactional
	@Override
	public boolean inRecord(Long articleId, Long warehouseId,int number){
		/* 1.判断表中是否有articleId，warehouseId记录 */
		LambdaQueryWrapper<Stock> stockLambdaQueryWrapper = new LambdaQueryWrapper<>();
		stockLambdaQueryWrapper.eq(Stock::getArticleId,articleId)
			.eq(Stock::getWarehouseId,warehouseId);
		Stock stock = this.getOne(stockLambdaQueryWrapper);
		/* 没找到 说明仓库没有id为articleId的货物 */
		/* 此时为新增 */
		if (stock == null) {
			Stock stockin = new Stock();
			stockin.setInventory(number);
			stockin.setArticleId(articleId);
			stockin.setWarehouseId(warehouseId);
			this.save(stockin);
			return true;
		}
		/* 找到了数量加 */
		/* 需要判断仓库容量 */
		/* 得到仓库可用容量 */
		//LambdaQueryWrapper<Warehouse> warehouseLambdaQueryWrapper = new LambdaQueryWrapper<>();
		Warehouse warehouse = warehouseService.getById(warehouseId);

		/* 仓库容量不足 */
		if (warehouse.getAvailableCapacity() - number < 0) {
			return false;
		}
		/* 容量足够时 */
		stock.setInventory(stock.getInventory() + number);
		warehouse.setAvailableCapacity(warehouse.getAvailableCapacity() - number);

		this.updateById(stock);
		warehouseService.updateById(warehouse);

		return true;

	}

	@Override
	public Page<StockDto> getPage(int page, int pageSize, String warehouseName) {

		Page<Stock> pageinfo = new Page<>(page,pageSize);
		Page<StockDto> pageResult = new Page<>();

		/* 根据仓库名获取仓库id 模糊查询 */
		List<Long> ids= warehouseMapper.getIdByWarehouseName(warehouseName);

		/* 构建条件查询 */
		LambdaQueryWrapper<Stock> stockLambdaQueryWrapper = new LambdaQueryWrapper<>();

		/* 判断ids是否为空 */
		if (ids.size() > 0) {
			stockLambdaQueryWrapper.in(Stock::getWarehouseId,ids);
		}

		/* 没查到仓库名对应的库存信息 */
		if (ids.size() == 0 && StringUtils.isNotEmpty(warehouseName)) {
			return pageResult;
		}

		stockLambdaQueryWrapper.orderByDesc(Stock::getUpdateTime);

		/* 查询 */
		this.page(pageinfo,stockLambdaQueryWrapper);

		/* 拷贝 去除records字段*/
		BeanUtils.copyProperties(pageinfo,pageResult,"records");

		/* 对records重新赋值 Stream流 */
		List<Stock> records = pageinfo.getRecords();
		List<StockDto> stockDtos = records.stream().map(item ->{
			StockDto stockDto = new StockDto();
			BeanUtils.copyProperties(item,stockDto);
			Warehouse warehouse = warehouseService.getById(item.getWarehouseId());
			stockDto.setWarehouseName(warehouse.getWarehouseName());
			ArticleNumber articleNumber = articleNumberService.getById(item.getArticleId());
			stockDto.setArticleNo(articleNumber.getArticleNo());
			stockDto.setArticleName(articleNumber.getName());
			stockDto.setColour(articleNumber.getColour());
			stockDto.setSize(articleNumber.getSize());
			return stockDto;
		}).collect(Collectors.toList());
		pageResult.setRecords(stockDtos);
		return pageResult;
	}
}




