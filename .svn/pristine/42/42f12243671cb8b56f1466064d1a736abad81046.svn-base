package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.BankCardDao;
import com.tk.oms.finance.dao.ServerBusinessWithdrawalDao;
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
 * FileName : BankCardService
 * 银行卡管理业务操作类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/6/13 15:58
 */
@Service("BankCardService")
public class BankCardService {

    @Resource
    private BankCardDao bankCardDao;

    @Resource
    private ServerBusinessWithdrawalDao serverBusinessWithdrawalDao;//服务商提现数据访问接口

    //支付项目地址
    @Value("${pay_service_url}")
    private String pay_service_url;

    //见证宝绑定银行卡
    @Value("${bankAccount_bind}")
    private String bankAccount_bind;

    //验证绑定银行卡 -- 短信
    @Value("${bankAccount_check_note}")
    private String bankAccount_check_note;

    //验证绑定银行卡 -- 小额鉴权
    @Value("${bankAccount_check_driblet}")
    private String bankAccount_check_driblet;

    //见证宝解绑银行卡
    @Value("${bank_unbind_card}")
    private String bank_unbind_card;

    //  服务公司
    public String SERVER_COMPANY = "3";
    //  仓储公司
    public String STORAGE_COMPANY = "4";
    //  新零售
    public String NEW_RETAIL = "5";
    //  汇总子账户
    public String SUMMARY_ACCOUNT = "6";


    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 获取已绑定的银行卡列表
     *
     * @param request
     * @return
     */
    public GridResult queryBankCardList(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少user_type参数");
                return pr;
            }
            //查询银行卡记录数及分页列表
            int count = this.bankCardDao.queryBankCardCount(params);
            List<Map<String, Object>> list = this.bankCardDao.queryBankCardList(params);
            if (list != null && list.size() > 0) {
                pr.setMessage("获取银行卡列表成功");
                pr.setObj(list);
                pr.setTotal(count);
            } else {
                pr.setMessage("没有数据");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 个人银行卡绑定申请
     *
     * @param request
     * @return
     */
    public ProcessResult bindBankCardApplyForPersonal(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("bank_card"))) {
                pr.setState(false);
                pr.setMessage("缺少参数bank_card");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("bank_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数bank_code");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("mobile_phone"))) {
                pr.setState(false);
                pr.setMessage("缺少参数mobile_phone");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_type");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("bank_clscode"))) {
                pr.setState(false);
                pr.setMessage("缺少参数bank_clscode");
                return pr;
            }
            //  检验用户綁卡数量 最多只可绑定一张银行卡
            if(bankCardDao.queryBankCardCount(params) >0){
                pr.setState(false);
                pr.setMessage("已有银行卡綁定，请解绑后再进行操作！");
                return pr;
            }

            //获取见证宝银行子账户
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getAccount");
            if (!pr.getState()) {
                return pr;
            }
            //银行账号
            String bank_account = pr.getObj().toString();
            //获取用户Id
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getId");
            if (!pr.getState()) {
                return pr;
            }
            //用户id
            long user_id = Long.parseLong(pr.getObj().toString());
            //获取身份证号
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getIdCard");
            if (!pr.getState()) {
                return pr;
            }
            //身份证号
            String id_card = pr.getObj().toString();
            //获取用户名
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getUserName");
            if (!pr.getState()) {
                return pr;
            }
            //用户名
            String userName = pr.getObj().toString();
            Map<String, Object> sbank = this.bankCardDao.queryBankByClscode(params.get("bank_clscode").toString());
            if (sbank == null || StringUtils.isEmpty(sbank.get("BANK_NAME"))) {
                pr.setState(false);
                pr.setMessage("缺少开户银行");
                return pr;
            }
            Map<String, Object> bank = this.bankCardDao.queryBankByCode(params.get("bank_code").toString());
            if (StringUtils.isEmpty(bank.get("BANK_NAME"))) {
                pr.setState(false);
                pr.setMessage("缺少银行名称");
                return pr;
            }
            //存储提交见证绑定所需参数
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("id_card", id_card);                                       //身份证
            paramMap.put("user_name", userName);                                    //用户姓名
            paramMap.put("bind_type", "6066");
            paramMap.put("user_id", user_id);                                       //用户ID
            paramMap.put("bank_account", bank_account);                             //银行账号
            paramMap.put("bank_card", params.get("bank_card").toString());          //银行卡号
            paramMap.put("bank_name", bank.get("BANK_NAME").toString());            //银行名称
            paramMap.put("bank_code", params.get("bank_code"));                     //银行行号
            paramMap.put("super_bank_code", sbank.get("BANK_CODE").toString());     //银行行号
            paramMap.put("mobile_phone", params.get("mobile_phone"));               //手机号码
            //  获取用户账户信息
            Map<String, Object> userAccount = serverBusinessWithdrawalDao.queryFacilitatorByUserId(user_id);
            //  清算平台商户ID
            paramMap.put("sub_merchant_id", userAccount.get("SUB_MERCHANT_ID").toString());
            // 提交见证宝绑定银行卡
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bankAccount_bind, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (resPr.getState()) {
                pr.setState(true);
                pr.setMessage("绑定个人银行卡申请提交成功");
            } else {
                pr.setState(false);
                pr.setMessage(resPr.getMessage());
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 公司银行卡绑定提交操作
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult bindBankCardForCompany(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少user_type参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("bank_card"))) {
                pr.setState(false);
                pr.setMessage("缺少bank_card参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("bank_code"))) {
                pr.setState(false);
                pr.setMessage("缺少bank_code参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("bind_type"))) {
                pr.setState(false);
                pr.setMessage("缺少bind_type参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少public_user_id参数");
                return pr;
            }

            //  获取用户id
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getId");
            if (!pr.getState()) {
                return pr;
            }
            params.put("user_id", pr.getObj());

            //  检验用户綁卡数量 最多只可绑定一张银行卡
            if(bankCardDao.queryBankCardCount(params) >0){
                pr.setState(false);
                pr.setMessage("已有银行卡綁定，请解绑后再进行操作！");
                return pr;
            }
            //  查询银行卡是否已存在
            Map<String, Object> bank_card = this.bankCardDao.queryBankCardByName(params);
            if (bank_card != null) {
                pr.setState(false);
                pr.setMessage("银行卡已经存在");
                return pr;
            } else {
            	Map<String, Object> sbank = this.bankCardDao.queryBankByClscode(params.get("bank_clscode").toString());
                if (sbank == null || StringUtils.isEmpty(sbank.get("BANK_NAME"))) {
                    pr.setState(false);
                    pr.setMessage("缺少开户银行");
                    return pr;
                }
                //  获取银行信息
                Map<String, Object> bank = this.bankCardDao.queryBankByCode(params.get("bank_code").toString());
                params.put("bind_state", '0');
                params.put("bank_clscode", bank.get("BANK_CLSCODE"));
                //设置默认状态 查询是否有多张银行卡
                if (this.bankCardDao.queryBankCardCount(params) > 0) {
                    //  有 设为非默认
                    params.put("default_flag", '1');
                } else {
                    //  无 设为默认
                    params.put("default_flag", '2');
                }
                //  新增
                if (this.bankCardDao.insert(params) <= 0) {
                    throw new RuntimeException("公司银行卡綁卡失败");
                }
                pr.setState(true);
                pr.setMessage("新增成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 银行卡绑定验证（小额验证）
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult checkBankCardForAmount(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        //获取参数
        String json = HttpUtil.getRequestInputStream(request);
        if (StringUtils.isEmpty(json)) {
            pr.setState(false);
            pr.setMessage("缺少请求参数");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
        if (StringUtils.isEmpty(params.get("id"))) {
            pr.setState(false);
            pr.setMessage("缺少id参数");
            return pr;
        }
        if (StringUtils.isEmpty(params.get("tran_amount"))) {
            pr.setState(false);
            pr.setMessage("请输入验证金额");
            return pr;
        }
        if (StringUtils.isEmpty(params.get("user_type"))) {
            pr.setState(false);
            pr.setMessage("缺少user_type参数");
            return pr;
        }
        Map<String, Object> bankCard = this.bankCardDao.queryById(Long.parseLong(params.get("id").toString()));
        if (bankCard == null) {
            pr.setState(false);
            pr.setMessage("不存在该条记录");
            return pr;
        }
        //判断该银行卡对否已被删除
        if ("2".equals(bankCard.get("IS_DELETE"))) {
            pr.setState(false);
            pr.setMessage("银行卡已被删除");
            return pr;
        }
        //判断银行卡是否已绑定
        if ("1".equals(bankCard.get("BIND_STATE"))) {
            pr.setState(false);
            pr.setMessage("银行卡已绑定");
            return pr;
        }
        //获取见证宝银行子账户
        pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getAccount");
        if (!pr.getState()) {
            return pr;
        }
        // 封装见证宝綁卡所需的参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id", bankCard.get("USER_ID"));                   //用户id
        paramMap.put("bank_account", pr.getObj().toString());               //银行子账户
        paramMap.put("bank_card", bankCard.get("BANK_CARD").toString());    //银行卡号
        paramMap.put("tran_amount", params.get("tran_amount").toString());  //验证金额
        //  清算平台商户ID
        paramMap.put("sub_merchant_id", bankCard.get("SUB_MERCHANT_ID").toString());
        // 提交见证宝绑定银行卡 -- 小额鉴权
        params.put("bind_state", '1');
        if (this.bankCardDao.updateBindState(params) > 0) {
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bankAccount_check_driblet, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if (resPr.getState()) {
                pr.setState(true);
                pr.setMessage("小额验证成功");
            } else {
                //见证宝错误代码
                if ("ERR147".equals(resPr.getObj())) {
                    logger.error(resPr.getMessage() + resPr.getObj());
                    throw new RuntimeException(resPr.getMessage() + resPr.getObj());
                } else {
                    logger.error(resPr.getMessage());
                    throw new RuntimeException(resPr.getMessage());
                }
            }
        }
        return pr;
    }

    /**
     * 银行卡绑定验证（短信验证）
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult checkBankCardForNote(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("bank_card"))) {
                pr.setState(false);
                pr.setMessage("缺少参数bank_card");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("bank_clscode"))) {
                pr.setState(false);
                pr.setMessage("缺少参数bank_clscode");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_type");
                return pr;
            }
            //  检验用户綁卡数量 最多只可绑定一张银行卡
            if(bankCardDao.queryBankCardCount(params) >0){
                pr.setState(false);
                pr.setMessage("已有银行卡綁定，请解绑后再进行操作！");
                return pr;
            }
            //  获取用户id
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getId");
            if (!pr.getState()) {
                return pr;
            }
            //  用户Id
            String user_id = pr.getObj().toString();

            params.put("user_id", user_id);
            params.put("bind_state", "1");
            if (this.bankCardDao.queryBankCardCount(params) > 0) {
                params.put("default_flag", '1');   //设为非默认
            } else {
                params.put("default_flag", '2');   //设为默认
            }
            //  提交见证宝绑定银行卡
            this.bankCardDao.insert(params);
            pr.setState(true);
            pr.setMessage("新增成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 解绑提现银行卡
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult unBindForBankCard(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_type");
                return pr;
            }
            long bank_card_id = Long.parseLong(params.get("id").toString());
            //判断银行卡是否存在
            Map<String, Object> bankCard = this.bankCardDao.queryById(bank_card_id);
            if (bankCard == null) {
                pr.setState(false);
                pr.setMessage("不存在该条记录");
                return pr;
            }
            if (this.bankCardDao.deleted(params) > 0) {
                pr.setState(true);
                pr.setMessage("银行卡解绑成功");
            } else {
                pr.setState(false);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除银行卡
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeBankCard(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            //检查银行卡是否申请提现
            long bank_card_id = Long.parseLong(params.get("id").toString());
            int count = serverBusinessWithdrawalDao.queryWithdrawalApprovalCountByBankCardId(bank_card_id);
            if (count > 0) {
                pr.setState(false);
                pr.setMessage("该银行卡已申请过提现，不可删除");
                return pr;
            }
            //判断银行卡是否存在
            Map<String, Object> bankCard = this.bankCardDao.queryById(bank_card_id);
            if (bankCard == null) {
                pr.setState(false);
                pr.setMessage("不存在该条记录");
                return pr;
            }
            if ("2".equals(bankCard.get("IS_DELETE"))) {
                pr.setState(false);
                pr.setMessage("银行卡已被删除");
                return pr;
            }
            //判断银行卡是否默认提现银行卡
            if ("2".equals(bankCard.get("DEFAULT_FLAG"))) {
                pr.setState(false);
                pr.setMessage("默认提现银行卡，不能删除");
                return pr;
            }
            if (this.bankCardDao.deleted(params) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 设置默认的银行卡
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editDefaultBankCard(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();

        //获取参数
        String json = HttpUtil.getRequestInputStream(request);
        if (StringUtils.isEmpty(json)) {
            pr.setState(false);
            pr.setMessage("缺少请求参数");
            return pr;
        }
        Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
        if (StringUtils.isEmpty(params.get("id"))) {
            pr.setState(false);
            pr.setMessage("缺少id参数");
            return pr;
        }
        if (StringUtils.isEmpty(params.get("user_type"))) {
            pr.setState(false);
            pr.setMessage("缺少user_type参数");
            return pr;
        }

        long bank_card_id = Long.parseLong(params.get("id").toString());
        //获取当前设置默认账户
        Map<String, Object> bankCard = this.bankCardDao.queryById(bank_card_id);
        if (bankCard == null) {
            pr.setState(false);
            pr.setMessage("记录不存在");
            return pr;
        }
        if ("2".equals(bankCard.get("IS_DELETE"))) {
            pr.setState(false);
            pr.setMessage("银行卡已被删除");
            return pr;
        }
        //判断银行卡是否未绑定或已解绑
        if ("0".equals(bankCard.get("BIND_STATE"))) {
            pr.setState(false);
            pr.setMessage("银行卡未绑定或已解绑");
            return pr;
        }

        //获取该用户默认的账户并设置为非默认
        Map<String, Object> bankCardDefault = this.bankCardDao.queryDefaultForBankCard(params);
        bankCardDefault.put("DEFAULT_FLAG", "1");
        if (this.bankCardDao.updateDefaultFlag(bankCardDefault) > 0) {
            bankCard.put("DEFAULT_FLAG", "2");
            if (this.bankCardDao.updateDefaultFlag(bankCard) > 0) {
                pr.setState(true);
                pr.setMessage("设置默认提现银行卡成功");
                pr.setObj(params);
            }

        }
        return pr;
    }

    /**
     * 获取银行列表信息
     *
     * @param request
     * @return
     */
    public ProcessResult queryBankList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            List<Map<String, Object>> list = this.bankCardDao.queryBankList();
            if (list != null && list.size() > 0) {
                pr.setMessage("获取银行列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询所有的省(第一级)
     *
     * @param request
     * @return
     */
    public ProcessResult queryPayProvince(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            List<Map<String, Object>> list = this.bankCardDao.queryPayProvince();
            if (list != null && list.size() > 0) {
                pr.setMessage("获取省份列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 通过城市代码查询相对应的市(第二级)
     *
     * @param request
     * @return
     */
    public ProcessResult queryPayCity(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("node_code"))) {
                pr.setState(false);
                pr.setMessage("缺少node_code参数");
                return pr;
            }
            List<Map<String, Object>> list = this.bankCardDao.queryPayCity(params.get("node_code").toString());
            if (list != null && list.size() > 0) {
                pr.setMessage("获取城市列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 通过城市代码查询相对应的市或县(第三级)
     *
     * @param request
     * @return
     */
    public ProcessResult queryPayArea(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("node_code"))) {
                pr.setState(false);
                pr.setMessage("缺少node_code参数");
                return pr;
            }
            List<Map<String, Object>> list = this.bankCardDao.queryPayArea(params.get("node_code").toString());
            if (list != null && list.size() > 0) {
                pr.setMessage("获取区域列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询银行网点列表
     *
     * @param request
     * @return
     */
    public ProcessResult queryCnapsBankList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);

            List<Map<String, Object>> list = this.bankCardDao.queryCnapsBankList(params);
            if (list != null && list.size() > 0) {
                pr.setMessage("获取支行列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取綁卡用户信息
     *
     * @param request
     * @return
     */
    public ProcessResult queryBankCardBindUserInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("user_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_type");
                return pr;
            }

            //获取身份证号
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getIdCard");
            if (!pr.getState()) {
                return pr;
            }
            //身份证号
            String id_card = pr.getObj().toString();

            //获取用户名
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getUserName");
            if (!pr.getState()) {
                return pr;
            }
            //用户名
            String userName = pr.getObj().toString();

            //获取公司名称
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getCompanyName");
            if (!pr.getState()) {
                return pr;
            }
            //公司名称
            String company_name = pr.getObj().toString();

            //获取营业执照
            pr = this.getServiceProvidersParamInfo(params.get("user_type").toString(), "getBusinessLicense");
            if (!pr.getState()) {
                return pr;
            }
            //营业执照
            String business_license = pr.getObj().toString();

            Map param = new HashMap();
            param.put("ID_CARD", id_card);
            param.put("USER_NAME", userName);
            param.put("COMPANY_NAME", company_name);
            param.put("BUSINESS_LICENSE", business_license);
            if (param != null) {
                pr.setMessage("获取綁卡用户信息成功");
                pr.setObj(param);
            } else {
                pr.setState(false);
                pr.setMessage("获取綁卡用户信息失败");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取服务商系统配置参数信息
     *
     * @return
     */
    public ProcessResult getServiceProvidersParamInfo(String user_type, String get_type) {
        ProcessResult pr = new ProcessResult();
        try {
            Map params = new HashMap();
            Map paramInfo = new HashMap();
            if (get_type.equals("getId")) {
                if (SERVER_COMPANY.equals(user_type)) {
                    //服务公司用户id
                    params.put("key", "platform_user_id");
                } else if (STORAGE_COMPANY.equals(user_type)) {
                    //仓储公司用户id
                    params.put("key", "storage_user_id");
                } else if (NEW_RETAIL.equals(user_type)) {
                    //新零售用户id
                    params.put("key", "new_retail_user_id");
                } else if (SUMMARY_ACCOUNT.equals(user_type)) {
                    //汇总子账户id
                    params.put("key", "summary_account_user_id");
                } else {
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
                //获取用户id
                paramInfo = this.bankCardDao.queryCompanyParamByKey(params);
                if (StringUtils.isEmpty(paramInfo.get("VALUE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少user_id参数");
                    return pr;
                }
            } else if (get_type.equals("getAccount")) {
                if (SERVER_COMPANY.equals(user_type)) {
                    //平台服务公司子账号
                    params.put("key", "platform_bank_account");
                } else if (STORAGE_COMPANY.equals(user_type)) {
                    //仓储公司子账号
                    params.put("key", "storage_bank_account");
                } else if (NEW_RETAIL.equals(user_type)) {
                    //新零售子账号
                    params.put("key", "new_retail_bank_account");
                } else if (SUMMARY_ACCOUNT.equals(user_type)) {
                    //汇总子账户id
                    params.put("key", "summary_account_bank_account");
                } else {
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
                //获取银行账户
                paramInfo = this.bankCardDao.queryCompanyParamByKey(params);
                if (StringUtils.isEmpty(paramInfo.get("VALUE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少bank_account参数");
                    return pr;
                }
            } else if (get_type.equals("getIdCard")) {
                if (SERVER_COMPANY.equals(user_type)) {
                    //平台服务个人身份证号
                    params.put("key", "platform_personer_user_id_card");
                } else if (STORAGE_COMPANY.equals(user_type)) {
                    //仓储服务个人身份证号
                    params.put("key", "storage_personer_user_id_card");
                } else if (NEW_RETAIL.equals(user_type)) {
                    //新零售个人身份证号
                    params.put("key", "new_retail_personer_user_id_card");
                } else if (SUMMARY_ACCOUNT.equals(user_type)) {
                    //汇总子账户id
                    params.put("key", "summary_account_user_id_card");
                } else {
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
                //获取个人身份证号
                paramInfo = this.bankCardDao.queryCompanyParamByKey(params);
                if (StringUtils.isEmpty(paramInfo.get("VALUE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少id_card参数");
                    return pr;
                }
            } else if (get_type.equals("getUserName")) {
                if (SERVER_COMPANY.equals(user_type)) {
                    //平台服务个人用户名
                    params.put("key", "platform_personer_user_name");
                } else if (STORAGE_COMPANY.equals(user_type)) {
                    //仓储公司个人用户名
                    params.put("key", "storage_personer_user_name");
                } else if (NEW_RETAIL.equals(user_type)) {
                    //新零售个人用户名
                    params.put("key", "new_retail_personer_user_name");
                } else if (SUMMARY_ACCOUNT.equals(user_type)) {
                    //汇总子账户id
                    params.put("key", "summary_account_personer_user_name");
                } else {
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
                //获取个人用户名
                paramInfo = this.bankCardDao.queryCompanyParamByKey(params);
                if (StringUtils.isEmpty(paramInfo.get("VALUE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少user_name参数");
                    return pr;
                }
            } else if (get_type.equals("getBusinessLicense")) {
                if (SERVER_COMPANY.equals(user_type)) {
                    //平台服务公司营业执照
                    params.put("key", "platform_company_business_license");
                } else if (STORAGE_COMPANY.equals(user_type)) {
                    //仓储公司营业执照
                    params.put("key", "storage_company_business_license");
                } else if (NEW_RETAIL.equals(user_type)) {
                    //新零售公司营业执照
                    params.put("key", "new_retail_company_business_license");
                } else if (SUMMARY_ACCOUNT.equals(user_type)) {
                    //汇总子账户id
                    params.put("key", "summary_account_business_license");
                } else {
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
                //获取公司营业执照
                paramInfo = this.bankCardDao.queryCompanyParamByKey(params);
                if (StringUtils.isEmpty(paramInfo.get("VALUE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少id_card参数");
                    return pr;
                }
            } else if (get_type.equals("getCompanyName")) {
                if (SERVER_COMPANY.equals(user_type)) {
                    //平台服务公司名称
                    params.put("key", "platform_company_name");
                } else if (STORAGE_COMPANY.equals(user_type)) {
                    //仓储公司名称
                    params.put("key", "storage_company_name");
                } else if (NEW_RETAIL.equals(user_type)) {
                    //新零售公司名称
                    params.put("key", "new_retail_company_name");
                } else if (SUMMARY_ACCOUNT.equals(user_type)) {
                    //汇总子账户id
                    params.put("key", "summary_account_company_name");
                } else {
                    pr.setState(false);
                    pr.setMessage("user_type参数错误");
                    return pr;
                }
                //获取公司营业执照
                paramInfo = this.bankCardDao.queryCompanyParamByKey(params);
                if (StringUtils.isEmpty(paramInfo.get("VALUE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少company_name参数");
                    return pr;
                }
            }
            if (paramInfo != null) {
                pr.setObj(paramInfo.get("VALUE").toString());
                pr.setState(true);
            } else {
                pr.setState(false);
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

}
