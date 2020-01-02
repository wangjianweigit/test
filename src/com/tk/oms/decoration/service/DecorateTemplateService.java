package com.tk.oms.decoration.service;

import com.tk.oms.decoration.dao.DecoratePlatformDao;
import com.tk.oms.decoration.dao.DecorateTemplateDao;
import com.tk.oms.decoration.dto.AppIcoConfigDTO;
import com.tk.oms.decoration.dto.AppIcoConfigParamDTO;
import com.tk.oms.platform.dao.FixedModuleDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台装修管理
 * @author zhenghui
 */
@Service("DecorateTemplateService")
public class DecorateTemplateService {

    @Resource
    private DecorateTemplateDao decorateTemplateDao;

    @Resource
    private DecoratePlatformDao decoratePlatformDao;

    @Resource
    private FixedModuleDao fixedModuleDao;

    /**
     * 分页获取装修模板列表
     * @param request
     * @return
     */
    public GridResult queryDecorateTemplateListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数 ");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                gr.setState(false);
                gr.setMessage("当前功能需要站点授权，请联系管理员");
                return gr;
            }
            if (params.containsKey("states")) {
                String[] states = ((String) params.get("states")).split(",");
                params.put("states", states);
            }
            int total = this.decorateTemplateDao.queryListForCount(params);
            List<Map<String, Object>> list = this.decorateTemplateDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取装修模板列表成功");
                gr.setObj(list);
            } else {
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
     * 添加装修模板
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addDecorateTemplate(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("template_name"))){
                pr.setState(false);
                pr.setMessage("缺少template_name参数 ");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("effective_start_date"))){
                pr.setState(false);
                pr.setMessage("缺少effective_start_date参数 ");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            //判断模板名称是否存在
            if(this.decorateTemplateDao.queryCountByName(params) > 0){
                pr.setState(false);
                pr.setMessage("装修模板名称已存在");
                return pr;
            }
            if (this.decorateTemplateDao.insert(params) > 0) {
                //封装装修页面创建所需参数
                Map<String, Object> paramMap = new HashMap<String, Object>();
                /**系统首页*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("public_user_site_id", Integer.parseInt(params.get("site_id").toString()));
                paramMap.put("public_user_id", Integer.parseInt(params.get("public_user_id").toString()));
                paramMap.put("support_flag", params.get("support_flag").toString());
                paramMap.put("template_id", Integer.parseInt(params.get("id").toString()));
                paramMap.put("page_name", "首页");
                paramMap.put("page_state", "2");
                paramMap.put("page_type", 2);
                paramMap.put("home_page_flag", "1");
                paramMap.put("remark", "自动创建系统首页");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);

                /**新品首发*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("page_name", "新品热卖");
                paramMap.put("page_type", 3);
                paramMap.put("remark", "自动创建新品热卖");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);

                /**每周秒杀*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("page_name", "每周秒杀");
                paramMap.put("page_type", 4);
                paramMap.put("remark", "自动创建每周秒杀");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);

                /**爆款秒批*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("page_name", "爆款秒批");
                paramMap.put("page_type", 5);
                paramMap.put("remark", "自动创建爆款秒批");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);

                /**预售抢先*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("page_name", "预售抢先");
                paramMap.put("page_type", 6);
                paramMap.put("remark", "自动创建预售抢先");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);
                /**潮童搭配*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("page_name", "潮童搭配");
                paramMap.put("page_type", 7);
                paramMap.put("remark", "自动创建潮童搭配");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);

                /**定制服务*/
                paramMap.put("page_id", decoratePlatformDao.queryPageId(params));
                paramMap.put("page_name", "定制服务");
                paramMap.put("page_type", 8);
                paramMap.put("remark", "自动创建定制服务");
                decoratePlatformDao.insertPage(paramMap);
                //复制首页固定模板
                this.fixedModuleDao.insertInitPageFixedModule(paramMap);
                //复制首页固定模板详细数据
                this.fixedModuleDao.insertInitPageFixedModuleDetail(paramMap);
                //复制首页布局数据
                this.fixedModuleDao.insertInitPageLayout(paramMap);

                pr.setState(true);
                pr.setMessage("添加装修模板成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取装修模板信息
     * @param request
     * @return
     */
    public ProcessResult queryDecorateTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数 ");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            Map<String, Object> decorate = this.decorateTemplateDao.queryById(Long.parseLong(params.get("id").toString()));
            if(decorate != null){
                pr.setMessage("获取装修模板成功");
                pr.setObj(decorate);
            }else{
                pr.setMessage("装修模板不存在");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 编辑装修模板
     * @param request
     * @return
     */
    public ProcessResult editDecorateTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数 ");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            Map<String, Object> decorate = this.decorateTemplateDao.queryById(Long.parseLong(params.get("id").toString()));
            if(decorate == null){
                pr.setState(false);
                pr.setMessage("装修模板不存在");
                return pr;
            }
            if(this.decorateTemplateDao.queryCountByName(params) > 0){
                pr.setState(true);
                pr.setMessage("装修模板名称已存在");
                return pr;
            }
            if(this.decorateTemplateDao.update(params) > 0){
                pr.setState(true);
                pr.setMessage("编辑装修模板成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 删除新装修模板
     * @param request
     * @return
     */
    public ProcessResult removeDecorateTemplate(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            Map<String, Object> decorate = this.decorateTemplateDao.queryById(Long.parseLong(params.get("id").toString()));
            if(decorate == null){
                pr.setState(false);
                pr.setMessage("装修模板不存在");
                return pr;
            }
            if(this.decorateTemplateDao.deleted(params) > 0){
                pr.setState(true);
                pr.setMessage("删除装修模板成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 装修模板传送
     * @param request
     * @return
     */
    public ProcessResult decorateTemplateTransfer(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if (StringUtils.isEmpty(params.get("template_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数template_id");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("site_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数site_id");
                return pr;
            }
            String public_user_site_id = params.get("public_user_site_id").toString();  //获取当前站点
            String site_id = params.get("site_id").toString();    //获取传送站点
            if (public_user_site_id.equals(site_id)) {
                pr.setState(false);
                pr.setMessage("不允许传送到当前站点");
                return pr;
            }
            this.decorateTemplateDao.decorateTemplateTransfer(params);
            String output_status = String.valueOf(params.get("output_status"));
            String output_msg = String.valueOf(params.get("output_msg"));
            if ("1".equals(output_status)) {//成功
                pr.setState(true);
            } else {
                pr.setState(false);
            }
            pr.setMessage(output_msg);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 分页获取app模板配置列表
     * @param request
     * @return
     */
    public GridResult listAppIcoTemplateConfig(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数 ");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if (params.containsKey("effect_state")) {
                if(params.get("effect_state") instanceof String){
                    String[] states = params.get("effect_state").toString().split(",");
                    params.put("effect_state", states);
                }
            }
            int total = decorateTemplateDao.countAppIcoTemplateConfig(params);
            List<Map<String, Object>> list = decorateTemplateDao.listAppIcoTemplateConfig(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取app模板配置成功");
                gr.setObj(list);
            } else {
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
     * 获取app模板配置数据
     * @param request
     * @return
     */
    public ProcessResult getAppIcoTemplateConfigInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数 ");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少[id]参数");
                return pr;
            }
            AppIcoConfigDTO appIcoConfig = decorateTemplateDao.getAppIcoTemplateConfigInfo(Long.parseLong(params.get("id").toString()));
            if(appIcoConfig == null){
                pr.setState(false);
                pr.setMessage("模板不存在");
                return pr;
            }
            pr.setObj(appIcoConfig);
            pr.setState(true);
            pr.setMessage("获取模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 编辑app模板配置信息
     * @param request
     * @return
     */
    public ProcessResult editAppIcoTemplateConfigInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            AppIcoConfigParamDTO appIcoConfigParam = (AppIcoConfigParamDTO)Transform.GetPacket(json, AppIcoConfigParamDTO.class);
            if(appIcoConfigParam.getId() == 0){
                decorateTemplateDao.insertAppIcoTemplateConfig(appIcoConfigParam);
                pr.setState(true);
                pr.setMessage("新增模板成功");
                return pr;
            }
            AppIcoConfigDTO appIcoConfig = decorateTemplateDao.getAppIcoTemplateConfigInfo(appIcoConfigParam.getId());
            if(appIcoConfig == null){
                pr.setState(false);
                pr.setMessage("模板不存在");
                return pr;
            }
            decorateTemplateDao.updateAppIcoTemplateConfig(appIcoConfigParam);
            pr.setState(true);
            pr.setMessage("编辑模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 删除app模板配置信息
     * @param request
     * @return
     */
    public ProcessResult deleteAppIcoTemplateConfigInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            AppIcoConfigDTO appIcoConfig = decorateTemplateDao.getAppIcoTemplateConfigInfo(Long.parseLong(params.get("id").toString()));
            if(appIcoConfig == null){
                pr.setState(false);
                pr.setMessage("模板不存在");
                return pr;
            }
            int count = decorateTemplateDao.deleteAppIcoTemplateConfigInfo(Long.parseLong(params.get("id").toString()), Long.parseLong(params.get("public_user_id").toString()));
            if(count < 0){
                pr.setState(false);
                pr.setMessage("删除模板失败");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("删除模板成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 更新app模板配置启用状态
     * @param request
     * @return
     */
    public ProcessResult updateAppIcoTemplateConfigState(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("state"))){
                pr.setState(false);
                pr.setMessage("缺少state参数");
                return pr;
            }
            AppIcoConfigDTO appIcoConfig = decorateTemplateDao.getAppIcoTemplateConfigInfo(Long.parseLong(params.get("id").toString()));
            if(appIcoConfig == null){
                pr.setState(false);
                pr.setMessage("模板不存在");
                return pr;
            }
            AppIcoConfigParamDTO appIcoConfigParam = new AppIcoConfigParamDTO();
            appIcoConfigParam.setId(Long.parseLong(params.get("id").toString()));
            appIcoConfigParam.setEnable_state(Integer.parseInt(params.get("state").toString()));
            int count = decorateTemplateDao.updateAppIcoTemplateConfigState(appIcoConfigParam);
            if(count == 0){
                pr.setState(false);
                pr.setMessage("1".equals(params.get("state").toString()) ? "启用失败" : "禁用失败");
                return pr;
            }
            pr.setMessage("1".equals(params.get("state").toString()) ? "启用成功" : "禁用成功");
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    public static void main (String[] args) throws UnsupportedEncodingException {
        String str = "{\"template_name\":\"23\",\"effect_start_time\":\"2019-07-15 14:50:14\",\"effect_end_time\":\"2019-07-15 00:00:00\",\"head_general_url\":\"\",\"head_activity_url\":\"\",\"head_mall_url\":\"\",\"head_movie_url\":\"\",\"head_replacement_order_url\":\"\",\"bottom_index_url\":\"\",\"bottom_index_check_url\":\"\",\"bottom_class_url\":\"\",\"bottom_class_check_url\":\"\",\"bottom_msg_url\":\"\",\"bottom_msg_check_url\":\"\",\"bottom_purchase_url\":\"\",\"bottom_purchase_check_url\":\"\",\"bottom_my_url\":\"\",\"bottom_my_check_url\":\"\",\"bottom_background_url\":\"\"}";
        //Map<String, Object> param = (Map<String, Object>)Transform.GetPacket(str, HashMap.class);
        AppIcoConfigParamDTO appIcoConfigParam = (AppIcoConfigParamDTO)Jackson.readJson2Object(str, AppIcoConfigParamDTO.class);

    }

}
