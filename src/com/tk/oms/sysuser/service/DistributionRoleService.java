package com.tk.oms.sysuser.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Service("DistributionRoleService")
public class DistributionRoleService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${retail_service_new_url}")
    private String retail_service_new_url;
	
	/**
     * 经销商角色列表
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult distributionRoleList(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("index", paramMap.get("pageIndex"));
            sendMap.put("pageSize", paramMap.get("pageSize"));
            if(!StringUtils.isEmpty(paramMap.get("keyword"))){
            	sendMap.put("ROLE_NAME", paramMap.get("keyword"));
            }
			if(!StringUtils.isEmpty(paramMap.get("dateMin"))){
				sendMap.put("TIME_START", paramMap.get("dateMin"));    	
            }
			if(!StringUtils.isEmpty(paramMap.get("dateMax"))){
				sendMap.put("TIME_END", paramMap.get("dateMax"));
            }
			if(!StringUtils.isEmpty(paramMap.get("type"))){
				sendMap.put("ACCOUNT", paramMap.get("distribution_id"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/roleList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	Map<String, Object> resMap=new HashMap<String,Object>();
                if(StringUtils.isEmpty(paramMap.get("type"))){
                	resMap=(Map<String, Object>)resPr.get("data");
                	gr.setTotal(Integer.parseInt(resMap.get("count").toString()));
                	gr.setObj(resMap.get("list"));
                }else{
                	gr.setObj(resPr.get("data"));
                }
                gr.setState(true);
                gr.setMessage("获取角色列表成功");
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 经销商角色新增
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult distributionRoleAdd(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
            if(StringUtils.isEmpty(paramMap.get("role_name"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数角色名称");
            	return gr;
            }else{
            	sendMap.put("ROLE_NAME", paramMap.get("role_name"));
            }
            if(!StringUtils.isEmpty(paramMap.get("role_nodes"))){
            	sendMap.put("NODES", paramMap.get("role_nodes"));
            }
            if(!StringUtils.isEmpty(paramMap.get("remarks"))){
            	sendMap.put("REMARK", paramMap.get("remarks"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/roleSet");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("新增成功");
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 经销商角色编辑
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult roleEdit(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
            Map<String, Object> sendMap = new HashMap<String, Object>();
        //  校验role_id参数
			if(StringUtils.isEmpty(paramMap.get("role_id"))) {
				gr.setState(false);
				gr.setMessage("缺少参数role_id");
				return gr;
			}else{
				sendMap.put("ROLE_ID", paramMap.get("role_id"));
			}
			if(StringUtils.isEmpty(paramMap.get("ROLE_NAME"))){
            	gr.setState(false);
            	gr.setMessage("缺少参数角色名称");
            	return gr;
            }else{
            	sendMap.put("ROLE_NAME", paramMap.get("ROLE_NAME"));
            }
		    if(!StringUtils.isEmpty(paramMap.get("role_nodes"))){
            	sendMap.put("NODES", paramMap.get("role_nodes"));
            }
            if(!StringUtils.isEmpty(paramMap.get("REMARK"))){
            	sendMap.put("REMARK", paramMap.get("REMARK"));
            }else{
            	sendMap.put("REMARK", "");
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/roleSet");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("编辑成功");
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 经销商角色删除
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult distributionRoleRemove(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
        //  校验role_id参数
			if(StringUtils.isEmpty(paramMap.get("role_id"))) {
				gr.setState(false);
				gr.setMessage("缺少参数role_id");
				return gr;
			}
			Map<String, Object> sendMap = new HashMap<String, Object>();
			sendMap.put("ROLE_ID", paramMap.get("role_id"));
			sendMap.put("DEL", "1");
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/roleSet");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("删除成功");
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 保存经销商对应角色
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult distributionRoleEdit(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("distribution_id"))) {
				gr.setState(false);
				gr.setMessage("缺少参数distribution_id");
				return gr;
			}
            if(StringUtils.isEmpty(paramMap.get("role_ids"))) {
				gr.setState(false);
				gr.setMessage("缺少参数role_ids");
				return gr;
			}
			Map<String,Object> sendMap=new HashMap<String,Object>();
			sendMap.put("ACCOUNT", paramMap.get("distribution_id"));
			sendMap.put("ROLE_IDS", paramMap.get("role_ids"));
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/agentRoleSet");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("保存成功");
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 查询节点列表
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryNodeList(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/nodeList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取节点成功");
                gr.setObj(resPr.get("data"));
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 查询当前角色节点详情
     * @param request
     * @return
     * @throws Exception
     */
    public GridResult queryNodeDetail(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            HashMap<String,Object> paramMap = (HashMap<String,Object>) Transform.GetPacket(json, HashMap.class);
            //  校验角色id参数
			if(!paramMap.containsKey("role_id")) {
				gr.setState(false);
				gr.setMessage("缺少参数role_id");
				return gr;
			}
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("ROLE_ID", paramMap.get("role_id"));
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, retail_service_new_url+"open/ManageRole/roleDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取角色节点详情成功");
                gr.setObj(resPr.get("data"));
            }else{
                gr.setState(false);
                gr.setMessage(resPr.get("message").toString());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.info(e.getMessage());
        }
        return gr;
    }
    
    public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params,20000);
        return Transform.GetPacketJzb(json,Map.class);
    }

}
