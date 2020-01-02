package com.tk.store.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : PublicNumberManagedao.java
 * 公众号DAO层
 *
 * @author liujialong
 * @version 1.00
 * @date 2018年9月7日
 */
@Repository
public interface PublicNumberManageDao {
	
	/**
	 * 公众号列表
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> queryPublicNumberManageList(Map<String, Object> params);
	/**
	 * 公众号列表总数
	 * @param list
	 * @return
	 */
	int queryPublicNumberManageCount(Map<String, Object> params);
	/**
	 * 公众号详情
	 * @param list
	 * @return
	 */
	Map<String, Object> queryPublicNumberManageDetail(Map<String, Object> params);

}
