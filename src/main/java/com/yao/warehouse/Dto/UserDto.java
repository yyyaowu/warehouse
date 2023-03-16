package com.yao.warehouse.Dto;

import com.yao.warehouse.domain.SysUser;
import lombok.Data;

import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.Dto
 * @className: UserDto
 * @author: yao
 * @description: TODO
 * @date: 2022/6/8 20:33
 * @version: 1.0
 */
@Data
public class UserDto  {

	String name;

	//private List<String> roles;

	private List<String> routes;

	private List<String> buttons;


}
