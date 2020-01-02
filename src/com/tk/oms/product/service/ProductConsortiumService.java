package com.tk.oms.product.service;

import java.io.IOException;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.product.dao.ProductConsortiumDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductConsortiumService")
public class ProductConsortiumService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	private ProductConsortiumDao productConsortiumDao;

	@SuppressWarnings("unchecked")
	public GridResult queryProductListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("参数缺失");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if (pageParamResult != null) {
				return pageParamResult;
			}
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			
			int count = productConsortiumDao.queryProductCount(paramMap);
			// 获取商品列表数据
			List<Map<String, Object>> productList = productConsortiumDao.queryProductListForPage(paramMap);

			if (productList != null && productList.size() > 0) {
				for(Map<String, Object> map : productList){
					if(!StringUtils.isEmpty(map.get("PRODUCT_TYPE"))){
						String product_type = map.get("PRODUCT_TYPE").toString();
						String[] str = product_type.split(",");
						String retStr = "";
						for(int i=0;i<str.length;i++){
							if(i == str.length-1){
								retStr = retStr + str[str.length-1-i];
							}else{
								retStr = retStr + str[str.length-1-i] + " → ";
							}
						}
						map.put("PRODUCT_TYPE", retStr);
					}
				}
				gr.setState(true);
				gr.setMessage("查询联营门店商品列表成功!");
				gr.setObj(productList);
				gr.setTotal(count);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}

		} catch (IOException e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e.getMessage());
            e.printStackTrace();
		}
		return gr;
	}
	/**
	 * 导入商品
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult insertProductConsortium(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("list");
	        if(list != null&&list.size()>0){
	        	for(Map<String, Object> map : list){
	        		//商品规格sku数量和联营商品规格sku数量比较 1.相同则更新,2.不相同则过滤后插入
	        		if(productConsortiumDao.queryProductSkuCount(map) == productConsortiumDao.queryProductConsortiumSkuCount(map)){
	        			productConsortiumDao.update(map);
	        		}else{
	        			map.put("public_user_id", paramMap.get("public_user_id"));
	        			productConsortiumDao.insert(map);
	        		}
		        }
	            pr.setState(true);
	            pr.setMessage("设置商品零售价成功！");
	        }else{
	        	pr.setState(false);
	            pr.setMessage("传入参数有误！");
	        }
	        
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("设置失败！");
            logger.error("设置商品零售价失败，"+e.getMessage());
            throw new RuntimeException("设置商品零售价异常："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 删除联营门店商品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult deleteProductConsortium(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
	        	pr.setState(false);
	        	pr.setMessage("缺少参数product_itemnumber");
	        	return pr;
	        }
	        
	        if(productConsortiumDao.queryProductStoreIsExists(paramMap) == 0){
	        	productConsortiumDao.deleted(paramMap);
	            pr.setState(true);
	            pr.setMessage("删除成功！");
	        }else{
	        	pr.setState(false);
	            pr.setMessage("商品被已被使用，无法删除！");
	        }
	        
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("删除失败！");
            logger.error("删除失败，"+e.getMessage());
            throw new RuntimeException("删除异常："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 联营门店商品详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductConsortiumDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("product_itemnumbers"))){
	        	pr.setState(false);
	        	pr.setMessage("缺少参数product_itemnumbers");
	        	return pr;
	        }
	        
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(productConsortiumDao.queryProductConsortiumDetail(paramMap));
	        
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败！");
            logger.error("查询失败，"+e.getMessage());
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
}
