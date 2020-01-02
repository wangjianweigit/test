package com.tk.oms.basicinfo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ContractTemplateDetailDao {


    int insert(Map<String, Object> params);

    int update(Map<String, Object> params);

    /**
     * 根据模板ID删除模板详情
     * @param template_id
     * @return
     */
    int deleteByTemplateId(@Param("template_id") long template_id);

}
