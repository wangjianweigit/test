package com.tk.oms.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface SysUpdateMsgDao {
	/**
     * 系统更新消息列表信息查询
     * @param params
     * @return
     */
    public List<Map<String, Object>> querySysUpdateMsgList(Map<String, Object> params);
    
    /**
     * 系统更新消息列表总条数
     * @param params
     * @return
     */
    public int querySysUpdateMsgCount(Map<String, Object> params);
    
    
    /**
     * 系统更新消息详细信息查询
     * @param params
     * @return
     */
    public Map<String, Object> querySysUpdateMsgDetail(Map<String, Object> params);
    
    
    /**
     * 系统更新消息详细信息编辑
     * @param params
     * @return
     */
    public int editSysUpdateMsg(Map<String, Object> params);
    
    /**
     * 系统更新消息状态修改
     * @param params
     * @return
     */
    public int editSysUpdateMsgState(Map<String, Object> params);
}
