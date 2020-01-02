package com.tk.store.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.store.member.dao.PublicNumberManageDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : PublicNumberManageService
 *
 * @author: liujialong
 * @version: 1.00
 * @date: 2018/9/7
 */
@Service("PublicNumberManageService")
public class PublicNumberManageService {
	
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private PublicNumberManageDao publicNumberManagedao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
//	@Value("${public_number_bind_url}")
//    private  String public_number_bind_url;
	
	/**
	 * 查询公众号管理列表页
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryPublicNumberManageList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = publicNumberManagedao.queryPublicNumberManageList(paramMap);
			int count = publicNumberManagedao.queryPublicNumberManageCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取公众号二维码
	 * @param request
	 * @return
     */
	public ProcessResult publicNumberPreview(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> sendMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数 ");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (StringUtils.isEmpty(paramMap.get("user_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数【user_id】");
				return pr;
			}
			//获取用户信息
			SysUserInfo sysUserInfo = sysUserInfoDao.queryByUserId(Long.parseLong(paramMap.get("user_id")+""));
			if (sysUserInfo == null) {
				pr.setState(false);
				pr.setMessage("用户不存在");
				return pr;
			}
			sendMap.put("COOPER_ID", paramMap.get("user_id"));
			//Map<String, Object> retMap= (Map<String, Object>) queryForPost(sendMap,public_number_bind_url);
			Map<String, Object> objMap = new HashMap<String, Object>();
			objMap.put("user_id",paramMap.get("user_id"));
			pr.setMessage("查询成功");
			pr.setState(true);
			pr.setObj(objMap);
		} catch (Exception ex) {
			pr.setState(false);
			pr.setMessage(ex.getMessage());
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
	 * 查询公众号管理详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPublicNumberManageDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[user_id]");
                return pr;
            }
			Map<String,Object> detail = publicNumberManagedao.queryPublicNumberManageDetail(paramMap);
			if(detail!=null){
				pr.setMessage("获取详情成功");
				pr.setObj(detail);
			}else{
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
