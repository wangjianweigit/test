package com.tk.pvtp.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PvtpMemberInfoDao {
	/**
	 * 获取业务员、业务经理、门店下属会员数量
	 * @param params
	 * @return
	 */
	int querySubsidiaryMemberCount(Map<String, Object> params);
	/**
	 * 获取业务员、业务经理、门店下属会员列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> querySubsidiaryMemberList(Map<String, Object> params);
	/**
      * 根据用户名查询用户信息
      * @param login_name
      * @return
      */
    Map<String,Object> queryMemberInfoById (Map<String,Object> params);
    /**
	 *会员预审通过次数加1或者预审登入次数加1
	 * @param paramMap
	 * @return
	 */
	int updateMemberInfoById(Map<String, Object> paramMap);
	/**
	 * 获取二级域名
	 * @param params
	 * @return
	 */
	Map<String, Object> getSLD(Map<String, Object> params);
	/**
     * 查询私有站会员列表
     * @param params 查询条件
     * @return
     */
    public List<Map<String,Object>> queryPvtpMemberInfoList(Map<String,Object> params);
    /**
     * 查询私有站会员列表总数
     * @param params
     * @return
     */
    public int queryPvtpMemberInfoCount(Map<String,Object> params);
    /**
	 * 查询私有站会员详情
	 * @param params
	 * @return
	 */
	Map<String, Object> queryPvtpMemberDetail(Map<String, Object> params);
	/**
	 * 查询私有站的业务员、业务经理 列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryStationUserList(Map<String, Object> params);
	/**
	 *  查询某个私有站业务经理下属的私有站门店列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> queryStationListByManager(Map<String, Object> params);
	/**
     * 查询私有会员收支记录列表
     * @param params 查询条件
     * @return
     */
    public List<Map<String,Object>> queryPvtpMemberRevenueRecordLit(Map<String,Object> params);
    /**
     * 查询私有会员收支记录列表总数
     * @param params
     * @return
     */
    public int queryPvtpMemberRevenueRecordCount(Map<String,Object> params);
    /**
	 * 查询私有站会员收支记录详情
	 * @param params
	 * @return
	 */
	Map<String, Object> queryPvtpMemberRevenueRecordDetail(Map<String, Object> params);
}
