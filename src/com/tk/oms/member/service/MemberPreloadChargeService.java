package com.tk.oms.member.service;

import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.oms.member.dao.MemberPreloadChargeDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
 * FileName : MemberPreloadChargeService
 * 会员预充值数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/12 11:40
 */
@Service
public class MemberPreloadChargeService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Value("${pay_service_url}")
    private String pay_service_url;//见证宝接口地址信息
    @Value("${tran_rechange}")
    private String tran_rechange;//会员充值地址

    @Resource
    private MemberPreloadChargeDao memberPreloadChargeDao;  //会员预充值数据访问接口
    @Resource
    private MemberInfoDao memberInfoDao;

    @Resource(name = "esbRabbitTemplate")
    private RabbitTemplate esbRabbitTemplate;

    /**
     * 添加员会预充值申请
     *
     * @param request
     * @return
     */
    public ProcessResult addPreloadedCharge(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (!StringUtils.isEmpty(json)) {
                params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            }
            if (params == null || params.isEmpty()) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少[public_user_id]申请人");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("money"))) {
                pr.setState(false);
                pr.setMessage("缺少[money]充值金额");
                return pr;
            }
            if(Float.parseFloat(params.get("money").toString()) <=0){
                pr.setState(false);
                pr.setMessage("充值金额有误");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("record_channel"))) {
                pr.setState(false);
                pr.setMessage("缺少[record_channel]收付渠道");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("collect_user_name"))) {
                pr.setState(false);
                pr.setMessage("缺少[collect_user_name]收款人用户名");
                return pr;
            }
            if (memberPreloadChargeDao.insert(params) > 0) {
            	//记录会员现金预充值日志
//				  Map<String,Object> logMap=new HashMap<String,Object>();
//				  logMap.put("USER_TYPE", 3);
//				  logMap.put("OPERATE_ID", 4);
//				  logMap.put("REMARK", "其它【预充值】");
//				  logMap.put("CREATE_USER_ID", params.get("public_user_id"));
//				  memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("申请成功");
            } else {
                pr.setState(true);
                pr.setMessage("申请失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 分页查询用户充值申请数据列表
     * @param request 查询条件
     * @return
     */
    public GridResult queryPreloadedChargeList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if ((!StringUtils.isEmpty(paramMap.get("state"))) && paramMap.get("state") instanceof String) {
                paramMap.put("state", (paramMap.get("state") + "").split(","));
            }
            //查询用户充值申请记录数
            int count = memberPreloadChargeDao.queryListForCount(paramMap);
            //分页查询用户充值申请记录
            List<Map<String, Object>> list = memberPreloadChargeDao.queryListForPage(paramMap);
            if (list != null) {
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
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
     * 查询会员预充值信息
     * @param request
     * @return
     */
    public ProcessResult queryPreloadedChargeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少参数
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[id]申请编号");
                return pr;
            }
            //获取现金预充值审批明细
            Map<String, Object> result = this.memberPreloadChargeDao.queryById(Long.parseLong(params.get("id").toString()));
            if (result != null) {
                pr.setState(true);
                pr.setMessage("获取成功");
                pr.setObj(result);
            } else {
                pr.setState(false);
                pr.setMessage("获取失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 会员现金预充值审批
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult approvePreloadedCharge(HttpServletRequest request) throws Exception {
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
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[id]申请编号");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[type]审批类型");
                return pr;
            }
            //审批类型参数检查
            String type = params.get("type").toString();
            if ("reject".equals(type)) {
                if (StringUtils.isEmpty(params.get("check_reject_reason"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[check_reject_reason]驳回原因");
                    return pr;
                }
            }
            //预充值申请信息
            Map<String, Object> preloadedCharge = this.memberPreloadChargeDao.queryById(Long.parseLong(params.get("id").toString()));
            if (preloadedCharge == null) {
                pr.setState(false);
                pr.setMessage("预充值申请单不存在");
                return pr;
            }
            // 交易申请记录当前状态
            if (!"1".equals(preloadedCharge.get("STATE"))) {
                pr.setState(false);
                pr.setMessage("记录已审批，请勿重复操作");
                return pr;
            }
            if (this.memberPreloadChargeDao.update(params) > 0) {
                if("approve".equals(type)){
                    //  更新会员余额和冻结余额
                    Map<String, Object> updateBalance = new HashMap<String, Object>();
                    updateBalance.put("user_id", preloadedCharge.get("COLLECT_USER_NAME").toString());
                    updateBalance.put("money", preloadedCharge.get("MONEY").toString());
                    if(memberInfoDao.updateAccountAndFrozenBalance(updateBalance) <= 0) {
                        throw new RuntimeException("更新会员余额失败");
                    }

                    //审批成功 创建收支记录
                    preloadedCharge.putAll(params);
                    if(this.memberPreloadChargeDao.insertIncomeRecord(preloadedCharge) <= 0){
                        throw new RuntimeException("创建收支记录失败");
                    }

                    // 更新会员余额相关校验码
                    memberInfoDao.updateUserAccountCode(Long.parseLong(preloadedCharge.get("COLLECT_USER_NAME").toString()));

                    // 获取会员信息
                    Map<String,Object> userInfo = memberInfoDao.queryUserAccountByUserName(preloadedCharge.get("COLLECT_USER_NAME").toString());
                    if(userInfo == null || StringUtils.isEmpty(userInfo.get("BANK_ACCOUNT"))){
                        throw new RuntimeException("会员用户帐户信息异常");
                    }

                    // 见证宝充值接口
                    updateBalance.put("bank_account", userInfo.get("BANK_ACCOUNT").toString());
                    updateBalance.put("rechange_number", preloadedCharge.get("RECORD_NUMBER").toString());
                    updateBalance.put("tran_amount", preloadedCharge.get("MONEY").toString());
                    updateBalance.put("thirdLogNo", preloadedCharge.get("TRAN_NUMBER").toString());
                    //远程调用见证宝接口
                    pr = HttpClientUtil.post(pay_service_url + tran_rechange, updateBalance);
                    if(!pr.getState()){
                        throw new RuntimeException(pr.getMessage());
                    }

                    // 组装财务记账消息体
                    Map<String, Object> messageMap = new HashMap<String, Object>();
                    messageMap.put("user_name", preloadedCharge.get("COLLECT_USER_NAME"));
                    messageMap.put("order_number", preloadedCharge.get("RECORD_NUMBER"));
                    messageMap.put("balance_money", "0");
                    messageMap.put("credit_money", "0");
                    messageMap.put("third_money", preloadedCharge.get("MONEY"));
                    messageMap.put("third_type", "现金");
                    messageMap.put("third_number", "");
                    messageMap.put("pay_seq", 1);
                    messageMap.put("operate_type", 2);
                    messageMap.put("pay_type", "tk.cashpay.trade.create");

                    // 发送财务记账消息
                    esbRabbitTemplate.convertAndSend(MqQueueKeyEnum.ESB_FINANCE_PAYMENT_RECORD.getKey(), Jackson.writeObject2Json(messageMap));
                    //记录会员现金预充值审批日志
//  				  	Map<String,Object> logMap=new HashMap<String,Object>();
//  				  	logMap.put("USER_TYPE", 3);
//  				  	logMap.put("OPERATE_ID", 4);
//  				  	logMap.put("REMARK", "其它【预充值审批】");
//  				  	logMap.put("CREATE_USER_ID", params.get("public_user_id"));
//  				  	memberInfoDao.insertUserOperationLog(logMap);
                }else{
                    pr.setState(true);
                    pr.setMessage("操作成功");
                }
            } else {
                pr.setState(true);
                pr.setMessage("操作失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
}
