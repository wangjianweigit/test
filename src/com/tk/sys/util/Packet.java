package com.tk.sys.util;

/**
 * 数据包封装类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class Packet {

	private String ciphertext;// 密文
	private long timestamp;// 时间戳
	private String sign;// 签名

	public Packet(){}
	
	public Packet(String ciphertext, long timestamp, String sign) {
		this.ciphertext = ciphertext;
		this.timestamp = timestamp;
		this.sign = sign;
	}

	public String getCiphertext() {
		return ciphertext;
	}

	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}