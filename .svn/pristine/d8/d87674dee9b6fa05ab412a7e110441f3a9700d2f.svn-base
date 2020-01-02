package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.ProductWrapperService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2018, TongKu
 * FileName : ProductWrapperDao
 * 商品包材信息维护接口访问类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/10/30
 */
@Controller
@RequestMapping("/product_wrapper")
public class ProductWrapperControl {

    @Resource
    private ProductWrapperService productWrapperService;

    /**
     * 分页查询商品包材信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductWrapperListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.productWrapperService.queryProductWrapperListForPage(request));
    }

    /**
     * 查询商品包材信息详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductWrapperDetail(HttpServletRequest request) {
        return Transform.GetResult(this.productWrapperService.queryProductWrapperDetail(request));
    }

    /**
     * 添加或者编辑商品包材信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/addOrEdit", method = RequestMethod.POST)
    @ResponseBody
    public Packet addOrEditProductWrapper(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.productWrapperService.addOrEditProductWrapper(request);
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());

        }
        return Transform.GetResult(pr);
    }

    /**
     * 删除商品包材信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeProductWrapper(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.productWrapperService.removeProductWrapper(request);
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());

        }
        return Transform.GetResult(pr);
    }
}
