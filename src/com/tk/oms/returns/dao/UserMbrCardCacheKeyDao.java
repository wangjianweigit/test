package com.tk.oms.returns.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 会员卡余额key值表
 * @author songwangwen
 * @date  2018-7-24  上午11:12:03
 */
@Repository
public interface UserMbrCardCacheKeyDao {
	/**
	 * 更新key值
	 * @param key
	 * @return
	 */
	int update(Map<String,Object> params);
}
