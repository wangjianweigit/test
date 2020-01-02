package com.tk.oms.marketing.service;

import com.tk.oms.marketing.dao.CustomMessageDao;
import com.tk.sys.util.*;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 自定义消息管理
 * @author shifan
 *
 */
@Service("CustomMessageService")
public class CustomMessageService {

	@Resource
	private CustomMessageDao customMessageDao;
	@Resource(name = "pushRabbitTemplate")
	private RabbitTemplate pushRabbitTemplate;
	
	/**
	 * 获取自定义消息列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryCustomMessageList(HttpServletRequest request) {
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
			List<Map<String, Object>> list = customMessageDao.queryCustomMessageList(paramMap);
			int count = customMessageDao.queryCustomMessageCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			e.printStackTrace();
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取自定义消息详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryCustomMessageDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[task_id]");
				return pr;
			}
			//获取促销活动基本信息
			Map<String,Object> detail = customMessageDao.queryCustomMessageDetail(paramMap);
			if(detail!=null){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.putAll(detail);
				//获取自定义推送消息用户配置信息
				List<Map<String,Object>> customMsgUserConfigList = customMessageDao.queryCustomMessageUserConfig(paramMap);
				Map<String,Object> configMap = new HashMap<String,Object>();
				configMap.put("1", "SITE_ID");
				configMap.put("2", "MARKET_SUPERVISION_USER_ID");
				configMap.put("3", "USER_GROUP_ID");
				for(Map<String,Object> tempMap:customMsgUserConfigList){
					resultMap.put(configMap.get(tempMap.get("USER_CONFIG_TYPE")+"")+"", tempMap.get("USER_CONFIG_CONTENT"));
				}
				//获取相关用户配置名称
				Map<String,Object> tempParamMap = new HashMap<String,Object>();
				if(!StringUtils.isEmpty(resultMap.get("SITE_ID"))){
					tempParamMap.put("site_id_arr", (resultMap.get("SITE_ID")+"").split(","));
					resultMap.put("SITE_ID_NAME",customMessageDao.querySiteNames(tempParamMap));
				}
				if(!StringUtils.isEmpty(resultMap.get("MARKET_SUPERVISION_USER_ID"))){
					tempParamMap.put("market_supervision_user_id_arr", (resultMap.get("MARKET_SUPERVISION_USER_ID")+"").split(","));
					resultMap.put("MARKET_SUPERVISION_USER_ID_NAME",customMessageDao.queryMarketUserNames(tempParamMap));
				}
				if(!StringUtils.isEmpty(resultMap.get("USER_GROUP_ID"))){
					tempParamMap.put("user_group_id_arr", (resultMap.get("USER_GROUP_ID")+"").split(","));
					resultMap.put("USER_GROUP_ID_NAME",customMessageDao.queryUserGroupNames(tempParamMap));
				}
				//获取自定义消息推送指定用户列表
				if("3".equals(""+detail.get("MSG_USER_TYPE"))){
					List<Map<String,Object>> customMsgUserList = customMessageDao.queryCustomMessageUserList(paramMap);
					resultMap.put("USER_LIST", customMsgUserList);
				}
				pr.setMessage("获取数据成功");
				pr.setObj(resultMap);
			}else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 自定义消息编辑
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult customMessageEdit(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("operate_type"))){
				pr.setMessage("缺少参数[operate_type]");
				pr.setState(false);
				return pr;
			}
			//商品编辑
			if("1".equals(paramMap.get("operate_type")+"")){
				Map<String,Object> customMessageDetailMap = null;
				if(!StringUtils.isEmpty(paramMap.get("task_id"))){
					customMessageDetailMap = customMessageDao.queryCustomMessageDetail(paramMap);
				}
				//校验当前记录是否可进行编辑
				if(customMessageDetailMap == null){
					int customMessageId = customMessageDao.queryCustomMessageId(null);
					paramMap.put("task_id", customMessageId);
				}else{
					//编辑自定义消息
					if(!"0".equals(customMessageDetailMap.get("TASK_STATE")+"") && !"4".equals(customMessageDetailMap.get("TASK_STATE")+"")){
						pr.setMessage("操作记录状态异常，请检查");
						pr.setState(false);
						return pr;
					}
					//清除用户信息
					customMessageDao.deleteCustomMessage(paramMap);
					customMessageDao.deleteCustomMessageUserConfig(paramMap);
					customMessageDao.deleteCustomMessageUser(paramMap);
				}
				//插入自定义消息
				paramMap.put("task_state", "0");
				int  numMessage=customMessageDao.insertCustomMessage(paramMap);
				//插入用户配置
				if(!"1".equals(paramMap.get("msg_user_type")+"")){
					customMessageDao.insertCustomMessageUserConfig(paramMap);
				}
				//插入用户配置
				if("3".equals(paramMap.get("msg_user_type")+"")){
					paramMap.put("user_id", paramMap.get("user_id").toString().split(","));
					customMessageDao.insertCustomMessageUser(paramMap);
				}
				if(numMessage < 1){
					throw new RuntimeException("保存失败");
				}
			}
			//商品提交审批
			if("2".equals(paramMap.get("operate_type")+"")){
				if(StringUtils.isEmpty(paramMap.get("task_id"))){
					pr.setMessage("缺少参数[task_id]");
					pr.setState(false);
					return pr;
				}
				//校验当前记录是否可进行编辑
				Map<String,Object> customMessageDetailMap = customMessageDao.queryCustomMessageDetail(paramMap);
				if(customMessageDetailMap == null){
					pr.setMessage("操作记录不存在，请检查");
					pr.setState(false);
					return pr;
				}
				if(!"0".equals(customMessageDetailMap.get("TASK_STATE")+"") && !"4".equals(customMessageDetailMap.get("TASK_STATE")+"")){
					pr.setMessage("操作记录状态异常，请检查");
					pr.setState(false);
					return pr;
				}
				Map<String,Object> tempParamMap = new HashMap<String,Object>();
				tempParamMap.put("task_id", paramMap.get("task_id"));
				tempParamMap.put("task_state", 1);
				customMessageDao.updateCustomMessage(tempParamMap);
			}
			pr.setMessage("操作成功");
			pr.setState(true);
			pr.setObj(paramMap.get("task_id"));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 自定义消息审批
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult customMessageAudit(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("task_state"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> customMessageDetailMap=customMessageDao.queryCustomMessageDetail(paramMap);
			if(customMessageDetailMap == null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"1".equals(customMessageDetailMap.get("TASK_STATE")+"")){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//3.更新待审批记录的审批状态
			int num = customMessageDao.updateCustomMessageAuditInfo(paramMap);
			//审批通过时，推送用户计算存储
			if("2".equals(paramMap.get("task_state")+"") && !"3".equals(customMessageDetailMap.get("MSG_USER_TYPE")+"")){
				//获取自定义推送消息用户配置信息
				List<Map<String,Object>> customMsgUserConfigList = customMessageDao.queryCustomMessageUserConfig(paramMap);
				Map<String,Object> configMap = new HashMap<String,Object>();
				configMap.put("1", "site_id_arr");
				configMap.put("2", "market_supervision_user_id_arr");
				configMap.put("3", "user_group_arr");
				for(Map<String,Object> tempMap:customMsgUserConfigList){
					paramMap.put(configMap.get(tempMap.get("USER_CONFIG_TYPE")+"")+"", (tempMap.get("USER_CONFIG_CONTENT")+"").split(","));
				}
				if(customMessageDao.batchInsertCustomMessageUser(paramMap)<1){
					throw new RuntimeException("审批时处理推送用户处理失败");
				}
			}
			//3.1计算待推送用户群
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			if("2".equals(paramMap.get("task_state")+"")){
				//4.审批通过发送消息
				//发送自定义推送发送消息
				Map<String,Object> mqParamMap = new HashMap<String,Object>();
				mqParamMap.put("task_id", paramMap.get("task_id"));
				pushRabbitTemplate.convertAndSend(MqQueueKeyEnum.ESB_PUSH_MESSAGE_SEND.getKey(), Jackson.writeObject2Json(mqParamMap));
			}
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 自定义消息删除
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult customMessageDel(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> customMessageDetailMap=customMessageDao.queryCustomMessageDetail(paramMap);
			if(customMessageDetailMap == null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"0".equals(customMessageDetailMap.get("TASK_STATE")+"") && !"9".equals(customMessageDetailMap.get("TASK_STATE")+"")){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//清除相关记录
			int num = customMessageDao.deleteCustomMessage(paramMap);
			customMessageDao.deleteCustomMessageUser(paramMap);
			customMessageDao.deleteCustomMessageUserConfig(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	
	/**
	 * 自定义消息终止
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult customMessageStop(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("task_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> activityDetailMap=customMessageDao.queryCustomMessageDetail(paramMap);
			if(activityDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"2".equals(activityDetailMap.get("TASK_STATE")+"") && !"3".equals(activityDetailMap.get("TASK_STATE")+"") && !"1".equals(activityDetailMap.get("CAN_STOP_FLAG")+"")){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//3.更新记录为待终止
			int num = customMessageDao.updateCustomMessage(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			//发送自定义推送终止消息
			Map<String,Object> mqParamMap = new HashMap<String,Object>();
			mqParamMap.put("task_id", paramMap.get("task_id"));
			pushRabbitTemplate.convertAndSend(MqQueueKeyEnum.ESB_PUSH_MESSAGE_CANCEL.getKey(), Jackson.writeObject2Json(mqParamMap));
			
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取自定义消息频道页列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryCustomMessageChannelPageList(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			List<Map<String, Object>> list = customMessageDao.queryCustomMessageChannelPageList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
		} catch (Exception e) {
			e.printStackTrace();
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取自定义消息计算用户列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryCustomMessageCalUserList(HttpServletRequest request) {
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
			if(!StringUtils.isEmpty(paramMap.get("user_group"))){
				paramMap.put("user_group_arr", (paramMap.get("user_group")+"").split(","));
			}
			if(!StringUtils.isEmpty(paramMap.get("user_id"))){
				paramMap.put("user_id_arr", (paramMap.get("user_id")+"").split(","));
			}
			if(!StringUtils.isEmpty(paramMap.get("site_id"))){
				paramMap.put("site_id_arr", (paramMap.get("site_id")+"").split(","));
			}
			if(!StringUtils.isEmpty(paramMap.get("market_supervision_user_id"))){
				paramMap.put("market_supervision_user_id_arr", (paramMap.get("market_supervision_user_id")+"").split(","));
			}
			if(!StringUtils.isEmpty(paramMap.get("task_id"))){
				//获取自定义推送消息用户配置信息
				List<Map<String,Object>> customMsgUserConfigList = customMessageDao.queryCustomMessageUserConfig(paramMap);
				Map<String,Object> configMap = new HashMap<String,Object>();
				configMap.put("1", "site_id_arr");
				configMap.put("2", "market_supervision_user_id_arr");
				configMap.put("3", "user_group_arr");
				for(Map<String,Object> tempMap:customMsgUserConfigList){
					paramMap.put(configMap.get(tempMap.get("USER_CONFIG_TYPE")+"")+"", (tempMap.get("USER_CONFIG_CONTENT")+"").split(","));
				}
			}
			
			List<Map<String, Object>> list = customMessageDao.queryCustomMessageCalUserList(paramMap);
			int count = customMessageDao.queryCustomMessageCalUserCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			
			
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			e.printStackTrace();
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取自定义消息用户列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryCustomMessageUserList(HttpServletRequest request) {
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
			
			List<Map<String, Object>> list = customMessageDao.queryCustomMessageUserList(paramMap);
			int count = customMessageDao.queryCustomMessageUserCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			e.printStackTrace();
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
     * 查询会员列表
     *
     * @param request 查询条件
     * @return
     */
    public GridResult queryMemberList(HttpServletRequest request) {
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
            if (paramMap.containsKey("user_state") && !StringUtils.isEmpty(paramMap.get("user_state"))) {
                String[] state = paramMap.get("user_state").toString().split(",");
                if (state.length > 1) {
                    paramMap.put("user_state", paramMap.get("user_state"));
                } else {
                    paramMap.put("user_state", paramMap.get("user_state").toString().split(","));
                }
            }
            
            if((!StringUtils.isEmpty(paramMap.get("user_type")))&&paramMap.get("user_type") instanceof String){
            	paramMap.put("user_type",(paramMap.get("user_type")+"").split(","));
			}
            
            if((!StringUtils.isEmpty(paramMap.get("check_site_id"))) && paramMap.get("check_site_id") instanceof String){
            	paramMap.put("check_site_id_arr",(paramMap.get("check_site_id")+"").split(","));
			}
            
            if((!StringUtils.isEmpty(paramMap.get("filter_user_id"))) && paramMap.get("filter_user_id") instanceof String){
            	paramMap.put("filter_user_id_arr",(paramMap.get("filter_user_id")+"").split(","));
			}
            
            if (paramMap.containsKey("userList") && !StringUtils.isEmpty(paramMap.get("userList"))) {
                paramMap.put("user_list", paramMap.get("userList").toString().split(","));
            }
            //查询记录数
            int count = customMessageDao.queryMemberListCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = customMessageDao.queryMemberList(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setObj(list);
                gr.setMessage("获取成功");
                gr.setTotal(count);
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
	 * 获取自定义页列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryCustomPageList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			List<Map<String, Object>> list = customMessageDao.queryCustomPageList(paramMap);
			if (list != null && list.size() > 0) {
				pr.setMessage("获取数据成功");
				pr.setObj(list);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
}