package com.tk.oms.decoration.service;

import com.tk.oms.decoration.control.DecoratePageTypeEnum;
import com.tk.oms.decoration.dao.DecoratePageDetailDao;
import com.tk.oms.decoration.dao.DecoratePlatformDao;
import com.tk.oms.decoration.dao.DecorateTemplateDao;
import com.tk.sys.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 装修中心管理
 * @author shif
 */
@Service("DecoratePlatformService")
public class DecoratePlatformService {

    @Resource
    private DecoratePlatformDao decoratePlatformDao;
    @Resource
    private DecoratePageDetailDao decoratePageDetailDao;
    @Resource
    private DecorateTemplateDao decorateTemplateDao;
    /**
     * 获取装修中心首页数据
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryDecoratePlatformIndex(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("template_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数template_id");
                 return pr;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            Map<String,Object> resultMap = new HashMap<String,Object>();
            //获取系统组件列表
            params.put("module_type", "1");
            List<Map<String, Object>> sysModuleList = decoratePlatformDao.queryDecorateModuleList(params);
            //获取自定义组件列表
            params.put("group_type", "2");
            List<Map<String, Object>> customModuleList = decoratePlatformDao.queryDecorateModuleList(params);
            resultMap.put("sys_module_list", sysModuleList);
            resultMap.put("custom_module_list", customModuleList);
            
            //获取自定义页
            List<Map<String, Object>> pageList = this.decoratePlatformDao.queryDecoratePageList(params);
            if(pageList!=null&&pageList.size()>0){
            	for(Map<String,Object> page:pageList){
            		if((!StringUtils.isEmpty(page.get("HOME_PAGE_FLAG")))&&DecoratePageTypeEnum.NORMAL_PAGE.value.equals(page.get("HOME_PAGE_FLAG"))){
            			//获取首页
                        Map<String, Object> homePage= page;
                        if(homePage!=null&&StringUtils.isEmpty(homePage.get("ID"))){
                        	//获取首页布局列表
                        	Map<String,Object> tempParamMap = new HashMap<String,Object>();
                        	tempParamMap.put("page_id", homePage.get("ID"));
                        	List<Map<String, Object>> layoutList = decoratePlatformDao.queryPageLayoutList(params);
                        	if(layoutList!=null&&layoutList.size()>0){
                        		Map<String,Object> tempModuleParamMap = new HashMap<String,Object>();
                        		tempModuleParamMap.put("page_id", homePage.get("ID"));
                        		for(Map<String, Object> layoutMap:layoutList){
                        			tempModuleParamMap.put("layout_id", layoutMap.get("ID"));
                        			layoutMap.put("module_list", decoratePlatformDao.queryPageLayoutModuleList(tempModuleParamMap));
                        		}
                        	}
                        	homePage.put("layout_list", layoutList);
                        }
                        resultMap.put("home_page", page);
                        break;
            		}
            	}
            }
            resultMap.put("custom_page_list", pageList);
            pr.setObj(resultMap);
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    
    /**
     * 获取装修中心组件列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryDecoratePlatformModuleList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("group_type")) 
            		&& StringUtils.isEmpty(params.get("module_type"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            //获取组件列表
            List<Map<String, Object>> moduleList = decoratePlatformDao.queryDecorateModuleList(params);
            pr.setObj(moduleList);
            pr.setState(true);
            pr.setMessage("获取数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    /**
     * 获取装修中心组件详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryDecorateModuleDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if (StringUtils.isEmpty(json)) {
    			pr.setState(false);
    			pr.setMessage("缺少参数 ");
    			return pr;
    		}
    		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    		if(StringUtils.isEmpty(params.get("module_id"))){
    			pr.setState(false);
    			pr.setMessage("缺少参数 ");
    			return pr;
    		}
    		//获取组件列表
    		Map<String, Object> moduleObj = decoratePlatformDao.queryDecorateModuleDetail(Long.parseLong(params.get("module_id").toString()));
    		pr.setObj(moduleObj);
    		pr.setState(true);
    		pr.setMessage("获取组件详情成功");
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return pr;
    }
    
    /**
     * 获取装修中心页面列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryDecoratePlatformPageList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("template_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            //获取装修模板页面列表
            List<Map<String, Object>> pageList = decoratePlatformDao.queryDecoratePageList(params);
            pr.setObj(pageList);
            pr.setState(true);
            pr.setMessage("获取数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    
    /**
     * 获取装修中心页面详细【布局、组件】
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryDecoratePlatformPageDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("page_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            //获取页面布局列表
            List<Map<String, Object>> layoutList = decoratePlatformDao.queryLayoutModuleDetailByPageId(params);
            List<Map<String, Object>> resultList =  dealWithPageLayoutInfo(layoutList, true);
            pr.setObj(resultList);
            pr.setState(true);
            pr.setMessage("获取数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    
    /**
     * 获取装修中心页面布局列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryDecoratePlatformPageLayout(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("page_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            //获取页面布局列表
            List<Map<String, Object>> layoutList = decoratePlatformDao.queryLayoutModuleDetailByPageId(params);
            List<Map<String, Object>> resultList =  dealWithPageLayoutInfo(layoutList, true);
            pr.setObj(resultList);
            pr.setState(true);
            pr.setMessage("获取数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    /**
     * 处理一个页面内的布局信息
     * @param layoutList  页面内的布局以及组件信息
     * @param detail_flag true:需要组件详情   false:不需要组件详情
     * @return
     */
    private List<Map<String, Object>> dealWithPageLayoutInfo(List<Map<String, Object>> layoutList,boolean detail_flag){
         List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
    	/**
         * songwangwen
         * 对布局信息进行处理，一个布局一组，布局中的控件属于一个布局
         */
        Map<String,List<Map<String, Object>>> layoutMap = new TreeMap<String,List<Map<String, Object>>>(
                new Comparator<String>() {
                    public int compare(String key1, String key2) {
                    	String SORT_ID1 = key1.split("_")[2];
                    	String SORT_ID2 = key2.split("_")[2];
                        //升序排序
                        return SORT_ID1.compareTo(SORT_ID2);
                    }
                });
        if(!StringUtils.isEmpty(layoutList)){
        	List<Map<String, Object>> tempList = null;
        	for(Map<String,Object> layout:layoutList){
        		 String ID =  layout.get("ID").toString();//表TBL_DECORATE_PAGE_LAYOUT的主键，一个主键表示一个页面中的一个布局
        		 String LAYOUT_TYPE =  layout.get("LAYOUT_TYPE").toString();//表示当前页面中各个布局的展示方式
        		 String LAYOUT_SORT_ID =  layout.get("LAYOUT_SORT_ID").toString();//表示当前页面中各个布局的排序顺序，数据越大越靠后
        		 //使用以上两个数字作为联合key，方便排序
        		 String key = ID+"_"+LAYOUT_TYPE+"_"+LAYOUT_SORT_ID;
        		 if(layoutMap.containsKey(key)){//已存在
        			 tempList = layoutMap.get(key);
        			 if(!StringUtils.isEmpty(layout.get("LAYOUT_PAGE_MODULE_ID"))){
        				 layout.put("SORT_ID",layout.get("MODULE_SORT_ID"));
        				 tempList.add(layout);
        				 //排序一个布局内的组件，以SORT_ID为准，数字大的靠后
        				 Collections.sort(tempList, new Comparator<Map<String, Object>>() {
        					 public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        						 if(!StringUtils.isEmpty(o1.get("MODULE_SORT_ID")) && !StringUtils.isEmpty(o2.get("MODULE_SORT_ID"))){
        							 int m_s1 = Integer.parseInt(o1.get("MODULE_SORT_ID").toString());
        							 int m_s2=  Integer.parseInt(o2.get("MODULE_SORT_ID").toString());
        							 if(m_s1>m_s2){
        								 return 1;
        							 }else{
        								 return -1;
        							 }
        						 }else{
        							 return 0;
        						 }
        					 }
        				 });
        				 /**
        				  	* 清除无用的信息
      		             */
          		         if(!detail_flag){
          		        	 layout.remove("MODULE_BASE_CONF");
          		        	 layout.remove("MODULE_EXTEND_CONF");
          		         }
        			 }
        		 }else{
        			 tempList = new ArrayList<Map<String,Object>>();
        			 if(!StringUtils.isEmpty(layout.get("LAYOUT_PAGE_MODULE_ID"))){
        				 layout.put("SORT_ID",layout.get("MODULE_SORT_ID"));
           		         if(!detail_flag){
        		        	 layout.remove("MODULE_BASE_CONF");
        		        	 layout.remove("MODULE_EXTEND_CONF");
        		         }
        				 tempList.add(layout);
        			 }
        			 layoutMap.put(key, tempList);
        		 }
        	}
        }
        for(String key:layoutMap.keySet()){
        	String[] keyArr = key.split("_");
        	Map<String,Object> resultMap = new HashMap<String, Object>();
        	resultMap.put("LAYOUT_ID",keyArr[0]);
        	resultMap.put("LAYOUT_TYPE",keyArr[1]);
        	resultMap.put("SORT_ID",keyArr[2]);
        	List<Map<String, Object>> MODULE_LIST = layoutMap.get(key);
        	for(Map<String,Object> temp:MODULE_LIST){
        		 /**
		           * 清除无用的信息
		           */
				 temp.remove("ID");
				 temp.remove("LAYOUT_ID");
				 temp.remove("MODULE_SORT_ID");
				 temp.remove("LAYOUT_SORT_ID");
        	}
        	resultMap.put("MODULE_LIST", MODULE_LIST);
        	resultList.add(resultMap);
        }
        return resultList;
    }
    /**
     * 新增装修中心页面
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageAdd(HttpServletRequest request) throws Exception {
    	ProcessResult pr = new ProcessResult();
        String json = HttpUtil.getRequestInputStream(request);
        if (StringUtils.isEmpty(json)) {
            pr.setState(false);
            pr.setMessage("缺少参数 ");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
        if(StringUtils.isEmpty(params.get("page_name"))
        		||StringUtils.isEmpty(params.get("template_id"))
        		||StringUtils.isEmpty(params.get("public_user_id"))||StringUtils.isEmpty(params.get("site_id"))||StringUtils.isEmpty(params.get("public_user_site_id"))){
        	 pr.setState(false);
             pr.setMessage("缺少参数 ");
             return pr;
        }
        if(!params.get("public_user_site_id").toString().equals(params.get("site_id").toString())){
        	pr.setState(false);
            pr.setMessage("当前装修模板所在站点与登录选择的站点不一致，请关闭页面后重试！ ");
            return pr;
        }
        
        if(!StringUtils.isEmpty(params.get("home_page_flag")) 
        		&& DecoratePageTypeEnum.PRODUCT_LIST_PAGE.value.equals(params.get("home_page_flag"))
        		&& StringUtils.isEmpty(params.get("layout_type"))
        		){
        	pr.setState(false);
        	pr.setMessage("缺少参数[layout_type] ");
        	return pr;
        }
        params.put("page_state","1");//默认停用状态
        //验证page名称是否重复
    	if(decoratePlatformDao.countPageByPageName(params)>0){
    		pr.setState(false);
    		pr.setMessage("该页面名称已被使用！ ");
    		return pr;
    	}
        //新增页面
        int page_id = decoratePlatformDao.queryPageId(params);
        params.put("page_id", page_id);
        if(StringUtils.isEmpty(params.get("home_page_flag"))){
        	params.put("home_page_flag",DecoratePageTypeEnum.NORMAL_PAGE.value);//默认为非首页
        }
        if(StringUtils.isEmpty(params.get("page_type"))){
        	params.put("page_type",DecoratePageTypeEnum.NORMAL_PAGE.value);//默认为非首页
        }
        if(decoratePlatformDao.insertPage(params)>0){
        	//如果当前页面为  【活动商品列表】页面，则需要在新增页面详细信息
        	 if(!StringUtils.isEmpty(params.get("home_page_flag")) 
        			 && DecoratePageTypeEnum.PRODUCT_LIST_PAGE.value.equals(params.get("home_page_flag").toString())){
             	decoratePageDetailDao.insertOrUpdate(params);
             	pr.setObj(params);
        		pr.setState(true);
        		pr.setMessage("添加成功");
             }else{
            	 //新增页面完成后，同时插入一个默认的页面布局，布局类型有界面传递
            	 int layout_id = decoratePlatformDao.queryPageLayoutId(params);
            	 params.put("layout_id", layout_id);
            	 if(StringUtils.isEmpty(params.get("layout_type"))){
            		 params.put("layout_type","0");//默认布局类型  0：通栏布局;1：单行布局;2：左窄右宽;3：左宽右窄；
            	 }
            	 if(decoratePlatformDao.insertPageLayout(params)>0){
            		 pr.setObj(params);
            		 pr.setState(true);
            		 pr.setMessage("添加成功");
            	 }
             }
        }else{
        	pr.setState(false);
        	pr.setMessage("添加失败");
        }
    
        return pr;
    }
    /**
     * 修改装修中心页面
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult decoratePlatformPageEdit(HttpServletRequest request) throws Exception {
    	ProcessResult pr = new ProcessResult();
    	String json = HttpUtil.getRequestInputStream(request);
    	if (StringUtils.isEmpty(json)) {
    		pr.setState(false);
    		pr.setMessage("缺少参数 ");
    		return pr;
    	}
    	Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	if(StringUtils.isEmpty(params.get("page_id"))){
    		pr.setState(false);
    		pr.setMessage("缺少参数 ");
    		return pr;
    	}
    	if(!StringUtils.isEmpty(params.get("site_id"))&&!StringUtils.isEmpty(params.get("public_user_site_id"))){
	    	if(!params.get("public_user_site_id").toString().equals(params.get("site_id").toString())){
	        	pr.setState(false);
	            pr.setMessage("当前装修模板所在站点与登录选择的站点不一致，请关闭页面后重试！ ");
	            return pr;
	        }
    	}
    	//验证page名称是否重复
    	if(decoratePlatformDao.countPageByPageName(params)>0){
    		pr.setState(false);
    		pr.setMessage("该页面名称已被使用！ ");
    		return pr;
    	}
    	/**
    	 * 如果更新的是 界面背景   或界面头部背景 则需要将json数据从集合转为字符串
    	 */
    	if(!StringUtils.isEmpty(params.get("page_background"))){
        	Object page_background = params.get("page_background");
        	if(page_background instanceof List  || page_background instanceof Map){
        		String page_background_json = Jackson.writeObject2Json(page_background);
        		params.put("page_background", page_background_json);
        	}
        }
    	if(!StringUtils.isEmpty(params.get("page_head_background"))){
    		Object page_head_background = params.get("page_head_background");
    		if(page_head_background instanceof List  || page_head_background instanceof Map){
    			String page_head_background_json = Jackson.writeObject2Json(page_head_background);
    			params.put("page_head_background", page_head_background_json);
    		}
    	}
    	if(!StringUtils.isEmpty(params.get("aside_nav"))){
    		Object aside_nav = params.get("aside_nav");
    		if(aside_nav instanceof List  || aside_nav instanceof Map){
    			String aside_nav_json = Jackson.writeObject2Json(aside_nav);
    			params.put("aside_nav", aside_nav_json);
    		}
    	}
    	Map<String, Object> templateMap = new HashMap<String, Object>();
    	if(!StringUtils.isEmpty(params.get("bottom_nav"))){
    		Object bottom_nav = params.get("bottom_nav");
    		if(bottom_nav instanceof List  || bottom_nav instanceof Map){
    			String bottom_nav_json = Jackson.writeObject2Json(bottom_nav);
    			templateMap.put("bottom_nav", bottom_nav_json);
    		}
    	}
    	//顶部导航数据
    	if(!StringUtils.isEmpty(params.get("top_nav"))){
    		Object top_nav = params.get("top_nav");
    		if(top_nav instanceof List  || top_nav instanceof Map){
    			String top_nav_json = Jackson.writeObject2Json(top_nav);
    			templateMap.put("top_nav", top_nav_json);
    		}
    	}
    	/***
		 * songwangwen  2017.11.10
		 * 更新装修模板中的bottom_nav 或 top_nav 字段
		 */
    	if(!StringUtils.isEmpty(templateMap.get("bottom_nav"))
    			||!StringUtils.isEmpty(templateMap.get("top_nav"))){
    		//查询页面详情，得到页面所属的模板信息
    		Map<String, Object> pmap = decoratePlatformDao.queryDecoratePageDetail(params);
    		templateMap.put("id", pmap.get("TEMPLATE_ID"));//赋值，模板ID
    		if(decorateTemplateDao.update(templateMap)<=0){
    			pr.setState(false);
    			pr.setMessage("更新装修模板失败(更新顶部或底部导航信息)");
    			return pr;
    		}
    	}
    	if(decoratePlatformDao.updateDecoratePage(params)>0){
    		//如果当前页面为  【活动商品列表】页面，则需要在新增页面详细信息
       	 	if(!StringUtils.isEmpty(params.get("home_page_flag")) 
       			 && DecoratePageTypeEnum.PRODUCT_LIST_PAGE.value.equals(params.get("home_page_flag").toString())){
            	decoratePageDetailDao.insertOrUpdate(params);
            }
    		//新增页面完成后，同时插入一个默认的页面布局，布局类型有界面传递
			pr.setState(true);
			pr.setMessage("编辑装修页面成功");
    	}else{
    		pr.setState(false);
			pr.setMessage("编辑装修页面失败");
    	}
    	
    	return pr;
    }
    
    /**
     * 获取装修中心布局新增
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutAdd(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("page_id"))
            		||StringUtils.isEmpty(params.get("layout_type"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            //新增页面布局
            int layout_id = decoratePlatformDao.queryPageLayoutId(params);
            params.put("layout_id", layout_id);
            if(decoratePlatformDao.insertPageLayout(params)>0){
            	Map<String, Object> page_layout = decoratePlatformDao.queryPageLayoutDetail(params);
            	//更新页面信息
            	decoratePlatformDao.updateDecoratePage(params);
            	pr.setState(true);
            	pr.setMessage("添加布局成功");
            	pr.setObj(page_layout);
            }else{
            	pr.setState(false);
            	pr.setMessage("添加布局失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("添加布局失败,"+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 获取装修中心布局删除
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutDelete(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();

        String json = HttpUtil.getRequestInputStream(request);
        if (StringUtils.isEmpty(json)) {
            pr.setState(false);
            pr.setMessage("缺少参数 ");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
        if(StringUtils.isEmpty(params.get("layout_id"))){
        	 pr.setState(false);
             pr.setMessage("缺少参数 ");
             return pr;
        }
        Map<String, Object> layoutMap=decoratePlatformDao.queryPageLayoutDetail(params);
        if(layoutMap==null||StringUtils.isEmpty(layoutMap.get("ID"))){
        	 pr.setState(false);
             pr.setMessage("布局记录不存在 ");
             return pr;
        }
        if(decoratePlatformDao.queryPageLayoutModuleCount(params) > 0){
        	//更新页面信息
        	decoratePlatformDao.updateDecoratePage(params);
        	pr.setState(false);
        	pr.setMessage("当前布局下还有组件，请先删除组件");
        	return pr;
        }
        params.put("page_id", layoutMap.get("PAGE_ID"));
        int sort_id = Integer.parseInt(layoutMap.get("SORT_ID").toString());
        if(decoratePlatformDao.deletePageLayout(params)>0){
        	 params.put("type","minus");
             params.put("old_sort_id",sort_id);
             decoratePlatformDao.updatePageLayoutSortId(params);
        	//更新页面信息
        	decoratePlatformDao.updateDecoratePage(params);
        	pr.setState(true);
        	pr.setMessage("删除成功");
        }else{
        	pr.setState(false);
        	pr.setMessage("删除失败");
        }
        return pr;
    }
    /**
     * 获取装修中心布局排序
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutSort(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("layout_id"))||StringUtils.isEmpty(params.get("sort_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            Map<String, Object> layoutMap=decoratePlatformDao.queryPageLayoutDetail(params);
            if(layoutMap==null||StringUtils.isEmpty(layoutMap.get("ID"))){
            	 pr.setState(false);
                 pr.setMessage("布局记录不存在 ");
                 return pr;
            }
            int new_sort_id = Integer.parseInt(params.get("sort_id").toString());
            int old_sort_id =Integer.parseInt(layoutMap.get("SORT_ID").toString());
            params.put("page_id", layoutMap.get("PAGE_ID"));
            params.put("type","sort");
            if(old_sort_id>new_sort_id){
            	params.put("add","add");
            	params.put("old_sort_id",new_sort_id);
            	params.put("new_sort_id",old_sort_id);
            }else{
            	params.put("minus","minus");
            	params.put("old_sort_id",old_sort_id);
            	params.put("new_sort_id",new_sort_id);
            }
            if(decoratePlatformDao.updatePageLayoutSort(params)>0){
            	decoratePlatformDao.updatePageLayoutSortId(params);
            	//更新页面信息
            	decoratePlatformDao.updateDecoratePage(params);
            	pr.setState(true);
            	pr.setMessage("排序成功");
            }else{
            	pr.setState(false);
            	pr.setMessage("排序失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 获取装修中心布局组件新增
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutModuleAdd(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("page_id"))
            		||StringUtils.isEmpty(params.get("layout_id"))
            		||StringUtils.isEmpty(params.get("module_id"))
            		||StringUtils.isEmpty(params.get("layout_column_id"))
            		||StringUtils.isEmpty(params.get("sort_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            //新增页面布局组件
            int layout_module_id = decoratePlatformDao.queryPageLayoutModuleId(params);
            params.put("layout_module_id", layout_module_id);
            if(decoratePlatformDao.insertPageLayoutModule(params)>0){//插入数据
            	//新增组件完成，更新该布局内的其他排序组件信息
            	Map<String, Object> moduleMap = decoratePlatformDao.queryPageLayoutModuleDetail(params);
                int new_sort_id = Integer.parseInt(params.get("sort_id").toString());
                params.put("type","add");
                params.put("new_sort_id",new_sort_id);
                decoratePlatformDao.updatePageLayoutModuleSortId(params);
            	//更新页面信息
            	decoratePlatformDao.updateDecoratePage(params);
            	pr.setState(true);
            	pr.setMessage("页面布局中添加控件成功");
            	pr.setObj(moduleMap);
            }else{
            	pr.setState(false);
            	pr.setMessage("页面布局中添加控件失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 获取装修中心布局组件排序
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutModuleSort(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();

        String json = HttpUtil.getRequestInputStream(request);
        if (StringUtils.isEmpty(json)) {
            pr.setState(false);
            pr.setMessage("缺少参数 ");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
        if(StringUtils.isEmpty(params.get("layout_module_id"))
        		||StringUtils.isEmpty(params.get("sort_id"))){
        	 pr.setState(false);
             pr.setMessage("缺少参数 ");
             return pr;
        }
        Map<String, Object> moduleMap=decoratePlatformDao.queryPageLayoutModuleDetail(params);
        if(moduleMap==null||StringUtils.isEmpty(moduleMap.get("ID"))){
        	 pr.setState(false);
             pr.setMessage("记录不存在 ");
             return pr;
        }
        int old_sort_id = Integer.parseInt(moduleMap.get("SORT_ID").toString());
        int new_sort_id = Integer.parseInt(params.get("sort_id").toString());
        if(old_sort_id == new_sort_id){
        	pr.setState(true);
        	pr.setMessage("排序顺序无变化，不需要更新");
            return pr;
        }
        params.put("type","sort");
        if(old_sort_id>new_sort_id){
        	params.put("add","add");
        	params.put("old_sort_id",new_sort_id);
        	params.put("new_sort_id",old_sort_id);
        }else{
        	params.put("minus","minus");
        	params.put("old_sort_id",old_sort_id);
        	params.put("new_sort_id",new_sort_id);
        }
        params.put("page_id", moduleMap.get("PAGE_ID"));
        params.put("layout_id", moduleMap.get("LAYOUT_ID"));
        if(decoratePlatformDao.updatePageLayoutModule(params)>0){
        	decoratePlatformDao.updatePageLayoutModuleSortId(params);
        	//更新页面信息
        	decoratePlatformDao.updateDecoratePage(params);
        	pr.setState(true);
        	pr.setMessage("排序成功");
        }else{
        	pr.setState(false);
        	pr.setMessage("排序失败");
        }
        return pr;
    }
    
    /**
     * 装修中心布局组件删除
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutModuleDelete(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();

        String json = HttpUtil.getRequestInputStream(request);
        if (StringUtils.isEmpty(json)) {
            pr.setState(false);
            pr.setMessage("缺少参数 ");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
        if(StringUtils.isEmpty(params.get("layout_module_id"))){
        	 pr.setState(false);
             pr.setMessage("缺少参数 ");
             return pr;
        }
        Map<String, Object> layoutMap=decoratePlatformDao.queryPageLayoutModuleDetail(params);
        if(layoutMap==null||StringUtils.isEmpty(layoutMap.get("ID"))){
        	 pr.setState(false);
             pr.setMessage("记录不存在 ");
             return pr;
        }
        params.put("page_id", layoutMap.get("PAGE_ID"));
        params.put("layout_id", layoutMap.get("LAYOUT_ID"));
        if(decoratePlatformDao.deletePageLayoutModule(params)>0){
            params.put("type","minus");
            params.put("old_sort_id",layoutMap.get("SORT_ID"));
            decoratePlatformDao.updatePageLayoutModuleSortId(params);
        	//更新页面信息
        	decoratePlatformDao.updateDecoratePage(params);
        	pr.setState(true);
        	pr.setMessage("删除成功");
        }else{
        	pr.setState(false);
        	pr.setMessage("删除失败");
        }
        return pr;
    }
    
    /**
     * 装修中心布局组件编辑
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult decoratePlatformPageLayoutModuleEdit(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if(!params.containsKey("user_group_id")){
				if(StringUtils.isEmpty(params.get("layout_module_id"))||StringUtils.isEmpty(params.get("module_base_conf"))){
					pr.setState(false);
					pr.setMessage("缺少参数 ");
					return pr;
				}
			}
            Map<String, Object> layoutMap=decoratePlatformDao.queryPageLayoutModuleDetail(params);
            if(layoutMap==null||StringUtils.isEmpty(layoutMap.get("ID"))){
            	 pr.setState(false);
                 pr.setMessage("记录不存在 ");
                 return pr;
            }
            /**
             * 如果存在json对象的更新信息，需要将json对象转化为json字符串
             */
            if(!StringUtils.isEmpty(params.get("module_base_conf"))){
            	Object module_base_conf = params.get("module_base_conf");
            	if(module_base_conf instanceof List  || module_base_conf instanceof Map){
            		String module_base_conf_json = Jackson.writeObject2Json(module_base_conf);
            		params.put("module_base_conf", module_base_conf_json);
            	}
            }
            if(!StringUtils.isEmpty(params.get("module_extend_conf"))){
            	Object module_base_conf = params.get("module_extend_conf");
            	if(module_base_conf instanceof List  || module_base_conf instanceof Map){
            		String module_extend_conf_json = Jackson.writeObject2Json(module_base_conf);
            		params.put("module_extend_conf", module_extend_conf_json);
            	}
            }
            int num = decoratePlatformDao.updatePageLayoutModule(params);
            if(num>0){
            	//更新页面信息
            	decoratePlatformDao.updateDecoratePage(params);
            	pr.setState(true);
            	pr.setMessage("编辑成功");
            }else{
            	pr.setState(false);
            	pr.setMessage("编辑失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
	 * 将一个装修模板设置为首页
	 */
	@SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
	public ProcessResult setTemplateHomepage(HttpServletRequest request) throws Exception{
		ProcessResult gr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String json = HttpUtil.getRequestInputStream(request);
		if(!StringUtils.isEmpty(json)){
			paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		}
		if(paramMap==null || StringUtils.isEmpty(paramMap.get("page_id"))){
			gr.setState(false);
			gr.setMessage("缺少参数【page_id】");
			return gr;
		}
		//将原先的模板的首页设置为非首页
		decoratePlatformDao.updateTemplateHomePage(paramMap);
		//将当前页面设置为首页
		decoratePlatformDao.updateDecoratePage(paramMap);
		gr.setState(true);
		gr.setMessage("设置首页成功");
		return gr;
	}
	
	 /**
     * 获取装修中心页面详情，主要为了获得页面的背景，头部背景信息
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryPageDetailById(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("page_id"))){
            	 pr.setState(false);
                 pr.setMessage("缺少参数 ");
                 return pr;
            }
            try {
            	Long.parseLong(params.get("page_id").toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
            //获取装修模板页面列表
            Map<String, Object> pageDetail = decoratePlatformDao.queryDecoratePageDetail(params);
            pr.setObj(pageDetail);
            pr.setState(true);
            pr.setMessage("获取页面数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
    /**
     * 根据页面id查询页面详情，即表TBL_DECORATE_PAGE_DETAIL数据
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryPageDetailJSONById(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if (StringUtils.isEmpty(json)) {
    			pr.setState(false);
    			pr.setMessage("缺少参数 ");
    			return pr;
    		}
    		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    		if(StringUtils.isEmpty(params.get("page_id"))){
    			pr.setState(false);
    			pr.setMessage("缺少参数 ");
    			return pr;
    		}
    		long page_id = 0;
    		try {
    			page_id = Long.parseLong(params.get("page_id").toString());
    		} catch (Exception e) {
    			// TODO: handle exception
    		}
    		//获取装修模板页面列表
    		Map<String, Object> pageDetail = decoratePageDetailDao.queryDetail(page_id);
    		pr.setObj(pageDetail);
    		pr.setState(true);
    		pr.setMessage("获取页面数据成功");
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return pr;
    }

	/**
	 * 获取有权限的装修用户列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryDecorateUserListBySiteId(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数 ");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (StringUtils.isEmpty(paramMap.get("site_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数【site_id】");
				return pr;
			}
			List<Map<String, Object>> list = decoratePlatformDao.queryDecorateUserListBySiteId(paramMap);
			pr.setMessage("装修用户列表获取成功");
			pr.setState(true);
			pr.setObj(list);
			return pr;

		} catch (Exception ex) {
			pr.setState(false);
			pr.setMessage(ex.getMessage());
		}
		return pr;
	}

	/**
	 * 验证装修预览权限
	 * @param request
	 * @return
     */
	public ProcessResult decoratePreviewCheck(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数 ");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (StringUtils.isEmpty(paramMap.get("user_name"))) {
				pr.setState(false);
				pr.setMessage("缺少参数【user_name】");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("page_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数【page_id】");
				return pr;
			}
			//获取用户信息
			Map<String, Object> userMap = this.decoratePlatformDao.queryUserInfoById(paramMap);
			if (userMap == null) {
				pr.setState(false);
				pr.setMessage("用户不存在");
				return pr;
			}
			//判断用户是否开通装修预览权限
			if ("0".equals(userMap.get("DECORATION_STATE").toString()) || StringUtils.isEmpty(userMap.get("DECORATION_STATE"))) {
				pr.setState(false);
				pr.setMessage("未开通装修预览权限");
				return pr;
			}
			Map<String, Object> objMap = new HashMap<String, Object>();
			objMap.put("user_name",paramMap.get("user_name"));
			objMap.put("page_id",paramMap.get("page_id"));
			objMap.put("type",paramMap.get("type"));
			objMap.put("template_id",paramMap.get("template_id"));
			pr.setMessage("装修预览权限验证成功");
			pr.setState(true);
			pr.setObj(objMap);
		} catch (Exception ex) {
			pr.setState(false);
			pr.setMessage(ex.getMessage());
		}
		return pr;
	}
}
