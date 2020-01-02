package com.tk.oms.sys.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ExchangeDateDao
 * 调换货时间配置
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-1-3
 */
@Repository
public interface ExchangeDateDao extends BaseDao<Map<String,Object>> {
	/**
	 * 校验该年份调换货时间配置是否存在
	 * @param paramMap
	 * @return
	 */
	public int isExist(Map<String, Object> paramMap);
	/**
	 * 查询调换货时间配置详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryExchangeDateDetail(Map<String, Object> paramMap);
	
}
