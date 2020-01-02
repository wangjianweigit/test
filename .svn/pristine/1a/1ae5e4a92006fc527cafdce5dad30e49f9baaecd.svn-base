package com.tk.oms.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.order.dao.OrderDao;
import com.tk.oms.order.dao.OrderUnusualDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("OrderUnusualService")
public class OrderUnusualService {
	
	@Resource
	private OrderUnusualDao orderUnusualDao;
	@Resource
	private OrderDao orderDao;
	
	/**
	 * 获取异常订单列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryOrderUnusualList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = orderUnusualDao.queryOrderUnusualList(paramMap);
			int count = orderUnusualDao.queryOrderUnusualCount(paramMap);
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
		}
		return gr;
	}
	
	/**
	 * 异常订单详情
	 */
	public ProcessResult queryOrderUnusualDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("order_number"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少订单号");
				return pr;
			}
			//获取待订单详情基本信息
			Map<String,Object> detail = orderDao.queryOrderDetail(params);
			if(detail==null){
				pr.setMessage("订单不存在");
				pr.setState(true);
				return pr;
			}
			//获取订单商品sku列表信息
			List<Map<String,Object>> productSkuList = orderUnusualDao.queryOrderProductSkuList(params);
			pr.setObj(productSkuList);
			pr.setMessage("获取数据成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 异常订单备注
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult orderUnusualRemark(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("state"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//新增活更新异常订单
			orderUnusualDao.addOrUpdateOrderUnusual(paramMap);
			pr.setMessage("异常订单备注成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 异常订单备注详情
	 */
	public ProcessResult orderUnusualRemarkDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("order_number"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少订单号");
				return pr;
			}
			//异常订单备注详情
			Map<String,Object> detail = orderUnusualDao.orderUnusualRemarkDetail(params);
			if(detail!=null){
				pr.setObj(detail);
				pr.setMessage("获取数据成功");
			}else{
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
