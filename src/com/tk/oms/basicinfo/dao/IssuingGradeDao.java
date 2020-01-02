package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface IssuingGradeDao{

    /**
     * 分页查询代发等级列表
     *
     * @param issuingGrade
     * @return
     */
    public List<Map<String,Object>> queryList(Map<String,Object> params);
	
	/**
	 * 修改代发等级
	 * @param t
	 * @return
     */
	public int updateIssuingGrade(Map<String,Object> params);


	/**
	 * 删除代发等级
	 * @param t
	 * @return
     */
	public int deleteIssuingGrade(Map<String,Object> params);
	
	/**
	 * 新增代发等级
	 * @param t
	 * @return
     */
	public int insertIssuingGrade(Map<String,Object> params);

	/**
	 * 新增默认代发等级
	 * @param params
	 * @return
	 */
	public int insertDefaultIssuingGrade(Map<String,Object> params);
    /**
     * 
     * 排序代发等级
     *
     * @param params
     * @return
     */
    public int sortIssuingGrade(Map<String, Object> params);

	/**
	 * 通过等级名称获取代发等级数量
	 * @param params
	 * @return
	 */
    public int queryIssuingGradeCountByName(Map<String, Object> params);

    /**
     * 
     * 获取代发等级数量
     *
     * @param params
     * @return
     */
    public int countIssuingGrade(Map<String, Object> params);

    /**
     * 查询所有代发等级列表
     *
     * @return
     */
    public List<Map<String, Object>> queryIssuingGradeAll();

	/**
	 * 通过代发等级ID获取平台会员的数量
	 * @param params
	 * @return
	 */
	public int queryUserCountByGradeId(Map<String, Object> params);
}
