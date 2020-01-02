package com.tk.store.finance.service;

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

import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Service("DailyHandOverService")
public class DailyHandOverService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Value("${store_service_url}")
    private String store_service_url;
	
	/**
	 * 查询日结记录列表
	 * @param request
	 * @return
	 */
	public GridResult queryDailyListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGESIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("STAFF_ID") && !StringUtils.isEmpty(params.get("STAFF_ID"))){
                sendMap.put("STAFF_ID",params.get("STAFF_ID"));
            }
            if(params.containsKey("START_TIME") && !StringUtils.isEmpty(params.get("START_TIME"))){
                sendMap.put("START_TIME",params.get("START_TIME"));
            }
            if(params.containsKey("END_TIME") && !StringUtils.isEmpty(params.get("END_TIME"))){
                sendMap.put("END_TIME",params.get("END_TIME"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",params.get("STORE_ID"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"store/Work/esbDailyList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取日结记录列表成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("msg").toString());
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 查询交接班记录列表
	 * @param request
	 * @return
	 */
	public GridResult queryHandoverListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGESIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("STAFF_ID") && !StringUtils.isEmpty(params.get("STAFF_ID"))){
                sendMap.put("STAFF_ID",params.get("STAFF_ID"));
            }
            if(params.containsKey("START_TIME") && !StringUtils.isEmpty(params.get("START_TIME"))){
                sendMap.put("START_TIME",params.get("START_TIME"));
            }
            if(params.containsKey("END_TIME") && !StringUtils.isEmpty(params.get("END_TIME"))){
                sendMap.put("END_TIME",params.get("END_TIME"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",params.get("STORE_ID"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"store/Work/esbWorkList");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
                gr.setState(true);
                gr.setMessage("获取交接班列表成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("msg").toString());
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	
	/**
	 * 查询日结详情
	 * @param request
	 * @return
	 */
	public GridResult queryDailyDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGESIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("TYPE") && !StringUtils.isEmpty(params.get("TYPE"))){
                sendMap.put("TYPE",params.get("TYPE"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",params.get("STORE_ID"));
            }
            if(params.containsKey("DAILY_ID") && !StringUtils.isEmpty(params.get("DAILY_ID"))){
                sendMap.put("DAILY_ID",params.get("DAILY_ID"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"store/Work/esbDailyDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	gr.setState(true);
                gr.setMessage("获取日结详情成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("msg").toString());
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
	}
	

	/**
	 * 查询交接班详情
	 * @param request
	 * @return
	 */
	public GridResult queryHandoverDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("AGENT_ID"))) {
                gr.setState(false);
                gr.setMessage("缺少参数AGENT_ID");
                return gr;
            }
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("PAGESIZE",params.get("pageSize"));
            sendMap.put("INDEX",params.get("pageIndex"));
            if(params.containsKey("AGENT_ID") && !StringUtils.isEmpty(params.get("AGENT_ID"))){
                sendMap.put("AGENT_ID",params.get("AGENT_ID"));
            }
            if(params.containsKey("TYPE") && !StringUtils.isEmpty(params.get("TYPE"))){
                sendMap.put("TYPE",params.get("TYPE"));
            }
            if(params.containsKey("STORE_ID") && !StringUtils.isEmpty(params.get("STORE_ID"))){
                sendMap.put("STORE_ID",params.get("STORE_ID"));
            }
            if(params.containsKey("WORK_ID") && !StringUtils.isEmpty(params.get("WORK_ID"))){
                sendMap.put("WORK_ID",params.get("WORK_ID"));
            }
            Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(sendMap, store_service_url+"store/Work/esbWorkDetail");
            if (Integer.parseInt(resPr.get("state").toString()) == 1) {
            	gr.setState(true);
                gr.setMessage("获取交接班详情成功");
                Map<String, Object> data =  (Map<String, Object>)resPr.get("data");
                List<Map<String, Object>> dataList=(List<Map<String, Object>>)data.get("data");
                int total=Integer.parseInt(data.get("total").toString());
                gr.setTotal(total);
                gr.setObj(dataList);
            }else{
            	gr.setState(false);
                gr.setMessage(resPr.get("msg").toString());
                gr.setObj("");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
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
        String json = HttpClientUtil.post(url, params);
        return Transform.GetPacketJzb(json,Map.class);
    }

}
