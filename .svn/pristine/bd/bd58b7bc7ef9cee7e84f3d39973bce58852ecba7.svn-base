package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.SettlementDao;
import com.tk.sys.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 入驻商结算管理
 * @author zhenghui
 */
@Service("SettlementService")
public class SettlementService {

    @Resource
    private SettlementDao settlementDao;

    /**
     * 分页获取结算单列表信息
     * @param request
     * @return
     */
    public GridResult querySettlementListByPage(HttpServletRequest request) {
        GridResult gr = new GridResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                gr.setState(false);
                gr.setMessage("缺少参数");
                return gr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            GridResult pageParamResult = PageUtil.handlePageParams(params);
            if (pageParamResult != null) {
                return pageParamResult;
            }
            if (params.containsKey("settlement_state")) {
                String[] states = ((String) params.get("settlement_state")).split(",");
                params.put("settlement_state", states);
            }
            int total = this.settlementDao.queryListForCount(params);
            List<Map<String, Object>> list = this.settlementDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("获取结算单列表成功");
                gr.setObj(list);
            } else {
                gr.setMessage("无数据");
            }
            gr.setState(true);
            gr.setTotal(total);
        } catch (Exception ex) {
            gr.setState(false);
            gr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return gr;
    }

    /**
     * 获取结算单详情信息
     * @return
     */
    public ProcessResult querySettlementDetail(HttpServletRequest request) {
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
                pr.setMessage("缺少参数ID");
                return pr;
            }
            Map<String, Object> settlement = this.settlementDao.queryByNumber(params.get("id").toString());
            if (settlement == null) {
                pr.setState(false);
                pr.setMessage("结算不存在");
                return pr;
            }
            pr.setState(true);
            pr.setMessage("获取结算单详情成功");
            pr.setObj(settlement);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
            ex.printStackTrace();
        }
        return pr;
    }
}
