package com.tk.oms.saas.service;

import com.tk.oms.saas.SaasRequest;
import com.tk.oms.saas.SaasResponse;
import com.tk.oms.saas.SaasUtils;
import com.tk.oms.saas.dao.SaasAPIDao;
import com.tk.sys.util.DateUtils;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.Jackson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Copyright (c), 2016, Tongku
 * FileName : SaasAPIService
 * 聚水潭接口处理类
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-11-22
 */
@Service("SaasAPIService")
public class SaasAPIService {

    /** 聚水潭请求地址 */
    @Value("${http_host_url}")
    private String http_host_url;
    @Value("${http_qm_host_url}")
    private String http_qm_host_url;

    /** 聚水潭请求固定参数 */
    @Value("${saas.format}")
    private String format;
    @Value("${saas.sign_method}")
    private String sign_method;
    @Value("${saas.v}")
    private String v;
    @Value("${saas.target_app_key}")
    private String target_app_key;

    /** 聚水潭请求方法 */
    @Value("${method.shops_query}")
    private String shops_query;
    @Value("${method.auth_query}")
    private String auth_query;
    @Value("${method.item_upload}")
    private String item_upload;
    @Value("${method.sku_query}")
    private String sku_query;
    @Value("${method.skumap_query}")
    private String skumap_query;
    @Value("${method.inventory_upload}")
    private String inventory_upload;
    @Value("${method.stock_upload_plate}")
    private String stock_upload_plate;
    @Value("${method.jst_orders_query}")
    private String jst_orders_query;
    @Value("${method.ordersent_upload}")
    private String ordersent_upload;
    @Value("${method.jst_orders_source_query}")
    private String jst_orders_source_query;
    //  平台商品查询接口
    @Value("${method.sku_source_query}")
    private String sku_source_query;

    @Resource
    private SaasAPIDao saasAPIDao;

    /**
     * 获取聚水潭公司列表
     *
     * @return
     */
    public List<Map<String, Object>> getSaasCompanyList() {
        return saasAPIDao.querySaasCompanyList();
    }

    /**
     * 初始化聚水潭请求GET参数
     *
     * @param sr
     * @param isJst
     * @return
     */
    private Map<String, String> init(SaasRequest sr, boolean isJst) {
        Map<String, String> reqMap = new HashMap<String, String>();

        reqMap.put("partnerid", sr.getPartnerid());
        reqMap.put("patrnerkey", sr.getPatrnerkey());
        reqMap.put("method", sr.getMethod());
        reqMap.put("token", sr.getToken());
        reqMap.put("ts", String.valueOf(SaasUtils.getStamp(new Date())));

        if(isJst) {
            reqMap.put("jstsign",  SaasUtils.getJstSign(reqMap));

            reqMap.put("app_key",  sr.getTaobao_appkey());
            reqMap.put("app_secret",  sr.getTaobao_appsecret());
            reqMap.put("format",  this.format);
            reqMap.put("sign_method",  this.sign_method);
            reqMap.put("v",  this.v);
            reqMap.put("timestamp", DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS));
            reqMap.put("target_app_key",  this.target_app_key);

            if(null != sr.getReq_params()) {
                Map<String, Object> paramsMap = (Map<String, Object>) sr.getReq_params();
                Iterator<Map.Entry<String, Object>> iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    reqMap.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            reqMap.put("sign", SaasUtils.getQmSign(reqMap));
        }else{
            reqMap.put("sign", SaasUtils.getSign(reqMap));
        }

        return reqMap;
    }

    /**
     * 请求聚水潭接口数据
     *
     * @param sr
     * @return
     * @throws UnsupportedEncodingException
     */
    public SaasResponse postData(SaasRequest sr) {
        SaasResponse response = new SaasResponse();

        String method = sr.getMethod();

        boolean isQm = false;
        // 判断请求方式是否需要通过奇门请求
        if(jst_orders_query.equals(method) || jst_orders_source_query.equals(method)) {
            isQm = true;
        }
        // 初始化请求GET参数
        Map<String, String> reqMap = init(sr, isQm);

        // 获取请求全链接地址
        try {
            String reqUrl = SaasUtils.getRequestUrl(http_host_url, reqMap);
            if(isQm) {
                reqUrl = SaasUtils.getRequestUrl(http_qm_host_url, reqMap);
            }

            String resStr = "";
            if(isQm) {
                resStr = HttpClientUtil.post(reqUrl, "");
            }else{
                resStr = HttpClientUtil.post(reqUrl, Jackson.writeObject2Json(sr.getReq_params()));
            }

            if(null != resStr || !"".equals(resStr)) {
                Map<String, Object> responseMap = (Map<String, Object>) Jackson.readJson2Object(resStr, HashMap.class);
                if(isQm) {
                    responseMap = (Map<String, Object>) responseMap.get("response");
                    if(responseMap.containsKey("flag") && "failure".equals(responseMap.get("flag").toString())) {
                        response.setSuccess(false);
                        response.setMessage(responseMap.get("message").toString());
                        return response;
                    }
                }else{
                    if(responseMap.containsKey("code") && !"0".equals(responseMap.get("code").toString())) {
                        response.setSuccess(false);
                        response.setMessage(String.valueOf(responseMap.get("msg")));
                        return response;
                    }

                }

                response.setSuccess(true);
                response.setMessage("OK");

                if(responseMap.containsKey("shop_id")) {
                    response.setShop_id(Long.parseLong(responseMap.get("shop_id").toString()));
                }
                if(responseMap.containsKey("has_next")) {
                    response.setHas_next(Boolean.parseBoolean(responseMap.get("has_next").toString()));
                }
                if(responseMap.containsKey("page_index")) {
                    response.setPage_index(Integer.parseInt(responseMap.get("page_index").toString()));
                }
                if(responseMap.containsKey("page_size")) {
                    response.setPage_size(Integer.parseInt(responseMap.get("page_size").toString()));
                }

                if(responseMap.containsKey("orders")) {
                    response.setDatas(responseMap.get("orders"));
                }
                if(responseMap.containsKey("datas")) {
                    response.setDatas(responseMap.get("datas"));
                }
                if(responseMap.containsKey("shops")) {
                    response.setDatas(responseMap.get("shops"));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("请求参数编码异常");
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setMessage("请求聚水潭接口超时");
        }

        return response;
    }

    public String getSku_source_query() {
        return sku_source_query;
    }

    public String getItem_upload() {
        return item_upload;
    }

    public String getSku_query() {
        return sku_query;
    }

    public String getSkumap_query() {
        return skumap_query;
    }

    public String getInventory_upload() {
        return inventory_upload;
    }

    public String getStock_upload_plate() {
        return stock_upload_plate;
    }

    public String getJst_orders_query() {
        return jst_orders_query;
    }

    public String getOrdersent_upload() {
        return ordersent_upload;
    }

    public String getJst_orders_source_query() {
        return jst_orders_source_query;
    }

    public String getAuth_query() {
        return auth_query;
    }

    public String getShops_query() {
        return shops_query;
    }
}
