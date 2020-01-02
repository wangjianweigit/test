package com.tk.oms.stationed.service;

import com.tk.oms.stationed.dao.StationedTemplateDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : StationedTemplateService
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/8/10
 */
@Service("StationedTemplateService")
public class StationedTemplateService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private StationedTemplateDao stationedTemplateDao;

    /**
     * 查询入驻商模板列表
     * @param request
     * @return
     */
    public GridResult queryList(HttpServletRequest request) {
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
            //查询入驻商数量
            int total = stationedTemplateDao.queryCount(paramMap);
            //查询入驻商列表
            List<Map<String, Object>> dataList = stationedTemplateDao.queryList(paramMap);

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
     * 查询入驻商模板详情
     * @param request
     * @return
     */
    public ProcessResult queryDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            long id =  Long.parseLong(paramMap.get("id").toString());

            //查询入驻商详情
            Map<String, Object> res = stationedTemplateDao.queryById(id);
            if (res != null) {
                pr.setState(true);
                pr.setMessage("查询模板详情成功!");
                pr.setObj(res);
            } else {
                pr.setState(false);
                pr.setMessage("查询模板详情失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑入驻商模板
     * @param request
     * @return
     */
    public ProcessResult edit(HttpServletRequest request) {
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
            if(stationedTemplateDao.isExistTemplateName(paramMap)> 0) {
                pr.setState(false);
                pr.setMessage("该模板名称已存在");
                return pr;
            }

            if (stationedTemplateDao.insertOrUpdate(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("编辑成功!");
            } else {
                pr.setState(false);
                pr.setMessage("编辑失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("编辑失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 查询模板序列ID
     * @return
     */
    public ProcessResult queryTemplateId() {
        ProcessResult pr = new ProcessResult();
        try {
            //查询入驻商详情
            int templateId = stationedTemplateDao.queryTemplateId();
            pr.setState(true);
            pr.setMessage("查询模板序列ID成功!");
            pr.setObj(templateId);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 更新数据
     * @return
     */
    public ProcessResult update(HttpServletRequest request) {
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
            if(stationedTemplateDao.update(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("更新成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 默认模板设置
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult templateDefault(HttpServletRequest request) {
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
            //"1.不是默认模板  2.是默认模板；(只允许存在一个默认模板)
            if("2".equals(paramMap.get("is_default").toString())){
                //1.更新该条运费模板的默认状态
                stationedTemplateDao.update(paramMap);
                stationedTemplateDao.updateByDefault(paramMap);
            }
            pr.setState(true);
            pr.setMessage("设置成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("设置失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 逻辑删除模版
     * @return
     */
    public ProcessResult remove(HttpServletRequest request) {
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
            if(stationedTemplateDao.updateIsDelete(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }




}

    
    
