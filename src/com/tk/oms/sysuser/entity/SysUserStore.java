package com.tk.oms.sysuser.entity;



/**
 * @author wangpeng
 * 用户门店关联表实体类，对于数据表：TBL_SYS_USER_STORE
 */
public class SysUserStore {
	private long id;// 主键ID
	private long user_id;// 用户ID
	private long store_id;//门店ID
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public long getStore_id() {
		return store_id;
	}
	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}
	
}
