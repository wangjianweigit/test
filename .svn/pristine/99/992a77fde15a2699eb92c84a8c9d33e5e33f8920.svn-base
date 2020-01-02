package com.tk.oms.basicinfo.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : AreaProductControlSetDao
 * 区域控货配置数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/11 18:21
 */
@Repository
public interface AreaProductControlSetDao {

    /**
     * 分页查询控货类型记录数
     * @param params 查询参数
     * @return
     */
    public List<Map<String, Object>> queryTypeListForPage(Map params);

    /**
     * 查询控货类型记录数
     * @param param 查询条件
     * @return
     */
    public int queryTypeCountForPage(Map<String, Object> param);

    /**
     * 修改控货类型数据信息
     * @param params 修改内容
     * @return
     */
    public int updateTypeInfo(Map<String, Object> params);

    /**
     * 根据id查询控货类型数据信息
     * @param id 类型id
     * @return
     */
    public Map<String, Object> queryTypeInfoById(Long id);






    /**
     * 查询区域控货配置列表（业务员，业务经理）
     * @param params 查询参数
     * @return
     */
    List<Map<String, Object>> queryAreaControlSetListForBusiness(Map params);

    /**
     * 查询区域控货配置区域列表（业务经理）
     * @param params
     * @return
     */
    List<Map<String, Object>> queryAreaListForBusiness(Map params);
    /**
     * 查询区域控货配置区域列表（业务员）
     * @param params
     * @return
     */
    List<Map<String, Object>> queryAreaListForBusinessByManager(Map params);

    /**
     * 查询区域控货配置列表总数量（业务员，业务经理）
     * @param param 查询参数
     * @return
     */
    int queryAreaControlSetCountForBusiness(Map<String, Object> param);

    /**
     * 新增区域控货配置（业务员，业务经理）
     * @param param 添加数据
     * @return
     */
    int insertAreaControlSetForBusiness(Map<String, Object> param);

    /**
     * 删除区域控货配置（业务员，业务经理）
     * @param param
     * @return
     */
    int deleteAreaControlSetForBusiness(Map<String, Object> param);





    /**
     * 查询区域控货配置列表（门店）
     * @param params 查询参数
     * @return
     */
    List<Map<String, Object>> queryAreaControlSetListForStore(Map params);

    /**
     * 查询区域控货配置列表总数量（门店）
     * @param param 查询参数
     * @return
     */
    int queryAreaControlSetCountForStore(Map<String, Object> param);

    /**
     * 查询区域控货配置区域列表（门店）
     * @param params
     * @return
     */
    List<Map<String, Object>> queryAreaListForStore(Map params);

    /**
     * 新增区域控货配置（门店）
     * @param param 添加数据
     * @return
     */
    int insertAreaControlSetForStore(Map<String, Object> param);

    /**
     * 删除区域控货配置（门店）
     * @param param 添加数据
     * @return
     */
    int deleteAreaControlSetForStore(Map<String, Object> param);
    /**
     * 控货类型(下拉框)
     * @return
     */
	public List<Map<String, Object>> queryProductControlTypeComboBoxList();
	/**
	 * 品牌控货类型说明
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryBrandExplain(Map<String, Object> paramMap);

    /**
     * 查询区域控货说明品牌配置信息列表
     * @param id
     * @return
     */
    List<Map<String,Object>> queryExplainListByBrandId(long id);

    /**
     * 获取品牌控货说明记录数
     * @param brand_id
     * @return
     */
    int queryExplainCountByBrandId(long brand_id);

    /**
     * 根据品牌id删除品牌所有说明
     * @param brand_id
     * @return
     */
    int deleteExplainByBrandId(long brand_id);

    /**
     * 批量插入品牌控货说明信息
     * @param params
     * @return
     */
    int insertBrandExplainByBatch(Map<String, Object> params);

    /**
     * 获取所有控货类型列表数据
     * @return
     */
    List<Map<String,Object>> queryTypeList();
    
    /**
     * 查询一个业务经理配置的某个区域控货城市，是否被赋值给其下属的业务员或者业务经理使用
     * @param params 
     * @return 0,：表示未被下属的门店或者业务员使用     大于0：表示已被使用
     */
    int queryIsUserByCistIdAndManager(Map<String, Object> params);
    /**
     * 查询一个业务经理配置的所有区域控货城市，是否被赋值给其下属的业务员或者业务经理使用
     * @param params 
     * @return 0,：表示未被下属的门店或者业务员使用     大于0：表示已被使用
     */
    int queryIsUserByManager(Map<String, Object> params);
    /**
     * 获取区域控货配置（业务员，业务经理）
     * @param user_id
     * @return
     */
    List<String> queryAreaControlSetForBusiness(@Param("user_id")long user_id);
}
