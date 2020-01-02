package com.tk.oms.stationed.service;

import com.tk.oms.basicinfo.dao.SiteInfoDao;
import com.tk.oms.stationed.dao.StationedDao;
import com.tk.pvtp.stationed.dao.PvtpStationedDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StationedService
 * 入驻商管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-10
 */

@Service("StationedService")
public class StationedService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Value("${pay_service_url}")
	private String pay_service_url;//远程调用见证宝接口
	@Value("${jdbc_user}")
	private String jdbc_user;
	@Resource
	private StationedDao stationedDao;
	@Resource
	private PvtpStationedDao pvtpStationedDao;
	/**OA服务地址*/
	@Value("${oa_service_url}")
	private String oa_service_url;
	/**OA供应商同步*/
	@Value("${sys_dictionary_sync_supplier_customer}")
	private String sys_dictionary_sync_supplier_customer;
	
	@Value("${scs_server_url}")
	private String scs_server_url;//SCS服务地址
	@Value("${decorate_init}")
	private String decorate_init;//开通店铺装修初始化
	
	@Resource
	private SiteInfoDao siteInfoDao;//私有商家配置站点处理
	/**
	 * 新增入驻商
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addStationed(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		String json = HttpUtil.getRequestInputStream(request);
		Map<String, Object> map = (Map<String, Object>)Transform.GetPacket(json, Map.class);
		if (!map.containsKey("merchants_types") || StringUtils.isEmpty(map.get("merchants_types"))) {
			pr.setState(false);
			pr.setMessage("缺少参数【merchants_types】");
			return pr;
		}
		// 加密
		byte[] encryptResult = Crypt.encrypt(map.get("user_pwd").toString(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.USER_PWD_KEY));
		// 编码
		map.put("user_pwd", Base64.encode(encryptResult));
		//获取IP
		map.put("user_create_ip", HttpUtil.getIpAddr(request));
		//判断是否已存在相同用户名
		if(stationedDao.queryStationedByUserName(map)==0){
			
			if(stationedDao.queryStationedApplyByCode(map)>0){
				pr.setState(false);
				pr.setMessage("入驻商代码已存在，请修改后提交");
				return pr;
			}
			if(stationedDao.queryRegistrationNumberIsExist(map)>0){
				pr.setState(false);
				pr.setMessage("企业注册号已存在，请修改后提交");
				return pr;
			}
			if(stationedDao.queryStationedApplyByNumber(map)>0){
				pr.setState(false);
				pr.setMessage("入驻商编号已存在，请修改后提交");
				return pr;
			}
			if(stationedDao.queryStationedApplyByCompanyName(map)>0){
				pr.setState(false);
				pr.setMessage("入驻商公司名称已存在，请修改后提交");
				return pr;
			}
			map.put("jdbc_user", jdbc_user);
			Map<String,Object> erpSupplier = this.stationedDao.queryErpSupplierByCode(map);
			if(erpSupplier == null){
				map.put("stationed_id",this.stationedDao.queryStationedId());
			}else {
				map.put("stationed_id",erpSupplier.get("ID"));
			}
			if (stationedDao.insertStationedAudit(map) > 0) {
				//新增商家类型
				this.stationedDao.insertMerchantsType(map);
				//记录入驻商创建日志
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 1);
        		if(Integer.parseInt(map.get("user_state")+"")==1){
        			logMap.put("REMARK", "创建【保存草稿】");
        		}else{
        			logMap.put("REMARK", "创建【注册】");
        		}
        		logMap.put("CREATE_USER_ID", map.get("public_user_id"));
        		logMap.put("USER_NAME", map.get("user_name"));
        		logMap.put("USER_REALNAME", map.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
				pr.setState(true);
				pr.setMessage("新增成功");
			} else {
				pr.setState(false);
				pr.setMessage("新增失败");
			}
		}else{
			pr.setState(false);
			pr.setMessage("新增失败,用户名已存在");
			logger.error("新增失败,用户名已存在");
		}
		return pr;
	}
	/**
	 * 查询入驻商申请数据列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStationedAuditList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("merchants_type")))&&paramMap.get("merchants_type") instanceof String){
				paramMap.put("merchants_type",(paramMap.get("merchants_type")+"").split(","));
			}
			//查询入驻商审核数量
			int total = stationedDao.queryStationedAuditCount(paramMap);
			//查询入驻商审核列表
			List<Map<String, Object>> dataList = stationedDao.queryStationedAuditList(paramMap);
			
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
	 * 入驻商详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedDetail(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("edit_type"))){
				pr.setState(false);
	        	pr.setMessage("缺少参数,edit_type");
	            return pr;
			}
			Map<String, Object> retMap = new HashMap<String, Object>();
			if ("audit".equals(paramMap.get("edit_type"))) {
				retMap = stationedDao.queryStationedAuditDetail(paramMap);
			} else {
				retMap = stationedDao.queryStationedDetail(paramMap);
			}
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败：" + e.getMessage());
		}
		return pr;
	}
	/**
	 * 入驻商申请数据审核
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult auditStationed(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("user_state"))){
				pr.setState(false);
				pr.setMessage("缺少审核状态参数");
				return pr;
			}else{
				Map<String, Object> verify = new HashMap<String, Object>();
				verify.put("id", paramMap.get("id"));
				//是否存在
				if(stationedDao.queryStationedApplyById(verify) > 0){
					verify.put("user_state","3");
					//审核数据校验
					if(stationedDao.queryStationedApplyById(verify)>0){
						pr.setState(false);
						pr.setMessage("该入驻商已审核！");
						return pr;
					}else{
						//查询 入驻商详情
						Map<String, Object> stationedDetail =stationedDao.queryStationedAuditDetail(paramMap);
						if("4".equals(paramMap.get("user_state"))){//驳回
							if(stationedDao.updateStationedUserApplyUserState(paramMap)>0){
								pr.setState(true);
								pr.setMessage("审核成功");
							}else{
								pr.setState(false);
								pr.setMessage("审核失败");
							}
						}else if ("3".equals(paramMap.get("user_state"))){//审核
							//更新入驻商申请表的用户审核状态
							if(stationedDao.updateStationedUserApplyUserState(paramMap)>0){
								/****************入驻商相关信息及账号处理*****************/
								//新增入驻商信息数据 
								stationedDao.insertStationedUserInfo(paramMap);
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("user_id", paramMap.get("id"));
								param.put("deposit_money", paramMap.get("deposit_money"));
								//新增入驻商关联的银行账户信息。
								addUserAccount(param);
								
								/****判断ERP供应商表是否存在入驻商CODE,存在则不任何操作;不存在则将入驻商信息复制到ERP供应商表（TBL_SUPPLIER_INFO）***/
								stationedDetail.put("jdbc_user", jdbc_user);
								if(stationedDao.queryErpSupplierByCode(stationedDetail)== null){
									//新增入驻商信息数据到erp数据库
									paramMap.put("jdbc_user", jdbc_user);
									stationedDao.insertErpSupplier(paramMap);
								}
								
								/***************************同步数据至OA********************************/
								long id = Long.parseLong(paramMap.get("id").toString());
								// 获取入驻商信息
								Map<String, Object> stationedUserMap = stationedDao.getById(id);
								stationedUserMap.put("operationType","add");
								stationedUserMap.put("merchants_types", stationedDao.getStationedUserType(id));
								ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + sys_dictionary_sync_supplier_customer, stationedUserMap);
								if (!oAPr.getState()) {
			 						throw new RuntimeException("同步入驻商信息至OA失败");
								}
								pr.setState(true);
								pr.setMessage("审核成功");
							}else{
								pr.setState(false);
								pr.setMessage("审核失败");
							}
						}
						//记录入驻商审核日志
		        		Map<String,Object> logMap=new HashMap<String,Object>();
		        		logMap.put("USER_TYPE", 2);
		        		logMap.put("OPERATE_ID", 1);
		        		logMap.put("REMARK", "创建【审核】");
		        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
		        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
		        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
		        		stationedDao.insertUserOperationLog(logMap);
					}
				}else{
					pr.setState(false);
					pr.setMessage("数据不存在,请确认！");
					return pr;
				}
			}
			
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("审核失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询入驻商信息列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryStationedList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("merchants_type")))&&paramMap.get("merchants_type") instanceof String){
				paramMap.put("merchants_type",(paramMap.get("merchants_type")+"").split(","));
			}
			//查询入驻商数量
			int total = stationedDao.queryStationedCount(paramMap);
			//查询入驻商列表
			List<Map<String, Object>> dataList = stationedDao.queryStationedList(paramMap);
			
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
	 * 入驻商服务费设置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editStationedServiceCharges(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
			if(!StringUtils.isEmpty(paramMap.get("service_charges"))){
				paramMap.put("service_charges", Double.parseDouble(paramMap.get("service_charges").toString())/100);
			}else{
				paramMap.remove("service_charges");
			}
			if(!StringUtils.isEmpty(paramMap.get("member_service_rate"))){
				paramMap.put("member_service_rate", Double.parseDouble(paramMap.get("member_service_rate").toString())/100);
			}else{
				paramMap.remove("member_service_rate");
			}
			if(!StringUtils.isEmpty(paramMap.get("pay_service_rate"))){
				paramMap.put("pay_service_rate", Double.parseDouble(paramMap.get("pay_service_rate").toString())/100);
			}else{
				paramMap.remove("pay_service_rate");
			}
			if(!StringUtils.isEmpty(paramMap.get("area_service_rate"))){
				paramMap.put("area_service_rate", Double.parseDouble(paramMap.get("area_service_rate").toString())/100);
			}else{
				paramMap.remove("area_service_rate");
			}
			if(!StringUtils.isEmpty(paramMap.get("service_charges"))||!StringUtils.isEmpty(paramMap.get("member_service_rate"))
					||!StringUtils.isEmpty(paramMap.get("pay_service_rate"))||!StringUtils.isEmpty(paramMap.get("area_service_rate"))){
				stationedDao.updateStationed(paramMap);
				//记录入驻配置相关服务费日志
				Map<String,Object> stationedDetail=stationedDao.queryStationedDetail(paramMap);
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【相关服务费】");
        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
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
	 * 入驻商仓储费设置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editStorageCharges(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
			List<Map<String, Object>> dataList=(List<Map<String, Object>>) paramMap.get("dataList");
			//1.删除
//			stationedDao.deleteStorageCharges(paramMap);
//			for(Map<String, Object> map :dataList){
//					map.put("public_user_id", paramMap.get("public_user_id"));
//					map.put("id", paramMap.get("id"));
//					//2.新增
//					stationedDao.insertStorageCharges(map);
//			}
			for(Map<String, Object> map :dataList){
				map.put("create_user_id", paramMap.get("public_user_id"));
				map.put("stationed_user_id", paramMap.get("id"));
			}
			stationedDao.updateOrInsertStorageCharges(dataList);
			//记录入驻配置仓储服务费日志
			Map<String,Object> stationedDetail=stationedDao.queryStationedDetail(paramMap);
    		Map<String,Object> logMap=new HashMap<String,Object>();
    		logMap.put("USER_TYPE", 2);
    		logMap.put("OPERATE_ID", 3);
    		logMap.put("REMARK", "配置【仓储费】");
    		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
    		logMap.put("USER_NAME", stationedDetail.get("user_name"));
    		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
    		stationedDao.insertUserOperationLog(logMap);
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
	 * 查询入驻商仓储费
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStorageChargesList(HttpServletRequest request) {
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
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			//商品类型
			if("1".equals(paramMap.get("type"))){
				dataList=stationedDao.queryStorageChargesList(paramMap);
			}else{
				dataList=stationedDao.queryStorageChargesTypeList(paramMap);
			}
			pr.setState(true);
        	pr.setMessage("查询成功");
        	pr.setObj(dataList);
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
		}	
		return pr;
	}
	/**
	 * 入驻商品牌授权
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editBrandLicensing(HttpServletRequest request) {
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
			Map<String,Object> stationedDetail=stationedDao.queryStationedDetail(paramMap);
			if("orization".equals(paramMap.get("type"))){
				//1.授权
				if(stationedDao.insertBrandUserRel(paramMap)>0){
					//记录入驻品牌授权日志
	        		Map<String,Object> logMap=new HashMap<String,Object>();
	        		logMap.put("USER_TYPE", 2);
	        		logMap.put("OPERATE_ID", 3);
	        		logMap.put("REMARK", "配置【品牌授权】授权");
	        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
	        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
	        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
	        		stationedDao.insertUserOperationLog(logMap);
					pr.setState(true);
		        	pr.setMessage("授权成功");
				}else{
					pr.setState(false);
		        	pr.setMessage("授权失败");
				}
			}else{
				//2.取消授权
				if(stationedDao.deleteBrandUserRel(paramMap)>0){
					//记录入驻品牌授权取消授权日志
	        		Map<String,Object> logMap=new HashMap<String,Object>();
	        		logMap.put("USER_TYPE", 2);
	        		logMap.put("OPERATE_ID", 3);
	        		logMap.put("REMARK", "配置【品牌授权】取消授权");
	        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
	        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
	        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
	        		stationedDao.insertUserOperationLog(logMap);
					pr.setState(true);
		        	pr.setMessage("取消授权成功");
				}else{
					pr.setState(false);
		        	pr.setMessage("取消授权失败");
				}
			}
			
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("设置失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}	
		return pr;
	}
	/**
	 * 查询入驻商品牌授权信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBrandLicensingList(HttpServletRequest request) {
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
			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			if("orization".equals(paramMap.get("type"))){
				//1.入驻商授权品牌信息
				dataList=stationedDao.queryBrandUserRel(paramMap);
			}else{
				//2.查询是否存在入驻商授权品牌信息
				if(stationedDao.queryBrandUserRelCount(paramMap)>0){
					paramMap.put("count", 1);//存在关联数据
				}else{
					paramMap.put("count", 0);//不存在关联数据
				}
				//2.品牌信息
				dataList=stationedDao.queryBrandList(paramMap);
			}
			pr.setState(true);
        	pr.setMessage("查询成功");
        	pr.setObj(dataList);
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
		}	
		return pr;
	}
	/**
	 * 生产计划列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductionPlanList(HttpServletRequest request) {
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
			//状态
			if (paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
				String[] state = paramMap.get("state").toString().split(",");
				if (state.length > 1) {
					paramMap.put("state", paramMap.get("state"));
				} else {
					paramMap.put("state", paramMap.get("state").toString().split(","));
				}
			}
			paramMap.put("jdbc_user", jdbc_user);
			//查询生产计划数量
			int total = stationedDao.queryProductionPlanCount(paramMap);
			//查询生产计划列表
			List<Map<String, Object>> dataList = stationedDao.queryProductionPlanList(paramMap);
			
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
	 * 生产计划详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductionPlanDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();

		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("plan_number") || StringUtils.isEmpty(paramMap.get("plan_number"))) {
				pr.setState(false);
				pr.setMessage("缺少参数plan_number");
				return pr;
			}

			//计划单号
			String plan_number = paramMap.get("plan_number").toString();
			//生产计划明细
			List<Map<String, Object>> productionPlanDetail = new ArrayList<Map<String, Object>>();
			//生产计划单分组明细
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("plan_number", plan_number);
			params.put("jdbc_user", jdbc_user);
			List<Map<String, Object>> productionPlanGroupDetail = stationedDao.queryProductionPlanDetailByGroup(params);
			//遍历计划明细封装规格、尺码分组计划数量明细
			for (Map<String, Object> specs_detail : productionPlanGroupDetail) {
				//查询参数
				Map param = new HashMap();
				param.put("plan_number", specs_detail.get("PLAN_NUMBER").toString());
				param.put("product_itemnumber", specs_detail.get("PRODUCT_ITEMNUMBER").toString());
				param.put("product_color", specs_detail.get("PRODUCT_COLOR").toString());
				param.put("product_specs", specs_detail.get("PRODUCT_SPECS").toString());

				//生产计划尺码分组明细
				param.put("jdbc_user", jdbc_user);
				List<Map<String, Object>> sizeDetail = stationedDao.queryProductionPlanDetailSizeByParam(param);
				specs_detail.put("size_detail", sizeDetail);//尺码明细
				productionPlanDetail.add(specs_detail);
			}

			if (productionPlanDetail != null) {
				pr.setState(true);
				pr.setMessage("获取成功");
				pr.setObj(productionPlanDetail);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 入库申请列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryInStorageApplyList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			// 解析传入参数
			Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
			//分页参数
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if (pageParamResult != null) {
				return pageParamResult;
			}

			//状态
			if (paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
				String[] state = paramMap.get("state").toString().split(",");
				if (state.length > 1) {
					paramMap.put("state", paramMap.get("state"));
				} else {
					paramMap.put("state", paramMap.get("state").toString().split(","));
				}
			}

			//查询入库申请数量
			int total = stationedDao.queryInStorageApplyCount(paramMap);
			//查询入库申请列表
			List<Map<String, Object>> dataList = stationedDao.queryInStorageApplyList(paramMap);
			
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
	 * 入库申请详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryInStorageApplyDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("apply_number") || StringUtils.isEmpty(paramMap.get("apply_number"))) {
				pr.setState(false);
				pr.setMessage("缺少参数apply_number");
				return pr;
			}

			//入库申请单号
			String apply_number = paramMap.get("apply_number").toString();
			//入库申请单明细
			List<Map<String, Object>> inStorageApplyDetail = new ArrayList<Map<String, Object>>();
			//入库申请单分组明细
			List<Map<String, Object>> inStorageApplyGroupDetail = stationedDao.queryInStorageApplyDetailByGroup(apply_number);
			//遍历入库申请明细封装规格、尺码分组申请数量明细
			for (Map<String, Object> specs_detail : inStorageApplyGroupDetail) {
				//查询参数
				Map param = new HashMap();
				param.put("apply_number", apply_number);
				param.put("product_itemnumber", specs_detail.get("PRODUCT_ITEMNUMBER").toString());
				param.put("product_color", specs_detail.get("PRODUCT_COLOR").toString());
				param.put("product_specs", specs_detail.get("PRODUCT_SPECS").toString());

				//入库申请尺码分组明细
				List<Map<String, Object>> sizeDetail = stationedDao.queryInStorageApplySizeByParam(param);
				specs_detail.put("size_detail", sizeDetail);//尺码明细
				inStorageApplyDetail.add(specs_detail);
			}
			if (inStorageApplyDetail != null) {
				pr.setState(true);
				pr.setMessage("获取成功");
				pr.setObj(inStorageApplyDetail);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 入驻商删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeStationed(HttpServletRequest request) {
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
			Map<String, Object> verify = new HashMap<String, Object>();
			verify.put("id", paramMap.get("id"));
			if(stationedDao.queryStationedApplyById(verify) > 0){
				//入驻商删除
				if(stationedDao.delete(paramMap)>0){
					//记录入驻商删除日志
					Map<String,Object> stationedDetail=stationedDao.queryStationedDetail(paramMap);
	        		Map<String,Object> logMap=new HashMap<String,Object>();
	        		logMap.put("USER_TYPE", 2);
	        		logMap.put("OPERATE_ID", 5);
	        		logMap.put("REMARK", "删除【入驻商】");
	        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
	        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
	        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
	        		stationedDao.insertUserOperationLog(logMap);
					pr.setState(true);
					pr.setMessage("删除成功");
				}else{
					pr.setState(false);
					pr.setMessage("删除失败");
				}
			}else{
				pr.setState(false);
				pr.setMessage("数据不存在,请确认！");
				return pr;
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
	 * 入驻商编辑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editStationed(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
			if (!paramMap.containsKey("merchants_types") || StringUtils.isEmpty(paramMap.get("merchants_types"))) {
				pr.setState(false);
				pr.setMessage("缺少参数【merchants_types】");
				return pr;
			}
			Map<String,Object> stationedDetail=stationedDao.queryStationedDetail(paramMap);
			//判断是否已存在相同用户名
			if(stationedDao.queryStationedByUserName(paramMap)==0){
				//审核列表(编辑)
				if("audit".equals(paramMap.get("edit_type"))){
					Map<String, Object> verify = new HashMap<String, Object>();
					verify.put("id", paramMap.get("id"));
					if(stationedDao.queryStationedApplyById(verify) > 0){
						if(stationedDao.queryStationedApplyByCode(paramMap)>0){
							pr.setState(false);
							pr.setMessage("入驻商代码已存在，请修改后提交");
							return pr;
						}
						if(stationedDao.queryRegistrationNumberIsExist(paramMap)>0){
							pr.setState(false);
							pr.setMessage("企业注册号已存在，请修改后提交");
							return pr;
						}
						if(stationedDao.queryStationedApplyByNumber(paramMap)>0){
							pr.setState(false);
							pr.setMessage("入驻商编号已存在，请修改后提交");
							return pr;
						}
						if(stationedDao.queryStationedApplyByCompanyName(paramMap)>0){
							pr.setState(false);
							pr.setMessage("入驻商公司名称已存在，请修改后提交");
							return pr;
						}
						if(stationedDao.updateStationedAudit(paramMap)>0){
							long stationedId = Long.parseLong(String.valueOf(paramMap.get("id")));
							this.stationedDao.deleteMerchantsType(stationedId);
							paramMap.put("stationed_id",stationedId);
							this.stationedDao.insertMerchantsType(paramMap);
							//记录入驻商编辑草稿日志
							stationedDetail=stationedDao.queryStationedAuditDetail(paramMap);
			        		Map<String,Object> logMap=new HashMap<String,Object>();
			        		logMap.put("USER_TYPE", 2);
			        		logMap.put("OPERATE_ID", 2);
			        		logMap.put("REMARK", "编辑【草稿信息】");
			        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
			        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
			        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
			        		stationedDao.insertUserOperationLog(logMap);
			        		
			        		//记录入驻商创建日志日志
			        		Map<String,Object> logMap1=new HashMap<String,Object>();
			        		logMap1.put("USER_TYPE", 2);
			        		logMap1.put("OPERATE_ID", 1);
			        		logMap1.put("REMARK", "创建【注册】");
			        		logMap1.put("CREATE_USER_ID", paramMap.get("public_user_id"));
			        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
			        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
			        		stationedDao.insertUserOperationLog(logMap1);
							pr.setState(true);
			            	pr.setMessage("更新成功");
						}else{
							pr.setState(false);
			            	pr.setMessage("更新失败");
						}
					}else{
						pr.setState(false);
						pr.setMessage("数据不存在,请确认！");
						return pr;
					}
				}else{
					if(stationedDao.queryStationedByNumber(paramMap)>0){
						pr.setState(false);
						pr.setMessage("入驻商编号已存在，请修改后提交");
						return pr;
					}
					if (stationedDao.updateStationed(paramMap) > 0) {

						/***************************同步数据至OA********************************/
						// 获取入驻商信息
						paramMap.put("operationType","edit");
						ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + sys_dictionary_sync_supplier_customer, paramMap);
						if (!oAPr.getState()) {
							throw new RuntimeException("同步入驻商信息至OA失败");
						}
						/****************************同步数据至OA*******************************/

						long stationedId = Long.parseLong(String.valueOf(paramMap.get("id")));
						this.stationedDao.deleteMerchantsType(stationedId);
						paramMap.put("stationed_id",stationedId);
						this.stationedDao.insertMerchantsType(paramMap);
						//记录入驻商编辑日志
		        		Map<String,Object> logMap=new HashMap<String,Object>();
		        		logMap.put("USER_TYPE", 2);
		        		logMap.put("OPERATE_ID", 2);
		        		logMap.put("REMARK", "编辑【入驻商信息】");
		        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
		        		logMap.put("USER_NAME", stationedDetail.get("user_name"));
		        		logMap.put("USER_REALNAME", stationedDetail.get("company_name"));
		        		stationedDao.insertUserOperationLog(logMap);
						pr.setState(true);
		            	pr.setMessage("更新成功");
					}else{
						pr.setState(false);
		            	pr.setMessage("更新失败");
					}
				}
			}else{
				pr.setState(false);
				pr.setMessage("编辑失败,用户名已存在");
				logger.error("编辑失败,用户名已存在");
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
	 * 是否启用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editState(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
			Map<String, Object> map = stationedDao.queryStationedDetail(paramMap);
			//启用时增加支付服务费配置校验 
			if("2".equals(paramMap.get("state"))) {
				if(StringUtils.isEmpty(map.get("PAY_SERVICE_RATE"))) {
					pr.setState(false);
	            	pr.setMessage("启用失败：未配置支付服务费");
	            	return pr;
				}
			}
			
			if(stationedDao.updateStationed(paramMap)>0){
				/***************************同步数据至OA********************************/
				// 获取入驻商信息
				paramMap.put("operationType","edit");
				ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + sys_dictionary_sync_supplier_customer, paramMap);
				if (!oAPr.getState()) {
					throw new RuntimeException("同步入驻商信息至OA失败");
				}
				/****************************同步数据至OA*******************************/
				//记录入驻停用启用日志
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【停用启用】");
        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
        		logMap.put("USER_NAME", map.get("user_name"));
        		logMap.put("USER_REALNAME", map.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
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
     * 获取入驻商节点权限
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryUserNodeList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            //判断id是否已传入
            
            paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            
            if(StringUtils.isEmpty(paramMap.get("user_id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
           
            //获取用户节点权限
            List<Map<String,Object>> list = stationedDao.queryUserNodeList(paramMap);
            
            pr.setState(true);
            pr.setMessage("获取入驻商节点权限成功");
            pr.setObj(list);
            
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("获取入驻商节点权限失败");
        }

        return pr;
    }
	
	/**
	 * 编辑入驻商权限
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editUserNode(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("user_id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
			//校验role_ids参数
            if(!paramMap.containsKey("node_ids")) {
                pr.setState(false);
                pr.setMessage("缺少参数node_ids");
                return pr;
            }
			//删除历史权限
			stationedDao.deleteUserNode(paramMap);
			
			if(((List<Map<String,Object>>)paramMap.get("node_ids")).size()>0){
            	//插入新的权限数据
				if(stationedDao.insertUserNode(paramMap)>0){
					pr.setState(true);
	            	pr.setMessage("入驻商权限设置成功");
				}else{
					pr.setState(false);
	            	pr.setMessage("入驻商权限设置失败");
				}
           }else{
           		pr.setState(true);
               pr.setMessage("成功收回该入驻商所有权限");
           }
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("操作失败："+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 密码重置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult resetPwd(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        Map<String,Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验id参数
            if(!params.containsKey("id")||StringUtils.isEmpty(params.get("id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数id");
            	return pr;
            }
            //校验user_pwd参数
            if(!params.containsKey("user_pwd")||StringUtils.isEmpty(params.get("user_pwd"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_pwd");
                return pr;
            }

            // 加密
			byte[] encryptResult = Crypt.encrypt(params.get("user_pwd").toString(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.USER_PWD_KEY));
			// 编码
			String ciphertext = Base64.encode(encryptResult);
			params.put("user_pwd", ciphertext);
            if(stationedDao.update_pwd(params) > 0) {
            	//记录入驻商密码重置日志
            	Map<String, Object> map = stationedDao.queryStationedDetail(params);
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【密码重置】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", map.get("user_name"));
        		logMap.put("USER_REALNAME", map.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
        		pr.setState(true);
        		pr.setMessage("入驻商密码重置成功");
            } else {
                pr.setState(false);
        		pr.setMessage("入驻商密码重置失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	/**
	 * 保证金配置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editDepositMoney(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        Map<String,Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验id参数
            if(!params.containsKey("id")||StringUtils.isEmpty(params.get("id"))) {
            	pr.setState(false);
            	pr.setMessage("入驻商ID为空");
            	return pr;
            }
            //校验deposit_money参数
            if(!params.containsKey("deposit_money")||StringUtils.isEmpty(params.get("deposit_money"))) {
                pr.setState(false);
                pr.setMessage("保证金金额为空");
                return pr;
            }

            if(stationedDao.editDepositMoney(params) > 0) {
            	//记录入驻商保证金配置日志
            	Map<String, Object> map = stationedDao.queryStationedDetail(params);
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【保证金配置】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", map.get("user_name"));
        		logMap.put("USER_REALNAME", map.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
        		pr.setState(true);
        		pr.setMessage("保证金配置成功");
            } else {
                pr.setState(false);
        		pr.setMessage("保证金配置失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	/**
	 * 查询保证金
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDepositMoney(HttpServletRequest request) {
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
			String deposit_money =stationedDao.queryDepositMoney(paramMap);
			pr.setState(true);
        	pr.setMessage("查询成功");
        	pr.setObj(deposit_money);
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
		}	
		return pr;
	}
	/**
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int addUserAccount(Map<String, Object> map) throws Exception{
		//user_name直接使用id，如果为空，则直接赋值  20162002
		map.put("user_name", map.get("user_id"));
		
		//创建用户账户信息
		Map<String,Object> codeParams = new HashMap<String,Object>();
		codeParams.put("c_user_name",map.get("user_id"));
		codeParams.put("c_money",0);
		codeParams.put("c_typeid","new");
		codeParams.put("c_user_type","2");
		String key = stationedDao.getUserKey(codeParams);
		codeParams.put("c_user_key",key);
		String code = stationedDao.getCheck_Code(codeParams);
		Map<String,Object> account = new HashMap<String,Object>();
		account.put("user_id", map.get("user_id"));
		account.put("account_balance_checkcode", code);//余额校验码
		account.put("credit_checkcode", code);//授信校验码
		account.put("deposit_checkcode", code);//保证金校验码
		account.put("bank_account", "");
		account.put("sub_merchant_id","");
		account.put("deposit_money", map.get("deposit_money"));
		account.put("user_type", "2");
		account.put("credit_money", 0);
		account.put("credit_money_use", 0);
		account.put("credit_money_balance", 0);
		account.put("account_balance", 0);
		account.put("deposit_money_balance", 0);
		//保存用户key
		Map<String,Object> uck = new HashMap<String,Object>();
		uck.put("user_name", map.get("user_id"));
		uck.put("cache_key", key);
		if(stationedDao.insertCacheKey(uck)>0){
			return stationedDao.insertBankAccountInfo(account);
		}
		return 0;
	}
	/**
	 * 入驻商收支记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryInExpRecord(HttpServletRequest request) {
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
			//查询入驻商收支记录数量
			int total = stationedDao.queryInExpRecordCount(paramMap);
			//查询入驻商收支记录列表
			List<Map<String, Object>> dataList = stationedDao.queryInExpRecordList(paramMap);
			
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
	 * 查询入驻商主账号简要信息
	 * @param request
	 * @return
	 */
	public ProcessResult querySimpleStationedList(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);

			if(!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			List<Map<String, Object>> dataList = stationedDao.querySimpleStationedList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询入驻商主账信息成功");
				gr.setObj(dataList);
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
	 * 查询入驻商主账号简要信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedOption(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			//  校验是否传入参数
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			List<Map<String, Object>> dataList = stationedDao.queryStationedOption(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询入驻商数据成功");
				gr.setObj(dataList);
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

	/*****************************************************************系统入驻商角色管理*******************************************************************************************************/

	/**
	 * 查询系统入驻商角色列表，如果有权限则选中
	 * @param request
	 * @return
	 */
	public ProcessResult queryStationedRole(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			//  校验是否传入参数
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			HashMap<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_id") || StringUtils.isEmpty(paramMap.get("user_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数[user_id]入驻商用户id");
				return pr;
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 查询菜单或按钮节点列表，如果有权限则选中
			List<Map<String, Object>> roleList = stationedDao.queryStationedRoleList(paramMap);
			//已选择角色列表
			List<Map<String, Object>> selectRoleList = new ArrayList<Map<String, Object>>();
			//未选择角色列表
			List<Map<String, Object>> noSelectRoleList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> roleMap : roleList) {
				if (Integer.parseInt(roleMap.get("checked").toString()) == 0) {
					noSelectRoleList.add(roleMap);
				} else {
					selectRoleList.add(roleMap);
				}
			}
			resultMap.put("haveChosen", selectRoleList);
			resultMap.put("noChoice", noSelectRoleList);
			pr.setState(true);
			pr.setMessage("获取用户角色配置信息成功");
			pr.setObj(resultMap);

		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 配置系统入驻商用户角色
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ProcessResult editStationedRole(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();

		String json = HttpUtil.getRequestInputStream(request);
		//  校验是否传入参数
		if(StringUtils.isEmpty(json)) {
			pr.setState(false);
			pr.setMessage("缺少参数");
			return pr;
		}

		HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
		//  校验user_id参数
		if(!paramMap.containsKey("user_id")) {
			pr.setState(false);
			pr.setMessage("缺少参数user_id");
			return pr;
		}
		//  校验role_ids参数
		if(!paramMap.containsKey("role_ids")) {
			pr.setState(false);
			pr.setMessage("缺少参数role_ids");
			return pr;
		}

		//  删除以前入驻商用户配置的权限数据
		stationedDao.deleteStationedRole(paramMap);
		if(((List<Map<String,Object>>)paramMap.get("role_ids")).size()>0){
			//  插入新的权限数据
			if(stationedDao.insertStationedRole(paramMap) > 0) {
				//记录入驻商角色配置日志
				paramMap.put("id", paramMap.get("user_id"));
				Map<String, Object> map = stationedDao.queryStationedDetail(paramMap);
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【角色配置】");
        		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
        		logMap.put("USER_NAME", map.get("user_name"));
        		logMap.put("USER_REALNAME", map.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
				pr.setState(true);
				pr.setMessage("配置入驻商用户角色成功");
			} else {
				pr.setState(false);
				pr.setMessage("配置入驻商用户角色失败");
			}
		}else{
			pr.setState(true);
			pr.setMessage("成功取消该入驻商用户所有角色权限");
		}

		return pr;
	}

	/**
	 * 查询系统入驻商角色列表
	 * @param request
	 * @return
	 */
	public ProcessResult querySysStationedRoleList(HttpServletRequest request) {
		GridResult pr = new GridResult();
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

			List<Map<String, Object>> list = stationedDao.querySysStationedRoleList(paramMap);
			int total = stationedDao.querySysStationedRoleCount(paramMap);
			pr.setState(true);
			pr.setMessage("获取系统入驻商角色列表成功");
			pr.setObj(list);
			pr.setTotal(total);

		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}

		return pr;
	}

	/**
	 * 查询角色详情
	 * @param request
	 * @return
	 */
	public ProcessResult querySysStationedRoleDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			//  校验是否传入参数
			if(StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			HashMap<String,Object> map = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
			//  校验name参数
			if(!map.containsKey("role_id")) {
				pr.setState(false);
				pr.setMessage("缺少参数role_id");
				return pr;
			}
			Map<String,Object> roleDetail = this.stationedDao.querySysStationedRoleDetail(map);
			pr.setState(true);
			pr.setMessage("获取角色详情成功");
			pr.setObj(roleDetail);
		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 修改系统入驻商角色
	 * @param request
	 * @return
	 */
	public ProcessResult editSysStationedRole(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();

		try {
			String json = HttpUtil.getRequestInputStream(request);
			//  校验是否传入参数
			if(StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
			//  校验name参数
			if(!paramMap.containsKey("role_id")) {
				pr.setState(false);
				pr.setMessage("缺少参数role_id");
				return pr;
			}
			//  判断是否有重复的角色名称
			if(stationedDao.queryBySysStationedRoleName(paramMap)>0){
				pr.setState(false);
				pr.setMessage("角色名称重复");
				return pr;
			}
			if(stationedDao.updateSysStationedRole(paramMap) > 0) {
				//  删除以前配置节点数据
				stationedDao.deleteSysStationedRoleNode(paramMap);
				if(((List<Map<String,Object>>)paramMap.get("role_nodes")).size()>0){
					if(stationedDao.insertSysStationedRoleNode(paramMap) > 0) {
						pr.setState(true);
						pr.setMessage("修改角色信息成功");
					} else {
						pr.setState(false);
						pr.setMessage("修改角色信息失败");
					}
				}else{
					pr.setState(true);
					pr.setMessage("成功取消该角色所有功能菜单权限");
				}
			} else {
				pr.setState(false);
				pr.setMessage("修改角色信息失败");
			}
		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}

		return pr;
	}

	/**
	 * 查询系统入驻商角色菜单或按钮节点列表，如果有权限则选中
	 *
	 * @param request
	 * @return
	 */
	public ProcessResult querySysStationedRoleNodeList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
			//  校验role_id参数
			if(!params.containsKey("role_id")) {
				pr.setState(false);
				pr.setMessage("缺少参数role_id");
				return pr;
			}
			Map<String,Object> resultMap = new HashMap<String, Object>();
			//入驻商
			List<Map<String,Object>> stationedList = new ArrayList<Map<String, Object>>();
			//供应商
			List<Map<String,Object>> supplierList = new ArrayList<Map<String, Object>>();
			//云仓货主
			List<Map<String,Object>> ownersList = new ArrayList<Map<String, Object>>();
			//私有平台商家
			List<Map<String,Object>> privateList = new ArrayList<Map<String, Object>>();
			//查询菜单或按钮节点列表，如果有权限则选中
			List<Map<String, Object>> nodeList = stationedDao.querySysStationedRoleNodeList(params);
			for (Map<String, Object> nodeMap : nodeList) {
				int merchantsType = Integer.parseInt(nodeMap.get("merchants_type").toString());
				if (merchantsType == 1) {
					stationedList.add(nodeMap);
				} else if (merchantsType == 2) {
					supplierList.add(nodeMap);
				} else if (merchantsType == 3) {
					ownersList.add(nodeMap);
				} else if (merchantsType == 4) {
					privateList.add(nodeMap);
				}
			}
			resultMap.put("stationed", stationedList);
			resultMap.put("supplier", supplierList);
			resultMap.put("owners", ownersList);
			resultMap.put("private", privateList);
			pr.setState(true);
			pr.setMessage("获取角色配置信息成功");
			pr.setObj(resultMap);

		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}

		return pr;
	}

	/**
	 * 新增系统入驻商角色
	 * @param request
	 * @return
	 */
	public ProcessResult addSysStationedRole(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();

		try {
			String json = HttpUtil.getRequestInputStream(request);
			//  校验是否传入参数
			if(StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}

			HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
			//  校验role_name参数
			if(!paramMap.containsKey("role_name")) {
				pr.setState(false);
				pr.setMessage("缺少参数role_name");
				return pr;
			}
			//  判断是否有重复的角色名称
			if(stationedDao.queryBySysStationedRoleName(paramMap)>0){
				pr.setState(false);
				pr.setMessage("角色名称重复");
				return pr;
			}
			if(stationedDao.insertSysStationedRole(paramMap)>0){
				paramMap.put("role_id", paramMap.get("id"));
				if(((List<Map<String,Object>>)paramMap.get("role_nodes")).size()>0){
					if(stationedDao.insertSysStationedRoleNode(paramMap) > 0) {
						pr.setState(true);
						pr.setMessage("新增角色成功");
					} else {
						pr.setState(false);
						pr.setMessage("新增角色失败");
					}
				}else{
					pr.setState(true);
					pr.setMessage("新增角色[未配置权限菜单]成功");
				}
			}else {
				pr.setState(false);
				pr.setMessage("新增角色失败");
			}
		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}

		return pr;
	}

	/**
	 * 删除系统入驻商角色
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ProcessResult delSysStationedRole(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		String json = HttpUtil.getRequestInputStream(request);
		//  校验是否传入参数
		if(StringUtils.isEmpty(json)) {
			pr.setState(false);
			pr.setMessage("缺少参数");
			return pr;
		}
		HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
		//  校验name参数
		if(!paramMap.containsKey("role_id")) {
			pr.setState(false);
			pr.setMessage("缺少参数[role_id]角色id");
			return pr;
		}

		if(stationedDao.deleteSysStationedRole(paramMap) > 0) {
			//  删除系统入驻商角色权限菜单节点表数据
			stationedDao.deleteSysStationedRoleNode(paramMap);
			//  删除系统入驻商用户中已使用的角色
			stationedDao.deleteSysStationedUserRole(paramMap);
			pr.setState(true);
			pr.setMessage("删除角色成功");

		} else {
			pr.setState(false);
			pr.setMessage("删除角色失败");
		}

		return pr;
	}

	/**
	 * 获取系统入驻商节点权限
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationedUserNodeList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			//  校验是否传入参数
			if(StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			//入驻商
			List<Map<String,Object>> stationedList = new ArrayList<Map<String, Object>>();
			//供应商
			List<Map<String,Object>> supplierList = new ArrayList<Map<String, Object>>();
			//云仓货主
			List<Map<String,Object>> ownersList = new ArrayList<Map<String, Object>>();
			//私有平台商家
			List<Map<String,Object>> privateList = new ArrayList<Map<String, Object>>();
			//  获取入驻商用户节点列表
			List<Map<String,Object>> nodeList = stationedDao.queryAllNodeList(paramMap);
			for (Map<String, Object> nodeMap : nodeList) {
				int merchantsType = Integer.parseInt(nodeMap.get("merchants_type").toString());
				if (merchantsType == 1) {
					stationedList.add(nodeMap);
				} else if (merchantsType == 2) {
					supplierList.add(nodeMap);
				} else if (merchantsType == 3) {
					ownersList.add(nodeMap);
				} else if (merchantsType == 4) {
					privateList.add(nodeMap);
				}
			}
			resultMap.put("stationed", stationedList);
			resultMap.put("supplier", supplierList);
			resultMap.put("owners", ownersList);
			resultMap.put("private", privateList);
			pr.setState(true);
			pr.setMessage("获取入驻商节点权限成功");
			pr.setObj(resultMap);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage("获取入驻商节点权限失败");
		}

		return pr;
	}

	/*****************************************************************系统入驻商角色管理*******************************************************************************************************/
	
	/**
	 * 查询供应商
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupplierList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验id参数
            if(!paramMap.containsKey("id")||StringUtils.isEmpty(paramMap.get("id"))) {
            	pr.setState(false);
            	pr.setMessage("入驻商ID为空");
            	return pr;
            }
            //1.查询供应商
            List<Map<String, Object>> dataList= stationedDao.querySupplierList(paramMap);
            
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
	 * 供应商配置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editSupplier(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            
            paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验id参数
            if(!paramMap.containsKey("id")||StringUtils.isEmpty(paramMap.get("id"))) {
            	pr.setState(false);
            	pr.setMessage("入驻商ID为空");
            	return pr;
            }
            //1.删除入驻商关联的供应商
            stationedDao.deleteSupplier(paramMap);
            List<String> supplier_ids= (List<String>) paramMap.get("supplier_id");
            if(supplier_ids.size()>0){
            	//2.新增
                if(stationedDao.insertSupplier(paramMap) > 0) {
                	//记录入驻商供应商关联配置日志
                	Map<String, Object> map = stationedDao.queryStationedDetail(paramMap);
            		Map<String,Object> logMap=new HashMap<String,Object>();
            		logMap.put("USER_TYPE", 2);
            		logMap.put("OPERATE_ID", 3);
            		logMap.put("REMARK", "配置【供应商关联配置】");
            		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
            		logMap.put("USER_NAME", map.get("user_name"));
            		logMap.put("USER_REALNAME", map.get("company_name"));
            		stationedDao.insertUserOperationLog(logMap);
            		pr.setState(true);
            		pr.setMessage("供应商关联配置成功");
                } else {
                    pr.setState(false);
            		pr.setMessage("供应商关联配置失败");
                }
            }else{
            	pr.setState(true);
        		pr.setMessage("供应商关联配置成功");
            }
            
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 入驻商认证审批列表
	 * @param request
	 * @return
	 */
	public GridResult stationedCertificationApprovalList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
				params.put("state",(params.get("state")+"").split(","));
			}
			List<Map<String,Object>> list = null;
			int count=stationedDao.queryStationedCertificationApprovalCount(params);
			list = stationedDao.queryStationedCertificationApprovalList(params);
			if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
				gr.setTotal(count);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 * 入驻商认证审批详情
	 * @param request
	 * @return
	 */
	public GridResult stationedCertificationApprovalDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
                return gr;
            }
			Map<String,Object> detail = stationedDao.queryStationedCertificationApprovalDetail(params);
			if (detail != null ) {
				gr.setMessage("查询成功!");
				gr.setObj(detail);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 * 入驻商认证审核
	 * @param request
	 * @return
     */
	@Transactional
	public ProcessResult stationedCertificationApproval(HttpServletRequest request) throws Exception{
		
		    Map<String,Object> params = null;
		    Map<String,Object> stationedInfoParam=new HashMap<String,Object>();
		    ProcessResult pr = new ProcessResult();
		    try{
				String json = HttpUtil.getRequestInputStream(request);
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
				//查询入驻商认证信息
				Map<String,Object> detail=stationedDao.queryStationedCertificationApprovalDetail(params);
				if(Integer.parseInt(detail.get("STATE").toString())!=0){
					pr.setState(false);
					pr.setMessage("当前入驻商认证信息状态异常");
					return pr;
				}
				stationedInfoParam.put("id", detail.get("USER_ID"));
				//查询 入驻商详情
				Map<String, Object> map =stationedDao.queryStationedAuditDetail(stationedInfoParam);;
				map.put("user_id", detail.get("USER_ID"));
				params.put("user_id", detail.get("USER_ID"));
				if(Integer.parseInt(params.get("state").toString())==1){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("user_id", map.get("id"));
					param.put("name", map.get("company_name") );
					param.put("alias_name", map.get("company_name"));
					param.put("service_phone", "0577-56578888");
					param.put("id_card_name", detail.get("USER_REAL_NAME"));
					param.put("id_card_num", detail.get("USER_MANAGE_CARDID"));
					param.put("store_address", detail.get("ADDRESS"));
					param.put("id_card_hand_img_url", detail.get("USER_MANAGE_CARDID_IMG"));
					param.put("store_front_img_url", detail.get("USER_COMPANY_IMG"));
					param.put("business_license_img_url", map.get("business_licence_imgurl"));
					param.put("province", "");
					param.put("city", "");
					param.put("district", "");

					//远程调用见证宝接口 查询是否存在银行子账号
					pr=HttpClientUtil.post(pay_service_url+"/bankAccount/query",param);
					if(pr.getState()){
						Map<String, Object> retMap=(Map<String, Object>) pr.getObj();
						map.put("bank_account", retMap.get("bank_account"));
						map.put("sub_merchant_id", retMap.get("sub_merchant_id"));
					}else{
						logger.error("注册银行会员子账户失败"+pr.getMessage());
						throw new RuntimeException("审批失败："+pr.getMessage());
					}
					//会员账户表更新见证宝相关信息
					if(stationedDao.updateBankAccountInfo(map)>0 && stationedDao.updateBankCardUserInfo(params)>0){
						pr.setState(true);
						pr.setMessage("审批成功");
					}else{
						pr.setState(false);
						pr.setMessage("审批失败");
					}
				}else{
					//将银行卡会员资料信息设置为驳回状态
					int count=stationedDao.updateBankCardUserInfo(params);
					if(count>0){
						pr.setState(true);
						pr.setMessage("驳回成功");
					}else{
						pr.setState(false);
						pr.setMessage("驳回失败");
					}
				}
			} catch (Exception e) {
		        pr.setState(false);
		        pr.setMessage(e.getMessage());
		        throw new RuntimeException(e);
		    }
		return pr;
	}
	
	/**
	 * 配置入驻商服务默认缺货订购仓库
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editStationedDefaultOutstockWarehouse(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数，入驻商ID不能为空");
                return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("default_outstock_warehouse"))){
				pr.setState(false);
				pr.setMessage("缺少参数，缺货订购仓库ID不能为空");
				return pr;
			}
			if(stationedDao.updateStationed(paramMap)<=0){
				pr.setState(false);
				pr.setMessage("默认缺货订购仓设置失败");
				return pr ;
			};
			//记录入驻商缺货订购配置日志
        	Map<String, Object> map = stationedDao.queryStationedDetail(paramMap);
    		Map<String,Object> logMap=new HashMap<String,Object>();
    		logMap.put("USER_TYPE", 2);
    		logMap.put("OPERATE_ID", 3);
    		logMap.put("REMARK", "配置【入驻商缺货订购仓库配置】");
    		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
    		logMap.put("USER_NAME", map.get("user_name"));
    		logMap.put("USER_REALNAME", map.get("company_name"));
    		stationedDao.insertUserOperationLog(logMap);
			pr.setState(true);
			pr.setMessage("默认缺货订购仓设置成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("设置失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 查询保证金余额和账户余额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDepositMoneyBalance(HttpServletRequest request) {
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
			Map<String, Object> retMap =stationedDao.queryBankAccountInfo(paramMap);
			pr.setState(true);
        	pr.setMessage("查询成功");
        	pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
		}	
		return pr;
	}
	/**
	 * 查询入驻商补扣款列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySupDeductListForPage(HttpServletRequest request) {
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
			
			if((!StringUtils.isEmpty(paramMap.get("states")))&&paramMap.get("states") instanceof String){
				paramMap.put("states",(paramMap.get("states")+"").split(","));
			}
			
			//查询入驻商补扣款总数
			int total = stationedDao.querySupDeductCount(paramMap);
			//查询入驻商补扣款列表
			List<Map<String, Object>> dataList = stationedDao.querySupDeductListForPage(paramMap);
			
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
	 * 新增入驻商补扣款
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addSupDeduct(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("stationed_user_id"))||
			   StringUtils.isEmpty(paramMap.get("money"))||
			   StringUtils.isEmpty(paramMap.get("type"))||
			   StringUtils.isEmpty(paramMap.get("account_type"))||
			   StringUtils.isEmpty(paramMap.get("remark"))){
				pr.setState(false);
            	pr.setMessage("缺少参数");
                return pr;
			}
			
			if(stationedDao.insertSupDeduct(paramMap)<=0){
				pr.setState(false);
				pr.setMessage("新增失败");
				return pr;
			}
			pr.setState(true);
			pr.setMessage("新增成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("新增入驻商补扣款失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 删除入驻商补扣款
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeSupDeduct(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数");
                return pr;
			}
			
			if(stationedDao.deleteSupDeduct(paramMap)<=0){
				pr.setState(false);
				pr.setMessage("删除失败");
				return pr ;
			}
			pr.setState(true);
			pr.setMessage("删除成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("删除入驻商补扣款失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 编辑入驻商补扣款
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editSupDeduct(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))||
			   StringUtils.isEmpty(paramMap.get("stationed_user_id"))||
			   StringUtils.isEmpty(paramMap.get("money"))||
			   StringUtils.isEmpty(paramMap.get("type"))||
			   StringUtils.isEmpty(paramMap.get("account_type"))||
			   StringUtils.isEmpty(paramMap.get("remark"))){
				pr.setState(false);
            	pr.setMessage("缺少参数");
                return pr;
			}
			
			if(stationedDao.updateSupDeduct(paramMap)<=0){
				pr.setState(false);
				pr.setMessage("更新失败");
				return pr ;
			}
			pr.setState(true);
			pr.setMessage("更新成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("更新入驻商补扣款失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 查询入驻商补扣款详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySupDeductDetail(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
            	pr.setMessage("缺少参数");
                return pr;
			}
			
			Map<String, Object> resultMap = stationedDao.querySupDeductDetail(paramMap);
			pr.setState(true);
			pr.setObj(resultMap);
			pr.setMessage("查询成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 入驻商补扣款审批
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult approvalSupDeduct(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("state"))){
				pr.setState(false);
            	pr.setMessage("缺少参数");
                return pr;
			}
			if(stationedDao.approvalSupDeduct(paramMap)<=0){
				pr.setState(false);
				pr.setMessage("审批失败");
				return pr ;
			}
			//审批通过
			if("2".equals(paramMap.get("state").toString())) {
				Map<String, Object> param = stationedDao.querySupDeductDetail(paramMap);
				Map<String,Object> codeParams = new HashMap<String,Object>();
				codeParams.put("user_id", param.get("STATIONED_USER_ID"));
				//查询入驻商账户信息
				Map<String, Object> baMap = stationedDao.queryBankAccountInfo(codeParams);
				if(StringUtils.isEmpty(baMap)){
					throw new RuntimeException("入驻商异常！");
				}
				if("2".equals(param.get("type").toString())) {
					if(Double.parseDouble(baMap.get("DEPOSIT_MONEY_BALANCE")+"") < Double.parseDouble(param.get("money")+"")) {
						throw new RuntimeException("审批失败：保证金余额不足！");
					}
				}
			}
			
			pr.setState(true);
			pr.setMessage("审批成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("审批入驻商补扣款失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 入驻商补扣款签批
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult signSupDeduct(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("state"))){
				pr.setState(false);
            	pr.setMessage("缺少参数");
                return pr;
			}
			if(stationedDao.signApprovalSupDeduct(paramMap)<=0){
				pr.setState(false);
				pr.setMessage("签批失败");
				return pr ;
			}
			//签批通过
			Map<String, Object> param = stationedDao.querySupDeductDetail(paramMap);
			//锁入驻商
			stationedDao.supDeductLockTable(paramMap);
			Map<String,Object> codeParams = new HashMap<String,Object>();
			codeParams.put("user_id", param.get("STATIONED_USER_ID"));
			//查询入驻商账户信息
			Map<String, Object> baMap = new HashMap<String, Object>();
			if("1".equals(param.get("account_type").toString())) { //私有商账户
				baMap = pvtpStationedDao.queryBankAccountInfo(codeParams);
			} else {
				baMap = stationedDao.queryBankAccountInfo(codeParams);
			}
			if(StringUtils.isEmpty(baMap)){
				throw new RuntimeException("账户异常！");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			double money = 0;
			double accountBalance = 0;
			if("扣款".equals(param.get("type").toString())) {//扣款
				double depositMoneyBalance = Double.parseDouble(baMap.get("DEPOSIT_MONEY_BALANCE")+"");
				double deductMoney = Double.parseDouble(param.get("money")+"");
				if(DoubleUtils.add(depositMoneyBalance, Double.parseDouble(baMap.get("ACCOUNT_BALANCE")+"")) < deductMoney) {
					throw new RuntimeException("审批失败：扣款金额大于保证金余额，账户余额之和！");
				}
				//扣款金额大于保证金余额，则多余部分扣到账户余额上。
				if(deductMoney > depositMoneyBalance) {
					money = DoubleUtils.sub(deductMoney, depositMoneyBalance); 
					accountBalance = DoubleUtils.sub(Double.parseDouble(baMap.get("ACCOUNT_BALANCE")+""), money); 
					codeParams.put("account_balance", accountBalance);
					codeParams.put("deposit_money_balance", 0);
				}else {
					codeParams.put("deposit_money_balance", DoubleUtils.sub(depositMoneyBalance, deductMoney));
				}

				if("1".equals(param.get("account_type").toString())) { //私有商账户
					// 校验私有商家保证金余额校验码
					if(pvtpStationedDao.queryCheckDepositBalance(codeParams) == 0){
						throw new RuntimeException("保证金余额发生篡改，无法完成当前操作");
					}
					if(pvtpStationedDao.updateDepositMoneyBalanceInfo(codeParams)==0){
						throw new RuntimeException("更新保证金余额错误");
					}
					// 更新私有商家保证金余额校验码
					pvtpStationedDao.updateUserAccountCheckCode(codeParams);
				} else {
					// 校验入驻商保证金余额校验码
					if(stationedDao.queryCheckDepositBalance(codeParams) == 0){
						throw new RuntimeException("保证金余额发生篡改，无法完成当前操作");
					}
					if(stationedDao.updateDepositMoneyBalanceInfo(codeParams)==0){
						throw new RuntimeException("更新保证金余额错误");
					}
					// 更新入驻商保证金余额校验码
					stationedDao.updateUserAccountCheckCode(codeParams);
				}
				map.put("id", paramMap.get("id"));
				map.put("record_channel", "保证金");
				map.put("surplus_money", codeParams.get("deposit_money_balance"));
				map.put("money", DoubleUtils.sub(Double.parseDouble(param.get("money")+""), money));
				map.put("log_type", "违规扣款");
				map.put("remark", "补扣单【" + param.get("sup_deduct_number") + "】违规扣款");
				//收支类型
				map.put("record_type", "付款");
				//收付类型
				map.put("turnover_type", "扣款");
				map.put("account_id", baMap.get("ID"));
				map.put("account_flag", param.get("account_type"));

                //增加入驻商资金流水
    			if(stationedDao.insertStationedCapitalLogsBySupDeductPass(map)==0) {
    				throw new RuntimeException("生成入驻商资金流水失败！");
    			}
    			//增加入驻商收支记录
    			if(stationedDao.insertStationRecordBySupDeductPass(map)==0) {
    				throw new RuntimeException("生成入驻商收支记录失败！");
    			}
    			if(money > 0) {
					if("1".equals(param.get("account_type").toString())) { //私有商账户
						// 校验入驻商余额校验码
						if(pvtpStationedDao.queryCheckAccountBalance(codeParams) == 0){
							throw new RuntimeException("余额发生篡改，无法完成当前操作");
						}
						// 更新入驻商余额
						if (pvtpStationedDao.updateAccountBalanceInfo(codeParams) <= 0) {
							throw new RuntimeException("更新余额失败");
						}
						// 更新私有商家保证金余额校验码
						pvtpStationedDao.updateUserAccountCheckCode(codeParams);
					} else {
						// 校验入驻商余额校验码
						if(stationedDao.queryCheckAccountBalance(codeParams) == 0){
							throw new RuntimeException("余额发生篡改，无法完成当前操作");
						}
						// 更新入驻商余额
						if (stationedDao.updateAccountBalanceInfo(codeParams) <= 0) {
							throw new RuntimeException("更新余额失败");
						}
						// 更新入驻商余额校验码
						stationedDao.updateUserAccountCheckCode(codeParams);
					}
	                map.put("id", paramMap.get("id"));
	                map.put("record_channel", "余额");
	                map.put("surplus_money", accountBalance);
	                map.put("money", money);
	                map.put("log_type", "违规扣款");
	                map.put("remark", "补扣单【" + param.get("sup_deduct_number") + "】违规扣款");
	                //收支类型
	                map.put("record_type", "付款");
	                //收付类型
	                map.put("turnover_type", "扣款");

					//增加入驻商资金流水
	    			if(stationedDao.insertStationedCapitalLogsBySupDeductPass(map)==0) {
	    				throw new RuntimeException("生成入驻商资金流水失败！");
	    			}
	    			//增加入驻商收支记录
	    			if(stationedDao.insertStationRecordBySupDeductPass(map)==0) {
	    				throw new RuntimeException("生成入驻商收支记录失败！");
	    			}
    			}
			}else {//补款
				double depositMoneyBalance = DoubleUtils.add(Double.parseDouble(baMap.get("DEPOSIT_MONEY_BALANCE")+""), Double.parseDouble(param.get("money")+""));
				double depositMoney = Double.parseDouble(baMap.get("DEPOSIT_MONEY")+"");
				//补款后保证金余额是否超出保证金
				if(depositMoneyBalance > depositMoney ) {
					money = DoubleUtils.sub(depositMoneyBalance, depositMoney);
					accountBalance = DoubleUtils.add(Double.parseDouble(baMap.get("ACCOUNT_BALANCE")+""), money);
					codeParams.put("account_balance", accountBalance);
					codeParams.put("deposit_money_balance", depositMoney);
				}else {
					codeParams.put("deposit_money_balance", depositMoneyBalance);
				}

				if("1".equals(param.get("account_type").toString())) { //私有商账户
					// 校验私有商家保证金余额校验码
					if(pvtpStationedDao.queryCheckDepositBalance(codeParams) == 0){
						throw new RuntimeException("保证金余额发生篡改，无法完成当前操作");
					}
					if(pvtpStationedDao.updateDepositMoneyBalanceInfo(codeParams)==0){
						throw new RuntimeException("更新保证金余额错误");
					}
					// 更新私有商家保证金余额校验码
					pvtpStationedDao.updateUserAccountCheckCode(codeParams);

				} else {
					// 校验入驻商保证金余额校验码
					if(stationedDao.queryCheckDepositBalance(codeParams) == 0){
						throw new RuntimeException("保证金余额发生篡改，无法完成当前操作");
					}
					if(stationedDao.updateDepositMoneyBalanceInfo(codeParams)==0){
						throw new RuntimeException("更新保证金余额错误");
					}
					// 更新入驻商保证金余额校验码
					stationedDao.updateUserAccountCheckCode(codeParams);
				}

                map.put("id", paramMap.get("id"));
                map.put("record_channel", "保证金");
                map.put("surplus_money", codeParams.get("deposit_money_balance"));
                map.put("money", DoubleUtils.sub(Double.parseDouble(param.get("money")+""), money));
                map.put("log_type", "补款");
                map.put("remark", "补扣单【" + param.get("sup_deduct_number") + "】补款");
                //收支类型
                map.put("record_type", "收款");
                //收付类型
                map.put("turnover_type", "补款");
				map.put("account_id", baMap.get("ID"));
				map.put("account_flag", param.get("account_type"));

				//增加入驻商资金流水
    			if(stationedDao.insertStationedCapitalLogsBySupDeductPass(map)==0) {
    				throw new RuntimeException("生成入驻商资金流水失败！");
    			}
    			//增加入驻商收支记录
    			if(stationedDao.insertStationRecordBySupDeductPass(map)==0) {
    				throw new RuntimeException("生成入驻商收支记录失败！");
    			}
    			//保证金余额超出保证金，则超出部分加到余额里
    			if(money > 0) {
					if("1".equals(param.get("account_type").toString())) { //私有商账户
						// 校验入驻商余额校验码
						if(pvtpStationedDao.queryCheckAccountBalance(codeParams) == 0){
							throw new RuntimeException("余额发生篡改，无法完成当前操作");
						}
						// 更新入驻商余额
						if (pvtpStationedDao.updateAccountBalanceInfo(codeParams) <= 0) {
							throw new RuntimeException("更新余额失败");
						}
						// 更新私有商家保证金余额校验码
						pvtpStationedDao.updateUserAccountCheckCode(codeParams);
					} else {
						// 校验入驻商余额校验码
						if(stationedDao.queryCheckAccountBalance(codeParams) == 0){
							throw new RuntimeException("余额发生篡改，无法完成当前操作");
						}
						// 更新入驻商余额
						if (stationedDao.updateAccountBalanceInfo(codeParams) <= 0) {
							throw new RuntimeException("更新余额失败");
						}
						// 更新入驻商余额校验码
						stationedDao.updateUserAccountCheckCode(codeParams);
					}
	                map.put("id", paramMap.get("id"));
	                map.put("record_channel", "余额");
	                map.put("surplus_money", accountBalance);
	                map.put("money", money);
	                map.put("log_type", "补款");
	                map.put("remark", "补扣单【" + param.get("sup_deduct_number") + "】补款");
	                //收支类型
	                map.put("record_type", "收款");
	                //收付类型
	                map.put("turnover_type", "补款");

	                //增加入驻商资金流水
	    			if(stationedDao.insertStationedCapitalLogsBySupDeductPass(map)==0) {
	    				throw new RuntimeException("生成入驻商资金流水失败！");
	    			}
	    			//增加入驻商收支记录
	    			if(stationedDao.insertStationRecordBySupDeductPass(map)==0) {
	    				throw new RuntimeException("生成入驻商收支记录失败！");
	    			}
    			}
			}
			
			pr.setState(true);
			pr.setMessage("签批成功");
		} catch (Exception e) {
			pr.setState(false);
        	pr.setMessage(e.getMessage());
        	logger.error("签批入驻商补扣款失败："+e.getMessage());
        	throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 云仓货主配置【童库-私有商品共享】
	 * @param request
	 * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult cloudWarehouseOwnerSet(HttpServletRequest request) throws Exception{
		
		    Map<String,Object> params = null;
		    ProcessResult pr = new ProcessResult();
		    try{
				String json = HttpUtil.getRequestInputStream(request);
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
				if(StringUtils.isEmpty(params.get("stat_user_id"))){
					pr.setState(false);
					pr.setMessage("缺少入驻商参数");
					return pr;
				}
				params.put("merchants_type", 3);
				int privateBusinessCount =stationedDao.queryStationedMerchantsTypeCount(params);
				Map<String,Object> tempMap=new HashMap<String,Object>();
				tempMap.put("id", params.get("stat_user_id"));
				Map<String,Object> stationedMap = stationedDao.queryStationedDetail(tempMap);
				//2.1如果已开通私有商家，只用编辑私有商家关联站点信息
				if(privateBusinessCount > 0){
					Map<String,Object> cloudWarehouseOwnerMap =stationedDao.queryCloudWarehouseOwnerSet(params);
					if(cloudWarehouseOwnerMap==null){
						stationedDao.insertCloudWarehouseOwnerSet(params);
					}else{
						stationedDao.updateCloudWarehouseOwnerSet(params);
					}
				}else{
					Map<String,Object> merchantsTypeParamsMap = new HashMap<String,Object>();
					merchantsTypeParamsMap.put("stationed_id", params.get("stat_user_id"));
					merchantsTypeParamsMap.put("merchants_types", new String[]{"3"});
					stationedDao.insertMerchantsType(merchantsTypeParamsMap);
					stationedDao.insertCloudWarehouseOwnerSet(params);
				}
				//记录入驻商创建日志
				Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 2);
        		logMap.put("OPERATE_ID", 1);
        		logMap.put("REMARK", privateBusinessCount <=0?"开通":"编辑"+"【云仓货主】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", stationedMap.get("user_name"));
        		logMap.put("USER_REALNAME", stationedMap.get("company_name"));
        		stationedDao.insertUserOperationLog(logMap);
        		pr.setMessage("云仓货主配置成功");
        		pr.setState(true);
			} catch (Exception e) {
		        pr.setState(false);
		        pr.setMessage(e.getMessage());
		        throw new RuntimeException(e);
		    }
		return pr;
	}
	
	/**
	 * 获取云仓货主配置详情【童库-私有商品共享】
	 * @param request
	 * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult getCloudWarehouseOwnerSet(HttpServletRequest request) throws Exception{
		
		    Map<String,Object> params = null;
		    ProcessResult pr = new ProcessResult();
		    try{
				String json = HttpUtil.getRequestInputStream(request);
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
				if(StringUtils.isEmpty(params.get("stat_user_id"))){
					pr.setState(false);
					pr.setMessage("缺少入驻商参数");
					return pr;
				}
				//获取云仓货主配置信息
				Map<String,Object> cloudWarehouseOwnerMap =stationedDao.queryCloudWarehouseOwnerSet(params);
				pr.setState(true);
				pr.setMessage("获取云仓货主配置成功");
				pr.setObj(cloudWarehouseOwnerMap);
			} catch (Exception e) {
		        pr.setState(false);
		        pr.setMessage(e.getMessage());
		        throw new RuntimeException(e);
		    }
		return pr;
	}
	
	/** 
	 * 私有商家配置【童库-私有商品共享】
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult privateBusinessSet(HttpServletRequest request) throws Exception{
		    Map<String,Object> paramsMap = null;
		    ProcessResult pr = new ProcessResult();
		    try{
		    	//1.请求参数处理
				String json = HttpUtil.getRequestInputStream(request);
				paramsMap = (Map<String,Object>)Transform.GetPacket(json, Map.class);
				if(StringUtils.isEmpty(paramsMap.get("stat_user_id"))){
					pr.setState(false);
					pr.setMessage("缺少入驻商参数");
					return pr;
				}
				//2.校验商家是否已开通过私有商家
				paramsMap.put("merchants_type", 4);
				int privateBusinessCount =stationedDao.queryStationedMerchantsTypeCount(paramsMap);
				paramsMap.put("id", paramsMap.get("stat_user_id"));
				Map<String,Object> stationedMap =stationedDao.queryStationedDetail(paramsMap);
				//2.1如果已开通私有商家，只用编辑私有商家关联站点信息
				if(privateBusinessCount > 0){
					//获取私有商家关联的站点id
					Map<String,Object> tempParamMap = new HashMap<String,Object>();
					tempParamMap.put("stationed_user_id", paramsMap.get("stat_user_id"));
					Map<String,Object> stationedSiteMap = siteInfoDao.querySiteInfoByStationedUser(tempParamMap);
					if(stationedSiteMap == null){
						pr.setState(false);
						pr.setMessage("私有商家站点配置异常，请联系管理员");
						return pr;
					}
					tempParamMap.put("name", paramsMap.get("site_name"));
					if(!paramsMap.get("site_name").equals(stationedSiteMap.get("NAME")) && siteInfoDao.checkSiteName(tempParamMap) > 0){
						pr.setState(false);
						pr.setMessage("站点名称已存在，请修改");
						return pr;
					}
					if(!paramsMap.get("site_name").equals(stationedSiteMap.get("NAME")) || !paramsMap.get("site_code").equals(stationedSiteMap.get("SITE_CODE"))){
						//更新站点信息
						Map<String,Object> stationedSiteParamMap = new HashMap<String,Object>();
						stationedSiteParamMap.put("id", stationedSiteMap.get("ID"));
						stationedSiteParamMap.put("NAME", paramsMap.get("site_name"));
						stationedSiteParamMap.put("SITE_CODE", paramsMap.get("site_code"));
						stationedSiteParamMap.put("platform_type", 2);
						stationedSiteParamMap.put("stationed_user_id", paramsMap.get("stat_user_id"));
						siteInfoDao.updateSiteInfo(stationedSiteParamMap);
					}
					//更新站点仓库信息
					tempParamMap = new HashMap<String,Object>();
					tempParamMap.put("site_id", stationedSiteMap.get("ID"));
					siteInfoDao.deleteSiteWarehouse(tempParamMap);
					Map<String, Object> siteWarehouseMap = new HashMap<String, Object>();
					ArrayList<String> parentWarehouseList = (ArrayList<String>)paramsMap.get("site_parent_warehouse");
					ArrayList<String> warehouseList=null;
					if(paramsMap.get("site_warehouse") instanceof java.lang.String){
						warehouseList = new ArrayList<String>();
						warehouseList.add(paramsMap.get("site_warehouse").toString());
					}else{
						warehouseList = (ArrayList<String>)paramsMap.get("site_warehouse");
					}
					siteWarehouseMap.put("site_id", stationedSiteMap.get("ID"));
					for(int i=0;i<parentWarehouseList.size();i++){
						siteWarehouseMap.put("parent_warehouse_id", parentWarehouseList.get(i));
						siteWarehouseMap.put("warehouse_id", warehouseList.get(i));
						//新增站点仓库信息
						siteInfoDao.insertSiteWarehouse(siteWarehouseMap);
					}
				}else{
					//2.2未开通私有商家处理
					if(StringUtils.isEmpty(paramsMap.get("site_name"))||StringUtils.isEmpty(paramsMap.get("site_code"))||StringUtils.isEmpty(paramsMap.get("site_warehouse"))){
						pr.setState(false);
						pr.setMessage("缺少私有商家配置参数");
						return pr;
					}
					String site_id = "";
					//2.21处理站点与私有商家关联信息（无站点直接新增，【已有进行关联编辑暂未处理】）
					Map<String,Object> siteParamsMap = new HashMap<String,Object>();
					siteParamsMap.put("stationed_user_id", paramsMap.get("stat_user_id"));
					siteParamsMap.put("name", paramsMap.get("site_name"));
					siteParamsMap.put("site_code", paramsMap.get("site_code"));
					siteParamsMap.put("discount", 1);
					siteParamsMap.put("remark", "");
					siteParamsMap.put("create_user_id", paramsMap.get("public_user_id"));
					siteParamsMap.put("state", "2");
					siteParamsMap.put("platform_type", "2");
					if(siteInfoDao.checkSiteName(siteParamsMap)<=0){
						if (siteInfoDao.insertSiteInfo(siteParamsMap) > 0) {
							Map<String, Object> siteWarehouseMap = new HashMap<String, Object>();
							ArrayList<String> parentWarehouseList = (ArrayList<String>)paramsMap.get("site_parent_warehouse");
							ArrayList<String> warehouseList=null;
							if(paramsMap.get("site_warehouse") instanceof java.lang.String){
								warehouseList = new ArrayList<String>();
								warehouseList.add(paramsMap.get("site_warehouse").toString());
							}else{
								warehouseList = (ArrayList<String>)paramsMap.get("site_warehouse");
							}
							siteWarehouseMap.put("site_id", siteParamsMap.get("id"));
							site_id = siteParamsMap.get("id")+"";
							for(int i=0;i<parentWarehouseList.size();i++){
								siteWarehouseMap.put("parent_warehouse_id", parentWarehouseList.get(i));
								siteWarehouseMap.put("warehouse_id", warehouseList.get(i));
								//新增站点仓库信息
								siteInfoDao.insertSiteWarehouse(siteWarehouseMap);
							}
						} else {
							pr.setState(false);
							pr.setMessage("站点设置失败");
							return pr;
						}
					}else{
						pr.setState(false);
						pr.setMessage("站点名称已存在，请修改");
						return pr;
					}
					//2.22如果未开通TBL_MERCHANTS_TYPE
					Map<String,Object> merchantsTypeParamsMap = new HashMap<String,Object>();
					merchantsTypeParamsMap.put("stationed_id", paramsMap.get("stat_user_id"));
					merchantsTypeParamsMap.put("merchants_types", new String[]{"4"});
					stationedDao.insertMerchantsType(merchantsTypeParamsMap);
					//2.23处理私有商家特有账号信息
					Map<String,Object> pvtpBankParamsMap = new HashMap<String,Object>();
					pvtpBankParamsMap.put("user_id",  paramsMap.get("stat_user_id"));
					pvtpBankParamsMap.put("deposit_money",0);
					addPvtpUserAccount(pvtpBankParamsMap);
					//初始化私有商家配置信息
					stationedDao.insertPvtpConfig(pvtpBankParamsMap);
					//2.24私有商家开通店铺处理
					//远程调用scs_server开通店铺
					Map<String, Object> scsParamMap = new HashMap<String, Object>();
					scsParamMap.put("stationed_user_id", paramsMap.get("stat_user_id"));
					scsParamMap.put("site_id", site_id);
					//3.记录入驻商创建日志
					Map<String,Object> logMap=new HashMap<String,Object>();
	        		logMap.put("USER_TYPE", 2);
	        		logMap.put("OPERATE_ID", 1);
	        		logMap.put("REMARK", "开通【私有商家】");
	        		logMap.put("CREATE_USER_ID", paramsMap.get("public_user_id"));
	        		logMap.put("USER_NAME", stationedMap.get("user_name"));
	        		logMap.put("USER_REALNAME", stationedMap.get("company_name"));
	        		stationedDao.insertUserOperationLog(logMap);
					pr=HttpClientUtil.post(scs_server_url + "" + decorate_init,scsParamMap);
					if(!pr.getState()){
						throw new RuntimeException("默认店铺开通失败");
					}
				}
				pr.setState(true);
				pr.setMessage("私有商家配置成功");
			} catch (Exception e) {
		        pr.setState(false);
		        pr.setMessage(e.getMessage());
		        throw new RuntimeException(e);
		    }
		return pr;
	}
	
	/** 
	 * 获取私有商家配置详情【童库-私有商品共享】
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult getPrivateBusinessSet(HttpServletRequest request) throws Exception{
		    Map<String,Object> paramsMap = null;
		    Map<String,Object> resultMap = new HashMap<String,Object>();
		    ProcessResult pr = new ProcessResult();
		    try{
		    	//1.请求参数处理
				String json = HttpUtil.getRequestInputStream(request);
				paramsMap = (Map<String,Object>)Transform.GetPacket(json, Map.class);
				if(StringUtils.isEmpty(paramsMap.get("stat_user_id"))){
					pr.setState(false);
					pr.setMessage("缺少入驻商参数");
					return pr;
				}
				//2.获取当前商家绑定的私有站点id
				Map<String, Object> siteInfo = stationedDao.queryStationedSiteInfo(paramsMap);
				if(!StringUtils.isEmpty(siteInfo)){
					resultMap.put("site_id", siteInfo.get("site_id"));
					resultMap.put("site_name", siteInfo.get("site_name"));
					resultMap.put("site_code", siteInfo.get("site_code"));
					//根据站点获取站点已配置仓库使用
					paramsMap.put("site_id", siteInfo.get("site_id"));
				}
				//3.获取平台的所有可用仓库，如果站点已配置对应仓库，则根据site_warehouse字段为“1”进行标识
				List<Map<String,Object>> siteWarehouseList = stationedDao.queryStationedSiteWarehouses(paramsMap);
				resultMap.put("site_warehouse_list", siteWarehouseList);
				pr.setState(true);
				pr.setMessage("获取私有商家配置成功");
				pr.setObj(resultMap);
			} catch (Exception e) {
		        pr.setState(false);
		        pr.setMessage(e.getMessage());
		        throw new RuntimeException(e);
		    }
		return pr;
	}
	
	/**
	 * 入驻商家私有账号创建
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Transactional
	private int addPvtpUserAccount(Map<String, Object> map) throws Exception{
		//user_name直接使用id，如果为空，则直接赋值  20162002
		map.put("user_name", map.get("user_id"));
		
		//创建用户账户信息
		Map<String,Object> codeParams = new HashMap<String,Object>();
		codeParams.put("c_user_name",map.get("user_id"));
		codeParams.put("c_money",0);
		codeParams.put("c_typeid","new");
		codeParams.put("c_user_type","8");
		String key = stationedDao.getPvtpUserKey(codeParams);
		codeParams.put("c_user_key",key);
		String code = stationedDao.getPvtpCheckCode(codeParams);
		Map<String,Object> account = new HashMap<String,Object>();
		account.put("user_id", map.get("user_id"));
		account.put("bank_account", "");
		account.put("account_balance", 0);
		account.put("account_balance_checkcode", code);//余额校验码
		account.put("deposit_money", map.get("deposit_money"));
		account.put("deposit_money_balance", 0);
		account.put("deposit_checkcode", code);//保证金校验码
		account.put("sub_merchant_id","");
		//保存用户key
		Map<String,Object> uck = new HashMap<String,Object>();
		uck.put("user_name", map.get("user_id"));
		uck.put("cache_key", key);
		if(stationedDao.insertPvtpCacheKey(uck)>0){
			return stationedDao.insertPvtpBankAccountInfo(account);
		}
		return 0;
	}
}