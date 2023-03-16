package com.yao.warehouse.Dto;

import com.yao.warehouse.domain.Role;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.Dto
 * @className: RolesVo
 * @author: yao
 * @description: 此类用于返回前端需要展示的角色信息
 * @date: 2022/6/20 10:36
 * @version: 1.0
 */
@Data
public class RolesVo {

	/* 用户已经设置的角色列表 */
	private List<Role> assignRoles;

	/* 全部角色列表 */
	private List<Role> allRolesList;


}
