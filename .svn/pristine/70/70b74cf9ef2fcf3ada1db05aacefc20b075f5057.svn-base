package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface HomeWorkbenchDao {
	
	 /**
     * 首页工作台查询订单信息
     * @param params
     * @return
     */
    public Map<String,Object> queryHomeWorkOrderInfo(Map<String,Object> params);
    /**
     * 首页工作台会员成交信息
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHomeWorkMemberTransactions(Map<String,Object> params);
    /**
     * 首页工作台异常会员信息
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHomeWorkUnusualMember(Map<String,Object> params);
    /**
     * 首页工作台会员门店地址信息
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHomeWorkMemberStoreAddress(Map<String,Object> params);
    /**
     * 首页工作台查询区域销售
     * @param params
     * @return
     */
    public Map<String,Object> queryHomeWorkMemberRegionSale(Map<String,Object> params);
    /**
     * 首页工作台会员活跃度
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryHomeWorkMemberActivity(Map<String,Object> params);
    /**
     * 首页工作台Z查询会员数
     * @param params
     * @return
     */
    public int queryHomeWorkMemberCount(Map<String,Object> params);
    

}
