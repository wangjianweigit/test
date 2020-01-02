package com.tk.oms.oauth2.entity;

import java.util.Date;

/**
 * ERP用户与其他系统用户的关联关系,如ERP系统可以用第三方的账号登录，
 * 则在该表中记录两个系统之间用户的关系
 * @author songwangwen
 * @date  2018-2-5  下午4:06:12
 */
public class UserAuthor {
	private long id;	//主键ID
	
	private long user_id;	//ERP用户ID

	private String oa_open_id ;//oa系统的用户信息

	private Date create_date ;//创建日期

	private int delete_flag ;//删除标志位  0.未删除  1.已删除

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

	public String getOa_open_id() {
		return oa_open_id;
	}

	public void setOa_open_id(String oa_open_id) {
		this.oa_open_id = oa_open_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}
}
