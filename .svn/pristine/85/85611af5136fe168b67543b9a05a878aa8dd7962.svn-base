package com.tk.oms.saas;

import com.tk.sys.security.MD5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Copyright (c), 2016, Tongku
 * FileName : SaasUtils
 * 聚水潭数据转换工具类
 *
 * @author wanghai
 * @version 1.00
 * @date 2017-11-22
 */
public class SaasUtils {

    /**
     * 获取普通请求签名
     *
     * @param map
     * @return
     */
    public static String getSign(Map<String, String> map){
        StringBuilder prestr = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            if("sign".equals(key) || "method".equals(key) || "partnerid".equals(key) || "patrnerkey".equals(key)) {
                continue;
            }

            prestr.append(key).append(entry.getValue());
        }

        String signStr = map.get("method") + map.get("partnerid") + prestr.toString() + map.get("patrnerkey");
        return MD5.sign(signStr, "", "UTF-8");
    }

    /**
     * 获取奇门请求签名
     *
     * @param map
     * @return
     */
    public static String getQmSign(Map<String, String> map) {
        // 对map排序
        List<Map.Entry<String, String>> sortList = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(sortList, new Comparator<Map.Entry<String, String>>() {
            // 升序排序
            //@Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder prestr = new StringBuilder();
        prestr.append(map.get("app_secret"));
        for(Map.Entry<String, String> entry : sortList) {
            if("app_secret".equals(entry.getKey()) || "patrnerkey".equals(entry.getKey())) {
                continue;
            }
            prestr.append(entry.getKey());
            prestr.append(entry.getValue());
        }
        prestr.append(map.get("app_secret"));

        return MD5.sign(prestr.toString(), "", "UTF-8").toUpperCase();
    }

    public static String getJstSign(Map<String, String> map){
        String signStr = map.get("method").replace("jst.", "") + map.get("partnerid") + "token" + map.get("token") + "ts" + map.get("ts") + map.get("patrnerkey");
        return MD5.sign(signStr, "", "UTF-8");
    }

    /**
     * 获取聚水潭请求地址
     *
     * @param url
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getRequestUrl(String url, Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder preurl = new StringBuilder();
        preurl.append(url);

        map.remove("patrnerkey");
        map.remove("app_secret");

        boolean isFirst = true;
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if(isFirst) {
                preurl.append("?");
            }else{
                preurl.append("&");
            }
            preurl.append(entry.getKey()).append("=");
            preurl.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            isFirst = false;
        }

        return preurl.toString();
    }

    /**
     * 获取请求时间
     * 时间戳格式(Unix 纪元到当前时间的秒数),API服务端允许客户端请求最大时间误差为10分钟。
     *
     * @param d
     * @return
     */
    public static int getStamp(Date d) {
        return (int) (d.getTime()/1000);
    }
}
