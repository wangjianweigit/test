package com.tk.oms.finance.control;

import com.tk.oms.finance.service.StatementAccountService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2016, Tongku
 * FileName : StatementAccountControl
 * 对账查询接口
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-01-11
 */
@Controller
@RequestMapping("/statement_account")
public class StatementAccountControl {

    @Resource
    private StatementAccountService statementAccountService;

    /**
     * 查询对账列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStatmentList(HttpServletRequest request) {
        return Transform.GetResult(statementAccountService.queryStatmentList(request));
    }

}
