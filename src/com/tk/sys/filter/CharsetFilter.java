package com.tk.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 字符编码过滤器
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class CharsetFilter implements Filter {
	/**
	 * 编码方式
	 */
	private String charset;
	/**
	 * 标识是否启用过滤器
	 */
	private boolean flag;

	public void destroy() {
		// 销毁过滤器
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 过滤器设为启用且字符编码不为空
		if (flag && null != charset) {
			// 设置编码方式
			request.setCharacterEncoding(charset);
			response.setCharacterEncoding(charset);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		// 初始化过滤器
		this.charset = config.getInitParameter("charset");
		this.flag = "true".equals(config.getInitParameter("flag"));
		System.out.println("设置的字符编码方式为：" + charset + " 是否启用：" + flag);
	}
}