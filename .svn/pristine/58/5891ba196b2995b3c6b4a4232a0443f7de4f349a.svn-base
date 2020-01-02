package com.tk.oms.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.product.dao.SampleDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("SampleService")
public class SampleService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private SampleDao sampleDao;
	/**
	 * 样品审批列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySampleApprovalList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if (paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
				String[] state = paramMap.get("state").toString().split(",");
				if (state.length > 1) {
					paramMap.put("state", paramMap.get("state"));
				} else {
					paramMap.put("state", paramMap.get("state").toString().split(","));
				}
			}
			int count = sampleDao.querySampleApprovalCount(paramMap);
			List<Map<String, Object>> list = sampleDao.querySampleApprovalList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 样品审批详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySampleApprovalDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取样品详情基本信息
			Map<String,Object> detail = sampleDao.querySampleApprovalDetail(paramMap);
			if(detail!=null){
				//获取样品内容
				List<Map<String,Object>> typeList = sampleDao.queryTemplateTypeList(paramMap);
				for(Map<String,Object> map : typeList){
					paramMap.put("type_id", map.get("ID"));
					map.put("form_list", sampleDao.queryTemplateFormList(paramMap));
					map.put("pic_list", sampleDao.querySampleReviewPicList(paramMap));
				}
				resultMap.put("base_info", detail);
				resultMap.put("type_list", typeList);
				pr.setMessage("获取数据成功");
				pr.setObj(resultMap);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 样品审批
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult check(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))||StringUtils.isEmpty(paramMap.get("public_user_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//获取样品详情基本信息
			Map<String,Object> detail = sampleDao.querySampleApprovalDetail(paramMap);
			if(detail!=null){
				if("3".equals(detail.get("STATE"))){
					pr.setState(false);
					pr.setMessage("样品已审核");
					return pr;
				}else if("4".equals(detail.get("STATE"))){
					pr.setState(false);
					pr.setMessage("样品已驳回");
					return pr;
				}else{
					//更新待审批记录
					if(sampleDao.updateSampleApprovalState(paramMap) > 0){
						pr.setState(true);
						pr.setMessage("操作成功");
					}else{
						pr.setState(true);
						pr.setMessage("操作失败");
					}
				}
			}else{
				pr.setState(false);
				pr.setMessage("样品不存在");
				return pr;
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取待审批商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult reviewUserList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			List<Map<String, Object>> list = sampleDao.querySampleReviewUserList(paramMap);
			int count = sampleDao.querySampleReviewUserCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 样品评审用户组新增
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reviewUserAdd(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}

			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_group_name") || StringUtils.isEmpty(paramMap.get("user_group_name"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_name");
				return pr;
			}
			if (!paramMap.containsKey("brand_ids") || StringUtils.isEmpty(paramMap.get("brand_ids"))) {
				pr.setState(false);
				pr.setMessage("缺少参数brand_ids");
				return pr;
			}
			List<Map<String,Object>> brand_ids = (List<Map<String,Object>>)paramMap.get("brand_ids");
			if(brand_ids.isEmpty()||brand_ids.size()<1){
				pr.setState(false);
				pr.setMessage("缺少参数,品牌列表为空");
				return pr;
			}
			//判断当前分组名称是否被使用
			int count=sampleDao.queryReviewUserGroupByGroupName(paramMap);
			if(count>0){
				pr.setState(false);
				pr.setMessage("当前分组名称已经被使用,请更换");
				return pr;
			}
			//新增样品审批用户组主表信息
			int num  = sampleDao.insertReviewUserGroup(paramMap);
			if(num<1){
				throw new RuntimeException("新增样品审批用户组主表信息失败");
			}
			//给每个品牌封装分组id
			for(Map<String,Object> brand_temp:brand_ids){
				brand_temp.put("user_group_id", paramMap.get("id"));
			}
			//新增样品审批用户组品牌信息
			 num = sampleDao.insertReviewGroupBrand(brand_ids);
			if(num<1){
				throw new RuntimeException("新增样品审批用户组品牌信息失败");
			}
			pr.setMessage("新增成功");
			pr.setState(true);
			pr.setObj(paramMap.get("id"));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询样品评审用户组关联会员
	 */
	@SuppressWarnings("unchecked")
	public GridResult reviewGroupUser(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("review_user_group_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[review_user_group_id]");
                return gr;
            }
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			List<Map<String, Object>> list = sampleDao.queryReviewGroupUserList(paramMap);
			int count = sampleDao.queryReviewGroupUserCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 样品评审用户组明细新增
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reviewUserDetailAdd(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}

			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_list") || StringUtils.isEmpty(paramMap.get("user_list"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_list");
				return pr;
			}
			if (!paramMap.containsKey("user_group_id") || StringUtils.isEmpty(paramMap.get("user_group_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_id");
				return pr;
			}
			List<Map<String,Object>> user_list = (List<Map<String,Object>>)paramMap.get("user_list");
			if(user_list.isEmpty()||user_list.size()<1){
				pr.setState(false);
				pr.setMessage("缺少参数,会员列表为空");
				return pr;
			}
			//给每个会员封装用户组id
			for(Map<String,Object> member_temp:user_list){
				member_temp.put("user_group_id", paramMap.get("user_group_id"));
			}
			
			//新增样品审批用户组明细信息
			int num  = sampleDao.insertReviewUserGroupDetail(user_list);
			if(num<1){
				throw new RuntimeException("新增样品审批用户组明细信息失败");
			}
			pr.setMessage("新增成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 样品评审用户组明细删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reviewUserDetailRemove(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}

			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_list") || StringUtils.isEmpty(paramMap.get("user_list"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_list");
				return pr;
			}
			if (!paramMap.containsKey("user_group_id") || StringUtils.isEmpty(paramMap.get("user_group_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_id");
				return pr;
			}
			List<Map<String,Object>> user_list = (List<Map<String,Object>>)paramMap.get("user_list");
			if(user_list.isEmpty()||user_list.size()<1){
				pr.setState(false);
				pr.setMessage("缺少参数,会员列表为空");
				return pr;
			}
			
			//新增样品审批用户组明细信息
			int num  = sampleDao.deleteReviewUserGroupDetail(paramMap);
			if(num<1){
				throw new RuntimeException("删除样品审批用户组明细信息失败");
			}
			pr.setMessage("删除成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 样品评审用户组编辑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reviewUserEdit(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_group_name") || StringUtils.isEmpty(paramMap.get("user_group_name"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_name");
				return pr;
			}
			if (!paramMap.containsKey("user_group_id") || StringUtils.isEmpty(paramMap.get("user_group_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_id");
				return pr;
			}
			paramMap.put("id", paramMap.get("user_group_id"));
			//判断当前分组名称是否被使用
			int count=sampleDao.queryReviewUserGroupByGroupName(paramMap);
			if(count>0){
				pr.setState(false);
				pr.setMessage("当前分组名称已经被使用,请更换");
				return pr;
			}
			//更新主表信息
			int num =sampleDao.editReviewUserGroup(paramMap);
			if(num<1){
				throw new RuntimeException("修改样品评审信息主表失败");
			}
			if(paramMap.containsKey("brand_ids") && !StringUtils.isEmpty(paramMap.get("brand_ids"))){
				List<Map<String,Object>> brand_ids = (List<Map<String,Object>>)paramMap.get("brand_ids");
				if(brand_ids.size()>0){
					//刪除分组品牌信息
					num =sampleDao.deleteReviewGroupBrandByGroupId(paramMap);
					if(num<1){
						throw new RuntimeException("删除样品评审分组品牌信息失败");
					}
					//给每个品牌封装分组id
					for(Map<String,Object> brand_temp:brand_ids){
						brand_temp.put("user_group_id", paramMap.get("user_group_id"));
					}
					//新增样品审批用户组品牌信息
					 num = sampleDao.insertReviewGroupBrand(brand_ids);
					if(num<1){
						throw new RuntimeException("新增样品审批用户组品牌信息失败");
					}
				}
			}
			pr.setMessage("修改成功");
			pr.setState(true);
			pr.setObj(paramMap.get("user_group_id"));
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 样品评审用户组删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reviewUserRemove(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_group_id") || StringUtils.isEmpty(paramMap.get("user_group_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_id");
				return pr;
			}
			Map<String, Object> user_group_detail=sampleDao.queryReviewUserGroupDetail(paramMap);
			if(user_group_detail == null){
                pr.setState(false);
                pr.setMessage("当前用户组不存在");
                return pr;
            }
			//判断当前用户组状态(启用状态不允许删除)
            if(Integer.parseInt(user_group_detail.get("STATE").toString())==2){
                pr.setState(false);
                pr.setMessage("启用状态用户组不允许删除");
                return pr;
            }
			//删除样品评审分组主表信息
			int num =sampleDao.deleteReviewUserGroupByGroupId(paramMap);
			if(num<1){
				throw new RuntimeException("删除样品评审分组主表信息失败");
			}
			//根据分组id查询当前分组下的用户数量
			int count=sampleDao.queryReviewUserGroupDetailCountByGroupId(paramMap);
			if(count>0){
				//删除样品评审分组用户信息
				num =sampleDao.deleteReviewUserGroupDetailByGroupId(paramMap);
				if(num<1){
					throw new RuntimeException("删除样品评审分组用户信息失败");
				}
			}
			//删除样品评审分组品牌信息
			num =sampleDao.deleteReviewGroupBrandByGroupId(paramMap);
			if(num<1){
				throw new RuntimeException("删除样品评审分组品牌信息失败");
			}
			pr.setMessage("修改成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 *样品评审用户组启用/禁用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reviewUserSwitch(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("user_group_id") || StringUtils.isEmpty(paramMap.get("user_group_id"))) {
				pr.setState(false);
				pr.setMessage("缺少参数user_group_id");
				return pr;
			}
			if (!paramMap.containsKey("state") || StringUtils.isEmpty(paramMap.get("state"))) {
				pr.setState(false);
				pr.setMessage("缺少参数state");
				return pr;
			}
			//修改用户组状态
			int num=sampleDao.updateSampleReviewUserGroupState(paramMap);
			if(num>0){
				pr.setMessage("修改成功");
				pr.setState(true);
			}else{
				pr.setMessage("修改失败");
				pr.setState(false);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询样品评审用户组详情
	 */
	@SuppressWarnings("unchecked")
	public GridResult reviewUserDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("user_group_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数[user_group_id]");
                return gr;
            }
			Map<String, Object> detail_info = sampleDao.queryReviewUserGroupDetail(paramMap);
			if (detail_info!=null) {
				gr.setMessage("获取数据成功");
				gr.setObj(detail_info);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	
	/**
	 * 样品评审列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySampleReviewList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if (paramMap.containsKey("review_state") && !StringUtils.isEmpty(paramMap.get("review_state"))) {
				String[] review_state = paramMap.get("review_state").toString().split(",");
				if (review_state.length > 1) {
					paramMap.put("review_state", paramMap.get("review_state"));
				} else {
					paramMap.put("review_state", paramMap.get("review_state").toString().split(","));
				}
			}
			int count = sampleDao.querySampleReviewCount(paramMap);
			List<Map<String, Object>> list = sampleDao.querySampleReviewList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 样品用户评审列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySampleUserReviewList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if (StringUtils.isEmpty(paramMap.get("itemnumber"))) {
				gr.setMessage("缺少参数");
				gr.setState(false);
				return gr;
			}
			//返回结果Map
			Map<String,Object> returnMap = new HashMap<String,Object>();
			//返回结果
			int count = sampleDao.querySampleUserReviewCount(paramMap);
			//评审用户列表
			List<Map<String, Object>> list = sampleDao.querySampleUserReviewList(paramMap);
			if (list != null && list.size() > 0) {
				for(Map<String, Object> reviewUserMap:list){
					//每个评审用户的具体评审信息[基础配置项评审列表]
					List<Map<String, Object>> reviewTypeBaseItemReviewList = sampleDao.querySampleReviewTypeBaseItemReviewList(reviewUserMap);
					//每个评审用户的具体评审信息[扩展配置项评审列表]
					List<Map<String, Object>> reviewTypeExtendItemReviewList = sampleDao.querySampleReviewTypeExtendItemReviewList(reviewUserMap);
					Map<String,Object> reviewMap = createUserReviewMap(reviewTypeBaseItemReviewList,reviewTypeExtendItemReviewList);
					//放入当前用户评审信息
					reviewUserMap.put("REVIEW_MAP", reviewMap);
				}
				returnMap.put("sample_user_review_list", list);
				gr.setMessage("获取数据成功");
			} else {
				gr.setMessage("无数据");
			}
			//获取样品评审类型列表
			List<Map<String, Object>> reviewTypeList = sampleDao.querySampleReviewTypeList(paramMap);
			returnMap.put("sample_review_type_list", reviewTypeList);
			gr.setObj(returnMap);
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 生成用户评审信息Map
	 * @param reviewTypeBaseItemReviewList 基础配置项评审列表
	 * @param reviewTypeExtendItemReviewList 扩展配置项评审列表
	 * @return 用户评审信息Map
	 */
	private Map<String, Object> createUserReviewMap(List<Map<String, Object>> reviewTypeBaseItemReviewList,List<Map<String, Object>> reviewTypeExtendItemReviewList) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(reviewTypeBaseItemReviewList!=null&&reviewTypeBaseItemReviewList.size()>0){
			for(Map<String, Object> reviewTypeBaseItemReviewMap:reviewTypeBaseItemReviewList){
				returnMap.put("REVIEW_TYPE_"+((BigDecimal)reviewTypeBaseItemReviewMap.get("REVIEW_TYPE_ID")).intValue()+"_GRADE", reviewTypeBaseItemReviewMap.get("GRADE"));
				returnMap.put("REVIEW_TYPE_"+((BigDecimal)reviewTypeBaseItemReviewMap.get("REVIEW_TYPE_ID")).intValue()+"_SUGGESTION", reviewTypeBaseItemReviewMap.get("SUGGESTION"));
			}
		}
		if(reviewTypeExtendItemReviewList!=null&&reviewTypeExtendItemReviewList.size()>0){
			List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
			String tempReviewTypeId = "";
			for(Map<String, Object> reviewTypeExtendItemReviewMap:reviewTypeExtendItemReviewList){
				String reviewTypeId = ((BigDecimal)reviewTypeExtendItemReviewMap.get("REVIEW_TYPE_ID")).intValue()+"";
				//第一次去类型id
				if("".equals(tempReviewTypeId)){
					tempReviewTypeId = reviewTypeId;
					tempList.add(reviewTypeExtendItemReviewMap);
				}else{
					if(!reviewTypeId.equals(tempReviewTypeId)){
						//取的类型id和之前的类型id不一致时，先将之前的评审列表存储，同时生成新的临时列表
						returnMap.put("REVIEW_TYPE_"+tempReviewTypeId+"_DLIST", tempList);
						tempList = new ArrayList<Map<String,Object>>();
						tempReviewTypeId = reviewTypeId;
					}else{
						tempList.add(reviewTypeExtendItemReviewMap);
					}
				}
			}
			returnMap.put("REVIEW_TYPE_"+tempReviewTypeId+"_DLIST", tempList);
		}
		return returnMap;
	}
	
	
	/**
	 * 样品分析列表【按分组查看】
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySampleUserReviewGroupList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("itemnumber"))) {
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			if(sampleDao.querySampleUserReviewCount(paramMap)==0){
				pr.setMessage("无数据");
				pr.setState(true);
				return pr;
			}
			//评审类型列表
			List<Map<String, Object>> reviewTypeList = sampleDao.querySampleReviewTypeList(paramMap);
			if (reviewTypeList != null && reviewTypeList.size() > 0) {
				for(Map<String, Object> reviewTypeMap:reviewTypeList){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("itemnumber", paramMap.get("itemnumber"));
					map.put("template_type_id", reviewTypeMap.get("ID"));
					//查询评分信息
					reviewTypeMap.put("grade_map", sampleDao.queryGrade(map));
					//查询评审项明细列表
					List<Map<String, Object>> form_list = sampleDao.querySampleReviewTypeDetailList(map);
					if(form_list != null && form_list.size() > 0){
						for(Map<String, Object> formMap : form_list){
							String review_contents =(String) formMap.get("TEMPLATE_FORM_OPTION");
							List<Map<String, Object>> forList = new ArrayList<Map<String, Object>>();
							if(!StringUtils.isEmpty(review_contents)){
								String[] review_content = review_contents.split(",");
								for(int i = 0; i < review_content.length; i++){
									Map<String, Object> formap = new HashMap<String, Object>();
									formap.put("itemnumber", paramMap.get("itemnumber"));
									formap.put("sample_review_detail_id", formMap.get("ID"));
									formap.put("review_content", review_content[i]);
									//查询反馈内容
									forList.add(sampleDao.queryReviewContent(formap));
								}
							}
							formMap.put("forList", forList);
						}
						reviewTypeMap.put("form_list", form_list);
					}
				}
				pr.setObj(reviewTypeList);
				pr.setMessage("获取数据成功");
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 客户建议列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryUserSuggestionList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("itemnumber"))||StringUtils.isEmpty(paramMap.get("template_type_id"))) {
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//客户建议列表
			List<Map<String, Object>> userSugList = sampleDao.queryUserSuggestionList(paramMap);
			if (userSugList != null && userSugList.size() > 0) {
				pr.setObj(userSugList);
				pr.setMessage("获取数据成功");
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 终止评审
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult updateReviewStop(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("itemnumber"))) {
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//终止评审
			if (sampleDao.updateReviewStop(paramMap) > 0) {
				pr.setState(true);
				pr.setMessage("终止成功");
			} else {
				pr.setState(false);
				pr.setMessage("终止失败");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
		
}
