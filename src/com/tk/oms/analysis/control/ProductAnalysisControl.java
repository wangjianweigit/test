package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.ProductAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/product_analysis")
public class ProductAnalysisControl {
	
	@Resource
	private ProductAnalysisService productAnalysisService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet list(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = productAnalysisService.queryProductAnalysisList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	@RequestMapping(value = "/chart_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberChartList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = productAnalysisService.queryProductChartList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}

}
