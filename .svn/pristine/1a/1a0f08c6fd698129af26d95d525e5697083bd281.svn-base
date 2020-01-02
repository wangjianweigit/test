package com.tk.oms.product.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.product.service.ProductGroupService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("product_group")
public class ProductGroupControl {
	@Resource
	private ProductGroupService productGroupService;
	
	/**
    *
    * @api {post} /{project_name}/product_group/list
    * @apiName list
    * @apiGroup product_group
    * @apiDescription  查询商品分组列表
    * @apiVersion 0.0.1
    * 
	* @apiParam {string} group_name    分组名称
	* @apiParam {number} parent_id     父分组ID
	* @apiParam {char} state           分组状态（1，不启用，2，启用）
	
    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息.
    * @apiSuccess {object[]} obj 数据集合.
    * @apiSuccess {number} obj.id                   分组ID
    * @apiSuccess {number} obj.parent_id            父分组ID
    * @apiSuccess {number} obj.create_user_id       创建人ID
    * @apiSuccess {date} obj.create_date            创建时间,格式如下:yyyy-MM-dd HH:mm:ss
    * @apiSuccess {string} obj.group_name           分组名称
    * @apiSuccess {char} obj.state                  分组状态（1，不启用，2，启用）
    * @apiSuccess {number} obj.sortid               分组排序字段（数字越大越靠前）
    * @apiSuccess {string} obj.img_url			          分组图标
    * @apiSuccess {string} obj.remark				分组描述
    * @apiSuccess {string} obj.redirect_url			分组链接，点击分组关键字后的跳转路径
    *
    */
   @RequestMapping(value = "/list", method = RequestMethod.POST)
   @ResponseBody
   public Packet queryProductGroupList(HttpServletRequest request) {
       return Transform.GetResult(productGroupService.queryProductGroupList(request));
   }
   /**
    *
    * @api {post} /{project_name}/product_group/add 
    * @apiName add
    * @apiGroup product_group
    * @apiDescription  添加商品分组
    * @apiVersion 0.0.1

    * @apiParam {number} [parent_id=0]        分组父单位ID
    * @apiParam {string} group_name           分组名称
    * @apiParam {string} img_url              分组图标志路径url
    * @apiParam {number} parent_id            父分组ID
    * @apiParam {number} create_user_id       创建人ID
    * @apiParam {date} create_date            创建时间,格式如下:yyyy-MM-dd HH:mm:ss
    * @apiParam {string} group_name           分组名称
    * @apiParam {char} state                  分组状态（1，不启用，2，启用）
    * @apiParam {number} sortid               分组排序字段（数字越大越靠前）
    * @apiParam {string} img_url			     分组图标
    * @apiParam {string} remark					分组描述
    * @apiParam {string} redirect_url			分组链接，点击分组关键字后的跳转路径


    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息.
    * @apiSuccess {object[]} 
    */
   @RequestMapping(value = "/add", method = RequestMethod.POST)
   @ResponseBody
   public Packet addProductGroup(HttpServletRequest request) {
       return Transform.GetResult(productGroupService.addProductGroup(request));
   }
   /**
    *
    * @api {post} /{project_name}/product_group/edit 
    * @apiName edit
    * @apiGroup product_group
    * @apiDescription  编辑商品分组
    * @apiVersion 0.0.1

    * @apiParam {number} id                   分组ID
    * @apiParam {string} group_name           分组名称
    * @apiParam {string} img_url              分组图标志路径url
    * @apiParam {char} state                  分组状态（1，不启用，2，启用）
    * @apiParam {number} sortid               分组排序字段（数字越大越靠前）
    * @apiParam {string} img_url			     分组图标
    * @apiParam {string} remark					分组描述
    * @apiParam {string} redirect_url			分组链接，点击分组关键字后的跳转路径


    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息.
    * @apiSuccess {object[]} 
    */
   @RequestMapping(value = "/edit", method = RequestMethod.POST)
   @ResponseBody
   public Packet editProductGroup(HttpServletRequest request) {
       return Transform.GetResult(productGroupService.editProductGroup(request));
   }
   /**
    *
    * @api {post} /{project_name}/product_group/remove
    * @apiName remove
    * @apiGroup product_group
    * @apiDescription  删除商品分组
    * @apiVersion 0.0.1

    * @apiParam {number} id          分组ID

    * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息.
    * @apiSuccess {number} obj 删除的数量
    */
   @RequestMapping(value = "/remove", method = RequestMethod.POST)
   @ResponseBody
   public Packet removeProductGroup(HttpServletRequest request) {
       return Transform.GetResult(productGroupService.removeProductGroup(request));
   }
   /**
    *
    * @api {post} /{project_name}/product_group/sort
    * @apiName sort
    * @apiGroup product_group
    * @apiDescription  交换两个分组记录的排序字段
    * @apiVersion 0.0.1

    * @apiParam {number} id1                   第一个分组ID
    * @apiParam {number} id2                   第二个分组ID


    * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
    * @apiSuccess {string} message 接口返回信息.
    * @apiSuccess {number} obj     无意义
    */
   @RequestMapping(value = "/sort", method = RequestMethod.POST)
   @ResponseBody
   public Packet productGroupSort(HttpServletRequest request) {
       return Transform.GetResult(productGroupService.productGroupSort(request));
   }
   /**
   *
   * @api {post} /{project_name}/product_group/state
   * @apiName state
   * @apiGroup product_group
   * @apiDescription  是否启用
   * @apiVersion 0.0.1

   * @apiParam {number} id				分组ID
   * @apiParam {char} state             分组状态（1，不启用，2，启用）


   * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
   * @apiSuccess {string} message 接口返回信息.
   * @apiSuccess {number} obj     
   */
   @RequestMapping(value = "/state", method = RequestMethod.POST)
   @ResponseBody
   public Packet editProductGroupByState(HttpServletRequest request) {
       return Transform.GetResult(productGroupService.editProductGroupByState(request));
   }
   
   /**
   *
   * @api {post} /{project_name}/product_group/all 
   * @apiName all
   * @apiGroup product_group
   * @apiDescription  查询商品分组层级信息
   * @apiVersion 0.0.1

   * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功  false:失败
   * @apiSuccess {string} message 接口返回信息.
   * @apiSuccess {object[]} obj 数据集合.
   * @apiSuccess {number} obj.id                   分组ID
   * @apiSuccess {number} obj.parent_id            父分组ID
   * @apiSuccess {number} obj.create_user_id       创建人ID
   * @apiSuccess {date} obj.create_date            创建时间,格式如下:yyyy-MM-dd HH:mm:ss
   * @apiSuccess {string} obj.group_name           分组名称
   * @apiSuccess {char} obj.state                  分组状态（1，不启用，2，启用）
   * @apiSuccess {number} obj.sortid               分组排序字段（数字越大越靠前）
   * @apiSuccess {string} obj.img_url			          分组图标
   * @apiSuccess {string} obj.remark				分组描述
   * @apiSuccess {string} obj.redirect_url			分组链接，点击分组关键字后的跳转路径
   * @apiSuccess {number} obj.level					分组层级
   *
   */
  @RequestMapping(value = "/all", method = RequestMethod.POST)
  @ResponseBody
  public Packet queryProductGroupAll(HttpServletRequest request) {
      return Transform.GetResult(productGroupService.queryProductGroupAll(request));
  }
  /**
   * @api {post} /{project_name}/product_group/batch_edit
   * @apiName batch_edit
   * @apiGroup product_group
   * @apiDescription  批量更新商品分组信息
   * @apiVersion 0.0.1
   * 
   * @apiParam {string} group_name 分类名称
   * @apiParam {number} id 分类id
   * 
   * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
   * @apiSuccess {string}     message 接口返回信息.
   * @apiSuccess {object[]}   obj 
   */
	@RequestMapping(value = "/batch_edit", method = RequestMethod.POST)
  @ResponseBody
  public Packet batchEditProductGroup(HttpServletRequest request) {
      return Transform.GetResult(productGroupService.batchEditProductGroup(request));
  }
	
 /**
   * @api {post} /{project_name}/product_group/update_state
   * @apiName update_state
   * @apiGroup product_group
   * @apiDescription  更新是否展开显示
   * @apiVersion 0.0.1
   * 
   * @apiParam {string} is_display 是否展开  1 不展开 2展开
   * @apiParam {number} id 分类id
   * 
   * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
   * @apiSuccess {string}     message 接口返回信息.
   */
	@RequestMapping(value = "/update_state", method = RequestMethod.POST)
  @ResponseBody
  public Packet updateIsDisplay(HttpServletRequest request) {
      return Transform.GetResult(productGroupService.updateIsDisplay(request));
  }
}
