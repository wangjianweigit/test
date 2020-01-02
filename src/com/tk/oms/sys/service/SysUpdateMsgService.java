package com.tk.oms.sys.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.sys.dao.SysUpdateMsgDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;



@Service("SysUpdateMsgService")
public class SysUpdateMsgService{
    @Resource
    private SysUpdateMsgDao sysUpdateMsgDao;
	/**
     * 系统更新消息列表信息查询
     * @param request
     * @return
     */
    public GridResult querySysUpdateMsgList(HttpServletRequest request) {
    	GridResult gr = new GridResult();
        try {
        	//获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            List<Map<String, Object>> list = sysUpdateMsgDao.querySysUpdateMsgList(params);
            int count=sysUpdateMsgDao.querySysUpdateMsgCount(params);
            if (list != null && list.size() > 0) {
				gr.setMessage("查询成功!");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
            gr.setState(true);
            gr.setTotal(count);
        } catch (IOException e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 系统更新消息详细信息查询
     * @param request
     * @return
     */
    public ProcessResult querySysUpdateMsgDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null || paramMap.equals("")  || paramMap.get("id")==null  || paramMap.get("id").equals("")){
            	pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            Map<String, Object> msg_data = sysUpdateMsgDao.querySysUpdateMsgDetail(paramMap);
            pr.setState(true);
            pr.setMessage("获取数据成功!");
            pr.setObj(msg_data);
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    
    
    /**
     * 系统更新消息详细信息编辑
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editSysUpdateMsg(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null || paramMap.equals("")  || paramMap.get("id")==null  || paramMap.get("id").equals("")){
            	pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            int counts = sysUpdateMsgDao.editSysUpdateMsg(paramMap);
            if(counts<1){
            	pr.setState(false);
                pr.setMessage("编辑失败!");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("获取数据成功!");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }
    
    
    
    /**
     * 系统更新消息状态
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult updateMsgState(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
    	    String json = HttpUtil.getRequestInputStream(request);
    	    paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap == null || paramMap.equals("")  || paramMap.get("id")==null  || paramMap.get("id").equals("")|| StringUtils.isEmpty(paramMap.get("state"))){
            	pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            int counts = sysUpdateMsgDao.editSysUpdateMsgState(paramMap);
            if(counts<1){
            	pr.setState(false);
                pr.setMessage("修改状态失败!");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("修改状态成功!");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }
    
   

}
