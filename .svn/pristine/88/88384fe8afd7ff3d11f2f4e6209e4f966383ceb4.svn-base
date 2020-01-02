package com.tk.oms.oss.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.oss.dao.FileDirectoryInfoDao;
import com.tk.oms.oss.entity.FileDirectoryInfo;
import com.tk.oms.oss.entity.OssDirectory;
import com.tk.oms.oss.entity.OssFileDto;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * **************************************************************************************
			 * 分享图片查询时，使用实际目录与虚拟目录结合的方式完成目录树的展示，例如：
			 * 运营总后定义了如下的目录文件：
			 * |-图片文件
			 * 		|-主图
			 * 			|-主图1
			 * 			|-主图2
			 * 		|-装修图
			 * 			|-装修模板1
			 * 			|-装修模板2
			 * 		|-颜色图    			 * 			
			 * |-活动图
			 * 		|-显示折扣
			 * 		|-满减活动
			 * 即使当前的入驻商不存在任何商品，其图片空间中也可见以下目录结构
			 *  |-分享素材
			 *  	|-活动图
			 * 			|-显示折扣
			 * 			|-满减活动
			 * 
			 * 则当一个入住商的一个商品审批同通后，假设生成的商品货号为9999，在该入驻商的图片空间中，即会展示如下的目录结构
			 * |-分享素材
			 * 		|-图片文件
			 * 			|-9999
			 * 				|-主图
			 * 					|-主图1
			 * 					|-主图2
			 * 				|-装修图
			 * 					|-装修模板1
			 * 					|-装修模板2
			 * 				|-颜色图    
			 *  		|-活动图
			 * 				|-显示折扣
			 * 				|-满减活动
			 ********************************获取子目录-start***********************************************
 * @author songwangwen
 * @date  2017-5-20  下午10:49:10
 */
@Service("FileDirectoryInfoService.java")
public class FileDirectoryInfoService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	//可选的用户类型
	private static final String[] user_type_arr = new String[]{"1","2","3"}; 
	//可选的文件夹类型
	private static final String[] directory_type_arr = new String[]{"1","2"}; 
	@Resource
	private FileDirectoryInfoDao fileDirectoryInfoDao;

	/**
	 * 根据父id获取子文件夹信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFileDirectoryInfoList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    	Map<String,Object> params = null;
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if(!StringUtils.isEmpty(json)){
    			params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
    		}
    		if(params==null || params.isEmpty()){
    			pr.setState(false);
    			pr.setMessage("缺少参数");
    			return pr;
    		}
    		if(StringUtils.isEmpty(params.get("user_type"))
    				||!Arrays.asList(user_type_arr).contains(params.get("user_type").toString())){
    			pr.setState(false);
    			pr.setMessage("缺少用户类型[user_type],或用户类型传值非法");
    			return pr;
    		}
    		if(StringUtils.isEmpty(params.get("user_id"))){
    			pr.setState(false);
    			pr.setMessage("缺少用户ID[user_id]");
    			return pr;
    		}
    		try {
				Long.parseLong(params.get("user_id").toString());
			} catch (Exception e) {
				pr.setState(false);
    			pr.setMessage("用户ID[user_id]必须是正整数");
    			return pr;
			}
    		if(StringUtils.isEmpty(params.get("directory_type"))
    				||!Arrays.asList(directory_type_arr).contains(params.get("directory_type").toString())){
    			pr.setState(false);
    			pr.setMessage("缺少文件夹类型[directory_type],或文件夹类型传值非法");
    			return pr;
    		}
    		long file_directory_info_id = 0;
    		if(StringUtils.isEmpty(params.get("file_directory_info_id"))){
    			params.put("file_directory_info_id",0);
    		}else{
    			file_directory_info_id = Long.parseLong(params.get("file_directory_info_id").toString());
    		}
    		/**************************************************************************************
    		 * 分享图片查询时，使用实际目录与虚拟目录结合的方式完成目录树的展示，例如：
    		 * 运营总后定义了如下的目录文件：
    		 * |-图片文件
    		 * 		|-主图
    		 * 			|-主图1
    		 * 			|-主图2
    		 * 		|-装修图
    		 * 			|-装修模板1
    		 * 			|-装修模板2
    		 * 		|-颜色图    			 * 			
    		 * |-活动图
    		 * 		|-显示折扣
    		 * 		|-满减活动
    		 * 即使当前的入驻商不存在任何商品，其图片空间中也可见以下目录结构
    		 *  |-分享素材
    		 *  	|-活动图
    		 * 			|-显示折扣
    		 * 			|-满减活动
    		 * 
    		 * 则当一个入住商的一个商品审批同通后，假设生成的商品货号为9999，在该入驻商的图片空间中，即会展示如下的目录结构
    		 * |-分享素材
    		 * 		|-图片文件
    		 * 			|-9999
    		 * 				|-主图
    		 * 					|-主图1
    		 * 					|-主图2
    		 * 				|-装修图
    		 * 					|-装修模板1
    		 * 					|-装修模板2
    		 * 				|-颜色图    
    		 *  		|-活动图
    		 * 				|-显示折扣
    		 * 				|-满减活动
    		 *****************************************************************************************/
			params.put("stationed_user_id",params.get("stationed_id"));
    		if(!StringUtils.isEmpty(params.get("search_itemnumber"))){//如果搜索商品货号，则直接查询货号信息
				List<String> itemnumberList = fileDirectoryInfoDao.queryProductItemNumberList(params);
				if(itemnumberList!=null && !itemnumberList.isEmpty()){
					/**
					 * 构造固定节点【商品图片】
					 */
					Map<String, Object> product_map = new HashMap<String, Object>();
					product_map.put("id",1);
					product_map.put("pId",0);
					product_map.put("open",true);
					product_map.put("name","商品图片");
					product_map.put("isParent",true);
					product_map.put("directory_type","1");//默认分享文件夹
					product_map.put("file_directory_info_id",FileDirectoryInfo.PRODUCT_IMG_ID);
					result.add(product_map);
					for(String itemnumber:itemnumberList){//循环一棵树，直至最末端的就节点
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id",itemnumber);
						map.put("pId",1);
						map.put("name",itemnumber);
						map.put("isParent",true);
						map.put("directory_type","1");//默认分享文件夹
						map.put("file_directory_info_id",FileDirectoryInfo.PRODUCT_IMG_ID);
						map.put("itemnumber",itemnumber);
						Map<String,Object> font = new HashMap<String, Object>();
                   	 	font.put("color","red");
                   	 	map.put("font",font);//默认文件夹为红色
						result.add(map);
					}
					pr.setState(true);
		    		pr.setMessage("查询成功");
		    		pr.setObj(result);
	    			return pr;
				}else{
					pr.setState(false);
		    		pr.setMessage("没有与搜索条件匹配的货号");
		    		pr.setObj(result);
	    			return pr;
				}
    		}else{
    			if(file_directory_info_id == FileDirectoryInfo.PRODUCT_IMG_ID 
    					&& StringUtils.isEmpty(params.get("itemnumber"))){//文件图片，展示货号列表
    				List<String> itemnumberList = null;
    				if(StringUtils.isEmpty(params.get("stationed_id"))){
    					itemnumberList = fileDirectoryInfoDao.queryProductItemNumberList(null);//服务商，则获取全部的货号
    				}else{
    					//入驻商，则获取自己的货号
    					itemnumberList = fileDirectoryInfoDao.queryProductItemNumberList(params);
    				}
    				for(String itemnumber:itemnumberList){//循环一棵树，直至最末端的就节点
    					Map<String, Object> map = new HashMap<String, Object>();
    					map.put("id",itemnumber);
    					map.put("name",itemnumber);
    					map.put("isParent",true);
    					map.put("directory_type","1");//默认分享文件夹
    					map.put("file_directory_info_id",file_directory_info_id);
    					map.put("itemnumber",itemnumber);
    					result.add(map);
    				}
    			}else{
    				List<FileDirectoryInfo> directoryTempList = fileDirectoryInfoDao.queryFileDirectoryInfoList(file_directory_info_id);
    				//目录初始化，仅需要展示当前的目录模板中的根目录即可
    				for(FileDirectoryInfo d:directoryTempList){//循环一棵树，直至最末端的就节点
    					Map<String, Object> map = new HashMap<String, Object>();
    					if(!StringUtils.isEmpty(params.get("itemnumber"))){
    						map.put("id", params.get("itemnumber")+""+d.getId());
    					}else{
    						map.put("id", d.getId());
    					}
    					map.put("name", d.getDirectory_name().replaceAll("/", ""));
    					map.put("isParent", d.isParent());
    					map.put("directory_type","1");//默认分享文件夹
    					map.put("itemnumber",params.get("itemnumber"));
    					map.put("file_directory_info_id",d.getId());
    					result.add(map);
    				}
    			}
    		}
    		pr.setState(true);
    		pr.setMessage("获取子文件夹成功");
    		pr.setObj(result);
    	}catch(Exception e){
    		logger.error(e);
    		e.printStackTrace();
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		return pr;
    	}
    	return pr;
    }
	/**
	 * 获取文件夹内包含的子文件信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryFileDirectoryContents(HttpServletRequest request) {
		//最终返回结果，包含文件夹的子文件夹信息
    	List<OssFileDto> resultList = new ArrayList<OssFileDto>();
		ProcessResult pr = new ProcessResult();
		Map<String,Object> params = null;
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
			}
			if(params==null || params.isEmpty()){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("user_type"))
					||!Arrays.asList(user_type_arr).contains(params.get("user_type").toString())){
				pr.setState(false);
				pr.setMessage("缺少用户类型[user_type],或用户类型传值非法");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("user_id"))){
				pr.setState(false);
				pr.setMessage("缺少用户ID[user_id]");
				return pr;
			}
			try {
				Long.parseLong(params.get("user_id").toString());
			} catch (Exception e) {
				pr.setState(false);
				pr.setMessage("用户ID[user_id]必须是正整数");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("directory_type"))
					||!Arrays.asList(directory_type_arr).contains(params.get("directory_type").toString())){
				pr.setState(false);
				pr.setMessage("缺少文件夹类型[directory_type],或文件夹类型传值非法");
				return pr;
			}
			long file_directory_info_id = 0;
			if(StringUtils.isEmpty(params.get("file_directory_info_id"))){
				params.put("file_directory_info_id",0);
			}else{
				file_directory_info_id = Long.parseLong(params.get("file_directory_info_id").toString());
			}
			
    		List<OssDirectory> dirList = new ArrayList<OssDirectory>();
    		if(file_directory_info_id == FileDirectoryInfo.PRODUCT_IMG_ID 
    				&& StringUtils.isEmpty(params.get("itemnumber"))){//文件图片，展示货号列表
    			List<String> itemnumberList = null;
    			if(StringUtils.isEmpty(params.get("stationed_id"))){
    				itemnumberList = fileDirectoryInfoDao.queryProductItemNumberList(null);//服务商，则获取全部的货号
    			}else{
    				//入驻商，则获取自己的货号
    				params.put("stationed_user_id",params.get("stationed_id"));
    				itemnumberList = fileDirectoryInfoDao.queryProductItemNumberList(params);
    			}
    			for(String itemnumber:itemnumberList){//循环一棵树，直至最末端的就节点
    				 OssDirectory od = new OssDirectory();
					 od.setDirectory_id(itemnumber);
    				 od.setDirectory_name(itemnumber);
    				 od.setDirectory_type("1");
   					 dirList.add(od);
				}
    		}else{
    			List<FileDirectoryInfo> directoryTempList = fileDirectoryInfoDao.queryFileDirectoryInfoList(file_directory_info_id);
    			//目录初始化，仅需要展示当前的目录模板中的根目录即可
    			for(FileDirectoryInfo d:directoryTempList){//循环一棵树，直至最末端的就节点
   					OssDirectory od = new OssDirectory();
   					if(!StringUtils.isEmpty(params.get("itemnumber"))){
						 od.setDirectory_id(params.get("itemnumber")+""+d.getId());
					 }else{
						 od.setDirectory_id(String.valueOf(d.getId()));
					 }
   					od.setDirectory_name(d.getDirectory_name());
   					od.setDirectory_type("1");
  					dirList.add(od);
				}
    		}
    		/********************************获取子目录-end*************************************************/
    		//循环文件夹
    		if (dirList != null && !dirList.isEmpty()) {
    			for (OssDirectory dir : dirList) {
    				resultList.add(OssFileDto.conver(dir));
    			}
    		}
			pr.setState(true);
			pr.setMessage("获取子文件夹成功");
			pr.setObj(resultList);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
			return pr;
		}
		return pr;
	}
	/**
	 * 查询该货号是否存在或者当前用户是否可以查看该货号
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult checkProductItemNumber(HttpServletRequest request) {
		//最终返回结果，包含文件夹的子文件夹信息
		ProcessResult pr = new ProcessResult();
		Map<String,Object> params = null;
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
			}
			if(params==null || params.isEmpty()){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("product_itemnumber"))){
				pr.setState(false);
				pr.setMessage("缺少参数[product_itemnumber]");
				return pr;
			}
			if(fileDirectoryInfoDao.checkProductItemNumber(params)>0){
				pr.setState(true);
				pr.setMessage("当前货号可用");
			}else{
				pr.setState(false);
				pr.setMessage("当前货号不存在或对当前用户不可见");
			}
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
			return pr;
		}
		return pr;
	}
	/**
	 * 查询该货号是否存在或者当前用户是否可以查看该货号
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductItemNumbers(HttpServletRequest request) {
		//最终返回结果，包含文件夹的子文件夹信息
		ProcessResult pr = new ProcessResult();
		Map<String,Object> params = null;
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
			}
			if(params==null || params.isEmpty()){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("stationed_user_id"))){
				pr.setState(false);
				pr.setMessage("缺少参数[stationed_user_id]");
				return pr;
			}
			List<String> itemnumberList = fileDirectoryInfoDao.queryProductItemNumberList(params);
			pr.setState(true);
			pr.setMessage("获取入驻商可见的货号列表成功");
			pr.setObj(itemnumberList);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
			return pr;
		}
		return pr;
	}
}
