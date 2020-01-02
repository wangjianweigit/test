package com.tk.sys.config;

/**
 * 系统配置文件
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class EsbConfig {
	
	/**
	 * 密钥配置文件位置
	 */
	public static final String SECRET_KEY_PATH = "resources/key.properties";
	
	/**
	 * MD5签名密钥
	 */
	public static final String MD5_SIGN_KEY_NAME = "md5_sign_key";

	/**
	 * 正向密钥（客户端加密，服务端解密）
	 */
	public static final String ERP_FORWARD_KEY_NAME = "erp_forward_key";
	
	/**
	 * 反向密钥（服务端加密，客户端解密）
	 */
	public static final String ERP_REVERSE_KEY_NAME = "erp_reverse_key";
	
	/**
	 * 字符编码格式 utf-8
	 */
	public static final String INPUT_CHARSET = "utf-8";
	
	/**
	 * 时间戳阈值 60秒
	 */
	public static final int THRESHOLD = 60;
	
	/**
	 * 用户密码密钥
	 */
	public static final String USER_PWD_KEY = "user_pwd_key";

	/**
	 * 支付密码密钥
	 */
	public static final String PAY_PWD_KEY = "pay_pwd_key";

	/**
	 * 支付项目正向加密
	 */
	public static final String PAY_FORWARD_KEY_NAME = "pay_forward_key";

	/**
	 * 支付项目反向解密
	 */
	public static final String PAY_REVERSE_KEY_NAME = "pay_reverse_key";

	/**
	 * 正向密钥（客户端加密，服务端解密）
	 */
	public static final String OA_FORWARD_KEY_NAME = "oa_forward_key";

	/**
	 * 反向密钥（服务端加密，客户端解密）
	 */
	public static final String OA_REVERSE_KEY_NAME = "oa_reverse_key";
	/**
	 * 正向密钥（客户端加密，服务端解密）
	 */
	public static final String SSO_FORWARD_KEY_NAME = "sso_forward_key";

	/**
	 * 反向密钥（服务端加密，客户端解密）
	 */
	public static final String SSO_REVERSE_KEY_NAME = "sso_reverse_key";
}