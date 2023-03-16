package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.Dto.RecordDto;
import com.yao.warehouse.common.BaseContext;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.DetailedRecord;
import com.yao.warehouse.domain.Record;
import com.yao.warehouse.domain.SysUser;
import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.service.*;
import com.yao.warehouse.mapper.RecordMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Lenovo
* @description 针对表【record】的数据库操作Service实现
* @createDate 2022-06-08 17:10:06
*/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
    implements RecordService{
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private DetailedRecordService detailedRecordService;

	@Autowired
	private StockService stockService;

	@Override
	public R<Page<RecordDto>> getOutPage(int page, int pageSize, String outboundid, String transactor,int type) {
		Page<Record> pageInfo = new Page<>(page,pageSize);
		Page<RecordDto> pageResult = new Page<>();
        /*用户名字获取用户id，模糊查询*/
		/* todo 代码优化，多表查询。
		 */
		LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
		sysUserLambdaQueryWrapper.like(StringUtils.isNotBlank(transactor),SysUser::getUserName,transactor);
		List<SysUser> list = sysUserService.list(sysUserLambdaQueryWrapper);
		List<Long> userids = new LinkedList<>();
    	for (SysUser sysUser : list) {
			userids.add(sysUser.getId());
    	}
		LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(StringUtils.isNotBlank(outboundid),Record::getRecordAc,outboundid)
			.eq(Record::getType,type);

		if (userids.size() > 0){
			lambdaQueryWrapper.in(Record::getUserId,userids);
		}
		this.page(pageInfo,lambdaQueryWrapper);

		BeanUtils.copyProperties(pageInfo,pageResult,"records");

		List<Record> records = pageInfo.getRecords();
		List<RecordDto> recordDtoList = records.stream().map((item)->{
			RecordDto recordDto = new RecordDto();
			BeanUtils.copyProperties(item,recordDto);
			Long userId = item.getUserId();
			Long warehouseId = item.getWarehouseId();
			SysUser sysUser = sysUserService.getById(userId);
			if (sysUser != null){
				String username = sysUser.getUserName();
				recordDto.setUserName(username);
			}
			Warehouse warehouse = warehouseService.getById(warehouseId);
			if (warehouse != null){
				recordDto.setWarehouseName(warehouse.getWarehouseName());
			}
			return recordDto;

		}).collect(Collectors.toList());
		pageResult.setRecords(recordDtoList);
		return R.success(pageResult);
	}

	@Override
	public R<String> updateOutbound(Record record) {
		//LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		this.updateById(record);

		return R.success("成功");
	}

	@Override
	public R<String> addOutbound(Record record,int type) {

		record.setUserId(BaseContext.getCurrentId());
		record.setType(type);

		this.save(record);
		return R.success("成功");
	}

	@Override
	public R<String> deletedOutbound(Long id) {

		this.removeById(id);
		return R.success("成功");
	}

	@Override
	@Transactional
	public R<String> reqsubmitOutbound(Long id) {
		/*1.根据记录id查出记录id对应的detailedRecord*/
		LambdaQueryWrapper<DetailedRecord> detailedRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
		detailedRecordLambdaQueryWrapper.eq(DetailedRecord::getRecordId,id);
		List<DetailedRecord> detailedRecords = detailedRecordService.list(detailedRecordLambdaQueryWrapper);
		/*2.得到仓库id*/
		Record record = this.getById(id);
		Long warehouseId = record.getWarehouseId();
    	/*3.操作stock 的关键字段
     	* 遍历detailedRecords集合
     	* */
		for (DetailedRecord detailedRecord : detailedRecords) {
			boolean b = stockService.outRecord(detailedRecord.getArticleId(), warehouseId, detailedRecord.getNumber());
			if (b == false){
				return R.error("仓库id: " + warehouseId + "货号： " + detailedRecord.getArticleId() + "货物不足");
			}
		}
		/* todo 这里有一个出库单详细项出仓失败会导致整个出库单无法提交 应向页面反馈哪一条出库详细出的问题
		 */

		record.setSubmit(1);

		this.updateById(record);

		return R.success("提交成功");
	}

	@Override
	public R<String> reqsubmitinbound(Long id) {
		/*1.根据记录id查出记录id对应的detailedRecord*/
		LambdaQueryWrapper<DetailedRecord> detailedRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
		detailedRecordLambdaQueryWrapper.eq(DetailedRecord::getRecordId,id);
		List<DetailedRecord> detailedRecords = detailedRecordService.list(detailedRecordLambdaQueryWrapper);
		/*2.得到仓库id*/
		Record record = this.getById(id);
		Long warehouseId = record.getWarehouseId();
		/*3.操作stock 的关键字段
		 * 遍历detailedRecords集合
		 * */
		for (DetailedRecord detailedRecord : detailedRecords) {
			boolean b = stockService.inRecord(detailedRecord.getArticleId(), warehouseId, detailedRecord.getNumber());
			if (b == false){
				return R.error("仓库容量不足");
			}
		}


		record.setSubmit(1);

		this.updateById(record);

		return R.success("提交成功");
	}
}




