package com.yao.warehouse.Dto;

import com.yao.warehouse.domain.DetailedRecord;
import lombok.Data;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.Dto
 * @className: DetailedRecordDto
 * @author: yao
 * @description: TODO
 * @date: 2022/6/16 10:35
 * @version: 1.0
 */

@Data
public class DetailedRecordDto extends DetailedRecord {
	private String ariticleName;

	private String articleNo;

	private String colour;

	private int size;

}
