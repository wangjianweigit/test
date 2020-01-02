package com.tk.oms.member.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : MemberPreloadChargeDao
 * 会员预充值数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/12 14:50
 */
@Repository
public interface MemberPreloadChargeDao extends BaseDao<Map<String, Object>> {
    /**
     * 保存充值收支记录
     * @param params
     * @return
     */
    int insertIncomeRecord(Map<String, Object> params);
}
