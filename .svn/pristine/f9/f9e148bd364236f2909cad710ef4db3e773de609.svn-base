package com.tk.oms.product.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.product.dao.ProductAnalyseDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : ProductAnalysisService.java
 * 商品分析报表
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月15日
 */
@Service("ProductAnalyseService")
public class ProductAnalyseService {
	@Resource
	private ProductAnalyseDao productAnalyseDao;
	@Value("${jdbc_user}")
	private String jdbc_user;
	
	@SuppressWarnings("unchecked")
	public GridResult queryProductAnalyseListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			paramMap.put("jdbc_user", jdbc_user);
			
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
            if((!StringUtils.isEmpty(paramMap.get("site_id")))&&paramMap.get("site_id") instanceof String){
                paramMap.put("site_id",(paramMap.get("site_id")+"").split(","));
            }
			//列表
			List<Map<String, Object>> list = productAnalyseDao.queryProductAnalyseListForPage(paramMap);
			//数量
			int count = productAnalyseDao.queryProductAnalyseCount(paramMap);
			if (list != null && list.size() > 0) {
				for(Map<String, Object> map : list){
					String product_content = "";
					if(StringUtils.isEmpty(map.get("PRODUCT_CONTENT"))){
						map.put("DETAIL_IMG_COUNT", 0);
					}else{
						int index = 0;
						product_content = map.get("PRODUCT_CONTENT").toString();
						
						String regex="<img.*?/>";
					    Pattern pt=Pattern.compile(regex);
					    Matcher mt=pt.matcher(product_content);
					    while(mt.find())
					    {
					       String s3="src=.*?>";
					       Pattern pt3=Pattern.compile(s3);
					       Matcher mt3=pt3.matcher(mt.group());
					       while(mt3.find())
					       {
					    	if(mt3.group().replaceAll("src=|>","").indexOf("http") > 0){
					    		index = index+1;
					    	}
					       }
					    }
					    
					    if(index > 0){
					    	map.put("DETAIL_IMG_COUNT", index);
					    }else{
					    	map.put("DETAIL_IMG_COUNT", 0);
					    }
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
