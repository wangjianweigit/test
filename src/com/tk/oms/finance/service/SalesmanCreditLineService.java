package com.tk.oms.finance.service;


import com.tk.oms.finance.dao.SalesmanCreditLineDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : SalesmanCreditLineService
 * 业务员授信管理业务操作
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/5/6 14:57
 */
@Service("SalesmanCreditLineService")
public class SalesmanCreditLineService {
    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private SalesmanCreditLineDao salesmanCreditLineDao;//业务员授信额度数据访问接口
    @Resource
    private SysUserInfoDao sysUserInfoDao;//用户数据访问接口


    /**
     * 查询业务员授信额度管理列表
     *
     * @param request 查询条件
     * @return
     */
    public GridResult queryList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }


            //查询记录数
            int count = salesmanCreditLineDao.querySalesmanCreditLineCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = salesmanCreditLineDao.querySalesmanCreditLineList(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
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
     * 修改活动通知内容信息
     *
     * @param request 修改内容
     * @return
     */
    @Transactional
    public ProcessResult update_verify_code(HttpServletRequest request) throws Exception {
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
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            SysUserInfo sysUserInfo = sysUserInfoDao.queryByUserId(Long.parseLong(paramMap.get("id").toString()));
            //校验id参数
            if(sysUserInfo == null) {
                pr.setState(false);
                pr.setMessage("该业务员不存在");
                return pr;
            }

            Map param = new HashMap();
            param.put("id", Long.parseLong(paramMap.get("id").toString()));
            param.put("verification_code", Utils.getVerifyCode());  // 产生验证码

            if(sysUserInfoDao.updateUserVerifyCode(param) >0){
                pr.setState(true);
                pr.setMessage("更新成功");
            }else{
                pr.setState(false);
                pr.setMessage("更新失败");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 修改活动通知内容信息
     *
     * @param request 修改内容
     * @return
     */
    @Transactional
    public ProcessResult update_credit(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            //校验id参数
            if(StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            //校验credit_money参数
            if(StringUtils.isEmpty(paramMap.get("credit_money"))) {
                pr.setState(false);
                pr.setMessage("额度不能为空！");
                return pr;
            }

            BigDecimal credit_money=new BigDecimal((String) paramMap.get("credit_money"));
            //校验授信额度是否大于0
            if(credit_money.compareTo(BigDecimal.ZERO) == -1){
                pr.setState(false);
                pr.setMessage("额度不能小于0！");
                return pr;
            }
            if(salesmanCreditLineDao.updateCreditMoney(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("该业务员授信额度编辑成功");

            } else {
                pr.setState(false);
                pr.setMessage("该业务员授信额度编辑失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
}
