package com.tk.oms.finance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.oms.attesttreas.dao.AttesttreasDao;
import com.tk.oms.member.dao.MemberInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.RetailExtractDao;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 新零售商家提现申请管理
 * @author shif
 * @date  20191023
 */
@Service("RetailExtractService")
public class RetailExtractService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Value("${retail_service_new_url}")
    private String retail_service_new_url;//新零售系统服务
	@Value("${retail_extract_approval}")
	private String retail_extract_approval;//新零售商家提现审批

	@Value ("${pay_service_url}")
	private String pay_service_url;//远程调用见证宝接口

	@Value("${tran_directpay}")//见证宝直接支付
	private String tran_directpay;

	@Resource
	private RetailExtractDao retailExtractDao;

	@Resource
	private MemberInfoDao memberInfoDao;

	@Resource
	private AttesttreasDao attesttreasDao;
	/**
     * 分页获取新零售商家提现申请记录列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult queryRetailExtractList(HttpServletRequest request) {
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
            int total = this.retailExtractDao.queryListForCount(params);
            List<Map<String, Object>> list = this.retailExtractDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取新零售商家提现申请记录成功");
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
     * 根据ID获取待新零售商家提现申请记录详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryRetailExtractDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if (StringUtils.isEmpty(json)) {
    			pr.setState(false);
    			pr.setMessage("缺少参数");
    			return pr;
    		}
    		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
    		if(params == null
    				||params.isEmpty()
    				||StringUtils.isEmpty(params.get("id"))){
    			pr.setState(false);
    			pr.setMessage("缺少参数[ID]");
    			return pr;
    		}
    		Map<String, Object> applyObj = this.retailExtractDao.queryById(Long.parseLong(params.get("id").toString()));
    		if(applyObj == null || applyObj.isEmpty()){
    			pr.setState(false);
    			pr.setMessage("当前申请记录不存在或已被删除");
    			return pr;
    		}
    		pr.setState(true);
    		pr.setMessage("获取申请记录成功");
    		pr.setObj(applyObj);
    		return pr;
    	} catch (Exception ex) {
    		pr.setState(false);
    		pr.setMessage(ex.getMessage());
    		ex.printStackTrace();
    	}
    	return pr;
    }
    /**
     * 审批新零售商家提现申请记录
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approvalRetailExtract(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
    	try{
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null || params.isEmpty() || StringUtils.isEmpty(params.get("id")) || StringUtils.isEmpty(params.get("state"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> applyObj = this.retailExtractDao.queryById(Long.parseLong(params.get("id").toString()));
			if(applyObj == null || applyObj.isEmpty()){
				pr.setState(false);
				pr.setMessage("当前申请记录不存在或已被删除");
				return pr;
			}
	
			// 交易申请记录当前状态
			if(!"1".equals(String.valueOf(applyObj.get("STATE")))){
				pr.setState(false);
				pr.setMessage("记录已审批，请勿重复操作");
				return pr;
			}
	
			if("10".equals(params.get("state").toString())){
				if(StringUtils.isEmpty(params.get("reject_reason"))){
					pr.setState(false);
	    			pr.setMessage("审批驳回必须输入驳回原因");
	    			return pr;
				}
				if (retailExtractDao.approval(params)>0) {
					pr.setState(true);
					pr.setMessage("审批驳回成功");
				} else {
					pr.setState(false);
					pr.setMessage("审批驳回失败");
				}
			}else if("2".equals(params.get("state").toString())){
				if (retailExtractDao.approval(params)>0) {
					pr.setState(true);
					pr.setMessage("审批通过成功");
				} else {
					pr.setState(false);
					pr.setMessage("审批通过失败");
				}
			}
			//调用新零售远程接口
			Map<String, Object> bank_param = new HashMap<String, Object>();
			bank_param.put("AGENT_ID",applyObj.get("USER_ID"));
			bank_param.put("CHECK_RESULT", "2".equals(params.get("state").toString())?"1":"2");// 操作类型。可选值 1审核通过。2驳回。3打款。
			bank_param.put("APPLY_NUMBER", applyObj.get("NEW_RETAIL_APPLY_NUMBER"));// 申请单号
			bank_param.put("CHECK_USER_NAME", params.get("public_user_name"));//"审核人用户名",
			bank_param.put("CHECK_BUSINESS_USER_NAME",params.get("public_user_realname"));//审核人真实姓名
			bank_param.put("REJECT_REASON",params.get("reject_reason"));//审核驳回原因
			//远程调用新零售接口
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(bank_param, retail_service_new_url + retail_extract_approval);
	        if (Integer.parseInt(resPr.get("state").toString()) == 1) {
	        	pr.setState(true);
	        	pr.setMessage("审核操作成功");
				return pr;
			}else{
				throw new RuntimeException("调用新零售接口失败："+(StringUtils.isEmpty(resPr)?"未知异常":resPr.get("msg").toString()));
			}
    	}catch (Exception e ){
    		logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
    	}
    }
    
    
    /**
     *  新零售商家提现申请打款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult payRetailExtract(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
    	try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null || params.isEmpty() || StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			if(!params.containsKey("voucher_img_url") && StringUtils.isEmpty(params.get("voucher_img_url").toString())) {
				pr.setState(false);
				pr.setMessage("请上传打款凭证");
				return pr;
			}
			Map<String, Object> applyObj = this.retailExtractDao.queryById(Long.parseLong(params.get("id").toString()));
			if(applyObj == null || applyObj.isEmpty()){
				pr.setState(false);
				pr.setMessage("当前申请记录不存在或已被删除");
				return pr;
			}
			// 交易申请记录当前状态
			if(!"1".equals(String.valueOf(applyObj.get("STATE")))){
				pr.setState(false);
				pr.setMessage("记录已打款，请勿重复操作");
				return pr;
			}
			params.put("state","4");//打款成功，待银行处理
			if(retailExtractDao.pay(params)>0) {
				Map<String, Object> bank_param = new HashMap<String, Object>();
				bank_param.put("AGENT_ID",applyObj.get("USER_ID"));
				// 操作类型。可选值 1审核通过。2驳回。3打款
				bank_param.put("CHECK_RESULT", "3");
				// 申请单号
				bank_param.put("APPLY_NUMBER", applyObj.get("NEW_RETAIL_APPLY_NUMBER"));
				// 审核人用户名
				bank_param.put("PLAYMONEY_USER_NAME", params.get("public_user_name"));
				// 审核人真实姓名
				bank_param.put("PLAYMONEY_BUSINESS_USER_NAME",params.get("public_user_realname"));
				// 远程调用新零售接口
				Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(bank_param, retail_service_new_url + retail_extract_approval);
		        if (Integer.parseInt(resPr.get("state").toString()) == 1) {
		        	pr.setState(true);
					pr.setMessage("打款操作成功");
					return pr;
				}else{
					throw new RuntimeException("调用新零售接口失败："+(StringUtils.isEmpty(resPr)?"未知异常":resPr.get("msg").toString()));
				}
			}
    	}catch(Exception e) {
    		 logger.error(e.getMessage());
             throw new RuntimeException(e.getMessage());
		}
    	return pr;
    }

	/**
	 * 新零售商家提现转余额申请列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRetailTransferApplyList(HttpServletRequest request) {
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
			int total = this.retailExtractDao.queryListBalanceTransferApplyForCount(params);
			List<Map<String, Object>> list = this.retailExtractDao.queryBalanceTransferApplyListForPage(params);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取新零售商家提现转余额申请记录成功");
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
	 * 新零售商家提现转余额申请打款
	 * @param request
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ProcessResult transferBalanceApproval(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		String json = HttpUtil.getRequestInputStream(request);
		if (StringUtils.isEmpty(json)) {
			pr.setState(false);
			pr.setMessage("缺少参数");
			return pr;
		}
		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		if(params == null || params.isEmpty() || StringUtils.isEmpty(params.get("id"))){
			pr.setState(false);
			pr.setMessage("缺少参数");
			return pr;
		}

		Map<String, Object> applyObj = this.retailExtractDao.queryById(Long.parseLong(params.get("id").toString()));
		if(applyObj == null || applyObj.isEmpty()){
			pr.setState(false);
			pr.setMessage("当前申请记录不存在或已被删除");
			return pr;
		}
		// 交易申请记录当前状态
		if("3".equals(String.valueOf(applyObj.get("STATE")))){
			pr.setState(false);
			pr.setMessage("记录已审批打款，请勿重复操作");
			return pr;
		}
		if("3".equals(params.get("state").toString())){
			// 更新为已打款状态
			params.put("state", 3);
			if (retailExtractDao.approval(params)>0) {
				Map<String, Object> updateBalanceMap = new HashMap<String, Object>(16);
				long inUserId = Long.parseLong(applyObj.get("USER_ID").toString());
				double tranAmount = Double.parseDouble(applyObj.get("WITHDRAWAL_AMOUNT").toString());
				updateBalanceMap.put("user_id", inUserId);
				updateBalanceMap.put("money", tranAmount);
				if (memberInfoDao.updateAccountBalance(updateBalanceMap) <= 0) {
					throw new RuntimeException("转入余额失败");
				}
				// 获取转账账户信息
				Map<String, Object> bankAccount = memberInfoDao.queryBankAccountInfoByUserId(inUserId);

				// 更新会员校验码
				memberInfoDao.updateUserAccountCode(inUserId);

				// 请求见证宝会员间交易
				Map<String, Object> retMap = new HashMap<String, Object>(16);
				retMap.put("out_user_id", applyObj.get("OUT_USER_ID"));
				retMap.put("out_bank_account", applyObj.get("OUT_BANK_ACCOUNT"));
				retMap.put("out_user_name", applyObj.get("OUT_USER_MANAGE_NAME"));
				retMap.put("in_user_id", inUserId);
				retMap.put("in_bank_account", bankAccount.get("BANK_ACCOUNT").toString());
				retMap.put("in_user_name", applyObj.get("USER_MANAGE_NAME"));
				retMap.put("tran_amount", tranAmount);
				retMap.put("order_number",  applyObj.get("NEW_RETAIL_APPLY_NUMBER").toString());
				// 交易流水号
				retMap.put("thirdLogNo", attesttreasDao.getThiredLogNo());
				pr = HttpClientUtil.post(pay_service_url + tran_directpay, retMap, EsbConfig.PAY_FORWARD_KEY_NAME, EsbConfig.PAY_REVERSE_KEY_NAME);
				if(!pr.getState()){
					throw new RuntimeException(pr.getMessage());
				}
				Map<String, Object> bank_param = new HashMap<String, Object>(16);
				bank_param.put("AGENT_ID",applyObj.get("USER_ID"));
				// 操作类型。可选值 1审核通过。2驳回。3打款
				bank_param.put("CHECK_RESULT", "3");
				// 申请单号
				bank_param.put("APPLY_NUMBER", applyObj.get("NEW_RETAIL_APPLY_NUMBER"));
				// 审核人用户名
				bank_param.put("PLAYMONEY_USER_NAME", params.get("public_user_name"));
				// 审核人真实姓名
				bank_param.put("PLAYMONEY_BUSINESS_USER_NAME",params.get("public_user_realname"));
				// 远程调用新零售接口
				Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(bank_param, retail_service_new_url + retail_extract_approval);
				if (Integer.parseInt(resPr.get("state").toString()) == 1) {
					pr.setState(true);
					pr.setMessage("审批通过成功");
					return pr;
				}else{
					throw new RuntimeException("调用新零售接口失败："+(StringUtils.isEmpty(resPr)?"未知异常":resPr.get("msg").toString()));
				}
			} else {
				pr.setState(false);
				pr.setMessage("审批通过失败");
			}
		}else if ("10".equals(params.get("state").toString())){
			if (StringUtils.isEmpty(params.get("reject_reason"))) {
				pr.setState(false);
				pr.setMessage("审批驳回必须输入驳回原因");
				return pr;
			}
			if (retailExtractDao.approval(params)>0) {
				pr.setState(true);
				pr.setMessage("审批驳回成功");
			} else {
				pr.setState(false);
				pr.setMessage("审批驳回失败");
			}
			Map<String, Object> bank_param = new HashMap<String, Object>(16);
			bank_param.put("AGENT_ID",applyObj.get("USER_ID"));
			// 操作类型。可选值 1审核通过。2驳回。3打款
			bank_param.put("CHECK_RESULT", "2");
			// 申请单号
			bank_param.put("APPLY_NUMBER", applyObj.get("NEW_RETAIL_APPLY_NUMBER"));
			// 审核人用户名
			bank_param.put("CHECK_USER_NAME", params.get("public_user_name"));
			// 审核人真实姓名
			bank_param.put("CHECK_BUSINESS_USER_NAME",params.get("public_user_realname"));
			// 驳回原因
			bank_param.put("REJECT_REASON", params.get("reject_reason"));
			// 远程调用新零售接口
			Map<String, Object> resPr = (Map<String, Object>) this.queryForPost(bank_param, retail_service_new_url + retail_extract_approval);
			if (Integer.parseInt(resPr.get("state").toString()) == 1) {
				pr.setState(true);
				pr.setMessage("审批通过成功");
				return pr;
			}else{
				throw new RuntimeException("调用新零售接口失败："+(StringUtils.isEmpty(resPr)?"未知异常":resPr.get("message").toString()));
			}
		}
		return pr;
	}

    
    public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params,30000);
        return Transform.GetPacketJzb(json,Map.class);
    }
}
