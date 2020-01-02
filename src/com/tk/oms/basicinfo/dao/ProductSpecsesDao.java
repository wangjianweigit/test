package com.tk.oms.basicinfo.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : ProductSpecsesDao
 * 商品规格Dao接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/18 10:13
 */
@Repository
public interface ProductSpecsesDao {

    /**
     * 查询商品规格分组列表信息
     *
     * @param params 查询条件
     * @return
     */
    public List<Map<String, Object>> queryProductSpecGroupList(Map params);


    /**
     * 根据父级id查询商品规格列表信息
     *
     * @param parent_id 父级id
     * @return
     */
    public List<Map<String, Object>> queryProductSpecListByParentId(long parent_id);

    /**
     * 根据id查询商品规格信息
     *
     * @param id 规格id
     * @return
     */
    public Map<String, Object> queryProductSpecById(Long id);

    /**
     * 根据规格名称查询规格数量
     *
     * @param product_specs 规格名称
     * @return
     */
    public int queryProductSpecCountBySpec(String product_specs);

    /**
     * 通过规格分组名称查询规格信息
     *
     * @param product_specs
     * @return
     */
    int queryProductSpecByGroup(Map<String,Object> params);

    /**
     * 通过规格父级id查询规格列表
     *
     * @param parent_id
     * @return
     */
    public List<Map<String,Object>> queryProductSpecsesByGroupParentId(long parent_id);


    /**
     * 新增商品规格信息
     *
     * @param params 添加数据
     * @return
     */
    public int insertProductSpecses(Map params);


    /**
     * 修改商品规格信息
     *
     * @param params 修改参数
     * @return
     */
    public int updateProductSpecses(Map params);

    /**
     * 根据商品规格id删除商品规格信息
     *
     * @param id 商品规格
     * @return
     */
    public int deleteProductSpecsesById(Long id);

    /**
     * 根据商品规格分组id删除商品规格信息
     *
     * @param parent_id 商品规格
     * @return
     */
    public int deleteProductSpecsesByParentId(Long parent_id);

    /**
     * 根据规格id删除规格分组所有信息
     * @param id
     * @return
     */
    public int deleteProductSpecGroupById(Long id);

    /**
     * 查询商品规格是否为可更改状态
     * @param param
     * @return
     */
    public int queryProductSpecsesByEditable(Map<String,Object> param);
    /**
     * 批量插入规格数据
     * @param param
     * @return
     */
    public int insertProductSpecsesByBatch(Map<String,Object> param);
    /**
     * 新增配码管理
     * @param request
     * @return
     */
	public int insertWithCodeInfo(Map<String, Object> paramMap);
	/**
     * 修改配码管理
     * @param request
     * @return
     */
	public int editWithCodeInfo(Map<String, Object> paramMap);
	/**
     * 删除配码管理
     * @param request
     * @return
     */
	public int removeWithCodeInfo(Map<String, Object> paramMap);
	/**
     * 删除配码管理详情
     * @param request
     * @return
     */
	public int removeWithCodeDetail(Map<String, Object> paramMap);
	/**
     * 查询配码管理数量
     * @param request
     * @return
     */
	public int queryWithCodeCount(Map<String, Object> paramMap);
	/**
     * 查询配码管理列表
     * @param request
     * @return
     */
	public List<Map<String,Object>> queryWithCodeList(Map<String, Object> paramMap);
	/**
     * 查询配码管理详情
     * @param request
     * @return
     */
	public Map<String,Object> queryWithCodeDetail(Map<String, Object> paramMap);
	/**
     * 查询配码管理详情
     * @param request
     * @return
     */
	public List<Map<String,Object>> queryWithCodeSpecsDetail(Map<String, Object> paramMap);
	/**
     * 查询配码可使用规格
     * @param request
     * @return
     */
	public List<String> queryWithCodeSpecs(Map<String, Object> paramMap);
	/**
     * 查询指定配码规格
     * @param request
     * @return
     */
	public List<String> queryWithCodeSpecsByCodeId(Map<String, Object> paramMap);
	/**
     * 查询规格下拉数据
     * @param request
     * @return
     */
	public List<Map<String,Object>> querySpecsOption(Map<String, Object> paramMap);
	/**
     * 查询配码下拉数据
     * @param request
     * @return
     */
	public List<Map<String,Object>> queryWithCodeOption(Map<String, Object> paramMap);
	/**
	 * 生成配码详情数据
	 */
	public int insertWithCodeDetail(List<Map<String, Object>> dataList);
	/**
	 * 查询当前配码设置规格数量
	 */
	public int queryWithCodeSetSpecsCount(Map<String, Object> paramMap);
	/**
	 * 查询当前配码实际需要配置规格数量
	 */
	public int queryWithCodeAcutalSetSpecsCount(Map<String, Object> paramMap);

	/**
	 * 查询商品规格列表
	 * @return
	 */
	public List<Map<String,Object>> listProductSpecs();
	/**
	 * 查询所有的鞋类商品规格信息，用于为商家进行可用的商品规格进行授权
	 * 同时根据商家ID返回该规格是否已经勾选的标志
	 * @return
	 */
	public List<Map<String,Object>> getAllShoesProductSpecs(Map<String, Object> paramMap);
	/**
	 * (根据商家ID)清除已经配置的商家可用规格数据
	 * @return
	 */
	public int deleteSpecsByStaId(Map<String, Object> paramMap);
	/**
	 * 批量插入商家可用规格数据
	 * @return
	 */
	public int batchInsertStaProductSpecs(Map<String, Object> paramMap);
}
