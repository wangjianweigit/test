package com.tk.oms.member.service;

import com.tk.oms.basicinfo.dao.StoreInfoDao;
import com.tk.oms.member.dao.MemberInfoDao;
import com.tk.oms.stationed.dao.StationedDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("memberService")
public class MemberInfoService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private MemberInfoDao memberInfoDao;
	@Resource
	private StoreInfoDao storeInfoDao;
	@Resource
	private SysUserInfoDao sysUserInfoDao;
	@Resource
    private StationedDao stationedDao;
	@Value("${pay_service_url}")
	private String pay_service_url;//
	@Value("${jdbc_user}")
	private String jdbc_user;//
	@Value("${store_service_url}")
    private String store_service_url;
	/**OA服务地址*/
    @Value("${oa_service_url}")
    private String oa_service_url;
    @Value("${sms_service_url}")
    private String sms_service_url;// --消息提醒
	/**
	 * 添加会员
	 * @param request
	 * @return
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult add(HttpServletRequest request) throws Exception{		
		ProcessResult pr = new ProcessResult();

	    Map<String, Object> user = new HashMap<String, Object>();
		String json = HttpUtil.getRequestInputStream(request);
		user = (Map<String, Object>) Transform.GetPacket(json,Map.class);
		// 加密
		byte[] encryptResult = Crypt.encrypt(user.get("user_pwd").toString(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.USER_PWD_KEY));
		// 编码
		user.put("user_pwd", Base64.encode(encryptResult));
		if(Integer.parseInt(user.get("user_company_type").toString())==4){
			user.put("shop_photo", user.get("business_licence_imgurl"));
		}else{
			user.put("user_business_licence_imgurl", user.get("business_licence_imgurl"));
		}
		//获取业务员名称
		String  referee_user_realname="";
		if(!StringUtils.isEmpty(user.get("referee_user_id"))){
			referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("referee_user_id").toString()));
		}
		//获取门店名称
		String  store_name="";
		if(!StringUtils.isEmpty(user.get("store_id"))){
			 store_name=storeInfoDao.queryStoreNameById(Integer.parseInt(user.get("store_id").toString()));
		}
		//获取业务经理名称
		String market_supervision_user_realna=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("market_supervision_user_id").toString()));
		user.put("referee_user_realname", referee_user_realname);
		user.put("market_supervision_user_realna", market_supervision_user_realna);
		user.put("store_name", store_name);
		user.put("user_resource", "2");
		
		int ln = memberInfoDao.queryMemberInfoRecordByLoginName(user.get("login_name").toString());
		int lp =memberInfoDao.queryMemberInfoRecordByMobilePhone(user);
		int lc =memberInfoDao.queryMemberInfoRecordByUserManageCardId(user);
		if(ln==0&&lp==0&&lc==0){
			//如果缺少大区数据，则根据省份查询
			if(null==user.get("user_company_address_max")&&Integer.parseInt(user.get("province").toString())!=0){
				Map<String, Object> dr = memberInfoDao.queryDicRegionById(Integer.parseInt(user.get("province").toString()));
				user.put("user_company_address_max", Integer.parseInt(dr.get("PARENT_ID").toString()));
			}
			user.put("user_state", 0);
			if(memberInfoDao.insertUserInfo(user)>0) {
				long user_id = StringUtils.isEmpty(user.get("id"))?0:Long.parseLong(user.get("id").toString());


				//记录会员创建日志
				Map<String,Object> logMap=new HashMap<String,Object>();
				logMap.put("USER_TYPE", 3);
				logMap.put("OPERATE_ID", 1);
				logMap.put("REMARK", "创建【注册】");
				logMap.put("CREATE_USER_ID", user.get("public_user_id"));
				logMap.put("USER_NAME", user.get("login_name"));
				logMap.put("USER_REALNAME", user.get("user_manage_name"));
				memberInfoDao.insertUserOperationLog(logMap);
				pr.setState(true);
				pr.setMessage("添加成功");
				pr.setObj(user);

				/***
				 * 注册会员时，在会员审批表中同时插入记录，标记为：待审核，站点等信息与【游客】相同
				 * 同时增加账号等信息
				 */
				user.put("user_id", user_id);
				user.put("record_state", 0);
				user.put("description", "运营总后台注册会员");
				if(memberInfoDao.insertMemberInfoRecord(user)<=0) {
					throw new RuntimeException("添加失败");
				}
				//添加账号表
				int account_count = memberInfoDao.queryBankAccountByUserId(user_id);
				user.put("ID", user_id);
				if(account_count<=0){
					//创建用户帐户信息
					if(addUserAccount(user)>0){
						logger.info("审核通过，创建用户帐户成功");
					}else{
						logger.error("审核通过，创建用户帐户失败");
					}
				}
				/**
				if(addUserAddress(user)>0){
					logger.info("审核通过，插入地址信息失成功");
				}else{
					logger.error("审核通过，插入地址信息失败");
				}
				**/

			}else{
				throw new RuntimeException("添加失败");
			}
		}else if(ln!=0){
			throw new RuntimeException("添加失败,用户名已存在");
		}else if(lp!=0){
			throw new RuntimeException("添加失败,手机号码已存在");
		}else if(lc!=0){
			throw new RuntimeException("添加失败,身份证号已存在");
		}
		return pr;
	}
	
	
	/**
	 * 重置物流公司信息，先清除，再插入
	 * @param user
     */
	private int resetLogisticsCompany(Map<String, Object> user){
		//添加物流公司信息
		int l = 0;
		if(!StringUtils.isEmpty(user.get("logistics_compamy_ids"))){
			//清除物流公司信息
			memberInfoDao.deleteByUserName(user.get("id").toString());
			List <Map<String, Object>> clist = new ArrayList<Map<String, Object>>();
			String logistics=user.get("logistics_compamy_ids").toString();
			for(String id:logistics.split(",")){
				Map<String, Object> ulc = new HashMap<String, Object>();
				ulc.put("user_name", user.get("id"));
				ulc.put("logistics_company_id",Integer.parseInt(id.trim()));
				clist.add(ulc);
			}
			l = memberInfoDao.batchInsert(clist);
		}
		return l;
	}
	
	
	/**
	 * 会员待审批列表
	 * @param request
	 * @return
	 */
	public GridResult approvalList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			//界面根据会员状态的筛选处理
			if(!StringUtils.isEmpty(params.get("state"))){
				String state = "";
				if(params.get("state") instanceof ArrayList){
					state = StringUtils.arrayToDelimitedString(((List<String>)params.get("state")).toArray(), ",");
				}else{
					state= params.get("state").toString();
				}
				//当筛选待审核时，预审通过及临时用户都属于待审核范围内
				if(state.indexOf("0") != -1){
					state +=",4,6"; 
				}
				params.put("state",state.split(","));
			}
			List<Map<String,Object>> userList = null;
			int userCount=memberInfoDao.queryMemberInfoApplyCount(params);
			userList = memberInfoDao.queryMemberInfoApplyPageBy(params);
			if (userList != null && userList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(userList);
				gr.setTotal(userCount);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 * 会员管理列表
	 * @param request
	 * @return
	 */
	public GridResult manageList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(params);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
				params.put("state",(params.get("state")+"").split(","));
			}
			if((!StringUtils.isEmpty(params.get("user_type")))&&params.get("user_type") instanceof String){
				params.put("user_type",(params.get("user_type")+"").split(","));
			}
			List<Map<String,Object>> userList = null;
			int userCount=memberInfoDao.queryMemberInfoCount(params);
			userList = memberInfoDao.queryMemberInfoPageBy(params);
			if (userList != null && userList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(userList);
				gr.setTotal(userCount);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	
	/**
	 * 查看会员详情列表(点击查看查询)
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberDetailList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			Map<String,Object> userList = null;
			userList = memberInfoDao.queryMemberDetailByUserName(params);
			pr.setState(true);
			pr.setMessage("获取信息数据成功");
			pr.setObj(userList);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	
	
	/**
	 * 暂不审核
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult updateUserStateToPause(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                pr.setState(false);
                pr.setMessage("缺少参数user_name");
                return pr;
            }
//             if(memberInfoDao.updateMemberStateToPause(params) > 0){
//            	pr.setState(true);
//     	        pr.setMessage("更新成功");
//             }else{
//            	 pr.setState(false);
//     	         pr.setMessage("更新失败");
//             }
	    } catch (Exception e) {
	        pr.setState(false);
	        pr.setMessage(e.getMessage());
	        logger.info(e);
	    }
		return pr;
	}
	
	
	/**
	 * 查询会员详情
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberInfoByUserName(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少用户名");
				return pr;
			}
			Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
			if(null==userInfo){
				pr.setObj("");
				pr.setState(true);
				pr.setMessage("获取会员信息成功");
				return pr;
			}
			pr.setObj(userInfo);
			pr.setState(true);
			pr.setMessage("获取会员信息成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	
	/**
	 * 待审批编辑会员详情
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberInfoRecordById(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_name"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少用户名");
				return pr;
			}
			Map<String,Object> userInfo = memberInfoDao.queryMemberInfoRecordById(params);
			pr.setState(true);
			pr.setMessage("获取会员信息成功");
			pr.setObj(userInfo);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 审核会员资料 审核通过插入tbl_user_info
	 * 与此同时，需要修改部分信息，创建帐户、创建收货地址
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult approvalUserInfo(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
        Map<String, Object> user = new HashMap<String, Object>();
			String json = HttpUtil.getRequestInputStream(request);
			user = (Map<String, Object>) Transform.GetPacket(json,Map.class);
			if(user==null||StringUtils.isEmpty(user.get("user_name"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少用户名");
				return pr;
			}
			Map<String,Object> userInfo = memberInfoDao.queryMemberInfoRecordById(user);
			if(!"0".equals(userInfo.get("RECORD_STATE").toString())) {
				pr.setState(false);
				pr.setMessage("用户资料已发生变更，请刷新列表重试");
				return pr;
			}
			userInfo.put("USER_NAME", userInfo.get("USER_ID"));
			int num=memberInfoDao.queryMemberInfoByMobilePhone(userInfo);
			if(num>0){
				pr.setState(false);
				pr.setMessage("该手机号已经被使用");
				return pr;
			}
			if(StringUtils.isEmpty(userInfo.get("MARKET_SUPERVISION_USER_ID"))||StringUtils.isEmpty(userInfo.get("MARKET_SUPERVISION_USER_REALNA"))){
				pr.setState(false);
				pr.setMessage("用户资料不全，请补充【业务经理】再操作");
				return pr;
			}
			if(StringUtils.isEmpty(userInfo.get("SITE_ID"))){
				pr.setState(false);
				pr.setMessage("用户资料不全，请补充【站点】再操作");
				return pr;
			}
			if("1".equals(userInfo.get("USER_STATE")+"")){
				pr.setState(false);
				pr.setMessage("已经审核通过的会员不允许审核");
				return pr;
			}
			if ("6".equals(userInfo.get("USER_STATE") + "")) {
				pr.setState(false);
				pr.setMessage("请先使用编辑完善用户资料后，才能审核通过");
				return pr;
			}
			//判断该用户会员门店管理表是否已经存在数据
			int userStoreCount=memberInfoDao.queryUserStoreAddressByUserId(Integer.parseInt(user.get("user_name").toString()));
			if(userStoreCount==0){
				//没有数据审批后进行新增
				Map<String,Object> memberStoreAddress=new HashMap<String,Object>();
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String approval_date=format.format(date);
				memberStoreAddress.put("user_id", userInfo.get("USER_NAME"));
				memberStoreAddress.put("login_name", userInfo.get("LOGIN_NAME"));
				memberStoreAddress.put("user_manage_name", userInfo.get("USER_MANAGE_NAME"));
				memberStoreAddress.put("longitude", userInfo.get("USER_LONGITUDE"));
				memberStoreAddress.put("latitude", userInfo.get("USER_LATITUDE"));
				memberStoreAddress.put("public_user_id",user.get("public_user_id"));
				memberStoreAddress.put("approval_state","1");
				memberStoreAddress.put("user_store_resource", "1");
				memberStoreAddress.put("approval_date", approval_date);
				memberStoreAddress.put("approval_user_id", user.get("public_user_id"));
				//当会员经营类型为4即网络营销时封装一下参数
				if(Integer.parseInt(userInfo.get("USER_COMPANY_TYPE").toString())==4){
					memberStoreAddress.put("store_name", userInfo.get("SHOP_NAME"));
					memberStoreAddress.put("store_address", userInfo.get("USER_COMPANY_ADDRESS_DEAILS"));
					memberStoreAddress.put("management_address", userInfo.get("USER_COMPANY_LOCATION_ADDRESS"));
					memberStoreAddress.put("province", userInfo.get("USER_COMPANY_ADDRESS_PROVINCE"));
					memberStoreAddress.put("city", userInfo.get("USER_COMPANY_ADDRESS_CITY"));
					memberStoreAddress.put("area", userInfo.get("USER_COMPANY_ADDRESS_COUNTY"));
				}else{
					//除网络营销外的其它经营类型封装一以下参数
					memberStoreAddress.put("store_name", userInfo.get("USER_CONTROL_STORE_NAME"));
					memberStoreAddress.put("store_address", userInfo.get("USER_STORE_ADDRESS_DEAILS"));
					memberStoreAddress.put("management_address", userInfo.get("USER_STORE_LOCATION_ADDRESS"));
					memberStoreAddress.put("province", userInfo.get("USER_STORE_ADDRESS_PROVINCE"));
					memberStoreAddress.put("city", userInfo.get("USER_STORE_ADDRESS_CITY"));
					memberStoreAddress.put("area", userInfo.get("USER_STORE_ADDRESS_COUNTY"));
				}
				memberInfoDao.insertMemberStore(memberStoreAddress);
				//记录配置会员门店管理新增日志
	    		Map<String,Object> memberStorelogMap=new HashMap<String,Object>();
	    		memberStorelogMap.put("USER_TYPE", 3);
	    		memberStorelogMap.put("OPERATE_ID", 3);
	    		memberStorelogMap.put("REMARK", "配置【会员门店管理】新增");
	    		memberStorelogMap.put("CREATE_USER_ID", user.get("public_user_id"));
	    		memberStorelogMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
	    		memberStorelogMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
	    		memberInfoDao.insertUserOperationLog(memberStorelogMap);
			}
			//设置用户状态为 “审核通过”
			user.put("user_state",1);
			user.put("record_state",1);
			user.put("id", user.get("user_name"));
			Map<String, Object> paramUser = new HashMap<String, Object>();
			paramUser.put("id", user.get("user_name"));
			Map<String,Object> params=new HashMap<String,Object>();
			Map<String,Object> recordInfoObj=new HashMap<String,Object>();
			if(memberInfoDao.updateMemberInfoRecordState(user)>0){
				recordInfoObj = memberInfoDao.queryMemberInfoRecordById(user);
				params.put("id", user.get("user_name"));
				params.put("USER_STATE", 1);
				params.put("USER_LOGISTICS_TEMPLATE_ID",recordInfoObj.get("USER_LOGISTICS_TEMPLATE_ID"));
				params.put("SITE_ID",recordInfoObj.get("SITE_ID"));
				memberInfoDao.updateMemberInfo(params);
				/**
				 * 判断用户售后地址信息是否存在，如果不存在，则以当前的店铺地址作为其默认收货地址
				 * reid 2019.11.13 
				 */
				if(memberInfoDao.countUserAddress(Long.parseLong(user.get("user_name").toString()))<=0) {
					//创建用户收货信息记录
					memberInfoDao.insertUserAddressByUserId(Long.parseLong(user.get("user_name").toString()));
				}
/**
 * 				联运相关代码暂时注释掉    reid  2019.11.11
				if(Integer.parseInt(userInfo.get("USER_TYPE").toString())==2){
					//门店会员初始化联营门店账号信息
					Map<String,Object> codeParams = new HashMap<String,Object>();
					codeParams.put("c_user_name", user_id);
					codeParams.put("c_money",0);
					codeParams.put("c_typeid","new");
					codeParams.put("c_user_type","3");
					String key = memberInfoDao.getUserKey(codeParams);
					codeParams.put("c_user_key",key);
					String code = memberInfoDao.getCheck_Code(codeParams);
					Map<String,Object> account = new HashMap<String,Object>();
					account.put("user_id", user_id);
					account.put("account_balance_checkcode",code);
					account.put("goods_deposit_bal_checkcode",code);
					account.put("store_goods_dpst_bal_checkcode",code);
					account.put("store_deposit_checkcode",code);
					account.put("shelf_deposit_checkcode",code);
					Map<String,Object> uck = new HashMap<String,Object>();
					uck.put("user_name", user_id);
					uck.put("cache_key", key);
					if(memberInfoDao.insertStoreUserCacheKey(uck)>0){
						int count =memberInfoDao.insertStoreBankAccount(account);
						if(count<0){
							throw new RuntimeException("创建联营门店账号信息失败");
						}
					}
					//商家审核通过默认初始化商家门店数据
					if(memberInfoDao.insertStoreUserManage(user.get("user_name").toString())<0){
						throw new RuntimeException("创建商家门店失败");
					}
					Map<String,Object> sendParam=new HashMap<String,Object>();
					sendParam.put("USER_NAME", param.get("USER_NAME"));
					sendParam.put("PARTNER_USER_ID", param.get("PARTNER_USER_ID"));
					sendParam.put("PARTNER_USER_REALNA", param.get("PARTNER_USER_REALNA"));
					sendParam.put("USER_MANAGE_NAME", param.get("USER_MANAGE_NAME"));
					sendParam.put("USER_MANAGE_MOBILEPHONE", param.get("USER_MANAGE_MOBILEPHONE"));
					sendParam.put("COMMISSION_RATE", param.get("COMMISSION_RATE"));
					Map<String, Object> resPr=(Map<String, Object>)this.queryForPost(sendParam,store_service_url+"agent/Lyinit/addAgentAccount");
					if (Integer.parseInt(resPr.get("state").toString()) != 1) {
						throw new RuntimeException(resPr.get("message")+"");
					}
					//更新本地经销商ID
					sendParam.put("AGENT_ID", resPr.get("data"));
					if(memberInfoDao.updateAgentId(sendParam)<1){
						throw new RuntimeException("更新本地经销商失败");
					}
				}
 * */
				//将会员数据同步OA
				Map<String,Object> memberInfoMap=memberInfoDao.queryMemberInfoById(paramUser);
				memberInfoMap.put("operationType","add");
//				调用oa同步接口
				 ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + "/sys_dictionary/sync_formal_customer", memberInfoMap);
				 if (!oAPr.getState()) {
					 throw new RuntimeException("OA系统会员数据同步失败");
				 }
           		//当用户类型为普通会员时发送短信或微信提醒
             	if(Integer.parseInt(memberInfoMap.get("USER_TYPE").toString())==1){
             	//记录会员审核日志
		    		Map<String,Object> logMap=new HashMap<String,Object>();
		    		logMap.put("USER_TYPE", 3);
		    		logMap.put("OPERATE_ID", 1);
		    		logMap.put("REMARK", "创建【审核】");
		    		logMap.put("CREATE_USER_ID", user.get("public_user_id"));
		    		logMap.put("USER_NAME", memberInfoMap.get("LOGIN_NAME"));
		    		logMap.put("USER_REALNAME", memberInfoMap.get("USER_MANAGE_NAME"));
		    		memberInfoDao.insertUserOperationLog(logMap);

	                Map<String,Object> smsMap = new HashMap<String,Object>();
					smsMap.put("openid", memberInfoMap.get("OPENID"));							//微信OPENID
					smsMap.put("type", "19");								//消息类型
					smsMap.put("mobile", memberInfoMap.get("USER_MANAGE_MOBILEPHONE"));//手机号码
					Map<String,Object> sendParam = new HashMap<String,Object>();
					sendParam.put("login_name", memberInfoMap.get("LOGIN_NAME"));			//账号
					smsMap.put("param", sendParam);
					try {
						ProcessResult sendPr = (ProcessResult) this.queryForPostSendNotice(smsMap,sms_service_url);
						if(!sendPr.getState()){
							logger.error(sendPr.getMessage());
						}
					}catch (Exception e) {
			            throw new RuntimeException("审核通过失败!(发送通知失败)");
					}
             }
				pr.setState(true);
				pr.setMessage("审核通过成功");
			}else{
				pr.setState(false);
				pr.setMessage("审核通过失败");
			}
		return pr;
	}
	
	/**
	 * 审核通过，添加用户帐户
	 * @param user
	 * @return
     */
	@Transactional
	private int addUserAccount(Map<String, Object> user) throws Exception{
		//user_name直接使用id，如果为空，则直接赋值  20162002
		user.put("user_name", user.get("ID"));
		
		//创建用户账户信息
		Map<String,Object> codeParams = new HashMap<String,Object>();
		codeParams.put("c_user_name",user.get("ID"));
		codeParams.put("c_money",0);
		codeParams.put("c_typeid","new");
		codeParams.put("c_user_type","1");
		String key = memberInfoDao.getUserKey(codeParams);
		codeParams.put("c_user_key",key);
		String code = memberInfoDao.getCheck_Code(codeParams);
		Map<String,Object> account = new HashMap<String,Object>();
		account.put("user_id", user.get("ID"));
		account.put("account_balance_checkcode", code);//余额校验码
		account.put("credit_checkcode", code);//授信校验码
		account.put("bank_account", "");
		account.put("sub_merchant_id", "");
		account.put("user_type", "1");
		account.put("credit_money", 0);
		account.put("credit_money_use", 0);
		account.put("credit_money_balance", 0);
		account.put("deposit_money", 0);
		account.put("account_balance", 0);
		account.put("score", 6001);

		//保存用户key
		Map<String,Object> uck = new HashMap<String,Object>();
		uck.put("user_name", user.get("ID"));
		uck.put("cache_key", key);
		if(memberInfoDao.insertCacheKey(uck)>0){
			return memberInfoDao.insertBankAccount(account);
		}
		return 0;
	}
	
	/**
	 * 审核通过，将用户的详细地址作为收货地址保存
	 * @param user
	 * @return
     */
	@Deprecated
	private int addUserAddress(Map<String,Object> user){
		//创建用户收货信息记录
		Map<String,Object> address = new HashMap<String,Object>();
		address.put("user_id", user.get("ID"));
		address.put("receiving_name", user.get("USER_MANAGE_NAME"));
		address.put("receiving_address_province_id", user.get("USER_COMPANY_ADDRESS_PROVINCE"));
		address.put("receiving_address_city_id", user.get("USER_COMPANY_ADDRESS_CITY"));
		address.put("receiving_address_county_id", user.get("USER_COMPANY_ADDRESS_COUNTY"));
		address.put("is_default", 0);
		address.put("receiving_address_details", user.get("USER_COMPANY_ADDRESS_DEAILS"));
		address.put("receiving_phone", user.get("USER_MANAGE_MOBILEPHONE"));
		address.put("address_type", 1);
		address.put("is_delete", 0);
		return memberInfoDao.insertUserAddress(address);
	}
	
	
	/**
	 * 启用或禁用
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult disableUserInfo(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			Map<String,Object> memberStateLogsMap = new HashMap<String,Object>();
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("state"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少必要参数");
				return pr;
			}
			if(!("1".equals(params.get("state")) || "2".equals(params.get("state")))){
				pr.setState(false);
				pr.setMessage("会员状态有误");
				return pr;
			}
			memberStateLogsMap.put("public_user_id", params.get("public_user_id"));
			memberStateLogsMap.put("user_id", params.get("id"));
			memberStateLogsMap.put("remark", params.get("remark"));
			memberStateLogsMap.put("state",params.get("state"));
			if(StringUtils.isEmpty(params.get("voucher_img_url"))){
				memberStateLogsMap.put("voucher_img_url", "");
			}else{
				memberStateLogsMap.put("voucher_img_url", params.get("voucher_img_url"));
			}
			if("1".equals(params.get("state"))){
				memberStateLogsMap.put("content", "启用会员");
			}
			if("2".equals(params.get("state"))){
				memberStateLogsMap.put("content", "禁用会员");
				//更新控货开关
				memberInfoDao.updateUserStoreAddress(params);
				//删除控货明细表数据
				memberInfoDao.deleteProductControlDetail(params);
				//删除控货明细申请表数据
				memberInfoDao.deleteProductControlDetApply(params);
				//删除控货文件表数据
				memberInfoDao.deleteProductControlAtt(params);
				//删除控货表数据
				memberInfoDao.deleteProductControl(params);
				//删除控货申请表数据
				memberInfoDao.deleteProductControlApply(params);
			}
			if(memberInfoDao.disableUserInfoById(params)<=0){
				pr.setState(false);
				throw new RuntimeException("操作失败");
			}
			//记录操作日志
			if(memberInfoDao.insertMemberStateLogs(memberStateLogsMap)<=0){
				pr.setState(false);
				throw new RuntimeException("记录日志失败");
			}
			//将会员数据同步OA
			Map<String,Object> memberInfoMap=new HashMap<String,Object>();
			memberInfoMap.put("operationType","edit");
			memberInfoMap.put("ID", params.get("id"));
			memberInfoMap.put("USER_STATE", params.get("state"));
			//调用oa同步接口
            ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + "/sys_dictionary/sync_formal_customer", memberInfoMap);
            if (!oAPr.getState()) {
                throw new RuntimeException("OA系统会员数据同步失败");
            }
            //记录会员停用启用日志
            Map<String,Object> memberDetail=memberInfoDao.queryMemberInfoById(params);
    		Map<String,Object> logMap=new HashMap<String,Object>();
    		logMap.put("USER_TYPE", 3);
    		logMap.put("OPERATE_ID", 3);
    		logMap.put("REMARK", "配置【停用启用】");
    		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
    		logMap.put("USER_NAME", memberDetail.get("LOGIN_NAME"));
    		logMap.put("USER_REALNAME", memberDetail.get("USER_MANAGE_NAME"));
    		memberInfoDao.insertUserOperationLog(logMap);
			pr.setState(true);
			pr.setMessage("操作成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	/**
	 * 批量启用或禁用
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult multiDisable(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("ids"))||StringUtils.isEmpty(params.get("user_state"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少用户名");
				return pr;
			}
			List<Integer> ids = new ArrayList<Integer>();
			for(String id:params.get("ids").toString().split(",")){
				ids.add(Integer.parseInt(id));
			}
			params.put("ids",ids);
			if(memberInfoDao.multiDisableUserInfoByUserId(params)>0){
				pr.setState(true);
				pr.setMessage("操作成功");
			}else{
				pr.setState(false);
				pr.setMessage("操作失败");
			}
		} catch (Exception e) {
			pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
		}
		return pr;
	}
	
	/**
	 * 更新会员积分
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult updateBankAccountScore(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("score"))||StringUtils.isEmpty(params.get("discount"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}

			//获取会员信息
			Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
			if(StringUtils.isEmpty(userInfo)){
				pr.setState(false);
				pr.setMessage("未找到会员信息");
				return pr;
			}

			Map<String,Object> userAccount = new HashMap<String,Object>();
			userAccount.put("user_id", params.get("id"));
			userAccount.put("score", Float.parseFloat(params.get("score").toString()));
			userAccount.put("discount", params.get("discount"));

			if(memberInfoDao.updateBankAccountByUserId(userAccount) > 0){
				if(memberInfoDao.updateUserInfoDiscount(userAccount)>0){
					//记录会员初始化会员积分日志
		    		Map<String,Object> logMap=new HashMap<String,Object>();
		    		logMap.put("USER_TYPE", 3);
		    		logMap.put("OPERATE_ID", 3);
		    		logMap.put("REMARK", "配置【初始化会员积分】");
		    		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
		    		logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
		    		logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
		    		memberInfoDao.insertUserOperationLog(logMap);
					pr.setState(true);
					pr.setMessage("初始化积分成功");
				}
			}else{
				pr.setState(false);
				pr.setMessage("初始化积分失败");
			}
		} catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}

	/**
	 * 查询会员积分
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberScore(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_name"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}

			//获取会员积分
			int score = memberInfoDao.queryScoreByUserId(Long.parseLong(params.get("user_name").toString()));

			pr.setState(true);
			pr.setMessage("获取积分成功");
			pr.setObj(score);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	
	/**
	 * 查询代发等级
	 * @param request
	 * @return
	 */
	public ProcessResult queryMemberIssuingGrade(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_name"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}

			//获取会员积分
			Map<String,Object> issuingGrade = memberInfoDao.queryIssuingGradeByUserId(Long.parseLong(params.get("user_name").toString()));

			pr.setState(true);
			pr.setMessage("获取代发等级成功");
			pr.setObj(issuingGrade);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	/**
	 * 更新会员代发货等级
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult updateUserIssuingGrade(HttpServletRequest request)throws Exception {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("issuing_grade_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}

			//获取会员信息
			Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
			if(null==userInfo.get("USER_NAME")){
				pr.setState(false);
				pr.setMessage("未找到会员信息");
				return pr;
			}
			userInfo.put("issuing_grade_id", Long.parseLong(params.get("issuing_grade_id").toString()));
			userInfo.put("id", params.get("id").toString());

			if(memberInfoDao.updateMemberIssuingGrade(userInfo) > 0){
				//记录会员代发等级设置日志
	    		Map<String,Object> logMap=new HashMap<String,Object>();
	    		logMap.put("USER_TYPE", 3);
	    		logMap.put("OPERATE_ID", 3);
	    		logMap.put("REMARK", "配置【代发设置】");
	    		logMap.put("CREATE_USER_ID", params.get("public_user_id"));
	    		logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
	    		logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
	    		memberInfoDao.insertUserOperationLog(logMap);
				pr.setState(true);
				pr.setMessage("设置代发等级成功");
			}else{
				pr.setState(false);
				pr.setMessage("设置代发等级失败");
			}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	
	
	/**
	 * 新增平台会员申请成为经销商申请记录
	 * @param request
	 * @return
	 */
	public ProcessResult addUserAgentApply(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_name"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}
			int count = memberInfoDao.addUserAgentApply(params);

			pr.setState(true);
			pr.setMessage("新增平台会员申请成为经销商申请记录成功");
			pr.setObj(count);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	
	/**
	 * 平台会员申请成为经销商申请记录审核
	 * @param request
	 * @return
	 */
	public ProcessResult userAgentApplyApproval(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = null;
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params==null||StringUtils.isEmpty(params.get("user_name"))||StringUtils.isEmpty(params.get("approval_user_id"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少参数");
				return pr;
			}
			int count = memberInfoDao.updateUserAgentApply(params);

			pr.setState(true);
			pr.setMessage("修改平台会员申请成为经销商申请记录成功");
			pr.setObj(count);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 * 编辑会员
	 * @param request
	 * @return
     */
	@Transactional
	public ProcessResult editMember(HttpServletRequest request) throws Exception{
	    Map<String,Object> user = null;
	    ProcessResult pr = new ProcessResult();
	    try{
			String json = HttpUtil.getRequestInputStream(request);
			user = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			long public_user_id = Long.parseLong(user.get("public_user_id").toString());
			int count = memberInfoDao.queryMemberInfoByMobilePhone(user);
			if(count>0){
				pr.setState(false);
				pr.setMessage("该手机号已经被注册");
				return pr;
			}
			if(!StringUtils.isEmpty(user.get("USER_MANAGE_CARDID"))){
				count=memberInfoDao.queryMemberInfoByUserManageCardId(user);
				if(count>0){
					pr.setState(false);
					pr.setMessage("该身份证号已经被注册");
					return pr;
				}
			}
			if(Integer.parseInt(user.get("USER_COMPANY_TYPE").toString())==4){
				user.put("shop_photo", user.get("business_licence_imgurl"));
			}else{
				user.put("user_business_licence_imgurl", user.get("business_licence_imgurl"));
			}
			//获取业务员名称
			String  referee_user_realname="";
			if(!StringUtils.isEmpty(user.get("REFEREE_USER_ID"))){
				referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("REFEREE_USER_ID").toString()));
			}
			//获取门店名称
			String  store_name="";
			if(!StringUtils.isEmpty(user.get("STORE_ID"))){
				store_name=storeInfoDao.queryStoreNameById(Integer.parseInt(user.get("STORE_ID").toString()));
			}
			//获取合作商名称
			String partner_user_realna="";
			if(!StringUtils.isEmpty(user.get("PARTNER_USER_ID"))){
				partner_user_realna=referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("PARTNER_USER_ID").toString()));
			}
			//获取督导名称
			String supervisor_user_realna="";
			if(!StringUtils.isEmpty(user.get("SUPERVISOR_USER_ID"))){
				supervisor_user_realna=referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("SUPERVISOR_USER_ID").toString()));
			}
			//获取业务经理名称
			String market_supervision_user_realna=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("MARKET_SUPERVISION_USER_ID").toString()));
			user.put("REFEREE_USER_REALNAME", referee_user_realname);
			user.put("MARKET_SUPERVISION_USER_REALNA", market_supervision_user_realna);
			user.put("PARTNER_USER_REALNA", partner_user_realna);
			user.put("SUPERVISOR_USER_REALNA", supervisor_user_realna);
			user.put("STORE_NAME", store_name);
			//获取佣金比例
			if(!StringUtils.isEmpty(user.get("COMMISSION_RATE"))){
				user.put("COMMISSION_RATE", Double.parseDouble(user.get("COMMISSION_RATE").toString())/100);
			}
			if(memberInfoDao.updateMemberInfo(user)>0){
				user.put("record_state", 1);
				long user_id = Long.parseLong(user.get("id").toString());
				user.put("user_id", user_id);
				user.put("description", "运营总后台审核后编辑会员");
				memberInfoDao.insertMemberInfoRecordByUser(user);
				user.put("id", user_id);
				//查询用户帐户信息
//				Integer id=Integer.parseInt(user.get("id").toString());
				user = memberInfoDao.queryMemberInfoById(user);
//				if(Integer.parseInt(user.get("USER_TYPE").toString())==2){
//					Map<String,Object> sendParam=new HashMap<String,Object>();
//					sendParam.put("AGENT_ID", user.get("ID"));
//					sendParam.put("COMMISSION_RATE", user.get("COMMISSION_RATE"));
//					sendParam.put("JOINT_NAME", user.get("USER_MANAGE_NAME"));
//					sendParam.put("COOPER_ID", user.get("PARTNER_USER_ID"));
//					sendParam.put("COOPER_NAME", user.get("PARTNER_USER_REALNA"));
//					Map<String, Object> resPr=(Map<String, Object>)this.queryForPost(sendParam,store_service_url+"agent/Lyinit/esbCooperUpdate");
//	                 if (Integer.parseInt(resPr.get("state").toString()) != 1) {
//	                	 throw new RuntimeException(resPr.get("message").toString());
//	                 }
//				}
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("id", user.get("ID"));
				//将会员数据同步OA
				Map<String,Object> memberInfoMap=memberInfoDao.queryMemberInfoById(params);
				memberInfoMap.put("operationType", "edit");
				//调用oa同步接口
                ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + "/sys_dictionary/sync_formal_customer", memberInfoMap);
                if (!oAPr.getState()) {
                    throw new RuntimeException("OA系统会员数据同步失败");
                }
                //记录会员审核信息编辑日志
	    		Map<String,Object> logMap=new HashMap<String,Object>();
	    		logMap.put("USER_TYPE", 3);
	    		logMap.put("OPERATE_ID", 2);
	    		logMap.put("REMARK", "编辑【会员信息】");
	    		logMap.put("CREATE_USER_ID", public_user_id);
	    		logMap.put("USER_NAME", memberInfoMap.get("LOGIN_NAME"));
	    		logMap.put("USER_REALNAME", memberInfoMap.get("USER_MANAGE_NAME"));
	    		memberInfoDao.insertUserOperationLog(logMap);
				pr.setState(true);
				pr.setMessage("编辑成功");
				pr.setObj(user);
			}else{
				pr.setState(false);
				throw new RuntimeException("编辑失败");
			}
		} catch (Exception e) {
	        pr.setState(false);
	        pr.setMessage(e.getMessage());
	        throw new RuntimeException(e);
	    }
		return pr;
	}
	
	
	/**
	 * 待审核编辑会员
	 * @param request
	 * @return
     */
	@Transactional
	public ProcessResult editMemberApply(HttpServletRequest request) throws Exception{
		Map<String,Object> user = null;
		ProcessResult pr = new ProcessResult();
		try{
			String json = HttpUtil.getRequestInputStream(request);
			user = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			long public_user_id = Long.parseLong(user.get("public_user_id").toString());
			long user_id = Long.parseLong(user.get("id").toString());
			//编辑用户为临时用户时，将状态改为待审批状态
			if(Integer.parseInt(user.get("user_state").toString()) == 6){

				Map<String,Object> params = new HashMap<String, Object>();
				params.put("id",user_id);
				params.put("login_name",user.get("LOGIN_NAME"));
				int ln = memberInfoDao.countMemberInfoApplyByLoginName(params);
				if (ln > 0) {
					pr.setState(false);
					pr.setMessage("该用户名已经被注册");
					return pr;
				}
				user.put("USER_STATE", "0");
			}
			if(Integer.parseInt(user.get("user_state").toString()) == 3) {
				user.put("USER_STATE", "0");
			}

			int count = memberInfoDao.queryMemberInfoRecordByMobilePhone(user);
			if(count>0){
				pr.setState(false);
				pr.setMessage("该手机号已经被注册");
				return pr;
			}
			if(!StringUtils.isEmpty(user.get("USER_MANAGE_CARDID"))){
				count=memberInfoDao.queryMemberInfoRecordByUserManageCardId(user);
				if(count>0){
					pr.setState(false);
					pr.setMessage("该身份证号已经被注册");
					return pr;
				}
			}
			if(Integer.parseInt(user.get("USER_COMPANY_TYPE").toString())==4){
				user.put("shop_photo", user.get("business_licence_imgurl"));
			}else{
				user.put("user_business_licence_imgurl", user.get("business_licence_imgurl"));
			}
			//获取业务员名称
			String  referee_user_realname="";
			if(!StringUtils.isEmpty(user.get("REFEREE_USER_ID"))){
				referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("REFEREE_USER_ID").toString()));
			}
			//获取门店名称
			String  store_name="";
			if(!StringUtils.isEmpty(user.get("STORE_ID"))){
				store_name=storeInfoDao.queryStoreNameById(Integer.parseInt(user.get("STORE_ID").toString()));
			}
			//获取合作商名称
			String partner_user_realna="";
			if(!StringUtils.isEmpty(user.get("PARTNER_USER_ID"))){
				partner_user_realna=referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("PARTNER_USER_ID").toString()));
			}
			//获取督导名称
			String supervisor_user_realna="";
			if(!StringUtils.isEmpty(user.get("SUPERVISOR_USER_ID"))){
				supervisor_user_realna=referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("SUPERVISOR_USER_ID").toString()));
			}
			//获取业务经理名称
			String market_supervision_user_realna=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("MARKET_SUPERVISION_USER_ID").toString()));
			user.put("REFEREE_USER_REALNAME", referee_user_realname);
			user.put("MARKET_SUPERVISION_USER_REALNA", market_supervision_user_realna);
			user.put("STORE_NAME", store_name);
			user.put("PARTNER_USER_REALNA", partner_user_realna);
			user.put("SUPERVISOR_USER_REALNA", supervisor_user_realna);
			if(!StringUtils.isEmpty(user.get("COMMISSION_RATE"))){
				user.put("COMMISSION_RATE", Double.parseDouble(user.get("COMMISSION_RATE").toString())/100);
			}

			memberInfoDao.updateMemberInfo(user);
			// TODO: 如果有信息变更，则待审核记录改为已作废状态， 同时新增一条记录
			if(Integer.parseInt(user.get("user_state").toString()) == 0) { //待审核
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("user_name", user_id);
				param.put("public_user_id", public_user_id);
				memberInfoDao.updateMemberInfoPendingRecord(param);
			}

			user.put("user_id", user_id);
			Map<String, Object> recordUser = new HashMap<String, Object>();
			recordUser.putAll(user);
			recordUser.put("record_state", 0);
			recordUser.put("description", "运营总后台审核前编辑会员");
			if(memberInfoDao.insertMemberInfoRecordFromOldData(recordUser) <= 0) {
				throw new RuntimeException("创建新的变更记录失败");
			}
			user.put("record_id", recordUser.get("id"));
			//查询用户帐户信息
			Map<String, Object> userParam = new HashMap<String, Object>();
			userParam.put("user_name", user_id);
			userParam.put("record_id", user.get("record_id"));
			user = memberInfoDao.queryMemberInfoRecordById(userParam);
			//记录会员申请编辑日志
			Map<String,Object> logMap=new HashMap<String,Object>();
			logMap.put("USER_TYPE", 3);
			logMap.put("OPERATE_ID", 2);
			logMap.put("REMARK", "编辑【申请信息】");
			logMap.put("CREATE_USER_ID", public_user_id);
			logMap.put("USER_NAME", user.get("LOGIN_NAME"));
			logMap.put("USER_REALNAME", user.get("USER_MANAGE_NAME"));
			memberInfoDao.insertUserOperationLog(logMap);
			pr.setState(true);
			pr.setMessage("编辑成功");
			pr.setObj(user);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e);
		}

		return pr;
	}
	
	/**
	 * 获取业务员、业务经理、门店下属会员列表[代客户下单]
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult querySubsidiaryMemberList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			GridResult paramGridResult=PageUtil.handlePageParams(params);
			if(paramGridResult!=null){
				return paramGridResult;
			}
			//业务员、业务经理、门店、营业员对应区分
			if(StringUtils.isEmpty(params.get("public_user_type"))||StringUtils.isEmpty(params.get("public_user_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			int public_user_type =Integer.parseInt(params.get("public_user_type")+"");
			switch(public_user_type){
				//TODO 后续此处管理员权限屏蔽
				case 2:break;
				case 3:
					params.put("referee_user_id", params.get("public_user_id"));
					break; 
				case 4:
					params.put("market_supervision_user_id", params.get("public_user_id"));
					break; 
				case 5: 
					params.put("store_user_id", params.get("public_user_id"));
					break;
				case 6: 
					params.put("store_user_id", params.get("public_user_id"));
					break;
				default: 
					gr.setState(false);
					gr.setMessage("无数据权限");
					return gr;
			}
			//获取数量
			int userCount=memberInfoDao.querySubsidiaryMemberCount(params);
			List<Map<String,Object>> userList = memberInfoDao.querySubsidiaryMemberList(params);
			if (userList != null && userList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(userList);
				gr.setTotal(userCount);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error(e);
		}
		return gr;
	}
	
	/**
	 * 获取个人收支记录详情
	 * @param request
	 * @return
	 *  turnover_number       收付关联号
	 *  record_number         收付单号
	 */
	public ProcessResult queryMemberAccountRecordDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> map = new HashMap<String,Object>();
			map = (Map<String,Object>) Transform.GetPacket(json, Map.class);
			//获取个人收支记录详情
			Map<String,Object> accountRecord = memberInfoDao.queryMemberAccountRecordDetail(map);
			pr.setState(true);
			pr.setMessage("获取个人收支记录详情成功");
			pr.setObj(accountRecord);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			logger.error(e);
		}
		return pr;
	}
	
	/**
	 *  查询个人收支记录信息列表
	 */
	public GridResult queryMemberAccountRecordList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//Map<String, Object> paramMap = getRequestParams(request);
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}

            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
          //获取个人收支记录
            int listCount=memberInfoDao.queryMemberAccountRecordCount(paramMap);
			List<Map<String,Object>> list = memberInfoDao.queryMemberAccountRecordList(paramMap);
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("获取数据成功");
				gr.setObj(list);
				gr.setTotal(listCount);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("获取个人收支记录信息失败,{}",e);
		}
		return gr;
	}
	
	/**
	 *  返回授信管理列表结果
	 */
	public GridResult queryCreditList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//Map<String, Object> paramMap = getRequestParams(request);
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}

            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
          //获取个人收支记录
            int listCount=memberInfoDao.queryCreditCount(paramMap);
			List<Map<String,Object>> list = memberInfoDao.queryCreditList(paramMap);
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("获取数据成功");
				gr.setObj(list);
				gr.setTotal(listCount);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("获取授信管理列表信息失败,{}",e);
		}
		return gr;
	}
	
	/**
     * 展示详细信息
     * @param request
     * @return
     */
    public ProcessResult queryCreditDetail(HttpServletRequest request) {
  	  ProcessResult pr = new ProcessResult();
  	  Map<String,Object> order = new HashMap<String,Object>();
  	  try {
  		  String json = HttpUtil.getRequestInputStream(request);
  		  if(!StringUtils.isEmpty(json))
  			  order = (Map<String,Object>) Transform.GetPacket(json,Map.class);
  		  if(order!=null && Integer.parseInt(order.get("id").toString())!=0){
  			  order = memberInfoDao.queryCreditById(Long.parseLong(order.get("id").toString()));
				  pr.setState(true);
				  pr.setMessage("查询详情成功!");
				  pr.setObj(order);
  		  }else{
  			  pr.setState(false);
  			  pr.setMessage("缺少参数!");
  		  }
  	  } catch (Exception e) {
  		  pr.setState(false);
  		  pr.setMessage(e.getMessage());
  	  }
  	  return pr;
    }
    
    
    /**
     * 驳回修改
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult reject(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> userInfo = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json))
				userInfo = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (userInfo != null && !StringUtils.isEmpty(userInfo.get("user_name"))) {
				Map<String, Object> params = memberInfoDao.queryMemberInfoRecordById(userInfo);
				if ("3".equals(params.get("USER_STATE") + "")) {
					pr.setState(false);
					pr.setMessage("已经驳回的会员不允许再次驳回");
					return pr;
				}
				if (!"0".equals(params.get("RECORD_STATE") + "")) {
					pr.setState(false);
					pr.setMessage("用户资料已发生变更，请刷新列表重试");
					return pr;
				}
				userInfo.put("record_state", 3);
				if (memberInfoDao.updateMemberInfoRecordState(userInfo) > 0) {
					userInfo.put("public_user_type",null);//参数置空，不考虑权限，仅以用户的id进行过滤
					userInfo.put("id", userInfo.get("user_name"));
					Map<String, Object> memberInfo = memberInfoDao.queryMemberInfoById(userInfo);
					/**
					 * 		用户驳回时,如果主表状态如果是以下状态，则主表的用户状态也同时改为：驳回
					 * 		用户状态： 0.待审批；  4.预审通过 ；6.临时用户状态
					 * */
					if (memberInfo != null 
							&& !memberInfo.isEmpty() 
							&& (
									"0".equals(memberInfo.get("USER_STATE").toString())
									||
									"4".equals(memberInfo.get("USER_STATE").toString())
									||
									"6".equals(memberInfo.get("USER_STATE").toString())
							   )
							) {
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("id", userInfo.get("user_name"));
						param.put("USER_STATE", 3);
						memberInfoDao.updateMemberInfo(param);
					}
					pr.setState(true);
					pr.setMessage("驳回成功!");
				}
			} else {
				pr.setState(false);
				pr.setMessage("缺少参数!");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return pr;
	}
    
    /**
     * 编辑 授信管理信息
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
    public ProcessResult editMemberCredit(HttpServletRequest request)throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	 Map<String,Object> order = new HashMap<String,Object>();
  	  try {
  		  String json = HttpUtil.getRequestInputStream(request);
  		  if(!StringUtils.isEmpty(json))
  			  order = (Map<String,Object>) Transform.GetPacket(json,Map.class);
  		  if(order!=null && Integer.parseInt(order.get("id").toString())!=0){
  			  if(!StringUtils.isEmpty(order.get("memberid"))){
  				  order.put("user_name", order.get("memberid"));
  			  }
  			  int count = memberInfoDao.updateCredit(order);
  			  if(count>0){
  				  pr.setState(true);
  				  pr.setMessage("会员授信编辑成功!");
  				  pr.setObj(order);
  			  }else{
  				  pr.setState(false);
  				  pr.setMessage("编辑失败!");
  				  pr.setObj(order);
  			  }
  		  }else{
  			  pr.setState(false);
  			  pr.setMessage("缺少参数!");
  		  }
	  	} catch (Exception e) {
	        pr.setState(false);
	        pr.setMessage(e.getMessage());
	        throw new RuntimeException(e);
	    }
  	  return pr;
    }
    /**
     * 添加 授信管理信息
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
    public ProcessResult addMemberCredit(HttpServletRequest request)throws Exception {
        ProcessResult pr = new ProcessResult();
        Map<String,Object> order = new HashMap<String,Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
          	  order = (Map<String,Object>) Transform.GetPacket(json,Map.class);
            if(order!=null){
            	order.put("user_name", order.get("memberid"));
	            int count = memberInfoDao.insertCredit(order);
	            if(count>0){
	                pr.setState(true);
	                pr.setMessage("添加会员授信成功!");
	                pr.setObj(order);
	           }else{
	            	pr.setState(false);
	                pr.setMessage("添加会员授信失败!");
	                pr.setObj(order);
	            }
            }else{
            	pr.setState(false);
                pr.setMessage("缺少参数!");
            }
        } catch (Exception e) {
	        pr.setState(false);
	        pr.setMessage(e.getMessage());
	        throw new RuntimeException(e);
	    }
        return pr;
    }
    
    /**
     * 删除授信管理记录
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
    public ProcessResult removeMemberCredit(HttpServletRequest request)throws Exception {
  	  ProcessResult pr = new ProcessResult();
  	 Map<String,Object> order = new HashMap<String,Object>();
  	  try {
  		  String json = HttpUtil.getRequestInputStream(request);
  		  if(!StringUtils.isEmpty(json))
  			  order = (Map<String,Object>) Transform.GetPacket(json,Map.class);
  		  if(order!=null && Integer.parseInt(order.get("id").toString())!=0){
  			  if(memberInfoDao.removeCredit(Long.parseLong(order.get("id").toString()))>0){
  				  pr.setState(true);
  				  pr.setMessage("删除成功!");
  			  }else{
  				  pr.setState(true);
  				  pr.setMessage("删除失败!");
  			  }
  		  }else{
  			  pr.setState(false);
  			  pr.setMessage("缺少参数!");
  		  }
	  	} catch (Exception e) {
	        pr.setState(false);
	        pr.setMessage(e.getMessage());
	        throw new RuntimeException(e);
	    }
  	  return pr;
    }
    /**
     * 授信  审批通过
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult creditUpdateState(HttpServletRequest request)throws Exception{
    	ProcessResult pr = new ProcessResult();
		// 获取传入参数
    	try {
			String json = HttpUtil.getRequestInputStream(request);
			Map<String,Object> paramMap=null;
			// 解析参数
			if(!StringUtils.isEmpty(json)) 
				paramMap = (Map<String, Object>) Transform.GetPacket(json,HashMap.class);
			if(paramMap==null ||StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			if(memberInfoDao.updateById(paramMap)>0){
				Map<String,Object>param=new HashMap<String,Object>();
				param.put("client_credit_user_name", paramMap.get("user_id"));
				param.put("client_new_credit_money_number", paramMap.get("credit_money"));
				memberInfoDao.updateBankAccount(param);
				String output_status = String.valueOf(param.get("output_status"));//状态 0-失败 1-成功
				String output_msg = String.valueOf(param.get("output_msg"));//当成功时为：取消成功   当失败是：为错误消息内容
				if(ResponseSateEnum.SUCCESS.getValue().equals(output_status)){//成功
					pr.setState(true);
				}else{
					pr.setState(false);
				}
				pr.setMessage(output_msg);
			}else{
				pr.setState(false);
				pr.setMessage("审核失败");
			}
    	} catch (Exception e) {
	        pr.setState(false);
	        pr.setMessage(e.getMessage());
	        throw new RuntimeException(e);
	    }
		return pr;
	}
    /**
	 *  查询账号预审列表
	 */
	public GridResult queryAccountPreList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			//Map<String, Object> paramMap = getRequestParams(request);
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}

            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			if(StringUtils.isEmpty(paramMap.get("user_manage_mobilephone"))){
				paramMap.put("user_manage_mobilephone", "");
			}
			//查询当前登录用户是否配置账号预审权限
			SysUserInfo sysUserInfo=sysUserInfoDao.queryByUserId(Long.parseLong(paramMap.get("public_user_id")+""));
			paramMap.put("all_view_flag",true);
			if(sysUserInfo.getAccount_approval_state()==1){
				if(Integer.parseInt(paramMap.get("public_user_type")+"")!=1){
					paramMap.put("all_view_flag",false);
				}
			}
            int listCount=memberInfoDao.queryAccountPreCount(paramMap);
			List<Map<String,Object>> list = memberInfoDao.queryAccountPreList(paramMap);
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("获取数据成功");
				gr.setObj(list);
				gr.setTotal(listCount);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("获取账号待审列表失败,{}",e);
		}
		return gr;
	}
	/**
	 *  账号预审审核
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult accountPreApproval(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			Map<String, Object> user = new HashMap<String, Object>();
			String json = HttpUtil.getRequestInputStream(request);
			user = (Map<String, Object>) Transform.GetPacket(json,Map.class);
			if(user==null||StringUtils.isEmpty(user.get("user_name"))){
				pr.setState(false);
				pr.setMessage("参数错误，缺少用户名");
				return pr;
			}
			Map<String,Object> userInfo = memberInfoDao.queryMemberInfoRecordById(user);
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("USER_MANAGE_MOBILEPHONE", user.get("USER_MANAGE_MOBILEPHONE"));
			searchMap.put("USER_NAME", user.get("user_name"));
			int num=memberInfoDao.queryMemberInfoByMobilePhone(searchMap);
			if(num>0){
				pr.setState(false);
				pr.setMessage("该手机号已经被使用");
				return pr;
			}
			if("1".equals(userInfo.get("USER_STATE"))){
				pr.setState(false);
				pr.setMessage("该会员已经审批通过,无法预审");
				return pr;
			}
			//判断该用户会员门店管理表是否已经存在数据
			int userStoreCount=memberInfoDao.queryUserStoreAddressByUserId(Integer.parseInt(user.get("user_name").toString()));
			if(userStoreCount==0){
				//没有数据审批后进行新增
				Map<String,Object> memberStoreAddress=new HashMap<String,Object>();
				Date date=new Date();
				DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String approval_date=format.format(date);
				memberStoreAddress.put("user_id", user.get("USER_NAME"));
				memberStoreAddress.put("login_name", user.get("LOGIN_NAME"));
				memberStoreAddress.put("user_manage_name", user.get("USER_MANAGE_NAME"));
				memberStoreAddress.put("longitude", userInfo.get("USER_LONGITUDE"));
				memberStoreAddress.put("latitude", userInfo.get("USER_LATITUDE"));
				memberStoreAddress.put("public_user_id",user.get("public_user_id"));
				memberStoreAddress.put("approval_state","1");
				memberStoreAddress.put("user_store_resource", "1");
				memberStoreAddress.put("approval_date", approval_date);
				memberStoreAddress.put("approval_user_id", user.get("public_user_id"));
				//当会员经营类型为4即网络营销时封装一下参数
				if(Integer.parseInt(user.get("USER_COMPANY_TYPE").toString())==4){
					memberStoreAddress.put("store_name", user.get("SHOP_NAME"));
					memberStoreAddress.put("store_address", user.get("USER_COMPANY_ADDRESS_DEAILS"));
					memberStoreAddress.put("management_address", user.get("USER_COMPANY_LOCATION_ADDRESS"));
					memberStoreAddress.put("province", user.get("USER_COMPANY_ADDRESS_PROVINCE"));
					memberStoreAddress.put("city", user.get("USER_COMPANY_ADDRESS_CITY"));
					memberStoreAddress.put("area", user.get("USER_COMPANY_ADDRESS_COUNTY"));
				}else{
					//除网络营销外的其它经营类型封装一以下参数
					memberStoreAddress.put("store_name", user.get("USER_CONTROL_STORE_NAME"));
					memberStoreAddress.put("store_address", user.get("USER_STORE_ADDRESS_DEAILS"));
					memberStoreAddress.put("management_address", user.get("USER_STORE_LOCATION_ADDRESS"));
					memberStoreAddress.put("province", user.get("USER_STORE_ADDRESS_PROVINCE"));
					memberStoreAddress.put("city", user.get("USER_STORE_ADDRESS_CITY"));
					memberStoreAddress.put("area", user.get("USER_STORE_ADDRESS_COUNTY"));
				}
				memberInfoDao.insertMemberStore(memberStoreAddress);
				//记录配置会员门店管理新增日志
				Map<String,Object> memberStorelogMap=new HashMap<String,Object>();
				memberStorelogMap.put("USER_TYPE", 3);
				memberStorelogMap.put("OPERATE_ID", 3);
				memberStorelogMap.put("REMARK", "配置【会员门店管理】新增");
				memberStorelogMap.put("CREATE_USER_ID", user.get("public_user_id"));
				memberStorelogMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
				memberStorelogMap.put("USER_REALNAME", user.get("USER_MANAGE_NAME"));
				memberInfoDao.insertUserOperationLog(memberStorelogMap);
			}
			//获取业务员名称
			String  referee_user_realname="";
			if(!StringUtils.isEmpty(user.get("REFEREE_USER_ID"))){
				referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("REFEREE_USER_ID").toString()));
			}
			//获取门店名称
			String  store_name="";
			if(!StringUtils.isEmpty(user.get("STORE_ID"))){
				store_name=storeInfoDao.queryStoreNameById(Integer.parseInt(user.get("STORE_ID").toString()));
			}
			//获取业务经理名称
			String market_supervision_user_realna=memberInfoDao.querySysUserInfoById(Integer.parseInt(user.get("MARKET_SUPERVISION_USER_ID").toString()));
			user.put("REFEREE_USER_REALNAME", referee_user_realname);
			user.put("MARKET_SUPERVISION_USER_REALNA", market_supervision_user_realna);
			user.put("STORE_NAME", store_name);
			user.put("id", user.get("user_name"));
			if(Integer.parseInt(user.get("USER_COMPANY_TYPE").toString())==4){
				user.put("shop_photo", user.get("business_licence_imgurl"));
			}else{
				user.put("user_business_licence_imgurl", user.get("business_licence_imgurl"));
			}
			// TODO: 如果有信息变更，则将待审核记录改为已作废状态， 同时新增一条记录
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("user_name", user.get("user_name"));
			param.put("public_user_id", user.get("public_user_id"));
			memberInfoDao.updateMemberInfoPendingRecord(param);
			user.put("user_id", user.get("user_name"));
			Map<String, Object> recordUser = new HashMap<String, Object>();
			recordUser.putAll(user);
			recordUser.put("record_state", 0);
			recordUser.put("description", "运营总后台预审会员");
			memberInfoDao.insertMemberInfoRecordFromOldData(recordUser);
			//设置用户状态为 “预审通过”
			user.put("USER_STATE",4);
			user.put("PREVIEW_DEL_FLAG", 0);
			memberInfoDao.updateMemberInfo(user);
			/**
			 * 判断用户售后地址信息是否存在，如果不存在，则以当前的店铺地址作为其默认收货地址
			 * reid 2019.11.13 
			 */
			if(memberInfoDao.countUserAddress(Long.parseLong(user.get("user_name").toString()))<=0) {
				//创建用户收货信息记录
				memberInfoDao.insertUserAddressByUserId(Long.parseLong(user.get("user_name").toString()));
			}
			Map<String,Object> memberInfo=new HashMap<String,Object>();
			memberInfo=memberInfoDao.queryMemberInfoById(user);

			memberInfo.put("pre_aprv_allowed_number", Integer.parseInt(memberInfo.get("PRE_APRV_ALLOWED_NUMBER").toString())+1);
			memberInfoDao.updateMemberInfoById(memberInfo);
			//记录会员预审日志
			Map<String,Object> memberDetail=memberInfoDao.queryMemberInfoById(user);
			Map<String,Object> logMap=new HashMap<String,Object>();
			logMap.put("USER_TYPE", 3);
			logMap.put("OPERATE_ID", 1);
			logMap.put("REMARK", "创建【预审】");
			logMap.put("CREATE_USER_ID", user.get("public_user_id"));
			logMap.put("USER_NAME", memberDetail.get("LOGIN_NAME"));
			logMap.put("USER_REALNAME", memberDetail.get("USER_MANAGE_NAME"));
			memberInfoDao.insertUserOperationLog(logMap);
			pr.setState(true);
			pr.setMessage("审核通过成功");

		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	
	/**
	 *  会员特殊价格列表查询
	 */
	public GridResult queryMemberSpecialList(HttpServletRequest request) {
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

            if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
            if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
            	paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
            int listCount=memberInfoDao.queryMemberSpecialPriceCount(paramMap);
			List<Map<String,Object>> list = memberInfoDao.queryMemberSpecialPriceList(paramMap);
			if(list != null && list.size()>0){
				gr.setState(true);
				gr.setMessage("获取数据成功");
				gr.setObj(list);
				gr.setTotal(listCount);
			}else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("获取特殊价格列表失败,{}",e);
		}
		return gr;
	}
	

    /**
     * 会员特殊价格详情
     * @param request
     * @return
     */
   @SuppressWarnings("unchecked")
    public ProcessResult queryMemberSpecialDetail(HttpServletRequest request) {
      ProcessResult pr = new ProcessResult();
      Map<String,Object> param = new HashMap<String,Object>();
      try {
          String json = HttpUtil.getRequestInputStream(request);
          if(!StringUtils.isEmpty(json))
            param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
          if(!StringUtils.isEmpty(param.get("apply_number"))){
              //查询特殊价格的会员
              // List<Map<String, Object>> productInfo = new ArrayList<Map<String, Object>>();
               List<Map<String,Object>> userDiscountMember = memberInfoDao.queryUserDicountUserInfo(param.get("apply_number").toString());
//		  			 for (Map<String, Object> retParams : userDiscountMember) {
//		 				//查询参数
//		 				Map params = new HashMap();
//		 				params.put("user_name", retParams.get("USER_NAME").toString());
//		 				params.put("apply_number", param.get("apply_number"));
//		 				 //查询特殊价格会员下的商品
//	  			   		List<Map<String,Object>> userDiscountProduct = memberInfoDao.queryUserDicountProduct(params);
//	  			   		retParams.put("product_detail", userDiscountProduct);//尺码明细
//	  			   		productInfo.add(retParams);
//		 			}
                if (userDiscountMember != null) {
                    pr.setState(true);
                    pr.setMessage("获取成功");
                    pr.setObj(userDiscountMember);
                } else {
                    pr.setState(true);
                    pr.setMessage("无数据");
                }
          }else{
            pr.setState(false);
            pr.setMessage("缺少参数");
          }
      } catch (Exception e) {
          pr.setState(false);
          pr.setMessage(e.getMessage());
      }
      return pr;
    }


   /**
     * 会员特殊价格详情
     * @param request
     * @return
     */
   @SuppressWarnings("unchecked")
    public ProcessResult memberProductDetail(HttpServletRequest request) {
      ProcessResult pr = new ProcessResult();
      Map<String,Object> param = new HashMap<String,Object>();
      try {
          String json = HttpUtil.getRequestInputStream(request);
          if(!StringUtils.isEmpty(json)){
            param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
          }
          if(!StringUtils.isEmpty(param.get("apply_number"))||!StringUtils.isEmpty(param.get("user_name"))){
            List<Map<String,Object>> userDiscountProduct = memberInfoDao.queryUserDicountProduct(param);
                if (userDiscountProduct != null) {
                    pr.setState(true);
                    pr.setMessage("获取成功");
                    pr.setObj(userDiscountProduct);
                } else {
                    pr.setState(true);
                    pr.setMessage("无数据");
                }
          }else{
            pr.setState(false);
            pr.setMessage("缺少参数");
          }
      } catch (Exception e) {
          pr.setState(false);
          pr.setMessage(e.getMessage());
      }
      return pr;
    }

    /**
     * 特价单新增
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addMemberSpecial(HttpServletRequest request)throws Exception {
        ProcessResult pr = new ProcessResult();
        Map<String,Object> param = new HashMap<String,Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
                //获取申请单号
                String applyNumber=memberInfoDao.queryApplyNumber();
                param.put("apply_number", applyNumber);
                int count= memberInfoDao.insertUserDiscount(param);
                if(count>0){
                    //新增特价单对应的会员和商品
                    List<Map<String,Object>>paraMap=(List<Map<String, Object>>) param.get("specail");
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("apply_number",param.get("apply_number"));
                    for(int i=0;i<paraMap.size();i++){
                        map.put("user_name",paraMap.get(i).get("USER_NAME"));
                        map.put("begin_time",paraMap.get(i).get("BEGIN_TIME"));
                        map.put("end_time",paraMap.get(i).get("END_TIME"));
                        List<Map<String,Object>> product_detail=(List<Map<String,Object>>)paraMap.get(i).get("product_detail");
                        for (int m=0;m<product_detail.size();m++){
                            map.put("product_itemnumber", product_detail.get(m).get("PRODUCT_ITEMNUMBER"));
                            map.put("product_color", product_detail.get(m).get("PRODUCT_COLOR"));
                            map.put("product_specs", product_detail.get(m).get("PRODUCT_SPECS"));
                            if(!StringUtils.isEmpty(product_detail.get(m).get("DISCOUNT"))){
                                map.put("discount", Double.parseDouble(product_detail.get(m).get("DISCOUNT").toString())/100);
                            }
                            memberInfoDao.insertUserDiscountProduct(map);
                        }
                    }
                    //记录会员特殊价格新增日志
//			    		Map<String,Object> logMap=new HashMap<String,Object>();
//			    		logMap.put("USER_TYPE", 3);
//			    		logMap.put("OPERATE_ID", 4);
//			    		logMap.put("REMARK", "其它【设置特殊价】新增");
//			    		logMap.put("CREATE_USER_ID", param.get("public_user_id"));
//			    		memberInfoDao.insertUserOperationLog(logMap);
                    pr.setState(true);
                    pr.setMessage("新增成功");
                    pr.setObj(applyNumber);
                }else{
                    pr.setState(false);
                    pr.setMessage("新增失败");
                }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 特价单删除
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeMemberSpecial(HttpServletRequest request) throws Exception {
      ProcessResult pr = new ProcessResult();
     Map<String,Object> param = new HashMap<String,Object>();
      try {
          String json = HttpUtil.getRequestInputStream(request);
          if(!StringUtils.isEmpty(json)){
            param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
          }
          if(param!=null && !StringUtils.isEmpty(param.get("apply_number"))){
              if(memberInfoDao.removeUserDiscount(param.get("apply_number")+"")>0){
                  memberInfoDao.removeUserDiscountProduct(param.get("apply_number")+"");
                  //记录会员特殊价格删除日志
//	  				  Map<String,Object> logMap=new HashMap<String,Object>();
//	  				  logMap.put("USER_TYPE", 3);
//	  				  logMap.put("OPERATE_ID", 4);
//	  				  logMap.put("REMARK", "其它【设置特殊价】删除");
//	  				  logMap.put("CREATE_USER_ID", param.get("public_user_id"));
//	  				  memberInfoDao.insertUserOperationLog(logMap);
                  pr.setState(true);
                  pr.setMessage("删除成功!");
              }else{
                  pr.setState(true);
                  pr.setMessage("删除失败!");
              }
          }else{
              pr.setState(false);
              pr.setMessage("缺少参数!");
          }
    } catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
      return pr;
    }

    /**
     * 特价单明细删除
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeMemberSpecialDetail(HttpServletRequest request) throws Exception {
      ProcessResult pr = new ProcessResult();
     Map<String,Object> param = new HashMap<String,Object>();
      try {
          String json = HttpUtil.getRequestInputStream(request);
          if(!StringUtils.isEmpty(json)){
            param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
          }
          if(StringUtils.isEmpty(param.get("product_itemnumber"))){
              if(!StringUtils.isEmpty(param.get("user_name")) && !StringUtils.isEmpty(param.get("apply_number"))){
                  if(memberInfoDao.removeByApplyNumberAndUserName(param)>0){
                    //记录会员特殊价格明细删除日志
//		  				  Map<String,Object> logMap=new HashMap<String,Object>();
//		  				  logMap.put("USER_TYPE", 3);
//		  				  logMap.put("OPERATE_ID", 4);
//		  				  logMap.put("REMARK", "其它【设置特殊价】明细删除");
//		  				  logMap.put("CREATE_USER_ID", param.get("public_user_id"));
//		  				  memberInfoDao.insertUserOperationLog(logMap);
                      pr.setState(true);
                      pr.setMessage("删除成功!");
                  }else{
                      pr.setState(false);
                      pr.setMessage("删除失败!");
                  }

              }else{
                  pr.setState(false);
                  pr.setMessage("缺少参数!");
              }
          }else{
            if(!StringUtils.isEmpty(param.get("user_name")) && !StringUtils.isEmpty(param.get("apply_number")) && !StringUtils.isEmpty(param.get("product_specs")) && !StringUtils.isEmpty(param.get("product_color"))){
                if(memberInfoDao.removeByCondition(param)>0){
                      //记录会员特殊价格明细删除日志
//		  				  Map<String,Object> logMap=new HashMap<String,Object>();
//		  				  logMap.put("USER_TYPE", 3);
//		  				  logMap.put("OPERATE_ID", 4);
//		  				  logMap.put("REMARK", "其它【设置特殊价】明细删除");
//		  				  logMap.put("CREATE_USER_ID", param.get("public_user_id"));
//		  				  memberInfoDao.insertUserOperationLog(logMap);
                      pr.setState(true);
                      pr.setMessage("删除成功!");
                  }else{
                      pr.setState(false);
                      pr.setMessage("删除失败!");
                  }
            }else{
                  pr.setState(false);
                  pr.setMessage("缺少参数!");
              }
          }
    } catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
      return pr;
    }

    /**
     * 特价单编辑
     * @param request
     * @return
     */
//	    @Transactional
//	    public ProcessResult editMemberSpecial(HttpServletRequest request) throws Exception {
//	  	  ProcessResult pr = new ProcessResult();
//	  	 Map<String,Object> param = new HashMap<String,Object>();
//	  	  try {
//	  		  String json = HttpUtil.getRequestInputStream(request);
//	  		  if(!StringUtils.isEmpty(json))
//	  			  param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
//	  		  if(param!=null && !StringUtils.isEmpty(param.get("apply_number"))){
//	  			  if(memberInfoDao.removeUserDiscount(param.get("apply_number")+"")>0){
//	  				 if(memberInfoDao.removeUserDiscountProduct(param.get("apply_number")+"")== 0){
//						 throw new RuntimeException("修改失败");
//					 }
//	  				 int count= memberInfoDao.insertUserDiscount(param);
//	  				  //新增特价单对应的会员和商品
//	  				 if(count>0){
//	  					List<Map<String,Object>>paraMap=(List<Map<String, Object>>) param.get("specail");
//		            	Map<String,Object> map=new HashMap<String,Object>();
//		            	map.put("apply_number",param.get("apply_number"));
//		            	for(int i=0;i<paraMap.size();i++){
//		            		map.put("user_name",paraMap.get(i).get("USER_NAME"));
//		            		map.put("begin_time",paraMap.get(i).get("BEGIN_TIME"));
//		            		map.put("end_time",paraMap.get(i).get("END_TIME"));
//		            		List<Map<String,Object>> product_detail=(List<Map<String,Object>>)paraMap.get(i).get("product_detail");
//		            		for (int m=0;m<product_detail.size();m++){
//		            			map.put("product_itemnumber", product_detail.get(m).get("PRODUCT_ITEMNUMBER"));
//		            			map.put("product_color", product_detail.get(m).get("PRODUCT_COLOR"));
//		            			map.put("product_specs", product_detail.get(m).get("PRODUCT_SPECS"));
//		            			if(!StringUtils.isEmpty(product_detail.get(m).get("DISCOUNT"))){
//		            				map.put("discount", Double.parseDouble(product_detail.get(m).get("DISCOUNT").toString())/100);
//		            			}
//		            			memberInfoDao.insertUserDiscountProduct(map);
//		            		}
//		            	} 
//	  				 }
//	            	pr.setState(true);
//        			pr.setMessage("修改成功");
//	  			  }else{
//	  				  pr.setState(false);
//	  				  pr.setMessage("修改失败!");
//	  			  }
//	  		  }else{
//	  			  pr.setState(false);
//	  			  pr.setMessage("缺少参数!");
//	  		  }
//	  	} catch (Exception e) {
//            pr.setState(false);
//            pr.setMessage(e.getMessage());
//            throw new RuntimeException(e);
//        }
//	  	  return pr;
//	    }

    /**
     * 特价单编辑
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editMemberSpecial(HttpServletRequest request) throws Exception {
      ProcessResult pr = new ProcessResult();
     Map<String,Object> param = new HashMap<String,Object>();
      try {
          String json = HttpUtil.getRequestInputStream(request);
          if(!StringUtils.isEmpty(json))
              param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
          if(param!=null && !StringUtils.isEmpty(param.get("specail"))){
                List<Map<String,Object>>paraMap=(List<Map<String, Object>>) param.get("specail");
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("apply_number",param.get("apply_number"));
                for(int i=0;i<paraMap.size();i++){
                    map.put("user_name",paraMap.get(i).get("USER_NAME"));
                    map.put("begin_time",paraMap.get(i).get("BEGIN_TIME"));
                    map.put("end_time",paraMap.get(i).get("END_TIME"));
                    int count=memberInfoDao.queryUserDiscountCount(map);
                    if(count==0){
                        List<Map<String,Object>> product_detail=(List<Map<String,Object>>)paraMap.get(i).get("product_detail");
                        for (int m=0;m<product_detail.size();m++){
                            map.put("product_itemnumber", product_detail.get(m).get("PRODUCT_ITEMNUMBER"));
                            map.put("product_color", product_detail.get(m).get("PRODUCT_COLOR"));
                            map.put("product_specs", product_detail.get(m).get("PRODUCT_SPECS"));
                            if(!StringUtils.isEmpty(product_detail.get(m).get("DISCOUNT"))){
                                map.put("discount", Double.parseDouble(product_detail.get(m).get("DISCOUNT").toString())/100);
                            }
                            if(memberInfoDao.insertUserDiscountProduct(map)>0){
                                continue;
                            }else{
                                pr.setState(false);
                                pr.setMessage("修改失败");
                            }
                        }
                    }else{
                        List<Map<String,Object>> product_detail=(List<Map<String,Object>>)paraMap.get(i).get("product_detail");
                        for (int m=0;m<product_detail.size();m++){
                            map.put("product_itemnumber", product_detail.get(m).get("PRODUCT_ITEMNUMBER"));
                            map.put("product_color", product_detail.get(m).get("PRODUCT_COLOR"));
                            map.put("product_specs", product_detail.get(m).get("PRODUCT_SPECS"));
                            if(!StringUtils.isEmpty(product_detail.get(m).get("DISCOUNT"))){
                                map.put("discount", Double.parseDouble(product_detail.get(m).get("DISCOUNT").toString())/100);
                            }
                            if(!StringUtils.isEmpty(product_detail.get(m).get("new_add_data"))){
                                memberInfoDao.insertUserDiscountProduct(map);
                                continue;
                            }
                            if(memberInfoDao.updateUserDiscount(map)>0){
                                continue;
                            }else{
                                pr.setState(false);
                                pr.setMessage("修改失败");
                            }
                        }
                    }

                }
                //记录会员特殊价格编辑日志
//		    		Map<String,Object> logMap=new HashMap<String,Object>();
//		    		logMap.put("USER_TYPE", 3);
//		    		logMap.put("OPERATE_ID", 4);
//		    		logMap.put("REMARK", "其它【设置特殊价】编辑");
//		    		logMap.put("CREATE_USER_ID", param.get("public_user_id"));
//		    		memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("修改成功");
          }else{
              pr.setState(false);
              pr.setMessage("缺少参数!");
          }
    } catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
      return pr;
    }

    /**
     * 特价单修改状态
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult memberSpecialUpdateState(HttpServletRequest request) throws Exception {
      ProcessResult pr = new ProcessResult();
     Map<String,Object> param = new HashMap<String,Object>();
      try {
          String json = HttpUtil.getRequestInputStream(request);
          if(!StringUtils.isEmpty(json))
              param = (Map<String,Object>) Transform.GetPacket(json,Map.class);
          if(param!=null && !StringUtils.isEmpty(param.get("apply_number"))&& !StringUtils.isEmpty(param.get("state"))){
              if(memberInfoDao.updateUserDiscountState(param)>0){
                  //记录会员特殊价格审批日志
//			    	  Map<String,Object> logMap=new HashMap<String,Object>();
//			    	  logMap.put("USER_TYPE", 3);
//			    	  logMap.put("OPERATE_ID", 4);
//			    	  if(Integer.parseInt(param.get("state")+"")==2){
//			    		  logMap.put("REMARK", "其它【设置特殊价】提交审批");
//			    	  }else{
//			    		  logMap.put("REMARK", "其它【设置特殊价】审批");
//			    	  }
//			    	  logMap.put("CREATE_USER_ID", param.get("public_user_id"));
//			    	  memberInfoDao.insertUserOperationLog(logMap);
                  pr.setState(true);
                  pr.setMessage("审批成功!");
              }else{
                  pr.setState(false);
                  pr.setMessage("审批失败!");
              }
          }else{
              pr.setState(false);
              pr.setMessage("缺少参数!");
          }
    } catch (Exception e) {
        pr.setState(false);
        pr.setMessage(e.getMessage());
        throw new RuntimeException(e);
    }
      return pr;
    }

    /**
     * 获取会员特殊折后价
     * @param request
     * @return
     * @throws Exception
     */
    public ProcessResult querySpecialDiscountPrice(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String,Object> param = new HashMap<String,Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("spec_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[spec_id]规格编号");
            }
            if(StringUtils.isEmpty(params.get("discount"))){
                pr.setState(false);
                pr.setMessage("缺少参数[discount]折扣率");
            }
            Map<String, Object> result = memberInfoDao.querySpecialDiscountPrice(params);
            if(result == null){
                pr.setState(false);
                pr.setMessage("获取失败");
            }else{
                pr.setState(true);
                pr.setObj(result);
                pr.setMessage("获取成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    /**
     * 用户销售日报表列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryUserDailyReportListBy(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            List<Map<String,Object>> list = null;
            Map<String,Object> retMap = new HashMap<String,Object>();

            //以下代码为查询数量
            int total = 0;
            if((params.containsKey("order_money_min")&&!"".equals(params.get("order_money_min").toString()))
                    ||(params.containsKey("order_money_max")&&!"".equals(params.get("order_money_max").toString()))){
                if("1".equals(params.get("order_type").toString())){
                    //全部销售订单
                    total = memberInfoDao.queryUserDailyReportCountBy_SaleOrder(params);
                }else if("2".equals(params.get("order_type").toString())){
                    //待付款
                    total = memberInfoDao.queryUserDailyReportCountBy_NoPay(params);
                }else if("3".equals(params.get("order_type").toString())){
                    //未发货
                    total = memberInfoDao.queryUserDailyReportCountBy_NoSend(params);
                }else if("4".equals(params.get("order_type").toString())){
                    //发货订单
                    total = memberInfoDao.queryUserDailyReportCountBy_Send(params);
                }
            }else{
                total = memberInfoDao.queryUserDailyReportCountBy_Default(params);
            }
            
            //用户
            String file_names_user = ":LOGIN_NAME:USER_MANAGE_NAME:MARKET_SUPERVISION_USER_REALNA:STORE_NAME:REFEREE_USER_REALNAME:";
            //日志
            String file_names_login = ":LOGIN_NUM:PAGE_VIEW:";
            //全部销售订单
            String file_names_allorder = ":ALL_ORDER_COUNT:ALL_ORDER_PRODUCT_COUNT:ALL_ORDER_PRODUCT_MONEY:";
            //待付款订单
            String file_names_obliorder = ":OBLI_ORDER_COUNT:OBLI_ORDER_PRODUCT_COUNT:OBLI_ORDER_PRODUCT_MONEY:";
            //未发货订单
            String file_names_unshiporder = ":UNSHIP_ORDER_PRODUCT_COUNT:UNSHIP_ORDER_PRODUCT_MONEY:";
            //发货订单
            String file_names_shiporder = ":SHIP_ORDER_PRODUCT_COUNT:SHIP_ORDER_PRODUCT_MONEY:";
            //退款单
            String file_names_refund = ":REFUND_ORDER_COUNT:REFUND_ORDER_PRODUCT_COUNT:REFUND_ORDER_PRODUCT_MONEY:";
            //退货单
            String file_names_returns = ":RETURNS_ORDER_COUNT:RETURNS_ORDER_PRODUCT_COUNT:RETURNS_ORDER_PRODUCT_MONEY:";
            //实际销售
            String file_names_actual = ":ACTUAL_SALE_PRODUCT_COUNT:ACTUAL_SALE_PRODUCT_MONEY:";

            //需要查询的用户列表
            List<String> userList = new ArrayList<String>();
            //以下代码为了排序
            if((params.containsKey("sort")&&!"".equals(params.get("sort").toString()))
                    &&(params.containsKey("sort_by")&&!"".equals(params.get("sort_by").toString()))){
                String sort = ":"+params.get("sort").toString()+":";
                params.put("sort", "nvl("+params.get("sort").toString()+",0)");
                if(file_names_user.indexOf(sort)!=-1){
                    //用户---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_User(params);
                }else if(file_names_login.indexOf(sort)!=-1){
                    //日志---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Log(params);
                }else if(file_names_allorder.indexOf(sort)!=-1){
                    //全部销售订单---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Allorder(params);
                }else if(file_names_obliorder.indexOf(sort)!=-1){
                    //待付款订单 ---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Obliorder(params);
                }else if(file_names_unshiporder.indexOf(sort)!=-1){
                    //未发货订单---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Unshiporder(params);
                }else if(file_names_shiporder.indexOf(sort)!=-1){
                    //发货订单---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Shiporder(params);
                }else if(file_names_refund.indexOf(sort)!=-1){
                    //退款单---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Refund(params);
                }else if(file_names_returns.indexOf(sort)!=-1){
                    //退货单---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Returns(params);
                }else if(file_names_actual.indexOf(sort)!=-1){
                    //实际销售---获取排序后的用户信息
                    userList = memberInfoDao.queryUserDailyReportListBy_Actual(params);
                }else{
                    gr.setState(false);
                    gr.setMessage("未配置的排序字段，请联系管理员【"+params.get("sort").toString()+"】");
                    logger.error("未配置的排序字段，请联系管理员【"+params.get("sort").toString()+"】");
                    return gr;
                }
            }else{
                //默认---获取排序后的用户信息
                userList = memberInfoDao.queryUserDailyReportListBy_Default(params);
            }

            /*计算查询时间跨度*/
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long beginTime = df.parse(params.get("start_date").toString()).getTime();
            long endTime = df.parse(params.get("end_date").toString()).getTime();
            //间隔点数
            long betweenDays = (long)((endTime - beginTime) / (1000 * 60 * 60 *24) );
            //天数
            int maxday = 7;

            if(!userList.isEmpty()&&userList.size()>0){

                params.put("userList", userList);
                
                list = memberInfoDao.queryUserDailyReportList_User(params);

                Map<String,Object> login_num_map = new HashMap<String,Object>();
                Map<String,Object> page_view_map = new HashMap<String,Object>();

                //如果查询跨度<=阈值，则统计日志相关信息
                if(betweenDays < maxday){
                    List<Map<String,Object>> list_login = memberInfoDao.queryUserDailyReportList_Log_Login(params);//数据获取-日志 -登录数（新）
                    login_num_map = list2Map(list_login,"USER_NAME","LOGIN_NUM");
                    List<Map<String,Object>> list_page = memberInfoDao.queryUserDailyReportList_Log_Page(params);//数据获取-日志 -访问数（新）
                    page_view_map = list2Map(list_page,"USER_NAME","PAGE_VIEW");
                }

                List<Map<String,Object>> list_allorder = memberInfoDao.queryUserDailyReportList_Allorder(params);//数据获取-全部销售订单 （新）
                Map<String,Object> all_order_count_map = list2Map(list_allorder,"USER_NAME","ALL_ORDER_COUNT");
                Map<String,Object> all_order_product_count_map = list2Map(list_allorder,"USER_NAME","ALL_ORDER_PRODUCT_COUNT");
                Map<String,Object> all_order_product_money_map = list2Map(list_allorder,"USER_NAME","ALL_ORDER_PRODUCT_MONEY");

                List<Map<String,Object>> list_preorder = memberInfoDao.queryUserDailyReportList_Preorder(params);//数据获取-预定订单 （新）
                Map<String,Object> preorder_product_money_map = list2Map(list_preorder,"USER_NAME","PRE_ORDER_PRODUCT_MONEY");


                List<Map<String,Object>> list_obliorder = memberInfoDao.queryUserDailyReportList_Obliorder(params);//数据获取-待付款订单 （新）
                Map<String,Object> obli_order_count_map = list2Map(list_obliorder,"USER_NAME","OBLI_ORDER_COUNT");
                Map<String,Object> obli_order_product_count_map = list2Map(list_obliorder,"USER_NAME","OBLI_ORDER_PRODUCT_COUNT");
                Map<String,Object> obli_order_product_money_map = list2Map(list_obliorder,"USER_NAME","OBLI_ORDER_PRODUCT_MONEY");

                List<Map<String,Object>> list_unshiporder = memberInfoDao.queryUserDailyReportList_Unshiporder(params);//数据获取-未发订单 （新）
                Map<String,Object> unship_order_count_map = list2Map(list_unshiporder,"USER_NAME","UNSHIP_ORDER_COUNT");
                Map<String,Object> unship_order_product_count_map = list2Map(list_unshiporder,"USER_NAME","UNSHIP_ORDER_PRODUCT_COUNT");
                Map<String,Object> unship_order_product_money_map = list2Map(list_unshiporder,"USER_NAME","UNSHIP_ORDER_PRODUCT_MONEY");

                List<Map<String,Object>> list_shiporder = memberInfoDao.queryUserDailyReportList_Shiporder(params);//数据获取-发货订单 （新）
                Map<String,Object> ship_order_count_map = list2Map(list_shiporder,"USER_NAME","SHIP_ORDER_COUNT");
                Map<String,Object> ship_order_product_count_map = list2Map(list_shiporder,"USER_NAME","SHIP_ORDER_PRODUCT_COUNT");
                Map<String,Object> ship_order_product_money_map = list2Map(list_shiporder,"USER_NAME","SHIP_ORDER_PRODUCT_MONEY");

                List<Map<String,Object>> list_refund = memberInfoDao.queryUserDailyReportList_Refund(params);//数据获取-退款订单 （新）
                Map<String,Object> refund_order_count_map = list2Map(list_refund,"USER_NAME","REFUND_ORDER_COUNT");
                Map<String,Object> refund_order_product_count_map = list2Map(list_refund,"USER_NAME","REFUND_ORDER_PRODUCT_COUNT");
                Map<String,Object> refund_order_product_money_map = list2Map(list_refund,"USER_NAME","REFUND_ORDER_PRODUCT_MONEY");

                List<Map<String,Object>> list_returns = memberInfoDao.queryUserDailyReportList_Returns(params);//数据获取-退货订单 （新）
                Map<String,Object> returns_order_count_map = list2Map(list_returns,"USER_NAME","RETURNS_ORDER_COUNT");
                Map<String,Object> returns_order_product_count_map = list2Map(list_returns,"USER_NAME","RETURNS_ORDER_PRODUCT_COUNT");
                Map<String,Object> returns_order_product_money_map = list2Map(list_returns,"USER_NAME","RETURNS_ORDER_PRODUCT_MONEY");

                Map<String,Object> tempMap = null;
                //拼装相关统计信息
                for(int i=0;i<list.size();i++){
                    tempMap = list.get(i);
                    String user_name = tempMap.get("USER_NAME").toString();

                    //如果查询跨度<=阈值，则统计日志相关信息
                    if(betweenDays < maxday){
                        tempMap.put("LOGIN_NUM", login_num_map.get(user_name)==null?0:login_num_map.get(user_name));
                        tempMap.put("PAGE_VIEW", page_view_map.get(user_name)==null?0:page_view_map.get(user_name));
                    }else{
                        tempMap.put("LOGIN_NUM", "<font title=\"由于查询时间跨度大于"+maxday+"天，为了更快的展示统计结果，此处暂不统计!\">暂不统计</font>");
                        tempMap.put("PAGE_VIEW", "<font title=\"由于查询时间跨度大于"+maxday+"天，为了更快的展示统计结果，此处暂不统计!\">暂不统计</font>");
                    }

                    tempMap.put("ALL_ORDER_COUNT", all_order_count_map.get(user_name)==null?0:all_order_count_map.get(user_name));
                    tempMap.put("ALL_ORDER_PRODUCT_COUNT", all_order_product_count_map.get(user_name)==null?0:all_order_product_count_map.get(user_name));
                    tempMap.put("ALL_ORDER_PRODUCT_MONEY", all_order_product_money_map.get(user_name)==null?0:all_order_product_money_map.get(user_name));

                    tempMap.put("PRE_ORDER_PRODUCT_MONEY", preorder_product_money_map.get(user_name)==null?0:preorder_product_money_map.get(user_name));

                    tempMap.put("OBLI_ORDER_COUNT", obli_order_count_map.get(user_name)==null?0:obli_order_count_map.get(user_name));
                    tempMap.put("OBLI_ORDER_PRODUCT_COUNT", obli_order_product_count_map.get(user_name)==null?0:obli_order_product_count_map.get(user_name));
                    tempMap.put("OBLI_ORDER_PRODUCT_MONEY", obli_order_product_money_map.get(user_name)==null?0:obli_order_product_money_map.get(user_name));

                    tempMap.put("UNSHIP_ORDER_COUNT", unship_order_count_map.get(user_name)==null?0:unship_order_count_map.get(user_name));
                    tempMap.put("UNSHIP_ORDER_PRODUCT_COUNT", unship_order_product_count_map.get(user_name)==null?0:unship_order_product_count_map.get(user_name));
                    tempMap.put("UNSHIP_ORDER_PRODUCT_MONEY", unship_order_product_money_map.get(user_name)==null?0:unship_order_product_money_map.get(user_name));

                    tempMap.put("SHIP_ORDER_COUNT", ship_order_count_map.get(user_name)==null?0:ship_order_count_map.get(user_name));
                    tempMap.put("SHIP_ORDER_PRODUCT_COUNT", ship_order_product_count_map.get(user_name)==null?0:ship_order_product_count_map.get(user_name));
                    tempMap.put("SHIP_ORDER_PRODUCT_MONEY", ship_order_product_money_map.get(user_name)==null?0:ship_order_product_money_map.get(user_name));

                    tempMap.put("REFUND_ORDER_COUNT", refund_order_count_map.get(user_name)==null?0:refund_order_count_map.get(user_name));
                    tempMap.put("REFUND_ORDER_PRODUCT_COUNT", refund_order_product_count_map.get(user_name)==null?0:refund_order_product_count_map.get(user_name));
                    tempMap.put("REFUND_ORDER_PRODUCT_MONEY", refund_order_product_money_map.get(user_name)==null?0:refund_order_product_money_map.get(user_name));

                    tempMap.put("RETURNS_ORDER_COUNT", returns_order_count_map.get(user_name)==null?0:returns_order_count_map.get(user_name));
                    tempMap.put("RETURNS_ORDER_PRODUCT_COUNT", returns_order_product_count_map.get(user_name)==null?0:returns_order_product_count_map.get(user_name));
                    tempMap.put("RETURNS_ORDER_PRODUCT_MONEY", returns_order_product_money_map.get(user_name)==null?0:returns_order_product_money_map.get(user_name));


                    float ship_order_product_count = ship_order_product_count_map.get(user_name)==null?0:Float.parseFloat(ship_order_product_count_map.get(user_name).toString());
                    float returns_order_product_count = returns_order_product_count_map.get(user_name)==null?0:Float.parseFloat(returns_order_product_count_map.get(user_name).toString());

                    float ship_order_product_money = ship_order_product_money_map.get(user_name)==null?0:Float.parseFloat(ship_order_product_money_map.get(user_name).toString());
                    float returns_order_product_money = returns_order_product_money_map.get(user_name)==null?0:Float.parseFloat(returns_order_product_money_map.get(user_name).toString());

                    tempMap.put("ACTUAL_SALE_PRODUCT_COUNT", ship_order_product_count-returns_order_product_count);
                    tempMap.put("ACTUAL_SALE_PRODUCT_MONEY", ship_order_product_money-returns_order_product_money);

                }
            }
            //总合计
            Map<String,Object> map = new HashMap<String,Object>();

            //如果查询跨度<=阈值，则统计日志相关信息
            if(betweenDays < maxday){
                map = memberInfoDao.queryUserDailyReportTotal_Log_Login(params);//数据获取-日志 -登录数（新）
                map.putAll(memberInfoDao.queryUserDailyReportTotal_Log_Page(params));//数据获取-日志 -访问数（新）
            }else{
                map.put("LOGIN_NUM", "<font title=\"由于查询时间跨度大于"+maxday+"天，为了更快的展示统计结果，此处暂不统计!\">暂不统计</font>");
                map.put("PAGE_VIEW", "<font title=\"由于查询时间跨度大于"+maxday+"天，为了更快的展示统计结果，此处暂不统计!\">暂不统计</font>");
            }

            map.putAll(memberInfoDao.queryUserDailyReportTotal_Allorder(params));//数据获取-全部销售订单 （新）

            map.putAll(memberInfoDao.queryUserDailyReportTotal_Preorder(params));//数据获取-预定订单 （新）

            map.putAll(memberInfoDao.queryUserDailyReportTotal_Obliorder(params));//数据获取-待付款订单 （新）

            map.putAll(memberInfoDao.queryUserDailyReportTotal_Unshiporder(params));//数据获取-未发订单 （新）


            Map<String,Object> total_ship_order_product_count_map = memberInfoDao.queryUserDailyReportTotal_Shiporder(params);
            map.putAll(total_ship_order_product_count_map);//数据获取-发货订单 （新）

            map.putAll(memberInfoDao.queryUserDailyReportTotal_Refund(params));//数据获取-退款订单 （新）

            Map<String,Object> total_returns_order_product_money_map = memberInfoDao.queryUserDailyReportTotal_Returns(params);
            map.putAll(total_returns_order_product_money_map);//数据获取-退货订单 （新）



            Float total_ship_order_product_count = Float.valueOf(total_ship_order_product_count_map.get("SHIP_ORDER_PRODUCT_COUNT").toString());
            Float total_returns_order_product_count = Float.valueOf(total_returns_order_product_money_map.get("RETURNS_ORDER_PRODUCT_COUNT").toString());

            Float total_ship_order_product_money = Float.valueOf(total_ship_order_product_count_map.get("SHIP_ORDER_PRODUCT_MONEY").toString());
            Float total_returns_order_product_money = Float.valueOf(total_returns_order_product_money_map.get("RETURNS_ORDER_PRODUCT_MONEY").toString());

            map.put("ACTUAL_SALE_PRODUCT_COUNT", total_ship_order_product_count-total_returns_order_product_count);
            map.put("ACTUAL_SALE_PRODUCT_MONEY", total_ship_order_product_money-total_returns_order_product_money);

            retMap.put("totalData",map);
            retMap.put("list", list);
            gr.setState(true);
            gr.setMessage("获取用户销售日报表列表成功");
            gr.setObj(retMap);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询用户销售日报表列表失败");
        }
        return gr;
    }


    /**
     * 将list转换成map
     * @param list
     * @param mapKey
     * @param valueKey
     * @return
     */
    private Map<String,Object> list2Map(List<Map<String, Object>> list ,String mapKey,String valueKey){
        Map<String,Object> returnMap = new HashMap<String,Object>();
        int size = list.size();
        String key ="";
        Object value ="";
        for(int i=0;i<size;i++)
        {
            key = list.get(i).get(mapKey).toString();
            value = list.get(i).get(valueKey);
            returnMap.put(key, value);
        }
        return returnMap;
    }


    /**
     * 查询全部订单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryAllOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);

            if(params.containsKey("order_state")&&params.get("order_state")!=null&&!"".equals(params.get("order_state").toString())){
                //未付款列表
                int total=memberInfoDao.queryOrderRecordCount_NoPayorder(params);
                List<Map<String, Object>> list = memberInfoDao.queryOrderRecordList_NoPayorder(params);
                gr.setState(true);
                gr.setMessage("查询未支付订单列表成功");
                gr.setObj(list);
                gr.setTotal(total);
            }else{
                //未付款列表
                int total=memberInfoDao.queryOrderRecordCount_Allorder(params);
                List<Map<String, Object>> list = memberInfoDao.queryOrderRecordList_Allorder(params);
                gr.setState(true);
                gr.setMessage("查询全部已支付订单列表成功");
                gr.setObj(list);
                gr.setTotal(total);
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }

    /**
     * 查询预定订单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryPreOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            int total=memberInfoDao.queryOrderRecordCount_Preorder(params);
            List<Map<String, Object>> list = memberInfoDao.queryOrderRecordList_Preorder(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }

    /**
     * 查询待付款订单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryObliOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            int total=memberInfoDao.queryOrderRecordCount(params);
            List<Map<String, Object>> list = memberInfoDao.queryOrderRecord(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }
    /**
     * 查询未发货订单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryUnshipOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            int total=memberInfoDao.queryUnshipOrderRecordCount(params);
            List<Map<String, Object>> list = memberInfoDao.queryUnshipOrderRecord(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }
    /**
     * 查询发货订单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryShipOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            int total=memberInfoDao.queryShipOrderRecordCount(params);
            List<Map<String, Object>> list = memberInfoDao.queryShipOrderRecord(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }
    /**
     * 查询退款单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryRefundOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            int total=memberInfoDao.queryRefundOrderRecordCount(params);
            List<Map<String, Object>> list = memberInfoDao.queryRefundOrderRecord(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }
    /**
     * 查询退货单明细
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryReturnOrder(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            params.put("jdbc_user", jdbc_user);
            int total=memberInfoDao.queryReturnOrderRecordCount(params);
            List<Map<String, Object>> list = memberInfoDao.queryReturnOrderRecord(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }

    /**
     * 查询浏览记录和登录次数
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult queryUserBrowseRecord(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_name");
                return gr;
            }
          //分页
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            int total=memberInfoDao.queryBrowseRecordCount(params);
            List<Map<String, Object>> list = memberInfoDao.queryBrowseRecord(params);

            gr.setState(true);
            gr.setMessage("查询成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }

        return gr;
    }
    /**
     * 取消月结
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult cancelMonthlyStatement(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数user_id");
                return pr;
            }
            long user_id = Long.parseLong(params.get("user_id").toString());
            Map<String, Object> banAccount = memberInfoDao.queryBankAccount(user_id);
            if(StringUtils.isEmpty(banAccount.get("CREDIT_MONEY_BALANCE")) || StringUtils.isEmpty(banAccount.get("CREDIT_MONEY_USE")) || StringUtils.isEmpty(banAccount.get("CREDIT_MONEY_USE"))){
                pr.setState(false);
                pr.setMessage("用户信息异常");
                return pr;
            }
            //取消月结 校验
            if(Float.parseFloat(banAccount.get("CREDIT_MONEY").toString()) == 0){
                pr.setState(false);
                pr.setMessage("当前用户没有月结额度，无需取消");
                return pr;
            }
            if(Float.parseFloat(banAccount.get("CREDIT_MONEY_USE").toString()) == 0 &&
                Float.parseFloat(banAccount.get("CREDIT_MONEY").toString()) == Float.parseFloat(banAccount.get("CREDIT_MONEY_BALANCE").toString())
            ){
                //取消月结
                if(memberInfoDao.cancelMonthlyStatement(user_id) >0){
                    memberInfoDao.updateUserAccountCode(user_id);
                    pr.setState(true);
                    pr.setMessage("操作成功");
                }else{
                    pr.setState(false);
                    pr.setMessage("操作失败");
                }
            }else{
                pr.setState(false);
                pr.setMessage("当前用户月结额度已使用，暂不允许取消月结");
                return pr;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }


    /**
     * 会员门店管理列表
     * @param request
     * @return
     */
    public GridResult queryMemberStoreList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(params.get("approval_state")))&&params.get("approval_state") instanceof String){
                params.put("approval_state",(params.get("approval_state")+"").split(","));
            }

            //会员启用/禁用状态筛选
            if ((!StringUtils.isEmpty(params.get("user_state")))&&params.get("user_state") instanceof String) {
                params.put("user_state", (params.get("user_state")+"").split(","));
            }

            List<Map<String,Object>> list = null;
            int count=memberInfoDao.queryMemberStoreInfoCount(params);
            list = memberInfoDao.queryMemberStoreInfoList(params);
            if (list != null && list.size() > 0) {
                gr.setState(true);
                gr.setMessage("查询成功!");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
    }

    /**
     * 新增会员门店管理
     * @param request
     * @return
     */
    @Transactional
    public GridResult addMemberStore(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("user_id"))){
                gr.setState(false);
                gr.setMessage("缺少参数user_id");
                return gr;
            }
            //判断是否有重复会员门店名称
            int storeNameCount=memberInfoDao.queryMemberStoreNameCount(params.get("store_name").toString());
            if(storeNameCount>0){
                gr.setState(false);
                gr.setMessage("该门店名称已经存在");
                return gr;
            }
            params.put("id", params.get("user_id"));
            Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
            //获取会员相关信息
            params.put("user_id", userInfo.get("USER_NAME"));
            params.put("login_name", userInfo.get("LOGIN_NAME"));
            params.put("user_manage_name", userInfo.get("USER_MANAGE_NAME"));
            params.put("approval_state", "0");
            params.put("user_store_resource", "0");

            int count=memberInfoDao.insertMemberStore(params);
            if(count>0){
                //记录会员门店管理新增日志
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【会员门店管理】新增");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                gr.setState(true);
                gr.setMessage("添加成功");
            }else{
                gr.setState(false);
                gr.setMessage("添加失败");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
    }

    /**
     * 会员门店详情
     * @param request
     * @return
     */
    public ProcessResult queryMemberStoreDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            Map<String,Object> memberStore = memberInfoDao.queryMemberStoreDetail(params);
            pr.setState(true);
            pr.setMessage("获取信息数据成功");
            pr.setObj(memberStore);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
    }

    /**
     * 会员门店审批
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult memberStoreApproval(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("approval_state"))){
                pr.setState(false);
                pr.setMessage("缺少参数approval_state");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if(memberInfoDao.updateMemberStoreApprovalState(params)>0){
                //记录配置会员门店管理审核日志
                Map<String,Object> detail=memberInfoDao.queryMemberStoreDetail(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【会员门店管理】审核");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", detail.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", detail.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("审批成功");
            } else{
                pr.setState(false);
                pr.setMessage("审批失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }


    /**
     * 会员门店编辑
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult memberStoreEdit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            Map<String,Object> memberStoreDetail=memberInfoDao.queryMemberStoreDetail(params);
            //会员门店编辑 门店名称、门店省、市、县、门店详细地址、门店定位地址 任意一个发生编辑都需重新审批
            if (!params.get("STORE_NAME").toString().equals(memberStoreDetail.get("STORE_NAME"))
                    || !params.get("STORE_ADDRESS").toString().equals(memberStoreDetail.get("STORE_ADDRESS"))
                    || Integer.parseInt(params.get("province").toString()) != Integer.parseInt(memberStoreDetail.get("PROVINCE").toString())
                    || Integer.parseInt(params.get("city").toString()) != Integer.parseInt(memberStoreDetail.get("CITY").toString())
                    || Integer.parseInt(params.get("area").toString()) != Integer.parseInt(memberStoreDetail.get("COUNTY").toString())
                    || (!StringUtils.isEmpty(params.get("longitude"))&&!StringUtils.isEmpty(params.get("latitude")))) {
                params.put("edit_flag", 1);
            }
            if(memberInfoDao.memberStoreEdit(params)>0){
                //记录配置【会员门店管理】编辑日志
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【会员门店管理】编辑");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", memberStoreDetail.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", memberStoreDetail.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("编辑成功");
            } else{
                pr.setState(false);
                pr.setMessage("编辑失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 会员门店启用禁用
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult memberStoreUpdateState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("user_store_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数user_store_id");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("user_store_state"))){
                pr.setState(false);
                pr.setMessage("缺少参数user_store_state");
                return pr;
            }
            if(memberInfoDao.memberStoreUpdateState(params)>0){
                pr.setState(true);
                pr.setMessage("状态修改成功");
            } else{
                pr.setState(false);
                pr.setMessage("状态修改失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }


    /**
     * 会员批量修改业务归属
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult batchEditBussiness(HttpServletRequest request) throws Exception{
        Map<String, Object> param=new HashMap<String,Object>();
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("ids"))){
                pr.setState(false);
                pr.setMessage("缺少参数ids");
                return pr;
            }
            //获取业务经理id业务经理名称
            if(!StringUtils.isEmpty(params.get("market_supervision_user_id"))){
                param.put("market_supervision_user_id", params.get("market_supervision_user_id"));
                String market_supervision_user_realna=memberInfoDao.querySysUserInfoById(Integer.parseInt(param.get("market_supervision_user_id").toString()));
                param.put("market_supervision_user_realna", market_supervision_user_realna);
            }
            //获取门店id门店名称
            if(!StringUtils.isEmpty(params.get("store_id"))){
                param.put("store_id", params.get("store_id"));
                String store_name=storeInfoDao.queryStoreNameById(Integer.parseInt(param.get("store_id").toString()));
                param.put("store_name", store_name);
            }
            //获取业务员
            if(!StringUtils.isEmpty(params.get("referee_user_id"))){
                param.put("referee_user_id", params.get("referee_user_id"));
                String referee_user_realname=memberInfoDao.querySysUserInfoById(Integer.parseInt(param.get("referee_user_id").toString()));
                param.put("referee_user_name", referee_user_realname);
            }
            //获取站点
            if(!StringUtils.isEmpty(params.get("site_id"))){
                param.put("site_id", params.get("site_id"));
            }
            //获取站点id
            String ids=params.get("ids").toString();
            for(String id:ids.split(",")){
                param.put("id", id);
              //记录批量修改业务归属日志
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(param);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 2);
                logMap.put("REMARK", "编辑【批量修改业务归属】");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                if(memberInfoDao.updateMemberBussiness(param)>0){
                    Map<String,Object> memberInfoMap = new HashMap<String, Object>();
                    for (Map.Entry<String, Object> entry : param.entrySet()) {
                        memberInfoMap.put(entry.getKey().toUpperCase(),entry.getValue());
                    }
                    memberInfoMap.put("operationType","edit");
                    //调用oa同步接口
                    ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + "/sys_dictionary/sync_formal_customer", memberInfoMap);
                    if (!oAPr.getState()) {
                        throw new RuntimeException("OA系统会员数据同步失败");
                    }
                }else{
                    throw new RuntimeException("修改失败");
                }
            }
            pr.setState(true);
            pr.setMessage("修改成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }

        return pr;
    }


    /**
     * 会员政策期修改
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult updatePolicyPeriod(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("user_policy_period"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            if(memberInfoDao.memberUpdatePolicyPeriod(params)>0){
                //记录会员政策期设置日志
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【会员政策期设置】");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("政策期设置成功");
            } else{
                pr.setState(false);
                pr.setMessage("政策期设置失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }


    /**
     * 会员状态操作日志列表
     * @param request
     * @return
     */
    public GridResult memberStateLogsList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            if(StringUtils.isEmpty(params.get("user_id"))){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            List<Map<String,Object>> list = null;
            int count=memberInfoDao.queryMemberStateLogsCount(params);
            list = memberInfoDao.queryMemberStateLogsList(params);
            if (list != null && list.size() > 0) {
                gr.setState(true);
                gr.setMessage("查询成功!");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
    }

    /**
     * 会员认证审批列表
     * @param request
     * @return
     */
    public GridResult memberCertificationApprovalList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(params.get("state")))&&params.get("state") instanceof String){
                params.put("state",(params.get("state")+"").split(","));
            }
            List<Map<String,Object>> list = null;
            int count=memberInfoDao.queryMemberCertificationApprovalCount(params);
            list = memberInfoDao.queryMemberCertificationApprovalList(params);
            if (list != null && list.size() > 0) {
                gr.setState(true);
                gr.setMessage("查询成功!");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e);
        }
        return gr;
    }

    /**
     * 会员认证审批详情
     * @param request
     * @return
     */
    public GridResult memberCertificationApprovalDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String,Object> detail = memberInfoDao.queryMemberCertificationApprovalDetail(params);
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
     * 会员认证审核
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult memberCertificationApproval(HttpServletRequest request) throws Exception{
            Map<String,Object> params = null;
            Map<String,Object> userInfoParam=new HashMap<String,Object>();
            ProcessResult pr = new ProcessResult();
            try{
                String json = HttpUtil.getRequestInputStream(request);
                params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
                //查询会员认证信息
                Map<String,Object> detail=memberInfoDao.queryMemberCertificationApprovalDetail(params);
                if(Integer.parseInt(detail.get("STATE").toString())!=0){
                    pr.setState(false);
                    pr.setMessage("当前会员认证状态异常");
                    return pr;
                }
                //查询会员相关信息
                userInfoParam.put("id", detail.get("USER_ID"));
                Map<String,Object> userInfo=memberInfoDao.queryMemberInfoById(userInfoParam);
                userInfo.put("user_id", detail.get("USER_ID"));
                //params.put("user_id", detail.get("USER_ID"));
                if(Integer.parseInt(params.get("state").toString())==1){
                    //普通会员需要校验认证信息真实姓名和会员注册姓名一致
                    if(Integer.parseInt(userInfo.get("USER_TYPE").toString())==1&&!detail.get("USER_REAL_NAME").equals(userInfo.get("USER_MANAGE_NAME"))){
                        pr.setState(false);
                        pr.setMessage("会员认证姓名与注册姓名不一致");
                        return pr;
                    }
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("user_id",userInfo.get("ID") );
                    param.put("name",userInfo.get("USER_COMPANY_NAME") );
                    param.put("alias_name",userInfo.get("USER_COMPANY_NAME") );
                    param.put("service_phone", "0577-56578888");
                    param.put("id_card_name",detail.get("USER_REAL_NAME"));
                    param.put("id_card_num",detail.get("USER_MANAGE_CARDID"));
                    param.put("store_address",detail.get("ADDRESS"));
                    param.put("id_card_hand_img_url", detail.get("USER_MANAGE_CARDID_IMG"));
                    param.put("store_front_img_url", detail.get("USER_COMPANY_IMG"));
                    param.put("province","");
                    param.put("city","");
                    param.put("district","");
                    pr=HttpClientUtil.post(pay_service_url+"/bankAccount/query",param);
                    if(pr.getState()){
                        Map<String, Object> retMap=(Map<String, Object>) pr.getObj();
                        userInfo.put("bank_account", retMap.get("bank_account"));
                        userInfo.put("sub_merchant_id", retMap.get("sub_merchant_id"));
                    }else{
                        logger.error("注册银行会员子账户失败"+pr.getMessage());
                        throw new RuntimeException("审核失败:"+pr.getMessage());
                    }
                    //会员账户表更新见证宝相关信息
                    if(memberInfoDao.updateBankAccountInfo(userInfo)>0 && memberInfoDao.updateBankCardUserInfo(params)>0){
                        pr.setState(true);
                        pr.setMessage("审批成功");
                    }else{
                        pr.setState(false);
                        throw new RuntimeException("审批失败");
                    }
                }else{
                    //将银行卡会员资料信息设置为驳回状态
                    int count=memberInfoDao.updateBankCardUserInfo(params);
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

    public Object queryForPostSendNotice(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj,EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params);
        return  Transform.GetPacket(json,ProcessResult.class,EsbConfig.ERP_REVERSE_KEY_NAME);
    }

    /**
     * 会员列表(下拉框)
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryMemberStoreSelectList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            pr.setState(true);
            pr.setMessage("查询成功");
            pr.setObj(memberInfoDao.queryMemberStoreSelectList(params));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 退货扣款规则
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult returnWithdrawing(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            //退货扣款规则
            if(memberInfoDao.memberUpdateDeductions(params)>0){
                //记录会员退货扣款规则日志
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【退货扣款规则】");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("退货扣款规则设置成功");
            }else{
                pr.setState(false);
                pr.setMessage("退货扣款规则设置失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    /**
     * 用户级别设置
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult userLevel(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> params = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            //用户级别设置
            if(memberInfoDao.memberUpdateUserLevel(params)>0){
                //记录会员用户级别设置日志
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【用户级别设置】");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("用户级别设置成功");
            }else{
                pr.setState(false);
                pr.setMessage("用户级别设置失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 会员子账户查询
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public ProcessResult queryMemberSubAccountList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            pr.setObj(memberInfoDao.queryMemberSubAccount(params));
            pr.setState(true);
            pr.setMessage("查询成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 会员子账户更新
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult updateMemberSubAccount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断子账号用户名是否重复
            int count =memberInfoDao.queryMemberSubAccountLoginName(params);
            if(count>0){
                pr.setState(false);
                pr.setMessage("用户名已存在");
                return pr;
            }
            if (params.get("sub_account_pwd") != null) {
                // 密码加密
                byte[] encryptResult = Crypt.encrypt(params.get("sub_account_pwd").toString(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.USER_PWD_KEY));
                params.put("sub_account_pwd", Base64.encode(encryptResult));
            }
            if (memberInfoDao.updateMemberSubAccount(params)>0) {
                //记录会员子账号编辑日志
                params.put("id", params.get("member_id"));
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【子账号管理】编辑");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("更新成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除会员子账号
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult deleteMemeberSubAccount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (memberInfoDao.deleteMemberSubAccount(params)>0) {
                //记录会员子账号删除日志
                params.put("id", params.get("member_id"));
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【子账号管理】删除");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("删除成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加会员子账号
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult addMemeberSubAccount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            // 密码加密
            byte[] encryptResult = Crypt.encrypt(params.get("sub_account_pwd").toString(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.USER_PWD_KEY));
            params.put("sub_account_pwd", Base64.encode(encryptResult));
            //判断子账号用户名是否重复
            int count =memberInfoDao.queryMemberSubAccountLoginName(params);
            if(count>0){
                pr.setState(false);
                pr.setMessage("用户名已存在");
                return pr;
            }
            if (memberInfoDao.addMemeberSubAccount(params)>0) {
                //记录会员子账号新增日志
                params.put("id", params.get("member_id"));
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【子账号管理】新增");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("添加成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 三方订单启/禁用
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult otherSyncState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            Map<String,Object> params = null;
            Map<String,Object> memberStateLogsMap = new HashMap<String,Object>();
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(params==null||StringUtils.isEmpty(params.get("id"))||StringUtils.isEmpty(params.get("other_sync_state"))){
                pr.setState(false);
                pr.setMessage("参数错误，缺少必要参数");
                return pr;
            }
            if(memberInfoDao.updateUserInfoForOtherSyncState(params)>0){
                //记录会员对接聚水潭日志
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 3);
                logMap.put("REMARK", "配置【对接聚水潭】");
                logMap.put("CREATE_USER_ID", params.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                pr.setState(true);
                pr.setMessage("操作成功");
            }else{
                pr.setState(false);
                pr.setMessage("操作失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e);
        }
        return pr;
    }

    /**
     * 查询用户预审信息
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryPreChkUser(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台登录用户
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> paramsMap = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(paramsMap.get("user_name"))){
                pr.setState(false);
                pr.setMessage("缺少参数【user_name】");
                return pr;
            }
//            MemberInfo memberInfo = memberInfoDao.queryUserInfoPreChkInfo(paramsMap.get("user_name").toString());
//            if(memberInfo!=null){
//                memberInfo.setUser_pwd(null);
//                memberInfo.setOpenid(null);
//                pr.setState(true);
//                pr.setMessage("查询预审用户信息成功！");
//                pr.setObj(memberInfo);
//            }else{
//                pr.setState(false);
//                pr.setMessage("查询预审用户信息不存在！");
//                pr.setObj(null);
//            }
            return pr;
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询用户信息失败"+e);
        }
        return pr;
    }

    /**
     * 会员送货入户设置信息
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult deliveryHomeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台登录用户
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> paramsMap = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(paramsMap.get("user_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数【user_id】");
                return pr;
            }
            Map<String,Object> info=memberInfoDao.queryMemberDeliveryHomeByUserId(paramsMap);
            pr.setMessage("查询成功！");
            pr.setState(true);
            if(info!=null){
                pr.setObj(info);
            }
            return pr;
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询用户信息失败"+e);
        }
        return pr;
    }

    /**
     * 会员送货入户设置
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult deliveryHomeSet(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            Map<String,Object> params = null;
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("user_id"))){
                pr.setState(false);
                pr.setMessage("参数错误，缺少用户ID");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("is_support"))){
                pr.setState(false);
                pr.setMessage("参数错误，缺少是否支持发货入户");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("effect_begin_date"))){
                pr.setState(false);
                pr.setMessage("参数错误，缺少有效开始时间");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("effect_end_date"))){
                pr.setState(false);
                pr.setMessage("参数错误，缺少有效结束时间");
                return pr;
            }
            //判断当前用户是否已设置送货入户设置
            Map<String,Object> info=memberInfoDao.queryMemberDeliveryHomeByUserId(params);
            int count=0;
            if(info==null){
                //新增
                count=memberInfoDao.insertMemberDeliveryHome(params);
            }else{
                //编辑
                count=memberInfoDao.updateMemberDeliveryHome(params);
            }
            if(count<=0){
                throw new RuntimeException("会员送货入户设置失败");
            }
            //记录会员送货入户设置日志
            params.put("id", params.get("user_id"));
            Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(params);
            Map<String,Object> logMap=new HashMap<String,Object>();
            logMap.put("USER_TYPE", 3);
            logMap.put("OPERATE_ID", 3);
            logMap.put("REMARK", "配置【会员送货入户】");
            logMap.put("CREATE_USER_ID", params.get("public_user_id"));
            logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
            logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
            memberInfoDao.insertUserOperationLog(logMap);
            pr.setMessage("会员送货入户设置成功");
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 会员账号预审删除
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult accountPreRemove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            Map<String,Object> params = null;
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("user_name"))){
                pr.setState(false);
                pr.setMessage("参数错误，缺少用户user_name");
                return pr;
            }
            int count=memberInfoDao.accountPreRemove(params);
            if(count<=0){
                pr.setState(false);
                throw new RuntimeException("会员账号预审删除失败");
            }
            //记录会员账号预审删除日志
            Map<String,Object> userInfo = memberInfoDao.queryMemberInfoRecordById(params);
            Map<String,Object> logMap=new HashMap<String,Object>();
            logMap.put("USER_TYPE", 3);
            logMap.put("OPERATE_ID", 5);
            logMap.put("REMARK", "删除【会员账号预审】");
            logMap.put("CREATE_USER_ID", params.get("public_user_id"));
            logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
            logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
            memberInfoDao.insertUserOperationLog(logMap);
            pr.setMessage("会员账号预审删除成功");
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 会员快递设置
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult memberLogisticsSet(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台用户数据
            Map<String,Object> paramMap = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(paramMap.get("user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            //先设置原数据为无效
            memberInfoDao.updateUserLogisticsState(paramMap);
            if(!StringUtils.isEmpty(paramMap.get("enable_logistics"))||!StringUtils.isEmpty(paramMap.get("disabled_logistics"))) {
                if(memberInfoDao.insertUserLogistics(paramMap)<=0){
                    throw new RuntimeException("会员快递设置异常");
                }
            }
            //记录会员快递设置日志
            paramMap.put("id", paramMap.get("user_id"));
            Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(paramMap);
            Map<String,Object> logMap=new HashMap<String,Object>();
            logMap.put("USER_TYPE", 3);
            logMap.put("OPERATE_ID", 3);
            logMap.put("REMARK", "配置【会员快递配置】");
            logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
            logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
            logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
            memberInfoDao.insertUserOperationLog(logMap);
            pr.setState(true);
            pr.setMessage("会员快递设置成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("会员快递设置，异常原因："+e.getMessage());
        }
        return pr;
    }

    /**
     * 会员快递详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryMeberLogisticsDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if (StringUtils.isEmpty(paramMap.get("user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> detail=memberInfoDao.queryUserLogisticsDetail(paramMap);
            pr.setState(true);
            pr.setMessage("查询成功");
            pr.setObj(detail);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

    /**
     * 控货详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryControlDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if (StringUtils.isEmpty(paramMap.get("user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //控货设置信息
            Map<String, Object> infoMap=memberInfoDao.queryControlSetInfo(paramMap);
            if(infoMap != null) {
                paramMap.put("control_set_id", infoMap.get("ID"));
                retMap.put("CONTROL_SET_ID", infoMap.get("ID"));
                retMap.put("CONTROL_RESTRICT", infoMap.get("CONTROL_RESTRICT"));
                list = memberInfoDao.queryControlSetDetail(paramMap);
                if("1".equals(infoMap.get("CONTROL_RESTRICT").toString())) {//全部品牌
                    Map<String, Object> tempMap = list.get(0);
                    retMap.put("DURATION_STATE", tempMap.get("DURATION_STATE"));
                    retMap.put("DURATION_START_DATE", tempMap.get("DURATION_START_DATE"));
                    retMap.put("DURATION_END_DATE", tempMap.get("DURATION_END_DATE"));
                    retMap.put("TARGET_STATE", tempMap.get("TARGET_STATE"));
                    retMap.put("TARGET_TYPE", tempMap.get("TARGET_TYPE"));
                    retMap.put("TARGET_VALUE", tempMap.get("TARGET_VALUE"));
                    //品牌默认数据
                    retMap.put("list", memberInfoDao.queryBrandList_Default(paramMap));
                }else {//单个品牌
                    retMap.put("list", list);
                }
            }else{
                //品牌默认数据
                retMap.put("list", memberInfoDao.queryBrandList_Default(paramMap));
            }
            pr.setState(true);
            pr.setMessage("查询控货设置详情成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

    /**
     * 控货设置
     * @param request
     * @return
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public ProcessResult controlSet(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台用户数据
            Map<String,Object> paramMap = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(paramMap.get("user_id"))||StringUtils.isEmpty(paramMap.get("control_restrict"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            //是否存在控货设置
            if(memberInfoDao.queryControlSetIsExists(paramMap) > 0) {//存在
                if("2".equals(paramMap.get("control_restrict").toString())) {//单个品牌
                    //更新控货设置
                    memberInfoDao.updateControlSet(paramMap);
                    //删除控货设置明细
                    memberInfoDao.deleteControlSetDetail(paramMap);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("list");
                    Map<String, Object> map = memberInfoDao.queryControlSetInfo(paramMap);
                    for(Map<String, Object> param : list) {
                        param.put("control_set_id", map.get("ID"));
                        if("2".equals(param.get("duration_state").toString())||"2".equals(param.get("target_state").toString())) {
                            //新增控货设置明细
                            memberInfoDao.insertControlSetDetail(param);
                        }
                    }
                }else {//全部品牌
                    if("1".equals(paramMap.get("duration_state").toString())&&"1".equals(paramMap.get("target_state").toString())) {//不需要
                        //删除控货设置明细
                        memberInfoDao.deleteControlSetDetail(paramMap);
                        //删除控货设置
                        memberInfoDao.deleteControlSet(paramMap);
                    }else {
                        Map<String, Object> map=memberInfoDao.queryControlSetInfo(paramMap);
                        paramMap.put("control_set_id",map.get("ID"));
                        //更新控货设置
                        memberInfoDao.updateControlSet(paramMap);
                        //删除控货设置明细
                        memberInfoDao.deleteControlSetDetail(paramMap);
                        //新增控货设置明细
                        memberInfoDao.insertControlSetDetail(paramMap);
                    }
                }
            }else {//不存在
                if("2".equals(paramMap.get("control_restrict").toString())) {//单个品牌
                    //新增控货设置
                    memberInfoDao.insertControlSet(paramMap);
                    List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("list");
                    for(Map<String, Object> param : list) {
                        param.put("control_set_id", paramMap.get("control_set_id"));
                        if("2".equals(param.get("duration_state").toString())||"2".equals(param.get("target_state").toString())) {
                            //新增控货设置明细
                            memberInfoDao.insertControlSetDetail(param);
                        }
                    }
                }else {//全部品牌
                    if("2".equals(paramMap.get("duration_state").toString())||"2".equals(paramMap.get("target_state").toString())) {
                        //新增控货设置
                        memberInfoDao.insertControlSet(paramMap);
                        //新增控货设置明细
                        memberInfoDao.insertControlSetDetail(paramMap);
                    }
                }
            }

            pr.setState(true);
            pr.setMessage("控货设置成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("控货设置，异常原因："+e.getMessage());
        }
        return pr;
    }
    
    /**
     * 批量修改站点
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult memberSiteSet(HttpServletRequest request) {
    	Map<String, Object> param=new HashMap<String,Object>();
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            // 前台用户数据
            Map<String,Object> paramMap = (Map<String,Object>)Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("ids"))){
                pr.setState(false);
                pr.setMessage("缺少参数ids");
                return pr;
            }
            //获取站点
            if(!StringUtils.isEmpty(paramMap.get("site_id"))){
                param.put("site_id", paramMap.get("site_id"));
            }
            //获取站点id
            String ids=paramMap.get("ids").toString();
            for(String id:ids.split(",")){
                param.put("id", id);
                //记录批量修改站点日志
                Map<String,Object> userInfo = memberInfoDao.queryMemberInfoById(param);
                Map<String,Object> logMap=new HashMap<String,Object>();
                logMap.put("USER_TYPE", 3);
                logMap.put("OPERATE_ID", 2);
                logMap.put("REMARK", "编辑【批量修改站点】");
                logMap.put("CREATE_USER_ID", paramMap.get("public_user_id"));
                logMap.put("USER_NAME", userInfo.get("LOGIN_NAME"));
                logMap.put("USER_REALNAME", userInfo.get("USER_MANAGE_NAME"));
                memberInfoDao.insertUserOperationLog(logMap);
                if(memberInfoDao.updateMemberBussiness(param)>0){
                    Map<String,Object> memberInfoMap = new HashMap<String, Object>();
                    for (Map.Entry<String, Object> entry : param.entrySet()) {
                        memberInfoMap.put(entry.getKey().toUpperCase(),entry.getValue());
                    }
                    memberInfoMap.put("operationType","edit");
                    //调用oa同步接口
                    ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + "/sys_dictionary/sync_formal_customer", memberInfoMap);
                    if (!oAPr.getState()) {
                        throw new RuntimeException("OA系统会员数据同步失败");
                    }
                }else{
                    throw new RuntimeException("修改失败");
                }
            }
            pr.setState(true);
            pr.setMessage("修改成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("批量修改站点，异常原因："+e.getMessage());
        }
        return pr;
    }

    /**
     * 查询会员补扣款列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public GridResult querySupDeductListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }
            if(paramMap.size() == 0) {
                gr.setState(false);
                gr.setMessage("参数缺失");
                return gr;
            }

            if((!StringUtils.isEmpty(paramMap.get("states")))&&paramMap.get("states") instanceof String){
                paramMap.put("states",(paramMap.get("states")+"").split(","));
            }

            //查询会员补扣款总数
            int total = memberInfoDao.querySupDeductCount(paramMap);
            //查询会员补扣款列表
            List<Map<String, Object>> dataList = memberInfoDao.querySupDeductListForPage(paramMap);

            if (dataList != null && dataList.size() > 0) {
                gr.setState(true);
                gr.setMessage("查询成功!");
                gr.setObj(dataList);
                gr.setTotal(total);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }
        return gr;
    }

    /**
     * 保存会员补扣款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult saveSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("user_name"))||
                    StringUtils.isEmpty(paramMap.get("money"))||
                    StringUtils.isEmpty(paramMap.get("type"))||
                    StringUtils.isEmpty(paramMap.get("remark"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            if(memberInfoDao.insertOrUpdateSupDeduct(paramMap)<=0){
                pr.setState(false);
                pr.setMessage("保存失败");
                return pr ;
            }
            pr.setState(true);
            pr.setMessage("保存成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("保存会员补扣款失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    /**
     * 删除会员补扣款
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult removeSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            if(memberInfoDao.deleteSupDeduct(paramMap)<=0){
                pr.setState(false);
                pr.setMessage("删除失败");
                return pr ;
            }
            pr.setState(true);
            pr.setMessage("删除成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除会员补扣款失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    /**
     * 查询会员补扣款详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult querySupDeductDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            Map<String, Object> resultMap = memberInfoDao.querySupDeductDetail(paramMap);
            pr.setState(true);
            pr.setObj(resultMap);
            pr.setMessage("查询成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 会员补扣款审批
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult approvalSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("state"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            if(memberInfoDao.approvalSupDeduct(paramMap)<=0){
                pr.setState(false);
                pr.setMessage("审批失败");
                return pr ;
            }
            pr.setState(true);
            pr.setMessage("审批成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("审批会员补扣款失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 会员补扣款签批
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult signSupDeduct(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("参数缺失");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("state"))){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            if(memberInfoDao.signApprovalSupDeduct(paramMap)<=0){
                pr.setState(false);
                pr.setMessage("签批失败");
                return pr ;
            }
            //签批通过
            Map<String, Object> param = memberInfoDao.querySupDeductDetail(paramMap);

            //查询会员账户信息
            Map<String, Object> baMap = memberInfoDao.queryBankAccountInfoByUserId(Long.parseLong(param.get("user_name").toString()));
            if(StringUtils.isEmpty(baMap)){
                throw new RuntimeException("会员异常！");
            }
            Map<String,Object> codeParams = new HashMap<String,Object>();
            codeParams.put("user_id", param.get("user_name"));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("public_user_name", paramMap.get("public_user_name"));
            map.put("public_user_realname", paramMap.get("public_user_realname"));
            map.put("parent_acc_subject_id", 1004);
            double accountBalance = 0;
            double deductMoney = Double.parseDouble(param.get("money").toString());
            if("扣款".equals(param.get("type").toString())) {//扣款
                accountBalance = DoubleUtils.sub(Double.parseDouble(baMap.get("ACCOUNT_BALANCE").toString()), deductMoney);
                codeParams.put("account_balance", accountBalance);
                if(deductMoney > 0) {
                    // 校验会员余额校验码
                    if(memberInfoDao.queryCheckAccountBalance(codeParams) == 0){
                        throw new RuntimeException("余额发生篡改，无法完成当前操作");
                    }
                    // 更新会员余额
                    if (stationedDao.updateAccountBalanceInfo(codeParams) <= 0) {
                        throw new RuntimeException("更新余额失败");
                    }
                    // 更新入驻商余额校验码
                    stationedDao.updateUserAccountCheckCode(codeParams);
                    map.put("id", paramMap.get("id"));
                    map.put("record_channel", "余额");
                    map.put("surplus_money", accountBalance);
                    map.put("money", param.get("money"));

                    String deduction = "1".equals(param.get("deduction_type").toString()) ? "违规操作" : "其他原因";

                    map.put("remark", "扣款：" + deduction + "，" + param.get("remark"));
                    //收支类型
                    map.put("record_type", "付款");
                    map.put("accountants_subject_id", map.get("parent_acc_subject_id") + "KK");
                    map.put("accountants_subject_name", "扣款");

                    //增加会员收支记录
                    if(memberInfoDao.insertMemberRecordBySupDeduct(map)==0) {
                        throw new RuntimeException("生成会员收支记录失败！");
                    }
                }
            }else {//补款
                accountBalance = DoubleUtils.add(Double.parseDouble(baMap.get("ACCOUNT_BALANCE").toString()), deductMoney);
                codeParams.put("account_balance", accountBalance);
                if(deductMoney > 0) {
                    // 校验会员余额校验码
                    if(memberInfoDao.queryCheckAccountBalance(codeParams) == 0){
                        throw new RuntimeException("余额发生篡改，无法完成当前操作");
                    }

                    // 更新会员余额
                    if (stationedDao.updateAccountBalanceInfo(codeParams) <= 0) {
                        throw new RuntimeException("更新余额失败");
                    }

                    // 更新会员余额校验码
                    stationedDao.updateUserAccountCheckCode(codeParams);
                    map.put("id", paramMap.get("id"));
                    map.put("record_channel", "余额");
                    map.put("surplus_money", accountBalance);
                    map.put("money", param.get("money"));
                    map.put("remark", "补款：" + param.get("remark"));
                    //收支类型
                    map.put("record_type", "收款");
                    map.put("accountants_subject_id", map.get("parent_acc_subject_id") + "BK");
                    map.put("accountants_subject_name", "补款");

                    //增加会员收支记录
                    if(memberInfoDao.insertMemberRecordBySupDeduct(map)==0) {
                        throw new RuntimeException("生成会员收支记录失败！");
                    }
                }
            }

            pr.setState(true);
            pr.setMessage("签批成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("签批会员补扣款失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

	/**
	 * 查询会员补扣款详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryMemberChangeLogList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);

			if(!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(paramMap.size() == 0) {
				gr.setState(false);
				gr.setMessage("参数缺失");
				return gr;
			}
			if(StringUtils.isEmpty(paramMap.get("user_id"))){
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}

			List<Map<String, Object>> resultMap = memberInfoDao.queryChangeLogListByUserId(paramMap);
			int total = memberInfoDao.queryChangeLogCountByUserId(paramMap);
			gr.setState(true);
			gr.setObj(resultMap);
			gr.setTotal(total);
			gr.setMessage("查询成功");
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}


	/**
	 * 查询会员补扣款详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryMemberChangeLogDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);

			if(!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			}
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("record_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}

			Map<String, Object> resultMap = memberInfoDao.queryChangeLogDetailById(paramMap);
			pr.setState(true);
			pr.setObj(resultMap);
			pr.setMessage("查询成功");
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
}
