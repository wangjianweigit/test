package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.AreaProductControlSetService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c), 2017, Tongku
 * FileName : AreaProductControlSetControl
 * 区域控货配置Control
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/11 17:32
 */
@Controller
@RequestMapping("area_product_control")
public class AreaProductControlSetControl {

    @Resource
    private AreaProductControlSetService areaProductControlSetService;

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/area_product_control/list 分页查询区域控货类型列表数据
     * @apiGroup area_product_control
     * @apiName area_product_control_list
     * @apiDescription 获取区域控货类型数据信息
     * @apiSuccess {object} obj                 控货类型列表信息
     * @apiSuccess {number} obj.ID              类型ID
     * @apiSuccess {string} obj.COLOR_CODE      颜色代码
     * @apiSuccess {string} obj.TYPE_NAME       类型名称
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/type_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryTypeList(HttpServletRequest request) {
        return Transform.GetResult(areaProductControlSetService.queryTypeList(request));
    }

    /**
     * @param request
     * @return
     * @api {post} /{project_name}/area_product_control/list 查询商业Pos设备信息接口
     * @apiGroup area_product_control
     * @apiName area_product_control_list
     * @apiDescription 查询区域控货类型数据信息
     * @apiParam {number} id 				    类型ID
     *
     * @apiSuccess {object} obj                 控货类型列表信息
     * @apiSuccess {number} obj.ID              类型ID
     * @apiSuccess {string} obj.COLOR_CODE      颜色代码
     * @apiSuccess {string} obj.TYPE_NAME       类型名称
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息.
     */
    @RequestMapping(value = "/type_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMerchantPosInfo(HttpServletRequest request) {
        return Transform.GetResult(areaProductControlSetService.queryTypeInfo(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/edit 编辑区域控货类型数据信息
     * @apiGroup area_product_control
     * @apiName area_product_control_edit
     * @apiDescription 编辑区域控货类型数据信息
     * @apiParam {number} id 				    类型ID
     * @apiParam {string} color_code 			颜色代码
     * @apiParam {string} type_name 			类型名称
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/type_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editTypeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = areaProductControlSetService.editTypeInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/area_product_control/business_list 区域配置列表（业务）
     * @apiGroup area_product_control
     * @apiName area_product_control_business_list
     * @apiDescription 分页获取控货区域配置列表（业务）
     *
     * @apiParam {string} user_realname 	    用户真实姓名
     * @apiParam {char} user_type 		        用户类型
     * @apiParam {char} state 			        用户状态
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object}  obj                更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {object} obj                 区域配置列表集合
     * @apiSuccess {number} obj.id 				用户ID
     * @apiSuccess {string} obj.user_realname 	用户真实姓名
     * @apiSuccess {char} obj.user_type 		用户类型
     * @apiSuccess {char} obj.state 			用户状态
     * @apiSuccess {string} obj.area_name 		区域名称（以逗号分隔，如：杭州,温州）
     */
    @RequestMapping(value = "/business_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaControlSetPageForBusiness(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.queryAreaControlSetPageForBusiness(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/business_area_list 区域列表（业务）
     * @apiGroup area_product_control
     * @apiName area_product_control_business_area_list
     * @apiDescription 获取控货区域列表（业务）
     *
     * @apiParam {number} user_id 	    用户ID
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object}  obj                更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {object} obj                 区域配置列表集合
     * @apiSuccess {number} obj.id 				区域ID
     * @apiSuccess {string} obj.name 	        区域名称
     * @apiSuccess {number} obj.parent_id 		区域父级ID
     * @apiSuccess {number} obj.check_flag 		是否选中（0：未选中，1：已选中）
     */
    @RequestMapping(value = "/business_area_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaListForBusiness(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.queryAreaListForBusiness(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/business_area_add 区域配置（业务）
     * @apiGroup area_product_control
     * @apiName area_product_control_business_area_add
     * @apiDescription 添加区域配置（业务）
     *
     * @apiParam {number} user_id 	        用户ID
     * @apiParam {number[]} city_list 	    城市ID数组
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object}  obj                更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     */
    @RequestMapping(value = "/business_area_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addAreaControlSetForBusiness(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.areaProductControlSetService.addAreaControlSetForBusiness(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/area_product_control/store_list 区域配置列表（门店）
     * @apiGroup area_product_control
     * @apiName area_product_control_store_list
     * @apiDescription 分页获取控货区域配置列表（门店）
     *
     * @apiParam {string} store_name 	        门店名称
     * @apiParam {string} store_address 		门店地址
     * @apiParam {char} state 			        门店状态
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object}  obj                更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {object} obj                 区域配置列表集合
     * @apiSuccess {number} obj.id 				门店ID
     * @apiSuccess {string} obj.store_name 	    门店名称
     * @apiSuccess {char} obj.store_address 	门店地址
     * @apiSuccess {char} obj.state 			门店状态
     * @apiSuccess {string} obj.area_name 		区域名称（以逗号分隔，如：杭州,温州）
     */
    @RequestMapping(value = "/store_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaControlSetPageForStore(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.queryAreaControlSetPageForStore(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/store_area_list 区域列表（门店）
     * @apiGroup area_product_control
     * @apiName area_product_control_store_area_list
     * @apiDescription 获取控货区域列表（门店）
     *
     * @apiParam {number} store_id 	    门店ID
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object}  obj                更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {object} obj                 区域配置列表集合
     * @apiSuccess {number} obj.id 				区域ID
     * @apiSuccess {string} obj.name 	        区域名称
     * @apiSuccess {number} obj.parent_id 		区域父级ID
     * @apiSuccess {number} obj.check_flag 		是否选中（0：未选中，1：已选中）
     */
    @RequestMapping(value = "/store_area_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAreaListForStore(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.queryAreaListForStore(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/store_area_add 区域配置（门店）
     * @apiGroup area_product_control
     * @apiName area_product_control_store_area_add
     * @apiDescription 添加区域配置（门店）
     *
     * @apiParam {number} store_id 	        门店ID
     * @apiParam {number[]} city_list 	    城市ID数组
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object}  obj                更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     */
    @RequestMapping(value = "/store_area_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addAreaControlSetForStore(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.areaProductControlSetService.addAreaControlSetForStore(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * 控货类型(下拉框)
     * @param request
     * @return
     */
    @RequestMapping(value = "/comboBox_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet comboBoxList(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.comboBoxList(request));
    }

    /**
     * 品牌控货类型说明
     * @param request
     * @return
     */
    @RequestMapping(value = "/brand_explain", method = RequestMethod.POST)
    @ResponseBody
    public Packet brandExplain(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.brandExplain(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/brand_explain_info 获取品牌控货说明信息
     * @apiGroup area_product_control
     * @apiName area_product_control_brand_explain_info
     * @apiDescription 获取品牌控货说明信息
     * @apiParam {number} type_id 				类型ID
     * @apiParam {string} type_name 			类型名称
     * @apiParam {string} explains_id 			控货说明ID
     * @apiParam {string} context 			    说明内容
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 品牌控货说明list
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/brand_explain_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryExplainInfo(HttpServletRequest request) {
        return Transform.GetResult(this.areaProductControlSetService.queryBrandExplainInfo(request));
    }

    /**
     * @api {post} /{project_name}/area_product_control/brand_explain_edit 编辑品牌控货说明
     * @apiGroup area_product_control
     * @apiName area_product_control_brand_explain_edit
     * @apiDescription 编辑品牌控货说明
     * @apiParam [object] setData 				设置品牌说明数据
     * @apiParam {string} setData[type_id] 	    类型名称
     * @apiParam {string} setData[context] 	    说明内容
     * @apiParam {string} brand_id 			    当前操作品牌id
     *
     * @apiVersion 0.0.1
     * @apiSuccess {object} obj                 更新控货类型列表信息
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     */
    @RequestMapping(value = "/brand_explain_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editExplainInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.areaProductControlSetService.editBrandExplainInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    /**
     * @api {post} /{project_name}/area_product_control/judge_voidable 判断业业务经理的某个城市是否可用取消控货
     * @apiGroup area_product_control
     * @apiName area_product_control_judge_voidable
     * @apiDescription  判断业业务经理的某个城市是否可用取消控货，如果业务经理下属的门店或者业务员未控货该城市，则返回0 ，已使用，则返回大于0的数字
     *        
     * @apiParam {number} user_id 			业务经理ID
     * @apiParam {number} city_id 			需要判断的城市ID
     *
     * @apiVersion 0.0.1
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message             接口返回信息
     * @apiSuccess {number} obj                 是否被使用   0：未使用   大于0，已使用
     */
    @RequestMapping(value = "/judge_voidable", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryIsUserByCistIdAndManager(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = this.areaProductControlSetService.queryIsUserByCistIdAndManager(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    	}
    	return Transform.GetResult(pr);
    }
}
