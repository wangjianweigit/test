package com.tk.sys.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Http客户端工具类
 * 
 * @author zhanglei
 * @date 2016/06/01
 * 
 */
public class HttpClientUtil {

	/**
	 * 发送post请求
	 * 
	 */
	public static String post(String url, String params) throws SocketTimeoutException{
		String result = "";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity strEntity;
		try {
			strEntity = new StringEntity(params, "UTF-8");
			httpPost.setEntity(strEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (SocketTimeoutException e) {
			throw e;
		} catch (ParseException e) {
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws SocketTimeoutException
	 */
	public static String postOaSync(String url, String params) throws SocketTimeoutException{
		String result = "";

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity strEntity;
		try {
			strEntity = new StringEntity(params, "UTF-8");
			strEntity.setContentType("application/json");
			httpPost.setEntity(strEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			throw e;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 发送post请求超时处理
	 * @param url  请求url
	 * @param params  请求参数
	 * @param millisecond  超时毫秒数
	 * 
	 */
	public static String post(String url, String params, int millisecond) {
		String result = "";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(millisecond).setConnectTimeout(millisecond).build();//设置请求和传输超时时间,如果不设置会导致死链接在24时以上
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		StringEntity strEntity;
		try {
			strEntity = new StringEntity(params, "UTF-8");
			httpPost.setEntity(strEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 发送get请求
	 */
	public static String get(String url) {
		String result = "";

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 发送post请求,返回ProcessResult 结果
	 * @param url  请求url
	 * @param paramMap  参数map对象
	 * @return ProcessResult
	 */
	public static ProcessResult post(String url,  Map<String, Object> paramMap) throws SocketTimeoutException{
		 //erp调用见证宝平台接口
		Packet packet = Transform.GetResultJzb(paramMap);// 加密数据
		String params = Jackson.writeObject2Json(packet);// 对象转json、字符串
		// 请求远程url
		String jsonOrderCheck = HttpClientUtil.post(url, params);
		// 解析返回的数据
		ProcessResult pr;
		try {
			pr = (ProcessResult) Transform.GetPacketJzb(jsonOrderCheck, ProcessResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			pr=new ProcessResult();
			pr.setState(false);
			pr.setMessage("调用远程接口异常");
			return pr;
		}
		return pr;
	}

	/**
	 * 发送post请求,返回ProcessResult 结果
	 * @param url  请求url
	 * @param paramMap  参数map对象
	 * @return ProcessResult
	 */
	public static ProcessResult postOaSync(String url,  Map<String, Object> paramMap) throws SocketTimeoutException{
		//erp调用见证宝平台接口
		Packet packet = Transform.GetResultJzb(paramMap);// 加密数据
		String params = Jackson.writeObject2Json(packet);// 对象转json、字符串
		// 请求远程url
		String jsonOrderCheck = HttpClientUtil.postOaSync(url, params);
		// 解析返回的数据
		ProcessResult pr;
		try {
			pr = (ProcessResult) Transform.GetPacketJzb(jsonOrderCheck, ProcessResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			pr=new ProcessResult();
			pr.setState(false);
			pr.setMessage("调用远程接口异常");
			return pr;
		}
		return pr;
	}

	/**
	 * 发送post请求,返回ProcessResult 结果
	 * @param url
	 * @param paramMap
	 * @param forwardKey
	 * @param reverseKey
	 * @return
	 */
	public static ProcessResult post(String url, Map<String, Object> paramMap, String forwardKey, String reverseKey) throws SocketTimeoutException{
		Packet packet = Transform.GetResult(paramMap, forwardKey);// 加密数据
		String params = Jackson.writeObject2Json(packet);// 对象转json、字符串
		// 请求远程url
		String jsonOrderCheck = HttpClientUtil.post(url, params);
		// 解析返回的数据
		ProcessResult pr;
		try {
			pr = (ProcessResult) Transform.GetPacket(jsonOrderCheck, ProcessResult.class, reverseKey);
		} catch (Exception e) {
			e.printStackTrace();
			pr = new ProcessResult();
			pr.setState(false);
			pr.setMessage("调用远程接口异常");
			return pr;
		}
		return pr;
	}

	/**
	 * 发送post请求
	 *
	 */
	public static String postOA(String url, String params) {
		String result = "";

		CloseableHttpClient httpClient = HttpClients.createDefault();
		//  设置请求和传输超时时间,如果不设置会导致死链接在24时以上
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		StringEntity strEntity;
		try {
			strEntity = new StringEntity(params, "UTF-8");
			httpPost.setEntity(strEntity);
			strEntity.setContentType("application/json");
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送post请求,返回ProcessResult 结果（OMS调用OA平台接口）
	 * @param url  		请求url
	 * @param paramMap  参数map对象
	 * @return ProcessResult
	 */
	public static ProcessResult postToOA(String url,  Map<String, Object> paramMap) {
		//  加密数据
		Packet packet = Transform.GetResultOA(paramMap);
		//  对象转json、字符串
		String params = Jackson.writeObject2Json(packet);
		//  请求远程url
		String jsonOrderCheck = HttpClientUtil.postOA(url, params);
		//  解析返回的数据
		ProcessResult pr;
		try {
			pr = (ProcessResult) Transform.GetPacketOA(jsonOrderCheck, ProcessResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			pr=new ProcessResult();
			pr.setState(false);
			pr.setMessage("调用远程接口异常");
			return pr;
		}
		return pr;
	}

	/**
	 * 发送post请求,返回ProcessResult 结果（OMS调用OA平台接口）
	 * @param url  		请求url
	 * @param paramMap  参数map对象
	 * @return ProcessResult
	 */
	public static ProcessResult postToOAByReverse(String url,  Map<String, Object> paramMap) {
		//  加密数据
		Packet packet = Transform.GetResultOA(paramMap);
		//  对象转json、字符串
		String params = Jackson.writeObject2Json(packet);
		//  请求远程url
		String jsonOrderCheck = HttpClientUtil.postOA(url, params);
		//  解析返回的数据
		ProcessResult pr;
		try {
			pr = (ProcessResult) Transform.GetPacketOAByReverse(jsonOrderCheck, ProcessResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			pr=new ProcessResult();
			pr.setState(false);
			pr.setMessage("调用远程接口异常");
			return pr;
		}
		return pr;
	}
	
	/**
	 * 发送post请求,返回ProcessResult 结果
	 * @param url  请求url
	 * @param paramMap  参数map对象
	 * @return ProcessResult
	 */
	public static GridResult postGrid(String url,  Map<String, Object> paramMap) throws SocketTimeoutException{
		 //erp调用见证宝平台接口
		Packet packet = Transform.GetResultJzb(paramMap);// 加密数据
		String params = Jackson.writeObject2Json(packet);// 对象转json、字符串
		// 请求远程url
		String jsonOrderCheck = HttpClientUtil.post(url, params);
		// 解析返回的数据
		GridResult gr;
		try {
			gr = (GridResult) Transform.GetPacketJzb(jsonOrderCheck, GridResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			gr=new GridResult();
			gr.setState(false);
			gr.setMessage("调用远程接口异常");
			return gr;
		}
		return gr;
	}
}