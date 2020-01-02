package com.tk.oms.sysuser.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tk.oms.sysuser.dao.SysOrganizationDao;
import com.tk.oms.sysuser.dao.SysUserInfoDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;


@Service("SysOrganizationService")
public class SysOrganizationService {
	private Log logger = LogFactory.getLog(this.getClass());
    @Resource
    private SysOrganizationDao sysOrganizationDao;
    @Resource
    private SysUserInfoDao sysUserInfoDao;
    /**
     * 查询组织架构信息列表
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult querySysOrganizationList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                //参数实体
            	map = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            // 数据库用户
            List<Map<String, Object>> dataList = sysOrganizationDao.querySysOrganizationList(map);
            if (dataList != null && dataList.size() > 0) {
				pr.setState(true);
				pr.setMessage("获取组织架构信息列表成功");
				pr.setObj(dataList);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 新增组织架构信息
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional
	public ProcessResult addSysOrganization(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json)){
                //参数实体
                map = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            }
            if(StringUtils.isEmpty(map.get("organization_name"))){
                pr.setState(false);
                pr.setMessage("参数错误，组织信息名称不能为空");
                return pr;
            }
            if(StringUtils.isEmpty(map.get("parent_id"))){
                pr.setState(false);
                pr.setMessage("参数错误，组织信息parent_id不能为空");
                return pr;
            }
            if(StringUtils.isEmpty(map.get("manager_limit"))){
                pr.setState(false);
                pr.setMessage("参数错误，组织信息manager_limit不能为空");
                return pr;
            }
            if(sysOrganizationDao.insert(map)>0){
                pr.setState(true);
                pr.setMessage("新增组织信息成功");
                pr.setObj(map);
            } else {
                pr.setState(false);
                pr.setMessage("新增组织信息失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException("新增组织信息失败："+e.getMessage());
        }
        return pr;
    }
    /**
     * 批量更新组织信息信息，存在则更新，不存在则新增
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional
    public ProcessResult batchEditOrganization(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            //将数据拆分为待插入，待更新两部分
            List<Map<String, Object>> insertList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> updateList =  new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> returlList =  new ArrayList<Map<String, Object>>();//返回值，用于更新界面上的ID

            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
            	paramMap = (Map<String, Object>)Transform.GetPacket(json, Map.class);
            List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("dataList");
            if(list!=null&&list.size()>0){
                for(Map<String, Object> map:list){
                    if(StringUtils.isEmpty(map.get("id"))){
                        insertList.add(map);
                    }else{
                        updateList.add(map);
                    }
                }
            }
            
            if(insertList.size()>0){
                for(Map<String, Object> map:insertList){
                	map.put("public_user_id", paramMap.get("public_user_id"));
                    if(sysOrganizationDao.insert(map)>0){
                    	returlList.add(map);
                    }
                }
            }
            if(updateList.size()>0)
            	sysOrganizationDao.batchUpdate(updateList);
            returlList.addAll(updateList);
            pr.setState(true);
            pr.setMessage("批量保存信息成功");
            pr.setObj(returlList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("批量编辑组织信息失败："+e.getMessage());
            logger.error("批量编辑组织信息失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }
    
    /**
     * 删除组织信息
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult removeOrganization(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String,Object> params = new HashMap<String,Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                //参数实体
                params = (Map<String,Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("id"))){
                pr.setState(false);
                pr.setMessage("参数错误，组织信息Id不能为空");
                return pr;
            }
            Map<String,Object> retMap = sysOrganizationDao.queryChildrenByParentId(params);
        	String organization_name = retMap.get("ORGANIZATION_NAME").toString();
        	int children = Integer.parseInt(retMap.get("CHILDREN").toString());
        	if(children>0){
        		 pr.setState(false);
                 pr.setMessage("组织【"+organization_name+"】存在子部门，不允许删除,请先删除子部门");
                 return pr;
        	}
            	
            params.put("organization_id", params.get("id"));
            //查询组织是否关联了用户
            int count = sysUserInfoDao.queryCount(params);
            if(count>0){
            	pr.setState(false);
                pr.setMessage("组织【"+organization_name+"】已有用户，请先解除用户的关联信息！");
                return pr;
            }
            	
            if(sysOrganizationDao.delete(params)>0){
                pr.setState(true);
                pr.setMessage("删除组织信息成功");
                pr.setObj(null);
            } else {
                pr.setState(false);
                pr.setMessage("删除组织信息失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
   
    /**
     * 查询所有组织架构信息列表
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryAllList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if(!StringUtils.isEmpty(json))
                //参数实体
            	map = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            // 数据库用户
            List<Map<String, Object>> dataList = sysOrganizationDao.queryAllList();
            if (dataList != null && dataList.size() > 0) {
				pr.setState(true);
				pr.setMessage("获取组织架构信息列表成功");
				pr.setObj(dataList);
			} else {
				pr.setState(true);
				pr.setMessage("无数据");
			}
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }
}