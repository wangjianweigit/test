package com.tk.oms.test.control;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.sys.config.EsbConfig;
import com.tk.sys.security.Base64;
import com.tk.sys.security.Crypt;
import com.tk.sys.security.MD5;
import com.tk.sys.util.FileUtils;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.Jackson;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Utils;

/**
 * 该方法用于HTML测试接口时的加解密
 * @author shifan
 * @date  2017-4-6  下午3:38:44
 */
@Controller
@RequestMapping("/test")
public class TestContol {
	private Log logger = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/encryption", method = RequestMethod.POST)  
	@ResponseBody
	public Packet encryption(HttpServletRequest request) throws IOException {
		Packet p = new Packet();
		String json = HttpUtil.getRequestInputStream(request);
		json = URLDecoder.decode(json.toString(), "UTF-8");

		// 加密
		byte[] encryptResult = Crypt.encrypt(json, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.ERP_FORWARD_KEY_NAME));
		// 编码
		String ciphertext = Base64.encode(encryptResult);
		// 时间戳
		long timestamp = Utils.getTimeMillis();
		// 签名
		String sign = MD5.sign(ciphertext + timestamp, FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.MD5_SIGN_KEY_NAME), EsbConfig.INPUT_CHARSET);
		p.setCiphertext(ciphertext);
		p.setTimestamp(timestamp);
		p.setSign(sign);
		return p;
	}
	
	/**
	 * 解密数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/decrypt", method = RequestMethod.POST)
	@ResponseBody
	public Packet decrypt(HttpServletRequest request) throws IOException {
		String result = "";
		String json = HttpUtil.getRequestInputStream(request);
//		json = URLDecoder.decode(URLDecoder.decode(json, "UTF-8"),"UTF-8");
//		Packet	packet = (Packet) Transform.GetPacket(json, Packet.class);
		Map<String,Object> map = (Map<String,Object>)Jackson.readJson2Object(json, Map.class);
		if(map.containsKey("ciphertext"))
			result = new String(Crypt.decrypt(Base64.decode(map.get("ciphertext").toString()), FileUtils.getSecretKey(EsbConfig.SECRET_KEY_PATH, EsbConfig.ERP_REVERSE_KEY_NAME)), "utf-8");
		Packet p = new Packet();
		p.setCiphertext(result);
		return p;
	}
}
