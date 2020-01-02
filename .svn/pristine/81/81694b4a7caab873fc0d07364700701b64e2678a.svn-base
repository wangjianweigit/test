package com.tk.oms.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.product.dao.ProductDao;
import com.tk.oms.product.dao.ProductSameDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductSameService")
public class ProductSameService {
	
	@Resource
	private ProductSameDao productSameDao;
	
	
	/**
	 * 获取同款商品列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryProductSameList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
			if(pageParamResult!=null){
				return pageParamResult;
			}
			List<Map<String, Object>> resMap = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> lists = productSameDao.queryProductSameListByCondition(paramMap);
			for(Map<String, Object> list:lists){
				List<Map<String, Object>> childrenProducts=productSameDao.queryProductSameByParentItemnumber(list.get("ITEMNUMBER").toString());
				list.put("dataPid", childrenProducts);
				resMap.add(list);
			}
			int count = productSameDao.queryProductSameCountByCondition(paramMap);
			if (lists != null && lists.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(resMap);
			} else {
				gr.setMessage("无数据");
			}
			gr.setState(true);
			gr.setTotal(count);
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
		}
		return gr;
	}
	
	
	/**
	 * 同款货号编辑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productSameEdit(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumbers"))||StringUtils.isEmpty(paramMap.get("parentItemnumber"))
					||StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			//先删除
			int num = productSameDao.removeProductSame(paramMap);
			if(num>0){
				String parent_itemnumber=paramMap.get("parentItemnumber").toString();
				String itemnumbers=paramMap.get("itemnumbers").toString();
				Map<String, Object> sameProduct = new HashMap<String, Object>();
				sameProduct.put("itemnumber", parent_itemnumber);
				sameProduct.put("parent_itemnumber","0");
				if(productSameDao.insertProductSame(sameProduct)>0){
					sameProduct.put("itemnumber", itemnumbers);
					sameProduct.put("parent_itemnumber", parent_itemnumber);
					productSameDao.insertProductSame(sameProduct);
				}else{
					pr.setMessage("操作失败");
					pr.setState(false);
				}
			}
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	
	/**
	 * 同款货号新增
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productSameAdd(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))||StringUtils.isEmpty(paramMap.get("parentItemnumber"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			String parent_itemnumber=paramMap.get("parentItemnumber").toString();
			String itemnumber=paramMap.get("itemnumber").toString();
			Map<String, Object> sameProduct = new HashMap<String, Object>();
			sameProduct.put("itemnumber", parent_itemnumber);
			sameProduct.put("parent_itemnumber","0");
			int num = productSameDao.insertProductSame(sameProduct);
			if(num>0){
				sameProduct.put("itemnumber", itemnumber);
				sameProduct.put("parent_itemnumber", parent_itemnumber);
				productSameDao.insertProductSame(sameProduct);
			}else{
				pr.setMessage("操作失败");
				pr.setState(false);
			}
			pr.setMessage("操作成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 同款货号删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productSameRemove(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			int num = productSameDao.removeProductSame(paramMap);
			if(num <= 0){
				throw new RuntimeException("操作失败");
			}
			pr.setMessage("删除成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	
	/**
	 * 同款商品详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult productSameDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			List<Map<String,Object>> detail = productSameDao.queryProductSameDetail(paramMap);
			if(detail!=null){
				
				pr.setMessage("获取数据成功");
				pr.setObj(detail);
			} else {
				pr.setMessage("无数据");
			}
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	
	
	/**
	 * 同款商品添加条件判断(有相同的颜色和尺码不能设为同款)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult productSameJudge(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("itemnumber"))||StringUtils.isEmpty(paramMap.get("parentItemnumber"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			if (this.productSameDao.countJoinActivityProduct(paramMap) > 0) {
				pr.setState(false);
				pr.setMessage("存在商品正在参加订货会或清尾活动,不能设为同款");
				return pr;
			}
			int count=productSameDao.queryCountByProductSize(paramMap);
			if(count==0){
				pr.setState(true);
				pr.setMessage("设置同款条件满足");
			}else{
				pr.setState(false);
				pr.setMessage("商品存在相同颜色相同尺码,不能设为同款");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

}
