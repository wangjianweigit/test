package com.tk.store.finance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.store.finance.dao.AccountBalanceDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : AccountBalanceService
 * 账户余额service层
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018-3-9
 */
@Service("AccountBalanceService")
public class AccountBalanceService {
	@Resource
	private AccountBalanceDao accountBalanceDao;
	
	@SuppressWarnings("unchecked")
	public GridResult queryAccountRecordListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			
			if((!StringUtils.isEmpty(paramMap.get("record_type")))&&paramMap.get("record_type") instanceof String){
				paramMap.put("record_type",(paramMap.get("record_type")+"").split(","));
			}
			//数量
			int count = accountBalanceDao.queryAccountRecordCount(paramMap);
			//列表
			List<Map<String, Object>> list = accountBalanceDao.queryAccountRecordListForPage(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	/**
	 * 账户余额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryAccountBalance(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			//查询账户余额
			Map<String, Object> retMap = accountBalanceDao.queryAccountBalance(paramMap);
			pr.setMessage("获取账户余额成功");
			pr.setObj(retMap);
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 获取用户银行卡信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBankInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			//查询银行卡信息
			Map<String, Object> retMap = accountBalanceDao.queryBankInfo(paramMap);
			if(retMap != null){
				String bank_card = (String) retMap.get("BANK_CARD");
				if(bank_card.length()>16){
					bank_card = bank_card.replace(bank_card.substring(4, 16), "************");
					
				}else{
					bank_card = bank_card.replace(bank_card.substring(4, 12), "********");
				}
				retMap.put("star_bank_card", bank_card);
				pr.setMessage("获取用户银行卡信息成功成功");
			}else{
				pr.setMessage("您还未添加银行卡");
			}
			pr.setObj(retMap);
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
}
