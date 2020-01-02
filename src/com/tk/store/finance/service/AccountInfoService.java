package com.tk.store.finance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.store.finance.dao.AccountInfoDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : AccountInfoService
 * 账户信息service层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-9
 */
@Service("AccountInfoService")
public class AccountInfoService {
	@Resource
	private AccountInfoDao accountInfoDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	//支付项目地址
    @Value("${pay_service_url}")
    private String pay_service_url;
    @Value("${bank_unbind_card}")
    private String bank_unbind_card;//解绑
    @Value("${bankAccount_bind}")
    private String bankAccount_bind;//绑定
    @Value("${bankAccount_check_note}")
    private String bankAccount_check_note;//短信验证
    
	/**
	 * 查询个人基本信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAccountInfoBasic(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			//个人基本信息
			Map<String, Object> retMap = accountInfoDao.queryAccountInfoBasic(paramMap);
			pr.setMessage("获取个人基本信息成功");
			pr.setObj(retMap);
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取银行卡信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBankCardList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			//银行卡
			List<Map<String, Object>> list = accountInfoDao.queryBankCardList(paramMap);
			pr.setMessage("获取银行卡列表成功");
			pr.setObj(list);
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 认证信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAuthenticationInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			paramMap.put("user_id", paramMap.get("public_user_id"));
			//认证信息
			Map<String, Object> data = accountInfoDao.queryAuthenticationInfo(paramMap);
			pr.setMessage("获取认证信息成功");
			pr.setObj(data);
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 绑卡
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult bind(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if (StringUtils.isEmpty(paramMap.get("bank_card"))) {
                pr.setState(false);
                pr.setMessage("缺少参数，bank_card");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("bank_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数，bank_code");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("mobile_phone"))){
                pr.setState(false);
                pr.setMessage("缺少参数，mobile_phone");
                return pr;
            }
            
            //获取银行信息
            Map<String, Object> bankMap = accountInfoDao.queryBankForNote(paramMap);
            if(bankMap == null){
                pr.setState(false);
                pr.setMessage("未知银行");
                return pr;
            }
            paramMap.put("bank_clscode", bankMap.get("BANK_CLSCODE"));
            
            //判断是否已绑定银行卡
            if(accountInfoDao.queryUserBankCardCount(paramMap) >0){
            	pr.setState(false);
                pr.setMessage("已绑定银行卡，请解绑后再进行操作！");
                return pr;
            }
            
            paramMap.put("user_id", paramMap.get("public_user_id"));
			Map<String, Object> map = accountInfoDao.queryBankCardDetail(paramMap);
			//判断银行卡是否存在
            if(map != null) {
                pr.setState(false);
                pr.setMessage("银行卡已存在");
                return pr;
            }else{
            	 // 添加银行卡
                if(accountInfoDao.insert(paramMap) <= 0){
                	pr.setState(false);
                	throw new RuntimeException("绑定异常：添加银行卡失败");
                }
            	// 获取见证宝银行子账户
                Map<String, Object> userAccountMap = accountInfoDao.queryBankAccountByUserId(Integer.valueOf(paramMap.get("user_id").toString()));
                if(userAccountMap == null){
                    pr.setState(false);
                    pr.setMessage("用户子账户不存在");
                    return pr;
                }
                if(StringUtils.isEmpty(userAccountMap.get("BANK_ACCOUNT"))){
                    pr.setState(false);
                    pr.setMessage("缺少账号");
                    return pr;
                }
                //获取开户银行
                
                Map<String, Object> sbank = accountInfoDao.queryBankByClscode(paramMap.get("bank_clscode").toString());
                param.put("user_id", paramMap.get("user_id"));
                param.put("state", 1);
                // 获取用户认证信息
                Map<String, Object> userMap = accountInfoDao.queryAuthenticationInfo(param);
                // 组织见证宝绑定所需参数
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                paramsMap.put("user_id", paramMap.get("user_id"));	//用户ID
                paramsMap.put("sub_merchant_id", userAccountMap.get("SUB_MERCHANT_ID"));	//商户ID
                paramsMap.put("bank_account", userAccountMap.get("BANK_ACCOUNT"));	//银行账号
                paramsMap.put("user_name", userMap.get("USER_REAL_NAME"));			//用户名称
                paramsMap.put("id_card", userMap.get("USER_MANAGE_CARDID"));		//身份证
                paramsMap.put("bank_card", paramMap.get("bank_card").toString());	//银行卡号
                paramsMap.put("bank_name", bankMap.get("BANK_NAME").toString());	//银行名称
                paramsMap.put("bank_code", paramMap.get("bank_code").toString());	//银行行号
                paramsMap.put("super_bank_code", sbank.get("BANK_CODE").toString());//银行行号
                paramsMap.put("mobile_phone", paramMap.get("mobile_phone"));	//手机号码
                paramsMap.put("bind_type", "6056");
                
                // 提交见证宝绑定银行卡
                ProcessResult resPr = HttpClientUtil.post(pay_service_url + bankAccount_bind, paramsMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
                if (resPr.getState()) {
                    pr.setState(true);
                    pr.setMessage("绑定个人银行卡申请提交成功");
                } else {
                    pr.setState(false);
                    throw new RuntimeException("绑定异常："+resPr.getMessage());
                }
            }
		} catch (Exception e) {
			pr.setState(false);
			throw new RuntimeException("绑定异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 解绑
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult unbind(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数，id");
				return pr;
			}
			Map<String, Object> map = accountInfoDao.queryBankCardDetail(paramMap);
			//判断银行卡是否存在
            if(map == null) {
                pr.setState(false);
                pr.setMessage("银行卡不存在");
                return pr;
            }
            // 获取见证宝银行子账户
            Map<String, Object> userAccountMap = accountInfoDao.queryBankAccountByUserId(Integer.valueOf(map.get("USER_ID").toString()));
            if(userAccountMap == null){
                pr.setState(false);
                pr.setMessage("用户子账户不存在");
                return pr;
            }
            param.put("user_id", map.get("USER_ID"));
            param.put("state", 1);
            // 获取用户认证信息
            Map<String, Object> userMap = accountInfoDao.queryAuthenticationInfo(param);
            
        	// 组织见证宝解绑所需参数
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("user_id", map.get("USER_ID"));
            paramsMap.put("sub_merchant_id", userAccountMap.get("SUB_MERCHANT_ID"));    //商户ID
            paramsMap.put("id_type", 1);
            paramsMap.put("id_code", userMap.get("USER_MANAGE_CARDID"));
            paramsMap.put("bank_account", userAccountMap.get("BANK_ACCOUNT"));
            paramsMap.put("bank_card", map.get("BANK_CARD"));
            
            // 删除绑定银行卡
            if(accountInfoDao.unbind(paramMap) > 0){
                // 提交见证宝解绑银行卡
                ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_unbind_card, paramsMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
                if(!resPr.getState()){
                    throw new RuntimeException(resPr.getMessage());
                }
                pr.setState(true);
                pr.setMessage("解绑成功");
            }else{
                pr.setState(false);
                pr.setMessage("解绑失败");
            }
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("解绑异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 短信验证
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult checkNote(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("bank_card"))){
				pr.setState(false);
				pr.setMessage("缺少参数，bank_card");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("bank_code"))){
				pr.setState(false);
				pr.setMessage("缺少参数，bank_code");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("mobile_phone"))){
				pr.setState(false);
				pr.setMessage("缺少参数，mobile_phone");
				return pr;
			}
			//判断是否已绑定银行卡
            if(accountInfoDao.queryUserBankCardCount(paramMap) >0){
            	pr.setState(false);
                pr.setMessage("已绑定银行卡，请解绑后再进行操作！");
                return pr;
            }
			// 获取见证宝银行子账户
            Map<String, Object> userAccountMap = accountInfoDao.queryBankAccountByUserId(Integer.valueOf(paramMap.get("public_user_id").toString()));
			
            // 组织见证宝绑卡所需参数
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("user_id", paramMap.get("public_user_id"));
            paramsMap.put("sub_merchant_id", userAccountMap.get("SUB_MERCHANT_ID"));    //商户ID
            paramsMap.put("bank_account", userAccountMap.get("BANK_ACCOUNT"));
            paramsMap.put("bank_card", paramMap.get("bank_card"));
            paramsMap.put("check_code", paramMap.get("check_code"));
            paramsMap.put("mobile_phone", paramMap.get("mobile_phone"));
            // 添加银行卡
            accountInfoDao.insert(paramMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("绑卡异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 增加认证信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult addAuthenticationInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);				
			}
			Map<String, Object> checParam=new HashMap<String,Object>();
			Map<String, Object> detail=accountInfoDao.queryAuthenticationInfo(paramMap);
			if(detail != null){
				//判断身份证是否有重复
				SysUserInfo sysUserInfo=sysUserInfoDao.queryByUserId(Long.parseLong(detail.get("USER_ID").toString()));
				checParam.put("id", detail.get("ID"));
	        	checParam.put("user_manage_cardid", paramMap.get("user_manage_cardid"));
	        	checParam.put("user_type", sysUserInfo.getUser_type());
				int count=accountInfoDao.checkUserManageCardIdCount(checParam);
	        	if(count>0){
	        		pr.setState(false);
	                pr.setMessage("当前身份证号已注册,请修改");
	                return pr;
	        	}
	        	if(Integer.parseInt(detail.get("STATE").toString())==1){
	            	pr.setState(false);
	                pr.setMessage("当前用户资料信息已经审批通过,不允许修改");
	                return pr;
	            }
				//重新提交审批
				if(accountInfoDao.updateAuthenticationInfo(paramMap)>0){
					pr.setMessage("提交成功");
					pr.setState(true);
				}else{
					pr.setMessage("提交失败");
					pr.setState(false);
				}
			}else{
				//判断当前身份证号是否重复
				SysUserInfo sysUserInfo=sysUserInfoDao.queryByUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
				checParam.put("user_manage_cardid", paramMap.get("user_manage_cardid"));
				checParam.put("user_type", sysUserInfo.getUser_type());
	        	int count=accountInfoDao.checkUserManageCardIdCount(checParam);
	        	if(count>0){
	        		pr.setState(false);
	                pr.setMessage("当前身份证号已注册,请修改");
	                return pr;
	        	}
				//增加认证信息
				if (accountInfoDao.insertAuthenticationInfo(paramMap)>0) {				
					pr.setMessage("增加认证信息成功");
					pr.setState(true);
				} else {
					pr.setMessage("增加认证信息失败");
					pr.setState(false);
				}
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
