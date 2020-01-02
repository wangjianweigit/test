package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface GroundPushDataStatisticsDao {
	
	/**
     * 将新用户存放到临时表
     * @param params
     * @return
     */
    public int insertNewUser(Map<String,Object> params);
    /**
     * 将沉睡用户存放到临时表
     * @param params
     * @return
     */
    public int insertSleepUser(Map<String,Object> params);
    /**
     * 查询业务人员数据列表总数
     * @param params
     * @return
     */
    public int queryYwryCount(Map<String,Object> params);
    /**
     * 查询业务人员数据列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryYwryList(Map<String,Object> params);
    /**
     * 查询业务人员地推数据统计列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryYwryGroundPushDataStatistics(Map<String,Object> params);
    /**
     * 查询推荐人地推数据统计列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryTjrGroundPushDataStatistics(Map<String,Object> params);
    /**
     * 查询推荐人地推数据统计总数
     * @param params
     * @return
     */
    public int queryTjrCount(Map<String,Object> params);
    /**
     * 查询推荐人地推数据统计列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryTjrList(Map<String,Object> params);
	

}
