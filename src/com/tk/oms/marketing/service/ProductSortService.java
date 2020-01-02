package com.tk.oms.marketing.service;

import java.util.ArrayList;
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

import com.tk.oms.marketing.dao.ProductSortDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;
import com.tk.sys.util.FileUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductSortService
 * 商品搜索排序策略
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-17
 */
@Service("ProductSortService")
public class ProductSortService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ProductSortDao productSortDao;
	/**
	 * 更新规则权重百分比
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editWeightPercent(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
			List<String> codes = (List<String>) paramMap.get("code");
			List<String> weight_percents = (List<String>) paramMap.get("weight_percent");
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            float allPercent = 0;
            for(int i=0; i<weight_percents.size();i++) {
            	float weight_percent = Float.parseFloat(weight_percents.get(i));
                allPercent += weight_percent;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("weight_percent",weight_percents.get(i));
                map.put("code",codes.get(i));
                dataList.add(map);
            }
            if(allPercent != 100){
                pr.setState(false);
                pr.setMessage("权重百分比总会必须为100%");
                return pr;
            }
            //更新
            if(productSortDao.updateWeightPercent(dataList) > 0){
                pr.setState(true);
                pr.setMessage("更新权重比例成功");
            }else{
                pr.setState(true);
                pr.setMessage("更新权重比例失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
	}
	/**
	 * 查询规则列表
	 * @param request
	 * @return
	 */
	public ProcessResult querySortRole(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	
            List<Map<String, Object>> dataList = productSortDao.querySortRole();

            pr.setState(true);
            pr.setMessage("查询成功");
            pr.setObj(dataList);

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	/**
	 * 查询人工加权商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			
			//查询人工加权商品总数
			int total = productSortDao.queryProductCount(paramMap);
			//查询人工加权商品列表
			List<Map<String, Object>> dataList = productSortDao.queryProductList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return gr;
	}
	/**
	 * 商品人工加权
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editProductWeighting(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
                pr.setState(false);
                pr.setMessage("缺少参数,货号product_itemnumber");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("temp_weighting"))) {
                pr.setState(false);
                pr.setMessage("缺少参数,权重值temp_weighting");
                return pr;
            }

            //1.根据站点ID和货号查询站点商品配置信息
            Map<String, Object>  productMap = productSortDao.querySiteProduct(paramMap);
            int count = 0;
            if(productMap == null) {
                count = productSortDao.insertProductWeighting(paramMap);
            }else{
                count = productSortDao.updateProductWeighting(paramMap);
            }

            if(count > 0) {
                pr.setState(true);
                pr.setMessage("人工加权成功");
            }else{
                pr.setState(false);
                pr.setMessage("人工加权失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("人工加权失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
	}
	/**
	 * 发布人工加权
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult releaseProductWeighting(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }

            if(productSortDao.releaseProductWeighting(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("发布成功");
            }else{
                pr.setState(false);
                pr.setMessage("发布失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("发布失败："+e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 上新商品查询上新商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult newProductList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);
	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			//上新商品查询上新商品列表
			List<Map<String, Object>> new_product_list = productSortDao.queryNewProductList(paramMap);
			int count=productSortDao.queryNewProductCount(paramMap);
//			for(Map<String, Object> new_product:new_product_list){
//				//获取销量
//				new_product.put("itemnumber", new_product.get("ITEMNUMBER"));
//				new_product.put("sale_volume", productSortDao.querySaleVolume(new_product));
//			}
			if (new_product_list != null && new_product_list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(new_product_list);
				gr.setTotal(count);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return gr;
	}
	
	@Transactional
	public ProcessResult newProductSort(HttpServletRequest request) throws Exception{		
		ProcessResult pr = new ProcessResult();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json,HashMap.class);
			}
			if (paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
                pr.setState(false);
                pr.setMessage("缺少参数type");
                return pr;
            }
			if("top".equals(paramMap.get("type"))){
				if(StringUtils.isEmpty(paramMap.get("product_id"))){
					pr.setState(false);
	                pr.setMessage("缺少参数product_id");
	                return pr;
				}
				//至顶时将当前货号排序值设为最大
				int num=productSortDao.updateMaxMinFirstSellSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				
			}
			if("buttom".equals(paramMap.get("type"))){
				if(StringUtils.isEmpty(paramMap.get("product_id"))){
					pr.setState(false);
	                pr.setMessage("缺少参数product_id");
	                return pr;
				}
				//至顶时将当前货号排序值设为最小
				int num=productSortDao.updateMaxMinFirstSellSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				
			}
			if("prev".equals(paramMap.get("type"))){
				//查询当前商品排序值
				Map<String, Object> ProductFirstSellSortValue=productSortDao.queryProductFirstSellSortValue(paramMap);
				if(Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())-Integer.parseInt(paramMap.get("rise").toString())<=0){
					paramMap.put("num", 1);
				}else{
					paramMap.put("num",Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())-Integer.parseInt(paramMap.get("rise").toString()));
				}
				paramMap.put("sort_value",  ProductFirstSellSortValue.get("FIRST_SELL_SORT_VALUE"));
				//查询改变排序后位置的排序值
				paramMap.put("id",paramMap.get("product_id"));
				paramMap.remove("product_id");
				Map<String, Object> ProductFirstSellSortValue1=productSortDao.queryProductFirstSellSortValue(paramMap);
				paramMap.put("new_sort_value", ProductFirstSellSortValue1.get("FIRST_SELL_SORT_VALUE"));
				//将排序值之内的商品排序值减一
				int num=productSortDao.updateRiseOrDownFirstSellSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				//将当前商品排序值加上升值
				num=productSortDao.updateFirstSellSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				
			}
			if("next".equals(paramMap.get("type"))){
				//查询最大排名
				int maxNum=productSortDao.queryProductMaxNum();
				//查询当前商品排序值
				Map<String, Object> ProductFirstSellSortValue=productSortDao.queryProductFirstSellSortValue(paramMap);
				if(Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())+Integer.parseInt(paramMap.get("down").toString())>maxNum){
					paramMap.put("num", maxNum);
				}else{
					paramMap.put("num",Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())+Integer.parseInt(paramMap.get("down").toString()));
				}
				paramMap.put("sort_value",  ProductFirstSellSortValue.get("FIRST_SELL_SORT_VALUE"));
				//查询改变排序后位置的排序值
				paramMap.put("id",paramMap.get("product_id"));
				paramMap.remove("product_id");
				Map<String, Object> ProductFirstSellSortValue1=productSortDao.queryProductFirstSellSortValue(paramMap);
				paramMap.put("new_sort_value", ProductFirstSellSortValue1.get("FIRST_SELL_SORT_VALUE"));
				//将排序值之内的商品排序值加一
				int num=productSortDao.updateRiseOrDownFirstSellSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				//将当前商品排序值减下降值
				num=productSortDao.updateFirstSellSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
			}
			pr.setState(true);
            pr.setMessage("排序成功");
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}

}
