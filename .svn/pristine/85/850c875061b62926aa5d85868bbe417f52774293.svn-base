package com.tk.sys.util;

import java.util.Map;

import org.springframework.util.StringUtils;

public class PageUtil {
	/**
	 * 处理分页参数数据，若不存在起始页，分页大小，则提示错误
	 * 将起始页以及分页大小数据转换为SQL中的起止行
	 * @param params
	 * @return 返回值非空，表示读取分页参数出错
	 */
	public static GridResult handlePageParams(Map<String,Object> params){
		GridResult pr = new GridResult();
		pr.setState(false);
		int pageIndex = 0;//初始页
		int pageSize = 0;//分页大小
		if(params==null){
			pr.setMessage("缺少分页参数");
			return pr;
		}
		if(!StringUtils.isEmpty(params.get("pageIndex"))) {
			try {
				pageIndex = Integer.parseInt(params.get("pageIndex")+"");
			} catch (Exception e) {
				pr.setMessage("起始页参数[pageIndex]必须是数字");
				return pr;
			}
		}else{
			pr.setMessage("缺少起始页参数[pageIndex]");
			return pr;
		}
		if(pageIndex<=0){
			pr.setMessage("起始页参数[pageIndex]必须是大于0的正整数");
			return pr;
		}
		if(!StringUtils.isEmpty(params.get("pageSize"))) {
			try {
				pageSize = Integer.parseInt(params.get("pageSize")+"");
			} catch (Exception e) {
				pr.setMessage("分页大小参数[pageSize]必须是数字");
				return pr;
			}
		}else{
			pr.setMessage("缺少分页大小参数[pageSize]");
			return pr;
		}
		if(pageSize<=0){
			pr.setMessage("分页大小参数[pageSize]必须是大于0的正整数");
			return pr;
		}
		int start_rownum = (pageIndex-1) * pageSize;
		int end_rownum = pageIndex * pageSize;
		params.put("start_rownum", start_rownum);
		params.put("end_rownum", end_rownum);
		return null;
	}
}
