package com.tk.store.order.control;

import com.tk.store.order.service.StoreBasicSaleDataService;
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
 * FileName : StoreBasicSaleDataControl
 * 联营销售基础数据相关
 * @author: liujialong
 * @version: 1.00
 * @date: 2018/9/19
 */
@Controller
@RequestMapping("store_basic_sale_data")
public class StoreBasicSaleDataControl {

    @Resource
    private StoreBasicSaleDataService storeBasicSaleDataService;


    /**
     * 获取销售基础数据列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBasicSaleDataForPage(HttpServletRequest request) {
        return Transform.GetResult(this.storeBasicSaleDataService.queryBasicSaleDataForPage(request));
    }

    /**
     * 查询门店下拉数据(新零售门店ID)
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_store_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreList(HttpServletRequest request) {
        return Transform.GetResult(this.storeBasicSaleDataService.queryStoreList(request));
    }
    
    /**
     * 查询商家下拉数据(新零售经销商ID)
     * @param request
     * @return
     */
    @RequestMapping(value = "/query_agent_id", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAgentId(HttpServletRequest request) {
        return Transform.GetResult(this.storeBasicSaleDataService.queryAgentId(request));
    }
    
    /**
     * 导出excel
     * @param request
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public Packet exportExcel(HttpServletRequest request) {
        return Transform.GetResult(this.storeBasicSaleDataService.exportExcel(request));
    }


}

    
    
