package com.tk.oms.basicinfo.dao;


import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Map;

@Repository
public interface SiteInfoDao{
	/**
	 * 	检测站点名称是否重复
	 * @param info
	 * @return  2：不存在，可用 ；  1：已经存在，不可用
	 */
	public int checkSiteName(Map<String,Object> params);
	
	/**
	 * 分页查询记录列表
	 * @param t
	 * @return
	 */
	public List<Map<String,Object>> queryListForPage(Map<String,Object> params);
	/**
	 * 查询所有站点
	 * @param t
	 * @return
	 */
	public List<Map<String,Object>> queryAllList(Map<String,Object> params);
	
	/**
	 * 修改站点
	 * @param t
	 * @return
     */
	public int updateSiteInfo(Map<String,Object> params);


	/**
	 * 删除站点
	 * @param t
	 * @return
     */
	public int deleteSiteInfo(Map<String,Object> params);
	/**
	 * 新增站点
	 * @param t
	 * @return
	 */
	public int insertSiteInfo(Map<String,Object> params);
	
	/**
	 * 新增站点仓库关联信息
	 * @param map
	 */
	public void insertSiteWarehouse(Map<String, Object> map);
	
	/**
	 * 删除站点仓库关联信息
	 * @param map
	 */
	public void deleteSiteWarehouse(Map<String, Object> map);
	
	
	/**
	 * 查询站点关联仓库
	 * @param string
	 * @return
	 */
	public List<Map<String, Object>> querySiteWarehouseList(Map<String, Object> params);
	
	/**
	 * 查询大仓列表
	 * @param params 
	 * @return
	 */
	public List<Map<String, Object>> queryWarehouseParentInfoList(Map<String, Object> params);
	/**
	 * 查询分仓列表
	 * @param map 
	 * @return
	 */
	public List<Map<String, Object>> queryWarehouseChilderInfoList(Map<String, Object> map);
	/**
	 * 查询仓库列表
	 * @return
	 */
	public List<Map<String, Object>> queryPlatformWarehouseList();
	
	/**
	 * 查询站点数量
	 * @return
	 */
	public int querySiteInfoCount(Map<String, Object> map);
	
	/**
	 * 根据站点ID和货号查询站点商品配置信息
	 *
	 * @param siteProduct
	 * @return
	 */
	public Map<String, Object> querySiteProduct(Map<String, Object> map);
	
	/**
	 * 查询站点详情
	 *
	 * @param siteProduct
	 * @return
	 */
	public Map<String, Object> querySiteInfoDetail(Map<String, Object> map);
	
	/**
	 * 启用禁用站点状态
	 * 
	 */
	int updateSiteInfoState(Map<String, Object> map);
	 /**
	 * 更新排序
	 * @param paramMap
	 * @return
	 */
	public int updateSiteWarehouseSort(Map<String, Object> paramMap);
	/**
	 * 查询站点仓库详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> querySiteWarehouseById(Map<String, Object> paramMap);
	
	 /**
     * 查询所有的平台可用展示的仓库列表，同时根据商家ID返回是否已经作为商家的默认缺货订购仓库 
     * @return
     */
    List<Map<String,Object>> queryAllSiteWarehouseList(Map<String,Object> map);
    
    /**
     * 查询童库平台站点或私有平台站点
     * @return
     */
    List<Map<String,Object>> queryPlatformSiteList(Map<String,Object> map);
    
    /**
	 * 通过商家id查询站点信息
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> querySiteInfoByStationedUser(Map<String, Object> paramMap);
}
