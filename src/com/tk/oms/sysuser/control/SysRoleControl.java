package com.tk.oms.sysuser.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tk.oms.sysuser.service.SysRoleService;
import com.tk.sys.util.Packet;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

/**
 * @author wangpeng
 * @data 2016/4/26 0001.
 * 权限管理Control
 */
@Controller
@RequestMapping("/role")
public class SysRoleControl {
    @Resource
    private SysRoleService sysRoleService;

    /*****************************************************************菜单节点*******************************************************************************************************/
    
    /**
     *新增菜单或节点
     */
    @RequestMapping(value = "/node_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet node_add(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.addSysNode(request));
    }
    /**
     *删除菜单或节点
     */
    @RequestMapping(value = "/node_delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet node_delete(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try{
    		 return Transform.GetResult(sysRoleService.delSysNode(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    /**
     *修改菜单或节点
     */
    @RequestMapping(value = "/node_update", method = RequestMethod.POST)
    @ResponseBody
    public Packet node_update(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.updateSysNode(request));
    }
    /**
     *菜单或节点列表
     */
    @RequestMapping(value = "/node_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet node_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysNodeList(request));
    }

    /**
     *菜单或节点详情
     */
    @RequestMapping(value = "/node_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet node_detail(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysNodeDetail(request));
    }
    
    /*****************************************************************角色*******************************************************************************************************/
    
    /**
     *新增角色
     */
    @RequestMapping(value = "/role_add", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_add(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.addSysRole(request));
    }
    /**
     *删除角色
     */
    @RequestMapping(value = "/role_delete", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_delete(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.delSysRole(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    /**
     *修改角色
     */
    @RequestMapping(value = "/role_update", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_update(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.updateSysRole(request));
    }
    /**
     *角色列表
     */
    @RequestMapping(value = "/role_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysRoleList(request));
    }

    /**
     *角色详情
     */
    @RequestMapping(value = "/role_detail", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_detail(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysRoleDetail(request));
    }
    
    /*****************************************************************角色菜单节点管理*******************************************************************************************************/
    
    /**
     *配置角色菜单节点权限
     */
    @RequestMapping(value = "/role_node_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_node_edit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.editSysRoleNode(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    /**
     *查询菜单或按钮节点列表，如果有权限则选中
     */
    @RequestMapping(value = "/role_node_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_node_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysRoleNode(request));
    }
    
    /*****************************************************************角色字段管理*******************************************************************************************************/
    
    /**
     *配置角色字段权限
     */
    @RequestMapping(value = "/role_field_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_filed_edit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.editSysRoleField(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    /**
     *查询字段列表，如果有权限则选中
     */
    @RequestMapping(value = "/role_field_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet role_filed_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysRoleField(request));
    }
    
    /*****************************************************************用户门店管理*******************************************************************************************************/
    /**
     *配置用户门店权限
     */
    @RequestMapping(value = "/user_store_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_store_edit(HttpServletRequest request) {
        
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.editSysUserStore(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    
    /**
     *查询门店列表，如果有权限则选中
     */
    @RequestMapping(value = "/user_store_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_store_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysUserStore(request));
    }
    
    /*****************************************************************用户站点管理*******************************************************************************************************/
    /**
     *配置用户站点权限
     */
    @RequestMapping(value = "/user_site_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_site_edit(HttpServletRequest request) {
        
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.editSysUserSite(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    
    /**
     *查询站点列表，如果有权限则选中
     */
    @RequestMapping(value = "/user_site_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_site_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysUserSite(request));
    }
    
    /*****************************************************************用户角色管理*******************************************************************************************************/
    /**
     *配置用户角色权限
     */
    @RequestMapping(value = "/user_role_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_role_edit(HttpServletRequest request) {
        
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.editSysUserRole(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    
    /**
     *查询角色列表，如果有权限则选中
     */
    @RequestMapping(value = "/user_role_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_role_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysUserRole(request));
    }
    
    /**
     *查询用户权限节点列表
     */
    @RequestMapping(value = "/user_node_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_node_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysUserNode(request));
    }
    
    /**
     *查询用户权限节点列表
     */
    @RequestMapping(value = "/user_filed_nolist", method = RequestMethod.POST)
    @ResponseBody
    public Packet user_filed_list(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysUserNoField(request));
    }
    
    /**
     * 配置用户私有门店权限
     */
    @RequestMapping(value = "/user_pvtp_store_edit", method = RequestMethod.POST)
    @ResponseBody
    public Packet editSysUserPvtpStore(HttpServletRequest request) {
        
        ProcessResult pr = new ProcessResult();
    	try{
    		return Transform.GetResult(sysRoleService.editSysUserPvtpStore(request));
    	} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            return Transform.GetResult(pr);
        }
    }
    
    /**
     * 查询私有门店列表，如果有权限则选中
     */
    @RequestMapping(value = "/user_pvtp_store_list", method = RequestMethod.POST)
    @ResponseBody
    public Packet queryUserPvtpStoreList(HttpServletRequest request) {
        return Transform.GetResult(sysRoleService.querySysUserPvtpStore(request));
    }
}
