package com.tk.oms.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tk.oms.sys.dao.VerifyCodeDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import com.tk.sys.util.Utils;



@Service("VerifyCodeService")
public class VerifyCodeService {
	@Resource
	private VerifyCodeDao verifyCodeDao;
	/**
     * 查询验证码信息
     *
     * @param request
     * @return
     */
    public GridResult queryAllVerifyCode(HttpServletRequest request) {
    	GridResult gr = new GridResult();

        try {
        	int verifyCodeCount=verifyCodeDao.queryVerifyCodeCount();
            List<Map<String,Object>> verifyCodeList = verifyCodeDao.queryAllVerifyCode();

            if (verifyCodeList != null && verifyCodeList.size() > 0) {
				gr.setState(true);
				gr.setMessage("查询成功!");
				gr.setObj(verifyCodeList);
				gr.setTotal(verifyCodeCount);
			} else {
				gr.setState(true);
				gr.setMessage("无数据");
			}
        }catch (Exception e) {
        	gr.setState(false);
        	gr.setMessage(e.getMessage());
        }

        return gr;
    }

    /**
     * 更新验证码
     *
     * @param request
     * @return
     */
    public ProcessResult updateVerifyCode(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();

        try {
        	Map<String, Object> verifyCode = new HashMap<String, Object>();
            String json = HttpUtil.getRequestInputStream(request);
            verifyCode = (Map<String, Object>) Transform.GetPacket(json,
					Map.class);
            if(StringUtils.isEmpty(verifyCode.get("type"))){
                pr.setState(false);
                pr.setMessage("缺少参数Type");
                return pr;
            }
            verifyCode.put("verify_code", Utils.getVerifyCode());
            verifyCodeDao.updateVerifyCode(verifyCode);

            pr.setState(true);
            pr.setMessage("更新成功");
            pr.setObj(verifyCode);
        }catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }
}
