package com.tk.oms.marketing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.marketing.dao.MemberGradeDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : MemberGradeService
 * 会员等级管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-13
 */
@Service("MemberGradeService")
public class MemberGradeService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private MemberGradeDao memberGradedao;
	/**
	 * 查询会员等级列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberGradeList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//查询会员等级列表
			List<Map<String, Object>> dataList = memberGradedao.queryMemberGradeList(paramMap);
			
			if (dataList != null && dataList.size() > 0) {
				pr.setState(true);
				pr.setMessage("查询成功!");
				pr.setObj(dataList);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 编辑会员等级
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editMemberGrade(HttpServletRequest request) {
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
			//判断等级名称是否重复
			if(memberGradedao.checkmemberGradeName(paramMap)>0){
                pr.setState(false);
                pr.setMessage("该等级名称已存在");
                return pr;
            }
			//判断等级代码是否重复
			if(memberGradedao.checkmemberGradeCode(paramMap)>0){
				pr.setState(false);
                pr.setMessage("该等级代码已存在");
                return pr;
			}
			if(!StringUtils.isEmpty(paramMap.get("discount"))){
				paramMap.put("discount", Double.parseDouble(paramMap.get("discount").toString())/100);
			}
			
			if(StringUtils.isEmpty(paramMap.get("id"))){
				//1.新增
				if(memberGradedao.insert(paramMap)>0){
		            pr.setState(true);
		            pr.setMessage("保存成功");
		        }else {
		            pr.setState(false);
		            pr.setMessage("保存失败");
		        }
			}else{
				//2.更新
				if(memberGradedao.update(paramMap)>0){
					pr.setState(true);
					pr.setMessage("保存成功");
				}else{
					pr.setState(false);
					pr.setMessage("保存失败");
				}
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("保存失败："+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 删除会员等级
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeMemberGrade(HttpServletRequest request) {
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
			//删除会员等级信息
			memberGradedao.delete(paramMap);
			pr.setState(true);
			pr.setMessage("删除成功");
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("删除失败："+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取全部会员等级(下拉框)
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberGradeAll(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			//删除会员等级信息
			List<Map<String, Object>> dataList = memberGradedao.queryMemberGradeAll();
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(dataList);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	
}
