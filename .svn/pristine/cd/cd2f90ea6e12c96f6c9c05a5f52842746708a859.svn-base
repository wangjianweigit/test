package com.tk.oms.product.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductBrandDao extends BaseDao<Map<String, Object>> {
	
	/**
     * 获取商品品牌列表数据
     * @param request
     * @return
     */
	public List<Map<String,Object>> queryProductBrandList(Map<String, Object> paramMap);
	/**
     * 获取商品品牌列表（下拉框专用）
     * @param request
     * @return
     */
	public List<Map<String,Object>> queryProductBrandAllList();
	/**
     * 新增商品品牌
     * @param request
     * @return
     */
	public int insertProductBrand(Map<String, Object> paramMap);
	/**
     * 商品品牌编辑更新
     * @param request
     * @return
     */
	public int updateProductBrand(Map<String, Object> paramMap);
	/**
     * 商品品牌删除
     * @param request
     * @return
     */
	public int deleteProductBrand(Map<String, Object> paramMap);
	/**
     * 商品品牌数量
     * @param request
     * @return
     */
	public int queryProductBrandCount(Map<String, Object> paramMap);

	/**
	 * 通过商品品牌名称或者编码获取品牌数量
	 * @param paramMap
	 * @return
	 */
	public int queryBrandCountByNameOrCode(Map<String, Object> paramMap);
	/**
	 * 查询品牌信息列表
	 * @return
	 */
	public List<Map<String, Object>> queryProductBrandInfoList();

	/**
	 * 批量插入商品品牌的分类
	 * @param paramMap
	 * @return
     */
	public int batchInsertBrandClassify(Map<String, Object> paramMap);

	/**
	 * 删除商品品牌的分类
 	 * @param paramMap
	 * @return
     */
    public int deleteBrandClassify(Map<String, Object> paramMap);

	/**
	 * 通过商品品牌查询商品数量
	 * @param paramMap
	 * @return
     */
    public int queryProductCountByBrand(Map<String, Object> paramMap);

	/**
	 * 查询品牌分类列表
	 * @param paramMap
	 * @return
     */
	public List<Map<String, Object>> queryBrandClassifyById(Map<String, Object> paramMap);

	/**
	 * 查询已开通店铺的入驻商列表
	 * @return
	 */
	public List<Map<String, Object>> listOpenShopStationed();

}
