package com.tk.oms.marketing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.marketing.dao.ActivityProductDiscountDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 促销活动商品优惠
 * @author Administrator
 *
 */
@Service("ActivityProductDiscountService")
public class ActivityProductDiscountService {

	@Resource
	private ActivityProductDiscountDao activityProductDiscountDao;
	/**
	 * 获取参与促销活动的商品优惠列表
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryActivityProductDiscountList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//最终的返回值
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(paramMap==null
					||paramMap.isEmpty()
					||StringUtils.isEmpty(paramMap.get("activity_product_id"))
					||StringUtils.isEmpty(paramMap.get("product_itemnumber"))
					||StringUtils.isEmpty(paramMap.get("activity_id"))
					){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			/**
			 * 查询当前商品的所有规格，以及该规格的  
			 * 原始售价 ：   使用函数GETPRODUCTSPEC_TSPRICE计算，在入驻商报价、平台服务费的基础上，结合活动折扣
			 * 最低售价 ：   入驻商报*活动折扣
			 * 注：如果存在不同颜色、相同规格的情况，则以价格高的规格为准
			 */
			List<Map<String, Object>> priceList = activityProductDiscountDao.queryActivityProductPrice(paramMap);
			if(priceList==null || priceList.isEmpty()){
				pr.setState(false);
				pr.setMessage("当前商品【"+paramMap.get("product_itemnumber")+"】规格数据异常");
				return pr;
			}
			/**
			 *将商品的全部的规格数据组装成一份数据，如果当前商品未设置过阶梯家，则直接使用该数据
			 */
			List<Map<String, Object>> priceMapList = new ArrayList<Map<String,Object>>();
			//key = 规格    value=该规格的商品的原价等信息
			Map<String,Map<String, Object>> priceMapMap = new HashMap<String, Map<String,Object>>();
			for(Map<String, Object> priceMap:priceList){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("spec", priceMap.get("PRODUCT_GROUP_MEMBER"));								//规格
				map.put("yj", Float.parseFloat(priceMap.get("ORIGINAL_PRICE").toString()));			//原价
				map.put("xj", Float.parseFloat(priceMap.get("ORIGINAL_PRICE").toString()));			//阶梯价格
				map.put("count", 1);																//阶梯价格数量下限,默认1
				map.put("floor_price", Float.parseFloat(priceMap.get("FLOOR_PRICE").toString()));	//价格下限
				priceMapList.add(map);
				priceMapMap.put(priceMap.get("PRODUCT_GROUP_MEMBER").toString(), map);
			}
			/**
			 * 查询已经配置的阶梯价格列表
			 */
			List<Map<String, Object>> stepPriceList = activityProductDiscountDao.queryActivityProductDiscountList(paramMap);
			if(stepPriceList!=null && !stepPriceList.isEmpty()){
				/**
				 * 已经设置了阶梯折扣
				 */
				for(Map<String, Object> stepMap:stepPriceList){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("spec", stepMap.get("PRODUCT_SPEC"));														//规格
					map.put("yj",priceMapMap.get(stepMap.get("PRODUCT_SPEC").toString()).get("yj"));					//原价
					map.put("xj", Float.parseFloat(stepMap.get("PRIZE").toString()));									//阶梯价格
					map.put("count",Integer.parseInt(stepMap.get("MIN_COUNT").toString()));								//阶梯价格数量下限
					map.put("floor_price",priceMapMap.get(stepMap.get("PRODUCT_SPEC").toString()).get("floor_price"));	//价格下限
		    		resultList.add(map);
				}
				//轮询resultList，需要补上不存在的规格（即未设置阶梯价格的规格）
				for(String spec:priceMapMap.keySet()){
					//判断该当前货号是否已经在resultList中存在，如果不存在，则直接使用当前priceMapMap中的value代替
					boolean isExist = false;
					for(Map<String, Object> mm:resultList){
						if(spec.equals(mm.get("spec").toString())){
							isExist = true;
							break;
						}
					}
					if(!isExist){
			    		resultList.add(priceMapMap.get(spec));
					}
				}
			}else{
				/**
				 * 还未设置阶梯折扣
				 */
				resultList.addAll(priceMapList);
			}
			pr.setState(true);
			pr.setMessage("获取数据成功");
			pr.setObj(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 促销活动商品优化设置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult saveActivityProductDiscount(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("activity_product_discounts"))
					||StringUtils.isEmpty(paramMap.get("activity_product_id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			List<Map<String,Object>> quitActivityProductList=(List<Map<String,Object>>)paramMap.get("activity_product_discounts");
			if(quitActivityProductList.size()<1||quitActivityProductList.isEmpty()){
				pr.setMessage("参数为空");
				pr.setState(false);
				return pr;
			}
			activityProductDiscountDao.deleteActivityProductDiscount(paramMap);
			if(activityProductDiscountDao.insertActivityProductDiscount(quitActivityProductList)>0){
				pr.setMessage("操作成功");
				pr.setState(true);
			}else{
				pr.setMessage("操作失败");
				pr.setState(false);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
}