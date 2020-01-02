package com.tk.oms.finance.dao;


import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : SalesmanCreditLineDao
 * 业务员授信额度管理数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/5/6 14:56
 */
@Repository
public interface SalesmanCreditLineDao extends BaseDao{

    /**
     * 查询业务员授信额度管理列表记录数
     * @param param
     * @return
     */
    public int querySalesmanCreditLineCount(Map param);

    /**
     * 查询业务员授信额度管理列表
     * @param param
     * @return
     */
    public List<Map<String,Object>> querySalesmanCreditLineList(Map param);

    /**
     * 修改业务员授信额度
     * @param params
     */
    public int updateCreditMoney(Map<String, Object> params);
}
