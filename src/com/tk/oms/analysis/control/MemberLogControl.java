package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.MemberLogService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/member_log")
public class MemberLogControl {
	@Resource
	private MemberLogService memberLogService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet list(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberLogService.queryMemberLogList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}

    /**
     * 查询日志详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(memberLogService.queryDetail(request));
    }


}
