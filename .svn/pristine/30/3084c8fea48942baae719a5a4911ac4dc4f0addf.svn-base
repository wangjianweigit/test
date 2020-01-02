package com.tk.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Http工具类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class HttpUtil {

	/**
	 * 获取Request内容
	 * @throws IOException
	 */
	public static String getRequestInputStream(HttpServletRequest request) throws IOException {
		if (request != null) {
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			String str = sb.toString();
			return str;
		}
		return null;
	}

	/**
	 * 通过HttpServletRequest返回IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}