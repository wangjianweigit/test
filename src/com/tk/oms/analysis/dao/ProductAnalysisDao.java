package com.tk.oms.analysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.basicinfo.dao.BaseDao;
@Repository
public interface ProductAnalysisDao extends BaseDao {
	
	/**
     * 统计商品分析总数
     * @param params
     * @return
     */
    public int queryProductAnalysisCount(Map<String,Object> params);
    /**
     * 商品分析列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryProductAnalysisList(Map<String,Object> params);
    /**
     * 商品分析列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryProductChartList(Map<String,Object> params);
    /**
     * 统计商品分析总数<私有>
     * @param params
     * @return
     */
    public int queryPvtpProductAnalysisCount(Map<String,Object> params);
    /**
     * 商品分析列表<私有>
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryPvtpProductAnalysisList(Map<String,Object> params);
    /**
     * 商品分析列表<私有>
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryPvtpProductChartList(Map<String,Object> params);

}
