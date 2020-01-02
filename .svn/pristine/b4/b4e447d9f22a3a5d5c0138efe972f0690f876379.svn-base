package com.tk.oms.basicinfo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : ProductWrapperDao
 * 商品包材信息维护数据库访问类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/10/30
 */
@Repository
public interface ProductWrapperDao {

    /**
     * 分页查询商品包材信息列表
     * @param params
     * @return
     */
    List<Map<String,Object>> listProductWrapperForPage(Map<String,Object> params);

    /**
     * 查询商品包材信息列表总数量
     * @param params
     * @return
     */
    int countProductWrapperForPage(Map<String,Object> params);

    /**
     * 查询商品包材信息
     * @param param
     * @return
     */
    Map<String,Object> getProductWrapperById(@Param("wrapper_id") long param);

    /**
     * 新增商品包材信息
     * @param params
     * @return
     */
    int insertProductWrapper(Map<String,Object> params);

    /**
     * 更新商品包材信息
     * @param params
     * @return
     */
    int updateProductWrapper(Map<String,Object> params);

    /**
     * 删除商品包材信息
     * @param params
     * @return
     */
    int deleteProductWrapper(Map<String,Object> params);

    /**
     * 生成包材ID
     * @return
     */
    long getWrapperId();

    /**
     * 根据ID获取包材编码
     * @param param
     * @return
     */
    String getWrapperCodeById(@Param("wrapper_id") long param);

    /**
     * 根据包材名称获取包材信息数量
     * @param params
     * @return
     */
    int countProductWrapperByName(Map<String,Object> params);

    /**
     * 根据包材名称获取包材信息数量
     * @param params
     * @return
     */
    int countProductWrapperRrf(Map<String,Object> params);


}
