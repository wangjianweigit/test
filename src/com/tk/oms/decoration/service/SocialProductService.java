package com.tk.oms.decoration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.decoration.dao.SocialProductDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2019,  TongKu
 * FileName : SocialProductControl
 * 社交首页内容管理相关
 * @author: liujialong
 * @version: 1.00
 * @date: 2019/4/26
 */
@Service("SocialProductService")
public class SocialProductService {
	
	@Resource
    private SocialProductDao socialProductDao;
	
	/**
	 * 查询社交首页商品列表
	 * @param request
	 * @return
	 */
	public GridResult querySocialProductList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("state"))){
            	 gr.setState(false);
                 gr.setMessage("缺少参数");
                 return gr;
            }
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            List<Map<String, Object>> list = this.socialProductDao.querySocialProductList(paramMap);

            int total = this.socialProductDao.querySocialProductCount(paramMap);
            gr.setState(true);
            gr.setMessage("查询数据列表成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
	
	/**
     * 社交首页商品发布或移除
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult socialProductUpdateState(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少获取列表所需的参数
            if (StringUtils.isEmpty(params.get("product_id_list"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[product_id_list]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[state]");
                return pr;
            }
            //执行发布操作
            if(!StringUtils.isEmpty(params.get("display_way"))){
            	//如果当前展示方式为视频展示则需判断当前选择的商品是否都有视频
            	if("2".equals(params.get("display_way")+"")){
            		String product_itemnumber=socialProductDao.checkedProductHaveVideo(params);
            		if(!StringUtils.isEmpty(product_itemnumber)){
            			pr.setState(false);
            			pr.setMessage("货号"+product_itemnumber+"没有视频");
            			return pr;
            		}
            	}
            	List<Integer> product_id_list=(List<Integer>) params.get("product_id_list");
            	for(Integer product_id:product_id_list){
            		params.put("product_id", product_id);
            		socialProductDao.socialProductRelease(params);
            	}
            	pr.setState(true);
                pr.setMessage("发布成功");
            }else{
            	//执行移除操作
                if (this.socialProductDao.socialProductRemove(params) > 0) {
                    pr.setState(true);
                    pr.setMessage("状态修改成功");
                    pr.setObj(params);
                }
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }
    
    /**
     * 社交首页切换展示方式
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult socialProductUpdateDisplayWay(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少获取列表所需的参数
            if (StringUtils.isEmpty(params.get("social_product_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[social_product_id]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("display_way"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[display_way]");
                return pr;
            }
            if (this.socialProductDao.socialProductUpdateDisplayWay(params) > 0) {
                pr.setState(true);
                pr.setMessage("切换展示方式成功");
                pr.setObj(params);
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }
    
    /**
     * 社交首页内容排序
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
	public ProcessResult socialProductSort(HttpServletRequest request) throws Exception{		
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
				if(StringUtils.isEmpty(paramMap.get("social_product_id"))){
					pr.setState(false);
	                pr.setMessage("缺少参数social_product_id");
	                return pr;
				}
				//至顶时将当前商品排序值设为最大
				int num=socialProductDao.updateMaxOrMinSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				
			}
			if("bottom".equals(paramMap.get("type"))){
				if(StringUtils.isEmpty(paramMap.get("social_product_id"))){
					pr.setState(false);
	                pr.setMessage("缺少参数social_product_id");
	                return pr;
				}
				//至顶时将当前商品排序值设为最小
				int num=socialProductDao.updateMaxOrMinSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				
			}
			if("prev".equals(paramMap.get("type"))){
				//查询当前商品排序值
				Map<String, Object> ProductFirstSellSortValue=socialProductDao.queryProductSortValue(paramMap);
				if(Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())-Integer.parseInt(paramMap.get("rise").toString())<=0){
					paramMap.put("num", 1);
				}else{
					paramMap.put("num",Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())-Integer.parseInt(paramMap.get("rise").toString()));
				}
				paramMap.put("sort_value",  ProductFirstSellSortValue.get("SORT_VALUE"));
				//查询改变排序后位置的排序值
				paramMap.put("id",paramMap.get("social_product_id"));
				paramMap.remove("social_product_id");
				Map<String, Object> ProductFirstSellSortValue1=socialProductDao.queryProductSortValue(paramMap);
				paramMap.put("new_sort_value", ProductFirstSellSortValue1.get("SORT_VALUE"));
				//将排序值之内的商品排序值减一
				int num=socialProductDao.updateRiseOrDownSortValue(paramMap);
				if(num<0){
					throw new RuntimeException("排序失败");
				}
				//将当前商品排序值加上升值
				num=socialProductDao.updateSortValue(paramMap);
				if(num<=0){
					throw new RuntimeException("排序失败");
				}
				
			}
			if("next".equals(paramMap.get("type"))){
				//查询最大排名
				int maxNum=socialProductDao.queryProductMaxNum();
				//查询当前商品排序值
				Map<String, Object> ProductFirstSellSortValue=socialProductDao.queryProductSortValue(paramMap);
				if(Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())+Integer.parseInt(paramMap.get("down").toString())>maxNum){
					paramMap.put("num", maxNum);
				}else{
					paramMap.put("num",Integer.parseInt(ProductFirstSellSortValue.get("NUM").toString())+Integer.parseInt(paramMap.get("down").toString()));
				}
				paramMap.put("sort_value",  ProductFirstSellSortValue.get("SORT_VALUE"));
				//查询改变排序后位置的排序值
				paramMap.put("id",paramMap.get("social_product_id"));
				paramMap.remove("social_product_id");
				Map<String, Object> ProductFirstSellSortValue1=socialProductDao.queryProductSortValue(paramMap);
				paramMap.put("new_sort_value", ProductFirstSellSortValue1.get("SORT_VALUE"));
				//将排序值之内的商品排序值加一
				int num=socialProductDao.updateRiseOrDownSortValue(paramMap);
				if(num<=0){
					throw new RuntimeException("排序失败");
				}
				//将当前商品排序值减下降值
				num=socialProductDao.updateSortValue(paramMap);
				if(num<=0){
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
