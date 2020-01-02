package com.tk.oms.notice.service;

import com.tk.oms.notice.dao.ActivityNoticeDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (c), 2017, Tongku
 * FileName : ActivityNoticeService
 * 活动通知业务操作
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/13 17:52
 */
@Service("ActivityNoticeService")
public class ActivityNoticeService {
    @Resource
    private ActivityNoticeDao activityNoticeDao;//活动通知接口

    /**
     * 查询活动通知内容列表
     *
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
            int count = activityNoticeDao.queryActivityNoticeContextCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = activityNoticeDao.queryActivityNoticeContextList(paramMap);
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
        }
        return gr;
    }

    /**
     * 新增活动通知内容
     *
     * @param request 新增内容
     * @return
     */
    @Transactional
    public ProcessResult addActivityNoticeContext(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("public_user_site_id") || StringUtils.isEmpty(paramMap.get("public_user_site_id"))) {
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }else{
                paramMap.put("site_id", paramMap.get("public_user_site_id"));
            }
            if (!paramMap.containsKey("public_user_id") || StringUtils.isEmpty(paramMap.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数public_user_id");
                return pr;
            }else{
                paramMap.put("create_user_id", paramMap.get("public_user_id"));
            }
            if (!paramMap.containsKey("name") || StringUtils.isEmpty(paramMap.get("name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数name");
                return pr;
            }
            if (!paramMap.containsKey("text_content") || StringUtils.isEmpty(paramMap.get("text_content"))) {
                pr.setState(false);
                pr.setMessage("缺少参数text_content");
                return pr;
            }
            if (!paramMap.containsKey("text_content") || StringUtils.isEmpty(paramMap.get("text_content"))) {
                pr.setState(false);
                pr.setMessage("缺少参数text_content");
                return pr;
            }

            // 插入活动通知内容
            if (activityNoticeDao.insertActivityNoticeContext(paramMap) > 0) {
                Map<String, Object> context = activityNoticeDao.queryActivityNoticeContextById(Long.parseLong(paramMap.get("id").toString()));
                pr.setState(true);
                pr.setMessage("新增成功");
                pr.setObj(context);
            } else {
                throw new RuntimeException("新增失败");
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 查询活动通知内容信息
     *
     * @param request
     * @return
     */
    public ProcessResult queryWaitUpdateActivityNoticeContextInfo(HttpServletRequest request) {
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

            //活动通知内容id
            long id = Long.parseLong(paramMap.get("id").toString());
            //活动通知内容信息
            Map<String, Object> context = activityNoticeDao.queryActivityNoticeContextById(id);
            //判断活动通知内容是否存在
            if (context != null) {
                pr.setState(true);
                pr.setObj(context);
                pr.setMessage("获取成功");
                return pr;
            } else {
                pr.setState(true);
                pr.setMessage("无数据");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 修改活动通知内容信息
     *
     * @param request 修改内容
     * @return
     */
    @Transactional
    public ProcessResult editActivityNoticeContext(HttpServletRequest request) throws Exception {
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

            //活动通知内容id
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            //活动通知内容id
            long id = Long.parseLong(paramMap.get("id").toString());
            //活动通知内容信息
            Map<String, Object> context = activityNoticeDao.queryActivityNoticeContextById(id);
            //判断活动通知内容是否存在
            if (context == null) {
                pr.setState(false);
                pr.setMessage("活动通知内容id不存在");
                return pr;
            }
            if (activityNoticeDao.updateActivityNoticeContext(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("更新成功");
            } else {
                throw new RuntimeException("更新失败");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 删除活动通知内容信息
     *
     * @param request
     * @return
     */
    public ProcessResult removeActivityNoticeContext(HttpServletRequest request) throws Exception {
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

            //活动通知内容id
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            //活动通知内容id
            long id = Long.parseLong(paramMap.get("id").toString());
            //活动通知内容信息
            Map<String, Object> context = activityNoticeDao.queryActivityNoticeContextById(id);
            //判断活动通知内容是否存在
            if (context == null) {
                pr.setState(false);
                pr.setMessage("活动通知内容id不存在");
                return pr;
            }
            if (activityNoticeDao.deleteActivityNoticeContext(id) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            } else {
                throw new RuntimeException("删除失败");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 启用/停用通知内容
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editActivityNoticeContextByStatus(HttpServletRequest request) throws Exception {
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

            //活动通知内容id
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            //活动通知内容id
            long id = Long.parseLong(paramMap.get("id").toString());
            //活动通知内容信息
            Map<String, Object> context = activityNoticeDao.queryActivityNoticeContextById(id);
            //判断活动通知内容是否存在
            if (context == null) {
                pr.setState(false);
                pr.setMessage("活动通知内容不存在");
                return pr;
            }

            //启用/停用通知模版
            String message = "";
            if (context.get("STATE").equals("1")) {
                paramMap.put("state", "2");
                message = "启用";
            } else {
                paramMap.put("state", "1");
                message = "禁用";
            }
            if (activityNoticeDao.updateActivityNoticeContext(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage(message + "成功");
            } else {
                throw new RuntimeException(message + "失败");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 查询活动通知内容数据接口
     * @param request
     * @return
     */
    public ProcessResult queryActivityNoticeContextSelect(HttpServletRequest request){
        ProcessResult pr = new ProcessResult();
        try {
            // 获取传入参数
            String json = HttpUtil.getRequestInputStream(request);
            // 解析传入参数
            Map<String, Object> paramMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            List<Map<String, Object>> activityNoticeContextList = activityNoticeDao.queryActivityNoticeContextSelect(paramMap);
            pr.setMessage("获取成功");
            pr.setState(true);
            pr.setObj(activityNoticeContextList);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }
    /**
     * 查询活动通知发送信息列表
     *
     * @param request 查询条件
     * @return
     */
    public GridResult querySendInfoList(HttpServletRequest request) {
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

            //发送渠道
            if (paramMap.containsKey("notice_channel") && !StringUtils.isEmpty(paramMap.get("notice_channel"))) {
                String[] notice_channel = paramMap.get("notice_channel").toString().split(",");
                if (notice_channel.length > 1) {
                    paramMap.put("notice_channel", paramMap.get("notice_channel"));
                } else {
                    paramMap.put("notice_channel", paramMap.get("notice_channel").toString().split(","));
                }
            }

            //查询记录数
            int count = activityNoticeDao.queryActivityNoticeSendInfoCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = activityNoticeDao.queryActivityNoticeSendInfoList(paramMap);
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
        }
        return gr;
    }

    /**
     * 添加活动通知发送明细
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult addActivityNoticeSendDetail(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("public_user_site_id") || StringUtils.isEmpty(paramMap.get("public_user_site_id"))) {
                pr.setState(false);
                pr.setMessage("当前功能需要站点授权，请联系管理员");
                return pr;
            }else{
                paramMap.put("site_id", paramMap.get("public_user_site_id"));
            }
            //是否全选 不是:判断参数 是：根据查询条件查询用户
            if(!paramMap.containsKey("checkedAll")){
                if (!paramMap.containsKey("user_name") || StringUtils.isEmpty(paramMap.get("user_name"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数user_name");
                    return pr;
                }
                if (!paramMap.containsKey("phone_number") || StringUtils.isEmpty(paramMap.get("phone_number"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数phone_number");
                    return pr;
                }
            }

            if (!paramMap.containsKey("notice_channel") || StringUtils.isEmpty(paramMap.get("notice_channel"))) {
                pr.setState(false);
                pr.setMessage("缺少参数notice_channel");
                return pr;
            }
            if(!paramMap.get("notice_channel").toString().equals("1")) {
                if (!paramMap.containsKey("notice_title") || StringUtils.isEmpty(paramMap.get("notice_title"))) {
                    pr.setState(false);
                    pr.setMessage("缺少参数notice_title");
                    return pr;
                }
            }
            if (!paramMap.containsKey("public_user_id") || StringUtils.isEmpty(paramMap.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数public_user_id");
                return pr;
            }else{
                paramMap.put("create_user_id", paramMap.get("public_user_id"));
            }
            if (!paramMap.containsKey("send_type") || StringUtils.isEmpty(paramMap.get("send_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数send_type");
                return pr;
            }else{
                //立即发送
                if(paramMap.get("send_type").toString().equals("at_once")){
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    paramMap.put("send_date", sf.format(new Date()));
                }
            }
            if (!paramMap.containsKey("send_date") || StringUtils.isEmpty(paramMap.get("send_date"))) {
                pr.setState(false);
                pr.setMessage("缺少参数send_date");
                return pr;
            }

            // 插入活动通知发送信息
            if (activityNoticeDao.insertActivityNoticeSendInfo(paramMap) > 0) {
                if(!paramMap.containsKey("checkedAll")){
                    //主表新增成功，新增详细表
                    String[] userNames = Utils.removeNull(paramMap.get("user_name").toString().split(","));
                    String[] phoneNumbers = Utils.removeNull(paramMap.get("phone_number").toString().split(","));

                    //待添加活动通知明细数据
                    List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
                    if (userNames.length != phoneNumbers.length) {
                        pr.setState(false);
                        pr.setMessage("参数错误");
                        throw new RuntimeException("新增失败");
                    }
                    for (int i = 0, l = userNames.length; i < l; i++) {
                        Map<String, Object> detail = new HashMap<String, Object>();
                        //活动通知信息id
                        detail.put("notice_id", Long.parseLong(paramMap.get("id").toString()));
                        //通知的会员
                        detail.put("user_name", userNames[i]);
                        //通知会员电话
                        detail.put("phone_number", phoneNumbers[i]);
                        detailList.add(detail);
                    }

                    //批量添加活动通知明细
                    if (activityNoticeDao.insertActivityNoticeSendDetailByBatch(detailList) > 0) {
                        pr.setState(true);
                        pr.setMessage("新增成功");
                    } else {
                        throw new RuntimeException("新增失败");
                    }
                }else{
                    //全选： 根据查询条件添加用户
                    /*********用户状态*********/
                    if(paramMap.containsKey("user_state") && !StringUtils.isEmpty(paramMap.get("user_state"))) {
                        String[] user_state = paramMap.get("user_state").toString().split(",");
                        if (user_state.length > 1) {
                            paramMap.put("user_state", paramMap.get("user_state"));
                        } else {
                            paramMap.put("user_state", paramMap.get("user_state").toString().split(","));
                        }
                    }
                    /*********用户状态*********/
                    if (activityNoticeDao.insertActivityNoticeSendDetailByCheckAll(paramMap) > 0) {
                        pr.setState(true);
                        pr.setMessage("新增成功");
                    } else {
                        throw new RuntimeException("新增失败");
                    }
                }
            } else {
                throw new RuntimeException("新增失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }


    /**
     * 查询活动通知发送明细
     *
     * @param request
     * @return
     */
    public ProcessResult queryActivityNoticeSendDetail(HttpServletRequest request){
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
            Map<String, Object> activityNotice = activityNoticeDao.queryActivityNoticeSendInfoById(Long.parseLong(paramMap.get("id").toString()));//活动通知信息
            List<Map<String, Object>> activityNoticeSendDetail = activityNoticeDao.queryActivityNoticeSendDetailByNoticeId(Long.parseLong(paramMap.get("id").toString()));//活动通知发送明细
            if (activityNotice != null) {
                pr.setState(true);
                pr.setMessage("获取成功");
                Map data = new HashMap();
                data.put("activityNotice", activityNotice);
                data.put("activityNoticeSendDetail", activityNoticeSendDetail);
                pr.setObj(data);
            } else {
                pr.setState(true);
                pr.setMessage("无数据");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取失败");
        }

        return pr;
    }


    /**
     * 修改活动通知发送明细
     *
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult editActivityNoticeSendDetail(HttpServletRequest request) throws Exception {
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
            if (!paramMap.containsKey("notice_id") || StringUtils.isEmpty(paramMap.get("notice_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数notice_id");
                return pr;
            }
            if (!paramMap.containsKey("notice_channel") || StringUtils.isEmpty(paramMap.get("notice_channel"))) {
                pr.setState(false);
                pr.setMessage("缺少参数notice_channel");
                return pr;
            }
            if (!paramMap.containsKey("user_name") || StringUtils.isEmpty(paramMap.get("user_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数user_name");
                return pr;
            }
            if (!paramMap.containsKey("phone_number") || StringUtils.isEmpty(paramMap.get("phone_number"))) {
                pr.setState(false);
                pr.setMessage("缺少参数phone_number");
                return pr;
            }
            if (!paramMap.containsKey("public_user_id") || StringUtils.isEmpty(paramMap.get("public_user_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数public_user_id");
                return pr;
            }else{
                paramMap.put("create_user_id", paramMap.get("public_user_id"));
            }
            if (!paramMap.containsKey("send_type") || StringUtils.isEmpty(paramMap.get("send_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数send_type");
                return pr;
            }else{
                //立即发送
                if(paramMap.get("send_type").toString().equals("at_once")){
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    paramMap.put("send_date", sf.format(new Date()));
                }
            }
            if (!paramMap.containsKey("send_date") || StringUtils.isEmpty(paramMap.get("send_date"))) {
                pr.setState(false);
                pr.setMessage("缺少参数send_date");
                return pr;
            }

            // 修改活动通知发送信息
            if (activityNoticeDao.updateActivityNoticeSendInfo(paramMap) > 0) {
                //主表修改成功，先删后插详细表
                int count = activityNoticeDao.queryActivityNoticeSendDetailCountByNoticeId(Long.parseLong(paramMap.get("notice_id").toString()));//查询数量
                int deleteCount = activityNoticeDao.deleteActivityNoticeSendDetailByNoticeId(Long.parseLong(paramMap.get("notice_id").toString()));//删除数量
                if (count != deleteCount) {
                    throw new RuntimeException("更新失败");
                }

                /**********************新增活动通知明细数据Start************************/
                String[] userNames = Utils.removeNull(paramMap.get("user_name").toString().split(","));
                String[] phoneNumbers = Utils.removeNull(paramMap.get("phone_number").toString().split(","));

                //待添加活动通知明细数据
                List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
                if (userNames.length != phoneNumbers.length) {
                    throw new RuntimeException("更新失败");
                }
                for (int i = 0, l = userNames.length; i < l; i++) {
                    Map<String, Object> detail = new HashMap<String, Object>();
                    //活动通知信息id
                    detail.put("notice_id", Long.parseLong(paramMap.get("notice_id").toString()));
                    //通知的会员
                    detail.put("user_name", userNames[i]);
                    //通知会员电话
                    detail.put("phone_number", phoneNumbers[i]);
                    detailList.add(detail);
                }

                //批量添加活动通知明细
                if (activityNoticeDao.insertActivityNoticeSendDetailByBatch(detailList) > 0) {
                    pr.setState(true);
                    pr.setMessage("更新失败");
                } else {
                    throw new RuntimeException("更新失败");
                }
                /**********************新增活动通知明细数据End**************************/

            } else {
                throw new RuntimeException("更新失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return pr;
    }

    /**
     * 删除活动通知发送明细
     * @param request
     * @return
     * @throws Exception
     */
    public ProcessResult removeActivityNoticeInfo(HttpServletRequest request) throws Exception {
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

            //活动通知发送明细id
            if (!paramMap.containsKey("notice_id") || StringUtils.isEmpty(paramMap.get("notice_id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数notice_id");
                return pr;
            }

            Long notice_id = Long.parseLong(paramMap.get("notice_id").toString());
            //活动通知内容信息
            Map<String, Object> context = activityNoticeDao.queryActivityNoticeSendInfoById(notice_id);

            //判断活动通知内容是否存在
            if (context == null) {
                pr.setState(false);
                pr.setMessage("活动通知内容id不存在");
                return pr;
            }
            //先删主表 再删子表
            //删除活动通知数据
            if (activityNoticeDao.deleteActivityNoticeInfoByNoticeId(notice_id) > 0) {
                //删除活动通知发送明细数据
                int count = activityNoticeDao.queryActivityNoticeSendDetailCountByNoticeId(notice_id);
                int deleteCount = activityNoticeDao.deleteActivityNoticeSendDetailByNoticeId(notice_id);
                if(count == deleteCount){
                    pr.setState(true);
                    pr.setMessage("删除成功");
                }
            } else {
                throw new RuntimeException("删除失败");
            }

        } catch (Exception e) {
            throw new RuntimeException("删除失败");
        }

        return pr;
    }

    /**
     * 取消活动通知内容信息
     *
     * @param request 修改内容
     * @return
     */
    @Transactional
    public ProcessResult editActivityNoticeInfo(HttpServletRequest request) throws Exception {
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

            //活动通知id
            if (!paramMap.containsKey("id") || StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }

            //待取消活动通知id
            String[] ids = paramMap.get("id").toString().split(",");
            for (String id : ids) {
                //活动通知
                Map<String, Object> activityNotice = activityNoticeDao.queryActivityNoticeSendInfoById(Long.parseLong(id));
                //检查活动通知是否存在
                if (activityNotice == null) {
                    pr.setState(false);
                    pr.setMessage("活动通知id不存在");
                    return pr;
                }
            }

            paramMap.put("ids", ids);
            //取消发送
            if (activityNoticeDao.updateActivityNoticeInfoByCancelSend(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("取消发送成功");
            } else {
                pr.setState(false);
                pr.setMessage("取消发送失败");
                throw new RuntimeException("取消发送失败");
            }

        } catch (Exception e) {
            throw new RuntimeException("取消发送失败");
        }

        return pr;
    }


    /**
     * 查询会员列表
     *
     * @param request 查询条件
     * @return
     */
    public GridResult queryMemberList(HttpServletRequest request) {
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

            //状态
            if (paramMap.containsKey("user_state") && !StringUtils.isEmpty(paramMap.get("user_state"))) {
                String[] state = paramMap.get("user_state").toString().split(",");
                if (state.length > 1) {
                    paramMap.put("user_state", paramMap.get("user_state"));
                } else {
                    paramMap.put("user_state", paramMap.get("user_state").toString().split(","));
                }
            }
            
            if((!StringUtils.isEmpty(paramMap.get("user_type")))&&paramMap.get("user_type") instanceof String){
            	paramMap.put("user_type",(paramMap.get("user_type")+"").split(","));
			}
            
            if((!StringUtils.isEmpty(paramMap.get("check_site_id"))) && paramMap.get("check_site_id") instanceof String){
            	paramMap.put("check_site_id_arr",(paramMap.get("check_site_id")+"").split(","));
			}
            
            if((!StringUtils.isEmpty(paramMap.get("filter_user_id"))) && paramMap.get("filter_user_id") instanceof String){
            	paramMap.put("filter_user_id_arr",(paramMap.get("filter_user_id")+"").split(","));
			}
            
            if (paramMap.containsKey("userList") && !StringUtils.isEmpty(paramMap.get("userList"))) {
                paramMap.put("user_list", paramMap.get("userList").toString().split(","));
            }
            //查询记录数
            int count = activityNoticeDao.queryMemberListCount(paramMap);
            //查询列表
            List<Map<String, Object>> list = activityNoticeDao.queryMemberList(paramMap);
            if (list != null) {
                gr.setState(true);
                gr.setObj(list);
                gr.setMessage("获取成功");
                gr.setTotal(count);
            } else {
                gr.setState(true);
                gr.setMessage("无数据");
            }
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

}
