package com.tk.oms.finance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.pvtp.stationed.dao.PvtpStationedDao;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.StationedWithdrawalDao;
import com.tk.oms.finance.dao.UserAccountRecordDao;
import com.tk.oms.finance.dao.UserChargeRecordDao;
import com.tk.oms.stationed.dao.StationedDao;
import com.tk.sys.config.EsbConfig;

@Service("StationedWithdrawalService")
public class StationedWithdrawalService {
	@Value("${pay_service_url}")
	private String pay_service_url;//见证宝接口地址信息
	@Value("${sd_pay_balance_withdrawal}")
	private String sd_pay_balance_withdrawal;//会员或入驻商提现接口
	@Value("${tran_capital_unfreeze}")//见证宝资金解冻
	private String tran_capital_unfreeze;
	/** 查询杉德账户余额 */
	@Value("${sandpay_balance}")
	private String sandpayBalanceUrl;
	@Resource
	private StationedWithdrawalDao stationedWithdrawalDao;
	@Resource
	private StationedDao stationed;
	@Resource
	private UserChargeRecordDao userChargeRecordDao;
	@Resource
	private UserAccountRecordDao userAccountRecordDao;
	@Value("${sd_pay_withdrawal_detail}")
	private String sd_pay_withdrawal_detail;

	@Resource
	private PvtpStationedDao pvtpStationedDao;

	/**
     * 分页获取待入驻商提现申请记录列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult queryStationedWithdrawalList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            List<String> state_list = null;
			if(params.get("state") instanceof List<?>){
				state_list = (List<String>) params.get("state");
			}else if(params.get("state") instanceof String){
				state_list = new ArrayList<String>();
				state_list.add(params.get("state").toString());
			}
			params.put("state", state_list);
            int total = this.stationedWithdrawalDao.queryListForCount(params);
            List<Map<String, Object>> list = this.stationedWithdrawalDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取入驻商提现申请记录成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception ex) {
            gr.setState(false);
            gr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return gr;
    }
    /**
     * 根据ID获取待入驻商提现申请记录详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryStationedWithdrawalDetail(HttpServletRequest request) {
    	ProcessResult gr = new ProcessResult();
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if (StringUtils.isEmpty(json)) {
    			gr.setState(false);
    			gr.setMessage("缺少参数");
    			return gr;
    		}
    		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
    		if(params == null
    				||params.isEmpty()
    				||StringUtils.isEmpty(params.get("id"))){
    			gr.setState(false);
    			gr.setMessage("缺少参数[ID]");
    			return gr;
    		}
    		Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
    		if(applyObj == null
    				||applyObj.isEmpty()){
    			gr.setState(false);
    			gr.setMessage("当前申请记录不存在或已被删除");
    			return gr;
    		}
    		gr.setState(true);
    		gr.setMessage("获取申请记录成功");
    		gr.setObj(applyObj);
    		return gr;
    	} catch (Exception ex) {
    		gr.setState(false);
    		gr.setMessage(ex.getMessage());
    		ex.printStackTrace();
    	}
    	return gr;
    }
    /**
     *  入驻商提现申请审批
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approvalStationedWithdrawal(HttpServletRequest request) throws Exception{
    	ProcessResult gr = new ProcessResult();

		String json = HttpUtil.getRequestInputStream(request);
		if (StringUtils.isEmpty(json)) {
			gr.setState(false);
			gr.setMessage("缺少参数");
			return gr;
		}
		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		if(params == null
				||params.isEmpty()
				||StringUtils.isEmpty(params.get("id"))
				||StringUtils.isEmpty(params.get("state"))
				){
			gr.setState(false);
			gr.setMessage("缺少参数[ID]");
			return gr;
		}
		Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
		if(applyObj == null||applyObj.isEmpty()){
			gr.setState(false);
			gr.setMessage("当前申请记录不存在或已被删除");
			return gr;
		}

		// 交易申请记录当前状态
		if(!"1".equals(String.valueOf(applyObj.get("STATE")))){
			gr.setState(false);
			gr.setMessage("记录已审批，请勿重复操作");
			return gr;
		}

		if("10".equals(params.get("state").toString())){
			if(StringUtils.isEmpty(params.get("reject_reason"))){
				gr.setState(false);
    			gr.setMessage("审批驳回必须输入驳回原因");
    			return gr;
			}
			if (this.stationedWithdrawalDao.approval(params)>0) {
				//入驻商ID
				long stationed_user_id = Long.parseLong(applyObj.get("STATIONED_USER_ID").toString());
				Map<String, Object> log_map = new HashMap<String, Object>();
                log_map.put("public_user_stationed_user_id", stationed_user_id);//入驻商ID
                //锁定入驻商信息
				stationed.lockTable(log_map);
				gr.setState(true);
				gr.setMessage("审批驳回成功");
				//调用见证宝接口，进行打款操作
				Map<String,Object> userInfo = stationed.queryUserAccountById(stationed_user_id);
				//审批驳回，将用户的提现余额反充
				/******************提现驳回，反充余额*****************************/
				Map<String, Object> rechange_param = new HashMap<String, Object>();
				rechange_param.put("user_id",userInfo.get("USER_ID") );
				rechange_param.put("user_type","2");//默认为入驻商
				float money = Float.parseFloat(applyObj.get("WITHDRAWAL_AMOUNT").toString());
				rechange_param.put("money",money);		//提现反充修改余额

				// 增加兼容私有平台商家提现 -- by wanghai 2019-10-09
				int accountFlag = Integer.parseInt(applyObj.get("ACCOUNT_FLAG").toString());

				Map<String, Object> unliquidated_balance_Map = null;
				//获取用户帐户信息
				Map<String, Object> new_account = null;
				if(accountFlag == 0) {
					userChargeRecordDao.updateUserAccountBalance(rechange_param);

					new_account = this.stationed.queryBankAccountById(stationed_user_id);

					//查询入驻商的待结算金额
					unliquidated_balance_Map = stationed.queryWaitSettlementAmount(stationed_user_id);
				}else{
					pvtpStationedDao.updateStationedAccountBalance(rechange_param);

					new_account = pvtpStationedDao.queryBankAccountById(stationed_user_id);

					unliquidated_balance_Map = pvtpStationedDao.queryWaitSettlementAmount(stationed_user_id);
				}
				if(!"1".equals(rechange_param.get("output_status"))){
					throw new RuntimeException(rechange_param.get("output_msg")==null?"修改帐户余额失败":rechange_param.get("output_msg").toString());
				}

				params.put("account_id", applyObj.get("ACCOUNT_ID").toString());
				params.put("account_flag", accountFlag);

				//驳回时，增加收支记录信息
				if(userAccountRecordDao.insertStationRecordByExtractReject(params)<=0){
					throw new RuntimeException("增加入驻商收支记录失败");
				}
				//驳回时，增加入驻商资金流水 songwangwen  20170720

                log_map.put("turnover_number", applyObj.get("WITHDRAWAL_NUMBER"));//turnover_number，关联单号，即体现申请单号
                log_map.put("log_type","提现退款");//操作类型：订单付款、订单结算、未发退款、退货退款、提现扣款、提现退款
                log_map.put("tran_amount",money);//提现金额
                log_map.put("account_balance",new_account.get("ACCOUNT_BALANCE").toString());//帐户余额
                log_map.put("deposit_balance",new_account.get("DEPOSIT_MONEY_BALANCE").toString());//保证金余额
                log_map.put("unliquidated_balance",unliquidated_balance_Map.get("UNLIQUIDATED_BALANCE").toString());//待结算金额,查询
                log_map.put("record_channel","余额");//收付渠道：余额、保证金、待结算
                log_map.put("account_id", applyObj.get("ACCOUNT_ID").toString());
                log_map.put("account_flag", accountFlag);
				if(userAccountRecordDao.insertStationedCapitalLogs(log_map)<=0){
					throw new RuntimeException("增加资金流水记录失败");
				}
				/***
				 * 审批驳回，解冻见证宝
				 */
				//见证宝冻结解冻
				/*
				Map<String, Object> unfreezeReqMap = new HashMap<String, Object>();
				unfreezeReqMap.put("user_id",userInfo.get("USER_ID"));
				unfreezeReqMap.put("bank_account",userInfo.get("BANK_ACCOUNT"));
				unfreezeReqMap.put("tran_amount", money);
				unfreezeReqMap.put("order_number",applyObj.get("WITHDRAWAL_NUMBER"));
	            ProcessResult unfreezePr = HttpClientUtil.post(pay_service_url + tran_capital_unfreeze, unfreezeReqMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
				if (!unfreezePr.getState()) {
				    //冻结资金失败，抛出异常，数据回滚
					throw new RuntimeException("解冻见证宝资金失败,"+unfreezePr.getMessage());
				} 	*/
			} else {
				gr.setState(false);
				gr.setMessage("审批驳回失败");
			}
		}else if("2".equals(params.get("state").toString())){
			if (this.stationedWithdrawalDao.approval(params)>0) {
				gr.setState(true);
				gr.setMessage("审批通过成功");
			} else {
				gr.setState(false);
				gr.setMessage("审批通过失败");
			}
		}
    	return gr;
    }

    /**
     *  入驻商提现申请打款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult payStationedWithdrawal(HttpServletRequest request) throws Exception{
    	ProcessResult gr = new ProcessResult();

		String json = HttpUtil.getRequestInputStream(request);
		if (StringUtils.isEmpty(json)) {
			gr.setState(false);
			gr.setMessage("缺少参数");
			return gr;
		}
		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		if(params == null
				||params.isEmpty()
				||StringUtils.isEmpty(params.get("id"))
				){
			gr.setState(false);
			gr.setMessage("缺少参数[ID]");
			return gr;
		}

//		if(!params.containsKey("voucher_img_url") && StringUtils.isEmpty(params.get("voucher_img_url").toString())) {
//			gr.setState(false);
//			gr.setMessage("请上传打款凭证");
//			return gr;
//		}

		Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
		if(applyObj == null
				||applyObj.isEmpty()){
			gr.setState(false);
			gr.setMessage("当前申请记录不存在或已被删除");
			return gr;
		}

		// 交易申请记录当前状态
		if(!"2".equals(String.valueOf(applyObj.get("STATE")))){
			gr.setState(false);
			gr.setMessage("记录已打款，请勿重复操作");
			return gr;
		}

		// 打款前先校验一遍账户余额是否充足
		// 请求支付网关账号余额接口
		gr = HttpClientUtil.post(pay_service_url + sandpayBalanceUrl, new HashMap<String, Object>());
		if(!gr.getState()) {
			gr.setState(false);
			gr.setMessage("查询账户余额异常，请稍后再试！！！");
			return gr;
		}

		double balance = Double.parseDouble(gr.getObj().toString());
		double withdrawalAmount = Double.parseDouble(DoubleUtils.mul(applyObj.get("WITHDRAWAL_AMOUNT").toString(), "100"));
		if(balance < withdrawalAmount) {
			gr.setState(false);
			gr.setMessage("当前账户余额不足，请稍后再试！！！");
			return gr;
		}

		//调用见证宝接口，进行打款操作
		Map<String,Object> userInfo = stationed.queryUserAccountById(Long.parseLong(applyObj.get("STATIONED_USER_ID").toString()));
		if(userInfo ==null || StringUtils.isEmpty(userInfo.get("BANK_ACCOUNT"))){
			gr.setState(false);
			gr.setMessage("会员用户帐户信息异常！");
			return gr;
		}
		
		/***
		 * 打款前，解冻见证宝
		 */
		//见证宝冻结解冻
		/*
		Map<String, Object> unfreezeReqMap = new HashMap<String, Object>();
		//见证宝冻结解冻
		unfreezeReqMap.put("user_id",userInfo.get("USER_ID"));
		unfreezeReqMap.put("bank_account",userInfo.get("BANK_ACCOUNT"));
		unfreezeReqMap.put("tran_amount", applyObj.get("WITHDRAWAL_AMOUNT"));
		unfreezeReqMap.put("order_number",applyObj.get("WITHDRAWAL_NUMBER"));
        ProcessResult unfreezePr = HttpClientUtil.post(pay_service_url + tran_capital_unfreeze, unfreezeReqMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
		if (!unfreezePr.getState()) {
		    //冻结资金失败，抛出异常，数据回滚
			throw new RuntimeException("解冻见证宝资金失败,"+unfreezePr.getMessage());
		} 	
		if (!unfreezePr.getState()) {
		    //冻结资金失败，抛出异常，数据回滚
			throw new RuntimeException("解冻见证宝资金失败,"+unfreezePr.getMessage());
		}*/
		Map<String, Object> bank_param = new HashMap<String, Object>();

		String remitDate = DateUtils.getCurrentTime(DateUtils.DATE_FORMAT_TIMESTAMP);

		// 提现申请修改 -- by wanghai 2018-12-20
		bank_param.put("orderCode",applyObj.get("WITHDRAWAL_NUMBER"));
		bank_param.put("tranTime",remitDate);
		bank_param.put("tranAmt",applyObj.get("WITHDRAWAL_AMOUNT"));
		bank_param.put("accType", 4);
		bank_param.put("accNo",applyObj.get("BANK_CARD"));
		bank_param.put("accName",applyObj.get("ACCOUNT_NAME"));
		if("2".equals(applyObj.get("BIND_TYPE"))){
			bank_param.put("accType", 3);
			bank_param.put("accName",userInfo.get("COMPANY_NAME"));
			bank_param.put("bankName",applyObj.get("BANK_NAME"));
			bank_param.put("bankType",applyObj.get("BANK_CODE"));
		}

		//远程调用支付网关接口
		gr = HttpClientUtil.post(pay_service_url + sd_pay_balance_withdrawal, bank_param);
		if(gr.getState()){
			//获取tran_logno提现交易号（用于查询银行处理状态）
			Map<String, Object> bakn_result = (Map<String, Object>) gr.getObj();
			// 原待银行处理(3) 变更为 打款到账(4) by wanghai
			// 重新改为待银行处理(3) by wanghai 20181227
			params.put("state","3");//打款成功，待银行处理
			params.put("tran_logno",bakn_result.get("tran_logno"));//打款成功
			params.put("remit_date", remitDate);//打款成功
			if (this.stationedWithdrawalDao.pay(params)>0) {
				gr.setState(true);
				gr.setMessage("打款成功");
			} else {
				gr.setState(false);
				gr.setMessage("打款失败");
			}
		}else{
			throw new RuntimeException("打款失败，"+gr.getMessage());
		}
    	return gr;
    }

	/**
	 * 标记成功
	 * @param request
	 * @return
	 */
	public ProcessResult markSuccess(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null
					||params.isEmpty()
					||StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数[ID]");
				return gr;
			}
			if(StringUtils.isEmpty(params.get("tranLogno"))){
				gr.setState(false);
				gr.setMessage("缺少参数[tranLogno]");
				return gr;
			}
			Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
			if(applyObj == null
					||applyObj.isEmpty()){
				gr.setState(false);
				gr.setMessage("当前申请记录不存在或已被删除");
				return gr;
			}

			int count = stationedWithdrawalDao.updateWithdrawApplyMarkSuccessState(params);
			if(count == 0){
				gr.setState(false);
				gr.setMessage("操作失败，请稍后再试！");
				return gr;
			}
			gr.setState(true);
			gr.setMessage("操作成功！");
			return gr;
		} catch (Exception ex) {
			gr.setState(false);
			gr.setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return gr;
	}

	/**
	 * 查看打款状态
	 * @param request
	 * @return
	 */
	public ProcessResult lookState(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null
					||params.isEmpty()
					||StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数[ID]");
				return gr;
			}
			Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
			if(applyObj == null
					||applyObj.isEmpty()){
				gr.setState(false);
				gr.setMessage("当前申请记录不存在或已被删除");
				return gr;
			}
			// 发送提现处理结果请求
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderCode", applyObj.get("WITHDRAWAL_NUMBER").toString());
			param.put("accType", applyObj.get("BIND_TYPE"));
			param.put("tranTime", applyObj.get("REMIT_DATE"));
			gr = HttpClientUtil.post(pay_service_url + sd_pay_withdrawal_detail, param);
			if(gr.getState()){
				Map<String, Object> resultMap = (Map<String, Object>) gr.getObj();
				int tran_status = Integer.parseInt(resultMap.get("resultFlag").toString());
				// 银行处理成功
				if (tran_status == 0) {
					gr.setState(true);
					gr.setMessage("OK");
				}else{
					gr.setState(true);
					gr.setObj(null);
					gr.setMessage("银行处理失败");
				}
			}
			return gr;
		} catch (Exception ex) {
			gr.setState(false);
			gr.setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return gr;
	}

	/**
	 * 标记失败并反充
	 * @param request
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ProcessResult markFailCharge(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null
					||params.isEmpty()
					||StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数[ID]");
				return gr;
			}
			Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
			if(applyObj == null
					||applyObj.isEmpty()){
				gr.setState(false);
				gr.setMessage("当前申请记录不存在或已被删除");
				return gr;
			}
			// 标记失败状态
			int count = stationedWithdrawalDao.updateWithdrawApplyMarkFailState(params);
			if(count == 0){
				gr.setState(false);
				gr.setMessage("操作失败，请稍后再试！");
				return gr;
			}

			// 更新提现申请单银行处理状态
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("state", 5);
			updateMap.put("id", params.get("id"));
			stationedWithdrawalDao.updateWithdrawApplyStateToStationed(updateMap);

			updateMap.put("user_type", 2);
			updateMap.put("tran_money", applyObj.get("WITHDRAWAL_AMOUNT").toString());
			updateMap.put("money", applyObj.get("WITHDRAWAL_AMOUNT").toString());
			updateMap.put("user_id", applyObj.get("STATIONED_USER_ID"));

			// 反充余额
			// 锁定入驻商
			stationedWithdrawalDao.lockTable(Long.parseLong(applyObj.get("STATIONED_USER_ID").toString()));

			int accountFlag = Integer.parseInt(applyObj.get("ACCOUNT_FLAG").toString());
			if(accountFlag == 0) {
				stationedWithdrawalDao.updateAccountBalance(updateMap);
			}else{
				pvtpStationedDao.updateStationedAccountBalance(updateMap);
			}
			if(Integer.parseInt(updateMap.get("output_status").toString()) != 1){
				throw new RuntimeException(updateMap.get("output_msg").toString());
			}

			updateMap.put("apply_number", applyObj.get("WITHDRAWAL_NUMBER"));
			updateMap.put("account_id", applyObj.get("ACCOUNT_ID"));
			updateMap.put("account_flag", applyObj.get("ACCOUNT_FLAG"));

			// 增加资金流水
			if(stationedWithdrawalDao.insertStationedCapitalLogs(updateMap) == 0) {
				throw new RuntimeException("提现银行处理失败增加资金流水失败");
			}

			// 增加收支记录
			if(stationedWithdrawalDao.insertUserAccountRecordForStationed(updateMap) == 0) {
				throw new RuntimeException("提现银行处理失败增加收支记录失败");
			}
			gr.setState(true);
			gr.setMessage("操作成功！");
			return gr;
		} catch (Exception ex) {
			gr.setState(false);
			gr.setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return gr;
	}

	/**
	 * 修改打款单为打款成功状态
	 * @param request
	 * @return
	 */
	public ProcessResult updatePaySuccessState(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null
					||params.isEmpty()
					||StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数[ID]");
				return gr;
			}
			Map<String, Object> applyObj = this.stationedWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
			if(applyObj == null
					||applyObj.isEmpty()){
				gr.setState(false);
				gr.setMessage("当前申请记录不存在或已被删除");
				return gr;
			}

			// 更新提现申请单银行处理状态
			params.put("state", 4);
			params.put("tran_remark", params.get("sandSerial").toString());
			params.put("tran_date", params.get("tran_date").toString() + params.get("tran_time").toString());
			stationedWithdrawalDao.updateWithdrawApplyStateToStationed(params);
			gr.setMessage("操作成功！");
			gr.setState(true);
			return gr;
		} catch (Exception ex) {
			gr.setState(false);
			gr.setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return gr;
	}

}
