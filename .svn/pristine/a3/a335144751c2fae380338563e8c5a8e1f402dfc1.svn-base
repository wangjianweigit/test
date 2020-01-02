package com.tk.oms.decoration.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.decoration.service.AdvertisingService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
@Controller
@RequestMapping("advertising")
public class AdvertisingControl {
	@Resource
	private AdvertisingService advertisingService;
	
	
	
	/**
     * @api{post} /{oms_server}/advertising/info_list 广告位列表
     * @apiGroup advertising
     * @apiDescription  广告位列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告位列表信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/info_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet getInfoList(HttpServletRequest request) {
        return Transform.GetResult(advertisingService.getInfoList(request));
    }

    /**
     * @api{post} /{oms_server}/advertising/detail_list 广告列表
     * @apiGroup advertising
     * @apiDescription  广告列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告列表信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/detail_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetailList(HttpServletRequest request) {
        return Transform.GetResult(advertisingService.queryDetailList(request));
    }
    
    /**
     * @api{post} /{oms_server}/advertising/detail 根据ID获取广告位详情
     * @apiGroup advertising
     * @apiDescription  根据ID获取广告位详情
     * @apiVersion 0.0.1
     * @apiParam{number} [ID] ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 获取广告位详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(advertisingService.queryDetail(request));
    }
    
    /**
     * @api{post} /{oms_server}/advertising/details 根据ID获取广告详情
     * @apiGroup advertising
     * @apiDescription  根据ID获取广告详情
     * @apiVersion 0.0.1
     * @apiParam{number} [ID] ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 获取广告详情
     */
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetails(HttpServletRequest request) {
        return Transform.GetResult(advertisingService.queryDetails(request));
    }

    /**
     * @api{post} /{oms_server}/advertising/add_info 广告位新增
     * @apiGroup advertising
     * @apiDescription  广告位新增
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键ID
	 * @apiSuccess {number} SITE_ID    站点ID（关联TBL_SITE_INFO的ID）
	 * @apiSuccess {string} NAME    广告位名称
	 * @apiSuccess {string} REMARK    广告位说明
	 * @apiSuccess {string} TYPE    类型 。1:自定义  2:图片轮播   3:静态图片
	 * @apiSuccess {number} WIDTH    宽度
	 * @apiSuccess {number} HEIGHT    高度
	 * @apiSuccess {number} CTRATE_USER_ID    创建人ID
	 * @apiSuccess {string} CREATE_DATE    创建时间
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 新增广告位信息
     *
     */
    @RequestMapping(value = "/add_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet addInfo(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = advertisingService.addDvertisingInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/advertising/edit_info 广告位修改
     * @apiGroup advertising
     * @apiDescription  广告位修改
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键ID
	 * @apiSuccess {number} SITE_ID    站点ID（关联TBL_SITE_INFO的ID）
	 * @apiSuccess {string} NAME    广告位名称
	 * @apiSuccess {string} REMARK    广告位说明
	 * @apiSuccess {string} TYPE    类型 。1:自定义  2:图片轮播   3:静态图片
	 * @apiSuccess {number} WIDTH    宽度
	 * @apiSuccess {number} HEIGHT    高度
	 * @apiSuccess {number} CTRATE_USER_ID    创建人ID
	 * @apiSuccess {string} CREATE_DATE    创建时间
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告位修改信息
     *
     */
    @RequestMapping(value = "/edit_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet editInfo(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = advertisingService.editDvertisingInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/advertising/delete_info 广告位删除
     * @apiGroup advertising
     * @apiDescription  广告位删除
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键ID
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告位修改信息
     *
     */
    @RequestMapping(value = "/remove_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteInfo(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = advertisingService.deleteDvertisingInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**@api{post} /{oms_server}/advertising/add_detail 广告新增
     * @apiGroup advertising
     * @apiDescription  广告新增
     * @apiVersion 0.0.1
     @apiSuccess {number} ID    主键ID
    * @apiSuccess {number} SITE_ID    站点ID（关联TBL_SITE_INFO的ID）
    * @apiSuccess {number} DVERTISING_ID    广告位ID
    * @apiSuccess {string} NAME    广告名称
    * @apiSuccess {string} FILE_URL    文件路径
    * @apiSuccess {string} LINK_URL    点击路径
    * @apiSuccess {number} TYPE    类型（1：图片，2：flash）
    * @apiSuccess {number} ORDER_ID    排序ID（越大越靠前）
    * @apiSuccess {string} CONTENT    自定义类型广告内容
    * @apiSuccess {number} CTRATE_USER_ID    创建人ID
    * @apiSuccess {string} CREATE_DATE    创建时间
     *@apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告新增信息
     * 
     */
    @RequestMapping(value = "/add_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet addDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = advertisingService.addDvertisingDetail(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    /**@api{post} /{oms_server}/advertising/sort_detail 广告排序
     * @apiGroup advertising
     * @apiDescription  广告排序
     * @apiVersion 0.0.1
     * @apiSuccess {number} fromId  需要排序的id
     * @apiSuccess {number} toId    需要排序的id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告排序
     * 
     */
    @RequestMapping(value = "/sort_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet sortDetail(HttpServletRequest request) {
    	 ProcessResult pr = new ProcessResult();
         try {
             pr = advertisingService.sortDetail(request);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return Transform.GetResult(pr);
    }
    /**@api{post} /{oms_server}/advertising/edit_detail 广告修改
     * @apiGroup advertising
     * @apiDescription  广告修改
     * @apiVersion 0.0.1
     @apiSuccess {number} ID    主键ID
    * @apiSuccess {number} SITE_ID    站点ID（关联TBL_SITE_INFO的ID）
    * @apiSuccess {number} DVERTISING_ID    广告位ID
    * @apiSuccess {string} NAME    广告名称
    * @apiSuccess {string} FILE_URL    文件路径
    * @apiSuccess {string} LINK_URL    点击路径
    * @apiSuccess {number} TYPE    类型（1：图片，2：flash）
    * @apiSuccess {number} ORDER_ID    排序ID（越大越靠前）
    * @apiSuccess {string} CONTENT    自定义类型广告内容
    * @apiSuccess {number} CTRATE_USER_ID    创建人ID
    * @apiSuccess {string} CREATE_DATE    创建时间
     *@apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告修改信息
     * 
     */
    @RequestMapping(value = "/edit_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet editDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = advertisingService.editDvertisingDetail(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    /**@api{post} /{oms_server}/advertising/delete_detail 广告删除
     * @apiGroup advertising
     * @apiDescription  广告删除
     * @apiVersion 0.0.1
     @apiSuccess {number} ID    主键ID
     *@apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 广告删除信息
     * 
     */
    @RequestMapping(value = "/remove_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = advertisingService.deleteDetail(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }

}
