package com.tk.oms.basicinfo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.tk.oms.basicinfo.dao.IssuingGradeDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.PageUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


@Service("IssuingGradeService")
public class IssuingGradeService {
	
	@Resource
    private IssuingGradeDao issuingGradeDao;

    /**
     * 添加代发等级
     *
     * @param request
     * @return
     */
    public ProcessResult add(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            //判断是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验Grade_name是否传入
            if(StringUtils.isEmpty(params.get("grade_name"))) {
                pr.setState(false);
                pr.setMessage("缺少参数grade_name");
                return pr;
            }
            if(StringUtils.isEmpty(params.get("piece_cost"))) {
                pr.setState(false);
                pr.setMessage("缺少参数piece_cost");
                return pr;
            }
            if(this.issuingGradeDao.queryIssuingGradeCountByName(params) > 0){
                pr.setState(false);
                pr.setMessage("等级名称已存在");
                return pr;
            }
            if(this.issuingGradeDao.countIssuingGrade(params) > 0){
                //保存数据
                if(issuingGradeDao.insertIssuingGrade(params) > 0) {
                    //保存成功，返回保存内容
                    pr.setState(true);
                    pr.setMessage("添加成功");
                    pr.setObj(params);
                }
            }else{
                if(issuingGradeDao.insertDefaultIssuingGrade(params) > 0) {
                    //保存成功，返回保存内容
                    pr.setState(true);
                    pr.setMessage("添加成功");
                    pr.setObj(params);
                }
            }

        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

    /**
     * 编辑代发等级
     *
     * @param request
     * @return
     */
    public ProcessResult edit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            //判断是否传入参数
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验id是否传入
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if(this.issuingGradeDao.queryIssuingGradeCountByName(params) > 0){
                pr.setState(false);
                pr.setMessage("等级名称已存在");
                return pr;
            }
            //保存数据
            if (issuingGradeDao.updateIssuingGrade(params) > 0) {
                //保存成功，返回保存内容
                pr.setState(true);
                pr.setMessage("更新成功");
                pr.setObj(params);
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 查询代发等级列表
     *
     * @param request
     * @return
     */
    public GridResult list(HttpServletRequest request) {
    	GridResult pr = new GridResult();

        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> issuingGrade = new HashMap<String, Object>();
            //判断是否传入参数
            if(!StringUtils.isEmpty(json)) {
            	issuingGrade = (Map<String, Object>) Transform.GetPacket(json,
    					Map.class);
            	GridResult pageParamResult=PageUtil.handlePageParams(issuingGrade);
            	if(pageParamResult!=null){
    				return pageParamResult;
    			}
            }
            int countIssuingGrade=issuingGradeDao.countIssuingGrade(issuingGrade);
            List<Map<String,Object>> list = issuingGradeDao.queryList(issuingGrade);
            //保存数据
            if(list != null &&list.size()>0) {
                //保存成功，返回保存内容
                pr.setState(true);
                pr.setMessage("获取成功");
                pr.setObj(list);
                pr.setTotal(countIssuingGrade);
            }else{
                pr.setState(true);
                pr.setMessage("无数据");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }


    /**
     * 删除代发等级
     * @param request
     * @return
     */
    public ProcessResult remove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            //判断是否传入参数
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //校验id是否传入
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少参数id");
                return pr;
            }
            if (this.issuingGradeDao.queryUserCountByGradeId(params) > 0) {
                pr.setState(false);
                pr.setMessage("代发等级已被使用");
                return pr;
            }
            //删除操作
            if (issuingGradeDao.deleteIssuingGrade(params) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }

    /**
     * 排序
     * @param request
     * @return
     */
    public ProcessResult sort(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();

        try {
            String json = HttpUtil.getRequestInputStream(request);
            //校验是否传入参数
            if(StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }

            Map<String, Object> sortMap = (HashMap<String, Object>) Transform.GetPacket(json, HashMap.class);
            //校验fromId参数
            if(StringUtils.isEmpty(sortMap.get("fromId"))) {
                pr.setState(false);
                pr.setMessage("缺少参数fromId");
                return pr;
            }
            //校验toId
            if(StringUtils.isEmpty(sortMap.get("toId"))) {
                pr.setState(false);
                pr.setMessage("缺少参数toId");
                return pr;
            }

            if(issuingGradeDao.sortIssuingGrade(sortMap) > 0) {
                pr.setState(true);
                pr.setMessage("排序成功");
            } else {
                pr.setState(false);
                pr.setMessage("排序失败");
            }
        } catch (IOException e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

    
    /**
     * 查询代发等级列表 -- 所有
     *
     * @param request
     * @return
     */
    public ProcessResult listAll(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();

        try {

            List<Map<String, Object>> list = issuingGradeDao.queryIssuingGradeAll();
            //保存数据
            if(list != null) {
                //保存成功，返回保存内容
                pr.setState(true);
                pr.setMessage("获取成功");
                pr.setObj(list);
            }else{
                pr.setState(false);
                pr.setMessage("获取失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }

        return pr;
    }

}
