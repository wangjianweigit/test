package com.tk.oms.decoration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface AdvertisingDao{
	
	/**
     * 查询广告位列表
     * @param params
     * @return
     */
	public List<Map<String,Object>> queryList(Map<String,Object> params);
	
	/**
     * 查询广告列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryDetail(Map<String,Object> params);
    /**
     * 查询广告位详情
     * @param params
     * @return
     */
    public Map<String,Object> queryAdvertisingDetail(Map<String,Object> params);
    
    /**
     * 通过id查询广告列表
     * @param params
     * @return
     */
    public Map<String,Object> queryDetailById(Map<String,Object> params);
    
    /**
     * 新增广告位
     * @param params
     * @return
     */
    public int insertAdvertising(Map<String,Object> params);
    /**
     * 修改广告位
     * @param params
     * @return
     */
    public int updateAdvertising(Map<String,Object> params);
    /**
     * 删除广告位
     * @param params
     * @return
     */
    public int removeAdvertising(Map<String,Object> params);
    
    /**
     * 新增广告
     * @param params
     * @return
     */
    public int insertAdvertisingDetail(Map<String,Object> params);
    /**
     * 修改广告
     * @param params
     * @return
     */
    public int updateAdvertisingDetail(Map<String,Object> params);
    /**
     * 删除广告
     * @param params
     * @return
     */
    public int removeAdvertisingDetail(Map<String,Object> params);
    
    /**
     * 根据广告位iD查询广告条数
     * @param params
     * @return
     */
    public Map<String,Object> queryDetailByDvertisingId(Map<String,Object> params);
    
    

}
