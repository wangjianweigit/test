package com.tk.oms.basicinfo.service;

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

import com.tk.oms.basicinfo.dao.FreightTemplateDao;
import com.tk.oms.basicinfo.dao.LogisticsCompanyDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : FreightTemplateService
 * 运费模板管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-14
 */
@Service("FreightTemplateService")
public class FreightTemplateService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private FreightTemplateDao freightTemplateDao;
	@Resource
	private LogisticsCompanyDao logisticsCompanyDao;
	/**
	 * 运费模板列表,当前端传“global_freight”全局运费获取参数时，返回全局运费信息。
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryFreightTemplateList(HttpServletRequest request) {
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
            if(!paramMap.containsKey("platform_type") || StringUtils.isEmpty(paramMap.get("platform_type"))){
	        	paramMap.put("platform_type",1);
			}
			//查询运费模板数量
			int total = freightTemplateDao.queryFreightTemplateCount(paramMap);
			//查询运费模板列表
			List<Map<String, Object>> dataList = freightTemplateDao.queryFreightTemplateList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				for(Map<String, Object> map : dataList){
					paramMap.put("template_id", map.get("ID"));
					List<Map<String,Object>> detailList = freightTemplateDao.queryFreightTemplateDetailList(paramMap);
					map.put("template_detail", detailList);
				}
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
	 * 添加运费模板
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addFreightTemplate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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
	        //模板名称是否重复
	        if(freightTemplateDao.queryNameIsExist(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("模板名称已存在,请修改后提交");
	        	return pr;
	        }
	        String logistic_ids = (String) paramMap.get("logistic_ids");
	        String logistic_names = (String) paramMap.get("logistic_names");
	        String send_to_areas = (String) paramMap.get("send_to_areas");
	        String send_to_area_names = (String) paramMap.get("send_to_area_names");
	        String first_counts = (String) paramMap.get("first_counts");
	        String first_moneys = (String) paramMap.get("first_moneys");
	        String continue_counts = (String) paramMap.get("continue_counts");
	        String continue_moneys = (String) paramMap.get("continue_moneys");
	        String warehouse_ids = (String) paramMap.get("warehouse_ids");
	        String[] logistic_id = logistic_ids.split("\\|");//快递ID
	        String[] logistic_name = logistic_names.split("\\|");//快递名称
	        String[] send_to_area = send_to_areas.split("\\|");//运送范围（省ID以逗号分隔）
	        String[] send_to_area_name = send_to_area_names.split("\\|");//运送范围文字（以逗号分隔）
	        String[] first_count = first_counts.split("\\|");//首件数量
	        String[] first_money = first_moneys.split("\\|");//首件价格
	        String[] continue_count = continue_counts.split("\\|");//续件数量
	        String[] continue_money = continue_moneys.split("\\|");//续件价格
	        String[] warehouse_id = warehouse_ids.split("\\|");//所属仓库【大仓】
	        if(logistic_id.length != send_to_area.length ||  logistic_id.length != first_money.length){
	        	pr.setState(false);
	        	pr.setMessage("运费模板传递的设置条数不符!");
                return pr;
	        }
	      	//1.根据模板名称查询运费模板数量
            if(freightTemplateDao.queryFreightTemplateCount(paramMap)<1){
            	//2.新增运费模板
            	freightTemplateDao.insert(paramMap);
            	for(int i=0;i<logistic_id.length;i++){
            		Map<String, Object> map = new HashMap<String, Object>();
            		map.put("user_id", paramMap.get("public_user_id"));
            		map.put("template_id", paramMap.get("id"));
            		map.put("logistics_company_id", logistic_id[i]);
            		map.put("logistics_company_name", logistic_name[i]);
            		map.put("range", send_to_area[i]);
            		map.put("first_count", first_count[i]);
            		map.put("first_money", first_money[i]);
            		map.put("continue_count", continue_count[i]);
            		map.put("continue_money", continue_money[i]);
            		map.put("range_names", send_to_area_name[i]);
            		map.put("warehouse_id", warehouse_id[i]);
            		dataList.add(map);
            	}
        		
            	//3.新增运费模板详情
            	freightTemplateDao.insertDetail(dataList);
    			pr.setState(true);
                pr.setMessage("新增成功！");
            }else{
        		pr.setState(false);
                pr.setMessage("该模板名称已存在！");
        	}
	        
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("新增失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

		return pr;
	}
	/**
	 * 编辑运费模板
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editFreightTemplate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
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
	        if(StringUtils.isEmpty(paramMap.get("id"))) {
	        	pr.setState(false);
	        	pr.setMessage("缺少模板ID");
                return pr;
            }
	        //模板名称是否重复
	        if(freightTemplateDao.queryNameIsExist(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("模板名称已存在,请修改后提交");
	        	return pr;
	        }
	        String logistic_ids = (String) paramMap.get("logistic_ids");
	        String logistic_names = (String) paramMap.get("logistic_names");
	        String send_to_areas = (String) paramMap.get("send_to_areas");
	        String send_to_area_names = (String) paramMap.get("send_to_area_names");
	        String first_counts = (String) paramMap.get("first_counts");
	        String first_moneys = (String) paramMap.get("first_moneys");
	        String continue_counts = (String) paramMap.get("continue_counts");
	        String continue_moneys = (String) paramMap.get("continue_moneys");
	        String warehouse_ids = (String) paramMap.get("warehouse_ids");
	        String[] logistic_id = logistic_ids.split("\\|");//快递ID
	        String[] logistic_name = logistic_names.split("\\|");//快递名称
	        String[] send_to_area = send_to_areas.split("\\|");//运送范围（省ID以逗号分隔）
	        String[] send_to_area_name = send_to_area_names.split("\\|");//运送范围文字（以逗号分隔）
	        String[] first_count = first_counts.split("\\|");//首件数量
	        String[] first_money = first_moneys.split("\\|");//首件价格
	        String[] continue_count = continue_counts.split("\\|");//续件数量
	        String[] continue_money = continue_moneys.split("\\|");//续件价格
	        String[] warehouse_id = warehouse_ids.split("\\|");//所属仓库【大仓】
	        if(logistic_id.length != send_to_area.length ||  logistic_id.length != first_money.length){
	        	pr.setState(false);
	        	pr.setMessage("运费模板传递的设置条数不符!");
                return pr;
	        }
	        for(int i=0;i<logistic_id.length;i++){
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("user_id", paramMap.get("public_user_id"));
        		map.put("template_id", paramMap.get("template_id"));
        		map.put("logistics_company_id", logistic_id[i]);
        		map.put("logistics_company_name", logistic_name[i]);
        		map.put("range", send_to_area[i]);
        		map.put("first_count", first_count[i]);
        		map.put("first_money", first_money[i]);
        		map.put("continue_count", continue_count[i]);
        		map.put("continue_money", continue_money[i]);
        		map.put("range_names", send_to_area_name[i]);
        		map.put("warehouse_id", warehouse_id[i]);
        		dataList.add(map);
        	}
	        //1.删除运费模板详情
	        freightTemplateDao.deleteDetail(paramMap);
	        //2.新增运费模板详情
	        freightTemplateDao.insertDetail(dataList);
	      	//3.更新运费模板信息
	        freightTemplateDao.update(paramMap);
	        pr.setState(true);
            pr.setMessage("更新成功");
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

		return pr;
	}
	/**
	 * 删除运费模板
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeFreightTemplate(HttpServletRequest request) {
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
	        if(StringUtils.isEmpty(paramMap.get("id"))) {
	        	pr.setState(false);
	        	pr.setMessage("缺少模板ID");
                return pr;
            }
	        //判断运费模板尚有商品在使用
	      	if(freightTemplateDao.queryFreightTemplateProduct(paramMap)>0){
	      		pr.setState(false);
	            pr.setMessage("当前运费模板尚有商品在使用，删除失败！");
	      	}else{
	      		//1.删除运费模板
		        freightTemplateDao.delete(paramMap);
		        //2.删除运费模板详情
		        freightTemplateDao.deleteDetail(paramMap);
	    		pr.setState(true);
	            pr.setMessage("删除成功");
	      	}
    		
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

		return pr;
	}
	/**
	 * 运费模板详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFreightTemplateDetail(HttpServletRequest request) {
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
	        paramMap.put("start_rownum", 0);
            paramMap.put("end_rownum", 10);
            //获取模板详情数据
			if(!paramMap.containsKey("platform_type") || StringUtils.isEmpty(paramMap.get("platform_type"))){
				paramMap.put("platform_type",1);
			}
			List<Map<String,Object>> list= freightTemplateDao.queryFreightTemplateList(paramMap);
			
			if(list != null && list.size()==1){
				Map<String,Object> template=list.get(0);
				template.put("name", template.get("NAME"));
				template.put("time", template.get("TIME"));
				paramMap.put("template_id", template.get("ID"));
				paramMap.remove("id");
				// 查询运费模板详情列表
				List<Map<String,Object>> list_detail = freightTemplateDao.queryFreightTemplateDetailList(paramMap);
				template.put("template_detail", list_detail);
				Map<String, Object> company = new HashMap<String, Object>();
                company.put("type",'1');
				List<Map<String, Object>> list_logistics = logisticsCompanyDao.queryLogisticsList(company);
				template.put("logistics_detail", list_logistics);
				company.put("type",'2');
                List<Map<String, Object>> list_issuing = logisticsCompanyDao.queryLogisticsList(company);
                template.put("issuing_detail", list_issuing);
				pr.setState(true);
				pr.setMessage("查询成功");
				pr.setObj(template);
			}else {
				pr.setState(false);
				pr.setMessage("无数据");
		}
            

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 运费模板启停用设置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult freightTemplateState(HttpServletRequest request) {
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
	      	//更新运费模板启停用状态
    		if(freightTemplateDao.update(paramMap)>0){
    			pr.setState(true);
                pr.setMessage("更新成功");
    		}else{
    			pr.setState(false);
                pr.setMessage("更新失败");
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
	 * 默认模板设置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult freightTemplateDefault(HttpServletRequest request) {
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
	        if("2".equals(paramMap.get("is_default"))){
	        	paramMap.put("is_default", '1');
	        	//1.更新该条运费模板的默认状态
		        freightTemplateDao.update(paramMap);
	        }else{
	        	paramMap.put("is_default", '2');
	        	//2.更新等于模板ID的运费模板默认状态
		        freightTemplateDao.update(paramMap);
		        //3.更新不等于模板ID的运费模板默认状态
		        freightTemplateDao.updateByDefault(paramMap);
	        }
	        pr.setState(true);
        	pr.setMessage("设置成功");
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("设置失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

		return pr;
	}
	/**
	 * 可用仓库列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryWarehouseList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();

        try {
            // 查询仓库列表
            List<Map<String, Object>> warehouseList = freightTemplateDao.queryWarehouseList();
            pr.setState(true);
            pr.setMessage("查询成功");
            pr.setObj(warehouseList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

        return pr;
	}

}
