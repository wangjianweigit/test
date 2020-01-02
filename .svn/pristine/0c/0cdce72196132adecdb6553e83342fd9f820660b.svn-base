package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.GroundPushDataStatisticsService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/ground_push_statistics")
public class GroundPushDataStatisticsControl {
	
	 @Resource
	 private GroundPushDataStatisticsService groundPushDataStatisticsService;
	
	 /**
	 * 业务人员地推数据查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ywry_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryYwryList(HttpServletRequest request) {
        return Transform.GetResult(groundPushDataStatisticsService.queryYwryList(request));
    }
	
	/**
	 * 推荐人地推数据查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tjr_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTjrList(HttpServletRequest request) {
        return Transform.GetResult(groundPushDataStatisticsService.queryTjrList(request));
    }

}
