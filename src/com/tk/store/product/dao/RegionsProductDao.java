package com.tk.store.product.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface RegionsProductDao extends BaseDao<Map<String, Object>> {
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
	 * 查询商品信息列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductInfoList(Map<String, Object> paramMap);
	
	/**
	 * 新增区域商品关联明细数据
	 * @param paramMap
	 * @return
	 */
	int insertRegionProductDetail(Map<String, Object> paramMap);
	/**
	 * 商品零售详情
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductRetailDetail(Map<String, Object> paramMap);
	/**
	 * 区域商品sku数量
	 * @param map
	 * @return
	 */
	int queryRegionProductSkuCount(Map<String, Object> map);
	/**
	 * 所属区域下拉框
	 * @param paramMap 
	 * @return
	 */
	List<Map<String, Object>> queryUserSelect(Map<String, Object> paramMap);
	/**
	 * 查询商品所属区域
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserList(Map<String, Object> paramMap);
	/**
	 * 删除区域商品数据
	 * @param paramMap
	 * @return
	 */
	int deleteRegionProduct(Map<String, Object> paramMap);
	/**
	 * 批量导入成功
	 * @param paramMap
	 */
	int addBatch(Map<String, Object> paramMap);
	/**
	 * 删除区域商品明细
	 * @param paramMap
	 * @return
	 */
	int deleteRegionProductDetail(Map<String, Object> paramMap);
	/**
	 * 查询区域信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryUserInfo(Map<String, Object> paramMap);
	/**
	 * 区域商品是否存在
	 * @param map
	 * @return
	 */
	int queryRegionProductIsExist(Map<String, Object> map);
	/**
	 * AB区域不相同的商品
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductDifferList(Map<String, Object> paramMap);
	/**
	 * A区域商品导入B区域
	 * @param param
	 * @return
	 */
	int addRegionProduct(Map<String, Object> param);
	/**
	 * 批量导入明细
	 * @param param
	 * @return
	 */
	int addRegionProductDetailBatch(Map<String, Object> param);
	/**
	 * 商品规格sku数量
	 * @param map
	 * @return
	 */
	int queryProductSkuCount(Map<String, Object> map);
	/**
	 * 新增区域商品明细(过滤sku)
	 * @param map
	 * @return
	 */
	int insertRegionProductDetailFilter(Map<String, Object> map);
	/**
	 * 获取区域商品数量
	 * @param paramMap
	 * @return
	 */
	int queryRegionProductCount(Map<String, Object> paramMap);
	/**
	 * 获取区域商品列表
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryRegionProductList(Map<String, Object> paramMap);
	/**
	 * 查询当前区域当前货号是否已经导入过
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryProductRegionByUserAndItenmuber(Map<String, Object> paramMap);
	/**
	 * 查询已导入商品设置的信息
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryProductRegionDetail(Map<String, Object> paramMap);
	/**
	 * 删除区域商品数据
	 * @param paramMap
	 * @return
	 */
	int deleteRegionProductByUserAndItemnumber(Map<String, Object> paramMap);
	/**
	 * 删除区域商品数据
	 * @param paramMap
	 * @return
	 */
	int deleteRegionProductDetailByUserAndItemnumber(Map<String, Object> paramMap);
}
