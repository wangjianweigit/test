package com.tk.oms.decoration.dao;

import com.tk.oms.decoration.dto.AppIcoConfigDTO;
import com.tk.oms.decoration.dto.AppIcoConfigParamDTO;
import com.tk.sys.common.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 平台装修管理
 * @author zhenghui
 */
@Repository
public interface DecorateTemplateDao extends BaseDao<Map<String, Object>> {

    /**
     * 通过模板名称获取平台装修模板数量
     * @param params
     * @return 装修模板数量
     */
    public int queryCountByName(Map<String,Object> params);

    /**
     * 平台装修模板传送
     * @param params
     */
    public void decorateTemplateTransfer(Map<String,Object> params);

    /**
     * 分页获取app模板配置列表
     * @param params
     * @return
     */
    List<Map<String, Object>> listAppIcoTemplateConfig(Map<String,Object> params);

    /**
     * 查询app模板配置记录数
     * @param params
     * @return
     */
    int countAppIcoTemplateConfig(Map<String,Object> params);

    /**
     * 获取app模板配置数据
     * @param id
     * @return
     */
    AppIcoConfigDTO getAppIcoTemplateConfigInfo(@Param("id") long id);

    /**
     * 新增模板配置信息
     * @param appIcoConfigParam
     * @return
     */
    int insertAppIcoTemplateConfig(AppIcoConfigParamDTO appIcoConfigParam);

    /**
     * 修改模板配置信息
     * @param appIcoConfigParam
     * @return
     */
    int updateAppIcoTemplateConfig(AppIcoConfigParamDTO appIcoConfigParam);

    /**
     * 修改模板配置信息状态
     * @param appIcoConfigParam
     * @return
     */
    int updateAppIcoTemplateConfigState(AppIcoConfigParamDTO appIcoConfigParam);

    /**
     * 逻辑删除模板配置信息
     * @param id
     * @param operationUserId
     * @return
     */
    int deleteAppIcoTemplateConfigInfo(@Param("id") long id,@Param("operation_user_id") long operationUserId);

}
