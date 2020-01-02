package com.tk.store.returns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.store.returns.dao.StoreReturnsDao;

@Service("StoreReturnsService")
public class StoreReturnsService {
	
	@Resource
	private StoreReturnsDao storeReturnsDao;
	private Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * 店铺退货单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryReturnList(HttpServletRequest request) {
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
			if((!StringUtils.isEmpty(paramMap.get("apply_state")))&&paramMap.get("apply_state") instanceof String){
				paramMap.put("apply_state",(paramMap.get("apply_state")+"").split(","));
			}
			//退货退款列表
			List<Map<String, Object>> list = storeReturnsDao.queryReturnInfoList(paramMap);
			//退货退款数量
			int count = storeReturnsDao.queryReturnInfoCount(paramMap);
			if (list != null && list.size() > 0) {
				gr.setMessage("获取数据成功");
				gr.setObj(list);
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
	 * 店铺退货单详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryReturnDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			//获取待退货退款详情基本信息
			Map<String,Object> detail = storeReturnsDao.queryReturnInfoDetail(paramMap);
			if(detail!=null){
				//获取退货退款商品详情信息
				//List<Map<String,Object>> productList = returnsDao.queryReturnInfoProductList(paramMap);
				paramMap.put("return_number", detail.get("RETURN_NUMBER"));
				//申请列表
				paramMap.put("type", "1");
				List<Map<String,Object>> applyReturnProduct=storeReturnsDao.queryCanReturn(paramMap);
				resultMap.put("apply_return_product", applyReturnProduct);
				//可退列表
				paramMap.put("type", "2");
				List<Map<String,Object>> canReturnProduct=storeReturnsDao.queryCanReturn(paramMap);
				resultMap.put("can_return_product", canReturnProduct);
				//不可退列表
				paramMap.put("type", "3");
				List<Map<String,Object>> canNotReturnProduct=storeReturnsDao.queryCanReturn(paramMap);
				resultMap.put("can_not_return_product", canNotReturnProduct);
				//查询货号凭证
				List<Map<String,Object>> itemnumber_images=storeReturnsDao.queryReturnImages(paramMap.get("return_number")+"");
				//查询物流凭证
				List<String> logistics_images=storeReturnsDao.queryLogisticsImages(paramMap.get("return_number")+"");
				//查询异常商品
				List<Map<String,Object>> unusual_product=storeReturnsDao.queryUnusualProduct(paramMap.get("return_number")+"");
				resultMap.put("base_info", detail);
				resultMap.put("itemnumber_images", itemnumber_images);
				resultMap.put("logistics_images", logistics_images);
				resultMap.put("unusual_product", unusual_product);
				pr.setMessage("获取数据成功");
				pr.setObj(resultMap);
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
	 * 店铺退货单合作商审批
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult auditReturn(HttpServletRequest request) throws Exception{
		ProcessResult pr = new ProcessResult();
		try {
			// 获取传入参数
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				pr.setState(false);
				pr.setMessage("缺少参数");
				return pr;
			}

			// 解析传入参数
			Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
			if (!paramMap.containsKey("return_number") || StringUtils.isEmpty(paramMap.get("return_number"))) {
				pr.setState(false);
				pr.setMessage("缺少参数return_number");
				return pr;
			}
			if (!paramMap.containsKey("apply_state") || StringUtils.isEmpty(paramMap.get("apply_state"))) {
				pr.setState(false);
				pr.setMessage("缺少参数apply_state");
				return pr;
			}
			
			Map<String, Object> returnOrder = storeReturnsDao.queryReturnInfoDetail(paramMap);
			if(returnOrder == null){
				pr.setState(false);
				pr.setMessage("退货单【"+paramMap.get("return_number").toString()+"】不存在");
				return pr;
			}
			if("2".equals(returnOrder.get("APPLY_STATE"))){
				pr.setState(false);
				pr.setMessage("该退货单已审批通过");
				return pr;
			}
			if("6".equals(returnOrder.get("APPLY_STATE")+"")){
				pr.setState(false);
				pr.setMessage("该退货单已驳回");
				return pr;
			}
			if("7".equals(returnOrder.get("APPLY_STATE")+"")){
				pr.setState(false);
				pr.setMessage("该退货单已撤销");
				return pr;
			}
			if("10".equals(returnOrder.get("APPLY_STATE")+"")){
				pr.setState(false);
				pr.setMessage("该退货单已审批驳回");
				return pr;
			}
			if(!"9".equals(returnOrder.get("APPLY_STATE")+"")){
				pr.setState(false);
				pr.setMessage("该退货单状态异常");
				return pr;
			}

			if(storeReturnsDao.auditReturnInfo(paramMap) >0){
				pr.setState(true);
				pr.setMessage("操作成功");
			}else{
				pr.setState(false);
				pr.setMessage("操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
}