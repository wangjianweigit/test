package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.ProductWrapperDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : ProductWrapperService
 * 商品包材信息维护业务类
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/10/30
 */
@Service("ProductWrapperService")
public class ProductWrapperService {

    @Resource
    private ProductWrapperDao productWrapperDao;

    /**
     * 分页查询商品包材信息列表
     * @param request
     * @return
     */
    public GridResult queryProductWrapperListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            // 解析传入参数
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //分页参数处理
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int count = this.productWrapperDao.countProductWrapperForPage(params);
            if (count > 0) {
                List<Map<String, Object>> dataList = this.productWrapperDao.listProductWrapperForPage(params);
                gr.setMessage("查询商品包材信息列表成功");
                gr.setObj(dataList);
            } else {

                gr.setMessage("无数据");
                gr.setObj(null);
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
     * 查询商品包材信息详情
     * @param request
     * @return
     */
    public ProcessResult queryProductWrapperDetail(HttpServletRequest request) {
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
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("wrapper_id") || StringUtils.isEmpty(params.get("wrapper_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[wrapper_id]");
                return pr;
            }
            long wrapperId = Long.parseLong(String.valueOf(params.get("wrapper_id")));
            // 查询规格分组列表
            Map<String, Object> wrapperMap = this.productWrapperDao.getProductWrapperById(wrapperId);
            if (wrapperMap == null) {
                pr.setState(false);
                pr.setMessage("包材信息不存在或已删除");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("查询商品包材信息详情成功");
            pr.setObj(wrapperMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加或者编辑商品包材信息
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult addOrEditProductWrapper(HttpServletRequest request) throws Exception {
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
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("state") || StringUtils.isEmpty(params.get("state"))) {
                if (!params.containsKey("wrapper_name") || StringUtils.isEmpty(params.get("wrapper_name"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[wrapper_name]");
                    return pr;
                }
                if (!params.containsKey("wrapper_long") || StringUtils.isEmpty(params.get("wrapper_long"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[wrapper_long]");
                    return pr;
                }
                if (!params.containsKey("wrapper_wide") || StringUtils.isEmpty(params.get("wrapper_wide"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[wrapper_wide]");
                    return pr;
                }
                if (!params.containsKey("wrapper_tall") || StringUtils.isEmpty(params.get("wrapper_tall"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[wrapper_tall]");
                    return pr;
                }
                if (!params.containsKey("product_specs") || StringUtils.isEmpty(params.get("product_specs"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[product_specs]");
                    return pr;
                }
                if (this.productWrapperDao.countProductWrapperByName(params) > 0) {
                    pr.setState(false);
                    pr.setMessage("包材名称重复，请重新填写");
                    return pr;
                }
            }
            //校验包材名称是否重复
            int count = 0;
            if (!params.containsKey("wrapper_id") || StringUtils.isEmpty(params.get("wrapper_id"))) {
                //生成包材编号
                long wrapperId = this.productWrapperDao.getWrapperId();
                params.put("wrapper_id", wrapperId);
                params.put("wrapper_code", this.productWrapperDao.getWrapperCodeById(wrapperId));
                count = this.productWrapperDao.insertProductWrapper(params);
            } else {
                long wrapperId = Long.parseLong(String.valueOf(params.get("wrapper_id")));
                // 查询规格分组列表
                Map<String, Object> wrapperMap = this.productWrapperDao.getProductWrapperById(wrapperId);
                if (wrapperMap == null) {
                    pr.setState(false);
                    pr.setMessage("包材信息不存在或已删除");
                    return pr;
                }
                count = this.productWrapperDao.updateProductWrapper(params);
            }
            if (count <= 0) {
                throw new RuntimeException("操作失败");
            }
            pr.setState(true);
            pr.setMessage("保存商品包材信息成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除商品包材信息
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult removeProductWrapper(HttpServletRequest request) throws Exception {
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
            Map<String, Object> params = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("wrapper_id") || StringUtils.isEmpty(params.get("wrapper_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[wrapper_id]");
                return pr;
            }
            long wrapperId = Long.parseLong(String.valueOf(params.get("wrapper_id")));
            // 查询规格分组列表
            Map<String, Object> wrapperMap = this.productWrapperDao.getProductWrapperById(wrapperId);
            if (wrapperMap == null) {
                pr.setState(false);
                pr.setMessage("包材信息不存在或已删除");
                return pr;
            }
            //包材信息是否被使用校验
            if(this.productWrapperDao.countProductWrapperRrf(params) > 0){
                pr.setState(false);
                pr.setMessage("包材信息已被使用，不允许删除");
                return pr;
            }
            int count = this.productWrapperDao.deleteProductWrapper(params);
            if (count <= 0) {
                throw new RuntimeException("删除商品包材信息失败");
            }
            pr.setState(true);
            pr.setMessage("删除商品包材信息成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
}
