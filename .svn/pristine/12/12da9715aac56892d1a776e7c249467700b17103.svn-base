package com.tk.oms.attesttreas.service;

import com.tk.oms.attesttreas.dao.AttesttreasDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Copyright (c), 2016, Tongku
 * FileName : AttesttreasTimeoutService
 * 见证宝请求超时异常记录
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-09-06
 */
@Service("AttesttreasTimeoutService")
public class AttesttreasTimeoutService {

    @Resource
    private AttesttreasDao attesttreasDao;

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    public void saveTimeoutException(Map<String, Object> map) {

        attesttreasDao.insertAttesttreasTimeout(map);
    }
}
