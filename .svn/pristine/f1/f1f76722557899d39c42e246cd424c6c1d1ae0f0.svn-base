package com.tk.store.product.service;

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

import com.tk.oms.product.dao.ProductDao;
import com.tk.store.product.dao.RegionsProductDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : RegionsProductService.java
 * 区域商品管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月16日
 */
@Service("RegionsProductService")
public class RegionsProductService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private RegionsProductDao regionsProductDao;
	@Resource
	private ProductDao productDao;
	/**
	 * 查询商品列表
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
			//数量
			int count = regionsProductDao.queryProductCount(paramMap);
			//列表
			List<Map<String, Object>> list = regionsProductDao.queryProductListForPage(paramMap);
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
			if((!StringUtils.isEmpty(paramMap.get("user_ids")))&&paramMap.get("user_ids") instanceof String){
				String user_id_arr[]=paramMap.get("user_ids").toString().split(",");
				paramMap.put("user_ids",user_id_arr);
				paramMap.put("itemnumber_count",user_id_arr.length);
			}
			//数量
			int count = regionsProductDao.queryProductLibraryCount(paramMap);
			//列表
			List<Map<String, Object>> list = regionsProductDao.queryProductLibraryListForPage(paramMap);
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
	public ProcessResult insertRegionProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("dataList"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			List<Map<String, Object>> dataList = (List<Map<String, Object>>) paramMap.get("dataList");
			for(Map<String, Object> data : dataList){
				List<Map<String, Object>> userList = (List<Map<String, Object>>) data.get("userList");
				for(Map<String, Object> user : userList){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("product_itemnumber", data.get("product_itemnumber"));
					param.put("public_user_id", paramMap.get("public_user_id"));
					param.put("user_id", user.get("user_id"));
					//查询当前区域当前货号是否已经导入过
		        	Map<String, Object> info=regionsProductDao.queryProductRegionByUserAndItenmuber(param);
		        	if(info!=null){
		        		//先删除
		        		if(regionsProductDao.deleteRegionProductDetailByUserAndItemnumber(param)<=0 || regionsProductDao.deleteRegionProductByUserAndItemnumber(param)<=0){
		        			throw new RuntimeException("删除原有区域商品信息失败");
		        		}
		        	}
					if(regionsProductDao.insert(param)==0){
						throw new RuntimeException("导入商品数据失败");
					}
					List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("list");
					for(Map<String, Object> map : list){
						map.put("id", param.get("id"));
						map.put("public_user_id", paramMap.get("public_user_id"));
						//明细
			        	if(regionsProductDao.insertRegionProductDetail(map)==0){
							throw new RuntimeException("导入商品明细数据失败");
						}
					}
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
	        
	        List<Map<String, Object>> retMap = regionsProductDao.queryProductInfoList(paramMap);
	        for(Map<String, Object> ret : retMap){
		        List<Map<String, Object>> dataList = null;
		        if(StringUtils.isEmpty(paramMap.get("user_ids"))){
		        	Map<String, Object> param = new HashMap<String, Object>();
		        	param.put("product_itemnumber", ret.get("PRODUCT_ITEMNUMBER"));
		        	if(!StringUtils.isEmpty(paramMap.get("user_id"))){
		        		param.put("user_id", paramMap.get("user_id"));
		        	}
		        	//商品所属区域
			        dataList = regionsProductDao.queryUserList(param);
			        
			        for(Map<String, Object> data : dataList){
			        	Map<String, Object> map = new HashMap<String, Object>();
			        	map.put("product_itemnumber", ret.get("PRODUCT_ITEMNUMBER"));
			        	map.put("id", data.get("ID"));
			        	data.put("list", regionsProductDao.queryProductRetailDetail(map));
			        }
		        }else{
		        	if(paramMap.get("user_ids") instanceof String){
						paramMap.put("user_ids",(paramMap.get("user_ids")+"").split(","));
					}
		        	dataList = regionsProductDao.queryUserInfo(paramMap);
		        	for(Map<String, Object> data : dataList){
			        	Map<String, Object> map = new HashMap<String, Object>();
			        	map.put("product_itemnumber", ret.get("PRODUCT_ITEMNUMBER"));
			        	map.put("user_id", data.get("USER_ID"));
			        	//查询当前区域当前货号是否已经导入过
			        	Map<String, Object> info=regionsProductDao.queryProductRegionByUserAndItenmuber(map);
			        	if(info!=null){
			        		//查询已导入商品设置的信息
			        		data.put("list", regionsProductDao.queryProductRegionDetail(map));
			        	}else{
			        		data.put("list", regionsProductDao.queryProductRetailDetail(map));
			        	}
			        }
		        }
	        	ret.put("dataList", dataList);
	        }
            pr.setState(true);
            pr.setMessage("查询成功！");
            pr.setObj(retMap);
	        
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
	        
	        for(Map<String, Object> data : dataList){
	        	List<Map<String, Object>> userList = (List<Map<String, Object>>) data.get("userList"); //[{user_id:val,id:val,list[{货号,规格,零售价}]}]
	        	for(Map<String, Object> user : userList){
	        		if(StringUtils.isEmpty(user.get("id"))){
	        			pr.setState(false);
	        			pr.setMessage("缺少参数id");
	        			return pr;
	        		}else{
	        			List<Map<String, Object>> list = (List<Map<String, Object>>) user.get("list");
			        	for(Map<String, Object> map : list){
			        		map.put("id", user.get("id"));
			        		//商品规格sku数量和区域商品规格sku数量比较 1.相同则更新，2.不相同则过滤插入
			        		if(regionsProductDao.queryProductSkuCount(map) == regionsProductDao.queryRegionProductSkuCount(map)){
			        			regionsProductDao.update(map);
			        		}else{
			        			map.put("public_user_id", paramMap.get("public_user_id"));
			        			regionsProductDao.insertRegionProductDetailFilter(map);
			        		}
				        }
	        		}
	        	}
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
	 * 所属区域下拉框
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUserSelect(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }

	        List<Map<String, Object>> list = regionsProductDao.queryUserSelect(paramMap);
	        
	        pr.setState(true);
	        pr.setMessage("获取所属区域成功");
	        pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	/**
	 * 查询商品所属区域
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUserList(HttpServletRequest request) {
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

	        List<Map<String, Object>> list = regionsProductDao.queryUserList(paramMap);
	        
	        pr.setState(true);
	        pr.setMessage("获取商品所属区域成功");
	        pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	/**
	 * 编辑所属区域
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult editUser(HttpServletRequest request) {
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
	        if(StringUtils.isEmpty(paramMap.get("user_ids"))){
				pr.setState(false);
				pr.setMessage("缺少参数user_ids");
				return pr;
			}
	        List<String> user_ids = (List<String>) paramMap.get("user_ids");
	        if(paramMap.get("user_ids") instanceof String){
				paramMap.put("user_ids",(paramMap.get("user_ids")+"").split(","));
			}
	        
	        regionsProductDao.deleteRegionProductDetail(paramMap);
	        
	        regionsProductDao.deleteRegionProduct(paramMap);
	        	
	        for(String user_id : user_ids){
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("user_id", user_id);
	        	map.put("product_itemnumber", paramMap.get("product_itemnumber"));
	        	map.put("public_user_id", paramMap.get("public_user_id"));
	        	//区域是否关联该商品校验
	        	if(regionsProductDao.queryRegionProductIsExist(map) == 0){
	        		if(regionsProductDao.insert(map)==0){
						throw new RuntimeException("导入商品数据失败");
					}
	        		//新增明细 
		        	if(regionsProductDao.insertRegionProductDetail(map)==0){
						throw new RuntimeException("导入商品明细数据失败");
					}
	        	}
	        }
	        
	        pr.setState(true);
	        pr.setMessage("编辑所属区域成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("编辑所属区域异常："+e.getMessage());
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
	        
	        List<Map<String, Object>> list = regionsProductDao.queryProductDifferList(paramMap);
	        for(Map<String, Object> map : list){
	        	Map<String, Object> param = new HashMap<String, Object>();
	        	param.put("product_itemnumber", map.get("PRODUCT_ITEMNUMBER"));
	        	param.put("product_region_id", map.get("ID"));
	        	param.put("user_id", paramMap.get("in_user_id"));
	        	param.put("public_user_id", paramMap.get("public_user_id"));
	        	regionsProductDao.insert(param);
	        	//批量导入明细
	        	regionsProductDao.addRegionProductDetailBatch(param);
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
	 * 查询商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductList(HttpServletRequest request) {
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
			//数量
			int count = regionsProductDao.queryRegionProductCount(paramMap);
			//列表
			List<Map<String, Object>> list = regionsProductDao.queryRegionProductList(paramMap);
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

}
