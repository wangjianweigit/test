package com.tk.oms.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.SampleService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : SampleControl
 * 样品管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-11-22
 */
@Controller
@RequestMapping("/sample")
public class SampleControl {
	@Resource
	private SampleService sampleService;
   /**
    *
    * @api {post} /{project_name}/sample/approval_list 样品审批列表
    * @apiGroup sample
    * @apiDescription  样品审批列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 样品审批列表
    * @apiSuccess {number} obj.ID					主键ID
    * @apiSuccess {number} obj.STATIONED_USER_ID 	入驻商ID
	* @apiSuccess {number} obj.BRAND_ID				品牌ID
	* @apiSuccess {string} obj.ITEMNUMBER			货号   
	* @apiSuccess {string} obj.SAMPLE_NAME			样品名称
	* @apiSuccess {string} obj.CREATE_DATE			创建日期
	* @apiSuccess {number} obj.CREATE_USER_ID		创建人
	* @apiSuccess {string} obj.UPDATE_DATE			编辑日期
	* @apiSuccess {number} obj.UPDATE_USER_ID		编辑人ID
	* @apiSuccess {string} obj.APPROVAL_DATE		审批日期
	* @apiSuccess {number} obj.APPROVAL_USER_ID		审批人ID
	* @apiSuccess {string} obj.SAMPLE_IMG_URL		样品主图路径
	* @apiSuccess {number} obj.STATE				状态 1（草稿）     2（待审批）    3（已审核通过）    4（已驳回） 
	* @apiSuccess {number} obj.TEMPLET_ID 			模板ID
	* @apiSuccess {string} obj.SAMPLE_FLOOR_PRICE	价格区间：价格下限即最低价
	* @apiSuccess {string} obj.SAMPLE_CEILING_PRICE	价格区间：价格上限即高低价
	* @apiSuccess {string} obj.REJECTED_REASON		驳回原因
	* 
    */
	@RequestMapping(value = "/approval_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySampleApprovalList(HttpServletRequest request) {
		return Transform.GetResult(sampleService.querySampleApprovalList(request));
	}
	
   /**
    *
    * @api {post} /{project_name}/sample/approval_detail 样品审批详情
    * @apiGroup sample
    * @apiDescription  样品审批详情
    * @apiVersion 0.0.1

	* @apiParam {string} itemnumber					样品货号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object} obj 
    * @apiSuccess {object} obj.base_info 样品审批详情
    * @apiSuccess {object[]} obj.type_list	样品模板类型列表
    * @apiSuccess {number} obj.type_list.type_id	类型ID
    * @apiSuccess {string} obj.type_list.type_name	类型名称
    * @apiSuccess {number} obj.type_list.if_display_pic	是否展示图片 ；1 不展示 2展示
    * @apiSuccess {number} obj.type_list.if_words	是否展示文字说明； 1 不展示、 2：展示
    * @apiSuccess {number} obj.type_list.if_open_score	是否展示评分；  1 不展示、 2：展示
    * @apiSuccess {number} obj.type_list.if_suggest	是否允许评审人员提交建议；  1 不允许、 2：允许
    * @apiSuccess {string} obj.type_list.description	文字说明内容，仅在可以文字说明内容后，该字段才有值
    * @apiSuccess {object[]} obj.type_list.form_list	样品类型表单项列表
    * @apiSuccess {string} obj.type_list.form_list.template_form_name	表单项名称
    * @apiSuccess {string} obj.type_list.form_list.template_form_way	表单项填写方式
    * @apiSuccess {number} obj.type_list.form_list.required_flag		是否必填 1：不必填 2：必填
    * @apiSuccess {string} obj.type_list.form_list.template_form_option	表单项选择项
    * @apiSuccess {object[]} obj.type_list.pic_list		样品类型图片列表
    * @apiSuccess {string} obj.type_list.pic_list.img_url	图片地址
	* 
    */
	@RequestMapping(value = "/approval_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySampleApprovalDetail(HttpServletRequest request) {
		return Transform.GetResult(sampleService.querySampleApprovalDetail(request));
	}
   /**
    *
    * @api {post} /{project_name}/sample/check 样品审批
    * @apiGroup sample
    * @apiDescription  样品审批
    * @apiVersion 0.0.1

	* @apiParam {string} itemnumber			样品货号
	* @apiParam {number} state				审批状态
	* @apiParam {string} review_start_date	评审开始时间
	* @apiParam {string} review_end_date	评审结束时间
	* @apiParam {string} rejected_reason	驳回原因

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	* 
    */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Packet audit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.check(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 样品评审用户组列表查询
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserList(HttpServletRequest request) {
		return Transform.GetResult(sampleService.reviewUserList(request));
	}
	/**
	 * 样品评审用户组新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserAdd(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.reviewUserAdd(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 查询样品评审用户组关联会员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_group_user", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewGroupUser(HttpServletRequest request) {
		return Transform.GetResult(sampleService.reviewGroupUser(request));
	}
	/**
	 * 样品评审用户组明细新增
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_detail_add", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserDetailAdd(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.reviewUserDetailAdd(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 样品评审用户组明细删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_detail_remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserDetailRemove(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.reviewUserDetailRemove(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 样品评审用户组编辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_edit", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserEdit(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.reviewUserEdit(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 样品评审用户组删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_remove", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserRemove(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.reviewUserRemove(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	/**
	 * 样品评审用户组启用/禁用
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_switch", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserSwitch(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try{
			pr=sampleService.reviewUserSwitch(request);
		}catch(Exception e){
			pr.setMessage(e.getMessage());
			pr.setState(false);
		}
		return Transform.GetResult(pr); 
	}
	
	/**
	 * 查询样品评审用户组详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/review_user_detail", method = RequestMethod.POST)
	@ResponseBody
	public Packet reviewUserDetail(HttpServletRequest request) {
		return Transform.GetResult(sampleService.reviewUserDetail(request));
	}
	
	 /**
    *
    * @api {post} /{project_name}/sample/review_list 样品评审列表
    * @apiGroup sample
    * @apiDescription  样品评审列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条
	* @apiParam {string} review_state		            评审状态，逗号分隔串（ 0-不能评审，1-待评审， 2-评审中，3-评审结束，4终止评审 ）
	* @apiParam {string} stationed_user_name	入驻商名
	* @apiParam {string} sample_name			样品名
	* @apiParam {number} brand_id				样品品牌
	* 

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {object[]} obj 样品审批列表
    * @apiSuccess {number} obj.ID					主键ID
    * @apiSuccess {number} obj.STATIONED_USER_ID 	入驻商ID
	* @apiSuccess {number} obj.BRAND_ID				品牌ID
	* @apiSuccess {string} obj.ITEMNUMBER			货号   
	* @apiSuccess {string} obj.SAMPLE_NAME			样品名称
	* @apiSuccess {string} obj.CREATE_DATE			创建日期
	* @apiSuccess {number} obj.CREATE_USER_ID		创建人
	* @apiSuccess {string} obj.UPDATE_DATE			编辑日期
	* @apiSuccess {number} obj.UPDATE_USER_ID		编辑人ID
	* @apiSuccess {string} obj.APPROVAL_DATE		审批日期
	* @apiSuccess {number} obj.APPROVAL_USER_ID		审批人ID
	* @apiSuccess {string} obj.SAMPLE_IMG_URL		样品主图路径
	* @apiSuccess {number} obj.STATE				状态 1（草稿）     2（待审批）    3（已审核通过）    4（已驳回） 
	* @apiSuccess {number} obj.TEMPLET_ID 			模板ID
	* @apiSuccess {string} obj.SAMPLE_FLOOR_PRICE	价格区间：价格下限即最低价
	* @apiSuccess {string} obj.SAMPLE_CEILING_PRICE	价格区间：价格上限即高低价
	* @apiSuccess {string} obj.REJECTED_REASON		驳回原因
	* 
    */
	@RequestMapping(value = "/review_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySampleReviewList(HttpServletRequest request) {
		return Transform.GetResult(sampleService.querySampleReviewList(request));
	}
	
	 /**
    *
    * @api {post} /{project_name}/sample/user_review_list 样品评审分析用户评审列表【按用户查看】
    * @apiGroup sample
    * @apiDescription  样品用户评审列表
    * @apiVersion 0.0.1

	* @apiParam {number} pageIndex				页码 （第几页） 
	* @apiParam {number} pageSize				每页多少条
	* @apiParam {string} itemnumber				样品货号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total   列表总数
    * @apiSuccess {object} obj   样品用户评审信息
    * @apiSuccess {object[]} obj.sample_review_type_list   样品评审类型列表
    * @apiSuccess {number} obj.sample_review_type_list.ID   样品评审类型id
    * @apiSuccess {string} obj.sample_review_type_list.TYPE_NAME   样品评审类型
    * @apiSuccess {number} obj.sample_review_type_list.IF_OPEN_SCORE   样品评审类型是否开启评分 1不允许 2允许
    * @apiSuccess {number} obj.sample_review_type_list.IF_SUGGEST   样品评审类型是否开启建议 1不允许 2允许
	* @apiSuccess {object[]} obj.sample_user_review_list   样品评审类型列表
	* @apiSuccess {number} obj.sample_user_review_list.ID   记录ID
	* @apiSuccess {string} obj.sample_user_review_list.CREATE_USER_NAME   评审用户名
	* @apiSuccess {number} obj.sample_user_review_list.CREATE_USER_ID   评审用户ID
	* @apiSuccess {string} obj.sample_user_review_list.CREATE_DATE      评审时间
	* @apiSuccess {object} obj.sample_user_review_list.REVIEW_MAP      评审信息Map
	* @apiSuccess {string} obj.sample_user_review_list.REVIEW_MAP.REVIEW_TYPE_0_GRADE 评审类型为0的评分值
	* @apiSuccess {object[]} obj.sample_user_review_list.REVIEW_MAP.REVIEW_TYPE_0_DLIST 类型为0的扩展项评审记录
	* @apiSuccess {string} obj.sample_user_review_list.REVIEW_MAP.REVIEW_TYPE_0_DLIST.REVIEW_FORM_NAME  类型为0的扩展项评审项名字  
	* @apiSuccess {string} obj.sample_user_review_list.REVIEW_MAP.REVIEW_TYPE_0_DLIST.REVIEW_CONTENT  类型为0的扩展项评审项值
	* 
    */
	@RequestMapping(value = "/user_review_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySampleUserReviewList(HttpServletRequest request) {
		return Transform.GetResult(sampleService.querySampleUserReviewList(request));
	}
	
	 /**
    *
    * @api {post} /{project_name}/sample/user_review_group_list 样品评审分析评审分组列表【分组查看】
    * @apiGroup sample
    * @apiDescription  样品评审分析评审分组列表
    * @apiVersion 0.0.1

	* @apiParam {string} itemnumber				样品货号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total   列表总数
    * @apiSuccess {object} obj   样品用户评审信息
    * @apiSuccess {object[]} obj.sample_review_type_list   					样品评审类型列表
    * @apiSuccess {string} obj.sample_review_type_list.type_name   			类型名称
    * @apiSuccess {object} obj.sample_review_type_list.grade_map			评分信息
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.one_star	一星数量
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.one_ratio	一星比例
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.two_star	二星数量
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.two_ratio	二星比例
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.three_star	三星数量
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.three_ratio三星比例
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.four_star	四星数量
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.four_ratio 四星比例
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.five_star	五星数量
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.five_ratio 五星比例
    * @apiSuccess {number} obj.sample_review_type_list.grade_map.average	平均评分
    * @apiSuccess {object[]} obj.sample_review_type_list.form_list			表单项列
    * @apiSuccess {string} obj.sample_review_type_list.form_list.template_form_name	表单项名
    * @apiSuccess {object[]} obj.sample_review_type_list.form_list.forList	评审内容列
    * @apiSuccess {string} obj.sample_review_type_list.form_list.forList.review_content	评审内容
    * @apiSuccess {number} obj.sample_review_type_list.form_list.forList.review_count	评审人数
    * @apiSuccess {number} obj.sample_review_type_list.form_list.forList.review_ratio	评审比例
	* 
    */
	@RequestMapping(value = "/user_review_group_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet querySampleUserReviewGroupList(HttpServletRequest request) {
		return Transform.GetResult(sampleService.querySampleUserReviewGroupList(request));
	}
	
   /**
    *
    * @api {post} /{project_name}/sample/user_suggestion_list 样品评审分析客户建议列表
    * @apiGroup sample
    * @apiDescription  样品评审分析客户建议列表
    * @apiVersion 0.0.1

	* @apiParam {string} itemnumber				样品货号
	* @apiParam {number} template_type_id		模板类型ID

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
    * @apiSuccess {number} total   列表总数
    * @apiSuccess {object} obj   样品用户评审信息
    * @apiSuccess {object[]} obj.user_suggestion_list   样品评审客户建议列表
    * @apiSuccess {string} obj.user_suggestion_list.create_user_name	用户名称
    * @apiSuccess {string} obj.user_suggestion_list.suggestion			建议
	* 
    */
	@RequestMapping(value = "/user_suggestion_list", method = RequestMethod.POST)
	@ResponseBody
	public Packet queryUserSuggestionList(HttpServletRequest request) {
		return Transform.GetResult(sampleService.queryUserSuggestionList(request));
	}
	
	/**
    *
    * @api {post} /{project_name}/sample/review_stop 样品评审分析终止评审
    * @apiGroup sample
    * @apiDescription  样品评审分析终止评审
    * @apiVersion 0.0.1

	* @apiParam {string} itemnumber				样品货号

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息说明
	* 
    */
	@RequestMapping(value = "/review_stop", method = RequestMethod.POST)
	@ResponseBody
	public Packet updateReviewStop(HttpServletRequest request) {
		return Transform.GetResult(sampleService.updateReviewStop(request));
	}
	
}
