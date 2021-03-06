package com.tk.oms.platform.service;

import com.tk.oms.platform.dao.DecorateCommonDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2018, TongKu
 * FileName : DecorateCommonService
 * 固定模块业务层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/09/12
 */
@Service("DecorateCommonService")
public class DecorateCommonService {

    @Resource
    private DecorateCommonDao decorateCommonDao;

    /**
     * 分页查询固定模块选择商品列表
     * @param request
     * @return
     */
    public GridResult querySelectProductListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if(!params.containsKey("site_id") && StringUtils.isEmpty(params.get("site_id"))){
                gr.setState(false);
                gr.setMessage("缺少参数【site_id】");
                return gr;
            }
            if (!params.containsKey("sort") || StringUtils.isEmpty(params.get("sort"))) {
                params.put("sort", "itemnumber");
            }
            int total = this.decorateCommonDao.countSelectProductForPage(params);
            if (total > 0) {
                List<Map<String, Object>> list = this.decorateCommonDao.listSelectProductForPage(params);
                gr.setMessage("获取装修模板列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
                gr.setObj(null);
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 查询选中商品列表
     * @param request
     * @return
     */
    public ProcessResult querySelectProductList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(params.containsKey("itemnumbers") && !StringUtils.isEmpty(params.get("itemnumbers"))){
                params.put("itemnumbers",(List<String>)params.get("itemnumbers"));
            }
            List<Map<String, Object>> productGroupList = this.decorateCommonDao.listSelectProduct(params);
            pr.setState(true);
            pr.setMessage("查询选中商品列表成功");
            pr.setObj(productGroupList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询商品分组
     * @param request
     * @return
     */
    public ProcessResult queryProductGroupList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<Map<String, Object>> productGroupList = null;
            if (params.containsKey("parent_id") && !StringUtils.isEmpty(params.get("parent_id"))) {
                productGroupList = this.decorateCommonDao.listParentProductGroup(params);
            } else {
                productGroupList = this.decorateCommonDao.listProductGroup();
            }
            pr.setState(true);
            pr.setMessage("查询商品分组成功");
            pr.setObj(productGroupList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询活动列表
     * @param request
     * @return
     */
    public ProcessResult queryActivityList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("site_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[site_id]");
                return pr;
            }
            List<Map<String, Object>> activityList = this.decorateCommonDao.listActivity(params);
            pr.setState(true);
            pr.setMessage("查询活动列表成功");
            pr.setObj(activityList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询装修页面列表
     * @param request
     * @return
     */
    public ProcessResult queryDecoratePageList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("template_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[template_id]");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("site_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[site_id]");
                return pr;
            }
            List<Map<String, Object>> pageList = this.decorateCommonDao.listDecoratePage(params);
            pr.setState(true);
            pr.setMessage("查询装修页面列表成功");
            pr.setObj(pageList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询商品季节
     * @param request
     * @return
     */
    public ProcessResult queryProductSeasonList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {

            List<Map<String, Object>> list = this.decorateCommonDao.listProductSeason();
            pr.setState(true);
            pr.setMessage("查询商品季节成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询商品品牌
     * @param request
     * @return
     */
    public ProcessResult queryProductBrandList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {

            List<Map<String, Object>> list = this.decorateCommonDao.listProductBrand();
            pr.setState(true);
            pr.setMessage("查询商品品牌成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 分页查询商品品牌列表
     * @param request
     * @return
     */
    public GridResult queryProductBrandListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("参数缺失");
                return gr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }

            int count = this.decorateCommonDao.countProductBrandForPage(paramMap);
            if (count > 0) {
                List<Map<String, Object>> list = this.decorateCommonDao.listProductBrandForPage(paramMap);
                gr.setMessage("查询品牌列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
                gr.setObj(null);
            }
            gr.setState(true);
            gr.setTotal(count);
        } catch (IOException e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return gr;
    }

    /**
     * 查询选中品牌列表
     * @param request
     * @return
     */
    public ProcessResult querySelectBrandList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(params.containsKey("brand_ids") && !StringUtils.isEmpty(params.get("brand_ids"))){
                params.put("brand_ids",(List<String>)params.get("brand_ids"));
            }
            List<Map<String, Object>> list = this.decorateCommonDao.listSelectProductBrand(params);
            pr.setState(true);
            pr.setMessage("查询选中品牌列表成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询商品规格
     * @param request
     * @return
     */
    public ProcessResult queryProductSpecList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            List<Map<String, Object>> list = this.decorateCommonDao.listProductSpec();
            pr.setState(true);
            pr.setMessage("查询商品规格成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 查询仓库列表
     * @param request
     * @return
     */
    public ProcessResult queryWarehouseList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(params.get("site_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[site_id]");
                return pr;
            }
            List<Map<String, Object>> list = this.decorateCommonDao.listWarehouse(Long.parseLong(String.valueOf(params.get("site_id"))));
            pr.setState(true);
            pr.setMessage("查询仓库列表成功");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }


    /**
     * 分页查询商品列表
     * @param request
     * @return
     */
    public GridResult queryAllProductListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if (!params.containsKey("sort") || StringUtils.isEmpty(params.get("sort"))) {
                params.put("sort", "itemnumber");
            }
            int total = this.decorateCommonDao.countProductForPage(params);
            if (total > 0) {
                List<Map<String, Object>> list = this.decorateCommonDao.listProductForPage(params);
                gr.setMessage("获取商品列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
                gr.setObj(null);
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }


}
