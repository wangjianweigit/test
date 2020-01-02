package com.tk.store.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.store.order.dao.StoreOrderDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.basicinfo.dao.LogisticsCompanyDao;
import com.tk.oms.finance.dao.SettlementDao;

@Service("StoreOrderService")
public class StoreOrderService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private StoreOrderDao storeOrderDao;
	@Resource
	private LogisticsCompanyDao logisticsCompanyDao;
	@Value("${pay_service_url}")
	private String pay_service_url;//远程调用见证宝接口
	@Resource
    private SettlementDao settlementDao;
	
	/**
	 * 获取店铺订货单列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryOrderList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
//			if(StringUtils.isEmpty(paramMap.get("region_id"))) {
//				gr.setState(false);
//				gr.setMessage("缺少参数，区域ID");
//				return gr;
//			}
			if((!StringUtils.isEmpty(paramMap.get("order_state")))&&paramMap.get("order_state") instanceof String){
				paramMap.put("order_state",(paramMap.get("order_state")+"").split(","));
			}
			List<Map<String, Object>> list = storeOrderDao.queryOrderList(paramMap);
			int count = storeOrderDao.queryOrderCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取店铺订货单详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOrderDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取待订单详情基本信息
			Map<String,Object> detail = storeOrderDao.queryOrderDetail(paramMap);
			if(detail!=null){
				//获取订单商品详情信息
				//List<Map<String,Object>> productList = orderDao.queryOrderProductList(paramMap);
				//获取订单商品sku列表信息
				List<Map<String,Object>> productSkuList = storeOrderDao.queryOrderProductSkuList(paramMap);
				if("3".equals(detail.get("ORDER_STATE")+"")||"5".equals(detail.get("ORDER_STATE")+"")){
					List<Map<String,Object>> boxList = storeOrderDao.queryOrderBoxList(paramMap);
					resultMap.put("box_info", boxList);
				}
				resultMap.put("base_info", detail);
				//获取物流公司列表
				if(!StringUtils.isEmpty(paramMap.get("require_logistics_company"))&&"1".equals(paramMap.get("require_logistics_company"))){
					Map<String, Object> map = new HashMap<String, Object>();
					if("批发".equals(detail.get("ORDER_TYPE"))) {//订单类型
						map.put("order_type", "1");
					}else if("代发".equals(detail.get("ORDER_TYPE"))) {
						map.put("order_type", "2");
					}
					map.put("shipping_method_id", detail.get("DELIVERY_TYPE"));//订单所属配送方式
					List<Map<String,Object>> logisticsCompanyList=logisticsCompanyDao.queryLogisticsCompany(map);
					resultMap.put("logistics_info", logisticsCompanyList);
				}
				//resultMap.put("product_info", productList);
				resultMap.put("product_sku_info", productSkuList);
				pr.setMessage("获取数据成功");
				pr.setObj(resultMap);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 店铺订货单取消
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult orderCancel(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("order_number"))||StringUtils.isEmpty(paramMap.get("public_user_name"))||StringUtils.isEmpty(paramMap.get("cancel_reason"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			paramMap.put("cancel_reason",paramMap.get("cancel_reason"));
			storeOrderDao.cancelOrder(paramMap);
			String output_status = String.valueOf(paramMap.get("output_status"));//状态 0-失败 1-成功
			String output_msg = String.valueOf(paramMap.get("output_msg"));//当成功时为：取消成功   当失败是：为错误消息内容
			if(ResponseSateEnum.SUCCESS.getValue().equals(output_status)){//成功
				pr.setState(true);
			}else{
				pr.setState(false);
			}
			pr.setMessage(output_msg);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	
	/**
	 * 店铺订货单支付
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult paymentStoreOrder(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			// 前台用户数据
			Map<String,Object> paramMap = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
			storeOrderDao.updatePaymentState(paramMap);
			String output_status = String.valueOf(paramMap.get("output_status"));//状态 0-失败 1-成功
			String output_msg = String.valueOf(paramMap.get("output_msg"));//当成功时为：取消成功   当失败是：为错误消息内容
			
			if(ResponseSateEnum.SUCCESS.getValue().equals(output_status)){//成功
				Map<String,Object> orderMap = new HashMap<String,Object>();
				//查询交易号
				String pay_trade_number = storeOrderDao.queryOrderPayTradeNumber(paramMap);
				orderMap.put("order_number", paramMap.get("order_number"));
				orderMap.put("pay_trade_number", pay_trade_number);
				orderMap.put("is_store_order", 0);
				// 处理入驻商待清分记录
                if(settlementDao.insertSettlementForStationed(orderMap) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！1");
                }
                // 处理平台服务待清分记录
                if(settlementDao.insertSettlementForPlatform(orderMap) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！2");
                }
                // 处理仓库服务待清分记录
                if(settlementDao.insertSettlementForStorage(orderMap) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！3");
                }
                // 处理入驻商服务待清分记录
                if(settlementDao.insertSettlementForStationedServer(orderMap) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！4");
                }
                // 处理入驻商支付服务费待清分记录
				settlementDao.insertSettlementForStationedPay(orderMap);
                // 处理代发费和运费待清分记录
                if(settlementDao.insertSettlementForConsignment(orderMap) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！6");
                }
                // 处理代发费和运费待清分记录
                if(settlementDao.insertSettlementForFreight(orderMap) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！7");
                }

                // 增加入驻资金流水记录
                if(settlementDao.insertStationedCapitalLogs(orderMap.get("order_number").toString()) <= 0){
                    throw new RuntimeException("付款异常，请稍后再试！8");
                }
				
				pr.setState(true);
				pr.setMessage(output_msg);
			}else{
				pr.setState(false);
				pr.setMessage(output_msg);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("审批异常，异常原因："+e.getMessage());
		}
		return pr;
	}
	
}