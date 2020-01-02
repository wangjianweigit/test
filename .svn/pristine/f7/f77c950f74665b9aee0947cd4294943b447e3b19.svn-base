package com.tk.oms.finance.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvanceOrderDao extends BaseDao<Map<String, Object>>{
	/**
	 * 退定金审批数量
	 * @param paramMap
	 * @return
	 */
	public int queryDepositRefundCount(Map<String, Object> paramMap);
	/**
	 * 退定金审批列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryDepositRefundList(Map<String, Object> paramMap);
	/**
	 * 退定金详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryDepositRefundDetail(Map<String, Object> paramMap);
	/**
	 * 预定订单数量
	 * @param paramMap
	 * @return
	 */
	public int queryAdvanceOrderCount(Map<String, Object> paramMap);
	/**
	 * 预定订单列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryAdvanceOrderList(Map<String, Object> paramMap);
	/**
	 * 预定订单详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryAdvanceOrderDetail(Map<String, Object> paramMap);
	/**
	 * 预定商品清单
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPreUserCart(Map<String, Object> paramMap);
	/**
	 * 预定商品清单明细
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryPreUserCartDetail(Map<String, Object> paramMap);

	/**
	 * 定制订单商品明细
	 * @param paramMap
	 * @return
     */
	Map<String, Object> queryCustomOrderProductDetail(Map<String, Object> paramMap);

	/**
	 * 相关联的普通订单
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryOrderList(Map<String, Object> paramMap);
	/**
	 * 预定订单金额信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryAdvanceOrderMoneyInfo(Map<String, Object> paramMap);
	/**
	 * 查询待支付的订单是否村子
	 * @param paramMap
	 * @return
	 */
	public int queryOrderUnpaid(Map<String, Object> paramMap);
	/**
	 * 取消订单
	 * @param paramMap
	 * @return
	 */
	public int updateOrderCancel(Map<String, Object> paramMap);

	/**
	 * 获取预定订单中止申请信息
	 *
	 * @param returnNumber
	 * @return
     */
	public Map<String, Object> queryDepositReturnForUpdate(@Param("return_number") String returnNumber);

	/**
	 * 更新申请单状态
	 *
	 * @param paramMap
	 * @return
     */
	public int updateDepositReturn(Map<String, Object> paramMap);

	/**
	 * 更新预定订单状态
	 *
	 * @param paramMap
	 * @return
     */
	public int updatePreOrderState(Map<String, Object> paramMap);
	/**
	 * 新增已经支付定金的预定订单退货申请
	 * @param paramMap
	 * @return
	 */
	public int insertAdvanceOrderMoneyInfo(Map<String, Object> paramMap);
	/**
	 * 根据单号查询预定订单信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryPreOrderByNumber(Map<String, Object> paramMap);
	
	/**
	 * 检测当卡预定订单所关联的尾款订单是否存在  待支付 的情况
	 * @pramat paramMap.order_number 预定订单单号
	 * @return 0：不存在待支付的尾款订单    ；    大于1 存在
	 */
	public int checkRemainOrderStateByPreOrderNumber(Map<String, Object> paramMap);
	/**
	 * 手工占用  更新预定订单手工占用状态
	 */
	public int updatePreOrderWarehouseState(Map<String, Object> paramMap);
	/**
	 * 手工占用  插入预定订单手工占用数据
	 */
	public int insertAdvanceOrderWarehouseCount(Map<String, Object> paramMap);
	/**
	 * 手工占用数据清除 【关闭交易时，退定金单审批通过时】
	 */
	public int deletePreOrderWarehouseCount(Map<String, Object> paramsMap);

	/**
	 * 更新预定订单完成时间
	 *
	 * @param orderNumber
	 * @return
     */
	int updatePreOrderFinishDate(@Param("order_number") String orderNumber);
	/**
	 * 查询实付金额
	 * @param paramsMap
	 * @return
	 */
	public int queryActuallyMoney(Map<String, Object> paramsMap);

	/**
	 * 商品定制订单审批
	 * @param paramMap
	 * @return
     */
	int checkingCustomOrder(Map<String, Object> paramMap);
	/**
	 * 保存定制商品的自定义定制品牌信息
	 * @param paramMap
	 * @return
	 */
	int insertCustomBrand(Map<String, Object> paramMap);
	/**
	 * 更新定制商品的品牌信息
	 * @param paramMap
	 * @return
	 */
	int updateProductBrandId(Map<String, Object> paramMap);
	
	/**
	 * 根据定制订单单号，确认定制品牌信息是否已经存在，存在则返回已存在的定制品牌记录ID
	 * @param paramMap
	 * @return
	 */
	Long queryCustomBrandByPreOrderNum(Map<String, Object> paramMap);
	/**
	 *  查询没有生产尾款订单（尾款订单未关闭）的预付订单商品金额 
	 * @param paramMap
	 * @return
	 */
	String queryOrderedProducrMoneyBrPreOrderNumber(Map<String, Object> paramMap);
}
