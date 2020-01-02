package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 会员物流模板管理
 * @author liujialong
 * @date 2018-9-10
 */
@Repository
public interface MemberLogisticsTemplateDao {
	
	/**
     * 查询会员物流模板列表
     * @return
     */
    List<Map<String, Object>> queryMemberLogisticsTemplateList(Map<String, Object> params);
    /**
     * 查询会员物流模板列表数量
     * @param params
     * @return
     */
    int queryMemberLogisticsTemplateCount(Map<String, Object> params);
    /**
     * 查询平台仓库列表
     * @return
     */
    List<Map<String, Object>> queryPlatformWarehouseList(Map<String, Object> params);
    /**
     * 查询普通物流信息
     * @return
     */
    List<Map<String, Object>> queryLogisticsCompanyByType(Map<String, Object> params);
    /**
     * 新增会员物流模板主表信息
     * @param params
     * @return
     */
    int insertUserLogisticsTemplate(Map<String, Object> params);
    /**
     * 新增会员物流模板详表信息
     * @param params
     * @return
     */
    int insertUserLogisticsTemplateDetail(Map<String, Object> params);
    /**
     * 会员物流模板详情
     * @param params
     * @return
     */
    Map<String, Object> queryMemberLogisticsTemplateDetail(Map<String, Object> params);
    /**
     * 会员物流模板详情列表
     * @param params
     * @return
     */
    List<Map<String, Object>> queryMemberLogisticsTemplateDetailList(Map<String, Object> params);
    /**
     * 编辑会员物流模板主表信息
     * @param params
     * @return
     */
    int updateUserLogisticsTemplate(Map<String, Object> params);
    /**
     * 查询当前模板名称数量
     * @param params
     * @return
     */
    int queryUserLogisticsTemplateNameCount(Map<String, Object> params);
    /**
     * 删除会员物流模板详情
     * @param params
     * @return
     */
    int deleteUserLogisticsTemplateDetail(Map<String, Object> params);
    /**
     * 删除会员物流模板主表信息
     * @param params
     * @return
     */
    int deleteUserLogisticsTemplate(Map<String, Object> params);
    /**
     * 判断当前模板名称是否被关联
     * @param params
     * @return
     */
    int queryIsRelatedByUser(Map<String, Object> params);
    /**
     * 会员物流模板下拉数据
     * @param params
     * @return
     */
    List<Map<String, Object>> queryMemberLogisticsTemplateOption(Map<String, Object> params);

    /**
     * 查询平台配送方式在物流模板中是否被使用
     * @param params
     * @return
     */
    int queryLogisticsIsUsedByTmpl(Map<String, Object> params);

}
