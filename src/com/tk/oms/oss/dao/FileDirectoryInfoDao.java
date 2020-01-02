package com.tk.oms.oss.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tk.oms.oss.entity.FileDirectoryInfo;

@Repository
public interface FileDirectoryInfoDao {
	/**
	 * 查询一个父ID下属的所有子目录列表
	 * @return
	 */
	List<FileDirectoryInfo> queryFileDirectoryInfoList(@Param("parent_id")long parent_id);
	/**
	 * 查询商品货号信息
	 * @param search_itemnumber  表示需要过滤的商品货号，支持模糊搜索
	 * @return
	 */
	List<String> queryProductItemNumberList(Map<String,Object> param);
	/**
	 * 查询该货号是否存在或者当前用户是否可以查看该货号
	 * @param param.stationed_user_id			入驻商ID
	 * @param param.product_itemnumber			查询的货号
	 * @return
	 */
	int checkProductItemNumber(Map<String,Object> param);
}
