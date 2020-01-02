package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.AreaProductControlSetDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.oms.sysuser.entity.SysUserInfo;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright (c), 2017, Tongku
 * FileName :AreaProductControlSetService
 * 区域控货配置业务处理类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/11 17:49
 */
@Service("AreaProductControlService")
public class AreaProductControlSetService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private AreaProductControlSetDao areaProductControlSetDao;//区域控货配置业务处理类
    @Resource
    private SysUserInfoDao sysUserInfoDao;//系统用户DAO类
    /**
     * 分页查询控货类型列表数据
     * @param request
     * @return
     */
    public GridResult queryTypeList(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //获取分页记录数
            int total = areaProductControlSetDao.queryTypeCountForPage(paramMap);
            //获取分页列表记录
            List<Map<String, Object>> list = areaProductControlSetDao.queryTypeListForPage(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取控货类型列表数据成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return gr;
    }

    /**
     * 获取控货类型数据信息
     * @param request
     * @return
     */
    public ProcessResult queryTypeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数ID");
                return pr;
            }
            Map<String, Object> result = this.areaProductControlSetDao.queryTypeInfoById(Long.parseLong(paramMap.get("id").toString()));
            if(result == null){
                pr.setState(false);
                pr.setMessage("控货类型数据不存在");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("获取控货类型数据成功");
            pr.setObj(result);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑控货类型数据信息
     * @param request
     * @return
     */
    public ProcessResult editTypeInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数ID");
                return pr;
            }
            //控货类型id
            long id = Long.parseLong(paramMap.get("id").toString());
            Map<String, Object> result = this.areaProductControlSetDao.queryTypeInfoById(id);
            if(result == null){
                pr.setState(false);
                pr.setMessage("控货类型数据不存在");
                return pr;
            }
            if (this.areaProductControlSetDao.updateTypeInfo(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("修改成功");
            }else{
                pr.setState(true);
                pr.setMessage("修改失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 分页获取区域控货配置列表（业务员，业务经理）
     * @param request
     * @return
     */
    public GridResult queryAreaControlSetPageForBusiness(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //获取分页记录数
            int total = areaProductControlSetDao.queryAreaControlSetCountForBusiness(paramMap);
            //获取分页列表记录
            List<Map<String, Object>> list = areaProductControlSetDao.queryAreaControlSetListForBusiness(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取区域控货配置列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return gr;
    }

    /**
     * 获取区域控货配置区域列表（业务员，业务经理）
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public GridResult queryAreaListForBusiness(HttpServletRequest request) {
        GridResult pr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数USER_ID");
                return pr;
            }
            /**
             * songwangwen 20170726
             * 查询当前用户是否是【业务经理】或者【业务员】
             * 如果是业务经理：可以看到全部的省市县信息，
             * 如果是业务员：可以看到所属的业务经理配置的省市县信息
             * */
            List<Map<String, Object>> areaList = new ArrayList<Map<String,Object>>();
            SysUserInfo sysUser = sysUserInfoDao.queryByUserId(Long.parseLong(paramMap.get("user_id").toString()));
            if(sysUser.getUser_type() == 4){ //4、业务经理
            	areaList = this.areaProductControlSetDao.queryAreaListForBusiness(paramMap);
            }else if(sysUser.getUser_type() == 3){//3、业务员
            	//根据所属的业经理返货可以控货区域的显示
            	areaList = this.areaProductControlSetDao.queryAreaListForBusinessByManager(paramMap);
            	if(areaList==null || areaList.isEmpty() || areaList.size()==0){
            		pr.setState(false);
                    pr.setMessage("当前业务员未配置业务经理或者所属业务经理还未配置控货区域");
                    return pr;
            	}
            }else{
            	 pr.setState(false);
                 pr.setMessage("用户类型错误，只有业务员或者业务经理可以配置控货区域");
                 return pr;
            }
            pr.setTotal(sysUser.getUser_type());
            pr.setState(true);
            pr.setMessage("获取配置区域列表成功");
            pr.setObj(areaList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加业务员、业务经理区域控货配置
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
    public ProcessResult addAreaControlSetForBusiness(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数USER_ID");
                return pr;
            }
            long user_id = Long.parseLong(paramMap.get("user_id").toString());
            List<String> new_list = (ArrayList<String>) paramMap.get("city_list");
            SysUserInfo sysUser = sysUserInfoDao.queryByUserId(user_id);
            //如果是业务经理，需要判断取消的哪些城市，是否已经被下属的门店或者业务员使用，如果是，则不允许取消
            if(sysUser.getUser_type() == 4){ //4、业务经理
            	//查询当前的控货区域列表
            	List<String> old_list = areaProductControlSetDao.queryAreaControlSetForBusiness(user_id);
            	//比较新旧城市列表，找出被取消的城市信息
                if (!StringUtils.isEmpty(old_list) && old_list.size()>0) {
                	List<String> cancle_list = new ArrayList<String>();
                	if (!StringUtils.isEmpty(new_list) && new_list.size()>0) {
                		for(String old_city_id:old_list){
                			if(!new_list.contains(old_city_id)){//存在旧的列表中，不存在新的列表中，表示被取消控货
                				cancle_list.add(old_city_id);
                			}
                		}
                	}else{
                		cancle_list = old_list;
                	}
                	if(!cancle_list.isEmpty()&&cancle_list.size()>0){
                		//查询是否可以取消
                		paramMap.put("cancle_list", cancle_list);
                		int num = areaProductControlSetDao.queryIsUserByManager(paramMap);
                		if(num>0){
                			pr.setState(false);
                			pr.setMessage("配置失败！被取消控货的城市中，已经被当前业务经理下属的门店或者业务员控货。");
                			return pr;
                		}
                	}
                }
            }
            this.areaProductControlSetDao.deleteAreaControlSetForBusiness(paramMap);
            if (!StringUtils.isEmpty(new_list) && new_list.size()>0) {
                if (this.areaProductControlSetDao.insertAreaControlSetForBusiness(paramMap) <= 0) {
                	throw new Exception("配置失败");
                }
            }

        } catch (Exception e) {
            pr.setState(false);
            throw new RuntimeException(e.getMessage());
        }
        pr.setState(true);
        pr.setMessage("配置成功");
        return pr;
    }

    /**
     * 分页获取区域控货配置列表（门店）
     * @param request
     * @return
     */
    public GridResult queryAreaControlSetPageForStore(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            //获取分页记录数
            int total = areaProductControlSetDao.queryAreaControlSetCountForStore(paramMap);
            //获取分页列表记录
            List<Map<String, Object>> list = areaProductControlSetDao.queryAreaControlSetListForStore(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取区域控货配置列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return gr;
    }

    /**
     * 获取区域控货配置区域列表（门店）
     * @param request
     * @return
     */
    public ProcessResult queryAreaListForStore(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数STORE_ID");
                return pr;
            }
            List<Map<String, Object>> areaList = this.areaProductControlSetDao.queryAreaListForStore(paramMap);
            if(areaList==null || areaList.isEmpty() || areaList.size()==0){
        		pr.setState(false);
                pr.setMessage("当前门店未配置业务经理或者所属业务经理还未配置控货区域");
                return pr;
        	}
            pr.setState(true);
            pr.setMessage("获取配置区域列表成功");
            pr.setObj(areaList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加门店区域控货配置
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addAreaControlSetForStore(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("store_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数STORE_ID");
                return pr;
            }
            this.areaProductControlSetDao.deleteAreaControlSetForStore(paramMap);
            List<?> list = (ArrayList<?>) paramMap.get("city_list");
            if (!StringUtils.isEmpty(list) && list.size()>0) {
                if (this.areaProductControlSetDao.insertAreaControlSetForStore(paramMap) <= 0) {
                	throw new Exception("配置失败");
                }
            }

        } catch (Exception e) {
            pr.setState(false);
            throw new RuntimeException(e.getMessage());
        }
        pr.setState(true);
        pr.setMessage("配置成功");
        return pr;
    }
    /**
     * 控货类型(下拉框)
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult comboBoxList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {

            List<Map<String, Object>> areaList = this.areaProductControlSetDao.queryProductControlTypeComboBoxList();
            pr.setState(true);
            pr.setMessage("查询控货类型成功");
            pr.setObj(areaList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
	}
	/**
	 * 品牌控货类型说明
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult brandExplain(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (paramMap.size() == 0) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> retMap = this.areaProductControlSetDao.queryBrandExplain(paramMap);
            pr.setState(true);
            pr.setMessage("查询品牌控货类型说明成功");
            pr.setObj(retMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
	}

    /**
     * 获取品牌控货说明信息
     * @param request
     * @return
     */
    public ProcessResult queryBrandExplainInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("brand_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[brand_id]品牌编号");
                return pr;
            }
            //所有控货类型
            List<Map<String, Object>> typeList = this.areaProductControlSetDao.queryTypeList();
            //品牌已设置的说明
            List<Map<String, Object>> explainsList = this.areaProductControlSetDao.queryExplainListByBrandId(Long.parseLong(paramMap.get("brand_id").toString()));
            //品牌控货类型已设置的说明
            for(Map<String, Object> type : typeList){
                for (Map<String, Object> explains :explainsList){
                    if(type.get("TYPE_ID").toString().equals(explains.get("TYPE_ID").toString())){
                        type.put("CONTEXT", explains.get("CONTEXT").toString());
                        type.put("EXPLAINS_ID", explains.get("ID"));
                        break;
                    }
                }

            }
            if (typeList!= null && typeList.size() > 0) {
                pr.setState(true);
                pr.setMessage("获取成功");
                pr.setObj(typeList);
            }else{
                pr.setState(false);
                pr.setMessage("获取失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑品牌控货说明
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editBrandExplainInfo(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("setData"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[setData]设置说明数据");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("brand_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[brand_id]品牌编号");
                return pr;
            }
            List<Map<String, Object>> setData = (List<Map<String, Object>>)paramMap.get("setData");
            for(Map<String, Object> map: setData){
                if (StringUtils.isEmpty(map.get("type_id"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[type_id]类型编号");
                    return pr;
                }
                if (StringUtils.isEmpty(map.get("context"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[context]说明内容");
                    return pr;
                }
            }

            /****添加品牌控货说明 先删除 后插入****/
            long brand_id = Long.parseLong(paramMap.get("brand_id").toString());
            int queryCount = areaProductControlSetDao.queryExplainCountByBrandId(brand_id);     //查询品牌说明记录数
            int deletCount = areaProductControlSetDao.deleteExplainByBrandId(brand_id);         //删除品牌说明配置
            if(deletCount == queryCount){
                int insertCount = areaProductControlSetDao.insertBrandExplainByBatch(paramMap); //添加品牌控货信息
                if(insertCount >0 && insertCount == setData.size()){
                    pr.setState(true);
                    pr.setMessage("编辑成功");
                }else{
                    throw new RuntimeException("编辑失败");
                }
            }else{
                throw new RuntimeException("编辑失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    /**
     * 查询一个业务经理配置的某个区域控货城市，是否被赋值给其下属的业务员或者业务经理使用
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult queryIsUserByCistIdAndManager(HttpServletRequest request) throws Exception{
    	ProcessResult pr = new ProcessResult();
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if (StringUtils.isEmpty(json)) {
    			pr.setState(false);
    			pr.setMessage("缺少参数");
    			return pr;
    		}
    		Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
    		if (StringUtils.isEmpty(paramMap.get("user_id"))) {
    			pr.setState(false);
    			pr.setMessage("缺少参数[user_id]");
    			return pr;
    		}
    		if (StringUtils.isEmpty(paramMap.get("city_id"))) {
    			pr.setState(false);
    			pr.setMessage("缺少参数[city_id]");
    			return pr;
    		}
    		int queryCount = areaProductControlSetDao.queryIsUserByCistIdAndManager(paramMap);     //查询品牌说明记录数
    		if(queryCount==0){
    			pr.setState(true);
    			pr.setMessage("可以取消该控货城市");
    			pr.setObj(queryCount);
    		}else{
    			pr.setState(false);
    			pr.setMessage("已被业务经理下属的门店或者业务员进行控货，不允许取消控货");
    			pr.setObj(queryCount);
    		}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		throw new RuntimeException(e.getMessage());
    	}
    	return pr;
    }
}
