package com.tk.oms.sysuser.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sysuser.service.DistributionRoleService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.Transform;

@Controller
@RequestMapping("/distribution_role")
public class DistributionRoleControl {
	
	@Resource
    private DistributionRoleService distributionRoleService;
	/**
     *查询角色列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Packet distributionRoleList(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.distributionRoleList(request));
    }
    
    /**
     *新增角色
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Packet distributionRoleAdd(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.distributionRoleAdd(request));
    }
    
    /**
     *编辑角色
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet roleEdit(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.roleEdit(request));
    }
    
    /**
     *删除角色
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Packet distributionRoleRemove(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.distributionRoleRemove(request));
    }
    
    /**
     *保存经销商对应角色
     */
    @RequestMapping(value = "/role_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet distributionRoleEdit(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.distributionRoleEdit(request));
    }
    
    /**
     *查询节点列表
     */
    @RequestMapping(value = "/node_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNodeList(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.queryNodeList(request));
    }
    
    /**
     *查询当前角色节点详情
     */
    @RequestMapping(value = "/node_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryNodeDetail(HttpServletRequest request) {
        return Transform.GetResult(distributionRoleService.queryNodeDetail(request));
    }

}
