package com.tk.oms.basicinfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreInfoDao {
	/**
     * 查询门店数量
     *
     * @param storeInfo
     * @return
     */
    public int queryStoreInfoCount(Map<String, Object> map);
    
    /**
     * 查询公司所在地大区ID
     * @param id
     */
    public long  queryBigAreaID(long id);
    /**
	 * 修改门店
	 * @param t
	 * @return
     */
	public int updateStoreInfo(Map<String,Object> params);
	/**
	 * 判断门店是否存在
	 * @param t
	 * @return
     */
	public int queryStoreInfoByName(Map<String,Object> params);

	/**
	 * 删除门店
	 * @param t
	 * @return
     */
	public int deleteStoreInfo(Map<String,Object> params);
	
	/**
	 * 删除门店用户关系表
	 * @param t
	 * @return
     */
	public int deleteStoreUserRel(Map<String,Object> params);
	/**
	 * 新增门店
	 * @param t
	 * @return
	 */
	public int insertStoreInfo(Map<String,Object> params);
	
	/**
	 * 新增门店人员关系表
	 * @param t
	 * @return
	 */
	public int insertStoreUserRel(Map<String,Object> params);
	/**
	 * 分页查询记录列表
	 * @param t
	 * @return
	 */
	public List<Map<String,Object>> queryStoreInfoList(Map<String,Object> params);
	
	  /**
     * 更新门店验证码
     *
     * @param storeInfo
     * @return
     */
    public int updateVerifyCode(Map<String,Object> params);
    /**
     * 更新门店授信额度
     *
     * @param storeInfo
     * @return
     */
    public int updateCredit(Map<String,Object> params);
    
    /**
     * 更新门店状态
     *
     * @param storeInfo
     * @return
     */
    public int updateState(Map<String,Object> params);
    
    
    /**
     * 查询所有门店列表
     * @return
     */
    public List<Map<String,Object>> queryAllStoreInfo(Map<String,Object> params);
    
    
    /**
     * 根据user_id(店长)查询门店信息
     * @return
     */
    public Map<String,Object> queryByUserId(Map<String, Object> paramMap);
    
    
    /**
     * 根据id获取店员名字
     * @return
     */
    public String queryStoreNameById(long id);
    
    /**
     * 查询门店详情
     * @return
     */
    public Map<String,Object> queryStoreInfoDetail(Map<String,Object> params);
    /**
     * 
     * 查询门店营业员或业务员
     */
    List<Map<String,Object>> queryStoreType(Map<String,Object> params);
    /**
     * 
     * 判断当前店长是否被其它门店关联过
     */
    int queryShopkeeperUserIdIsRelated(Map<String,Object> params);
    /**
     * 
     * 判断当前业务员或营业员是否被关联
     */
    String queryYwyOrYyyIsRelated(Map<String,Object> params);
    



}
