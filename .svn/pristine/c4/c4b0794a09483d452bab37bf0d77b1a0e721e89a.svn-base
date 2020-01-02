package com.tk.oms.marketing.service;

import com.tk.oms.marketing.dao.RefillCardDao;
import com.tk.sys.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : RefillCardService
 * 会员充值卡相关
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/7/4
 */
@Service("RefillCardService")
public class RefillCardService {

    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private RefillCardDao refillCardDao;

    /**
     * 查询充值卡列表
     * @param request
     * @return
     */
    public GridResult queryList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }
            if ((!StringUtils.isEmpty(paramMap.get("state"))) && paramMap.get("state") instanceof String) {
                paramMap.put("state", (paramMap.get("state").toString()).split(","));
            }
            //查询充值卡总数
            int total = refillCardDao.queryCount(paramMap);
            //查询充值卡列表
            List<Map<String, Object>> dataList = refillCardDao.queryList(paramMap);

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
     * 查询充值卡信息
     * @param request
     * @return
     */
    public ProcessResult queryDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            long id = Long.parseLong(paramMap.get("id").toString());
            Map<String, Object> data = refillCardDao.queryById(id);

            if (data != null ) {
                pr.setState(true);
                pr.setMessage("查询成功!");
                pr.setObj(data);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑充值卡（保存或修改）
     * @param request
     * @return
     */
    public ProcessResult edit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if(StringUtils.isEmpty(paramMap.get("edit_type"))){
                String[] user_types = paramMap.get("USER_TYPE").toString().split(",");
                Map<String, Object> existMap = new HashMap<String, Object>();
                existMap.put("user_types", Arrays.asList(user_types));
                existMap.put("refill_card_name", paramMap.get("REFILL_CARD_NAME"));
                if(paramMap.containsKey("ID") && !StringUtils.isEmpty(paramMap.get("ID").toString())) {
                    existMap.put("id", paramMap.get("ID"));
                }
                int count = refillCardDao.isExistByNameAndType(existMap);

                if(count > 0) {
                    pr.setState(false);
                    pr.setMessage("相同用户类型下，不允许存在同名的会员卡");
                    return pr;
                }
                if (refillCardDao.insertOrUpdate(paramMap) > 0) {
                    pr.setState(true);
                    pr.setMessage("保存成功!");
                } else {
                    pr.setState(true);
                    pr.setMessage("保存失败");
                }
            } else {
                if (!paramMap.containsKey("ID") || StringUtils.isEmpty(paramMap.get("ID"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数【ID】");
                    return pr;
                }
                if (!paramMap.containsKey("SELL_START_DATE") || StringUtils.isEmpty(paramMap.get("SELL_START_DATE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数【SELL_START_DATE】");
                    return pr;
                }
                if (!paramMap.containsKey("SELL_END_DATE") || StringUtils.isEmpty(paramMap.get("SELL_END_DATE"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数【SELL_END_DATE】");
                    return pr;
                }
                int count = this.refillCardDao.updateRefillCardDate(paramMap);
                if (count <= 0) {
                    pr.setState(false);
                    pr.setMessage("更新会员销售时间失败");
                    return pr;
                }
                pr.setState(true);
                pr.setMessage("更新会员销售时间成功");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("保存失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 删除充值卡
     * @param request
     * @return
     */
    public ProcessResult remove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            long id = Long.parseLong(paramMap.get("id").toString());
            if (refillCardDao.deletedById(id) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功!");
            } else {
                pr.setState(true);
                pr.setMessage("删除失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("保存失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 更改充值卡状态
     * @param request
     * @return
     */
    public ProcessResult editState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            }
            if (refillCardDao.updateState(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("更改成功!");
            } else {
                pr.setState(true);
                pr.setMessage("更改失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更改失败："+e.getMessage());
        }
        return pr;
    }

    /**
     * 查询销售记录
     * @param request
     * @return
     */
    public GridResult querySaleDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }
            List<Map<String, Object>> list = refillCardDao.querySaleDetailByCard(paramMap);
            int count = refillCardDao.querySaleDetailCountByCard(paramMap);
            gr.setObj(list);
            gr.setState(true);
            gr.setTotal(count);
            gr.setMessage("查询销售记录成功!");

        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询销售记录失败："+e.getMessage());
        }
        return gr;
    }


    /**
     * 查询购卡记录
     * @param request
     * @return
     */
    public GridResult queryBuyCardDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }

            List<Map<String, Object>> list = refillCardDao.queryBuyCardDetail(paramMap);
            int count = refillCardDao.queryBuyCardDetailCount(paramMap);
            gr.setObj(list);
            gr.setState(true);
            gr.setTotal(count);
            gr.setMessage("查询购卡记录成功!");

        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询购卡记录失败："+e.getMessage());
        }
        return gr;
    }



    /**
     * 查询收支记录
     * @param request
     * @return
     */
    public GridResult queryBalanceDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }

            List<Map<String, Object>> list = refillCardDao.queryBalanceDetail(paramMap);
            int count = refillCardDao.queryBalanceDetailCount(paramMap);
            gr.setObj(list);
            gr.setState(true);
            gr.setTotal(count);
            gr.setMessage("查询收支记录成功!");

        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询收支记录失败："+e.getMessage());
        }
        return gr;
    }


    /**
     * 查询会员卡销售记录
     * @param request
     * @return
     */
    public GridResult queryBuyDetail(HttpServletRequest request) {
        GridResult gr = new GridResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);

            if(!StringUtils.isEmpty(json)) {
                paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
                PageUtil.handlePageParams(paramMap);
            }

            List<Map<String, Object>> list = refillCardDao.queryBuyDetail(paramMap);
            int count = refillCardDao.queryBuyDetailCount(paramMap);
            gr.setObj(list);
            gr.setState(true);
            gr.setTotal(count);
            gr.setMessage("查询会员卡销售记录成功!");

        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            logger.error("查询会员卡销售记录失败："+e.getMessage());
        }
        return gr;
    }



}

    
    
