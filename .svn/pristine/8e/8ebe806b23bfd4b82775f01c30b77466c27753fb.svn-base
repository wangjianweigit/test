package com.tk.oms.marketing.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.marketing.service.MemberGradeService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/member_grade")
public class MemberGradeControl {
	@Resource
	private MemberGradeService memberGradeService;
	
    /**
     * @api {post} /{project_name}/member_grade/list
     * @apiGroup list
     * @apiName member_grade
     * @apiDescription  查询积分等级列表
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {object[]} obj   积分等级列表
     * @apiSuccess {number}   obj.id   主建ID
     * @apiSuccess {string}   obj.grade_name   等级名称
     * @apiSuccess {string}   obj.grade_code   等级代码
     * @apiSuccess {number}   obj.min_score    等级最低分数，包含
     * @apiSuccess {number}   obj.max_score    等级最高分数，不包含
     * @apiSuccess {number}   obj.discount     等级折扣率，百分比制度，100表示不打折，50表示打5折
     * @apiSuccess {string}   obj.remark       备注信息
     * 
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberGradeList(HttpServletRequest request) {
        return Transform.GetResult(memberGradeService.queryMemberGradeList(request));
    }
    
    /**
     * @api {post} /{project_name}/member_grade/edit 
     * @apiGroup edit
     * @apiName member_grade
     * @apiDescription  编辑积分等级
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id   等级名称
     * @apiParam {string}   grade_name   等级名称
     * @apiParam {string}   grade_code   等级名称
     * @apiParam {number}   min_score    等级最低分数，包含
     * @apiParam {number}   max_score    等级最高分数，不包含
     * @apiParam {number}   discount     等级折扣率，百分比制度，100表示不打折，50表示打5折
     * @apiParam {string}   remark       备注信息
     *
     * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * 
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editMemberGrade(HttpServletRequest request) {
        return Transform.GetResult(memberGradeService.editMemberGrade(request));
    }
    
    /**
     * @api {post} /{project_name}/member_grade/remove  
     * @apiGroup remove
     * @apiName member_grade
     * @apiDescription  删除积分等级
     * @apiVersion 0.0.1
     *
     * @apiParam {number}   id   等级id
     *
     * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {number} obj     删除数量
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet removeMemberGrade(HttpServletRequest request) {
       return Transform.GetResult(memberGradeService.removeMemberGrade(request));
    }
     
    /**
     * @api {post} /{project_name}/member_grade/all
     * @apiGroup all
     * @apiName member_grade
     * @apiDescription  获取全部会员等级(下拉框)
     * @apiVersion 0.0.1
     *
     *
     * @apiSuccess {boolean} state  接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string} message 接口返回信息.
     * @apiSuccess {object[]} obj   积分等级列表
     * @apiSuccess {number}   obj.id   主建ID
     * @apiSuccess {string}   obj.grade_name   等级名称
     * @apiSuccess {number}   obj.min_score    等级最低分数，包含
     * @apiSuccess {number}   obj.max_score    等级最高分数，不包含
     * @apiSuccess {number}   obj.discount     等级折扣率，百分比制度，100表示不打折，50表示打5折
     * @apiSuccess {string}   obj.remark       备注信息
     * @apiSuccess {date}     obj.create_date  创建时间
     * 
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryMemberGradeAll(HttpServletRequest request) {
        return Transform.GetResult(memberGradeService.queryMemberGradeAll(request));
    }
}
