package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.MemberFlagDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会员标记配置管理
 * @author zhenghui
 * @date 2017-7-4
 */
@Service("MemberFlagService")
public class MemberFlagService {

    @Resource
    private MemberFlagDao memberFlagDao;

    /**
     * 分页获取会员标记列表数据
     * @param request
     * @return
     */
    public GridResult queryMemberFlagForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(paramMap);
            if (pageParamResult != null) {
                return pageParamResult;
            }

            //获取分类信息
            int total = memberFlagDao.queryCountForPage(paramMap);
            List<Map<String, Object>> list = memberFlagDao.queryListForPage(paramMap);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取会员标记列表成功");
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
     * 查询会员标记列表
     * @param request
     * @return
     */
    public ProcessResult queryMemberFlagForList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("mark_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数MARK_TYPE");
                return pr;
            }
            // 查询会员标记列表
            List<Map<String, Object>> list = this.memberFlagDao.queryMemberFlagForList(paramMap);
            if (list != null && list.size() > 0) {
                pr.setMessage("获取会员标记列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取会员标记详情
     * @param request
     * @return
     */
    public ProcessResult queryMemberFlagForDetail(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数ID");
                return pr;
            }
            Map<String, Object> map = this.memberFlagDao.queryById(Long.parseLong(paramMap.get("id").toString()));
            pr.setState(true);
            pr.setMessage("获取会员标记详情成功");
            pr.setObj(map);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加会员标记
     * @param request
     * @return
     */
    public ProcessResult addMemberFlag(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("mark_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数MARK_NAME");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("mark_img_url"))) {
                pr.setState(false);
                pr.setMessage("缺少参数MARK_IMG_URL");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("mark_type"))) {
                pr.setState(false);
                pr.setMessage("缺少参数MARK_TYPE");
                return pr;
            }
            if (this.memberFlagDao.queryMemberFlagCountByName(paramMap) > 0) {
                pr.setState(false);
                pr.setMessage("标记名称已存在");
                return pr;
            }
            if (this.memberFlagDao.insert(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("添加标记成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑会员标记
     * @param request
     * @return
     */
    public ProcessResult editMemberFlag(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数ID");
                return pr;
            }
            if (this.memberFlagDao.queryMemberFlagCountByName(paramMap) > 0) {
                pr.setState(false);
                pr.setMessage("标记名称已存在");
                return pr;
            }
            if (this.memberFlagDao.update(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("编辑标记成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除会员标记
     * @param request
     * @return
     */
    public ProcessResult removeMemberFlag(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数ID");
                return pr;
            }
            Map<String, Object> memberFlag = this.memberFlagDao.queryById(Long.parseLong(paramMap.get("id").toString()));
            if (memberFlag == null) {
                pr.setState(false);
                pr.setMessage("标记不存在");
                return pr;
            }
            //获取标记类型
            int mark_type = Integer.parseInt(memberFlag.get("mark_type").toString());
            int count = 0;
            if (mark_type == 1) {
                //获取已经标记的会员标记数量
                count = this.memberFlagDao.queryUserMarkCountById(paramMap);
            }
            if (mark_type == 2) {
                //获取已经标记的库存标记数量
                count = this.memberFlagDao.queryStockMarkCountById(paramMap);
            }
            if (count > 0) {
                pr.setState(false);
                pr.setMessage("已被占用，不允许删除");
                return pr;
            }
            //删除操作
            if (this.memberFlagDao.delete(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("删除标记成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 会员标记排序
     * @param request
     * @return
     */
    public ProcessResult editMemberFlagForSort(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> paramMap = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(paramMap.get("toId"))) {
                pr.setState(false);
                pr.setMessage("缺少参数toId");
                return pr;
            }
            if (StringUtils.isEmpty(paramMap.get("fromId"))) {
                pr.setState(false);
                pr.setMessage("缺少参数fromId");
                return pr;
            }
            if (this.memberFlagDao.updateMemberFlagForSort(paramMap) > 0) {
                pr.setState(true);
                pr.setMessage("标记排序成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
}
