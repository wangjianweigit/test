package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.ContractTemplateDao;
import com.tk.oms.basicinfo.dao.ContractTemplateDetailDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * Copyright (c), 2018,  TongKu
 * FileName : ContractTemplateService
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/12/10
 */
@Service("ContractTemplateService")
public class ContractTemplateService {
    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private ContractTemplateDao contractTemplateDao;
    @Resource
    private ContractTemplateDetailDao contractTemplateDetailDao;

    /**
     * 获取合同模板列表信息
     * @param request
     * @return
     */
    public GridResult queryList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            List<String> state_list = null;
            if(paramMap.get("state") instanceof List<?>){
                state_list = (List<String>) paramMap.get("state");
            }else if(paramMap.get("state") instanceof String){
                state_list = new ArrayList<String>();
                state_list.add(paramMap.get("state").toString());
            }
            paramMap.put("state", state_list);
            List<Map<String, Object>> list = contractTemplateDao.queryListForPage(paramMap);
            int total = contractTemplateDao.queryCountForPage(paramMap);
            if (list != null) {
                gr.setMessage("获取合同模板列表成功");
                gr.setObj(list);
                gr.setTotal(total);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return gr;
    }

    /**
     * 保存合同模板数据信息
     * @param request
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ProcessResult save(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            pr = saveData(paramMap);

            pr.setState(true);
            pr.setMessage("保存合同模板数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException("保存合同模板数据失败");
        }
        return pr;
    }

    /**
     * 发布合同模板
     * @param request
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public ProcessResult release(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);

            if(paramMap.containsKey("flag") && "1".equals(paramMap.get("flag").toString())) { //列表发布操作

                Map<String, Object> releaseMap = new HashMap<String, Object>();
                releaseMap.put("id", paramMap.get("id"));
                releaseMap.put("state", paramMap.get("state"));
                contractTemplateDao.update(releaseMap);
            } else {
                pr = saveData(paramMap);
            }
            // 令同公司同类型的其他发布状态的模板失效
            contractTemplateDao.invalidOther(paramMap);
            pr.setState(true);
            pr.setMessage("发布合同模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException("发布合同模板失败");
        }
        return pr;
    }

    /**
     * 保存数据
     * @param paramMap
     * @return
     */
    public ProcessResult saveData(Map<String, Object> paramMap) {
        ProcessResult pr = new ProcessResult();
        int count = 0;
        if(StringUtils.isEmpty(paramMap.get("id"))){
            count = contractTemplateDao.insert(paramMap);
            if(count == 0) {
                throw new RuntimeException("保存合同模板数据失败");
            }
            paramMap.put("template_id", paramMap.get("id"));
            contractTemplateDetailDao.insert(paramMap);
        } else {
            contractTemplateDao.update(paramMap);
            paramMap.put("template_id", paramMap.get("id"));
            contractTemplateDetailDao.update(paramMap);
        }
        return pr;
    }

    /**
     * 获取合同模板详情
     * @param request
     * @return
     */
    public ProcessResult queryDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);

            long template_id = Long.parseLong(paramMap.get("id").toString());
            Map<String, Object> result = contractTemplateDao.queryById(template_id);

            if(result == null || result.isEmpty()) {
                pr.setState(false);
                pr.setMessage("该合同模板不存在");
            } else {
                pr.setState(true);
                pr.setMessage("获取详情成功");
                pr.setObj(result);
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException("保存合同模板数据失败");
        }
        return pr;
    }

    /**
     * 删除合同模板
     * @param request
     * @return
     */
    public ProcessResult remove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);

            long template_id = Long.parseLong(paramMap.get("id").toString());
            int count = contractTemplateDao.deleteById(template_id);
            if(count == 0) {
                throw new RuntimeException("删除合同模板数据失败");
            }
            contractTemplateDetailDao.deleteByTemplateId(template_id);

            pr.setState(true);
            pr.setMessage("删除合同模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
            throw new RuntimeException("保存合同模板数据失败");
        }
        return pr;
    }
}

    
    
