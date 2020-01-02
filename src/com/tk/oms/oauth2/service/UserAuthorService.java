package com.tk.oms.oauth2.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.oauth2.dao.UserAuthorDao;
import com.tk.oms.oauth2.entity.UserAuthor;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;

@Service("UserAuthorService")
public class UserAuthorService {
    private Log logger = LogFactory.getLog(this.getClass());

	@Resource 
	private UserAuthorDao userAuthorDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	/** OA数据业务接口 */
	@Value ("${oa_service_url}")
	private String oa_service_url;

	/** 客户端系统Id */
	@Value("${oauth.oauth_client_id}")
	private String oauth_client_id;

	@Value("${oa.emp_device_binding}")
	private String empDeviceBinding;
	/**
	 * 根据OA系统用户的openId查询，ERP中的用户是否已经与OA用户关联
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult getByOAOpenId(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
		Map<String, Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
			params = (Map<String, Object>) Transform.GetPacket(json,Map.class);
            if (StringUtils.isEmpty(params.get("open_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
            if(userAuthorDao.getByOAOpenId(params.get("open_id").toString())==0){
            	pr.setState(false);
            	pr.setMessage("帐号关联关系成不存在");
            }else{
            	pr.setState(true);
            	pr.setMessage("获取帐号关联关系成功");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
	/**
	 * ERP使用自身的帐户，密码登录，同时对ERP用户与OA用户进行绑定操作
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult accountBind(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json)) {
				params = (Map<String, Object>) Transform.GetPacket(json,Map.class);
				// 判断用户名，密码非空
				if (!params.containsKey("user_name")
						|| StringUtils.isEmpty(params.get("user_name"))
						|| !params.containsKey("user_pwd")
						|| StringUtils.isEmpty(params.get("user_pwd"))
						|| StringUtils.isEmpty(params.get("open_id"))
						) {
					pr.setState(false);
					pr.setMessage("缺少参数");
				} else {// 判断用户是否可用
					SysUserInfo userInfo = sysUserInfoDao.querySysUserInfoByUserName(String.valueOf(params.get("user_name")));
					if (userInfo == null) {
						pr.setState(false);
						pr.setMessage("用户名不存在");
						pr.setObj(-1);
					} else {
						if (new String(Crypt.decrypt(Base64.decode(userInfo
								.getUser_pwd()), FileUtils.getSecretKey(
								EsbConfig.SECRET_KEY_PATH,
								EsbConfig.USER_PWD_KEY)),
								EsbConfig.INPUT_CHARSET).equals(String
								.valueOf(params.get("user_pwd")))) {
							if (userInfo.getState() == 1) {
								pr.setState(false);
								pr.setMessage("该用户已被禁用，无法登陆");
								pr.setObj(-1);
							} else if (userInfo.getState() == 2) {
								userInfo.setUser_pwd(null);
								pr.setState(true);
								pr.setMessage("登陆成功");
								pr.setObj(userInfo);
								/***
								 * 登录成功，校验openId有效性，进行ERP用户与OA用户的open_id的绑定操作
								 */
								if(userAuthorDao.countByOAOpenId(params.get("open_id").toString(), userInfo.getId())<=0){
									UserAuthor userAuthor = new UserAuthor();
									userAuthor.setUser_id(userInfo.getId());
									userAuthor.setOa_open_id(params.get("open_id").toString());
									if (userAuthorDao.insertForOA(userAuthor) > 0) {
										pr.setState(true);
										pr.setMessage("帐号关联成功");
									} else {
										pr.setState(false);
										pr.setMessage("帐号关联失败");
									}
								}
								Map<String, Object> paramMap = new HashMap<String, Object>();
								paramMap.put("clientId", oauth_client_id);
								paramMap.put("openId", params.get("open_id").toString());
								paramMap.put("systemUserId", userInfo.getId());
								pr = HttpClientUtil.postToOAByReverse(oa_service_url + "oauth2/user/bind", paramMap);
								if(pr.getState()){
									pr.setState(true);
									pr.setObj(userInfo);
									pr.setMessage("帐号关联成功");
								}else{
									pr.setState(false);
									pr.setMessage("帐号关联失败");
								}
							} else {
								pr.setState(false);
								pr.setMessage("该用户状态异常，无法登陆");
								pr.setObj(-1);
							}
						} else {
							pr.setState(false);
							pr.setMessage("用户名或密码错误");
							pr.setObj(-1);
						}

					}
				}
			} else {
				pr.setState(false);
				pr.setMessage("缺少参数,用户名密码不能为空");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
			logger.error("登录出错" + e);
		}
		return pr;
	}
	/**
	 * 使用OA的openId直接获取用户信息，用于登录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult loginByOpenId(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json)) {
				params = (Map<String, Object>) Transform.GetPacket(json,Map.class);
				// 判断用户名，密码非空
				if (StringUtils.isEmpty(params.get("open_id"))) {
					pr.setState(false);
					pr.setMessage("缺少参数");
				} else {// 判断用户是否可用
					/***
					 * 根据openId获取用户信息
					 */
					SysUserInfo userInfo = userAuthorDao.querySysUserInfoByOAOpenId(String.valueOf(params.get("open_id")));
					if (userInfo == null) {
						pr.setState(false);
						pr.setMessage("用户绑定异常");
						pr.setObj(-1);
					} else {
						if (userInfo.getState() == 1) {
							pr.setState(false);
							pr.setMessage("该用户已被禁用，无法登陆");
							pr.setObj(null);
						} else if (userInfo.getState() == 2) {

							if(!StringUtils.isEmpty(params.get("deviceCode"))) {
								// 请求OA发送设备授信绑定
								Map<String, Object> deviceBindMap = new HashMap<String, Object>(5);
								deviceBindMap.put("deviceCode", params.get("deviceCode"));
								deviceBindMap.put("sysOpenid", params.get("open_id"));
								deviceBindMap.put("deviceType", "login");

								HttpClientUtil.postToOAByReverse(oa_service_url + empDeviceBinding, deviceBindMap);
							}

							userInfo.setUser_pwd(null);
							pr.setState(true);
							pr.setMessage("登陆成功");
							pr.setObj(userInfo);
						} else {
							pr.setState(false);
							pr.setMessage("该用户状态异常，无法登陆");
							pr.setObj(null);
						}
					
					}
				}
			} else {
				pr.setState(false);
				pr.setMessage("缺少参数,用户名密码不能为空");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
			logger.error("登录出错" + e);
		}
		return pr;
	}
}
