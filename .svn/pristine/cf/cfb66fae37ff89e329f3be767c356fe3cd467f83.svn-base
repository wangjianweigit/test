package com.tk.oms.sys.control;

import com.tk.oms.sys.service.AppVersionService;
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
 * FileName : AppVersionControl
 * 应用程序版本管理接口访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-06-26
 */
@Controller
@RequestMapping("/app_version")
public class AppVersionControl {

    @Resource
    private AppVersionService appVersionService;

    /**
     * @api {post}/{scs_server}/app_version/page_list 分页查询App版本列表
     * @apiGroup app_version
     * @apiDescription 分页查询App版本列表
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     * @apiSucess {number} total 接口返回数据总数量
     * @apiSucess {object[]} obj 接口返回数据集合
     * @apiSucess {number} obj.ID 接口返回数据集合
     * @apiSucess {string} obj.SYSTEM APP的系统类型 <p>ios:表示苹果应用</p> <p>android：表示安卓应用</p>
     * @apiSucess {string} obj.MINIMUM_VERSION APP的最低支持版本
     * @apiSucess {number} obj.MINIMUM_VERSION_CODE APP的最低支持版本的版本code
     * @apiSucess {string} obj.LATEST_VERSION APP的最新支持版本
     * @apiSucess {number} obj.LATEST_VERSION_CODE APP最新版本code
     * @apiSucess {string} obj.APP_URL APP下载地址
     * @apiSucess {string} obj.REMARK 更新内容
     * @apiSucess {number} obj.APP_TYPE APP类型 <p>1.商家APP</p> <p>2.批发平台APP</p>
     * @apiSucess {number} obj.STATE 是否启用 <p>1.启用</p> <p>2.禁用</p>
     * @apiSucess {date} obj.CREATE_DATE 创建时间
     */
    @RequestMapping(value = "/page_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAppVersionListForPage(HttpServletRequest request) {
        return Transform.GetResult(this.appVersionService.queryAppVersionListForPage(request));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAppVersionForDetail(HttpServletRequest request) {
        return Transform.GetResult(this.appVersionService.queryAppVersionForDetail(request));
    }

    /**
     * @api {post}/{scs_server}/app_version/add 新增App版本
     * @apiGroup app_version
     * @apiDescription 新增App版本
     *
     * @apiParam {string} system APP的系统类型 <p>ios:表示苹果应用</p> <p>android：表示安卓应用</p>
     * @apiParam {string} minimum_version APP的最低支持版本
     * @apiParam {number} minimum_version_code APP的最低支持版本的版本code
     * @apiParam {string} latest_version APP的最新支持版本
     * @apiParam {number} latest_version_code APP最新版本code
     * @apiParam {string} app_url APP下载地址
     * @apiParam {string} remark 更新内容
     * @apiParam {number} app_type APP类型 <p>1.商家APP</p> <p>2.批发平台APP</p>
     * @apiParam {number} state 是否启用 <p>1.启用</p> <p>2.禁用</p>
     * @apiParam {date} create_date 创建时间
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addAppVersion(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.appVersionService.addAppVersion(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            pr.setObj(null);
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/app_version/edit 更新App版本
     * @apiGroup app_version
     * @apiDescription 更新App版本
     *
     * @apiParam {string} version_id 版本ID
     * @apiParam {string} state 是否启用 <p>1.启用</p> <p>2.禁用</p>
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editAppVersion(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.appVersionService.editAppVersion(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            pr.setObj(null);
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/app_version/edit_state 更新App版本状态
     * @apiGroup app_version
     * @apiDescription 更新App版本状态
     *
     * @apiParam {string} version_id 版本ID
     * @apiParam {string} state 是否启用 <p>1.启用</p> <p>2.禁用</p>
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editAppVersionState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.appVersionService.editAppVersionState(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            pr.setObj(null);
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    /**
     * @api {post}/{scs_server}/app_version/edit_function_state 更新App的标志位function_state
     * @apiGroup app_version
     * @apiDescription 更新App的标志位function_state
     *
     * @apiParam {string} version_id 		版本ID
     * @apiParam {string} function_state 	是否启用 <p>1.启用</p> <p>2.禁用</p>
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/edit_function_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editAppVersionFunctionState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = this.appVersionService.editAppVersionFunctionState(request);
    	} catch (Exception ex) {
    		pr.setState(false);
    		pr.setMessage(ex.getMessage());
    		pr.setObj(null);
    		ex.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/app_version/remove 删除App版本
     * @apiGroup app_version
     * @apiDescription 删除App版本
     *
     * @apiParam {string} version_id 版本ID
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeAppVersion(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.appVersionService.removeAppVersion(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            pr.setObj(null);
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
}
