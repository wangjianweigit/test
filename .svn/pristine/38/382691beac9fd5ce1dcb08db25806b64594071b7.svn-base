package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tk.oms.basicinfo.service.StoreInfoService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/store_info")
public class StoreInfoControl {
	@Resource
    private StoreInfoService storeInfoService;
	/**
	 * @api{post} /{oms_server}/store_info/add 门店新增
     * @apiGroup add
     * @apiName add
     * @apiDescription  门店新增
     * @apiVersion 0.0.1
     * @apiSuccess {number} ORGANIZATION_ID    门店关联组织架构ID，关联组织架构表TBL_SYS_ORGANIZATION_INFO的主键
     * @apiSuccess {number} STORE_MANAGER_ID    门店店长ID
     * @apiSuccess {string} CREATE_DATE    创建时间
     * @apiSuccess {string} STATE    门店状态：1：停运  2:运营中
     * @apiSuccess {string} OFF_DATE    停运时间
     * @apiSuccess {number} ADDRESS_MAX    大区
     * @apiSuccess {number} ADDRESS_PROVINCE    省份ID
     * @apiSuccess {number} ADDRESS_CITY    城市ID
     * @apiSuccess {number} ADDRESS_COUNTY    区县ID
     * @apiSuccess {string} LAST_UPDATE_TIME    最后更新时间(同步用)
     * @apiSuccess {string} VERIFY_CODE    验证码
     * @apiSuccess {number} SITE_ID    关联站点ID
     * @apiSuccess {number} ID    编号
     * @apiSuccess {string} STORE_CODE    门店代码
     * @apiSuccess {string} STORE_NAME    门店名称
     * @apiSuccess {string} STORE_ADDRESS    门店地址
     * @apiSuccess {string} STORE_PHONE    门店电话
     * @apiSuccess {number} STORE_LIMIT    门店额度
     * @apiSuccess {string} STORE_MOBILE_PHONE    门店手机
     * @apiSuccess {number} CREATE_USER_ID    创建人ID
     *@apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 新增门店信息
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet addStoreInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = storeInfoService.addStoreInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
	 * @api{post} /{oms_server}/store_info/edit 门店更新
     * @apiGroup edit
     * @apiName edit
     * @apiDescription  门店更新
     * @apiVersion 0.0.1
     * @apiSuccess {number} ORGANIZATION_ID    门店关联组织架构ID，关联组织架构表TBL_SYS_ORGANIZATION_INFO的主键
     * @apiSuccess {number} STORE_MANAGER_ID    门店店长ID
     * @apiSuccess {string} CREATE_DATE    创建时间
     * @apiSuccess {string} STATE    门店状态：1：停运  2:运营中
     * @apiSuccess {string} OFF_DATE    停运时间
     * @apiSuccess {number} ADDRESS_MAX    大区
     * @apiSuccess {number} ADDRESS_PROVINCE    省份ID
     * @apiSuccess {number} ADDRESS_CITY    城市ID
     * @apiSuccess {number} ADDRESS_COUNTY    区县ID
     * @apiSuccess {string} LAST_UPDATE_TIME    最后更新时间(同步用)
     * @apiSuccess {string} VERIFY_CODE    验证码
     * @apiSuccess {number} SITE_ID    关联站点ID
     * @apiSuccess {number} ID    编号
     * @apiSuccess {string} STORE_CODE    门店代码
     * @apiSuccess {string} STORE_NAME    门店名称
     * @apiSuccess {string} STORE_ADDRESS    门店地址
     * @apiSuccess {string} STORE_PHONE    门店电话
     * @apiSuccess {number} STORE_LIMIT    门店额度
     * @apiSuccess {string} STORE_MOBILE_PHONE    门店手机
     * @apiSuccess {number} CREATE_USER_ID    创建人ID
     *@apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 修改门店信息
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editStoreInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = storeInfoService.editStoreInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/store_info/list 查询门店列表
     * @apiGroup list
     * @apiName list
     * @apiDescription  查询门店列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 门店信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreInfoList(HttpServletRequest request) {
        return Transform.GetResult(storeInfoService.queryStoreInfoList(request));
    }
    
    /**
     * @api{post} /{oms_server}/store_info/detail 通过ID查询门店详情
     * @apiGroup detail
     * @apiName detail
     * @apiDescription  通过ID查询门店详情
     * @apiVersion 0.0.1
     * @apiParam{number} [ID] id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 门店信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryStoreInfoDetail(HttpServletRequest request) {
        return Transform.GetResult(storeInfoService.queryStoreInfoDetail(request));
    }
    /**
     * @api{post} /{oms_server}/store_info/remove 门店信息删除
     * @apiGroup remove
     * @apiName remove
     * @apiDescription  热门关键字删除
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 热门关键字id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 热门关键字信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet deleteStoreInfo(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            pr = storeInfoService.deleteStoreInfo(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    /**
     * @api{post} /{oms_server}/store_info/update_verify_code 更新门店验证码
     * @apiGroup update_verify_code
     * @apiName update_verify_code
     * @apiDescription  更新门店验证码
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 门店id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 门店信息
     */
    @RequestMapping(value = "/update_verify_code", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateVerifyCode(HttpServletRequest request) {
    	 ProcessResult pr = new ProcessResult();
         try {
             pr = storeInfoService.updateVerifyCode(request);
         } catch (Exception e) {
             pr.setState(false);
             pr.setMessage(e.getMessage());
         }
         return Transform.GetResult(pr);
    }
    /**
     * @api{post} /{oms_server}/store_info/update_credit 更新门店授信额度
     * @apiGroup update_credit
     * @apiName update_credit
     * @apiDescription  更新门店授信额度
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 门店id
     * @apiParam{double} store_limit 授信额度
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 门店信息
     */
    @RequestMapping(value = "/update_credit", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateCredit(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            pr = storeInfoService.updateCredit(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }
    
    /**
     * @api{post} /{oms_server}/store_info/update_state 更新门店状态
     * @apiGroup update_state
     * @apiName update_state
     * @apiDescription  更新门店状态
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 门店id
     * @apiParam{number} state 状态
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 门店信息
     */
    @RequestMapping(value = "/update_state", method = RequestMethod.POST)
    @ResponseBody
    public Packet updateState(HttpServletRequest request) {
    	ProcessResult pr = new ProcessResult();
        try {
            pr = storeInfoService.updateState(request);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return Transform.GetResult(pr);
    }

    
    /**
     * @api{post} /{oms_server}/store_info/all_list 查询所有门店数据
     * @apiGroup all_list
     * @apiName all_list
     * @apiDescription  查询所有门店数据
     * @apiVersion 0.0.1
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 门店信息
     */
    @RequestMapping(value = "/all_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryAllStoreInfo(HttpServletRequest request) {
    	return Transform.GetResult(storeInfoService.queryAllStoreInfo(request));
    }
}
