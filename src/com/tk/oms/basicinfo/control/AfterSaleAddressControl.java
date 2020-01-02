package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.AfterSaleAddressService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("after_sale_address")
public class AfterSaleAddressControl {
	
	@Resource
    private AfterSaleAddressService afterSaleAddressService;
	
	
	/**
     * @api {post} /{project_name}/after_sale_address/list 售后地址列表
     * @apiGroup after_sale_address
     * @apiDescription  售后地址列表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex						页码 （第几页） 
	 * @apiParam {number} pageSize						每页多少条   
	 * 
     * @apiSuccess {object[]} obj               		售后地址列表
     * @apiSuccess {boolean} state              		接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             		接口返回信息.
     * @apiSuccess {number} obj.ID              		主键ID
     * @apiSuccess {string} obj.AFTER_SALE_NAME     	 售后人
     * @apiSuccess {string} obj.AFTER_SALE_ADDRESS      售后地址
     * @apiSuccess {string} obj.AFTER_SALE_MOBILE       联系方式
     * @apiSuccess {string} obj.STATE       			启用状态  1.禁用  2.启用
     * @apiSuccess {string} obj.CREATE_DATE       		创建时间
     * @apiSuccess {string} obj.CREATE_USER_ID       			创建人
     
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAfterSaleAddressList(HttpServletRequest request) {
        return Transform.GetResult(afterSaleAddressService.queryAfterSaleAddressList(request));
    }
    
    /**
     * @api {post} /{project_name}/after_sale_address/detail 售后地址详情
     * @apiGroup after_sale_address
     * @apiDescription  售后地址详情
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} id						主键id
     * 
     * @apiSuccess {object} obj               		售后地址列表
     * @apiSuccess {boolean} state              		接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             		接口返回信息.
     * @apiSuccess {number} obj.ID              		主键ID
     * @apiSuccess {string} obj.AFTER_SALE_NAME     	 售后人
     * @apiSuccess {string} obj.AFTER_SALE_ADDRESS      售后地址
     * @apiSuccess {string} obj.AFTER_SALE_MOBILE       联系方式
     * @apiSuccess {string} obj.STATE       			启用状态  1.禁用  2.启用
     * @apiSuccess {string} obj.CREATE_DATE       		创建时间
     * @apiSuccess {string} obj.CREATE_USER_ID       			创建人
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet afterSaleAddressDetail(HttpServletRequest request) {
        return Transform.GetResult(afterSaleAddressService.afterSaleAddressDetail(request));
    }
    
    
    /** 
	* @api {post} /{project_name}/after_sale_address/add 售后地址新增
    * @apiGroup after_sale_address
    * @apiDescription  售后地址新增
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} after_sale_name     	 售后人
    * @apiParam {string} after_sale_address      售后地址
    * @apiParam {string} after_sale_mobile       联系方式
    * @apiParam {string} state       			启用状态  1.禁用  2.启用
    * @apiParam {string} create_user_id       			创建人
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addAfterSaleAddress(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=afterSaleAddressService.addAfterSaleAddress(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/after_sale_address/edit 售后地址修改
    * @apiGroup after_sale_address
    * @apiDescription  售后地址修改
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} id     	 			主键id
	* @apiParam {string} AFTER_SALE_NAME     	 售后人
    * @apiParam {string} AFTER_SALE_ADDRESS      售后地址
    * @apiParam {string} AFTER_SALE_MOBILE       联系方式
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editAfterSaleAddress(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=afterSaleAddressService.editAfterSaleAddress(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

	/**
	 * 售后地址商家授权
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/authorized", method = RequestMethod.POST)
	@ResponseBody
	public Packet addOrEditStationedAfterSaleAddress(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=afterSaleAddressService.addOrEditStationedAfterSaleAddress(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr);
	}

	/**
	 * 查询售后地址关联商家列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationed_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAfterSaleAddressStationedList(HttpServletRequest request) {
		return Transform.GetResult(afterSaleAddressService.queryAfterSaleAddressStationedList(request));
	}
	
	/** 
	* @api {post} /{project_name}/after_sale_address/remove 售后地址删除
    * @apiGroup after_sale_address
    * @apiDescription  售后地址删除
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} id     	 			主键id
    * 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet removeAfterSaleAddress(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=afterSaleAddressService.removeAfterSaleAddress(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/** 
	* @api {post} /{project_name}/after_sale_address/update_state 启用禁用售后地址
    * @apiGroup after_sale_address
    * @apiDescription  启用禁用售后地址
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} id     	 			主键id
    * @apiParam {string} state     	 			启用状态  1.禁用  2.启用
    * 
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    */
	@RequestMapping(value = "/update_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet editAfterSaleAddressState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=afterSaleAddressService.editAfterSaleAddressState(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

}
