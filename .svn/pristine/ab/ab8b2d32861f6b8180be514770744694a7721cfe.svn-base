package com.tk.oms.sys.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sys.service.VerifyCodeService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;



@Controller
@RequestMapping("/verify_code")
public class VerifyCodeControl {
	@Resource
    private VerifyCodeService verifyCodeService;

    /**
     * /**
     * @api{post} /{oms_server}/verify_code/list 验证码消息列表
     * @apiGroup list
     * @apiName list
     * @apiDescription  验证码消息列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 验证码消息列表信息
     * @apiSuccess{number} total 总条数
     */
     
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAllVerifyCode(HttpServletRequest request) {
        return Transform.GetResult(verifyCodeService.queryAllVerifyCode(request));
    }

    /**@api{post} /{oms_server}/verify_code/update 验证码更新
     * @apiGroup update
     * @apiName update
     * @apiDescription  验证码更新
     * @apiVersion 0.0.1
 	 * @apiSuccess {string}TYPE    类型
	 * @apiSuccess {string}VERIFY_CODE    验证码
     * @apiSuccess {string}UPDATE_DATE    更新时间
     *@apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 验证码更新信息
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateVerifyCode(HttpServletRequest request) {
        return Transform.GetResult(verifyCodeService.updateVerifyCode(request));
    }
}
