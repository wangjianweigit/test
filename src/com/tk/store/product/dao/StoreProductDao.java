package com.tk.store.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface StoreProductDao extends BaseDao<Map<String, Object>> {
	/**
	 * 商品数量
	 * @param paramMap
	 * @return
	 */
	int queryProductCount(Map<String, Object> paramMap);
	/**
	 * 商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductListForPage(Map<String, Object> paramMap);
	/**
	 * 是否销售
	 * @param paramMap
	 * @return
	 */
	int updateStoreProductState(Map<String, Object> paramMap);
	/**
	 * 商品库数量
	 * @param paramMap
	 * @return
	 */
	int queryProductLibraryCount(Map<String, Object> paramMap);
	/**
	 * 商品库列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductLibraryListForPage(Map<String, Object> paramMap);
	/**
	 * 获取商品详情基本信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryProductDetail(Map<String, Object> paramMap);
	/**
	 * 获取商品详情参数信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductParamsList(Map<String, Object> paramMap);
	/**
	 * 获取商品sku列表信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductSkuList(Map<String, Object> paramMap);
	/**
	 * 获取商品图片
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductImagesList(Map<String, Object> paramMap);
	/**
	 * 查询联营商品信息
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryProductInfo(Map<String, Object> paramMap);
	/**
	 * 获取商品大类
	 * @param info
	 * @return
	 */
	int queryBasicType(Map<String, Object> info);
	/**
	 * 查询sku颜色信息
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> querySkuColorList(Map<String, Object> map);
	/**
	 * 查询sku基本信息
	 * @param subMap
	 * @return
	 */
	List<Map<String, Object>> querySkuInfoList(Map<String, Object> subMap);
	/**
	 * 获取商品图片数据
	 * @param paramMap
	 * @return
	 */
	List<String> queryImgList(Map<String, Object> paramMap);
	/**
	 * 新增门店商品关联明细数据
	 * @param paramMap
	 * @return
	 */
	int insertStoreProductDetail(Map<String, Object> paramMap);
	/**
	 * 商品零售详情
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductRetailDetail(Map<String, Object> paramMap);
	/**
	 * 查询sku零售价
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> querySkuPrice(Map<String, Object> user);
	/**
	 * 商家商品规格sku数量
	 * @param map
	 * @return
	 */
	int queryStoreProductSkuCount(Map<String, Object> map);
	/**
	 * 获取商家ID
	 * @param paramMap
	 * @return
	 */
	List<String> queryById(Map<String, Object> paramMap);
	/**
	 * 查询所属商家
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserList(Map<String, Object> paramMap);
	/**
	 * 所属商家下拉框
	 * @return
	 */
	List<Map<String, Object>> queryUserSelect();
	/**
	 * 商家商品是否存在
	 * @param map
	 * @return
	 */
	int queryStoreProductIsExist(Map<String, Object> map);
	/**
	 * AB区域不相同的商品信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductDifferList(Map<String, Object> paramMap);
	/**
	 * 批量导入商品明细
	 * @param param
	 * @return
	 */
	int addStoreProductDetailBatch(Map<String, Object> param);
	/**
	 * 商品信息列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductInfoList(Map<String, Object> paramMap);
	/**
	 * 查询商家信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserInfo(Map<String, Object> paramMap);
	/**
	 * 查询商品相关的商家信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserListByItemnumber(Map<String, Object> paramMap);
	/**
	 * 商品规格sku数量
	 * @param map
	 * @return
	 */
	int queryProductSkuCount(Map<String, Object> map);
	/**
	 * 新增商家商品明细(过滤sku)
	 * @param map
	 * @return
	 */
	int insertStoreProductDetailFilter(Map<String, Object> map);
	/**
	 * 新增商家商品临时数据
	 * @param list
	 * @return
	 */
	int insertTmpStoreProductBatch(List<Map<String, Object>> list);
	/**
	 * 批量新增商家商品
	 * @param paramMap
	 * @return
	 */
	int insertStoreProductBatch(Map<String, Object> paramMap);
	/**
	 * 批量新增商品明细
	 * @param paramMap
	 * @return
	 */
	int insertStoreProductDetailBatch(Map<String, Object> paramMap);
	/**
	 * 批量获取商品信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getProductInfo(Map<String, Object> paramMap);
	/**
	 * 批量获取商品参数
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getProductParamsList(Map<String, Object> paramMap);
	/**
	 * 批量获取商品颜色数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getSkuColorList(Map<String, Object> paramMap);
	/**
	 * 批量获取sku基本信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getSkuInfoList(Map<String, Object> paramMap);
	/**
	 * 批量获取商品图片数据
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> getImgList(Map<String, Object> paramMap);
	/**
	 * 批量新增门店商品关联明细数据
	 * @param productList
	 * @return
	 */
	int batchInsertStoreProductDetail(List<Map<String, Object>> productList);
	
}
