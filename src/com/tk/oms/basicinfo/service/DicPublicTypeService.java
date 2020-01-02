package com.tk.oms.basicinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.basicinfo.dao.DicPublicTypeDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : DicPublicTypeService.java
 * 经营类型service类
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月3日
 */
@Service("DicPublicTypeService")
public class DicPublicTypeService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private DicPublicTypeDao dicPublicTypeDao;
	/**
	 * 经营类型（下拉框）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryDicPublicTypeOption(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
	        	pr.setState(false);
	        	pr.setMessage("参数缺失");
                return pr;
            }
	        
            //获取经营类型
			List<Map<String,Object>> list= dicPublicTypeDao.queryDicPublicTypeOption(paramMap);
			
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(list);
            

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

        return pr;
	}
}
