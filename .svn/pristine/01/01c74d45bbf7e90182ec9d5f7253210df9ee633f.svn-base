package com.tk.pvtp.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PvtpProductDao {
	
	/**
	 * 获取商品列表
	 */
	public List<Map<String,Object>> queryProductList(Map<String,Object> paramMap);
	/**
	 * 获取商品总数
	 */
	public int queryProductCount(Map<String,Object> paramMap);
	/**
	 * 获取商品详情
	 */
	public Map<String, Object> queryProductDetail(Map<String,Object> paramMap);
	/**
	 * 获取商品参数列表
	 */
	public List<Map<String,Object>> queryProductParamsList(Map<String,Object> paramMap);
	/**
	 * 获取商品sku列表
	 */
	public List<Map<String,Object>> queryProductSkuList(Map<String,Object> paramMap);
	/**
	 * 获取商品图片
	 */
	public List<Map<String, Object>> queryProductImagesList(Map<String, Object> paramMap);
    /**
     * 获取商品包材信息
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryProductWrapperList(Map<String, Object> paramMap);
	/**
	 * 获取商品站点延迟显示时间
	 */
	public List<Map<String, Object>> queryProductSiteDelayList(Map<String, Object> paramMap);
	/**
	 * 获取商品显示区域
	 */
	public List<Map<String, Object>> queryProductRegionAreaList(Map<String, Object> paramMap);
}
