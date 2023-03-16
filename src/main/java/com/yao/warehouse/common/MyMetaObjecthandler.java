package com.yao.warehouse.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yao.warehouse.domain.LoginUser;
import com.yao.warehouse.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @projectName: reggite
 * @package: com.yao.reggite.common
 * @className: MyMetaObjecthandler
 * @author: yao
 * @description: 自定义元数据对象处理器
 * @date: 2022/5/1 14:26
 * @version: 1.0
 */
@Slf4j
@Component
public class MyMetaObjecthandler implements MetaObjectHandler {
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("insert公共字段填充");

		//redisUtil.get()
//		String redisKey = "login:" + userid;
//		LoginUser loginUser = (LoginUser) redisUtil.get(redisKey);
//		if(Objects.isNull(loginUser)){
//			throw new RuntimeException("用户未登录");
//		}
		metaObject.setValue("createTime", LocalDateTime.now());
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("createUser",BaseContext.getCurrentId());
		metaObject.setValue("updateUser",BaseContext.getCurrentId());



	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("update公共字段填充");
		log.info("当前线程{}",Thread.currentThread().getId());
		//metaObject.setValue("createTime", LocalDateTime.now());
		metaObject.setValue("updateTime", LocalDateTime.now());
		//metaObject.setValue("createUser",1L);
		Long currentId = BaseContext.getCurrentId();
		log.info("{}",currentId);
		metaObject.setValue("updateUser",currentId);
	}
}
