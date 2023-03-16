package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.DetailedRecordDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.DetailedRecord;
import com.yao.warehouse.service.DetailedRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: DetailedRecordController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/16 10:22
 * @version: 1.0
 */

@RestController
@RequestMapping("/boundinfo")
public class DetailedRecordController {



	@Autowired
	private DetailedRecordService detailedRecordService;

	@GetMapping("/page")
	public R<Page<DetailedRecordDto>> getOutDetailedRecordPage(int page, int pageSize, Long outboundid, String productname, String productid){
		return detailedRecordService.getOutDetailedRecordPage(page,pageSize,outboundid,productname,productid);
	}

	@PostMapping("/addoutboundinfo")
	public R<String> addoutboudinfo(@RequestBody DetailedRecord detailedRecord){
		detailedRecordService.save(detailedRecord);
		return R.success("成功");
	}

	@DeleteMapping("/deleteDetailedRecord")
	public R<String> deleteDetailedRecord(Long id){
		detailedRecordService.removeById(id);
		return R.success("删除记录详情成功");
	}

	@PutMapping("/updateDetailedRecord")
	public R<String> updateDetailedRecord(@RequestBody DetailedRecord detailedRecord){
		detailedRecordService.updateById(detailedRecord);
		return R.success("修改成功");
	}

}
