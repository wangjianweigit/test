package com.tk.oms.sysuser.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PrivateInfoLogsDao {
	
	/**
     * 新增私密日志
     * @param node
     */
    public int insert(Map<String, Object> params);
    
    /**
     * 私密日志总条数
     * @param node
     */
    public int queryPrivateInfoLogsCount(Map<String, Object> params);
    
    /**
     * 私密日志列表
     * @param node
     */
    public List<Map<String,Object>> queryPrivateInfoLogsList(Map<String, Object> params);

}
