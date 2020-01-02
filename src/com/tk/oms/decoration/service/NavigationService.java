package com.tk.oms.decoration.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.decoration.dao.DecorateDataDao;
import com.tk.oms.decoration.dao.NavigationDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * 导航管理
 * @author zhenghui
 * @data 2017.1.16
 */
@Service("NavigationService")
public class NavigationService {

    @Resource
    private NavigationDao navigationDao;
    @Resource
    private DecorateDataDao decorateDataDao;
    /**
     * 分页获取导航列表
     * @param request
     * @return
     */
    public GridResult queryNavigationListForPage(HttpServletRequest request){
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String,Object> params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少获取列表所需的参数
            if(StringUtils.isEmpty(params.get("template_id"))){
                gr.setState(false);
                gr.setMessage("缺少参数[template_id]");
                return gr;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                gr.setState(false);
                gr.setMessage("当前功能需要站点授权，请联系管理员");
                return gr;
            }
            List<Map<String, Object>> navigationList = this.navigationDao.queryListForPage(params);
            Map<String, Object> map = new HashMap<String, Object>(16);
            map.put("ID", 0);
            map.put("PAGE_ID", 0);
            map.put("NAME", "首页");
            map.put("USER_GROUP_NAME", "不限");
            navigationList.add(0, map);
            if (navigationList != null && navigationList.size() > 0) {
                gr.setMessage("获取导航列表成功");
                gr.setObj(navigationList);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
        } catch (Exception ex) {
            gr.setState(false);
            gr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return gr;
    }

    /**
     * 添加导航
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addNavigation(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少获取列表所需的参数
            if (StringUtils.isEmpty(params.get("name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[name]");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("template_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[template_id]");
                return pr;
            }
            if("1".equals(params.get("url_type"))){
                if (StringUtils.isEmpty(params.get("page_id"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[page_id]");
                    return pr;
                }
                params.remove("url");
            }else{
                if (StringUtils.isEmpty(params.get("url"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[url]");
                    return pr;
                }
                params.remove("page_id");
            }
            //如果是系统连接需要校验关联的合法性
            if(params.get("url_type").equals("1")){
	            if(this.decorateDataDao.queryPageCountBySitePageTemplate(params)<=0){
	            	pr.setState(false);
	                pr.setMessage("导航添加失败，可能是您切换了站点，请关闭装修中心重试！");
	                return pr;
	            }
            }
            //添加操作
            if (this.navigationDao.insert(params) > 0) {
                pr.setState(true);
                pr.setMessage("导航添加成功");
                pr.setObj(params);
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 获取导航详情
     * @param request
     * @return
     */
    public ProcessResult queryNavigationDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[id]");
                return pr;
            }
            Map<String, Object> navigation = this.navigationDao.queryById(Long.parseLong(params.get("id").toString()));
            if (navigation != null) {
                pr.setMessage("获取导航列表成功");
                pr.setObj(navigation);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return pr;
    }

    /**
     * 编辑导航
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editNavigation(HttpServletRequest request) throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[id]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("NAME"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[NAME]");
                return pr;
            }
            if("1".equals(params.get("URL_TYPE"))){
                if (StringUtils.isEmpty(params.get("PAGE_ID"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[PAGE_ID]");
                    return pr;
                }
                params.remove("URL");
            }else{
                if (StringUtils.isEmpty(params.get("URL"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数[URL]");
                    return pr;
                }
                params.remove("PAGE_ID");
            }
            //更新操作
            if (this.navigationDao.update(params) > 0) {
                pr.setState(true);
                pr.setMessage("导航编辑成功");
                pr.setObj(params);
            }
        }catch (Exception ex){
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 删除导航
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeNavigation(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[id]");
                return pr;
            }
            //更新操作
            if (this.navigationDao.deleted(params) > 0) {
                pr.setState(true);
                pr.setMessage("导航删除成功");
                pr.setObj(params);
            }
        }catch (Exception ex){
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 导航排序
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public ProcessResult updateNavSort(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(StringUtils.isEmpty(json)){
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String,Object> params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("toId"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[toId]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("fromId"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[fromId]");
                return pr;
            }
            //排序操作
            if (this.navigationDao.updateNavSort(params) > 0) {
                pr.setState(true);
                pr.setMessage("导航排序成功");
            }
        }catch (Exception ex){
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 导航广告添加
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public ProcessResult addOrEditNavDrumbeating(HttpServletRequest request) throws Exception {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("template_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[template_id]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("img_url"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[img_url]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("img_x_value"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[img_x_value]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("img_y_value"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[img_y_value]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("img_href"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[img_href]");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("state"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[state]");
                return pr;
            }
            //判断导航广告是否存在（不存在添加，存在编辑）
            if (this.navigationDao.queryNavDrumbeatingDetail(params) == null) {
                if (this.navigationDao.addNavDrumbeating(params) > 0) {
                    pr.setState(true);
                    pr.setMessage("导航广告添加成功");
                }
            } else {
                if (this.navigationDao.updateNavDrumbeating(params) > 0) {
                    pr.setState(true);
                    pr.setMessage("导航广告添加成功");
                }
            }
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
        return pr;
    }

    /**
     * 获取导航广告详情
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public ProcessResult queryNavDrumbeatingDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("template_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数[template_id]");
                return pr;
            }
            Map<String, Object> nav_drumbeating = this.navigationDao.queryNavDrumbeatingDetail(params);
            if (nav_drumbeating != null) {
                pr.setMessage("获取导航广告详情成功");
                pr.setObj(nav_drumbeating);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return pr;
    }

    /**
     * 获取页面列表
     * @param request
     * @return
     * @throws Exception
     */
    public ProcessResult queryPageList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //判断是否缺少获取列表所需的参数
            if(StringUtils.isEmpty(params.get("template_id"))){
                pr.setState(false);
                pr.setMessage("缺少参数[template_id]");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("public_user_site_id"))){
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }
            //默认只展示【启用的页面】
            params.put("page_state","2");// 页面状态   1（停用）2（启用）
            List<Map<String, Object>> pageList = this.navigationDao.queryPageList(params);
            if (pageList != null && pageList.size() > 0) {
                pr.setMessage("获取页面列表成功");
                pr.setObj(pageList);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return pr;
    }

}
