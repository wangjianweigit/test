package com.tk.store.returns.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
@Repository
public interface StoreReturnsDao {
	
	/**
	 * 店铺退货单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryReturnInfoList(Map<String, Object> paramMap);
	/**
	 * 店铺退货单数量
	 * @param paramMap
	 * @return
	 */
	public int queryReturnInfoCount(Map<String, Object> paramMap);
	/**
	 * 店铺退货单详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryReturnInfoDetail(Map<String, Object> paramMap);

	/**
	 * 店铺退货单合作商审批
	 * @param paramMap
	 * @return
	 */
	public int auditReturnInfo(Map<String, Object> paramMap);

	/**
	 * 查询店铺退货单 申请 可退 不可退 商品列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCanReturn(Map<String, Object> paramMap);
	/**
	 *查询店铺退货单 货号凭证
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryReturnImages(String return_number);
	
	/**
	 * 根据店铺退货单-退货单号查询物流凭证
	 * @param paramMap
	 * @return
	 */
	public List<String> queryLogisticsImages(String return_number);
	
	/**
	 * 查询店铺退货单-异常商品
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> queryUnusualProduct(String return_number);

	
	
	
}