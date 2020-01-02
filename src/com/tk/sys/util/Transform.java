package com.tk.sys.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;
import com.tk.sys.security.MD5;

/**
 * 数据转换类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class Transform {

	/**
	 * 加密响应数据
	 */
	public static Packet GetResult(Object obj) {
		// 转换JSON
		String content = Jackson.writeObject2Json(obj);
		// 加密
		byte[] encryptResult = Crypt.encrypt(content, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.ERP_REVERSE_KEY_NAME));
		// 编码
		String ciphertext = Base64.encode(encryptResult);
		// 时间戳
		long timestamp = Utils.getTimeMillis();
		// 签名
		String sign = MD5.sign(ciphertext + timestamp, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		Packet p = new Packet(ciphertext, timestamp, sign);
		return p;
	}

	/**
	 * 解密请求数据
	 * @throws UnsupportedEncodingException 
	 */
	public static Object GetPacket(String json, Class<?> c) throws UnsupportedEncodingException {
		Packet p = (Packet) Jackson.readJson2Object(json, Packet.class);
		// 验证签名
		String sign = MD5.sign(p.getCiphertext() + p.getTimestamp(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		if (sign.equals(p.getSign())) {
			// 验证时间戳
			long timestamp = Utils.getTimeMillis();
			if (timestamp - p.getTimestamp() > EsbConfig.THRESHOLD) {
				throw new RuntimeException("时间戳校验失败");
			} else {
				// 解码
				byte[] base64Arr = Base64.decode(p.getCiphertext());
				// 解密
				String base64DeStr = new String(Crypt.decrypt(base64Arr, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.ERP_FORWARD_KEY_NAME)), EsbConfig.INPUT_CHARSET);
				// 封装对象
				Object obj = Jackson.readJson2Object(base64DeStr, c);
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * 加密响应数据
	 */
	public static Packet GetResultJzb(Object obj) {
		// 转换JSON
		String content = Jackson.writeObject2Json(obj);

		// 加密
		byte[] encryptResult = Crypt.encrypt(content, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.ERP_FORWARD_KEY_NAME));
		
		// 编码
		String ciphertext = Base64.encode(encryptResult);

		// 时间戳
		long timestamp = Utils.getTimeMillis();

		// 签名
		String sign = MD5.sign(ciphertext + timestamp, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		
		Packet p = new Packet(ciphertext, timestamp, sign);
		return p;
	}
	
	/**
	 * 解密请求数据
	 * @throws UnsupportedEncodingException 
	 */
	public static Object GetPacketJzb(String json, Class<?> c) throws UnsupportedEncodingException {
		Packet p = (Packet) Jackson.readJson2Object(json, Packet.class);

		// 验证签名
		String sign = MD5.sign(p.getCiphertext() + p.getTimestamp(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		if (sign.equals(p.getSign())) {
			// 验证时间戳
			long timestamp = Utils.getTimeMillis();
			if (timestamp - p.getTimestamp() > EsbConfig.THRESHOLD) {
				throw new RuntimeException("时间戳校验失败");
			} else {
				// 解码
				byte[] base64Arr = Base64.decode(p.getCiphertext());
				
				// 解密
				String base64DeStr = new String(Crypt.decrypt(base64Arr, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.ERP_REVERSE_KEY_NAME)), EsbConfig.INPUT_CHARSET);
				
				// 封装对象
				Object obj = Jackson.readJson2Object(base64DeStr, c);
				return obj;
			}
		}
		return null;
	}

	/**
	 * 加密响应数据
	 */
	public static Packet GetResult(Object obj, String secretKey) {
		// 转换JSON
		String content = Jackson.writeObject2Json(obj);

		// 加密
		byte[] encryptResult = Crypt.encrypt(content, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, secretKey));

		// 编码
		String ciphertext = Base64.encode(encryptResult);

		// 时间戳
		long timestamp = Utils.getTimeMillis();

		// 签名
		String sign = MD5.sign(ciphertext + timestamp, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);

		Packet p = new Packet(ciphertext, timestamp, sign);
		return p;
	}

	/**
	 * 解密请求数据
	 */
	public static Object GetPacket(String json, Class<?> c, String secretKey) throws UnsupportedEncodingException {
		Packet p = (Packet) Jackson.readJson2Object(json, Packet.class);

		// 验证签名
		String sign = MD5.sign(p.getCiphertext() + p.getTimestamp(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		if (sign.equals(p.getSign())) {
			// 验证时间戳
			long timestamp = Utils.getTimeMillis();
			if (timestamp - p.getTimestamp() > EsbConfig.THRESHOLD) {
				throw new RuntimeException("时间戳校验失败");
			} else {
				// 解码
				byte[] base64Arr = Base64.decode(p.getCiphertext());

				// 解密
				String base64DeStr = new String(Crypt.decrypt(base64Arr, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, secretKey)), EsbConfig.INPUT_CHARSET);

				// 封装对象
				Object obj = Jackson.readJson2Object(base64DeStr, c);
				return obj;
			}
		}
		return null;
	}

	/**
	 * 加密响应数据
	 */
	public static Packet GetResultOA(Object obj) {
		// 转换JSON
		String content = Jackson.writeObject2Json(obj);

		// 加密
		byte[] encryptResult = Crypt.encrypt(content, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.OA_FORWARD_KEY_NAME));

		// 编码
		String ciphertext = Base64.encode(encryptResult);

		// 时间戳
		long timestamp = Utils.getTimeMillis();

		// 签名
		String sign = MD5.sign(ciphertext + timestamp, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);

		Packet p = new Packet(ciphertext, timestamp, sign);
		return p;
	}

	/**
	 * 解密请求数据
	 * @throws UnsupportedEncodingException
	 */
	public static Object GetPacketOA(String json, Class<?> c) throws UnsupportedEncodingException {
		Packet p = (Packet) Jackson.readJson2Object(json, Packet.class);
		//  验证签名
		String sign = MD5.sign(p.getCiphertext() + p.getTimestamp(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		if (sign.equals(p.getSign())) {
			//  验证时间戳
			long timestamp = Utils.getTimeMillis();
			if (timestamp - p.getTimestamp() > EsbConfig.THRESHOLD) {
				throw new RuntimeException("时间戳校验失败");
			} else {
				//  解码
				byte[] base64Arr = Base64.decode(p.getCiphertext());

				//  解密
				String base64DeStr = new String(Crypt.decrypt(base64Arr, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.OA_FORWARD_KEY_NAME)), EsbConfig.INPUT_CHARSET);

				//  封装对象
				Object obj = Jackson.readJson2Object(base64DeStr, c);
				return obj;
			}
		}
		return null;
	}

	/**
	 * 解密请求数据
	 * @throws UnsupportedEncodingException
	 */
	public static Object GetPacketOAByReverse(String json, Class<?> c) throws UnsupportedEncodingException {
		Packet p = (Packet) Jackson.readJson2Object(json, Packet.class);
		//  验证签名
		String sign = MD5.sign(p.getCiphertext() + p.getTimestamp(), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		if (sign.equals(p.getSign())) {
			//  验证时间戳
			long timestamp = Utils.getTimeMillis();
			if (timestamp - p.getTimestamp() > EsbConfig.THRESHOLD) {
				throw new RuntimeException("时间戳校验失败");
			} else {
				//  解码
				byte[] base64Arr = Base64.decode(p.getCiphertext());

				//  解密
				String base64DeStr = new String(Crypt.decrypt(base64Arr, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.OA_REVERSE_KEY_NAME)), EsbConfig.INPUT_CHARSET);

				//  封装对象
				Object obj = Jackson.readJson2Object(base64DeStr, c);
				return obj;
			}
		}
		return null;
	}

}