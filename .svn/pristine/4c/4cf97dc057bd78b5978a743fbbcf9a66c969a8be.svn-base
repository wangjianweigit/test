package com.tk.oms.sys.dao;

import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;

/**
 * 站点延后时间模板管理
 * @author zhenghui
 */
@Repository
public interface SiteDelayTemplateDao {
	
	/**
	 * 分页查询数据列表
	 * @param obj
	 * @return 返回数据列表数据
	 */
	public List<Map<String,Object>> queryForPage(Map<String,Object> obj);
	
	/**
	 * 查询数据总条数
	 * @param obj
	 * @return 返回总的数据条数
	 */
	public int queryForCount(Map<String,Object> obj);

    /**
     * 判定是否存在一条模板记录
     * @param paramId
     * @return
     */
    public int isExistTemplate(Long paramId);

    /**
     * 查询所有站点
     * @return
     */
    public List<Map<String,Object>> querySiteInfo(Map<String,Object> params);

    /**
     * 通过模板ID查询站点延后时间
     * @param paramId
     * @return
     */
    public List<Map<String,Object>> querySiteDelayById(Long paramId);

    /**
     * 判定是否存在相同模板名称
     * @param params
     * @return
     */
    public int isExistTemplateName(Map<String,Object> params);

    /**
     * 获取当前入驻商默认模板
     * @param paramId
     * @return
     */
    public Map<String, Object> queryDefaultTemplate(Long paramId);

    /**
     * 设置默认站点延后时间模板
     * @param params
     * @return
     */
    public int updateDefaultTemplate(Map<String,Object> params);

    /**
     * 设置站点延后时间模板停用和启用
     * @param params
     * @return
     */
    public int updateTemplateState(Map<String,Object> params);

    /**
     * 删除站点延后时间配置
     * @param params
     * @return
     */
    public int deleteSiteDelay(Map<String,Object> params);
    
    /**
	 * 根据ID查询数据库记录实体
	 * @param id 数据库中的主键ID
	 * @return 查询得到的返回结果
	 */
	public Map<String,Object> queryById(long id);
	
	/**
	 * 通过ID删除数据
	 * @param obj
	 * @return 返回删除数量
	 */
	public int deleteById(long id);
	/**
	 * 批量插入数据
	 * @param obj
	 * @return 新增数量
	 */
	public int batchInsert(List<Map<String,Object>> list);
	/**
	 * 插入数据
	 * @param obj
	 * @return 新增数量
	 */
	public int insert(Map<String,Object> obj);
	
	/**
	 * 更新数据
	 * @param obj
	 * @return 返回更新数量
	 */
	public int update(Map<String,Object> obj);
	
	/**
	 * 查询站点延后显示时间模板下拉框列表
	 * @param params 入驻商ID信息
	 * @return	站点延后显示时间模板集合
	 */
	public List<Map<String,Object>> querySiteDelayTempletList(Map<String,Object> params);
	/**
	 * 查询站点延后显示时间模板详细信息
	 * @param params 模板ID
	 * @return	模板详情
	 */
	public List<Map<String,Object>> querySiteDelayTempletDetail(Map<String,Object> params);
	/**
	 * 删除未设置显示时间的数据
	 * @param obj
	 * @return 删除数量
	 */
	public int deleteNotin(Map<String,Object> params);
	/**
	 * 批量插入数据
	 * @param obj
	 * @return 新增数量
	 */
	public int batchInsertOrUpdate(Map<String,Object> params);
	/**
	 * 删除数据
	 * @param obj
	 * @return 返回删除数量
	 */
	public int delete(Map<String,Object> obj);
	
	/**
	 * 获取某个商品的站点延后显示时间
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryProductSiteDelay(Map<String,Object> params);
	
	/**
	 * 删除站点设置时间信息（共享库商品移除私有站点设置相关） 
	 * @param obj
	 * @return 返回删除数量
	 */
	public int deletePrivate(Map<String,Object> obj);
	
	/**
	 * 批量插入站点设置时间信息（共享库商品）
	 * @param obj
	 * @return 返回删除数量
	 */
	public int batchInsertPrivate(Map<String,Object> obj);

}
