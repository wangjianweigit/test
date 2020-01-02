package com.tk.oms.analysis.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.analysis.dao.SaleTargetDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2018, Tongku
 * FileName : SaleTargetService
 * 业务员、会员销售指标相关接口
 *
 * @author liujialong
 * @version 1.00
 * @date 2018/8/28
 */
@Service("SaleTargetService")
public class SaleTargetService {
	
	@Resource
    private SaleTargetDao saleTargetDao;
	@Value("${jdbc_user}")
	private String jdbc_user;
	
	/**
     * 获取业务员销售指标列表数据
     * @param request
     * @return
     */
    public GridResult queryYwySaleTargetList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        List<String> temList = new ArrayList<String>();
        Map<String,Object> retMap = new HashMap<String,Object>();
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
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
            if((!StringUtils.isEmpty(paramMap.get("approval_state")))&&paramMap.get("approval_state") instanceof String){
            	paramMap.put("approval_state",(paramMap.get("approval_state")+"").split(","));
			}
            //查询销售人员关联的业务经理和门店
        	List<Map<String, Object>> userYwjlAndStorelist=saleTargetDao.queryUserYwjlAndStoreData(paramMap);
        	if(userYwjlAndStorelist!=null&&userYwjlAndStorelist.size()>0){
        		if(!StringUtils.isEmpty(paramMap.get("ywjl_user_id"))){
	            	for(Map<String, Object> map:userYwjlAndStorelist){
		            		List managerUserList=Arrays.asList(map.get("MANAGER_USER_ID").toString().split(","));
		            		if(managerUserList.contains(paramMap.get("ywjl_user_id").toString())){
		            			if(!StringUtils.isEmpty(paramMap.get("store_id"))){
		    	            		List storeList=Arrays.asList(map.get("STORE_ID").toString().split(","));
		    	            		if(storeList.contains(paramMap.get("store_id").toString())){
		    	            			temList.add(map.get("ID").toString());
		    	            		}
		                		}else{
		                			temList.add(map.get("ID").toString());
		                		}
		            		}
            		}
            	}
            	if(temList!=null && temList.size()>0){
            		paramMap.put("list", temList);
            	}else{
            		//如果业务经理或门店数据未查询到数据到则为空
            		if(!StringUtils.isEmpty(paramMap.get("store_id"))||!StringUtils.isEmpty(paramMap.get("ywjl_user_id"))){
            			temList.add("0");
            			paramMap.put("list", temList);
            		}
            	}
        	}
            int count=saleTargetDao.queryYwySaleTargetCount(paramMap);
			List<Map<String, Object>> list = saleTargetDao.queryYwySaleTargetList(paramMap);
			Map<String, Object> totalMap=saleTargetDao.queryTotalAnnualTarget(paramMap);
			retMap.put("list", list);
			retMap.put("totalData", totalMap.get("TOTAL_ANNUAL_TARGET"));
			if(list!=null&&list.size()>0){
				gr.setMessage("查询成功!");
			}else{
				gr.setMessage("无数据!");
			}
			gr.setObj(retMap);
            gr.setState(true);
            gr.setTotal(count);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 获取业务员列表数据
     * @param request
     * @return
     */
    public GridResult queryUserTypeList(HttpServletRequest request) {
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
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if(StringUtils.isEmpty(paramMap.get("year"))){
            	gr.setState(false);
                gr.setMessage("缺少参数year");
                return gr;
            }
            if(StringUtils.isEmpty(paramMap.get("ywy_id"))){
            	paramMap.remove("ywy_id");
            }else{
            	paramMap.put("ywy_id",(paramMap.get("ywy_id")+"").split(","));
            }
            int count=saleTargetDao.queryUserTypeCount(paramMap);
			List<Map<String, Object>> list = saleTargetDao.queryUserTypeList(paramMap);
			if(list!=null&&list.size()>0){
				gr.setMessage("查询成功!");
				gr.setObj(list);
			}else{
				gr.setMessage("无数据!");
			}
            gr.setState(true);
            gr.setTotal(count);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 获取会员销售指标列表数据
     * @param request
     * @return
     */
    public GridResult queryHySaleTargetList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String,Object> retMap = new HashMap<String,Object>();
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
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(paramMap.get("approval_state")))&&paramMap.get("approval_state") instanceof String){
            	paramMap.put("approval_state",(paramMap.get("approval_state")+"").split(","));
			}
            int count=saleTargetDao.queryHySaleTargetCount(paramMap);
			List<Map<String, Object>> list = saleTargetDao.queryHySaleTargetList(paramMap);
			Map<String, Object> totalMap=saleTargetDao.queryHyTotalAnnualTarget(paramMap);
			retMap.put("list", list);
			retMap.put("totalData", totalMap.get("TOTAL_ANNUAL_TARGET"));
			if(list!=null&&list.size()>0){
				gr.setMessage("查询成功!");
			}else{
				gr.setMessage("无数据!");
			}
			gr.setObj(retMap);
            gr.setState(true);
            gr.setTotal(count);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 新增业务员销售指标
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addYwy(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			List<Map<String, Object>> list=(List<Map<String, Object>>)paramMap.get("param");
			for(Map<String, Object> map:list){
				map.put("CREATE_USER_ID", paramMap.get("public_user_id"));
				if(StringUtils.isEmpty(map.get("YEAR"))){
					pr.setState(false);
					pr.setMessage("缺少参数YEAR");
					return pr;
				}
				if(StringUtils.isEmpty(map.get("ANNUAL_TARGET"))){
					pr.setState(false);
					pr.setMessage("缺少参数ANNUAL_TARGET");
					return pr;
				}
				List<Map<String, Object>> detailList=(List<Map<String, Object>>)map.get("list");
				if(detailList==null ||detailList.size()==0){
					pr.setState(false);
					pr.setMessage("缺少参数年度指标详情");
					return pr;
				}
				//新增业务员销售指标主表信息
				if(saleTargetDao.insertSaleTargetYwy(map) <= 0){
					pr.setState(false);
					throw new RuntimeException("新增业务员销售指标主表信息失败");
				}
				//新增业务员销售指标详表信息
				for(Map<String, Object> dettailMap:detailList){
					dettailMap.put("approval_state", 1);
					dettailMap.put("sale_target_ywy_id", map.get("id"));
					dettailMap.put("month_value", Integer.parseInt(dettailMap.get("month_value")+""));
				}
				if(saleTargetDao.insertSaleTargetYwyDetail(detailList) <= 0){
					pr.setState(false);
					throw new RuntimeException("新增业务员销售指标详表信息失败");
				}
			}
			pr.setState(true);
			pr.setMessage("新增成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 新增会员销售指标
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addHy(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			List<Map<String, Object>> list=(List<Map<String, Object>>)paramMap.get("param");
			for(Map<String, Object> map:list){
				map.put("CREATE_USER_ID", paramMap.get("public_user_id"));
				if(StringUtils.isEmpty(map.get("YEAR"))){
					pr.setState(false);
					pr.setMessage("缺少参数YEAR");
					return pr;
				}
				if(StringUtils.isEmpty(map.get("ANNUAL_TARGET"))){
					pr.setState(false);
					pr.setMessage("缺少参数ANNUAL_TARGET");
					return pr;
				}
				List<Map<String, Object>> detailList=(List<Map<String, Object>>)map.get("list");
				if(detailList==null ||detailList.size()==0){
					pr.setState(false);
					pr.setMessage("缺少参数年度指标详情");
					return pr;
				}
				//新增会员销售指标主表信息
				if(saleTargetDao.insertSaleTargetHy(map) <= 0){
					pr.setState(false);
					throw new RuntimeException("新增会员销售指标主表信息失败");
				}
				//新增会员销售指标详表信息
				for(Map<String, Object> dettailMap:detailList){
					dettailMap.put("approval_state", 1);
					dettailMap.put("sale_target_hy_id", map.get("id"));
					dettailMap.put("month_value", Integer.parseInt(dettailMap.get("month_value")+""));
				}
				if(saleTargetDao.insertSaleTargetHyDetail(detailList) <= 0){
					pr.setState(false);
					throw new RuntimeException("新增会员销售指标详表信息失败");
				}
			}
			pr.setState(true);
			pr.setMessage("新增成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 编辑业务员销售指标
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editYwy(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			List<Map<String, Object>> list=(List<Map<String, Object>>)paramMap.get("param");
			for(Map<String, Object> map:list){
				if(StringUtils.isEmpty(map.get("YEAR"))){
					pr.setState(false);
					pr.setMessage("缺少参数YEAR");
					return pr;
				}
				if(StringUtils.isEmpty(map.get("ANNUAL_TARGET"))){
					pr.setState(false);
					pr.setMessage("缺少参数ANNUAL_TARGET");
					return pr;
				}
				List<Map<String, Object>> detailList=(List<Map<String, Object>>)map.get("list");
				if(detailList==null ||detailList.size()==0){
					pr.setState(false);
					pr.setMessage("缺少参数年度指标详情");
					return pr;
				}
				Map<String, Object> info=saleTargetDao.querySaleTargetsYwyInfo(map);
				if(info==null){
					pr.setState(false);
					pr.setMessage("数据不存在");
					return pr;
				}
				//编辑业务员销售指标主表信息
				if(saleTargetDao.updateSaleTargetYwy(map) <= 0){
					pr.setState(false);
					throw new RuntimeException("编辑业务员销售指标主表信息失败");
				}
				int flag=0;
				if(Integer.parseInt(info.get("MAX_APPROVAL_STATE")+"")==1){
					flag=1;
				}else{
					flag=2;
				}
				//编辑业务员销售指标详表信息
				for(Map<String, Object> dettailMap:detailList){
					if(flag==1){
						dettailMap.put("approval_state", 1);
					}else if(flag==2){
						Calendar cale = null;  
				        cale = Calendar.getInstance();
						int month = cale.get(Calendar.MONTH) + 1;
						if(Integer.parseInt(dettailMap.get("month_type")+"")<=month){
							dettailMap.put("approval_state", 2);
						}else{
							dettailMap.put("approval_state", 1);
						}
					}
					dettailMap.put("sale_target_ywy_id", map.get("ID"));
					dettailMap.put("month_value", Integer.parseInt(dettailMap.get("month_value")+""));
				}
				//删除原详表信息
				saleTargetDao.deleteSaleTargetYwyDetail(map);
				if(saleTargetDao.insertSaleTargetYwyDetail(detailList) <= 0){
					pr.setState(false);
					throw new RuntimeException("编辑业务员销售指标详表信息失败");
				}
			}
			pr.setState(true);
			pr.setMessage("编辑成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 编辑会员销售指标
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editHy(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			List<Map<String, Object>> list=(List<Map<String, Object>>)paramMap.get("param");
			for(Map<String, Object> map:list){
				if(StringUtils.isEmpty(map.get("YEAR"))){
					pr.setState(false);
					pr.setMessage("缺少参数YEAR");
					return pr;
				}
				if(StringUtils.isEmpty(map.get("ANNUAL_TARGET"))){
					pr.setState(false);
					pr.setMessage("缺少参数ANNUAL_TARGET");
					return pr;
				}
				List<Map<String, Object>> detailList=(List<Map<String, Object>>)map.get("list");
				if(detailList==null ||detailList.size()==0){
					pr.setState(false);
					pr.setMessage("缺少参数年度指标详情");
					return pr;
				}
				Map<String, Object> info=saleTargetDao.querySaleTargetsHyInfo(map);
				if(info==null){
					pr.setState(false);
					pr.setMessage("数据不存在");
					return pr;
				}
				//编辑会员销售指标主表信息
				if(saleTargetDao.updateSaleTargetHy(map) <= 0){
					pr.setState(false);
					throw new RuntimeException("编辑会员销售指标主表信息失败");
				}
				int flag=0;
				if(Integer.parseInt(info.get("MAX_APPROVAL_STATE")+"")==1){
					flag=1;
				}else{
					flag=2;
				}
				//编辑会员销售指标详表信息
				for(Map<String, Object> dettailMap:detailList){
					if(flag==1){
						dettailMap.put("approval_state", 1);
					}else if(flag==2){
						Calendar cale = null;  
				        cale = Calendar.getInstance();
						int month = cale.get(Calendar.MONTH) + 1;
						if(Integer.parseInt(dettailMap.get("month_type")+"")<=month){
							dettailMap.put("approval_state", 2);
						}else{
							dettailMap.put("approval_state", 1);
						}
					}
					dettailMap.put("sale_target_hy_id", map.get("ID"));
					dettailMap.put("month_value", Integer.parseInt(dettailMap.get("month_value")+""));
				}
				//删除原详表信息
				saleTargetDao.deleteSaleTargetHyDetail(map);
				if(saleTargetDao.insertSaleTargetHyDetail(detailList) <= 0){
					pr.setState(false);
					throw new RuntimeException("编辑会员销售指标详表信息失败");
				}
			}
			pr.setState(true);
			pr.setMessage("编辑成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 审批业务员销售指标
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult approvalYwy(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if((StringUtils.isEmpty(paramMap.get("sale_target_id")))){
				pr.setState(false);
				pr.setMessage("缺少参数sale_target_id");
				return pr;
			}
			if((StringUtils.isEmpty(paramMap.get("approval_state")))){
				pr.setState(false);
				pr.setMessage("缺少参数approval_state");
				return pr;
			}
			List<String> sale_target_id=Arrays.asList(paramMap.get("sale_target_id").toString().split(","));
			paramMap.put("sale_target_id", sale_target_id);
			if(saleTargetDao.approvalYwySaleTarget(paramMap)<=0){
				pr.setState(false);
				throw new RuntimeException("审批失败");
			}
			//修改详情表审批状态
			Calendar cale = null;  
	        cale = Calendar.getInstance();
			int month_type = cale.get(Calendar.MONTH) + 1;
			paramMap.put("month_type", month_type);
			if(saleTargetDao.approvalYwySaleTargetDetail(paramMap)<=0){
				pr.setState(false);
				throw new RuntimeException("审批失败");
			}
			pr.setState(true);
			pr.setMessage("审批成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
     * 审批会员销售指标
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult approvalHy(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if((StringUtils.isEmpty(paramMap.get("sale_target_hy_id")))){
				pr.setState(false);
				pr.setMessage("缺少参数sale_target_hy_id");
				return pr;
			}
			if((StringUtils.isEmpty(paramMap.get("approval_state")))){
				pr.setState(false);
				pr.setMessage("缺少参数approval_state");
				return pr;
			}
			List<String> sale_target_id=Arrays.asList(paramMap.get("sale_target_hy_id").toString().split(","));
			paramMap.put("sale_target_hy_id", sale_target_id);
			if(saleTargetDao.approvalHySaleTarget(paramMap)<=0){
				pr.setState(false);
				throw new RuntimeException("审批失败");
			}
			//修改详情表审批状态
			Calendar cale = null;  
	        cale = Calendar.getInstance();
			int month_type = cale.get(Calendar.MONTH) + 1;
			paramMap.put("month_type", month_type);
			if(saleTargetDao.approvalHySaleTargetDetail(paramMap)<=0){
				pr.setState(false);
				throw new RuntimeException("审批失败");
			}
			pr.setState(true);
			pr.setMessage("审批成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	
	/**
     * 业务员销售指标详情(支持单个和批量查看)
     * @param request
     * @return
     */
    public GridResult ywyDetail(HttpServletRequest request) {
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
            if((!StringUtils.isEmpty(paramMap.get("sale_target_id")))&&paramMap.get("sale_target_id") instanceof String){
            	paramMap.put("sale_target_id",(paramMap.get("sale_target_id")+"").split(","));
			}
			List<Map<String, Object>> list = saleTargetDao.queryYwySaleTargetDetail(paramMap);
			for(Map<String, Object> map:list){
				map.put("sale_target_id", map.get("ID"));
				//根据当前业务员指标id查询详细数据
				List<Map<String, Object>> detailList=saleTargetDao.queryYwySaleTargetDetailList(map);
				for(Map<String, Object> detailMap:detailList){
					map.put(detailMap.get("MONTH_TYPE")+"", detailMap.get("MONTH_VALUE"));
					map.put("DETAIL_APPROVAL_STATE_"+detailMap.get("MONTH_TYPE"), detailMap.get("DETAIL_APPROVAL_STATE"));
				}
			}
			if(list!=null&&list.size()>0){
				gr.setMessage("查询成功!");
				gr.setObj(list);
			}else{
				gr.setMessage("无数据!");
			}
            gr.setState(true);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 会员销售指标详情(支持单个和批量查看)
     * @param request
     * @return
     */
    public GridResult hyDetail(HttpServletRequest request) {
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
            if((!StringUtils.isEmpty(paramMap.get("sale_target_hy_id")))&&paramMap.get("sale_target_hy_id") instanceof String){
            	paramMap.put("sale_target_hy_id",(paramMap.get("sale_target_hy_id")+"").split(","));
			}
			List<Map<String, Object>> list = saleTargetDao.queryHySaleTargetDetail(paramMap);
			for(Map<String, Object> map:list){
				map.put("sale_target_hy_id", map.get("ID"));
				//根据当前业务员指标id查询详细数据
				List<Map<String, Object>> detailList=saleTargetDao.queryHySaleTargetDetailList(map);
				for(Map<String, Object> detailMap:detailList){
					map.put(detailMap.get("MONTH_TYPE")+"", detailMap.get("MONTH_VALUE"));
					map.put("DETAIL_APPROVAL_STATE_"+detailMap.get("MONTH_TYPE"), detailMap.get("DETAIL_APPROVAL_STATE"));
				}
			}
			if(list!=null&&list.size()>0){
				gr.setMessage("查询成功!");
				gr.setObj(list);
			}else{
				gr.setMessage("无数据!");
			}
            gr.setState(true);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 查询已设置用户指标的列表数据
     * @param request
     * @return
     */
    public GridResult queryUserTypeOption(HttpServletRequest request) {
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
            gr.setObj(saleTargetDao.queryUserTypeOption(paramMap));
            gr.setState(true);
            gr.setMessage("查询成功");
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 查询当前指标完成人在当前年份设置的销售指标
     * @param request
     * @return
     */
    public GridResult queryUserSalesTarget(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("year"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数year");
				return gr;
			}
            if(StringUtils.isEmpty(paramMap.get("user_id"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数user_id");
				return gr;
			}
            gr.setObj(saleTargetDao.queryUserSalesTarget(paramMap));
            gr.setState(true);
            gr.setMessage("查询成功");
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
     * 查询会员库
     * @param request
     * @return
     */
    public GridResult queryMemberLibrary(HttpServletRequest request) {
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
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if(StringUtils.isEmpty(paramMap.get("sys_user_id"))){
            	gr.setState(false);
                gr.setMessage("缺少参数sys_user_id");
                return gr;
            }
            if(StringUtils.isEmpty(paramMap.get("year"))){
            	gr.setState(false);
                gr.setMessage("缺少参数year");
                return gr;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
            if((!StringUtils.isEmpty(paramMap.get("user_id")))&&paramMap.get("user_id") instanceof String){
            	paramMap.put("user_id",(paramMap.get("user_id")+"").split(","));
			}
            int count=saleTargetDao.queryMemberLibraryCount(paramMap);
			List<Map<String, Object>> list = saleTargetDao.queryMemberLibraryList(paramMap);
			if(list!=null&&list.size()>0){
				gr.setMessage("查询成功!");
				gr.setObj(list);
			}else{
				gr.setMessage("无数据!");
			}
            gr.setState(true);
            gr.setTotal(count);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    
    /**
	 * 销售情况完成情况统计列表(按人员)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySaleTargetTotalYwyListForPage(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("month_types"))||StringUtils.isEmpty(paramMap.get("year"))) {
				gr.setState(false);
				gr.setMessage("缺少时间参数");
				return gr;
			}
			Map<String,Object> retMap = new HashMap<String,Object>();
			if((!StringUtils.isEmpty(paramMap.get("month_types")))&&paramMap.get("month_types") instanceof String){
				paramMap.put("month_types",(paramMap.get("month_types")+"").split(","));
			}
			
			paramMap.put("jdbc_user", jdbc_user);
			//数量
			int total = saleTargetDao.querySaleTargetTotalYwyCount(paramMap);
			List<Map<String, Object>> list=null;
			//需要查询的用户列表
			List<String> userList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				if("PERFORM_MONEY".equals(paramMap.get("sort").toString())||"PERFORM_RATIO".equals(paramMap.get("sort").toString())
						||"SUM_MOUTH_VALUE".equals(paramMap.get("sort").toString())||"TARGET_RATIO".equals(paramMap.get("sort").toString())){
					//任务指标、指标占比、任务完成量、任务完成率---获取排序后的用户信息
					userList=saleTargetDao.querySaleTargetTotalYwyListByPerformMoney(paramMap);
				}
				if("TODAY_COUNT".equals(paramMap.get("sort").toString())||"TODAY_MONEY".equals(paramMap.get("sort").toString())){
					//今日销售量---获取排序后的用户信息
					userList=saleTargetDao.querySaleTargetTotalYwyListByTodayCount(paramMap);
				}
			}else{
				//查询默认排序的人员
				userList=saleTargetDao.querySaleTargetTotalYwyListByDefault(paramMap);
			}
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = saleTargetDao.querySaleTargetTotalYwyList(paramMap);
			}
			if (list != null && list.size() > 0) {
				
				List<Map<String,Object>> list_ywy_perform = saleTargetDao.queryYwyPerformMoney(paramMap);//数据获取-完成量(按人员)
				
				List<Map<String,Object>> list_ywy_refund = saleTargetDao.queryYwyRefundList(paramMap);//数据获取-退款数据(按人员)
				
				List<Map<String,Object>> list_ywy_return = saleTargetDao.queryYwyReturnList(paramMap);//数据获取-退货数据(按人员)
				
				List<Map<String,Object>> list_ywyToday = saleTargetDao.queryYwyTodayMoneyAndCount(paramMap);//数据获取-今天销额销量(按人员)
				
				List<Map<String,Object>> list_ywy_today_refund = saleTargetDao.queryYwyTodayRefundList(paramMap);//数据获取-今天退款数据(按人员)
				
				List<Map<String,Object>> list_ywy_today_return = saleTargetDao.queryYwyTodayReturnList(paramMap);//数据获取-今天退货数据(按人员)
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					float perform_money = 0;
					float refund_order_product_money = 0;
					float returns_order_product_money = 0;
					
					float today_count = 0;
					float today_refund_count = 0;
					float today_return_count = 0;
					
					float today_money = 0;
					float today_refund_money = 0;
					float today_return_money = 0;
					String user_name = tempMap.get("USER_NAME").toString();
					for(Map<String, Object> m : list_ywy_perform) {
						if(!StringUtils.isEmpty(m.get("USER_NAME"))){
							if(user_name.equals(m.get("USER_NAME"))) {
								perform_money = m.get("PERFORM_MONEY")==null?0:Float.parseFloat(m.get("PERFORM_MONEY").toString());
								break;
							}
						}
					}
					
					for(Map<String, Object> m : list_ywy_refund) {
						if(user_name.equals(m.get("USER_NAME"))) {
							refund_order_product_money = m.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(m.get("REFUND_ORDER_PRODUCT_MONEY").toString());
							break;
						}
					}
					
					for(Map<String, Object> m : list_ywy_return) {
						if(user_name.equals(m.get("USER_NAME"))) {
							returns_order_product_money = m.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(m.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
							break;
						}
					}
					
					for(Map<String, Object> m : list_ywyToday) {
						if(user_name.equals(m.get("USER_NAME"))) {
							today_count = m.get("TODAY_COUNT")==null?0:Float.parseFloat(m.get("TODAY_COUNT").toString());
							today_money = m.get("TODAY_MONEY")==null?0:Float.parseFloat(m.get("TODAY_MONEY").toString());
							break;
						}
					}
					
					for(Map<String, Object> m : list_ywy_today_refund) {
						if(user_name.equals(m.get("USER_NAME"))) {
							today_refund_count = m.get("REFUND_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(m.get("REFUND_ORDER_PRODUCT_COUNT").toString());
							today_refund_money = m.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(m.get("REFUND_ORDER_PRODUCT_MONEY").toString());
							break;
						}
					}
					
					for(Map<String, Object> m : list_ywy_today_return) {
						if(user_name.equals(m.get("USER_NAME"))) {
							today_return_count = m.get("RETURNS_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(m.get("RETURNS_ORDER_PRODUCT_COUNT").toString());
							today_return_money = m.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(m.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
							break;
						}
					}
					
					tempMap.put("PERFORM_MONEY", perform_money-refund_order_product_money-returns_order_product_money);
					tempMap.put("TODAY_COUNT", today_count-today_refund_count-today_return_count);
					tempMap.put("TODAY_MONEY", today_money-today_refund_money-today_return_money);
				}
				
				//汇总
				Map<String, Object> map = saleTargetDao.querySaleTargetTotalYwySummary(paramMap);
				
				Map<String, Object> pfMap = saleTargetDao.queryPerformMoneyYwySummary(paramMap);
				Map<String, Object> tmcMap = saleTargetDao.queryTodayMoneyAndCountYwySummary(paramMap);
				Map<String, Object> sum_refund_map = saleTargetDao.queryRefundYwySummary(paramMap);
				Map<String, Object> sum_return_map = saleTargetDao.queryReturnYwySummary(paramMap);
				Map<String, Object> sum_today_refund_map = saleTargetDao.queryTodayRefundYwySummary(paramMap);
				Map<String, Object> sum_today_return_map = saleTargetDao.queryTodayReturnYwySummary(paramMap);
				
				
				float sum_perform_money = pfMap.get("PERFORM_MONEY")==null?0:Float.parseFloat(pfMap.get("PERFORM_MONEY").toString());
				float sum_refund_money = sum_refund_map.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_refund_map.get("REFUND_ORDER_PRODUCT_MONEY").toString());
				float sum_return_money = sum_return_map.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_return_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
				
				float sum_today_count = tmcMap.get("TODAY_COUNT")==null?0:Float.parseFloat(tmcMap.get("TODAY_COUNT").toString());
				float sum_today_refund_count = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT").toString());
				float sum_today_return_count = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT").toString());
						
				float sum_today_money = tmcMap.get("TODAY_MONEY")==null?0:Float.parseFloat(tmcMap.get("TODAY_MONEY").toString());
				float sum_today_refund_money = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY").toString());
				float sum_today_return_money = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
				
				map.put("PERFORM_MONEY", sum_perform_money-sum_refund_money-sum_return_money);
				map.put("TODAY_COUNT", sum_today_count-sum_today_refund_count-sum_today_return_count);
				map.put("TODAY_MONEY", sum_today_money-sum_today_refund_money-sum_today_return_money);
				if(StringUtils.isEmpty(paramMap.get("ywjl_user_id"))) {
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("jdbc_user", jdbc_user);
					tmcMap = saleTargetDao.queryTodayMoneyAndCountYwySummary(m);
					sum_today_refund_map = saleTargetDao.queryTodayRefundYwySummary(m);
					sum_today_return_map = saleTargetDao.queryTodayReturnYwySummary(m);
					
					sum_today_count = tmcMap.get("TODAY_COUNT")==null?0:Float.parseFloat(tmcMap.get("TODAY_COUNT").toString());
					sum_today_refund_count = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT").toString());
					sum_today_return_count = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT").toString());
							
					sum_today_money = tmcMap.get("TODAY_MONEY")==null?0:Float.parseFloat(tmcMap.get("TODAY_MONEY").toString());
					sum_today_refund_money = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY").toString());
					sum_today_return_money = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
				}
				retMap.put("totalData",map);
				retMap.put("today_total_count", sum_today_count-sum_today_refund_count-sum_today_return_count);//获取今日销售总量
				retMap.put("today_total_money", sum_today_money-sum_today_refund_money-sum_today_return_money);//获取今日销售总额
				retMap.put("list", list);
				gr.setMessage("获取数据成功");
				gr.setObj(retMap);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(total);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return gr;
	}
	
	/**
	 * 销售情况完成情况统计列表(按会员)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySaleTargetTotalHyListForPage(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("month_types"))||StringUtils.isEmpty(paramMap.get("year"))) {
				gr.setState(false);
				gr.setMessage("缺少时间参数");
				return gr;
			}
			Map<String,Object> retMap = new HashMap<String,Object>();
			if((!StringUtils.isEmpty(paramMap.get("month_types")))&&paramMap.get("month_types") instanceof String){
				paramMap.put("month_types",(paramMap.get("month_types")+"").split(","));
			}
			paramMap.put("jdbc_user", jdbc_user);
			//数量
			int total = saleTargetDao.querySaleTargetTotalHyCount(paramMap);
			//任务指标
			String file_names_smv = ":SUM_MOUTH_VALUE:";
			//指标占比
			String file_names_tr = ":TARGET_RATIO:";
			//任务完成量
			String file_names_pm = ":PERFORM_MONEY:";
			//任务完成率
			String file_names_pr = ":PERFORM_RATIO:";
			//今日销售量
			String file_names_tc = ":TODAY_COUNT:";
			//今日销售额
			String file_names_tm = ":TODAY_MONEY:";
			//今日销售量占比
			String file_names_tcr = ":TOTAL_COUNT_RATIO:";
			//今日销售额占比
			String file_names_tmr = ":TODAY_MONEY_RATIO:";
			
			Map<String, Object> tmcMap = saleTargetDao.queryTodayMoneyAndCountHySummary(paramMap);
			Map<String, Object> sum_today_refund_map = saleTargetDao.queryTodayRefundHySummary(paramMap);
			Map<String, Object> sum_today_return_map = saleTargetDao.queryTodayReturnHySummary(paramMap);
			
			float sum_today_count = tmcMap.get("TODAY_COUNT")==null?0:Float.parseFloat(tmcMap.get("TODAY_COUNT").toString());
			float sum_today_refund_count = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT").toString());
			float sum_today_return_count = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT").toString());
					
			float sum_today_money = tmcMap.get("TODAY_MONEY")==null?0:Float.parseFloat(tmcMap.get("TODAY_MONEY").toString());
			float sum_today_refund_money = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY").toString());
			float sum_today_return_money = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
			
			
			List<Map<String, Object>> list=null;
			//需要查询的用户列表
			List<String> userList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_smv.indexOf(sort)!=-1) {
					//任务指标---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHySumMouthValue_User(paramMap);
				}else if(file_names_tr.indexOf(sort)!=-1) {
					//指标占比---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyTargetRatio_User(paramMap);
				}else if(file_names_pm.indexOf(sort)!=-1) {
					//任务完成量---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyPerformMoney_User(paramMap);
				}else if(file_names_pr.indexOf(sort)!=-1) {
					//任务完成率---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyPerformRatio_User(paramMap);
				}else if(file_names_tc.indexOf(sort)!=-1) {
					//今日销售量---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyTodayCount_User(paramMap);
				}else if(file_names_tm.indexOf(sort)!=-1) {
					//今日销售额---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyTodayMoney_User(paramMap);
				}else if(file_names_tcr.indexOf(sort)!=-1) {
					paramMap.put("today_total_count", sum_today_count-sum_today_refund_count-sum_today_return_count);
					//今日销售量占比---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyTodayCountRatio_User(paramMap);
				}else if(file_names_tmr.indexOf(sort)!=-1) {
					paramMap.put("today_total_money", sum_today_money-sum_today_refund_money-sum_today_return_money);
					//今日销售额占比---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryHyTodayMoneyRatio_User(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的用户信息(按会员)
				userList=saleTargetDao.querySaleTargetTotalHyListBy_Default(paramMap);
			}
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = saleTargetDao.querySaleTargetTotalHyList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				List<Map<String,Object>> list_hy_perform = saleTargetDao.queryHyPerformMoney(paramMap);//数据获取-完成量(按会员)
				Map<String,Object> hy_perform_money_map = list2Map(list_hy_perform,"USER_NAME","PERFORM_MONEY");
				
				List<Map<String,Object>> list_hy_refund = saleTargetDao.queryHyRefundList(paramMap);//数据获取-退款数据(按会员)
				Map<String,Object> hy_refund_money_map = list2Map(list_hy_refund,"USER_NAME","REFUND_ORDER_PRODUCT_MONEY");
				
				List<Map<String,Object>> list_hy_return = saleTargetDao.queryHyReturnList(paramMap);//数据获取-退货数据(按会员)
				Map<String,Object> hy_return_money_map = list2Map(list_hy_return,"USER_NAME","RETURNS_ORDER_PRODUCT_MONEY");
				
				List<Map<String,Object>> list_hyToday = saleTargetDao.queryHyTodayMoneyAndCount(paramMap);//数据获取-今天销额销量(按会员)
				Map<String,Object> hy_today_count_map = list2Map(list_hyToday,"USER_NAME","TODAY_COUNT");
				Map<String,Object> hy_today_money_map = list2Map(list_hyToday,"USER_NAME","TODAY_MONEY");
				
				List<Map<String,Object>> list_hy_today_refund = saleTargetDao.queryHyTodayRefundList(paramMap);//数据获取-今天退款数据(按会员)
				Map<String,Object> hy_today_refund_count_map = list2Map(list_hy_today_refund,"USER_NAME","REFUND_ORDER_PRODUCT_COUNT");
				Map<String,Object> hy_today_refund_money_map = list2Map(list_hy_today_refund,"USER_NAME","REFUND_ORDER_PRODUCT_MONEY");
				
				List<Map<String,Object>> list_hy_today_return = saleTargetDao.queryHyTodayReturnList(paramMap);//数据获取-今天退货数据(按会员)
				Map<String,Object> hy_today_return_count_map = list2Map(list_hy_today_return,"USER_NAME","RETURNS_ORDER_PRODUCT_COUNT");
				Map<String,Object> hy_today_return_money_map = list2Map(list_hy_today_return,"USER_NAME","RETURNS_ORDER_PRODUCT_MONEY");
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String user_name = tempMap.get("ID").toString();
					float perform_money = hy_perform_money_map.get(user_name)==null?0:Float.parseFloat(hy_perform_money_map.get(user_name).toString());
					float refund_order_product_money = hy_refund_money_map.get(user_name)==null?0:Float.parseFloat(hy_refund_money_map.get(user_name).toString());
					float returns_order_product_money = hy_return_money_map.get(user_name)==null?0:Float.parseFloat(hy_return_money_map.get(user_name).toString());
					
					float today_count = hy_today_count_map.get(user_name)==null?0:Float.parseFloat(hy_today_count_map.get(user_name).toString());
					float today_refund_count = hy_today_refund_count_map.get(user_name)==null?0:Float.parseFloat(hy_today_refund_count_map.get(user_name).toString());
					float today_return_count = hy_today_return_count_map.get(user_name)==null?0:Float.parseFloat(hy_today_return_count_map.get(user_name).toString());
					
					float today_money = hy_today_money_map.get(user_name)==null?0:Float.parseFloat(hy_today_money_map.get(user_name).toString());
					float today_refund_money = hy_today_refund_money_map.get(user_name)==null?0:Float.parseFloat(hy_today_refund_money_map.get(user_name).toString());
					float today_return_money = hy_today_return_money_map.get(user_name)==null?0:Float.parseFloat(hy_today_return_money_map.get(user_name).toString());
					
					tempMap.put("PERFORM_MONEY", perform_money-refund_order_product_money-returns_order_product_money);
					tempMap.put("TODAY_COUNT", today_count-today_refund_count-today_return_count);
					tempMap.put("TODAY_MONEY", today_money-today_refund_money-today_return_money);
				}
				//汇总
				Map<String, Object> map = saleTargetDao.querySaleTargetTotalHySummary(paramMap);
				
				Map<String, Object> pfMap = saleTargetDao.queryPerformMoneyHySummary(paramMap);
				Map<String, Object> sum_refund_map = saleTargetDao.queryRefundHySummary(paramMap);
				Map<String, Object> sum_return_map = saleTargetDao.queryReturnHySummary(paramMap);
				
				
				float sum_perform_money = pfMap.get("PERFORM_MONEY")==null?0:Float.parseFloat(pfMap.get("PERFORM_MONEY").toString());
				float sum_refund_money = sum_refund_map.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_refund_map.get("REFUND_ORDER_PRODUCT_MONEY").toString());
				float sum_return_money = sum_return_map.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_return_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
				
				map.put("PERFORM_MONEY", sum_perform_money-sum_refund_money-sum_return_money);
				map.put("TODAY_COUNT", sum_today_count-sum_today_refund_count-sum_today_return_count);
				map.put("TODAY_MONEY", sum_today_money-sum_today_refund_money-sum_today_return_money);
				if(StringUtils.isEmpty(paramMap.get("ywjl_user_id"))) {
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("jdbc_user", jdbc_user);
					tmcMap = saleTargetDao.queryTodayMoneyAndCountHySummary(m);
					sum_today_refund_map = saleTargetDao.queryTodayRefundHySummary(m);
					sum_today_return_map = saleTargetDao.queryTodayReturnHySummary(m);
					
					sum_today_count = tmcMap.get("TODAY_COUNT")==null?0:Float.parseFloat(tmcMap.get("TODAY_COUNT").toString());
					sum_today_refund_count = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_COUNT").toString());
					sum_today_return_count = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_COUNT").toString());
							
					sum_today_money = tmcMap.get("TODAY_MONEY")==null?0:Float.parseFloat(tmcMap.get("TODAY_MONEY").toString());
					sum_today_refund_money = sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_refund_map.get("REFUND_ORDER_PRODUCT_MONEY").toString());
					sum_today_return_money = sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY")==null?0:Float.parseFloat(sum_today_return_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
				}
				retMap.put("totalData",map);
				retMap.put("today_total_count", sum_today_count-sum_today_refund_count-sum_today_return_count);//获取今日销售总量
				retMap.put("today_total_money", sum_today_money-sum_today_refund_money-sum_today_return_money);//获取今日销售总额
				retMap.put("list", list);
				
				gr.setMessage("获取数据成功");
				gr.setObj(retMap);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("SUM_MOUTH_VALUE",0);
				map.put("PERFORM_MONEY",0);
				map.put("TODAY_COUNT",0);
				map.put("TODAY_MONEY",0);
				retMap.put("totalData",map);
				retMap.put("today_total_count", 0);//获取今日销售总量
				retMap.put("today_total_money", 0);//获取今日销售总额
				retMap.put("list",list);
				gr.setMessage("无数据");
				gr.setObj(retMap);
			}
			gr.setState(true);
			gr.setTotal(total);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return gr;
	}
	
	/**
	 * 重点客户跟进表(按会员)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySaleTargetTotalEmpHyListForPage(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("month_type"))||StringUtils.isEmpty(paramMap.get("year"))) {
				gr.setState(false);
				gr.setMessage("缺少时间参数");
				return gr;
			}
			
			if((!StringUtils.isEmpty(paramMap.get("user_state")))&&paramMap.get("user_state") instanceof String){
				paramMap.put("user_state",(paramMap.get("user_state")+"").split(","));
			}
			int maxDate = getMonthLastDay(Integer.parseInt(paramMap.get("year").toString()),Integer.parseInt(paramMap.get("month_type").toString()));
			List<String> time_list = new ArrayList<String>();
			for(int i= 0; i < maxDate;i++) {
				if(i<=8) {
					time_list.add("0"+(i+1)+"");
				}else {
					time_list.add(i+1+"");
				}
			}
			paramMap.put("jdbc_user", jdbc_user);
			//数量
			int total = saleTargetDao.querySaleTargetTotalEmpHyCount(paramMap);
			//年度指标
			String file_names_at = ":ANNUAL_TARGET:";
			//年度完成量
			String file_names_apm = ":ANNUAL_PERFORM_MONEY:";
			//年度完成率
			String file_names_apr = ":ANNUAL_PERFORM_RATIO:";
			//月份指标
			String file_names_mt = ":MONTH_TARGET:";
			//月份完成量
			String file_names_mpm = ":MONTH_PERFORM_MONEY:";
			//月份完成率
			String file_names_mpr = ":MONTH_PERFORM_RATIO:";
			
			List<Map<String, Object>> list=null;
			//需要查询的用户列表
			List<String> userList = new ArrayList<String>();
			if((paramMap.containsKey("sort")&&!"".equals(paramMap.get("sort").toString()))
					&&(paramMap.containsKey("sort_by")&&!"".equals(paramMap.get("sort_by").toString()))){
				String sort = ":"+paramMap.get("sort").toString()+":";
				if(file_names_at.indexOf(sort)!=-1) {
					//年度指标---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryAnnualTarget_User(paramMap);
				}else if(file_names_apm.indexOf(sort)!=-1) {
					//年度完成量---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryAnnualPerformMoney_User(paramMap);
				}else if(file_names_apr.indexOf(sort)!=-1) {
					//年度完成率---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryAnnualPerformRatio_User(paramMap);
				}else if(file_names_mt.indexOf(sort)!=-1) {
					//月份指标---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryMonthTarget_User(paramMap);
				}else if(file_names_mpm.indexOf(sort)!=-1) {
					//月份完成量---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryMonthPerformMoney_User(paramMap);
				}else if(file_names_mpr.indexOf(sort)!=-1) {
					//月份完成率---获取排序后的用户信息(按会员)
					userList=saleTargetDao.queryMonthPerformRatio_User(paramMap);
				}else {
					gr.setState(false);
					gr.setMessage("未配置的排序字段，请联系管理员【"+paramMap.get("sort").toString()+"】");
					return gr;
				}
			}else{
				//查询默认排序的用户信息(按会员)
				userList=saleTargetDao.querySaleTargetTotalEmpHyListBy_Default(paramMap);
			}
			if(!userList.isEmpty()&&userList.size()>0){
				paramMap.put("userList", userList);
				list = saleTargetDao.querySaleTargetTotalEmpHyList(paramMap);
			}
			
			if (list != null && list.size() > 0) {
				
				List<Map<String, Object>> list_annual_perform = saleTargetDao.queryAnnualPerformMoney(paramMap);//数据获取-年度完成量(按会员)
				Map<String,Object> annual_perform_money_map = list2Map(list_annual_perform,"USER_NAME","ANNUAL_PERFORM_MONEY");
				
				List<Map<String, Object>> list_annual_refund = saleTargetDao.queryAnnualRefundList(paramMap);//数据获取-年度退款数据(按会员)
				Map<String,Object> annual_refund_money_map = list2Map(list_annual_refund,"USER_NAME","REFUND_ORDER_PRODUCT_MONEY");
				
				List<Map<String, Object>> list_annual_return = saleTargetDao.queryAnnualReturnList(paramMap);//数据获取-年度退货数据(按会员)
				Map<String,Object> annual_return_money_map = list2Map(list_annual_return,"USER_NAME","RETURNS_ORDER_PRODUCT_MONEY");
				
				List<Map<String, Object>> list_month_perform = saleTargetDao.queryMonthPerformMoney(paramMap);//数据获取-月度完成量(按会员)
				Map<String,Object> month_perform_money_map = list2Map(list_month_perform,"USER_NAME","MONTH_PERFORM_MONEY");
				
				List<Map<String, Object>> list_month_refund = saleTargetDao.queryMonthRefundList(paramMap);//数据获取-年度退款数据(按会员)
				Map<String,Object> month_refund_money_map = list2Map(list_month_refund,"USER_NAME","REFUND_ORDER_PRODUCT_MONEY");
				
				List<Map<String, Object>> list_month_return = saleTargetDao.queryMonthReturnList(paramMap);//数据获取-年度退货数据(按会员)
				Map<String,Object> month_return_money_map = list2Map(list_month_return,"USER_NAME","RETURNS_ORDER_PRODUCT_MONEY");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("start_date", paramMap.get("start_date"));
				map.put("list", list);
				map.put("jdbc_user", jdbc_user);
				//获取每天销售额和销售数量
				List<Map<String, Object>> dayList = saleTargetDao.querySaleMoneyAndCountToDate(map);
				//获取每天退款
				List<Map<String, Object>> dayRefundList = saleTargetDao.queryRefundMoneyAndCountToDate(map);
				//获取每天退货
				List<Map<String, Object>> dayReturnList = saleTargetDao.queryReturnMoneyAndCountToDate(map);
				
				Map<String,Object> tempMap = null;
				for(int i=0;i<list.size();i++) {
					tempMap = list.get(i);
					String user_name = tempMap.get("USER_ID").toString();
					
					float annual_perform_money = annual_perform_money_map.get(user_name)==null?0:Float.parseFloat(annual_perform_money_map.get(user_name).toString());
					float annual_refund_money = annual_refund_money_map.get(user_name)==null?0:Float.parseFloat(annual_refund_money_map.get(user_name).toString());
					float annual_return_money = annual_return_money_map.get(user_name)==null?0:Float.parseFloat(annual_return_money_map.get(user_name).toString());
					
					float month_perform_money = month_perform_money_map.get(user_name)==null?0:Float.parseFloat(month_perform_money_map.get(user_name).toString());
					float month_refund_money = month_refund_money_map.get(user_name)==null?0:Float.parseFloat(month_refund_money_map.get(user_name).toString());
					float annual_retrun_money = month_return_money_map.get(user_name)==null?0:Float.parseFloat(month_return_money_map.get(user_name).toString());
					
					tempMap.put("ANNUAL_PERFORM_MONEY", annual_perform_money-annual_refund_money-annual_return_money);
					tempMap.put("MONTH_PERFORM_MONEY", month_perform_money-month_refund_money-annual_retrun_money);
					for(String day : time_list) {
						float day_money = 0;
						float day_refund_money = 0;
						float day_return_money = 0;
						float day_count = 0;
						float day_refund_count = 0;
						float day_return_count = 0;
						for(Map<String, Object> m : dayList) {
							if(user_name.equals(m.get("USER_NAME").toString())&& day.equals(m.get("TIME_DATE").toString())) {
								day_money = Float.parseFloat(m.get("PAYMENT_MONEY").toString());
								day_count = Float.parseFloat(m.get("PRODUCT_COUNT").toString());
								break;
							}
						}
						for(Map<String, Object> m : dayRefundList) {
							if(user_name.equals(m.get("USER_NAME").toString())&& day.equals(m.get("TIME_DATE").toString())) {
								day_refund_money = Float.parseFloat(m.get("REFUND_ORDER_PRODUCT_MONEY").toString());
								day_refund_count = Float.parseFloat(m.get("REFUND_ORDER_PRODUCT_COUNT").toString());
								break;
							}
						}
						
						for(Map<String, Object> m : dayReturnList) {
							if(user_name.equals(m.get("USER_NAME").toString())&& day.equals(m.get("TIME_DATE").toString())) {
								day_return_money = Float.parseFloat(m.get("RETURNS_ORDER_PRODUCT_MONEY").toString());
								day_return_count = Float.parseFloat(m.get("RETURNS_ORDER_PRODUCT_COUNT").toString());
								break;
							}
						}
						
						tempMap.put(day+"Money", day_money-day_refund_money-day_return_money);
						tempMap.put(day+"Count", day_count-day_refund_count-day_return_count);
					}
				}
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(total);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 查询日期列
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDateLine(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("month_type"))||StringUtils.isEmpty(paramMap.get("year"))||StringUtils.isEmpty(paramMap.get("start_date"))) {
				pr.setState(false);
				pr.setMessage("缺少时间参数");
				return pr;
			}
			int maxDate = getMonthLastDay(Integer.parseInt(paramMap.get("year").toString()),Integer.parseInt(paramMap.get("month_type").toString()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(int i= 0; i < maxDate;i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				if(i<=8) {
					map.put("id", "0"+(i+1)+"");
					String week= getWeekOfDate(sdf.parse(paramMap.get("start_date").toString()+"-0"+(i+1)+""));
					map.put("name", paramMap.get("month_type").toString()+"月"+(i+1)+"日/"+week);
				}else {
					map.put("id", (i+1)+"");
					String week= getWeekOfDate(sdf.parse(paramMap.get("start_date").toString()+"-"+(i+1)+""));
					map.put("name", paramMap.get("month_type").toString()+"月"+(i+1)+"日/"+week);
				}
				list.add(map);
			}
			pr.setState(true);
			pr.setMessage("获取数据成功");
			pr.setObj(list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询单据信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDocumentList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("user_name"))||StringUtils.isEmpty(paramMap.get("date_type"))||StringUtils.isEmpty(paramMap.get("query_date"))) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			paramMap.put("jdbc_user", jdbc_user);
			List<Map<String, Object>> list = saleTargetDao.queryDocumentList(paramMap);
			pr.setState(true);
			pr.setMessage("获取数据成功");
			pr.setObj(list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/** 
	 * 得到指定月的天数 
	 */  
	public int getMonthLastDay(int year, int month)  
	{  
	    Calendar a = Calendar.getInstance();  
	    a.set(Calendar.YEAR, year);  
	    a.set(Calendar.MONTH, month - 1);
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}
	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param date
	 * @return 当前日期是星期几
	 */
	public String getWeekOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	
	/**
	 * 将list转换成map
	 * @param list
	 * @param mapKey
	 * @param valueKey
	 * @return
	 */
	private Map<String,Object> list2Map(List<Map<String, Object>> list ,String mapKey,String valueKey){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		int size = list.size();
		String key ="";
		Object value ="";
		for(int i=0;i<size;i++)
		{
			key = list.get(i).get(mapKey).toString();
			value = list.get(i).get(valueKey);
			returnMap.put(key, value);
		}
		return returnMap;
	}

}
