package com.tk.store.finance.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.store.finance.dao.StoreMoneyDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("StoreMoneyService")
public class StoreMoneyService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private StoreMoneyDao storeMoneyDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	
	public GridResult queryStoreMoneyListForPage(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			//数量
			int count = storeMoneyDao.queryStoreMoneyCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeMoneyDao.queryStoreMoneyListForPage(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	/**
	 * 查询账户余额详情
	 * @param request
	 * @return
	 */
	public GridResult queryStoreAccountDetailList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if(StringUtils.isEmpty(params.get("store_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数store_id");
                return gr;
            }
			if(StringUtils.isEmpty(params.get("record_channel"))){
				gr.setState(false);
				gr.setMessage("缺少参数record_channel");
                return gr;
            }
			List<Map<String,Object>> list = storeMoneyDao.queryStoreAccountDetailList(params);
			int count=storeMoneyDao.queryStoreAccountDetailCount(params);
			if (list != null && list.size()>0 ) {
				gr.setMessage("查询成功!");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 *查询收入支出金额
	 * @param request
	 * @return
	 */
	public GridResult queryStoreIncomeSpendMoney(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String,Object> resMap=new HashMap<String,Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("store_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数store_id");
                return gr;
            }
			if(StringUtils.isEmpty(params.get("record_channel"))){
				gr.setState(false);
				gr.setMessage("缺少参数record_channel");
                return gr;
            }
			params.put("record_type", 1);
			resMap.put("income", storeMoneyDao.queryStoreIncomeSpendMoney(params));
			params.put("record_type", 2);
			resMap.put("spend", storeMoneyDao.queryStoreIncomeSpendMoney(params));
			gr.setMessage("查询成功!");
			gr.setObj(resMap);
			gr.setState(true);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 * 查询待结算金额记录
	 * @param request
	 * @return
	 */
	public GridResult queryStoreWaitSettleDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if(StringUtils.isEmpty(params.get("store_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数store_id");
                return gr;
            }
			List<Map<String,Object>> list = storeMoneyDao.queryStoreWaitSettleDetail(params);
			int count=storeMoneyDao.queryStoreWaitSettleDetailCount(params);
			if (list != null && list.size()>0 ) {
				gr.setMessage("查询成功!");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
     * 押金充值
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult storeMoneyDepositRecharge(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(StringUtils.isEmpty(param.get("store_id"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数store_id");
			  return pr;
		  }
		  if(StringUtils.isEmpty(param.get("money"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数money");
			  return pr;
		  }
		  if(storeMoneyDao.insertStoreDepositRecharge(param)>0){
			  pr.setState(true);
			  pr.setMessage("新增成功!");
		  }else{
			  pr.setState(false);
			  pr.setMessage("新增失败!");
		  }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }
    /**
     * 押金充值记录列表
     * @param request
     * @return
     */
    public GridResult storeMoneyDepositRechargeRecord(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			//数量
			int count = storeMoneyDao.queryStoreMoneyDepositRechargeRecordCount(paramMap);
			//列表
			List<Map<String, Object>> list = storeMoneyDao.queryStoreMoneyDepositRechargeRecordList(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
    
    /**
	 * 押金充值审批详情
	 * @param request
	 * @return
	 */
	public GridResult storeMoneyDepositRechargeApprovalDetail(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(StringUtils.isEmpty(params.get("id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
                return gr;
            }
			Map<String,Object> detail = storeMoneyDao.queryStoreMoneyDepositRechargeApprovalDetail(params);
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
     * 押金充值审批
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult storeMoneyDepositRechargeApproval(HttpServletRequest request) throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> param = new HashMap<String,Object>();
  	  try {
		  String json = HttpUtil.getRequestInputStream(request);
		  if(!StringUtils.isEmpty(json)){
			param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
		  }
		  if(StringUtils.isEmpty(param.get("id"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数id");
			  return pr;
		  }
		  if(StringUtils.isEmpty(param.get("state"))){
			  pr.setState(false);
			  pr.setMessage("缺少参数state");
			  return pr;
		  }
		  Map<String,Object> detail=storeMoneyDao.queryStoreMoneyDepositRechargeApprovalDetail(param);
		  if(detail==null){
			  pr.setState(false);
			  pr.setMessage("当前账号有误");
			  return pr;
		  }
		  //店铺账户信息
		  param.put("user_id", detail.get("STORE_ID"));
		  Map<String,Object> storeBankAccount=storeMoneyDao.queryStoreBankAccountForUpdate(param);
		  if(storeBankAccount==null){
			  pr.setState(false);
			  pr.setMessage("当前店铺账号不存在");
			  return pr;
		  }
		  //审批修改对应状态
		  int count=storeMoneyDao.updateStoreDepositRechargeState(param);
		  if(count<0){
			  throw new RuntimeException("审批失败");
		  }
		  //审批通过操作
		  if(Integer.parseInt(param.get("state").toString())==2){
			  //审批成功更新店铺账户余额以及新增收支记录数据
			  Map<String,Object> acc=new HashMap<String,Object>();
			  acc.put("id", detail.get("STORE_ID"));
			  Map<String,Object> userInfo=memberInfoDao.queryMemberInfoById(acc);
			  acc.put("user_id", detail.get("STORE_ID"));
			  acc.put("recharge_money", detail.get("MONEY"));//充值金额
			  acc.put("goods_deposit_balance", storeBankAccount.get("GOODS_DEPOSIT_BALANCE"));//原账户货品押金余额
			  acc.put("store_goods_deposit_balance", storeBankAccount.get("STORE_GOODS_DEPOSIT_BALANCE"));//原账户店铺货品押金余额
			  acc.put("store_goods_deposit_rate", storeBankAccount.get("STORE_GOODS_DEPOSIT_RATE"));//店铺-货品押金比例
			  acc.put("collect_user_id", detail.get("STORE_ID"));
			  acc.put("collect_user_manager_name", userInfo.get("USER_MANAGE_NAME"));
			  acc.put("collect_store_name", userInfo.get("USER_CONTROL_STORE_NAME"));
			  acc.put("collect_user_partner_id", userInfo.get("PARTNER_USER_ID"));
			  acc.put("account_balance", storeBankAccount.get("ACCOUNT_BALANCE"));//账号余额
			  acc.put("goods_deposit", storeBankAccount.get("GOODS_DEPOSIT"));//货品押金
			  acc.put("store_goods_deposit_balance", storeBankAccount.get("STORE_GOODS_DEPOSIT_BALANCE"));//店铺-货品押金余额
			  acc.put("store_deposit", storeBankAccount.get("STORE_DEPOSIT"));//店铺-保证金
			  acc.put("shelf_deposit", storeBankAccount.get("SHELF_DEPOSIT"));//货架押金
			  BigDecimal tradeCommissionRate = (BigDecimal)storeBankAccount.get("STORE_GOODS_DEPOSIT_RATE");//店铺-货品押金比例
			  BigDecimal money = (BigDecimal)detail.get("MONEY");//充值金额
			  acc.put("store_goods_deposit",  money.multiply(tradeCommissionRate));//店铺押金金额
			  
			  Map<String,Object> codeParams=new HashMap<String,Object>();
			  codeParams.put("user_id", detail.get("STORE_ID"));
			  codeParams.put("c_user_type", "old");
			  //获取用户key
			  String user_key = storeMoneyDao.queryUserKey(codeParams);
			  codeParams.put("user_key", user_key);
			  codeParams.put("goods_deposit_balance", storeBankAccount.get("GOODS_DEPOSIT_BALANCE"));
			  codeParams.put("store_goods_deposit_balance", storeBankAccount.get("STORE_GOODS_DEPOSIT_BALANCE"));
			  //获取授信校验码
			  Map<String,Object> resCode= storeMoneyDao.getCheck_Code(codeParams);
			  if(!storeBankAccount.get("GOODS_DEPOSIT_BAL_CHECKCODE").equals(resCode.get("GOODS_DEPOSIT_BAL_CHECKCODE"))||!storeBankAccount.get("STORE_GOODS_DPST_BAL_CHECKCODE").equals(resCode.get("STORE_GOODS_DPST_BAL_CHECKCODE"))){
				  pr.setState(false);
				  pr.setMessage("余额发生篡改，无法完成当前操作!");
				  return pr;
			  }
			  
			  //更新账户余额和验证码
			  codeParams.put("c_user_type", "new");
			  //获取用户key
			  String newKey= storeMoneyDao.queryUserKey(codeParams);
			  codeParams.put("c_user_key", newKey);
			  acc.put("c_user_key", newKey);
			  if(storeMoneyDao.updateStoreBankAccountBalance(acc)<=0){
				  throw new RuntimeException("更新押金余额失败");
			  }
			  //更新用户账户key
			  if(storeMoneyDao.updateSysUserCacheKey(codeParams)<=0){
			      throw new RuntimeException("更新用户账户key错误");
			  }
			  //新增充值记录
			  if(storeMoneyDao.insertStoreGoodsDepositAccountRecord(acc)>0 && storeMoneyDao.insertGoodsDepositAccountRecord(acc)>0){
				  pr.setState(true);
				  pr.setMessage("审批成功");
			  }else{
				  throw new RuntimeException("审批失败");
			  }
		  }else{
			  pr.setState(true);
			  pr.setMessage("审批成功");
		  }
  	} catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
  	  return pr;
   }

}
