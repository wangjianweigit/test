package com.tk.oms.oss.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OssDirectory {
	private long id; // 主键id
	private String user_type; // 用户类型 1：入驻商 2：分销商
	private long user_id; // 所属用户ID
	private long parent_id; // 父节目录id
	private String directory_type; // 文件夹类型 1:运营总后台定义的文件夹 2:用户自定义的文件夹
	private String directory_name; // 本目录文件夹名称
	private String directory_full_name; // 包含本文件夹以及所有的父文件夹的完整目录
	private Date create_date; // 创建时间
	private Date delete_date; // 删除时间
	private long file_directory_info_id; // 管理的目录管理记录ID

	private boolean isParent;// 是否拥有子文件夹 true:有子文文件夹 false 无子文文件夹
	private boolean isSearch;//是否是搜索包含关键字的文件夹  true:是   false:否
	private String directory_id;//防止id为非数字的情况，使用单独属性存储文件夹的ID信息
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}

	public String getDirectory_type() {
		return directory_type;
	}

	public void setDirectory_type(String directory_type) {
		this.directory_type = directory_type;
	}

	public String getDirectory_name() {
		return directory_name;
	}

	public void setDirectory_name(String directory_name) {
		this.directory_name = directory_name;
	}

	public String getDirectory_full_name() {
		return directory_full_name;
	}

	public void setDirectory_full_name(String directory_full_name) {
		this.directory_full_name = directory_full_name;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public long getFile_directory_info_id() {
		return file_directory_info_id;
	}

	public void setFile_directory_info_id(long file_directory_info_id) {
		this.file_directory_info_id = file_directory_info_id;
	}

	public boolean isSearch() {
		return isSearch;
	}

	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}

	public String getDirectory_id() {
		if(StringUtils.isEmpty(directory_id)){
			directory_id = String.valueOf(this.id);
		}
		return directory_id;
	}

	public void setDirectory_id(String directory_id) {
		this.directory_id = directory_id;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getDelete_date() {
		return delete_date;
	}

	public void setDelete_date(Date delete_date) {
		this.delete_date = delete_date;
	}
}