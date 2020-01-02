package com.tk.oms.decoration.dao;

import java.util.List;
import java.util.Map;

public interface SocialProductDao {
	
	/**
     * 查询社交首页商品列表
     * @param params
     * @return
     */
    List<Map<String, Object>> querySocialProductList(Map<String, Object> params);

    /**
     * 查询社交首页商品总数
     * @param params
     * @return
     */
    int querySocialProductCount(Map<String, Object> params);
    
    /**
     * 社交首页商品发布
     * @param params
     * @return
     */
    int socialProductRelease(Map<String, Object> params);
    
    /**
     * 社交首页商品移除
     * @param params
     * @return
     */
    int socialProductRemove(Map<String, Object> params);
    
    /**
     * 社交首页商品切换展示方式
     * @param params
     * @return
     */
    int socialProductUpdateDisplayWay(Map<String, Object> params);
    
    /**
     * 社交首页商品设置最大或最小排序值
     * @param params
     * @return
     */
    int updateMaxOrMinSortValue(Map<String, Object> params);
    
    /**
     * 查询当前商品排序值
     * @param params
     * @return
     */
    Map<String,Object>queryProductSortValue(Map<String, Object> params);
    
    /**
     * 将当前商品排序值增加或减少
     * @param params
     * @return
     */
    int updateRiseOrDownSortValue(Map<String, Object> params);
    
    /**
     * 更新商品排序值 
     * @param params
     * @return
     */
    int updateSortValue(Map<String, Object> params);
    
    /**
     * 查询最大排名
     * @param params
     * @return
     */
    int queryProductMaxNum();
    /**
     * 校验当前商品是否都有视频
     * @param params
     * @return
     */
    String checkedProductHaveVideo(Map<String, Object> params);

}
