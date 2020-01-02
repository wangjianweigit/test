package com.tk.oms.decoration.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DecoratePageDetailDao {
	 /**
	  * 查询根据页面ID页面详细信息JSON 
	  * @return
	  */
	 Map<String,Object> queryDetail(@Param("page_id")long page_id);
	 /**
	  * 新增或者更新活动商品页面详情中的参数值 
	  * @return
	  */
	 int insertOrUpdate(Map<String,Object> param);
}
