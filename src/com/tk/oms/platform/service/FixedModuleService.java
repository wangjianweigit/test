package com.tk.oms.platform.service;

import com.tk.oms.platform.dao.FixedModuleDao;
import com.tk.oms.platform.entity.FixedModuleEnum;
import com.tk.oms.platform.entity.SceneTypeEnum;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright (c), 2018, TongKu
 * FileName : FixedModuleService
 * 固定模块业务层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018/09/12
 */
@Service("FixedModuleService")
public class FixedModuleService {

    @Resource
    private FixedModuleDao fixedModuleDao;

    /**
     * 查询固定模块详细数据
     *
     * @param request
     * @return
     */
    public ProcessResult queryFixedModuleDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            if (!params.containsKey("module_code") || StringUtils.isEmpty(params.get("module_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_code】");
                return pr;
            }
            if (!params.containsKey("group_sort") || StringUtils.isEmpty(params.get("group_sort"))) {
                params.put("group_sort", 0);
            }
            if (StringUtils.isEmpty(FixedModuleEnum.getName(String.valueOf(params.get("module_code"))))) {
                pr.setState(false);
                pr.setMessage("参数值不合法");
                return pr;
            }
            //查询固定模块详细数据
            List<Map<String, Object>> list = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params);
            if (list != null && list.size() > 0) {
                String moduleBaseConf = StringUtil.append(list, "MODULE_BASE_CONF");
                pr.setObj(moduleBaseConf);
            } else {
                pr.setObj(null);
            }
            pr.setState(true);
            pr.setMessage("查询" + FixedModuleEnum.getName(String.valueOf(params.get("module_code"))) + "配置数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 编辑固定模板
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editFixedModule(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            if (!params.containsKey("module_code") || StringUtils.isEmpty(params.get("module_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_code】");
                return pr;
            }
            if (!params.containsKey("module_base_conf") || StringUtils.isEmpty(params.get("module_base_conf"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_base_conf】");
                return pr;
            }
            if (!params.containsKey("group_sort") || StringUtils.isEmpty(params.get("group_sort"))) {
                params.put("group_sort", 0);
            }
            Map<String, Object> fixedModuleMap = this.fixedModuleDao.getPageFixedModuleByModuleCode(params);
            if (fixedModuleMap == null) {
                this.fixedModuleDao.insertPageFixedModule(params);
                params.put("page_module_id", params.get("id"));
            } else {
                params.put("page_module_id", fixedModuleMap.get("PAGE_MODULE_ID"));
            }
            //查询固定模块详细数据
            List<Map<String, Object>> list = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params);
            if (list != null && list.size() > 0) {
                params.put("group_id", list.get(0).get("GROUP_ID"));
            }
            StringUtil.handleModuleBaseConfToParams(params);
            //删除历史数据
            this.fixedModuleDao.deletePageFixedModuleDetail(params);
            int count = this.fixedModuleDao.insertPageFixedModuleDetail(params);
            if (count <= 0) {
                throw new RuntimeException("编辑固定模板失败");
            }
            pr.setState(true);
            pr.setMessage("编辑" + FixedModuleEnum.getName(String.valueOf(params.get("module_code").toString())) + "模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除固定模板
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult removeFixedModule(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            if (!params.containsKey("module_code") || StringUtils.isEmpty(params.get("module_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_code】");
                return pr;
            }
            if (!params.containsKey("group_sort") || StringUtils.isEmpty(params.get("group_sort"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【group_sort】");
                return pr;
            }
            List<Map<String, Object>> fixedModuleList = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params);
            if (fixedModuleList == null || fixedModuleList.size() <= 0) {
                pr.setState(false);
                pr.setMessage("模块不存在或已删除");
                return pr;
            }
            Map<String, Object> fixedModuleMap = fixedModuleList.get(0);
            //删除数据
            params.put("page_module_id", fixedModuleMap.get("PAGE_MODULE_ID"));
            int count = this.fixedModuleDao.deletePageFixedModuleDetail(params);
            if (count <= 0) {
                throw new RuntimeException("删除固定模板失败");
            }
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("page_module_id", fixedModuleMap.get("PAGE_MODULE_ID"));
            searchMap.put("group_id", fixedModuleMap.get("GROUP_ID"));
            this.fixedModuleDao.updatePageFixedModuleAllSort(searchMap);

            searchMap.remove("group_id");
            searchMap.put("group_sort", params.get("group_sort"));
            this.fixedModuleDao.updatePageFixedModuleAllSort(searchMap);
            pr.setState(true);
            pr.setMessage("删除" + FixedModuleEnum.getName(String.valueOf(params.get("module_code").toString())) + "模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 更新固定模板排序
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editFixedModuleSort(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            if (!params.containsKey("module_code") || StringUtils.isEmpty(params.get("module_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_code】");
                return pr;
            }
            if (!params.containsKey("new_group_sort") || StringUtils.isEmpty(params.get("new_group_sort"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【new_group_sort】");
                return pr;
            }
            if (!params.containsKey("old_group_sort") || StringUtils.isEmpty(params.get("old_group_sort"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【old_group_sort】");
                return pr;
            }
            params.put("group_sort", params.get("old_group_sort"));
            Map<String, Object> fixedModuleMap = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params).get(0);
            if (fixedModuleMap == null) {
                pr.setState(false);
                pr.setMessage("模块数据异常");
                return pr;
            }
            //获取新的排序值
            int newSort = Integer.parseInt(String.valueOf(params.get("new_group_sort")));
            //获取历史的排序值
            int oldSort = Integer.parseInt(String.valueOf(params.get("old_group_sort")));
            params.put("page_module_id", fixedModuleMap.get("PAGE_MODULE_ID"));
            params.put("group_id", fixedModuleMap.get("GROUP_ID"));
            if (oldSort > newSort) {
                params.put("add", "add");
                params.put("old_sort_id", newSort);
                params.put("new_sort_id", oldSort);
            } else {
                params.put("minus", "minus");
                params.put("old_sort_id", oldSort);
                params.put("new_sort_id", newSort);
            }
            params.put("group_sort", params.get("new_group_sort"));
            int count = this.fixedModuleDao.updatePageFixedModuleSort(params);
            if (count > 0) {
                this.fixedModuleDao.updatePageFixedModuleSortId(params);
                pr.setState(true);
                pr.setMessage("固定模块排序成功");
            } else {
                pr.setState(false);
                pr.setMessage("固定模块排序失败");
            }

        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 查询固定模板数量
     *
     * @param request
     * @return
     */
    public ProcessResult queryFixedModuleCount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            long pageId = Long.parseLong(String.valueOf(params.get("page_id")));
            List<Map<String, Object>> moduleCount = this.fixedModuleDao.listPageFixedModuleCountByPageId(pageId);
            pr.setState(true);
            pr.setMessage("查询固定模板数量成功");
            pr.setObj(moduleCount);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }


    /**
     * 查询固定模块数据列表
     *
     * @param request
     * @return
     */
    public ProcessResult queryFixedModuleDataList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            if (!params.containsKey("module_code") || StringUtils.isEmpty(params.get("module_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_code】");
                return pr;
            }
            if (!params.containsKey("site_id") || StringUtils.isEmpty(params.get("site_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【site_id】");
                return pr;
            }
            if (!params.containsKey("group_sort") || StringUtils.isEmpty(params.get("group_sort"))) {
                params.put("group_sort", 0);
            }
            if (StringUtils.isEmpty(FixedModuleEnum.getName(String.valueOf(params.get("module_code"))))) {
                pr.setState(false);
                pr.setMessage("参数值不合法");
                return pr;
            }
            //查询装修页面信息
            Map<String, Object> pageMap = this.fixedModuleDao.getDecoratePageById(Long.parseLong(String.valueOf(params.get("page_id"))));
            if (pageMap == null || pageMap.isEmpty()) {
                pr.setState(false);
                pr.setMessage("页面不存在或已删除");
                return pr;
            }
            int supportFlag = Integer.parseInt(String.valueOf(pageMap.get("SUPPORT_FLAG")));
            int pageType = Integer.parseInt(String.valueOf(pageMap.get("PAGE_TYPE")));
            String moduleCode = String.valueOf(params.get("module_code"));

            //查询固定模块详细数据
            List<Map<String, Object>> list = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params);
            if (list == null || list.size() <= 0) {
                pr.setState(false);
                pr.setMessage("固定模块数据异常");
                return pr;
            }
            /**
             * 获取固定模块参数
             */
            String moduleBaseConf = StringUtil.append(list, "MODULE_BASE_CONF");
            Map<String, Object> confMap = (Map<String, Object>) Jackson.readJson2Object(moduleBaseConf, Map.class);
            if(confMap == null){
                confMap = new HashMap<String, Object>();
            }
            confMap.put("site_id", params.get("site_id"));
            if (FixedModuleEnum.FIXED_MODULE_BANNER.getKey().equals(moduleCode) || FixedModuleEnum.FIXED_MODULE_SMALL_BANNER.getKey().equals(moduleCode)
                    || FixedModuleEnum.FIXED_MODULE_MENU.getKey().equals(moduleCode)) {
                //banner图或banner小广告位
                Map<String, Object> returnMap = new HashMap<String, Object>();
                if (pageType == 4 || pageType == 5  || pageType == 6 || pageType == 8) {
                    pr.setObj(confMap);
                } else {
                    returnMap.put("links", this.handlePageLinksShowNumber(confMap, "links", "count","show_type"));
                    if (FixedModuleEnum.FIXED_MODULE_BANNER.getKey().equals(moduleCode)) {
                        returnMap.put("switch_type", confMap.get("switch_type"));
                        returnMap.put("switch_interval", confMap.get("switch_interval"));
                    }
                    if (FixedModuleEnum.FIXED_MODULE_MENU.getKey().equals(moduleCode)) {
                        returnMap.put("rows_count", StringUtils.isEmpty(confMap.get("rows_count")) ? "" : confMap.get("rows_count"));
                    }
                    pr.setObj(returnMap);
                }
                pr.setState(true);
                pr.setMessage("查询" + FixedModuleEnum.getName(String.valueOf(params.get("module_code"))) + "模块详细数据成功");
            } else if (FixedModuleEnum.FIXED_MODULE_SECKILL.getKey().equals(moduleCode)) {
                //限时秒杀
                //初始返回结果集
                Map<String, Object> returnMap = new HashMap<String, Object>(16);
                //获取活动信息
                Map<String, Object> activity = this.fixedModuleDao.getActivityInfo(params);
                List<Map<String, Object>> returnList = null;
                if (activity != null) {
                    params.put("activity_id", activity.get("ACTIVITY_ID"));
                    params.put("sort", StringUtils.isEmpty(confMap.get("sort")) ? "sort_id" : confMap.get("sort"));
                    String sort = confMap.get("sort").toString();
                    if(sort.indexOf("product_count") >= 0){
                        params.put("sort", "sale_count desc");
                    }
                    returnList = this.fixedModuleDao.listSeckillProduct(params);
                    returnMap.put("start_flag", activity.get("START_FLAG"));
                    returnMap.put("countdown", activity.get("COUNTDOWN"));
                    returnMap.put("begin_date", activity.get("BEGIN_DATE"));
                    returnMap.put("end_date", activity.get("END_DATE"));
                    returnMap.put("now_date", activity.get("NOW_DATE"));
                    returnMap.put("scene_img_url", activity.get("SCENE_IMG_URL"));
                    String sceneTypeName = StringUtils.isEmpty(activity.get("SCENE_TYPE")) ? "" : SceneTypeEnum.getDes(Integer.parseInt(String.valueOf(activity.get("SCENE_TYPE"))));
                    returnMap.put("scene_type_name", sceneTypeName);
                    returnMap.put("products", returnList);
                }
                //限时秒杀
                pr.setState(true);
                pr.setObj(returnMap);
                pr.setMessage("查询" + FixedModuleEnum.getName(String.valueOf(params.get("module_code"))) + "模块数据成功");
            } else if (FixedModuleEnum.FIXED_MODULE_UPDATE.getKey().equals(moduleCode)) {
                //每日上新模块
                pr = this.queryUpdateProductList(confMap, supportFlag);
            } else if (FixedModuleEnum.FIXED_MODULE_BRAND.getKey().equals(moduleCode)) {
                //品牌街模块
                pr = this.queryProductBrandList(confMap, supportFlag);
            } else if (FixedModuleEnum.FIXED_MODULE_HOT.getKey().equals(moduleCode)) {
                //热卖TOP模块
                pr = this.queryTopProductList(confMap, supportFlag);
            } else if (FixedModuleEnum.FIXED_MODULE_BUY.getKey().equals(moduleCode)) {
                //特惠抢购模块
                pr = this.queryBuyProductList(confMap, supportFlag);
            } else if (FixedModuleEnum.FIXED_MODULE_CLASSIFY.getKey().equals(moduleCode)) {
                //商品分类模块
                pr = this.queryClassifyProductList(confMap, supportFlag, pageType);
            } else if (FixedModuleEnum.FIXED_MODULE_PRESELL.getKey().equals(moduleCode)) {
                //预售抢先模块
                pr = this.queryPreSellProductList(confMap, supportFlag,pageType);
            } else if (FixedModuleEnum.FIXED_MODULE_CUSTOM.getKey().equals(moduleCode) || FixedModuleEnum.FIXED_MODULE_MAIN_CUSTOM.getKey().equals(moduleCode)
                    || FixedModuleEnum.FIXED_MODULE_RANKING_CUSTOM.getKey().equals(moduleCode) || FixedModuleEnum.FIXED_MODULE_HOT_CUSTOM.getKey().equals(moduleCode)) {
                //实力定制模块
                pr = this.queryCustomProductList(confMap, supportFlag, moduleCode);
            } else if (FixedModuleEnum.FIXED_MODULE_NEW_SHOES.getKey().equals(moduleCode)) {
                //最新童鞋模块
                pr = this.queryNewShoesModuleDataList(confMap, supportFlag);
            } else if (FixedModuleEnum.FIXED_MODULE_GIFT.getKey().equals(moduleCode)) {
                //礼品配件模块
                pr = this.queryGiftModuleDataList(confMap, supportFlag);
            } else if (FixedModuleEnum.FIXED_MODULE_ACTIVITY.getKey().equals(moduleCode)) {
                //活动模块
                pr = this.queryActivityModuleDataList(confMap, supportFlag, pageType);
            } else if (FixedModuleEnum.FIXED_MODULE_PAST_PRESELL.getKey().equals(moduleCode)) {
                //往期预售模块
                pr = this.queryPastPreSellModuleDataList(confMap, supportFlag, pageType);
            } else if (FixedModuleEnum.FIXED_MODULE_CUSTOM_HOT.getKey().equals(moduleCode)) {
                //自定义模块
                if (pageType == 6) {
                    pr.setObj(confMap);
                } else {
                    Map<String, Object> returnMap = new HashMap<String, Object>();
                    returnMap.put("links", this.handlePageLinksShowNumber(confMap, "links", "count","show_type"));
                    pr.setObj(returnMap);
                }
                pr.setState(true);
                pr.setMessage("查询自定义模块数据成功");
            } else if (FixedModuleEnum.FIXED_MODULE_GROUP.getKey().equals(moduleCode)) {
                //商品分组模块
                pr.setObj(confMap);
                pr.setState(true);
                pr.setMessage("查询商品分组模块数据成功");
            } else if (FixedModuleEnum.FIXED_MODULE_ADVERTISING.getKey().equals(moduleCode)) {
                //广告位模块
            }else if(FixedModuleEnum.FIXED_MODULE_HOME_LIKE.getKey().equals(moduleCode)){
                //存储猜你喜欢菜单列表
                List<Map<String, Object>> titleList = new ArrayList<Map<String, Object>>();
                if(confMap != null && !confMap.isEmpty()){
                    List<Map<String, Object>> confList = (List<Map<String, Object>>) confMap.get("conf_list");
                    for (int i = 0; i < confList.size(); i++) {
                        Map<String, Object> confDataMap = confList.get(i);
                        //封装猜你喜欢菜单数据
                        Map<String, Object> titleMap = new HashMap<String, Object>();
                        titleMap.put("title", confDataMap.get("title"));
                        titleMap.put("subtitle", confDataMap.get("subtitle"));
                        titleMap.put("fixed_period", confDataMap.get("fixed_period"));
                        titleMap.put("menu_type", i + 1);
                        titleList.add(titleMap);

                    }
                }
                Map<String,Object> resultMap = new HashMap<String, Object>();
                resultMap.put("title_list",titleList);
                pr.setState(true);
                pr.setMessage("查询首页猜你喜欢菜单数据成功");
                pr.setObj(resultMap);
            } else if (FixedModuleEnum.FIXED_MODULE_HOME_CUSTOM.getKey().equals(moduleCode)) {
                //首页自定义区
                pr.setObj(confMap);
                pr.setState(true);
                pr.setMessage("查询首页自定义区数据成功");

            } else {
                pr.setState(false);
                pr.setMessage("参数不符合规则");
                return pr;
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询猜你喜欢模块商品数据
     *
     * @param request
     * @return
     */
    public ProcessResult queryLikeModuleDataList(HttpServletRequest request) {
        ProcessResult gr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【page_id】");
                return gr;
            }
            if (!params.containsKey("site_id") || StringUtils.isEmpty(params.get("site_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【site_id】");
                return gr;
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            params.put("module_code", "like");
            params.put("group_sort", 0);
            //查询固定模块详细数据
            List<Map<String, Object>> list = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params);
            if (list == null || list.size() <= 0) {
                gr.setState(false);
                gr.setMessage("固定模块数据异常");
                return gr;
            }
            /**
             * 获取固定模块参数
             */
            String moduleBaseConf = StringUtil.append(list, "MODULE_BASE_CONF");
            Map<String, Object> confMap = (Map<String, Object>) Jackson.readJson2Object(moduleBaseConf, Map.class);
            if (!confMap.containsKey("select_type") || StringUtils.isEmpty(confMap.get("select_type"))) {
                gr.setState(false);
                gr.setMessage("固定模块配置信息异常");
                return gr;
            }
            Map<String, Object> searchParams = new HashMap<String, Object>();
            searchParams.put("site_id", params.get("site_id"));
            searchParams.put("sort", confMap.get("sort"));
            searchParams.put("sort_type", StringUtils.isEmpty(confMap.get("sort_type")) ? 1 : confMap.get("sort_type"));
            searchParams.put("count", confMap.get("count"));
            searchParams.put("start_rownum", params.get("start_rownum"));
            int endNum = Integer.parseInt(String.valueOf(params.get("end_rownum")));
            int searchCount = StringUtils.isEmpty(confMap.get("count")) ? 50 : Integer.parseInt(String.valueOf(confMap.get("count")));
            searchParams.put("end_rownum", endNum < searchCount ? endNum : searchCount);
            //获取查询类型
            int selectType = Integer.parseInt(String.valueOf(confMap.get("select_type")));
            int count = 0;
            if (selectType == 1) {
                int recommendType = Integer.parseInt(String.valueOf(confMap.get("recommend_type")));
                if (recommendType == 1) {
                    searchParams.put("product_type_id", confMap.get("product_type_id"));
                } else if (recommendType == 2) {
                    searchParams.put("activity_id", confMap.get("activity_id"));
                } else {
                    gr.setState(false);
                    gr.setMessage("参数不符合规则");
                    return gr;
                }
                count = this.fixedModuleDao.countLikeProductForPage(searchParams);
            } else if (selectType == 2) {
                List<Map<String, Object>> productList = (List<Map<String, Object>>) confMap.get("products");
                if (productList != null && productList.size() > 0) {
                    searchParams.put("products", productList);
                    count = this.fixedModuleDao.countLikeProductForPage(searchParams);
                }
            } else {
                gr.setState(false);
                gr.setMessage("参数不符合规则");
                return gr;
            }
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (count > 0) {
                int number = (int) (Math.random() * 10);
                searchParams.put("number", number % 2 == 1 ? 1 : 2);
                List<Map<String, Object>> likeList = this.fixedModuleDao.listLikeProductForPage(searchParams);
                gr.setMessage("查询【猜你喜欢】模块数据成功");
                returnMap.put("products", likeList);
            } else {
                gr.setMessage("无数据");
                returnMap.put("products", null);
            }
            returnMap.put("total", count);
            returnMap.put("title", confMap.get("title"));
            returnMap.put("fixed_period", confMap.get("fixed_period"));
            gr.setState(true);
            gr.setObj(returnMap);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 查询首页猜你喜欢模块商品数据
     * @param request
     * @return
     */
    public ProcessResult queryHomePageLikeModuleDataList(HttpServletRequest request) {
        ProcessResult gr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【page_id】");
                return gr;
            }
            if (!params.containsKey("site_id") || StringUtils.isEmpty(params.get("site_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【site_id】");
                return gr;
            }
            //菜单类型
            int menuType = 1;
            if (params.containsKey("menu_type") && !StringUtils.isEmpty(params.get("menu_type"))) {
                menuType = Integer.parseInt(params.get("menu_type").toString());
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            params.put("module_code", "home_like");
            params.put("group_sort", 0);
            //查询固定模块详细数据
            List<Map<String, Object>> list = this.fixedModuleDao.listPageFixedModuleDetailByModuleCode(params);
            if (list == null || list.size() <= 0) {
                gr.setState(false);
                gr.setMessage("固定模块数据异常");
                return gr;
            }
            //初始化查询数据参数Map
            Map<String, Object> searchParams = null;
            /**
             * 获取固定模块参数
             */
            String moduleBaseConf = StringUtil.append(list, "MODULE_BASE_CONF");
            Map<String, Object> confDataMap = (Map<String, Object>) Jackson.readJson2Object(moduleBaseConf, Map.class);
            if(confDataMap != null && !confDataMap.isEmpty()){
                List<Map<String, Object>> confList = (List<Map<String, Object>>) confDataMap.get("conf_list");
                for (int i = 0; i < confList.size(); i++) {
                    Map<String, Object> confMap = confList.get(i);
                    //获取查询商品参数
                    if (i == (menuType - 1)) {
                        searchParams = this.handleFixedModuleConfParams(confMap, params);
                    }

                }
            }
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            int count = 0;
            List<Map<String, Object>> likeList = null;
            if(searchParams != null){
                //查询商品数量
                count = this.fixedModuleDao.countLikeProductForPage(searchParams);
                if (count > 0) {
                    likeList = this.fixedModuleDao.listLikeProductForPage(searchParams);
                    gr.setMessage("查询【首页猜你喜欢】模块数据成功");

                } else {
                    gr.setMessage("无数据");
                    returnMap.put("products", null);
                }
            }
            returnMap.put("total", count);
            returnMap.put("products", likeList);
            gr.setState(true);
            gr.setObj(returnMap);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 查询每日上新商品列表
     *
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    private ProcessResult queryUpdateProductList(Map<String, Object> params, int supportFlag) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            /**
             * 每日上新视频
             */
            if (!params.containsKey("video_select_type") || StringUtils.isEmpty(params.get("video_select_type"))) {
                pr.setState(false);
                pr.setMessage("固定模块配置信息异常");
                return pr;
            }
            //获取查询类型
            int videoSelectType = Integer.parseInt(String.valueOf(params.get("video_select_type")));
            if (videoSelectType != 1 && videoSelectType != 2) {
                pr.setState(false);
                pr.setMessage("每日上新视频配置信息不符合规则");
                return pr;
            }
            List<Map<String, Object>> productList = null;
            //设置默认排序方式
            params.put("video_sort", StringUtils.isEmpty(params.get("video_sort")) ? "first_sell_sort_value" : params.get("video_sort"));
            params.put("video_sort_type", StringUtils.isEmpty(params.get("video_sort_type")) ? 1 : params.get("video_sort_type"));
            params.put("video_count", StringUtils.isEmpty(params.get("video_count")) ? 3 : params.get("video_count"));
            if (videoSelectType == 2) {
                //获取所选商品列表
                List<Map<String, Object>> video_products_list = (List<Map<String, Object>>) params.get("video_products");
                if (video_products_list != null && !video_products_list.isEmpty()) {
                    params.put("products", video_products_list);
                    params.remove("product_type_id");
                    productList = this.fixedModuleDao.listUpdateVideoProduct(params);
                }
            } else {
                String sort = String.valueOf(params.get("video_sort"));
                if (sort.indexOf("create_date") >= 0) {
                    params.put("video_sort", "pi." + sort);
                }
                productList = this.fixedModuleDao.listUpdateVideoProduct(params);
            }

            if (productList != null && !productList.isEmpty()) {
                params.put("first_products", productList.get(0).get("PRODUCT_ITEMNUMBER"));
            }
            returnMap.put("video_products", productList);
            /**
             * 每日上新商品
             */
            //设置默认排序方式
            params.put("sort", StringUtils.isEmpty(params.get("sort")) ? "first_sell_sort_value" : params.get("sort"));
            params.put("count", StringUtils.isEmpty(params.get("count")) ? supportFlag == 1 ? 8 : 5 : params.get("count"));
            //封装返回结果
            returnMap.put("products", this.fixedModuleDao.listUpdateProduct(params));
            returnMap.put("title", params.get("title"));
            if (supportFlag == 1) {
                returnMap.put("sub_title", params.get("sub_title"));
            }
            returnMap.put("fixed_period", params.get("fixed_period"));
            returnMap.put("is_auto_play", params.get("is_auto_play"));
            returnMap.put("is_continued_play", params.get("is_continued_play"));
            returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
            pr.setState(true);
            pr.setMessage("查询【每日上新-商品】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询商品品牌列表
     *
     * @param params
     * @return
     */
    private ProcessResult queryProductBrandList(Map<String, Object> params, int supportFlag) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (!params.containsKey("select_type") || StringUtils.isEmpty(params.get("select_type"))) {
                pr.setState(false);
                pr.setMessage("固定模块配置信息异常");
                return pr;
            }
            if (!params.containsKey("sort") || StringUtils.isEmpty(params.get("sort"))) {
                //设置默认排序方式
                params.put("sort", "create_date asc");
            }
            List<Map<String, Object>> brandList = null;
            //获取查询类型
            int selectType = Integer.parseInt(String.valueOf(params.get("select_type")));
            if (selectType == 1) {
                if (params.containsKey("product_type_ids") && !StringUtils.isEmpty(params.get("product_type_ids"))) {
                    String productTypeIds = String.valueOf(params.get("product_type_ids"));
                    params.put("product_type_ids", StringUtil.splitStrToArray(productTypeIds, ","));
                }
                brandList = this.fixedModuleDao.listProductBrand(params);
            } else if (selectType == 2) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("brand_ids");
                if (list != null && list.size() > 0) {
                    params.put("brand_ids", list);
                    brandList = this.fixedModuleDao.listProductBrand(params);
                }
            } else {
                pr.setState(false);
                pr.setMessage("商品品牌模块参数不符合规则");
                return pr;
            }
            //封装返回结果
            returnMap.put("brands", brandList);
            returnMap.put("show_type", params.get("show_type"));
            returnMap.put("fixed_period", params.get("fixed_period"));
            returnMap.put("switch_interval", params.get("switch_interval"));
            returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
            if (supportFlag == 2) {
                returnMap.put("sub_title", StringUtils.isEmpty(params.get("sub_title")) ? "" : params.get("sub_title"));
            }
            pr.setState(true);
            pr.setMessage("查询【品牌街】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询热卖top商品数据
     *
     * @param params
     * @return
     */
    private ProcessResult queryTopProductList(Map<String, Object> params, int supportFlag) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            //初始查询参数结果集
            Map<String, Object> searchMap = new HashMap<String, Object>(16);
            searchMap.put("site_id", params.get("site_id"));
            searchMap.put("is_discount", StringUtils.isEmpty(params.get("is_discount")) ? "0" : params.get("is_discount"));
            searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "product_count desc" : params.get("sort"));
            searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
            searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 6 : params.get("count"));
            searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
            searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
            searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
            searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
            searchMap.put("products", params.get("products"));
            //查询商品数据
            returnMap.put("products", this.queryAutoSelectProductList(searchMap, supportFlag == 2 ? new String[0] : new String[]{"title"}));
            //封装返回结果
            returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
            returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
            returnMap.put("is_discount", StringUtils.isEmpty(params.get("is_discount")) ? 0 : params.get("is_discount"));
            returnMap.put("fixed_period", StringUtils.isEmpty(params.get("fixed_period")) ? 0 : params.get("fixed_period"));
            pr.setState(true);
            pr.setMessage("查询【热卖TOP】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询特惠抢购商品数据
     *
     * @param params
     * @return
     */
    private ProcessResult queryBuyProductList(Map<String, Object> params, int supportFlag) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            //初始查询参数结果集
            Map<String, Object> searchMap = new HashMap<String, Object>(16);
            searchMap.put("site_id", params.get("site_id"));
            searchMap.put("is_discount", StringUtils.isEmpty(params.get("is_discount")) ? "0" : params.get("is_discount"));
            searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
            searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
            searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 6 : params.get("count"));
            searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
            searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
            searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
            searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
            searchMap.put("products", params.get("products"));
            //查询商品数据
            returnMap.put("products", this.queryAutoSelectProductList(searchMap, supportFlag == 2 ? new String[0] : new String[]{"title"}));
            //封装返回结果
            returnMap.put("title", params.get("title"));
            returnMap.put("link", params.get("link"));
            returnMap.put("is_discount", StringUtils.isEmpty(params.get("is_discount")) ? 0 : params.get("is_discount"));
            pr.setState(true);
            pr.setMessage("查询【特惠抢购】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询商品分类商品数据
     *
     * @param params
     * @return
     */
    private ProcessResult queryClassifyProductList(Map<String, Object> params, int supportFlag, int pageType) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (supportFlag == 1) {
                returnMap.put("title", params.get("title"));
                returnMap.put("color", params.get("color"));
                returnMap.put("links", this.handlePageLinksShowNumber(params, "links", "count", "show_type"));
                //初始查询参数结果集
                Map<String, Object> searchMap = new HashMap<String, Object>(16);
                if (pageType == 2) {
                    returnMap.put("left_links", this.handlePageLinksShowNumber(params, "left_links", "left_count", "left_show_type"));
                    /**
                     * 获取右侧商品数据
                     */
                    searchMap.put("site_id", params.get("site_id"));
                    searchMap.put("sort", StringUtils.isEmpty(params.get("right_sort")) ? "overall" : params.get("right_sort"));
                    searchMap.put("sort_type", StringUtils.isEmpty(params.get("right_sort_type")) ? 1 : params.get("right_sort_type"));
                    searchMap.put("count", StringUtils.isEmpty(params.get("right_count")) ? 4 : params.get("right_count"));
                    searchMap.put("select_type", StringUtils.isEmpty(params.get("right_select_type")) ? 1 : params.get("right_select_type"));
                    searchMap.put("recommend_type", StringUtils.isEmpty(params.get("right_recommend_type")) ? 1 : params.get("right_recommend_type"));
                    searchMap.put("product_type_id", StringUtils.isEmpty(params.get("right_product_type_id")) ? 0 : params.get("right_product_type_id"));
                    searchMap.put("activity_id", StringUtils.isEmpty(params.get("right_activity_id")) ? 0 : params.get("right_activity_id"));
                    searchMap.put("products", params.get("right_products"));
                    //查询商品数据
                    returnMap.put("right_products", this.queryAutoSelectProductList(searchMap, new String[0]));
                    /**
                     * 获取底端商品数据
                     */
                    searchMap = new HashMap<String, Object>(16);
                    searchMap.put("site_id", params.get("site_id"));
                    searchMap.put("count", StringUtils.isEmpty(params.get("bottom_count")) ? 3 : params.get("bottom_count"));
                    searchMap.put("sort_type", StringUtils.isEmpty(params.get("bottom_sort_type")) ? 1 : params.get("bottom_sort_type"));
                    searchMap.put("select_type", 2);
                    searchMap.put("products", params.get("bottom_products"));
                    //查询商品数据
                    String[] attr = {"title", "selling"};
                    returnMap.put("bottom_products", this.queryAutoSelectProductList(searchMap, attr));
                } else if (pageType == 3) {
                    /**左侧商品*/
                    searchMap.put("site_id", params.get("site_id"));
                    searchMap.put("sort", StringUtils.isEmpty(params.get("left_sort")) ? "overall" : params.get("left_sort"));
                    searchMap.put("sort_type", StringUtils.isEmpty(params.get("left_sort_type")) ? 1 : params.get("left_sort_type"));
                    searchMap.put("count", StringUtils.isEmpty(params.get("left_count")) ? 4 : params.get("left_count"));
                    searchMap.put("select_type", StringUtils.isEmpty(params.get("left_select_type")) ? 1 : params.get("left_select_type"));
                    searchMap.put("recommend_type", StringUtils.isEmpty(params.get("left_recommend_type")) ? 1 : params.get("left_recommend_type"));
                    searchMap.put("product_type_id", StringUtils.isEmpty(params.get("left_product_type_id")) ? 0 : params.get("left_product_type_id"));
                    searchMap.put("activity_id", StringUtils.isEmpty(params.get("left_activity_id")) ? 0 : params.get("left_activity_id"));
                    searchMap.put("products", params.get("left_products"));
                    //查询商品数据
                    returnMap.put("left_products", this.queryAutoSelectProductList(searchMap, new String[0]));

                    /**右侧商品*/
                    searchMap.put("sort", StringUtils.isEmpty(params.get("right_sort")) ? "overall" : params.get("right_sort"));
                    searchMap.put("sort_type", StringUtils.isEmpty(params.get("right_sort_type")) ? 1 : params.get("right_sort_type"));
                    searchMap.put("count", StringUtils.isEmpty(params.get("right_count")) ? 6 : params.get("right_count"));
                    searchMap.put("select_type", StringUtils.isEmpty(params.get("right_select_type")) ? 1 : params.get("right_select_type"));
                    searchMap.put("recommend_type", StringUtils.isEmpty(params.get("right_recommend_type")) ? 1 : params.get("right_recommend_type"));
                    searchMap.put("product_type_id", StringUtils.isEmpty(params.get("right_product_type_id")) ? 0 : params.get("right_product_type_id"));
                    searchMap.put("activity_id", StringUtils.isEmpty(params.get("right_activity_id")) ? 0 : params.get("right_activity_id"));
                    searchMap.put("products", params.get("right_products"));
                    //查询商品数据
                    returnMap.put("right_products", this.queryAutoSelectProductList(searchMap, new String[0]));
                } else if (pageType == 7) {
                    returnMap.put("sub_title", StringUtils.isEmpty(params.get("sub_title")) ? "" : params.get("sub_title"));
                    returnMap.put("background", StringUtils.isEmpty(params.get("background")) ? "" : params.get("background"));
                    /**商品数据*/
                    searchMap.put("site_id", params.get("site_id"));
                    searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
                    searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
                    searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 6 : params.get("count"));
                    searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
                    searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
                    searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
                    searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
                    searchMap.put("products", params.get("products"));
                    //查询商品数据
                    returnMap.put("products", this.queryAutoSelectProductList(searchMap, new String[0]));
                }
            } else {
                returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
                if (pageType == 2) {
                    returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
                    returnMap.put("start_color", StringUtils.isEmpty(params.get("start_color")) ? "" : params.get("start_color"));
                    returnMap.put("end_color", StringUtils.isEmpty(params.get("end_color")) ? "" : params.get("end_color"));
                    returnMap.put("advert", this.handlePageLinksShowNumber(params, "advert", "advert_count", "show_type"));
                } else if (pageType == 3) {
                    returnMap.put("background", StringUtils.isEmpty(params.get("background")) ? "" : params.get("background"));
                    returnMap.put("sub_title", StringUtils.isEmpty(params.get("sub_title")) ? "" : params.get("sub_title"));
                    returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
                }else if (pageType == 7) {
                    returnMap.put("background", StringUtils.isEmpty(params.get("background")) ? "" : params.get("background"));
                    returnMap.put("sub_title", StringUtils.isEmpty(params.get("sub_title")) ? "" : params.get("sub_title"));
                    returnMap.put("color", params.get("color"));
                    returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
                    returnMap.put("style", StringUtils.isEmpty(params.get("style")) ? 1 : params.get("style"));
                }
                //初始查询参数结果集
                Map<String, Object> searchMap = new HashMap<String, Object>(16);
                searchMap.put("site_id", params.get("site_id"));
                searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 6 : params.get("count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
                searchMap.put("products", params.get("products"));
                //查询商品数据
                returnMap.put("products", this.queryAutoSelectProductList(searchMap, new String[0]));

            }
            pr.setState(true);
            pr.setMessage("查询【商品分类】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询预售抢先商品数据
     * @param params
     * @param supportFlag
     * @param pageType
     * @return
     */
    private ProcessResult queryPreSellProductList(Map<String, Object> params, int supportFlag, int pageType) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            params.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
            params.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
            if(pageType == 2){
                params.put("count", StringUtils.isEmpty(params.get("count")) ? 20 : params.get("count"));
            }
            if (!params.containsKey("select_type") || StringUtils.isEmpty(params.get("select_type"))) {
                pr.setState(false);
                pr.setMessage("固定模块配置信息异常");
                return pr;
            }

            //获取查询类型
            int selectType = Integer.parseInt(String.valueOf(params.get("select_type")));
            if (selectType != 1 && selectType != 2) {
                pr.setState(false);
                pr.setMessage("参数不符合规则");
                return pr;
            }
            List<Map<String, Object>> productList = null;
            if (selectType == 1) {
                if (!params.containsKey("recommend_type") || StringUtils.isEmpty(params.get("recommend_type"))) {
                    pr.setState(false);
                    pr.setMessage("模块[预售抢先]配置信息异常");
                    return pr;
                }
                productList = this.fixedModuleDao.listPreSellProduct(params);
            } else if (selectType == 2) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("products");
                if (list != null && list.size() > 0) {
                    params.put("products", list);
                    productList = this.fixedModuleDao.listPreSellProduct(params);
                }

            }
            //封装返回结果
            returnMap.put("products", productList);
            if(pageType == 2){
                returnMap.put("title", params.get("title"));
            }else{
                returnMap.put("explain", params.get("explain"));
                returnMap.put("is_discount", params.get("is_discount"));
            }
            returnMap.put("sub_title", params.get("sub_title"));
            returnMap.put("goods_time", params.get("goods_time"));
            returnMap.put("fixed_period", params.get("fixed_period"));
            returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
            pr.setState(true);
            pr.setMessage("查询【预售抢先】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询实力定制商品数据
     *
     * @param params
     * @return
     */
    private ProcessResult queryCustomProductList(Map<String, Object> params, int supportFlag,String moduleCode) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            //初始查询参数结果集
            Map<String, Object> searchMap = new HashMap<String, Object>(16);
            searchMap.put("is_custom", 1);
            searchMap.put("site_id", params.get("site_id"));
            searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
            searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
            searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 7 : params.get("count"));
            searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
            searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
            searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
            searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
            searchMap.put("products", params.get("products"));
            //查询商品数据
            returnMap.put("products", this.queryAutoSelectProductList(searchMap, new String[0]));
            if (FixedModuleEnum.FIXED_MODULE_CUSTOM.getKey().equals(moduleCode)) {
                //封装返回结果
                returnMap.put("title", params.get("title"));
                returnMap.put("sub_title", params.get("sub_title"));
                returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
            } else if (FixedModuleEnum.FIXED_MODULE_MAIN_CUSTOM.getKey().equals(moduleCode)) {

            } else if (FixedModuleEnum.FIXED_MODULE_RANKING_CUSTOM.getKey().equals(moduleCode)) {
                returnMap.put("title", params.get("title"));
                returnMap.put("custom_way", params.get("custom_way"));
            } else if (FixedModuleEnum.FIXED_MODULE_HOT_CUSTOM.getKey().equals(moduleCode)) {
                returnMap.put("title", params.get("title"));
            }
            returnMap.put("fixed_period", params.get("fixed_period"));
            pr.setState(true);
            pr.setMessage("查询【实力定制】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询最新童鞋模块商品列表
     *
     * @param params
     * @param supportFlag
     * @return
     */
    private ProcessResult queryNewShoesModuleDataList(Map<String, Object> params, int supportFlag) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            /**
             * 基础设置
             */
            returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
            returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
            returnMap.put("fixed_period", StringUtils.isEmpty(params.get("fixed_period")) ? "" : params.get("fixed_period"));
            returnMap.put("links", this.handlePageLinksShowNumber(params, "links", "count", "show_type"));
            if (supportFlag == 1) {
                /**
                 * 左侧广告设置
                 */
                returnMap.put("left_links", this.handlePageLinksShowNumber(params, "left_links", "left_count", "left_show_type"));
                /**
                 * 中间商品设置
                 */
                Map<String, Object> searchMap = new HashMap<String, Object>();
                searchMap.put("site_id", params.get("site_id"));
                searchMap.put("sort", StringUtils.isEmpty(params.get("middle_sort")) ? "overall" : params.get("middle_sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("middle_sort_type")) ? 1 : params.get("middle_sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("middle_count")) ? 6 : params.get("middle_count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("middle_select_type")) ? 1 : params.get("middle_select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("middle_recommend_type")) ? 1 : params.get("middle_recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("middle_product_type_id")) ? 0 : params.get("middle_product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("middle_activity_id")) ? 0 : params.get("middle_activity_id"));
                searchMap.put("products", params.get("middle_products"));
                //查询商品数据
                returnMap.put("middle_products", this.queryAutoSelectProductList(searchMap, new String[]{"title"}));
                /**
                 * 右商品设置
                 */
                searchMap.put("sort", StringUtils.isEmpty(params.get("right_sort")) ? "overall" : params.get("right_sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("right_sort_type")) ? 1 : params.get("right_sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("right_count")) ? 5 : params.get("right_count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("right_select_type")) ? 1 : params.get("right_select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("right_recommend_type")) ? 1 : params.get("right_recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("right_product_type_id")) ? 0 : params.get("right_product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("right_activity_id")) ? 0 : params.get("right_activity_id"));
                searchMap.put("products", params.get("right_products"));
                //查询商品数据
                returnMap.put("right_products", this.queryAutoSelectProductList(searchMap, new String[]{"title"}));
            } else {
                /**
                 * 商品设置
                 */
                Map<String, Object> searchMap = new HashMap<String, Object>();
                searchMap.put("site_id", params.get("site_id"));
                searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("products_count")) ? 6 : params.get("products_count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
                searchMap.put("products", params.get("products"));
                //查询商品数据
                returnMap.put("products", this.queryAutoSelectProductList(searchMap, new String[]{"title", "selling"}));
            }
            pr.setState(true);
            pr.setMessage("查询【最新童鞋】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询礼品配件模块商品列表
     *
     * @param params
     * @param supportFlag
     * @return
     */
    private ProcessResult queryGiftModuleDataList(Map<String, Object> params, int supportFlag) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            /**
             * 基础设置
             */
            returnMap.put("link", StringUtils.isEmpty(params.get("link")) ? "" : params.get("link"));
            returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
            returnMap.put("fixed_period", StringUtils.isEmpty(params.get("fixed_period")) ? "" : params.get("fixed_period"));
            if (supportFlag == 1) {
                /**
                 * 基础设置
                 */
                returnMap.put("links", this.handlePageLinksShowNumber(params, "links", "count", "show_type"));
                /**
                 * 左侧广告设置
                 */
                returnMap.put("left_links", this.handlePageLinksShowNumber(params, "left_links", "left_count", "left_show_type"));
                /**
                 * 中间商品设置
                 */
                Map<String, Object> searchMap = new HashMap<String, Object>();
                searchMap.put("site_id", params.get("site_id"));
                searchMap.put("sort", StringUtils.isEmpty(params.get("middle_sort")) ? "overall" : params.get("middle_sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("middle_sort_type")) ? 1 : params.get("middle_sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("middle_count")) ? 6 : params.get("middle_count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("middle_select_type")) ? 1 : params.get("middle_select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("middle_recommend_type")) ? 1 : params.get("middle_recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("middle_product_type_id")) ? 0 : params.get("middle_product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("middle_activity_id")) ? 0 : params.get("middle_activity_id"));
                searchMap.put("products", params.get("middle_products"));
                //查询商品数据
                returnMap.put("middle_products", this.queryAutoSelectProductList(searchMap, new String[]{"title"}));
                /**
                 * 右商品设置
                 */
                searchMap.put("sort", StringUtils.isEmpty(params.get("right_sort")) ? "overall" : params.get("right_sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("right_sort_type")) ? 1 : params.get("right_sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("right_count")) ? 5 : params.get("right_count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("right_select_type")) ? 1 : params.get("right_select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("right_recommend_type")) ? 1 : params.get("right_recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("right_product_type_id")) ? 0 : params.get("right_product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("right_activity_id")) ? 0 : params.get("right_activity_id"));
                searchMap.put("products", params.get("right_products"));
                //查询商品数据
                returnMap.put("right_products", this.queryAutoSelectProductList(searchMap, new String[]{"title"}));
            } else {
                /**
                 * 中间商品设置
                 */
                Map<String, Object> searchMap = new HashMap<String, Object>();
                searchMap.put("site_id", params.get("site_id"));
                searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
                searchMap.put("sort_type", StringUtils.isEmpty(params.get("sort_type")) ? 1 : params.get("sort_type"));
                searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 6 : params.get("count"));
                searchMap.put("select_type", StringUtils.isEmpty(params.get("select_type")) ? 1 : params.get("select_type"));
                searchMap.put("recommend_type", StringUtils.isEmpty(params.get("recommend_type")) ? 1 : params.get("recommend_type"));
                searchMap.put("product_type_id", StringUtils.isEmpty(params.get("product_type_id")) ? 0 : params.get("product_type_id"));
                searchMap.put("activity_id", StringUtils.isEmpty(params.get("activity_id")) ? 0 : params.get("activity_id"));
                searchMap.put("products", params.get("products"));
                //查询商品数据
                returnMap.put("products", this.queryAutoSelectProductList(searchMap, new String[]{"title", "selling"}));
            }
            pr.setState(true);
            pr.setMessage("查询【礼品配件】模块数据成功");
            pr.setObj(returnMap);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询活动模块数据列表
     *
     * @param params
     * @param supportFlag
     * @return
     */
    private ProcessResult queryActivityModuleDataList(Map<String, Object> params, int supportFlag, int pageType) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            if (pageType == 5) { //新品秒杀 爆款秒批页面
                Map<String, Object> activityMap = new HashMap<String, Object>();
                if (!params.containsKey("activity_id") || StringUtils.isEmpty(params.get("activity_id"))) {
                    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("start_flag", 1);
                    searchMap.put("site_id", params.get("site_id"));
                    List<Map<String, Object>> list = this.fixedModuleDao.listActivityById(searchMap);
                    if (list != null && !list.isEmpty()) {
                        activityMap = list.get(0);
                        params.put("activity_id", activityMap.get("ACTIVITY_ID"));
                    }
                } else {
                    //获取活动基本信息
                    List<Map<String, Object>> activityList = this.fixedModuleDao.listActivityById(params);
                    if (activityList != null && !activityList.isEmpty()) {
                        activityMap = activityList.get(0);
                    }
                }
                returnMap.put("activity_state", StringUtils.isEmpty(activityMap.get("START_FLAG")) ? "" : activityMap.get("START_FLAG"));
                returnMap.put("begin_date", StringUtils.isEmpty(activityMap.get("BEGIN_DATE")) ? "" : activityMap.get("BEGIN_DATE"));
                returnMap.put("end_date", StringUtils.isEmpty(activityMap.get("END_DATE")) ? "" : activityMap.get("END_DATE"));
                returnMap.put("now_date", StringUtils.isEmpty(activityMap.get("NOW_DATE")) ? "" : activityMap.get("NOW_DATE"));
                returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
                //获取活动商品信息
                params.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
                returnMap.put("products", this.fixedModuleDao.listActivityProduct(params));
                pr.setObj(returnMap);
            } else {
                List<Map<String, Object>> activityList = (List<Map<String, Object>>) params.get("activities");
                if (activityList != null && !activityList.isEmpty()) {
                    //查询活动信息
                    List<Map<String, Object>> list = this.fixedModuleDao.listActivityById(params);
                    for (Map<String, Object> activityMap : activityList) {
                        String activityId = StringUtils.isEmpty(activityMap.get("activity_id")) ? "0" : String.valueOf(activityMap.get("activity_id"));
                        for (Map<String, Object> activity : list) {
                            if (String.valueOf(activity.get("ACTIVITY_ID")).equals(activityId)) {
                                activityMap.put("begin_date", activity.get("BEGIN_DATE"));
                                activityMap.put("end_date", activity.get("END_DATE"));
                                activityMap.put("start_flag", activity.get("START_FLAG"));
                                activityMap.put("now_date", activity.get("NOW_DATE"));
                                break;
                            }
                        }
                    }
                } else {
                    Map<String, Object> activityMap = new HashMap<String, Object>();
                    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("start_flag", 1);
                    searchMap.put("site_id", params.get("site_id"));
                    List<Map<String, Object>> list = this.fixedModuleDao.listActivityById(searchMap);
                    if (list != null && !list.isEmpty()) {
                        activityMap = list.get(0);
                    }
                    activityList = new ArrayList<Map<String, Object>>();
                    searchMap = new HashMap<String, Object>();
                    searchMap.put("activity_id", StringUtils.isEmpty(activityMap.get("ACTIVITY_ID")) ? 0 : activityMap.get("ACTIVITY_ID"));
                    searchMap.put("begin_date", StringUtils.isEmpty(activityMap.get("BEGIN_DATE")) ? "" : activityMap.get("BEGIN_DATE"));
                    searchMap.put("end_date", StringUtils.isEmpty(activityMap.get("END_DATE")) ? "" : activityMap.get("END_DATE"));
                    searchMap.put("now_date", StringUtils.isEmpty(activityMap.get("NOW_DATE")) ? "" : activityMap.get("NOW_DATE"));
                    searchMap.put("start_flag", StringUtils.isEmpty(activityMap.get("START_FLAG")) ? "" : activityMap.get("START_FLAG"));
                    searchMap.put("title", "活动标题");
                    activityList.add(searchMap);
                }
                returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
                returnMap.put("activities", activityList);
                pr.setObj(returnMap);
            }
            pr.setState(true);
            pr.setMessage("查询【活动模块】模块数据成功");

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询活动模块商品列表
     *
     * @param request
     * @return
     */
    public GridResult queryActivityModuleProductList(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("activity_id") || StringUtils.isEmpty(params.get("activity_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【activity_id】");
                return gr;
            }
            if (!params.containsKey("site_id") || StringUtils.isEmpty(params.get("site_id"))) {
                gr.setState(false);
                gr.setMessage("缺少参数【site_id】");
                return gr;
            }
            if (!params.containsKey("sort") || StringUtils.isEmpty(params.get("sort"))) {
                params.put("sort", "overall");
            }
            //分页参数
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int count = this.fixedModuleDao.countActivityProductForPage(params);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if (count > 0) {
                list = this.fixedModuleDao.listActivityProductForPage(params);
                gr.setMessage("查询活动模块商品列表成功");
            } else {
                gr.setMessage("无商品列表成功");
            }
            gr.setState(true);
            gr.setObj(list);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 查询往期预售模块数据列表
     * @param params
     * @param supportFlag
     * @param pageType
     * @return
     */
    private ProcessResult queryPastPreSellModuleDataList(Map<String, Object> params, int supportFlag,int pageType) {
        ProcessResult pr = new ProcessResult();
        try {
            //初始返回结果集
            Map<String, Object> returnMap = new HashMap<String, Object>(16);
            //封装查询往期预售商品参数
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("site_id", params.get("site_id"));
            searchMap.put("sort", StringUtils.isEmpty(params.get("sort")) ? "overall" : params.get("sort"));
            searchMap.put("count", StringUtils.isEmpty(params.get("count")) ? 15 : params.get("count"));
            searchMap.put("period_num", StringUtils.isEmpty(params.get("period_num")) ? 5 : params.get("period_num"));
            //查询数据
            returnMap.put("title", StringUtils.isEmpty(params.get("title")) ? "" : params.get("title"));
            returnMap.put("product", this.fixedModuleDao.listPastPreSellProduct(searchMap));
            pr.setState(true);
            pr.setObj(returnMap);
            pr.setMessage("查询【往期预售】模块数据成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 处理处理页面链接展示数量
     *
     * @param confMap
     * @param linksObj
     * @param countObj
     * @return
     */
    private List<Map<String, Object>> handlePageLinksShowNumber(Map<String, Object> confMap, Object linksObj, Object countObj, Object showObj) {
        //初始返回结果集
        List<Map<String, Object>> linksList = null;
        try {
            //控制链接数量，返回指定数量链接
            if (confMap.containsKey(linksObj) && !StringUtils.isEmpty(confMap.get(linksObj))) {
                linksList = (List<Map<String, Object>>) confMap.get(linksObj);
                int count = StringUtils.isEmpty(confMap.get(countObj)) ? linksList.size() : Integer.parseInt(String.valueOf(confMap.get(countObj)));
                int showType = StringUtils.isEmpty(confMap.get(showObj)) ? 1 : Integer.parseInt(String.valueOf(confMap.get(showObj)));
                if(showType == 2){
                    Collections.shuffle(linksList);
                }
                linksList = linksList.subList(0, count <= linksList.size() ? count : linksList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linksList;
    }

    /**
     * 获取自动选择商品列表
     *
     * @param searchParams
     * @return
     */
    private List<Map<String, Object>> queryAutoSelectProductList(Map<String, Object> searchParams, String[] attr) {
        //初始返回结果集
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        try {
            //获取推荐方式
            int isCustom = StringUtils.isEmpty(searchParams.get("is_custom")) ? 0 : Integer.parseInt(String.valueOf(searchParams.get("is_custom")));
            int selectType = Integer.parseInt(String.valueOf(searchParams.get("select_type")));
            if (selectType == 1) {
                String sort = String.valueOf(searchParams.get("sort"));
                if (sort.indexOf("discount_price") >= 0) {
                    searchParams.put("discount_price", searchParams.get("sort"));
                }
                //获取宝贝类型
                int recommendType = Integer.parseInt(String.valueOf(searchParams.get("recommend_type")));
                if (recommendType == 1) {
                    searchParams.remove("activity_id");
                } else if (recommendType == 2) {
                    searchParams.remove("product_type_id");
                } else {
                    return returnList;
                }
                //查询商品列表
                returnList = isCustom == 0 ? this.fixedModuleDao.listAutoSelectProduct(searchParams) : this.fixedModuleDao.listAutoSelectCustomProduct(searchParams);
            } else if (selectType == 2) {
                List<Map<String, Object>> productList = (List<Map<String, Object>>) searchParams.get("products");
                if (productList != null && !productList.isEmpty()) {
                    searchParams.put("products", productList);
                    //查询商品列表
                    returnList = isCustom == 0 ? this.fixedModuleDao.listManualSelectProduct(searchParams) : this.fixedModuleDao.listManualSelectCustomProduct(searchParams);
                    if (attr.length > 0) {
                        for (Map<String, Object> returnMap : returnList) {
                            String number = String.valueOf(returnMap.get("PRODUCT_ITEMNUMBER"));
                            for (Map<String, Object> productMap : productList) {
                                if (String.valueOf(productMap.get("product_itemnumber")).equals(number)) {
                                    for (String str : attr) {
                                        returnMap.put(str.toUpperCase(), StringUtils.isEmpty(productMap.get(str)) ? "" : productMap.get(str));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                return returnList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * 编辑固定模块状态
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editFixedModuleState(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少请求参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (!params.containsKey("page_id") || StringUtils.isEmpty(params.get("page_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【page_id】");
                return pr;
            }
            if (!params.containsKey("module_code") || StringUtils.isEmpty(params.get("module_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【module_code】");
                return pr;
            }
            int count = this.fixedModuleDao.updatePageFixedModuleState(params);
            if (count <= 0) {
                throw new RuntimeException("编辑固定模块状态失败");
            }
            pr.setState(true);
            pr.setMessage("编辑固定模块状态成功");
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 查询最新十条定制订单列表
     * @param request
     * @return
     */
    public ProcessResult queryNewCustomOrderList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<Map<String, Object>> list = this.fixedModuleDao.listNewCustomOrder(params);
            pr.setState(true);
            pr.setMessage("查询最新定制订单列表");
            pr.setObj(list);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 处理固定模块配置参数，用于查询使用
     * @param confMap
     * @param params
     * @return
     */
    public Map<String, Object> handleFixedModuleConfParams(Map<String, Object> confMap, Map<String, Object> params) {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        try {
            if (!confMap.containsKey("select_type") || StringUtils.isEmpty(confMap.get("select_type"))) {
                return null;
            }
            int number = (int) (Math.random() * 10);
            searchParams.put("number", number % 2 == 1 ? 1 : 2);
            searchParams.put("site_id", params.get("site_id"));
            searchParams.put("sort", confMap.get("sort"));
            searchParams.put("sort_type", StringUtils.isEmpty(confMap.get("sort_type")) ? 1 : confMap.get("sort_type"));
            searchParams.put("count", confMap.get("count"));
            searchParams.put("start_rownum", params.get("start_rownum"));
            int endNum = Integer.parseInt(String.valueOf(params.get("end_rownum")));
            int searchCount = StringUtils.isEmpty(confMap.get("count")) ? 50 : Integer.parseInt(String.valueOf(confMap.get("count")));
            searchParams.put("end_rownum", endNum < searchCount ? endNum : searchCount);
            //获取查询类型
            int selectType = Integer.parseInt(String.valueOf(confMap.get("select_type")));
            if (selectType == 1) {
                int recommendType = Integer.parseInt(String.valueOf(confMap.get("recommend_type")));
                if (recommendType == 1) {
                    searchParams.put("product_type_id", confMap.get("product_type_id"));
                } else if (recommendType == 2) {
                    searchParams.put("activity_id", confMap.get("activity_id"));
                } else {
                    return null;
                }
            } else if (selectType == 2) {
                List<Map<String, Object>> productList = (List<Map<String, Object>>) confMap.get("products");
                if (productList != null && productList.size() > 0) {
                    searchParams.put("products", productList);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return searchParams;
    }

}
