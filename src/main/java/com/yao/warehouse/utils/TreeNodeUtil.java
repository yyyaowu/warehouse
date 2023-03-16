package com.yao.warehouse.utils;

import com.yao.warehouse.Dto.MenuDto;

import java.util.LinkedList;
import java.util.List;

/**
 * @projectName: warehouse
 * @package: com.yao.warehouse.utils
 * @className: TreeNodeUtil
 * @author: yao
 * @description: TODO
 * @date: 2022/6/10 16:17
 * @version: 1.0
 */
public class TreeNodeUtil {
	public static List<MenuDto> transformData(List<MenuDto> treeNodeList,Long pid){

		List<MenuDto> list = new LinkedList<MenuDto>();
    	for (MenuDto menuDto : treeNodeList) {
			if (menuDto.getPid().equals(pid)){
				menuDto.setChildren(transformData(treeNodeList,menuDto.getId()));
				list.add(menuDto);
			}
		}
		return list;
	}


}
