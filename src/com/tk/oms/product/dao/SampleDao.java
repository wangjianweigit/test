package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface SampleDao {
	/**
	 * 样品审批数量
	 * @param paramMap
	 * @return
	 */
	public int querySampleApprovalCount(Map<String, Object> paramMap);
	/**
	 * 样品审批列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleApprovalList(Map<String, Object> paramMap);
	/**
	 * 样品审批详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> querySampleApprovalDetail(Map<String, Object> paramMap);
	/**
	 * 样品参数列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleParamsList(Map<String, Object> paramMap);
	/**
	 * 样品SKU列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleSkuList(Map<String, Object> paramMap);
	/**
	 * 样品图片列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleImagesList(Map<String, Object> paramMap);
	/**
	 * 样品评审类型
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryTemplateTypeList(Map<String, Object> paramMap);
	/**
	 * 样品评审表单项
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryTemplateFormList(Map<String, Object> paramMap);
	/**
	 * 样品评审图片列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewPicList(Map<String, Object> paramMap);
	/**
	 * 审批
	 * @param paramMap
	 * @return
	 */
	public int updateSampleApprovalState(Map<String, Object> paramMap);
	/**
	 * 样品评审用户组数量
	 * @param paramMap
	 * @return
	 */
	public int querySampleReviewUserCount(Map<String, Object> paramMap);
	/**
	 * 样品评审用户组列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewUserList(Map<String, Object> paramMap);
	/**
	 * 样品评审用户组新增
	 * @param paramMap
	 * @return
	 */
	public int insertReviewUserGroup(Map<String, Object> paramMap);
	/**
	 * 新增样品审批用户组明细
	 * @param paramMap
	 * @return
	 */
	public int insertReviewUserGroupDetail(List<Map<String, Object>> user_list);
	/**
	 * 新增样品审批用户组品牌信息
	 * @param paramMap
	 * @return
	 */
	public int insertReviewGroupBrand(List<Map<String, Object>> group_brand_list);
	/**
	 * 查询样品评审用户组关联会员数量
	 * @param paramMap
	 * @return
	 */
	public int queryReviewGroupUserCount(Map<String, Object> paramMap);
	/**
	 * 查询样品评审用户组关联会员列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryReviewGroupUserList(Map<String, Object> paramMap);
	/**
	 *样品评审用户组明细删除
	 * @param paramMap
	 * @return
	 */
	public int deleteReviewUserGroupDetail(Map<String, Object> paramMap);
	/**
	 * 修改样品评审用户组主表信息
	 * @param paramMap
	 * @return
	 */
	public int editReviewUserGroup(Map<String, Object> paramMap);
	/**
	 * 根据分组ID删除样品评审主表数据
	 * @param paramMap
	 * @return
	 */
	public int deleteReviewUserGroupByGroupId(Map<String, Object> paramMap);
	/**
	 * 根据分组ID删除样品评审分组用户信息
	 * @param paramMap
	 * @return
	 */
	public int deleteReviewUserGroupDetailByGroupId(Map<String, Object> paramMap);
	/**
	 * 根据分组ID删除样品评审分组品牌信息
	 * @param paramMap
	 * @return
	 */
	public int deleteReviewGroupBrandByGroupId(Map<String, Object> paramMap);
	/**
	 * 判断当前分组名称是否被使用
	 * @param paramMap
	 * @return
	 */
	public int queryReviewUserGroupByGroupName(Map<String, Object> paramMap);
	/**
	 * 根据分组id查询当前分组下的用户数量
	 * @param paramMap
	 * @return
	 */
	public int queryReviewUserGroupDetailCountByGroupId(Map<String, Object> paramMap);
	/**
	 * 更新用户组启用/禁用状态
	 * @param paramMap
	 * @return
	 */
	public int updateSampleReviewUserGroupState(Map<String, Object> paramMap);
	/**
	 * 查询样品评审用户组主表详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryReviewUserGroupDetail(Map<String, Object> paramMap);
	/**
	 * 样品评审数量
	 * @param paramMap
	 * @return
	 */
	public int querySampleReviewCount(Map<String, Object> paramMap);
	/**
	 * 样品评审列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewList(Map<String, Object> paramMap);
	/**
	 * 样品用户评审数量
	 * @param paramMap
	 * @return
	 */
	public int querySampleUserReviewCount(Map<String, Object> paramMap);
	/**
	 * 样品用户评审列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleUserReviewList(Map<String, Object> paramMap);
	/**
	 * 样品评审类型列表【评审分析用】
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewTypeList(Map<String, Object> paramMap);
	/**
	 * 样品评审类型基本配置项评审列表【评审分析用】
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewTypeBaseItemReviewList(Map<String, Object> paramMap);
	/**
	 * 样品评审类型扩展配置项评审列表【评审分析用】
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewTypeExtendItemReviewList(Map<String, Object> paramMap);
	/**
	 * 查询评分信息【评审分析用】
	 * @param map
	 * @return
	 */
	public Map<String, Object> queryGrade(Map<String, Object> map);
	/**
	 * 查询评审项明细列表【评审分析用】
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySampleReviewTypeDetailList(Map<String, Object> paramMap);
	/**
	 * 查询反馈内容 【评审分析用】
	 * @param formap
	 * @return
	 */
	public Map<String, Object> queryReviewContent(Map<String, Object> formap);
	/**
	 * 客户建议列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryUserSuggestionList(Map<String, Object> paramMap);
	/**
	 * 终止评审
	 * @param paramMap
	 * @return
	 */
	public int updateReviewStop(Map<String, Object> paramMap);
}
