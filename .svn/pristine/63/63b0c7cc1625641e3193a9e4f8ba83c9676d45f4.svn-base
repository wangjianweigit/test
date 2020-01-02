package com.tk.store.marking.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.marking.service.StoreMarketActivityService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
@Controller
@RequestMapping("/store_market_activity")
public class StoreMarketActivityControl {
	
	@Resource
    private StoreMarketActivityService storeMarketActivityService;
	/**
    * @api{post} /{oms_server}/store_market_activity/list 店铺营销活动列表
    * @apiGroup list
    * @apiName list
    * @apiDescription  店铺营销活动列表
    * @apiVersion 0.0.1
    * @apiParam{number} [pageIndex=1] 起始页
    * @apiParam{number} [pageSize=10] 分页大小
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 店铺列表
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {number} obj.STORE_ID    门店ID
    * @apiSuccess {number} ACTIVITY_NAME 活动名称
    * @apiSuccess {number} obj.DISCOUNT    折扣
    * @apiSuccess {number} obj.STATE    活动状态(1 待审核，2已审核，3驳回)
    * @apiSuccess {string} obj.BEGIN_DATE    开始时间
    * @apiSuccess {string} obj.END_DATE    结束时间
    * @apiSuccess{number} total 总条数
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMarketActivityList(HttpServletRequest request) {
		return Transform.GetResult(storeMarketActivityService.storeMarketActivityList(request));
	}
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/store_market_activity/add 店铺营销活动新增
	 * @apiGroup store_market_activity
	 * @apiName add
	 * @apiDescription 店铺营销活动新增
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number} store_id 门店id 必填
	 * @apiParam {number} state 活动状态(1 待审核，2已审核，3驳回) 必填
	 * @apiParam {number} discount 折扣 必填
	 * @apiparam {number} begin_date 开始时间 必填
	 * @apiparam {number} end_date 结束时间 必填
	 * @apiparam {number} activity_name 活动名称 必填
	 * @apiparam {number} create_date 创建时间 必填
	 * @apiparam {number} create_user_id 创建人 必填
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addStore(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMarketActivityService.add(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
    * @api{post} /{oms_server}/store_market_activity/detail 店铺营销活动详情
    * @apiGroup store_market_activity
    * @apiName detail
    * @apiDescription  店铺营销活动详情
    * @apiVersion 0.0.1
    * 
    * @apiParam{number} id 主键ID
    * 
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 详情信息
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {number} obj.STORE_ID    门店ID
    * @apiSuccess {number} ACTIVITY_NAME 活动名称
    * @apiSuccess {number} obj.DISCOUNT    折扣
    * @apiSuccess {number} obj.STATE    活动状态(1 待审核，2已审核，3驳回)
    * @apiSuccess {string} obj.BEGIN_DATE    开始时间
    * @apiSuccess {string} obj.END_DATE    结束时间
    * @apiSuccess{number} total 总条数
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMarketActivityDetail(HttpServletRequest request) {
		return Transform.GetResult(storeMarketActivityService.storeMarketActivityDetail(request));
	}
	
	/**
	 * 商品库列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/library_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeMarketActivityService.queryProductLibraryListForPage(request));
	}
	
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/store_market_activity/add 店铺营销活动新增
	 * @apiGroup store_market_activity
	 * @apiName add
	 * @apiDescription 店铺营销活动新增
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number} STORE_ID 门店id 必填
	 * @apiParam {number} STATE 活动状态(1 待审核，2已审核，3驳回) 必填
	 * @apiParam {number} DISCOUNT 折扣 必填
	 * @apiparam {number} BEGIN_DATE 开始时间 必填
	 * @apiparam {number} END_DATE 结束时间 必填
	 * @apiparam {number} ACTIVITY_NAME 活动名称 必填
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMarketActivityEdit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMarketActivityService.storeMarketActivityEdit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/store_market_activity/approval 店铺营销活动审批
	 * @apiGroup store_market_activity
	 * @apiName approval
	 * @apiDescription 店铺营销活动审批
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number} activity_id 活动id
	 * @apiParam {number} activity_state 审批状态(1 待审核，2已审核，3驳回) 
	 * @apiParam {String} reject_reason 驳回原因(驳回的时候填写)
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMarketActivityApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMarketActivityService.storeMarketActivityApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/store_market_activity/delete 店铺营销活动删除
	 * @apiGroup store_market_activity
	 * @apiName delete
	 * @apiDescription 店铺营销活动删除
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number} activity_id 活动id
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMarketActivityDelete(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMarketActivityService.storeMarketActivityDelete(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * @param request
	 * @return
	 * @api {post} /{project_name}/store_market_activity/pause 店铺营销活动暂停
	 * @apiGroup store_market_activity
	 * @apiName pause
	 * @apiDescription 店铺营销活动暂停
	 * @apiVersion 0.0.1
	 * 
	 * @apiParam {number} activity_id 活动id
	 * @apiParam {number} PAUSE_STATE 暂停状态  1 正常 2暂停
	 * 
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 */
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeMarketActivityPause(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeMarketActivityService.storeMarketActivityPause(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 查询商家下面的店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_user_store_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserStoreList(HttpServletRequest request) {
		return Transform.GetResult(storeMarketActivityService.queryUserStoreList(request));
	}
	

}
