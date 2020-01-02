package com.tk.store.marking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.store.marking.dao.CouponsDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
@Service("CouponsService")
public class CouponsService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Value("${store_service_url}")
    private String store_service_url;
	@Resource
	private CouponsDao couponsDao;
	
	/**
	 * 优惠券列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryCouponsList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			Map<String,Object> sendMap=new HashMap<String,Object>();
			sendMap.put("INDEX",params.get("pageIndex"));
			sendMap.put("PAGE_SIZE",params.get("pageSize"));
			sendMap.put("COOPER_ID",params.get("COOPER_ID"));
			if((!StringUtils.isEmpty(params.get("COUPON_NAME")))&&params.get("COUPON_NAME") instanceof String){
				sendMap.put("COUPON_NAME",(params.get("COUPON_NAME")));
			}
			if((!StringUtils.isEmpty(params.get("JOINT_STORE_ID")))&&params.get("JOINT_STORE_ID") instanceof String){
				sendMap.put("JOINT_STORE_ID",(params.get("JOINT_STORE_ID")));
			}
			if((!StringUtils.isEmpty(params.get("TIME_START")))&&params.get("TIME_START") instanceof String){
				sendMap.put("TIME_START",(params.get("TIME_START")));
			}
			if((!StringUtils.isEmpty(params.get("TIME_END")))&&params.get("TIME_END") instanceof String){
				sendMap.put("TIME_END",(params.get("TIME_END")));
			}
			if((!StringUtils.isEmpty(params.get("STATUS")))){
				if(params.get("STATUS") instanceof String){
					sendMap.put("STATUS",(params.get("STATUS")+"").split(","));
				}else{
					sendMap.put("STATUS",params.get("STATUS"));
				}
			}
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/lyCouponList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取优惠券列表成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(true);
            	gr.setMessage("无数据");
            }
		} catch (Exception e) {			
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
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
     * 优惠券新增
     * @param request
     * @return
     */
	@Transactional
	@SuppressWarnings("unchecked")
    public ProcessResult couponsAdd(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  Map<String, Object> storeListMap = new HashMap<String, Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(!param.containsKey("NAME") || StringUtils.isEmpty(param.get("NAME"))) {
			  pr.setState(false);
			  pr.setMessage("缺少参数优惠券名称NAME");
			  return pr;
		  }
		  List<String> store_ids=null;
		  if(Integer.parseInt(param.get("store_type").toString()) == 1){
			  //指定门店
			  store_ids = (List<String>) param.get("store_id");
		  }else{
			  //全部门店
			  store_ids=couponsDao.queryStoreByPartner(param);
		  }
		  storeListMap.put("store_ids", store_ids);
		  //获取门店所属商家集合
		  List<String> userList=couponsDao.queryUserList(storeListMap);
		  Map<String,List<String>> data = new HashMap<String,List<String>>();
		  for(String user_id:userList){
				storeListMap.put("user_id", user_id);
				//查询该商家下的门店
				data.put(user_id, couponsDao.queryUserStoreList(storeListMap));
			}
		  Map<String,Object> sendMap=new HashMap<String,Object>();
		  sendMap.put("AGENT_ID", userList);
		  sendMap.put("COOPER_ID", param.get("partner_user_id"));
		  sendMap.put("TYPE", 1);
		  sendMap.put("NAME", param.get("NAME"));
		  sendMap.put("DISCOUNT", param.get("DISCOUNT"));
		  sendMap.put("LIMIT_PRICE", param.get("limit_price"));
		  sendMap.put("TOTAL", param.get("TOTAL"));
		  sendMap.put("TIME_START", param.get("TIME_START"));
		  sendMap.put("TIME_END", param.get("TIME_END"));
		  sendMap.put("ITEM_TYPE", param.get("item_type"));
		  sendMap.put("STORE_TYPE", param.get("store_type"));
		  if(Integer.parseInt(param.get("item_type").toString()) == 1){
			  sendMap.put("ITEM_ARR", param.get("item_arr"));
		  }
		  sendMap.put("DATA", data);
		  sendMap.put("STATUS", 1);
		  sendMap.put("LIMIT_NUMBER", param.get("limit_number"));
		  
		  Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/couponBatchInsert");
		  if (Integer.parseInt(resPr.get("state").toString()) == 1) {
			  pr.setState(true);
			  pr.setMessage("新增成功");
          }else{
        	  pr.setState(false);
          	  throw new RuntimeException(resPr.get("message").toString());
          }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
	
	/**
     * 优惠券编辑
     * @param request
     * @return
     */
	@Transactional
	@SuppressWarnings("unchecked")
    public ProcessResult couponsEdit(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	 Map<String, Object> storeListMap = new HashMap<String, Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(!param.containsKey("cooper_id") || StringUtils.isEmpty(param.get("cooper_id"))) {
			  pr.setState(false);
			  pr.setMessage("缺少参数cooper_id");
			  return pr;
		  }
		  if(!param.containsKey("coupon_id") || StringUtils.isEmpty(param.get("coupon_id"))) {
			  pr.setState(false);
			  pr.setMessage("缺少参数coupon_id");
			  return pr;
		  }
		  List<String> store_ids=null;
		  if(Integer.parseInt(param.get("store_type").toString()) == 1){
			  //指定门店
			  store_ids = (List<String>) param.get("store_id");
		  }else{
			  //全部门店
			  store_ids=couponsDao.queryStoreByPartner(param);
		  }
		  storeListMap.put("store_ids", store_ids);
		  //获取门店所属商家集合
		  List<String> userList=couponsDao.queryUserList(storeListMap);
		  Map<String,List<String>> data = new HashMap<String,List<String>>();
		  for(String user_id:userList){
				storeListMap.put("user_id", user_id);
				//查询该商家下的门店
				data.put(user_id, couponsDao.queryUserStoreList(storeListMap));
			}
		  Map<String,Object> sendMap=new HashMap<String,Object>();
		  sendMap.put("COOPER_ID", param.get("cooper_id"));
		  sendMap.put("COUPON_ID", param.get("coupon_id"));
		  sendMap.put("NAME", param.get("name"));
		  sendMap.put("LIMIT_NUMBER", param.get("limit_number"));
		  sendMap.put("AGENT_ID", userList);
		  sendMap.put("DATA", data);
		  sendMap.put("ITEM_TYPE", param.get("item_type"));
		  sendMap.put("STORE_TYPE", param.get("store_type"));
		  if(Integer.parseInt(param.get("item_type").toString()) == 1){
			  sendMap.put("ITEM_ARR", param.get("item_arr"));
		  }
		  Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/couponBatchEdit");
		  if (Integer.parseInt(resPr.get("state").toString()) == 1) {
			  pr.setState(true);
			  pr.setMessage("编辑成功");
          }else{
        	  pr.setState(false);
          	  throw new RuntimeException(resPr.get("message").toString());
          }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
	
	/**
	 * 优惠券详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult couponsDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			  if(!StringUtils.isEmpty(json)){
				param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
			  }
			Map<String,Object> sendMap=new HashMap<String,Object>();
			
			if(!param.containsKey("COOPER_ID") || StringUtils.isEmpty(param.get("COOPER_ID"))) {
				 gr.setState(false);
				 gr.setMessage("缺少参数COOPER_ID");
				 return gr;
			  }
			if(!param.containsKey("COUPON_ID") || StringUtils.isEmpty(param.get("COUPON_ID"))) {
				 gr.setState(false);
				 gr.setMessage("缺少参数COUPON_ID");
				 return gr;
			  }
			sendMap.put("COOPER_ID", param.get("COOPER_ID"));
			sendMap.put("COUPON_ID", param.get("COUPON_ID"));
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/lyCouponInfo");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取优惠券详情成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                //查询门店信息
                List<String> store_ids= Arrays.asList(data.get("JOINT_STORE").toString().split(","));
                resPr.put("store_ids", store_ids);
                List<Map<String,Object>> store_list=couponsDao.queryStoreList(resPr);
                data.put("store_list", store_list);
                //查询商品信息
                if(!StringUtils.isEmpty(data.get("ITEMNUMBER"))){
                	List<String> product_ids= Arrays.asList(data.get("ITEMNUMBER").toString().split(","));
                    resPr.put("product_ids", product_ids);
                    List<Map<String,Object>> product_list=couponsDao.queryProductList(resPr);
                    data.put("product_list", product_list);
                }
                gr.setObj(data);
            }else{
            	gr.setState(true);
            	gr.setMessage("无数据");
            }
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
     * 优惠券删除
     * @param request
     * @return
     */
	@Transactional
	@SuppressWarnings("unchecked")
    public ProcessResult couponsDelete(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  Map<String,Object> sendMap=new HashMap<String,Object>();
		  if(!param.containsKey("COOPER_ID") || StringUtils.isEmpty(param.get("COOPER_ID"))) {
			   pr.setState(false);
			   pr.setMessage("缺少参数COOPER_ID");
			   return pr;
		  }
		  if(!param.containsKey("COUPON_ID") || StringUtils.isEmpty(param.get("COUPON_ID"))) {
			pr.setState(false);
			pr.setMessage("缺少参数COUPON_ID");
			 return pr;
		   }
		  sendMap.put("COOPER_ID", param.get("COOPER_ID"));
		  sendMap.put("COUPON_ID", param.get("COUPON_ID"));
		  sendMap.put("STATUS", param.get("STATUS"));
		  Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/couponBatchEdit");
		  if (Integer.parseInt(resPr.get("state").toString()) == 1) {
			  pr.setState(true);
			  pr.setMessage("删除成功");
          }else{
        	  pr.setState(false);
          	  throw new RuntimeException(resPr.get("message").toString());
          }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
	
	/**
     * 优惠券启用禁用
     * @param request
     * @return
     */
	@Transactional
	@SuppressWarnings("unchecked")
    public ProcessResult couponsUpdateState(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(!param.containsKey("product_list") || StringUtils.isEmpty(param.get("product_list"))) {
			 pr.setState(false);
			 pr.setMessage("缺少参数product_list");
			 return pr;
		  }
		  List<Map<String,Object>> product_list = (List<Map<String,Object>>)param.get("product_list");
		  if(product_list.isEmpty()||product_list.size()<1){
			 pr.setState(false);
			 pr.setMessage("缺少参数,商品列表为空");
			 return pr;
		  }
		
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
	
	/**
	 * 获取商品库列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductLibraryListForPage(HttpServletRequest request) {
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
			if(!paramMap.containsKey("partner_user_id") || StringUtils.isEmpty(paramMap.get("partner_user_id"))) {
				gr.setState(false);
				gr.setMessage("缺少参数partner_user_id");
				return gr;
			  }
			if((!StringUtils.isEmpty(paramMap.get("product_itemnumbers")))&&paramMap.get("product_itemnumbers") instanceof String){
				paramMap.put("product_itemnumbers",(paramMap.get("product_itemnumbers")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("store_ids")))&&paramMap.get("store_ids") instanceof String){
				paramMap.put("store_ids",(paramMap.get("store_ids")+"").split(","));
			}
			//数量
			int count = couponsDao.queryProductLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = couponsDao.queryProductLibraryListForPage(paramMap);
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
	 * 获取门店库列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStoreLibraryListForPage(HttpServletRequest request) {
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
			if(!paramMap.containsKey("partner_user_id") || StringUtils.isEmpty(paramMap.get("partner_user_id"))) {
				gr.setState(false);
				gr.setMessage("缺少参数partner_user_id");
			    return gr;
			 }
			if((!StringUtils.isEmpty(paramMap.get("store_ids")))&&paramMap.get("store_ids") instanceof String){
				paramMap.put("store_ids",(paramMap.get("store_ids")+"").split(","));
			}
			//数量
			int count = couponsDao.queryStoreLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = couponsDao.queryStoreLibraryListForPage(paramMap);
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
	 * 优惠券详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult couponsFlow(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			  if(!StringUtils.isEmpty(json)){
				param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
			  }
			Map<String,Object> sendMap=new HashMap<String,Object>();
			
			if(!param.containsKey("COOPER_ID") || StringUtils.isEmpty(param.get("COOPER_ID"))) {
				 gr.setState(false);
				 gr.setMessage("缺少参数COOPER_ID");
				 return gr;
			  }
			if(!param.containsKey("COUPON_ID") || StringUtils.isEmpty(param.get("COUPON_ID"))) {
				 gr.setState(false);
				 gr.setMessage("缺少参数COUPON_ID");
				 return gr;
			  }
			sendMap.put("COOPER_ID", param.get("COOPER_ID"));
			sendMap.put("COUPON_ID", param.get("COUPON_ID"));
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/lyCountCouponRecord");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取优惠券流量成功");
                List<Map<String, Object>> data = (List<Map<String, Object>>)resPr.get("data");
                gr.setObj(data);
            }else{
            	gr.setState(true);
            	gr.setMessage("无数据");
            }
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 * 优惠券使用详情列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult couponsDetailList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if(!params.containsKey("COOPER_ID") || StringUtils.isEmpty(params.get("COOPER_ID"))) {
				 gr.setState(false);
				 gr.setMessage("缺少参数COOPER_ID");
				 return gr;
			  }
			if(!params.containsKey("COUPON_ID") || StringUtils.isEmpty(params.get("COUPON_ID"))) {
				 gr.setState(false);
				 gr.setMessage("缺少参数COUPON_ID");
				 return gr;
			  }
			Map<String,Object> sendMap=new HashMap<String,Object>();
			sendMap.put("INDEX",params.get("pageIndex"));
			sendMap.put("PAGE_SIZE",params.get("pageSize"));
			sendMap.put("COOPER_ID", params.get("COOPER_ID"));
			sendMap.put("COUPON_ID", params.get("COUPON_ID"));
			
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/lyCouponDetailList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取优惠券使用详情列表列表成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(true);
            	gr.setMessage("无数据");
            }
		} catch (Exception e) {			
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
     * 优惠券增加发行量
     * @param request
     * @return
     */
	@Transactional
	@SuppressWarnings("unchecked")
    public ProcessResult couponsAddCount(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  Map<String,Object> sendMap=new HashMap<String,Object>();
		  if(!param.containsKey("COOPER_ID") || StringUtils.isEmpty(param.get("COOPER_ID"))) {
			   pr.setState(false);
			   pr.setMessage("缺少参数COOPER_ID");
			   return pr;
		  }
		  if(!param.containsKey("COUPON_ID") || StringUtils.isEmpty(param.get("COUPON_ID"))) {
			pr.setState(false);
			pr.setMessage("缺少参数COUPON_ID");
			 return pr;
		   }
		  sendMap.put("COOPER_ID", param.get("COOPER_ID"));
		  sendMap.put("COUPON_ID", param.get("COUPON_ID"));
		  sendMap.put("INCREASE_TOTAL",param.get("increase_total"));
		  Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/couponBatchEdit");
		  if (Integer.parseInt(resPr.get("state").toString()) == 1) {
			  pr.setState(true);
			  pr.setMessage("增加发行量成功");
          }else{
        	  pr.setState(false);
          	  throw new RuntimeException(resPr.get("message").toString());
          }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e.getMessage());
    }
  	  return pr;
   }
	
	/**
	 * 下载二维码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult couponsDownloadCode(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(!params.containsKey("COOPER_ID") || StringUtils.isEmpty(params.get("COOPER_ID"))) {
				pr.setState(false);
				pr.setMessage("缺少参数COOPER_ID");
				return pr;
			  }
			if(!params.containsKey("COUPON_ID") || StringUtils.isEmpty(params.get("COUPON_ID"))) {
				pr.setState(false);
				pr.setMessage("缺少参数COUPON_ID");
				return pr;
			  }
			Map<String,Object> sendMap=new HashMap<String,Object>();
			sendMap.put("INDEX",0);
			sendMap.put("PAGE_SIZE",0);
			sendMap.put("COOPER_ID", params.get("COOPER_ID"));
			sendMap.put("COUPON_ID", params.get("COUPON_ID"));
			
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"marketing/Coupon/lyCouponDetailList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	pr.setState(true);
            	pr.setMessage("获取二维码列表成功");
                Map<String, Object> data = (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                List<String> codeList=new ArrayList<String >(); 
                for (Map<String, Object> datas : dataList){
                	codeList.add(datas.get("CODE").toString());
                }
                pr.setObj(codeList);
            }else{
            	pr.setState(true);
            	pr.setMessage("无数据");
            }
		} catch (Exception e) {			
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
}
