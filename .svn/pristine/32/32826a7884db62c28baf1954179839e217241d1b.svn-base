package com.tk.oms.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.analysis.dao.UserOperationLogDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.Transform;

@Service("UserOperationLogService")
public class UserOperationLogService {
	
	@Resource
    private UserOperationLogDao userOperationLogDao;

    /**
     * 分页查询商品操作日志列表
     * @param request
     * @return
     */
    public GridResult userOperationLogListForPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺失参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            int total = userOperationLogDao.countUserOperationLogForPage(params);
            List<Map<String, Object>> dataList = null;
            if (total > 0) {
                dataList = userOperationLogDao.listUserOperationLogForPage(params);
                gr.setMessage("查询用户操作日志列表成功!");
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setObj(dataList);
            gr.setTotal(total);
        } catch (Exception e) {
            gr.setState(false);
            gr.setMessage(e.getMessage());
        }
        return gr;
    }

}
