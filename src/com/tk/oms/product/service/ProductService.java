package com.tk.oms.product.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.oms.product.entity.KeywordTypeEnum;
import com.tk.oms.product.entity.OperationTypeEnum;
import com.tk.oms.product.entity.ProductOperationLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.analysis.dao.ProductOperationLogDao;
import com.tk.oms.basicinfo.dao.ProductSpecsesDao;
import com.tk.oms.decoration.dao.DirectoryDao;
import com.tk.oms.product.dao.ProductDao;
import com.tk.oms.sys.dao.SiteDelayTemplateDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.MqQueueKeyEnum;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductService")
public class ProductService {
	private Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private ProductDao productDao;
	@Resource
	private DirectoryDao directoryDao;
	@Resource
	private ProductSpecsesDao productSpecsesDao;
	@Resource
	private SiteDelayTemplateDao sitedelaytemplatedao;
	@Resource
	private RabbitTemplate rabbitTemplate;
	@Resource
	private ProductOperationLogDao productOperationLogDao;
	

	@Value("${oss_service_url}")
	private String oss_service_url;// oss服务地址
	@Value("${file_copy_product_main_img}")
	private String file_copy_product_main_img;// 商品通过审批后，复制商品主图至分享目录
	/**
	 * 获取待审批商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryPendingApprovalProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = productDao.queryPendingApprovalProductList(paramMap);
			int count = productDao.queryPendingApprovalProductCount(paramMap);
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
	 * 获取待审批商品详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryPendingApprovalProductDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取待审批商品详情基本信息
			Map<String,Object> detail = productDao.queryPendingApprovalProductDetail(paramMap);
			if(detail!=null){
				//获取待审批商品详情参数信息
				List<Map<String,Object>> paramsList = productDao.queryPendingApprovalProductParamsList(paramMap);
				//获取待审批商品sku列表信息
				paramMap.put("product_group", "尺码");
				List<Map<String,Object>> skusList = productDao.queryPendingApprovalProductSkuList(paramMap);
				//获取商品图片
				List<Map<String,Object>> imgsApplyList = productDao.queryProductImagesApplyList(paramMap);
				resultMap.put("imgs_info", imgsApplyList);
				//获取商品站点显示时间
				List<Map<String,Object>> siteDelayList = productDao.queryProductSiteDelayList(paramMap);
				resultMap.put("site_delay_info", siteDelayList);
				//获取商品站点显示区域数据
				paramMap.put("district_templet_id", detail.get("DISTRICT_TEMPLET_ID"));
				List<Map<String,Object>> regionAreaList = productDao.queryProductRegionAreaListList(paramMap);
				resultMap.put("areas_info", regionAreaList);
				paramMap.put("is_apply", 1);
				List<Map<String, Object>> wrapperList = productDao.queryProductWrapperList(paramMap);
				resultMap.put("wrapper_info", wrapperList);
				//获取已审批的商品数据【货号生成在入驻商发布商品时已生成】
				Map<String,Object> paramDetailMap = new HashMap<String,Object>();
				paramDetailMap.put("itemnumber", detail.get("ITEMNUMBER"));
				//获取商品详情基本信息
				Map<String,Object> oldDetail = productDao.queryProductDetail(paramDetailMap);
				if(StringUtils.isEmpty(oldDetail)){
					//商品第一次发布
					resultMap.put("product_flag", "01");
					resultMap.put("base_info", detail);
					resultMap.put("params_info", paramsList);
					resultMap.put("skus_info", skusList);
					resultMap.put("new_img_flag", 0);
				}else{
					boolean flag = false; //主图与原主图是否一致
					//获取商品图片
					List<Map<String,Object>> imgsList = productDao.queryProductImagesList(paramMap);
					if(imgsList.size() > 0) {
						resultMap.put("old_imgs_info", imgsList);
						if(imgsList.size() == imgsApplyList.size()) {
							for(int j = 0; j < imgsApplyList.size(); j++) {
								flag = false;
								for(int i = 0; i < imgsList.size(); i++) {
									if(imgsApplyList.get(j).get("IMAGE_URL").equals(imgsList.get(i).get("IMAGE_URL"))) {
										flag = true;
										continue;
									}
								}
							}
						}
					}
					resultMap.put("new_img_flag", flag ? 0 : 1);

					oldDetail.remove("STATE");//去除商品上下架状态，不做对比（apply表中STATE为审批状态）
					//拼装新老商品基本信息
					Map<String,Object> newDetail = resultMapJoin(detail,oldDetail);
					newDetail.put("FREIGHT_TEMPLATE_ID", oldDetail.get("FREIGHT_TEMPLATE_ID"));
					newDetail.put("WITH_CODE_ID", oldDetail.get("WITH_CODE_ID"));
					//获取商品参数信息
					List<Map<String,Object>> oldParamsList = productDao.queryProductParamsList(paramDetailMap);
					//拼装新老商品参数信息
					List<Map<String,Object>> newParamsList = resultListJoin(paramsList,oldParamsList,"PARAMETER_ID");
					//获取待审批商品sku列表信息
					List<Map<String,Object>> oldSkusList = productDao.queryProductSkuList(paramDetailMap);
					//拼装新老商品sku信息
					List<Map<String,Object>> newSkusList = resultListJoin(skusList,oldSkusList,"ID");
					//商品更新
					resultMap.put("product_flag", "02");
					resultMap.put("base_info", newDetail);
					resultMap.put("params_info", newParamsList);
					resultMap.put("skus_info", newSkusList);
				}
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
	 * 待审批商品审批通过
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productApproved(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("public_user_id"))||StringUtils.isEmpty(paramMap.get("freight_template_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//TODO 20170411-003 1.验证操作用户是否存在
//			Map<String,Object> operateUserMap=userDao.queryUserDetail(paramMap);
//			if(operateUserMap==null){
//				pr.setMessage("操作用户不存在，请检查");
//				pr.setState(false);
//				return pr;
//			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> pendingApprovalProductDetailMap=productDao.queryPendingApprovalProductDetail(paramMap);
			/***将当前的商品颜色数据插入至数据库中***/
			String pid = StringUtils.isEmpty(pendingApprovalProductDetailMap.get("ID"))?"0":pendingApprovalProductDetailMap.get("ID").toString();
			if(pendingApprovalProductDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"2".equals(pendingApprovalProductDetailMap.get("STATE"))){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			if("1".equals(pendingApprovalProductDetailMap.get("PRODUCT_SPEC_TYPE"))){
				//配码校验
				//查询当前配码设置规格数量
				int codeSetSpecs=productSpecsesDao.queryWithCodeSetSpecsCount(paramMap);
				//查询当前配码实际需要配置规格数量
				int codeActualSetSpecs=productSpecsesDao.queryWithCodeAcutalSetSpecsCount(paramMap);
				if(codeSetSpecs!=codeActualSetSpecs){
					pr.setMessage("当前配码有未设置的规格");
					pr.setState(false);
					return pr;
				}
			}else{
				paramMap.remove("code_id");
			}
			
			List<Map<String, Object>> skuList = null;
			//商品审批时，校验是否申请仓储费增加对非鞋类商品的支持  shif
			/***增加待审批商品的对应入驻商的仓储费设置是否正确。20170808 shif***/
			/***增加待审批商品的对应入驻商的仓储费设置是否正确，区分鞋类和非鞋类。20170913 shif***/
			paramMap.put("stationed_user_id", pendingApprovalProductDetailMap.get("STATIONED_USER_ID"));
			int check_count= productDao.queryProductSizeStorageChargesNotExistsNum(paramMap);//鞋类
			if(check_count > 0 ){
				pr.setMessage("入驻商仓储费配置不正确，请检查");
				pr.setState(false);
				return pr;
			}else{//非鞋类
				check_count= productDao.queryProductTypeStorageChargesNotExistsNum(paramMap);
				if(check_count > 0 ){
					pr.setMessage("入驻商仓储费配置不正确，请检查");
					pr.setState(false);
					return pr;
				}	
			}
			if(StringUtils.isEmpty(pendingApprovalProductDetailMap.get("ITEMNUMBER"))){
				/*//2.1 生成货号【由入驻商发布商品时自己生成】
				Map<String, Object> paramItemnumberMap = new HashMap<String, Object>();
				paramItemnumberMap.put("type_id", pendingApprovalProductDetailMap.get("PRODUCT_TYPE_ID"));
				paramItemnumberMap.put("brand_id", pendingApprovalProductDetailMap.get("BRAND_ID"));
				paramItemnumberMap.put("year", pendingApprovalProductDetailMap.get("YEAR"));
				paramItemnumberMap.put("season_id", pendingApprovalProductDetailMap.get("SEASON_ID"));
				String itemnumber=productDao.queryProductItemnumber(paramItemnumberMap);
				if(StringUtils.isEmpty(itemnumber)){
					pr.setMessage("货号生成失败");
					return pr;
				}
				paramMap.put("itemnumber",itemnumber);
				//2.2验证生成的货号是否已存在
				Map<String, Object> checkParamMap = new HashMap<String, Object>();
				checkParamMap.put("itemnumber",itemnumber);
				int count=productDao.queryProductCount(checkParamMap);
				if(count > 0){
					pr.setMessage("货号已存在");
					return pr;
				}*/
			}else{
				paramMap.put("itemnumber", pendingApprovalProductDetailMap.get("ITEMNUMBER"));
				Map<String,Object> tempParamMap=new HashMap<String,Object>();
				tempParamMap.put("itemnumber", pendingApprovalProductDetailMap.get("ITEMNUMBER"));
				Map<String,Object> productDetailMap=productDao.queryProductDetail(tempParamMap);
				if(productDetailMap!=null){//已审批过的商品
					paramMap.put("product_state", productDetailMap.get("STATE"));
					/**
					 * 已经审批，检测是否有新增的商品颜色，如果存在，需要将新的颜色复制到“颜色排序表中”，顺序从最大开始递增
					songwangwebn 2017.06.26
					**/
					if(!StringUtils.isEmpty(pid)){
						productDao.insertAdditionalProductColorSort(Long.parseLong(pid));
					}
				}else{
					/**
					 * 是否为首次审批标识
					 */
					paramMap.put("first_approval_flag", true);
					//2.3自动上下架状态处理
					paramMap.put("product_state", "待上架");
					if("2".equals(pendingApprovalProductDetailMap.get("SELL_STATE_FLAG"))){
						paramMap.put("product_state", "上架");
						paramMap.put("product_sort_value", this.productDao.queryFirstSellSortValue());
					}
					/***
					 * 调用远程接口，商品主图的复制
					 * songwangwen 2017.05.26
					 */
					Map<String,Object> ossMap = new HashMap<String, Object>();
					//1.查询商品主图
					List<Map<String,Object>> imgsList = productDao.queryProductImagesList(paramMap);
					//
					List<String> file_path_list = new ArrayList<String>();
					for(Map<String,Object> m:imgsList){
						//仅仅复制图片，不复制视频 TYPE = 1表示图片     TYPE=2：表示视频
						if(!StringUtils.isEmpty(m.get("IMAGE_URL")) && "1".equals(m.get("TYPE"))){
							file_path_list.add(m.get("IMAGE_URL").toString());
						}
					}
					if(!StringUtils.isEmpty(file_path_list) && !file_path_list.isEmpty()){
						ossMap.put("file_path_list",file_path_list);//商品图片列表
						ossMap.put("user_id", paramMap.get("public_user_id"));//当前用户ID
						StringBuffer directory_full_name = new StringBuffer("商品图片");
						directory_full_name.append("/").append(paramMap.get("itemnumber"));
						directory_full_name.append("/").append("商品主图").append("/");
						ossMap.put("directory_full_name",directory_full_name);//完整路径
						ossMap.put("itemnumber",paramMap.get("itemnumber"));//商品货号
						//模板ID
						long file_directory_info_id = directoryDao.queryProductMainImgDir();
						ossMap.put("file_directory_info_id",file_directory_info_id);//模板ID
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("directory_id", paramMap.get("id"));
						//1.远程调用OSS接口 验证目录模板是否被使用
						pr=HttpClientUtil.post(oss_service_url+file_copy_product_main_img,ossMap);
						if(!pr.getState()){
							logger.error("复制商品主图失败！");
						}
					}
				}
			}
			
			//查询联营商家是否关联商品
			if(productDao.queryStoreProductIsExists(paramMap) > 0) {
				//查询是否存在被修改过吊牌价的sku数据
				skuList = productDao.queryProductPrizeTagDifferent(paramMap);
			}
			
			/******************商品各个站点延后显示时间******************/
			List<Map<String,Object>> site_delay_list = (List<Map<String,Object>>)paramMap.get("site_delay_list");
	    	   if(site_delay_list!=null && !site_delay_list.isEmpty()){
	    		   Map<String,Object> site_delay_map= new HashMap<String, Object>();
	    		   site_delay_map.put("product_id",pid);
	    		   site_delay_map.put("list",site_delay_list);
	    		   sitedelaytemplatedao.deleteNotin(site_delay_map);//删除未设置显示时间的数据
	    		   sitedelaytemplatedao.batchInsertOrUpdate(site_delay_map);//批量插入新数据
	    	   }else{
	    		   paramMap.put("product_id",pid);
	    		   sitedelaytemplatedao.delete(paramMap);//清除站点延后时间
	    	   }
	    	   
	    	//定制定金需要除以100，以小数形式保存
	    	float custom_deposit_rate = 0;
	    	if(!StringUtils.isEmpty(paramMap.get("custom_deposit_rate"))){
	    		custom_deposit_rate =  Float.parseFloat(paramMap.get("custom_deposit_rate").toString());
	    	}
	    	
			int IS_BRAND_CUSTOM = StringUtils.isEmpty(pendingApprovalProductDetailMap.get("IS_BRAND_CUSTOM"))?0:Integer.parseInt(pendingApprovalProductDetailMap.get("IS_BRAND_CUSTOM").toString());//品牌定制
			int IS_COMMON_CUSTOM = StringUtils.isEmpty(pendingApprovalProductDetailMap.get("IS_COMMON_CUSTOM"))?0:Integer.parseInt(pendingApprovalProductDetailMap.get("IS_COMMON_CUSTOM").toString());//品牌定制
			if (IS_BRAND_CUSTOM + IS_COMMON_CUSTOM >0) {
				if (custom_deposit_rate <= 0) {
					pr.setMessage("定制商品定制定金不能小于零");
					pr.setState(false);
					return pr;
				}
			}
			paramMap.put("custom_deposit_rate", custom_deposit_rate/100f);
			//3.将待审批商品主表变更更新到商品主表
			int num=0;
			num += productDao.updateProductInfoFromPendingApprovalProduct(paramMap);
			//4.将待审批商品参数表变更更新到商品参数表
			num += productDao.updateProductParamFromPendingApprovalProductParam(paramMap);
			//5.将待审批商品Sku表变更更新到商品Sku表

			/**
			 * 将主表中的【是否预售】作为SKU表的是否预售的条件
			 * songwangwen  2017.06.14
			 * **/
			int is_outstock = StringUtils.isEmpty(pendingApprovalProductDetailMap.get("IS_OUTSTOCK"))?0:Integer.parseInt(pendingApprovalProductDetailMap.get("IS_OUTSTOCK").toString());
			paramMap.put("is_outstock", is_outstock);
			num += productDao.updateProductSkuFromPendingApprovalProductSku(paramMap);
			if(num <= 0 ){
				throw new RuntimeException("操作失败");
			}

			productDao.deleteProductImages(paramMap);

			num = productDao.updateProductImageFromProductImageApply(paramMap);

			num =  productDao.updateProductImagesItemnumber(paramMap);
			//6.更新待审批记录为审批通过
			paramMap.put("state", "3");
			num = productDao.updatePendingApprovalProductState(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			/**
			 * 首次审核通过的商品往社交首页商品管理表新增一条数据
			 * liujialong 2017.04.28
			 */
			if(!StringUtils.isEmpty(paramMap.get("first_approval_flag"))){
				paramMap.remove("id");
				Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
				productDetailMap.put("public_user_id", paramMap.get("public_user_id"));
				if(productDao.insertSocialProduct(productDetailMap)<=0){
					throw new RuntimeException("新增社交首页商品管理数据失败");
				}
			}
			/**
			 * 校验联营商家是否关联商品，如果有则推送消息队列
			 * yejingquan 2018.09.17
			 */
			if(!StringUtils.isEmpty(pendingApprovalProductDetailMap.get("ITEMNUMBER"))){
				/*************************<MQ>商品审批通过后推送消息**************************/
				if(skuList != null && skuList.size()>0) {
					Map<String, Object> mqMap = new HashMap<String, Object>();
					mqMap.put("product_itemnumber", paramMap.get("itemnumber"));
					mqMap.put("skuList", skuList);
					rabbitTemplate.convertAndSend(MqQueueKeyEnum.ESB_PRODUCT_PRIZE_TAG_UPDATE.getKey(), Jackson.writeObject2Json(mqMap));
				}
			}
			/**
			 * 记录商品操作日志
			 */
			ProductOperationLog productOperationLog = new ProductOperationLog();
			productOperationLog.setProductItemnumber(paramMap.get("itemnumber").toString());
			productOperationLog.setOperationType(OperationTypeEnum.ADD_PRODUCT.value);
			productOperationLog.setOperationName(OperationTypeEnum.ADD_PRODUCT.des);
			productOperationLog.setKeywordType(KeywordTypeEnum.APPROVE_PRODUCT.value);
			productOperationLog.setKeywordName(KeywordTypeEnum.APPROVE_PRODUCT.des);
			productOperationLog.setRemark("商品货号为" + paramMap.get("itemnumber") + ",审批通过");
			productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
			productOperationLog.setOperatorPlatform(1);
			this.productOperationLogDao.insertProductOperationLog(productOperationLog);
			//TODO 20170412-001 7.记录审批历史
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
	 * 待审批商品审批驳回
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productRejected(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("public_user_id"))||StringUtils.isEmpty(paramMap.get("rejected_reason"))){
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
			Map<String,Object> pendingApprovalProductDetailMap=productDao.queryPendingApprovalProductDetail(paramMap);
			if(pendingApprovalProductDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"2".equals(pendingApprovalProductDetailMap.get("STATE"))){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//3.更新待审批记录为审批驳回
			paramMap.put("state", "4");
			int num = productDao.updatePendingApprovalProductState(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}

			/**
			 * 记录商品操作日志
			 */
			ProductOperationLog productOperationLog = new ProductOperationLog();
			productOperationLog.setProductItemnumber(pendingApprovalProductDetailMap.get("ITEMNUMBER").toString());
			productOperationLog.setOperationType(OperationTypeEnum.ADD_PRODUCT.value);
			productOperationLog.setOperationName(OperationTypeEnum.ADD_PRODUCT.des);
			productOperationLog.setKeywordType(KeywordTypeEnum.APPROVE_PRODUCT.value);
			productOperationLog.setKeywordName(KeywordTypeEnum.APPROVE_PRODUCT.des);
			productOperationLog.setRemark("商品货号为" + paramMap.get("itemnumber") + ",审批驳回。驳回原因为：" + paramMap.get("rejected_reason"));
			productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
			productOperationLog.setOperatorPlatform(1);
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
	 * 商品编辑（运费模板设置）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productEdit(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))||StringUtils.isEmpty(paramMap.get("freight_template_id"))
			  ||StringUtils.isEmpty(paramMap.get("public_user_id"))){
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
			Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
			if(productDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if("1".equals(productDetailMap.get("PRODUCT_SPEC_TYPE"))){
				//配码校验
				//查询当前配码设置规格数量
				int codeSetSpecs=productSpecsesDao.queryWithCodeSetSpecsCount(paramMap);
				//查询当前配码实际需要配置规格数量
				int codeActualSetSpecs=productSpecsesDao.queryWithCodeAcutalSetSpecsCount(paramMap);
				if(codeSetSpecs!=codeActualSetSpecs){
					pr.setMessage("当前配码有未设置的规格");
					pr.setState(false);
					return pr;
				}
			}else{
				paramMap.remove("code_id");
			}
			
			/******************商品各个站点延后显示时间******************/
			List<Map<String,Object>> site_delay_list = (List<Map<String,Object>>)paramMap.get("site_delay_list");
	    	   if(site_delay_list!=null && !site_delay_list.isEmpty()){
	    		   Map<String,Object> site_delay_map= new HashMap<String, Object>();
	    		   site_delay_map.put("product_id",productDetailMap.get("ID"));
	    		   site_delay_map.put("list",site_delay_list);
	    		   sitedelaytemplatedao.deleteNotin(site_delay_map);//删除未设置显示时间的数据
	    		   sitedelaytemplatedao.batchInsertOrUpdate(site_delay_map);//批量插入新数据
	    	   }else{
	    		   paramMap.put("product_id",productDetailMap.get("ID"));
	    		   sitedelaytemplatedao.delete(paramMap);//清除站点延后时间
	    	   }
	    	float custom_deposit_rate = 0;
		    if(!StringUtils.isEmpty(paramMap.get("custom_deposit_rate"))){
		    		custom_deposit_rate =  Float.parseFloat(paramMap.get("custom_deposit_rate").toString());
		    }
    	    custom_deposit_rate = custom_deposit_rate / 100f;
			paramMap.put("custom_deposit_rate", custom_deposit_rate);
			int num = productDao.updateProduct(paramMap);
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
	 * 商品强制下架
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productSoldOut(HttpServletRequest request) throws Exception{
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
			//TODO 20170411-002 1.验证操作用户是否存在
//			Map<String,Object> operateUserMap=userDao.queryUserDetail(paramMap);
//			if(operateUserMap==null){
//				pr.setMessage("操作用户不存在，请检查");
//				pr.setState(false);
//				return pr;
//			}
			//2.验证当前操作待审批记录状态是否正常
			Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
			if(productDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			if(!"上架".equals(productDetailMap.get("STATE"))){
				pr.setMessage("操作记录状态异常，请检查");
				pr.setState(false);
				return pr;
			}
			//3.更新待审批记录为审批驳回
			paramMap.put("state", "下架");
			int num = productDao.updateProductState(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}

			/**
			 * 记录商品操作日志
			 */
			ProductOperationLog productOperationLog = new ProductOperationLog();
			productOperationLog.setProductItemnumber(paramMap.get("itemnumber").toString());
			productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
			productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
			productOperationLog.setKeywordType(KeywordTypeEnum.PRODUCT_SHELVES.value);
			productOperationLog.setKeywordName(KeywordTypeEnum.PRODUCT_SHELVES.des);
			productOperationLog.setRemark("商品货号为" + paramMap.get("itemnumber") + ",强制下架");
			productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
			productOperationLog.setOperatorPlatform(1);
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
	 * 商品强制下架
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productBatchSoldOut(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ids"))||StringUtils.isEmpty(paramMap.get("public_user_id"))){
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
			int num=productDao.queryProductSoldOutCount(paramMap);
			if(num>0){
				pr.setMessage("操作记录中存在已下架商品");
				pr.setState(false);
				return pr;
			}
			//3.更新商品下架状态为下架
			paramMap.put("state", "下架");
			num = productDao.bacthUpdateProductState(paramMap);
			if(num <= 0){
				throw new RuntimeException("批量下架商品操作失败");
			}
			pr.setMessage("批量下架商品操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("itemnumbers")))&&paramMap.get("itemnumbers") instanceof String){
				paramMap.put("itemnumbers",(paramMap.get("itemnumbers")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("is_sale_sort")))&&paramMap.get("is_sale_sort") instanceof String){
				paramMap.put("is_sale_sort",(paramMap.get("is_sale_sort")+"").split(","));
			}
			if(StringUtils.isEmpty(paramMap.get("start_stop_state"))){
				paramMap.remove("start_stop_state");
			}
			if((!StringUtils.isEmpty(paramMap.get("start_stop_state")))&&paramMap.get("start_stop_state") instanceof String){
				paramMap.put("start_stop_state",(paramMap.get("start_stop_state")+"").split(","));
			}
			List<Map<String, Object>> list = productDao.queryProductList(paramMap);
			int count = productDao.queryProductCount(paramMap);
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
	 * 获取商品详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取待审批商品详情基本信息
			Map<String,Object> detail = productDao.queryProductDetail(paramMap);
			if(detail!=null){
				paramMap.put("id", detail.get("ID"));
				//获取商品详情参数信息
				List<Map<String,Object>> paramsList = productDao.queryProductParamsList(paramMap);
				//获取商品sku列表信息
				paramMap.put("product_group", "尺码");
				List<Map<String,Object>> skusList = productDao.queryProductSkuList(paramMap);
				//获取商品图片
				List<Map<String,Object>> imgsList = productDao.queryProductImagesList(paramMap);
				//获取商品站点显示时间
				List<Map<String,Object>> siteDelayList = productDao.queryProductSiteDelayList(paramMap);
				resultMap.put("site_delay_info", siteDelayList);
                List<Map<String, Object>> wrapperList = productDao.queryProductWrapperList(paramMap);
                resultMap.put("wrapper_info", wrapperList);
				//获取商品站点显示区域数据
				paramMap.put("district_templet_id", detail.get("DISTRICT_TEMPLET_ID"));
				List<Map<String,Object>> regionAreaList = productDao.queryProductRegionAreaListList(paramMap);
				resultMap.put("areas_info", regionAreaList);
				
				resultMap.put("base_info", detail);
				resultMap.put("params_info", paramsList);
				resultMap.put("imgs_info", imgsList);
				resultMap.put("skus_info", skusList);
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
	 * 获取商品规格列表
	 */
	public ProcessResult queryProductSpecList(HttpServletRequest request){

		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setMessage("缺少参数[货号：itemnumber]");
				pr.setState(false);
				return pr;
			}
			List<Map<String, Object>> list = productDao.queryProductSpecList(paramMap);
			if (list != null && list.size() > 0) {
				pr.setMessage("获取数据成功");
				pr.setObj(list);
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
	 * 获取待审批商品列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSeason(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			List<Map<String, Object>> list = productDao.queryProductSeasonList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
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
	 * 将两个相同结构list拼接合并，相同key的，后面的一个key加前缀‘OLD_’,再放入到拼接结果中
	 * @param paramsList
	 * @param oldParamsList
	 * @param key
	 * @return
	 */
	private List<Map<String, Object>> resultListJoin(List<Map<String, Object>> paramsList,List<Map<String, Object>> oldParamsList,String key) {
		
		if(paramsList != null && paramsList.size() > 0 && oldParamsList != null && oldParamsList.size() > 0){
			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			//将原List的结果按key对应的值进行封装
			Map<String,Map<String,Object>> tempMap=new HashMap<String,Map<String,Object>>();
			for(Map<String,Object> oldParamMap:oldParamsList){
				tempMap.put(""+oldParamMap.get(key), oldParamMap);
			}
			//将新List的结果循环处理
			for(Map<String,Object> paramMap:paramsList){
				Map<String,Object> oldParamMap=tempMap.get(paramMap.get(key)+"");
				if(oldParamMap!=null){
					Map<String,Object> tempReslutMap=resultMapJoin(paramMap,oldParamMap);
					resultList.add(tempReslutMap);
				}else{
					resultList.add(paramMap);
				}
				
			}
			return resultList;
		}
		return paramsList;
	}

	/**
	 * 将两个相同结构Map拼接合并，相同key的，后面的一个key加前缀‘OLD_’,再放入到拼接结果中
	 * @param paramMap
	 * @param oldParamMap
	 * @return
	 */
	private Map<String,Object> resultMapJoin(Map<String,Object> paramMap,Map<String,Object> oldParamMap){
		if(paramMap != null && oldParamMap != null){
			Iterator<Entry<String, Object>> iterator= oldParamMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, Object> entry=iterator.next();
				if(paramMap.containsKey(entry.getKey())){
					paramMap.put("OLD_"+entry.getKey(),entry.getValue());
				}
			}
			return paramMap;
		}
		return paramMap;
	}

	/**
	 * 根据关键字搜索获取商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductListByKeyWord(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(paramMap==null || StringUtils.isEmpty(paramMap.get("site_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数站点ID【site_id】");
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			int count = productDao.queryProductCountByKwyword(paramMap);
			if(count>0){
				list = productDao.queryProductListByKwyword(paramMap);
				gr.setTotal(count);
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			}else{
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
	 * 根据商品货号 获取商品列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductByItemnumbers(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(paramMap==null || StringUtils.isEmpty(paramMap.get("itemnumbers"))){
				gr.setState(false);
				gr.setMessage("缺少参数商品货号集合,对个货号则以英文逗号分隔【itemnumbers】");
			}
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			String itemnumbers = paramMap.get("itemnumbers").toString();
			paramMap.put("itemnumbers", itemnumbers.split(","));
			list = productDao.queryProductByItemnumbers(paramMap);
			gr.setMessage("获取商品数据成功");
			gr.setObj(list);
			gr.setState(true);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	
	/**
	 * 会员选择商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult querymemberProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = productDao.queryProductList(paramMap);
			int count = productDao.queryProductCount(paramMap);
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
	 * 获取分销商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryDistributionProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			List<Map<String, Object>> list = productDao.queryDistributionProductList(paramMap);
			int count = productDao.queryDistributionProductCount(paramMap);
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
	 * 添加分销商品
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryDistributionProductEdit(HttpServletRequest request) {
		Map<String, Object> param=new HashMap<String,Object>();
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ids"))){
				gr.setState(false);
				gr.setMessage("缺少参数站点ids");
			}
			if(StringUtils.isEmpty(paramMap.get("is_distribution"))){
				gr.setState(false);
				gr.setMessage("缺少参数站点is_distribution");
			}
			String ids=paramMap.get("ids").toString();
			param.put("is_distribution", paramMap.get("is_distribution"));
			if(Integer.parseInt(paramMap.get("is_distribution").toString())==0){
				param.put("id", paramMap.get("ids"));
				productDao.updateIsDistributionState(param);
			}else{
				for(String id:ids.split(",")){
					param.put("id", id);
					productDao.updateIsDistributionState(param);
					//param.remove("id");
				}
			}
			gr.setState(true);
			gr.setMessage("修改成功");
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 修改商品是否销量排序
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult productUpdateSaleSort(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("is_sale_sort"))){
				pr.setState(false);
				pr.setMessage("缺少参数is_sale_sort");
                return pr;
            }
            Map<String,Object> productMap = this.productDao.queryPendingApprovalProductDetail(params);
			if(StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数id");
                return pr;
            }
			if(productDao.updateIsSaleSortState(params)>0){
				/**
				 * 记录商品操作日志
				 */
				ProductOperationLog productOperationLog = new ProductOperationLog();
				productOperationLog.setProductItemnumber(productMap.get("ITEMNUMBER").toString());
				productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
				productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
				productOperationLog.setKeywordType(KeywordTypeEnum.SALE_SORT.value);
				productOperationLog.setKeywordName(KeywordTypeEnum.SALE_SORT.des);
				productOperationLog.setRemark("商品货号为" + productMap.get("ITEMNUMBER") + "," + (Integer.parseInt(params.get("is_sale_sort").toString()) == 1 ? "启用" : "禁用") + "销量排序");
				productOperationLog.setCreateUserId(Long.parseLong(params.get("public_user_id").toString()));
				productOperationLog.setOperatorPlatform(1);
				this.productOperationLogDao.insertProductOperationLog(productOperationLog);
				pr.setState(true);
				pr.setMessage("修改成功");
			} else{
				pr.setState(false);
				pr.setMessage("修改失败");
			}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	
	/**
	 * 获取商品的站点延后显示时间
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductSiteDelay(HttpServletRequest request){
		ProcessResult pr = new ProcessResult();
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params == null||params.isEmpty()){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			/******************************判断参数是否合格-start**********************************/
			if(StringUtils.isEmpty(params.get("product_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数商品申请ID[product_id]");
				return pr;
			}
			/******************************判断参数是否合格-end**********************************/
			// 判断商品是否存在
//			if (productDao.queryByIdAndUserId(params) <= 0) {
//				pr.setState(false);
//				pr.setMessage("该商品ID不存在");
//				return pr;
//			}
			List<Map<String,Object>> resultList = sitedelaytemplatedao.queryProductSiteDelay(params);
			/************************/
			pr.setState(true);
			if(resultList==null || resultList.isEmpty()){
				pr.setMessage("商品站点延后显示时间数据不存在");
				pr.setObj(resultList);
			}else{
				pr.setMessage("获取商品站点延后显示时间数据成功");
				pr.setObj(resultList);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 商品站点延后显示时间编辑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productSiteDelayedit(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String,Object> site_delay_map= new HashMap<String, Object>();
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
			/******************商品各个站点延后显示时间******************/
			List<Map<String,Object>> site_delay_list = (List<Map<String,Object>>)paramMap.get("site_delay_list");
			List<String> itemnumberList=Arrays.asList((paramMap.get("itemnumber")).toString().split(","));
			for(String itemnumber:itemnumberList){
				paramMap.put("itemnumber", itemnumber);
				//查询当前货号信息
				Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
				if(productDetailMap==null){
					pr.setMessage("操作记录不存在，请检查");
					pr.setState(false);
					return pr;
				}
				if(site_delay_list!=null && !site_delay_list.isEmpty()){
	    		   site_delay_map.put("product_id",productDetailMap.get("ID"));
	    		   site_delay_map.put("list",site_delay_list);
	    		   sitedelaytemplatedao.deleteNotin(site_delay_map);//删除未设置显示时间的数据
	    		   sitedelaytemplatedao.batchInsertOrUpdate(site_delay_map);//批量插入新数据
	    	   }else{
	    		   paramMap.put("product_id",productDetailMap.get("ID"));
	    		   sitedelaytemplatedao.delete(paramMap);//清除站点延后时间
	    	   }
				/**
				 * 记录商品操作日志
				 */
				ProductOperationLog productOperationLog = new ProductOperationLog();
				productOperationLog.setProductItemnumber(paramMap.get("itemnumber").toString());
				productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
				productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
				productOperationLog.setKeywordType(KeywordTypeEnum.SITE_DELAY.value);
				productOperationLog.setKeywordName(KeywordTypeEnum.SITE_DELAY.des);
				productOperationLog.setRemark("商品货号为" + paramMap.get("itemnumber") + ",进行站点延后设置");
				productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
				productOperationLog.setOperatorPlatform(1);
				this.productOperationLogDao.insertProductOperationLog(productOperationLog);
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
	 * 根据货号获取商品基本信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProcuctInfoDetail_Base(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数【itemnumber】");
				return pr;
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, Object> infoMap = productDao.queryByItemnumber_Base(paramMap);
			if(infoMap != null) {
				paramMap.put("id", infoMap.get("ID"));
				resultMap.put("base_info", infoMap);
				resultMap.put("img", productDao.queryProductImagesList(paramMap));
				pr.setState(true);
				pr.setMessage("查询商品详情成功！");
				pr.setObj(resultMap);
			} else {
				pr.setState(false);
				pr.setMessage("该商品不存在");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 根据货号获取商品颜色信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryColorListByItemnumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_itemnumber】");
				return pr;
			}
			
			List<Map<String, Object>> listMap = productDao.queryColorListByItemNumber(paramMap);
			pr.setState(true);
			pr.setObj(listMap);
			pr.setMessage("查询成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 根据货号以及颜色获取商品规格信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySpecsListByItemnumberAndColor(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_itemnumber】");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_color"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_color】");
				return pr;
			}
			
			List<Map<String, Object>> listMap = productDao.querySpecsListByItemnumberAndColor(paramMap);
			pr.setState(true);
			pr.setObj(listMap);
			pr.setMessage("查询成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 根据货号以及颜色和规格获取商品尺码信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySizeListByItemnumberAndColorAndSpecs(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_itemnumber】");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_color"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_color】");
				return pr;
			}
			
			if(StringUtils.isEmpty(paramMap.get("query_type"))){
				pr.setState(false);
				pr.setMessage("缺少参数【query_type】");
				return pr;
			}
			
			List<Map<String, Object>> listMap = productDao.querySizeListByItemnumberAndColorAndSpecs(paramMap);
			pr.setState(true);
			pr.setObj(listMap);
			pr.setMessage("查询成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 根据货号获取商品尺码和内长
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryCommodityListByItemNumber(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_itemnumber】");
				return pr;
			}
			
			List<Map<String, Object>> listMap = productDao.queryCommodityListByItemNumber(paramMap);
			pr.setState(true);
			pr.setObj(listMap);
			pr.setMessage("查询商品尺码和内长成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 根据货号查询商品参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductParam(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			
			if(StringUtils.isEmpty(paramMap.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数【product_itemnumber】");
				return pr;
			}
			
			List<Map<String, Object>> listMap = productDao.queryProductParam(paramMap);
			
			pr.setState(true);
			pr.setObj(listMap);
			pr.setMessage("查询商品参数成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 商品站点延后显示时间编辑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productMainProductFlagSet(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setMessage("缺少参数【货号】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("main_product_flag"))){
				pr.setMessage("缺少参数【商品主推款标识】");
				pr.setState(false);
				return pr;
			}
			//查询当前货号信息
			Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
			if(productDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			//设置商品主推款
			int count=productDao.productMainProductFlagSet(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("是指设置商品主推款失败");
			}

			/**
			 * 记录商品操作日志
			 */
			ProductOperationLog productOperationLog = new ProductOperationLog();
			productOperationLog.setProductItemnumber(paramMap.get("itemnumber").toString());
			productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
			productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
			productOperationLog.setKeywordType(KeywordTypeEnum.MAINLY_POPULARIZE.value);
			productOperationLog.setKeywordName(KeywordTypeEnum.MAINLY_POPULARIZE.des);
			productOperationLog.setRemark("商品货号为" + paramMap.get("itemnumber") + "," + (Integer.parseInt(paramMap.get("main_product_flag").toString()) == 1 ? "取消" : "开启") + "电商主推款");
			productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
			productOperationLog.setOperatorPlatform(1);
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
	 * 新品标签启用/禁用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productUpdateNewProductLabel(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setMessage("缺少参数【ID】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("new_product_label"))){
				pr.setMessage("缺少参数【新品标签状态】");
				pr.setState(false);
				return pr;
			}
			//查询当前货号信息
			Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
			if(productDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			int count=productDao.updateProductNewProductLabel(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("新品标签启用/禁用失败");
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
	 * 热卖标签启用/禁用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productUpdateHotSaleLabel(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setMessage("缺少参数【ID】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("hot_sale_label"))){
				pr.setMessage("缺少参数【热卖标签状态】");
				pr.setState(false);
				return pr;
			}
			//查询当前货号信息
			Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
			if(productDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			int count=productDao.updateProductHotSaleLabel(paramMap);
			if(count<=0){
				pr.setState(false);
				throw new RuntimeException("热卖标签启用/禁用失败");
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
	 * 商品终止补货
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productStopOutstock(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setMessage("缺少参数【ID】");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("stop_outstock_state"))) {
				pr.setMessage("缺少参数【stop_outstock_state】");
				pr.setState(false);
				return pr;
			}
			//查询当前货号信息
			Map<String,Object> productDetailMap=productDao.queryProductDetail(paramMap);
			if(productDetailMap==null){
				pr.setMessage("操作记录不存在，请检查");
				pr.setState(false);
				return pr;
			}
			paramMap.put("product_itemnumber", productDetailMap.get("ITEMNUMBER"));
			productDao.updateProductIsOutstock(paramMap);
			//终止发货启用
			if("1".equals(paramMap.get("stop_outstock_state").toString())) {
				productDao.updateProductSkuIsOutstock(paramMap);
			}
			//记录当前终止补货的商品货号
			int num=productDao.insertProductStopOutstock(paramMap);
			if(num<=0){
				pr.setState(false);
				throw new RuntimeException("记录终止补货启停用商品货号失败");
			}
			/**
			 * 记录商品操作日志
			 */
			ProductOperationLog productOperationLog = new ProductOperationLog();
			productOperationLog.setProductItemnumber(productDetailMap.get("ITEMNUMBER").toString());
			productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
			productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
			productOperationLog.setKeywordType(KeywordTypeEnum.STOP_OUTSTOCK.value);
			productOperationLog.setKeywordName(KeywordTypeEnum.STOP_OUTSTOCK.des);
			productOperationLog.setRemark("商品货号为" + productDetailMap.get("ITEMNUMBER") + "," + (Integer.parseInt(paramMap.get("stop_outstock_state").toString()) == 1 ? "启用" : "禁用") + "商品终止补货");
			productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
			productOperationLog.setOperatorPlatform(1);
			this.productOperationLogDao.insertProductOperationLog(productOperationLog);

			pr.setMessage("终止补货启停用成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("终止补货启停用异常："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 获取共享商品库商品列表【童库-私有商品共享】
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySharedProductList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("itemnumbers")))&&paramMap.get("itemnumbers") instanceof String){
				paramMap.put("itemnumbers",(paramMap.get("itemnumbers")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("is_sale_sort")))&&paramMap.get("is_sale_sort") instanceof String){
				paramMap.put("is_sale_sort",(paramMap.get("is_sale_sort")+"").split(","));
			}
			if((!StringUtils.isEmpty(paramMap.get("start_stop_state")))&&paramMap.get("start_stop_state") instanceof String){
				paramMap.put("start_stop_state",(paramMap.get("start_stop_state")+"").split(","));
			}
			//仅查询共享商品库数据
			paramMap.put("is_private", "1");
			List<Map<String, Object>> list = productDao.queryProductList(paramMap);
			int count = productDao.queryProductCount(paramMap);
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
	 * 共享商品库商品编辑（添加、移除、站点批量设置）【童库-私有商品共享】
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult sharedProductEdit(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//1.参数处理
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("ids"))){
				pr.setState(false);
				pr.setMessage("缺少参数商品ids");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("share_opertate_flag"))){
				pr.setState(false);
				pr.setMessage("缺少参数share_opertate_flag");
				return pr;
			}
			String ids = paramMap.get("ids").toString();//一组商品id，逗号分隔
			paramMap.put("productIdArray", ids.split(","));
			//1.1商品共享相关操作1:添加共享商品  2:移除共享商品 3:站点设置
			int shareOpertateFlag= Integer.parseInt(paramMap.get("share_opertate_flag").toString());
			if((shareOpertateFlag == 1 || shareOpertateFlag == 3)&&StringUtils.isEmpty(paramMap.get("site_delay_list"))){
				pr.setState(false);
				pr.setMessage("缺少参数site_delay_list");
				return pr;
			}
			//2.共享商品添加、移除处理
			if(shareOpertateFlag == 1 || shareOpertateFlag == 2){
				paramMap.put("is_private", shareOpertateFlag == 1 ? 1 : 0);
				//共享库商品操作处理(添加或移除)
				if(productDao.updateProductIsPrivtateState(paramMap) < 1){
					throw new RuntimeException("共享商品"+(shareOpertateFlag==1?"添加":"移除")+"失败,请刷新重试");
				}
			}
			//3.共享商品添加站点设置、批量站点设置
			if(shareOpertateFlag == 1 || shareOpertateFlag == 3){
				//商品各个站点延后显示时间
				List<Map<String,Object>> site_delay_list = (List<Map<String,Object>>)paramMap.get("site_delay_list");
				sitedelaytemplatedao.deletePrivate(paramMap);//清除站点延后时间
				if(site_delay_list!=null && !site_delay_list.isEmpty()){
	    		   sitedelaytemplatedao.batchInsertPrivate(paramMap);//批量插入新数据
	    	    }
			}
			//记录商品重要操作日志
			List<ProductOperationLog> productLogList = new ArrayList<ProductOperationLog>();
			//移除无效参数
			paramMap.remove("is_private");
			paramMap.put("start_rownum", 0);
			paramMap.put("end_rownum", 1000);
			List<Map<String,Object>> operateProductList = productDao.queryProductList(paramMap);
			for(Map<String,Object> tempProduct:operateProductList){
				ProductOperationLog productOperationLog = new ProductOperationLog();
				productOperationLog.setProductItemnumber(tempProduct.get("ITEMNUMBER").toString());
				productOperationLog.setOperationType(OperationTypeEnum.OTHER_PRODUCT.value);
				productOperationLog.setOperationName(OperationTypeEnum.OTHER_PRODUCT.des);
				productOperationLog.setKeywordType(KeywordTypeEnum.IS_PRIVATE.value);
				productOperationLog.setKeywordName(KeywordTypeEnum.IS_PRIVATE.des);
				if(shareOpertateFlag == 1 || shareOpertateFlag == 2){
					productOperationLog.setRemark("商品货号为" + tempProduct.get("ITEMNUMBER") + ","+(shareOpertateFlag==2?"从共享商品库移除":"添加到共享商品库"));
				}
				if(shareOpertateFlag == 3){
					productOperationLog.setRemark("商品货号为" + tempProduct.get("ITEMNUMBER") + ",私有站点延后时间修改");
				}
				productOperationLog.setCreateUserId(Long.parseLong(paramMap.get("public_user_id").toString()));
				productOperationLog.setOperatorPlatform(1);
				productLogList.add(productOperationLog);
			}
			productOperationLogDao.batchInsertProductOperationLog(productLogList);
			pr.setState(true);
			pr.setMessage("操作成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("共享商品编辑异常："+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
}