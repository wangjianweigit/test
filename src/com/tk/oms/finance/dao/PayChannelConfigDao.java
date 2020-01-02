package com.tk.oms.finance.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : PayChannelDao
 * 支付渠道配置Dao
 * @author wangjianwei
 * @version 1.00
 * @date 2019/8/8 16:38
 */
@Repository
public interface PayChannelConfigDao extends BaseDao<Map<String, Object>> {
    /**
     * 新增操作记录
     * @param paramMap
     * @return
     */
    int insertOperationRecord(Map<String, Object> paramMap);
}
