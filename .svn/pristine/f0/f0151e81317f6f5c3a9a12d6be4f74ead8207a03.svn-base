package com.tk.oms.finance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.SysUserExtractDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.store.finance.dao.AccountBalanceDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("SysUserExtractService")
public class SysUserExtractService {
	@Resource
	private SysUserExtractDao sysUserExtractDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	@Resource
	private AccountBalanceDao accountBalanceDao;
	/**
     * 分页获取系统用户提现申请记录列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult querySysUserExtractList(HttpServletRequest request) {
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
            int total = sysUserExtractDao.queryListForCount(params);
            List<Map<String, Object>> list = sysUserExtractDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取系统用户提现申请记录成功");
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
     * 根据ID获取待系统用户提现申请记录详情
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
    		Map<String, Object> applyObj = sysUserExtractDao.queryById(Long.parseLong(params.get("id").toString()));
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
     * 审批系统用户提现申请记录
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approvalSysUserExtract(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
		String json = HttpUtil.getRequestInputStream(request);
		try{
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if(params == null
					||params.isEmpty()
					||StringUtils.isEmpty(params.get("id"))
					||StringUtils.isEmpty(params.get("state"))
					){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			Map<String, Object> applyObj = sysUserExtractDao.queryById(Long.parseLong(params.get("id").toString()));
			
			if(applyObj == null
					||applyObj.isEmpty()){
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
	
			if("3".equals(params.get("state").toString())){
				if(StringUtils.isEmpty(params.get("reject_reason"))){
					pr.setState(false);
	    			pr.setMessage("审批驳回必须输入驳回原因");
	    			return pr;
				}
				if (sysUserExtractDao.approval(params)>0) {
					pr.setState(true);
					pr.setMessage("审批驳回成功");
				} else {
					pr.setState(false);
					pr.setMessage("审批驳回失败");
				}
			}else if("2".equals(params.get("state").toString())){
				Map<String,Object> codeParams = new HashMap<String,Object>();
				codeParams.put("user_id", applyObj.get("APPLY_USER_ID"));
				//查询系统用户账户信息,并加锁
				Map<String, Object> sbaMap = sysUserExtractDao.querySysBankAccountInfo(codeParams);
				if(StringUtils.isEmpty(sbaMap)){
					pr.setState(false);
					pr.setMessage("帐户异常");
					return pr;
				}
				//校验余额是否发生变更
				codeParams.put("c_user_type", "old");
				//获取用户key
				String key= sysUserExtractDao.getUserKey(codeParams);
				//获取余额校验码
				codeParams.put("account_balance", sbaMap.get("ACCOUNT_BALANCE"));
				codeParams.put("c_user_key",key);
				String code = sysUserExtractDao.getCheck_Code(codeParams);
				//判断校验码是否被篡改
				if(StringUtils.isEmpty(sbaMap.get("ACCOUNT_BALANCE_CHECKCODE"))||!sbaMap.get("ACCOUNT_BALANCE_CHECKCODE").equals(code)){
					pr.setState(false);
					pr.setMessage("余额发生篡改，无法完成当前操作!");
					return pr;
				}
				//生成个人收支记录
				codeParams.put("remark", applyObj.get("REMARK"));
				codeParams.put("user_realname", applyObj.get("USER_REALNAME"));
				codeParams.put("extract_money", applyObj.get("EXTRACT_MONEY"));
				if(sysUserExtractDao.insertSysAccountRecord(codeParams) ==0){
					throw new RuntimeException("生成个人收支记录失败");
				}
				//更新账户余额和验证码
				codeParams.put("c_user_type", "new");
				//获取用户key
				String newKey= sysUserExtractDao.getUserKey(codeParams);
				codeParams.put("c_user_key", newKey);
				if(sysUserExtractDao.updateSysBankAccountBalance(codeParams)==0){
					throw new RuntimeException("更新账户余额和验证码错误");
				}
				//更新用户账户key
				if(sysUserExtractDao.updateSysUserCacheKey(codeParams)==0){
					throw new RuntimeException("更新用户账户key错误");
				}
				if (sysUserExtractDao.approval(params)>0) {
					pr.setState(true);
					pr.setMessage("审批通过成功");
				} else {
					throw new RuntimeException("审批错误");
				}
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("审批异常:"+e.getMessage());
		}
    	return pr;
    }
    
    /**
     * 添加系统用户提现信息
     * @param request
     * @return
     */
    @Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult addSysUserExtract(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(StringUtils.isEmpty(paramMap.get("bank_card"))){
				pr.setState(false);
				pr.setMessage("缺少参数银行卡号【bank_card】");
				return pr;
			}
			/********************提现金额*************************/
			float extract_money = 0f;
			if(StringUtils.isEmpty(paramMap.get("extract_money"))){
				pr.setState(false);
				pr.setMessage("缺少参数提现金额【extract_money】");
				return pr;
			}
			try {
				extract_money = Float.parseFloat(paramMap.get("extract_money").toString());
			} catch (Exception e) {
				pr.setState(false);
				pr.setMessage("提现金额必须为大于0的有效数字");
				return pr;
			}
			if(extract_money<=0){
				pr.setState(false);
				pr.setMessage("提现金额必须为大于0的有效金额");
				return pr;
			}
			if(extract_money>50000){
				pr.setState(false);
				pr.setMessage("单笔提现金额限额50000，如有大于50000的提现需求，请按多笔拆分");
				return pr;
			}
			//查询提现系统用户
			param.put("id", paramMap.get("public_user_id"));
			SysUserInfo userInfo = sysUserInfoDao.queryById(param);
			if(userInfo==null || StringUtils.isEmpty(userInfo)){
				pr.setState(false);
				pr.setMessage("用户信息不存在");
				return pr;
			}
			if(userInfo.getState() != 2){
				pr.setState(false);
				pr.setMessage("用户状态异常");
				return pr;
			}
			//系统用户银行卡信息
			Map<String, Object> bankMap = sysUserExtractDao.querySysUserBankInfo(paramMap);
			if(bankMap==null || StringUtils.isEmpty(bankMap)){
				pr.setState(false);
				pr.setMessage("卡号不存在或者与当前用户不匹配");
				return pr;
			}
			if(!bankMap.get("BIND_STATE").equals("1")){
				pr.setState(false);
				pr.setMessage("该卡号未绑定");
				return pr;
			}
			//查询账户余额
			Map<String, Object> map = accountBalanceDao.queryAccountBalance(paramMap);
			float final_balance = StringUtils.isEmpty(map.get("ACCOUNT_BALANCE"))?0:Float.parseFloat(map.get("ACCOUNT_BALANCE").toString());
			if(extract_money>final_balance){
				pr.setState(false);
				pr.setMessage("提现金额【"+extract_money+"】大于可提现金额【"+final_balance+"】");
				return pr;
			}
			/***********************所有验证完成，开始执行提现操作**********************************/
			/*
			 *1、插入提现申请记录 
			 */
			Map<String,Object>  sysUserExtractMap = new HashMap<String, Object>();
			sysUserExtractMap.put("bank_id", bankMap.get("ID"));				       //提现银行卡记录ID
			sysUserExtractMap.put("user_id", paramMap.get("public_user_id"));	   //提现用户ID
			sysUserExtractMap.put("extract_money", extract_money);				   //提现金额
			sysUserExtractMap.put("phone", userInfo.getPhone());					//负责人手机
			sysUserExtractMap.put("remark", paramMap.get("remark"));			   //提现申请备注信息
			
			sysUserExtractDao.insertSysUserExtractRecord(sysUserExtractMap);
			pr.setMessage("提现成功，等待审批");;
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException("提现异常："+e.getMessage());
		}
		return pr;
	}
}
