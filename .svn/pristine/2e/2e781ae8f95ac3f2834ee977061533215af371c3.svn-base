package com.tk.oms.analysis.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.analysis.service.AreaSalesAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("area_sales_analysis")
public class AreaSalesAnalysisControl {
	@Resource
	private AreaSalesAnalysisService areaSalesAnalysisService;
	
	/**
     * @api {post} /{project_name}/area_sales_analysis/list 查询区域销售分析报表
     * @apiName list
     * @apiGroup area_sales_analysis
     * @apiDescription  查询区域销售分析报表
     * @apiVersion 0.0.1
     * 
     * @apiParam {number} pageIndex 		开始页码
     * @apiParam {number} pageSize 			每页数据量 
     
     * 
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商申请数据列表
     * @apiSuccess {string} 	obj.manager_user_realname 业务经理
     * @apiSuccess {string} 	obj.store_name 门店
     * @apiSuccess {string} 	obj.referee_user_realname 业务员
     * @apiSuccess {number} 	obj.member_count 会员数
     * @apiSuccess {number} 	obj.big_member_ratio 大客户比例(已做百分比转换)
     * @apiSuccess {number} 	obj.new_member_count 新增会员数
     * @apiSuccess {number} 	obj.sales_money_total 总销售额
     * @apiSuccess {number} 	obj.order_total 订单总数
     * @apiSuccess {number}   	obj.replace_order_total 代发订单数
     * @apiSuccess {number}   	obj.per_capita_order_money 人均下单金额
     * @apiSuccess {number}   	obj.average_order_money 订单均额
     * @apiSuccess {number} 	obj.return_product_rate 退货率(已做百分比转换)
     * @apiSuccess {number} 	obj.member_service_money 会员服务费
     * @apiSuccess {number}   	obj.member_service_rate 会员服务费率
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request){
        return Transform.GetResult(areaSalesAnalysisService.queryList(request));
    }
}
