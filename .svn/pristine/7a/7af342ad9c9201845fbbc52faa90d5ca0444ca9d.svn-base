package com.tk.oms.sysuser.entity;


/**
 * @author wangpeng
 * 权限菜单功能节点表实体类，对于数据表：TBL_SYS_NODE
 */
public class SysNode {
	private long id;// 节点ID
	private String node_name;// 节点名称
	private String node_type;//节点类型
	private long parent_id;// 父节点ID
	private String key_name;// 唯一代码
	private String remarks;//节点描述
	private long sort_id;//排序ID（越小越靠前）
	private int is_delete;//是否删除（0，正常，1，已禁用）
	private int checked;// 是否选中--补充字段 业务使用
	private String url;//访问路径，只有类型为menu时才有值
	private String ico;//图片样式名
	private int menu_type;//菜单类型[0运营后台、1联营门店]
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNode_type() {
		return node_type;
	}
	public void setNode_type(String node_type) {
		this.node_type = node_type;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean getChecked() {
		return checked!=0;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return node_name;
	}
	public String getNode_name() {
		return node_name;
	}
	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}
	public long getSort_id() {
		return sort_id;
	}
	public void setSort_id(long sort_id) {
		this.sort_id = sort_id;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public int getMenu_type() {
		return menu_type;
	}
	public void setMenu_type(int menu_type) {
		this.menu_type = menu_type;
	}
	
}
