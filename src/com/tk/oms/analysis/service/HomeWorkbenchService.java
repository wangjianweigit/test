package com.tk.oms.analysis.service;

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

import com.tk.oms.analysis.dao.ChannelHeatDao;
import com.tk.oms.analysis.dao.HomeWorkbenchDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("HomeWorkbenchService")
public class HomeWorkbenchService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
    private HomeWorkbenchDao homeWorkbenchDao;
	
	@Value("${jdbc_user}")
	private String jdbc_user;
	
	/**
	 * 首页工作台查询订单信息
	 * @param request
	 * @return
	 */
	public GridResult queryHomeWorkOrderInfo(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			Map<String,Object> order_info = homeWorkbenchDao.queryHomeWorkOrderInfo(params);
			pr.setState(true);
			pr.setMessage("获取首页工作台查询订单信息成功");
			pr.setObj(order_info);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 首页工作台会员成交信息
	 * @param request
	 * @return
	 */
	public GridResult queryHomeWorkMemberTransactions(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			List<Map<String,Object>> member_transactions_list = homeWorkbenchDao.queryHomeWorkMemberTransactions(params);
			pr.setState(true);
			pr.setMessage("获取首页工作台会员成交信息成功");
			pr.setObj(member_transactions_list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 首页工作台异常会员信息
	 * @param request
	 * @return
	 */
	public GridResult queryHomeWorkUnusualMember(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			params.put("jdbc_user", jdbc_user);
			List<Map<String,Object>> unusual_member_list = homeWorkbenchDao.queryHomeWorkUnusualMember(params);
			pr.setState(true);
			pr.setMessage("获取首页工作台异常会员信息成功");
			pr.setObj(unusual_member_list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 首页工作台会员门店地址信息
	 * @param request
	 * @return
	 */
	public GridResult queryHomeWorkMemberStoreAddress(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			List<Map<String,Object>> member_store_address_list = homeWorkbenchDao.queryHomeWorkMemberStoreAddress(params);
			pr.setState(true);
			pr.setMessage("获取首页工做台会员门店地址信息成功");
			pr.setObj(member_store_address_list);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 首页工作台查询区域销售
	 * @param request
	 * @return
	 */
	public GridResult queryHomeWorkMemberRegionSale(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("address_type"))){
                pr.setState(false);
                pr.setMessage("缺少参数address_type");
                return pr;
	        }
			if(StringUtils.isEmpty(params.get("address_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数address_id");
                return pr;
	        }
			Map<String,Object> member_region_sale = homeWorkbenchDao.queryHomeWorkMemberRegionSale(params);
			pr.setState(true);
			pr.setMessage("获取首页工作台区域销售信息成功");
			pr.setObj(member_region_sale);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 首页工作台会员活跃度
	 * @param request
	 * @return
	 */
	public GridResult queryHomeWorkMemberActivity(HttpServletRequest request) {
		GridResult pr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			List<Map<String,Object>> member_activity = homeWorkbenchDao.queryHomeWorkMemberActivity(params);
			pr.setState(true);
			pr.setMessage("获取首页工作台会员成交信息成功");
			pr.setObj(member_activity);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 首页工作台查询会员数
	 * @param request
	 * @return
	 */
	public ProcessResult queryHomeWorkMemberCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			 if (StringUtils.isEmpty(json)) {
				 pr.setState(false);
				 pr.setMessage("缺少参数");
                 return pr;
            }
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			Map<String, Object> retMap = new HashMap<String, Object>();
			//全部会员
			retMap.put("member_count", homeWorkbenchDao.queryHomeWorkMemberCount(params));
			params.put("type", 0);
			//未定位的会员
			retMap.put("not_location_member_count", homeWorkbenchDao.queryHomeWorkMemberCount(params));
			pr.setState(true);
			pr.setMessage("获取首页工作台会员数成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}

}
