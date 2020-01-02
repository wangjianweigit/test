package com.tk.oms.analysis.control;

import com.tk.oms.analysis.service.ChannelHeatService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 频道热度分析
 * @author zhenghui
 */
@Controller
@RequestMapping("channel_heat")
public class ChannelHeatControl {

    @Resource
    private ChannelHeatService channelHeatService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLogsNumberByPageType(HttpServletRequest request){
        return Transform.GetResult(this.channelHeatService.queryLogsNumberByPageType(request));
    }
}