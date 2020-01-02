package com.tk.oms.member.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : DealerSalesStatisticsDao
 * 经销商销售报表数据访问接口
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/11/21 9:02
 */
@Repository
public interface DealerSalesStatisticsDao {

    /**
     * 分页查询经销商销售列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> queryDealerSalesListForPage(Map<String, Object> paramMap);

    /**
     * 查询经销商销售记录数
     * @param paramMap
     * @return
     */
    int queryDealerSalesListForCount(Map<String, Object> paramMap);
}
