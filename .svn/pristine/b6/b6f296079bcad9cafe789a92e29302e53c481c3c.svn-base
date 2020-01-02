package com.tk.store.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.store.order.dao.StoreTransactDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StoreTransactService")
public class StoreTransactService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Value("${store_service_url}")
    private String store_service_url;
	@Resource
	private StoreTransactDao storeTransactDao;
	
	/**
	 * 销售订单列表
	 * @param request
	 * @return
	 */
	public GridResult storeTranscatSalesList(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGE_SIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("ORDER_ID") && !StringUtils.isEmpty(params.get("ORDER_ID"))){
                sendMap.put("ORDER_ID",params.get("ORDER_ID"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("LY_STORE_ID",params.get("STORE_ID"));
            }
            if(params.containsKey("STAFF_ID") && !StringUtils.isEmpty(params.get("STAFF_ID"))){
                sendMap.put("STAFF_ID",params.get("STAFF_ID"));
            }
            if(params.containsKey("STORE_SALES_ID") && !StringUtils.isEmpty(params.get("STORE_SALES_ID"))){
                sendMap.put("STORE_SALES_ID",params.get("STORE_SALES_ID"));
            }
            if(params.containsKey("MIN_DATE") && !StringUtils.isEmpty(params.get("MIN_DATE"))){
                sendMap.put("MIN_DATE",params.get("MIN_DATE"));
            }
            if(params.containsKey("MAX_DATE") && !StringUtils.isEmpty(params.get("MAX_DATE"))){
                sendMap.put("MAX_DATE",params.get("MAX_DATE"));
            }
            if(params.containsKey("PAY_TYPE") && !StringUtils.isEmpty(params.get("PAY_TYPE"))){
                sendMap.put("PAY_TYPE",params.get("PAY_TYPE"));
            }
            if(params.containsKey("ORDER_STATE")){
                sendMap.put("ORDER_STATE",params.get("ORDER_STATE"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"order/Order/orderList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取销售订单列表成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("LIST");
                int total=Integer.parseInt(data.get("TOTAL").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(true);
            	gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 销售订单初始化收营员列表
	 * @param request
	 * @return
	 */
	public GridResult storeTranscatSyySelectList(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("TYPE",1);
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",params.get("STORE_ID"));
            }
            if(params.containsKey("IS_CLERK") && !StringUtils.isEmpty(params.get("IS_CLERK"))){
                sendMap.put("IS_CLERK",params.get("IS_CLERK"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"auth/Staff/staffMes");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取收银员列表成功");
                List<Map<String, Object>> dataList =  (List<Map<String, Object>>)resPr.get("data");
                gr.setObj(dataList);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("msg").toString());
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 销售订单详情
	 * @param request
	 * @return
	 */
	public GridResult storeTranscatSalesDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("ORDER_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数ORDER_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            sendMap.put("ORDER_ID",params.get("ORDER_ID"));
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"order/Order/orderDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                gr.setState(true);
                gr.setMessage("获取销售订单详情成功");
                gr.setObj(data);
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 售后订单列表
	 * @param request
	 * @return
	 */
	public GridResult storeTranscatReturnsList(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGE_SIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("REFUND_ID") && !StringUtils.isEmpty(params.get("REFUND_ID"))){
                sendMap.put("REFUND_ID",params.get("REFUND_ID"));
            }
            if(params.containsKey("ORDER_ID") && !StringUtils.isEmpty(params.get("ORDER_ID"))){
                sendMap.put("ORDER_ID",params.get("ORDER_ID"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("LY_STORE_ID",params.get("STORE_ID"));
            }
            if(params.containsKey("MIN_DATE") && !StringUtils.isEmpty(params.get("MIN_DATE"))){
                sendMap.put("MIN_DATE",params.get("MIN_DATE"));
            }
            if(params.containsKey("MAX_DATE") && !StringUtils.isEmpty(params.get("MAX_DATE"))){
                sendMap.put("MAX_DATE",params.get("MAX_DATE"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"order/Refund/refundList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取售后订单列表成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>>dataList=(List<Map<String, Object>>)data.get("LIST");
                int total=Integer.parseInt(data.get("TOTAL").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(true);
            	gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 售后订单详情
	 * @param request
	 * @return
	 */
	public GridResult storeTranscatReturnsDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("REFUND_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数REFUND_ID");
                return gr; 
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            sendMap.put("REFUND_ID",params.get("REFUND_ID"));
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"order/Refund/refundDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                gr.setState(true);
                gr.setMessage("获取售后订单详情成功");
                gr.setObj(data);
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
	}
	public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params);
        return Transform.GetPacketJzb(json,Map.class);
    }
	
	/**
	 * 商家列表(下拉框)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberStoreSelectList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(storeTransactDao.queryMemberStoreSelectList(params));
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
		return pr;
	}
	
	/**
  	 * 查询商家下面的店铺列表
  	 * @param request
  	 * @return
  	 */
  	@SuppressWarnings("unchecked")
  	public GridResult queryUserStoreList(HttpServletRequest request) {
  		GridResult gr = new GridResult();
  		Map<String, Object> paramMap = new HashMap<String, Object>();
  		try {
  			String json = HttpUtil.getRequestInputStream(request);
  			if(!StringUtils.isEmpty(json)){
  				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
  			}
  			if(StringUtils.isEmpty(paramMap.get("user_id"))){
  				gr.setState(false);
  				gr.setMessage("缺少参数user_id");
  				return gr;
  			}
  			//列表
  			List<Map<String, Object>> list = storeTransactDao.queryUserStoreList(paramMap);
  			if (list != null && list.size() > 0) {
  				gr.setMessage("获取数据成功");
  				gr.setObj(list);
  			} else {
  				gr.setMessage("无数据");
  			}
  			gr.setState(true);
  		} catch (Exception e) {
  			gr.setState(false);
  			gr.setMessage(e.getMessage());
  		}
  		return gr;
  	}
}
