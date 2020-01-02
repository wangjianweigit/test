package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {

	/**
	 * 获取待审批商品列表
	 */
	public List<Map<String,Object>> queryPendingApprovalProductList(Map<String,Object> paramMap);
	/**
	 * 获取待审批商品总数
	 */
	public int queryPendingApprovalProductCount(Map<String,Object> paramMap);
	/**
	 * 获取待审批商品详情
	 */
	public Map<String, Object> queryPendingApprovalProductDetail(Map<String,Object> paramMap);
	/**
	 * 获取待审批商品参数列表
	 */
	public List<Map<String,Object>> queryPendingApprovalProductParamsList(Map<String,Object> paramMap);
	/**
	 * 获取待审批商品sku列表
	 */
	public List<Map<String,Object>> queryPendingApprovalProductSkuList(Map<String,Object> paramMap);
	/**
	 * 获取商品规格数据列表
	 */
	public List<Map<String,Object>> queryProductSpecList(Map<String,Object> paramMap);
	/**
	 * 获取商品详情
	 */
	public Map<String, Object> queryProductDetail(Map<String,Object> paramMap);
	/**
	 * 获取商品参数列表
	 */
	public List<Map<String,Object>> queryProductParamsList(Map<String,Object> paramMap);
	/**
	 * 获取商品sku列表
	 */
	public List<Map<String,Object>> queryProductSkuList(Map<String,Object> paramMap);
	/**
	 * 更新商品基本信息表（从待审批商品主表）
	 */
	public int updateProductInfoFromPendingApprovalProduct(Map<String, Object> paramMap);
	/**
	 * 更新商品参数表（从待审批商品参数表）
	 */
	public int updateProductParamFromPendingApprovalProductParam(Map<String, Object> paramMap);
	/**
	 * 更新商品sku表（从待审批商品sku表）
	 */
	public int updateProductSkuFromPendingApprovalProductSku(Map<String, Object> paramMap);
	/**
	 * 更新待审批商品状态(审批通过或驳回)
	 */
	public int updatePendingApprovalProductState(Map<String, Object> paramMap);
	/**
	 * 获取商品列表
	 */
	public List<Map<String,Object>> queryProductList(Map<String,Object> paramMap);
	/**
	 * 获取商品总数
	 */
	public int queryProductCount(Map<String,Object> paramMap);
	/**
	 * 获取商品货号（生成）
	 */
	public String queryProductItemnumber(Map<String, Object> paramItemnumberMap);
	/**
	 * 获取商品图片
	 */
	public List<Map<String, Object>> queryProductImagesList(Map<String, Object> paramMap);

    /**
     * 获取商品包材信息
     * @param paramMap
     * @return
     */
	public List<Map<String, Object>> queryProductWrapperList(Map<String, Object> paramMap);
	/**
	 * 获取商品站点延迟显示时间
	 */
	public List<Map<String, Object>> queryProductSiteDelayList(Map<String, Object> paramMap);
	/**
	 * 获取商品显示区域
	 */
	public List<Map<String, Object>> queryProductRegionAreaListList(Map<String, Object> paramMap);
	/**
	 * 商品编辑更新（运费模板设置）
	 */
	public int updateProduct(Map<String, Object> paramMap);
	/**
	 * 商品强制下架
	 */
	public int updateProductState(Map<String, Object> paramMap);
	/**
	 * 商品季节获取【下拉框】
	 */
	public List<Map<String, Object>> queryProductSeasonList(Map<String, Object> paramMap);
	/**
	 * 商品批量强制下架
	 */
	public int bacthUpdateProductState(Map<String, Object> paramMap);
	/**
	 * 获取下架商品状态数量
	 */
	public int queryProductSoldOutCount(Map<String,Object> paramMap);
	/**
	 * 更新商品图片表的货号
	 */
	public int updateProductImagesItemnumber(Map<String, Object> paramMap);
	/**
	 * 根据商品名称或货号，模糊搜索商品
	 */
	public int queryProductCountByKwyword(Map<String, Object> paramMap);
	/**
	 * 根据商品名称或货号，模糊搜索商品
	 */
	public List<Map<String, Object>> queryProductListByKwyword(Map<String, Object> paramMap);
	/**
	 * 根据一个或者多个商品货号获取商品列表
	 */
	public List<Map<String, Object>> queryProductByItemnumbers(Map<String, Object> paramMap);
	/**
	 * 商品审批通过后，增加商品颜色再次审批，将新的颜色插入排序表
	 */
	public int insertAdditionalProductColorSort(@Param("product_id")long product_id);
	
	/**
	 * 获取分销商品列表
	 */
	public List<Map<String,Object>> queryDistributionProductList(Map<String,Object> paramMap);
	/**
	 * 获取分销商品总数
	 */
	public int queryDistributionProductCount(Map<String,Object> paramMap);
	/**
	 * 更新是否支持分销状态
	 */
	public int updateIsDistributionState(Map<String,Object> paramMap);
	
	/**
	 * 获取商品对应的尺码未设置仓储费的个数
	 */
	public int queryProductSizeStorageChargesNotExistsNum(Map<String,Object> paramMap);
	
	/**
	 * 修改商品是否销量排序
	 */
	public int updateIsSaleSortState(Map<String,Object> paramMap);
	/**
	 * 获取商品对应的分类未设置仓储费的个数
	 */
	public int queryProductTypeStorageChargesNotExistsNum(Map<String, Object> paramMap);
	/**
	 * 查询商品关联配码数量
	 */
	public int queryProductWithCodeCount(Map<String, Object> paramMap);
	/**
	 * 通过商品ID判断商品是否存在
	 * @return 0 不存在  1.存在
	 */
	public int queryByIdAndUserId(Map<String,Object> params);


	public int queryFirstSellSortValue();
	/**
	 * 根据商品货号获取商品基本信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryByItemnumber_Base(Map<String, Object> paramMap);
	
	/**
	 * 根据货号获取商品颜色信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryColorListByItemNumber(Map<String, Object> paramMap);
	/**
	 * 根据货号以及颜色获取商品规格信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySpecsListByItemnumberAndColor(Map<String, Object> paramMap);
	/**
	 * 根据货号以及颜色和规格获取商品尺码信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> querySizeListByItemnumberAndColorAndSpecs(Map<String, Object> paramMap);
	
	/**
	 * 根据货号获取商品尺码和内长
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryCommodityListByItemNumber(Map<String, Object> paramMap);
	/**
	 * 根据货号查询商品参数
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductParam(Map<String, Object> paramMap);
	/**
	 * 商品主推款标识设置
	 * @param paramMap
	 * @return
	 */
	public int productMainProductFlagSet(Map<String, Object> paramMap);
	/**
	 * 查询联营商家是否关联商品
	 * @param paramMap
	 * @return
	 */
	public int queryStoreProductIsExists(Map<String, Object> paramMap);
	/**
	 * 查询是否存在被修改过吊牌价的sku数据
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryProductPrizeTagDifferent(Map<String, Object> paramMap);
	/**
	 * 热卖标签启用/禁用
	 * @param paramMap
	 * @return
	 */
	public int updateProductHotSaleLabel(Map<String, Object> paramMap);
	/**
	 * 新品标签启用/禁用
	 * @param paramMap
	 * @return
	 */
	public int updateProductNewProductLabel(Map<String, Object> paramMap);
	/**
	 * 新增社交首页商品管理数据
	 * @param paramMap
	 * @return
	 */
	public int insertSocialProduct(Map<String, Object> paramMap);
	/**
	 * 更新商品表终止补货启停用状态
	 * @param paramMap
	 * @return
	 */
	public int updateProductIsOutstock(Map<String, Object> paramMap);
	/**
	 * 更新商品sku表缺货订购状态
	 * @param paramMap
	 * @return
	 */
	public int updateProductSkuIsOutstock(Map<String, Object> paramMap);
	/**
	 * 记录终止补货商品货号
	 * @param paramMap
	 * @return
	 */
	public int insertProductStopOutstock(Map<String, Object> paramMap);

	/**
	 * 获取申请的图片列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductImagesApplyList(Map<String, Object> paramMap);

	/**
	 * 清空当前货号的图片
	 * @param paramMap
	 * @return
	 */
	int deleteProductImages(Map<String, Object> paramMap);

	/**
	 * 更新商品图片信息表（从待审批商品主表）
	 * @param paramMap
	 * @return
	 */
	int updateProductImageFromProductImageApply(Map<String, Object> paramMap);
	
	/**
	 * 更新是否支持私有平台下载销售【童库-私有商品共享】
	 */
	public int updateProductIsPrivtateState(Map<String,Object> paramMap);
	
}