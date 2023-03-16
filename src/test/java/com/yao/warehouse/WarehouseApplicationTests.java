package com.yao.warehouse;

import com.yao.warehouse.domain.Warehouse;
import com.yao.warehouse.mapper.WarehouseMapper;
import com.yao.warehouse.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WarehouseApplicationTests {

	@Autowired
	private MenuService menuService;

	@Autowired
	private WarehouseMapper warehouseMapper;

	@Test
	void contextLoads() {
    	System.out.println(menuService.permission());
	}


	@Test
	void testWarehouse() {
		String ui = null;
		List<Long> yi = warehouseMapper.getIdByWarehouseName("hh");
    	for (Long aLong : yi) {
			System.out.println(aLong);
		//
    	}
	}

}
