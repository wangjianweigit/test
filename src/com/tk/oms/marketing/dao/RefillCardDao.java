package com.tk.oms.marketing.dao;

import com.tk.sys.common.BaseDao;

import java.util.List;
import java.util.Map;

public interface RefillCardDao extends BaseDao<Map<String, Object>> {


    /**
     * 查询充值卡列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> queryList(Map<String, Object> paramMap);
    /**
     * 查询充值卡总数
     * @param paramMap
     * @return
     */
    int queryCount(Map<String, Object> paramMap);

    /**
     * 插入或更新
     * @param paramMap
     * @return
     */
    int insertOrUpdate(Map<String, Object> paramMap);

    /**
     * 更改充值卡状态
     * @param paramMap
     * @return
     */
    int updateState(Map<String, Object> paramMap);


    /**
     * 查询销售记录
     * @param id
     * @return
     */
    List<Map<String, Object>> querySaleDetailByCard(Map<String, Object> params);

    int querySaleDetailCountByCard(Map<String, Object> params);
    /**
     * 查询购卡记录
     * @return
     */
    List<Map<String, Object>> queryBuyCardDetail(Map<String, Object> params);

    int queryBuyCardDetailCount(Map<String, Object> params);

    List<Map<String, Object>> queryBalanceDetail(Map<String, Object> params);

    int queryBalanceDetailCount(Map<String, Object> params);

    List<Map<String, Object>> queryBuyDetail(Map<String, Object> params);

    int queryBuyDetailCount(Map<String, Object> params);

    int isExistByNameAndType(Map<String, Object> params);

    /**
     * 更新会员卡销售时间
     * @param params
     * @return
     */
    int updateRefillCardDate(Map<String, Object> params);

}
