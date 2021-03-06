package com.tk.pvtp.member.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tk.pvtp.member.service.PvtpMemberInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/pvtp_member")
public class PvtpMemberInfoControl {
	@Resource
	private PvtpMemberInfoService pvtpMemberInfoService;
	
	/**
	 * 业务员、门店、业务经理--下属会员列表[代用户下单]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/subsidiary_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySubsidiaryMemberlist(HttpServletRequest request) {
		return Transform.GetResult(pvtpMemberInfoService.querySubsidiaryMemberList(request));
	}
	
	/**
     *业务员或门店人员授权登录
     */
    @RequestMapping(value = "/accredit_login", method = RequestMethod.POST)
    @ResponseBody
    public Packet accreditLogin(HttpServletRequest request) {
        return Transform.GetResult(pvtpMemberInfoService.accreditLogin(request));
    }
    
    /**
	 * 查询私有站会员列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPvtpMemberlist(HttpServletRequest request) {
		return Transform.GetResult(pvtpMemberInfoService.queryPvtpMemberlist(request));
	}
	
	/**
	 * 查询私有站会员详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryPvtpMemberDetail(HttpServletRequest request) {
		return Transform.GetResult(pvtpMemberInfoService.queryPvtpMemberDetail(request));
	}
	/**
	 * 查询私有站的业务员、业务经理 列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/business/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationUserList(HttpServletRequest request) {
		return Transform.GetResult(pvtpMemberInfoService.queryStationUserList(request));
	}
	/**
	 * 查询某个私有站业务经理下属的私有站门店列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStationListByManager(HttpServletRequest request) {
		return Transform.GetResult(pvtpMemberInfoService.queryStationListByManager(request));
	}
	/**
	 * 私有会员收支记录查询
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/revenue_record_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserAccountRecordList(HttpServletRequest request) {
        return Transform.GetResult(pvtpMemberInfoService.queryPvtpMemberRevenueRecordLit(request));
    }
    
    /**
     * 私有会员收支记录详情查询
     * @param request
     * @return
     */
   	@RequestMapping(value = "/revenue_record_detail", method = RequestMethod.POST)
   	@ResponseBody
   	public Packet queryUserAccountRecordDetail(HttpServletRequest request) {
   		return Transform.GetResult(pvtpMemberInfoService.queryPvtpMemberRevenueRecordDetail(request));
   	}
}
