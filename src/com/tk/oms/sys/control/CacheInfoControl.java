package com.tk.oms.sys.control;

import com.tk.oms.sys.service.CacheInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, Tongku
 * FileName : CacheInfoControl
 * 缓存信息接口
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/7/19
 */
@Controller
@RequestMapping("/cache_info")
public class CacheInfoControl {

    @Resource
    private CacheInfoService cacheInfoService;


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCacheInfoListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.cacheInfoService.queryCacheInfoListForPage(request));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateCacheInfo(HttpServletRequest request) {
        return Transform.GetResult(this.cacheInfoService.updateCacheInfo(request));
    }
}
