package com.tk.oms.sysuser.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.oms.oauth2.dao.UserAuthorDao;
import com.tk.oms.oauth2.entity.UserAuthor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.oms.sys.dao.SysConfigDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.dao.SysUserSiteDao;
import com.tk.oms.sysuser.dao.SysUserStoreDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;
import com.tk.sys.util.FileUtils;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import com.tk.sys.util.Utils;

/**
* Copyright (c), 2016, Tongku
* FileName : LoginControl.java
* ERP用户管理
* @author  wangpeng
* @date 2016-06-07
* @version1.00
*/
@Service("SysUserInfoService")
public class SysUserInfoService {
    private Log logger = LogFactory.getLog(this.getClass());
    @Value("${store_service_url}")
    private String store_service_url;
    @Resource
    private SysUserInfoDao sysUserInfoDao;
    @Resource
    private SysUserSiteDao sysUserSiteDao;
    @Resource
    private SysUserStoreDao sysUserStoreDao;
    @Resource
    private MemberInfoDao memberInfoDao;
    @Resource
    private SysConfigDao sysConfigDao;
    @Resource
    private UserAuthorDao userAuthorDao;

    /** sso数据业务接口 */
    @Value("${oauth.sso_service_url}")
    private String sso_service_url;

    /** 客户端系统Id */
    @Value("${oauth.oauth_client_id}")
    private String oauth_client_id;

    /** OA公司代码 */
    @Value ("${oauth.oa_company_code}")
    private String oa_company_code;
    @Value("${oa_service_url}")
    private String oaServerUrl;
    @Value("${oa.company_vcode}")
    private String oaCompanyVcode;

    /**
     * 后台用户登录
     */
    public ProcessResult sysUserInfoLogin(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String,Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)) {
                params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
                //判断用户名，密码非空
                if(!params.containsKey("user_name")||StringUtils.isEmpty(params.get("user_name"))
                    ||!params.containsKey("user_pwd")||StringUtils.isEmpty(params.get("user_pwd"))){
                    pr.setState(false);
                    pr.setMessage("缺少参数,用户名密码不能为空");
                }else{
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("userName", params.get("user_name").toString());
                    paramMap.put("userPwd", params.get("user_pwd"));
                    paramMap.put("company", oa_company_code);
                    paramMap.put("clientId", oauth_client_id);
                    paramMap.put("userIp", params.get("ip"));
                    paramMap.put("deviceCode", params.get("deviceCode"));
                    pr = (ProcessResult)HttpClientUtil.postToOA(sso_service_url + "oauth2/auth_login", paramMap);
                    if(pr.getState()){
                        pr.setState(true);
                        pr.setObj(pr.getObj());
                        pr.setMessage("登录成功");
                    }
                }
            }else {
                pr.setState(false);
                pr.setMessage("缺少参数,用户名密码不能为空");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            logger.error("登录出错"+e);
        }
        return pr;
    }
   
    
    /**
     * 新增管理用户
     * @param request
     * @return
     */
    public ProcessResult addSysUserInfo(HttpServletRequest request) {
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
            //校验user_name参数
            if(!params.containsKey("user_name")||StringUtils.isEmpty(params.get("user_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_name");
                return pr;
            }
            //校验user_pwd参数
            if(!params.containsKey("user_pwd")||StringUtils.isEmpty(params.get("user_pwd"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_pwd");
                return pr;
            }
            //校验user_realname参数
            if(!params.containsKey("user_realname")||StringUtils.isEmpty(params.get("user_realname"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_realname");
                return pr;
            }
            //校验phone参数
            if(!params.containsKey("phone")||StringUtils.isEmpty(params.get("phone"))) {
                pr.setState(false);
                pr.setMessage("缺少参数phone");
                return pr;
            }
            //校验state参数
            if(!params.containsKey("state")||StringUtils.isEmpty(params.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数state");
                return pr;
            }
            //校验state参数
            if(!params.containsKey("user_type")||StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_type");
                return pr;
            }
            //销售管理类型用户或业务经理
            if(params.get("user_type").equals("9")||params.get("user_type").equals("4")){
            	if(!params.containsKey("organization_id")||StringUtils.isEmpty(params.get("organization_id"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数organization_id");
                    return pr;
            	}
            }
            /*
            else if(!params.get("user_type").equals("2")){
            	if(!params.containsKey("site_id")||StringUtils.isEmpty(params.get("site_id"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数site_id");
                    return pr;
            	}
            	if(!params.containsKey("province")||StringUtils.isEmpty(params.get("province"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数province");
                    return pr;
            	}
            	if(!params.containsKey("city")||StringUtils.isEmpty(params.get("city"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数city");
                    return pr;
            	}
            	if(!params.containsKey("area")||StringUtils.isEmpty(params.get("area"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数area");
                    return pr;
            	}
            	
            	//根据省份ID获取大区ID
	            long user_company_address_max = sysUserInfoDao.queryBigAreaID(Integer.parseInt(params.get("province").toString()));
	            if(user_company_address_max==0){
	            	pr.setState(false);
	                pr.setMessage("非法的省份");
	                return pr;
	            }
	            params.put("user_company_address_max",user_company_address_max);
            }
            */
            
            //判读用户是否存在
            SysUserInfo us = sysUserInfoDao.querySysUserInfoByUserName(params.get("user_name").toString());
            if(us!=null){
            	 pr.setState(false);
                 pr.setMessage("该用户名已存在，请重新输入！");
                 return pr;
            }
            //密码加密
            // 加密
			byte[] encryptResult = Crypt.encrypt(params.get("user_pwd").toString(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.USER_PWD_KEY));
			// 编码
			String ciphertext = Base64.encode(encryptResult);
            
			params.put("user_pwd", ciphertext);
            if(sysUserInfoDao.insert(params) > 0 && addSysBankCount(params)>0) {
            	//记录系统用户创建日志
            	Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 1);
        		logMap.put("OPERATE_ID", 1);
        		logMap.put("REMARK", "创建【系统用户】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", params.get("user_name"));
        		logMap.put("USER_REALNAME", params.get("user_realname"));
        		sysUserInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("新增管理用户成功");
            } else {
                pr.setState(false);
                pr.setMessage("新增管理用户失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
    
    /**
	 * 初始化合作商账
	 * @param user
     */
	private int addSysBankCount(Map<String, Object> user){
		int l = 0;
		//创建用户账户信息
		Map<String,Object> codeParams = new HashMap<String,Object>();
		codeParams.put("c_user_name",user.get("id"));
		codeParams.put("c_money",0);
		codeParams.put("c_typeid","new");
		codeParams.put("c_user_type","4");
		String key = memberInfoDao.getUserKey(codeParams);
		codeParams.put("c_user_key",key);
		String code = memberInfoDao.getCheck_Code(codeParams);
		Map<String,Object> account = new HashMap<String,Object>();
		account.put("user_id", user.get("id"));
		account.put("account_balance_checkcode",code);
		
		//保存用户key
		Map<String,Object> uck = new HashMap<String,Object>();
		uck.put("user_name", user.get("id"));
		uck.put("cache_key", key);
		if(sysUserInfoDao.insertSysUserCacheKey(uck)>0){
			l=sysUserInfoDao.insertSysBankAccount(account);
		}
		return l;
	}
    
    /**
     * 禁用启用管理用户
     * @param request
     * @return
     */
    public ProcessResult updateStateSysUserInfo(HttpServletRequest request) {
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
            //校验state参数
            if(!params.containsKey("state")||StringUtils.isEmpty(params.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数state");
                return pr;
            }

            if(sysUserInfoDao.update_state(params) > 0) {
        		pr.setState(true);
        		if(params.get("state").toString().equals("2")){
        			pr.setMessage("启用管理用户成功");
        		}else{
        			pr.setMessage("禁用管理用户成功");
        		}
        		SysUserInfo info=sysUserInfoDao.queryByUserId(Long.parseLong(params.get("id")+""));
        		//记录启用/禁用操作日志
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 1);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【启用停用】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", info.getUser_name());
        		logMap.put("USER_REALNAME", info.getUser_realname());
        		sysUserInfoDao.insertUserOperationLog(logMap);
            } else {
                pr.setState(false);
                if(params.get("state").toString().equals("2")){
        			pr.setMessage("启用管理用户失败");
        		}else{
        			pr.setMessage("禁用管理用户失败");
        		}
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
    
    /**
     * 管理用户密码重置
     * @param request
     * @return
     */
    public ProcessResult updatePwdSysUserInfo(HttpServletRequest request) {
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
            //校验state参数
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
            if(sysUserInfoDao.update_pwd(params) > 0) {
            	SysUserInfo info=sysUserInfoDao.queryByUserId(Long.parseLong(params.get("id")+""));
        		//记录密码重置日志
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 1);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【密码重置】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", info.getUser_name());
        		logMap.put("USER_REALNAME", info.getUser_realname());
        		sysUserInfoDao.insertUserOperationLog(logMap);
        		pr.setState(true);
        		pr.setMessage("管理用户密码重置成功");
            } else {
                pr.setState(false);
        		pr.setMessage("管理用户密码重置失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
    
    /**
     * 修改管理用户
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult updateSysUserInfo(HttpServletRequest request)throws Exception {
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
            //校验user_realname参数
            if(!params.containsKey("user_realname")||StringUtils.isEmpty(params.get("user_realname"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_realname");
                return pr;
            }
            //校验phone参数
            if(!params.containsKey("phone")||StringUtils.isEmpty(params.get("phone"))) {
                pr.setState(false);
                pr.setMessage("缺少参数phone");
                return pr;
            }
            
            //校验state参数
            if(!params.containsKey("state")||StringUtils.isEmpty(params.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数state");
                return pr;
            }
            //校验state参数
            if(!params.containsKey("user_type")||StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_type");
                return pr;
            }
            
            SysUserInfo dbUserInfo = sysUserInfoDao.queryById(params);
            if(dbUserInfo==null){
            	pr.setState(false);
        		pr.setMessage("用户信息不存在，请刷新页面后重试");
        		return pr;
            }
            //用户类型做了变更，需要校验用户是否被会员关联
            if(dbUserInfo.getUser_type()!=Integer.parseInt(params.get("user_type").toString())){
            	//非管理员   销售管理  业务经理
            	if(dbUserInfo.getUser_type()!=2 || dbUserInfo.getUser_type()!=9 ){
            		Map<String,Object> map = new HashMap<String,Object>();
            		map.put("id", dbUserInfo.getId());
            		map.put("user_type", dbUserInfo.getUser_type());
            		String user_type_str = "";
            		if(dbUserInfo.getUser_type()==3){user_type_str="业务员";}
            		if(dbUserInfo.getUser_type()==4){user_type_str="业务经理";}
            		if(dbUserInfo.getUser_type()==5){user_type_str="店长";}
            		if(dbUserInfo.getUser_type()==6){user_type_str="营业员";}
            		String returnMessage="";
            		if(dbUserInfo.getUser_type()==4 || dbUserInfo.getUser_type()==5){
		            	//查询用户关联的门店信息（业务经理、店长）
	            		List<Map<String,Object>> list1 = sysUserInfoDao.queryMd1ForUserId(map);
	            		if(list1.size()>0){
	            			returnMessage+="变更用户类型失败，由于此用户<br/>以【"+user_type_str+"】身份关联了["+list1.size()+"]个门店，<br/>";
	            			if(list1.size()>5){returnMessage+="，以下为前5个门店信息：<br/>";}
	            			else{returnMessage+="，相关门店信息如下：<br/>";}
	            			for(int i=0;i<list1.size()&&i<5;i++){
	            				returnMessage+=list1.get(i).get("STORE_NAME").toString()+"<br/>";
	            			}
	            			pr.setState(false);
	            			pr.setMessage(returnMessage);
	            			return pr;
	            		}
	            	}
            		if(dbUserInfo.getUser_type()==3 || dbUserInfo.getUser_type()==6){
	            		//查询用户关联的门店信息（营业员、业务员）
	            		List<Map<String,Object>> list2 = sysUserInfoDao.queryMd2ForUserId(map);
	            		if(list2.size()>0){
	            			returnMessage+="变更用户类型失败，由于此用户<br/>以【"+user_type_str+"】身份关联了["+list2.size()+"]个门店，<br/>";
	            			if(list2.size()>5){returnMessage+="，以下为前5个门店信息：<br/>";}
	            			else{returnMessage+="，相关门店信息如下：<br/>";}
	            			for(int i=0;i<list2.size()&&i<5;i++){
	            				returnMessage+=list2.get(i).get("STORE_NAME").toString()+"<br/>";
	            			}
	            			pr.setState(false);
	            			pr.setMessage(returnMessage);
	            			return pr;
	            		}
            		}
            		if(dbUserInfo.getUser_type()==3 || dbUserInfo.getUser_type()==4){
	            		//查询用户关联的会员信息(待审核)
	            		List<Map<String,Object>> list3 = sysUserInfoDao.queryMember1ForUserId(map);
	            		if(list3.size()>0){
	            			returnMessage+="变更用户类型失败，由于此用户<br/>以【"+user_type_str+"】身份关联了["+list3.size()+"]个【待审】会员，<br/>";
	            			if(list3.size()>5){returnMessage+="，以下为前5个会员信息：<br/>";}
	            			else{returnMessage+="，相关会员信息如下：<br/>";}
	            			for(int i=0;i<list3.size()&&i<5;i++){
	            				returnMessage+=list3.get(i).get("USER_NAME").toString()+"<br/>";
	            			}
	            			pr.setState(false);
	            			pr.setMessage(returnMessage);
	            			return pr;
	            		}
	            		//查询用户关联的会员信息(已审核)
	            		List<Map<String,Object>> list4 = sysUserInfoDao.queryMember2ForUserId(map);
	            		if(list4.size()>0){
	            			returnMessage+="变更用户类型失败，由于此用户<br/>以【"+user_type_str+"】身份关联了["+list4.size()+"]个【已审】会员，<br/>";
	            			if(list4.size()>5){returnMessage+="，以下为前5个会员信息：<br/>";}
	            			else{returnMessage+="，相关会员信息如下：<br/>";}
	            			for(int i=0;i<list4.size()&&i<5;i++){
	            				returnMessage+=list4.get(i).get("USER_NAME").toString()+"<br/>";
	            			}
	            			pr.setState(false);
	            			pr.setMessage(returnMessage);
	            			return pr;
	            		}
            		}
            	}
            }
            
            //销售管理类型用户
            if(params.get("user_type").equals("9")||params.get("user_type").equals("4")){
            	if(!params.containsKey("organization_id")||StringUtils.isEmpty(params.get("organization_id"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数organization_id");
                    return pr;
            	}
            	params.put("site_id", null);
            	params.put("province", null);
            	params.put("city", null);
            	params.put("area", null);
            	params.put("user_company_address_max", null);
            	/*
            }
            else if(!params.get("user_type").equals("2")){
            	if(!params.containsKey("site_id")||StringUtils.isEmpty(params.get("site_id"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数site_id");
                    return pr;
            	}
            	if(!params.containsKey("province")||StringUtils.isEmpty(params.get("province"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数province");
                    return pr;
            	}
            	if(!params.containsKey("city")||StringUtils.isEmpty(params.get("city"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数city");
                    return pr;
            	}
            	if(!params.containsKey("area")||StringUtils.isEmpty(params.get("area"))) {
            		pr.setState(false);
                    pr.setMessage("缺少参数area");
                    return pr;
            	}
            	
            	//根据省份ID获取大区ID
	            long user_company_address_max = sysUserInfoDao.queryBigAreaID(Integer.parseInt(params.get("province").toString()));
	            if(user_company_address_max==0){
	            	pr.setState(false);
	                pr.setMessage("非法的省份");
	                return pr;
	            }
	            params.put("user_company_address_max",user_company_address_max);
	            params.put("organization_id", null);
             */
            }else{
            	params.put("site_id", null);
            	params.put("province", null);
            	params.put("city", null);
            	params.put("area", null);
            	params.put("user_company_address_max", null);
            	params.put("organization_id", null);
            }
            if(sysUserInfoDao.update(params) > 0) {
            	//记录编辑系统用户日志
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 1);
        		logMap.put("OPERATE_ID", 2);
        		logMap.put("REMARK", "编辑系统用户");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", dbUserInfo.getUser_name());
        		logMap.put("USER_REALNAME", dbUserInfo.getUser_realname());
        		sysUserInfoDao.insertUserOperationLog(logMap);
            	//销售管理修改调用远程接口修改合作商名称
            	if("9".equals(params.get("user_type"))){
            		Map<String,Object> sendParam=new HashMap<String,Object>();
            		sendParam.put("COOPER_ID", params.get("id"));
            		sendParam.put("COOPER_NAME", params.get("user_realname"));
            		Map<String, Object> resPr=(Map<String, Object>)this.queryForPost(sendParam,store_service_url+"/agent/Lyinit/esbPartnerUpdate");
                    if (Integer.parseInt(resPr.get("state").toString()) != 1) {
                   	 throw new RuntimeException("调用远程接口异常");
                    }
            	}
            	
            } else {
                pr.setState(false);
                pr.setMessage("修改管理用户信息失败");
            }
            pr.setState(true);
            pr.setMessage("修改管理用户信息成功");
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }

        return pr;
    }
    public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
       String params = "";
       if(obj != null){
           Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
           params = Jackson.writeObject2Json(packet);//对象转json、字符串
       }
       //发送至服务端
       String json = HttpClientUtil.post(url, params,30000);
       return Transform.GetPacketJzb(json,Map.class);
   }
    /**
     * 查询管理用户列表
     * @param request
     * @return
     */
    public ProcessResult querySysUserInfoList(HttpServletRequest request) {
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
           
			List<String> state_list = null;
			if(paramMap.get("state") instanceof List<?>){
				state_list = (List<String>) paramMap.get("state");
			}else if(paramMap.get("state") instanceof String){
				state_list = new ArrayList<String>();
				state_list.add(paramMap.get("state").toString());
			}
			paramMap.put("state", state_list);
			
            List<SysUserInfo> list = sysUserInfoDao.queryList(paramMap);
            int total = sysUserInfoDao.queryCount(paramMap);
    		pr.setState(true);
    		pr.setMessage("获取管理用户列表成功");
    		pr.setObj(list);
    		pr.setTotal(total);
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
    
    /**
     * 查询管理用户详情
     * @param request
     * @return
     */
    public ProcessResult querySysUserInfoDetail(HttpServletRequest request) {
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
            SysUserInfo userinfo = sysUserInfoDao.queryById(params);
            pr.setState(true);
            pr.setMessage("获取管理用户详情成功");
            pr.setObj(userinfo);
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 查询用户可用站点列表
     * @param request
     * @return
     */
    public ProcessResult queryUserSiteList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            SysUserInfo userinfo = (SysUserInfo) Transform.GetPacket(json, SysUserInfo.class);
            //校验id参数
            if(StringUtils.isEmpty(userinfo.getId())) {
            	pr.setState(false);
            	pr.setMessage("缺少参数id");
            	return pr;
            }
            List<Map<String,Object>> list = sysUserSiteDao.queryList(userinfo);
            //将分享站点移动至队列尾端
            if(list!=null && !list.isEmpty()){
            	//找到id小于0的元素，将其放到队列尾部
            	int index = -1;
            	for(int i=0,l=list.size();i<l;i++){
            		Map<String,Object> siteMap = list.get(i);
            		long id = StringUtils.isEmpty(siteMap.get("id"))?0:Long.parseLong(siteMap.get("id").toString());
            		if(id < 0){
            			index = i;
            			break;
            		}
            	}
            	if(index != -1){
            		Map<String,Object> nodeMap = list.get(index);//取出该对象
            		list.remove(index);//删除该对象
            		list.add(nodeMap);//将该对象放置到队列尾部
            	}
            }
            pr.setState(true);
            pr.setObj(list);
            pr.setMessage("获取用户可用站点权限成功");
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
	
	/**
	 * 获取业务员或者业务经理
	 * @param request
	 * @return
	 */
	public ProcessResult typeList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_type"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}
			List<Map<String,Object>> typeList = sysUserInfoDao.querytypeList(params);

			pr.setState(true);
			pr.setMessage("获取业务员或者业务经理成功");
			pr.setObj(typeList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
    
    /**
     * 业务员或门店店长或门店营业员授权登录
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult accreditLogin(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(params.get("public_user_id"))||StringUtils.isEmpty(params.get("user_name"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数");
            	return pr;
            }
            params.put("id", params.get("public_user_id"));
            //获取当前登录用户信息
            SysUserInfo sysUser =  sysUserInfoDao.queryById(params);
            //只有业务员、业务经理、门店店长或门店营业员可快捷登录
            if(sysUser==null||sysUser.getUser_type() < 3 || sysUser.getUser_type() > 6){
                pr.setState(false);
                pr.setMessage("当前用户无法使用授权登录");
                return pr;
            }
            //获取用户门店信息
            List<Map<String, Object>> storeInfoList = sysUserStoreDao.queryUserStoreInfo(sysUser);
            if(storeInfoList==null||storeInfoList.size()<1){
                pr.setState(false);
                pr.setMessage("未关联门店，无法授权登录");
                return pr; 
            }
            params.put("id", params.get("user_name").toString());
          	Map<String,Object> memberInfo=memberInfoDao.queryMemberInfoById(params);
          	if(memberInfo==null){
          		pr.setState(false);
                pr.setMessage("获取会员信息失败");
                return pr;
          	}else{
          		if(memberInfo.containsKey("user_state") && "2".equals(memberInfo.get("USER_STATE"))){
                    pr.setState(false);
                    pr.setMessage("当前会员已禁用，不能登录");
                    return pr;
                }
          	}
          	boolean storeInfoFlag=false;
          	int member_store_id=Integer.parseInt(memberInfo.get("STORE_ID")+"");
          	for(Map<String,Object> storeInfo:storeInfoList){
          		if(member_store_id==Integer.parseInt(storeInfo.get("ID")+"")){
          			storeInfoFlag=true;
          		}
          	}
          	if(!storeInfoFlag){
          		pr.setState(false);
                pr.setMessage("当前用户与会员关系存疑，不能登录");
                return pr;
          	}
          	//每次预审会员登入后数量加1
          	memberInfo=memberInfoDao.queryMemberInfoById(params);
          	memberInfo.put("pre_aprv_login_number", Integer.parseInt(memberInfo.get("PRE_APRV_LOGIN_NUMBER").toString())+1);
          	memberInfoDao.updateMemberInfoById(memberInfo);
          	// 返回加密数据
            Map<String, Object> loginMap = new HashMap<String, Object>();
            loginMap.put("user_name", params.get("user_name"));
            loginMap.put("business_user_name", sysUser.getUser_name());
            loginMap.put("business_user_type", sysUser.getUser_type());

            pr.setState(true);
            pr.setObj(loginMap);
            pr.setMessage("获取用户可用站点权限成功");
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
	
	/**
	 * 【通用下拉框】查询【业务经理】下拉
	 * @param request
	 * @return
	 */
	public ProcessResult querySysUserYwjlList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if((!StringUtils.isEmpty(params.get("ids")))&&params.get("ids") instanceof String){
				params.put("ids",(params.get("ids")+"").split(","));
			}
			List<Map<String,Object>> typeList = sysUserInfoDao.querySysUserYwjlList(params);

			pr.setState(true);
			pr.setMessage("获取权限内业务经理成功");
			pr.setObj(typeList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 【通用下拉框】查询【门店】下拉
	 * @param request
	 * @return
	 */
	public ProcessResult querySysStoreList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("page_select_ywjl_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少业务经理参数【page_select_ywjl_id】");
				return pr;
			}
			
			List<Map<String,Object>> typeList = sysUserInfoDao.querySysStoreList(params);

			pr.setState(true);
			pr.setMessage("获取权限内门店列表成功");
			pr.setObj(typeList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 【通用下拉框】查询【业务人员】下拉
	 * @param request
	 * @return
	 */
	public ProcessResult querySysUserYwyList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("page_select_md_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少门店ID参数【page_select_md_id】");
				return pr;
			}
			//List<Map<String,Object>> typeList = sysUserInfoDao.querySysUserYwyList(params);
			List<Map<String,Object>> typeList = sysUserInfoDao.querySysUserYwryList(params);
			pr.setState(true);
			pr.setMessage("获取权限内业务人员列表成功");
			pr.setObj(typeList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 获取当前业务经理 、当前门店下业务员、业务经理、店长、营业员数据
	 * @param request
	 * @return
	 */
	public ProcessResult querySysUserList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("ywjl_user_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数【ywjl_user_id】");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("store_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数【store_id】");
				return pr;
			}
			List<Map<String,Object>> userList = sysUserInfoDao.querySysUserList(params);

			pr.setState(true);
			pr.setMessage("获取列表数据成功");
			pr.setObj(userList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}

	/**
	 * 查询用户默认登录验证码
	 * @param request
	 * @return
	 */
	public ProcessResult queryLoginVerifyCode(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            // 使用OA验证码，--update by wanghai 2019-11-25
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("companyCode", oa_company_code);
            paramsMap.put("type", "login");
            pr = HttpClientUtil.postToOAByReverse(oaServerUrl + oaCompanyVcode, paramsMap);

//            String verify_code = sysUserInfoDao.queryLoginVerifyCode();
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	/**
	 * 系统用户ip设置
	 * @param request
	 * @return
	 */
	public ProcessResult userIpSet(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String,Object> params = null;
        	String json = HttpUtil.getRequestInputStream(request);
        	if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            int count = sysUserInfoDao.updateUserIp(params);
            if(count>0){
            	SysUserInfo info=sysUserInfoDao.queryByUserId(Long.parseLong(params.get("id")+""));
            	//记录系统用户ip设置日志
        		Map<String,Object> logMap=new HashMap<String,Object>();
        		logMap.put("USER_TYPE", 1);
        		logMap.put("OPERATE_ID", 3);
        		logMap.put("REMARK", "配置【IP权限】");
        		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
        		logMap.put("USER_NAME", info.getUser_name());
        		logMap.put("USER_REALNAME", info.getUser_realname());
        		sysUserInfoDao.insertUserOperationLog(logMap);
            	pr.setState(true);
                pr.setMessage("更新成功");
            }else{
            	pr.setState(false);
                pr.setMessage("更新失败");
            }
            
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
	}
	
	
	
	/**
	 * ip白名单列表
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult whiteIpList(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	    if(StringUtils.isEmpty(paramMap.get("user_name"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
    		}
    	    String ips=sysUserInfoDao.queryWhiteIpList(paramMap.get("user_name")+"");
    	    pr.setState(true);
            pr.setMessage("添加成功！");
            pr.setObj(ips);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}

    /**
     * 获取用户OA系统登录OpenId
     * @param userId
     * @return
     */
	public String getSysUserOAOpenIdById(Long userId){
        return sysUserInfoDao.getSysUserOAOpenIdById(userId);
    }

    /**
     * 解锁
     * @param request
     * @return
     * @throws Exception
     */
    public ProcessResult unLock(HttpServletRequest request) throws Exception {
	    ProcessResult pr = new ProcessResult();
        String json = HttpUtil.getRequestInputStream(request);
        if(StringUtils.isEmpty(json)) {
            pr.setMessage("参数错误!");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
        if(StringUtils.isEmpty(params.get("userPwd"))){
            pr.setState(false);
            pr.setMessage("缺少参数,密码不能为空");
        }
        Long userId = Long.parseLong(params.get("public_user_id").toString());
        params.put("loginType", "OA_AUTH");
        params.put("openid", sysUserInfoDao.getSysUserOAOpenIdById(userId));
        params.put("clientId", oauth_client_id);
        params.put("company", oa_company_code);
        params.put("systemUserId", userId);
        params.put("userIp", params.get("ip"));
        pr = (ProcessResult)HttpClientUtil.postToOA(sso_service_url + "oauth2/auth_login", params);
        if(pr.getState()){
            pr.setState(true);
            pr.setObj(pr.getObj());
            pr.setMessage("登录成功");
        }
        return pr;
    }

	
	/**
	 * 查询指定门店下的【业务员、业务经理、店长、营业员】
	 * @param request
	 * @return
	 */
	public ProcessResult querySalersList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("store_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数【store_id】");
				return pr;
			}
			List<Map<String,Object>> userList = sysUserInfoDao.querySalersList(params);

			pr.setState(true);
			pr.setMessage("获取列表数据成功");
			pr.setObj(userList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 按用户类型查询指定用户列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryUserList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			List<Map<String,Object>> userList = sysUserInfoDao.queryUserList(params);

			pr.setState(true);
			pr.setMessage("获取列表数据成功");
			pr.setObj(userList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
    /**
     * 创建自定义用户帐户
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult customerCreateUserAccount(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        try {
            Map<String,Object> params = null;
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            }
            //  创建帐户
            if(sysUserInfoDao.insertCustomUserAccount(params) > 0){
                //  关联帐户
                UserAuthor userAuthor = new UserAuthor();
                userAuthor.setUser_id(Long.parseLong(params.get("id").toString()));
                userAuthor.setOa_open_id(params.get("openId").toString());
                if(userAuthorDao.insertForOA(userAuthor) >0){
                    pr.setState(true);
                    pr.setObj(userAuthor.getUser_id());
                    pr.setMessage("创建自定义系统账户成功");
                }else{
                    pr.setState(false);
                    pr.setMessage("创建自定义系统账户失败");
                }
            }else{
                pr.setState(false);
                pr.setMessage("创建自定义系统账户失败");
            }

        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    
    /**
	 * 查询推介人列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryGroundPushList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			List<Map<String,Object>> userList = sysUserInfoDao.queryGroundPushList(params);

			pr.setState(true);
			pr.setMessage("获取列表数据成功");
			pr.setObj(userList);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 设置会员账号预审是否全部查看
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult updateAccountApprovalState(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
    	    if(StringUtils.isEmpty(paramMap.get("user_id"))){
				pr.setMessage("缺少参数user_id");
				pr.setState(false);
				return pr;
    		}
    	    if(StringUtils.isEmpty(paramMap.get("account_approval_state"))){
				pr.setMessage("缺少参数account_approval_state");
				pr.setState(false);
				return pr;
    		}
    	    int count=sysUserInfoDao.updateAccountApprovalState(paramMap);
    	    if(count<=0){
    	    	pr.setState(false);
    	    	throw new RuntimeException("设置失败");
    	    }
    	    SysUserInfo info=sysUserInfoDao.queryByUserId(Long.parseLong(paramMap.get("user_id")+""));
    	    //记录账号预审权限配置日志
        	Map<String,Object> logMap=new HashMap<String,Object>();
    		logMap.put("USER_TYPE", 1);
    		logMap.put("OPERATE_ID", 3);
    		logMap.put("REMARK", "配置【账号预审权限】");
    		logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
    		logMap.put("USER_NAME", info.getUser_name());
    		logMap.put("USER_REALNAME", info.getUser_realname());
    		sysUserInfoDao.insertUserOperationLog(logMap);
    	    pr.setState(true);
            pr.setMessage("设置成功！");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	/**
	 * 业务员下拉框(单独)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySysUserYwyOption(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("page_select_ywjl_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少业务经理参数【page_select_ywjl_id】");
				return pr;
			}
			List<Map<String, Object>> list = sysUserInfoDao.querySysUserYwyOption(params);
			pr.setState(true);
			pr.setMessage("获取业务员下拉框列表成功");
			pr.setObj(list);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}

	/**
	 * 获取用户类型(提供给OA调用)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult getSysUserType(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数【user_id】");
				return pr;
			}
			
			Map<String, Object> retMap = sysUserInfoDao.getSysUserType(params);
			pr.setState(true);
			pr.setMessage("获取用户类型成功");
			pr.setObj(retMap);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
}
