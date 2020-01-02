package com.tk.store.stock.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.stock.service.StockAllocateService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : StockAllocateControl
 * 库存调拨control层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-2
 */
@Controller
@RequestMapping("/stock_allocate")
public class StockAllocateControl {
	@Resource
	private StockAllocateService stockAllocateService;
	
	/**
    *
    * @api {post} /{project_name}/stock_allocate/list 库存调拨列表
    * @apiGroup stock_allocate
    * @apiName list
    * @apiDescription  库存调拨列表
    * @apiVersion 0.0.1
	
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 商品列表
    * @apiSuccess {number} obj.approval_state		审核状态：1草稿，2待审批，3审批通过，4驳回
    * @apiSuccess {number} obj.state				调拨状态：1未开始，2待出库，3待入库，4已完成，5终止
    * @apiSuccess {string} obj.in_user_name			调入门店
	* @apiSuccess {string} obj.out_user_name		调出门店
	* @apiSuccess {string} obj.create_user_name		申请人
	* @apiSuccess {string} obj.create_date			申请时间
	* @apiSuccess {number} obj.approval_user_name	审批人
	* @apiSuccess {string} obj.approval_date		审批时间
	* @apiSuccess {string} obj.rejected_reason		驳回原因
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStockAllocateListForPage(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryStockAllocateListForPage(request));
	}
	
	/**
	 * 新增调拨指令单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet insertStockAllocate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stockAllocateService.insertStockAllocate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
    *
    * @api {post} /{project_name}/stock_allocate/detail 调拨单详情
    * @apiGroup stock_allocate
    * @apiName detail
    * @apiDescription  调拨单详情
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} allocate_number		调拨单号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 调拨单详情
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStockAllocateDetail(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryStockAllocateDetail(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/stock_allocate/detail_sku 调拨单sku详情
    * @apiGroup stock_allocate
    * @apiName detail_sku
    * @apiDescription  调拨单sku详情
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} allocate_number		调拨单号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 调拨单详情
    */
	@RequestMapping(value = "/detail_sku", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStockAllocateDetailSku(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryStockAllocateDetailSku(request));
	}
	/**
	 * 商品库存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_stock", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductStock(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryProductStock(request));
	}
	/**
	 * 商品分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_group", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductGroup(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryProductGroup(request));
	}
	
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet approvalStockAllocate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stockAllocateService.approvalStockAllocate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 出入库详情【基本信息】
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inout_info", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryInoutInfoDetail(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryInoutInfoDetail(request));
	}
	
	/**
	 * 出入库详情【商品列表】
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inout_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryInoutListDetail(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryInoutListDetail(request));
	}
	
	/**
	 * 差异列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/diff_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryDiffList(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryDiffList(request));
	}
	
	/**
	 * 核销
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancel_ver", method = RequestMethod.POST)
	@ResponseBody
	public Packet cancelVer(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.cancelVer(request));
	}
	
	/**
	 * 获取调拨任务单详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAllotTaskDetail(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryAllotTaskDetail(request));
	}
	
	/**
	 * 获取商品库存列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStockProductList(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryStockProductList(request));
	}
	
	/**
	 * 获取sku库存列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sku_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStockSkuList(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryStockSkuList(request));
	}
	
	/**
	 * 新零售门店下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeSelect(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.storeSelect(request));
	}
	
	/**
	 * 门店分组下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_group_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeGroupSelect(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.storeGroupSelect(request));
	}
	
	/**
	 * 门店分组详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_group_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreGroupDetail(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryStoreGroupDetail(request));
	}
	
	/**
	 * 新增门店分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_group_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addStoreGroup(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.addStoreGroup(request));
	}
	
	/**
	 * 删除门店分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_group_remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet delStoreGroup(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.delStoreGroup(request));
	}
	/**
	 * 新增调拨单(调出)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/out_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet insertStockAllocateOut(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stockAllocateService.insertStockAllocateOut(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
	 * 新增调拨单(调入)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/in_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet insertStockAllocateIn(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stockAllocateService.insertStockAllocateIn(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 获取调拨任务单(调出)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/out_task", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryAllotOutTask(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.queryAllotOutTask(request));
	}
	
	/**
	 * 查看要货/退货单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_order_qeury", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeOrderQuery(HttpServletRequest request) {
		return Transform.GetResult(stockAllocateService.storeOrderQuery(request));
	}
	/**
	 * 关闭调拨指令单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	@ResponseBody
	public Packet closeStockAllocate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = stockAllocateService.closeStockAllocate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
}
