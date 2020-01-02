package com.tk.oms.marketing.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.tk.oms.marketing.dao.ProductControlDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductControlService.java
 * 控货管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-7-6
 */
@Service("ProductControlService")
public class ProductControlService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ProductControlDao productControlDao;
	/**
	 * 控货查询列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductControlList(HttpServletRequest request) {
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
			if(paramMap.containsKey("user_store_state") && !StringUtils.isEmpty(paramMap.get("user_store_state"))) {
                String[] user_store_state = paramMap.get("user_store_state").toString().split(",");
                if (user_store_state.length > 1) {
                    paramMap.put("user_store_state", paramMap.get("user_store_state"));
                } else {
                    paramMap.put("user_store_state", paramMap.get("user_store_state").toString().split(","));
                }
            }
			if((!StringUtils.isEmpty(paramMap.get("product_control_mode")))&&paramMap.get("product_control_mode") instanceof String){
				paramMap.put("product_control_mode",(paramMap.get("product_control_mode")+"").split(","));
			}
			//查询控货数量
			int total = productControlDao.queryProductControlCount(paramMap);
			//查询控货列表
			List<Map<String, Object>> dataList = productControlDao.queryProductControlList(paramMap);
			List<String> list = new ArrayList<String>();
			if (dataList != null && dataList.size() > 0) {
				for(Map<String, Object> map : dataList) {
					if(!StringUtils.isEmpty(map.get("PRODUCT_CONTROL_ID"))) {
						list.add(map.get("PRODUCT_CONTROL_ID").toString());
					}
				}
				paramMap.put("list", list);
				List<Map<String, Object>> brandList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
				if(list.size()>0) {
					//控货详情品牌列表
					detailList = productControlDao.queryProductControlDetailList(paramMap);
				}
				
				for(Map<String, Object> data : dataList){
					for(Map<String, Object> detail : detailList) {
						if(detail.get("PRODUCT_CONTROL_ID").equals(data.get("PRODUCT_CONTROL_ID"))){
							brandList.add(detail);
						}
					}
					data.put("brandList", brandList);
				}
				gr.setState(true);
				gr.setMessage("查询成功");
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
	 * 控货开关
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失id");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("state"))) {
				pr.setState(false);
				pr.setMessage("参数缺失state");
				return pr;
			}
			//更新控货开关状态
			if(productControlDao.updateState(paramMap)>0){
				pr.setState(true);
				pr.setMessage("操作成功");
			}else{
				pr.setState(false);
				pr.setMessage("操作失败");
			}
			
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 控货详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductControlDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("user_store_address_id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失user_store_address_id");
				return pr;
			}
			//查询控货详情
			Map<String, Object> detailMap = productControlDao.queryProductControlDetail(paramMap);
			if(detailMap!=null){
				//查询控货品牌列表
				List<Map<String, Object>> dataList = productControlDao.queryProductControlDetailListById(Long.parseLong(detailMap.get("ID").toString()));
				detailMap.put("dataList", dataList);
			}
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(detailMap);
			
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 申请控货
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult editProductControl(HttpServletRequest request) {
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
			//查询控货申请ID
			Map<String, Object> applyMap = productControlDao.queryProductControlApplyByUserId(paramMap);
			if(StringUtils.isEmpty(applyMap)){
				//新增控货申请数据
				productControlDao.insertProductControlApply(paramMap);
				
				List<Map<String, Object>> list = (List<Map<String, Object>>)paramMap.get("list");
				for(Map<String, Object> map : list){
					map.put("product_control_id", paramMap.get("id"));
				}
				//新增控货申请明细数据
				productControlDao.insertProductControlDetailApply(list);
				//新增控货附件
				productControlDao.insertProductControlAttachment(paramMap);
			}else{
				//更新控货申请数据
				productControlDao.updateProductControlApply(paramMap);
				//删除控货申请明细数据
				productControlDao.deleteProductControlDetailApply(Long.parseLong(applyMap.get("ID").toString()));
				List<Map<String, Object>> list = (List<Map<String, Object>>)paramMap.get("list");
				for(Map<String, Object> map : list){
					map.put("product_control_id", applyMap.get("ID"));
				}
				//新增控货申请明细数据
				productControlDao.insertProductControlDetailApply(list);
				paramMap.put("id", applyMap.get("ID"));
				//删除控货附件
				productControlDao.deleteProductControlAttachment(paramMap);
				//新增控货附件
				productControlDao.insertProductControlAttachment(paramMap);
				
			}
			pr.setState(true);
			pr.setMessage("申请控货成功");
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("申请控货异常，异常原因："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询申请控货详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductControlApplyDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			Map<String, Object> detailMap = new HashMap<String, Object>();
			//申请列表查看
			if("apply_query".equals(paramMap.get("type"))){
				if (StringUtils.isEmpty(paramMap.get("id"))) {
					pr.setState(false);
					pr.setMessage("缺少参数id");
					return pr;
				}
				//申请详情
				detailMap = productControlDao.queryApplyDetail(paramMap);
				if(detailMap!=null){
					//申请详情品牌列表
					List<Map<String, Object>> dataList = productControlDao.queryApplyDetailList(paramMap);
					detailMap.put("dataList", dataList);
				}
				
			}else{//申请控货
				if (StringUtils.isEmpty(paramMap.get("user_store_address_id"))) {
					pr.setState(false);
					pr.setMessage("缺少参数user_store_address_id");
					return pr;
				}
				//查询申请控货详情
				detailMap = productControlDao.queryProductControlApplyDetail(paramMap);
				if(detailMap!=null){
					//查询申请控货明细列表
					List<Map<String, Object>> dataList = productControlDao.queryProductControlApplyDetailList(Long.parseLong(detailMap.get("ID").toString()) );
					detailMap.put("dataList", dataList);
				}
			}
			
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(detailMap);
			
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 申请列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductControlApplyList(HttpServletRequest request) {
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
			if(paramMap.containsKey("approval_state") && !StringUtils.isEmpty(paramMap.get("approval_state"))) {
                String[] approval_state = paramMap.get("approval_state").toString().split(",");
                if (approval_state.length > 1) {
                    paramMap.put("approval_state", paramMap.get("approval_state"));
                } else {
                    paramMap.put("approval_state", paramMap.get("approval_state").toString().split(","));
                }
            }
			//查询申请数量
			int total = productControlDao.queryProductControlApplyCount(paramMap);
			//查询申请列表
			List<Map<String, Object>> dataList = productControlDao.queryProductControlApplyList(paramMap);
			
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功");
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
	 * 审批
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productControlCheck(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("approval_state"))){
				pr.setState(false);
				pr.setMessage("缺少审批状态参数");
				return pr;
			}else{
				//校验审批数据是否存在
				if (productControlDao.queryProductControlApplyById(paramMap) > 0){
					pr.setState(false);
					pr.setMessage("该控货申请已经审核");
					return pr;
				}else{
					if("3".equals(paramMap.get("approval_state"))){//驳回
						//更新审批状态
						if(productControlDao.updateProductControlApplyApprovalState(paramMap)>0){
							pr.setState(true);
							pr.setMessage("驳回成功");
						}else{
							pr.setState(false);
							pr.setMessage("驳回失败");
						}
					}else if("2".equals(paramMap.get("approval_state"))){//通过
						if(productControlDao.updateProductControlApplyApprovalState(paramMap)>0){
							//查询控货信息是否存在
							if(productControlDao.queryProductControlIsExist(paramMap)>0){
								//更新控货信息
								productControlDao.updateProductControl(paramMap);
								//删除控货明细
								productControlDao.deleteProductControlDetail(paramMap);
								//新增控货明细
								productControlDao.insertProductControlDetail(paramMap);
								pr.setState(true);
								pr.setMessage("审批成功");
							}else{
								//新增控货信息
								productControlDao.inserProductControl(paramMap);
								//新增控货明细
								productControlDao.insertProductControlDetail(paramMap);
								pr.setState(true);
								pr.setMessage("审批成功");
							}
							//审批通过后 打开控货开关
							productControlDao.updateUserSAState(paramMap);
						}else{
							pr.setState(false);
							pr.setMessage("审批失败");
						}
					}
				}
			}
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("审批异常，异常原因："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询周边控货信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPeripheryList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
			//int meter = 15000;//范围15km,【直接改为xml里配置 20171007 shif】。
			//paramMap.put("METER", meter);
			int index = 1;//计数
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			//查询控货信息列表
			List<Map<String, Object>> detailList = productControlDao.queryPeripheryList(paramMap);
			for(Map<String, Object> map : detailList){
				if(index <= 1000){
					if(!map.get("total").equals(0)&&!StringUtils.isEmpty(map.get("ID"))){
						//控货品牌列表
						List<Map<String, Object>> brandList = productControlDao.queryProductControlDetailListById(Long.parseLong(map.get("ID").toString()));
						map.put("brand", brandList);
					}
					dataList.add(map);
					index++;
				}else{
					break;
				}
			}
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
	 * 查询控货设置列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductControlSettingList(HttpServletRequest request) {
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
			
			//查询控货设置总数
			int total = productControlDao.queryProductControlSettingCount(paramMap);
			//查询控货设置列表
			List<Map<String, Object>> dataList = productControlDao.queryProductControlSettingList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				
				for(Map<String, Object> data : dataList){
					if(!StringUtils.isEmpty(data.get("FRIST_CONDITION")) && !StringUtils.isEmpty(data.get("LAST_CONDITION"))){
						data.put("CONDITION", data.get("FRIST_CONDITION").toString()+';'+data.get("LAST_CONDITION").toString());
					}else if(StringUtils.isEmpty(data.get("FRIST_CONDITION")) && !StringUtils.isEmpty(data.get("LAST_CONDITION"))){
						data.put("CONDITION", data.get("LAST_CONDITION"));
					}else if(!StringUtils.isEmpty(data.get("FRIST_CONDITION")) && StringUtils.isEmpty(data.get("LAST_CONDITION"))){
						data.put("CONDITION", data.get("FRIST_CONDITION"));
					}
				}
				gr.setState(true);
				gr.setMessage("查询成功");
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
	 * 控货查询-达成率明细列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductControlAchievingList(HttpServletRequest request) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
			
			//查询开始控货时间
			Map<String, Object> map  = productControlDao.queryproductControlApprovalDate(paramMap);
			if(map != null) {
				paramMap.put("approval_date", map.get("APPROVAL_DATE"));
				paramMap.put("year_month", map.get("YEAR_MONTH"));
			}else {
				paramMap.put("approval_date", sdf.format(new Date()));
				paramMap.put("year_month", sf.format(new Date()));
			}
			
			//查询达成率明细总数
			int total = productControlDao.queryProductControlAchievingCount(paramMap);
			//查询达成率明细列表
			List<Map<String, Object>> dataList = productControlDao.queryProductControlAchievingList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功");
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
	 * 控货查询-控货门店列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductControlStoreList(HttpServletRequest request) {
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
			
			//查询控货门店总数
			int total = productControlDao.queryProductControlStoreCount(paramMap);
			//查询控货门店列表
			List<Map<String, Object>> dataList = productControlDao.queryProductControlStoreList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功");
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
}
