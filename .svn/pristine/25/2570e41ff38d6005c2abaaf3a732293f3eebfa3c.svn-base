package com.tk.oms.decoration.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.decoration.service.DecorateDataService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 装修预览数据
 * Created by wangpeng on 2017/5/23 0001.
 */
@Controller
@RequestMapping("/decorate_data")
public class DecorateDataControl {

    @Resource
    private DecorateDataService decorateDataService;

    /**
     * 获取站点导航列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/nav_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNavList(HttpServletRequest request) {
        return Transform.GetResult(decorateDataService.queryNavList(request));
    }
    
    /**
     * 通过页面查询对应数据源
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/module_data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryModuleByPagemoduleId(HttpServletRequest request) {
        return Transform.GetResult(decorateDataService.queryModuleByPagemoduleId(request));
    }
}
