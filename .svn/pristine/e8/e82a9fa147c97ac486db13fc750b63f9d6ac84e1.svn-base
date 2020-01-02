package com.tk.oms.basicinfo.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.tk.oms.basicinfo.service.IssuingGradeService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;


@Controller
@RequestMapping("/issuing_grade")
public class IssuingGradeControl {
	@Resource
    private IssuingGradeService issuingGradeService;
	/**
     * @api{post} /{oms_server}/issuing_grade/add 代发等级新增
     * @apiGroup add
     * @apiName add
     * @apiDescription  代发等级新增
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{String} grade_name 代发等级名称
     * @apiParam{number} piece_cost 代发单件费用
     * @apiParam{string} remark 描述
     * @apiParam{number} CREATE_USER_ID 创建人id
     * @apiParam{date} CREATE_DATE 创建日期
     * @apiParam{string} IS_DEFAULT 1，表示会员默认代发等级。该字段只限标注使用。设为默认代发等级的ID必须设为1
     * @apiParam{string} SORT_ID 排序，越大越靠前
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 新增代发等级信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet add(HttpServletRequest request) {
        return Transform.GetResult(issuingGradeService.add(request));
    }

    /**
     * @api{post} /{oms_server}/issuing_grade/edit 代发等级修改
     * @apiGroup edit
     * @apiName edit
     * @apiDescription  代发等级修改
     * @apiVersion 0.0.1
     * @apiParam{number} id 主键
     * @apiParam{String} grade_name 代发等级名称
     * @apiParam{number} piece_cost 代发单件费用
     * @apiParam{string} remark 描述
     * @apiParam{number} CREATE_USER_ID 创建人id
     * @apiParam{date} CREATE_DATE 创建日期
     * @apiParam{string} IS_DEFAULT 1，表示会员默认代发等级。该字段只限标注使用。设为默认代发等级的ID必须设为1
     * @apiParam{string} SORT_ID 排序，越大越靠前
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 修改代发等级信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet edit(HttpServletRequest request) {
        return Transform.GetResult(issuingGradeService.edit(request));
    }

    /**
     * @api{post} /{oms_server}/issuing_grade/list 分页查询代发等级列表
     * @apiGroup list
     * @apiName list
     * @apiDescription  分页查询品牌信息列表
     * @apiVersion 0.0.1
     * @apiParam{number} [pageIndex=1] 起始页
     * @apiParam{number} [pageSize=10] 分页大小
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 代发等级信息
     * @apiSuccess{number} total 总条数
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet list(HttpServletRequest request) {
        return Transform.GetResult(issuingGradeService.list(request));
    }

    /**
     * @api{post} /{oms_server}/issuing_grade/remove 代发等级删除
     * @apiGroup remove
     * @apiName remove
     * @apiDescription  代发等级删除
     * @apiVersion 0.0.1
     * @apiParam{number} [id] 品牌id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 代发等级信息
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet delete(HttpServletRequest request) {
        return Transform.GetResult(issuingGradeService.remove(request));
    }

    /**
     * @api{post} /{oms_server}/issuing_grade/remove 代发等级排序
     * @apiGroup remove
     * @apiName remove
     * @apiDescription  代发等级排序
     * @apiVersion 0.0.1
     * @apiParam{number} [fromId] 需要排序的id
     * @apiParam{number} [toId]   需要排序的id
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 品牌信息
     */
    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public Packet sort(HttpServletRequest request) {
        return Transform.GetResult(issuingGradeService.sort(request));
    }
    /**
     * @api{post} /{oms_server}/issuing_grade/list_all 查询所有代发等级
     * @apiGroup list_all
     * @apiName list_all
     * @apiDescription  查询所有代发等级
     * @apiVersion 0.0.1
     * @apiSuccess{boolean} state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess{string} message 接口返回信息
     * @apiSuccess{object} obj 品牌信息
     */
    @RequestMapping(value = "/list_all", method = RequestMethod.POST)
    @ResponseBody
    public Packet listAll(HttpServletRequest request) {
        return Transform.GetResult(issuingGradeService.listAll(request));
    }
   
}
