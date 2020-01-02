package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.OrderImportModelDao;
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
 *
 * Copyright (c), 2017, Tongku
 * FileName : OrderImportModelService
 * 订单导入模版业务处理类
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/8/23 14:53
 */
@Service("OrderImportModelService")
public class OrderImportModelService {

    @Resource
    private OrderImportModelDao OrderImportModelDao;//订单导入模版数据访问接口

    private static final String IMPORT_ORDER = "TMP_IMPORT_ORDER_";
    private static final String IMPORT_PRODUCT = "TMP_IMPORT_PRODUCT_";

    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 获取订单导入模版列表
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
            //查询记录数
            int count = OrderImportModelDao.queryOrderImportModelCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = OrderImportModelDao.queryOrderImportModelList(paramMap);
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
     * 编辑订单导入模版启用状态
     * @param request 新增内容
     * @return
     */
    public ProcessResult editOrderImportModelState(HttpServletRequest request){
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
            Map<String, Object> OrderImportModel  = OrderImportModelDao.queryOrderImportModelById(Long.parseLong(paramMap.get("id").toString()));
            if(OrderImportModel == null){
                pr.setState(false);
                pr.setMessage("订单导入模版不存在");
                return pr;
            }

            if(OrderImportModel.get("STATE").equals("1")){
                paramMap.put("state", "2");//禁用
            }else{
                paramMap.put("state", "1");//启用
            }
            if(OrderImportModelDao.updateOrderImportModel(paramMap) > 0){
                pr.setState(true);
                pr.setMessage("操作成功");
                return pr;
            }else{
                pr.setState(false);
                pr.setMessage("操作失败");
                return pr;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

    /**
     * 获取订单导入模版数据信息
     * @param request 新增内容
     * @return
     */
    public ProcessResult queryOrderImportModelInfo(HttpServletRequest request){
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
            Map<String, Object> OrderImportModel  = OrderImportModelDao.queryOrderImportModelById(Long.parseLong(paramMap.get("id").toString()));
            pr.setState(true);
            pr.setMessage("获取成功");
            pr.setObj(OrderImportModel);
        } catch (Exception e) {
            logger.error(e.getMessage());
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

    /**
     * 新增订单导入模版数据
     * @param request
     * @return
     */
    public ProcessResult addOrderImportModel(HttpServletRequest request){
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
            if (!paramMap.containsKey("model_name") || StringUtils.isEmpty(paramMap.get("model_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数model_name");
                return pr;
            }
            if (!paramMap.containsKey("model_type") || StringUtils.isEmpty(paramMap.get("model_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数model_type");
                return pr;
            }
            if (!paramMap.containsKey("model_code") || StringUtils.isEmpty(paramMap.get("model_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数model_code");
                return pr;
            }
            if (!paramMap.containsKey("icon_img") || StringUtils.isEmpty(paramMap.get("icon_img"))) {
                pr.setState(false);
                pr.setMessage("缺少参数icon_img");
                return pr;
            }
            if (!paramMap.containsKey("example_path") || StringUtils.isEmpty(paramMap.get("example_path"))) {
                pr.setState(false);
                pr.setMessage("缺少参数example_path");
                return pr;
            }
            // 模版名称唯一校验
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("model_name", paramMap.get("model_name").toString());
            param.put("model_code", paramMap.get("model_code").toString());
            if(OrderImportModelDao.queryOrderImportModelByNameCount(param) >0){
                pr.setState(false);
                pr.setMessage("该模版名称已创建或模版代码重复");
                return pr;
            }

            // 插入订单导入模版数据
            if (OrderImportModelDao.insertOrderImportModel(paramMap) > 0) {
                Map<String, Object> OrderImportModel  = OrderImportModelDao.queryOrderImportModelById(Long.parseLong(paramMap.get("id").toString()));
                pr.setState(true);
                pr.setMessage("新增成功");
                pr.setObj(OrderImportModel);
            } else {
                pr.setState(false);
                pr.setMessage("新增失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return pr;
    }

    /**
     * 修改订单导入模版数据信息
     * @param request 修改内容
     * @return
     */
    public ProcessResult editOrderImportModel(HttpServletRequest request){
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
            //模版id
            long id = Long.parseLong(paramMap.get("id").toString());
            //模版信息
            Map<String, Object> OrderImportModel  = OrderImportModelDao.queryOrderImportModelById(id);
            //判断模版是否存在
            if (OrderImportModel == null) {
                pr.setState(false);
                pr.setMessage("模版不存在");
                return pr;
            }
            if (OrderImportModelDao.updateOrderImportModel(paramMap) > 0) {
                pr.setState(true);
                pr.setObj(paramMap);
                pr.setMessage("更新成功");
            } else {
                pr.setState(false);
                pr.setMessage("更新失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return pr;
    }

    /**
     * 删除订单导入模版数据信息
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeOrderImportModel(HttpServletRequest request) throws Exception {
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

            //模版id
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            //模版id
            long id = Long.parseLong(paramMap.get("id").toString());
            //模版信息
            Map<String, Object> OrderImportModel = OrderImportModelDao.queryOrderImportModelById(id);
            //判断模版是否存在
            if (OrderImportModel == null) {
                pr.setState(false);
                pr.setMessage("模版数据不存在");
                return pr;
            }
            //删除模版配置数据信息
            if (OrderImportModelDao.queryOrderImportModelConfigCount(id) != OrderImportModelDao.deleteOrderImportModelConfig(id)) {
                throw new RuntimeException("删除失败");
            }
            //删除模版
            if (OrderImportModelDao.deleteOrderImportModel(id) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            } else {
                pr.setState(false);
                pr.setMessage("删除失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取订单导入模版配置数据信息
     * @param request
     * @return
     */
    public ProcessResult queryOrderImportModelConfigDetail(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        Map<String,Object> data = new HashMap<String,Object>();
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
            if (!paramMap.containsKey("model_id") || StringUtils.isEmpty(paramMap.get("model_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数model_id");
                return pr;
            }
            //模版id
            long model_id = Long.parseLong(paramMap.get("model_id").toString());
            Map<String, Object> orderImportModel = OrderImportModelDao.queryOrderImportModelById(model_id);
            if(orderImportModel == null){
                pr.setState(false);
                pr.setMessage("不存在该模版数据");
                return pr;
            }
            //主表
            paramMap.clear();
            paramMap.put("model_id", model_id);
            paramMap.put("type", 1);
            List<Map<String, Object>> configOrder = OrderImportModelDao.queryOrderImportModelConfigDetail(paramMap);
            if(configOrder.size() == 0){
                Map<String, Object> order = new HashMap<String, Object>();
                order.put("HEADER_NAME", "订单编号");
                order.put("FIELD_NAME", "out_order_number");
                order.put("MATCH_TYPE", "3");   //重复校验
                order.put("TYPE", "1");         //订单报表
                order.put("FIELD_TYPE", "1");   //字段类型
                configOrder.add(order);
            }
            data.put("order", configOrder);

            //明细表
            paramMap.clear();
            paramMap.put("model_id", model_id);
            paramMap.put("type", 2);
            List<Map<String, Object>> configDetail = OrderImportModelDao.queryOrderImportModelConfigDetail(paramMap);
            if(configDetail.size() == 0){
                Map<String, Object> detail = new HashMap<String, Object>();
                detail.put("HEADER_NAME", "商家编码");
                detail.put("FIELD_NAME", "product_code");
                detail.put("MATCH_TYPE", "4");  //数据匹配
                detail.put("TYPE", "2");        //宝贝报表
                detail.put("FIELD_TYPE", "2");  //字段类型
                configDetail.add(detail);

                //  商品数量
                Map<String, Object> productCountMap = new HashMap<String, Object>();
                productCountMap.put("HEADER_NAME", "商品数量");
                productCountMap.put("FIELD_NAME", "product_count");
                productCountMap.put("MATCH_TYPE", "1");  //数据匹配
                productCountMap.put("TYPE", "2");        //宝贝报表
                productCountMap.put("FIELD_TYPE", "2");  //字段类型
                configDetail.add(productCountMap);

                Map<String, Object> order = new HashMap<String, Object>();
                order.put("HEADER_NAME", "订单编号");
                order.put("FIELD_NAME", "out_order_number");
                order.put("MATCH_TYPE", "3");   //重复校验
                order.put("TYPE", "1");         //订单报表
                order.put("FIELD_TYPE", "1");   //字段类型
                configDetail.add(order);

            }
            data.put("detail", configDetail);
            pr.setState(true);
            pr.setMessage("获取成功");
            pr.setObj(data);
        } catch (Exception e) {
            logger.error(e.getMessage());
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑订单导入模版配置数据信息
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editOrderImportModelConfig(HttpServletRequest request) throws Exception{
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
            if (!paramMap.containsKey("model_id") || StringUtils.isEmpty(paramMap.get("model_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数model_id");
                return pr;
            }
            long model_id = Long.parseLong(paramMap.get("model_id").toString());
            Map<String, Object> OrderImportModel  = OrderImportModelDao.queryOrderImportModelById(model_id);
            //判断模版是否存在
            if (OrderImportModel == null) {
                pr.setState(false);
                pr.setMessage("模版不存在");
                return pr;
            }
            List<Map<String, Object>> modelConfig = (List<Map<String, Object>>) paramMap.get("modelConfig");                                  //新修改的模版配置数据
            ProcessResult resPr = this.createConfigOrderAndDetailTable(modelConfig, OrderImportModel.get("MODEL_CODE").toString(),model_id);  //生成配置订单及明细表

            if(OrderImportModelDao.queryOrderImportModelConfigCount(model_id) !=  OrderImportModelDao.deleteOrderImportModelConfig(model_id)){
                throw new RuntimeException("配置失败");
            }
            if(!resPr.getState()) throw new RuntimeException(resPr.getMessage());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("modelConfig", modelConfig);
            map.put("model_id", model_id);
            if(OrderImportModelDao.insertOrderImportModelConfigByBatch(map) >0){
                pr.setState(true);
                pr.setMessage("配置成功");
            }else{
                throw new RuntimeException("配置失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 动态创建模版订单及明细表
     * @param modelConfig
     * @return
     */
    public ProcessResult createConfigOrderAndDetailTable(List<Map<String,Object>> modelConfig, String model_code, long model_id){
        ProcessResult pr= new ProcessResult();
        List<Map<String, Object>> orderTable = new ArrayList<Map<String, Object>>();        //订单表集合
        List<Map<String, Object>> orderDetailTable = new ArrayList<Map<String, Object>>();  //订单明细表集合
        try{
            for (Map<String, Object> data : modelConfig) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("FIELD_NAME", data.get("FIELD_NAME").toString().toLowerCase());     //字段名称
                if("1".equals(data.get("TYPE").toString())){
                    //订单表
                    orderTable.add(param);
                }else if("2".equals(data.get("TYPE").toString())){
                    //订单明细表
                    orderDetailTable.add(param);
                }
            }

            Map paramMap = new HashMap();
            //判断订单是否存在 1、存在 添加或修改字段 2、不存在 添加字段
            String order_table = IMPORT_ORDER + model_code;
            boolean order_flag = true;  //订单表存在标识
            try{
                this.OrderImportModelDao.queryExistTable(order_table);
            }catch (Exception e){
                //创建订单表
                paramMap.put("columns", orderTable);
                paramMap.put("table_name", order_table);
                this.OrderImportModelDao.createImportOrderTable(paramMap);
                order_flag = false;
            }

            //判断订单明细表是否存在 1、存在 添加或修改字段 2、不存在 添加字段
            boolean detail_flag = true;  //订单明细表存在标识
            String detail_table = IMPORT_PRODUCT + model_code;
            try{
                this.OrderImportModelDao.queryExistTable(detail_table);
            }catch (Exception e){
                //创建订单明细表
                paramMap.clear();
                paramMap.put("columns", orderDetailTable);
                paramMap.put("table_name", detail_table);
                this.OrderImportModelDao.createImportOrderDetailTable(paramMap);
                detail_flag = false;
            }
            paramMap.clear();
            paramMap.put("model_id", model_id);
            List<Map<String, Object>> oldModelConfig = this.OrderImportModelDao.queryOrderImportModelConfigDetail(paramMap);    //已存在的模版配置数据
            Map param = new HashMap();
            for (Map<String, Object> nowMap: modelConfig) {
                for (Map<String, Object> oldMap: oldModelConfig) {
                    if(!order_flag && "1".equals(nowMap.get("TYPE").toString()) || !detail_flag && "2".equals(nowMap.get("TYPE").toString())) continue;  //订单表已创建
                    param.clear();
                    param.put("table_name", "1".equals(nowMap.get("TYPE").toString()) ? IMPORT_ORDER + model_code : IMPORT_PRODUCT + model_code);
                    /* 判断配置信息是否已存在 1、未存在 添加至字段 2、存在 更新字段 */
                    if(nowMap.containsKey("ID")){
                        if(oldMap.get("ID").toString().equals(nowMap.get("ID").toString()) && !oldMap.get("FIELD_NAME").toString().equals(nowMap.get("FIELD_NAME").toString())){
                            param.put("old_column", oldMap.get("FIELD_NAME").toString());
                            param.put("new_column", nowMap.get("FIELD_NAME").toString());
                            this.OrderImportModelDao.updateTableColumn(param);     //字段重命名
                        }
                    }
                }
            }
            for (Map<String, Object> newMap: modelConfig){
                if(!order_flag && "1".equals(newMap.get("TYPE").toString()) || !detail_flag && "2".equals(newMap.get("TYPE").toString())) continue;   //订单表已创建
                if(!newMap.containsKey("ID")){
                    param.clear();
                    param.put("table_name", "1".equals(newMap.get("TYPE").toString()) ? IMPORT_ORDER + model_code : IMPORT_PRODUCT + model_code);
                    param.put("column", newMap.get("FIELD_NAME").toString());
                    this.OrderImportModelDao.addTableColumn(param);                //添加字段
                }
            }
            pr.setState(true);
        }catch (Exception e){
            logger.error(e.getMessage());
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除订单导入模版配置数据信息
     * @param request
     * @return
     */
    public ProcessResult removeOrderImportModelConfig(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("model_id") || StringUtils.isEmpty(paramMap.get("model_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数model_id");
                return pr;
            }
            //模版配置id
            long config_id = Long.parseLong(paramMap.get("id").toString());
            //模版配置信息
            Map<String, Object> config = OrderImportModelDao.queryOrderImportModelConfig(config_id);
            if (config == null) {
                pr.setState(false);
                pr.setMessage("模版配置数据不存在");
                return pr;
            }
            long model_id = Long.parseLong(paramMap.get("model_id").toString());
            Map<String, Object> model = OrderImportModelDao.queryOrderImportModelById(model_id);
            if(model == null){
                pr.setState(false);
                pr.setMessage("模版数据不存在");
                return pr;
            }

            Map<String,Object> params = new HashMap<String,Object>();
            String model_code = model.get("MODEL_CODE").toString();
            params.put("table_name", "1".equals(config.get("TYPE").toString()) ? IMPORT_ORDER + model_code : IMPORT_PRODUCT + model_code);
            params.put("column", config.get("FIELD_NAME").toString());
            //删除模版配置表数据
            this.OrderImportModelDao.deleteTableColumn(params);
            //删除配置数据
            if (OrderImportModelDao.deleteOrderImportModelConfigById(config_id) >0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            } else {
                throw new RuntimeException("删除失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

}
