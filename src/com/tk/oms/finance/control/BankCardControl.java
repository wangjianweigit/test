package com.tk.oms.finance.control;

import com.tk.oms.finance.service.BankCardService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : BankCardControl
 * 银行卡管理
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/6/13 15:56
 */
@Controller
@RequestMapping("/bank_card")
public class BankCardControl {

    @Resource
    private BankCardService bankCardService;

    /**
     * @api {post} /{project_name}/bank_card/list 获取银行卡列表
     * @apiGroup bank_card
     * @apiDescription  获取所有银行卡信息列表
     * @apiVersion 0.0.1
     *
     * @apiParam {string}  user_type  服务商用户类型
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj              目录结构层级列表
     * @apiSuccess {number}   	obj.id           银行卡ID
     * @apiSuccess {string}   	obj.bank_card    银行卡卡号
     * @apiSuccess {char}   	obj.default_flag 是否为默认
     * @apiSuccess {char}   	obj.bind_state   绑定状态
     * @apiSuccess {string}   	obj.bank_name    银行名称
     * @apiSuccess {string}   	obj.logo_url     银行logo
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBankCardList(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.queryBankCardList(request));
    }

    /**
     * @api {post} /{project_name}/bank_card/company_bind 公司银行卡绑定申请提交操作
     * @apiGroup bank_card
     * @apiDescription  公司银行卡绑定申请提交操作
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   user_type       服务商用户类型
     * @apiParam {number}   mobile_phone    手机号码
     * @apiParam {string}   bank_card       银行卡卡号
     * @apiParam {string}   bank_code       银行代码
     * @apiParam {char}     default_flag    是否为默认
     * @apiParam {char}     bind_state      绑定状态
     * @apiParam {number}   public_user_id  创建人ID
     * @apiParam {char}     bind_type       绑定类型
     * @apiParam {number}   bank_clscode    银行编码
     * @apiParam {string}   user_type       服务商用户类型
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/company_bind", method = RequestMethod.POST)
    @ResponseBody
    public Packet bindBankCardForCompany(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.bindBankCardForCompany(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/bank_card/personal_bind_apply 个人银行卡绑定申请
     * @apiGroup bank_card
     * @apiDescription  个人银行卡绑定申请操作
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   user_type    服务商用户类型
     * @apiParam {string}   bank_card    银行卡卡号
     * @apiParam {string}   bank_code    银行代码
     * @apiParam {number}   mobile_phone 手机号码
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/personal_bind_apply", method = RequestMethod.POST)
    @ResponseBody
    public Packet bindBankCardApplyForPersonal(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.bindBankCardApplyForPersonal(request));
    }

    /**
     * @api {post} /{project_name}/bank_card/check_amount 小额验证
     * @apiGroup bank_card
     * @apiDescription  银行卡绑定验证（小额验证）
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id          银行卡ID
     * @apiParam {string}   tran_amount 验证金额
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/check_amount", method = RequestMethod.POST)
    @ResponseBody
    public Packet checkBankCardForAmount(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.checkBankCardForAmount(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/bank_card/check_note 短信验证
     * @apiGroup bank_card
     * @apiDescription  银行卡绑定验证（短信验证）
     * @apiVersion 0.0.1
     *
     * @apiParam {string}   user_type 用户类型
     * @apiParam {string}   bank_card 银行卡卡号
     * @apiParam {string}   bank_code 银行代码
     * @apiParam {string}   mobile_phone 绑定银行手机号
     *
     * @apiSuccess {boolean}    state   接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/check_note", method = RequestMethod.POST)
    @ResponseBody
    public Packet checkBankCardForNote(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.checkBankCardForNote(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/bank_card/unBind 解绑银行卡
     * @apiGroup bank_card
     * @apiDescription  解绑提现银行卡
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id  银行卡ID
     *
     * @apiSuccess {boolean}   state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message 接口返回信息
     */
    @RequestMapping(value = "/unBind", method = RequestMethod.POST)
    @ResponseBody
    public Packet unBindForBankCard(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.unBindForBankCard(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/bank_card/remove 删除银行卡
     * @apiGroup bank_card
     * @apiDescription  删除已绑定银行卡
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id  银行卡ID
     *
     * @apiSuccess {boolean}   state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message 接口返回信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeBankCard(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.removeBankCard(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }



    /**
     * @api {post} /{project_name}/bank_card/edit_default 设置默认的银行卡
     * @apiGroup bank_card
     * @apiDescription  设置默认的银行卡
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id  银行卡ID
     *
     * @apiSuccess {boolean}   state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message 接口返回信息
     */
    @RequestMapping(value = "/edit_default", method = RequestMethod.POST)
    @ResponseBody
    public Packet editDefaultBankCard(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.editDefaultBankCard(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api {post} /{project_name}/bank_card/edit_default 获取银行列表
     * @apiGroup bank_card
     * @apiDescription  获取银行列表信息
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}   state    接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}    message  接口返回信息
     * @apiSuccess {object[]}   obj              目录结构层级列表
     * @apiSuccess {string}   	obj.bank_card    银行卡卡号
     * @apiSuccess {char}   	obj.bank_code    银行代码
     * @apiSuccess {string}   	obj.bank_clscode 银行编码
     * @apiSuccess {string}   	obj.logo_url     银行logo
     */
    @RequestMapping(value = "/bank_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBankList(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.queryBankList(request));
    }

    /**
     * @api {post}/{scs_server}/bank_card/province_list 省份列表
     * @apiGroup
     * @apiName  bank_card_province_list
     * @apiDescription 查询省份列表
     * @apiVersion 0.0.0
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     * @apiSucess {List} obj  数组集合
     * @apiSucess {List} obj.node_code  区域代码
     * @apiSucess {List} obj.node_name  区域名称
     */
    @RequestMapping(value = "/province_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayProvince(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.queryPayProvince(request));
    }

    /**
     * @api {post}/{scs_server}/bank_card/city_list 城市列表
     * @apiGroup
     * @apiName  bank_card_city_list
     * @apiDescription 查询城市列表
     * @apiVersion 0.0.0
     *
     * @apiParam {string} node_code     区域代码
     *
     * @apiSucess {boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     * @apiSucess {List} obj  数组集合
     * @apiSucess {List} obj.node_code  区域代码
     * @apiSucess {List} obj.node_name  区域名称
     * @apiSucess {List} obj.city_code  银行网点代码
     */
    @RequestMapping(value = "/city_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayCity(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.queryPayCity(request));
    }

    /**
     * @api {post}/{scs_server}/bank_card/area_list 查询区域列表
     * @apiGroup
     * @apiName  bank_card_area_list
     * @apiDescription 查询区域列表（包过市、县）
     * @apiVersion 0.0.0
     *
     * @apiParam {string} node_code     区域代码
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     * @apiSucess {List} obj  数组集合
     * @apiSucess {List} obj.node_code  区域代码
     * @apiSucess {List} obj.node_name  区域名称
     * @apiSucess {List} obj.city_code  银行网点代码
     */
    @RequestMapping(value = "/area_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryPayArea(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.queryPayArea(request));
    }

    /**
     * @api {post}/{scs_server}/bank_card/dot_bank_list 查询银行网点
     * @apiGroup
     * @apiName  bank_card_dot_bank_list
     * @apiDescription 查询银行网点列表
     * @apiVersion 0.0.0
     *
     * @apiParam {string} bank_code     银行代码
     * @apiParam {string} city_code   城市银行网点代码
     * @apiParam {string} bank_name   网点银行名称
     *
     * @apiSucess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSucess {string} message 接口返回信息
     * @apiSucess {List} obj            银行网点列表集合
     * @apiSucess {List} obj.bank_no    支付行号
     * @apiSucess {List} obj.bank_name  网点银行名称
     */
    @RequestMapping(value = "/dot_bank_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryCnapsBankList(HttpServletRequest request) {
        return Transform.GetResult(this.bankCardService.queryCnapsBankList(request));
    }


    /**
     * @api {post} /{project_name}/bank_card/bind_user_info 获取綁卡用户信息
     * @apiGroup bind_user_info
     * @apiDescription  获取綁卡用户信息
     * @apiVersion 0.0.1
     *
     * @apiParam {string} user_type  服务商用户类型
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     */
    @RequestMapping(value = "/bind_user_info", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryBankCardBindUserInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = this.bankCardService.queryBankCardBindUserInfo(request);
        } catch (Exception ex) {
            pr.setState(false);
            pr.setMessage(ex.getMessage());
        }
        return Transform.GetResult(pr);
    }



}
