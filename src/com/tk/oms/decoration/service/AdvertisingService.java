package com.tk.oms.decoration.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.decoration.dao.AdvertisingDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("AdvertisingService")
public class AdvertisingService {
	
	@Resource
	private AdvertisingDao advertisingDao;
	
	
	
	 /**
     * 获取广告位列表
     * @param request
     * @return
     */
    public GridResult getInfoList(HttpServletRequest request) {
    	GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> info = null;
            if(!StringUtils.isEmpty(json)) {
                info = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            }
            int advertising_type=Integer.parseInt(info.get("advertising_type")+"");
            if(advertising_type==2){
            	info.put("public_user_site_id", 0);
            }
            List<Map<String,Object>> list = advertisingDao.queryList(info);
            if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}

        } catch (IOException e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 获取广告列表
     * @param request
     * @return
     */
    public GridResult queryDetailList(HttpServletRequest request) {
    	GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> dvertisingDetail = null;
            if(!StringUtils.isEmpty(json)) {
                dvertisingDetail = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            }
            int advertising_type=Integer.parseInt(dvertisingDetail.get("advertising_type")+"");
            if(advertising_type==2){
            	dvertisingDetail.put("public_user_site_id", 0);
            }
            List<Map<String,Object>> list = advertisingDao.queryDetail(dvertisingDetail);
            if (list != null && list.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(list);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        } catch (IOException e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }
        return gr;
    }
    
    /**
     * 获取广告位详情
     * @param request
     * @return
     */
    public ProcessResult queryDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> dvertisingDetail = null;
            if(!StringUtils.isEmpty(json)) {
                dvertisingDetail = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            }
            Map<String,Object> list= advertisingDao.queryAdvertisingDetail(dvertisingDetail);
            if(list != null) {
                pr.setState(true);
                pr.setMessage("获取广告位详情成功");
                pr.setObj(list);
            } else {
                pr.setState(false);
                pr.setMessage("获取广告位详情失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
    /**
     * 获取广告详情
     * @param request
     * @return
     */
    public ProcessResult queryDetails(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            Map<String,Object> dvertisingDetail = null;
            if(!StringUtils.isEmpty(json)) {
                dvertisingDetail = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            }
            Map<String,Object> list= advertisingDao.queryDetailById(dvertisingDetail);
            if(list != null) {
                pr.setState(true);
                pr.setMessage("获取广告详情成功");
                pr.setObj(list);
            } else {
                pr.setState(false);
                pr.setMessage("获取广告详情失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 新增广告位
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addDvertisingInfo(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String,Object> info=new HashMap<String,Object>();
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否有参数传入
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

             info = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(info.get("name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数name");
                return pr;
            }
            if(StringUtils.isEmpty(info.get("type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数type");
                return pr;
            }

            if(advertisingDao.insertAdvertising(info) > 0) {
                pr.setState(true);
                pr.setMessage("新增广告位成功");
                pr.setObj(info);
            } else {
                pr.setState(false);
                pr.setMessage("新增广告位失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 修改广告位
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editDvertisingInfo(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String,Object> info=new HashMap<String,Object>();
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否有参数传入
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            info = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(info.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if(StringUtils.isEmpty(info.get("name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数name");
                return pr;
            }
            if(StringUtils.isEmpty(info.get("type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数type");
                return pr;
            }

            if(advertisingDao.updateAdvertising(info) > 0) {
                pr.setState(true);
                pr.setMessage("修改广告位成功");
                pr.setObj(info);
            } else {
                pr.setState(false);
                pr.setMessage("修改广告位失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 删除广告位
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult deleteDvertisingInfo(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
        	Map<String,Object> info=new HashMap<String,Object>();
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否有参数传入
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            info = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(info.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if(advertisingDao.removeAdvertising(info) > 0) {
            	advertisingDao.removeAdvertisingDetail(info);
        		pr.setState(true);
                pr.setMessage("删除广告位成功");
                pr.setObj(info);
            } else {
                pr.setState(false);
                pr.setMessage("删除广告位失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 新增广告
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addDvertisingDetail(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> detail=new HashMap<String,Object>();
            detail = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if(Integer.parseInt(detail.get("dvertising_id").toString()) == 0) {
                pr.setState(false);
                pr.setMessage("缺少参数dvertising_id");
                return pr;
            }
            if(StringUtils.isEmpty(detail.get("apply_start_date"))) {
                pr.setState(false);
                pr.setMessage("缺少参数apply_start_date");
                return pr;
            }
            if(StringUtils.isEmpty(detail.get("apply_end_date"))) {
                pr.setState(false);
                pr.setMessage("缺少参数apply_end_date");
                return pr;
            }
            
            int advertising_type=Integer.parseInt(detail.get("advertising_type")+"");
            if(advertising_type==2){
            	detail.put("public_user_site_id", 0);
            }
//            if(detail.get("type").equals("1")){
//            	Map<String,Object> param=advertisingDao.queryDetailByDvertisingId(detail);
//            	if(null==param){
//            		advertisingDao.insertAdvertisingDetail(detail);
//            		pr.setState(true);
//                    pr.setMessage("新增广告成功");
//                    return pr;
//            	}else{
//            		detail.put("id",param.get("ID"));
//                	advertisingDao.updateAdvertisingDetail(detail);
//                	pr.setState(true);
//                    pr.setMessage("修改广告成功");
//                    return pr;
//            	}
//            }else 
            if(advertisingDao.insertAdvertisingDetail(detail) > 0) {
                pr.setState(true);
                pr.setMessage("新增广告成功");
            } else {
                pr.setState(false);
                pr.setMessage("新增广告失败");
                throw new RuntimeException("新增广告失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 排序广告
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult sortDetail(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> sortMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //校验name参数
            if(StringUtils.isEmpty(sortMap.get("id1"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id1");
                return pr;
            }
            //校验广告内容地址
            if(StringUtils.isEmpty(sortMap.get("id2"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id2");
                return pr;
            }
            Map<String, Object> fromDetail = new HashMap<String, Object>();
            fromDetail.put("id",sortMap.get("id1"));
            fromDetail = advertisingDao.queryDetailById(fromDetail);
            Map<String, Object> toDetail = new HashMap<String, Object>();
            toDetail.put("id",sortMap.get("id2"));
            toDetail = advertisingDao.queryDetailById(toDetail);

           // int toOrderId = (Integer)toDetail.get("order_id");
            toDetail.put("order_id",fromDetail.get("ORDER_ID"));
            fromDetail.put("order_id",toDetail.get("ORDER_ID"));
            toDetail.put("id",toDetail.get("ID"));
            fromDetail.put("id",fromDetail.get("ID"));
            fromDetail.put("advertising_type", sortMap.get("advertising_type"));
            toDetail.put("advertising_type", sortMap.get("advertising_type"));
            if(Integer.parseInt(sortMap.get("advertising_type")+"")==1){
            	fromDetail.put("public_user_site_id", sortMap.get("public_user_site_id"));
                toDetail.put("public_user_site_id", sortMap.get("public_user_site_id"));
            }else if(Integer.parseInt(sortMap.get("advertising_type")+"")==2){
            	fromDetail.put("public_user_site_id", 0);
                toDetail.put("public_user_site_id", 0);
            }
            if(advertisingDao.updateAdvertisingDetail(fromDetail) > 0 && advertisingDao.updateAdvertisingDetail(toDetail) > 0) {
                pr.setState(true);
                pr.setMessage("排序广告成功");
            } else {
                pr.setState(false);
                pr.setMessage("排序广告失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 修改广告
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editDvertisingDetail(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> detail=new HashMap<String,Object>();
            detail = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if(Integer.parseInt(detail.get("id").toString()) == 0) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            int advertising_type=Integer.parseInt(detail.get("advertising_type")+"");
            if(advertising_type==2){
            	detail.put("public_user_site_id", 0);
            }
            if(advertisingDao.updateAdvertisingDetail(detail) > 0) {
                pr.setState(true);
                pr.setMessage("修改广告成功");
            } else {
                pr.setState(false);
                pr.setMessage("修改广告失败");
                throw new RuntimeException("修改广告失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

    /**
     * 删除广告
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult deleteDetail(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> detail=new HashMap<String,Object>();
            detail = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            //校验name参数
            if(StringUtils.isEmpty(detail.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if(advertisingDao.removeAdvertisingDetail(detail) > 0) {
                pr.setState(true);
                pr.setMessage("删除广告成功");
            } else {
                pr.setState(false);
                pr.setMessage("删除广告失败");
                throw new RuntimeException("删除广告失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e);
        }
        return pr;
    }

}
