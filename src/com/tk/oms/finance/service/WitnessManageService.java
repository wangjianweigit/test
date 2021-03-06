package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.BankCardDao;
import com.tk.oms.finance.dao.WitnessManageDao;
import com.tk.oms.finance.dto.CapitalDistributionDetailDTO;
import com.tk.oms.finance.dto.CapitalDistributionListDTO;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 见证宝管理
 * @zhenghui
 */
@Service("WitnessManageService")
public class WitnessManageService {

    /** 调用见证宝接口 **/
    @Value("${pay_service_url}")
    private String pay_service_url;
    @Value("${bank_balance}")
    private String bank_balance;
    /** 获取会员银行交易明细 **/
    @Value("${bank_transaction_detail}")
    private String bank_transaction_detail;
    /** 获取见证宝充值提现明细 **/
    @Value("${withdraw_deposit_detail}")
    private String withdraw_deposit_detail;
    /** 获取(衫徳)见证宝账户余额 **/
    @Value("${sd_pay_balance}")
    private String sd_pay_balance;
    /** 获取(衫徳)见证宝账户余额提现 **/
    @Value("${sd_pay_balance_withdrawal}")
    private String sd_pay_balance_withdrawal;
    /** 获取(衫徳)见证宝账户余额提现单条记录明细 **/
    @Value("${sd_pay_balance_withdrawal_detail}")
    private String sd_pay_balance_withdrawal_detail;
    @Value("${jdbc_user}")
    private String jdbc_user;

    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private WitnessManageDao witnessManageDao;
    @Resource
    private BankCardDao bankCardDao;

    /**
     * 用户见证宝账户余额查询
     * @param request
     * @return
     */
    public GridResult userAccountBalance(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(params.get("pageIndex"))) {
                gr.setState(false);
                gr.setMessage("缺少pageIndex参数");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("selectFlag"))) {
                gr.setState(false);
                gr.setMessage("缺少selectFlag参数");
                return gr;
            }
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            //当普通会员子账户获取银行会员子账号
            int selectFlag = Integer.parseInt(params.get("selectFlag").toString());
            if (selectFlag == 1 || selectFlag == 2) {
                if (StringUtils.isEmpty(params.get("user_id"))) {
                    gr.setState(false);
                    gr.setMessage("缺少user_id参数");
                    return gr;
                }
                Map<String, Object> bankAccount = this.witnessManageDao.queryUserAccountById(Long.parseLong(params.get("user_id").toString()));
                if (bankAccount == null) {
                    gr.setState(false);
                    gr.setMessage("银行账户不存在");
                    return gr;
                }
                if (StringUtils.isEmpty(bankAccount.get("BANK_ACCOUNT"))) {
                    gr.setState(false);
                    gr.setMessage("银行子账户不存在");
                    return gr;
                }
                paramsMap.put("custAcctId", bankAccount.get("BANK_ACCOUNT"));
                params.put("selectFlag", "2");
            }

            if (selectFlag == 4 || selectFlag == 5 || selectFlag == 6) {
                String bankAccount = witnessManageDao.queryServeAccount(params);
                if (StringUtils.isEmpty(bankAccount)) {
                    gr.setState(false);
                    if(selectFlag == 4) {
                        gr.setMessage("平台服务账户未配置");
                    }else if(selectFlag == 5){
                        gr.setMessage("仓储服务账户未配置");
                    }else{
                        gr.setMessage("新零售服务账户未配置");
                    }
                    return gr;
                }
                paramsMap.put("custAcctId", bankAccount);
                params.put("selectFlag", "2");
            }
            paramsMap.put("pageNum", params.get("pageIndex"));
            paramsMap.put("selectFlag", params.get("selectFlag"));
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_balance, paramsMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (resPr.getState()) {
                Map<String, Object> obj = (Map<String, Object>) resPr.getObj();
                if (!StringUtils.isEmpty(obj.get("array"))) {
                    Map<String,Object> user = new HashMap<String,Object>();
                    //查询会员信息
                    if(selectFlag == 1){
                        user = this.witnessManageDao.queryUserInfoByUserName(Long.parseLong(params.get("user_id").toString()));
                    }
                    //查询入驻商信息
                    if(selectFlag == 2){
                        user = this.witnessManageDao.queryStationedUserById(Long.parseLong(params.get("user_id").toString()));
                    }
                    if (selectFlag == 1 || selectFlag == 2) {
                        List<Map<String, Object>> arrayList = (List<Map<String, Object>>) obj.get("array");
                        for (Map<String, Object> array : arrayList) {
                            array.put("custName", user.get("USER_MANAGE_NAME"));
                            array.put("loginName", user.get("LOGIN_NAME"));
                        }
                        gr.setObj(arrayList);
                    } else {
                        gr.setObj(obj.get("array"));
                    }
                    gr.setMessage("获取数据成功");
                } else {
                    gr.setMessage("无数据");
                }
                gr.setState(true);
                gr.setTotal(Integer.parseInt(obj.get("total").toString()));
            } else {
                gr.setState(false);
                gr.setMessage(resPr.getMessage());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 分页获取入驻商列表
     * @param request
     * @return
     */
    public GridResult queryStationedUserListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            List<Map<String, Object>> list = this.witnessManageDao.queryStationedUserList(params);
            int total = this.witnessManageDao.queryStationedUserCount(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 查询见证宝交易明细
     * @param request
     * @return
     */
    public GridResult queryBankTransactionDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(params.get("pageIndex"))) {
                gr.setState(false);
                gr.setMessage("缺少pageIndex参数");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("user_id"))) {
                gr.setState(false);
                gr.setMessage("缺少user_id参数");
                return gr;
            }else{
                if("platform".equals(params.get("user_id").toString()) || "storage".equals(params.get("user_id").toString()) || "newRetail".equals(params.get("user_id").toString())){
                    Map<String, Object> selectMap = new HashMap<String, Object>();
                    if("platform".equals(params.get("user_id").toString())){
                        selectMap.put("selectFlag", 4);
                    }else if("storage".equals(params.get("user_id").toString())){
                        selectMap.put("selectFlag", 5);
                    }else{
                        selectMap.put("selectFlag", 6);
                    }
                    String bankAccount = witnessManageDao.queryServeAccount(selectMap);
                    if (StringUtils.isEmpty(bankAccount)) {
                        gr.setState(false);
                        gr.setMessage("账户未配置");
                        return gr;
                    }
                    paramsMap.put("custAcctId", bankAccount);
                }else{
                    //根据会员id查询银行账户信息
                    Map<String, Object> bankAccount = this.witnessManageDao.queryUserAccountById(Long.parseLong(params.get("user_id").toString()));
                    if (bankAccount == null) {
                        gr.setState(false);
                        gr.setMessage("该用户不存在银行账户");
                        return gr;
                    }
                    if (StringUtils.isEmpty(bankAccount.get("BANK_ACCOUNT"))) {
                        gr.setState(false);
                        gr.setMessage("该用户没有银行子账户");
                        return gr;
                    }
                    paramsMap.put("custAcctId", bankAccount.get("BANK_ACCOUNT"));//子账户
                }
            }
            if (StringUtils.isEmpty(params.get("selectFlag"))) {
                //交易类型 默认全部
                params.put("selectFlag", "1");
            }else{
                if(params.get("selectFlag").toString().split(",").length > 1){
                    params.put("selectFlag", "1");
                }
            }
            if (StringUtils.isEmpty(params.get("funcFlag"))) {
                //查询类型 默认当日
                params.put("funcFlag", "1");
            }else{
                //查询历史
                if(params.get("funcFlag").equals("2")){
                    if (StringUtils.isEmpty(params.get("beginDate"))) {
                        gr.setState(false);
                        gr.setMessage("缺少beginDate参数");
                        return gr;
                    }else{
                        paramsMap.put("beginDate", DateUtils.format(DateUtils.parse(params.get("beginDate").toString(),"yyyy-mm-dd"),"yyyymmdd"));
                    }
                    if (StringUtils.isEmpty(params.get("endDate"))) {
                        gr.setState(false);
                        gr.setMessage("缺少endDate参数");
                        return gr;
                    }else{
                        paramsMap.put("endDate", DateUtils.format(DateUtils.parse(params.get("endDate").toString(),"yyyy-mm-dd"),"yyyymmdd"));
                    }
                }
            }
            paramsMap.put("pageNum", params.get("pageIndex"));
            paramsMap.put("selectFlag", params.get("selectFlag"));
            paramsMap.put("funcFlag", params.get("funcFlag"));
            //调用见证宝接口查询充值提现记录
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_transaction_detail, paramsMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (resPr.getState()) {
                Map<String, Object> obj = (Map<String, Object>) resPr.getObj();
                if (!StringUtils.isEmpty(obj.get("array"))) {
                    gr.setMessage("获取数据成功");
                    gr.setObj(obj.get("array"));
                    gr.setTotal(Integer.parseInt(obj.get("total").toString()));
                } else {
                    gr.setMessage("无数据");
                }
                gr.setState(true);
            } else {
                gr.setState(false);
                gr.setMessage(resPr.getMessage());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 见证宝充值提现明细列表
     * @param request
     * @return
     */
    public GridResult queryWithdrawDepositDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(params.get("pageIndex"))) {
                gr.setState(false);
                gr.setMessage("缺少pageIndex参数");
                return gr;
            }
            if (StringUtils.isEmpty(params.get("user_id"))) {
                gr.setState(false);
                gr.setMessage("缺少user_id参数");
                return gr;
            }else{
                //根据会员id查询银行账户信息
                Map<String, Object> bankAccount = this.witnessManageDao.queryUserAccountById(Long.parseLong(params.get("user_id").toString()));
                if (bankAccount == null) {
                    gr.setState(false);
                    gr.setMessage("该用户不存在银行账户");
                    return gr;
                }
                if (StringUtils.isEmpty(bankAccount.get("BANK_ACCOUNT"))) {
                    gr.setState(false);
                    gr.setMessage("该用户没有银行子账户");
                    return gr;
                }
                paramsMap.put("custAcctId", bankAccount.get("BANK_ACCOUNT"));//子账户
            }
            if (StringUtils.isEmpty(params.get("selectFlag"))) {
                //交易类型 默认提现
                params.put("selectFlag", "2");
            }else{
                if(params.get("selectFlag").toString().split(",").length > 1){
                    params.put("selectFlag", "1");
                }
            }
            if (StringUtils.isEmpty(params.get("funcFlag"))) {
                //查询类型 默认当日
                params.put("funcFlag", "1");
            }else{
                //查询历史
                if(params.get("funcFlag").equals("2")){
                    if (StringUtils.isEmpty(params.get("beginDate"))) {
                        gr.setState(false);
                        gr.setMessage("缺少beginDate参数");
                        return gr;
                    }else{
                        paramsMap.put("beginDate", DateUtils.format(DateUtils.parse(params.get("beginDate").toString(),"yyyy-mm-dd"),"yyyymmdd"));
                    }
                    if (StringUtils.isEmpty(params.get("endDate"))) {
                        gr.setState(false);
                        gr.setMessage("缺少endDate参数");
                        return gr;
                    }else{
                        paramsMap.put("endDate", DateUtils.format(DateUtils.parse(params.get("endDate").toString(),"yyyy-mm-dd"),"yyyymmdd"));
                    }
                }
            }
            paramsMap.put("pageNum", params.get("pageIndex"));
            paramsMap.put("selectFlag", params.get("selectFlag"));
            paramsMap.put("funcFlag", params.get("funcFlag"));
            //调用见证宝接口查询会员银行交易记录
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + withdraw_deposit_detail, paramsMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (resPr.getState()) {
                Map<String, Object> obj = (Map<String, Object>) resPr.getObj();
                if (!StringUtils.isEmpty(obj.get("array"))) {
                    if (!StringUtils.isEmpty(params.get("user_name"))) {
                        List<Map<String, Object>> arrayList = (List<Map<String, Object>>) obj.get("array");
                        for (Map<String, Object> array : arrayList) {
                            array.put("custName", params.get("user_name"));
                        }
                        gr.setObj(arrayList);
                    } else {
                        gr.setObj(obj.get("array"));
                    }
                    gr.setMessage("获取数据成功");

                } else {
                    gr.setMessage("无数据");
                }
                gr.setState(true);
                gr.setTotal(Integer.parseInt(obj.get("total").toString()));
            } else {
                gr.setState(false);
                gr.setMessage(resPr.getMessage());
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 分页清分明细列表
     * @param request
     * @return
     */
    public GridResult queryAllocatingDetailForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //结算状态
            if (params.containsKey("settlement_state") && !StringUtils.isEmpty(params.get("settlement_state"))) {
                String[] settlement_state = params.get("settlement_state").toString().split(",");
                if (settlement_state.length > 1) {
                    params.put("settlement_state", params.get("settlement_state"));
                } else {
                    params.put("settlement_state", params.get("settlement_state").toString().split(","));
                }
            }
            //结算类型
            if (params.containsKey("settlement_type") && !StringUtils.isEmpty(params.get("settlement_type"))) {
                String[] settlement_type = params.get("settlement_type").toString().split(",");
                if (settlement_type.length > 1) {
                    params.put("settlement_type", params.get("settlement_type"));
                } else {
                    params.put("settlement_type", params.get("settlement_type").toString().split(","));
                }
            }
            //清分分类
            if (params.containsKey("settlement_group") && !StringUtils.isEmpty(params.get("settlement_group"))) {
                String[] settlement_group = params.get("settlement_group").toString().split(",");
                if (settlement_group.length > 1) {
                    params.put("settlement_group", params.get("settlement_group"));
                } else {
                    params.put("settlement_group", params.get("settlement_group").toString().split(","));
                }
            }
            //银行子账户类型
            if (params.containsKey("bank_account_type") && !StringUtils.isEmpty(params.get("bank_account_type"))) {
                String[] bank_account_type = params.get("bank_account_type").toString().split(",");
                if (bank_account_type.length > 1) {
                    params.put("bank_account_type", params.get("bank_account_type"));
                } else {
                    params.put("bank_account_type", params.get("bank_account_type").toString().split(","));
                }
            }
            //用户类型
            if(params.containsKey("user_type") && !StringUtils.isEmpty(params.get("user_type"))){
                String[] user_type = params.get("user_type").toString().split(",");
                if (user_type.length > 1) {
                    params.put("user_type", params.get("user_type"));
                } else {
                    params.put("user_type", params.get("user_type").toString().split(","));
                }
            }
            List<Map<String, Object>> list = this.witnessManageDao.queryAllocatingDetailForPage(params);
            int total = this.witnessManageDao.queryAllocatingDetailCount(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 分页退款明细列表
     * @param request
     * @return
     */
    public GridResult queryRefundDetailForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //退款状态
            if (params.containsKey("return_state") && !StringUtils.isEmpty(params.get("return_state"))) {
                String[] operation_type = params.get("return_state").toString().split(",");
                if (operation_type.length > 1) {
                    params.put("return_state", params.get("return_state"));
                } else {
                    params.put("return_state", params.get("return_state").toString().split(","));
                }
            }
            //用户类型
            if(params.containsKey("user_type") && !StringUtils.isEmpty(params.get("user_type"))){
                String[] user_type = params.get("user_type").toString().split(",");
                if (user_type.length > 1) {
                    params.put("user_type", params.get("user_type"));
                } else {
                    params.put("user_type", params.get("user_type").toString().split(","));
                }
            }
            List<Map<String, Object>> list = this.witnessManageDao.queryRefundDetailForPage(params);
            int total = this.witnessManageDao.queryRefundDetailCount(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 分页收入明细列表
     * @param request
     * @return
     */
    public GridResult queryIncomeDetailForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //用户类型
            if(params.containsKey("user_type") && !StringUtils.isEmpty(params.get("user_type"))){
                String[] user_type = params.get("user_type").toString().split(",");
                if (user_type.length > 1) {
                    params.put("user_type", params.get("user_type"));
                } else {
                    params.put("user_type", params.get("user_type").toString().split(","));
                }
            }
            params.put("search_type", "income");
            List<Map<String, Object>> list = this.witnessManageDao.queryAllocatingDetailForPage(params);
            int total = this.witnessManageDao.queryAllocatingDetailCount(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }
    /**
     * 查询资金清分明细
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public GridResult queryClearingFundsDetailForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }

            if(params.containsKey("order_state") && !StringUtils.isEmpty(params.get("order_state"))) {
                String[] order_state = params.get("order_state").toString().split(",");
                if (order_state.length > 1) {
                	params.put("order_state", params.get("order_state"));
                } else {
                	params.put("order_state", params.get("order_state").toString().split(","));
                }
            }

            List<Map<String, Object>> list = this.witnessManageDao.queryClearingFundsDetailForPage(params);
            int total = this.witnessManageDao.queryClearingFundsDetailCount(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
	}
	/**
	 * 资金清分明细详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryClearFundsDetails(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setState(false);
            	pr.setMessage("缺少order_number");
                return pr;
			}
			//资金概况
			Map<String, Object> retMap = this.witnessManageDao.queryMoneySituation(paramMap);
			//商家列表
			List<Map<String, Object>> merchantList = this.witnessManageDao.queryMerchantList(paramMap);
			//其他费用列表
			List<CapitalDistributionDetailDTO> chargesList= this.witnessManageDao.queryOtherCharges(paramMap);

			retMap.put("merchantList", merchantList);
			retMap.put("chargesList", chargesList);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询结算类型、结算明细列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryClearFundsInfo(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setState(false);
            	pr.setMessage("缺少order_number");
                return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("stationed_user_id"))){
				pr.setState(false);
            	pr.setMessage("缺少stationed_user_id");
                return pr;
			}
			Map<String, Object> retMap = new HashMap<String, Object>();
			//结算类型列表
			List<CapitalDistributionDetailDTO> settlementType = this.witnessManageDao.querySettlementTypeList(paramMap);
			//结算明细列表
			List<Map<String, Object>> billingDetails= this.witnessManageDao.queryBillingDetailsList(paramMap);
			//结算明细汇总
			Map<String, Object> totalBillingDetail= this.witnessManageDao.queryBillingDetailsTotal(paramMap);
			retMap.put("stationed_user_id", paramMap.get("stationed_user_id"));
			retMap.put("settlementType", settlementType);
			retMap.put("billingDetails", billingDetails);
			retMap.put("totalBillingDetail", totalBillingDetail);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 查询代发费、运费详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryOtherChargesDetail(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("order_number"))){
				pr.setState(false);
            	pr.setMessage("缺少order_number");
                return pr;
			}
			Map<String, Object> retMap = new HashMap<String, Object>();
			if("df".equals(paramMap.get("type"))){
				//代发费详情
				retMap = this.witnessManageDao.queryGenerationExpensesDetail(paramMap);
				List<Map<String, Object>> dfList = this.witnessManageDao.queryGenerationExpensesDetailList(paramMap);
				retMap.put("dfList", dfList);
			}else{
				//运费详情
				retMap.putAll(this.witnessManageDao.queryFreightDetail(paramMap));
			}
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

    /**
     * 获取(衫徳)见证宝账户余额
     * @return
     */
    public ProcessResult querySdAccountBalance() {
        ProcessResult pr = new ProcessResult();
        try {
            /**
             * 获取（衫徳）见证宝账户余额提现
             */
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + sd_pay_balance, null, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            double totalBalance = Double.parseDouble(resPr.getObj().toString()) / 100;

            if(!resPr.getState()){
                pr.setMessage(resPr.getMessage());
                pr.setState(false);
                return pr;
            }
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("total_balance", totalBalance);
            resultMap.put("total_tran_out_amount", totalBalance);
            pr.setObj(resultMap);
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * (衫徳)见证宝账户余额提现
     * @return
     */
    public ProcessResult sdAccountWithdrawal(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(!params.containsKey("tran_amount") || StringUtils.isEmpty(params.get("tran_amount"))){
                pr.setState(false);
                pr.setMessage("缺少参数[tran_amount]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少银行卡编号[id]参数");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("vCode"))){
                pr.setState(false);
                pr.setMessage("缺少参数[vCode]");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("oldVCode"))){
                pr.setState(false);
                pr.setMessage("获取提现验证码失败");
                return pr;
            }
            if(!params.get("vCode").toString().equals(params.get("oldVCode").toString())){
                pr.setState(false);
                pr.setMessage("提现验证码填写有误");
                return pr;
            }
            String remark ="";
            if(params.containsKey("remark") || !StringUtils.isEmpty(params.get("remark"))){
                //  提现备注信息
                remark = params.get("remark").toString();
            }

            // 获取银行卡信息
            Map<String, Object> bankCard = bankCardDao.queryById(Long.parseLong(params.get("id").toString()));
            if(null == bankCard || bankCard.isEmpty()){
                pr.setState(false);
                pr.setMessage("银行卡不存在");
                return pr;
            }

            /*******************************************提现金额校验(Start)*********************************************/
            /**
             * 获取（衫徳）见证宝账户余额提现
             */
            ProcessResult resAmountPr = HttpClientUtil.post(pay_service_url + sd_pay_balance, null, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (!resAmountPr.getState()) {
                pr.setState(false);
                pr.setMessage(resAmountPr.getMessage());
                return pr;
            }
            //  提现金额
            float tran_amount = Float.parseFloat(params.get("tran_amount").toString());
            //  可提现金额
            float total_tran_out_amount =  Float.parseFloat(resAmountPr.getObj().toString());
            if (tran_amount > total_tran_out_amount) {
                pr.setState(false);
                pr.setMessage("提现金额有误");
                return pr;
            }
            /****************************************提现金额校验(End)***********************************************/

            /*******************************************提现申请（Start）***********************************************/
            //  获取本地提现申请单号
            String withdrawalNumber = witnessManageDao.getSdAccountWithdrawal();
            if(StringUtils.isEmpty(withdrawalNumber)){
                pr.setState(false);
                pr.setMessage("获取提现申请单号异常");
                return pr;
            }

            String tranDate = DateUtils.getCurrentTime(DateUtils.DATE_FORMAT_TIMESTAMP);

            Map<String, Object> paramMap = new HashMap<String, Object>();
            //  提现单号
            paramMap.put("orderCode", withdrawalNumber);
            // 交易时间
            paramMap.put("tranTime", tranDate);
            //  提现金额
            paramMap.put("tranAmt", tran_amount);
            //  账户类型，3：公司账户（对公）；4：个人账户（银行卡）
            paramMap.put("accType" ,"1" .equals(bankCard.get("BIND_TYPE").toString()) ? 4 : 3);
            //  收款人账户号
            paramMap.put("accNo", bankCard.get("BANK_CARD"));
            //  收款人账户名
            paramMap.put("accName", bankCard.get("OWNER_NAME"));
            //  收款账户开户行名称，账户类型为3时，必填
            paramMap.put("bankName", bankCard.get("BANK_NAME"));
            //  收款账户联行号，账户类型为3时，必填
            paramMap.put("bankType", bankCard.get("BANK_CODE"));
            //  摘要
            paramMap.put("remark", bankCard.get("OWNER_NAME") + "提现申请，申请金额：" + tran_amount);

            /**
             *
             * （衫徳）见证宝账户余额提现
             * @param    orderCode                订单号
             * @param    tranAmt                  金额，单位元
             * @param    accType                  账户类型，3：公司账户（对公）；4：个人账户（银行卡）
             * @param    accNo                    收款人账户号
             * @param    accName                  收款人账户名
             * @param    bankName                 收款账户开户行名称，账户类型为3时，必填
             * @param    bankType                 收款账户联行号，账户类型为3时，必填
             * @param    remark                   摘要
             *
             * @apiSucess    resultFlag               处理状态：0-成功；1-失败；2-处理中（等银行返回明确结果）
             * @apiSucess    sandSerial               杉德系统流水号
             * @apiSucess    tranDate                 交易日期，格式：yyyyMMdd
             * @apiSucess    tranFee                  手续费，处理状态为0时有返回，12位，单位为分
             * @apiSucess    extraFee                 额外手续费，处理状态为0时有返回，12位，单位为分
             * @apiSucess    holidayFee               节假日手续费，处理状态为0时有返回，12位，单位为分
             */
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + sd_pay_balance_withdrawal, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if(!resPr.getState()){
                throw new RuntimeException(resPr.getMessage());
            }
            Map<String, Object> returnMap = (Map<String, Object>)resPr.getObj();

            //  保存参数
            Map<String, Object> saveMap = new HashMap<String, Object>();
            //  提现金额
            saveMap.put("tran_amount", tran_amount);
            //  提现备注信息
            saveMap.put("remark", remark);
            //  创建用户id
            saveMap.put("create_user_id", params.get("public_user_id").toString());
            //  提现处理状态 默认：待处理状态  后续定时处理
            switch (Integer.parseInt(returnMap.get("resultFlag").toString())){
                case 0:
                    saveMap.put("state", '0');
                    saveMap.put("remitDate", returnMap.get("tranDate"));
                    break;
                case 1:
                    saveMap.put("state", '1');
                    break;
                case 2:
                    saveMap.put("state", '6');
                    break;
                default:
                    saveMap.put("state", '5');
            }
            //  交易流水号
            saveMap.put("tran_logno", returnMap.get("sandSerial"));
            //  银行卡id
            saveMap.put("buss_bank_card_id", params.get("id"));
            //  银行卡
            saveMap.put("bank_card", bankCard.get("BANK_CARD"));
            //  银行名称
            saveMap.put("bank_name", bankCard.get("BANK_NAME"));
            //  持卡人
            saveMap.put("owner_name", bankCard.get("OWNER_NAME"));
            //  提现单号
            saveMap.put("withdrawalNumber", withdrawalNumber);
            // 创建时间
            saveMap.put("createDate", tranDate);
            //  新增提现申请信息
            if(this.witnessManageDao.insertSdAccountWithdrawal(saveMap) >0){
                pr.setMessage("待银行打款");
                pr.setState(true);
            }else{
                pr.setMessage("提现异常");
                pr.setState(false);
            }
            /*******************************************提现申请（End）***********************************************/
        } catch (IOException e) {
            pr.setMessage(e.getMessage());
            pr.setState(false);
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取(衫徳)见证宝账户余额提现记录
     * @return
     */
    public GridResult querySdAccountWithdrawalRecord(HttpServletRequest request) {
        GridResult gr = new GridResult();
        //  请求参数
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(params.get("state")))&& params.get("state") instanceof String){
                params.put("state",(params.get("state").toString()).split(","));
            }
            int count = this.witnessManageDao.querySdAcountWithdrawalCount(params);
            List<Map<String, Object>> withdrawalList = this.witnessManageDao.querySdAcountWithdrawalList(params);
            if(withdrawalList.size() >0){
                gr.setObj(withdrawalList);
                gr.setMessage("获取成功");
                gr.setTotal(count);
            }else{
                gr.setMessage("无数据");
            }
            gr.setState(true);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return gr;
    }
    /**
	 * 退款退货明细查询
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRefundReturnDetailForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            
            if(params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))) {
                String[] type = params.get("type").toString().split(",");
                if (type.length > 1) {
                	params.put("type", params.get("type"));
                } else {
                	params.put("type", params.get("type").toString().split(","));
                }
            }
            params.put("jdbc_user",jdbc_user);
            List<Map<String, Object>> list = this.witnessManageDao.queryRefundReturnDetailForPage(params);
            int total = this.witnessManageDao.queryRefundReturnDetailCount(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
	}
	/**
	 * 退款退货详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRefundReturnDetails(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("return_number"))){
				pr.setState(false);
            	pr.setMessage("缺少return_number");
                return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("type"))){
				pr.setState(false);
            	pr.setMessage("缺少type");
                return pr;
			}
			paramMap.put("jdbc_user",jdbc_user);
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			List<Map<String, Object>> chargesList = new ArrayList<Map<String, Object>>();
			if(paramMap.get("type").equals("1")){// 仅退款
				//退款概况
				retMap = this.witnessManageDao.queryRefundSituation(paramMap);
				//其他费用列表
				chargesList = this.witnessManageDao.queryOtherRefundCharges(paramMap);
			}else{//退货退款
				//退款概况
				retMap = this.witnessManageDao.queryReturnRefundSituation(paramMap);
				//其他费用列表
				chargesList = this.witnessManageDao.queryOtherReturnRefundCharges(paramMap);
			}
			//退款 商家列表
			List<Map<String, Object>> merchantList = this.witnessManageDao.queryRefundMerchantList(paramMap);
			
			retMap.put("merchantList", merchantList);
			retMap.put("chargesList", chargesList);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	/**
	 * 查询退款类型、退款明细列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRefundReturnInfo(HttpServletRequest request) {
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
			if(StringUtils.isEmpty(paramMap.get("return_number"))){
				pr.setState(false);
            	pr.setMessage("缺少return_number");
                return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("user_id"))){
				pr.setState(false);
            	pr.setMessage("缺少user_id");
                return pr;
			}
			paramMap.put("jdbc_user",jdbc_user);
			Map<String, Object> retMap = new HashMap<String, Object>();
			List<Map<String, Object>> refundDetails = new ArrayList<Map<String, Object>>();
			Map<String, Object> totalRefundDetail = new HashMap<String, Object>();
			//退款类型列表
			List<Map<String, Object>> refundType = new ArrayList<Map<String, Object>>();
			if(paramMap.get("type").equals("1")){//仅退款
                refundType = this.witnessManageDao.queryRefundTypeList(paramMap);
				//退款明细列表
				refundDetails= this.witnessManageDao.queryRefundDetailsList(paramMap);
				//退款明细汇总
				totalRefundDetail= this.witnessManageDao.queryRefundDetailsTotal(paramMap);
			}else{//退货退款
                refundType = this.witnessManageDao.queryReturnRefundTypeList(paramMap);

                refundDetails= this.witnessManageDao.queryReturnRefundDetailsList(paramMap);
				//退款明细汇总
				totalRefundDetail= this.witnessManageDao.queryReturnRefundDetailsTotal(paramMap);
			}
			retMap.put("user_id", paramMap.get("user_id"));
			retMap.put("refundType", refundType);
			retMap.put("refundDetails", refundDetails);
			retMap.put("totalRefundDetail", totalRefundDetail);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(retMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}


    /**
     * 提现验证
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult withdrawCheck(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[id]银行卡ID");
                return pr;
            }
            //  获取银行卡信息
            Map<String, Object> bankCard = bankCardDao.queryById(Long.parseLong(paramMap.get("id").toString()));
            if(null == bankCard || bankCard.isEmpty()){
                pr.setState(false);
                pr.setMessage("银行卡不存在");
                return pr;
            }
            String mobile = witnessManageDao.getWithdrawCheckDefaultPhone();
            if(StringUtils.isEmpty(mobile) || mobile.length() !=11){
                pr.setState(false);
                pr.setMessage("获取提现验证手机号异常，请联系管理员");
                return pr;
            }

            String verifCode = Utils.getVerifyCode();

            pr.setState(true);
            pr.setMessage("获取短信验证码");
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("mobile", mobile);
            retMap.put("vcode", verifCode);
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    /**
     * 清分明细列表查询
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryClearFundsList(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("order_number"))){
                pr.setState(false);
                pr.setMessage("缺少order_number");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("stationed_user_id"))){
                pr.setState(false);
                pr.setMessage("缺少stationed_user_id");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("bank_account_type"))){
                pr.setState(false);
                pr.setMessage("缺少bank_account_type");
                return pr;
            }
            List<CapitalDistributionListDTO> list = witnessManageDao.querySettlementTypeListByType(paramMap);
            if(list.size() >0){
                pr.setState(true);
                pr.setMessage("查询成功");
                pr.setObj(list);
            }else{
                pr.setState(true);
                pr.setMessage("无数据");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
}
