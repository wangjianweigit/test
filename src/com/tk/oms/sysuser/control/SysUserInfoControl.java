package com.tk.oms.sysuser.control;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sysuser.service.SysUserInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
* Copyright (c), 2016, Tongku
* FileName : LoginControl.java
* ERP用户管理
* @author  wangpeng
* @date 2016-06-07
* @version1.00
*/
@Controller
@RequestMapping("/sys_user")
public class SysUserInfoControl {

    @Resource
    private SysUserInfoService sysUserInfoService;
    /**
     * 管理系统用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Packet login(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.sysUserInfoLogin(request));
    }
    
    /**
     *新增管理用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet add(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.addSysUserInfo(request));
    }
    /**
     * 禁用启用管理用户
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet update_state(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.updateStateSysUserInfo(request));
    }
    /**
     * 管理用户密码重置
     */
    @RequestMapping(value = "/update_pwd", method = RequestMethod.POST)
    @ResponseBody
    public Packet update_pwd(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.updatePwdSysUserInfo(request));
    }
    /**
     *修改管理用户
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Packet update(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = sysUserInfoService.updateSysUserInfo(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
    /**
     *管理用户列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet list(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.querySysUserInfoList(request));
    }

    /**
     *管理用户详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet detail(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.querySysUserInfoDetail(request));
    }
    /**
     *用户可用站点列表
     */
    @RequestMapping(value = "/usersitelist", method = RequestMethod.POST)
    @ResponseBody
    public Packet usersitelist(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.queryUserSiteList(request));
    }
    /**
     *业务员或门店人员授权登录
     */
    @RequestMapping(value = "/accredit_login", method = RequestMethod.POST)
    @ResponseBody
    public Packet accreditLogin(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.accreditLogin(request));
    }
    /**
	 * 
	 * @api{post} /{oms_server}/sys_user/type_list 获取业务员或者业务经理
     * @apiGroup type_list
     * @apiName type_list
     * @apiDescription  获取业务员或者业务经理
     * @apiVersion 0.0.1
     * @apiSuccess {number} user_type 用户类型(3 业务员 4业务经理)
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 获取业务员或者业务经理
     * @apiSuccess{number} total 总条数
	 */
	@RequestMapping(value = "/type_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet type_list(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.typeList(request));
	}
	
	/**
	 * 
	 * @api{post} /{oms_server}/sys_user/select_ywjl_list 获取【通用下拉框】查询【业务经理】下拉
     * @apiGroup select_list
     * @apiName select_ywjl_list
     * @apiDescription  【通用下拉框】查询【业务经理】下拉
     * @apiVersion 0.0.1
     * @apiSuccess {number} public_user_type 登录用户类型
     * @apiSuccess {number} public_user_id   登录用户ID
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 权限内业务经理列表
	 */
	@RequestMapping(value = "/select_ywjl_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysUserYwjlList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.querySysUserYwjlList(request));
	}
	
	/**
	 * 
	 * @api{post} /{oms_server}/sys_user/select_md_list 获取【通用下拉框】查询【门店】下拉
     * @apiGroup select_list
     * @apiName select_md_list
     * @apiDescription  【通用下拉框】查询【门店】下拉
     * @apiVersion 0.0.1
     * @apiSuccess {number} public_user_type 登录用户类型
     * @apiSuccess {number} public_user_id   登录用户ID
     * @apiSuccess {number} page_select_ywjl_id  上级业务经理ID
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 权限内门店列表
	 */
	@RequestMapping(value = "/select_md_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysStoreList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.querySysStoreList(request));
	}
	
	/**
	 * 
	 * @api{post} /{oms_server}/sys_user/select_ywy_list 获取【通用下拉框】查询【业务人员】下拉
     * @apiGroup select_list
     * @apiName select_ywy_list
     * @apiDescription  【通用下拉框】查询【业务人员】下拉
     * @apiVersion 0.0.1
     * @apiSuccess {number} public_user_type 登录用户类型
     * @apiSuccess {number} public_user_id   登录用户ID
     * @apiSuccess {number} page_select_md_id  上级门店ID
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 权限内业务员列表
	 */
	@RequestMapping(value = "/select_ywy_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysUserYwyList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.querySysUserYwyList(request));
	}
	
	/**
	 * 获取当前业务经理 、当前门店下业务员、业务经理、店长、营业员数据
	 */
	@RequestMapping(value = "/select_user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysUserList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.querySysUserList(request));
	}
	
	/**
     * 查询用户默认登录验证码
     */
    @RequestMapping(value = "/login_veriyf_code", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLoginVerifyCode(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.queryLoginVerifyCode(request));
    }
    
    /**
     * 系统用户ip设置
     */
    @RequestMapping(value = "/ip_set", method = RequestMethod.POST)
    @ResponseBody
    public Packet userIpSet(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.userIpSet(request));
    }
    /**
     * ip白名单列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/white_id_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet whiteIpList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysUserInfoService.whiteIpList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }


    /**
     * 解锁
     * @param request
     * @return
     */
    @RequestMapping(value = "/un_lock", method = RequestMethod.POST)
    @ResponseBody
    public Packet unLock(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = sysUserInfoService.unLock(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    
    /**
	 * 查询指定门店下的【业务员、业务经理、店长、营业员】
	 */
	@RequestMapping(value = "/query_salers_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySalersList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.querySalersList(request));
	}
	
	/**
	 * 按用户类型查询指定用户列表
	 */
	@RequestMapping(value = "/query_user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.queryUserList(request));
	}
    /**
     * 自定义创建系统用户
     */
    @RequestMapping(value = "/custom_create", method = RequestMethod.POST)
    @ResponseBody
    public Packet customerCreateUserAccount(HttpServletRequest request) {
        return Transform.GetResult(sysUserInfoService.customerCreateUserAccount(request));
    }
    /**
	 * 查询推介人列表
	 */
	@RequestMapping(value = "/query_ground_push_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryGroundPushList(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.queryGroundPushList(request));
	}
	
	/**
     * 设置会员账号预审是否全部查看
     * @param request
     * @return
     */
    @RequestMapping(value = "/update_account_approval_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateAccountApprovalState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = sysUserInfoService.updateAccountApprovalState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * 业务员下拉框(单独)
     * @param request
     * @return
     */
    @RequestMapping(value = "/ywy_option", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySysUserYwyOption(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.querySysUserYwyOption(request));
	}
    
    /**
     * 获取用户类型(提供给OA调用)
     * @param request
     * @return
     */
    @RequestMapping(value = "/getSysUserType", method = RequestMethod.POST)
	@ResponseBody
	public Packet getSysUserType(HttpServletRequest request) {
		return Transform.GetResult(sysUserInfoService.getSysUserType(request));
	}
    
}
