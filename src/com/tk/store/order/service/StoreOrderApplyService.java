package com.tk.store.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.store.order.dao.StoreOrderApplyDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StoreOrderApplyService")
public class StoreOrderApplyService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	private StoreOrderApplyDao storeOrderApplyDao;
	
	/**
	 * 获取要货单、退款单列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreOrderApplyList(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("type"))){
				gr.setMessage("缺少参数");
				gr.setState(false);
				return gr;
			}
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = storeOrderApplyDao.queryStoreOrderApplyList(paramMap);
			int count = storeOrderApplyDao.queryStoreOrderApplyCount(paramMap);
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
	 * 获取要货单、退款单详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreOrderApplyDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
				pr.setMessage("缺少参数【type】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setMessage("缺少参数【order_number】");
				pr.setState(false);
				return pr;
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取要货单、退款单基本信息
			Map<String,Object> detail = storeOrderApplyDao.queryStoreOrderApplyDetail(paramMap);
			if(detail!=null){
				//获取要货单、退款单商品sku列表信息
				List<Map<String,Object>> productSkuList = storeOrderApplyDao.queryOrderProductSkuList(paramMap);
				//查询货品总金额
				float product_total_money = storeOrderApplyDao.queryProductTotalMoney(paramMap);
				resultMap.put("base_info", detail);
				resultMap.put("product_sku_info", productSkuList);
				resultMap.put("product_total_money", product_total_money);
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
	 * 要货单、退款单确认
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult storeOrderApplyConfirm(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setMessage("缺少参数【order_number】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
				pr.setMessage("缺少参数【type】");
				pr.setState(false);
				return pr;
			}
			//获取要货单、退款单基本信息
			Map<String,Object> detail = storeOrderApplyDao.queryStoreOrderApplyDetail(paramMap);
			if(detail==null){
				pr.setMessage("当前订单不存在，请检查！");
				pr.setState(false);
				return pr;
			}
			//判断订单状态
			if(Integer.parseInt(detail.get("STATE").toString())!=2){
				pr.setMessage("当前订单状态异常，请检查！");
				pr.setState(false);
				return pr;
			}
			//订单确认
			int count= storeOrderApplyDao.storeOrderApplyConfirm(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("订单确认失败");
			}
			pr.setState(true);
			pr.setMessage("订单确认成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 要货单、退款单审批
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult storeOrderApplyApproval(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setMessage("缺少参数【order_number】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
				pr.setMessage("缺少参数【type】");
				pr.setState(false);
				return pr;
			}
			//获取要货单、退款单基本信息
			Map<String,Object> detail = storeOrderApplyDao.queryStoreOrderApplyDetail(paramMap);
			if(detail==null){
				pr.setMessage("当前订单不存在，请检查！");
				pr.setState(false);
				return pr;
			}
			//判断订单状态
			if(Integer.parseInt(detail.get("STATE").toString())!=1){
				pr.setMessage("当前订单状态异常，请检查！");
				pr.setState(false);
				return pr;
			}
			//订单审批
			int count= storeOrderApplyDao.storeOrderApplyApproval(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("订单审批失败");
			}
			pr.setState(true);
			pr.setMessage("订单审批成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 按照登入用户查询门店列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStoreList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			List<Map<String, Object>> storeList=storeOrderApplyDao.queryStoreList(paramMap);
			if(storeList==null || storeList.size()==0){
				pr.setMessage("无数据");
			}else{
				pr.setMessage("查询数据成功");
				pr.setObj(storeList);
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
