package com.tk.sys.util;

import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {

    //默认截取长度3000
	public static final int SUBLENGTH = 3000;

	/**
     * 按指定长度切割字符串
     *
     * @param targetStr
     * @param subLength
     * @return
     */
    public static String[] substringToArray(String targetStr, int subLength) {
        try {
            if (targetStr == null || targetStr.length() == 0) {
                return new String[0];
            }
            if (subLength <= 0 || targetStr.length() <= subLength) {
                return new String[]{Base64.encode(targetStr.getBytes(EsbConfig.INPUT_CHARSET))};
            }

            String str = Base64.encode(targetStr.getBytes(EsbConfig.INPUT_CHARSET));
            int length = str.length();
            int size = length % subLength == 0 ? length / subLength : (length / subLength) + 1;

            String[] array = new String[size];
            for (int i = 0; i < size; i++) {
                if (i + 1 == size) {
                    array[i] = str.substring(i * subLength, str.length());
                } else {
                    array[i] = str.substring(i * subLength, (i + 1) * subLength);
                }
            }
            return array;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
    
    /**
     * 将List<javaBean></>数据中javaBean的fieldName字段拼接成字符串
     *
     * @param datas 	要拼接的实体类集合
     * @param fieldName 拼接字段（实体类属性名）
     * @param <T>
     * @return
     * @throws Exception 
     */
    public static <T> String append(List<Map<String,Object>> datas, String fieldName) throws Exception {
        StringBuffer buffer = new StringBuffer("");
        for (Map<String, Object> map : datas) {
            try {
                buffer.append(StringUtils.isEmpty(map.get(fieldName)) ? "" : map.get(fieldName).toString());
            } catch (Exception e) {
                throw new Exception("拼接异常！", e);
            }
        }

        byte[] array = com.tk.sys.security.Base64.decode(buffer.toString());
        if (array == null) {
            return "";
        }
        try {
            return new String(array, EsbConfig.INPUT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 处理模块基础配置
     * @param params
     * @return
     */
    public static Map<String,Object> handleModuleBaseConfToParams(Map<String,Object> params){
        Object moduleBaseConfObj = params.get("module_base_conf");
        String moduleBaseConfJson = null;
        if (!StringUtils.isEmpty(moduleBaseConfObj)) {
            if (moduleBaseConfObj instanceof List || moduleBaseConfObj instanceof Map) {
                moduleBaseConfJson = Jackson.writeObject2Json(moduleBaseConfObj);
            } else {
                moduleBaseConfJson = moduleBaseConfObj.toString();
            }
        }
        //截取字符串
        String[] moduleBaseConfAttr = StringUtil.substringToArray(moduleBaseConfJson,StringUtil.SUBLENGTH);
        List<Map<String, Object>> moduleBaseConfList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < moduleBaseConfAttr.length; i++) {
            Map<String, Object> confMap = new HashMap<String, Object>(16);
            confMap.put("module_base_conf", moduleBaseConfAttr[i]);
            confMap.put("sort", i);
            moduleBaseConfList.add(confMap);
        }
        params.put("moduleBaseConfList",moduleBaseConfList);
        return params;
    }

    /**
     * 按指定字符截取字符串
     * @param targetStr 被截取字符串
     * @param splitStr 截取字符
     * @return
     */
    public static List<String> splitStrToArray(String targetStr, String splitStr) {
        List<String> returnList = new ArrayList<String>();
        try {
            if (targetStr == null || targetStr.length() == 0) {
                return new ArrayList<String>();
            }

            String[] array = targetStr.split(splitStr);
            for (int i = 0; i < array.length; i++) {
                returnList.add(array[i]);
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}
