package com.tk.oms.returns.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.oms.returns.dao.ReturnsDao;
import com.tk.oms.returns.dao.UserMbrCardCacheKeyDao;

@Service("UserMbrCardInfoService")
public class UserMbrCardInfoService {
	@Resource
	private ReturnsDao returnsDao;
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private UserMbrCardCacheKeyDao userMbrCardCacheKeyDao;
	/**
	 * 更新会员卡余额
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public synchronized boolean updateUserMbrCardInfo(long userId,Map<String, Object> userMbrCard) throws Exception{
        boolean flag = false;
      //余额是否篡改
		if (this.returnsDao.checkBalance(userId) == 0) {
			throw new RuntimeException("会员卡余额发送篡改,更新会员卡信息失败");
		}
		/**
		 * 重新计算key ; code
		 */
    	Map<String,Object> codeParams = new HashMap<String,Object>();
		codeParams.put("c_user_name",String.valueOf(userId));
		codeParams.put("c_money",userMbrCard.get("CARD_BALANCE"));
		codeParams.put("c_typeid","new");
		String cache_key = memberInfoDao.getUserKey(codeParams);
		codeParams.put("c_user_key",cache_key);
		String card_balance_checkcode = memberInfoDao.getCheck_Code(codeParams);
		
		Map<String,Object> cardKey = new HashMap<String, Object>();
    	cardKey.put("user_name",String.valueOf(userId));
    	cardKey.put("cache_key",cache_key);
		if(userMbrCardCacheKeyDao.update(cardKey)<=0){
			throw new RuntimeException("更新余额校验码的key记录失败");
		}
		userMbrCard.put("card_balance_checkcode", card_balance_checkcode);
		if (this.returnsDao.updateUserMbrCard(userMbrCard) <= 0) {
			throw new RuntimeException("更新会员卡信息失败");
		}
    	flag = true;
    	return flag;
    }
}
