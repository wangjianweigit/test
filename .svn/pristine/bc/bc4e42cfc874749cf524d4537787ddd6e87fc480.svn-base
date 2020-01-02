package com.tk.store.marking.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface CouponsDao {
	
	/**
	 * 商品库数量
	 * @param paramMap
	 * @return
	 */
	int queryProductLibraryCount(Map<String, Object> paramMap);
	/**
	 * 商品库列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductLibraryListForPage(Map<String, Object> paramMap);
	/**
	 * 门店库数量
	 * @param paramMap
	 * @return
	 */
	int queryStoreLibraryCount(Map<String, Object> paramMap);
	/**
	 * 门店库列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreLibraryListForPage(Map<String, Object> paramMap);
	/**
	 * 获取门店所属商家集合
	 * @param paramMap
	 * @return
	 */
	List<String> queryUserList(Map<String, Object> paramMap);
	/**
	 * 查询商家下的门店
	 * @param paramMap
	 * @return
	 */
	List<String> queryUserStoreList(Map<String, Object> paramMap);
	/**
	 * 查询门店信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String,Object>> queryStoreList(Map<String, Object> paramMap);
	/**
	 * 查询商品信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String,Object>> queryProductList(Map<String, Object> paramMap);
	/**
	 * 查询合作商下的门店
	 * @param paramMap
	 * @return
	 */
	List<String> queryStoreByPartner(Map<String, Object> paramMap);

}
