package com.tk.oms.finance.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.finance.dao.SysUserAuthApprovalDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("SysUserAuthApprovalService")
public class SysUserAuthApprovalService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private SysUserAuthApprovalDao sysUserAuthApprovalDao;
	@Value("${pay_service_url}")
	private String pay_service_url;//
	
	/**
     * 分页获取用户认证信息列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult querySysUserAuthApprovalList(HttpServletRequest request) {
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
            int total = sysUserAuthApprovalDao.querySysUserAuthApprovalCount(params);
            List<Map<String, Object>> list = sysUserAuthApprovalDao.querySysUserAuthApprovalList(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取用户认证信息列表成功");
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
	 * 用户认证信息详情
	 * @param request
	 * @return
	 */
	public GridResult querySysUserAuthApprovalDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
                return gr;
            }
			Map<String,Object> detail = sysUserAuthApprovalDao.querySysUserAuthApprovalDetail(params);
			if (detail != null ) {
				gr.setMessage("查询成功!");
				gr.setObj(detail);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	
	/**
	 * 系统用户认证信息审核
	 * @param request
	 * @return
     */
	@Transactional
	public ProcessResult authenticationInfoApproval(HttpServletRequest request) throws Exception{
		    ProcessResult pr = new ProcessResult();
		    try{
				String json = HttpUtil.getRequestInputStream(request);
				 Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
				//查询会员认证信息
				Map<String,Object> detail=sysUserAuthApprovalDao.querySysUserAuthApprovalDetail(params);
				if(Integer.parseInt(detail.get("STATE").toString())!=0){
					pr.setState(false);
					pr.setMessage("当前会员认证信息状态异常");
					return pr;
				}
				if(Integer.parseInt(params.get("state").toString())==1){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("user_id",detail.get("USER_ID") );
					param.put("name",detail.get("USER_REAL_NAME") );
					param.put("alias_name",detail.get("USER_REAL_NAME") );
					param.put("service_phone", "0577-56578888");
					param.put("id_card_name",detail.get("USER_REAL_NAME"));
					param.put("id_card_num",detail.get("USER_MANAGE_CARDID"));
					param.put("store_address","");
					param.put("id_card_hand_img_url", detail.get("USER_MANAGE_CARDID_IMG"));
					param.put("store_front_img_url", "");
					param.put("province","");
					param.put("city","");
					param.put("district","");
					
					Map<String, Object> accInfo=new HashMap<String,Object>();
					accInfo.put("user_id",detail.get("USER_ID"));
					pr=HttpClientUtil.post(pay_service_url+"/bankAccount/query",param);
					if(pr.getState()){
						Map<String, Object> retMap=(Map<String, Object>) pr.getObj();
						accInfo.put("bank_account", retMap.get("bank_account"));
						accInfo.put("sub_merchant_id", retMap.get("sub_merchant_id"));
					}else{
						logger.error("注册银行会员子账户失败"+pr.getMessage());
						throw new RuntimeException("审核失败:"+pr.getMessage());
					}
					//会员账户表更新见证宝相关信息
					if(sysUserAuthApprovalDao.updateBankAccountInfo(accInfo)>0 && sysUserAuthApprovalDao.updateBankCardUserInfo(params)>0){
						pr.setState(true);
						pr.setMessage("审批成功");
					}else{
						pr.setState(false);
						throw new RuntimeException("审批失败");
					}
				}else{
					//将银行卡会员资料信息设置为驳回状态
					int count=sysUserAuthApprovalDao.updateBankCardUserInfo(params);
					if(count>0){
						pr.setState(true);
						pr.setMessage("驳回成功");
					}else{
						pr.setState(false);
						throw new RuntimeException("驳回失败");
					}
				}
			} catch (Exception e) {
		        pr.setState(false);
		        pr.setMessage(e.getMessage());
		        throw new RuntimeException(e);
		    }
		return pr;
	}

}
