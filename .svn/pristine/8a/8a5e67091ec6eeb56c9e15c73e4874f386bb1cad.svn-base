package com.tk.oms.sysuser.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tk.oms.sysuser.entity.SysNode;

/**
 * @author wangpeng
 * @data 2016/4/25 001.
 *
 */
@Repository
public interface SysNodeDao {

    /**
     * 新增菜单或节点
     * @param node
     */
    public int insert(SysNode node);
    /**
     * 删除菜单或节点
     * @param node
     */
    public int delete(Map<String, Object> params);

    /**
     * 修改菜单或节点
     * @param node
     */
    public int update(SysNode node);

    /**
     * 查询菜单或节点列表
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryAllList(Map<String, Object> params);
    
    /**
     * 查询菜单或节点列表
     * @param params
     * @return
     */
    public List<SysNode> queryList(Map<String, Object> params);
    
    /**
     * 查询菜单或节点列表总数
     * @param params
     * @return
     */
    public int queryCount(Map<String, Object> params);
    
    /**
     * 查询菜单或节点详情
     * @param node
     * @return
     */
    public SysNode queryById(SysNode node);
}
