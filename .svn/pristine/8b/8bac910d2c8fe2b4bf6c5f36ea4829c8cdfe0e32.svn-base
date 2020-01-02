package com.tk.oms.analysis.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : SalesAchievementDao
 * 销售人员绩效相关统计数据库访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-7-10
 */
@Repository
public interface SalesAchievementDao {
	/**
	 * 查询销售人员绩效分析数量
	 * @param paramMap
	 * @return
	 */
	int queryCount(Map<String, Object> paramMap);
	/**
	 * 查询销售人员绩效分析列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryListForPage(Map<String, Object> paramMap);

	/**
	 * 查询有效会员数量
	 * @param params
	 * @return
	 */
	int countEffectiveMember(Map<String, Object> params);

	/**
	 * 查询有效会员列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> listEffectiveMember(Map<String, Object> params);

    /**
     * 分页查询会员列表
     * @param params
     * @return
     */
	List<Map<String, Object>> listMemberForPage(Map<String, Object> params);

    /**
     * 查询会员列表总数量
     * @param params
     * @return
     */
	int countMemberForPage(Map<String, Object> params);

	/**
	 * 获取会员级别数量
	 * @param param
	 * @return
	 */
	int getMgsCount(Map<String, Object> param);
    /**
     * 根据类型查询统计规则列表
     * @param param
     * @return
     */
    List<Map<String,Object>> listSalesAchieveRuleByType(Map<String, Object> param);

    /**
     * 批量新增或编辑统计规则
     * @param params
     * @return
     */
    int batchInsertOrUpdateRule(Map<String,Object> params);

    /**
     * 根据ID删除统计规则
     * @param param
     * @return
     */
    int deleteRuleById(@Param("rule_id") long param);
    /**
     * 查询级别名称是否存在
     * @param rule
     * @return
     */
	int queryGradeNameIsExists(Map<String, Object> rule);
	/**
	 * 查询门店业务员所属会员
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryUserList(Map<String, Object> param);
	/**
	 * 查询级别会员数量信息
	 * @param p
	 * @return
	 */
	Map<String, Object> queryGradeUserInfo(Map<String, Object> p);
	/**
     * 获取激活会员数量
     * @param params
     * @return
     */
    int getEffectiveActiveMemberCount(Map<String,Object> params);
    /**
     * 获取沉睡会员数量
     * @param params
     * @return
     */
    int getSleepMemberCount(Map<String,Object> params);
    /**
     * 获取激活会员数量
     * @param params
     * @return
     */
    int getActiveMemberCount(Map<String,Object> params);
	/**
	 * 查询休眠会员详情列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMemberSleepDetailList(Map<String, Object> paramMap);
	/**
	 * 查询激活会员详情列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMemberActiveDetailList(Map<String, Object> paramMap);
	/**
	 * 查询有效激活会员详情列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMemberEffectActiveDetailList(Map<String, Object> paramMap);
	/**
	 * 新增规则
	 * @param rule
	 * @return
	 */
	int insert(Map<String, Object> rule);
	/**
	 * 更新规则
	 * @param rule
	 * @return
	 */
	int update(Map<String, Object> rule);
}
