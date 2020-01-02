package com.tk.oms.member.control;

import com.tk.oms.member.service.MemberInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("member")
public class MemberInfoControl {
	
	@Resource
	private MemberInfoService memberInfoService;
	
	/**   @api{post} /{oms_server}/member/add 会员新增
        * @apiGroup add
        * @apiName add
        * @apiDescription 会员新增
        * @apiVersion 0.0.1
	    * @apiSuccess {number}ID    主键
	    * @apiSuccess {string}USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
	    * @apiSuccess {string}LOGIN_NAME    登录名称，用于登录
	    * @apiSuccess {string}LOGIN_PASSWORD    用户登录密码，加密数据
	    * @apiSuccess {number}USER_STATE    用户审核状态-1.非注册会员  0.待审核 1.审核通过 2.禁用 3.审核不通过
	    * @apiSuccess {string}USER_RESOURCE    会员来源    1:自行注册   2:后台注册
	    * @apiSuccess {number}CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}CREATE_DATE    创建时间
	    * @apiSuccess {number}EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}EDIT_DATE    编辑时间
	    * @apiSuccess {number}APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}APPROVAL_DATE    审核时间
	    * @apiSuccess {string}USER_CREATE_IP    用户注册IP
	    * @apiSuccess {number}USER_LOGIN_COUNT    用户登录次数
	    * @apiSuccess {string}USER_LAST_LOGIN_DATE    用户最后登录时间
	    * @apiSuccess {string}USER_LAST_LOGIN_IP    用户最后登录IP
	    * @apiSuccess {string}USER_HEAD_IMGURL    用户图像
	    * @apiSuccess {string}USER_MANAGE_NAME    负责人姓名
	    * @apiSuccess {string}USER_MANAGE_SEX    负责人性别
	    * @apiSuccess {string}USER_MANAGE_CARDID    负责人身份证号码
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE1    身份证正面附件路径
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE2    身份证反面附件路径
	    * @apiSuccess {string}USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
	    * @apiSuccess {string}USER_MANAGE_TELEPHONE    负责人电话
	    * @apiSuccess {string}USER_MANAGE_MOBILEPHONE    负责人手机
	    * @apiSuccess {string}USER_MANAGE_WEIXIN    负责人微信
	    * @apiSuccess {string}USER_MANAGE_QQ    负责人QQ
	    * @apiSuccess {string}USER_MANAGE_EMAIL    负责人邮箱
	    * @apiSuccess {string}USER_COMPANY_NAME    公司名称
	    * @apiSuccess {string}USER_COMPANY_CORPORATION    公司法人
	    * @apiSuccess {string}USER_COMPANY_TELEPHONE    公司电话
	    * @apiSuccess {number}USER_COMPANY_TYPE    公司经营类型（代码）
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_CITY    公司所在地城市id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
	    * @apiSuccess {string}USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
	    * @apiSuccess {string}USER_COMPANY_COMMENT    公司简介
	    * @apiSuccess {number}REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}STORE_ID    门店ID，表TBL_STORE_INFO主键
	    * @apiSuccess {string}STORE_NAME    门店名称，冗余字段，节约查询时间
	    * @apiSuccess {string}OPENID    微信的OPENID
	    * @apiSuccess {string}LAST_UPDATE_TIME    最后更新时间--【同步用】
	    * @apiSuccess {number}DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
	    * @apiSuccess {number}ISSUING_GRADE_ID    代发等级ID
	    * @apiSuccess {string}USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
	    * @apiSuccess {string}SHOP_WEBSITE    店铺网址
	    * @apiSuccess {string}SHOP_NAME    店铺名称
	    * @apiSuccess {string}PLATFORM    所属平台
	    * @apiSuccess {string}MAIN_CATEGORY    主营类目
	    * @apiSuccess {string}SHOP_PHOTO    店铺照片
       * @apiSuccess {string}UNBUNDING_DATE    上次解绑时间
	    * @apiSuccess {number}SITE_ID    站点ID
	   * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
       * @apiSuccess{string} message 接口返回信息
       * @apiSuccess{object} obj 新增会员信息
	   * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet add(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.add(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
     * @api{post} /{oms_server}/member/reject 审批回驳
     * @apiGroup reject
     * @apiName reject
     * @apiDescription  审批回驳
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiParam{number} [user_state] 用户状态
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 审批回驳
     */
	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	@ResponseBody
	public Packet reject(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.reject(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
     * @api{post} /{oms_server}/member/approval_list 审批列表列表
     * @apiGroup approval_list
     * @apiName approval_list
     * @apiDescription  审批列表列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 审批列表列表
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/approval_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet approvalList(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.approvalList(request));
	}
	
	/**
     * @api{post} /{oms_server}/member/approval_list 会员管理列表
     * @apiGroup manager_list
     * @apiName manager_list
     * @apiDescription  会员管理列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 会员管理列表
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/manage_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet manageList(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.manageList(request));
	}
	
	/**
     * @api{post} /{oms_server}/member/manager_list 查看会员详情列表(点击查看查询)
     * @apiGroup manager_list
     * @apiName manager_list
     * @apiDescription  查看会员详情列表(点击查看查询)
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 查看会员详情列表
     */
	@RequestMapping(value = "/detail_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryMemberDetailList(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.queryMemberDetailList(request));
	}
	
	
	/**
     * @api{post} /{oms_server}/member/approval_status_pause 暂不审核
     * @apiGroup approval_status_pause
     * @apiName approval_status_pause
     * @apiDescription  暂不审核
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiParam{number} [user_state] 用户状态
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 暂不审核
     * @apiSuccess{number} total 总条数
     */
//    @RequestMapping(value = "/approval_status_pause", method = RequestMethod.POST)
//	@ResponseBody
//	public Packet updateUserStateToPause(HttpServletRequest request) {
//		return Transform.GetResult(memberInfoService.updateUserStateToPause(request));
//	}
    
    

	/**
     * @api{post} /{oms_server}/member/find 根据用户名获取用户信息用于展现用户详情(点击编辑查询)
     * @apiGroup find
     * @apiName find
     * @apiDescription  根据用户名获取用户信息用于展现用户详情(点击编辑查询)
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 根据用户名获取用户信息用于展现用户详情(点击编辑查询)
     */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserInfo(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.queryMemberInfoByUserName(request));
	}
	
	/**
     * @api{post} /{oms_server}/member/find_apply 待审批查看会员详情
     * @apiGroup find_apply
     * @apiName find_apply
     * @apiDescription  待审批查看会员详情
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 待审批查看会员详情
     */
	@RequestMapping(value = "/find_apply", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserInfoApply(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.queryMemberInfoRecordById(request));
	}
	
	
	/**   @api{post} /{oms_server}/member/approval 审核会员
     * @apiGroup approval
     * @apiName approval
     * @apiDescription  审核会员
     * @apiVersion 0.0.1
	    * @apiSuccess {number}ID    主键
	    * @apiSuccess {string}USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
	    * @apiSuccess {string}LOGIN_NAME    登录名称，用于登录
	    * @apiSuccess {string}LOGIN_PASSWORD    用户登录密码，加密数据
	    * @apiSuccess {number}USER_STATE    用户审核状态-1.非注册会员  0.待审核 1.审核通过 2.禁用 3.审核不通过
	    * @apiSuccess {string}USER_RESOURCE    会员来源    1:自行注册   2:后台注册
	    * @apiSuccess {number}CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}CREATE_DATE    创建时间
	    * @apiSuccess {number}EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}EDIT_DATE    编辑时间
	    * @apiSuccess {number}APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}APPROVAL_DATE    审核时间
	    * @apiSuccess {string}USER_CREATE_IP    用户注册IP
	    * @apiSuccess {number}USER_LOGIN_COUNT    用户登录次数
	    * @apiSuccess {string}USER_LAST_LOGIN_DATE    用户最后登录时间
	    * @apiSuccess {string}USER_LAST_LOGIN_IP    用户最后登录IP
	    * @apiSuccess {string}USER_HEAD_IMGURL    用户图像
	    * @apiSuccess {string}USER_MANAGE_NAME    负责人姓名
	    * @apiSuccess {string}USER_MANAGE_SEX    负责人性别
	    * @apiSuccess {string}USER_MANAGE_CARDID    负责人身份证号码
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE1    身份证正面附件路径
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE2    身份证反面附件路径
	    * @apiSuccess {string}USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
	    * @apiSuccess {string}USER_MANAGE_TELEPHONE    负责人电话
	    * @apiSuccess {string}USER_MANAGE_MOBILEPHONE    负责人手机
	    * @apiSuccess {string}USER_MANAGE_WEIXIN    负责人微信
	    * @apiSuccess {string}USER_MANAGE_QQ    负责人QQ
	    * @apiSuccess {string}USER_MANAGE_EMAIL    负责人邮箱
	    * @apiSuccess {string}USER_COMPANY_NAME    公司名称
	    * @apiSuccess {string}USER_COMPANY_CORPORATION    公司法人
	    * @apiSuccess {string}USER_COMPANY_TELEPHONE    公司电话
	    * @apiSuccess {number}USER_COMPANY_TYPE    公司经营类型（代码）
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_CITY    公司所在地城市id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
	    * @apiSuccess {string}USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
	    * @apiSuccess {string}USER_COMPANY_COMMENT    公司简介
	    * @apiSuccess {number}REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}STORE_ID    门店ID，表TBL_STORE_INFO主键
	    * @apiSuccess {string}STORE_NAME    门店名称，冗余字段，节约查询时间
	    * @apiSuccess {string}OPENID    微信的OPENID
	    * @apiSuccess {string}LAST_UPDATE_TIME    最后更新时间--【同步用】
	    * @apiSuccess {number}DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
	    * @apiSuccess {number}ISSUING_GRADE_ID    代发等级ID
	    * @apiSuccess {string}USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
	    * @apiSuccess {string}SHOP_WEBSITE    店铺网址
	    * @apiSuccess {string}SHOP_NAME    店铺名称
	    * @apiSuccess {string}PLATFORM    所属平台
	    * @apiSuccess {string}MAIN_CATEGORY    主营类目
	    * @apiSuccess {string}SHOP_PHOTO    店铺照片
        * @apiSuccess {string}UNBUNDING_DATE    上次解绑时间
	    * @apiSuccess {number}SITE_ID    站点ID
	   * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
       * @apiSuccess{string} message 接口返回信息
       * @apiSuccess{object} obj 审核会员信息
	   * 
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet approvalUserInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.approvalUserInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	
	/**
	 * /**
     * @api{post} /{oms_server}/member/disable 启用或禁用用户信息
     * @apiGroup disable
     * @apiName disable
     * @apiDescription  启用或禁用用户信息
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiParam{number} user_state 用户状态
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public Packet removeUser(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.disableUserInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
		//return Transform.GetResult(memberInfoService.disableUserInfo(request));
	}

	/**
	 * /**
	 * /**
     * @api{post} /{oms_server}/member/multi_disable 批量启用或禁用用户信息
     * @apiGroup multi_disable
     * @apiName multi_disable
     * @apiDescription  批量启用或禁用用户信息
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiParam{number} user_state 用户状态
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{number} total 总条数
     */
	 
	@RequestMapping(value = "/multi_disable", method = RequestMethod.POST)
	@ResponseBody
	public Packet multiDisable(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.multiDisable(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	
	/**
	 * /**
	 * /**
     * @api{post} /{oms_server}/member/update_score 更新会员积分
     * @apiGroup update_score
     * @apiName update_score
     * @apiDescription  更新会员积分
     * @apiVersion 0.0.1
     * @apiParam{number} user_id 用户id
     * @apiParam{number} score 积分
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 更新会员积分信息
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/update_score", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateUserAccountScore(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.updateBankAccountScore(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}

	/**
	 * /**
	 * /**
     * @api{post} /{oms_server}/member/query_score 查询会员积分
     * @apiGroup query_score
     * @apiName query_score
     * @apiDescription  查询会员积分
     * @apiVersion 0.0.1
     * @apiParam{number} user_id 用户id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 查询会员积分
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/query_score", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryScore(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.queryMemberScore(request));
	}
	/**
	 * /**
	 * /**
     * @api{post} /{oms_server}/member/query_issuing_grade 查询会员代发等级
     * @apiGroup query_issuing_grade
     * @apiName query_issuing_grade
     * @apiDescription  查询会员代发等级
     * @apiVersion 0.0.1
     * @apiParam{number} user_name 用户名
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 设置代发会员等级信息
     */
	@RequestMapping(value = "/query_issuing_grade", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryMemberIssuingGrade(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.queryMemberIssuingGrade(request));
	}
	/**
	 * /**
	 * /**
     * @api{post} /{oms_server}/member/update_issuing_grade 设置代发会员等级
     * @apiGroup update_issuing_grade
     * @apiName update_issuing_grade
     * @apiDescription  设置代发会员等级
     * @apiVersion 0.0.1
     * @apiParam{number} id 用户id
     * @apiParam{number} issuing_grade_id 代发等级id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 设置代发会员等级信息
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/update_issuing_grade", method = RequestMethod.POST)
	@ResponseBody
	public Packet setIssuingGrade(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.updateUserIssuingGrade(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 
	 * @api{post} /{oms_server}/member/user_agent_add 新增平台会员申请成为经销商申请记录
     * @apiGroup user_agent_add
     * @apiName user_agent_add
     * @apiDescription  新增平台会员申请成为经销商申请记录
     * @apiVersion 0.0.1
	 * @apiSuccess {string} obj.USER_NAME    用户名,关联平台会员表TBL_USER_INFO的USER_NAME字段
	 * @apiSuccess {string} obj.CREATE_DATE    创建时间
	 * @apiSuccess {number} obj.APPROVAL_USER_ID    审批人
	 * @apiSuccess {string} obj.APPROVAL_DATE    审批时间
	 * @apiSuccess {string} obj.STATE    1（草稿）     2（待审批）    3（已审核通过）    4（已驳回）  
	 * @apiSuccess {string} obj.APPROVAL_REMARK    审批备注
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 新增平台会员申请成为经销商申请记录
     * @apiSuccess{number} total 总条数
	 */
	@RequestMapping(value = "/user_agent_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addUserAgentApply(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.addUserAgentApply(request));
	}
	
	/**
	 * 
	 * /**
	 * 
	 * @api{post} /{oms_server}/member/user_agent_approval 平台会员申请成为经销商审核
     * @apiGroup user_agent_approval
     * @apiName user_agent_approval
     * @apiDescription  平台会员申请成为经销商审核
     * @apiVersion 0.0.1
	 * @apiSuccess {string} obj.USER_NAME    用户名,关联平台会员表TBL_USER_INFO的USER_NAME字段
	 * @apiSuccess {string} obj.CREATE_DATE    创建时间
	 * @apiSuccess {number} obj.APPROVAL_USER_ID    审批人
	 * @apiSuccess {string} obj.APPROVAL_DATE    审批时间
	 * @apiSuccess {string} obj.STATE    1（草稿）     2（待审批）    3（已审核通过）    4（已驳回）  
	 * @apiSuccess {string} obj.APPROVAL_REMARK    审批备注
	 * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 平台会员申请成为经销商审核
     * @apiSuccess{number} total 总条数
	 */
	@RequestMapping(value = "/user_agent_approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet userAgentApplyApproval(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.userAgentApplyApproval(request));
	}
	/**   @api{post} /{oms_server}/member/edit 会员修改
     * @apiGroup edit
     * @apiName edit
     * @apiDescription  代发等级新增
     * @apiVersion 0.0.1
	    * @apiSuccess {number}ID    主键
	    * @apiSuccess {string}USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
	    * @apiSuccess {string}LOGIN_NAME    登录名称，用于登录
	    * @apiSuccess {string}LOGIN_PASSWORD    用户登录密码，加密数据
	    * @apiSuccess {number}USER_STATE    用户审核状态-1.非注册会员  0.待审核 1.审核通过 2.禁用 3.审核不通过
	    * @apiSuccess {string}USER_RESOURCE    会员来源    1:自行注册   2:后台注册
	    * @apiSuccess {number}CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}CREATE_DATE    创建时间
	    * @apiSuccess {number}EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}EDIT_DATE    编辑时间
	    * @apiSuccess {number}APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}APPROVAL_DATE    审核时间
	    * @apiSuccess {string}USER_CREATE_IP    用户注册IP
	    * @apiSuccess {number}USER_LOGIN_COUNT    用户登录次数
	    * @apiSuccess {string}USER_LAST_LOGIN_DATE    用户最后登录时间
	    * @apiSuccess {string}USER_LAST_LOGIN_IP    用户最后登录IP
	    * @apiSuccess {string}USER_HEAD_IMGURL    用户图像
	    * @apiSuccess {string}USER_MANAGE_NAME    负责人姓名
	    * @apiSuccess {string}USER_MANAGE_SEX    负责人性别
	    * @apiSuccess {string}USER_MANAGE_CARDID    负责人身份证号码
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE1    身份证正面附件路径
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE2    身份证反面附件路径
	    * @apiSuccess {string}USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
	    * @apiSuccess {string}USER_MANAGE_TELEPHONE    负责人电话
	    * @apiSuccess {string}USER_MANAGE_MOBILEPHONE    负责人手机
	    * @apiSuccess {string}USER_MANAGE_WEIXIN    负责人微信
	    * @apiSuccess {string}USER_MANAGE_QQ    负责人QQ
	    * @apiSuccess {string}USER_MANAGE_EMAIL    负责人邮箱
	    * @apiSuccess {string}USER_COMPANY_NAME    公司名称
	    * @apiSuccess {string}USER_COMPANY_CORPORATION    公司法人
	    * @apiSuccess {string}USER_COMPANY_TELEPHONE    公司电话
	    * @apiSuccess {number}USER_COMPANY_TYPE    公司经营类型（代码）
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_CITY    公司所在地城市id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
	    * @apiSuccess {string}USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
	    * @apiSuccess {string}USER_COMPANY_COMMENT    公司简介
	    * @apiSuccess {number}REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}STORE_ID    门店ID，表TBL_STORE_INFO主键
	    * @apiSuccess {string}STORE_NAME    门店名称，冗余字段，节约查询时间
	    * @apiSuccess {string}OPENID    微信的OPENID
	    * @apiSuccess {string}LAST_UPDATE_TIME    最后更新时间--【同步用】
	    * @apiSuccess {number}DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
	    * @apiSuccess {number}ISSUING_GRADE_ID    代发等级ID
	    * @apiSuccess {string}USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
	    * @apiSuccess {string}SHOP_WEBSITE    店铺网址
	    * @apiSuccess {string}SHOP_NAME    店铺名称
	    * @apiSuccess {string}PLATFORM    所属平台
	    * @apiSuccess {string}MAIN_CATEGORY    主营类目
	    * @apiSuccess {string}SHOP_PHOTO    店铺照片
    * @apiSuccess {string}UNBUNDING_DATE    上次解绑时间
	    * @apiSuccess {number}SITE_ID    站点ID
	   * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 会员修改信息
	   * 
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet edit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.editMember(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	
	/**   @api{post} /{oms_server}/member/edit_apply 待审批修改会员
     * @apiGroup edit_apply
     * @apiName edit_apply
     * @apiDescription  代发等级新增
     * @apiVersion 0.0.1
	    * @apiSuccess {number}ID    主键
	    * @apiSuccess {string}USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
	    * @apiSuccess {string}LOGIN_NAME    登录名称，用于登录
	    * @apiSuccess {string}LOGIN_PASSWORD    用户登录密码，加密数据
	    * @apiSuccess {number}USER_STATE    用户审核状态-1.非注册会员  0.待审核 1.审核通过 2.禁用 3.审核不通过
	    * @apiSuccess {string}USER_RESOURCE    会员来源    1:自行注册   2:后台注册
	    * @apiSuccess {number}CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}CREATE_DATE    创建时间
	    * @apiSuccess {number}EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}EDIT_DATE    编辑时间
	    * @apiSuccess {number}APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}APPROVAL_DATE    审核时间
	    * @apiSuccess {string}USER_CREATE_IP    用户注册IP
	    * @apiSuccess {number}USER_LOGIN_COUNT    用户登录次数
	    * @apiSuccess {string}USER_LAST_LOGIN_DATE    用户最后登录时间
	    * @apiSuccess {string}USER_LAST_LOGIN_IP    用户最后登录IP
	    * @apiSuccess {string}USER_HEAD_IMGURL    用户图像
	    * @apiSuccess {string}USER_MANAGE_NAME    负责人姓名
	    * @apiSuccess {string}USER_MANAGE_SEX    负责人性别
	    * @apiSuccess {string}USER_MANAGE_CARDID    负责人身份证号码
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE1    身份证正面附件路径
	    * @apiSuccess {string}USER_MANAGE_CARDID_FILE2    身份证反面附件路径
	    * @apiSuccess {string}USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
	    * @apiSuccess {string}USER_MANAGE_TELEPHONE    负责人电话
	    * @apiSuccess {string}USER_MANAGE_MOBILEPHONE    负责人手机
	    * @apiSuccess {string}USER_MANAGE_WEIXIN    负责人微信
	    * @apiSuccess {string}USER_MANAGE_QQ    负责人QQ
	    * @apiSuccess {string}USER_MANAGE_EMAIL    负责人邮箱
	    * @apiSuccess {string}USER_COMPANY_NAME    公司名称
	    * @apiSuccess {string}USER_COMPANY_CORPORATION    公司法人
	    * @apiSuccess {string}USER_COMPANY_TELEPHONE    公司电话
	    * @apiSuccess {number}USER_COMPANY_TYPE    公司经营类型（代码）
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_CITY    公司所在地城市id
	    * @apiSuccess {number}USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
	    * @apiSuccess {string}USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
	    * @apiSuccess {string}USER_COMPANY_COMMENT    公司简介
	    * @apiSuccess {number}REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string}MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
	    * @apiSuccess {number}STORE_ID    门店ID，表TBL_STORE_INFO主键
	    * @apiSuccess {string}STORE_NAME    门店名称，冗余字段，节约查询时间
	    * @apiSuccess {string}OPENID    微信的OPENID
	    * @apiSuccess {string}LAST_UPDATE_TIME    最后更新时间--【同步用】
	    * @apiSuccess {number}DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
	    * @apiSuccess {number}ISSUING_GRADE_ID    代发等级ID
	    * @apiSuccess {string}USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
	    * @apiSuccess {string}SHOP_WEBSITE    店铺网址
	    * @apiSuccess {string}SHOP_NAME    店铺名称
	    * @apiSuccess {string}PLATFORM    所属平台
	    * @apiSuccess {string}MAIN_CATEGORY    主营类目
	    * @apiSuccess {string}SHOP_PHOTO    店铺照片
    * @apiSuccess {string}UNBUNDING_DATE    上次解绑时间
	    * @apiSuccess {number}SITE_ID    站点ID
	   * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 会员修改信息
	   * 
	 */
	@RequestMapping(value = "/edit_apply", method = RequestMethod.POST)
	@ResponseBody
	public Packet editApply(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.editMemberApply(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 业务员、门店、业务经理--下属会员列表[代用户下单]
	 */
	/**
    *
    * @api {post} /{project_name}/order/list 会员列表
    * @apiGroup member
    * @apiDescription  会员列表
    * @apiVersion 0.0.1
	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 会员列表
    * @apiSuccess {number} obj.ID    主键
    * @apiSuccess {string} obj.USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
    * @apiSuccess {string} obj.LOGIN_NAME    登录名称，用于登录
    * @apiSuccess {number} obj.USER_STATE    用户审核状态-1.非注册会员  0.待审核 1.审核通过 2.禁用 3.审核不通过
    * @apiSuccess {string} obj.USER_RESOURCE    会员来源    1:自行注册   2:后台注册
    * @apiSuccess {number} obj.CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.CREATE_DATE    创建时间
    * @apiSuccess {number} obj.EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.EDIT_DATE    编辑时间
    * @apiSuccess {number} obj.APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.APPROVAL_DATE    审核时间
    * @apiSuccess {string} obj.USER_HEAD_IMGURL    用户图像
    * @apiSuccess {string} obj.USER_MANAGE_NAME    负责人姓名
    * @apiSuccess {string} obj.USER_MANAGE_SEX    负责人性别
    * @apiSuccess {string} obj.USER_MANAGE_CARDID    负责人身份证号码
    * @apiSuccess {string} obj.USER_MANAGE_CARDID_FILE1    身份证正面附件路径
    * @apiSuccess {string} obj.USER_MANAGE_CARDID_FILE2    身份证反面附件路径
    * @apiSuccess {string} obj.USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
    * @apiSuccess {string} obj.USER_MANAGE_TELEPHONE    负责人电话
    * @apiSuccess {string} obj.USER_MANAGE_MOBILEPHONE    负责人手机
    * @apiSuccess {string} obj.USER_MANAGE_WEIXIN    负责人微信
    * @apiSuccess {string} obj.USER_MANAGE_QQ    负责人QQ
    * @apiSuccess {string} obj.USER_MANAGE_EMAIL    负责人邮箱
    * @apiSuccess {string} obj.USER_COMPANY_NAME    公司名称
    * @apiSuccess {string} obj.USER_COMPANY_CORPORATION    公司法人
    * @apiSuccess {string} obj.USER_COMPANY_TELEPHONE    公司电话
    * @apiSuccess {number} obj.USER_COMPANY_TYPE    公司经营类型（代码）
    * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
    * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
    * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_CITY    公司所在地城市id
    * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
    * @apiSuccess {string} obj.USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
    * @apiSuccess {string} obj.USER_COMPANY_COMMENT    公司简介
    * @apiSuccess {number} obj.REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
    * @apiSuccess {number} obj.MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
    * @apiSuccess {number} obj.STORE_ID    门店ID，表TBL_STORE_INFO主键
    * @apiSuccess {string} obj.STORE_NAME    门店名称，冗余字段，节约查询时间
    * @apiSuccess {string} obj.OPENID    微信的OPENID
    * @apiSuccess {number} obj.DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
    * @apiSuccess {number} obj.ISSUING_GRADE_ID    代发等级ID
    * @apiSuccess {string} obj.USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
    * @apiSuccess {string} obj.SHOP_WEBSITE    店铺网址
    * @apiSuccess {string} obj.SHOP_NAME    店铺名称
    * @apiSuccess {string} obj.PLATFORM    所属平台
    * @apiSuccess {string} obj.MAIN_CATEGORY    主营类目
    * @apiSuccess {string} obj.SHOP_PHOTO    店铺照片
    * @apiSuccess {number} obj.SITE_ID    站点ID
    */
	@RequestMapping(value = "/subsidiary_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySubsidiaryMemberlist(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.querySubsidiaryMemberList(request));
	}
	
	/**
     * @api{post} /{oms_server}/member/record_list 返回个人收支记录列表结果
     * @apiGroup record_list
     * @apiName record_list
     * @apiDescription  返回个人收支记录列表结果
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 返回个人收支记录列表结果
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/record_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserAccountRecordList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryMemberAccountRecordList(request));
    }
    
    /**
     * @api{post} /{oms_server}/member/record_detail 根据记录号或收付关联号获取个人收支记录详情
     * @apiGroup record_detail
     * @apiName record_detail
     * @apiDescription   根据记录号或收付关联号获取个人收支记录详情
     * @apiVersion 0.0.1
     * @apiParam{string} [RECORD_NUMBER] 记录单号
     * @apiParam{string} [TURNOVER_NUMBER] 收付关联号
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 根据记录号或收付关联号获取个人收支记录详情
     */
   	@RequestMapping(value = "/record_detail", method = RequestMethod.POST)
   	@ResponseBody
   	public Packet queryUserAccountRecordDetail(HttpServletRequest request) {
   		return Transform.GetResult(memberInfoService.queryMemberAccountRecordDetail(request));
   	}
   	
    /**
     * @api{post} /{oms_server}/member/query_credit_list 返回授信管理列表结果
     * @apiGroup query_credit_list
     * @apiName query_credit_list
     * @apiDescription  返回授信管理列表结果
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj返回授信管理列表结果
     */
    @RequestMapping(value = "/query_credit_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCreditList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryCreditList(request));
    }
    /**
     * @api{post} /{oms_server}/member/query_credit_detail  根据ID返回授信管理列表详情
     * @apiGroup query_credit_detail
     * @apiName query_credit_detail
     * @apiDescription   根据ID返回授信管理列表详情
     * @apiVersion 0.0.1
     * @apiParam{number} id 授信管理ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj根据ID返回授信管理列表详情
     */
    @RequestMapping(value = "/query_credit_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCreditDetail(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryCreditDetail(request));
    }
    
       /**   @api{post} /{oms_server}/member/credit_add 新增授信管理
        * @apiGroup credit_add
        * @apiName credit_add
        * @apiDescription  新增授信管理
        * @apiVersion 0.0.1
        * @apiSuccess {number}ID    ID
	    * @apiSuccess {string}CREDIT_NUMBER    申请单号
	    * @apiSuccess {number}USER_ID    会员用户ID（关联tbl_user_info表的ID）
	    * @apiSuccess {string}IDCARD_NUMBER    会员用户身份证号码
	    * @apiSuccess {number}CREDIT_MONEY    申请金额
	    * @apiSuccess {string}PHONE    会员手机号码
	    * @apiSuccess {string}TELPHONE    会员固定号码
	    * @apiSuccess {string}IDCARD_ADDRESS    会员身份证地址
	    * @apiSuccess {string}CREATE_DATE    创建时间
	    * @apiSuccess {number}CREATE_USER_ID    创建人ID,表TBL_SYS_USER_INFO主键
	    * @apiSuccess {string}AUDIT_DATE    审核时间
	    * @apiSuccess {number}AUDIT_USER_ID    审核人ID,表TBL_SYS_USER_INFO主键
	    * @apiSuccess {string}STATE    状态：1、草稿，2、待审核，3、已审核，4：已关闭
	    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
        * @apiSuccess{string} message 接口返回信息
        * @apiSuccess{object} obj 新增授信管理
	    * 
	    */
    @RequestMapping(value = "/credit_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMemberCredit(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.addMemberCredit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**   @api{post} /{oms_server}/member/credit_edit 修改授信管理
     * @apiGroup credit_edit
     * @apiName credit_edit
     * @apiDescription  修改授信管理
     * @apiVersion 0.0.1
     * @apiSuccess {number}ID    ID
	 * @apiSuccess {string}CREDIT_NUMBER    申请单号
	 * @apiSuccess {number}USER_ID    会员用户ID（关联tbl_user_info表的ID）
	 * @apiSuccess {string}IDCARD_NUMBER    会员用户身份证号码
	 * @apiSuccess {number}CREDIT_MONEY    申请金额
	 * @apiSuccess {string}PHONE    会员手机号码
	 * @apiSuccess {string}TELPHONE    会员固定号码
	 * @apiSuccess {string}IDCARD_ADDRESS    会员身份证地址
	 * @apiSuccess {string}CREATE_DATE    创建时间
	 * @apiSuccess {number}CREATE_USER_ID    创建人ID,表TBL_SYS_USER_INFO主键
	 * @apiSuccess {string}AUDIT_DATE    审核时间
	 * @apiSuccess {number}AUDIT_USER_ID    审核人ID,表TBL_SYS_USER_INFO主键
	 * @apiSuccess {string}STATE    状态：1、草稿，2、待审核，3、已审核，4：已关闭
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 修改授信管理
	 * 
	 */
    @RequestMapping(value = "/credit_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMemberCredit(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.editMemberCredit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**   @api{post} /{oms_server}/member/credit_edit  删除授信管理
     * @apiGroup credit_remove
     * @apiName credit_remove
     * @apiDescription   删除授信管理
     * @apiVersion 0.0.1
     * @apiSuccess {number}ID    ID
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj  删除授信管理
	 * 
	 */
    @RequestMapping(value = "/credit_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeMemberCredit(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.removeMemberCredit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**   @api{post} /{oms_server}/member/credit_approval 授信管理审批
     * @apiGroup credit_approval
     * @apiName credit_approval
     * @apiDescription  授信管理审批
     * @apiVersion 0.0.1
     * @apiSuccess {number}ID    ID
	 * @apiSuccess {string}AUDIT_DATE    审核时间
	 * @apiSuccess {number}AUDIT_USER_ID    审核人ID,表TBL_SYS_USER_INFO主键
	 * @apiSuccess {string}STATE    状态：1、草稿，2、待审核，3、已审核，4：已关闭
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 授信管理审批
	 * 
	 */
	@RequestMapping(value = "/credit_update_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet creditUpdateState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.creditUpdateState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
     * @api{post} /{oms_server}/member/account_pre_list 账号预审列表
     * @apiGroup account_pre_list
     * @apiName account_pre_list
     * @apiDescription  账号预审列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 账号预审列表
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/account_pre_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAccountPreList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryAccountPreList(request));
    }
	
	/**   
	* @api{post} /{oms_server}/member/account_pre_approval 账号预审审核
    * @apiGroup account_pre_approval
    * @apiName account_pre_approval
    * @apiDescription  账号预审审核
    * @apiVersion 0.0.1
    * @apiSuccess {number}ID    主键
    * @apiSuccess {string}USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
    * @apiSuccess {string}LOGIN_NAME    登录名称，用于登录
    * @apiSuccess {string}LOGIN_PASSWORD    用户登录密码，加密数据
    * @apiSuccess {number}USER_STATE    用户审核状态-1.非注册会员  0.待审核 1.审核通过 2.禁用 3.审核不通过
    * @apiSuccess {string}USER_RESOURCE    会员来源    1:自行注册   2:后台注册
    * @apiSuccess {number}CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string}CREATE_DATE    创建时间
    * @apiSuccess {number}EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string}EDIT_DATE    编辑时间
    * @apiSuccess {number}APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string}APPROVAL_DATE    审核时间
    * @apiSuccess {string}USER_CREATE_IP    用户注册IP
    * @apiSuccess {number}USER_LOGIN_COUNT    用户登录次数
    * @apiSuccess {string}USER_LAST_LOGIN_DATE    用户最后登录时间
    * @apiSuccess {string}USER_LAST_LOGIN_IP    用户最后登录IP
    * @apiSuccess {string}USER_HEAD_IMGURL    用户图像
    * @apiSuccess {string}USER_MANAGE_NAME    负责人姓名
    * @apiSuccess {string}USER_MANAGE_SEX    负责人性别
    * @apiSuccess {string}USER_MANAGE_CARDID    负责人身份证号码
    * @apiSuccess {string}USER_MANAGE_CARDID_FILE1    身份证正面附件路径
    * @apiSuccess {string}USER_MANAGE_CARDID_FILE2    身份证反面附件路径
    * @apiSuccess {string}USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
    * @apiSuccess {string}USER_MANAGE_TELEPHONE    负责人电话
    * @apiSuccess {string}USER_MANAGE_MOBILEPHONE    负责人手机
    * @apiSuccess {string}USER_MANAGE_WEIXIN    负责人微信
    * @apiSuccess {string}USER_MANAGE_QQ    负责人QQ
    * @apiSuccess {string}USER_MANAGE_EMAIL    负责人邮箱
    * @apiSuccess {string}USER_COMPANY_NAME    公司名称
    * @apiSuccess {string}USER_COMPANY_CORPORATION    公司法人
    * @apiSuccess {string}USER_COMPANY_TELEPHONE    公司电话
    * @apiSuccess {number}USER_COMPANY_TYPE    公司经营类型（代码）
    * @apiSuccess {number}USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
    * @apiSuccess {number}USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
    * @apiSuccess {number}USER_COMPANY_ADDRESS_CITY    公司所在地城市id
    * @apiSuccess {number}USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
    * @apiSuccess {string}USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
    * @apiSuccess {string}USER_COMPANY_COMMENT    公司简介
    * @apiSuccess {number}REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string}REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
    * @apiSuccess {number}MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string}MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
    * @apiSuccess {number}STORE_ID    门店ID，表TBL_STORE_INFO主键
    * @apiSuccess {string}STORE_NAME    门店名称，冗余字段，节约查询时间
    * @apiSuccess {string}OPENID    微信的OPENID
    * @apiSuccess {string}LAST_UPDATE_TIME    最后更新时间--【同步用】
    * @apiSuccess {number}DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
    * @apiSuccess {number}ISSUING_GRADE_ID    代发等级ID
    * @apiSuccess {string}USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
    * @apiSuccess {string}SHOP_WEBSITE    店铺网址
    * @apiSuccess {string}SHOP_NAME    店铺名称
    * @apiSuccess {string}PLATFORM    所属平台
    * @apiSuccess {string}MAIN_CATEGORY    主营类目
    * @apiSuccess {string}SHOP_PHOTO    店铺照片
    * @apiSuccess {string}UNBUNDING_DATE    上次解绑时间
    * @apiSuccess {number}SITE_ID    站点ID
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 账号预审审核信息
	*/
	@RequestMapping(value = "/account_pre_approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet AccountPreApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.accountPreApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
     * @api{post} /{oms_server}/member/special_price_list 会员特殊价格列表查询
     * @apiGroup special_price_list
     * @apiName special_price_list
     * @apiDescription  会员特殊价格列表查询
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 会员特殊价格列表查询
     * @apiSuccess{number} total 总条数
     */
	@RequestMapping(value = "/special_price_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberSpecialList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.queryMemberSpecialList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
	 * /**
     * @api{post} /{oms_server}/member/special_price_detail 会员特殊价格详情
     * @apiGroup special_price_detail
     * @apiName special_price_detail
     * @apiDescription 会员特殊价格详情
     * @apiVersion 0.0.1
     * @apiParam{string} apply_number 申请单号
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/special_price_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberSpecialDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.queryMemberSpecialDetail(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	@RequestMapping(value = "/member_product_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberProductDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.memberProductDetail(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**@api{post} /{oms_server}/member/special_price_add 会员特殊价格新增
     * @apiGroup special_price_add
     * @apiName special_price_add
     * @apiDescription 会员新增
     * @apiVersion 0.0.1
     * @apiSuccess {string}APPLY_NUMBER    申请单号
     * @apiSuccess {string}STATE    状态：1、草稿，2、待审核，3、已审核，4：已关闭
     * @apiSuccess {number}CREATE_USER_ID    创建人ID
     * @apiSuccess {string}CREATE_DATE    创建时间
     * @apiSuccess {number}AUDIT_USER_ID    审核人用户ID
     * @apiSuccess {string}AUDIT_DATE    审核时间
     * @apiSuccess {string}REMARK    备注信息
 	 * @apiSuccess {string}USER_NAME    用户名
     * @apiSuccess {string}PRODUCT_ITEMNUMBER    商品货号
     * @apiSuccess {string}PRODUCT_COLOR    商品颜色
     * @apiSuccess {string}PRODUCT_SPECS    商品规格
     * @apiSuccess {number}DISCOUNT    会员服务费特殊折扣
     * @apiSuccess {string}BEGIN_TIME    开始时间
     * @apiSuccess {string}END_TIME    结束时间
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
	   * 
	 */
	@RequestMapping(value = "/special_price_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMemberSpecial(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.addMemberSpecial(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**@api{post} /{oms_server}/member/special_price_add 会员特殊价格编辑
     * @apiGroup special_price_edit
     * @apiName special_price_edit
     * @apiDescription 会员特殊价格编辑
     * @apiVersion 0.0.1
     * @apiSuccess {string}APPLY_NUMBER    申请单号
     * @apiSuccess {string}STATE    状态：1、草稿，2、待审核，3、已审核，4：已关闭
     * @apiSuccess {number}CREATE_USER_ID    创建人ID
     * @apiSuccess {string}CREATE_DATE    创建时间
     * @apiSuccess {number}AUDIT_USER_ID    审核人用户ID
     * @apiSuccess {string}AUDIT_DATE    审核时间
     * @apiSuccess {string}REMARK    备注信息
 	 * @apiSuccess {string}USER_NAME    用户名
     * @apiSuccess {string}PRODUCT_ITEMNUMBER    商品货号
     * @apiSuccess {string}PRODUCT_COLOR    商品颜色
     * @apiSuccess {string}PRODUCT_SPECS    商品规格
     * @apiSuccess {number}DISCOUNT    会员服务费特殊折扣
     * @apiSuccess {string}BEGIN_TIME    开始时间
     * @apiSuccess {string}END_TIME    结束时间
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
	   * 
	 */
	@RequestMapping(value = "/special_price_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMemberSpecial(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.editMemberSpecial(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	/**
	 * /**
     * @api{post} /{oms_server}/member/special_price_remove 会员特殊价删除
     * @apiGroup special_price_remove
     * @apiName special_price_remove
     * @apiDescription 会员特殊价删除
     * @apiVersion 0.0.1
     * @apiParam{string} apply_number 申请单号
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/special_price_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeMemberSpecial(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.removeMemberSpecial(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * /**
     * @api{post} /{oms_server}/member/special_price_detail_remove 会员特殊价明细删除
     * @apiGroup special_price_detail_remove
     * @apiName special_price_detail_remove
     * @apiDescription 会员特殊价明细删除
     * @apiVersion 0.0.1
     * @apiParam{string} apply_number 申请单号
     * @apiParam{string} user_name 用户名
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/special_price_detail_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeMemberSpecialDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.removeMemberSpecialDetail(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * /**
     * @api{post} /{oms_server}/member/special_price_update_state 会员特殊价修改状态
     * @apiGroup special_price_update_state
     * @apiName special_price_update_state
     * @apiDescription 会员特殊价修改状态
     * @apiVersion 0.0.1
     * @apiParam{string} apply_number 申请单号
     * @apiParam{string} state 状态
     * @apiParam{string} remark 描述（选填）
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/special_price_update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberSpecialUpdateState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.memberSpecialUpdateState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}

	/**
	 * /**
	 * @api{post} /{oms_server}/member/special_price_get_discount_price 获取会员特殊折后价
	 * @apiGroup special_price_get_discount_price
	 * @apiName special_price_get_discount_price
	 * @apiDescription 获取会员特殊折后价
	 * @apiVersion 0.0.1
	 * @apiParam {number} spec_id 规格id
	 * @apiParam {float} discount 折扣
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息
	 * @apiSuccess {string} obj 会员特殊折后价
	 */
	@RequestMapping(value = "/special_price_get_discount_price", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySpecialDiscountPrice(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.querySpecialDiscountPrice(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}

	/**
     * 加载用户销售日报表列表
     */
    @RequestMapping(value = "/user_daily_report_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserDailyReportListBy(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryUserDailyReportListBy(request));
    }
    
    /**
     * 查询全部订单明细
     */
    @RequestMapping(value = "/user_all_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAllOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryAllOrder(request));
    }
    
    /**
     * 查询预订订单明细
     */
    @RequestMapping(value = "/user_pre_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPreOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryPreOrder(request));
    }
    
    /**
     * 查询待付款订单明细
     */
    @RequestMapping(value = "/user_obli_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryObliOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryObliOrder(request));
    }
    
    /**
     * 查询未发货订单明细
     */
    @RequestMapping(value = "/user_unship_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUnshipOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryUnshipOrder(request));
    }
    /**
     * 查询发货订单明细
     */
    @RequestMapping(value = "/user_ship_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryShipOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryShipOrder(request));
    }
    /**
     * 查询退款单明细
     */
    @RequestMapping(value = "/user_refund_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRefundOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryRefundOrder(request));
    }
    /**
     * 查询退货单明细
     */
    @RequestMapping(value = "/user_returns_order", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryReturnOrder(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryReturnOrder(request));
    }
    /**
     * 查询浏览访问明细
     */
    @RequestMapping(value = "/browse_record", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBrowseRecord(HttpServletRequest request){
    	return Transform.GetResult(memberInfoService.queryUserBrowseRecord(request));
    }
	/**
	 * 取消月结
	 */
	@RequestMapping(value = "/cancel_monthly_statement", method = RequestMethod.POST)
	@ResponseBody
	public Packet cancelMonthlyStatement(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.cancelMonthlyStatement(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	/**
	* @api {post} /{project_name}/member/member_store_list 会员门店列表
    * @apiGroup member_store
    * @apiDescription  会员门店列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条   
	* @apiSuccess {number}ID    主键
 	* @apiSuccess {string}APPROVAL_STATE    0 待审核 1 审核通过 2已驳回
    * @apiSuccess {number}CREATE_USER_ID    创建人
    * @apiSuccess {string}APPROVAL_DATE    审批时间
    * @apiSuccess {number}APPROVAL_USER_ID    审批人
    * @apiSuccess {number}USER_ID    用户ID（关联表tbl_user_info表的ID）
    * @apiSuccess {string}LOGIN_NAME    用户名
    * @apiSuccess {string}USER_MANAGE_NAME    姓名
    * @apiSuccess {number}STORE_ID    门店ID
    * @apiSuccess {string}STORE_NAME    门店名称
    * @apiSuccess {string}STORE_ADDRESS    门店地址
    * @apiSuccess {string}MANAGEMENT_ADDRESS    经营地址
    * @apiSuccess {string}LONGITUDE    经度
    * @apiSuccess {string}LATITUDE    纬度
    * @apiSuccess {string}STATE    控货开关 1：关 2：开
    * @apiSuccess {string}CREATE_DATE    创建时间
    * @apiSuccess {number}PROVINCE    门店所在地省份id
    * @apiSuccess {number}CITY    门店所在地城市id
    * @apiSuccess {number}COUNTY    门店所在地区县id
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
	* @apiSuccess {string} message 接口返回信息说明
 */
@RequestMapping(value = "/member_store_list", method = RequestMethod.POST)
@ResponseBody
public Packet queryMemberStoreList(HttpServletRequest request){
	ProcessResult pr = new ProcessResult();
	try{
		pr = memberInfoService.queryMemberStoreList(request);
	}catch (Exception e){
		pr.setState(false);
		pr.setMessage(e.getMessage());
	}
	return Transform.GetResult(pr);
}

		/**
		* @api {post} /{project_name}/member/member_store_add 会员门店新增
	    * @apiGroup member_store
	    * @apiDescription  会员门店新增
	    * @apiVersion 0.0.1
	
		* @apiParam {number}ID    主键
	 	* @apiParam {string}APPROVAL_STATE    0 待审核 1 审核通过 2已驳回
	    * @apiParam {number}CREATE_USER_ID    创建人
	    * @apiParam {string}APPROVAL_DATE    审批时间
	    * @apiParam {number}APPROVAL_USER_ID    审批人
	    * @apiParam {number}USER_ID    用户ID（关联表tbl_user_info表的ID）
	    * @apiParam {string}LOGIN_NAME    用户名
	    * @apiParam {string}USER_MANAGE_NAME    姓名
	    * @apiParam {number}STORE_ID    门店ID
	    * @apiParam {string}STORE_NAME    门店名称
	    * @apiParam {string}STORE_ADDRESS    门店地址
	    * @apiParam {string}MANAGEMENT_ADDRESS    经营地址
	    * @apiParam {string}LONGITUDE    经度
	    * @apiParam {string}LATITUDE    纬度
	    * @apiParam {string}STATE    控货开关 1：关 2：开
	    * @apiParam {string}CREATE_DATE    创建时间
	    * @apiParam {number}PROVINCE    门店所在地省份id
	    * @apiParam {number}CITY    门店所在地城市id
	    * @apiParam {number}COUNTY    门店所在地区县id
	    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
		* @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/member_store_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addMemberStore(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.addMemberStore(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	* @api {post} /{project_name}/member/member_store_detail 会员门店详情
    * @apiGroup member_store
    * @apiDescription  会员门店详情
    * @apiVersion 0.0.1

	* @apiParam {number}ID    主键
 	* @apiSuccess {string}APPROVAL_STATE    0 待审核 1 审核通过 2已驳回
    * @apiSuccess {number}CREATE_USER_ID    创建人
    * @apiSuccess {string}APPROVAL_DATE    审批时间
    * @apiSuccess {number}APPROVAL_USER_ID    审批人
    * @apiSuccess {number}USER_ID    用户ID（关联表tbl_user_info表的ID）
    * @apiSuccess {string}LOGIN_NAME    用户名
    * @apiSuccess {string}USER_MANAGE_NAME    姓名
    * @apiSuccess {number}STORE_ID    门店ID
    * @apiSuccess {string}STORE_NAME    门店名称
    * @apiSuccess {string}STORE_ADDRESS    门店地址
    * @apiSuccess {string}MANAGEMENT_ADDRESS    经营地址
    * @apiSuccess {string}LONGITUDE    经度
    * @apiSuccess {string}LATITUDE    纬度
    * @apiSuccess {string}STATE    控货开关 1：关 2：开
    * @apiSuccess {string}CREATE_DATE    创建时间
    * @apiSuccess {number}PROVINCE    门店所在地省份id
    * @apiSuccess {number}CITY    门店所在地城市id
    * @apiSuccess {number}COUNTY    门店所在地区县id
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
	* @apiSuccess {string} message 接口返回信息说明
 */
@RequestMapping(value = "/member_store_detail", method = RequestMethod.POST)
@ResponseBody
public Packet queryMemberStoreDetail(HttpServletRequest request){
	ProcessResult pr = new ProcessResult();
	try{
		pr = memberInfoService.queryMemberStoreDetail(request);
	}catch (Exception e){
		pr.setState(false);
		pr.setMessage(e.getMessage());
	}
	return Transform.GetResult(pr);
}

		/**
		* @api {post} /{project_name}/member/member_store_approval 会员门店审批
	    * @apiGroup member_store
	    * @apiDescription  会员门店审批
	    * @apiVersion 0.0.1
	
		* @apiParam {number}ID    主键
		* @apiParam {number}APPROVAL_STATE    审批状态
		* @apiParam {number}REJECTED_REASON   驳回原因(驳回时参数)
		* 
		* @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
		* @apiSuccess {string} message 接口返回信息说明
		* 
	 */
	@RequestMapping(value = "/member_store_approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberStoreApproval(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.memberStoreApproval(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	* @api {post} /{project_name}/member/member_store_edit 会员门店编辑
    * @apiGroup member_store
    * @apiDescription  会员门店编辑
    * @apiVersion 0.0.1

	* @apiParam {number}ID    主键
 	* @apiParam {string}APPROVAL_STATE    0 待审核 1 审核通过 2已驳回
    * @apiParam {number}CREATE_USER_ID    创建人
    * @apiParam {string}APPROVAL_DATE    审批时间
    * @apiParam {number}APPROVAL_USER_ID    审批人
    * @apiParam {number}USER_ID    用户ID（关联表tbl_user_info表的ID）
    * @apiParam {string}LOGIN_NAME    用户名
    * @apiParam {string}USER_MANAGE_NAME    姓名
    * @apiParam {number}STORE_ID    门店ID
    * @apiParam {string}STORE_NAME    门店名称
    * @apiParam {string}STORE_ADDRESS    门店地址
    * @apiParam {string}MANAGEMENT_ADDRESS    经营地址
    * @apiParam {string}LONGITUDE    经度
    * @apiParam {string}LATITUDE    纬度
    * @apiParam {string}STATE    控货开关 1：关 2：开
    * @apiParam {string}CREATE_DATE    创建时间
    * @apiParam {number}PROVINCE    门店所在地省份id
    * @apiParam {number}CITY    门店所在地城市id
    * @apiParam {number}COUNTY    门店所在地区县id
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
 */
	@RequestMapping(value = "/member_store_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberStoreEdit(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.memberStoreEdit(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	/**
		* @api {post} /{project_name}/member/member_store_update_state 会员门店启用禁用
	    * @apiGroup member_store_update_state
	    * @apiDescription  会员门店启用禁用
	    * @apiVersion 0.0.1
	
		* @apiParam {number}user_store_id    主键
	 	* @apiParam {string}user_store_state 1  禁用  2 启用
	    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
	    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/member_store_update_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberStoreUpdateState(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.memberStoreUpdateState(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 会员批量修改业务归属
	 */
	@RequestMapping(value = "/batch_edit_bussiness", method = RequestMethod.POST)
	@ResponseBody
	public Packet batchEditBussiness(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.batchEditBussiness(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
     * @api{post} /{oms_server}/member/update_policy_period 会员政策期修改
     * @apiGroup update_policy_period
     * @apiName update_policy_period
     * @apiDescription  会员政策期修改
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{number} user_policy_period 政策期
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/update_policy_period", method = RequestMethod.POST)
	@ResponseBody
	public Packet updatePolicyPeriod(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.updatePolicyPeriod(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	
	/**
     * @api{post} /{oms_server}/member/member_state_log_list 会员状态操作日志列表
     * @apiGroup member_state_log_list
     * @apiName member_state_log_list
     * @apiDescription  会员状态操作日志列表
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 会员状态列表集合
     * @apiSuccess {number} obj.ID    				主键id
     * @apiSuccess {number} obj.USER_ID    			主用户id
     * @apiSuccess {string} obj.REMARK    			备注
     * @apiSuccess {string} obj.VOUCHER_IMG_URL     凭证
     * @apiSuccess {number} obj.STATE    			状态(1 启用,2  禁用)
     * @apiSuccess {string} obj.OPERATOR_NAME       操作人
     * @apiSuccess {string} obj.CREATE_DATE         操作时间
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/member_state_logs_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberStateLogList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.memberStateLogsList(request));
    }
    
    /**
     * @api{post} /{oms_server}/member/member_certification_approval_list 会员认证审批列表
     * @apiGroup member
     * @apiName member_certification_approval_list
     * @apiDescription  会员认证审批列表
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 会员认证审批列表集合
     * @apiSuccess {number} obj.ID    				主键id
     * @apiSuccess {number} obj.USER_ID    			会员id
     * @apiSuccess {string} obj.USER_REAL_NAME    			真实姓名
     * @apiSuccess {string} obj.USER_MANAGE_CARDID    用户身份证号
     * @apiSuccess {string} obj.USER_MANAGE_CARDID_IMG    			身份证正面照
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_PROVINCE       公司/店铺所在省id
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_CITY        公司/店铺所在市id
     * @apiSuccess {number} USER_COMPANY_ADDRESS_COUNTY 公司/店铺所在县id
     * @apiSuccess {string} USER_COMPANY_ADDRESS_DEAILS 公司/店铺详细地址
     * @apiSuccess {number} STATE 认证状态：0、待审核；1、已审批；2、驳回
     * @apiSuccess {number} TYPE 1 会员信息  2入驻商信息
     * @apiSuccess {string} CREATE_DATE 创建时间
     * @apiSuccess {string} USER_COMPANY_IMG 公司/店铺照片
     * @apiSuccess {number} APPROVAL_USER_ID 审批人id
     * @apiSuccess {string} APPROVAL_DATE 审批时间
     * @apiSuccess {string} RREJECTED_REASON 驳回原因
     * @apiSuccess {string} LOGIN_NAME 用户名
     * @apiSuccess {string} USER_MANAGE_NAME 姓名
     * @apiSuccess {string} ADDRESS 公司/店铺全地址
     */
    @RequestMapping(value = "/member_certification_approval_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberCertificationApprovalList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.memberCertificationApprovalList(request));
    }
    
    /**
     * @api{post} /{oms_server}/member/member_certification_approval_detail 会员认证审批详情
     * @apiGroup member
     * @apiName member_certification_approval_detail
     * @apiDescription  会员认证审批详情
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 会员认证审批列表集合
     * @apiSuccess {number} obj.ID    				主键id
     * @apiSuccess {number} obj.USER_ID    			会员id
     * @apiSuccess {string} obj.USER_REAL_NAME    			真实姓名
     * @apiSuccess {string} obj.USER_MANAGE_CARDID    用户身份证号
     * @apiSuccess {string} obj.USER_MANAGE_CARDID_IMG    			身份证正面照
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_PROVINCE       公司/店铺所在省id
     * @apiSuccess {number} obj.USER_COMPANY_ADDRESS_CITY        公司/店铺所在市id
     * @apiSuccess {number} USER_COMPANY_ADDRESS_COUNTY 公司/店铺所在县id
     * @apiSuccess {string} USER_COMPANY_ADDRESS_DEAILS 公司/店铺详细地址
     * @apiSuccess {number} STATE 认证状态：0、待审核；1、已审批；2、驳回
     * @apiSuccess {number} TYPE 1 会员信息  2入驻商信息
     * @apiSuccess {string} CREATE_DATE 创建时间
     * @apiSuccess {string} USER_COMPANY_IMG 公司/店铺照片
     * @apiSuccess {number} APPROVAL_USER_ID 审批人id
     * @apiSuccess {string} APPROVAL_DATE 审批时间
     * @apiSuccess {string} RREJECTED_REASON 驳回原因
     * @apiSuccess {string} LOGIN_NAME 用户名
     * @apiSuccess {string} USER_MANAGE_NAME 姓名
     * @apiSuccess {string} ADDRESS 公司/店铺全地址
     */
    @RequestMapping(value = "/member_certification_approval_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet memberCertificationApprovalDetail(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.memberCertificationApprovalDetail(request));
    }
    
    /**
     * @api{post} /{oms_server}/member/member_certification_approval 会员认证审批
     * @apiGroup member
     * @apiName member_certification_approval
     * @apiDescription  会员认证审批
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键id
     * @apiParam{number} approva_user_id 审批人id
     * @apiParam{string} rejected_reason  驳回原因(驳回时含改参数)
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/member_certification_approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberCertificationApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.memberCertificationApproval(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
    
    /**
     * @api{post} /{oms_server}/member/store_list 会员门店列表(店铺)
     * @apiGroup member
     * @apiName store_list
     * @apiDescription  会员门店列表(店铺)
     * @apiVersion 0.0.1
     * 
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 
     * @apiSuccess{number} obj.id			会员id
     * @apiSuccess{string} obj.store_name	门店名称
     * 
     */
    @RequestMapping(value = "/store_select_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryMemberStoreSelectList(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.queryMemberStoreSelectList(request));
	}
    
    /**
     * @api{post} /{oms_server}/member/select_sub_account 查询会员子账户
     * @apiGroup member
     * @apiName member_sub_account_list
     * @apiDescription  查询会员子账户
     * @apiVersion 0.0.1
     * @apiParam{number} MEMBER_ID 会员（主账号）ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/select_sub_account", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberSubAccountList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = memberInfoService.queryMemberSubAccountList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/member/return_withdrawing 退货扣款规则
     * @apiGroup member
     * @apiName return_withdrawing
     * @apiDescription  退货扣款规则
     * @apiVersion 0.0.1
     * 
     * @apiParam{number} id 主键
     * @apiParam{number} expired_deductions 	退货超期每双扣款金额
     * @apiParam{number} unexpired_deductions 	退货未超期每双扣款金额
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/return_withdrawing", method = RequestMethod.POST)
	@ResponseBody
	public Packet returnWithdrawing(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.returnWithdrawing(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
     * @api{post} /{oms_server}/member/user_level 用户级别设置
     * @apiGroup member
     * @apiName user_level
     * @apiDescription  用户级别设置
     * @apiVersion 0.0.1
     * 
     * @apiParam{number} id 主键
     * @apiParam{number} user_level 	级别
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
	@RequestMapping(value = "/user_level", method = RequestMethod.POST)
	@ResponseBody
	public Packet userLevel(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = memberInfoService.userLevel(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}

    /**
     * @api{post} /{oms_server}/member/select_sub_account 更新会员子账户
     * @apiGroup member
     * @apiName member_update_sub_account
     * @apiDescription  更新会员子账户
     * @apiVersion 0.0.1
     * @apiParam{number} MEMBER_ID 会员（主账号）ID
     * @apiParam{number} ID 子账户id
     * @apiParam{number} SUB_ACCOUNT_NAME 子账户用户名
     * @apiParam{number} PHONE_NUM 子账户手机号
     * @apiParam{number} SUB_ACCOUNT_PWD 子账户密码
     * @apiParam{number} CREATE_USER_ID 更新该子账户人的ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/update_sub_account", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateMemberSubAccount(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
			pr = memberInfoService.updateMemberSubAccount(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	return Transform.GetResult(pr);
    }
    
    /**
     * @api{post} /{oms_server}/member/delete_sub_account 更新会员子账户
     * @apiGroup member
     * @apiName member_delete_sub_account
     * @apiDescription  删除会员子账户
     * @apiVersion 0.0.1
     * @apiParam{number} ID 子账户ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/delete_sub_account", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteMemeberSubAccount(HttpServletRequest request){
    	ProcessResult pr = new ProcessResult();
    	try {
			pr = memberInfoService.deleteMemeberSubAccount(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	return Transform.GetResult(pr);
    };
    
    /**
     * @api{post} /{oms_server}/member/add_sub_account 添加会员子账户
     * @apiGroup member
     * @apiName member_add_sub_account
     * @apiDescription  添加会员子账户
     * @apiVersion 0.0.1
     * @apiParam{number} MEMBER_ID 与之关联的子账号ID
     * @apiParam{number} SUB_ACCOUNT_NAME 子账户用户名
     * @apiParam{number} SUB_ACCOUNT_PWD 子账户密码
     * @apiParam{number} PHONE_NUM 子账户手机号
     * @apiParam{number} CREATE_USER_ID 创建人ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     */
    @RequestMapping(value = "/add_sub_account", method = RequestMethod.POST)
    @ResponseBody
    public Packet addMemberSubAccount(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
			pr = memberInfoService.addMemeberSubAccount(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	return Transform.GetResult(pr);
    }  
    /**
     * 三方订单启/禁用
     * @param request
     * @return
     */
    @RequestMapping(value = "/other_sync_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet otherSyncState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
			pr = memberInfoService.otherSyncState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	return Transform.GetResult(pr);
    }

	/**
	 * @api {post} /{project_name}/member/get_user_pre_chk_info 查询预审用户信息
	 * @apiGroup member
	 * @apiName member_get_user_pre_chk_info
	 * @apiDescription  查询预审用户信息<p>
	 * @apiVersion 0.0.1

	 * @apiParam {string} [user_name] 			登录用户名
	 * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
	 * @apiSuccess {string} message 接口返回信息.
	 * @apiSuccess {object} obj 用户信息
	 * @apiSuccess {string} obj.id	        用户记录ID
	 * @apiSuccess {string} obj.user_name	用户名，仅用于关联操作
	 * @apiSuccess {string} obj.login_name	登录用户名
	 * @apiSuccess {string} obj.user_head_imgurl		用户头像
	 * @apiSuccess {string} obj.user_manage_name	负责人姓名
	 * @apiSuccess {string} obj.user_manage_sex	负责人性别
	 * @apiSuccess {string} obj.user_manage_cardid	负责人身份证号码
	 * @apiSuccess {string} obj.user_manage_cardid_file1	负责人身份证正面
	 * @apiSuccess {string} obj.user_manage_cardid_file2	负责人身份证反面
	 * @apiSuccess {string} obj.user_manage_current_address	负责人现居住地
	 * @apiSuccess {string} obj.user_manage_telephone	负责人电话
	 * @apiSuccess {string} obj.user_manage_mobilephone	负责人手机
	 * @apiSuccess {string} obj.user_manage_weixin	负责人微信
	 * @apiSuccess {string} obj.user_manage_qq	负责人QQ
	 * @apiSuccess {string} obj.user_manage_email	负责人邮箱
	 * @apiSuccess {string} obj.user_company_name	公司名称
	 * @apiSuccess {string} obj.user_company_corporation	公司法人
	 * @apiSuccess {string} obj.user_company_telephone	公司电话
	 * @apiSuccess {number} obj.user_company_address_province	公司所在地省份
	 * @apiSuccess {number} obj.user_company_address_city	公司所在地城市
	 * @apiSuccess {number} obj.user_company_address_county	公司所在地区县
	 * @apiSuccess {string} obj.user_company_address_deails	公司所在地详细地址
	 * @apiSuccess {string} obj.user_company_type	公司经营类型（代码）
	 * @apiSuccess {string} obj.user_company_comment	公司简介
	 * @apiSuccess {string} obj.user_create_ip	用户注册IP
	 * @apiSuccess {string} obj.openid	微信openId
	 * @apiSuccess {string} obj.user_last_login_ip		用户最后登录IP
	 * @apiSuccess {number} obj.user_level			    用户级别
	 * @apiSuccess {number} obj.user_state			    用户审核状态     -1非注册会员 ；  0.待审核； 1.审核通过 ；2.禁用 ；3.审核未通过
	 * @apiSuccess {date}   obj.user_last_login_date		用户最后登录时间 yyyy-MM-dd HH:mm:ss
	 * @apiSuccess {number} obj.user_login_count		登录次数
	 * @apiSuccess {date} obj.last_update_time		       用户记录最终更新时间 yyyy-MM-dd HH:mm:ss
	 * @apiSuccess {string} obj.user_business_licence_imgurl	用户营业执照图片地址
	 * @apiSuccess {int} obj.is_open_vip 				是否开通会员卡 0 否，1 是
	 * @apiSuccess {int} obj.expiration_flag 			会员卡过期标志位 0.未过期   1.已过期
	 * @apiSuccess {number} obj.card_balance 		会员卡未过期剩余余额,余额为零则会员卡已过期
	 * @apiSuccess {date} obj.expiration_date 		会员卡过期时间 时间格式字符串 yyyy-MM-dd HH:mm:ss
	 * @apiSuccess {int} obj.user_type 				用户类型
	 * 													<p>1.平台会员</p>
	 * 													<p>2.店铺会员</p>
	 * 													<p>3.店铺查看用户</p>
	 * 													<p>4.游客用户</p>
	 */
//	@RequestMapping(value = "/get_user_pre_chk_info", method = RequestMethod.POST)
//	@ResponseBody
//	public Packet findPreChkUser(HttpServletRequest request) {
//		return Transform.GetResult(memberInfoService.queryPreChkUser(request));
//	}
	
	/**
     * 会员送货入户设置信息
     * @param request
     * @return
     */
	@RequestMapping(value = "/delivery_home_info", method = RequestMethod.POST)
	@ResponseBody
	public Packet deliveryHomeInfo(HttpServletRequest request) {
		return Transform.GetResult(memberInfoService.deliveryHomeInfo(request));
	}
	
	/**
     * 会员送货入户设置
     * @param request
     * @return
     */
    @RequestMapping(value = "/delivery_home_set", method = RequestMethod.POST)
    @ResponseBody
    public Packet deliveryHomeSet(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
			pr = memberInfoService.deliveryHomeSet(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	return Transform.GetResult(pr);
    }
    
    /**
     * 会员账号预审删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/account_pre_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet accountPreRemove(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
			pr = memberInfoService.accountPreRemove(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
    	return Transform.GetResult(pr);
    }
    
    /**
	 * 会员快递设置
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logistics_set", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberLogisticsSet(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=memberInfoService.memberLogisticsSet(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
 	
 	/**
     * 会员快递详情
     * @param request
     * @return
     */
     @RequestMapping(value = "/logistics_detail", method = RequestMethod.POST)
     @ResponseBody
     public Packet queryMeberLogisticsDetail(HttpServletRequest request) {
         return Transform.GetResult(memberInfoService.queryMeberLogisticsDetail(request));
     }
     
     /**
      * 控货详情
      * @param request
      * @return
      */
      @RequestMapping(value = "/control_detail", method = RequestMethod.POST)
      @ResponseBody
      public Packet queryControlDetail(HttpServletRequest request) {
          return Transform.GetResult(memberInfoService.queryControlDetail(request));
      }
     
     /**
 	 * 控货设置
 	 * @param request
 	 * @return
 	 */
 	@RequestMapping(value = "/control_set", method = RequestMethod.POST)
 	@ResponseBody
 	public Packet controlSet(HttpServletRequest request) {
 		ProcessResult pr = new ProcessResult();
 		try{
 			pr=memberInfoService.controlSet(request);
 		}catch(Exception e){
 			pr.setMessage(e.getMessage());
 			pr.setState(false);
 		}
 		return Transform.GetResult(pr); 
 	}
 	
 	/**
	 * 批量修改站点
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/site_set", method = RequestMethod.POST)
	@ResponseBody
	public Packet memberSiteSet(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=memberInfoService.memberSiteSet(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}

    /**
     * 查询会员补扣款列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupDeductListForPage(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.querySupDeductListForPage(request));
    }

    /**
     * 保存会员补扣款
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_save", method = RequestMethod.POST)
    @ResponseBody
    public Packet saveSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberInfoService.saveSupDeduct(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * 删除会员补扣款
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberInfoService.removeSupDeduct(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * 查询会员补扣款详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupDeductDetail(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.querySupDeductDetail(request));
    }

    /**
     * 会员补扣款审批
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_approval", method = RequestMethod.POST)
    @ResponseBody
    public Packet approvalSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberInfoService.approvalSupDeduct(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * 会员补扣款签批
     * @param request
     * @return
     */
    @RequestMapping(value = "/sup_deduct_sign", method = RequestMethod.POST)
    @ResponseBody
    public Packet signSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = memberInfoService.signSupDeduct(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

    /**
     * 获取会员变更记录
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_log", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberChangeLogList(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryMemberChangeLogList(request));
    }
    /**
     * 获取会员变更记录详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/change_log_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberChangeLogDetail(HttpServletRequest request) {
        return Transform.GetResult(memberInfoService.queryMemberChangeLogDetail(request));
    }
}