package com.tk.oms.platform.control;

import com.tk.oms.platform.service.FixedModuleService;
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
 * Copyright (c), 2018, TongKu
 * FileName : FixedModuleControl
 * 固定模块接口访问层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/09/12
 */
@Controller
@RequestMapping("/fixed_module")
public class FixedModuleControl {

    @Resource
    private FixedModuleService fixedModuleService;

    /**
     * @api {post}/{project_name}/fixed_module/detail 固定模块详情
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 0.0.1
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回固定模块配置信息数据
     * @apiSuccess (banner图){object[]} obj.links 链接集合
     * @apiSuccess (banner图){string} obj.links.img_url 图片路径
     * @apiSuccess (banner图){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图){number} obj.switch_type 切换方式
     * @apiSuccess (banner图){number} obj.switch_interval 自动切换时间（单位秒）
     * @apiSuccess (banner图){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (banner小广告){object[]} obj.links 链接集合
     * @apiSuccess (banner小广告){string} obj.links.img_url 图片路径
     * @apiSuccess (banner小广告){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner小广告){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (每日上新-视频){number} obj.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (每日上新-视频){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (每日上新-视频){string} obj.sort 排序规则。自动推荐存储
     * 														<p>first_sell_sort_value:按照【商品首次上架排序值】降序（即按照运营总后的上新商品排序功能设置的顺序）</p>
     *														<p>create_date:按照【商品发布时间】降序</p>
     *														<p>sell_state_date:按照【商品最近一次上架时间】降序</p>
     * 														<p>first_sell_state_date:按照【商品首次上架时间】降序</p>
     * @apiSuccess (每日上新-视频){object[]} obj.products 商品货号集合，手动推荐存储
     * @apiSuccess (每日上新-视频){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (每日上新-视频){number} obj.is_auto_play 是否自动播放
     * 														<p>1:自动播放</p>
     * 														<p>2:不自动播放</p>
     * @apiSuccess (每日上新-视频){number} obj.is_continued_play 是否连续播放
     * 														<p>1:连续播放</p>
     * 														<p>2:不连续播放</p>
     * @apiSuccess (每日上新-视频){number} obj.count 需要展示商品数量
     *
     * @apiSuccess (每日上新-商品){string} obj.sort 排序规则。自动推荐
     * 														<p>first_sell_sort_value:按照【商品首次上架排序值】降序（即按照运营总后的上新商品排序功能设置的顺序）</p>
	 *														<p>create_date:按照【商品发布时间】降序</p>
	 *														<p>sell_state_date:按照【商品最近一次上架时间】降序</p>
     * 														<p>first_sell_state_date:按照【商品首次上架时间】降序</p>
     * @apiSuccess (每日上新-商品){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (每日上新-商品){number} obj.count 需要展示商品数量
     * 
     * @apiSuccess (品牌街){number} obj.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (品牌街){string} obj.product_type_ids 商品分类ID集合，如果存在多个商品分类则使用英文逗号分隔
     * @apiSuccess (品牌街){object[]} obj.brand_ids 品牌ID集合，以数组形式存储
     * @apiSuccess (品牌街){string} obj.sort 排序规则
     * 										 <p>create_date asc:创建时间顺序</p>
     * 										 <p>create_date desc :创建时间倒序</p>
     * @apiSuccess (品牌街){number} obj.show_type 展示方式
     * 										 <p>1：顺序展示</p>
     * 										 <p>2：随机展示</p>
     * @apiSuccess (品牌街){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (品牌街){number} obj.switch_interval 自动切换时间（单位秒）
     *
     * @apiSuccess (热卖TOP){number} obj.select_type 推荐方式 <p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiSuccess (热卖TOP){string} obj.sort 排序规则
     *                                               <p>product_count：按照总销量降序</p>
     *                                               <p>product_count7：按照最近7天销量降序</p>
     * 												 <p>product_count15：按照最近15天销量降序</p>
     * 												 <p>product_count30：按照最近30天销量降序</p>
     * 												 <p>product_count90：按照最近90天销量降序</p>
     * @apiSuccess (热卖TOP){object[]} obj.products 商品货号集合，以数组形式存储
     * @apiSuccess (热卖TOP){string} obj.title 标题文字
     * @apiSuccess (热卖TOP){string} obj.link 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (热卖TOP){number} obj.is_discount 是否显示折扣： <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (热卖TOP){number} obj.is_price 是否显示价格 <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (热卖TOP){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (热卖TOP){number} obj.count 查询商品数量
     *
     * @apiSuccess (特惠抢购){number} obj.select_type 推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (特惠抢购){number} obj.recommend_type 宝贝类型 p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (特惠抢购){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (特惠抢购){number} obj.activity_id 活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (特惠抢购){string} obj.sort 排序规则。自动推荐存储 <p>overall：汇总排序(上新+库存)降序</p>
     * @apiSuccess (特惠抢购){string} obj.products 商品货号集合，数组形式存储
     * @apiSuccess (特惠抢购){string} obj.title 标题文字
     * @apiSuccess (特惠抢购){string} obj.link 链接链接，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (特惠抢购){number} obj.is_discount 是否显示折扣 <p>1：显示</p><p>2：不显示</p>
     * @apiSuccess (特惠抢购){number} obj.is_price 是否显示价格 <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (特惠抢购){number} obj.count 查询商品数量
     *
     * @apiSuccess (商品分类){string} obj.title 标题文字
     * @apiSuccess (商品分类){string} obj.color 模块主色（颜色代码）
     * @apiSuccess (商品分类){object[]} obj.links 链接集合
     * @apiSuccess (商品分类){string} obj.links.link_name 链接名称
     * @apiSuccess (商品分类){string} obj.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类){number} obj.count 展示的链接数量
     * @apiSuccess (商品分类){object[]} obj.left_links 左侧链接集合
     * @apiSuccess (商品分类){string} obj.left_links.img_url 左侧图片路径
     * @apiSuccess (商品分类){string} obj.left_links.link_url 左侧链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类){string} obj.left_count 左侧展示的链接数量
     * @apiSuccess (商品分类){number} obj.right_select_type 右侧推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类){number} obj.right_recommend_type 右侧宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类){number} obj.right_product_type_id 右侧商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类){number} obj.right_activity_id 右侧活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类){string} obj.right_sort 右侧排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类){object[]} obj.right_products 右侧商品信息
     * @apiSuccess (商品分类){string} obj.right_products.title 标题
     * @apiSuccess (商品分类){string} obj.right_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){object[]} obj.right_count 右侧展示的商品数量
     * @apiSuccess (商品分类){object[]} obj.bottom_products 底部商品信息
     * @apiSuccess (商品分类){string} obj.bottom_products.title 标题
     * @apiSuccess (商品分类){string} obj.bottom_products.selling 卖点
     * @apiSuccess (商品分类){string} obj.bottom_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){number} obj.bottom_count 底部展示的商品数量

     *
     * @apiSuccess (预售抢先){number} obj.select_type 推荐方式 <p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiSuccess (预售抢先){number} obj.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (预售抢先){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (预售抢先){number} obj.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (预售抢先){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (预售抢先){string} obj.products 商品货号集合，数组形式存储
     * @apiSuccess (预售抢先){number} obj.main_title 标题大字
     * @apiSuccess (预售抢先){number} obj.sub_title 标题小字
     * @apiSuccess (预售抢先){number} obj.goods_time 到货时间：1显示或者2不显示
     * @apiSuccess (预售抢先){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (预售抢先){number} obj.count 展示的商品数量
     * @apiSuccess (实力定制){number} obj.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (实力定制){number} obj.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (实力定制){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (实力定制){number} obj.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (实力定制){string} obj.sort 排序规则
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量生序</p>
     * @apiSuccess (实力定制){object[]} obj.products 商品货号集合
     * @apiSuccess (实力定制){number} obj.main_title 标题大字
     * @apiSuccess (实力定制){number} obj.sub_title 标题小字
     * @apiSuccess (实力定制){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (实力定制){number} obj.count 展示的商品数量
     *
     * @apiSuccess (猜你喜欢){number} obj.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (猜你喜欢){number} obj.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (猜你喜欢){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (猜你喜欢){number} obj.activity_id 		活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (猜你喜欢){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (猜你喜欢){object[]} obj.products 商品货号集合
     * @apiSuccess (猜你喜欢){number} obj.title 标题文字
     * @apiSuccess (猜你喜欢){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (猜你喜欢){number} obj.count 展示的商品数量
     *
     * @apiSuccess (快捷菜单){object[]} obj.links 链接集合
     * @apiSuccess (快捷菜单){string} obj.links.title 链接标题
     * @apiSuccess (快捷菜单){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (快捷菜单){number} obj.count 样式布局，直接填写数量
     */

    /**
     * @api {post}/{project_name}/fixed_module/detail 固定模块详情
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 0.0.2
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>new_shoes：最新童鞋</p>
     *                                          <p>classify：商品分类</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回固定模块配置信息数据
     * @apiSuccess (banner图-PC){object[]} obj.links 链接集合
     * @apiSuccess (banner图-PC){string} obj.links.img_url 图片路径
     * @apiSuccess (banner图-PC){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图-PC){number} obj.switch_type 切换方式
     * @apiSuccess (banner图-PC){number} obj.switch_interval 自动切换时间（单位秒）
     * @apiSuccess (banner图-PC){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (banner小广告-PC){object[]} obj.links 链接集合
     * @apiSuccess (banner小广告-PC){string} obj.links.img_url 图片路径
     * @apiSuccess (banner小广告-PC){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner小广告-PC){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (最新童鞋-PC){string} obj.fixed_period 固化时间
     * @apiSuccess (最新童鞋-PC){number} obj.link 更多链接
     * @apiSuccess (最新童鞋-PC){number} obj.title 标题
     * @apiSuccess (最新童鞋-PC){number} obj.count 链接展示数量
     * @apiSuccess (最新童鞋-PC){object[]} obj.links 链接集合
     * @apiSuccess (最新童鞋-PC){string} obj.links.link_name 链接名称
     * @apiSuccess (最新童鞋-PC){string} obj.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋-PC){number} obj.left_count 左侧链接展示数量
     * @apiSuccess (最新童鞋-PC){object[]} obj.left_links 左侧链接集合
     * @apiSuccess (最新童鞋-PC){string} obj.left_links.img_url 左侧图片路径
     * @apiSuccess (最新童鞋-PC){string} obj.left_links.link_url 左侧链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋-PC){number} obj.middle_select_type 中间推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (最新童鞋-PC){number} obj.middle_recommend_type 中间宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (最新童鞋-PC){number} obj.middle_product_type_id 中间商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋-PC){number} obj.middle_activity_id 中间活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋-PC){string} obj.middle_sort 中间排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (最新童鞋-PC){object[]} obj.middle_products 中间商品信息
     * @apiSuccess (最新童鞋-PC){string} obj.middle_products.title 标题
     * @apiSuccess (最新童鞋-PC){string} obj.middle_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋-PC){number} obj.middle_count 中间展示的商品数量
     *
     * @apiSuccess (最新童鞋-PC){number} obj.right_select_type 右侧推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (最新童鞋-PC){number} obj.right_recommend_type 右侧宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (最新童鞋-PC){number} obj.right_product_type_id 右侧商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋-PC){number} obj.right_activity_id 右侧活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋-PC){string} obj.right_sort 右侧排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (最新童鞋-PC){object[]} obj.right_products 右侧商品信息
     * @apiSuccess (最新童鞋-PC){string} obj.right_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋-PC){number} obj.right_count 右侧展示的商品数量
     *
     * @apiSuccess (商品分类-PC){string} obj.title 标题文字
     * @apiSuccess (商品分类-PC){string} obj.color 模块主色（颜色代码）
     * @apiSuccess (商品分类-PC){object[]} obj.links 链接集合
     * @apiSuccess (商品分类-PC){string} obj.links.link_name 链接名称
     * @apiSuccess (商品分类-PC){string} obj.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类-PC){number} obj.left_select_type 右侧推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类-PC){number} obj.left_recommend_type 右侧宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类-PC){number} obj.left_product_type_id 右侧商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-PC){number} obj.left_activity_id 右侧活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-PC){string} obj.left_sort 右侧排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类-PC){object[]} obj.left_products 右侧商品信息
     * @apiSuccess (商品分类-PC){number} obj.left_count 右侧展示的商品数量
     * @apiSuccess (商品分类-PC){number} obj.right_select_type 右侧推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类-PC){number} obj.right_recommend_type 右侧宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类-PC){number} obj.right_product_type_id 右侧商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-PC){number} obj.right_activity_id 右侧活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-PC){string} obj.right_sort 右侧排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类-PC){object[]} obj.right_products 右侧商品信息
     * @apiSuccess (商品分类-PC){number} obj.right_count 右侧展示的商品数量
     *
     *
     * @apiSuccess (banner图-微信){object[]} obj.links 链接集合
     * @apiSuccess (banner图-微信){string} obj.links.img_url 图片路径
     * @apiSuccess (banner图-微信){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图-微信){number} obj.switch_type 切换方式
     * @apiSuccess (banner图-微信){number} obj.switch_interval 自动切换时间（单位秒）
     * @apiSuccess (banner图-微信){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (最新童鞋-微信){string} obj.fixed_period 固化时间
     * @apiSuccess (最新童鞋-微信){number} obj.link 更多链接
     * @apiSuccess (最新童鞋-微信){number} obj.title 标题
     * @apiSuccess (最新童鞋-微信){number} obj.count 链接展示数量
     * @apiSuccess (最新童鞋-微信){object[]} obj.links 链接集合
     * @apiSuccess (最新童鞋-微信){string} obj.links.link_name 链接名称
     * @apiSuccess (最新童鞋-微信){string} obj.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋-微信){number} obj.select_type 中间推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (最新童鞋-微信){number} obj.recommend_type 中间宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (最新童鞋-微信){number} obj.product_type_id 中间商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋-微信){number} obj.activity_id 中间活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋-微信){string} obj.sort 中间排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (最新童鞋-微信){object[]} obj.products 中间商品信息
     * @apiSuccess (最新童鞋-微信){string} obj.products.title 标题
     * @apiSuccess (最新童鞋-微信){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋-微信){number} obj.products_count 展示的商品数量
     *
     * @apiSuccess (礼品配件-微信){string} obj.fixed_period 固化时间
     * @apiSuccess (礼品配件-微信){number} obj.title 标题
     * @apiSuccess (礼品配件-微信){number} obj.link 更多链接
     * @apiSuccess (礼品配件-微信){number} obj.select_type 中间推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (礼品配件-微信){number} obj.recommend_type 中间宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (礼品配件-微信){number} obj.product_type_id 中间商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (礼品配件-微信){number} obj.activity_id 中间活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (礼品配件-微信){string} obj.sort 中间排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (礼品配件-微信){object[]} obj.products 中间商品信息
     * @apiSuccess (礼品配件-微信){string} obj.products.title 标题
     * @apiSuccess (礼品配件-微信){string} obj.products.selling 卖点
     * @apiSuccess (礼品配件-微信){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (礼品配件-微信){number} obj.count 展示的商品数量
     *
     * @apiSuccess (商品分类-微信){string} obj.background 背景图片
     * @apiSuccess (商品分类-微信){string} obj.title 大标题
     * @apiSuccess (商品分类-微信){string} obj.sub_title 小标题
     * @apiSuccess (商品分类-微信){string} obj.link 更多链接
     * @apiSuccess (商品分类-微信){string} obj.fixed_period 固化时间
     * @apiSuccess (商品分类-微信){number} obj.select_type 推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类-微信){number} obj.recommend_type 宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类-微信){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-微信){number} obj.activity_id 活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-微信){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类-微信){object[]} obj.products 商品信息
     * @apiSuccess (商品分类-微信){number} obj.count 展示的商品数量
     */

    /**
     * @api {post}/{project_name}/fixed_module/detail 固定模块详情
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 0.0.3
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>activity：活动模块</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回固定模块配置信息数据
     *
     * @apiSuccess (banner图-PC){string} obj.img_url 图片路径
     *
     * @apiSuccess (活动模块-PC){string} obj.activities 活动模块集合
     * @apiSuccess (活动模块-PC){number} obj.activities.title 标题
     * @apiSuccess (活动模块-PC){number} obj.activities.activity_id 活动ID
     * @apiSuccess (活动模块-PC){number} obj.activities.sort 排序值
     *                                                           <p>overall:综合</p>
     *                                                           <p>sell_state_date:上架时间</p>
     *                                                           <p>product_count:销量</p>
     *                                                           <p>sort_id:按活动手工调整排名</p>
     *
     * @apiSuccess (活动模块-微信){string} obj.activities 活动模块集合
     * @apiSuccess (活动模块-微信){number} obj.activities.title 标题
     * @apiSuccess (活动模块-微信){number} obj.activities.activity_id 活动ID
     * @apiSuccess (活动模块-微信){number} obj.activities.sort 排序值
     *                                                           <p>overall:综合</p>
     *                                                           <p>sell_state_date:上架时间</p>
     *                                                           <p>product_count:销量</p>
     *                                                           <p>sort_id:按活动手工调整排名</p>
     */

    /**
     * @api {post}/{project_name}/fixed_module/detail 固定模块详情
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 0.0.4
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>activity：活动模块</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回固定模块配置信息数据
     *
     * @apiSuccess (banner图-PC){string} obj.img_url 图片路径
     *
     * @apiSuccess (活动模块-PC){string} obj.activities 活动模块集合
     * @apiSuccess (活动模块-PC){number} obj.activities.title 标题
     * @apiSuccess (活动模块-PC){number} obj.activities.activity_id 活动ID
     * @apiSuccess (活动模块-PC){number} obj.activities.sort 排序值
     *                                                           <p>overall:综合</p>
     *                                                           <p>sell_state_date:上架时间</p>
     *                                                           <p>product_count:销量</p>
     *                                                           <p>sort_id:按活动手工调整排名</p>
     *
     * @apiSuccess (活动模块-微信){string} obj.activities 活动模块集合
     * @apiSuccess (活动模块-微信){number} obj.activities.title 标题
     * @apiSuccess (活动模块-微信){number} obj.activities.activity_id 活动ID
     * @apiSuccess (活动模块-微信){number} obj.activities.sort 排序值
     *                                                           <p>overall:综合</p>
     *                                                           <p>sell_state_date:上架时间</p>
     *                                                           <p>product_count:销量</p>
     *                                                           <p>sort_id:按活动手工调整排名</p>
     */
    /**
     * @api {post}/{project_name}/fixed_module/detail 固定模块详情
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 0.0.5
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>presell：预售</p>
     *                                          <p>past_presell：往期预售</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回固定模块配置信息数据
     *
     * @apiSuccess (banner图-PC){string} obj.img_url 图片路径
     *
     * @apiSuccess (预售抢先-PC){number} obj.select_type 推荐方式 <p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiSuccess (预售抢先-PC){number} obj.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (预售抢先-PC){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (预售抢先-PC){number} obj.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (预售抢先-PC){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (预售抢先-PC){string} obj.products 商品货号集合，数组形式存储
     * @apiSuccess (预售抢先-PC){number} obj.is_discount 商品折扣 1.显示 2.不显示
     * @apiSuccess (预售抢先-PC){number} obj.goods_time 到货时间：1显示或者2不显示
     * @apiSuccess (预售抢先-PC){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (预售抢先-PC){number} obj.count 展示的商品数量
     *
     * @apiSuccess (往期预售-PC){number} obj.period_num 往期期数
     * @apiSuccess (往期预售-PC){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     *
     * @apiSuccess (预售抢先-微信){number} obj.select_type 推荐方式 <p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiSuccess (预售抢先-微信){number} obj.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (预售抢先-微信){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (预售抢先-微信){number} obj.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (预售抢先-微信){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (预售抢先-微信){string} obj.products 商品货号集合，数组形式存储
     * @apiSuccess (预售抢先-微信){number} obj.count 展示的商品数量
     * @apiSuccess (预售抢先-微信){number} obj.sub_title 标题小字
     * @apiSuccess (预售抢先-微信){number} obj.goods_time 到货时间：1显示或者2不显示
     * @apiSuccess (预售抢先-微信){number} obj.fixed_period 数据固化周期（单位小时）
     *
     * @apiSuccess (往期预售-微信){number} obj.period_num 往期期数
     * @apiSuccess (往期预售-微信){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     */

    /**
     * @api {post}/{project_name}/fixed_module/detail 固定模块详情
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 0.0.6
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：小广告图</p>
     *                                          <p>group：商品分组</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>custom_hot：自定义热区</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回固定模块配置信息数据
     *
     * @apiSuccess (banner图-PC){object[]} obj.links 链接集合
     * @apiSuccess (banner图-PC){string} obj.links.img_url 图片路径
     * @apiSuccess (banner图-PC){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图-PC){number} obj.switch_type 切换方式
     * @apiSuccess (banner图-PC){number} obj.switch_interval 自动切换时间（单位秒）
     * @apiSuccess (banner图-PC){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (banner小广告-PC){object[]} obj.links 链接集合
     * @apiSuccess (banner小广告-PC){string} obj.links.img_url 图片路径
     * @apiSuccess (banner小广告-PC){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner小广告-PC){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (商品分组-PC){object[]} obj.links 链接集合
     * @apiSuccess (商品分组-PC){string} obj.links.group_name 分组名称
     * @apiSuccess (商品分组-PC){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分组-PC){string} obj.links.child_links 子级链接集合
     * @apiSuccess (商品分组-PC){string} obj.links.child_links.group_name 分组名称
     * @apiSuccess (商品分组-PC){string} obj.links.child_links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分组-PC){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (商品分类-PC){string} obj.background 背景图片
     * @apiSuccess (商品分类-PC){string} obj.title 大标题
     * @apiSuccess (商品分类-PC){string} obj.sub_title 小标题
     * @apiSuccess (商品分类-PC){string} obj.color 颜色
     * @apiSuccess (商品分类-PC){string} obj.fixed_period 固化时间
     * @apiSuccess (商品分类-PC){number} obj.select_type 推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类-PC){number} obj.recommend_type 宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类-PC){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-PC){number} obj.activity_id 活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-PC){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类-PC){object[]} obj.products 商品信息
     * @apiSuccess (商品分类-PC){number} obj.count 展示的商品数量
     *
     * @apiSuccess (banner图-微信){object[]} obj.links 链接集合
     * @apiSuccess (banner图-微信){string} obj.links.img_url 图片路径
     * @apiSuccess (banner图-微信){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图-微信){number} obj.switch_type 切换方式
     * @apiSuccess (banner图-微信){number} obj.switch_interval 自动切换时间（单位秒）
     * @apiSuccess (banner图-微信){number} obj.count 需要展示链接数量
     *
     * @apiSuccess (快捷菜单-微信){object[]} obj.links 链接集合
     * @apiSuccess (快捷菜单-微信){string} obj.links.title 链接标题
     * @apiSuccess (快捷菜单-微信){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (快捷菜单-微信){number} obj.count 样式布局，直接填写数量
     *
     * @apiSuccess (商品分类-微信){string} obj.background 背景图片
     * @apiSuccess (商品分类-微信){string} obj.title 大标题
     * @apiSuccess (商品分类-微信){string} obj.sub_title 小标题
     * @apiSuccess (商品分类-微信){string} obj.color 颜色
     * @apiSuccess (商品分类-微信){string} obj.links 更多链接
     * @apiSuccess (商品分类-微信){string} obj.style 样式
     * @apiSuccess (商品分类-微信){string} obj.fixed_period 固化时间
     * @apiSuccess (商品分类-微信){number} obj.select_type 推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类-微信){number} obj.recommend_type 宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类-微信){number} obj.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-微信){number} obj.activity_id 活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类-微信){string} obj.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类-微信){object[]} obj.products 商品信息
     * @apiSuccess (商品分类-微信){number} obj.count 展示的商品数量
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFixedModuleDetail(HttpServletRequest request) {
        return Transform.GetResult(this.fixedModuleService.queryFixedModuleDetail(request));
    }

    /**
     * @api {post}/{project_name}/fixed_module/edit 固定模块配置
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiParam {number} [group_sort=0] 固定模块分组排序值，【商品分类】模块可能存在多个分组
     * @apiParam {string} module_base_conf 固定模块配置json
     * @apiSuccess (banner图){object[]} module_base_conf.links 链接集合
     * @apiSuccess (banner图){string} module_base_conf.links.img_url 图片路径
     * @apiSuccess (banner图){string} module_base_conf.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图){number} module_base_conf.switch_type 切换方式
     * @apiSuccess (banner图){number} module_base_conf.switch_interval 自动切换时间（单位秒）
     * @apiSuccess (banner图){number} module_base_conf.count 需要展示链接数量
     *
     * @apiSuccess (banner小广告){object[]} module_base_conf.links 链接集合
     * @apiSuccess (banner小广告){string} module_base_conf.links.img_url 图片路径
     * @apiSuccess (banner小广告){string} module_base_conf.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner小广告){number} module_base_conf.count 需要展示链接数量
     *
     * @apiSuccess (每日上新-视频){number} module_base_conf.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (每日上新-视频){number} module_base_conf.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (每日上新-视频){string} module_base_conf.sort 排序规则。自动推荐存储
     * 														<p>first_sell_sort_value:按照【商品首次上架排序值】降序（即按照运营总后的上新商品排序功能设置的顺序）</p>
     *														<p>create_date:按照【商品发布时间】降序</p>
     *														<p>sell_state_date:按照【商品最近一次上架时间】降序</p>
     * 														<p>first_sell_state_date:按照【商品首次上架时间】降序</p>
     * @apiSuccess (每日上新-视频){object[]} module_base_conf.products 商品货号集合，手动推荐存储
     * @apiSuccess (每日上新-视频){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (每日上新-视频){number} module_base_conf.is_auto_play 是否自动播放
     * 														<p>1:自动播放</p>
     * 														<p>2:不自动播放</p>
     * @apiSuccess (每日上新-视频){number} module_base_conf.is_continued_play 是否连续播放
     * 														<p>1:连续播放</p>
     * 														<p>2:不连续播放</p>
     * @apiSuccess (每日上新-视频){number} module_base_conf.count 需要展示商品数量
     *
     * @apiSuccess (每日上新-商品){string} module_base_conf.sort 排序规则。自动推荐
     * 														<p>first_sell_sort_value:按照【商品首次上架排序值】降序（即按照运营总后的上新商品排序功能设置的顺序）</p>
     *														<p>create_date:按照【商品发布时间】降序</p>
     *														<p>sell_state_date:按照【商品最近一次上架时间】降序</p>
     * 														<p>first_sell_state_date:按照【商品首次上架时间】降序</p>
     * @apiSuccess (每日上新-商品){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (每日上新-商品){number} module_base_conf.count 需要展示商品数量
     *
     * @apiSuccess (品牌街){number} module_base_conf.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (品牌街){string} module_base_conf.product_type_ids 商品分类ID集合，如果存在多个商品分类则使用英文逗号分隔
     * @apiSuccess (品牌街){object[]} module_base_conf.brand_ids 品牌ID集合，以数组形式存储
     * @apiSuccess (品牌街){string} module_base_conf.sort 排序规则
     * 										 <p>create_date asc:创建时间顺序</p>
     * 										 <p>create_date desc :创建时间倒序</p>
     * @apiSuccess (品牌街){number} module_base_conf.show_type 展示方式
     * 										 <p>1：顺序展示</p>
     * 										 <p>2：随机展示</p>
     * @apiSuccess (品牌街){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (品牌街){number} module_base_conf.switch_interval 自动切换时间（单位秒）
     *
     * @apiSuccess (热卖TOP){number} module_base_conf.select_type 推荐方式 <p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiSuccess (热卖TOP){string} module_base_conf.sort 排序规则
     *                                               <p>product_count：按照总销量降序</p>
     *                                               <p>product_count7：按照最近7天销量降序</p>
     * 												 <p>product_count15：按照最近15天销量降序</p>
     * 												 <p>product_count30：按照最近30天销量降序</p>
     * 												 <p>product_count90：按照最近90天销量降序</p>
     * @apiSuccess (热卖TOP){object[]} module_base_conf.products 商品货号集合，以数组形式存储
     * @apiSuccess (热卖TOP){string} module_base_conf.title 标题文字
     * @apiSuccess (热卖TOP){string} module_base_conf.link 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (热卖TOP){number} module_base_conf.is_discount 是否显示折扣： <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (热卖TOP){number} module_base_conf.is_price 是否显示价格 <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (热卖TOP){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (热卖TOP){number} module_base_conf.count 查询商品数量
     *
     * @apiSuccess (特惠抢购){number} module_base_conf.select_type 推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (特惠抢购){number} module_base_conf.recommend_type 宝贝类型 p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (特惠抢购){number} module_base_conf.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (特惠抢购){number} module_base_conf.activity_id 活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (特惠抢购){string} module_base_conf.sort 排序规则。自动推荐存储 <p>overall：汇总排序(上新+库存)降序</p>
     * @apiSuccess (特惠抢购){string} module_base_conf.products 商品货号集合，数组形式存储
     * @apiSuccess (特惠抢购){string} module_base_conf.title 标题文字
     * @apiSuccess (特惠抢购){string} module_base_conf.link 链接链接，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (特惠抢购){number} module_base_conf.is_discount 是否显示折扣 <p>1：显示</p><p>2：不显示</p>
     * @apiSuccess (特惠抢购){number} module_base_conf.is_price 是否显示价格 <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (特惠抢购){number} module_base_conf.count 查询商品数量
     *
     * @apiSuccess (商品分类){string} module_base_conf.title 标题文字
     * @apiSuccess (商品分类){string} module_base_conf.color 模块主色（颜色代码）
     * @apiSuccess (商品分类){object[]} module_base_conf.links 链接集合
     * @apiSuccess (商品分类){string} module_base_conf.links.link_name 链接名称
     * @apiSuccess (商品分类){string} module_base_conf.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类){number} module_base_conf.count 展示的链接数量
     * @apiSuccess (商品分类){object[]} module_base_conf.left_links 左侧链接集合
     * @apiSuccess (商品分类){string} module_base_conf.left_links.img_url 左侧图片路径
     * @apiSuccess (商品分类){string} module_base_conf.left_links.link_url 左侧链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类){string} module_base_conf.left_count 左侧展示的链接数量
     * @apiSuccess (商品分类){number} module_base_conf.right_select_type 右侧推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (商品分类){number} module_base_conf.right_recommend_type 右侧宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (商品分类){number} module_base_conf.right_product_type_id 右侧商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类){number} module_base_conf.right_activity_id 右侧活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (商品分类){string} module_base_conf.right_sort 右侧排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (商品分类){object[]} module_base_conf.right_products 右侧商品信息
     * @apiSuccess (商品分类){string} module_base_conf.right_products.title 标题
     * @apiSuccess (商品分类){string} module_base_conf.right_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){object[]} module_base_conf.right_count 右侧展示的商品数量
     * @apiSuccess (商品分类){object[]} module_base_conf.bottom_products 底部商品信息
     * @apiSuccess (商品分类){string} module_base_conf.bottom_products.title 标题
     * @apiSuccess (商品分类){string} module_base_conf.bottom_products.selling 卖点
     * @apiSuccess (商品分类){string} module_base_conf.bottom_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){number} module_base_conf.bottom_count 底部展示的商品数量
     *
     * @apiSuccess (预售抢先){number} module_base_conf.select_type 推荐方式 <p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiSuccess (预售抢先){number} module_base_conf.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (预售抢先){number} module_base_conf.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (预售抢先){number} module_base_conf.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (预售抢先){string} module_base_conf.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (预售抢先){string} module_base_conf.products 商品货号集合，数组形式存储
     * @apiSuccess (预售抢先){number} module_base_conf.main_title 标题大字
     * @apiSuccess (预售抢先){number} module_base_conf.sub_title 标题小字
     * @apiSuccess (预售抢先){number} module_base_conf.goods_time 到货时间：1显示或者2不显示
     * @apiSuccess (预售抢先){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (预售抢先){number} module_base_conf.count 展示的商品数量
     * @apiSuccess (实力定制){number} module_base_conf.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (实力定制){number} module_base_conf.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (实力定制){number} module_base_conf.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (实力定制){number} module_base_conf.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (实力定制){string} module_base_conf.sort 排序规则
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量生序</p>
     * @apiSuccess (实力定制){object[]} module_base_conf.products 商品货号集合
     * @apiSuccess (实力定制){number} module_base_conf.main_title 标题大字
     * @apiSuccess (实力定制){number} module_base_conf.sub_title 标题小字
     * @apiSuccess (实力定制){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (实力定制){number} module_base_conf.count 展示的商品数量
     *
     * @apiSuccess (猜你喜欢){number} module_base_conf.select_type 推荐方式
     * 														<p>1:自动推荐</p>
     * 														<p>2:手动推荐</p>
     * @apiSuccess (猜你喜欢){number} module_base_conf.recommend_type 推荐类型
     * 														<p>1.按照分类商品推荐</p>
     * 														<p>2.按照活动推荐</p>
     * @apiSuccess (猜你喜欢){number} module_base_conf.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (猜你喜欢){number} module_base_conf.activity_id 		活动ID，0为全部活动。自动推荐存储
     * @apiSuccess (猜你喜欢){string} module_base_conf.sort 排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (猜你喜欢){object[]} module_base_conf.products 商品货号集合
     * @apiSuccess (猜你喜欢){number} module_base_conf.title 标题文字
     * @apiSuccess (猜你喜欢){number} module_base_conf.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (猜你喜欢){number} module_base_conf.count 展示的商品数量
     *
     * @apiSuccess (快捷菜单){object[]} module_base_conf.links 链接集合
     * @apiSuccess (快捷菜单){string} module_base_conf.links.title 链接标题
     * @apiSuccess (快捷菜单){string} module_base_conf.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (快捷菜单){number} module_base_conf.count 样式布局，直接填写数量
     *
     * @apiSuccess (最新童鞋){string} module_base_conf.fixed_period 固化时间
     * @apiSuccess (最新童鞋){number} module_base_conf.count 链接展示数量
     * @apiSuccess (最新童鞋){object[]} module_base_conf.links 链接集合
     * @apiSuccess (最新童鞋){string} module_base_conf.links.link_name 链接名称
     * @apiSuccess (最新童鞋){string} module_base_conf.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋){number} module_base_conf.left_count 左侧链接展示数量
     * @apiSuccess (最新童鞋){object[]} module_base_conf.left_links 左侧链接集合
     * @apiSuccess (最新童鞋){string} module_base_conf.left_links.img_url 左侧图片路径
     * @apiSuccess (最新童鞋){string} module_base_conf.left_links.link_url 左侧链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋){number} module_base_conf.middle_select_type 中间推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (最新童鞋){number} module_base_conf.middle_recommend_type 中间宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (最新童鞋){number} module_base_conf.middle_product_type_id 中间商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋){number} module_base_conf.middle_activity_id 中间活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋){string} module_base_conf.middle_sort 中间排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (最新童鞋){object[]} module_base_conf.middle_products 中间商品信息
     * @apiSuccess (最新童鞋){string} module_base_conf.middle_products.title 标题
     * @apiSuccess (最新童鞋){string} module_base_conf.middle_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋){number} module_base_conf.middle_count 中间展示的商品数量
     *
     * @apiSuccess (最新童鞋){number} module_base_conf.right_select_type 右侧推荐方式 <p>1:自动推荐</p> <p>2:手动推荐</p>
     * @apiSuccess (最新童鞋){number} module_base_conf.right_recommend_type 右侧宝贝类型 <p>1:分类宝贝</p> <p>2:活动宝贝</p>
     * @apiSuccess (最新童鞋){number} module_base_conf.right_product_type_id 右侧商品分类ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋){number} module_base_conf.right_activity_id 右侧活动ID，0为全部分类。自动推荐存储
     * @apiSuccess (最新童鞋){string} module_base_conf.right_sort 右侧排序规则
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiSuccess (最新童鞋){object[]} module_base_conf.right_products 右侧商品信息
     * @apiSuccess (最新童鞋){string} module_base_conf.right_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋){number} module_base_conf.right_count 右侧展示的商品数量
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    /**
     * @api {post}/{project_name}/fixed_module/edit 固定模块配置
     * @apiGroup fixed_module
     * @apiDescription 配置固定模块详细信息，根据参数module_code不同，返回值也不同，所有的返回值数据全部使用JSON字符串进行返回
     * @apiVersion 2.0.0
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>home_like：首页猜你喜欢</p>
     *                                          <p>home_custom：首页自定义区</p>
     * @apiParam {number} [group_sort=0] 固定模块分组排序值，【商品分类】模块可能存在多个分组
     * @apiParam {object} module_base_conf 固定模块配置json
     * @apiParam (首页猜你喜欢){object[]} module_base_conf.conf_list 展示商品配置数组
     * @apiParam (首页猜你喜欢){string} module_base_conf.conf_list.title 标题
     * @apiParam (首页猜你喜欢){string} module_base_conf.conf_list.subtitle 副标题
     * @apiParam (首页猜你喜欢){number} module_base_conf.conf_list.select_type 推荐方式<p>1:自动推荐</p><p>2:手动推荐</p>
     * @apiParam (首页猜你喜欢){number} module_base_conf.conf_list.recommend_type 推荐类型<p>1.按照分类商品推荐</p><p>2.按照活动推荐</p>
     * @apiParam (首页猜你喜欢){number} module_base_conf.conf_list.product_type_id 商品分类ID，0为全部分类。自动推荐存储
     * @apiParam (首页猜你喜欢){number} module_base_conf.conf_list.activity_id 活动ID，0为全部活动。自动推荐存储
     * @apiParam (首页猜你喜欢){string} module_base_conf.conf_list.sort_type 排序类型，手动推荐存储<p>1.默认顺序</p><p>2.随机顺序</p>
     * @apiParam (首页猜你喜欢){string} module_base_conf.conf_list.sort 排序规则。自动推荐存储
     *                                              <p>overall desc：汇总排序(上新+库存)降序</p>
     *                                              <p>overall asc：汇总排序(上新+库存)升序</p>
     *												<p>sell_state_date desc:按照上架时间降序</p>
     *												<p>sell_state_date asc:按照上架时间升序</p>
     *												<p>product_count desc:按照总销量降序</p>
     *												<p>product_count asc:按照总销量升序</p>
     * @apiParam (首页猜你喜欢){object[]} module_base_conf.conf_list.products 商品货号集合
     * @apiParam (首页猜你喜欢){number} module_base_conf.conf_list.fixed_period 数据固化周期（单位小时）
     * @apiParam (首页猜你喜欢){number} module_base_conf.conf_list.product_conf.is_enabled 是否启用 <p>1.启用</p><p>2.禁用</p>
     *
     * @apiParam (首页自定义区){object[]} module_base_conf.conf_list 配置信息数组
     * @apiParam (首页自定义区){string} module_base_conf.conf_list.title 标题
     * @apiParam (首页自定义区){string} module_base_conf.conf_list.subtitle 副标题
     * @apiParam (首页自定义区){string} module_base_conf.conf_list.img_url 图片路径
     * @apiParam (首页自定义区){string} module_base_conf.conf_list.link_url 链接路径，以#号分隔。以数组形式存储
     *       					<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 							<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 							<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>/
     * 							<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 							<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editFixedModule(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.fixedModuleService.editFixedModule(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/fixed_module/remove 删除固定模板
     * @apiGroup fixed_module
     * @apiDescription 删除固定模板
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiParam {number} group_sort 固定模块分组排序值，【商品分类】模块可能存在多个分组
     *
     * @apiSuccess {boolean} state              接口获取数据是否成功.true:成功 false:失败
     * @apiSuccess {string} message             接口返回信息注释.
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeFixedModule(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.fixedModuleService.removeFixedModule(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/fixed_module/sort 固定模板排序
     * @apiGroup fixed_module
     * @apiDescription 更新固定模板排序
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiParam {number} new_group_sort 新的排序值（从0开始）
     * @apiParam {number} old_group_sort 原来排序值（从0开始）
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功 false:失败
     * @apiSuccess {string} message 接口返回信息注释.
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet editFixedModuleSort(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.fixedModuleService.editFixedModuleSort(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/fixed_module/count 固定模板数量
     * @apiGroup fixed_module
     * @apiDescription 查询固定模板数量
     *
     * @apiParam {number} page_id 页面ID
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功 false:失败
     * @apiSuccess {string} message 接口返回信息注释.
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {string} obj.module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiSuccess {number} obj.module_count 模块数量
     * @apiSuccess {number} obj.state 状态 1.启用 2.禁用
     */
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFixedModuleCount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.fixedModuleService.queryFixedModuleCount(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post}/{project_name}/fixed_module/data 固定模块数据
     * @apiGroup fixed_module
     * @apiDescription 查询固定模块展示数据
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>seckill：限时秒杀</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiParam {number} [group_sort] 固定模块分组排序值，商品分类模块需填写。默认为0
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     *
     * @apiSuccess (banner图){object[]} obj.links 链接集合
     * @apiSuccess (banner图){string} obj.links.img_url 图片路径
     * @apiSuccess (banner图){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (banner图){number} obj.switch_type 切换方式
     * @apiSuccess (banner图){number} obj.switch_interval 自动切换时间（单位秒）
     *
     * @apiSuccess (banner小广告){object[]} obj.links 链接集合
     * @apiSuccess (banner小广告){string} obj.links.img_url 链接图片路径
     * @apiSuccess (banner小广告){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     *
     * @apiSuccess (每日上新-视频){object[]} obj.products 商品信息
     * @apiSuccess (每日上新-视频){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (每日上新-视频){string} obj.products.product_name 商品名称
     * @apiSuccess (每日上新-视频){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (每日上新-视频){string} obj.products.product_video_url 商品视频路径
     * @apiSuccess (每日上新-视频){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (每日上新-视频){number} obj.is_auto_play 是否自动播放，1是 2否
     * @apiSuccess (每日上新-视频){number} obj.is_continued_play 是否连续播放，1是 2否
     *
     * @apiSuccess (每日上新-商品){object[]} obj.products 商品信息
     * @apiSuccess (每日上新-商品){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (每日上新-商品){string} obj.products.product_name 商品名称
     * @apiSuccess (每日上新-商品){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (每日上新-商品){string} obj.products.sale_price_min 商品价格
     * @apiSuccess (每日上新-商品){number} obj.fixed_period 数据固化周期（单位小时）
     *
     * @apiSuccess (品牌街){object[]} obj.brands 商品品牌信息
     * @apiSuccess (品牌街){string} obj.brands.brand_name 商品品牌名称
     * @apiSuccess (品牌街){string} obj.brands.brand_logo 商品品牌LOGO
     * @apiSuccess (品牌街){number} obj.brands.update_count 商品上新数量
     * @apiSuccess (品牌街){number} obj.show_type 展示方式：1顺序展示；2随机展示
     * @apiSuccess (品牌街){number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess (品牌街){number} obj.switch_interval 自动切换时间（单位秒）
     *
     * @apiSuccess (热卖TOP){object[]} obj.products 商品信息
     * @apiSuccess (热卖TOP){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (热卖TOP){string} obj.products.product_name 商品名称
     * @apiSuccess (热卖TOP){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (热卖TOP){string} obj.products.sale_price_min 商品价格
     * @apiSuccess (热卖TOP){string} obj.products.discount 商品折扣率
     * @apiSuccess (热卖TOP){string} obj.title 标题文字
     * @apiSuccess (热卖TOP){string} obj.link 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (热卖TOP){number} obj.is_discount 是否显示折扣： <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (热卖TOP){number} obj.is_price 是否显示价格 <p>1：显示</p> <p>2：不显示</p>
     * @apiSuccess (热卖TOP){number} obj.fixed_period 数据固化周期（单位小时）
     *
     * @apiSuccess (特惠抢购){object[]} obj.products 商品信息
     * @apiSuccess (特惠抢购){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (特惠抢购){string} obj.products.product_name 商品名称
     * @apiSuccess (特惠抢购){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (特惠抢购){string} obj.products.sale_price_min 商品价格
     * @apiSuccess (特惠抢购){string} obj.title 标题文字
     * @apiSuccess (特惠抢购){string} obj.link 链接链接，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (特惠抢购){number} obj.is_discount 是否显示折扣 <p>1：显示</p><p>2：不显示</p>
     * @apiSuccess (特惠抢购){number} obj.is_price 是否显示价格 <p>1：显示</p> <p>2：不显示</p>
     *
     * @apiSuccess (商品分类){string} obj.title 标题文字
     * @apiSuccess (商品分类){string} obj.color 模块主色（颜色代码）
     * @apiSuccess (商品分类){object[]} obj.links 链接集合
     * @apiSuccess (商品分类){string} obj.links.link_name 链接名称
     * @apiSuccess (商品分类){string} obj.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类){object[]} obj.left_links 左侧链接集合
     * @apiSuccess (商品分类){string} obj.left_links.img_url 左侧图片路径
     * @apiSuccess (商品分类){string} obj.left_links.link_url 左侧链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (商品分类){object[]} obj.right_products 右侧商品信息
     * @apiSuccess (商品分类){string} obj.right_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){string} obj.right_products.product_name 商品名称
     * @apiSuccess (商品分类){string} obj.right_products.product_img_url 商品主图路径
     * @apiSuccess (商品分类){string} obj.right_products.sale_price_min 商品价格
     * @apiSuccess (商品分类){string} obj.right_products.title 标题
     * @apiSuccess (商品分类){string} obj.right_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){object[]} obj.bottom_products 底部商品信息
     * @apiSuccess (商品分类){string} obj.bottom_products.title 标题
     * @apiSuccess (商品分类){string} obj.bottom_products.selling 卖点
     * @apiSuccess (商品分类){string} obj.bottom_products.product_itemnumber 商品货号
     * @apiSuccess (商品分类){string} obj.bottom_products.product_name 商品名称
     * @apiSuccess (商品分类){string} obj.bottom_products.product_img_url 商品主图路径
     * @apiSuccess (商品分类){string} obj.bottom_products.sale_price_min 商品价格
     *
     * @apiSuccess (预售抢先){object[]} obj.products 商品信息
     * @apiSuccess (预售抢先){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (预售抢先){string} obj.products.product_name 商品名称
     * @apiSuccess (预售抢先){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (预售抢先){string} obj.products.sale_price_min 商品价格
     * @apiSuccess (预售抢先){string} obj.products.plan_delivery_date 预计发货时间
     * @apiSuccess (预售抢先){string} obj.main_title 标题大字
     * @apiSuccess (预售抢先){string} obj.sub_title 标题小字
     * @apiSuccess (预售抢先){number} obj.goods_time 到货时间：1显示或者2不显示
     * @apiSuccess (预售抢先){number} obj.fixed_period 数据固化周期（单位小时）
     *
     * @apiSuccess (实力定制){object[]} obj.products 商品信息
     * @apiSuccess (实力定制){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (实力定制){string} obj.products.product_name 商品名称
     * @apiSuccess (实力定制){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (实力定制){string} obj.products.sale_price_min 商品价格
     * @apiSuccess (实力定制){string} obj.main_title 标题大字
     * @apiSuccess (实力定制){string} obj.sub_title 标题小字
     * @apiSuccess (实力定制){number} obj.fixed_period 数据固化周期（单位小时）
     *
     * @apiSuccess (限时秒杀){object[]} obj.products 商品信息
     * @apiSuccess (限时秒杀){string} obj.products.product_itemnumber 商品货号
     * @apiSuccess (限时秒杀){string} obj.products.product_name 商品名称
     * @apiSuccess (限时秒杀){string} obj.products.product_img_url 商品主图路径
     * @apiSuccess (限时秒杀){string} obj.products.sale_price_min 商品价格
     * @apiSuccess (限时秒杀){number} obj.products.sale_count 商品销量
     * @apiSuccess (限时秒杀){number} obj.countdown 倒计时时间，单位秒
     * @apiSuccess (限时秒杀){number} obj.start_flag 活动状态 <p>1.活动开始</p><p>2.活动预告</p>
     * @apiSuccess (限时秒杀){number} obj.begin_date 活动开始时间
     * @apiSuccess (限时秒杀){number} obj.end_date 活动结束时间
     * @apiSuccess (限时秒杀){number} obj.now_date 当前时间
     *
     * @apiSuccess (快捷菜单){object[]} obj.links 链接集合
     * @apiSuccess (快捷菜单){string} obj.links.title 链接标题
     * @apiSuccess (快捷菜单){string} obj.links.link_url 链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (快捷菜单){number} obj.count 样式布局，直接填写数量
     *
     * @apiSuccess (最新童鞋){string} obj.fixed_period 标题文字
     * @apiSuccess (最新童鞋){string} obj.color 模块主色（颜色代码）
     * @apiSuccess (最新童鞋){object[]} obj.links 链接集合
     * @apiSuccess (最新童鞋){string} obj.links.link_name 链接名称
     * @apiSuccess (最新童鞋){string} obj.links.link_url 链接内容，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋){object[]} obj.left_links 左侧链接集合
     * @apiSuccess (最新童鞋){string} obj.left_links.img_url 左侧图片路径
     * @apiSuccess (最新童鞋){string} obj.left_links.link_url 左侧链接路径，以#号分隔
     *       												<p>页面链接，格式：1(链接类型)#link#10001(页面ID)</p>
     * 														<p>商品链接，格式：2(链接类型)#link#605289(商品货号)</p>
     * 														<p>分组链接，格式：3(链接类型)#link#10001(分组ID)</p>
     * 														<p>列表页链接，格式：4(链接类型)#1ink#keyword(关键字)=1&brands_id(品牌ID)=1&season(季节)=1&sex(性别)=1&warehouse(仓库)=1&product_size(尺码)=1&sort(排序，overall：综合 product_count：销量 first_sell_sort_value：上新)=1</p>
     * 														<p>自定义链接，格式：5(链接类型)#link#http://www.tkvip.com(自定义链接)</p>
     * @apiSuccess (最新童鞋){object[]} obj.right_products 右侧商品信息
     * @apiSuccess (最新童鞋){string} obj.right_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋){string} obj.right_products.product_name 商品名称
     * @apiSuccess (最新童鞋){string} obj.right_products.product_img_url 商品主图路径
     * @apiSuccess (最新童鞋){string} obj.right_products.sale_price_min 商品价格
     * @apiSuccess (最新童鞋){string} obj.right_products.title 标题
     * @apiSuccess (最新童鞋){string} obj.right_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋){object[]} obj.bottom_products 底部商品信息
     * @apiSuccess (最新童鞋){string} obj.bottom_products.title 标题
     * @apiSuccess (最新童鞋){string} obj.bottom_products.selling 卖点
     * @apiSuccess (最新童鞋){string} obj.bottom_products.product_itemnumber 商品货号
     * @apiSuccess (最新童鞋){string} obj.bottom_products.product_name 商品名称
     * @apiSuccess (最新童鞋){string} obj.bottom_products.product_img_url 商品主图路径
     * @apiSuccess (最新童鞋){string} obj.bottom_products.sale_price_min 商品价格
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryFixedModuleDataList(HttpServletRequest request) {
        return Transform.GetResult(this.fixedModuleService.queryFixedModuleDataList(request));
    }

    /**
     * @api {post}/{project_name}/fixed_module/like_data 猜你喜欢商品数据
     * @apiGroup fixed_module
     * @apiDescription 查询猜你喜欢模块商品数据
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} page_id 页面ID
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {string} obj.title 标题文字
     * @apiSuccess {number} obj.fixed_period 数据固化周期（单位小时）
     * @apiSuccess {number} obj.total 商品总数量
     * @apiSuccess {object[]} obj.products 商品集合
     * @apiSuccess {string} obj.products.product_itemnumber 商品货号
     * @apiSuccess {string} obj.products.product_name 商品名称
     * @apiSuccess {string} obj.products.product_img_url 商品主图路径
     * @apiSuccess {string} obj.products.sale_price_min 商品价格
     * @apiSuccess {string} obj.products.product_tag 商品标签
     * @apiSuccess {string} obj.products.product_count 商品销量
     * @apiSuccess {string} obj.products.is_outstock 是否缺货<p>0.否</p><p>1.是</p>
     * @apiSuccess {string} obj.products.is_custom 是否定制 <p>0.否</p><p>1.是</p>
     */
    @RequestMapping(value = "/like_data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryLikeModuleDataList(HttpServletRequest request) {
        return Transform.GetResult(this.fixedModuleService.queryLikeModuleDataList(request));
    }

    /**
     * @api {post}/{project_name}/fixed_module/home_page/like_data 首页猜你喜欢商品数据
     * @apiGroup fixed_module
     * @apiDescription 查询首页猜你喜欢模块商品数据
     * @apiVersion 2.0.0
     *
     * @apiParam {number} pageIndex 开始页码
     * @apiParam {number} pageSize 每页数据量
     * @apiParam {number} page_id 页面ID
     * @apiParam {number} [menu_type] 菜单类型，默认为1，用于查询商品相关数据
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {string} obj.title_list 菜单集合
     * @apiSuccess {string} obj.title_list.title 标题文字
     * @apiSuccess {string} obj.title_list.subtitle 副标题文字
     * @apiSuccess {number} obj.title_list.fixed_period 数据固化周期（单位小时）
     * @apiSuccess {number} obj.title_list.menu_type 菜单类型，用于查询商品相关数据
     * @apiSuccess {number} obj.total 商品总数量
     * @apiSuccess {object[]} obj.products 商品集合
     * @apiSuccess {string} obj.products.PRODUCT_ITEMNUMBER 商品货号
     * @apiSuccess {string} obj.products.PRODUCT_NAME 商品名称
     * @apiSuccess {string} obj.products.PRODUCT_IMG_URL 商品主图路径
     * @apiSuccess {string} obj.products.SALE_PRICE_MIN 商品价格
     * @apiSuccess {string} obj.products.PRODUCT_TAG 商品标签
     * @apiSuccess {string} obj.products.PRODUCT_COUNT 商品销量
     * @apiSuccess {string} obj.products.MIN_SIZE 最小尺码
     * @apiSuccess {string} obj.products.IS_OUTSTOCK 是否缺货<p>0.否</p><p>1.是</p>
     * @apiSuccess {string} obj.products.IS_CUSTOM 是否定制 <p>0.否</p><p>1.是</p>
     */
    @RequestMapping(value = "/home_page/like_data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryHomePageLikeModuleDataList(HttpServletRequest request) {
        return Transform.GetResult(this.fixedModuleService.queryHomePageLikeModuleDataList(request));
    }


    /**
     * @api {post}/{project_name}/fixed_module/activity_data 活动模块商品数据
     * @apiGroup fixed_module
     * @apiDescription 查询活动模块商品数据
     *
     * @apiParam {number} activity_id 活动ID
     * @apiParam {number} site_id 站点ID
     * @apiParam {string} [sort] 排序值，默认综合排序
     *                                            <p>overall:综合排序</p>
     *                                            <p>sell_state_date：上架时间</p>
     *                                            <p>product_count：销量</p>
     *                                            <p>sort_id：按活动手工调整排名</p>
     *
     * @apiSuccess {boolean} state 接口审核是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息说明
     * @apiSuccess {object[]} obj 接口返回数据
     * @apiSuccess {string} obj.products.product_itemnumber 商品货号
     * @apiSuccess {string} obj.products.product_name 商品名称
     * @apiSuccess {string} obj.products.product_img_url 商品主图路径
     * @apiSuccess {string} obj.products.sale_price_min 商品价格
     */
    @RequestMapping(value = "/activity_data", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryActivityModuleProductList(HttpServletRequest request) {
        return Transform.GetResult(this.fixedModuleService.queryActivityModuleProductList(request));
    }

    /**
     * @api {post} /{project_name}/fixed_module/edit_state 编辑固定模板状态
     * @apiGroup fixed_module
     * @apiDescription 编辑固定模板状态
     *
     * @apiParam {number} page_id 页面ID
     * @apiParam {string} module_code 固定模块代码
     *                                          <p>banner：banner图</p>
     *                                          <p>small_banner：banner小广告位</p>
     *                                          <p>update_video：每日上新-视频</p>
     *                                          <p>update_product：每日上新-商品</p>
     *                                          <p>brand：品牌街</p>
     *                                          <p>hot：热卖TOP</p>
     *                                          <p>buy：特惠抢购</p>
     *                                          <p>classify：商品分类</p>
     *                                          <p>presell：预售抢先</p>
     *                                          <p>custom：实力定制</p>
     *                                          <p>like：猜你喜欢</p>
     *                                          <p>menu：快捷菜单</p>
     * @apiParam {number} state 状态 1.启用 2.禁用
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功 false:失败
     * @apiSuccess {string} message 接口返回信息注释.
     */
    @RequestMapping(value = "/edit_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet editFixedModuleState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.fixedModuleService.editFixedModuleState(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/fixed_module/order_list 定制订单列表
     * @apiGroup fixed_module
     * @apiDescription 查询最新十条定制订单列表
     *
     * @apiSuccess {boolean} state 接口获取数据是否成功.true:成功 false:失败
     * @apiSuccess {string} message 接口返回信息注释.
     * @apiSuccess {object[]} obj 接口返回信息数据.
     * @apiSuccess {string} obj.user_manage_name 用户名
     * @apiSuccess {string} obj.product_itemnumber 用户名
     * @apiSuccess {number} obj.product_count 用户名
     */
    @RequestMapping(value = "/order_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNewCustomOrderList(HttpServletRequest request) {
        return Transform.GetResult(this.fixedModuleService.queryNewCustomOrderList(request));
    }
}
