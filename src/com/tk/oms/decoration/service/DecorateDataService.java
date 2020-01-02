package com.tk.oms.decoration.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.decoration.dao.DecorateDataDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 装修预览
 * Created by wangpeng on 2017/5/23 0001.
 */
@Service("DecorateDataService")
public class DecorateDataService {

    @Resource
    private DecorateDataDao decorateDataDao;

    /**
     * 获取站点导航列表
     * @param request
     * @return
     */
	public ProcessResult queryNavList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_name");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("site_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数site_id");
                return pr;
            }

            List<Map<String,Object>> navList = decorateDataDao.queryNavList(params);
            if(navList != null) {
                pr.setState(true);
                pr.setMessage("获取导航成功");
                pr.setObj(navList);
            }else {
                pr.setState(false);
                pr.setMessage("获取导航失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 通过页面查询对应数据
	 */
	public ProcessResult queryModuleByPagemoduleId(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			
			if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			
			if(StringUtils.isEmpty(params.get("site_id"))){
           	 	pr.setState(false);
                pr.setMessage("缺少site_id参数 ");
                return pr;
            }
			//获取当前登录用户
			String site_id = params.get("site_id").toString();
            if(StringUtils.isEmpty(params.get("page_module_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少page_module_id参数 ");
                 return pr;
            }
            Map<String,Object> moduleMap = decorateDataDao.queryModuleBaseConfByPageModuleId(params);
            if(null==moduleMap||moduleMap.isEmpty()){
        		pr.setState(false);
        		pr.setMessage("控件信息异常，请联系管理员page_module_id="+params.get("page_module_id"));
        		return pr;
        	}else{
        		
        		String module_code = moduleMap.get("MODULE_CODE").toString();
        		String module_base_conf = StringUtils.isEmpty(moduleMap.get("MODULE_BASE_CONF"))?"":moduleMap.get("MODULE_BASE_CONF").toString();
        		if(module_base_conf.equals("")){
        			pr.setState(true);
            		pr.setMessage("控件的MODULE_BASE_CONF未配置，请配置");
            		pr.setObj(null);
            		return pr;
        		}
        		/**
        		 * 不是【Tab商品】控件，则仍然按照【商品列表】处理
        		 */
        		if(module_code.indexOf("tab_product_list") == -1 && module_code.indexOf("product_list")!=-1){//商品控件|滚动商品控件(非tab商品控件)
        			//获取商品列表数据
        			return this.getProductList(module_base_conf,site_id);
        		}else if(module_code.indexOf("tab_product_list") != -1){//【Tab商品】
        			if(StringUtils.isEmpty(params.get("tab_index"))){//tab_index 从0开始。
        				params.put("tab_index",0);//不传值则默认选取第一个TAB
                   }
	       			int tab_index = Integer.parseInt(params.get("tab_index")+"");
	       			Map<String,Object> paramMap = (Map<String,Object>)Jackson.readJson2Object(module_base_conf, Map.class);
	       			if(paramMap == null || StringUtils.isEmpty(paramMap.get("configList"))){
	       				pr.setState(false);
	                       pr.setMessage("tab商品控件配置异常，请联系管理员MODULE_BASE_CONF配置异常 ");
	                       return pr;
	       			}
	       			List<Map<String,Object>> paramMapList = (List<Map<String,Object>>)paramMap.get("configList");
	       			if(paramMapList==null||paramMapList.size()<0||paramMapList.size()<= tab_index){
	       				pr.setState(false);
	                       pr.setMessage("tab商品控件配置异常，请联系管理员MODULE_BASE_CONF配置异常 ");
	                       return pr;
	       			}
	       			//根据前端的传值index，确定展示不同的商品列表
	       			String module_base_conf_ =Jackson.writeObject2Json(paramMapList.get(tab_index));
	       			if(StringUtils.isEmpty(module_base_conf_)){
	       				pr.setState(false);
	                       pr.setMessage("tab商品控件信息异常，请联系管理员-MODULE_BASE_CONF配置异常 ");
	                       return pr;
	       			}
	       			//获取选项卡商品列表数据
        			return this.getProductList(module_base_conf_,site_id);
        		}else if(module_code.indexOf("notice") != -1){//【新闻组件】
        			return this.getNewsList(module_base_conf);
        		}else{
        			pr.setMessage("未配置的【MODULE_CODE】！");
        			pr.setState(false);
        		}
        	}
            
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取新闻列表
	 * @param list
	 * @return
	 */
	private ProcessResult getNewsList(String module_base_conf){
		ProcessResult pr = new ProcessResult();
		//将参数转换成对象
		Map<String,Object> paramMap = (Map<String,Object>)Jackson.readJson2Object(module_base_conf, Map.class);
		if(StringUtils.isEmpty(paramMap.get("count"))||StringUtils.isEmpty(paramMap.get("sort_by"))){
			pr.setState(false);
			pr.setMessage("新闻控件信息获取缺少参数");
			return pr;
		}
		List<Map<String,Object>> list = decorateDataDao.queryNewsListForModul(paramMap);
		pr.setMessage("新闻控件信息获取成功");
		pr.setState(true);
		pr.setObj(list);
		return pr;
	}
	/**
	 * 获取商品列表
	 * @param list
	 * @return
	 */
	private ProcessResult getProductList(String module_base_conf,String site_id){
		ProcessResult pr = new ProcessResult();
		//将参数转换成对象
		Map<String,Object> paramMap = (Map<String,Object>)Jackson.readJson2Object(module_base_conf, Map.class);
		paramMap.put("site_id", site_id);
		
		List<Map<String,Object>> list = null;
		
		//商品筛选类型
		String select_type = paramMap.get("select_type").toString();
		
		if(select_type.equals("1")){//1:按活动    
			list = decorateDataDao.queryProductForActivity(paramMap);
		}else if(select_type.equals("2")){//2:按分类 
			list = decorateDataDao.queryProductForType(paramMap);
		}else if(select_type.equals("3")){//3:手动
			String[] items = paramMap.get("items").toString().split(",");
			List<String> itemslist = new ArrayList<String>();
			for(int i=0 ;i<items.length;i++){
				itemslist.add(items[i]);
			}
			paramMap.put("productlist", itemslist);
			list = decorateDataDao.queryProductForSelect(paramMap);
			if(!StringUtils.isEmpty(list) && !list.isEmpty()){
				//将得到的结果按照设定的货号的顺序进行排序
				List<Map<String,Object>> order_list = new ArrayList<Map<String,Object>>();
				for(String itemnumber:itemslist){
					loop:for(Map<String,Object> node:list){
						if(itemnumber.equals(node.get("ITEMNUMBER"))){
							order_list.add(node);
							break loop;
						}
					}
				}//end for;
				if(!StringUtils.isEmpty(order_list) && !order_list.isEmpty())
					list = order_list;
			}
		}else{
			pr.setState(false);
			pr.setMessage("错误的商品选择类型");
			return pr;
		}
		pr.setMessage("控件商品信息获取成功");
		pr.setState(true);
		pr.setObj(list);
		return pr;
	}
}
