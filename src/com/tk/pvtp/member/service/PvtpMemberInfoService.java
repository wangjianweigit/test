package com.tk.pvtp.member.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.dao.SysUserStoreDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.pvtp.member.dao.PvtpMemberInfoDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("PvtpMemberInfoService")
public class PvtpMemberInfoService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private PvtpMemberInfoDao pvtpMemberInfoDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	@Resource
	private SysUserStoreDao sysUserStoreDao;
	
	
	/**
	 * 获取业务员、业务经理、门店下属会员列表[代客户下单]
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySubsidiaryMemberList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult paramGridResult=PageUtil.handlePageParams(params);
			if(paramGridResult!=null){
				return paramGridResult;
			}
			//业务员、业务经理、门店、营业员对应区分
			if(StringUtils.isEmpty(params.get("public_user_type"))||StringUtils.isEmpty(params.get("public_user_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			int public_user_type =Integer.parseInt(params.get("public_user_type")+"");
			switch(public_user_type){
				//TODO 后续此处管理员权限屏蔽
				case 2:break;
				case 3:
					params.put("referee_user_id", params.get("public_user_id"));
					break; 
				case 5: 
					params.put("store_user_id", params.get("public_user_id"));
					break;
				case 6: 
					params.put("store_user_id", params.get("public_user_id"));
					break;
				default: 
					gr.setState(false);
					gr.setMessage("无数据权限");
					return gr;
			}
			//获取数量
			int userCount=pvtpMemberInfoDao.querySubsidiaryMemberCount(params);
			List<Map<String,Object>> userList = pvtpMemberInfoDao.querySubsidiaryMemberList(params);
			if (userList != null && userList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(userList);
				gr.setTotal(userCount);
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
          	Map<String,Object> memberInfo=pvtpMemberInfoDao.queryMemberInfoById(params);
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
          	//获取二级域名
          	Map<String, Object> sldMap= pvtpMemberInfoDao.getSLD(params);
          	String sld = "";
          	if(StringUtils.isEmpty(sldMap.get("DOMAIN_NAME"))) {
          		if("0".equals(sldMap.get("SHOP_ID").toString())) {
          			pr.setState(false);
                    pr.setMessage("当前用户未开通店铺，不能授权登录");
                    return pr;
          		}else {
          			sld = sldMap.get("SHOP_ID").toString();
          		}
          	}else {
          		sld = sldMap.get("DOMAIN_NAME").toString();
          	}
          	
          	// 返回加密数据
            Map<String, Object> loginMap = new HashMap<String, Object>();
            loginMap.put("user_name", params.get("user_name"));
            loginMap.put("business_user_name", sysUser.getUser_name());
            loginMap.put("business_user_type", sysUser.getUser_type());
            loginMap.put("sld", sld);
            
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
	 * 查询私有站会员列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryPvtpMemberlist(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(params.get("user_type")))&&params.get("user_type") instanceof String){
				params.put("user_type",(params.get("user_type")+"").split(","));
			}
			List<Map<String,Object>> userList = null;
			int userCount=pvtpMemberInfoDao.queryPvtpMemberInfoCount(params);
			userList = pvtpMemberInfoDao.queryPvtpMemberInfoList(params);
			if (userList != null && userList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(userList);
				gr.setTotal(userCount);
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
	 * 查询私有站会员详情
	 * @param request
	 * @return
	 */
	public ProcessResult queryPvtpMemberDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少用户名");
				return pr;
			}
			Map<String,Object> userInfo = pvtpMemberInfoDao.queryPvtpMemberDetail(params);
			if(null==userInfo){
				pr.setObj("");
				pr.setState(true);
				pr.setMessage("获取会员信息成功");
				return pr;
			}
			pr.setObj(userInfo);
			pr.setState(true);
			pr.setMessage("获取会员信息成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	/**
	 * 查询私有站的业务员、业务经理 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationUserList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null
					||StringUtils.isEmpty(params.get("stationed_user_id"))
					||StringUtils.isEmpty(params.get("user_type"))
					){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}
			List<Map<String,Object>> userList = pvtpMemberInfoDao.queryStationUserList(params);
			pr.setObj(userList);
			pr.setState(true);
			pr.setMessage("获取私有站业务员、业务经理数据列表成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	/**
	 * 查询某个私有站业务经理下属的私有站门店列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryStationListByManager(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null
					||StringUtils.isEmpty(params.get("manager_user_id"))
					){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}
			List<Map<String,Object>> storeList = pvtpMemberInfoDao.queryStationListByManager(params);
			pr.setObj(storeList);
			pr.setState(true);
			pr.setMessage("获取私有站门店列表成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 获取私有收支记录详情
	 * @param request
	 * @return
	 */
	public ProcessResult queryPvtpMemberRevenueRecordDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> map = new HashMap<String,Object>();
			map = (Map<String,Object>) Transform.GetPacket(json, Map.class);
			//获取个人收支记录详情
			Map<String,Object> accountRecord = pvtpMemberInfoDao.queryPvtpMemberRevenueRecordDetail(map);
			pr.setState(true);
			pr.setMessage("获取个人收支记录详情成功");
			pr.setObj(accountRecord);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 获取私有收支记录列表
	 * @param request
	 * @return
	 */
	public GridResult queryPvtpMemberRevenueRecordLit(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//Map<String, Object> paramMap = getRequestParams(request);
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}

            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
          //获取个人收支记录
            int count=pvtpMemberInfoDao.queryPvtpMemberRevenueRecordCount(paramMap);
			List<Map<String,Object>> list = pvtpMemberInfoDao.queryPvtpMemberRevenueRecordLit(paramMap);
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("获取数据成功");
				gr.setObj(list);
				gr.setTotal(count);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("获取个人收支记录信息失败,{}",e);
		}
		return gr;
	}
}
