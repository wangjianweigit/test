package com.tk.analysis.stationed.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.analysis.stationed.service.StationedAnalysisService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/stationed_analysis")
public class StationedAnalysisControl {
	@Resource
	private StationedAnalysisService stationedAnalysisService;
	
	/**
	 * 商家今日实时销量-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_sale_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeSaleChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryRealTimeSaleChart(request));
    }
	
	/**
	 * 商家今日实时销量-商家销售列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realTime_sale_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRealTimeSaleList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryRealTimeSaleList(request));
    }
	
	/**
	 * 商家分布地图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distribution_map", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributionMap(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryDistributionMap(request));
    }
	
	/**
	 * 商家分布区域排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distribution_area_rank", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributionAreaRank(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryDistributionAreaRank(request));
    }
	
	/**
	 * 商家分布区域详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distribution_area_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDistributionAreaDetail(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryDistributionAreaDetail(request));
    }
	
	/**
	 * 在售商品-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/on_sale_product_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOnSaleProductChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryOnSaleProductChart(request));
    }
	
	/**
	 * 在售商品-列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/on_sale_product_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOnSaleProductList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryOnSaleProductList(request));
    }
	
	/**
	 * 商家注册资本-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registered_capital_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRegisteredCapitalChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryRegisteredCapitalChart(request));
    }
	
	/**
	 * 商家成立年数-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/establish_year_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryEstablishYearChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryEstablishYearChart(request));
    }
	
	/**
	 * 在售商品过季情况-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/out_of_season_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOutOfSeasonChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryOutOfSeasonChart(request));
    }
	
	/**
	 * 在售商品过季情况-列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/out_of_season_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOutOfSeasonList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryOutOfSeasonList(request));
    }
	
	/**
	 * 用户复购-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/repeat_purchase_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRepeatPurchaseChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryRepeatPurchaseChart(request));
    }

	/**
	 * 用户复购-列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/repeat_purchase_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryRepeatPurchaseList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryRepeatPurchaseList(request));
    }
	
	/**
	 * 商家交易-商品销量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_payCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTr_PayCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTr_PayCount(request));
    }
	
	/**
	 * 商家交易-商品销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTr_PayMoney(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTr_PayMoney(request));
    }
	
	/**
	 * 商家交易-未发退货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_unsentReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTr_UnsentReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTr_UnsentReturnCount(request));
    }
	
	/**
	 * 商家交易-已发退货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_sentReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTr_SentReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTr_SentReturnCount(request));
    }
	
	/**
	 * 商家交易-终检异常
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_fqcCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTr_FqcCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTr_FqcCount(request));
    }
	
	/**
	 * 商家交易-售后异常
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_saleReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTr_SaleReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTr_SaleReturnCount(request));
    }
	
	/**
	 * 商家交易-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTrChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTrChart(request));
    }
	
	/**
	 * 商家交易-商家列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedTr_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedTrList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedTrList(request));
    }
	
	/**
	 * 商家交易-商家配合度
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cooperationDegree_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCooperationDegreeList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryCooperationDegreeList(request));
    }
	
	/**
	 * 商家分析-详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedAnalysis_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedAnalysisDetail(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedAnalysisDetail(request));
    }
	
	/**
	 * 商家分析-今日实时销售-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedAnalysis_sale_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedAnalysis_SaleChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedAnalysis_SaleChart(request));
    }
	
	/**
	 * 商家分析-商品分类销量-折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedAnalysis_productType_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedAnalysis_ProductTypeChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedAnalysis_ProductTypeChart(request));
    }
	
	/**
	 * 商家分析-今日实时销售
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationedAnalysis_sale_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStationedAnalysis_SaleDetail(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryStationedAnalysis_SaleDetail(request));
    }
	
	/**
	 * 商家分析-在售商品基本情况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/onSaleProduct_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryOnSaleProductDetail(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryOnSaleProductDetail(request));
    }
	
	/**
	 * 商品-基本情况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/product_basic", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductBasic(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductBasic(request));
    }
	
	/**
	 * 商品-商品类型占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productType_ratio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductTypeRatio(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductTypeRatio(request));
    }
	
	/**
	 * 商品-商品类型列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productType_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductTypeList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductTypeList(request));
    }
	
	/**
	 * 商品-商品结构占比
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productStructure_ratio", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductStructureRatio(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductStructureRatio(request));
    }
	
	/**
	 * 商品-商品结构列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productStructure_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductStructureList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductStructureList(request));
    }
	
	/**
	 * 商品-商品结构详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productStructure_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductStructureDetail(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductStructureDetail(request));
    }
	
	/**
	 * 商品-码段结构
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/code_segment", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCodeSegment(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryCodeSegment(request));
    }
	
	/**
	 * 销售-商品销量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_payCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySalePayCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySalePayCount(request));
    }
	
	/**
	 * 销售-商品销售总额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_payMoney", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySalePayMoney(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySalePayMoney(request));
    }
	
	/**
	 * 销售-支付买家数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_purchaseNumber", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySalePurchaseNumber(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySalePurchaseNumber(request));
    }

	/**
	 * 销售-未发退货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_unsentReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleUnsentReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleUnsentReturnCount(request));
    }
	
	/**
	 * 销售-已发退货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_sentReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleSentReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleSentReturnCount(request));
    }
	
	/**
	 * 销售-终检异常
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_fqcCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleFqcCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleFqcCount(request));
    }
	
	/**
	 * 销售-售后异常
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_returnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleReturnCount(request));
    }
	
	/**
	 * 销售-新品数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_newProductCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleNewProductCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleNewProductCount(request));
    }
	
	/**
	 * 销售-活动商品数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_activityProductCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleActivityProductCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleActivityProductCount(request));
    }
	
	/**
	 * 查询销售分析折线
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleChart(request));
    }
	
	/**
	 * 销售分析-商品列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_productList", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleProductList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleProductList(request));
    }
	
	/**
	 * 销售分析-商品列表-供应商查看
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sale_supplier_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleSupplierList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleSupplierList(request));
    }
	
	/**
	 * 销售-区域销售排行
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationed_saleAreaRank", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaRank(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleAreaRank(request));
    }
	
	/**
	 * 销售-区域销售详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationed_saleAreaDetail", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaDetail(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleAreaDetail(request));
    }
	
	/**
	 * 销售-区域销售地图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stationed_saleAreaMap", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySaleAreaMap(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySaleAreaMap(request));
    }
	
	/**
	 * 销售-售后/品质问题分析
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/return_quality_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryReturnQualityChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryReturnQualityChart(request));
    }
	
	/**
	 * 销售-品质退换货列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/return_quality_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryReturnQualityList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryReturnQualityList(request));
    }
	
	/**
	 * 供应商-生产计划件数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_productionCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierProductionCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierProductionCount(request));
    }
	
	/**
	 * 供应商-入库数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_instorageCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierInstorageCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierInstorageCount(request));
    }
	
	/**
	 * 供应商-出货数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_outProductCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierOutProductCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierOutProductCount(request));
    }
	
	/**
	 * 供应商-终检异常
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_fqcCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierFqcCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierFqcCount(request));
    }

	/**
	 * 供应商-售后异常
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_saleReturnCount", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierSaleReturnCount(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierSaleReturnCount(request));
    }
	
	/**
	 * 供应商-折线图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_chart", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierChart(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierChart(request));
    }
	
	/**
	 * 供应商-列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierList(request));
    }
	
	/**
	 * 供应商-关联商品列表(无时间限制)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_productList", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierProductList(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierProductList(request));
    }
	
	/**
	 * 供应商-关联商品列表(有时间限制)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/supplier_productList_date", method = RequestMethod.POST)
    @ResponseBody
    public Packet querySupplierProductListByDate(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.querySupplierProductListByDate(request));
    }
	
	/**
	 * 商品类型下拉框
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/productType_option", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryProductTypeOption(HttpServletRequest request){
        return Transform.GetResult(stationedAnalysisService.queryProductTypeOption(request));
    }
	
}
