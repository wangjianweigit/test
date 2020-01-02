package com.tk.oms.marketing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.marketing.dao.RecommendedDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
/**
 * 
 * Copyright (c), 2017, Tongku
 * FileName : RecommendedService
 * 为你推荐管理
 *
 * @author yejingquan
 * @version 1.00
 * @date 2017-4-12
 */
@Service("RecommendedService")
public class RecommendedService {
private Log logger = LogFactory.getLog(this.getClass());
	
	@Resource
	private RecommendedDao recommendedDao;
	/**
	 * 查询推荐列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryRecommendedList(HttpServletRequest request) {
		GridResult gr = new GridResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        	PageUtil.handlePageParams(paramMap);
	        }
			if(paramMap.size() == 0) {
            	gr.setState(false);
            	gr.setMessage("参数缺失");
                return gr;
            }
			if((!StringUtils.isEmpty(paramMap.get("state")))&&paramMap.get("state") instanceof String){
				paramMap.put("state",(paramMap.get("state")+"").split(","));
			}
			//查询为你推荐总数
			int total = recommendedDao.queryRecommendedCount(paramMap);
			//查询为你推荐列表
			List<Map<String, Object>> dataList = recommendedDao.queryRecommendedList(paramMap);
			
			if (dataList != null && dataList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(dataList);
				gr.setTotal(total);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
			
		} catch (Exception e) {
			gr.setState(false);
			gr.setMessage(e.getMessage());
			logger.error("查询失败："+e.getMessage());
		}
		return gr;
	}
	/**
	 * 新增推荐信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addRecommended(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);
	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
                return pr;
            }
			String product_itemnumbers = paramMap.get("product_itemnumbers")==""?"":paramMap.get("product_itemnumbers").toString();
			String[] product_itemnumber = null;
			if(!StringUtils.isEmpty(product_itemnumbers)){
				product_itemnumber = product_itemnumbers.split(",");
			}
			if(recommendedDao.checkRecommendedName(paramMap)<=0){
				paramMap.put("state", "2");
				if (recommendedDao.insert(paramMap) > 0) {
					List<Map<String, Object>> pList = new ArrayList<Map<String, Object>>();
					if(product_itemnumber!=null && product_itemnumber.length >0){
						for(int i=0;i<product_itemnumber.length;i++){
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("product_itemnumber", product_itemnumber[i]);
							map.put("recommended_id", paramMap.get("id"));
							map.put("sort",(i+1));
							pList.add(map);
						}
						if(recommendedDao.batchInsertRecommendedProduct(pList)<1){
							throw new RuntimeException("新增失败");
						}
					}
					pr.setState(true);
					pr.setMessage("新增成功");
				} else {
					pr.setState(false);
					pr.setMessage("新增推荐失败");
				}
			}else{
				pr.setState(false);
				pr.setMessage("推荐名称已存在，请修改");
			}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editRecommended(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
                return pr;
            }
			String product_itemnumbers = paramMap.get("product_itemnumbers")==""?"":paramMap.get("product_itemnumbers").toString();
			String[] product_itemnumber = null;
			if(!StringUtils.isEmpty(product_itemnumbers)){
				product_itemnumber = product_itemnumbers.split(",");
			}
			if (recommendedDao.delete(paramMap) > 0) {
					recommendedDao.deleteRecommendedProductByRid(paramMap);
					paramMap.put("name", paramMap.get("NAME"));
					paramMap.put("type", paramMap.get("TYPE"));
					paramMap.put("start_date", paramMap.get("START_DATE"));
					paramMap.put("remark", paramMap.get("REMARK"));
					paramMap.put("state", paramMap.get("STATE"));
					paramMap.put("create_date", paramMap.get("CREATE_DATE"));
					if (recommendedDao.insertNew(paramMap) > 0) {
						List<Map<String, Object>> pList = new ArrayList<Map<String, Object>>();
						if(product_itemnumber!=null && product_itemnumber.length >0){
							for(int i=0;i<product_itemnumber.length;i++){
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("product_itemnumber", product_itemnumber[i]);
								map.put("recommended_id", paramMap.get("id"));
								map.put("sort",(i+1));
								pList.add(map);
							}
							if(recommendedDao.batchInsertRecommendedProduct(pList)<1){
								throw new RuntimeException("修改推荐失败");
							}
						}
						pr.setState(true);
						pr.setMessage("修改推荐成功");
					} else {
						pr.setState(false);
						pr.setMessage("修改推荐失败");
						pr.setObj(paramMap);
					}
			}else{
				pr.setState(false);
				pr.setMessage("修改推荐失败");
				pr.setObj(paramMap);
			}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	
	/**
	 * 启用禁用推荐商品
	 * @param request
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public ProcessResult updateRecommendState(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
                return pr;
            }
			if (recommendedDao.updateState(paramMap) > 0) {
				pr.setState(true);
				pr.setMessage("更新状态成功");
				pr.setObj(paramMap);
			}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	/**
	 * 删除推荐信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeRecommended(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);
	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
                return pr;
            }
			if (recommendedDao.delete(paramMap) < 1) {
				throw new RuntimeException("删除失败");
			}
			recommendedDao.deleteRecommendedProductByRid(paramMap);
			pr.setState(true);
			pr.setMessage("删除成功");
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return pr;
	}
	/**
	 * 查询推荐详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult detailRecommended(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);
	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
                return pr;
            }
			resMap = recommendedDao.queryRecommendDetailById(paramMap);
			List<Map<String, Object>> product_itemnumbers=recommendedDao.queryProductItemnumbers(paramMap);
			if(product_itemnumbers!=null && product_itemnumbers.size()>0){
				resMap.put("product_itemnumbers", product_itemnumbers);
			}
			pr.setState(true);
			pr.setMessage("获取详情成功");
			pr.setObj(resMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 查询推荐的商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryRecommendedProductList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
			if(paramMap.size() == 0) {
				pr.setState(false);
				pr.setMessage("参数缺失");
                return pr;
            }
			List<Map<String, Object>> pList= recommendedDao.queryProductListByRId(paramMap);
			pr.setState(true);
			pr.setMessage("获取商品列表成功");
			pr.setObj(pList);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 批量更新推荐商品表
	 * @return
	 */
	@Transactional
	private int updateRecommendedProduct(Map<String, Object> paramMap) throws Exception{
		int result = 0;
		ProcessResult pr = new ProcessResult();
		String product_itemnumbers = paramMap.get("product_itemnumbers")==null?null:paramMap.get("product_itemnumbers").toString();
		String[] product_itemnumber = null;
		if(!StringUtils.isEmpty(product_itemnumbers)){
			product_itemnumber = product_itemnumbers.split(",");
		}
		//先删除
		try {
			result = recommendedDao.deleteRecommendedProductByRid(paramMap);
			//再插入
			List<Map<String, Object>> pList = new ArrayList<Map<String, Object>>();
			if(product_itemnumber!=null && product_itemnumber.length >0){
				for(int i=0,l=product_itemnumber.length;i<l;i++){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("product_itemnumber", product_itemnumber[i]);
					map.put("recommended_id", paramMap.get("id"));
					map.put("sort",(i+1));
					pList.add(map);
				}
				result = recommendedDao.batchInsertRecommendedProduct(pList);
			}
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
		return result;
	}

}
