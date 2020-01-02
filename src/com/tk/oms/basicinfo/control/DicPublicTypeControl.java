package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.basicinfo.service.DicPublicTypeService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : DicPublicTypeControl.java
 * 经营类型
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月3日
 */
@Controller
@RequestMapping("/dic_public_type")
public class DicPublicTypeControl {
	@Resource
	private DicPublicTypeService dicPublicTypeService;
	/**
	 * 经营类型列表（下拉框）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDicPublicTypeOption(HttpServletRequest request) {
        return Transform.GetResult(dicPublicTypeService.queryDicPublicTypeOption(request));
    }
}
