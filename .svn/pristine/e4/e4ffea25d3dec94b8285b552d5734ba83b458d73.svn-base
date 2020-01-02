package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface UserOperationLogDao {
	
	/**
     * 分页查询商品操作日志列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listUserOperationLogForPage(Map<String, Object> params);

    /**
     * 查询商品操作日志列表总数量
     * @param params
     * @return
     */
    int countUserOperationLogForPage(Map<String, Object> params);

}
