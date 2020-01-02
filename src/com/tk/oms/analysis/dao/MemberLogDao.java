package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface MemberLogDao {
	
    /**
     * 查询会员日志列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryListForPage(Map<String,Object> params);

    /**
     * 统计查询会员日志总数
     * @param params
     * @return
     */
    int queryCountForPage(Map<String,Object> params);

    /**
     *  获取日志详情
     * @param params
     * @return
     */
    Map<String,Object> queryDetail(Map<String,Object> params);

}
