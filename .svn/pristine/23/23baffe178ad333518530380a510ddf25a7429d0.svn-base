package com.tk.oms.finance.control;

import com.tk.oms.finance.service.SettlementService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 入驻商结算管理
 * @author zhenghui
 */
@Controller
@RequestMapping("/settlement")
public class SettlementControl {

    @Resource
    private SettlementService settlementService;

    /**
     * @api{post} /{oms_server}/settlement/list  结算单列表
     * @apiGroup settlement_list
     * @apiName settlement
     * @apiDescription  分页获取结算单列表信息
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} pageIndex  起始页
     * @apiParam   {number} pageSize   分页大小
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     * @apiSuccess  {number} total     总条数
     * @apiSuccess  {object} obj       结算单列表信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySettlementListByPage(HttpServletRequest request){
        return Transform.GetResult(this.settlementService.querySettlementListByPage(request));

    }

    /**
     * @api{post} /{oms_server}/settlement/detail  结算单列表
     * @apiGroup settlement_detail
     * @apiName settlement
     * @apiDescription  分页获取结算单列表信息
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} id         结算单ID
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySettlementDetail(HttpServletRequest request){
        return Transform.GetResult(this.settlementService.querySettlementDetail(request));

    }
}
