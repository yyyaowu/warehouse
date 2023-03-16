package com.yao.warehouse.common;


/**
 * @projectName: reggite
 * @package: com.yao.reggite.common
 * @className: BaseContext
 * @author: yao
 * @description: TODO
 * @date: 2022/5/1 14:50
 * @version: 1.0
 */

public class BaseContext {
	private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

	public static void setCurrentId(Long id){
		//log.info("threadlocal 设置值{}",id);
		threadLocal.set(id);
	}
	public static Long getCurrentId(){
		Long id = threadLocal.get();
		//log.info("threadlocal 取出值{}",id);
		//return threadLocal.get();
		return id;
	}
}
