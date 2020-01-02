package com.tk.sys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {
    
    @Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String log_token = "";
		if (request != null && !StringUtils.isEmpty(request.getParameter("log_token"))) {
			log_token = request.getParameter("log_token");
		}
    	MDC.put("log_token", log_token);
    	return true;
	}
    
    @Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
    	MDC.remove("log_token");
	}
}