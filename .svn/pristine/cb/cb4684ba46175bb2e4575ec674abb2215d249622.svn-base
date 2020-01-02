package com.tk.oms.decoration.service;

import com.tk.oms.decoration.dao.MobileVideoDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2019,  TongKu
 * FileName : MobileVideoService
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2019/1/11
 */
@Service("AppVideoService")
public class MobileVideoService {


    @Resource
    private MobileVideoDao mobileVideoDao;

    /**
     * 查询移动视频分类列表
     * @param request
     * @return
     */
    public ProcessResult queryCategoryList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);


            List<Map<String, Object>> categoryList = this.mobileVideoDao.queryCategoryList(params);
            pr.setState(true);
            pr.setMessage("查询移动视频分类列表成功");
            pr.setObj(categoryList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询移动视频分类详情
     * @param request
     * @return
     */
    public ProcessResult detail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            long id = Long.parseLong(params.get("id").toString());

            Map<String, Object> detail = this.mobileVideoDao.queryById(id);
            pr.setState(true);
            pr.setMessage("查询移动视频分类详情成功");
            pr.setObj(detail);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
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
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);

            long id = Long.parseLong(params.get("id").toString());

            int count  = this.mobileVideoDao.delete(id);
            if(count == 0) {
                throw new RuntimeException("删除移动视频分类数据失败");
            }
            pr.setState(true);
            pr.setMessage("删除移动视频分类数据成功");
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

            Object detail_json = params.get("detail_json");
            if(detail_json instanceof List  || detail_json instanceof Map){
                String module_base_conf_json = Jackson.writeObject2Json(detail_json);
                params.put("detail_json", module_base_conf_json);
            }

            int count = 0;
            if(!params.containsKey("id") || StringUtils.isEmpty(params.get("id"))) {
                count  = this.mobileVideoDao.insert(params);
            } else {
                count  = this.mobileVideoDao.update(params);
            }
            if(count == 0) {
                throw new RuntimeException("保存移动视频分类数据失败");
            }
            pr.setState(true);
            pr.setMessage("保存移动视频分类数据成功");
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
                Map<String, Object> t1 = mobileVideoDao.querySortById(Long.parseLong(paramMap.get("id1").toString()));
                Map<String, Object> t2 = mobileVideoDao.querySortById(Long.parseLong(paramMap.get("id2").toString()));
                t1.put("id", paramMap.get("id1"));
                t1.put("sort_id", t2.get("SORT_ID"));
                t2.put("id", paramMap.get("id2"));
                t2.put("sort_id", t1.get("SORT_ID"));
                if(mobileVideoDao.updateSort(t1)>0&& mobileVideoDao.updateSort(t2)>0){
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
                if(mobileVideoDao.updateSort(paramMap)>0){
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
}

    
    
