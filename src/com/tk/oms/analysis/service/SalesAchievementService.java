package com.tk.oms.analysis.service;

import com.tk.oms.analysis.dao.SalesAchievementDao;
import com.tk.sys.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright (c), 2018, TongKu
 * FileName : SalesAchievementService
 * 销售人员绩效相关统计业务层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-7-10
 */
@Service("SalesAchievementService")
public class SalesAchievementService {

    @Resource
    private SalesAchievementDao salesAchievementDao;
    @Value("${jdbc_user}")
	private String jdbc_user;

    /**
     * 查询销售人员绩效分析列表
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public GridResult queryListForPage(HttpServletRequest request) {
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
			if (!paramMap.containsKey("rule_type") || StringUtils.isEmpty(paramMap.get("rule_type"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【rule_type】");
                return gr;
            }
			if("1".equals(paramMap.get("rule_type").toString())) {
				if (!paramMap.containsKey("start_date") || StringUtils.isEmpty(paramMap.get("start_date"))) {
	                gr.setState(false);
	                gr.setMessage("缺少参数【start_date】");
	                return gr;
	            }
				if (!paramMap.containsKey("end_date") || StringUtils.isEmpty(paramMap.get("end_date"))) {
	                gr.setState(false);
	                gr.setMessage("缺少参数【end_date】");
	                return gr;
	            }
			}
			
			//查询销售人员绩效分析数量
			int total = salesAchievementDao.queryCount(paramMap);
			//查询销售人员绩效分析列表
			List<Map<String, Object>> dataList = salesAchievementDao.queryListForPage(paramMap);
			//查询规则信息
			List<Map<String, Object>> gzList = salesAchievementDao.listSalesAchieveRuleByType(paramMap);
			for(Map<String, Object> data : dataList) {
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("store_id", data.get("STORE_ID"));
				if(!StringUtils.isEmpty(data.get("YWY_USER_ID"))){param.put("ywy_user_id", data.get("YWY_USER_ID"));}
				if(!StringUtils.isEmpty(paramMap.get("start_date"))){param.put("start_date", paramMap.get("start_date"));}
				if(!StringUtils.isEmpty(paramMap.get("end_date"))){param.put("end_date", paramMap.get("end_date"));}
				//获取符合规则的数据
				if("1".equals(paramMap.get("rule_type").toString())) {
					//查询门店业务员所属会员
					List<Map<String, Object>> userList = salesAchievementDao.queryUserList(param);
					param.put("user_list", userList);
					param.put("jdbc_user", jdbc_user);
					String user_names = "";
					for(Map<String, Object> gz : gzList) {
						param.put("money", gz.get("MONEY"));
						param.put("return_rate", gz.get("RETURN_RATE"));
						if(user_names != ""){
							param.put("user_names",user_names.split(","));
						}
						if(userList != null && userList.size() > 0) {
							Map<String, Object> gradeMap = salesAchievementDao.queryGradeUserInfo(param);
							if(!StringUtils.isEmpty(gradeMap.get("USER_NAME"))) {
								user_names += gradeMap.get("USER_NAME").toString()+",";
							}
							//查询会员的采购金额
							data.put(gz.get("ID").toString(), gradeMap.get("USER_COUNT"));
							data.put("n"+gz.get("ID").toString(), gradeMap.get("USER_NAME"));
						}else {
							data.put(gz.get("ID").toString(), 0);
						}
					}
				}else if("2".equals(paramMap.get("rule_type").toString())) {
					//沉睡会员数
					data.put("SLEEP_MEMBER_COUNT", salesAchievementDao.getSleepMemberCount(param));
					//激活会员数
					data.put("ACTIVE_MEMBER_COUNT", salesAchievementDao.getActiveMemberCount(param));
					//有效激活会员数
					data.put("EFFECT_ACTIVE_MEMBER_COUNT", salesAchievementDao.getEffectiveActiveMemberCount(param));
                } else if ("3".equals(paramMap.get("rule_type").toString())) {
                    if (gzList != null && gzList.size() > 0) {
                        param.put("money", gzList.get(0).get("MONEY"));
                    } else {
                        param.put("money", 0);
                    }
                    data.put("EFFECTIVE_MEMBER_COUNT", this.salesAchievementDao.countEffectiveMember(param));
                } else {
                    gr.setState(false);
                    gr.setMessage("参数值异常，请检查");
                    return gr;
                }
			}
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询统计列表成功");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}

    /**
     * 查询新增有效会员详细信息
     * @param request
     * @return
     */
    public GridResult queryEffectiveMemberDetail(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("rule_type") || StringUtils.isEmpty(paramsMap.get("rule_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【rule_type】");
                return pr;
            }
            if (!paramsMap.containsKey("store_id") || StringUtils.isEmpty(paramsMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【store_id】");
                return pr;
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //查询规则信息
            List<Map<String, Object>> gzList = salesAchievementDao.listSalesAchieveRuleByType(paramsMap);
            if (gzList != null && gzList.size() > 0) {
                Map<String, Object> ruleMap = gzList.get(0);
                if (StringUtils.isEmpty(ruleMap.get("MONEY"))) {
                    pr.setState(false);
                    pr.setMessage("未设定订单金额");
                    return pr;
                }
                paramsMap.put("money",ruleMap.get("MONEY"));
            } else {
                pr.setState(false);
                pr.setMessage("统计规则未设定");
                return pr;
            }
            int count = this.salesAchievementDao.countEffectiveMember(paramsMap);
            List<Map<String, Object>> list = null;
            if(count > 0){
                list = this.salesAchievementDao.listEffectiveMember(paramsMap);
                pr.setMessage("查询新增有效会员详细成功");
            }else{
                pr.setMessage("无数据");
            }
            pr.setState(true);

            pr.setTotal(count);
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 分页查询会员列表
     * @param request
     * @return
     */
    public GridResult queryMemberListForPage(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("store_id") || StringUtils.isEmpty(paramsMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【store_id】");
                return pr;
            }
            paramsMap.put("jdbc_user", jdbc_user);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if((!StringUtils.isEmpty(paramsMap.get("user_names")))&&paramsMap.get("user_names") instanceof String){
            	paramsMap.put("user_names",paramsMap.get("user_names").toString().split(","));
			}
            int count = this.salesAchievementDao.countMemberForPage(paramsMap);
            List<Map<String, Object>> list = null;
            if (count > 0) {
                list = this.salesAchievementDao.listMemberForPage(paramsMap);
                pr.setMessage("查询会员列表成功");
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
            pr.setTotal(count);
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询统计规则详情
     * @param request
     * @return
     */
    public ProcessResult querySalesAchieveRuleDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("rule_type") || StringUtils.isEmpty(paramsMap.get("rule_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【rule_type】");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("查询统计规则详情成功");
            pr.setObj(this.salesAchievementDao.listSalesAchieveRuleByType(paramsMap));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 批量新增或编辑统计规则
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult batchAddOrEditRule(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("rule_type") || StringUtils.isEmpty(paramsMap.get("rule_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【rule_type】");
                return pr;
            }
            if (!paramsMap.containsKey("rule_list") || StringUtils.isEmpty(paramsMap.get("rule_list"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【rule_list】");
                return pr;
            }
            List<Map<String, Object>> ruleList = (List<Map<String, Object>>) paramsMap.get("rule_list");
            if (ruleList == null || ruleList.size() <= 0) {
                pr.setState(false);
                pr.setMessage("参数【rule_list】数据不能为空");
                return pr;
            }
            if("1".equals(paramsMap.get("rule_type").toString())) {
            	for(Map<String, Object> rule : ruleList) {
                	if(salesAchievementDao.queryGradeNameIsExists(rule) >0) {
            			throw new RuntimeException("级别名称重复，请检查！");
            		}
                	rule.put("rule_type", paramsMap.get("rule_type"));
                	if(StringUtils.isEmpty(rule.get("rule_id"))) {
                		if(salesAchievementDao.insert(rule) == 0) {
                			throw new Exception("新增统计规则失败");
                		}
                	}else {
                		if(salesAchievementDao.update(rule) == 0) {
                			throw new Exception("更新统计规则失败");
                		}
                	}
                }
            }else {
            	if (this.salesAchievementDao.batchInsertOrUpdateRule(paramsMap) <= 0) {
                    throw new Exception("统计规则设置失败");
                }
            }
            
            pr.setState(true);
            pr.setMessage("统计规则设置成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除统计规则
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult removeRule(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("rule_id") || StringUtils.isEmpty(paramsMap.get("rule_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【rule_id】");
                return pr;
            }
            long ruleId = Long.parseLong(paramsMap.get("rule_id").toString());
            if (this.salesAchievementDao.deleteRuleById(ruleId) <= 0) {
                throw new Exception("删除统计规则失败");
            }
            pr.setState(true);
            pr.setMessage("删除统计规则成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 查询休眠会员详情
     * @param request
     * @return
     */
    public GridResult memberSleepDetailList(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("store_id") || StringUtils.isEmpty(paramsMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【store_id】");
                return pr;
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int count = this.salesAchievementDao.getSleepMemberCount(paramsMap);
            List<Map<String, Object>> list = null;
            if (count > 0) {
                list = this.salesAchievementDao.queryMemberSleepDetailList(paramsMap);
                pr.setMessage("查询休眠会员详情列表成功");
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
            pr.setTotal(count);
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 查询激活会员详情
     * @param request
     * @return
     */
    public GridResult memberActiveDetailList(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("store_id") || StringUtils.isEmpty(paramsMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【store_id】");
                return pr;
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int count = this.salesAchievementDao.getActiveMemberCount(paramsMap);
            List<Map<String, Object>> list = null;
            if (count > 0) {
                list = this.salesAchievementDao.queryMemberActiveDetailList(paramsMap);
                pr.setMessage("查询激活会员详情列表成功");
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
            pr.setTotal(count);
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 查询有效激活会员详情
     * @param request
     * @return
     */
    public GridResult memberEffectActiveDetailList(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramsMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramsMap.containsKey("store_id") || StringUtils.isEmpty(paramsMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【store_id】");
                return pr;
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int count = this.salesAchievementDao.getEffectiveActiveMemberCount(paramsMap);
            List<Map<String, Object>> list = null;
            if (count > 0) {
                list = this.salesAchievementDao.queryMemberEffectActiveDetailList(paramsMap);
                pr.setMessage("查询激活会员详情列表成功");
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
            pr.setTotal(count);
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
}
