package com.tk.oms.stationed.dao;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StationedTemplateDao extends BaseDao<Map<String, Object>> {


    /**
     * 查询入驻商模板列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> queryList(Map<String, Object> paramMap);

    /**
     * 查询入驻商模板数量
     * @param paramMap
     * @return
     */
    int queryCount(Map<String, Object> paramMap);

    /**
     * 获取序列ID
     * @return
     */
    int queryTemplateId();

    /**
     * 插入或更新
     * @param paramMap
     * @return
     */
    int insertOrUpdate(Map<String, Object> paramMap);

    /**
     * 更新数据
     * @param paramMap
     * @return
     */
    int update(Map<String, Object> paramMap);

    /**
     * 更新不等于模板ID的模板默认状态
     * @param paramMap
     * @return
     */
    int updateByDefault(Map<String, Object> paramMap);


    /**
     * 逻辑删除模版
     * @param paramMap
     * @return
     */
    int updateIsDelete(Map<String, Object> paramMap);

    /**
     * 是否存在相同模板名称
     * @param paramMap
     * @return
     */
    int isExistTemplateName(Map<String, Object> paramMap);
}
