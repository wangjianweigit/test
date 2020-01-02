package com.tk.oms.analysis.service;

import com.tk.oms.analysis.dao.StockChangesDao;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (c), 2017, Tongku
 * FileName : StockChangesService
 * 库存异动参考操作业务类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2017/7/19
 */
@Service("StockChangesService")
public class StockChangesService {

    @Resource
    private StockChangesDao stockChangesDao;

    @Value("${jdbc_user}")
    private String jdbc_user;

    /**
     * 分页获取库存异动参考列表数据
     * @param request
     * @return
     */
    public GridResult queryStockChangesListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            paramMap.put("jdbc_user", jdbc_user);
            if (StringUtils.isEmpty(paramMap.get("start_date")) && StringUtils.isEmpty(paramMap.get("end_date"))) {
                paramMap.put("time_count", 0);
            } else {
                paramMap.put("time_count", 1);
            }
            int total = this.stockChangesDao.queryStockChangesCount(paramMap);
            //用于存储所有数据
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //获取sku列表
            List<Map<String, Object>> skuList = this.stockChangesDao.queryProductSkuList(paramMap);
            if (skuList != null && skuList.size() > 0) {
                for (Map<String, Object> sku : skuList) {
                    //存储获取的数据
                    Map<String, Object> map = new HashMap<String, Object>();
                    //商品货号
                    paramMap.put("product_itemnumber", sku.get("PRODUCT_ITEMNUMBER").toString());
                    //商品颜色
                    paramMap.put("product_color", sku.get("PRODUCT_COLOR").toString());
                    //商品规格
                    paramMap.put("product_specs", sku.get("PRODUCT_SPECS").toString());
                    //获取sku的库存数量
                    map.putAll(sku);
                    map.put("stockList", this.stockChangesDao.queryProductStockListBySku(paramMap));
                    map.put("activityNum", this.stockChangesDao.queryActivityNumByItemnumber(paramMap));
                    list.add(map);
                }
                gr.setMessage("获取库存异动列表成功");
                gr.setObj(list);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 获取活动列表
     * @param request
     * @return
     */
    public ProcessResult queryActivityList(HttpServletRequest request) {
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
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<Map<String, Object>> list = null;
            if (!StringUtils.isEmpty(paramMap.get("product_itemnumber"))) {
                list = this.stockChangesDao.queryActivityListByNumber(paramMap);
            }
            if (!StringUtils.isEmpty(paramMap.get("site_id"))) {
                list = this.stockChangesDao.queryActivityListBySite(paramMap);
            }

            if (list != null && list.size() > 0) {
                pr.setState(true);
                pr.setMessage("获取活动列表成功");
                pr.setObj(list);
            } else {
                pr.setState(true);
                pr.setMessage("无数据");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询库存异动参考详情
     * @param request
     * @return
     */
    public ProcessResult queryStockChangesDetailList(HttpServletRequest request) {
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
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(paramMap.get("product_itemnumber"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_ITEMNUMBER");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("product_specs"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_SPECS");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("product_color"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_COLOR");
                return pr;
            }
            paramMap.put("jdbc_user", jdbc_user);
            //用于存储所有数据
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            //获取大仓库列表
            List<Map<String, Object>> warehouseList = this.stockChangesDao.queryWarehouseList();
            //获取库存数量
            List<Map<String, Object>> stockList = this.stockChangesDao.queryStockChangesDetailList(paramMap);
            if (warehouseList != null && warehouseList.size() > 0) {
                for (Map<String, Object> warehouse : warehouseList) {
                    Integer id = Integer.parseInt(warehouse.get("id").toString());
                    Map<String, Object> map = new HashMap<String, Object>();
                    List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
                    map.put("warehouse_name", warehouse.get("option").toString());
                    for (Map<String, Object> stock : stockList) {
                        Integer warehouse_id = Integer.parseInt(stock.get("WAREHOUSE_ID").toString());
                        if (id == warehouse_id) {
                            list1.add(stock);
                        }
                    }
                    map.put("stockList", list1);
                    list.add(map);
                }

            }
            pr.setState(true);
            pr.setMessage("获取库存异动参考详情成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 获取仓库列表（大仓）
     * @param request
     * @return
     */
    public ProcessResult queryWarehouseList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            List<Map<String, Object>> list = this.stockChangesDao.queryWarehouseList();
            if (list != null && list.size() > 0) {
                pr.setState(true);
                pr.setMessage("获取仓库列表成功");
                pr.setObj(list);
            } else {
                pr.setState(true);
                pr.setMessage("无数据");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 库存异动标记
     * @param request
     * @return
     */
    public ProcessResult stockChangesMark(HttpServletRequest request) {
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
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(paramMap.get("product_itemnumber"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_ITEMNUMBER");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("product_color"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_COLOR");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("product_specs"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_SPECS");
                return pr;
            }
            if (this.stockChangesDao.queryStockChangesMarkCount(paramMap) == 0) {
                this.stockChangesDao.insertStockChangesMark(paramMap);
                pr.setState(true);
                pr.setMessage("添加库存异动标记成功");
            } else {
                this.stockChangesDao.updateStockChangesMark(paramMap);
                pr.setState(true);
                pr.setMessage("编辑库存异动标记成功");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 删除库存异动标记
     * @param request
     * @return
     */
    public ProcessResult removeStockChangesMark(HttpServletRequest request) {
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
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(paramMap.get("product_itemnumber"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_ITEMNUMBER");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("product_color"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_COLOR");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("product_specs"))) {
                pr.setState(false);
                pr.setMessage("缺少参数PRODUCT_SPECS");
                return pr;
            }
            if(this.stockChangesDao.deleteStockChangesMark(paramMap) > 0){
                pr.setState(true);
                pr.setMessage("删除库存异动标记成功");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
}
