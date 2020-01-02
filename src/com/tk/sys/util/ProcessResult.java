package com.tk.sys.util;

/**
 * 处理结果实体类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class ProcessResult {

	/**
	 * 状态
	 */
	private boolean state;
	/**
	 * 消息
	 */
	private String message = "未知异常，请与服务端开发人员联系";
	/**
	 * 实体对象封装
	 */
	private Object obj;

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}