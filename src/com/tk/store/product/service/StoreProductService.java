package com.tk.store.product.service;

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

import com.tk.oms.product.dao.ProductDao;
import com.tk.store.product.dao.StoreProductDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StoreProductService")
public class StoreProductService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	private StoreProductDao storeProductDao;
	@Resource
	private ProductDao productDao;
	
	@Value("${store_service_url}")
	private String store_service_url;// 联营门店服务地址
	@Value("${product_info_update}")
	private String product_info_update;// 商品是否销售(店铺)
	@Value("${ly_product_import}")
	private String ly_product_import;// 导入商品(店铺)
	@Value("${ly_sku_update}")
	private String ly_sku_update;// 设置零售价
	
	/**
	 * 商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductListForPage(HttpServletRequest request) {
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
			if(StringUtils.isEmpty("user_id")){
				gr.setState(false);
				gr.setMessage("缺少参数user_id");
				return gr;
			}
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			//数量
			int count = storeProductDao.queryProductCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeProductDao.queryProductListForPage(paramMap);
			if (list != null && list.size() > 0) {
				for(Map<String, Object> map : list){
					if(!StringUtils.isEmpty(map.get("PRODUCT_TYPE"))){
						String product_type = map.get("PRODUCT_TYPE").toString();
						String[] str = product_type.split(",");
						String retStr = "";
						for(int i=0;i<str.length;i++){
							if(i == str.length-1){
								retStr = retStr + str[str.length-1-i];
							}else{
								retStr = retStr + str[str.length-1-i] + ">";
							}
						}
						map.put("PRODUCT_TYPE", retStr);
					}
				}
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
	 * 是否销售
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult updateStoreProductState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数product_itemnumber");
				return pr;
			}
			
			storeProductDao.updateStoreProductState(paramMap);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ITEMNUMBER",paramMap.get("product_itemnumber"));
			map.put("STATE",paramMap.get("state"));
			map.put("AGENT_ID", storeProductDao.queryById(paramMap));
			
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(map,store_service_url+product_info_update);
			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
				throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
			}
			pr.setState(true);
			pr.setMessage("更新成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("更新失败"+e.getMessage());
			throw new RuntimeException("更新异常："+e.getMessage());
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
			
			if((!StringUtils.isEmpty(paramMap.get("product_itemnumbers")))&&paramMap.get("product_itemnumbers") instanceof String){
				paramMap.put("product_itemnumbers",(paramMap.get("product_itemnumbers")+"").split(","));
			}
			
			//数量
			int count = storeProductDao.queryProductLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeProductDao.queryProductLibraryListForPage(paramMap);
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
	 * 导入商品
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult insertStoreProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> retmap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("userList"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			List<String> agent_id = new ArrayList<String>();
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> userList = (List<Map<String, Object>>) paramMap.get("userList");
			List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
			for(Map<String, Object> user : userList){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("product_itemnumber", user.get("product_itemnumber"));
				param.put("public_user_id", paramMap.get("public_user_id"));
				param.put("user_id", user.get("user_id"));
				if(storeProductDao.insert(param)==0){
					throw new RuntimeException("导入商品数据失败");
				}
				user.put("id", param.get("id"));
				
				List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("list");
				for(Map<String, Object> map : list){
					map.put("id", user.get("id"));
					map.put("public_user_id", paramMap.get("public_user_id"));
					map.put("user_id", user.get("user_id"));
					productList.add(map);
				}
			}
			
			//明细
			if(storeProductDao.batchInsertStoreProductDetail(productList) == 0) {
				throw new RuntimeException("导入商品明细数据失败");
			}
			
			for(Map<String, Object> user : userList) {
				Map<String, Object> m = new HashMap<String, Object>();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("product_itemnumber", user.get("product_itemnumber"));
				param.put("public_user_id", paramMap.get("public_user_id"));
				param.put("user_id", user.get("user_id"));
				param.put("id", user.get("id"));
				
				m.put("ITEMNUMBER", user.get("product_itemnumber"));
				m.put("AGENT_ID", user.get("user_id"));
				agent_id.add(user.get("user_id").toString());
				Map<String, Object> info = storeProductDao.queryProductInfo(param);
				//获取商品大类
				info.put("BASIC_TYPE", storeProductDao.queryBasicType(info));
				m.put("info", info);
				//获取商品详情参数信息
				List<Map<String,Object>> params = storeProductDao.queryProductParamsList(param);
				m.put("param", params);
				//获取sku数据
				List<Map<String, Object>> sku = storeProductDao.querySkuColorList(param);
				for(Map<String, Object> color : sku){
					Map<String, Object> subMap = new HashMap<String, Object>();
					subMap.put("product_color", color.get("PRODUCT_COLOR"));
					subMap.put("product_itemnumber", user.get("product_itemnumber"));
					subMap.put("id", param.get("id"));
					List<Map<String, Object>> sub = storeProductDao.querySkuInfoList(subMap);
					color.put("sub", sub);
				}
				m.put("sku", sku);
				//获取商品图片数据
				List<String> img = storeProductDao.queryImgList(param);
				m.put("img", img);
				retList.add(m);
			}
			
			retmap.put("data", retList);
			retmap.put("AGENT_ID", agent_id);
			Map<String, Object> retMap= (Map<String, Object>) queryForPost(retmap,store_service_url+ly_product_import);
			if(retMap == null) {
				throw new RuntimeException("调用远程接口异常");
			}else {
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException(retMap.get("message")+"");
				}
			}
			pr.setState(true);
			pr.setMessage("导入商品成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("导入商品失败"+e.getMessage());
			throw new RuntimeException("导入商品异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 商品零售详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductRetailDetail(HttpServletRequest request) {
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
	        if(paramMap.get("product_itemnumber") instanceof String){
				paramMap.put("product_itemnumber",(paramMap.get("product_itemnumber")+"").split(","));
			}
	        List<Map<String, Object>> retList = storeProductDao.queryProductInfoList(paramMap);
	        List<Map<String, Object>> userList = null;
	        List<Map<String, Object>> productList = null;
	        if(StringUtils.isEmpty(paramMap.get("user_ids"))){
	        	paramMap.put("type", 1);
	        	userList=storeProductDao.queryUserList(paramMap);
	        	paramMap.put("userList", userList);
	        	productList=storeProductDao.queryProductRetailDetail(paramMap);
	        	for(Map<String, Object> retMap : retList) {
	        		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		        	for(Map<String, Object> userMap : userList) {
		        		Map<String, Object> map = new HashMap<String, Object>();
		        		map.putAll(userMap);
		        		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		        		for(Map<String, Object> productMap : productList) {
		        			if(userMap.get("ID").equals(productMap.get("PRODUCT_STORE_ID"))) {
		        				list.add(productMap);
		        			}
		        		}
		        		map.put("list", list);
		        		dataList.add(map);
		        	}
		        	retMap.put("dataList", dataList);
		        }
	        }else{
	        	if(paramMap.get("user_ids") instanceof String){
					paramMap.put("user_ids",(paramMap.get("user_ids")+"").split(","));
				}
	        	userList = storeProductDao.queryUserInfo(paramMap);
	        	productList = storeProductDao.queryProductRetailDetail(paramMap);
	        	for(Map<String, Object> retMap : retList) {
	        		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		        	for(Map<String, Object> userMap : userList) {
		        		Map<String, Object> map = new HashMap<String, Object>();
		        		map.putAll(userMap);
		        		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		        		for(Map<String, Object> productMap : productList) {
		        			if(StringUtils.isEmpty(productMap.get("USER_ID"))) {
		        				if(retMap.get("PRODUCT_ITEMNUMBER").equals(productMap.get("PRODUCT_ITEMNUMBER"))) {
			        				list.add(productMap);
			        			}
		        			}else {
		        				if(userMap.get("USER_ID").equals(productMap.get("USER_ID"))&&retMap.get("PRODUCT_ITEMNUMBER").equals(productMap.get("PRODUCT_ITEMNUMBER"))) {
			        				list.add(productMap);
			        			}
		        			}
		        		}
		        		map.put("list", list);
		        		dataList.add(map);
		        	}
		        	retMap.put("dataList", dataList);
		        }
	        }
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(retList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败！");
            logger.error("查询失败，"+e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 设置零售价
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult updateRetailPrice(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> retlist = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> intlist = new ArrayList<Map<String, Object>>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("dataList"))){
	        	pr.setState(false);
	        	pr.setMessage("缺少参数");
	        	return pr;
	        }
	        List<Map<String, Object>> dataList = (List<Map<String, Object>>) paramMap.get("dataList");
	        List<String> in_agent_id = new ArrayList<String>();
	        List<String> u_agent_id = new ArrayList<String>();
	        //1.循环取货号
	        for(Map<String, Object> data : dataList){
	        	List<Map<String, Object>> userList = (List<Map<String, Object>>) data.get("userList"); //[{user_id:value,id:value}]
	        	//2.循环取商家id和 商品关联商家ID
	        	for(Map<String, Object> user : userList){
	        		Map<String, Object> m = new HashMap<String, Object>();
	        		//3.是否存在 商品关联商家ID
	        		if(StringUtils.isEmpty(user.get("id"))){
	        			//3.1 做新增操作
						param.put("product_itemnumber", data.get("product_itemnumber"));
						param.put("public_user_id", paramMap.get("public_user_id"));
						param.put("user_id", user.get("user_id"));
						in_agent_id.add(user.get("user_id").toString());
						if(storeProductDao.insert(param)==0){
							throw new RuntimeException("导入商品数据失败");
						}
						List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("list");
						for(Map<String, Object> map : list){
							map.put("id", param.get("id"));
							map.put("public_user_id", paramMap.get("public_user_id"));
							map.put("user_id", user.get("user_id"));
							//明细
				        	if(storeProductDao.insertStoreProductDetail(map)==0){
								throw new RuntimeException("导入商品明细数据失败");
							}
						}
						m.put("ITEMNUMBER", data.get("product_itemnumber"));
						m.put("AGENT_ID", user.get("user_id"));
						Map<String, Object> info = storeProductDao.queryProductInfo(param);
						//获取商品大类
						info.put("BASIC_TYPE", storeProductDao.queryBasicType(info));
						m.put("info", info);
						//获取商品详情参数信息
						List<Map<String,Object>> params = storeProductDao.queryProductParamsList(param);
						m.put("param", params);
						//获取sku数据
						List<Map<String, Object>> sku = storeProductDao.querySkuColorList(param);
						for(Map<String, Object> color : sku){
							Map<String, Object> subMap = new HashMap<String, Object>();
							subMap.put("product_color", color.get("PRODUCT_COLOR"));
							subMap.put("product_itemnumber", data.get("product_itemnumber"));
							subMap.put("id", param.get("id"));
							List<Map<String, Object>> sub = storeProductDao.querySkuInfoList(subMap);
							color.put("sub", sub);
						}
						m.put("sku", sku);
						//获取商品图片数据
						List<String> img = storeProductDao.queryImgList(param);
						m.put("img", img);
						intlist.add(m);
	        		}else{
	        			int index = 0;
	    	        	List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("list");
	    		        	for(Map<String, Object> map : list){
	    		        		map.put("id", user.get("id"));
	    		        		//商品规格sku数量和商家商品规格sku数量比较 1.相同则更新，2.不相同则过滤插入
	    		        		if(storeProductDao.queryProductSkuCount(map) == storeProductDao.queryStoreProductSkuCount(map)){
	    		        			storeProductDao.update(map);
	    		        		}else{
	    		        			map.put("public_user_id", paramMap.get("public_user_id"));
	    		        			storeProductDao.insertStoreProductDetailFilter(map);
	    		        			index=index+1;
	    		        		}
	    			        }
	    		        	m.put("AGENT_ID", user.get("user_id"));
    		        		m.put("ITEMNUMBER", data.get("product_itemnumber"));
    		        		u_agent_id.add(user.get("user_id").toString());
    		        		if(index>0){
    			        		//获取sku数据
    			    			List<Map<String, Object>> sku = storeProductDao.querySkuColorList(data);
    			    			for(Map<String, Object> color : sku){
    			    				Map<String, Object> subMap = new HashMap<String, Object>();
    			    				subMap.put("product_color", color.get("PRODUCT_COLOR"));
    			    				subMap.put("product_itemnumber", data.get("product_itemnumber"));
    			    				subMap.put("id", user.get("id"));
    			    				List<Map<String, Object>> sub = storeProductDao.querySkuInfoList(subMap);
    			    				color.put("sub", sub);
    			    			}
    			    			m.put("EXTRA", sku);
    			        	}else{
    			        		m.put("SKU", storeProductDao.querySkuPrice(user));
    			        	}
    		        		retlist.add(m);
	        			}
	        		}
	        	}
	        	//存在新增数据,调用远程接口
	        	if(intlist.size() > 0){
	        		Map<String, Object> rm = new HashMap<String, Object>();
	        		rm.put("data", intlist);
	        		rm.put("AGENT_ID", in_agent_id);
	        		Map<String, Object> retMap= (Map<String, Object>) queryForPost(rm,store_service_url+ly_product_import);
	    			if (Integer.parseInt(retMap.get("state").toString()) != 1) {
	    				throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
	    			}
	        	}
	        	
	        	Map<String, Object> retmap = new HashMap<String, Object>();
	        	retmap.put("data", retlist);
	        	retmap.put("AGENT_ID", u_agent_id);
        		Map<String, Object> retMap= (Map<String, Object>) queryForPost(retmap,store_service_url+ly_sku_update);
        		if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
				}
	            pr.setState(true);
	            pr.setMessage("设置商品零售价成功！");
	        
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("设置失败！");
            logger.error("设置商品零售价失败，"+e.getMessage());
            throw new RuntimeException("设置商品零售价异常："+e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 查询商品所属商家
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUserListByItemnumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        
	        if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数itemnumber");
				return pr;
			}

	        Map<String, Object> retmap = storeProductDao.queryProductDetail(paramMap);
	        
	        if(!StringUtils.isEmpty(retmap.get("PRODUCT_TYPE"))){
				String product_type = retmap.get("PRODUCT_TYPE").toString();
				String[] str = product_type.split(",");
				String retStr = "";
				for(int i=0;i<str.length;i++){
					if(i == str.length-1){
						retStr = retStr + str[str.length-1-i];
					}else{
						retStr = retStr + str[str.length-1-i] + " > ";
					}
				}
				retmap.put("PRODUCT_TYPE", retStr);
			}
	        
	        retmap.put("list", storeProductDao.queryUserListByItemnumber(paramMap));
	        pr.setState(true);
	        pr.setMessage("获取商品所属商家成功");
	        pr.setObj(retmap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	/**
	 * 编辑所属商家
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult editUser(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> retmap = new HashMap<String, Object>();
		
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("dataList"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
	        List<String> agent_id = new ArrayList<String>();
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) paramMap.get("dataList");
			for(Map<String, Object> data : dataList){
				List<Map<String, Object>> userList = (List<Map<String, Object>>) data.get("userList");
				for(Map<String, Object> user : userList){
		        	Map<String, Object> m = new HashMap<String, Object>();
		        	Map<String, Object> param = new HashMap<String, Object>();
		        	param.put("user_id", user.get("user_id"));
		        	param.put("product_itemnumber", data.get("product_itemnumber"));
		        	param.put("public_user_id", paramMap.get("public_user_id"));
		        	if(storeProductDao.queryStoreProductIsExist(param) == 0){
		        		if(storeProductDao.insert(param)==0){
							throw new RuntimeException("导入商品数据失败");
						}
		        		//明细
			        	if(storeProductDao.insertStoreProductDetail(param)==0){
							throw new RuntimeException("导入商品明细数据失败");
						}
			        	
			        	m.put("ITEMNUMBER", data.get("product_itemnumber"));
						m.put("AGENT_ID", user.get("user_id"));
						agent_id.add(user.get("user_id").toString());
						Map<String, Object> info = storeProductDao.queryProductInfo(param);
						//获取商品大类
						info.put("BASIC_TYPE", storeProductDao.queryBasicType(info));
						m.put("info", info);
						//获取商品详情参数信息
						List<Map<String,Object>> params = storeProductDao.queryProductParamsList(param);
						m.put("param", params);
						//获取sku数据
						List<Map<String, Object>> sku = storeProductDao.querySkuColorList(param);
						for(Map<String, Object> color : sku){
							Map<String, Object> subMap = new HashMap<String, Object>();
							subMap.put("product_color", color.get("PRODUCT_COLOR"));
							subMap.put("product_itemnumber", data.get("product_itemnumber"));
							subMap.put("id", param.get("id"));
							List<Map<String, Object>> sub = storeProductDao.querySkuInfoList(subMap);
							color.put("sub", sub);
						}
						m.put("sku", sku);
						//获取商品图片数据
						List<String> img = storeProductDao.queryImgList(param);
						m.put("img", img);
						retList.add(m);
		        	}
				}
	        }
	        if(retList.size() > 0){
	        	retmap.put("data", retList);
	        	retmap.put("AGENT_ID", agent_id);
	        	Map<String, Object> retMap= (Map<String, Object>) queryForPost(retmap,store_service_url+ly_product_import);
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
				}
	        }
	        
	        pr.setState(true);
	        pr.setMessage("编辑所属商家成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("编辑所属商家异常："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 批量导入
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult addBatch(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> retmap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        
	        if(StringUtils.isEmpty(paramMap.get("in_user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数in_user_id");
				return pr;
			}
	        
	        if(StringUtils.isEmpty(paramMap.get("out_user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数out_user_id");
				return pr;
			}
	        List<String> agent_id = new ArrayList<String>();
	        agent_id.add(paramMap.get("in_user_id").toString());
	        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
	        List<Map<String, Object>> list = storeProductDao.queryProductDifferList(paramMap);
	        if(list.size() == 0) {
	        	pr.setState(false);
		        pr.setMessage("没有需要导入的商品！");
		        return pr;
	        }
	        //新增商家商品临时数据
	        storeProductDao.insertTmpStoreProductBatch(list);
	        //批量新增商家商品
	        storeProductDao.insertStoreProductBatch(paramMap);
	        //批量新增商品明细
	        storeProductDao.insertStoreProductDetailBatch(paramMap);
	        //批量获取商品信息
	        List<Map<String,Object>> infoList=storeProductDao.getProductInfo(paramMap);
	        Map<String, Object> infoMap = list2Map(infoList,"ITEMNUMBER");
	        //批量获取商品详情参数信息
			List<Map<String,Object>> paramsList = storeProductDao.getProductParamsList(paramMap);
			Map<String, Object> params = new HashMap<String, Object>();
			for(Map<String, Object> p : paramsList) {
				if(!StringUtils.isEmpty(params.get(p.get("PRODUCT_ITEMNUMBER")))) {
					List<Map<String,Object>> dataList = (List<Map<String, Object>>) params.get(p.get("PRODUCT_ITEMNUMBER"));
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("PARAM_NAME",p.get("PARAM_NAME"));
					if(!StringUtils.isEmpty(p.get("PARAM_VALUE"))){data.put("PARAM_VALUE",p.get("PARAM_VALUE"));}
					dataList.add(data);
					params.put(p.get("PRODUCT_ITEMNUMBER").toString(), dataList);
				}else {
					List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("PARAM_NAME",p.get("PARAM_NAME"));
					if(!StringUtils.isEmpty(p.get("PARAM_VALUE"))){data.put("PARAM_VALUE",p.get("PARAM_VALUE"));}
					dataList.add(data);
					params.put(p.get("PRODUCT_ITEMNUMBER").toString(), dataList);
				}
			}
			//批量获取商品颜色数据
			List<Map<String, Object>> colorList = storeProductDao.getSkuColorList(paramMap);
			//批量获取sku基本信息
			List<Map<String, Object>> skuInfoList = storeProductDao.getSkuInfoList(paramMap);
			Map<String, Object> sku = new HashMap<String, Object>();
			for(Map<String, Object> color : colorList) {
				for(Map<String, Object> skuInfo : skuInfoList) {
					if(color.get("PRODUCT_ITEMNUMBER").equals(skuInfo.get("PRODUCT_ITEMNUMBER")) && color.get("PRODUCT_COLOR").equals(skuInfo.get("PRODUCT_COLOR"))) {
						if(!StringUtils.isEmpty(color.get("sub"))) {
							List<Map<String,Object>> dataList = (List<Map<String, Object>>) color.get("sub");
							Map<String,Object> data = new HashMap<String,Object>();
							data.put("SKU", skuInfo.get("SKU"));
							data.put("PRODUCT_SIZE", skuInfo.get("PRODUCT_SIZE"));
							data.put("STATUS", skuInfo.get("STATUS"));
							data.put("PRICE_SALE", skuInfo.get("PRICE_SALE"));
							data.put("PRICE_TAG", skuInfo.get("PRICE_TAG"));
							data.put("PRODUCT_WEIGHT", skuInfo.get("PRODUCT_WEIGHT"));
							data.put("GB_CODE", skuInfo.get("GB_CODE"));
							data.put("PRODUCT_INLONG", skuInfo.get("PRODUCT_INLONG"));
							dataList.add(data);
							color.put("sub", dataList);
						}else {
							List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
							Map<String,Object> data = new HashMap<String,Object>();
							data.put("SKU", skuInfo.get("SKU"));
							data.put("PRODUCT_SIZE", skuInfo.get("PRODUCT_SIZE"));
							data.put("STATUS", skuInfo.get("STATUS"));
							data.put("PRICE_SALE", skuInfo.get("PRICE_SALE"));
							data.put("PRICE_TAG", skuInfo.get("PRICE_TAG"));
							data.put("PRODUCT_WEIGHT", skuInfo.get("PRODUCT_WEIGHT"));
							data.put("GB_CODE", skuInfo.get("GB_CODE"));
							data.put("PRODUCT_INLONG", skuInfo.get("PRODUCT_INLONG"));
							dataList.add(data);
							color.put("sub", dataList);
						}
					}
				}
			}
			for(Map<String, Object> color : colorList) {
				if(!StringUtils.isEmpty(sku.get(color.get("PRODUCT_ITEMNUMBER")))) {
					List<Map<String,Object>> dataList = (List<Map<String, Object>>) sku.get(color.get("PRODUCT_ITEMNUMBER"));
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("PRODUCT_COLOR",color.get("PRODUCT_COLOR"));
					if(!StringUtils.isEmpty(color.get("PRODUCT_COLOR_IMGURL"))){data.put("PRODUCT_COLOR_IMGURL",color.get("PRODUCT_COLOR_IMGURL"));}
					data.put("sub", color.get("sub"));
					dataList.add(data);
					sku.put(color.get("PRODUCT_ITEMNUMBER").toString(), dataList);
				}else {
					List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("PRODUCT_COLOR",color.get("PRODUCT_COLOR"));
					if(!StringUtils.isEmpty(color.get("PRODUCT_COLOR_IMGURL"))){data.put("PRODUCT_COLOR_IMGURL",color.get("PRODUCT_COLOR_IMGURL"));}
					data.put("sub", color.get("sub"));
					dataList.add(data);
					sku.put(color.get("PRODUCT_ITEMNUMBER").toString(), dataList);
				}
			}
			//批量获取商品图片数据
			List<Map<String, Object>> imgList= storeProductDao.getImgList(paramMap);
			Map<String, Object> img = new HashMap<String, Object>();
			for(Map<String, Object> imgs : imgList) {
				if(!StringUtils.isEmpty(img.get(imgs.get("PRODUCT_ITEMNUMBER")))) {
					List<String> dataList = (List<String>) img.get(imgs.get("PRODUCT_ITEMNUMBER"));
					if(!StringUtils.isEmpty(imgs.get("IMAGE_URL"))){dataList.add(imgs.get("IMAGE_URL").toString());}
					img.put(imgs.get("PRODUCT_ITEMNUMBER").toString(), dataList);
				}else {
					List<String> dataList = new ArrayList<String>();
					if(!StringUtils.isEmpty(imgs.get("IMAGE_URL"))){dataList.add(imgs.get("IMAGE_URL").toString());}
					img.put(imgs.get("PRODUCT_ITEMNUMBER").toString(), dataList);
				}
			}
			
			
			List<Map<String,Object>> dataList_temp = null;
			Map<String,Object> data_temp = null;
			
	        for(Map<String, Object> map : list){
	        	Map<String, Object> m = new HashMap<String, Object>();
	        	
	        	m.put("ITEMNUMBER", map.get("PRODUCT_ITEMNUMBER"));
				m.put("AGENT_ID", paramMap.get("in_user_id"));
				//获取商品信息
				m.put("info", infoMap.get(map.get("PRODUCT_ITEMNUMBER")));
				//获取商品详情参数信息
				if(!params.containsKey(map.get("PRODUCT_ITEMNUMBER"))){
					dataList_temp = new ArrayList<Map<String,Object>>();
					data_temp = new HashMap<String,Object>();
					data_temp.put("PARAM_NAME","系统货号");
					data_temp.put("PARAM_VALUE",map.get("PRODUCT_ITEMNUMBER"));
					dataList_temp.add(data_temp);
					m.put("param", dataList_temp);
				}else{
					m.put("param", params.get(map.get("PRODUCT_ITEMNUMBER")));
				}
				//获取sku数据
				m.put("sku", sku.get(map.get("PRODUCT_ITEMNUMBER")));
				//获取商品图片数据
				m.put("img", img.get(map.get("PRODUCT_ITEMNUMBER")));
				retList.add(m);
	        }
	        
	        if(retList.size() > 0){
	        	int size = retList.size();
	        	int toIndex=100;
		        for(int i = 0;i<retList.size();i+=100){
		            if(i+100>size){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
		                toIndex=size-i;
		            }
		        List<Map<String, Object>> newList = retList.subList(i,i+toIndex);
        		retmap.put("data", newList);
		        retmap.put("AGENT_ID", agent_id);
	        	Map<String, Object> retMap= (Map<String, Object>) queryForPost(retmap,store_service_url+ly_product_import);
				if (Integer.parseInt(retMap.get("state").toString()) != 1) {
					throw new RuntimeException("调用远程接口异常:"+retMap.get("message"));
				}
		      }
	        }
			
	        pr.setState(true);
	        pr.setMessage("批量导入成功");
	        
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("批量导入异常："+e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 将list转换成map
	 * @param list
	 * @param mapKey
	 * @param valueKey
	 * @return
	 */
	private Map<String,Object> list2Map(List<Map<String, Object>> list ,String mapKey){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		int size = list.size();
		String key ="";
		for(int i=0;i<size;i++)
		{
			key = list.get(i).get(mapKey).toString();
			returnMap.put(key, list.get(i));
		}
		return returnMap;
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
