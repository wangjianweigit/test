package com.tk.store.store.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.store.store.service.StoreManageService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/store")
public class StoreManageControl {
	
	@Resource
    private StoreManageService storeManageService;
	
	/**
	* @api {post} /{project_name}/store/add 店铺新增
    * @apiGroup add
    * @apiDescription  店铺新增
    * @apiVersion 0.0.1
    * @apiParam {number} USER_TYPE    1 用户会员 2 店铺会员
    * @apiParam {string} USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
    * @apiParam {string} LOGIN_NAME    登录名称，用于登录
    * @apiParam {string} USER_PWD    用户登录密码，加密数据
    * @apiParam {number} USER_STATE    用户状态   1.启用    2.禁用    4.预审通过
    * @apiParam {string} USER_RESOURCE    会员来源    1:自行注册   2:后台注册
    * @apiParam {number} CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} CREATE_DATE    创建时间
    * @apiParam {number} EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} EDIT_DATE    编辑时间
    * @apiParam {number} APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} APPROVAL_DATE    审核时间
    * @apiParam {string} USER_CREATE_IP    用户注册IP
    * @apiParam {number} USER_LOGIN_COUNT    用户登录次数
    * @apiParam {string} USER_LAST_LOGIN_DATE    用户最后登录时间
    * @apiParam {string} USER_LAST_LOGIN_IP    用户最后登录IP
    * @apiParam {string} USER_HEAD_IMGURL    用户图像
    * @apiParam {string} USER_MANAGE_NAME    负责人姓名
    * @apiParam {string} USER_MANAGE_SEX    负责人性别
    * @apiParam {string} USER_MANAGE_CARDID    负责人身份证号码
    * @apiParam {string} USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
    * @apiParam {string} USER_MANAGE_TELEPHONE    负责人电话
    * @apiParam {string} USER_MANAGE_MOBILEPHONE    负责人手机
    * @apiParam {string} USER_MANAGE_WEIXIN    负责人微信
    * @apiParam {string} USER_MANAGE_QQ    负责人QQ
    * @apiParam {string} USER_MANAGE_EMAIL    负责人邮箱
    * @apiParam {string} USER_COMPANY_NAME    公司名称
    * @apiParam {string} USER_COMPANY_CORPORATION    公司法人
    * @apiParam {string} USER_COMPANY_TELEPHONE    公司电话
    * @apiParam {number} USER_COMPANY_TYPE    公司经营类型ID。3:批发；4：网络销售；5：实体销售  6：其他  
    * @apiParam {number} USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
    * @apiParam {number} USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
    * @apiParam {number} USER_COMPANY_ADDRESS_CITY    公司所在地城市id
    * @apiParam {number} USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
    * @apiParam {string} USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
    * @apiParam {string} USER_COMPANY_COMMENT    公司简介
    * @apiParam {number} REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
    * @apiParam {number} MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
    * @apiParam {number} STORE_ID    门店ID，表TBL_STORE_INFO主键
    * @apiParam {string} STORE_NAME    门店名称，冗余字段，节约查询时间
    * @apiParam {string} OPENID    微信的OPENID
    * @apiParam {string} LAST_UPDATE_TIME    最后更新时间--【同步用】
    * @apiParam {number} DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
    * @apiParam {number} ISSUING_GRADE_ID    代发等级ID
    * @apiParam {string} USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
    * @apiParam {string} BUSINESS_LICENSE_NUMBER    营业执照编号
    * @apiParam {string} SHOP_WEBSITE    店铺网址
    * @apiParam {string} SHOP_NAME    店铺名称
    * @apiParam {string} PLATFORM    所属平台
    * @apiParam {string} MAIN_CATEGORY    主营类目
    * @apiParam {string} SHOP_PHOTO    店铺照片
    * @apiParam {string} UNBUNDING_DATE    上次解绑时间
    * @apiParam {number} SITE_ID    站点ID
    * @apiParam {number} DISCOUNT    会员服务费折扣
    * @apiParam {number} PRE_APRV_ALLOWED_NUMBER    预审会员允许登录次数；每次预审通过后，数量加1
    * @apiParam {number} PRE_APRV_LOGIN_NUMBER    预审会员已经登录次数；每次预审会员登录后，数量加1
    * @apiParam {string} USER_COMPANY_LOCATION_ADDRESS    公司定位地址
    * @apiParam {string} USER_CONTROL_STORE_NAME    会员控货门店名称
    * @apiParam {number} USER_STORE_ADDRESS_PROVINCE    控货门店所在省id
    * @apiParam {number} USER_STORE_ADDRESS_CITY    控货门店所在城市id
    * @apiParam {number} USER_STORE_ADDRESS_COUNTY    控货门店所在区县id
    * @apiParam {string} USER_STORE_ADDRESS_DEAILS    控货门店所在详细地址
    * @apiParam {string} USER_STORE_LOCATION_ADDRESS    控货门店所在定位地址
    * @apiParam {string} IS_WECHAT    是否开通微商城 (0.未开通，1.已开通)
    * @apiParam {string} IS_POS    是否开通POS (0.未开通，1.已开通)
    * @apiParam {number} SERVICE_RATE    经销商服务费率，1表示收取100% 0.9表示收取90% 取值范围[0.01,1] 0表示不收取
    * @apiParam {string} DISTRIBUTOR_DATE    成为经销商时间
    * @apiParam {number} DECORATION_STATE    装修状态（0.不开通 1.开通），默认为不开通
    * @apiParam {string} DECORATION_DATE    成为装修用户时间
    * @apiParam {number} obj.USER_POLICY_PERIOD    会员政策期
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Packet addStore(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeManageService.addStore(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	* @api {post} /{project_name}/store/edit 店铺编辑
    * @apiGroup edit
    * @apiDescription  店铺编辑
    * @apiVersion 0.0.1
   * @apiParam {number} USER_TYPE    1 用户会员 2 店铺会员
    * @apiParam {string} USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
    * @apiParam {string} LOGIN_NAME    登录名称，用于登录
    * @apiParam {string} USER_PWD    用户登录密码，加密数据
    * @apiParam {number} USER_STATE    用户状态   1.启用    2.禁用    4.预审通过
    * @apiParam {string} USER_RESOURCE    会员来源    1:自行注册   2:后台注册
    * @apiParam {number} CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} CREATE_DATE    创建时间
    * @apiParam {number} EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} EDIT_DATE    编辑时间
    * @apiParam {number} APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} APPROVAL_DATE    审核时间
    * @apiParam {string} USER_CREATE_IP    用户注册IP
    * @apiParam {number} USER_LOGIN_COUNT    用户登录次数
    * @apiParam {string} USER_LAST_LOGIN_DATE    用户最后登录时间
    * @apiParam {string} USER_LAST_LOGIN_IP    用户最后登录IP
    * @apiParam {string} USER_HEAD_IMGURL    用户图像
    * @apiParam {string} USER_MANAGE_NAME    负责人姓名
    * @apiParam {string} USER_MANAGE_SEX    负责人性别
    * @apiParam {string} USER_MANAGE_CARDID    负责人身份证号码
    * @apiParam {string} USER_MANAGE_CURRENT_ADDRESS    负责人现居住地
    * @apiParam {string} USER_MANAGE_TELEPHONE    负责人电话
    * @apiParam {string} USER_MANAGE_MOBILEPHONE    负责人手机
    * @apiParam {string} USER_MANAGE_WEIXIN    负责人微信
    * @apiParam {string} USER_MANAGE_QQ    负责人QQ
    * @apiParam {string} USER_MANAGE_EMAIL    负责人邮箱
    * @apiParam {string} USER_COMPANY_NAME    公司名称
    * @apiParam {string} USER_COMPANY_CORPORATION    公司法人
    * @apiParam {string} USER_COMPANY_TELEPHONE    公司电话
    * @apiParam {number} USER_COMPANY_TYPE    公司经营类型ID。3:批发；4：网络销售；5：实体销售  6：其他  
    * @apiParam {number} USER_COMPANY_ADDRESS_MAX    公司所在地大区ID
    * @apiParam {number} USER_COMPANY_ADDRESS_PROVINCE    公司所在地省份id
    * @apiParam {number} USER_COMPANY_ADDRESS_CITY    公司所在地城市id
    * @apiParam {number} USER_COMPANY_ADDRESS_COUNTY    公司所在地区县id
    * @apiParam {string} USER_COMPANY_ADDRESS_DEAILS    公司所在地详细地址
    * @apiParam {string} USER_COMPANY_COMMENT    公司简介
    * @apiParam {number} REFEREE_USER_ID    业务员ID，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} REFEREE_USER_REALNAME    业务员姓名，冗余字段，节约查询时间
    * @apiParam {number} MARKET_SUPERVISION_USER_ID    业务经理ID，表TBL_SYS_USER_INFO中主键
    * @apiParam {string} MARKET_SUPERVISION_USER_REALNA    业务经理姓名，冗余字段，节约查询时间
    * @apiParam {number} STORE_ID    门店ID，表TBL_STORE_INFO主键
    * @apiParam {string} STORE_NAME    门店名称，冗余字段，节约查询时间
    * @apiParam {string} OPENID    微信的OPENID
    * @apiParam {string} LAST_UPDATE_TIME    最后更新时间--【同步用】
    * @apiParam {number} DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
    * @apiParam {number} ISSUING_GRADE_ID    代发等级ID
    * @apiParam {string} USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
    * @apiParam {string} BUSINESS_LICENSE_NUMBER    营业执照编号
    * @apiParam {string} SHOP_WEBSITE    店铺网址
    * @apiParam {string} SHOP_NAME    店铺名称
    * @apiParam {string} PLATFORM    所属平台
    * @apiParam {string} MAIN_CATEGORY    主营类目
    * @apiParam {string} SHOP_PHOTO    店铺照片
    * @apiParam {string} UNBUNDING_DATE    上次解绑时间
    * @apiParam {number} SITE_ID    站点ID
    * @apiParam {number} DISCOUNT    会员服务费折扣
    * @apiParam {number} PRE_APRV_ALLOWED_NUMBER    预审会员允许登录次数；每次预审通过后，数量加1
    * @apiParam {number} PRE_APRV_LOGIN_NUMBER    预审会员已经登录次数；每次预审会员登录后，数量加1
    * @apiParam {string} USER_COMPANY_LOCATION_ADDRESS    公司定位地址
    * @apiParam {string} USER_CONTROL_STORE_NAME    会员控货门店名称
    * @apiParam {number} USER_STORE_ADDRESS_PROVINCE    控货门店所在省id
    * @apiParam {number} USER_STORE_ADDRESS_CITY    控货门店所在城市id
    * @apiParam {number} USER_STORE_ADDRESS_COUNTY    控货门店所在区县id
    * @apiParam {string} USER_STORE_ADDRESS_DEAILS    控货门店所在详细地址
    * @apiParam {string} USER_STORE_LOCATION_ADDRESS    控货门店所在定位地址
    * @apiParam {string} IS_WECHAT    是否开通微商城 (0.未开通，1.已开通)
    * @apiParam {string} IS_POS    是否开通POS (0.未开通，1.已开通)
    * @apiParam {number} SERVICE_RATE    经销商服务费率，1表示收取100% 0.9表示收取90% 取值范围[0.01,1] 0表示不收取
    * @apiParam {string} DISTRIBUTOR_DATE    成为经销商时间
    * @apiParam {number} DECORATION_STATE    装修状态（0.不开通 1.开通），默认为不开通
    * @apiParam {string} DECORATION_DATE    成为装修用户时间
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet editStore(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			pr = storeManageService.editStore(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
	}
	
	/**
	    * @api{post} /{oms_server}/store/approval_list 店铺审批列表
	    * @apiGroup store
	    * @apiName approval_list
	    * @apiDescription  店铺审批列表
	    * @apiVersion 0.0.1
	    * @apiParam{number} [pageIndex=1] 起始页
	    * @apiParam{number} [pageSize=10] 分页大小
	    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
	    * @apiSuccess{string} message 接口返回信息
	    * @apiSuccess{object} obj 店铺列表
	    * @apiSuccess {number} obj.COMMISSION_RATE    佣金比例(只有店铺会员设置佣金比例)
	    * @apiSuccess {number} obj.USER_TYPE    1 用户会员 2 店铺会员
	    * @apiSuccess {number} obj.ID    主键
	    * @apiSuccess {string} obj.USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
	    * @apiSuccess {string} obj.LOGIN_NAME    登录名称，用于登录
	    * @apiSuccess {string} obj.USER_PWD    用户登录密码，加密数据
	    * @apiSuccess {number} obj.USER_STATE    用户状态   1.启用    2.禁用    4.预审通过
	    * @apiSuccess {string} obj.USER_RESOURCE    会员来源    1:自行注册   2:后台注册
	    * @apiSuccess {number} obj.CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string} obj.CREATE_DATE    创建时间
	    * @apiSuccess {number} obj.EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string} obj.EDIT_DATE    编辑时间
	    * @apiSuccess {number} obj.APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
	    * @apiSuccess {string} obj.APPROVAL_DATE    审核时间
	    * @apiSuccess {string} obj.USER_CREATE_IP    用户注册IP
	    * @apiSuccess {number} obj.USER_LOGIN_COUNT    用户登录次数
	    * @apiSuccess {string} obj.USER_LAST_LOGIN_DATE    用户最后登录时间
	    * @apiSuccess {string} obj.USER_LAST_LOGIN_IP    用户最后登录IP
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
	    * @apiSuccess {number} obj.USER_COMPANY_TYPE    公司经营类型ID。3:批发；4：网络销售；5：实体销售  6：其他  
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
	    * @apiSuccess {string} obj.LAST_UPDATE_TIME    最后更新时间--【同步用】
	    * @apiSuccess {number} obj.DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
	    * @apiSuccess {number} obj.ISSUING_GRADE_ID    代发等级ID
	    * @apiSuccess {string} obj.USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
	    * @apiSuccess {string} obj.BUSINESS_LICENSE_NUMBER    营业执照编号
	    * @apiSuccess {string} obj.SHOP_WEBSITE    店铺网址
	    * @apiSuccess {string} obj.SHOP_NAME    店铺名称
	    * @apiSuccess {string} obj.PLATFORM    所属平台
	    * @apiSuccess {string} obj.MAIN_CATEGORY    主营类目
	    * @apiSuccess {string} obj.SHOP_PHOTO    店铺照片
	    * @apiSuccess {string} obj.UNBUNDING_DATE    上次解绑时间
	    * @apiSuccess {number} obj.SITE_ID    站点ID
	    * @apiSuccess {number} obj.DISCOUNT    会员服务费折扣
	    * @apiSuccess {number} obj.PRE_APRV_ALLOWED_NUMBER    预审会员允许登录次数；每次预审通过后，数量加1
	    * @apiSuccess {number} obj.PRE_APRV_LOGIN_NUMBER    预审会员已经登录次数；每次预审会员登录后，数量加1
	    * @apiSuccess {string} obj.USER_COMPANY_LOCATION_ADDRESS    公司定位地址
	    * @apiSuccess {string} obj.USER_CONTROL_STORE_NAME    会员控货门店名称
	    * @apiSuccess {number} obj.USER_STORE_ADDRESS_PROVINCE    控货门店所在省id
	    * @apiSuccess {number} obj.USER_STORE_ADDRESS_CITY    控货门店所在城市id
	    * @apiSuccess {number} obj.USER_STORE_ADDRESS_COUNTY    控货门店所在区县id
	    * @apiSuccess {string} obj.USER_STORE_ADDRESS_DEAILS    控货门店所在详细地址
	    * @apiSuccess {string} obj.USER_STORE_LOCATION_ADDRESS    控货门店所在定位地址
	    * @apiSuccess {string} obj.IS_WECHAT    是否开通微商城 (0.未开通，1.已开通)
	    * @apiSuccess {string} obj.IS_POS    是否开通POS (0.未开通，1.已开通)
	    * @apiSuccess {number} obj.SERVICE_RATE    经销商服务费率，1表示收取100% 0.9表示收取90% 取值范围[0.01,1] 0表示不收取
	    * @apiSuccess {string} obj.DISTRIBUTOR_DATE    成为经销商时间
	    * @apiSuccess {number} obj.DECORATION_STATE    装修状态（0.不开通 1.开通），默认为不开通
	    * @apiSuccess {string} obj.DECORATION_DATE    成为装修用户时间
	    * @apiSuccess {number} obj.USER_POLICY_PERIOD    会员政策期
	    * @apiSuccess{number} total 总条数
	    */
		@RequestMapping(value = "/approval_list", method = RequestMethod.POST)
		@ResponseBody
		public Packet storeApprovalList(HttpServletRequest request) {
			return Transform.GetResult(storeManageService.storeApprovalList(request));
		}
	
	
	/**
    * @api{post} /{oms_server}/store/list 店铺列表
    * @apiGroup list
    * @apiName list
    * @apiDescription  店铺列表
    * @apiVersion 0.0.1
    * @apiParam{number} [pageIndex=1] 起始页
    * @apiParam{number} [pageSize=10] 分页大小
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 店铺列表
    * @apiSuccess {number} obj.COMMISSION_RATE    佣金比例(只有店铺会员设置佣金比例)
    * @apiSuccess {number} obj.USER_TYPE    1 用户会员 2 店铺会员
    * @apiSuccess {number} obj.ID    主键
    * @apiSuccess {string} obj.USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
    * @apiSuccess {string} obj.LOGIN_NAME    登录名称，用于登录
    * @apiSuccess {string} obj.USER_PWD    用户登录密码，加密数据
    * @apiSuccess {number} obj.USER_STATE    用户状态   1.启用    2.禁用    4.预审通过
    * @apiSuccess {string} obj.USER_RESOURCE    会员来源    1:自行注册   2:后台注册
    * @apiSuccess {number} obj.CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.CREATE_DATE    创建时间
    * @apiSuccess {number} obj.EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.EDIT_DATE    编辑时间
    * @apiSuccess {number} obj.APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.APPROVAL_DATE    审核时间
    * @apiSuccess {string} obj.USER_CREATE_IP    用户注册IP
    * @apiSuccess {number} obj.USER_LOGIN_COUNT    用户登录次数
    * @apiSuccess {string} obj.USER_LAST_LOGIN_DATE    用户最后登录时间
    * @apiSuccess {string} obj.USER_LAST_LOGIN_IP    用户最后登录IP
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
    * @apiSuccess {number} obj.USER_COMPANY_TYPE    公司经营类型ID。3:批发；4：网络销售；5：实体销售  6：其他  
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
    * @apiSuccess {string} obj.LAST_UPDATE_TIME    最后更新时间--【同步用】
    * @apiSuccess {number} obj.DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
    * @apiSuccess {number} obj.ISSUING_GRADE_ID    代发等级ID
    * @apiSuccess {string} obj.USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
    * @apiSuccess {string} obj.BUSINESS_LICENSE_NUMBER    营业执照编号
    * @apiSuccess {string} obj.SHOP_WEBSITE    店铺网址
    * @apiSuccess {string} obj.SHOP_NAME    店铺名称
    * @apiSuccess {string} obj.PLATFORM    所属平台
    * @apiSuccess {string} obj.MAIN_CATEGORY    主营类目
    * @apiSuccess {string} obj.SHOP_PHOTO    店铺照片
    * @apiSuccess {string} obj.UNBUNDING_DATE    上次解绑时间
    * @apiSuccess {number} obj.SITE_ID    站点ID
    * @apiSuccess {number} obj.DISCOUNT    会员服务费折扣
    * @apiSuccess {number} obj.PRE_APRV_ALLOWED_NUMBER    预审会员允许登录次数；每次预审通过后，数量加1
    * @apiSuccess {number} obj.PRE_APRV_LOGIN_NUMBER    预审会员已经登录次数；每次预审会员登录后，数量加1
    * @apiSuccess {string} obj.USER_COMPANY_LOCATION_ADDRESS    公司定位地址
    * @apiSuccess {string} obj.USER_CONTROL_STORE_NAME    会员控货门店名称
    * @apiSuccess {number} obj.USER_STORE_ADDRESS_PROVINCE    控货门店所在省id
    * @apiSuccess {number} obj.USER_STORE_ADDRESS_CITY    控货门店所在城市id
    * @apiSuccess {number} obj.USER_STORE_ADDRESS_COUNTY    控货门店所在区县id
    * @apiSuccess {string} obj.USER_STORE_ADDRESS_DEAILS    控货门店所在详细地址
    * @apiSuccess {string} obj.USER_STORE_LOCATION_ADDRESS    控货门店所在定位地址
    * @apiSuccess {string} obj.IS_WECHAT    是否开通微商城 (0.未开通，1.已开通)
    * @apiSuccess {string} obj.IS_POS    是否开通POS (0.未开通，1.已开通)
    * @apiSuccess {number} obj.SERVICE_RATE    经销商服务费率，1表示收取100% 0.9表示收取90% 取值范围[0.01,1] 0表示不收取
    * @apiSuccess {string} obj.DISTRIBUTOR_DATE    成为经销商时间
    * @apiSuccess {number} obj.DECORATION_STATE    装修状态（0.不开通 1.开通），默认为不开通
    * @apiSuccess {string} obj.DECORATION_DATE    成为装修用户时间
    * @apiSuccess {number} obj.USER_POLICY_PERIOD    会员政策期
    * @apiSuccess{number} total 总条数
    */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeList(HttpServletRequest request) {
		return Transform.GetResult(storeManageService.storeList(request));
	}
	
	/**
    * @api{post} /{oms_server}/store/detail 店铺详情
    * @apiGroup detail
    * @apiName detail
    * @apiDescription  店铺详情
    * @apiVersion 0.0.1
    * @apiParam{number} id 店铺ID
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 店铺列表
    * @apiSuccess {number} obj.COMMISSION_RATE    佣金比例(只有店铺会员设置佣金比例)
    * @apiSuccess {number} obj.USER_TYPE    1 用户会员 2 店铺会员
    * @apiSuccess {number} obj.ID    主键
    * @apiSuccess {string} obj.USER_NAME    历史遗留字段，用于作为关联字段，保持与ID一致
    * @apiSuccess {string} obj.LOGIN_NAME    登录名称，用于登录
    * @apiSuccess {string} obj.USER_PWD    用户登录密码，加密数据
    * @apiSuccess {number} obj.USER_STATE    用户状态   1.启用    2.禁用    4.预审通过
    * @apiSuccess {string} obj.USER_RESOURCE    会员来源    1:自行注册   2:后台注册
    * @apiSuccess {number} obj.CREATE_USER_ID    创建人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.CREATE_DATE    创建时间
    * @apiSuccess {number} obj.EDIT_USER_ID    编辑人，若用户自行注册，则该字段为空，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.EDIT_DATE    编辑时间
    * @apiSuccess {number} obj.APPROVAL_USER_ID    审核人，表TBL_SYS_USER_INFO中主键
    * @apiSuccess {string} obj.APPROVAL_DATE    审核时间
    * @apiSuccess {string} obj.USER_CREATE_IP    用户注册IP
    * @apiSuccess {number} obj.USER_LOGIN_COUNT    用户登录次数
    * @apiSuccess {string} obj.USER_LAST_LOGIN_DATE    用户最后登录时间
    * @apiSuccess {string} obj.USER_LAST_LOGIN_IP    用户最后登录IP
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
    * @apiSuccess {number} obj.USER_COMPANY_TYPE    公司经营类型ID。3:批发；4：网络销售；5：实体销售  6：其他  
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
    * @apiSuccess {string} obj.LAST_UPDATE_TIME    最后更新时间--【同步用】
    * @apiSuccess {number} obj.DISTRIBUTION_STATE    分销状态 0-默认值，不开通 1-开通
    * @apiSuccess {number} obj.ISSUING_GRADE_ID    代发等级ID
    * @apiSuccess {string} obj.USER_BUSINESS_LICENCE_IMGURL    会员营业执照图片url
    * @apiSuccess {string} obj.BUSINESS_LICENSE_NUMBER    营业执照编号
    * @apiSuccess {string} obj.SHOP_WEBSITE    店铺网址
    * @apiSuccess {string} obj.SHOP_NAME    店铺名称
    * @apiSuccess {string} obj.PLATFORM    所属平台
    * @apiSuccess {string} obj.MAIN_CATEGORY    主营类目
    * @apiSuccess {string} obj.SHOP_PHOTO    店铺照片
    * @apiSuccess {string} obj.UNBUNDING_DATE    上次解绑时间
    * @apiSuccess {number} obj.SITE_ID    站点ID
    * @apiSuccess {number} obj.DISCOUNT    会员服务费折扣
    * @apiSuccess {number} obj.PRE_APRV_ALLOWED_NUMBER    预审会员允许登录次数；每次预审通过后，数量加1
    * @apiSuccess {number} obj.PRE_APRV_LOGIN_NUMBER    预审会员已经登录次数；每次预审会员登录后，数量加1
    * @apiSuccess {string} obj.USER_COMPANY_LOCATION_ADDRESS    公司定位地址
    * @apiSuccess {string} obj.USER_CONTROL_STORE_NAME    会员控货门店名称
    * @apiSuccess {number} obj.USER_STORE_ADDRESS_PROVINCE    控货门店所在省id
    * @apiSuccess {number} obj.USER_STORE_ADDRESS_CITY    控货门店所在城市id
    * @apiSuccess {number} obj.USER_STORE_ADDRESS_COUNTY    控货门店所在区县id
    * @apiSuccess {string} obj.USER_STORE_ADDRESS_DEAILS    控货门店所在详细地址
    * @apiSuccess {string} obj.USER_STORE_LOCATION_ADDRESS    控货门店所在定位地址
    * @apiSuccess {string} obj.IS_WECHAT    是否开通微商城 (0.未开通，1.已开通)
    * @apiSuccess {string} obj.IS_POS    是否开通POS (0.未开通，1.已开通)
    * @apiSuccess {number} obj.SERVICE_RATE    经销商服务费率，1表示收取100% 0.9表示收取90% 取值范围[0.01,1] 0表示不收取
    * @apiSuccess {string} obj.DISTRIBUTOR_DATE    成为经销商时间
    * @apiSuccess {number} obj.DECORATION_STATE    装修状态（0.不开通 1.开通），默认为不开通
    * @apiSuccess {string} obj.DECORATION_DATE    成为装修用户时间
    * @apiSuccess {number} obj.USER_POLICY_PERIOD    会员政策期
    */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeDetail(HttpServletRequest request) {
		return Transform.GetResult(storeManageService.storeDetail(request));
	}
	
	/**
	* @api {post} /{project_name}/store/update_state 店铺启用禁用
    * @apiGroup update_state
    * @apiDescription  店铺启用禁用
    * @apiVersion 0.0.1
	* @apiParam {number}id    主键
 	* @apiParam {string}user_state 1  禁用  2 启用
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/update_state", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateStoreState(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.updateStoreState(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	* @api {post} /{project_name}/store/commission_rate_set 店铺佣金比例设置
    * @apiGroup commission_rate_set
    * @apiDescription  店铺佣金比例设置
    * @apiVersion 0.0.1
	* @apiParam {number}id    主键
 	* @apiParam {number}commission_rate 佣金比例
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/commission_rate_set", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateStoreCommissionRate(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.updateStoreCommissionRate(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	 * 支付服务费设置
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay_service_rate_set", method = RequestMethod.POST)
	@ResponseBody
	public Packet storePayServiceRateSet(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.storePayServiceRateSet(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	* @api {post} /{project_name}/store/platform_login_set 店铺平台登入设置
    * @apiGroup platform_login_set
    * @apiDescription  店铺平台登入设置
    * @apiVersion 0.0.1
	* @apiParam {number}id    主键
 	* @apiParam {number}platform_login_state 平台可登入状态
    * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	 */
	@RequestMapping(value = "/platform_login_set", method = RequestMethod.POST)
	@ResponseBody
	public Packet storePlatformLoginSet(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.storePlatformLoginSet(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
     * @api{post} /{oms_server}/store/store_member_auth_approval_list 店铺会员认证审批列表
     * @apiGroup store
     * @apiName store_member_auth_approval_list
     * @apiDescription  店铺会员认证审批列表
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiParam {string} user_name 		用户名
     * @apiParam {string} user_real_name 	姓名
     * @apiParam {string} user_manage_mobilephone 手机号
     * @apiParam {list} state 认证状态：0、待审核；1、已审批；2、驳回
     * 
     * @apiSuccess {boolean}  state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}   message 接口返回信息
     * @apiSuccess {number}   total 总条数
     * @apiSuccess {object[]} obj 店铺会员认证审批列表集合
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
    @RequestMapping(value = "/store_member_auth_approval_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreMemberAuthApprovalList(HttpServletRequest request) {
        return Transform.GetResult(storeManageService.queryStoreMemberAuthApprovalList(request));
    }
    
    /**
     * @api{post} /{oms_server}/store/store_member_auth_approval_detail 店铺会员认证审批详情
     * @apiGroup store
     * @apiName store_member_auth_approval_detail
     * @apiDescription  店铺会员认证审批详情
     * @apiVersion 0.0.1
     * @apiParam{string} [pageIndex=1] 起始页
     * @apiParam{string} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 店铺会员认证审批详情
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
    @RequestMapping(value = "/store_member_auth_approval_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreMemberAuthApprovalDetail(HttpServletRequest request) {
        return Transform.GetResult(storeManageService.queryStoreMemberAuthApprovalDetail(request));
    }
    
    /**
     * 店铺会员密码重置
     */
    @RequestMapping(value = "/store_member_reset_pwd", method = RequestMethod.POST)
    @ResponseBody
    public Packet storeMemberResetPwd(HttpServletRequest request) {
        return Transform.GetResult(storeManageService.storeMemberResetPwd(request));
    }
    
    @RequestMapping(value = "/get_partner_user_id", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPartnerUserId(HttpServletRequest request) {
        return Transform.GetResult(storeManageService.queryPartnerUserId(request));
    }
    
    /**
     * @api{post} /{oms_server}/store/query_store_user_manage_list 查询商家经营店铺列表
     * @apiGroup store
     * @apiName query_store_user_manage_list
     * @apiDescription  查询商家经营店铺列表
     * @apiVersion 0.0.1
     * 
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=20] 分页大小
     * @apiParam{number} [user_id] 	         商家id
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj 商家经营店铺列表
     * @apiSuccess {number} obj.ID    主键ID
     * @apiSuccess {number} obj.USER_ID    商家id
     * @apiSuccess {string} obj.STORE_NAME    店铺名称
     * @apiSuccess {string} obj.STORE_MOBILEPHONE    门店电话
     * @apiSuccess {string} obj.STORE_DUTY_PERSON    门店负责人
     * @apiSuccess {number} obj.PROVINCE    门店所在地省份id
     * @apiSuccess {number} obj.CITY    门店所在地城市id
     * @apiSuccess {number} obj.COUNTY    门店所在地区县id
     * @apiSuccess {string} obj.STORE_DETAIL_ADDRESS    门店详细地址
     * @apiSuccess {string} obj.STORE_POSITION_ADDRESS    门店定位地址
     * @apiSuccess {number} obj.LONGITUDE    经度
     * @apiSuccess {number} obj.LATITUDE    维度
     * @apiSuccess {string} obj.CREATE_DATE    创建时间
     * @apiSuccess {number} obj.CREATE_USER_ID    创建人
     * @apiSuccess {number} obj.APPROVAL_USER_ID    审批人
     * @apiSuccess {string} obj.APPROVAL_DATE    审批时间
     * @apiSuccess {string} obj.REJECTED_REASON    驳回原因
     * @apiSuccess {number} obj.APPROVAL_STATE    0 待审核 1 审核通过 2已驳回
     */
 	@RequestMapping(value = "/query_store_user_manage_list", method = RequestMethod.POST)
 	@ResponseBody
 	public Packet queryStoreUserManageList(HttpServletRequest request) {
 		return Transform.GetResult(storeManageService.queryStoreUserManageList(request));
 	}
 	
 	/**
     * @api{post} /{oms_server}/store/query_store_member_library_list 查询商家库列表
     * @apiGroup store
     * @apiName query_store_member_library_list
     * @apiDescription 查询商家库列表
     * @apiVersion 0.0.1
     * 
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=20] 分页大小
     * 
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object[]} obj   数据列表
     * @apiSuccess {number} obj.ID    主键ID
     * @apiSuccess {string} obj.LOGIN_NAME    用户名
     * @apiSuccess {string} obj.USER_MANAGE_NAME    姓名
     * @apiSuccess {string} obj.USER_MANAGE_MOBILEPHONE    手机号
     * @apiSuccess {string} obj.CREATE_DATE    创建时间
     * @apiSuccess {string} obj.MARKET_SUPERVISION_USER_REALNA    业务经理名称
     * @apiSuccess {string} obj.SUPERVISOR_USER_REALNA    督导名称
     */
 	@RequestMapping(value = "/query_store_member_library_list", method = RequestMethod.POST)
 	@ResponseBody
 	public Packet queryStoreMemberLibraryList(HttpServletRequest request) {
 		return Transform.GetResult(storeManageService.queryStoreMemberLibraryList(request));
 	}
 	
 	/**
	* @api {post} /{project_name}/store/store_user_manage_add 商家经营店铺新增
	* @apiGroup store
    * @apiName store_user_manage_add
    * @apiDescription  商家经营店铺新增
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} obj.USER_ID    商家id
    * @apiParam {string} obj.STORE_NAME    店铺名称
    * @apiParam {string} obj.STORE_MOBILEPHONE    门店电话
    * @apiParam {string} obj.STORE_DUTY_PERSON    门店负责人
    * @apiParam {number} obj.PROVINCE    门店所在地省份id
    * @apiParam {number} obj.CITY    门店所在地城市id
    * @apiParam {number} obj.COUNTY    门店所在地区县id
    * @apiParam {string} obj.STORE_DETAIL_ADDRESS    门店详细地址
    * @apiParam {string} obj.STORE_POSITION_ADDRESS    门店定位地址
    * @apiParam {number} obj.LONGITUDE    经度
    * @apiParam {number} obj.LATITUDE    维度
    * @apiParam {string} obj.CREATE_DATE    创建时间
    * @apiParam {number} obj.CREATE_USER_ID    创建人
    * @apiParam {number} obj.APPROVAL_STATE    审批状态
    * @apiParam {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiParam {string} message 接口返回信息说明
	*/
	@RequestMapping(value = "/store_user_manage_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeUserManageAdd(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.storeUserManageAdd(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
	* @api {post} /{project_name}/store/store_user_manage_edit 商家经营店铺编辑
	* @apiGroup store
    * @apiName store_user_manage_edit
    * @apiDescription  商家经营店铺编辑
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} obj.USER_ID    商家id
    * @apiParam {string} obj.STORE_NAME    店铺名称
    * @apiParam {string} obj.STORE_MOBILEPHONE    门店电话
    * @apiParam {string} obj.STORE_DUTY_PERSON    门店负责人
    * @apiParam {number} obj.PROVINCE    门店所在地省份id
    * @apiParam {number} obj.CITY    门店所在地城市id
    * @apiParam {number} obj.COUNTY    门店所在地区县id
    * @apiParam {string} obj.STORE_DETAIL_ADDRESS    门店详细地址
    * @apiParam {string} obj.STORE_POSITION_ADDRESS    门店定位地址
    * @apiParam {number} obj.LONGITUDE    经度
    * @apiParam {number} obj.LATITUDE    维度
    * @apiParam {number} obj.APPROVAL_STATE    审批状态
    * @apiParam {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiParam {string} message 接口返回信息说明
	*/
	@RequestMapping(value = "/store_user_manage_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeUserManageEdit(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.storeUserManageEdit(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	
	/**
    * @api{post} /{oms_server}/store/query_store_user_manage_detail 查询商家店铺详情
    * @apiGroup store
    * @apiName query_store_user_manage_detail
    * @apiDescription  查询商家店铺详情
    * @apiVersion 0.0.1
    * @apiParam{number} id 商家经营店铺ID
    * 
    * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess{string} message 接口返回信息
    * @apiSuccess{object} obj 商家经营店铺详情
    * @apiSuccess {number} obj.ID    主键ID
    * @apiSuccess {number} obj.USER_ID    商家id
    * @apiSuccess {string} obj.STORE_NAME    店铺名称
    * @apiSuccess {string} obj.STORE_MOBILEPHONE    门店电话
    * @apiSuccess {string} obj.STORE_DUTY_PERSON    门店负责人
    * @apiSuccess {number} obj.PROVINCE    门店所在地省份id
    * @apiSuccess {number} obj.CITY    门店所在地城市id
    * @apiSuccess {number} obj.COUNTY    门店所在地区县id
    * @apiSuccess {string} obj.STORE_DETAIL_ADDRESS    门店详细地址
    * @apiSuccess {string} obj.STORE_POSITION_ADDRESS    门店定位地址
    * @apiSuccess {number} obj.LONGITUDE    经度
    * @apiSuccess {number} obj.LATITUDE    维度
    * @apiSuccess {string} obj.CREATE_DATE    创建时间
    * @apiSuccess {number} obj.CREATE_USER_ID    创建人
    * @apiSuccess {number} obj.APPROVAL_USER_ID    审批人
    * @apiSuccess {string} obj.APPROVAL_DATE    审批时间
    * @apiSuccess {string} obj.REJECTED_REASON    驳回原因
    * @apiSuccess {number} obj.APPROVAL_STATE    0 待审核 1 审核通过 2已驳回
    */
	@RequestMapping(value = "/query_store_user_manage_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryStoreUserManageDetail(HttpServletRequest request) {
		return Transform.GetResult(storeManageService.queryStoreUserManageDetail(request));
	}
	
	/**
	* @api {post} /{project_name}/store/store_user_manage_approval 商家经营店铺审批
	* @apiGroup store
    * @apiName store_user_manage_approval
    * @apiDescription  商家经营店铺审批
    * @apiVersion 0.0.1
    * 
    * @apiParam {number} obj.id    商家店铺id
    * @apiParam {number} obj.approval_state    审批状态
    * @apiParam {string} obj.rejected_reason   驳回原因(仅当驳回时才由此参数)
    * @apiParam {boolean} state 接口审核是否成功.true:成功  false:失败
    * @apiParam {string} message 接口返回信息说明
	*/
	@RequestMapping(value = "/store_user_manage_approval", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeUserManageApproval(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		try{
			pr = storeManageService.storeUserManageApproval(request);
		}catch (Exception e){
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return Transform.GetResult(pr);
	}
	/**
	 * 门店下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/store_select", method = RequestMethod.POST)
	@ResponseBody
	public Packet storeSelect(HttpServletRequest request) {
		return Transform.GetResult(storeManageService.storeSelect(request));
	}

}
