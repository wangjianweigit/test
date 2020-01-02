package com.tk.oms.sys.service;

import com.tk.oms.sys.dao.AppVersionDao;
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
 * FileName : AppVersionService
 * 应用程序版本管理业务层
 *
 * @author zhenghui
 * @version 1.00
 * @date 2018-06-12
 */
@Service("AppVersionService")
public class AppVersionService {

    @Resource
    private AppVersionDao appVersionDao;

    /**
     * 分页查询App版本列表
     *
     * @param request
     * @return
     */
    public GridResult queryAppVersionListForPage(HttpServletRequest request) {
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
            // 查询缓存信息总条数
            int total = this.appVersionDao.countAppVersionForPage(paramMap);
            // 分页查询缓存信息列表
            List<Map<String, Object>> list = null;
            if (total > 0) {
                list = this.appVersionDao.listAppVersionForPage(paramMap);
                gr.setMessage("获取查询App版本列表成功");
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
            gr.setObj(list);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

    /**
     * 查询App版本详细信息
     * @param request
     * @return
     */
    public ProcessResult queryAppVersionForDetail(HttpServletRequest request) {
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
            if (!paramMap.containsKey("version_id") || StringUtils.isEmpty(paramMap.get("version_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_id】");
                return pr;
            }
            long versionId = Long.parseLong(paramMap.get("version_id").toString());
            Map<String, Object> appVersion = this.appVersionDao.getAppVersionById(versionId);
            if (appVersion == null) {
                pr.setState(false);
                pr.setMessage("APP版本不存在或已删除");
                return pr;
            }
            pr.setState(true);
            pr.setObj(appVersion);
            pr.setMessage("查询App版本详细信息成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 新增App版本
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult addAppVersion(HttpServletRequest request) throws Exception {
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
            if (StringUtils.isEmpty(paramMap.get("version"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("version_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_code】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("app_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【app_type】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("system"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【system】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("project_version"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【project_version】");
                return pr;
            }
            if(this.appVersionDao.countAppVersionByVersion(paramMap) > 0){
                pr.setState(false);
                pr.setMessage("版本号已存在");
                return pr;
            }
            int versionCode = Integer.parseInt(paramMap.get("version_code").toString());
            if (this.appVersionDao.getMaxVersionCode(paramMap) > versionCode) {
                pr.setState(false);
                pr.setMessage("版本代号不能小于历史版本代号");
                return pr;
            }
            int count = this.appVersionDao.insertAppVersion(paramMap);
            if (count <= 0) {
                throw new Exception("新增App版本失败");
            }
            pr.setState(true);
            pr.setMessage("新增App版本成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑App版本
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editAppVersion(HttpServletRequest request) throws Exception {
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
            if (StringUtils.isEmpty(paramMap.get("version_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_id】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("version"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("version_code"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_code】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("app_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【app_type】");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("system"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【system】");
                return pr;
            }
            if (this.appVersionDao.countAppVersionByVersion(paramMap) > 0) {
                pr.setState(false);
                pr.setMessage("版本号已存在");
                return pr;
            }
            int versionCode = Integer.parseInt(paramMap.get("version_code").toString());
            if (this.appVersionDao.getMaxVersionCode(paramMap) > versionCode) {
                pr.setState(false);
                pr.setMessage("版本代号不能小于历史版本代号");
                return pr;
            }
            int count = this.appVersionDao.updateAppVersion(paramMap);
            if (count <= 0) {
                throw new Exception("编辑App版本失败");
            }
            pr.setState(true);
            pr.setMessage("编辑App版本成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 更新App版本状态
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult editAppVersionState(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("version_id") || StringUtils.isEmpty(paramMap.get("version_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_id】");
                return pr;
            }
            //确认发布或者启用禁用标志位，确认发布操作时才传值
            if (paramMap.containsKey("state") && !StringUtils.isEmpty(paramMap.get("state"))) {
                pr.setObj("1");
            }else{
                pr.setObj("2");
            }
            long versionId = Long.parseLong(paramMap.get("version_id").toString());
            Map<String, Object> appVersion = this.appVersionDao.getAppVersionById(versionId);
            if (appVersion == null) {
                pr.setState(false);
                pr.setMessage("APP版本不存在或已删除");
                return pr;
            }
            int count = this.appVersionDao.updateAppVersion(paramMap);
            if (count <= 0) {
                throw new Exception("更新App版本状态失败");
            }
            pr.setState(true);
            pr.setMessage("更新App版本状态成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 更新App的标志位function_state
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
    public ProcessResult editAppVersionFunctionState(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("version_id") || StringUtils.isEmpty(paramMap.get("version_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_id】");
                return pr;
            }
            if (!paramMap.containsKey("function_state") || StringUtils.isEmpty(paramMap.get("function_state"))) {
            	pr.setState(false);
            	pr.setMessage("缺少参数【function_state】");
            	return pr;
            }
            int function_state = 0;
            try {
            	function_state =  Integer.parseInt(paramMap.get("function_state").toString());
			} catch (Exception e) {}
            if(function_state!=1 && function_state!=2){
            	pr.setState(false);
            	pr.setMessage("参数【function_state】取值错误");
            	return pr;
            }
            long versionId = Long.parseLong(paramMap.get("version_id").toString());
            Map<String, Object> appVersion = this.appVersionDao.getAppVersionById(versionId);
            if (appVersion == null) {
                pr.setState(false);
                pr.setMessage("APP版本不存在或已删除");
                return pr;
            }
            int count = this.appVersionDao.updateAppVersion(paramMap);
            if (count <= 0) {
                throw new Exception("更新App是的使用原始标志位成功");
            }
            pr.setState(true);
            pr.setMessage("更新App版本状态成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除App版本
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult removeAppVersion(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("version_id") || StringUtils.isEmpty(paramMap.get("version_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数【version_id】");
                return pr;
            }
            long versionId = Long.parseLong(paramMap.get("version_id").toString());
            Map<String, Object> appVersion = this.appVersionDao.getAppVersionById(versionId);
            if (appVersion == null) {
                pr.setState(false);
                pr.setMessage("APP版本不存在或已删除");
                return pr;
            }
            int count = this.appVersionDao.deleteAppVersion(versionId);
            if (count <= 0) {
                throw new Exception("删除App版本失败");
            }
            pr.setState(true);
            pr.setMessage("删除App版本成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
}
