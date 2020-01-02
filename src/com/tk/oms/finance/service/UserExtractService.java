package com.tk.oms.finance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.UserAccountRecordDao;
import com.tk.oms.finance.dao.UserChargeRecordDao;
import com.tk.oms.finance.dao.UserExtractDao;
import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 平台会员提现申请管理
 * @author songwangwen
 * @date  2017-5-8  下午2:22:14
 */
@Service("UserExtractService")
public class UserExtractService {
	@Value("${pay_service_url}")
	private String pay_service_url;//见证宝接口地址信息
	@Value("${bank_withdraw}")
	private String bank_withdraw;//会员或入驻商提现接口
	@Value("${tran_capital_unfreeze}")//见证宝资金解冻
	private String tran_capital_unfreeze;
	@Resource
	private UserExtractDao userExtractDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private UserChargeRecordDao userChargeRecordDao;
	@Resource
	private UserAccountRecordDao userAccountRecordDao;
	/**
     * 分页获取平台会员提现申请记录列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult queryUserExtractList(HttpServletRequest request) {
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
            int total = this.userExtractDao.queryListForCount(params);
            List<Map<String, Object>> list = this.userExtractDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取平台会员提现申请记录成功");
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
     * 根据ID获取待平台会员提现申请记录详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryUserExtractDetail(HttpServletRequest request) {
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
    		Map<String, Object> applyObj = this.userExtractDao.queryById(Long.parseLong(params.get("id").toString()));
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
     * 审批平台会员提现申请记录
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approvalUserExtract(HttpServletRequest request) throws Exception{
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
		Map<String, Object> applyObj = this.userExtractDao.queryById(Long.parseLong(params.get("id").toString()));
		if(applyObj == null
				||applyObj.isEmpty()){
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
			if (this.userExtractDao.approval(params)>0) {
				Map<String,Object> userInfo = memberInfoDao.queryUserAccountById(Long.parseLong(applyObj.get("APPLY_USER_ID").toString()));
				/******************提现驳回，反充余额修改*****************************/
				Map<String, Object> rechange_param = new HashMap<String, Object>();
				rechange_param.put("user_id",userInfo.get("USER_ID") );
				rechange_param.put("user_type","1");//默认为平台会员
				float money = Float.parseFloat(applyObj.get("EXTRACT_MONEY").toString());
				rechange_param.put("money",money);		//提现反充
				userChargeRecordDao.updateUserAccountBalance(rechange_param);
				if("2".equals(rechange_param.get("output_status"))){//本地余额修改成功
					throw new RuntimeException(rechange_param.get("output_msg")==null?"修改帐户余额失败":rechange_param.get("output_msg").toString());
				}
				//增加收支记录
				if(userAccountRecordDao.insertUserRecordByExtractReject(params)<=0){
					throw new RuntimeException("增加收支记录失败");
				}
				/***
				 * 审批驳回，解冻见证宝
				 */
				//见证宝冻结解冻
				/*
				Map<String, Object> unfreezeReqMap = new HashMap<String, Object>();
				unfreezeReqMap.put("user_id",userInfo.get("USER_ID"));
				unfreezeReqMap.put("bank_account",userInfo.get("BANK_ACCOUNT"));
				unfreezeReqMap.put("tran_amount", applyObj.get("EXTRACT_MONEY"));
				unfreezeReqMap.put("order_number",applyObj.get("APPLY_NUMBER"));
				ProcessResult unfreezePr = HttpClientUtil.post(pay_service_url + tran_capital_unfreeze, unfreezeReqMap);
				if (!unfreezePr.getState()) {
				    //冻结资金失败，抛出异常，数据回滚
					throw new RuntimeException("解冻见证宝资金失败,"+unfreezePr.getMessage());
				} 	*/
				gr.setState(true);
				gr.setMessage("审批驳回成功");
			} else {
				gr.setState(false);
				gr.setMessage("审批驳回失败");
			}
		}else if("2".equals(params.get("state").toString())){
			if (this.userExtractDao.approval(params)>0) {
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
     *  平台会员提现申请打款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult payUserExtract(HttpServletRequest request) throws Exception{
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
		Map<String, Object> applyObj = this.userExtractDao.queryById(Long.parseLong(params.get("id").toString()));
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

		//调用见证宝接口，进行打款操作
		Map<String,Object> userInfo = memberInfoDao.queryUserAccountById(Long.parseLong(applyObj.get("APPLY_USER_ID").toString()));
		if(userInfo ==null || StringUtils.isEmpty(userInfo.get("BANK_ACCOUNT"))){
			gr.setState(false);
			gr.setMessage("会员用户帐户信息异常！");
			return gr;
		}
		//见证宝冻结解冻
		/*
		Map<String, Object> unfreezeReqMap = new HashMap<String, Object>();
		unfreezeReqMap.put("user_id",userInfo.get("USER_ID"));
		unfreezeReqMap.put("bank_account",userInfo.get("BANK_ACCOUNT"));
		unfreezeReqMap.put("tran_amount", applyObj.get("EXTRACT_MONEY"));
		unfreezeReqMap.put("order_number",applyObj.get("APPLY_NUMBER"));
		ProcessResult unfreezePr = HttpClientUtil.post(pay_service_url + tran_capital_unfreeze, unfreezeReqMap);
		if (!unfreezePr.getState()) {
		    //冻结资金失败，抛出异常，数据回滚
			throw new RuntimeException("解冻见证宝资金失败,"+unfreezePr.getMessage());
		} 	*/
		Map<String, Object> bank_param = new HashMap<String, Object>();
		/**
		 * params: {
				user_id: 用户ID，
				bank_account：银行会员子账户，
				apply_number：提现申请单号，
				tran_amount：金额，
				id_card：身份证号，
				bank_card：银行卡号，
				user_name：用户姓名
			}
		 */
		bank_param.put("user_id",userInfo.get("USER_ID") );
		bank_param.put("bank_account",userInfo.get("BANK_ACCOUNT"));
		bank_param.put("apply_number",applyObj.get("APPLY_NUMBER"));
		bank_param.put("tran_amount",applyObj.get("EXTRACT_MONEY"));
		bank_param.put("id_card",userInfo.get("USER_MANAGE_CARDID"));
		bank_param.put("bank_card",applyObj.get("BANK_CARD"));
		bank_param.put("user_name",userInfo.get("USER_MANAGE_NAME"));
		bank_param.put("thirdLogNo",applyObj.get("TRAN_LOGNO"));
		//远程调用见证宝接口
		gr = HttpClientUtil.post(pay_service_url + bank_withdraw, bank_param);
		if(gr.getState()){
			//获取tran_logno提现交易号（用于查询银行处理状态）
			Map<String, Object> bakn_result = (Map<String, Object>) gr.getObj();
			// 原待银行处理(3) 变更为 打款到账(4) by wanghai
			params.put("state","4");//打款成功，待银行处理
			params.put("tran_logno",bakn_result.get("tran_logno"));//打款成功
			if (this.userExtractDao.pay(params)>0) {
				gr.setState(true);
				gr.setMessage("打款成功");
			} else {
				gr.setState(false);
				gr.setMessage("打款失败");
			}
		}else{
			throw new RuntimeException("审批通过失败，"+gr.getMessage());
		}
    	return gr;
    }
}
