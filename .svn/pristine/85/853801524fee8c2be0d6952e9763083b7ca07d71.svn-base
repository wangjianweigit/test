package com.tk.oms.decoration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.sys.common.BaseDao;

@Repository
public interface DirectoryDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询目录结构层级
	 * @return
	 */
	public List<Map<String, Object>> queryDirectoryAll();
	/**
	 * 根据节点名称查询是否存在
	 * @param paramMap
	 * @return
	 */
	public int queryDirectoryByDirectoryName(Map<String, Object> paramMap);
	/**
	 * 查询是否存在子节点
	 * @param paramMap
	 * @return
	 */
	public int queryDirectoryByParentId(Map<String, Object> paramMap);
	/**
	 * 删除节点信息
	 * @param paramMap
	 * @return
	 */
	public int delete(Map<String, Object> paramMap);
	/**
	 * 查询节点详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryDirectoryDetail(Map<String, Object> paramMap);
	/**
	 * 查询文件类型和扩展名信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryFileTypeList(Map<String, Object> paramMap);
	/**
	 * 更新节点
	 * @param map
	 * @return
	 */
	public int updateByType(Map<String, Object> map);
	/**
	 * 新增节点
	 * @param paramMap
	 * @return
	 */
	public int insertByType(Map<String, Object> paramMap);
	
	/**
	 * 查询商品主图目录的ID，默认其父节点为1
	 * @return
	 */
	public long queryProductMainImgDir();
	
	/**
	 * 获取素材目录管理中，商品图片的所有子目录
	 * @param paramMap  paramMap.is_share 当前文件夹内容是否分享给分销商。 空则表示前部  ；  1：不分享  ； 2：分享
	 * @return
	 */
	public List<Map<String, Object>> queryProductImgChild(Map<String, Object> paramMap);
}
