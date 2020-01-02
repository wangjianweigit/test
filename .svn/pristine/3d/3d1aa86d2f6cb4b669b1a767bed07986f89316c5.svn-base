package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.BankCardDao;
import com.tk.oms.finance.dao.ServerBusinessWithdrawalDao;
import com.tk.oms.finance.dao.UserChargeRecordDao;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : WithdrawalService
 * 提现业务操作类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/6/13 16:23
 */
@Service("WithdrawalService")
public class ServerBusinessWithdrawalService {

    //支付项目地址
    @Value("${pay_service_url}")
    private String pay_service_url;

    //获取见证宝账户可提现余额
    @Value("${bank_withdraw_balance}")
    private String bank_withdraw_balance;

    //获取见证宝子账户余额
    @Value("${bank_balance}")
    private String bank_balance;

    //服务商提现打款接口
    @Value("${bank_withdraw}")
    private String bank_withdraw;

    @Resource
    private ServerBusinessWithdrawalDao serverBusinessWithdrawalDao;//服务商提现数据访问接口
    @Resource
    private UserChargeRecordDao userChargeRecordDao;


    //  服务公司
    public String SERVER_COMPANY = "3";
    //  仓储公司
    public String STORAGE_COMPANY = "4";
    //  新零售
    public String NEW_RETAIL = "5";
    //  汇总子账户
    public String SUMMARY_ACCOUNT = "6";

    @Resource
    private BankCardDao bankCardDao;//银行卡数据访问接口
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 添加提现申请单
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addWithdrawalApply(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //查询参数
            Map paramMap = new HashMap();
            //判断是否缺少参数
            if (StringUtils.isEmpty(params.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少public_user_id参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("withdrawal_amount"))) {
                pr.setState(false);
                pr.setMessage("请输入提现金额");
                return pr;
            }
            double withdrawal_amount = Double.parseDouble(params.get("withdrawal_amount").toString());  //提现金额
            if(withdrawal_amount == 0){
                pr.setState(false);
                pr.setMessage("提现金额不能为零");
                return pr;
            }
            if(withdrawal_amount > 50000){
                pr.setState(false);
                pr.setMessage("单笔提现最高5万");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少银行卡编号[id]参数");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("user_type"))){
                pr.setState(false);
                pr.setMessage("缺少用户类型[user_type]参数");
                return pr;
            }else{
                if(SERVER_COMPANY.equals(params.get("user_type").toString())){
                    //服务公司
                    params.put("key", "platform_bank_account");
                    paramMap.put("key", "platform_user_id");
                }else if(STORAGE_COMPANY.equals(params.get("user_type").toString())){
                    //仓储公司
                    params.put("key", "storage_bank_account");
                    paramMap.put("key", "storage_user_id");
                }else if(NEW_RETAIL.equals(params.get("user_type").toString())){
                    //新零售
                    params.put("key", "new_retail_bank_account");
                    paramMap.put("key", "new_retail_user_id");
                } else{
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
            }

            // 获取服务商账户数据
            Map<String, Object> facilitator = serverBusinessWithdrawalDao.queryFacilitator(params);
            if(null == facilitator || facilitator.isEmpty()) {
                pr.setState(false);
                pr.setMessage("未获取到服务商账户信息");
                return pr;
            }

            // 获取银行卡信息
            Map<String, Object> bankCard = bankCardDao.queryById(Long.parseLong(params.get("id").toString()));
            if(null == bankCard || bankCard.isEmpty()){
                pr.setState(false);
                pr.setMessage("银行卡不存在");
                return pr;
            }

            // 获取服务商账户余额
            double facilitatorBalance = Double.parseDouble(facilitator.get("account_balance").toString());

            long userId = Long.parseLong(facilitator.get("user_id").toString());
            params.put("user_id", userId);                                              //服务商id
            params.put("buss_bank_card_id", Long.parseLong(params.get("id").toString()));//银行卡编号
            params.put("bank_card", bankCard.get("BANK_CARD"));//银行卡
            params.put("bank_name", bankCard.get("BANK_NAME"));//银行名称
            params.put("owner_name", bankCard.get("OWNER_NAME"));//持卡人

            //存储提交见证宝绑定所需参数
            paramMap.clear();
            paramMap = new HashMap<String, Object>();
            paramMap.put("bank_account", facilitator.get("bank_account").toString());   //银行子账号
            /*******************提现金额校验*************Start**************/
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_withdraw_balance, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (!resPr.getState()) {
                pr.setState(false);
                pr.setMessage(resPr.getMessage());
                return pr;
            }
            double accountCanWithdrawalBalance = Double.parseDouble(resPr.getObj().toString());                 //见证宝可提现余额
            double rigthWithdrawalAmount = this.serverBusinessWithdrawalDao.queryWithdrawalAmount(params);      //已提现申请金额
            double cash_available = DoubleUtils.sub(accountCanWithdrawalBalance , rigthWithdrawalAmount);       //可提现金额

            cash_available = facilitatorBalance > cash_available ? cash_available : facilitatorBalance;

            cash_available = cash_available > 0 ? cash_available : 0D;

            //检查提现金额是否有误
            if (withdrawal_amount > cash_available) {
                pr.setState(false);
                pr.setMessage("提现金额有误");
                return pr;
            }
            /*******************提现金额校验*************End***************/

            /*******************创建提现申请单*************Start************/
            if (this.serverBusinessWithdrawalDao.insert(params) > 0) {
                /*=======创建提现申请单成功,添加提现收支明细===Start===*/
                Map<String, Object> withdrawal = this.serverBusinessWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user_id", userId);
                if(SERVER_COMPANY.equals(params.get("user_type").toString())){
                    map.put("remark", "平台服务公司余额提现");
                }else if(STORAGE_COMPANY.equals(params.get("user_type").toString())){
                    map.put("remark", "仓储服务公司余额提现");
                }else if(NEW_RETAIL.equals(params.get("user_type").toString())){
                    map.put("remark", "新零售服务公司余额提现");
                }
                map.put("money", withdrawal_amount);                             //提现金额
                map.put("surplus_money", DoubleUtils.sub(facilitatorBalance, withdrawal_amount));                        //账户余额
                map.put("turnover_number", withdrawal.get("WITHDRAWAL_NUMBER")); //提现申请单号
                map.put("record_type", "付款"); //收支类型
                map.put("turnover_type", "提现"); //收付类型

                // 提现扣减账户余额
                Map<String, Object> accountParams = new HashMap<String, Object>();
                accountParams.put("user_id",userId);
                accountParams.put("user_type", 3);      //服务商账户类型
                accountParams.put("money", DoubleUtils.mul(-1D, withdrawal_amount));				   //提现金额，金额减少，需要改为负数
                userChargeRecordDao.updateUserAccountBalance(accountParams);
                if("2".equals(accountParams.get("output_status"))){
                    throw new Exception(accountParams.get("output_msg")==null?"修改帐户余额失败":accountParams.get("output_msg").toString());
                }

                //插入提现收支记录
                if(this.serverBusinessWithdrawalDao.insertRevenueRecord(map) > 0){
                    pr.setState(true);
                    pr.setMessage("提现申请成功");
                }else{
                    throw new RuntimeException("创建提现收支记录失败");
                }
                /*=======创建提现申请单成功,添加提现收支明细===Start===*/
            }else{
                pr.setState(false);
                pr.setMessage("提现申请失败");
            }
            /*******************创建提现申请单*************Start************/

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 分页查询提现申请单
     * @param request
     * @return
     */
    public GridResult queryWithdrawalApplyForPage(HttpServletRequest request) {
        GridResult pr = new GridResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)) {
                params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(params);
            }
            if(params.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少user_type参数");
                return pr;
            }
            //状态
            if (params.containsKey("state") && !StringUtils.isEmpty(params.get("state"))) {
                String[] notice_channel = params.get("state").toString().split(",");
                if (notice_channel.length > 1) {
                    params.put("state", params.get("state"));
                } else {
                    params.put("state", params.get("state").toString().split(","));
                }
            }
            //获取分页记录及记录数
            int total = this.serverBusinessWithdrawalDao.queryListForCount(params);                       //获取提现记录条数
            List<Map<String, Object>> list = this.serverBusinessWithdrawalDao.queryListForPage(params);   //获取提现记录列表
            if (list != null && list.size() > 0) {
                pr.setMessage("获取成功");
                pr.setObj(list);
            } else {
                pr.setMessage("没数据");
            }
            pr.setState(true);
            pr.setTotal(total);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }
        return pr;
    }

    /**
     * 分页查询提现审批列表数据
     * @param request
     * @return
     */
    public GridResult queryWithdrawalApprovalList(HttpServletRequest request) {
        GridResult pr = new GridResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)) {
                params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(params);
            }
            if(params.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            //状态
            if (params.containsKey("state") && !StringUtils.isEmpty(params.get("state"))) {
                String[] notice_channel = params.get("state").toString().split(",");
                if (notice_channel.length > 1) {
                    params.put("state", params.get("state"));
                } else {
                    params.put("state", params.get("state").toString().split(","));
                }
            }
            //获取分页记录及记录数
            int total = this.serverBusinessWithdrawalDao.queryWithdrawalApprovalCount(params);                       //获取提现记录条数
            List<Map<String, Object>> list = this.serverBusinessWithdrawalDao.queryWithdrawalApprovalList(params);   //获取提现记录列表
            if (list != null && list.size() > 0) {
                pr.setMessage("获取成功");
                pr.setObj(list);
            } else {
                pr.setMessage("没数据");
            }
            pr.setState(true);
            pr.setTotal(total);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }
        return pr;
    }


    /**
     * 获取提现银行卡信息
     * @param request
     * @return
     */
    public ProcessResult queryBankCardWithdrawalDetail(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少参数
            if (StringUtils.isEmpty(params.get("bank_card_id"))) {
                pr.setState(false);
                pr.setMessage("缺少bank_card_id参数");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("user_type"))){
                pr.setState(false);
                pr.setMessage("缺少user_type参数");
                return pr;
            }
            if(SERVER_COMPANY.equals(params.get("user_type").toString())){
                //服务公司
                params.put("key", "platform_bank_account");
            }else if(STORAGE_COMPANY.equals(params.get("user_type").toString())){
                //仓储公司
                params.put("key", "storage_bank_account");
            }else if(NEW_RETAIL.equals(params.get("user_type").toString())){
                //新零售
                params.put("key", "new_retail_bank_account");
            }else{
                pr.setState(false);
                pr.setMessage("user_type参数错误");
                return pr;
            }

            // 获取服务商账户数据
            Map<String, Object> facilitator = serverBusinessWithdrawalDao.queryFacilitator(params);
            if(null == facilitator || facilitator.isEmpty()) {
                pr.setState(false);
                pr.setMessage("未获取到服务商账户信息");
                return pr;
            }

            // 获取服务商账户余额
            double facilitatorBalance = Double.parseDouble(facilitator.get("account_balance").toString());

            Map paramMap = new HashMap();
            paramMap.put("bank_account", facilitator.get("bank_account").toString());   //银行子账号
            /*****获取提现账户可用余额****Start*****/
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_withdraw_balance, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (!resPr.getState()) {
                pr.setState(false);
                pr.setMessage(resPr.getMessage());
                return pr;
            }
            double accountCanWithdrawalBalance = Double.parseDouble(resPr.getObj().toString());            //见证宝可提现余额
            double withdrawalAmount = this.serverBusinessWithdrawalDao.queryWithdrawalAmount(params);      //已提现申请金额
            double cash_available = DoubleUtils.sub(accountCanWithdrawalBalance,withdrawalAmount);         //可提现金额

            cash_available = facilitatorBalance > cash_available ? cash_available : facilitatorBalance;

            cash_available = cash_available > 0 ? cash_available : 0D;
            /*****获取提现账户可用余额****End*****/

            //获取提现银行卡信息
            Map<String, Object> bankCard = this.bankCardDao.queryById(Long.parseLong(params.get("bank_card_id").toString()));
            bankCard.put("cash_available", cash_available);
            if (bankCard != null) {
                pr.setState(true);
                pr.setMessage("获取提现银行卡信息成功");
                pr.setObj(bankCard);
            }else{
                pr.setState(false);
                pr.setMessage("提现银行卡不存在");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取提现审批明细
     * @param request
     * @return
     */
    public ProcessResult queryWithdrawalApprovalDetail(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少参数
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            //获取提现审批明细
            Map<String, Object> result = this.serverBusinessWithdrawalDao.queryWithdrawalApprovalDetail(Long.parseLong(params.get("id").toString()));
            if (result != null) {
                pr.setState(true);
                pr.setMessage("获取成功");
                pr.setObj(result);
            }else{
                pr.setState(false);
                pr.setMessage("获取成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取服务商账户余额
     * @param request
     * @return
     */
    public ProcessResult queryAccountBalance(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            //获取请求参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("user_type"))){
                pr.setState(false);
                pr.setMessage("缺少user_type参数");
                return pr;
            }else{
                String userType =params.get("user_type").toString();
                if(SERVER_COMPANY.equals(userType)){
                    //服务公司
                    params.put("key", "platform_bank_account");;
                }else if(STORAGE_COMPANY.equals(userType)){
                    //仓储公司
                    params.put("key", "storage_bank_account");
                }else if(NEW_RETAIL.equals(userType)){
                    //新零售
                    params.put("key", "new_retail_bank_account");
                }else if(SUMMARY_ACCOUNT.equals(userType)){
                    //新零售
                    params.put("key", "summary_account_bank_account");
                }else{
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
            }

            // 获取服务商账户数据
            Map<String, Object> facilitator = serverBusinessWithdrawalDao.queryFacilitator(params);
            if(null == facilitator || facilitator.isEmpty()) {
                pr.setState(false);
                pr.setMessage("未获取到服务商账户信息");
                return pr;
            }

            // 获取服务商账户余额
            double facilitatorBalance = Double.parseDouble(facilitator.get("account_balance").toString());

            Map paramMap = new HashMap();
            paramMap.put("bank_account", facilitator.get("bank_account").toString());   //银行子账号
            //获取账户可提现余额
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_withdraw_balance, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (!resPr.getState()) {
                pr.setState(false);
                pr.setMessage(resPr.getMessage());
                return pr;
            }
            double accountCanWithdrawalBalance = Double.parseDouble(resPr.getObj().toString());               //见证宝可提现余额
            double rigthWithdrawalAmount = this.serverBusinessWithdrawalDao.queryWithdrawalAmount(params);    //已提现申请金额
            double cash_available = DoubleUtils.sub(accountCanWithdrawalBalance,rigthWithdrawalAmount);       //可提现金额

            cash_available = facilitatorBalance > cash_available ? cash_available : facilitatorBalance;

            cash_available = cash_available > 0 ? cash_available : 0D;

            dataMap.put("ACCOUNT_BALANCE", facilitatorBalance);             //账户余额
            /**获取见证宝账户余额**/
            dataMap.put("CASH_AVAILABLE", cash_available);                  //可提现余额
            pr.setState(true);
            pr.setMessage("获取账户余额成功");
            pr.setObj(dataMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }


    /**
     * 提现申请审批通过
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult approveWithdrawal(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取请求参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }

            if(!params.containsKey("voucher_img_url") && StringUtils.isEmpty(params.get("voucher_img_url").toString())) {
                pr.setState(false);
                pr.setMessage("请上传打款凭证");
                return pr;
            }

            Map<String, Object> withdrawal = this.serverBusinessWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
            if (withdrawal == null) {
                pr.setState(false);
                pr.setMessage("提现申请单不存在");
                return pr;
            }

            // 交易申请记录当前状态
            if(!"1".equals(String.valueOf(withdrawal.get("STATE")))){
                pr.setState(false);
                pr.setMessage("记录已审批，请勿重复操作");
                return pr;
            }

            params.put("type", "approve");//审批
            if(this.serverBusinessWithdrawalDao.update(params) >0){
                Map<String, Object> bank_param = new HashMap<String, Object>();
                /**
                 * params: {
                     user_id:            用户ID，
                     bank_account：      银行会员子账户，
                     withdrawal_number： 提现申请单号，
                     tran_amount：       金额，
                     id_card：           身份证号，
                     bank_card：         银行卡号，
                     user_name：         用户姓名
                 }
                 */
                String bind_type =  withdrawal.get("BIND_TYPE").toString(); //提现银行卡綁卡类型
                String bank_account = "";                                   //服务商银行会员子账户
                String id_card = "";                                        //服务商身份证号
                String user_name= "";                                       //服务商用户姓名
                String user_id = withdrawal.get("USER_ID").toString();      //服务商用户ID

                /******************************打款用户类型参数区分*******(start)***************/
                /**获取服务公司用户id**/
                Map param = new HashMap();
                param.put("key", "platform_user_id");
                Map<String, Object> result = bankCardDao.queryCompanyParamByKey(param);
                String platform_user_id = result.get("VALUE").toString();

                /**获取仓储公司用户id**/
                param.clear();
                param.put("key", "storage_user_id");
                result = bankCardDao.queryCompanyParamByKey(param);
                String storage_user_id = result.get("VALUE").toString();

                /**获取新零售公司用户id**/
                param.clear();
                param.put("key", "new_retail_user_id");
                result = bankCardDao.queryCompanyParamByKey(param);
                String new_retail_user_id = result.get("VALUE").toString();

                //确定用户类型: 服务公司 or 仓储公司
                if(user_id.equals(platform_user_id)){
                    /**==================获取服务公司参数==========start==**/
                    if(bind_type.equals("1")){          //綁卡类型：短信(个人)
                        //获取子账号
                        param.clear();
                        param.put("key", "platform_bank_account");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        bank_account = result.get("VALUE").toString();

                        //获取身份证号
                        param.clear();
                        param.put("key", "platform_personer_user_id_card");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        id_card = result.get("VALUE").toString();

                        //获取身用户名
                        param.clear();
                        param.put("key", "platform_personer_user_name");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        user_name = result.get("VALUE").toString();
                    }else if(bind_type.equals("2")){    //綁卡类型：小额(公司)
                        //获取子账号
                        param.clear();
                        param.put("key", "platform_bank_account");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        bank_account = result.get("VALUE").toString();

                        //获取营业执照
                        param.clear();
                        param.put("key", "platform_company_business_license");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        id_card = result.get("VALUE").toString();

                        //获取公司名称
                        param.clear();
                        param.put("key", "platform_company_name");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        user_name = result.get("VALUE").toString();
                    }
                    /**==================获取服务公司参数=============end==**/
                }else if(user_id.equals(storage_user_id)){

                    /**==================获取仓储公司参数==========start==**/
                    if(bind_type.equals("1")){          //綁卡类型：短信(个人)
                        //获取子账号
                        param.clear();
                        param.put("key", "storage_bank_account");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        bank_account = result.get("VALUE").toString();

                        //获取身份证号
                        param.clear();
                        param.put("key", "storage_personer_user_id_card");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        id_card = result.get("VALUE").toString();

                        //获取身用户名
                        param.clear();
                        param.put("key", "storage_personer_user_name");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        user_name = result.get("VALUE").toString();
                    }else if(bind_type.equals("2")){    //綁卡类型：小额(公司)
                        //获取子账号
                        param.clear();
                        param.put("key", "storage_bank_account");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        bank_account = result.get("VALUE").toString();

                        //获取营业执照
                        param.clear();
                        param.put("key", "storage_company_business_license");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        id_card = result.get("VALUE").toString();

                        //获取公司名称
                        param.clear();
                        param.put("key", "storage_company_name");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        user_name = result.get("VALUE").toString();

                    }
                    /**==================获取仓储公司参数=============end==**/
                }else if(user_id.equals(new_retail_user_id)){
                    /**==================获取新零售公司参数==========start==**/
                    if(bind_type.equals("1")){          //綁卡类型：短信(个人)
                        //获取子账号
                        param.clear();
                        param.put("key", "new_retail_bank_account");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        bank_account = result.get("VALUE").toString();

                        //获取身份证号
                        param.clear();
                        param.put("key", "new_retail_personer_user_id_card");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        id_card = result.get("VALUE").toString();

                        //获取身用户名
                        param.clear();
                        param.put("key", "new_retail_personer_user_name");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        user_name = result.get("VALUE").toString();
                    }else if(bind_type.equals("2")){    //綁卡类型：小额(公司)
                        //获取子账号
                        param.clear();
                        param.put("key", "new_retail_bank_account");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        bank_account = result.get("VALUE").toString();

                        //获取营业执照
                        param.clear();
                        param.put("key", "new_retail_company_business_license");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        id_card = result.get("VALUE").toString();

                        //获取公司名称
                        param.clear();
                        param.put("key", "new_retail_company_name");
                        result = bankCardDao.queryCompanyParamByKey(param);
                        user_name = result.get("VALUE").toString();
                    }
                    /**==================获取新零售公司参数=============end==**/
                }
                /******************************打款用户类型参数区分*********(end)***************/

                bank_param.put("user_id", user_id);                                     //服务商用户ID
                bank_param.put("bank_account", bank_account);                           //服务商银行会员子账户
                bank_param.put("apply_number",withdrawal.get("WITHDRAWAL_NUMBER"));     //服务商提现申请单号
                bank_param.put("tran_amount",withdrawal.get("WITHDRAWAL_AMOUNT"));      //提现打款金额
                bank_param.put("id_card",  id_card);                                    //服务商身份证号
                bank_param.put("bank_card",withdrawal.get("BANK_CARD"));                //服务商银行卡号
                bank_param.put("user_name",user_name);                                  //服务商用户姓名
                bank_param.put("thirdLogNo",withdrawal.get("TRAN_LOGNO") == null ? "" : withdrawal.get("TRAN_LOGNO"));                //交易流水号
                //远程调用见证宝打款接口
                pr = HttpClientUtil.post(pay_service_url + bank_withdraw, bank_param);
                if(pr.getState()){
                    //获取tran_logno提现交易号（用于查询银行处理状态）
                    Map<String, Object> bakn_result = (Map<String, Object>) pr.getObj();
                    // 原待银行处理(3) 变更为 打款到账(4) by wanghai
                    params.put("state","4");                               //打款成功，待银行处理 --> 打款到账
                    params.put("tran_logno",bakn_result.get("tran_logno"));//打款成功
                    if (this.serverBusinessWithdrawalDao.withdrawPay(params)>0) {
                        pr.setState(true);
                        pr.setMessage("打款成功");
                    } else {
                        throw new RuntimeException("打款失败，"+pr.getMessage());
                    }
                }else{
                    throw new RuntimeException("打款失败，"+pr.getMessage());
                }
                pr.setState(true);
                pr.setMessage("审批成功");
            }else {
                pr.setState(false);
                pr.setMessage("审批失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
           throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 提现申请审批驳回
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult rejectWithdrawal(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取请求参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("reject_reason"))){
                pr.setState(false);
                pr.setMessage("缺少参数[reject_reason]驳回原因");
                return pr;
            }
            Map<String, Object> withdrawal = this.serverBusinessWithdrawalDao.queryById(Long.parseLong(params.get("id").toString()));
            if (withdrawal == null) {
                pr.setState(false);
                pr.setMessage("提现申请单不存在");
                return pr;
            }
            if (withdrawal == null) {
                pr.setState(false);
                pr.setMessage("该提现申请单不存在");
                return pr;
            }
            // 交易申请记录当前状态
            if(!"1".equals(String.valueOf(withdrawal.get("STATE")))){
                pr.setState(false);
                pr.setMessage("记录已审批，请勿重复操作");
                return pr;
            }
            params.put("type", "reject");//驳回
            if(this.serverBusinessWithdrawalDao.update(params) >0){

                // 获取服务商账户数据
                Map<String, Object> facilitator = serverBusinessWithdrawalDao.queryFacilitatorByUserId(Long.parseLong(withdrawal.get("USER_ID").toString()));

                double withdrawalAmount = Double.parseDouble(withdrawal.get("WITHDRAWAL_AMOUNT").toString());
                // 获取服务商账户余额
                double facilitatorBalance = Double.parseDouble(facilitator.get("account_balance").toString());
                long userId = Long.parseLong(facilitator.get("user_id").toString());

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("user_id", userId);
                map.put("remark", "提现驳回，反充余额");
                map.put("money", withdrawalAmount);                             //提现金额
                map.put("surplus_money", DoubleUtils.add(facilitatorBalance, withdrawalAmount));                        //账户余额
                map.put("turnover_number", withdrawal.get("WITHDRAWAL_NUMBER")); //提现申请单号
                map.put("record_type", "收款"); //收支类型
                map.put("turnover_type", "充值"); //收付类型

                // 驳回反冲余额
                Map<String, Object> accountParams = new HashMap<String, Object>();
                accountParams.put("user_id",userId);
                accountParams.put("user_type", 3);
                accountParams.put("money", withdrawalAmount);				   //提现金额，金额减少，需要改为负数
                userChargeRecordDao.updateUserAccountBalance(accountParams);
                if("2".equals(accountParams.get("output_status"))){
                    throw new Exception(accountParams.get("output_msg")==null?"修改帐户余额失败":accountParams.get("output_msg").toString());
                }

                //插入反冲收支记录
                if(this.serverBusinessWithdrawalDao.insertRevenueRecord(map) > 0){
                    pr.setState(true);
                    pr.setMessage("驳回成功");
                }else{
                    throw new RuntimeException("创建提现驳回反充收支记录失败");
                }
            }else {
                throw new RuntimeException("驳回失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("驳回异常");
        }
        return pr;
    }

    /**
     * 服务商收支记录查询
     * @param request
     * @return
     */
    public GridResult queryBalanceOfPaymentsList(HttpServletRequest request) {
        GridResult pr = new GridResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)) {
                params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(params);
            }
            if(params.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            //  收支类型
            if (params.containsKey("record_type") && !StringUtils.isEmpty(params.get("record_type"))) {
                String[] recordType = params.get("record_type").toString().split(",");
                if (recordType.length > 1) {
                    params.put("record_type", params.get("record_type"));
                } else {
                    params.put("record_type", recordType);
                }
            }
            //  收付类型
            if (params.containsKey("turnover_type") && !StringUtils.isEmpty(params.get("turnover_type"))) {
                String[] turnoverType = params.get("turnover_type").toString().split(",");
                if (turnoverType.length > 1) {
                    params.put("turnover_type", params.get("turnover_type"));
                } else {
                    params.put("turnover_type", turnoverType);
                }
            }
            //  获取分页记录及记录数
            int total = this.serverBusinessWithdrawalDao.queryBalanceOfPaymentsForCount(params);
            //  获取提现记录列表
            List<Map<String, Object>> list = this.serverBusinessWithdrawalDao.queryBalanceOfPaymentsForList(params);
            if (list != null && list.size() > 0) {
                pr.setMessage("获取成功");
                pr.setObj(list);
            } else {
                pr.setMessage("没数据");
            }
            pr.setState(true);
            pr.setTotal(total);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }
        return pr;
    }
}
