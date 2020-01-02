package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 会员标记配置管理
 * @author zhenghui
 * @date 2017-7-4
 */
@Repository
public interface MemberFlagDao extends BaseDao<Map<String, Object>> {

    /**
     * 查询会员标记列表
     * @return
     */
    List<Map<String, Object>> queryMemberFlagForList(Map<String, Object> params);

    /**
     * 通过标记名称获取会员标记数量
     * @param params
     * @return
     */
    int queryMemberFlagCountByName(Map<String, Object> params);

    /**
     * 修改会员标记排序
     * @param params
     * @return
     */
    int updateMemberFlagForSort(Map<String, Object> params);


    /**
     * 通过ID查询已经标记的会员标记数量
     * @param params
     * @return
     */
    int queryUserMarkCountById(Map<String, Object> params);

    /**
     * 通过ID查询已经标记的库存标记数量
     * @param params
     * @return
     */
    int queryStockMarkCountById(Map<String, Object> params);


}
