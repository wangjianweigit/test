package com.tk.oms.product.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.tk.sys.security.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.tk.oms.product.dao.ProductBrandDao;
import com.tk.oms.sys.dao.CacheInfoDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductBrandService")
public class ProductBrandService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
    private ProductBrandDao productBrandDao;
	@Resource
    private CacheInfoDao cacheInfoDao;

	/**
     * 获取商品品牌列表数据
     * @param request
     * @return
     */
	public GridResult queryProductBrandList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("参数缺失");
				return gr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if (pageParamResult != null) {
				return pageParamResult;
			}
			if (paramMap.containsKey("states")) {
				String[] states = ((String) paramMap.get("states")).split(",");
				paramMap.put("states", states);
			}
			int productBrandCount = productBrandDao.queryProductBrandCount(paramMap);
			// 获取商品品牌列表数据
			List<Map<String, Object>> productBrandList = productBrandDao.queryProductBrandList(paramMap);

			if (productBrandList != null && productBrandList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询品牌列表成功!");
				gr.setObj(productBrandList);
				gr.setTotal(productBrandCount);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}

		} catch (IOException e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
            e.printStackTrace();
		}
		return gr;
	}

	/**
	 * 获取品牌列表（下拉框专用）
	 * @param request
	 * @return
	 */
	public ProcessResult queryProductBrandAllList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			List<Map<String, Object>> brand = productBrandDao.queryProductBrandAllList();
			pr.setState(true);
			pr.setMessage("获取品牌详情成功");
			pr.setObj(brand);

		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage("获取品牌详情失败");
            e.printStackTrace();
		}

		return pr;
	}
	/**
	 * 获取品牌详情
	 * @param request
	 * @return
	 */
	public ProcessResult queryProductBrandDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setState(false);
				pr.setMessage("参数id缺失");
				return pr;
			}
			resultMap.put("brand",this.productBrandDao.queryById(Long.parseLong(paramMap.get("id").toString())));
			resultMap.put("classify_list",this.productBrandDao.queryBrandClassifyById(paramMap));
			pr.setState(true);
			pr.setMessage("获取品牌详情成功");
			pr.setObj(resultMap);

		} catch (IOException e) {
			pr.setState(false);
			pr.setMessage("获取品牌详情失败");
            e.printStackTrace();
		}

		return pr;
	}
	/**
     * 商品品牌新增
     * @param request
     * @return
     */
	@Transactional
	public ProcessResult addProductBrand(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
        try {
        	String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("brand_name"))) {
				pr.setState(false);
				pr.setMessage("参数缺失brand_name");
				return pr;
			}
            if (StringUtils.isEmpty(paramMap.get("code"))) {
                pr.setState(false);
                pr.setMessage("参数缺失code");
                return pr;
            }
            if('9'==paramMap.get("code").toString().charAt(0)){
            	pr.setState(false);
                pr.setMessage("品牌代码不能以9开头");
                return pr;
            }
			if (StringUtils.isEmpty(paramMap.get("logo"))) {
				pr.setState(false);
				pr.setMessage("参数缺失logo");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("member_service_rate"))) {
				pr.setState(false);
				pr.setMessage("参数缺失member_service_rate");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("trade_mark_license_img"))) {
				pr.setState(false);
				pr.setMessage("参数缺失trade_mark_license_img");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("insole_logo"))) {
				pr.setState(false);
				pr.setMessage("参数缺失insole_logo");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("classify_id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失classify_id");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("product_prize_tag_ratio"))) {
				pr.setState(false);
				pr.setMessage("参数缺失product_prize_tag_ratio");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("is_watermark"))) {
				pr.setState(false);
				pr.setMessage("参数缺失is_watermark");
				return pr;
			}
			ProcessResult watermarkPr = this.handleWatermarkSuffix(paramMap);
			if (watermarkPr != null) {
				return watermarkPr;
			}
			float member_service_rate = Float.parseFloat(paramMap.get("member_service_rate").toString());
			member_service_rate = member_service_rate / 100;
			paramMap.put("member_service_rate",member_service_rate);
			float custom_deposit_rate = 0;
			if(!StringUtils.isEmpty(paramMap.get("custom_deposit_rate"))){
				custom_deposit_rate = Float.parseFloat(paramMap.get("custom_deposit_rate").toString());
			}
			custom_deposit_rate = custom_deposit_rate / 100;
			paramMap.put("custom_deposit_rate", custom_deposit_rate);
			//品牌名称和代码都不能重复
			if(this.productBrandDao.queryBrandCountByNameOrCode(paramMap) > 0){
                pr.setState(false);
                pr.setMessage("品牌名称或代码已存在");
                return pr;
			}
			if(productBrandDao.insertProductBrand(paramMap) > 0){
				//添加商品品牌的分类
				List<Integer> classify_list = (List<Integer>)paramMap.get("classify_id");
				paramMap.put("brand_id",Integer.parseInt(paramMap.get("id").toString()));
				paramMap.put("classify_list",classify_list);
				this.productBrandDao.batchInsertBrandClassify(paramMap);
				pr.setState(true);
				pr.setMessage("添加品牌成功！");
				pr.setObj(paramMap);
			}

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
        }
        return pr;
	}
	/**
	 * 商品品牌编辑
	 * @param request
	 * @return
	 */
	@Transactional
	public ProcessResult editProductBrand(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失ID");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("logo"))) {
				pr.setState(false);
				pr.setMessage("参数缺失logo");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("trade_mark_license_img"))) {
				pr.setState(false);
				pr.setMessage("参数缺失trade_mark_license_img");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("insole_logo"))) {
				pr.setState(false);
				pr.setMessage("参数缺失insole_logo");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("member_service_rate"))) {
				pr.setState(false);
				pr.setMessage("参数缺失member_service_rate");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("classify_id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失classify_id");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("is_watermark"))) {
				pr.setState(false);
				pr.setMessage("参数缺失is_watermark");
				return pr;
			}
			ProcessResult watermarkPr = this.handleWatermarkSuffix(paramMap);
			if (watermarkPr != null) {
				return watermarkPr;
			}
			float member_service_rate = Float.parseFloat(paramMap.get("member_service_rate").toString());
			member_service_rate = member_service_rate / 100;
			float custom_deposit_rate = 0;
	    	if(!StringUtils.isEmpty(paramMap.get("custom_deposit_rate"))){
	    		custom_deposit_rate =  Float.parseFloat(paramMap.get("custom_deposit_rate").toString());
	    	}
			custom_deposit_rate = custom_deposit_rate / 100;
			paramMap.put("member_service_rate", member_service_rate);
			paramMap.put("custom_deposit_rate", custom_deposit_rate);
			Map<String, Object> brand = this.productBrandDao.queryById(Long.parseLong(paramMap.get("id").toString()));
			if (brand == null) {
				pr.setState(false);
				pr.setMessage("品牌不存在");
				return pr;
			}
            if(this.productBrandDao.queryBrandCountByNameOrCode(paramMap) > 0){
                pr.setState(false);
                pr.setMessage("品牌名称或者代码已存在");
                return pr;
            }

			if (productBrandDao.updateProductBrand(paramMap) > 0) {
				//添加商品品牌的分类
				paramMap.put("brand_id", Integer.parseInt(paramMap.get("id").toString()));
				this.productBrandDao.deleteBrandClassify(paramMap);
				paramMap.put("classify_list", (List<Integer>) paramMap.get("classify_id"));
				this.productBrandDao.batchInsertBrandClassify(paramMap);
				pr.setState(true);
				pr.setMessage("编辑品牌成功！");
				pr.setObj(paramMap);
			}

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 处理图片水印后缀
	 * @param paramMap
	 * @return
	 */
	private ProcessResult handleWatermarkSuffix(Map<String, Object> paramMap) {
		ProcessResult pr = new ProcessResult();
		try {
			int isWatermark = Integer.parseInt(paramMap.get("is_watermark").toString());
			if (isWatermark == 2) {
				if (!paramMap.containsKey("watermark_img_url") || StringUtils.isEmpty(paramMap.get("is_watermark"))) {
					pr.setState(false);
					pr.setMessage("参数缺失[watermark_img_url]");
					return pr;
				}
				String watermarkImgUrl = paramMap.get("watermark_img_url").toString();
				StringBuffer suffixSb = new StringBuffer(watermarkImgUrl.substring(watermarkImgUrl.indexOf("com/") + 4));
				suffixSb.append("?x-oss-process=image/resize,P_20");
				byte[] suffixByte = suffixSb.toString().getBytes("UTF-8");
				String watermarkSuffix = Base64.encode(suffixByte) + "";
				String newSuffix = watermarkSuffix.toString().replace("/", "_").replace("+", "-") + ",g_nw,x_0,y_0";
				paramMap.put("watermark_suffix", "watermark,image_" + newSuffix);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 商品品牌删除
	 * @param request
	 * @return
	 */
	public ProcessResult removeProductBrand(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
        	String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("参数缺失ID");
                return pr;
            }
            Map<String, Object> brand = this.productBrandDao.queryById(Long.parseLong(paramMap.get("id").toString()));
            if (brand == null) {
                pr.setState(false);
                pr.setMessage("品牌不存在");
                return pr;
            }
            if(productBrandDao.deleteProductBrand(paramMap) > 0){
                pr.setState(true);
                pr.setMessage("删除成功！");
                pr.setObj(paramMap);
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }

        return pr;
	}
	
	/**
	 * 商品品牌的排序
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public ProcessResult sortProductBrand(HttpServletRequest request) throws IOException {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String json = HttpUtil.getRequestInputStream(request);
		paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
		if (!paramMap.containsKey("id") || !paramMap.containsKey("toId")) {
			pr.setState(false);
			pr.setMessage("参数缺失");
			return pr;
		}
		if (swapOrderProductBrand(paramMap) == 1) {
			pr.setState(true);
			pr.setMessage("操作成功！");
		} else {
			pr.setState(false);
			pr.setMessage("操作失败！");
		}
		return pr;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public ProcessResult stateProductBrand(HttpServletRequest request) throws IOException {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失ID");
				return pr;
			}
			Map<String, Object> brand = this.productBrandDao.queryById(Long.parseLong(paramMap.get("id").toString()));
			if (brand == null) {
				pr.setState(false);
				pr.setMessage("品牌不存在");
				return pr;
			}
			if (productBrandDao.updateProductBrand(paramMap) > 0) {
				pr.setState(true);
				pr.setMessage("编辑品牌状态成功！");
				pr.setObj(paramMap);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return pr;
	}
	/**
	 * 两个 商品品牌 的排序值交换
	 * @param paramMap
	 * @return
	 */
	private int swapOrderProductBrand(Map<String,Object> paramMap){
		int returnInt = 0;
		long id = Long.parseLong(paramMap.get("id").toString());
		long toId = Long.parseLong(paramMap.get("toId").toString());
		Map<String, Object> list1 = productBrandDao.queryById(id);
		Map<String, Object> list2 = productBrandDao.queryById(toId);
		if (list1 != null && list2 != null) {
			String sort1 = list1.get("SORTID").toString();
			String sort2 = list2.get("SORTID").toString();
			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			Map<String, Object> paramMap2 = new HashMap<String, Object>();
			paramMap1.put("id", id);
			paramMap1.put("sortid", sort2);
			paramMap2.put("id", toId);
			paramMap2.put("sortid", sort1);
			if (productBrandDao.updateProductBrand(paramMap1) == 1 && productBrandDao.updateProductBrand(paramMap2) == 1) {
				returnInt = 1;
			} else {
				paramMap1.put("id", id);
				paramMap1.put("sortid", sort1);
				paramMap2.put("id", toId);
				paramMap2.put("sortid", sort2);
				productBrandDao.updateProductBrand(paramMap1);
				productBrandDao.updateProductBrand(paramMap2);
			}
		}
		return returnInt;
	}
	/**
	 * 查询品牌信息列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryProductBrandInfoList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		
		List<Map<String, Object>> dataList = productBrandDao.queryProductBrandInfoList();
		pr.setState(true);
		pr.setMessage("查询品牌信息成功");
		pr.setObj(dataList);

		return pr;
	}

	/**
	 * 验证品牌分类是否已被商品使用
	 * @param request
	 * @return
     */
	public ProcessResult checkProductBrand(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("classify_id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失[classify_id]");
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("brand_id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失[brand_id]");
				return pr;
			}
			//验证品牌是否存在
			Map<String, Object> brand = this.productBrandDao.queryById(Long.parseLong(paramMap.get("brand_id").toString()));
			if (brand == null) {
				pr.setState(false);
				pr.setMessage("品牌不存在");
				return pr;
			}
			//验证品牌分类是否被占用
			if(productBrandDao.queryProductCountByBrand(paramMap) > 0){
				pr.setState(false);
				pr.setMessage("当前分类下已存在此品牌商品,不允许再取消关联");
				return pr;
			}
			pr.setState(true);
			pr.setMessage("品牌分类可修改");

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return pr;
	}

	/**
	 * 修改商品是否展示的状态
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Transactional
	public Object showProductBrand(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("参数缺失");
				return pr;
			}
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			if (StringUtils.isEmpty(paramMap.get("id"))) {
				pr.setState(false);
				pr.setMessage("参数缺失ID");
				return pr;
			}
			Map<String, Object> brand = this.productBrandDao.queryById(Long.parseLong(paramMap.get("id").toString()));
			if (brand == null) {
				pr.setState(false);
				pr.setMessage("品牌不存在");
				return pr;
			}
			if (productBrandDao.updateProductBrand(paramMap) > 0) {
				//清除缓存
				Map<String, Object> cacheInfo=new HashMap<String,Object>();
				cacheInfo.put("key", "com.tk.service.ProductInfoService.queryProductBrand");
				Map<String, Object> cacheDetail=cacheInfoDao.queryCacheInfoDetail(cacheInfo);
				pr.setState(true);
				pr.setMessage("修改展示状态成功！");
				pr.setObj(cacheDetail);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return pr;
	}

	/**
	 * 查询已开通店铺的入驻商列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryOpenShopStationedList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			List<Map<String, Object>> list = this.productBrandDao.listOpenShopStationed();
			pr.setState(true);
			pr.setMessage("查询店铺成功");
			pr.setObj(list);

		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return pr;
	}
	
}
