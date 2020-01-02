package com.tk.store.marking.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.marking.service.StoreActivityService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : StoreActivityControl.java
 * 营销活动 control层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月30日
 */
@Controller
@RequestMapping("/store_activity")
public class StoreActivityControl {
	@Resource
	private StoreActivityService storeActivityService;
	
	/**
	 * 活动列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryActivityListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryActivityListForPage(request));
	}
	
	/**
	 * 是否启用
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/state", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateActivityState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.updateActivityState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 删除活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet deleteActivityInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.deleteActivityInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 获取商品库
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_library", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryProductLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryProductLibraryListForPage(request));
	}
	
	/**
	 * 获取自营门店库
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/zy_store_library", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryZyStoreLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryZyStoreLibraryListForPage(request));
	}
	
	/**
	 * 获取门店库
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_library", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryStoreLibraryListForPage(request));
	}
	
	/**
	 * 新增活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addActivity(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.addActivity(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 活动编辑详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreActivityEditDetail(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryStoreActivityEditDetail(request));
	}
	
	/**
	 * 修改活动时间
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update_date", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateActivityDate(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.updateActivityDate(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 查询活动商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_product_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryActivityProductList(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryActivityProductList(request));
	}
	
	/**
	 * 活动审核列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approval_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryActivityApprovalListForPage(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryActivityApprovalListForPage(request));
	}
	
	/**
	 * 活动详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryActivityDetail(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryActivityDetail(request));
	}
	
	/**
	 * 审批
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Packet check(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.check(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 撤出活动商品(支持单个和多个商品撤出)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/remove_product", method = RequestMethod.POST)
	@ResponseBody
	public Packet removeProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.removeProduct(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 商品追加
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/append_product", method = RequestMethod.POST)
	@ResponseBody
	public Packet appendProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.appendProduct(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 编辑活动
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editdActivity(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.editActivity(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 审批商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/approval_product", method = RequestMethod.POST)
	@ResponseBody
	public Packet approvalProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeActivityService.approvalProduct(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 查询指定商家下拉数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query_user_option", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserOption(HttpServletRequest request) {
		return Transform.GetResult(storeActivityService.queryUserOption(request));
	}
	
}
