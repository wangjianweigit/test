package com.tk.oms.member.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : MemberBonusPointsDao
 * 会员消费积分数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/20 10:49
 */
@Repository
public interface MemberBonusPointsDao {


    /**
     * 查询会员消费分列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryMemberBonusPointsList(Map<String, Object> params);

    /**
     * 查询会员消费分数据记录数
     *
     * @param params
     * @return
     */
    public int queryMemberBonusPointsCount(Map<String, Object> params);


    /**
     * 分页查询会员积分详细列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryMemberScoreDetailList(Map<String, Object> params);


    /**
     * 查询会员积分详细记录数
     *
     * @param params
     * @return
     */
    public int queryMemberScoreDetailCount(Map<String, Object> params);

}
