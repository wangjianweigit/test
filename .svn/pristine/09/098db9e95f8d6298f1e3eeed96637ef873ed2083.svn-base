package com.tk.oms.stationed.control;

import com.tk.oms.stationed.service.StationedEvaluateService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 入驻商评估表相关内容
 */
@Controller
@RequestMapping("/stationed_evaluate")
public class StationedEvaluateControl {

    @Resource
    private StationedEvaluateService stationedEvaluateService;

    /**
     * @api {post} /{project_name}/stationed_evaluate/list 入驻商评估表
     * @apiName list
     * @apiGroup stationed_evaluate
     * @apiDescription  获取入驻商评估表数据
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商信息
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryList(HttpServletRequest request) {
        return Transform.GetResult(stationedEvaluateService.queryList(request));
    }

    /**
     * @api {post} /{project_name}/stationed_evaluate/detail 入驻商评估表
     * @apiName detail
     * @apiGroup stationed_evaluate
     * @apiDescription  获取入驻商评估表数据
     * @apiVersion 0.0.1
     *
     * @apiSuccess {boolean}    state 接口获取数据是否成功.true:成功  false:失败
     * @apiSuccess {string}     message 接口返回信息
     * @apiSuccess {object[]}   obj 查询入驻商信息
     *
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryDetail(HttpServletRequest request) {
        return Transform.GetResult(stationedEvaluateService.queryDetail(request));
    }
}