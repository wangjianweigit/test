package com.tk.oms.sysuser.entity;



/**
 * @author wangpeng
 * 角色权限关联表实体类，对于数据表：TBL_SYS_ROLE_NODE
 */
public class SysRoleNode {
	private long id;// 主键ID
	private long role_id;// 角色ID
	private long node_id;//节点ID
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRole_id() {
		return role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	public long getNode_id() {
		return node_id;
	}
	public void setNode_id(long node_id) {
		this.node_id = node_id;
	}
	
}
