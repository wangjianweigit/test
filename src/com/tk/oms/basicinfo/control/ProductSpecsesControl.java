package com.tk.oms.basicinfo.control;

import com.tk.oms.basicinfo.service.ProductSpecsesService;
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
 * 
 * Copyright (c), 2017, Tongku
 * FileName : ProductSpecsesControl
 * 商品规格Control
 * 
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/18 10:12
 */
@Controller
@RequestMapping("/product_spec")
public class ProductSpecsesControl {

    @Resource
    private ProductSpecsesService productSpecsesService;

    /**
     *
     * @api {post} /{project_name}/product_spec/list 查询商品规格列表接口
     * @apiGroup product_spec
     * @apiName product_spec_list
     * @apiDescription  分页查询商品规格列表接口
     * @apiVersion 0.0.1
     * @apiParam {string} group_name 规格分组名称 选填
     * @apiParam {string} group_code 规格分组代码 选填
     * @apiSuccess {object} obj 查询商品规格数据信息
     * @apiSuccess {number} obj.GROUP_NAME 规格分组名称
     * @apiSuccess {string} obj.GROUP_CODE 规格分组代码
     * @apiSuccess {number[]} obj.ID 商品规格编号
     * @apiSuccess {string[]} obj.PRODUCT_SPECS 商品规格
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(productSpecsesService.queryGroupList(request));
    }
    /**
     *
     * @api {post} /{project_name}/product_spec/spec_list 查询一个分组下的规格列表
     * @apiGroup product_spec
     * @apiName product_spec_list
     * @apiDescription  分页查询商品规格列表接口
     * @apiVersion 0.0.1
     * @apiParam {number} parent_id 规格分组ID
     * 
     * @apiSuccess {object} obj 查询商品规格数据信息
     * @apiSuccess {number} obj.GROUP_NAME 规格分组名称
     * @apiSuccess {object[]} obj.ID 				规格记录ID
     * @apiSuccess {object[]} obj.PRODUCT_SPECS 	规格名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/detai_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySpecList(HttpServletRequest request) {
    	return Transform.GetResult(productSpecsesService.querySpecList(request));
    }

    /**
     *
     * @api {post} /{project_name}/product_spec/search_update 查询商品规格信息接口
     * @apiGroup product_spec
     * @apiName product_spec_search_update
     * @apiDescription  查询商品规格信息接口
     * @apiVersion 0.0.1
     * @apiParam {string} group_name 规格分组名称 必填
     * @apiParam {string} group_code 规格分组代码 必填
     * @apiSuccess {object} obj 查询商品规格数据信息
     * @apiSuccess {number} obj.GROUP_NAME 规格分组名称
     * @apiSuccess {string} obj.GROUP_CODE 规格分组代码
     * @apiSuccess {number[]} obj.ID 商品规格编号
     * @apiSuccess {string[]} obj.PRODUCT_SPECS 商品规格
     * @apiSuccess {number[]} obj.CREATE_USER_ID 创建用户id
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @param request
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductSpecsesInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = productSpecsesService.queryProductSpecsesInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }


    /**
     *
     * @api {post} /{project_name}/product_spec/group_add 新增商品规格分组接口
     * @apiGroup product_spec
     * @apiName product_spec_group_add
     * @apiDescription  新增商品规格分组接口
     * @apiVersion 0.0.1
     * @apiParam {string} product_specs 规格分组名称 必填
     * @apiSuccess {number} obj.CREATE_USER_ID 创建人ID
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/group_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addGroup(HttpServletRequest request) {
        return Transform.GetResult(productSpecsesService.addProductSpecGroup(request));
    }

    /**
     *
     * @api {post} /{project_name}/product_spec/group_remove 删除商品规格分组接口
     * @apiGroup product_spec
     * @apiName product_spec_group_remove
     * @apiDescription  新增商品规格分组接口
     * @apiVersion 0.0.1
     * @apiParam {string} group_name 规格分组名称 必填
     * @apiSuccess {number} obj.CREATE_USER_ID 创建人ID
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/group_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeGroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = productSpecsesService.removeProductSpecGroup(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }


    /**
     *
     * @api {post} /{project_name}/product_spec/group_edit 编辑商品规格分组接口
     * @apiGroup product_spec
     * @apiName product_spec_group_edit
     * @apiDescription  新增商品规格分组接口
     * @apiVersion 0.0.1
     * @apiParam {string} product_specs 规格分组名称 必填
     * @apiSuccess {number} obj.CREATE_USER_ID 创建人ID
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/group_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editGroup(HttpServletRequest request) {
        return Transform.GetResult(productSpecsesService.editProductSpecGroup(request));
    }

    /**
     *
     * @api {post} /{project_name}/product_spec/spec_add 新增商品规格接口
     * @apiGroup product_spec
     * @apiName product_spec_spec_add
     * @apiDescription  新增商品规格接口
     * @apiVersion 0.0.1
     * @apiParam {number} group_name 规格分组名称 必填
     * @apiParam {string} product_speces 商品规格,同一分组中的规格不允许重 必填
     * @apiSuccess {object} obj 新增商品规格信息
     * @apiSuccess {number} obj.ID 商品规格id
     * @apiSuccess {string} obj.PRODUCT_SPECS 商品规格
     * @apiSuccess {date} obj.CREATE_DATE 创建日期
     * @apiSuccess {number} obj.CREATE_USER_ID 创建人ID
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/spec_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addSpec(HttpServletRequest request) {
        return Transform.GetResult(productSpecsesService.addSpec(request));
    }


    /**
     *
     * @api {post} /{project_name}/product_spec/update 更新商品规格接口
     * @apiGroup product_spec
     * @apiName product_spec_update
     * @apiDescription  更新商品规格接口
     * @apiVersion 0.0.1
     * @apiParam {string} group_name 规格分组名称 必填
     * @apiParam {string} group_code 规格分组代码 必填
     * @apiParam {string} ids 商品规格编号 多个逗号分隔 必填
     * @apiParam {string} product_speces 商品规格,同一分组中的规格不允许重 多个逗号分隔 必填
     * @apiParam {string} create_user_ids 创建用户id 多个逗号分隔 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Packet editProductionPlan(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = productSpecsesService.editProductSpecses(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     *
     * @api {post} /{project_name}/product_spec/delete 删除商品规格接口
     * @apiGroup product_spec
     * @apiName product_spec_delete
     * @apiDescription  删除商品规格单接口
     * @apiVersion 0.0.1
     * @apiParam {number} id 商品规格id 必填
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/spec_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeSpec(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = productSpecsesService.removeSpec(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    
    
    
    @RequestMapping(value = "/with_code_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeList(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeList(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }


	 @RequestMapping(value = "/with_code_add", method = RequestMethod.POST)
	    @ResponseBody
	    public Packet withCodeAdd(HttpServletRequest request) {
	    	ProcessResult pr = new ProcessResult();
			try {
				pr = productSpecsesService.withCodeAdd(request);
			} catch (Exception e) {
				pr.setState(false);
				pr.setMessage(e.getMessage());
				e.printStackTrace();
			}
			return Transform.GetResult(pr);
	    }
 
	 @RequestMapping(value = "/with_code_detail_add", method = RequestMethod.POST)
	 @ResponseBody
	 public Packet withCodeDetailAdd(HttpServletRequest request) {
	 	ProcessResult pr = new ProcessResult();
			try {
				pr = productSpecsesService.withCodeDetailAdd(request);
			} catch (Exception e) {
				pr.setState(false);
				pr.setMessage(e.getMessage());
				e.printStackTrace();
			}
			return Transform.GetResult(pr);
	 }
    
    @RequestMapping(value = "/with_code_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeEdit(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeEdit(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    @RequestMapping(value = "/with_code_remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeRemove(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeRemove(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    @RequestMapping(value = "/with_code_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeDetail(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    @RequestMapping(value = "/with_code_specs_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeSpecsDetail(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeSpecsDetail(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    
    @RequestMapping(value = "/with_code_specs", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeSpecs(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeSpecs(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    @RequestMapping(value = "/specs_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet specsOption(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.specsOption(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }
    /**
     * 配码模板下拉框
     * @param request
     * @return
     */
    @RequestMapping(value = "/with_code_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet withCodeOption(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
		try {
			pr = productSpecsesService.withCodeOption(request);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return Transform.GetResult(pr);
    }

    /**
     * 查询商品规格列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/specs_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductSpecsList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = productSpecsesService.queryProductSpecsList(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return Transform.GetResult(pr);
    }
    /**
    *
    * @api {post} /{project_name}/product_spec/shoes/list 查询所有鞋类规格信息
    * @apiGroup product_spec
    * @apiName product_spec_list
    * @apiDescription  询所有的鞋类商品规格信息，用于为商家进行可用的商品规格进行授权
	* 					 同时根据商家ID返回该规格是否已经勾选的标志
    * @apiVersion 0.0.1
    * @apiParam {number} parent_id 规格分组ID
    * 
    * @apiSuccess {object[]} obj 查询商品规格数据信息
    * @apiSuccess {long} obj.PARENT_ID			规格分组ID
    * @apiSuccess {long} obj.ID 				规格ID
    * @apiSuccess {string} obj.PRODUCT_SPECS 	规格名称
    * @apiSuccess {int} obj.SELECTED			是否自动勾选
    * 												<p>0：不勾选</p>
    * 												<p>1：勾选</p>
    */
   
    @RequestMapping(value = "/shoes/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet getAllShoesProductSpecs(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = productSpecsesService.getAllShoesProductSpecs(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
    /**
     * 配置 商家可以的使用的 商品规格信息 数据
     */
    @RequestMapping(value = "/sta_spec", method = RequestMethod.POST)
    @ResponseBody
    public Packet setStationedProductSpecs(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
    	try {
    		pr = productSpecsesService.setStationedProductSpecs(request);
    	} catch (Exception e) {
    		pr.setState(false);
    		pr.setMessage(e.getMessage());
    		e.printStackTrace();
    	}
    	return Transform.GetResult(pr);
    }
}
