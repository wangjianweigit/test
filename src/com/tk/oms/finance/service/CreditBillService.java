package com.tk.oms.finance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.CreditBillDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : CreditBillService
 * 资金清算
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-5-16
 */
@Service("CreditBillService")
public class CreditBillService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private CreditBillDao creditBillDao;
	@Resource
	private TransactionService transactionService;

	/**
	 * 资金清算
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryCapitalSettlementList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询资金清算数量
			int total = creditBillDao.queryCapitalSettlementCount(paramMap);
            //查询资金清算列表
			List<Map<String, Object>> dataList = creditBillDao.queryCapitalSettlementList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 账户概况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryViewAccounts(HttpServletRequest request) {
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
			//账户概况
			Map<String, Object> retMap = creditBillDao.queryViewAccounts(paramMap);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 已出账单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryBillQueryOkList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询已出账单数量
			int total = creditBillDao.queryBillQueryOkCount(paramMap);
            //查询已出账单列表
			List<Map<String, Object>> dataList = creditBillDao.queryBillQueryOkList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 未出账单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryBillQueryNoList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询未出账单数量
			int total = creditBillDao.queryBillQueryNoCount(paramMap);
            //查询未出账单列表
			List<Map<String, Object>> dataList = creditBillDao.queryBillQueryNoList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 账单明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryBillDetail(HttpServletRequest request) {
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
			//账单详情
			Map<String, Object> retMap = creditBillDao.queryBillDetail(paramMap);
			//账单交易明细
			List<Map<String, Object>> dataList = creditBillDao.queryBillDetailList(paramMap);
			retMap.put("dataList", dataList);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 结算情况
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult querySettlementSituation(HttpServletRequest request) {
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

			//结算情况
			Map<String, Object> retMap = creditBillDao.querySettlementSituation(paramMap);

			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}

	/**
	 * 结算列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySettlementSituationList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询结算单数量
			int total = creditBillDao.querySettlementSituationCount(paramMap);
            //查询结算单列表
			List<Map<String, Object>> dataList = creditBillDao.querySettlementSituationList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 查询运费、代发费
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOrderShareMoney(HttpServletRequest request) {
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
			//查询运费 代发费
			Map<String, Object> retMap = creditBillDao.queryOrderShareMoney(paramMap);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 结算明细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySettlementDetails(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询结算明细数量
			int total = creditBillDao.querySettlementDetailsCount(paramMap);
            //查询结算明细列表
			List<Map<String, Object>> dataList = creditBillDao.querySettlementDetailsList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 月结还款列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMonthlyRepaymentList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询月结还款数量
			int total = creditBillDao.queryCapitalSettlementCount(paramMap);
            //查询月结还款列表
			List<Map<String, Object>> dataList = creditBillDao.queryCapitalSettlementList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 充值还款
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult addMonthlyRepaymentPrepaid(HttpServletRequest request) {
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
			
			if(StringUtils.isEmpty(paramMap.get("user_id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少用户ID，请联系管理员");
                return pr;
            }
			
			//新增还款申请
			if(creditBillDao.insertCreditRepaymentApply(paramMap)>0){
				pr.setState(true);
				pr.setMessage("充值还款申请成功");
			}else{
				pr.setState(false);
				pr.setMessage("充值还款申请失败");
			}
			
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 还款记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMonthlyRepaymentHistory(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            //查询还款记录数量
			int total = creditBillDao.queryMonthlyRepaymentHistoryCount(paramMap);
            //查询还款记录列表
			List<Map<String, Object>> dataList = creditBillDao.queryMonthlyRepaymentHistoryList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 月结还款审批列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMonthlyRepaymentApprovalList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
	        
            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            if(paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
                String[] state = paramMap.get("state").toString().split(",");
                if (state.length > 1) {
                    paramMap.put("state", paramMap.get("state"));
                } else {
                    paramMap.put("state", paramMap.get("state").toString().split(","));
                }
            }
            //查询月结还款审批数量
			int total = creditBillDao.queryMonthlyRepaymentApprovalCount(paramMap);
            //查询月结还款审批列表
			List<Map<String, Object>> dataList = creditBillDao.queryMonthlyRepaymentApprovalList(paramMap);
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
        } catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        	logger.error("查询失败："+e.getMessage());
        }
		
		return gr;
	}
	/**
	 * 月结还款审批详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMonthlyRepaymentApprovalDetail(HttpServletRequest request) {
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
			
			if(StringUtils.isEmpty(paramMap.get("id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少ID，请联系管理员");
                return pr;
            }
			Map<String, Object> retMap = creditBillDao.queryMonthlyRepaymentApprovalDetail(paramMap);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return pr;
	}
	/**
	 * 月结还款审批审核
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult monthlyRepaymentApprovalCheck(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		ProcessResult rechargePr = null;
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
			if(StringUtils.isEmpty(paramMap.get("id"))) {
            	pr.setState(false);
            	pr.setMessage("缺少ID，请联系管理员");
                return pr;
            }
			//审核数据校验
			if(creditBillDao.queryMonthlyRepaymentById(paramMap)==0){
				pr.setState(false);
				pr.setMessage("还款申请已审核，请检查！");
				return pr;
			}
			if("2".equals(paramMap.get("state"))){//审批通过
				Map<String, Object> param = new HashMap<String, Object>();
				param = paramMap;
				//详情
				Map<String, Object> map = creditBillDao.queryMonthlyRepaymentApprovalDetail(paramMap);
				param.put("tran_amount", map.get("BILL_AMOUNT"));//还款金额
				param.put("user_id", map.get("USER_ID"));//所属用户ID
				param.put("user_name", map.get("USER_MANAGE_NAME"));
				param.put("voucher_img_url", map.get("VOUCHER_IMG_URL"));//支付凭证
				param.put("create_date", map.get("CREATE_DATE"));//还款时间
				param.put("bill_amount", map.get("BILL_AMOUNT"));//还款金额
				param.put("tran_logno", map.get("TRAN_LOGNO"));//交易流水号
				//用户账户信息
				Map<String, Object> baMap = creditBillDao.queryBankAccount(param);
				if(baMap == null){
					pr.setState(false);
					pr.setMessage("用户账户信息不能为空，请检查!");
					return pr;
				}
				//获取用户key
				String user_key = creditBillDao.queryUserKey(param);
				param.put("user_key", user_key);
				param.put("credit_money_balance", baMap.get("CREDIT_MONEY_BALANCE"));
				//获取授信校验码
				String create_code = creditBillDao.queryCreateCode(param);
				if(StringUtils.isEmpty(baMap.get("CREDIT_CHECKCODE"))||!baMap.get("CREDIT_CHECKCODE").equals(create_code)){
					pr.setState(false);
					pr.setMessage("授信余额发生篡改，无法完成当前操作!");
					return pr;
				}
				//生成交易号
				String trade_number = creditBillDao.createTradeNumber();
				param.put("trade_number", trade_number);//交易号
				param.put("account_balance", baMap.get("ACCOUNT_BALANCE"));//账户余额
				param.put("referee_user_realname", baMap.get("REFEREE_USER_REALNAME"));//业务员用户名
				param.put("market_supervision_user_realna", baMap.get("MARKET_SUPERVISION_USER_REALNA"));//业务员用户名
				param.put("store_id", baMap.get("STORE_ID"));//门店ID
		
				//是否已经登记
				if(creditBillDao.queryRegisterState(paramMap)>0){
					//月结还款登记
					rechargePr = transactionService.monthlyRepaymentCharge(param);
				}
				//月结还款
				pr = transactionService.monthlyRepayment(param);
               
			}else if("3".equals(paramMap.get("state"))){//驳回
				if(creditBillDao.updateMonthlyRepaymentApproval(paramMap)>0){
					pr.setState(true);
					pr.setMessage("驳回成功");
				}else{
					pr.setState(false);
					pr.setMessage("驳回失败");
				}
			}
		} catch (Exception e) {
			if(rechargePr != null){
                return rechargePr;
            }
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}
