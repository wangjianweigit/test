package com.tk.oms.marketing.service;

import com.tk.oms.marketing.dao.ActivityDao;
import com.tk.oms.marketing.dao.ActivityDetailDao;
import com.tk.oms.marketing.dao.ActivityProductDao;
import com.tk.sys.util.*;
import com.tk.oms.product.entity.KeywordTypeEnum;
import com.tk.oms.product.entity.OperationTypeEnum;
import com.tk.oms.analysis.dao.ProductOperationLogDao;
import com.tk.oms.product.entity.ProductOperationLog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 促销活动商品
 * @author Administrator
 *
 */
@Service("ActivityProductService")
public class ActivityProductService {

	@Resource
	private ActivityProductDao activityProductDao;
	@Resource
	private ActivityDetailDao activityDetailDao;
	@Resource
	private ActivityDao activityDao;
    @Resource
    private ProductOperationLogDao productOperationLogDao;
    
	@Value("${oss_service_url}")
	private String oss_service_url;//oss服务地址
	@Value("${file_watermark_image}")
	private String file_watermark_image;//为商品图片生成水印图片
	/**
	 * 获取参与促销活动的商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryActivityProductList(HttpServletRequest request) {
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

			//获取促销活动基本信息
			paramMap.put("id",paramMap.get("activity_id"));
			Map<String,Object> detail = this.activityDao.queryActivityDetail(paramMap);
			if(detail == null){
				gr.setState(false);
				gr.setMessage("活动不存在或已删除");
				return gr;
			}
			List<Map<String, Object>> list = activityProductDao.queryActivityProductList(paramMap);
			int count = activityProductDao.queryActivityProductCount(paramMap);
			if (list != null && list.size() > 0) {
				if ("5".equals(detail.get("ACTIVITY_TYPE").toString())) {
					paramMap.put("list", list);
					List<Map<String, Object>> ladderPriceList = activityProductDao.listProductLadderDiscountById(paramMap);
					for (Map<String, Object> map : list) {
						String id = map.get("ID").toString();
						List<Map<String, Object>> newLadderPriceList = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> ladderPriceMap : ladderPriceList) {
							String ativityProductId = ladderPriceMap.get("ACTIVITY_PRODUCT_ID").toString();
							if (id.equals(ativityProductId)) {
								newLadderPriceList.add(ladderPriceMap);
							}
						}
						map.put("LADDER_LIST", newLadderPriceList);
					}
				}
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			e.printStackTrace();
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 获取参与促销活动的待审批商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryPendingApprovalActivityProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("states")))&&paramMap.get("states") instanceof String){
				paramMap.put("states",(paramMap.get("states")+"").split(","));
			}
			List<Map<String, Object>> list = activityProductDao.queryPendingApprovalActivityProductList(paramMap);
			int count = activityProductDao.queryPendingApprovalActivityProductCount(paramMap);
			if (list != null && list.size() > 0) {
				if (paramMap.containsKey("activity_type") && !StringUtils.isEmpty(paramMap.get("activity_type")) && "5".equals(paramMap.get("activity_type").toString())) {
					paramMap.put("list", list);
					List<Map<String, Object>> ladderPriceList = activityProductDao.listProductLadderDiscountById(paramMap);
					for (Map<String, Object> map : list) {
						String id = map.get("ID").toString();
						List<Map<String, Object>> newLadderPriceList = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> ladderPriceMap : ladderPriceList) {
							String ativityProductId = ladderPriceMap.get("ACTIVITY_PRODUCT_ID").toString();
							if (id.equals(ativityProductId)) {
								newLadderPriceList.add(ladderPriceMap);
							}
						}
						map.put("LADDER_LIST", newLadderPriceList);
					}
				}
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			e.printStackTrace();
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
		
	/**
	 * 促销活动商品审批通过
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult activityProductApproved(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("public_user_id"))){
						pr.setMessage("缺少参数");
						pr.setState(false);
						return pr;
			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> pendingApprovalActivityProductDetailMap=activityProductDao.queryPendingApprovalActivityProductDetail(paramMap);
			if(pendingApprovalActivityProductDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"2".equals(pendingApprovalActivityProductDetailMap.get("STATE"))){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//验证当前商品的活动时间是否已结束
			Map<String, Object> stateMap = new HashMap<String, Object>();
			stateMap.put("id", paramMap.get("id"));
			stateMap.put("state", "is_end");
			if(activityProductDao.queryIsExistProductByState(stateMap) > 0) {
				throw new RuntimeException("当前商品不在活动期间");
			}

			//4.校验活动报名时间是否已截止
			int check_apply_date_count= activityProductDao.checkActivityApplyDateCount(paramMap);
			if(check_apply_date_count <= 0){
				throw new RuntimeException("当前商品报名时间不在活动报名期间");
			}
			//当为限时折扣类活动时
			//5.1校验活动商品报名数量是否已达上限
			int check_count= activityProductDao.checkActivityProductCount(paramMap);
			if(check_count <= 0){
				throw new RuntimeException("当前活动入驻商报名商品数已达上限");
			}
			//6.活动商品审批通过后转移到主表
			int num=activityProductDao.updateActivityProductFromPendingApprovalActivityProduct(paramMap);
			paramMap.put("activity_id", pendingApprovalActivityProductDetailMap.get("ACTIVITY_ID"));
			paramMap.put("product_itemnumber", pendingApprovalActivityProductDetailMap.get("PRODUCT_ITEMNUMBER"));
			num += activityProductDao.updateActivityProductSkuFromPendingApprovalActivityProductSku(paramMap);

			//复制预售活动商品SKU数据
			num += activityProductDao.updatePreSellActivitySkuFromApprovalActivityProductSku(paramMap);
			//7.更新待审批记录为审批通过
			paramMap.put("state", "3");
			num = activityProductDao.updatePendingApprovalActivityProductState(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
            /**
             * 记录商品操作日志
             */
            ProductOperationLog productOperationLog = new ProductOperationLog();
            productOperationLog.setProductItemnumber(paramMap.get("product_itemnumber").toString());
            productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
            productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
            productOperationLog.setKeywordType(KeywordTypeEnum.ACTIVITY_APPROVE.value);
            productOperationLog.setKeywordName(KeywordTypeEnum.ACTIVITY_APPROVE.des);
            productOperationLog.setBusinessId(Long.parseLong(paramMap.get("activity_id").toString()));
            productOperationLog.setBusinessName(pendingApprovalActivityProductDetailMap.get("ACTIVITY_NAME").toString());
            productOperationLog.setRemark("商品货号为" + paramMap.get("product_itemnumber") + ",活动审批通过");
            productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
            productOperationLog.setOperatorPlatform(2);
            this.productOperationLogDao.insertProductOperationLog(productOperationLog);

			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 待审批促销活动商品审批驳回
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult activityProductRejected(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("public_user_id"))
			   ||StringUtils.isEmpty(paramMap.get("approval_remark"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//TODO 20170411-002 1.验证操作用户是否存在
//			Map<String,Object> operateUserMap=userDao.queryUserDetail(paramMap);
//			if(operateUserMap==null){
//				pr.setMessage("操作用户不存在，请检查");
//				pr.setState(false);
//				return pr;
//			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> pendingApprovalActivityProductDetailMap=activityProductDao.queryPendingApprovalActivityProductDetail(paramMap);
			if(pendingApprovalActivityProductDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"2".equals(pendingApprovalActivityProductDetailMap.get("STATE"))){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//验证当前商品的活动时间是否已结束
			Map<String, Object> stateMap = new HashMap<String, Object>();
			stateMap.put("id", paramMap.get("id"));
			stateMap.put("state", "is_end");
			if(activityProductDao.queryIsExistProductByState(stateMap) > 0) {
				throw new RuntimeException("当前商品不在活动期间");
			}

			//3.更新待审批记录为审批驳回
			paramMap.put("state", "4");
			int num = activityProductDao.updatePendingApprovalActivityProductState(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}

            /**
             * 记录商品操作日志
             */
            ProductOperationLog productOperationLog = new ProductOperationLog();
            productOperationLog.setProductItemnumber(pendingApprovalActivityProductDetailMap.get("PRODUCT_ITEMNUMBER").toString());
            productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
            productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
            productOperationLog.setKeywordType(KeywordTypeEnum.ACTIVITY_APPROVE.value);
            productOperationLog.setKeywordName(KeywordTypeEnum.ACTIVITY_APPROVE.des);
            productOperationLog.setBusinessId(Long.parseLong(pendingApprovalActivityProductDetailMap.get("ACTIVITY_ID").toString()));
            productOperationLog.setBusinessName(pendingApprovalActivityProductDetailMap.get("ACTIVITY_NAME").toString());
            productOperationLog.setRemark("商品货号为" + pendingApprovalActivityProductDetailMap.get("PRODUCT_ITEMNUMBER") + ",活动审批驳回。原因："+paramMap.get("approval_remark"));
            productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
            productOperationLog.setOperatorPlatform(2);
            this.productOperationLogDao.insertProductOperationLog(productOperationLog);

			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	
	/**
	 * 获取参与促销活动的商品sku列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityProductSkuList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty("activity_id")||StringUtils.isEmpty("product_itemnumber")){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			List<Map<String, Object>> list = activityProductDao.queryActivityProductSkuList(paramMap);
			if (list != null && list.size() > 0) {
				pr.setMessage("获取数据成功");
				pr.setObj(list);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取参与促销活动的待审批商品sku列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPendingApprovalActivityProductSkuList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty("activity_id")||StringUtils.isEmpty("product_itemnumber")){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			List<Map<String, Object>> list = activityProductDao.queryPendingApprovalActivityProductSkuList(paramMap);
			if (list != null && list.size() > 0) {
				pr.setMessage("获取数据成功");
				pr.setObj(list);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取参与促销活动的商品sku库存列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityProductSkuStockList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty("activity_id")||StringUtils.isEmpty("product_itemnumber")){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			List<Map<String, Object>> list = activityProductDao.queryActivityProductSkuStockList(paramMap);
			if (list != null && list.size() > 0) {
				pr.setMessage("获取数据成功");
				pr.setObj(list);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 待审批促销活动商品审批驳回
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult activityProductSort(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))||StringUtils.isEmpty(paramMap.get("public_user_id"))
			   ||StringUtils.isEmpty(paramMap.get("sort_product_list"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//TODO 20170411-002 1.验证操作用户是否存在
//			Map<String,Object> operateUserMap=userDao.queryUserDetail(paramMap);
//			if(operateUserMap==null){
//				pr.setMessage("操作用户不存在，请检查");
//				pr.setState(false);
//				return pr;
//			}
			List<Map<String,Object>> sortProductList=(List<Map<String,Object>>)paramMap.get("sort_product_list");
			if(sortProductList.size() < 1){
				pr.setMessage("参数错误【sort_product_list】");
				pr.setState(false);
				return pr;
			}
			//2.排序
			int num = activityProductDao.updateActivityProductSortId(sortProductList);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 促销活动商品强制退出
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult activityProductForceQuit(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_product_ids"))||StringUtils.isEmpty(paramMap.get("public_user_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//TODO 20170411-002 1.验证操作用户是否存在
//			Map<String,Object> operateUserMap=userDao.queryUserDetail(paramMap);
//			if(operateUserMap==null){
//				pr.setMessage("操作用户不存在，请检查");
//				pr.setState(false);
//				return pr;
//			}
			List<Integer> quitActivityProductList=(List<Integer>)paramMap.get("activity_product_ids");
			if(quitActivityProductList.size() < 1){
				pr.setMessage("参数错误【activity_product_ids】");
				pr.setState(false);
				return pr;
			}
			//2.清除活动商品【先清除sku，再清除商品】
			activityProductDao.deleteActivityProductSku(paramMap);
			int num_product = activityProductDao.deleteActivityProduct(paramMap);
			activityProductDao.deleteActivityProductSkuApply(paramMap);
			int num_product_apply = activityProductDao.deleteActivityProductApply(paramMap);
			if(num_product <= 0 || num_product_apply <=0){
				throw new RuntimeException("操作失败");
			}
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 预售活动预售数量列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPreSellActivityProductSkuList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty("activity_id")
					||StringUtils.isEmpty("product_itemnumber")
					||StringUtils.isEmpty("approval")
					){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			List<Map<String, Object>> list = activityDetailDao.queryActivityProductSkuList(paramMap);
			if (list != null && list.size() > 0) {
				pr.setMessage("获取数据成功");
				pr.setObj(list);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 活动商品审批(批量)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult approvalProduct(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ids"))){
				pr.setState(false);
				pr.setMessage("缺少参数[ids]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("state"))){
				pr.setState(false);
				pr.setMessage("缺少参数[state]");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("activity_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数activity_id");
				return pr;
			}
			List<String> ids= Arrays.asList(paramMap.get("ids").toString().split(","));
			paramMap.put("ids",ids);
			List<Map<String, Object>> activityProductList = activityProductDao.queryActivityProductApplyList(paramMap);
			if(Integer.parseInt(paramMap.get("state").toString())==3){//通过
				for(Map<String, Object> apMap : activityProductList) {
					//2.验证当前操作待审批记录状态是否正常
					if(!"2".equals(apMap.get("STATE"))){
						pr.setMessage("货号："+apMap.get("PRODUCT_ITEMNUMBER")+"操作记录状态异常，请检查");
						pr.setState(false);
						return pr;
					}
					paramMap.put("id", apMap.get("ID"));
					//验证当前商品的活动时间是否已结束
					Map<String, Object> stateMap = new HashMap<String, Object>();
					stateMap.put("id", paramMap.get("id"));
					stateMap.put("state", "is_end");
					if(activityProductDao.queryIsExistProductByState(stateMap) > 0) {
						throw new RuntimeException("当前商品不在活动期间");
					}
					//3.校验活动报名时间是否已截止
					if(activityProductDao.checkActivityApplyDateCount(paramMap) <= 0){
						throw new RuntimeException("货号："+apMap.get("PRODUCT_ITEMNUMBER")+"当前商品报名时间不在活动报名期间");
					}
					//当为限时折扣类活动时
					//4.校验活动商品报名数量是否已达上限
					if(activityProductDao.checkActivityProductCount(paramMap) <= 0){
						throw new RuntimeException("当前活动入驻商报名商品数已达上限");
					}
					//5.活动商品审批通过后转移到主表
					activityProductDao.updateActivityProductFromPendingApprovalActivityProduct(paramMap);
					paramMap.put("activity_id", apMap.get("ACTIVITY_ID"));
					paramMap.put("product_itemnumber", apMap.get("PRODUCT_ITEMNUMBER"));
					activityProductDao.updateActivityProductSkuFromPendingApprovalActivityProductSku(paramMap);

					//6.复制预售活动商品SKU数据
					activityProductDao.updatePreSellActivitySkuFromApprovalActivityProductSku(paramMap);
				}
			}
			
			//7.批量更新待审批状态
			if(activityProductDao.batchUpdateActivityProductApplyState(paramMap) <= 0){
				throw new RuntimeException("更新审批状态失败！");
			}
			
			pr.setState(true);
			pr.setMessage("审批活动商品成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("审批异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取商品最低活动价格
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult queryActivityProductPrice(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			Map<String, Object> priceMap = activityProductDao.getActivityProductPrice(paramMap);
			pr.setObj(priceMap);
			pr.setState(true);
			pr.setMessage("获取商品最低活动价格成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("审批异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 *  获取活动商品图片水印模板列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult queryActivityProductImgTemplateList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			List<Map<String, Object>> templateList = activityProductDao.queryActivityProductImgTemplateList(paramMap);
			pr.setObj(templateList);
			pr.setState(true);
			pr.setMessage("获取水印模板列表成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("获取水印模板列表异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 *  单个活动商品水印图片持久化
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult createWatermarkImgSingle(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			String activity_product_img_url = null;
			long activity_product_id = 0;
			int pc_template_flag = 0;//是否使用PC模板   1：使用  2：不使用
			long moblie_template_flag = 0;//是否使用微信模板   1：使用  2：不使用
			long pc_template_id = 0;//使用的PC模板ID
			long mobile_template_id = 0; //使用的微信模板ID
			try {
				//活动商品图片、活动商品ID、水印图片模板ID必传递
				if(!StringUtils.isEmpty(paramMap.get("activity_product_img_url"))
						&&!StringUtils.isEmpty(paramMap.get("activity_product_id"))
						&&!StringUtils.isEmpty(paramMap.get("pc_template_flag"))
						&&!StringUtils.isEmpty(paramMap.get("moblie_template_flag"))
						) {
					activity_product_img_url = paramMap.get("activity_product_img_url").toString();
					activity_product_id = Long.parseLong(paramMap.get("activity_product_id").toString());
					pc_template_flag = Integer.parseInt(paramMap.get("pc_template_flag").toString());
					moblie_template_flag = Integer.parseInt(paramMap.get("moblie_template_flag").toString());
				}
			}catch (Exception e) {
				activity_product_img_url = null;
				activity_product_id = 0;
				pc_template_flag = 0;
				moblie_template_flag = 0;
			}
			//活动商品图片、活动商品ID、水印图片模板ID必传递
			if(StringUtils.isEmpty(activity_product_img_url)
					||activity_product_id == 0
					||(pc_template_flag!=1 && pc_template_flag!=2)
					||(moblie_template_flag!=1 && moblie_template_flag!=2)
					) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			/**
			 * 如果PC、微信有选择模板，还需要判断模板ID
			 */
			if(pc_template_flag == 1) {
				try {
					pc_template_id = Long.parseLong(paramMap.get("pc_template_id").toString());
				}catch (Exception e) {
					pc_template_id = 0;
				}
				if(pc_template_id==0){
					pr.setState(false);
					pr.setMessage("参数【pc_template_id】错误");
					return pr;
				}
			}
			/**
			 * 如果PC、微信有选择模板，还需要判断模板ID
			 */
			if(moblie_template_flag == 1) {
				try {
					mobile_template_id = Long.parseLong(paramMap.get("moblie_template_id").toString());
				}catch (Exception e) {
					mobile_template_id = 0;
				}
				if(mobile_template_id==0){
					pr.setState(false);
					pr.setMessage("参数【pc_template_id】错误");
					return pr;
				}
			}
			String pc_watermark_img_url = null;
			String mobile_watermark_img_url = null;
			Map<String, Object> pc_watermark_template = null;
			Map<String, Object> mobile_watermark_template = null;
			Map<String, Object> product_price = activityProductDao.getActivityProductPrice(paramMap);
			if(pc_template_flag==1 && moblie_template_flag==2 && judgeImgSuffix(activity_product_img_url)) {//只有PC使用模板
				pc_watermark_template = activityProductDao.getActivityProductImgTemplateById(pc_template_id);
				pc_watermark_img_url = handleWatermarkImg(product_price, pc_watermark_template, activity_product_img_url);
			}else if(pc_template_flag==2 && moblie_template_flag==1 && judgeImgSuffix(activity_product_img_url)) {//只有微信使用模板
				mobile_watermark_template = activityProductDao.getActivityProductImgTemplateById(mobile_template_id);
				mobile_watermark_img_url = handleWatermarkImg(product_price, mobile_watermark_template, activity_product_img_url);
			}else if(pc_template_flag==1 && moblie_template_flag==1 && judgeImgSuffix(activity_product_img_url)){
				//如果PC、微信使用了相同的模板，则，只需要生成一次即可
				if(pc_template_id == mobile_template_id) {
					pc_watermark_template = activityProductDao.getActivityProductImgTemplateById(pc_template_id);
					pc_watermark_img_url = handleWatermarkImg(product_price, pc_watermark_template, activity_product_img_url);
					mobile_watermark_img_url = pc_watermark_img_url;
				}else {
					pc_watermark_template = activityProductDao.getActivityProductImgTemplateById(pc_template_id);
					mobile_watermark_template = activityProductDao.getActivityProductImgTemplateById(mobile_template_id);
					pc_watermark_img_url = handleWatermarkImg(product_price, pc_watermark_template, activity_product_img_url);
					mobile_watermark_img_url = handleWatermarkImg(product_price, mobile_watermark_template, activity_product_img_url);
				}
			}
			if(pc_template_flag==2 || !judgeImgSuffix(activity_product_img_url)) {
				pc_watermark_img_url = activity_product_img_url;
			}
			//不使用移动端模板，则移动端水印图直接使用活动图片
			if(moblie_template_flag==2 || !judgeImgSuffix(activity_product_img_url)) {
				mobile_watermark_img_url = activity_product_img_url;
			}
			paramMap.put("pc_watermark_img_url",pc_watermark_img_url);
			paramMap.put("mobile_watermark_img_url",mobile_watermark_img_url);
			paramMap.put("pc_template_id", pc_template_id);
			paramMap.put("mobile_template_id", mobile_template_id);
			//更新数据库
			activityProductDao.updateActivityProductImgApply(paramMap);
			if(activityProductDao.updateActivityProductImg(paramMap)<=0) {
				pr.setState(false);
				pr.setMessage("水印图片持久化失败");
				return pr;
			}
			pr.setState(true);
			pr.setMessage("水印图片持久化成功");
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("获取水印模板列表异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 *  批量活动商品水印图片持久化
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult createWatermarkImgBatch(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			List<Long> activity_product_ids = null;//批量的活动商品ID集合
			int pc_template_flag = 0;//是否使用PC模板   1：使用  2：不使用
			long moblie_template_flag = 0;//是否使用微信模板   1：使用  2：不使用
			long pc_template_id = 0;//使用的PC模板ID
			long mobile_template_id = 0; //使用的微信模板ID
			try {
				//活动商品图片、活动商品ID、水印图片模板ID必传递
				if(!StringUtils.isEmpty(paramMap.get("activity_product_ids"))
						&&!StringUtils.isEmpty(paramMap.get("pc_template_flag"))
						&&!StringUtils.isEmpty(paramMap.get("moblie_template_flag"))
						) {
					activity_product_ids = (List<Long>) paramMap.get("activity_product_ids");
					pc_template_flag = Integer.parseInt(paramMap.get("pc_template_flag").toString());
					moblie_template_flag = Integer.parseInt(paramMap.get("moblie_template_flag").toString());
				}
			}catch (Exception e) {
				activity_product_ids = null;
				pc_template_flag = 0;
				moblie_template_flag = 0;
			}
			//活动商品图片、活动商品ID、水印图片模板ID必传递
			if(activity_product_ids==null
					||activity_product_ids.isEmpty()
					||(pc_template_flag!=1 && pc_template_flag!=2)
					||(moblie_template_flag!=1 && moblie_template_flag!=2)
					) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			/**
			 * 如果PC、微信有选择模板，还需要判断模板ID
			 */
			if(pc_template_flag == 1) {
				try {
					pc_template_id = Long.parseLong(paramMap.get("pc_template_id").toString());
				}catch (Exception e) {
					pc_template_id = 0;
				}
				if(pc_template_id==0){
					pr.setState(false);
					pr.setMessage("参数【pc_template_id】错误");
					return pr;
				}
			}
			/**
			 * 如果PC、微信有选择模板，还需要判断模板ID
			 */
			if(moblie_template_flag == 1) {
				try {
					mobile_template_id = Long.parseLong(paramMap.get("moblie_template_id").toString());
				}catch (Exception e) {
					mobile_template_id = 0;
				}
				if(mobile_template_id==0){
					pr.setState(false);
					pr.setMessage("参数【pc_template_id】错误");
					return pr;
				}
			}
			paramMap.put("activity_product_ids", activity_product_ids);
			String pc_watermark_img_url = null;
			String mobile_watermark_img_url = null;
			Map<String, Object> pc_watermark_template = null;
			Map<String, Object> mobile_watermark_template = null;
			List<Map<String, Object>> product_price_list = activityProductDao.queryActivityProductPriceList(paramMap);
			for(Map<String, Object> product_price:product_price_list) {
				paramMap.put("activity_product_id", product_price.get("ID"));
				paramMap.put("product_img_url", product_price.get("PRODUCT_IMG_URL"));
				String activity_product_img_url = product_price.get("ACTIVITY_PRODUCT_IMG_URL").toString();
				//截取图片的扩展名，如果不是jpg、png，则直接不生成水印图片
				if(pc_template_flag==1 && moblie_template_flag==2 && judgeImgSuffix(activity_product_img_url)) {//只有PC使用模板
					if(pc_watermark_template==null) {
						pc_watermark_template = activityProductDao.getActivityProductImgTemplateById(pc_template_id);
					}
					pc_watermark_img_url = handleWatermarkImg(product_price, pc_watermark_template, activity_product_img_url);
				}else if(pc_template_flag==2 && moblie_template_flag==1 && judgeImgSuffix(activity_product_img_url)) {//只有微信使用模板
					if(pc_watermark_template==null) {
						mobile_watermark_template = activityProductDao.getActivityProductImgTemplateById(mobile_template_id);
					}
					mobile_watermark_img_url = handleWatermarkImg(product_price, mobile_watermark_template, activity_product_img_url);
				}else if(pc_template_flag==1 && moblie_template_flag==1 && judgeImgSuffix(activity_product_img_url)){
					//如果PC、微信使用了相同的模板，则，只需要生成一次即可
					if(pc_template_id == mobile_template_id) {
						if(pc_watermark_template==null)
							pc_watermark_template = activityProductDao.getActivityProductImgTemplateById(pc_template_id);
						pc_watermark_img_url = handleWatermarkImg(product_price, pc_watermark_template, activity_product_img_url);
						mobile_watermark_img_url = pc_watermark_img_url;
					}else {
						if(pc_watermark_template==null)
							pc_watermark_template = activityProductDao.getActivityProductImgTemplateById(pc_template_id);
						if(mobile_watermark_template==null)
							mobile_watermark_template = activityProductDao.getActivityProductImgTemplateById(mobile_template_id);
						pc_watermark_img_url = handleWatermarkImg(product_price, pc_watermark_template, activity_product_img_url);
						mobile_watermark_img_url = handleWatermarkImg(product_price, mobile_watermark_template, activity_product_img_url);
					}
				}
				//不使用PC模板，则PC水印图直接使用活动图片
				if(pc_template_flag==2 || !judgeImgSuffix(activity_product_img_url)) {
					pc_watermark_img_url = activity_product_img_url;
				}
				//不使用移动端模板，则移动端水印图直接使用活动图片
				if(moblie_template_flag==2 || !judgeImgSuffix(activity_product_img_url)) {
					mobile_watermark_img_url = activity_product_img_url;
				}
				//批量操作不更新字段【PRODUCT_IMG_URL】
				paramMap.put("pc_watermark_img_url",pc_watermark_img_url);
				paramMap.put("mobile_watermark_img_url",mobile_watermark_img_url);
				paramMap.put("pc_template_id", pc_template_id);
				paramMap.put("mobile_template_id", mobile_template_id);
				//更新数据库
				if(activityProductDao.updateActivityProductImg(paramMap)<=0) {
					throw new RuntimeException("水印图片持久化失败");
				}
			}
			pr.setState(true);
			pr.setMessage("水印图片持久化成功");
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("获取水印模板列表异常："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 判断文件的文件后缀是否符合要求
	 * @param filename 文件，名称
	 * @return 		true：如果文件后缀是jpg 或者  png
	 * 				false：如果文件后缀不是jpg 或者  png
	 */
	private boolean judgeImgSuffix(String filename) {
		final String TYPE_JPG = "jpg";
		final String TYPE_PNG = "png";
		boolean flag = false;
		try {
			String suffix = filename.substring(filename.lastIndexOf(".") + 1);
			if(!StringUtils.isEmpty(suffix)) {
				suffix = suffix.toLowerCase();
				if(TYPE_JPG.equals(suffix)) {
					flag = true;
				}else if(TYPE_PNG.equals(suffix)) {
					flag = true;
				}
			}
			
		} catch (Exception e) {
		}
		return flag;
	}
	/**
	 * 生成水印图片
	 * @param product_price   				价格
	 * @param watermark_template			水印模板
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String handleWatermarkImg(Map<String, Object> product_price,Map<String, Object> watermark_template,String activity_product_img_url) {
		String result = null;
		try {
			//如果activity_product_img_url是完整的图片路径，需要删除前缀
			if(activity_product_img_url.indexOf(".com")>=0) {
				activity_product_img_url = activity_product_img_url.substring(activity_product_img_url.lastIndexOf(".com")+5);
			}
			/******************替换商品图片**********/
			//需要替换内容，实时展示预览图
			List<Map<String, Object>> watermark_image_list  = (List<Map<String, Object>>) Jackson.readJson2Object(watermark_template.get("WATERMARK_IMAGE_JSON").toString(), HashMap.class);
			List<Map<String, Object>> watermark_text_list = (List<Map<String, Object>>) Jackson.readJson2Object(watermark_template.get("WATERMARK_TEXT_JSON").toString(), HashMap.class);
			String img_url = null;
			if(watermark_image_list!=null && !watermark_image_list.isEmpty()) {
				for(Map<String,Object> img:watermark_image_list) {
					img_url = img.get("img_url").toString();
					if(img.get("img_url").toString().indexOf("product_img_url")>=0){//需要使用主图替换
						img_url =  img_url.replace("product_img_url",activity_product_img_url);//替换
					}
					img.put("img_url", img_url);
				}
			}
			String text = null;
			if(watermark_text_list!=null && !watermark_text_list.isEmpty()) {
				for(Map<String,Object> textObj:watermark_text_list) {
					text = textObj.get("text").toString();
					/**
					 * page.product_price{
					 *  	ACTIVITY_PRICE: "11.50"
					 *		ORIGINAL_PRICE: "13.00"
					 *		REDUCED_PRICE: "1.50"
					 * }
					 */
					if("activity_price".equals(text)){//活动价格
						text  = product_price.get("ACTIVITY_PRICE").toString();
					}else if("reduced_price".equals(text)){//降价
						text  = product_price.get("REDUCED_PRICE").toString();
					}else if("original_price".equals(text)){//原价
						text  = product_price.get("ORIGINAL_PRICE").toString();
					}
					textObj.put("text",text);
				}
			}
			Map<String, Object> ossParam = new HashMap<String, Object>();
			ossParam.put("background_img_url", watermark_template.get("BACKGROUND_IMG_URL").toString());
			ossParam.put("watermark_image_list",watermark_image_list);
			ossParam.put("watermark_text_list",watermark_text_list);
			//1.远程调用OSS接口 验证目录模板是否被使用
			/**
			 * @apiParam {int}  	[watermark_type=1]              			水印生成的模板类型 
			 *																		<p>1：以白色图片作为底图进行水印添加<p> 
			 * @apiParam {int}  	background_img_url              			水印底图地址，watermark_type=1时必填。注意不需要访问地址前缀，例如：201910/20191031/f20851618fc17233fe9a.png
			 * @apiParam {object[]}  watermark_image_list               		图片水印列表
			 * @apiParam {string}    watermark_image_list.img_urlimg_url		图片水印图片地址
			 * @apiParam {string}    watermark_image_list.style				图片水印图片样式，控制图片位置
			 * @apiParam {object[]}  watermark_text_list               		文字水印列表
			 * @apiParam {string}    watermark_text_list.text					文字水印文字内容
			 * @apiParam {string}    watermark_text_list.style					文字水印样式，控制文字的颜色。字体、位置等信息
			 */
			/*********************调用oss接口********************/
			ProcessResult pr = null;
			try {
				 pr=HttpClientUtil.post(oss_service_url+file_watermark_image,ossParam);
			}catch (Exception e) {
				pr = null;
			}
			if(pr!=null && pr.getState() && !StringUtils.isEmpty(pr.getObj())) {
				result = (String) pr.getObj();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
}