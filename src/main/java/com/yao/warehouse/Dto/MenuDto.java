package com.yao.warehouse.Dto;

import com.yao.warehouse.domain.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.Dto
 * @className: MenuDto
 * @author: yao
 * @description: TODO
 * @date: 2022/6/10 16:12
 * @version: 1.0
 */

@Data
public class MenuDto extends Menu implements Serializable {


	private List<MenuDto> children;

	private boolean select;
}
