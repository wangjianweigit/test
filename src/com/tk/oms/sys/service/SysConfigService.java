package com.tk.oms.sys.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.sys.dao.SysConfigDao;
import com.tk.oms.sys.dao.VerifyCodeDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("SysManagerService")
public class SysConfigService {
	@Resource
    private SysConfigDao sysConfigDao;
	@Resource
	private VerifyCodeDao verifyCodeDao;
	
	
	
	/**
     * 获取系统配置表数据
     * @param request
     * @return
     */
	public GridResult querySysConfigList(HttpServletRequest request) {
		GridResult gr = new GridResult();

	        try {
	        	Map<String, Object> paramMap = new HashMap<String, Object>();
	        	String json = HttpUtil.getRequestInputStream(request);
		        paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	           int sysConfigCount=sysConfigDao.querySysConfigCount(paramMap);
	            // 获取配置表列表数据
	            Map<String,Object> sysConfigList = sysConfigDao.querySysConfigList(paramMap);
	           List<Map<String,Object>> codes= verifyCodeDao.queryAllVerifyCode();
	           for (Map<String,Object> code:codes){
	        	   if("pay".equals(code.get("TYPE"))){
	        		   sysConfigList.put("payCode", code.get("VERIFY_CODE"));
	        	   }else if("other".equals(code.get("TYPE"))){
	        		   sysConfigList.put("otherCode", code.get("VERIFY_CODE"));
	        	   }else{
	        		   sysConfigList.put("loginCode", code.get("VERIFY_CODE"));
	        	   }
	        	   
	           }
	            if (sysConfigList != null && sysConfigList.size() > 0) {
					gr.setState(true);
					gr.setMessage("查询成功!");
					gr.setObj(sysConfigList);
					gr.setTotal(sysConfigCount);
				} else {
					gr.setState(true);
					gr.setMessage("无数据");
				}

	        } catch (IOException e) {
	            gr.setState(false);
	            gr.setMessage(e.getMessage());
	        }

	        return gr;
	}
	/**
     * 系统配置新增
     * @param request
     * @return
     */
	@Transactional
	public ProcessResult addSysConfig(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            // 
            int  num = sysConfigDao.insertSysConfig(paramMap);

            pr.setState(true);
            pr.setMessage("添加成功！");
            pr.setObj(num);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	/**
	 * 系统配置编辑
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult editSysConfig(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            float member_service_rate=Integer.parseInt(paramMap.get("MEMBER_SERVICE_RATE").toString());
            member_service_rate=(float)member_service_rate/100;
            paramMap.put("MEMBER_SERVICE_RATE", member_service_rate);
            int  num = sysConfigDao.updateSysConfig(paramMap);

            pr.setState(true);
            pr.setMessage("更新成功！");
            pr.setObj(num);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}
	/**
	 * 系统配置删除
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult removeSysConfig(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            // 
            int  num = sysConfigDao.deleteSysConfig(paramMap);

            pr.setState(true);
            pr.setMessage("删除成功！");
            pr.setObj(num);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
	}

}
