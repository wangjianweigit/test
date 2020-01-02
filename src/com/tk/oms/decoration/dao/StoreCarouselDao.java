package com.tk.oms.decoration.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface StoreCarouselDao {
	
	/**
     *查询列表信息
     * @param params
     * @return
     */
    public List<Map<String, Object>> querylist(Map<String, Object> params);
    
    /**
     * 查询列表数据数量
     * @param params
     * @return
     */
    public int querycount(Map<String, Object> params);
    
    /**
     * 添加新轮播方案
     * @param params
     * @return
     */
    public int insert(Map<String, Object> params);
    
//    /**
//     * 附件附表插入数据
//     * @param params
//     * @return
//     */
//    public int insert_filetable(Map<String, Object> params);
    /**
     * 附件附表插入数据
     * @param params
     * @return
     */
    public int insert_filetable(List<Map<String, Object>> params);
    /**
     * 删除轮播方案
     * @param params
     * @return
     */
    public int remove(Map<String, Object> params);
    
    /**
     * 删除轮播方案图片视频附件
     * @param params
     * @return
     */
    public int delete_carousel_files(Map<String, Object> params);
    
    /**
     *停用全部
     * @return
     */
    public int changestateAll();
    /**
     * 轮播方案状态编辑
     * @param params
     * @return
     */
    public int changestate(Map<String, Object> params);
    
    /**
     * 详情查询
     * @param params
     * @return
     */
    public Map<String, Object>  query_detail(Map<String, Object> params);
    /**
     * 查询子表(文件关联表)
     * @param params
     * @return
     */
    public List<Map<String,Object>> query_files(Map<String, Object> params);
    
    /**
     * 更新
     * @param params
     * @return
     */
    public int update_carousel(Map<String, Object> params);

}
