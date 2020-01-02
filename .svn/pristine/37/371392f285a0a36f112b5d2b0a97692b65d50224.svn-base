package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.MerchantPosDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : MerchantPosService
 * 商业Pos设备管理业务操作类
 * @author wangjianwei
 * @version 1.00
 * @date 2017/7/5 11:20
 */
@Service("MerchantPosService")
public class MerchantPosService {

    @Resource
    private MerchantPosDao merchantPosDao;  //商业Pos设备管理数据访问接口
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 获取商业Pos设备列表
     * @param request 查询条件
     * @return
     */
    public GridResult queryList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if (paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
                String[] stateArray = paramMap.get("state").toString().split(",");
                paramMap.put("state", Arrays.asList(stateArray));
            }
            //查询记录数
            int count = merchantPosDao.queryMerchantPosCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = merchantPosDao.queryMerchantPosList(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setMessage("获取成功");
                gr.setObj(list);
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return gr;
    }

    /**
     * 获取商业Pos设备信息
     * @param request 新增内容
     * @return
     */
    public ProcessResult queryMerchantPosInfo(HttpServletRequest request){
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
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            Map<String, Object> merchantPos  = merchantPosDao.queryMerchantPosById(Long.parseLong(paramMap.get("id").toString()));
            pr.setState(true);
            pr.setMessage("获取成功");
            pr.setObj(merchantPos);
        } catch (Exception e) {
            logger.error(e.getMessage());
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
    /**
     * 新增商业Pos设备
     * @param request 新增内容
     * @return
     */
    @Transactional
    public ProcessResult addMerchantPos(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("public_user_id") || StringUtils.isEmpty(paramMap.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数public_user_id");
                return pr;
            }
            if (!paramMap.containsKey("merchant_name") || StringUtils.isEmpty(paramMap.get("merchant_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数merchant_name");
                return pr;
            }
            if (!paramMap.containsKey("merchant_no") || StringUtils.isEmpty(paramMap.get("merchant_no"))) {
                pr.setState(false);
                pr.setMessage("缺少参数merchant_no");
                return pr;
            }
            if (!paramMap.containsKey("state") || StringUtils.isEmpty(paramMap.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数state");
                return pr;
            }
            if (!paramMap.containsKey("terminal_model") || StringUtils.isEmpty(paramMap.get("terminal_model"))) {
                pr.setState(false);
                pr.setMessage("缺少参数terminal_model");
                return pr;
            }
            if (!paramMap.containsKey("terminal_no") || StringUtils.isEmpty(paramMap.get("terminal_no"))) {
                pr.setState(false);
                pr.setMessage("缺少参数terminal_no");
                return pr;
            }
            if (!paramMap.containsKey("effective_date") || StringUtils.isEmpty(paramMap.get("effective_date"))) {
                pr.setState(false);
                pr.setMessage("缺少参数effective_date");
                return pr;
            }

            //终端号唯一校验
            int count = merchantPosDao.queryMerchantPosCountByTerminalNo(paramMap.get("terminal_no").toString());
            if(count >0){
                pr.setState(false);
                pr.setMessage("已有该终端号的Pos设备");
                return pr;
            }
            // 插入商业Pos设备
            if (merchantPosDao.insertMerchantPos(paramMap) > 0) {
                Map<String, Object> merchantPos  = merchantPosDao.queryMerchantPosById(Long.parseLong(paramMap.get("id").toString()));
                pr.setState(true);
                pr.setMessage("新增成功");
                pr.setObj(merchantPos);
            } else {
                pr.setState(false);
                pr.setMessage("新增失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 修改商业Pos设备信息
     * @param request 修改内容
     * @return
     */
    @Transactional
    public ProcessResult editMerchantPos(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            //商业Pos设备管理id
            long id = Long.parseLong(paramMap.get("id").toString());
            //商业Pos设备信息
            Map<String, Object> merchantPos  = merchantPosDao.queryMerchantPosById(id);
            //判断商业Pos设备是否存在
            if (merchantPos == null) {
                pr.setState(false);
                pr.setMessage("商业Pos设备管理不存在");
                return pr;
            }
            if (merchantPosDao.updateMerchantPos(paramMap) > 0) {
                pr.setState(true);
                pr.setObj(paramMap);
                pr.setMessage("更新成功");
            } else {
                pr.setState(false);
                pr.setMessage("更新失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 删除商业Pos设备信息
     * @param request
     * @return
     */
    public ProcessResult removeMerchantPos(HttpServletRequest request) throws Exception {
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

            //商业Pos设备管理id
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            //商业Pos设备管理id
            long id = Long.parseLong(paramMap.get("id").toString());
            //商业Pos设备信息
            Map<String, Object> merchantPos = merchantPosDao.queryMerchantPosById(id);
            //判断商业Pos设备管理是否存在
            if (merchantPos == null) {
                pr.setState(false);
                pr.setMessage("商业Pos设备管理id不存在");
                return pr;
            }
            if (merchantPosDao.deleteMerchantPos(id) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            } else {
                pr.setState(false);
                pr.setMessage("删除失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return pr;
    }
}
