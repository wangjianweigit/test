package com.tk.store.marking.service;

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

import com.tk.oms.basicinfo.dao.StoreInfoDao;
import com.tk.store.marking.dao.StoreMarketActivityDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StoreMarketActivityService")
public class StoreMarketActivityService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Value("${store_service_url}")
    private String store_service_url;
	
	@Resource
	private StoreMarketActivityDao storeMarketActivityDao;
	 /**
	 * 店铺营销活动列表
	 * @param request
	 * @return
	 */
	public GridResult storeMarketActivityList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if((!StringUtils.isEmpty(params.get("pause_state")))&&params.get("pause_state") instanceof String){
				params.put("pause_state",(params.get("pause_state")+"").split(","));
			}
			if((!StringUtils.isEmpty(params.get("start_state")))&&params.get("start_state") instanceof String){
				params.put("start_state",(params.get("start_state")+"").split(","));
			}
			if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
				params.put("state",(params.get("state")+"").split(","));
			}
			int storeCount=storeMarketActivityDao.queryStoreMarketActivityCount(params);
			List<Map<String,Object>> list = storeMarketActivityDao.queryStoreMarketActivityList(params);
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
				gr.setTotal(storeCount);
			} else {
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
     * 店铺营销活动新增
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult add(HttpServletRequest request) throws Exception {
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
		  param.put("discount", Double.parseDouble(param.get("discount").toString())/100);
		  if(storeMarketActivityDao.insertStoreMarketActivity(param)<0){
			  throw new RuntimeException("新增失败");
		  }
		//给商品封装活动ID
		 for(Map<String,Object> product_temp:product_list){
			 product_temp.put("activity_id", param.get("id"));
		  }
		 if(storeMarketActivityDao.insertStoreMarketActivityProduct(product_list)>0){
			 pr.setState(true);
			 pr.setMessage("新增成功");
		 }else{
			 pr.setState(false);
			 throw new RuntimeException("新增失败");
		 }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
    
    /**
	 * 商品库
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
			if(StringUtils.isEmpty(paramMap.get("user_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数user_id");
				return gr;
			}
			List<Map<String,Object>> itemnumber_list = (List<Map<String,Object>>)paramMap.get("itemnumber_list");
			paramMap.put("itemnumber_list", itemnumber_list);
			//数量
			int count = storeMarketActivityDao.queryProductLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeMarketActivityDao.queryProductLibraryListForPage(paramMap);
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
	 * 店铺营销活动详情
	 * @param request
	 * @return
	 */
	public GridResult storeMarketActivityDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("activity_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
                return gr;
            }
			Map<String,Object> detail = storeMarketActivityDao.queryStoreMarketActivityDetail(params);
			if (detail != null ) {
				List<Map<String,Object>> activity_product=storeMarketActivityDao.queryStoreMarketActivityProduct(params);
				detail.put("activity_product", activity_product);
				gr.setMessage("查询成功!");
				gr.setObj(detail);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
     * 店铺营销活动编辑
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult storeMarketActivityEdit(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(StringUtils.isEmpty(param.get("activity_id"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数");
              return pr;
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
		  param.put("DISCOUNT", Double.parseDouble(param.get("DISCOUNT").toString())/100);
		  param.put("STATE", 1);
		  if(storeMarketActivityDao.updateStoreMarketActivity(param)<0){
			  throw new RuntimeException("编辑失败");
		  }
		//给商品封装活动ID
		 for(Map<String,Object> product_temp:product_list){
			 product_temp.put("activity_id", param.get("activity_id"));
		  }
		 //先删除原活动商品关系信息
		 if(storeMarketActivityDao.deleteStoreMarketActivityProduct(param)<0){
			  throw new RuntimeException("删除活动商品关系信息失败");
		  }
		 if(storeMarketActivityDao.insertStoreMarketActivityProduct(product_list)>0){
			 pr.setState(true);
			 pr.setMessage("编辑成功");
		 }else{
			 pr.setState(false);
			 pr.setMessage("编辑失败");
		 }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
    
    /**
     * 店铺营销活动审批
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult storeMarketActivityApproval(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(StringUtils.isEmpty(param.get("activity_id"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数activity_id");
              return pr;
          }
		  if(StringUtils.isEmpty(param.get("activity_state"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数activity_state");
              return pr;
          }
		  Map<String,Object> activity_detail = storeMarketActivityDao.queryStoreMarketActivityDetail(param);
		  if(activity_detail==null){
			  pr.setState(false);
			  pr.setMessage("当前活动不存在");
              return pr;
		  }
		  if(Integer.parseInt(activity_detail.get("STATE").toString())==2){
			  pr.setState(false);
			  pr.setMessage("当前活动已经审批，请勿重复操作");
              return pr;
		  }
		  //活动审批操作
		  int count=storeMarketActivityDao.queryStoreMarketActivityApproval(param);
		  if(count<0){
			  throw new RuntimeException("审批失败");
		  }
		  if(Integer.parseInt(param.get("activity_state").toString())==2){
			  //审批通过推送活动数据信息
			  List<String> itemnumber_list=storeMarketActivityDao.queryStoreMarketActivityItemnumber(param);
			  Map<String,Object> sendParams=new HashMap<String,Object>();
			  sendParams.put("AGENT_ID", activity_detail.get("USER_ID"));
			  sendParams.put("TIME_START", activity_detail.get("BEGIN_DATE"));
			  sendParams.put("TIME_END", activity_detail.get("END_DATE"));
			  sendParams.put("TYPE", activity_detail.get("TYPE"));
			  sendParams.put("ITEM_TYPE", 1);
			  sendParams.put("ITEMNUMBER", itemnumber_list);
			  sendParams.put("DISCOUNT", Float.parseFloat(activity_detail.get("DISCOUNT").toString())*100);
			  sendParams.put("NAME", activity_detail.get("ACTIVITY_NAME"));
			  sendParams.put("STORE_TYPE", 1);
			  sendParams.put("STORE_ARR", Arrays.asList(activity_detail.get("STORE_ID").toString().split(",")));
			  Map<String, Object> resPr=(Map<String, Object>)this.queryForPost(sendParams,store_service_url+"marketing/Discount/discountInsert");
			  if (Integer.parseInt(resPr.get("state").toString()) == 1) {
	        	  Map<String, Object> resDate=(Map<String, Object>)resPr.get("data");
	        	  resDate.put("activity_id", param.get("activity_id"));
	        	  resDate.put("AGENT_ACTIVITY_ID", resDate.get("DISCOUNT_ID"));
	        	  //更新经销商活动ID
	        	  if(storeMarketActivityDao.updateStoreMarketActivity(resDate)<0){
	    			  throw new RuntimeException("编辑失败");
	    		  }
	          }else{
	        	  throw new RuntimeException("调用远程接口异常");
	          }
		  }
		  pr.setState(true);
		  pr.setMessage("审批成功");
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
   public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
       String params = "";
       if(obj != null){
           Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
           params = Jackson.writeObject2Json(packet);//对象转json、字符串
       }
       //发送至服务端
       String json = HttpClientUtil.post(url, params,30000);
       return Transform.GetPacketJzb(json,Map.class);
   }
   
   /**
    * 店铺营销活动删除
    * @param request
    * @return
    */
   @Transactional
   public ProcessResult storeMarketActivityDelete(HttpServletRequest request) throws Exception {
 	  ProcessResult pr = new ProcessResult();
 	  Map<String,Object> param = new HashMap<String,Object>();
 	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(StringUtils.isEmpty(param.get("activity_id"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数activity_id");
             return pr;
         }
		  Map<String,Object> activity_detail = storeMarketActivityDao.queryStoreMarketActivityDetail(param);
		  if(activity_detail==null){
			  pr.setState(false);
			  pr.setMessage("当前活动不存在");
             return pr;
		  }
		  //活动删除操作
		  if(storeMarketActivityDao.deleteStoreMarketActivity(param)>0 && storeMarketActivityDao.deleteStoreMarketActivityProduct(param)>0){
			  pr.setState(true);
			  pr.setMessage("删除成功");
		  }else{
			  pr.setState(true);
			  pr.setMessage("删除失败");
		  }
		  
 	} catch (Exception e) {
       pr.setState(false);
       pr.setMessage(e.getMessage());
       throw new RuntimeException(e);
   }
 	  return pr;
  }
   
   /**
    * 店铺营销活动暂停
    * @param request
    * @return
    */
   @Transactional
   public ProcessResult storeMarketActivityPause(HttpServletRequest request) throws Exception {
 	  ProcessResult pr = new ProcessResult();
 	  Map<String,Object> param = new HashMap<String,Object>();
 	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(StringUtils.isEmpty(param.get("activity_id"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数activity_id");
             return pr;
         }
		 if(StringUtils.isEmpty(param.get("PAUSE_STATE"))){
			 pr.setState(false);
			 pr.setMessage("缺少参数PAUSE_STATE");
             return pr;
         }
		 if(Integer.parseInt(param.get("PAUSE_STATE").toString())!=1 && Integer.parseInt(param.get("PAUSE_STATE").toString())!=2){
			 pr.setState(false);
			 pr.setMessage("当前活动状态异常");
             return pr;
		 }
		 Map<String,Object> storeMarketActivityDetail=storeMarketActivityDao.queryStoreMarketActivityDetail(param);
		 if(storeMarketActivityDetail==null){
			 pr.setState(false);
			 pr.setMessage("未找到当前店铺活动");
             return pr;
		 }
		 int count = storeMarketActivityDao.updateStoreMarketActivity(param);
		 if(count<0){
			 throw new RuntimeException("状态修改失败");
		 }
		 Map<String,Object> sendParams=new HashMap<String,Object>();
		 int state=0;
		 if(Integer.parseInt(param.get("PAUSE_STATE").toString())==1){
			 state=1;
		 }
		 sendParams.put("AGENT_ID", storeMarketActivityDetail.get("USER_ID"));
		 sendParams.put("DISCOUNT_ID", storeMarketActivityDetail.get("AGENT_ACTIVITY_ID"));
		 sendParams.put("STATUS", state);
		 Map<String, Object> resPr=(Map<String, Object>)this.queryForPost(sendParams,store_service_url+"marketing/Discount/discountEdit");
         if (Integer.parseInt(resPr.get("state").toString()) == 1) {
        	pr.setState(true);
    		pr.setMessage("修改活动状态成功");
         }else{
        	throw new RuntimeException("调用远程接口异常");
         }
 	} catch (Exception e) {
       pr.setState(false);
       pr.setMessage(e.getMessage());
       throw new RuntimeException(e);
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
  			List<Map<String, Object>> list = storeMarketActivityDao.queryUserStoreList(paramMap);
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
