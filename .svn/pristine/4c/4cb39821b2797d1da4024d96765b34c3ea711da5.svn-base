package com.tk.oms.sysuser.entity;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author wangpeng
 * 系统角色表实体类，对于数据表：TBL_SYS_ROLE
 */
public class SysRole {
	private long id;// 角色ID
	private String role_name;// 角色名称
	private String remarks;//角色描述
	private Date create_time;// 创建时间
	private int checked;// 是否选中--补充字段 业务使用
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public String getCreate_time_frm() {
		if(create_time!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(create_time);
		}else{
			return "--";
		}
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
}
