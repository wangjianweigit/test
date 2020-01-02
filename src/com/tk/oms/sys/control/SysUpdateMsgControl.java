package com.tk.oms.sys.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sys.service.SysUpdateMsgService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;



@Controller
@RequestMapping("/sys_update_msg")
public class SysUpdateMsgControl {
	@Resource
	private SysUpdateMsgService sysUpdateMsgService;
	
	 /**
     * @api{post} /{oms_server}/sys_update_msg/list 系统更新消息列表
     * @apiGroup list
     * @apiName list
     * @apiDescription  系统更新消息列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 系统更新消息信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet Query_UpdateMsg_list(HttpServletRequest request) {
        return Transform.GetResult(sysUpdateMsgService.querySysUpdateMsgList(request));
    }
    /**@api{post} /{oms_server}/sys_update_msg/detail 系统更新消息详细查询
     * @apiGroup detail
     * @apiName detail
     * @apiDescription 系统更新消息详细查询
     * @apiSuccess {number}ID    
	 * @apiSuccess {string}SYSTEM_NAME    所属系统名字
	 * @apiSuccess {string}CONTEXT    消息内容
	 * @apiSuccess {string}BEGIN_TIME    生效时间
	 * @apiSuccess {string}END_TIME    失效时间
	 * @apiSuccess {string}STATE    消息状态 1（停用）2（启用）
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 系统更新消息详细信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet Query_UpdateMsg_Detail(HttpServletRequest request) {
        return Transform.GetResult(sysUpdateMsgService.querySysUpdateMsgDetail(request));
    }
    /**@api{post} /{oms_server}/sys_update_msg/edit 修改系统更新信息
     * @apiGroup edit
     * @apiName edit
     * @apiDescription  修改系统更新信息
     * @apiSuccess {number}ID    
	 * @apiSuccess {string}SYSTEM_NAME    所属系统名字
	 * @apiSuccess {string}CONTEXT    消息内容
	 * @apiSuccess {string}BEGIN_TIME    生效时间
	 * @apiSuccess {string}END_TIME    失效时间
	 * @apiSuccess {string}STATE    消息状态 1（停用）2（启用）
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 系统更新消息信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet Edit_UpdateMsg(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysUpdateMsgService.editSysUpdateMsg(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    
    /**@api{post} /{oms_server}/sys_update_msg/edit 修改系统更新状态
     * @apiGroup update_state
     * @apiName update_state
     * @apiDescription  修改系统更新状态
     * @apiSuccess {number}ID    
	 * @apiSuccess {string}STATE    消息状态 1（停用）2（启用）
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 系统更新消息信息
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateMsgState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysUpdateMsgService.updateMsgState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

}
