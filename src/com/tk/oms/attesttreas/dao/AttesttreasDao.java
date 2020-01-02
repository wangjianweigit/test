package com.tk.oms.attesttreas.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Copyright (c), 2016, Tongku
 * FileName : AttesttreasTimeoutDao
 * 见证宝请求超时异常记录
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-09-06
 */
@Repository
public interface AttesttreasDao {

    /**
     * 添加见证宝请求超时异常记录
     *
     * @param paramsMap
     * @return
     */
    int insertAttesttreasTimeout(Map<String, Object> paramsMap);

    /**
     * 获取见证宝请求编号
     *
     * @return
     */
    String getThiredLogNo();
}
