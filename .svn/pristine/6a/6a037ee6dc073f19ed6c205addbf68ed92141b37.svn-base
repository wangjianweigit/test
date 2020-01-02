package com.tk.oms.analysis.control;

import com.tk.oms.analysis.service.BasicSaleDataService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : SalesBasicDataControl
 * 销售基础数据相关
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/8/31
 */
@Controller
@RequestMapping("basic_sale_data")
public class BasicSaleDataControl {

    @Resource
    private BasicSaleDataService basicSaleDataService;


    /**
     * 获取销售基础数据列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBasicSaleDataForPage(HttpServletRequest request) {
        return Transform.GetResult(this.basicSaleDataService.queryBasicSaleDataForPage(request));
    }

    /**
     * 获取门店、员工列表数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/store_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreList(HttpServletRequest request) {
        return Transform.GetResult(this.basicSaleDataService.queryStoreList(request));
    }


}

    
    
