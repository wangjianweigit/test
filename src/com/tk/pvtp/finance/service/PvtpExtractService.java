package com.tk.pvtp.finance.service;

import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.pvtp.finance.dao.PvtpExtractDao;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 私有平台会员提现申请管理
 * @author zhengfy
 * @date  2019-8-22
 */
@Service("PvtpExtractService")
public class PvtpExtractService {
	@Value("${pay_service_url}")
	private String pay_service_url;//见证宝接口地址信息
	@Value("${bank_withdraw}")
	private String bank_withdraw;//会员或入驻商提现接口
	@Resource
	private PvtpExtractDao pvtpExtractDao;
	@Resource
	private MemberInfoDao memberInfoDao;

	/**
     * 分页获取私有平台会员提现申请记录列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult queryList(HttpServletRequest request) {
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

            int total = this.pvtpExtractDao.queryListForCount(params);
            List<Map<String, Object>> list = this.pvtpExtractDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取私有平台会员提现申请记录成功");
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
     * 根据ID获取私有平台会员提现申请记录详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryDetail(HttpServletRequest request) {
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
    		Map<String, Object> applyObj = this.pvtpExtractDao.queryById(Long.parseLong(params.get("id").toString()));
    		if(applyObj == null ||applyObj.isEmpty()){
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
     * 审批平台会员提现申请记录
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approval(HttpServletRequest request) throws Exception{
    	ProcessResult gr = new ProcessResult();
		String json = HttpUtil.getRequestInputStream(request);
		if (StringUtils.isEmpty(json)) {
			gr.setState(false);
			gr.setMessage("缺少参数");
			return gr;
		}
		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		if(params == null ||params.isEmpty() ||StringUtils.isEmpty(params.get("id")) ||StringUtils.isEmpty(params.get("state"))){
			gr.setState(false);
			gr.setMessage("缺少参数");
			return gr;
		}
		Map<String, Object> applyObj = this.pvtpExtractDao.queryById(Long.parseLong(params.get("id").toString()));
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

		if("10".equals(params.get("state").toString())) {
			if (StringUtils.isEmpty(params.get("reject_reason"))) {
				gr.setState(false);
				gr.setMessage("审批驳回必须输入驳回原因");
				return gr;
			}
			params.put("platform_state", "4"); //商家审核状态改为待处理
		}
		if (this.pvtpExtractDao.approval(params)>0) {
			gr.setState(true);
			gr.setMessage("审批成功");
		} else {
			gr.setState(false);
			gr.setMessage("审批失败");
		}
    	return gr;
    }
    
    
    /**
     *  平台会员提现申请打款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult pay(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
		String json = HttpUtil.getRequestInputStream(request);
		if (StringUtils.isEmpty(json)) {
			pr.setState(false);
			pr.setMessage("缺少参数");
			return pr;
		}
		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		if(params == null ||params.isEmpty() ||StringUtils.isEmpty(params.get("id"))){
			pr.setState(false);
			pr.setMessage("缺少参数");
			return pr;
		}
		if(!params.containsKey("voucher_img_url") && StringUtils.isEmpty(params.get("voucher_img_url").toString())) {
			pr.setState(false);
			pr.setMessage("请上传打款凭证");
			return pr;
		}
		Map<String, Object> applyObj = this.pvtpExtractDao.queryById(Long.parseLong(params.get("id").toString()));
		if(applyObj == null ||applyObj.isEmpty()){
			pr.setState(false);
			pr.setMessage("当前申请记录不存在或已被删除");
			return pr;
		}

		// 交易申请记录当前状态
		if(!"2".equals(String.valueOf(applyObj.get("STATE")))){
			pr.setState(false);
			pr.setMessage("记录已打款，请勿重复操作");
			return pr;
		}

		//调用见证宝接口，进行打款操作
		Map<String,Object> userInfo = memberInfoDao.queryUserAccountById(Long.parseLong(applyObj.get("APPLY_USER_ID").toString()));
		if(userInfo ==null || StringUtils.isEmpty(userInfo.get("BANK_ACCOUNT"))){
			pr.setState(false);
			pr.setMessage("会员用户帐户信息异常！");
			return pr;
		}
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
		pr = HttpClientUtil.post(pay_service_url + bank_withdraw, bank_param);
		if(pr.getState()){
			//获取tran_logno提现交易号（用于查询银行处理状态）
			Map<String, Object> bakn_result = (Map<String, Object>) pr.getObj();
			// 原待银行处理(3) 变更为 打款到账(4) by wanghai
			params.put("state","4");//打款成功，待银行处理
			params.put("tran_logno",bakn_result.get("tran_logno"));//打款成功
			if (this.pvtpExtractDao.pay(params)>0) {
				pr.setState(true);
				pr.setMessage("打款成功");
			} else {
				pr.setState(false);
				pr.setMessage("打款失败");
			}
		}else{
			throw new RuntimeException("审批通过失败，"+pr.getMessage());
		}
    	return pr;
    }
}
