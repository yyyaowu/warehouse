package com.yao.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.RecordDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Record;
import com.yao.warehouse.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.controller
 * @className: OutboundController
 * @author: yao
 * @description: TODO
 * @date: 2022/6/14 9:48
 * @version: 1.0
 */
@RestController
@RequestMapping("/outbound")
@Slf4j
public class RecordController {
	@Autowired
	private RecordService recordService;
	@GetMapping("/page")
	public R<Page<RecordDto>> getOutPage(int page, int pageSize, String outboundid, String transactor){

		return recordService.getOutPage(page,pageSize,outboundid,transactor,1);

	}
	@PutMapping("/update")
	public R<String> updateOutbound(@RequestBody Record record){
		return recordService.updateOutbound(record);
		//return null;
	}
	@PostMapping("/addOutbound")
	public R<String> addOutbound(@RequestBody Record record){
		return recordService.addOutbound(record,1);
	}

	@PostMapping("/addinbound")
	public R<String> addinbound(@RequestBody Record record){
		return recordService.addOutbound(record,0);
	}
	@DeleteMapping("/delete")
	public R<String> deletedOutbound(Long id){
		return recordService.deletedOutbound(id);
	}

	@GetMapping("/pagein")
	public R<Page<RecordDto>> getInPage(int page, int pageSize, String outboundid, String transactor){
		return recordService.getOutPage(page,pageSize,outboundid,transactor,0 );
	}

	@PutMapping("/reqsubmitOutbound")
	public R<String> reqsubmitOutbound(Long id){
		return recordService.reqsubmitOutbound(id);
	}
	@PutMapping("/reqsubmitinbound")
	public R<String> reqsubmitinbound(Long id){
		return recordService.reqsubmitinbound(id);
	}

}
