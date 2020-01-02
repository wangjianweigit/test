package com.tk.oms.basicinfo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.basicinfo.dao.AfterSaleAddressDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.ResponseSateEnum;
import com.tk.sys.util.Transform;

@Service("AfterSaleAddressService")
public class AfterSaleAddressService {
	
	@Resource
	private AfterSaleAddressDao afterSaleAddressDao;
	
	/**
	 * 获取订单列表
	 */
	@SuppressWarnings("unchecked")
	public GridResult queryAfterSaleAddressList(HttpServletRequest request) {
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
			List<Map<String, Object>> list = afterSaleAddressDao.queryAfterSaleAddressList(paramMap);
			int count = afterSaleAddressDao.queryAfterSaleAddressCount(paramMap);
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
	 * 获取售后地址详情
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult afterSaleAddressDetail(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			//获取待订单详情基本信息
			Map<String,Object> detail = afterSaleAddressDao.queryAfterSaleAddressDetail(paramMap);
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
	 * 新增售后地址
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addAfterSaleAddress(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}

			int count=afterSaleAddressDao.addAfterSaleAddress(paramMap);
			if(count>0){
				pr.setMessage("新增数据成功");
				pr.setState(true);
			}else{
				pr.setMessage("新增数据失败");
				pr.setState(false);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	
	/**
	 * 编辑售后地址
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editAfterSaleAddress(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			int count=afterSaleAddressDao.editAfterSaleAddress(paramMap);
			if(count>0){
				pr.setMessage("编辑数据成功");
				pr.setState(true);
			}else{
				pr.setMessage("编辑数据失败");
				pr.setState(false);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 售后地址商家授权
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public ProcessResult addOrEditStationedAfterSaleAddress(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("after_sale_address_id"))) {
				pr.setMessage("缺少参数【after_sale_address_id】");
				pr.setState(false);
				return pr;
			}
			if (StringUtils.isEmpty(paramMap.get("type"))) {
				pr.setMessage("缺少参数【type】");
				pr.setState(false);
				return pr;
			}
			int type = Integer.parseInt(paramMap.get("type").toString());
			if(type == 1){
				int count = afterSaleAddressDao.deleteStationedAfterAddress(paramMap);
				if (count <= 0) {
					throw new RuntimeException("售后地址商家授权失败");
				}
			}else{
				if (StringUtils.isEmpty(paramMap.get("stationed_user_id"))) {
					pr.setMessage("缺少参数【stationed_user_id】");
					pr.setState(false);
					return pr;
				}
				int count1 = this.afterSaleAddressDao.insertStationedAfterAddress(paramMap);
				if (count1 <= 0) {
					throw new RuntimeException("售后地址商家授权失败");
				}
			}
			pr.setMessage("售后地址商家授权成功");
			pr.setState(true);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}

	/**
	 * 查询售后地址关联商家列表
	 * @param request
	 * @return
	 */
	public ProcessResult queryAfterSaleAddressStationedList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (!StringUtils.isEmpty(json)) {
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if (StringUtils.isEmpty(paramMap.get("after_sale_address_id"))) {
				pr.setMessage("缺少参数【after_sale_address_id】");
				pr.setState(false);
				return pr;
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			/**
			 * 已选商家
			 */
			paramMap.put("select_type", 1);
			List<Map<String, Object>> stationedList = this.afterSaleAddressDao.listStationedUser(paramMap);
			resultMap.put("stationed_list", stationedList);
			/**
			 * 未选商家
			 */
			paramMap.put("select_type", 2);
			List<Map<String, Object>> noStationedList = this.afterSaleAddressDao.listStationedUser(paramMap);
			resultMap.put("no_stationed_list", noStationedList);

			pr.setMessage("查询售后地址关联商家列表成功");
			pr.setState(true);
			pr.setObj(resultMap);
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

	/**
	 * 删除售后地址
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult removeAfterSaleAddress(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setMessage("缺少参数");
				pr.setState(false);
				return pr;
			}
			int count=afterSaleAddressDao.removeAfterSaleAddress(paramMap);
			if(count>0){
				pr.setMessage("删除数据成功");
				pr.setState(true);
			}else{
				pr.setMessage("删除数据失败");
				pr.setState(false);
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return pr;
	}
	
	/**
	 * 启用禁用售后地址
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult editAfterSaleAddressState(HttpServletRequest request) throws Exception {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if(!StringUtils.isEmpty(json)){
				paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
			}
			if(StringUtils.isEmpty(paramMap.get("id"))){
				pr.setMessage("缺少参数id");
				pr.setState(false);
				return pr;
			}
			if(StringUtils.isEmpty(paramMap.get("state"))){
				pr.setMessage("缺少参数state");
				pr.setState(false);
				return pr;
			}
			int count=afterSaleAddressDao.editAfterSaleAddressState(paramMap);
			if(count>0){
				pr.setMessage("更新数据成功");
				pr.setState(true);
			}else{
				pr.setMessage("更新数据失败");
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
