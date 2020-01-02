package com.tk.oms.marketing.dao;

import java.util.List;
import java.util.Map;

import com.tk.sys.common.BaseDao;

public interface MemberGradeDao extends BaseDao<Map<String, Object>>{
	/**
	 * 查询会员等级列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMemberGradeList(Map<String, Object> paramMap);
	/**
	 * 查询会员等级总数
	 * @param paramMap
	 * @return
	 */
	public int queryMemberGradeCount(Map<String, Object> paramMap);
	/**
	 * 等级名称是否重复
	 * @param paramMap
	 * @return
	 */
	public int checkmemberGradeName(Map<String, Object> paramMap);
	/**
	 * 等级代码是否重复
	 * @param paramMap
	 * @return
	 */
	public int checkmemberGradeCode(Map<String, Object> paramMap);
	
	/**
	 * 获取全部会员等级(下拉框)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryMemberGradeAll();
	/**
	 * 删除会员等级
	 * @param paramMap
	 * @return
	 */
	public int delete(Map<String, Object> paramMap);

}
