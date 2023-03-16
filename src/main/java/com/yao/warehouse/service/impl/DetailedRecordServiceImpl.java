package com.yao.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.warehouse.Dto.DetailedRecordDto;
import com.yao.warehouse.Dto.RecordDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.ArticleNumber;
import com.yao.warehouse.domain.DetailedRecord;
import com.yao.warehouse.domain.SysUser;
import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.mapper.DetailedRecordMapper;
import com.yao.warehouse.service.ArticleNumberService;
import com.yao.warehouse.service.DetailedRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.service.impl
 * @className: DetailedRecordServiceImpl
 * @author: yao
 * @description: TODO
 * @date: 2022/6/16 10:30
 * @version: 1.0
 */
@Service
@Slf4j
public class DetailedRecordServiceImpl extends ServiceImpl<DetailedRecordMapper, DetailedRecord> implements DetailedRecordService{
	@Autowired
	private ArticleNumberService articleNumberService;


	@Override
	public R<Page<DetailedRecordDto>> getOutDetailedRecordPage(int page, int pageSize, Long RecordId, String productname, String productid) {
		Page<DetailedRecord> pageinfo = new Page<>(page,pageSize);
		/* 根据用户输入的货号，货号名，得到货号id*/
		/* todo 优化 */
		LambdaQueryWrapper<ArticleNumber> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.like(StringUtils.isNotBlank(productname),ArticleNumber::getName,productname)
			.eq(StringUtils.isNotBlank(productid),ArticleNumber::getArticleNo,productid);
		List<ArticleNumber> list = articleNumberService.list(lambdaQueryWrapper);
		List<Long> articleIdList = new ArrayList<>();
		if (list.size() > 0) {
			for (ArticleNumber item : list) {
				articleIdList.add(item.getId());
			}
		}
		/* 查询对应的详细订单*/
		LambdaQueryWrapper<DetailedRecord> detailedRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
		detailedRecordLambdaQueryWrapper.eq(DetailedRecord::getRecordId,RecordId);
		if (articleIdList.size() > 0) {
			detailedRecordLambdaQueryWrapper.in(DetailedRecord::getArticleId,articleIdList);
		}
		this.page(pageinfo,detailedRecordLambdaQueryWrapper);
		/* 定义泛型为DetailedRecordDto类型的Page对象，用于返回结果
		   1.拷贝
		   2.string流处理，设置冗余字段
		   todo 优化 多表查询*/
		Page<DetailedRecordDto> pageResult = new Page<>();
		BeanCopy(pageinfo,pageResult);
		return R.success(pageResult);
	}

	private Page<DetailedRecordDto> BeanCopy(Page<DetailedRecord> page,Page<DetailedRecordDto> pagetarget){
		BeanUtils.copyProperties(page,pagetarget,"records");

		List<DetailedRecord> records = page.getRecords();

		List<DetailedRecordDto> detailedRecordDtos = records.stream().map((item) -> {
			DetailedRecordDto detailedRecordDto = new DetailedRecordDto();
			BeanUtils.copyProperties(item,detailedRecordDto);
			ArticleNumber articleNumber = articleNumberService.getById(item.getArticleId());
			detailedRecordDto.setAriticleName(articleNumber.getName());
			detailedRecordDto.setColour(articleNumber.getColour());
			detailedRecordDto.setArticleNo(articleNumber.getArticleNo());
			detailedRecordDto.setSize(articleNumber.getSize());
			return detailedRecordDto;
		}).collect(Collectors.toList());
		pagetarget.setRecords(detailedRecordDtos);
		return pagetarget;
	}

}
