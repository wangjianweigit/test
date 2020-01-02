package com.tk.oms.basicinfo.dao;


import com.tk.sys.common.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品颜色管理
 * @author zhenghui
 */
@Repository
public interface ColorsDao extends BaseDao {

    /**
     * 查询色系列表
     * @return 色系列表
     */
    public List<Map<String,Object>> queryColorsGroupList();

    /**
     * 通过parent_id查询颜色列表
     * @param paramId
     * @return  颜色列表
     */
    public List<Map<String,Object>> queryColorsList(long paramId);

    /**
     * 通过名称查询色系数量
     * @param params
     * @return
     */
    public int queryColorsGroupCountByName(Map<String,Object> params);

    /**
     * 通过名称查询颜色数量
     * @param params
     * @return
     */
    public int queryColorsCountByName(Map<String,Object> params);

    /**
     * 通过代码查询颜色数量
     * @param params
     * @return
     */
    public int queryColorsCountByCode(Map<String,Object> params);

    /**
     * 通过颜色编码查询颜色数量
     * @param params
     * @return
     */
    public int queryColorCountByNumber(Map<String,Object> params);

    /**
     * 通过颜色名称查询商品sku数量
     * @param params
     * @return
     */
    public int querySkuColorCountByName(Map<String,Object> params);

    /**
     * 通过色系ID查询商品sku数量
     * @param params
     * @return
     */
    public int querySkuColorCountByNames(Map<String,Object> params);

    /**
     * 判断是否存在该记录
     * @param params
     * @return
     */
    public int isExist(Map<String,Object> params);

    /**
     * 通过父ID删除子元素
     * @param params
     * @return
     */
    public int deleteByParentId(Map<String,Object> params);


}
