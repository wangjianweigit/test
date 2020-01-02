package com.tk.oms.sysuser.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.sysuser.dao.PrivateInfoLogsDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;

@Service("PrivateInfoLogsService")
public class PrivateInfoLogsService {
	
	@Resource
	private PrivateInfoLogsDao privateInfoLogsDao;
	@Resource
    private SysUserInfoDao sysUserInfoDao;
	@Resource
    private IpdbService ipdbService;

	/** sso数据业务接口 */
	@Value ("${oauth.sso_service_url}")
	private String sso_service_url;

	/** 客户端系统Id */
	@Value("${oauth.oauth_client_id}")
	private String oauth_client_id;

	/** OA公司代码 */
	@Value ("${oauth.oa_company_code}")
	private String oa_company_code;
	
	/**
	 * 私密日志新增
	 */
	public ProcessResult add(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
        try {
        	String json = HttpUtil.getRequestInputStream(request);
             //校验是否传入参数
             if(StringUtils.isEmpty(json)) {
                 pr.setState(false);
                 pr.setMessage("缺少参数");
                 return pr;
             }
             Map<String, Object>  paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
             if(StringUtils.isEmpty(paramMap.get("module"))) {
                 pr.setState(false);
                 pr.setMessage("缺少功能模块");
                 return pr;
             }
             if(StringUtils.isEmpty(paramMap.get("remark"))) {
                 pr.setState(false);
                 pr.setMessage("缺少详细操作记录");
                 return pr;
             }
             if(!StringUtils.isEmpty(paramMap.get("user_create_ip"))){
                 String[] address_arr = ipdbService.find(paramMap.get("user_create_ip").toString());
                 if(address_arr.length > 2 && !StringUtils.isEmpty(address_arr[2])){
                	 paramMap.put("ip_attribution", address_arr[2]);
                 }else if(address_arr.length > 1 && !StringUtils.isEmpty(address_arr[1])){
                	 paramMap.put("ip_attribution", address_arr[1]);
                 }else if(address_arr.length > 0 && !StringUtils.isEmpty(address_arr[0])) {
                	 paramMap.put("ip_attribution", address_arr[0]);
                 }
             }

             if(privateInfoLogsDao.insert(paramMap)>0){
            	 pr.setState(true);
     			 pr.setMessage("私密日志记录成功");
             }else{
            	 pr.setState(false);
     			 pr.setMessage("私密日志记录失败");
             }
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	
	/**
	 * 私密日志列表
	 * @param request
	 * @return
	 */
	public GridResult queryList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			List<Map<String,Object>> logsList = null;
			int userCount=privateInfoLogsDao.queryPrivateInfoLogsCount(params);
			logsList = privateInfoLogsDao.queryPrivateInfoLogsList(params);
			if (logsList != null && logsList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(logsList);
				gr.setTotal(userCount);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 私密日志查询判断密码是否正确
	 */
	public ProcessResult judgePwd(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
        try {
        	String json = HttpUtil.getRequestInputStream(request);
             //校验是否传入参数
             if(StringUtils.isEmpty(json)) {
                 pr.setState(false);
                 pr.setMessage("缺少参数");
                 return pr;
             }
             Map<String, Object>  params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
             if(StringUtils.isEmpty(params.get("user_pwd"))){
                 pr.setState(false);
                 pr.setMessage("请输入密码");
                 return pr;
             }

			Long userId = Long.parseLong(params.get("public_user_id").toString());
            Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("loginType", "OA_AUTH");
			paramMap.put("openid", sysUserInfoDao.getSysUserOAOpenIdById(userId));
			paramMap.put("clientId", oauth_client_id);
			paramMap.put("company", oa_company_code);
			paramMap.put("systemUserId", userId);
			paramMap.put("userPwd", params.get("user_pwd").toString());
			pr = (ProcessResult)HttpClientUtil.postToOA(sso_service_url + "oauth2/auth_login", paramMap);
			if(pr.getState()){
				pr.setState(true);
				pr.setMessage("密码正确");
			}else{
				pr.setState(false);
				pr.setMessage("密码错误");
				pr.setObj(null);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
