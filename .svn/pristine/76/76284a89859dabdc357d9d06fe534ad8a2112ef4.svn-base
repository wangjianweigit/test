package com.tk.sys.util;

/**
 * 使用ThreadLocal技术来记录当前线程中的数据源的key
 * 
 * @author wangpeng
 * @version 1.0
 * @date 2019-04-04
 */
public class DynamicDataSourceHolder {

	// 主库-写库对应的数据源key
	private static final String MASTER = "master";

	// 主库-读库对应的数据源key
	private static final String SLAVE = "slave";
	
	// 资源库-写库对应的数据源key
	private static final String RESOURCE_MASTER = "resource_master";
	
	// 资源库-读库对应的数据源key
	private static final String RESOURCE_SLAVE = "resource_slave";

	// 使用ThreadLocal记录当前线程的数据源key
	private static final ThreadLocal<String> holder = new ThreadLocal<String>();

	/**
	 * 设置数据源key
	 * @param key
	 */
	public static void putDataSourceKey(String key) {
		holder.set(key);
	}

	/**
	 * 获取数据源key
	 * @return
	 */
	public static String getDataSourceKey() {
		return holder.get();
	}

	/**
	 * 主库-标记写库
	 */
	public static void markMaster() {
		putDataSourceKey(MASTER);
	}

	/**
	 * 主库-标记读库
	 */
	public static void markSlave() {
		putDataSourceKey(SLAVE);
	}
	
	/**
	 * 资源库-标记写库
	 */
	public static void markResourceMaster() {
		putDataSourceKey(RESOURCE_MASTER);
	}
	
	/**
	 * 资源库-标记读库
	 */
	public static void markResourceSlave() {
		putDataSourceKey(RESOURCE_SLAVE);
	}

}