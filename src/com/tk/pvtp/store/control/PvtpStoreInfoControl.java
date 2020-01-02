package com.tk.pvtp.store.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.pvtp.store.service.PvtpStoreInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/pvtp_store")
public class PvtpStoreInfoControl {
	@Resource
	private PvtpStoreInfoService pvtpStoreInfoService;
    
    /**
	 * 查询私有平台门店列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPvtpStoreInfolist(HttpServletRequest request) {
		return Transform.GetResult(pvtpStoreInfoService.queryPvtpStoreInfolist(request));
	}
	
}
