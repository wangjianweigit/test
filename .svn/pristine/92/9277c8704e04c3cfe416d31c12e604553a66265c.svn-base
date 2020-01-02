package com.tk.oms.decoration.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.decoration.service.DirectoryService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : DirectoryControl
 * 目录管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-18
 */
@Controller
@RequestMapping("directory")
public class DirectoryControl {
	@Resource
	private DirectoryService directoryService;
	/**
     * @api {post} /{project_name}/directory/all 
     * @apiName all
     * @apiGroup directory
     * @apiDescription  查询目录结构层级
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 目录结构层级列表
     * @apiSuccess {number}   	obj.id 节点id
     * @apiSuccess {string}   	obj.directory_name 文件夹名称
     * @apiSuccess {string}		obj.remark	备注信息
     * @apiSuccess {char}		obj.file_content_type	文件夹内容(1：存储文件和文件夹，2：存储文件夹)
     * @apiSuccess {string}		obj.file_type_ids	允许存储的文件类型，可以多选
     * @apiSuccess {string}		obj.picture_file_formats	图片文件格式,根据文件类型而定，多个则以逗号分隔。
     * @apiSuccess {string}		obj.video_file_formats		视频文件格式,根据文件类型而定，多个则以逗号分隔。
     * @apiSuccess {number}   	obj.picture_size_limit	当前文件夹中，单个文件的大小限制，存储单位为B（1GB=1024MB；1MG = 1024KB；1KB=1024B）
     * @apiSuccess {number}   	obj.picture_height	图片固定尺寸高度,固定尺寸必填该值
     * @apiSuccess {number}   	obj.picture_width	图片固定尺寸宽度,固定尺寸必填该值
     * @apiSuccess {number}   	obj.video_size_limit	当前文件夹中，单个文件的大小限制，存储单位为B（1GB=1024MB；1MG = 1024KB；1KB=1024B）
     * @apiSuccess {number}   	obj.file_quantity_limit	文件数量上限,当前文件夹中最多允许存放的文件个数
     * @apiSuccess {char}   	obj.is_share	是否共享
     * @apiSuccess {number}   	obj.parent_id 父节点id
     * @apiSuccess {date}     	obj.create_date 创建日期
     * @apiSuccess {number}   	obj.level 层级值
     * 
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDirectoryAll(HttpServletRequest request) {
        return Transform.GetResult(directoryService.queryDirectoryAll(request));
    }
    
    /**
     * @api {post} /{project_name}/directory/remove
     * @apiName remove
     * @apiGroup directory
     * @apiDescription  删除节点信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}   	id	节点ID
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj
     * 
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeDirectory(HttpServletRequest request) {
        return Transform.GetResult(directoryService.removeDirectory(request));
    }
    
    /**
     * @api {post} /{project_name}/directory/edit
     * @apiName edit
     * @apiGroup directory
     * @apiDescription  编辑节点信息
     * @apiVersion 0.0.1
     * 
     * @apiParam {number}   	id	节点ID
     * @apiParam {string}   	directory_name 文件夹名称
     * @apiParam {string}		remark	备注信息
     * @apiParam {char}			file_content_type	文件夹内容(1：存储文件和文件夹，2：存储文件夹)
     * @apiParam {string}		file_type_ids	允许存储的文件类型，可以多选
     * @apiParam {string}		picture_file_formats	图片文件格式,根据文件类型而定，多个则以逗号分隔。
     * @apiParam {string}		video_file_formats		视频文件格式,根据文件类型而定，多个则以逗号分隔。
     * @apiParam {number}   	picture_size_limit	当前文件夹中，单个文件的大小限制，存储单位为B（1GB=1024MB；1MG = 1024KB；1KB=1024B）
     * @apiParam {number}   	picture_height	图片固定尺寸高度,固定尺寸必填该值
     * @apiParam {number}   	picture_width	图片固定尺寸宽度,固定尺寸必填该值
     * @apiParam {number}   	video_size_limit	当前文件夹中，单个文件的大小限制，存储单位为B（1GB=1024MB；1MG = 1024KB；1KB=1024B）
     * @apiParam {number}   	file_quantity_limit	文件数量上限,当前文件夹中最多允许存放的文件个数
     * @apiParam {char}   		is_share	是否共享
     * @apiParam {number}   	parent_id 父节点id
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editDirectory(HttpServletRequest request) {
        return Transform.GetResult(directoryService.editDirectory(request));
    }
    
    /**
     * @api {post} /{project_name}/directory/file_type 
     * @apiName file_type
     * @apiGroup directory
     * @apiDescription  查询文件类型和扩展名信息
     * @apiVersion 0.0.1
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 文件类型和扩展名信息
     * @apiSuccess {number}   	obj.id 
     * @apiSuccess {string}   	obj.file_type_name  文件类型名称
     * @apiSuccess {string}		obj.file_type_code	未见类型代码
     * @apiSuccess {string}		obj.file_extension	文件类型允许的扩展名
     * 
     */
    @RequestMapping(value = "/file_type", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFileTypeList(HttpServletRequest request) {
        return Transform.GetResult(directoryService.queryFileTypeList(request));
    }
    /**
    *
    * @api {post} /{project_name}/directory/verify_upload_file 验证图片是否可以上传
    * @apiGroup directory
    * @apiDescription  上传文件至平台素材库时，验证图片是否可以上传
    * @apiVersion 0.0.1
    *
    * @apiParam {number} id	    				目录模板中的ID
    * @apiParam {string} file_type	    		文件类型  video=视频   picture=图片
    * @apiParam {string} file_format	    	文件扩展名，小写
    * @apiParam {number} file_size	    		文件大小，单位为B
    * @apiParam {number} file_height	    	文件尺寸，高度，单位像素
    * @apiParam {number} file_width	    		文件尺寸，宽度，单位像素
    * 
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:允许上传  false:失败不允许上传
    * @apiSuccess {string} message 接口返回信息注释.
    */
   @RequestMapping(value = "/verify_upload_file", method = RequestMethod.POST)
   @ResponseBody
   public Packet verifyFileDirectoryInfoById(HttpServletRequest request) {
   	return Transform.GetResult(directoryService.verifyFileDirectoryInfoById(request));
   }
   /**
    *
    * @api {post} /{project_name}/directory/product_img_child 获取【商品图片】所有子目录
    * @apiGroup directory
    * @apiDescription  获取素材目录管理中，商品图片的所有子目录
    * @apiVersion 0.0.1
    *
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:允许上传  false:失败不允许上传
    * @apiSuccess {string} message 接口返回信息注释.
    */
   @RequestMapping(value = "/product_img_child", method = RequestMethod.POST)
   @ResponseBody
   public Packet queryProductImgChild(HttpServletRequest request) {
	   return Transform.GetResult(directoryService.queryProductImgChild(request));
   }
}
