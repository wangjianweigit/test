package com.tk.oms.finance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.StoreExtractDao;
import com.tk.oms.finance.dao.UserAccountRecordDao;
import com.tk.oms.finance.dao.UserChargeRecordDao;
import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 
 * Copyright (c), 2018, Tongku
 * FileName : StoreExtractService.java
 * 联营商户提现管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2018年5月23日
 */
@Service("StoreExtractService")
public class StoreExtractService {
	@Resource
	private StoreExtractDao storeExtractDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private UserChargeRecordDao userChargeRecordDao;
	@Resource
	private UserAccountRecordDao userAccountRecordDao;
	/**
     * 分页获取联营商户提现申请记录列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult queryStoreExtractList(HttpServletRequest request) {
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
            if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
            	params.put("state",(params.get("state")+"").split(","));
			}
            int total = this.storeExtractDao.queryListForCount(params);
            List<Map<String, Object>> list = this.storeExtractDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取联营商户提现申请记录成功");
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
     * 根据ID获取待联营商户提现申请记录详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryStoreExtractDetail(HttpServletRequest request) {
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
    		Map<String, Object> applyObj = this.storeExtractDao.queryById(Long.parseLong(params.get("id").toString()));
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
     * 审批联营商户提现申请记录
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approvalStoreExtract(HttpServletRequest request) throws Exception{
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
			gr.setMessage("缺少参数");
			return gr;
		}
		
		Map<String, Object> applyObj = this.storeExtractDao.queryById(Long.parseLong(params.get("id").toString()));
		if(applyObj == null
				||applyObj.isEmpty()){
			gr.setState(false);
			gr.setMessage("当前申请记录不存在或已被删除");
			return gr;
		}
		//查询联营用户账户信息,并加锁
		Map<String, Object> sbaMap = storeExtractDao.queryStoreBankAccountInfo(Long.parseLong(applyObj.get("APPLY_USER_ID").toString()));
		if(StringUtils.isEmpty(sbaMap)){
			gr.setState(false);
			gr.setMessage("帐户异常");
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
			if (this.storeExtractDao.approval(params)>0) {
				Map<String,Object> userInfo = memberInfoDao.queryUserAccountById(Long.parseLong(applyObj.get("APPLY_USER_ID").toString()));
				/******************提现驳回，反充余额修改*****************************/
				Map<String, Object> rechange_param = new HashMap<String, Object>();
				rechange_param.put("user_id",userInfo.get("USER_ID") );
				rechange_param.put("user_type","1");//默认为联营商户
				float money = Float.parseFloat(applyObj.get("EXTRACT_MONEY").toString());
				rechange_param.put("money",money);		//提现反充
				userChargeRecordDao.updateStoreAccountBalance(rechange_param);
				if("2".equals(rechange_param.get("output_status"))){//本地余额修改成功
					throw new RuntimeException(rechange_param.get("output_msg")==null?"修改帐户余额失败":rechange_param.get("output_msg").toString());
				}
				//增加收支记录
				if(userAccountRecordDao.insertStoreRecordByExtractReject(params)<=0){
					throw new RuntimeException("增加收支记录失败");
				}
				gr.setState(true);
				gr.setMessage("审批驳回成功");
			} else {
				gr.setState(false);
				gr.setMessage("审批驳回失败");
			}
		}else if("2".equals(params.get("state").toString())){
			if (this.storeExtractDao.approval(params)>0) {
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
     *  联营商户提现申请打款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult payStoreExtract(HttpServletRequest request) throws Exception{
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
			gr.setMessage("缺少参数");
			return gr;
		}
		if(!params.containsKey("voucher_img_url") && StringUtils.isEmpty(params.get("voucher_img_url").toString())) {
			gr.setState(false);
			gr.setMessage("请上传打款凭证");
			return gr;
		}
		Map<String, Object> applyObj = this.storeExtractDao.queryById(Long.parseLong(params.get("id").toString()));
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

		params.put("state","4");//打款成功
		if (this.storeExtractDao.pay(params)>0) {
			gr.setState(true);
			gr.setMessage("打款成功");
		} else {
			gr.setState(false);
			gr.setMessage("打款失败");
		}
    	return gr;
    }
}
