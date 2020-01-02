package com.tk.store.marking.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : StoreActivityDao.java
 * 营销活动 DAO层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月30日
 */
@Repository
public interface StoreActivityDao {
	/**
	 * 活动列表总数量
	 * @param params
	 * @return
	 */
	int queryActivityListCount(Map<String, Object> params);
	/**
	 * 活动列表数据
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryActivityListForPage(Map<String, Object> params);
	/**
	 * 是否启用
	 * @param paramMap
	 */
	int updateActivityState(Map<String, Object> paramMap);
	/**
	 * 删除活动
	 * @param paramMap
	 * @return
	 */
	int deleteActivityInfo(Map<String, Object> paramMap);
	/**
	 * 删除活动门店
	 * @param paramMap
	 * @return
	 */
	int deleteStoreActivity(Map<String, Object> paramMap);
	/**
	 * 删除活动商品
	 * @param paramMap
	 */
	int deleteStoreActivityProduct(Map<String, Object> paramMap);
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
	 * 新增活动信息
	 * @param paramMap
	 * @return
	 */
	int insertStoreActivityInfo(Map<String, Object> paramMap);
	/**
	 * 新增活动关联门店
	 * @param paramMap
	 * @return
	 */
	int insertStoreActivity(Map<String, Object> paramMap);
	/**
	 * 新增活动关联商品
	 * @param paramMap
	 * @return
	 */
	int insertStoreActivityProduct(Map<String, Object> paramMap);
	/**
	 * 查询活动主表信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreActivityInfo(Map<String, Object> paramMap);
	/**
	 * 活动商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryActivityProductList(Map<String, Object> paramMap);
	/**
	 * 活动商品列表总数
	 * @param paramMap
	 * @return
	 */
	int queryActivityProductCount(Map<String, Object> paramMap);
	/**
	 * 活动店铺列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryActivityStoreList(Map<String, Object> paramMap);
	/**
	 * 查询满减满折信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryActivityPreferentList(Map<String, Object> paramMap);
	/**
	 * 删除满减满折信息
	 * @param paramMap
	 * @return
	 */
	int deleteStoreActivityPreferential(Map<String, Object> paramMap);
	/**
	 * 修改活动时间
	 * @param paramMap
	 * @return
	 */
	int updateActivityDate(Map<String, Object> paramMap);
	/**
	 * 新增满减满折数据
	 * @param paramMap
	 * @return
	 */
	int insertStoreActivityPreferential(List<Map<String, Object>> list);
	/**
	 * 活动审核总数量
	 * @param params
	 * @return
	 */
	int queryActivityApprovalListCount(Map<String, Object> params);
	/**
	 * 活动审核列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryActivityApprovalListForPage(Map<String, Object> params);
	/**
	 * 撤出活动商品(支持单个和多个商品撤出)
	 * @param params
	 * @return
	 */
	int removeProduct(Map<String, Object> params);
	/**
	 * 活动是否存在
	 * @param params
	 * @return
	 */
	int queryActivityIsExists(Map<String, Object> params);
	/**
	 * 更新审批信息
	 * @param paramMap
	 */
	int updateActivityApprovalInfo(Map<String, Object> paramMap);
	/**
	 * 更新活动信息
	 * @param paramMap
	 * @return
	 */
	int updateStoreActivityInfo(Map<String, Object> paramMap);
	/**
	 * 获取活动商家ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryPartnerUserList(Map<String, Object> paramMap);
	/**
	 * 获取活动门店ID和商家ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryStoreList(Map<String, Object> paramMap);
	/**
	 * 查询活动商品信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryStoreActivityProductInfo(Map<String, Object> paramMap);
	/**
	 * 店铺商品审批
	 * @param paramMap
	 * @return
	 */
	int updateStoreActivityProductState(Map<String, Object> paramMap);
	/**
	 * 更新新零售活动ID
	 * @param paramMap
	 * @return
	 */
	int updateAgentActivityId(Map<String, Object> paramMap);
	/**
	 * 查询当前活动商品是否在活动中
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryActivingProductInfo(String id);
	/**
	 * 查询满减满折信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryActivityPreferentInfo(Map<String, Object> paramMap);
	/**
	 * 查询当前活动所有已经审批的和当前审批的商品
	 * @param paramMap
	 * @return
	 */
	List<String> queryProductItemnumberByActivityId(Map<String, Object> paramMap);
	/**
	 * 查询当前货号是否已存在
	 * @param paramMap
	 * @return
	 */
	int queryProductCountByItemnumber(Map<String, Object> paramMap);
	/**
	 * 更新当前商品状态
	 * @param paramMap
	 * @return
	 */
	int updateStoreActivityProduct(Map<String, Object> paramMap);
	/**
	 * 查询未撤出活动商品的并且是审批通过的商品货号
	 * @param paramMap
	 * @return
	 */
	List<String> queryNotRemoveApprovalProduct(Map<String, Object> paramMap);
	/**
	 * 查询未撤销的经销商id
	 * @param paramMap
	 * @return
	 */
	List<String> queryNotRemovePartnerUserList(Map<String, Object> paramMap);
	/**
	 * 查询新零售活动id
	 * @param paramMap
	 * @return
	 */
	String qeuryDiscountId(Map<String, Object> paramMap);
	/**
	 * 判断当前商品所在门店进行的数量
	 * @param paramMap
	 * @return
	 */
	int checkActivityStoreCount(Map<String, Object> paramMap);
	/**
	 * 查询经销商ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryAgentId(Map<String, Object> paramMap);
	/**
	 * 查询商家名称
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryUserManageNameByAgentId(Map<String, Object> paramMap);
	/**
	 * 查询商品分类
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryProductTypeByTypeId(Map<String, Object> paramMap);
	/**
	 * 查询指定商家下拉数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserOption(Map<String, Object> paramMap);
	/**
	 * 转换经销商ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryActivityAgentID(Map<String, Object> paramMap);
	/**
	 * 封装货号
	 * @param paramMap
	 * @return
	 */
	List<String> queryActivityItemnumber(Map<String, Object> paramMap);
	/**
	 * 查询需要过滤的商品货号
	 * @param paramMap
	 * @return
	 */
	List<String> queryFilterProductList(Map<String, Object> paramMap);
	/**
	 * 查询正在活动中的自营商品
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryActivingZyProductList(Map<String, Object> paramMap);
	/**
	 * 将需要过滤的商品货号插入到临时表
	 * @param paramMap
	 * @return
	 */
	int insertTempProductItemnumber(Map<String, Object> paramMap);
	/**
	 * 查询是否已存在活动中的门店
	 * @param paramMap
	 * @return
	 */
	int queryIsExistsActivingStore(Map<String, Object> paramMap);
}
