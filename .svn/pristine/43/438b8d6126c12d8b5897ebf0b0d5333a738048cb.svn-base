package com.tk.oms.finance.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : UserAccountCheckDao
 * 用户对账数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/9/4 17:52
 */
@Repository
public interface UserAccountCheckDao extends BaseDao {
    /**
     * 分页查询任务对着那个明细记录数
     * @param params
     * @return
     */
    List<Map<String,Object>> queryListDetailForPage(Map<String, Object> params);

    /**
     * 分页查询任务对着那个明细数据
     * @param params
     * @return
     */
    int queryListDetailForCount(Map<String, Object> params);

    /**
     * 根据对账明细Id查询对账明细信息
     * @param params
     * @return
     */
    Map<String, Object> queryAccountCheckDetail(Map<String, Object> params);

    /**
     * 查询本地账户余额
     * @param bank_account
     * @return
     */
    float queryUserAccountBalance(String bank_account);

    /**
     * 新增会员见证宝对账明细
     * @param params
     * @return
     */
    int insertUserAccountCheckingDetail(Map<String, Object> params);

    /**
     * 查询未处理完的对账任务记录数
     * @return
     */
    int queryUserAccountCheckingTaskCount();
}
