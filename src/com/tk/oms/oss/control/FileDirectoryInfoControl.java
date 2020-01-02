package com.tk.oms.oss.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.oss.server.FileDirectoryInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

/**
 * 获取文件夹目录模板相关信息
 * @author songwangwen
 * @date  2017-5-20  下午10:09:13
 */
@Controller
@RequestMapping("/template_directory")
public class FileDirectoryInfoControl {
	@Resource
	private FileDirectoryInfoService  fileDirectoryInfoService;
	
	 /**
     * @api {post} /{project_name}/template_directory/list 获取模板中定义的子目录信息
     * @apiGroup template_directory
     * @apiDescription  获取模板中定义的子目录信息
     * @apiVersion 0.0.1
     *
	 * @apiParam {string} user_type					        所属用户类型  1：入驻商   2：分销商  3:运营总后台用户
	 * @apiParam {number} user_id					        用户ID
	 * @apiParam {string} directory_type 				文件夹类型   1：表示由运营总后台定义的不可修改的文件夹    2：表示用户自定义的文件夹
	 * @apiParam {number} [directory_id=0] 				父目录ID，不传值则为0
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj  货号集合
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFileDirectoryInfoList(HttpServletRequest request) {
        return Transform.GetResult(this.fileDirectoryInfoService.queryFileDirectoryInfoList(request));
    }
    
    /**
     * @api {post} /{project_name}/template_directory/contents 获取文件夹内包含的子文件信息
     * @apiGroup template_directory
     * @apiDescription  获取文件夹内包含的子文件信息
     * @apiVersion 0.0.1
     *
	 * @apiParam {string} user_type					        所属用户类型  1：入驻商   2：分销商  3:运营总后台用户
	 * @apiParam {number} user_id					        用户ID
	 * @apiParam {string} directory_type 				文件夹类型   1：表示由运营总后台定义的不可修改的文件夹    2：表示用户自定义的文件夹
     * @apiParam {number} [directory_id=0] 				父目录ID，不传值则为0

     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj  货号集合
     */
    @RequestMapping(value = "/contents", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFileDirectoryContents(HttpServletRequest request) {
        return Transform.GetResult(this.fileDirectoryInfoService.queryFileDirectoryContents(request));
    }
    /**
     * @api {post} /{project_name}/template_directory/check_itemnumber 查询该货号是否存
     * @apiGroup template_directory
     * @apiDescription  查询该货号是否存在或者当前用户是否可以查看该货号
     * @apiVersion 0.0.1
     *
     * @apiParam {string} product_itemnumber		 货号信息
     * @apiParam {number} [stationed_user_id]		 入驻商ID		

     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/check_itemnumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet checkProductItemNumber(HttpServletRequest request) {
    	return Transform.GetResult(this.fileDirectoryInfoService.checkProductItemNumber(request));
    }
    /**
     * @api {post} /{project_name}/template_directory/itemnumbers 	获取入驻商的货号信息
     * @apiGroup template_directory
     * @apiDescription  根据入驻商ID获取入驻商的货号信息
     * @apiVersion 0.0.1
     *
     * @apiParam {number} stationed_user_id		 入驻商ID		

     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/itemnumbers", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductItemNumbers(HttpServletRequest request) {
    	return Transform.GetResult(this.fileDirectoryInfoService.queryProductItemNumbers(request));
    }
}
