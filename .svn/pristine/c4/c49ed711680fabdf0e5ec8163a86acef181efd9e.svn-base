package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : MerchantPosDao
 * 商业Pos设备管理数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/5 11:24
 */
@Repository
public interface MerchantPosDao {

    /**
     * 查询商业Pos设备列表
     *
     * @param params 查询参数
     * @return
     */
    public List<Map<String, Object>> queryMerchantPosList(Map params);

    /**
     * 查询商业Pos设备记录数
     *
     * @param param 查询条件
     * @return
     */
    public int queryMerchantPosCount(Map<String, Object> param);

    /**
     * 新增商业Pos设备
     *
     * @param params 添加内容
     * @return
     */
    public int insertMerchantPos(Map params);

    /**
     * 根据id查询商业Pos设备
     *
     * @param id 通知内容id
     * @return
     */
    public Map<String, Object> queryMerchantPosById(long id);

    /**
     * 根据终端号查询Pos设备数量
     *
     * @param merchant_name
     * @return
     */
    public int queryMerchantPosCountByTerminalNo(String terminal_no);


    /**
     * 修改商业Pos设备、启停用状态
     *
     * @param params 修改内容
     * @return
     */
    public int updateMerchantPos(Map params);

    /**
     * 删除商业Pos设备
     *
     * @param id 商业Pos设备id
     * @return
     */
    public int deleteMerchantPos(long id);

}
