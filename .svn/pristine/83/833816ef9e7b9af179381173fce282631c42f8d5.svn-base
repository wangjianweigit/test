package com.tk.oms.finance.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.CreditBillDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


/**
 * Copyright (c), 2017, Tongku
 * FileName : MessageService
 * 消息发送处理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-06-29
 */
@Service("MessageService")
public class MessageService {
	private Log logger = LogFactory.getLog(this.getClass());

    @Value("${sms_service_url}")
    private String sms_service_url;// --消息提醒
    
    @Resource
    private CreditBillDao creditBillDao;
    
    
    @Async
    public void asyncSendCreditBillMessage(Map<String, Object> param) {
    	/******************************************************发送微信或短信通知****begin********************************************/
        try{
        	//查询会员还款信息
			Map<String, Object> userMap = creditBillDao.queryUserRepaymentInfo(param);
			Map<String, Object> smsMap = new HashMap<String, Object>();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			smsMap.put("type", "9");//消息类型
            smsMap.put("mobile", userMap.get("MOBILE"));//手机号
            smsMap.put("openid", userMap.get("OPENID"));//微信号
            dataMap.put("repayment_date", param.get("create_date"));//还款时间
            dataMap.put("repayment_money", param.get("bill_amount"));//还款金额
            double surplus_money = 0;
            if(!StringUtils.isEmpty(param.get("creditbill_amount"))){
            	surplus_money = Double.parseDouble(param.get("creditbill_amount").toString())-Double.parseDouble(param.get("repayment_amount").toString())-Double.parseDouble(param.get("tran_amount").toString());
            }
			if(surplus_money<=0){
				dataMap.put("surplus_money", "");//本期剩余还款金额
			}else{
				dataMap.put("surplus_money", surplus_money);//本期剩余还款金额
			}
			
			smsMap.put("param", dataMap);
			
            //发送短信或微信提醒
    		ProcessResult pr = (ProcessResult) this.queryForPost(smsMap,sms_service_url);
            if(!pr.getState()){
            	logger.info("还款成功通知发送失败"+pr.getMessage());
            }else{
            	logger.info("还款成功通知发送成功");
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        /******************************************************发送微信或短信通知****end********************************************/
    }
    
    public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj,EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params);
        return  Transform.GetPacket(json,ProcessResult.class,EsbConfig.ERP_REVERSE_KEY_NAME);
	}
}
