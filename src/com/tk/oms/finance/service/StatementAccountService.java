package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.StatementAccountDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2016, Tongku
 * FileName : StatementAccountService
 * 杉德支付对账单下载处理
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-01-09
 */
@Service("StatementAccountService")
public class StatementAccountService {

    @Resource
    private StatementAccountDao statementAccountDao;

    /**
     * 查询对账列表
     *
     * @param request
     * @return
     * @throws IOException
     */
    public GridResult queryStatmentList(HttpServletRequest request) {
        GridResult gr = new GridResult();

        Map<String, Object> params = new HashMap<String, Object>();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }

            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }

            List<Map<String, Object>> statementAccountList = statementAccountDao.queryStatmentList(params);
            int count = statementAccountDao.queryStatmentCount(params);

            gr.setState(true);
            gr.setMessage("获取成功");
            gr.setObj(statementAccountList);
            gr.setTotal(count);
        } catch (IOException e) {
            e.printStackTrace();
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }

}
