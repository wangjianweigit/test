package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.AddressLibraryManageService;
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
 * Copyright (c), 2018,  TongKu
 * FileName : AddressLibraryManageControl
 * 地址库维护相关控制类
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/6/15
 */
@Controller
@RequestMapping("/address_library_manage")
public class AddressLibraryManageControl {


    @Resource
    private AddressLibraryManageService addressLibraryManageService;

    /**
     * @api {post} /{project_name}/address_library_manage/list
     * @apiName address_library_manage_list
     * @apiGroup address_library_manage
     * @apiDescription  查询省市县数据列表
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj
     * @apiSuccess {string}     obj.last_release_date   最新发布时间
     * @apiSuccess {object[]}   obj.list                省市县数据
     * @apiSuccess {number}   obj.list.id             id
     * @apiSuccess {number}   obj.list.parent_id      父级ID
     * @apiSuccess {string}   obj.list.name           名称
     * @apiSuccess {string}   obj.list.code           行政编码
     * @apiSuccess {string}   obj.list.area_code      行政区号
     * @apiSuccess {string}   obj.list.zip_code       邮政编码
     * @apiSuccess {string}   obj.list.alias_name     别名
     * @apiSuccess {number}   obj.list.sort     排序字段
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(addressLibraryManageService.queryList(request));
    }

    /**
     * @api {post} /{project_name}/address_library_manage/release
     * @apiName address_library_manage_release
     * @apiGroup address_library_manage
     * @apiDescription  发布省市县数据
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public Packet release(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = addressLibraryManageService.release(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }




    /**
     * @api {post} /{project_name}/address_library_manage/edit
     * @apiName address_library_manage_edit
     * @apiGroup address_library_manage
     * @apiDescription  批量编辑省市县数据
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj     查询省市县数据列表
     *
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet edit(HttpServletRequest request) {
        return Transform.GetResult(addressLibraryManageService.edit(request));
    }


    /**
     * @api {post} /{project_name}/address_library_manage/sort
     * @apiName address_library_manage_sort
     * @apiGroup address_library_manage
     * @apiDescription 排序省市县数据
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sort(HttpServletRequest request) {
        return Transform.GetResult(addressLibraryManageService.sort(request));
    }

    /**
     * @api {post} /{project_name}/address_library_manage/remove
     * @apiName address_library_manage_delete
     * @apiGroup address_library_manage
     * @apiDescription  单个删除省市县数据
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     *
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet delete(HttpServletRequest request) {
        return Transform.GetResult(addressLibraryManageService.remove(request));
    }
}

    
    
