package com.yao.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.warehouse.Dto.RecordDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Lenovo
* @description 针对表【record】的数据库操作Service
* @createDate 2022-06-08 17:10:06
*/
public interface RecordService extends IService<Record> {

	R<Page<RecordDto>> getOutPage(int page, int pageSize, String outboundid, String transactor,int type);

	R<String> updateOutbound(Record record);

	R<String> addOutbound(Record record,int type);

	R<String> deletedOutbound(Long id);

	R<String> reqsubmitOutbound(Long id);

	R<String> reqsubmitinbound(Long id);
}
