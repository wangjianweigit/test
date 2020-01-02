package com.tk.oms.decoration.service;

import com.tk.oms.decoration.dao.SocialMenuDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2019,  TongKu
 * FileName : SocialMenuService
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2019/3/11
 */
@Service("SocialMenuService")
public class SocialMenuService {
    private Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private SocialMenuDao socialMenuDao;

    public GridResult queryList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            List<Map<String, Object>> list = this.socialMenuDao.queryListForPage(paramMap);

            int total = this.socialMenuDao.queryCountForPage(paramMap);
            gr.setState(true);
            gr.setMessage("查询菜单列表成功");
            gr.setObj(list);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }


    /**
     * 删除移动视频分类数据
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
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            int count  = this.socialMenuDao.delete(params);
            if(count == 0) {
                throw new RuntimeException("删除菜单数据失败");
            }
            pr.setState(true);
            pr.setMessage("删除菜单数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }


    /**
     * 保存移动视频分类数据
     * @param request
     * @return
     */
    public ProcessResult save(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            int count = 0;
            if(!params.containsKey("id") || StringUtils.isEmpty(params.get("id"))) {
                count  = this.socialMenuDao.insert(params);
            } else {
                count  = this.socialMenuDao.update(params);
            }
            if(count == 0) {
                throw new RuntimeException("保存菜单数据失败");
            }
            pr.setState(true);
            pr.setMessage("保存菜单数据成功");
            pr.setObj(params.get("id"));
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }


    /**
     * 排序
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult sort(HttpServletRequest request) {
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
            if(StringUtils.isEmpty(paramMap.get("type"))){
                if(StringUtils.isEmpty(paramMap.get("id1"))||StringUtils.isEmpty(paramMap.get("id2"))){
                    pr.setState(false);
                    pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
                    return pr;
                }
                Map<String, Object> t1 = socialMenuDao.querySortById(Long.parseLong(paramMap.get("id1").toString()));
                Map<String, Object> t2 = socialMenuDao.querySortById(Long.parseLong(paramMap.get("id2").toString()));
                t1.put("id", paramMap.get("id1"));
                t1.put("sort_id", t2.get("SORT_ID"));
                t2.put("id", paramMap.get("id2"));
                t2.put("sort_id", t1.get("SORT_ID"));
                if(socialMenuDao.updateSort(t1)>0&& socialMenuDao.updateSort(t2)>0){
                    pr.setState(true);
                    pr.setMessage("排序字段修改成功");
                    pr.setObj(null);
                } else {
                    pr.setState(false);
                    pr.setMessage("排序字段修改失败");
                }
            }else{
                if(StringUtils.isEmpty(paramMap.get("id"))){
                    pr.setState(false);
                    pr.setMessage("参数缺失，分组ID为空");
                    return pr;
                }
                if(socialMenuDao.updateSort(paramMap)>0){
                    pr.setState(true);
                    pr.setMessage("排序字段修改成功");
                }else{
                    pr.setState(false);
                    pr.setMessage("排序字段修改失败");
                }
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }



    /**
     * 更改状态
     * @param request
     * @return
     */
    public ProcessResult updateState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            int count = this.socialMenuDao.update(params);
            if(count == 0) {
                throw new RuntimeException("更改状态失败");
            }
            pr.setState(true);
            pr.setMessage("更改状态成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }


    /**
     * 查询菜单详情
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public ProcessResult queryDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("菜单ID为空");
                return pr;
            }
            //菜单详情
            Map<String, Object> retMap = socialMenuDao.queryDetail(paramMap);
            if(retMap != null){
                pr.setState(true);
                pr.setMessage("获取数据成功");
                pr.setObj(retMap);
            }else {
                pr.setState(false);
                pr.setMessage("错误");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("查询失败");
            logger.error("查询失败，"+e.getMessage());
        }

        return pr;
    }

    /**
     * 设置默认选中
     * @param request
     * @return
     */
    public ProcessResult updateDefault(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            int count = this.socialMenuDao.update(params);
            if(count == 0) {
                throw new RuntimeException("设置默认状态失败");
            }
            this.socialMenuDao.updateByDefault(params);
            pr.setState(true);
            pr.setMessage("设置默认状态成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
}

    
    
