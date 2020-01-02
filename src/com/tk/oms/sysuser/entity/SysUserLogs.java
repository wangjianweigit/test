package com.tk.oms.sysuser.entity;

import java.util.Date;

/**
* Copyright (c), 2016, Tongku
* FileName : SysUserLogs.java
* 系统用户日志实体类
* @author  wangpeng
* @date 2016-06-20
* @version1.00
*/

public class SysUserLogs {

	/** 日志ID */
	private Long id;
	/** 操作人ID */
	private Long user_id;
	/** 操作人姓名 */
	private String user_name;
	/** 访问路径 */
	private String query_url;
	/** 访问类型 */
	private String query_type;
	/** POST参数 */
	private String post_param;
	/** GET参数 */
	private String get_param;
	/** 访问的IP地址 */
	private String query_ip;
	/** 操作说明 */
	private String remark;
	/** 操作时间 */
	private Date create_date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getQuery_url() {
		return query_url;
	}
	public void setQuery_url(String query_url) {
		this.query_url = query_url;
	}
	public String getQuery_type() {
		return query_type;
	}
	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}
	public String getPost_param() {
		return post_param;
	}
	public void setPost_param(String post_param) {
		this.post_param = post_param;
	}
	public String getGet_param() {
		return get_param;
	}
	public void setGet_param(String get_param) {
		this.get_param = get_param;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getQuery_ip() {
		return query_ip;
	}
	public void setQuery_ip(String query_ip) {
		this.query_ip = query_ip;
	}
}
