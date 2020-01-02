package com.tk.sys.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson Object与json相互转换通用类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class Jackson {

	private static ObjectMapper objectMapper = null;

	/**
	 * 转化Object为json字符串
	 */
	public static String writeObject2Json(Object obj) {
		objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转换json字符串为Object
	 */
	public static Object readJson2Object(String json, Class<?> c) {
		objectMapper = new ObjectMapper();
		//json字符串转化为实体时，如果存在多余的字段（即实体中不存在的字段）则忽略
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			if (isList(json)) {
				JavaType t = objectMapper.getTypeFactory().constructCollectionType(List.class, c);
				return objectMapper.readValue(json, t);
			} else {
				return objectMapper.readValue(json, c);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判断json字符串为List
	 */
	public static boolean isList(String json){
		if (json != null && !"".equals(json)) {
			if (json.indexOf("[") != -1 && json.indexOf("]") != -1) {
				if (json.indexOf("[") == 0
						&& json.indexOf("]") == json.length() - 1) {
					return true;
				}
			}
		}
		return false;
	}
}