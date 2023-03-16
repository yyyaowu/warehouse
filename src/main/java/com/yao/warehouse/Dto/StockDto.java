package com.yao.warehouse.Dto;

import com.yao.warehouse.domain.Stock;
import lombok.Data;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.Dto
 * @className: StockDto
 * @author: yao
 * @description: TODO
 * @date: 2022/6/20 8:51
 * @version: 1.0
 */
@Data
public class StockDto extends Stock {

	//仓库名称
	private String warehouseName;


	//品名
	private String articleName;



	//货号
	private String articleNo;

	private String colour;

	private int size;

}
