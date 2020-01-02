package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ActivityProductDiscountDao {

	/**
	 * 获取参与促销活动商品优惠列表
	 */
	public List<Map<String,Object>> queryActivityProductDiscountList(Map<String,Object> paramMap);
	/**
	 * 插入促销活动商品优惠列表
	 */
	public int insertActivityProductDiscount(List<Map<String, Object>> quitActivityProductList);
	/**
	 * 插入前删除促销活动商品优惠列表
	 */
	public int deleteActivityProductDiscount(Map<String, Object> paramMap);
	/**
	 * 查询一个参加活动的商品的各个规格的原始售价以及最低售价，作为设置阶梯价格的参考 
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> queryActivityProductPrice(Map<String,Object> paramMap);

	
	
}