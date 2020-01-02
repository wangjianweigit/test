package com.tk.store.marking.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.marking.service.CouponsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/coupons")
public class CouponsControl {
	
	@Resource
    private CouponsService couponsService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryCouponsList(HttpServletRequest request) {
		return Transform.GetResult(couponsService.queryCouponsList(request));
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsAdd(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = couponsService.couponsAdd(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsEdit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = couponsService.couponsEdit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsDetail(HttpServletRequest request) {
		return Transform.GetResult(couponsService.couponsDetail(request));
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsDelete(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = couponsService.couponsDelete(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	@RequestMapping(value = "/update_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsUpdateState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = couponsService.couponsUpdateState(request);
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
		return Transform.GetResult(couponsService.queryProductLibraryListForPage(request));
	}
	
	/**
	 * 获取门店库
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_library", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreLibraryListForPage(HttpServletRequest request) {
		return Transform.GetResult(couponsService.queryStoreLibraryListForPage(request));
	}
	
	/**
	 * 优惠券流量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/flow", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsFlow(HttpServletRequest request) {
		return Transform.GetResult(couponsService.couponsFlow(request));
	}
	
	/**
	 * 优惠券使用详情列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsDetailList(HttpServletRequest request) {
		return Transform.GetResult(couponsService.couponsDetailList(request));
	}
	
	/**
	 * 增加发行量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add_count", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsAddCount(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = couponsService.couponsAddCount(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 下载二维码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/download_code", method = RequestMethod.POST)
	@ResponseBody
	public Packet couponsDownloadCode(HttpServletRequest request) {
		return Transform.GetResult(couponsService.couponsDownloadCode(request));
	}
}
