package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.UserAccountCheckDao;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : UserAccountCheckService
 * 用户账户对账业务处理类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/9/4 17:49
 */
@Service("UserAccountCheckService")
public class UserAccountCheckService{


    @Value("${pay_service_url}")
    private String pay_service_url;                     //支付项目地址
    @Value("${bank_balance}")
    private String bank_balance;                        //获取见证宝子账户余额
    @Resource
    private UserAccountCheckDao userAccountCheckDao;    //账户对账数据访问接口

    private Log logger = LogFactory.getLog(this.getClass());
    
    /**
     * 分页获取对账任务列表数据
     * @param request
     * @return
     */
    public GridResult queryUserAccountCheckTaskByPage(HttpServletRequest request) {
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
            int total = this.userAccountCheckDao.queryListForCount(params);
            List<Map<String, Object>> list = this.userAccountCheckDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception ex) {
            gr.setState(false);
            gr.setMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }
        return gr;
    }

    /**
     * 添加对账任务
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addUserAccountCheckTask(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[public_user_id]创建用户");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("title"))){
                pr.setState(false);
                pr.setMessage("缺少参数[title]标题");
                return pr;
            }
            if(this.userAccountCheckDao.queryUserAccountCheckingTaskCount() >0){
                pr.setState(false);
                pr.setMessage("已存在即将运行或正在运行<br/>的对账任务，请稍后重试~");
                return pr;
            }

            if(this.userAccountCheckDao.insert(params) >0){
                //保存对账明细
                if(userAccountCheckDao.insertUserAccountCheckingDetail(params) >0){
                    pr.setState(true);
                    pr.setMessage("对账任务创建成功");
                }else{
                    throw new RuntimeException("对账任务创建失败");
                }
            }else {
                throw new RuntimeException("对账任务创建失败");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 对账任务明细
     * @param request
     * @return
     */
    public GridResult queryAccountCheckDetailByPage(HttpServletRequest request) {
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
            int total = this.userAccountCheckDao.queryListDetailForCount(params);
            List<Map<String, Object>> list = this.userAccountCheckDao.queryListDetailForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取对账明细成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception ex) {
            gr.setState(false);
            gr.setMessage(ex.getMessage());
            logger.error(ex.getMessage());
        }
        return gr;
    }

    /**
     * 重新核对
     * @param request
     * @return
     */
    public ProcessResult accountAgainCheck(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        try{
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[id]对账明细id");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("task_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[task_id]对账任务id");
                return pr;
            }
            //查询已对账明细数据
            Map<String, Object> detail = this.userAccountCheckDao.queryAccountCheckDetail(params);
            if(detail == null){
                pr.setState(false);
                pr.setMessage("该对账信息不存在");
                return pr;
            }

            long task_id = Long.parseLong(params.get("task_id").toString());                                //任务id
            String bank_account =detail.get("BANK_ACCOUNT").toString();                                     //银行子账户
            float local_account_balance = this.userAccountCheckDao.queryUserAccountBalance(bank_account);   //本地账户余额
            //发送请求见证宝账户余额
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("custAcctId", bank_account);                                                //银行子账户
            paramMap.put("selectFlag", "2");                                                         //普通会员子账户类型
            ProcessResult resPr = HttpClientUtil.post(pay_service_url + bank_balance, paramMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
            if(resPr.getState()){
                Map<String, Object> resultMap = (Map<String, Object>) resPr.getObj();
                float jzb_accountBalance = 0f;                                                       //见证宝账户余额
                if (!StringUtils.isEmpty(resultMap.get("array"))) {
                    List<Map<String, Object>> result = (List<Map<String, Object>>) resultMap.get("array");
                    jzb_accountBalance = Float.parseFloat(result.get(0).get("totalBalance").toString());
                }
                char state = '2';   //对账状态: 正常
                if(local_account_balance != jzb_accountBalance){
                    state = '1';    //对账状态: 异常
                }

                /*************封装对账明细数据(start)**********/
                Map<String, Object> updateMap = new HashMap<String, Object>();
                updateMap.put("state", state);
                updateMap.put("jzb_balance", jzb_accountBalance);
                updateMap.put("local_balance", local_account_balance);
                updateMap.put("task_id", task_id);                                  //对账所属任务id
                updateMap.put("id", Long.parseLong(detail.get("ID").toString()));   //明细id
                /**************封装对账明细数据(end)***********/

                if(this.userAccountCheckDao.update(updateMap) >0){
                    pr.setState(true);
                    pr.setMessage("已重新核对");
                    return pr;
                }else{
                    pr.setState(false);
                    pr.setMessage("核对失败");
                    return pr;
                }
            }else{
                pr.setState(false);
                pr.setMessage("获取见证宝子账户余额失败："+ resPr.getMessage());
                return pr;
            }
        }catch (Exception e){
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return pr;
    }
}
