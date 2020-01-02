package com.tk.oms.decoration.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.decoration.service.StoreCarouselService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("store_carousel")
public class StoreCarouselControl {
	@Resource
	private StoreCarouselService storeCarouselService;
	
	/**@api{post} /{oms_server}/store_carousel/add_carousel 新增轮播方案
     * @apiGroup store_carousel
     * @apiDescription  新增轮播方案
     * @apiVersion 0.0.1
     * @apiSuccess {string} PLAN_NAME    方案名称
	 * @apiSuccess {string} STATE    方案状态  1（停用）2（启用）
	 * @apiSuccess {number} SWITCH_TIME    图片间切换时间（单位/s）
	 * @apiSuccess {number} CTRATE_USER_ID    创建人ID
	 * @apiSuccess {string} CREATE_DATE    创建时间
	 * @apiSuccess {string} EFFECT_DATE    生效时间
     * @apiSuccess {number} ID    主键
	 * @apiSuccess {number} SITE_ID    站点ID（关联TBL_SITE_INFO的ID）
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 新增轮播方案信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet add_carousel(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try{
    		pr=storeCarouselService.add_carousel(request);
    	}catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/store_carousel/list 查询轮播方案数据
     * @apiGroup store_carousel
     * @apiDescription  查询轮播方案数据
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 查询轮播方案数据
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet QueryList(HttpServletRequest request) {
        return Transform.GetResult(storeCarouselService.QueryList(request));
    }
    
    /**@api{post} /{oms_server}/store_carousel/remove 删除轮播方案
     * @apiGroup store_carousel
     * @apiDescription  删除轮播方案
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 删除轮播方案
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet Delete_info(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = storeCarouselService.removeInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**@api{post} /{oms_server}/store_carousel/change_state 修改轮播状态
     * @apiGroup store_carousel
     * @apiDescription  修改轮播状态
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键
     * @apiSuccess {number} state    状态
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 轮播方案信息
     */
    @RequestMapping(value = "/change_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet ChangeState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = storeCarouselService.changeState(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    /**@api{post} /{oms_server}/store_carousel/change_state 修改轮播状态
     * @apiGroup store_carousel
     * @apiDescription  修改轮播状态
     * @apiVersion 0.0.1
     * @apiSuccess {number} ID    主键
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 轮播方案信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet Query_detailInfo(HttpServletRequest request) {
        return Transform.GetResult(storeCarouselService.queryDetailInfo(request));
    }

    /**@api{post} /{oms_server}/store_carousel/update 修改轮播方案
     * @apiGroup store_carousel
     * @apiDescription  修改轮播方案
     * @apiVersion 0.0.1
     * @apiSuccess {string} PLAN_NAME    方案名称
	 * @apiSuccess {string} STATE    方案状态  1（停用）2（启用）
	 * @apiSuccess {number} SWITCH_TIME    图片间切换时间（单位/s）
	 * @apiSuccess {number} CTRATE_USER_ID    创建人ID
	 * @apiSuccess {string} CREATE_DATE    创建时间
	 * @apiSuccess {string} EFFECT_DATE    生效时间
     * @apiSuccess {number} ID    主键
	 * @apiSuccess {number} SITE_ID    站点ID（关联TBL_SITE_INFO的ID）
	 * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 修改轮播方案信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Packet UpdateInfo(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = storeCarouselService.UpdateInfo(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

}
