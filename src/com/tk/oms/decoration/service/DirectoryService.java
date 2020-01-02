package com.tk.oms.decoration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.decoration.dao.DirectoryDao;
import com.tk.sys.util.HttpClientUtil;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import com.tk.sys.util.Utils;

@Service("DirectoryService")
public class DirectoryService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${oss_service_url}")
	private String oss_service_url;// oss服务地址
	@Value("${file_directory_used}")
	private String file_directory_used;// 校验文件模板是否被使用
	@Value("${file_directory_virtual_count}")
	private String file_directory_virtual_count;// 根据文件夹的完整路径，获取当前文件夹下的文件数量
	@Resource
	private DirectoryDao directoryDao;

	public ProcessResult queryDirectoryAll(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
		
		List<Map<String, Object>> dataList = directoryDao.queryDirectoryAll();
		for(Map<String, Object> map : dataList){
			if(!StringUtils.isEmpty(map.get("picture_file_formats"))) {
				String picture_file_formats = map.get("picture_file_formats").toString();
				String[] picture_file_formatss = picture_file_formats.split(",");
				map.put("picture_file_formats", picture_file_formatss);
			}
			if(!StringUtils.isEmpty(map.get("video_file_formats"))) {
				String video_file_formats = map.get("video_file_formats").toString();
				String[] video_file_formatss = video_file_formats.split(",");
				map.put("video_file_formats", video_file_formatss);
			}
		}
		pr.setState(true);
		pr.setMessage("查询成功");
		pr.setObj(dataList);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("新增失败："+e.getMessage());
        }

		return pr;
	}
	/**
	 * 删除节点信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeDirectory(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
	        	pr.setState(false);
	        	pr.setMessage("参数缺失");
                return pr;
            }
	        if(paramMap.get("id").equals(1)){
	        	pr.setState(false);
	        	pr.setMessage("该目录不能删除");
	        	return pr;
	        }else{
	        	Map<String, Object> param = new HashMap<String, Object>();
		        param.put("directory_id", paramMap.get("id"));
		        //1.远程调用OSS接口 验证目录模板是否被使用
		        pr=HttpClientUtil.post(oss_service_url+file_directory_used,param);
		        if(pr.getState()){
			        //2.根据节点ID查询是否存在子节点
			        if(directoryDao.queryDirectoryByParentId(paramMap)>0){
			        	pr.setState(false);
			        	pr.setMessage("该节点存在子节点，请先处理子节点");
			        	return pr;
			        }
	    			//3.删除
	    	        if(directoryDao.delete(paramMap)>0){
	    	        	pr.setState(true);
	    	        	pr.setMessage("删除成功");
	    	        }else{
	    	        	pr.setState(false);
	    	        	pr.setMessage("删除失败");
	    	        }
		        }
	        }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
		return pr;
	}
	/**
	 * 编辑节点信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editDirectory(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
	        	pr.setState(false);
	        	pr.setMessage("参数缺失");
                return pr;
            }
	        if(StringUtils.isEmpty(paramMap.get("type"))){
	        	pr.setState(false);
	        	pr.setMessage("缺少参数，节点类型");
                return pr;
	        }
	        if(StringUtils.isEmpty(paramMap.get("picture_height"))){
	        	paramMap.put("picture_height", 0);
	        }
	        if(StringUtils.isEmpty(paramMap.get("picture_width"))){
	        	paramMap.put("picture_width", 0);
	        }
	        if("1".equals(paramMap.get("type"))){
	        	if(paramMap.get("file_type_ids").toString().indexOf("picture") == -1 ){
	        		paramMap.put("picture_file_formats", "");
	        		paramMap.put("picture_size_limit", "");
	        		paramMap.put("picture_height", "");
	        		paramMap.put("picture_width", "");
	        	}else if(paramMap.get("file_type_ids").toString().indexOf("video") == -1){
	        		paramMap.put("video_file_formats", "");
	        		paramMap.put("video_size_limit", "");
	        	}
	        	
	        	String file_type_ids = "";
				if(paramMap.get("file_type_ids") instanceof List<?>){
					String file_type_ids_str =paramMap.get("file_type_ids").toString().replace(" ", "");
					file_type_ids = file_type_ids_str.substring(1, file_type_ids_str.length()-1);
				}else if(paramMap.get("file_type_ids") instanceof String){
					file_type_ids = paramMap.get("file_type_ids").toString();
				}
				paramMap.put("file_type_ids", file_type_ids);

				
				String picture_file_formats = "";
				if(paramMap.get("picture_file_formats") instanceof List<?>){
					String picture_file_format_str =paramMap.get("picture_file_formats").toString().replace(" ", "");
					picture_file_formats = picture_file_format_str.substring(1, picture_file_format_str.length()-1);
				}else if(paramMap.get("picture_file_formats") instanceof String){
					picture_file_formats = paramMap.get("picture_file_formats").toString();
				}
				paramMap.put("picture_file_formats", picture_file_formats);

				
				String video_file_formats = "";
				if(paramMap.get("video_file_formats") instanceof List<?>){
					String video_file_formats_str =paramMap.get("video_file_formats").toString().replace(" ", "");
					video_file_formats = video_file_formats_str.substring(1, video_file_formats_str.length()-1);
				}else if(paramMap.get("video_file_formats") instanceof String){
					video_file_formats = paramMap.get("video_file_formats").toString();
				}
				paramMap.put("video_file_formats", video_file_formats);
	        }
	        //1.根据节点名称查询是否存在
	        if(directoryDao.queryDirectoryByDirectoryName(paramMap)>0){
	        	pr.setState(false);
	        	pr.setMessage("节点名称已存在");
	        	return pr;
	        }
	        
	        if(StringUtils.isEmpty(paramMap.get("id"))){
	        	if("1".equals(paramMap.get("type"))){
		        	//2.新增
		        	if(directoryDao.insert(paramMap)>0){
		        		pr.setState(true);
			        	pr.setMessage("新增成功");
			        	pr.setObj(paramMap);
		        	}else{
		        		pr.setState(false);
			        	pr.setMessage("新增失败");
		        	}
	        	}else{
	        		Map<String, Object> map = new HashMap<String, Object>();
		        	map.put("name", paramMap.get("name"));
		        	map.put("type", paramMap.get("type"));
		        	map.put("parent_id", paramMap.get("parent_id"));
		        	map.put("public_user_id", paramMap.get("public_user_id"));
	        		//2.新增
		        	if(directoryDao.insertByType(map)>0){
		        		pr.setState(true);
			        	pr.setMessage("新增成功");
			        	pr.setObj(map);
		        	}else{
		        		pr.setState(false);
			        	pr.setMessage("新增失败");
		        	}
	        	}
	        }else{
	        	if("1".equals(paramMap.get("type"))){
	        		//3.更新
			        if(directoryDao.update(paramMap)>0){
			        	pr.setState(true);
			        	pr.setMessage("更新成功");
			        }else{
			        	pr.setState(false);
			        	pr.setMessage("更新失败");
			        }
	        	}else{
	        		Map<String, Object> param = new HashMap<String, Object>();
	     	        param.put("directory_id", paramMap.get("id"));
	     	        //1.远程调用OSS接口 验证目录模板是否被使用
	     	        pr=HttpClientUtil.post(oss_service_url+"file/directory_used",param);
	     	        if(pr.getState()){
		        		Map<String, Object> map = new HashMap<String, Object>();
		        		map.put("id", paramMap.get("id"));
			        	map.put("name", paramMap.get("name"));
			        	map.put("type", paramMap.get("type"));
			        	//3.更新
				        if(directoryDao.updateByType(map)>0){
				        	pr.setState(true);
				        	pr.setMessage("更新成功");
				        }else{
				        	pr.setState(false);
				        	pr.setMessage("更新失败");
				        }
	     	        }
	        	}
        		
	        }
	        
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
		return pr;
	}
	/**
	 * 查询文件类型和扩展名信息
	 * @param request
	 * @return
	 */
	public ProcessResult queryFileTypeList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {

	        List<Map<String, Object>> retMap = directoryDao.queryFileTypeList(paramMap);
	        pr.setState(true);
	        pr.setMessage("查询成功");
	        pr.setObj(retMap);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }
		return pr;
	}
	
	
	/**
	 * 验证当前上传的图片是否符合文件夹的要求
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult verifyFileDirectoryInfoById(HttpServletRequest request) {

		ProcessResult pr = new ProcessResult();
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json))
				params = (Map<String,Object>)Transform.GetPacket(json, Map.class);
			if(params == null||params.isEmpty()){
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("id"))){
				pr.setState(false);
				pr.setMessage("缺少参数，目录管理数据表ID[id]");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("file_type"))){
				pr.setState(false);
				pr.setMessage("缺少参数，文件类型[file_type]");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("file_format"))){
				pr.setState(false);
				pr.setMessage("缺少参数，文件扩展名[file_format]");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("file_size"))){
				pr.setState(false);
				pr.setMessage("缺少参数，文件大小[file_size]");
				return pr;
			}
			if(StringUtils.isEmpty(params.get("directory_full_name"))){
				pr.setState(false);
				pr.setMessage("缺少参数，文件所在目录[directory_full_name]");
				return pr;
			}
			Map<String,Object>  fileMap = directoryDao.queryDirectoryDetail(params);
			if(fileMap==null || fileMap.isEmpty()){
				pr.setState(false);
				pr.setMessage("当前文件夹状态异常，请重试");
				return pr;
			}
			if("2".equals(fileMap.get("FILE_CONTENT_TYPE").toString())){//1.仅允许文件    2.仅允许文件夹
				pr.setState(false);
				pr.setMessage("当前文件夹不允许上传文件");
				return pr;
			}
			if(StringUtils.isEmpty(fileMap.get("FILE_TYPE_IDS"))
					||fileMap.get("FILE_TYPE_IDS").toString().indexOf(params.get("file_type").toString())<0){//1.仅允许文件    2.仅允许文件夹
				pr.setState(false);
				pr.setMessage("当前文件夹不允许上传该类型文件");
				return pr;
			}
			if(!StringUtils.isEmpty(params.get("PICTURE_FILE_FORMATS"))
					&&"picture".equals(params.get("file_type").toString()) 
					&& fileMap.get("PICTURE_FILE_FORMATS").toString().indexOf(params.get("file_format").toString())<0){//图片以及视频的扩展名
				pr.setState(false);
				pr.setMessage("当前文件夹仅允许上传以下格式图片["+fileMap.get("PICTURE_FILE_FORMATS")+"]");
				return pr;
			}
			if(!StringUtils.isEmpty(params.get("VIDEO_FILE_FORMATS"))
					&&"video".equals(params.get("file_type").toString()) 
					&&fileMap.get("VIDEO_FILE_FORMATS").toString().indexOf(params.get("file_format").toString())<0){//图片以及视频的扩展名
				pr.setState(false);
				pr.setMessage("当前文件夹仅允许上传以下格式视频["+fileMap.get("VIDEO_FILE_FORMATS")+"]");
				return pr;
			}
			/**
			 * 数据库中的单位为KB
			 */
			long PICTURE_SIZE_LIMIT = StringUtils.isEmpty(fileMap.get("PICTURE_SIZE_LIMIT"))?0:Long.parseLong(fileMap.get("PICTURE_SIZE_LIMIT").toString());
			PICTURE_SIZE_LIMIT = PICTURE_SIZE_LIMIT*1024;
			long VIDEO_SIZE_LIMIT = StringUtils.isEmpty(fileMap.get("VIDEO_SIZE_LIMIT"))?0:Long.parseLong(fileMap.get("VIDEO_SIZE_LIMIT").toString());
			VIDEO_SIZE_LIMIT = VIDEO_SIZE_LIMIT*1024;
			long file_size = Long.parseLong(params.get("file_size").toString());
			if("picture".equals(params.get("file_type").toString()) 
					&& file_size>PICTURE_SIZE_LIMIT){//图片大小限制
				pr.setState(false);
				pr.setMessage("当前文件夹要求图片大小超过上限为["+Utils.getPrintSize(PICTURE_SIZE_LIMIT)+"]");
				return pr;
			}
			if("video".equals(params.get("file_type").toString()) 
					&& file_size>VIDEO_SIZE_LIMIT){//视频大小限制
				pr.setState(false);
				pr.setMessage("当前文件夹要求视频大小超过上限为["+Utils.getPrintSize(PICTURE_SIZE_LIMIT)+"]");
				return pr;
			}
			/*******验证图片高宽*************/
			if("picture".equals(params.get("file_type").toString())){
				if(StringUtils.isEmpty(params.get("file_height"))){
					pr.setState(false);
					pr.setMessage("缺少参数，文件高度[file_height]");
					return pr;
				}
				if(StringUtils.isEmpty(params.get("file_width"))){
					pr.setState(false);
					pr.setMessage("缺少参数，文件宽度[file_width]");
					return pr;
				}
				long PICTURE_HEIGHT = StringUtils.isEmpty(fileMap.get("PICTURE_HEIGHT"))?0:Long.parseLong(fileMap.get("PICTURE_HEIGHT").toString());
				long PICTURE_WIDTH = StringUtils.isEmpty(fileMap.get("PICTURE_WIDTH"))?0:Long.parseLong(fileMap.get("PICTURE_WIDTH").toString());
				long file_height = Long.parseLong(params.get("file_height").toString());
				long file_width = Long.parseLong(params.get("file_width").toString());
				if("picture".equals(params.get("file_type").toString()) 
						&&PICTURE_HEIGHT!=0 && PICTURE_WIDTH!=0
						&& (file_height!=PICTURE_HEIGHT || file_width!=PICTURE_WIDTH)
						){//图片尺寸限制
					pr.setState(false);
					pr.setMessage("当前文件夹要求图片尺寸为["+PICTURE_WIDTH+"*"+PICTURE_HEIGHT+"]");
					return pr;
				}
			}
			/*********************判断文件数量是否达到上限***********************/
			//文件上传数量上限
			int FILE_QUANTITY_LIMIT = StringUtils.isEmpty(fileMap.get("FILE_QUANTITY_LIMIT"))?0:Integer.parseInt(fileMap.get("FILE_QUANTITY_LIMIT").toString());
			int current_amount = 0;//已经上传的数量
			params.put("directory_type","1");		//共享文件夹，默认directory_type=1
	        //1.远程调用OSS接口 验证目录模板是否被使用
	        pr=HttpClientUtil.post(oss_service_url+file_directory_virtual_count,params);
	        if(pr.getState()){
	        	current_amount = (Integer) pr.getObj();
	        }else{
	        	pr.setState(false);
				pr.setMessage("获取文件夹中的文件数量接口调用异常");
				return pr;
	        }
	        if(current_amount>=FILE_QUANTITY_LIMIT){
	        	pr.setState(false);
				pr.setMessage("当前文件夹文件数量已到达上限["+FILE_QUANTITY_LIMIT+"]");
				return pr;
	        }
			pr.setState(true);
			pr.setMessage("该文件可以上传到当前文件夹中");
			return pr;
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return pr;
	}
	/**
	 * 获取素材目录管理中，商品图片的所有子目录
	 * @param request
	 * @return
	 */
	public ProcessResult queryProductImgChild(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<Map<String, Object>> dataList = directoryDao.queryProductImgChild(paramMap);
			pr.setState(true);
			pr.setMessage("查询成功");
			pr.setObj(dataList);
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }

		return pr;
	}
}
