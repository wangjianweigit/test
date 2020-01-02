package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.WaterTanBasicDao;
import com.tk.oms.saas.SaasRequest;
import com.tk.oms.saas.SaasResponse;
import com.tk.oms.saas.dao.SaasAPIDao;
import com.tk.oms.saas.service.SaasAPIService;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : WaterTanBasicService
 * 聚水谭公司基础信息维护业务处理类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/11/22 16:58
 */
@Service("WaterTanBasicService")
public class WaterTanBasicService {

    @Resource
    private WaterTanBasicDao waterTanBasicDao;
    @Resource
    private SaasAPIDao saasAPIDao;

    @Resource
    private SaasAPIService saasAPIService;

    /**
     * 获取聚水谭公司列表
     *
     * @param request
     * @return
     */
    public GridResult queryWaterTanList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
                String[] stateArray = paramMap.get("state").toString().split(",");
                paramMap.put("state", Arrays.asList(stateArray));
            }
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            List<Map<String, Object>> list = waterTanBasicDao.queryWaterTanList(paramMap);
            if (list != null && list.size() > 0) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(waterTanBasicDao.queryWaterTanCount(paramMap));
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (IOException e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 编辑聚水谭公司明细
     *
     * @param request
     * @return
     */
    public ProcessResult queryWaterTanDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("获取成功");
            pr.setObj(waterTanBasicDao.queryWaterTanDetail(paramMap));
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 新增聚水谭公司明细
     *
     * @param request
     * @return
     */
    public ProcessResult addWaterTanDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("NAME"))){
                pr.setState(false);
                pr.setMessage("缺少参数NAME");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("MOBILE"))){
                pr.setState(false);
                pr.setMessage("缺少参数MOBILE");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("PARTNERID"))){
                pr.setState(false);
                pr.setMessage("缺少参数PARTNERID");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("PATRNERKEY"))){
                pr.setState(false);
                pr.setMessage("缺少参数PATRNERKEY");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("TOKEN"))){
                pr.setState(false);
                pr.setMessage("缺少参数TOKEN");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("TAOBAO_APPKEY"))){
                pr.setState(false);
                pr.setMessage("缺少参数TAOBAO_APPKEY");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("TAOBAO_APPSECRET"))){
                pr.setState(false);
                pr.setMessage("缺少参数TAOBAO_APPSECRET");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("STATE"))){
                pr.setState(false);
                pr.setMessage("缺少参数STATE");
                return pr;
            }
            if (waterTanBasicDao.insertWaterTanDetail(paramMap) < 1) {
                pr.setState(false);
                pr.setMessage("新增失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("新增成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑聚水谭公司明细
     *
     * @param request
     * @return
     */
    public ProcessResult editWaterTanDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            Map<String, Object> data = waterTanBasicDao.queryWaterTanDetail(paramMap);
            if(data == null){
                pr.setState(false);
                pr.setMessage("数据不存在");
                return pr;
            }
            if (waterTanBasicDao.editWaterTanDetail(paramMap) < 1) {
                pr.setState(false);
                pr.setMessage("编辑失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("编辑成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 更改聚水谭公司启用状态
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult updateWaterTanDetailState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("STATE"))){
                pr.setState(false);
                pr.setMessage("缺少参数STATE");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            Map<String, Object> data = waterTanBasicDao.queryWaterTanDetail(paramMap);
            if(data == null){
                pr.setState(false);
                pr.setMessage("数据不存在");
                return pr;
            }
            if (waterTanBasicDao.editWaterTanDetail(paramMap) < 1) {
                pr.setState(false);
                pr.setMessage("修改状态失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("1".equals(paramMap.get("STATE").toString()) ? "启用成功" : "禁用成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }


    /**
     * 获取聚水谭会员店铺列表
     *
     * @param request
     * @return
     */
    public GridResult queryWaterTanMemberStoreList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
                String[] stateArray = paramMap.get("state").toString().split(",");
                paramMap.put("state", Arrays.asList(stateArray));
            }
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            List<Map<String, Object>> list = waterTanBasicDao.queryWaterTanMemberStoreList(paramMap);
            if (list != null && list.size() > 0) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(waterTanBasicDao.queryWaterTanMemberStoreCount(paramMap));
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (IOException e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 编辑聚水谭会员店铺明细
     *
     * @param request
     * @return
     */
    public ProcessResult queryWaterTanMemberStoreDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("获取成功");
            pr.setObj(waterTanBasicDao.queryWaterTanMemberStoreDetail(paramMap));
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑聚水谭会员店铺明细
     *
     * @param request
     * @return
     */
    public ProcessResult authCancel(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            //  取消授权
            paramMap.put("STATE", "1");
            paramMap.put("SHOP_ID", "");
            paramMap.put("SHOP_NAME", "");
            if(waterTanBasicDao.editWaterTanMemberStoreDetail(paramMap) > 0){
                pr.setState(true);
                pr.setMessage("取消授权成功");
            }else{
                pr.setState(false);
                pr.setMessage("取消授权失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }


    /**
     * 新增聚水谭会员店铺明细
     *
     * @param request
     * @return
     */
    public ProcessResult addWaterTanMemberStoreDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("SHOP_ID"))){
                pr.setState(false);
                pr.setMessage("缺少参数SHOP_ID");
                return pr;
            }
            //  店铺ID不为空  默认授权状态
            paramMap.put("STATE", "2");
            if(StringUtils.isEmpty(paramMap.get("TYPE"))){
                pr.setState(false);
                pr.setMessage("缺少参数TYPE");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("SYNC_TYPE"))){
                pr.setState(false);
                pr.setMessage("缺少参数SYNC_TYPE");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("USER_ID"))){
                pr.setState(false);
                pr.setMessage("缺少参数USER_ID");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("COMPANY_ID"))){
                pr.setState(false);
                pr.setMessage("缺少参数COMPANY_ID");
                return pr;
            }
            //  查询店铺授权状态
            if(waterTanBasicDao.queryWaterTanUserShopInfo(paramMap) != null){
                pr.setState(false);
                pr.setMessage("该店铺已授权,请取消后再授权!");
                return pr;
            }
            if (waterTanBasicDao.insertWaterTanMemberStoreDetail(paramMap) < 1) {
                pr.setState(false);
                pr.setMessage("新增失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("新增成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑聚水谭会员店铺明细
     *
     * @param request
     * @return
     */
    public ProcessResult editWaterTanMemberStoreDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("STOCK_SYNC_STATE"))){
                //  店铺ID不为空  默认授权状态
                if(!StringUtils.isEmpty(paramMap.get("SHOP_ID"))){
                    paramMap.put("STATE", "2");
                }
                Map<String, Object> data = waterTanBasicDao.queryWaterTanMemberStoreDetail(paramMap);
                if(data == null){
                    pr.setState(false);
                    pr.setMessage("数据不存在");
                    return pr;
                }
                if(paramMap.containsKey("SHOP_ID")){
                    //  查询店铺授权状态
                    if(waterTanBasicDao.queryWaterTanUserShopInfo(paramMap) != null){
                        pr.setState(false);
                        pr.setMessage("该店铺已授权,请取消后再授权!");
                        return pr;
                    }
                }
            }

            if (waterTanBasicDao.editWaterTanMemberStoreDetail(paramMap) < 1) {
                pr.setState(false);
                pr.setMessage("编辑失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("编辑成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 聚水谭会员店铺解除授权
     *
     * @param request
     * @return
     */
    public ProcessResult removeWaterTanMemberStore(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            if(StringUtils.isEmpty(paramMap.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            Map<String, Object> data = waterTanBasicDao.queryWaterTanMemberStoreDetail(paramMap);
            if(data == null){
                pr.setState(false);
                pr.setMessage("数据不存在");
                return pr;
            }

            if (waterTanBasicDao.deleteWaterTanMemberStore(Long.parseLong(paramMap.get("id").toString())) < 1) {
                pr.setState(false);
                pr.setMessage("解除失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("解除成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询聚水谭公司下拉框
     *
     * @param request
     * @return
     */
    public ProcessResult queryWaterTanCompanyOption(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (paramMap == null || paramMap.equals("")) {
                pr.setState(false);
                pr.setMessage("参数缺失!");
                return pr;
            }
            List<Map<String, Object>> list = waterTanBasicDao.queryWaterTanCompanyOption(paramMap);
            if(list == null){
                pr.setState(false);
                pr.setMessage("获取聚水谭公司失败");
                return pr;
            }
            Map<String, Object> retMap = new HashMap<String, Object>(2);
            //  查询聚水谭系统配置托管公司id
            retMap.put("host_company_id", waterTanBasicDao.queryWaterTanHostCompany());
            retMap.put("options", list);
            pr.setState(true);
            pr.setObj(retMap);
            pr.setMessage("获取成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取聚水潭店铺列表
     *
     * @param request
     * @return
     */
    public GridResult getSaasShopList(HttpServletRequest request) {
        GridResult gr = new GridResult();

        try {
            // 获取请求参数
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析请求参数
            Map<String, Object> paramsMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(!paramsMap.containsKey("company_id") || StringUtils.isEmpty(paramsMap.get("company_id").toString())) {
                gr.setState(false);
                gr.setMessage("缺少聚水潭公司参数");
                return gr;
            }

            if (paramsMap.containsKey("state") && !StringUtils.isEmpty(paramsMap.get("state"))) {
                String[] state = paramsMap.get("state").toString().split(",");
                if (state.length > 1) {
                    paramsMap.put("states", paramsMap.get("state"));
                } else {
                    paramsMap.put("states", paramsMap.get("state").toString().split(","));
                }
            }


            GridResult pageParamResult = PageUtil.handlePageParams(paramsMap);
            if(pageParamResult!=null){
                return pageParamResult;
            }
            List<Map<String, Object>> list = waterTanBasicDao.queryWaterTanCompanyStoreList(paramsMap);
            if (list != null && list.size() > 0) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(waterTanBasicDao.queryWaterTanCompanyStoreCount(paramsMap));
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
            gr.setState(false);
            gr.setMessage("获取聚水潭店铺异常");
        }

        return gr;
    }



}
