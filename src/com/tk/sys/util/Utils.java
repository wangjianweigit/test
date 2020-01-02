package com.tk.sys.util;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 工具类（包括一些通用方法）
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class Utils {

	/**
	 * 获取时间戳，精确到秒
	 */
	public static long getTimeMillis() {
		return System.currentTimeMillis() / 1000;
	}
	
	
	/**
	 * 获取8位随机数
	 *
	 * @return
     */
	public static String getVerifyCode(){
		StringBuilder str=new StringBuilder();//定义变长字符串
		Random random=new Random();
		//随机生成数字，并添加到字符串
		for(int i=0;i<6;i++){
			str.append(random.nextInt(10));
		}
		return str.toString();
	}

	/**
	 * 去除数组中的空值
	 * @param arr
	 * @return
	 */
	public static String[] removeNull(String[] arr){
		List<String> list = new ArrayList<String>();
		if(arr!=null && arr.length>0){
			for(String a:arr){
				if(!StringUtils.isEmpty(a) && !"0".equals(a))
					list.add(a);
			}
		}
		int size = list.size();
		return (String[]) list.toArray(new String[size]);
	}

	/**
	 * 将文件字节转换为对应的单位
	 * @param size 单位B
	 * @return 具体的字符串信息
	 */
	public static String getPrintSize(long size) {  
	    //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	    if (size < 1024) {  
	        return String.valueOf(size) + "B";  
	    } else {  
	        size = size / 1024;  
	    }  
	    //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
	    //因为还没有到达要使用另一个单位的时候  
	    //接下去以此类推  
	    if (size < 1024) {  
	        return String.valueOf(size) + "KB";  
	    } else {  
	        size = size / 1024;  
	    }  
	    if (size < 1024) {  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	        size = size * 100;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "MB";  
	    } else {  
	        //否则如果要以GB为单位的，先除于1024再作同样的处理  
	        size = size * 100 / 1024;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "GB";  
	    }  
	}  
	
	/**
	 * ip地址权限校验，允许"192.168.0.*"和"192.169.0.1-100"格式  
	 * @param ipStr ip地址 例如：192.168.0.21
	 * @param ipPattern 白名单权限ip列表   以,隔开  例如：192.168.0.1-20,192.168.0.188,192.168.0.*
	 * @return 校验是否通过：通过返回true 不通过返回false
	 */
	public static boolean validateIP(String ipStr, String ipPattern) {
		if (ipStr == null || ipPattern == null) {
			return false;
		}
		String[] patternList = ipPattern.split(",");
		for (String patternStr : patternList) {
			if (passValidate(ipStr, patternStr)) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean passValidate(String ipStr, String patternStr) {
		String[] ipArray = ipStr.split("\\.");
		String[] patternArray = patternStr.split("\\.");
		if (ipArray.length != 4 || patternArray.length != 4) {
			return false;
		}
		int end = ipArray.length;
		if (patternArray[3].contains("-")) {
			end = 3;
			String[] rangeArr = patternArray[3].split("-");
			int from = Integer.valueOf(rangeArr[0]).intValue();
			int to = Integer.valueOf(rangeArr[1]).intValue();
			int value = Integer.valueOf(ipArray[3]).intValue();
			if (value < from || value > to) {
				return false;
			}
		}
		for (int i = 0; i < end; i++) {
			if (patternArray[i].equals("*")) {
				continue;
			}
			if (!patternArray[i].equalsIgnoreCase(ipArray[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 生成见证宝交易流水号
	 *
	 * @return
	 */
	public static String getAttesttreasThirdlogno() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateTime = format.format(new Date());
		Random random = new Random();
		int seq = random.nextInt(999);
		DecimalFormat dFormat = new DecimalFormat("000");
		return dateTime + dFormat.format(seq);
	}
}