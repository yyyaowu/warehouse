package com.yao.warehouse.Dto;

import com.yao.warehouse.domain.Record;
import lombok.Data;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.Dto
 * @className: RecordDto
 * @author: yao
 * @description: TODO
 * @date: 2022/6/14 20:33
 * @version: 1.0
 */
@Data
public class RecordDto extends Record {

	private String warehouseName;

	private String userName;



}
