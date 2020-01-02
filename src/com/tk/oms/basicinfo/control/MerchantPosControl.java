package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.MerchantPosService;
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
 * Copyright (c), 2017, Tongku
 * FileName : MerchantPosControl
 * 商业pos设备管理Control
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/5 10:44
 */
@Controller
@RequestMapping("/merchant_pos")
public class MerchantPosControl {

    @Resource
    private MerchantPosService merchantPosService;  //商业pos设备管理业务操作类

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/merchant_pos/list 查询商业Pos设备列表接口
     * @apiGroup merchant_pos
     * @apiName merchant_pos_list
     * @apiDescription                    查询商业Pos设备列表接口
     * @apiSuccess {object} obj                 商业Pos设备列表
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(merchantPosService.queryList(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/merchant_pos/list 查询商业Pos设备信息接口
     * @apiGroup merchant_pos
     * @apiName merchant_pos_list
     * @apiDescription                    查询商业Pos设备信息接口
     * @apiSuccess {object} obj                 商业Pos设备列信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMerchantPosInfo(HttpServletRequest request) {
        return Transform.GetResult(merchantPosService.queryMerchantPosInfo(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/merchant_pos/add 新增商业Pos设备接口
     * @apiGroup merchant_pos
     * @apiName merchant_pos_add
     * @apiDescription                     新增商业Pos设备接口
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 新增商业Pos设备信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMerchantPos(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try{
            return Transform.GetResult(merchantPosService.addMerchantPos(request));
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    /**
     * @api {post} /{project_name}/merchant_pos/edit 更新商业Pos设备接口
     * @apiGroup merchant_pos
     * @apiName merchant_pos_edit
     * @apiDescription                    更新商业Pos设备接口
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 更新商业Pos设备信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMerchantPos(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = merchantPosService.editMerchantPos(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/merchant_pos/delete 删除商业Pos设备接口
     * @apiGroup merchant_pos
     * @apiName merchant_pos_delete
     * @apiDescription                  删除商业Pos设备接口
     * @apiVersion 0.0.1
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeMerchantPos(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = merchantPosService.removeMerchantPos(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

}
