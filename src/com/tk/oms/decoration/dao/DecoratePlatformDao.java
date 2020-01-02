package com.tk.oms.decoration.dao;

import com.tk.sys.common.BaseDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 装修中心管理
 * @author shif
 */
@Repository
public interface DecoratePlatformDao extends BaseDao<Map<String, Object>> {
	/**获取装修组件列表*/
	public List<Map<String, Object>> queryDecorateModuleList(Map<String, Object> params);
	/**获取装修模板装修页列表*/
	public List<Map<String, Object>> queryDecoratePageList(Map<String, Object> params);
	/**获取装修布局列表*/
	public List<Map<String, Object>> queryPageLayoutList(Map<String, Object> params);
	/**获取装修布局下组件列表*/
	public Object queryPageLayoutModuleList(Map<String, Object> tempModuleParamMap);
	/**获取页面id*/
	public int queryPageId(Map<String, Object> params);
	/**验证装修页面名称是否重复*/
	public int countPageByPageName(Map<String, Object> params);
	/**新增页面*/
	public int insertPage(Map<String, Object> params);
	/**获取布局id*/
	public int queryPageLayoutId(Map<String, Object> params);
	/**新增页面布局*/
	public int insertPageLayout(Map<String, Object> params);
	/**删除页面布局*/
	public int deletePageLayout(Map<String, Object> params);
	/**获取页面布局组件Id*/
	public int queryPageLayoutModuleId(Map<String, Object> params);
	/**新增页面布局组件*/
	public int insertPageLayoutModule(Map<String, Object> params);
	/**删除页面布局组件*/
	public int deletePageLayoutModule(Map<String, Object> params);
	/**新增页面布局组件时排序更新处理*/
	public int updateyPageLayoutModuleSortIdWhenInsert(Map<String, Object> params);
	/**删除页面布局组件时排序更新处理*/
	public int updateyPageLayoutModuleSortIdWhenDelete(Map<String, Object> params);
	/**获取页面布局详情*/
	public Map<String, Object> queryPageLayoutDetail(Map<String, Object> params);
	/**
	 * 排序-页面布局排序
	 */
	public int updatePageLayoutSortId(Map<String, Object> params);
	
	/**页面布局排序更新排序值*/
	public int updatePageLayoutSort(Map<String, Object> params);
	
	/**获取页面布局组件详情*/
	public Map<String, Object> queryPageLayoutModuleDetail(Map<String, Object> params);
	/**一个装修页面中，更新所有的组件排序排序值*/
	public int updatePageLayoutModuleSortId(Map<String, Object> params);
	/**获取页面布局下组件数量*/
	public int queryPageLayoutModuleCount(Map<String, Object> params);
	/**页面布局下组件编辑*/
	public int updatePageLayoutModule(Map<String, Object> params);
	
	/**
	 * 获取装修页的布局信息,以及布局中的控件，控件的排序信息
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryLayoutModuleDetailByPageId(Map<String, Object> params);
	/**
	 * 根据装修组件的ID，获取装修组件的详细信息
	 * @param module_id 组件id
	 * @return
	 */
	public Map<String, Object> queryDecorateModuleDetail(@Param("module_id")long module_id);
	/**
	 * 更新装修页面的变更时间以及变更人
	 * @param params
	 * @return
	 */
	int updateDecoratePage(Map<String, Object> params);
	/**
	 * 批量更新页面布局信息的排序字段
	 * @param params
	 * @return
	 */
	int beachUpdatePageLayoutSort(@Param("list")List<Map<String, Object>> list);
	/**
	 * 将当前的首页设置为非首页
	 * @param params
	 * @return
	 */
	int updateTemplateHomePage(Map<String, Object> params);
	/**
	 * 获取装修页详情
	 * @param params page_id 装修页面ID
	 * @param params type    需要获取的页面信息类型，详见接口API
	 * @return
	 */
	Map<String, Object> queryDecoratePageDetail(Map<String,Object> param);

	/**
	 * 通过站点ID查询装修用户列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryDecorateUserListBySiteId(Map<String, Object> params);

	/**
	 * 通过用户ID获取用户信息
	 * @param param
	 * @return
     */
	Map<String, Object> queryUserInfoById(Map<String,Object> param);

	/**
	 * 通过页面ID获取页面信息（未使用）
	 * @param param
	 * @return
     */
	Map<String, Object> queryDecoratePageById(Map<String,Object> param);
}
