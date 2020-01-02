package com.tk.oms.sys.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sys.service.SysConfigService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/sys_config")
public class SysConfigControl {
	
	@Resource
    private SysConfigService sysConfigService;
	
	/**
     * @api{post} /{oms_server}/sysManager/sys_config_list 系统配置查询
     * @apiGroup querySysConfigList
     * @apiName sys_config_list
     * @apiDescription  分页查询系统配置查询
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 品牌信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySysConfigList(HttpServletRequest request) {
        return Transform.GetResult(sysConfigService.querySysConfigList(request));
    }
    
    /**
     * @api{post} /{oms_server}/sysManager/sys_config_add 系统配置新增
     * @apiGroup addSysConfig
     * @apiName sys_config_add
     * @apiDescription  新增系统配置信息
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{number} member_service_rate 会员服务费率（格式如：90%则存储的值为0.9）
     * @apiParam{date} create_date 创建时间
     * @apiParam{number} create_user_id 创建人id）
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 操作记录数
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addSysConfig(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysConfigService.addSysConfig(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/sysManager/sys_config_edit 系统配置修改
     * @apiGroup editSysConfig
     * @apiName sys_config_edit
     * @apiDescription  系统配置修改
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{number} member_service_rate 会员服务费率（格式如：90%则存储的值为0.9）
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 操作记录数
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editSysConfig(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysConfigService.editSysConfig(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/sysManager/sys_config_remove 删除系统配置
     * @apiGroup removeSysConfig
     * @apiName sys_config_remove
     * @apiDescription 删除系统配置
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 操作记录数
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeSysConfig(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysConfigService.removeSysConfig(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    


}
