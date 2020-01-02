package com.tk.store.member.service;

import com.tk.sys.config.EsbConfig;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : StoreMemberLevelService
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/8/1
 */
@Service("StoreMemberLevelService")
public class StoreMemberLevelService {

    @Value("${store_service_url}")
    private String store_service_url;

    @Value("${member_level_list}")
    private String member_level_list;

    @Value("${member_level_set}")
    private String member_level_set;

    @Value("${member_level_add}")
    private String member_level_add;

    @Value("${member_level_del}")
    private String member_level_del;

    @Value("${member_level_set_user_level}")
    private String member_level_set_user_level;


    /**
     * 会员等级列表
     * @param request
     * @return
     */
    public ProcessResult queryList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            param.put("COOPER_ID", paramMap.get("public_user_id"));
            if(!StringUtils.isEmpty(paramMap.get("region_id"))){
            	param.put("COOPER_ID", paramMap.get("region_id"));
            }
            Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url + member_level_list);
            if(!StringUtils.isEmpty(retMap.get("data"))){
                pr.setState(true);
                pr.setMessage("获取会员等级列表成功");
                pr.setObj(retMap.get("data"));
            }else{
                pr.setState(false);
                pr.setMessage("获取会员等级列表失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑会员等级
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public ProcessResult edit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> retMap = new HashMap<String, Object>();
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
            param.put("COOPER_ID", paramMap.get("public_user_id"));
            param.put("NAME", paramMap.get("name"));
            param.put("DISCOUNT", paramMap.get("discount"));
            param.put("INTRO", paramMap.get("intro"));

            if(StringUtils.isEmpty(paramMap.get("id"))){
                //1.新增
                retMap= (Map<String, Object>) queryForPost(param,store_service_url + member_level_add);
            }else{
                //2.更新
                param.put("ID", paramMap.get("id"));
                retMap= (Map<String, Object>) queryForPost(param,store_service_url + member_level_set);
            }
            if (Integer.parseInt(retMap.get("state").toString()) != 1) {
                throw new RuntimeException(retMap.get("message")+"");
            }
            pr.setState(true);
            pr.setMessage("保存成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除会员等级
     * @param request
     * @return
     */
    public ProcessResult remove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            param.put("COOPER_ID", paramMap.get("public_user_id"));
            param.put("ID", paramMap.get("id"));
            Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url + member_level_del);
            if (Integer.parseInt(retMap.get("state").toString()) != 1) {
                throw new RuntimeException(retMap.get("message")+"");
            }
            pr.setState(true);
            pr.setMessage("删除会员等级成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 设置会员的等级
     * @param request
     * @return
     */
    public ProcessResult setUserLevel(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            param.put("COOPER_ID", paramMap.get("public_user_id"));
            if(!StringUtils.isEmpty(paramMap.get("region_id"))){
            	param.put("COOPER_ID", paramMap.get("region_id"));
            }
            param.put("ID", paramMap.get("id"));
            param.put("USER_ID", paramMap.get("user_id"));
            Map<String, Object> retMap= (Map<String, Object>) queryForPost(param,store_service_url + member_level_set_user_level);
            if (Integer.parseInt(retMap.get("state").toString()) != 1) {
                throw new RuntimeException(retMap.get("message")+"");
            }
            pr.setState(true);
            pr.setMessage("设置会员等级成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }


    public Object queryForPost(Map<String,Object> obj,String url) throws Exception{
        String params = "";
        if(obj != null){
            Packet packet = Transform.GetResult(obj, EsbConfig.ERP_FORWARD_KEY_NAME);//加密数据
            params = Jackson.writeObject2Json(packet);//对象转json、字符串
        }
        //发送至服务端
        String json = HttpClientUtil.post(url, params,30000);
        return Transform.GetPacketJzb(json,Map.class);
    }

}

    
    
