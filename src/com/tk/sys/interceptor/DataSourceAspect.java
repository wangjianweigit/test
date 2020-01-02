package com.tk.sys.interceptor;

import org.aspectj.lang.JoinPoint;
import org.springframework.util.StringUtils;

import com.tk.sys.util.DynamicDataSourceHolder;

/**
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走读库还是写库
 * 
 * @author wangpeng
 * @version 1.0
 * @date 2019-04-04
 */
public class DataSourceAspect {

	/**
	 * 在进入Dao方法之前执行
	 * @param point 切面对象
	 */
	public void before(JoinPoint point) {
		// 获取到当前执行的方法名
		String methodName = point.getSignature().getName();
		
		String dbType = getDbType(methodName);
		if(dbType.equals("MASTER")){
			// 主库-标记为写库
			DynamicDataSourceHolder.markMaster();
		}
		if(dbType.equals("SLAVE")){
			// 主库-标记为读库
			DynamicDataSourceHolder.markSlave();
		}
		if(dbType.equals("RESOURCE_MASTER")){
			// 资源库-标记为写库
			DynamicDataSourceHolder.markResourceMaster();
		}
		if(dbType.equals("RESOURCE_SLAVE")){
			// 资源库-标记为读库
			DynamicDataSourceHolder.markResourceSlave();
		}
		
		/**************************仅适用于一个主库的读写分离********begin***********停用*******/
		/*
		if (isSlave(methodName)) {
			// 标记为读库
			DynamicDataSourceHolder.markSlave();
		} else {
			// 标记为写库
			DynamicDataSourceHolder.markMaster();
		}
		*/
		/**************************仅适用于一个主库的读写分离********begin***********停用*******/
	}

	/**
	 * 在进入Dao方法之后执行
	 * @param point 切面对象
	 */
	public void after(JoinPoint point) {
		DynamicDataSourceHolder.markMaster();
	}

	/**
	 * 获取库类型
	 * @param methodName
	 * @return
	 */
	private String getDbType(String methodName) {
		/**************************************************** 资源库-匹配读库  begin ***************************************************/
		if (StringUtils.startsWithIgnoreCase(methodName, "r_query")) {
			return "RESOURCE_SLAVE";
		}
		if (StringUtils.startsWithIgnoreCase(methodName, "r_find")) {
			return "RESOURCE_SLAVE";
		}
		if (StringUtils.startsWithIgnoreCase(methodName, "r_get")) {
			return "RESOURCE_SLAVE";
		}
		/**************************************************** 资源库-匹配读库  end ***************************************************/
		
		/**************************************************** 资源库-匹配写库  begin ***************************************************/
		if (StringUtils.startsWithIgnoreCase(methodName, "r_")) {
			return "RESOURCE_MASTER";
		}
		/**************************************************** 资源库-匹配写库  begin ***************************************************/
		
		
		/**************************************************** 主库-匹配  begin ***************************************************/
		if (StringUtils.startsWithIgnoreCase(methodName, "query")) {
			return "SLAVE";
		}
		if (StringUtils.startsWithIgnoreCase(methodName, "find")) {
			return "SLAVE";
		}
		if (StringUtils.startsWithIgnoreCase(methodName, "get")) {
			return "SLAVE";
		} else
			//设置主库
			return "MASTER";
	}
	
	/**
	 * 判断是否为读库    
	 * 仅适用于一个主库的读写分离
	 * @param methodName
	 * @return
	 */
	@Deprecated
	private Boolean isSlave(String methodName) {
		// 方法名以query、find、get开头的方法名走从库
		if (StringUtils.startsWithIgnoreCase(methodName, "query")) {
			return true;
		}
		if (StringUtils.startsWithIgnoreCase(methodName, "find")) {
			return true;
		}
		if (StringUtils.startsWithIgnoreCase(methodName, "get")) {
			return true;
		} else
			return false;
	}

}