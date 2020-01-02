package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

public interface AreaSalesAnalysisDao {
	/**
	 * 区域销售报表数量
	 * @param paramMap
	 * @return
	 */
	public int queryCount(Map<String, Object> paramMap);
	/**
	 * 区域销售报表列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryList(Map<String, Object> paramMap);
	/**
	 * 区域销售报表总计
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryListTotal(Map<String, Object> paramMap);

}
