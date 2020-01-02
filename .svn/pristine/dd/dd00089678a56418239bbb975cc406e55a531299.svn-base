package com.tk.oms.finance.control;

import com.tk.oms.finance.service.UserChargeRecordService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 会员充值记录管理
 * @author songwangwen
 * @date  2017-5-6  下午3:24:46
 */
@Controller
@RequestMapping("/user_charge")
public class UserChargeRecordControl {

    @Resource
    private UserChargeRecordService userChargeRecordService;

    /**
     * @api{post} /{oms_server}/user_charge/list 会员充值记录列表
     * @apiGroup user_charge
     * @apiName user_charge
     * @apiDescription  分页获取会员充值记录列表
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} pageIndex  起始页
     * @apiParam   {number} pageSize   分页大小
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     * @apiSuccess  {number} total     总条数
     * @apiSuccess  {object} obj       会员充值记录列表信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryChargeRecordListByPage(HttpServletRequest request){
        return Transform.GetResult(this.userChargeRecordService.queryChargeRecordListByPage(request));

    }
    /**
     * @api{post} /{oms_server}/user_charge/detail 会员充值记录详情
     * @apiGroup user_charge
     * @apiName user_charge
     * @apiDescription  根据ID获取会员充值记录详情
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} id  会员充值记录ID
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     * @apiSuccess  {object} obj      会员充值记录详情信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryChargeRecordById(HttpServletRequest request){
    	return Transform.GetResult(this.userChargeRecordService.queryChargeRecordById(request));
    	
    }
    /**
     * @api{post} /{oms_server}/user_charge/edit 会员充值记录审核
     * @apiGroup user_charge
     * @apiName user_charge
     * @apiDescription  会员充值记录审核
     * @apiVersion 0.0.1
     *
     * @apiParam   {number} id  	会员充值记录ID
     * @apiParam   {number} id  	会员充值记录ID
     * @apiParam   {string} state   新状态
     *
     * @apiSuccess  {boolean} state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess  {string} message   接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editChargeRecordById(HttpServletRequest request){
    	ProcessResult pr= new ProcessResult();
    	try {
    		pr = this.userChargeRecordService.editChargeRecordById(request);
		} catch (Exception e) {
			// TODO: handle exception
			pr.setMessage(e.toString());
		}
    	return Transform.GetResult(pr);
    }
}
