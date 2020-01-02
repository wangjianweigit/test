package com.tk.oms.decoration.control;

import com.tk.oms.decoration.service.DecorateTemplateService;
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
 * 平台装修管理
 * @author zhenghui
 */
@Controller
@RequestMapping("/decorate_template")
public class DecorateTemplateControl {

    @Resource
    private DecorateTemplateService decorateTemplateService;

    /**
     * @api {post} /{project_name}/decorate_template/list 分页获取装修模板列表
     * @apiGroup decorate_template
     * @apiDescription  分页获取装修模板列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   pageIndex 起始页
     * @apiParam {number}   pageSize 分页大小
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 节点id
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDecorateTemplateListForPage(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.queryDecorateTemplateListForPage(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/add 添加装修模板
     * @apiGroup decorate_template
     * @apiDescription  添加装修模板
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 装修模板ID
     *
     * @apiSuccess {boolean}   state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addDecorateTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.decorateTemplateService.addDecorateTemplate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/decorate_template/edit 编辑装修模板
     * @apiGroup decorate_template
     * @apiDescription  编辑装修模板
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 装修模板ID
     *
     * @apiSuccess {boolean}   state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editDecorateTemplate(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.editDecorateTemplate(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/detail   装修模板详情
     * @apiGroup decorate_template
     * @apiDescription  装修模板详情
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 装修模板ID
     *
     * @apiSuccess {boolean}   state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message 接口返回信息
     * @apiSuccess {object[]}  obj 装修模板详细信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDecorateTemplate(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.queryDecorateTemplate(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/remove 删除装修模板
     * @apiGroup decorate_template
     * @apiDescription  删除装修模板
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id 装修模板ID
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeDecorateTemplate(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.removeDecorateTemplate(request));
    }


    /**
     * 装修模板传送
     * @param request
     * @return
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ResponseBody
    public Packet decorateTemplateTransfer(HttpServletRequest request) {
        return Transform.GetResult(this.decorateTemplateService.decorateTemplateTransfer(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/app_ico_config_list 分页获取app模板配置列表
     * @apiGroup decorate_template
     * @apiDescription  分页获取app模板配置列表
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   pageIndex 起始页
     * @apiParam {number}   pageSize 分页大小
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.effect_state 生效状态： 1、未开始 2、进行中 3、已过期
     */
    @RequestMapping(value = "/app_ico_config_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet listAppIcoTemplateConfig(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.listAppIcoTemplateConfig(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/info 获取app模板配置数据
     * @apiGroup decorate_template
     * @apiDescription  获取app模板配置数据
     * @apiVersion 0.0.1
     * @apiParam {number}   id
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object}   obj
     */
    @RequestMapping(value = "/app_ico_config_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet getAppIcoTemplateConfigInfo(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.getAppIcoTemplateConfigInfo(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/app_ico_config_edit 编辑app模板配置信息
     * @apiGroup decorate_template
     * @apiDescription  编辑app模板配置信息
     * @apiVersion 0.0.1
     * @apiParam {number}   id
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/app_ico_config_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editAppIcoTemplateConfigInfo(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.editAppIcoTemplateConfigInfo(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/app_ico_config_delete 删除app模板配置信息
     * @apiGroup decorate_template
     * @apiDescription  删除app模板配置信息
     * @apiVersion 0.0.1
     * @apiParam {number}   id
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/app_ico_config_delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteAppIcoTemplateConfigInfo(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.deleteAppIcoTemplateConfigInfo(request));
    }

    /**
     * @api {post} /{project_name}/decorate_template/app_ico_config_update_state 更新app模板配置启用状态
     * @apiGroup decorate_template
     * @apiDescription  更新app模板配置启用状态
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id
     * @apiParam {number}   state 启用状态：1、启用 2、禁用
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 节点id
     */
    @RequestMapping(value = "/app_ico_config_update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateAppIcoTemplateConfigState(HttpServletRequest request){
        return Transform.GetResult(this.decorateTemplateService.updateAppIcoTemplateConfigState(request));
    }
}
