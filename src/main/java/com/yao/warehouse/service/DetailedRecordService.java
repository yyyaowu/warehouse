package com.yao.warehouse.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yao.warehouse.Dto.DetailedRecordDto;
import com.yao.warehouse.common.R;
import com.yao.warehouse.domain.DetailedRecord;

/**
* @author Lenovo
* @description 针对表【datailed_record】的数据库操作Service
* @createDate 2022-06-16 10:18:56
*/
public interface DetailedRecordService extends IService<DetailedRecord> {

	R<Page<DetailedRecordDto>> getOutDetailedRecordPage(int page, int pageSize, Long Recordid, String productname, String productid);
}
