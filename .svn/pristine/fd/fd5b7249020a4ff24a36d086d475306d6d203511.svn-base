package com.tk.oms.finance.control;

import com.tk.oms.finance.service.PayChannelConfigService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018, TongKu
 * FileName : PayChannelConfigControl
 * 支付渠道配置
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2019/8/8 16:30
 */
@Controller
@RequestMapping ("/pay_channel")
public class PayChannelConfigControl {

    @Resource
    private PayChannelConfigService payChannelConfigService;

    /**
     * @api {post} /{project_name}/pay_channel/config_operation_record 分页获取操作记录
     * @apiName pay_channel
     * @apiGroup config_operation_record
     * @apiDescription 分页获取操作记录
     * @apiVersion 0.0.1
     * @apiParam {number}	pageIndex 			开始页码
     * @apiParam {number}	pageSize 			每页数据量
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 提现申请详情列表
     */
    @RequestMapping (value = "/config_operation_record", method = RequestMethod.POST)
    @ResponseBody
    public Packet listPayChannelOperationRecord (HttpServletRequest request) {
        return Transform.GetResult(payChannelConfigService.listPayChannelOperationRecord(request));
    }

    /**
     * 获取支付渠道默认配置
     *
     * @param request
     *
     * @return
     */
    @RequestMapping (value = "/default_config", method = RequestMethod.POST)
    @ResponseBody
    public Packet getPayChannelConfig (HttpServletRequest request) {
        return Transform.GetResult(payChannelConfigService.getPayChannelConfig(request));
    }

    /**
     * 配置支付渠道
     *
     * @param request
     *
     * @return
     */
    @RequestMapping (value = "/config", method = RequestMethod.POST)
    @ResponseBody
    public Packet configPayChannel (HttpServletRequest request) {
        return Transform.GetResult(payChannelConfigService.configPayChannel(request));
    }
}
