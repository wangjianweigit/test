package com.tk.oms.sys.control;

import com.tk.oms.sys.service.SiteDelayTemplateService;
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
 * 站点延后时间模板管理
 * @author zhenghui
 */
@Controller
@RequestMapping("/site_delay_template")
public class SiteDelayTemplateControl {

    @Resource
    private SiteDelayTemplateService siteDelayTemplateService;

    /**
     * @api {post}/{scs_server}/site_delay_template/add  模板添加
     * @apiGroup
     * @apiName site_delay_template_add
     * @apiDescription 添加站点延后时间模板
     * @apiVersion 0.0.0
     *
     * @apiParam {number} public_user_stationed_user_id 入驻商id 必填
     * @apiParam {string} templet_name 模板名称 必填
     * @apiParam {char} state 模板状态（1.停用 2.启用）必填
     * @apiParam {number} create_user_id 创建人id 必填
     * @apiParam {string} remark 备注信息
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addSiteDelayTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.siteDelayTemplateService.addSiteDelayTemplate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/site_delay_template/list
     * @apiGroup
     * @apiName site_delay_template_list
     * @apiDescription 分页查询站点延后时间列表信息
     * @apiVersion 0.0.0
     *
     * @apiParam {number} pageSize 分页大小 必填
     * @apiParam {number} pageIndex 起始页 必填
     * @apiParam {number} public_user_stationed_user_id 起始页 必填
     * @apiParam {string} templet_name 模板名称
     * @apiParam {char} state 模板状态（1.停用 2.启用）
     * @apiParam {date} start_time 创建时间的开始时间
     * @apiParam {date} end_time 创建时间的结束时间
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     * @apiSucess {object[]} obj 区域模板列表
     * @apiSucess {string} obj.list 数据集合
     * @apiSucess {string} obj.templet_name 区域模板名称
     * @apiSucess {date} obj.create_date 创建模板时间
     * @apiSucess {number} obj.default_flag 是否为默认（1.是 2.否）
     * @apiSucess {char} obj.create_user_name 创建人名称
     * @apiSucess {char} obj.user_name 用户名称
     * @apiSucess {char} obj.state 模板状态（1.停用 2.启用）
     * @apiSucess {char} obj.remark 备注信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySiteDelayTemplateForPage(HttpServletRequest request) {
        return Transform.GetResult(siteDelayTemplateService.querySiteDelayTemplateForPage(request));
    }

    /**
     * @api {post}/{scs_server}/site_delay_template/detail 获取站点延后时间模板详情
     * @apiGroup
     * @apiName site_delay_template_detail
     * @apiDescription 获取站点延后时间模板详情信息
     * @apiVersion 0.0.0
     *
     * @apiParam {number} id 模板id 必填
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     * @apiSucess {object[]} obj  站点延后模板的基本信息
     * @apiSucess {string} obj.templet_name  模板名称
     * @apiSucess {char} obj.default_flag  是否为默认
     * @apiSucess {string} obj.remark  备注信息
     * @apiSucess {char} obj.is_delete  删除状态
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySiteDelayTemplate(HttpServletRequest request) {
        return Transform.GetResult(siteDelayTemplateService.querySiteDelayTemplateDetail(request));
    }

    /**
     * @api {post}/{scs_server}/site_delay_template/edit
     * @apiGroup
     * @apiName site_delay_template_edit
     * @apiDescription 编辑站点延后时间模板基本信息
     * @apiVersion 0.0.0
     *
     * @apiParam {number} id 模板id 必填
     * @apiParam {string} templet_name 模板名称
     * @apiParam {string} remark 备注信息
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editSiteDelayTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.siteDelayTemplateService.editSiteDelayTemplate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/site_delay_template/remove
     * @apiGroup
     * @apiName site_delay_template_remove
     * @apiDescription 删除站点延后时间模板和配置延后时间
     * @apiVersion 0.0.0
     *
     * @apiParam {number} id 模板id 必填
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeSiteDelayTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.siteDelayTemplateService.removeSiteDelayTemplate(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{scs_server}/site_delay_template/site_list 站点列表
     * @apiGroup
     * @apiName site_delay_template_site_list
     * @apiDescription 获取所有站点列表
     * @apiVersion 0.0.0
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message  接口返回信息
     * @apiSucess {object[]} obj  站点列表
     * @apiSucess {List} obj.list  数据集合
     * @apiSucess {number} obj.id  站点ID
     * @apiSucess {string} obj.name  站点名称
     */
    @RequestMapping(value = "/site_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySiteInfo(HttpServletRequest request) {
        return Transform.GetResult(siteDelayTemplateService.querySiteInfo(request));
    }
    
    
    /**
    *
    * @api {post} /{project_name}/site_delay_template/site_delay_temp_list 查询入驻商可用的站点延后显示时间模板列表
    * @apiGroup common
    * @apiDescription  查询入驻商可用的站点延后显示时间模板列表
    * @apiVersion 0.0.1


    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息注释.
    * @apiSuccess {object[]} obj 模板信息集合
    * @apiSuccess {number} obj.ID								模板ID
    * @apiSuccess {number} obj.TEMPLET_NAME					模板名称
    */
   @RequestMapping(value = "/site_delay_temp_list", method = RequestMethod.POST)
   @ResponseBody
   public Packet querySiteDelayTempletList(HttpServletRequest request) {
   	return Transform.GetResult(siteDelayTemplateService.querySiteDelayTempletList(request));
   }
   /**
    *
    * @api {post} /{project_name}/site_delay_template/site_delay_temp_detail 查询站点模板详情
    * @apiGroup common
    * @apiDescription  查询站点模板详情
    * @apiVersion 0.0.1

    * @apiParam {number} templet_id 	模板ID

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息注释.
    * @apiSuccess {object[]} obj 模板信息集合
    * @apiSuccess {number} obj.TEMPLET_ID						模板ID
    * @apiSuccess {number} obj.SITE_ID							站点ID
    * @apiSuccess {number} obj.DELAY_DAYS						站延后时间
    *
    */
   @RequestMapping(value = "/site_delay_temp_detail", method = RequestMethod.POST)
   @ResponseBody
   public Packet querySiteDelayTempletDetail(HttpServletRequest request) {
   	return Transform.GetResult(siteDelayTemplateService.querySiteDelayTempletDetail(request));
   }

}
