package com.tk.oms.finance.service;

import com.tk.oms.finance.dao.PayChannelConfigDao;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayChannelConfigService {

	@Resource
	private PayChannelConfigDao payChannelConfigDao;

	@Value ("${pay_service_url}")
	private String pay_service_url;//远程调用见证宝接口

	/**
     * 分页获取操作记录
     * @param request
     * @return
     */
	public GridResult listPayChannelOperationRecord(HttpServletRequest request) {
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
            int total = payChannelConfigDao.queryListForCount(params);
            List<Map<String, Object>> list = payChannelConfigDao.queryListForPage(params);
            if (list != null && list.size() > 0) {
                gr.setMessage("OK");
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
	 * 获取支付渠道默认配置
	 * @param request
	 * @return
	 */
	public ProcessResult getPayChannelConfig(HttpServletRequest request) {
		ProcessResult gr = new ProcessResult();
		try {
			String json = HttpUtil.getRequestInputStream(request);
			if (StringUtils.isEmpty(json)) {
				gr.setState(false);
				gr.setMessage("缺少参数");
				return gr;
			}
			ProcessResult pr = HttpClientUtil.post(pay_service_url + "/basic/get_pay_channel", new HashMap<String, Object>());
			if (!pr.getState()) {
				throw new RuntimeException(gr.getMessage());
			}
			Map<String, Object> map = (Map<String, Object>)pr.getObj();

			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("zfb_new_config", map.get("zfb_channel"));
			returnMap.put("wx_new_config", map.get("wx_channel"));
			gr.setState(true);
			gr.setMessage("获取支付渠道默认配置成功");
			gr.setObj(returnMap);
		} catch (Exception ex) {
			gr.setState(false);
			gr.setMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return gr;
	}

    /**
     * 配置支付渠道
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult configPayChannel(HttpServletRequest request) {
    	ProcessResult gr = new ProcessResult();
    	try {
    		String json = HttpUtil.getRequestInputStream(request);
    		if (StringUtils.isEmpty(json)) {
    			gr.setState(false);
    			gr.setMessage("缺少参数");
    			return gr;
    		}
    		Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
    		if(params == null ||params.isEmpty()){
    			gr.setState(false);
    			gr.setMessage("缺少参数");
    			return gr;
    		}
			if(StringUtils.isEmpty(params.get("zfb_new_config"))){
				gr.setState(false);
				gr.setMessage("缺少参数[zfb_new_config]");
				return gr;
			}
			if(StringUtils.isEmpty(params.get("wx_new_config"))){
				gr.setState(false);
				gr.setMessage("缺少参数[wx_new_config]");
				return gr;
			}
			if(StringUtils.isEmpty(params.get("zfb_old_config"))){
				gr.setState(false);
				gr.setMessage("缺少参数[zfb_old_config]");
				return gr;
			}
			if(StringUtils.isEmpty(params.get("wx_old_config"))){
				gr.setState(false);
				gr.setMessage("缺少参数[wx_old_config]");
				return gr;
			}
			// 新增操作记录
			payChannelConfigDao.insertOperationRecord(params);

			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("zfb_pay_channel", params.get("zfb_new_config").toString());
			retMap.put("wx_pay_channel", params.get("wx_new_config").toString());
			// 修改支付配置
			ProcessResult pr = HttpClientUtil.post(pay_service_url + "/basic/native/toggle", retMap);
			if (!pr.getState()) {
				throw new RuntimeException(gr.getMessage());
			}
    		gr.setState(true);
    		gr.setMessage("配置支付渠道成功");
    	} catch (Exception ex) {
    		gr.setState(false);
    		gr.setMessage(ex.getMessage());
    		ex.printStackTrace();
			throw new RuntimeException(gr.getMessage());
    	}
    	return gr;
    }
}
