package com.tk.oms.product.service;

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

import com.tk.oms.product.dao.ProductGroupDao;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;

@Service("ProductGroupService")
public class ProductGroupService {
	private Log logger = LogFactory.getLog(this.getClass());
	@Resource
	private ProductGroupDao productGroupDao;
	/**
	 * 查询商品分组列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult queryProductGroupList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        //查询商品分组数据
	        List<Map<String, Object>> groupList = productGroupDao.queryProductGroupList(paramMap);
            pr.setState(true);
            pr.setMessage("查询商品分组信息成功");
            pr.setObj(groupList);
	        
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询商品分组信息失败："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 添加商品分组
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult addProductGroup(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("group_name", paramMap.get("group_name"));
	        if(productGroupDao.isExist(map)>0){
                pr.setState(false);
                pr.setMessage("分组名称已存在");
                return pr;
	        }
	        paramMap.put("parent_id",(paramMap.get("parent_id")==null?0:paramMap.get("parent_id")));
	        //新增
	        if(productGroupDao.insert(paramMap)>0){
	        	pr.setState(true);
	        	pr.setMessage("新增成功");
	        	pr.setObj(paramMap);
	        }else{
	        	pr.setState(false);
	        	pr.setMessage("新增失败");
	        }
	        
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("新增商品分组信息失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return pr;
	}

	@SuppressWarnings("unchecked")
	public ProcessResult editProductGroup(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
	        if(productGroupDao.update(paramMap)>0){
	        	pr.setState(true);
	        	pr.setMessage("更新成功");
	        }else{
	        	pr.setState(false);
	        	pr.setMessage("更新失败");
	        }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新商品分组信息失败："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 删除商品分组信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult removeProductGroup(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
	        Map<String,Object> retMap = productGroupDao.queryChildrenByParentId(paramMap);
        	String group_name = retMap.get("GROUP_NAME").toString();
        	int children = Integer.parseInt(retMap.get("CHILDREN").toString());
        	if(children>0){
        		 pr.setState(false);
                 pr.setMessage("分组"+group_name+"存在子节点，不允许删除,请先删除子节点");
                 return pr;
        	}
	        //删除
	        if(productGroupDao.delete(paramMap)>0){
	        	pr.setState(true);
	        	pr.setMessage("删除成功");
	        }else{
	        	pr.setState(false);
	        	pr.setMessage("删除失败");
	        }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("删除商品分组信息失败："+e.getMessage());
        }

        return pr;
	}
	
	/**
	 * 获取商品分组层级信息
	 * @param request
	 * @return
	 */
    public ProcessResult queryProductGroupAll(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //查询商品分组层级信息
            List<Map<String, Object>> groupList = productGroupDao.queryProductGroupAll();
            pr.setState(true);
            pr.setMessage("获取商品分组信息成功");
            pr.setObj(groupList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("查询失败："+e.getMessage());
        }
        return pr;
    }
    /**
     * 排序
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	public ProcessResult productGroupSort(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
	        if(StringUtils.isEmpty(paramMap.get("type"))){
		        if(StringUtils.isEmpty(paramMap.get("id1"))||StringUtils.isEmpty(paramMap.get("id2"))){
	                pr.setState(false);
	                pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
	                return pr;
	            }
		        Map<String, Object> t1 = productGroupDao.queryById(Long.parseLong(paramMap.get("id1").toString()));
		        Map<String, Object> t2 = productGroupDao.queryById(Long.parseLong(paramMap.get("id2").toString()));
		        t1.put("id", paramMap.get("id1"));
	            t1.put("sortid", t2.get("SORTID"));
	            t2.put("id", paramMap.get("id2"));
	            t2.put("sortid", t1.get("SORTID"));
	            if(productGroupDao.update(t1)>0&&productGroupDao.update(t2)>0){
	                pr.setState(true);
	                pr.setMessage("排序字段修改成功");
	                pr.setObj(null);
	            } else {
	                pr.setState(false);
	                pr.setMessage("排序字段修改失败");
	            }
	        }else{
            	if(StringUtils.isEmpty(paramMap.get("id"))){
                    pr.setState(false);
                    pr.setMessage("参数缺失，分组ID为空");
                    return pr;
                }
            	if(StringUtils.isEmpty(paramMap.get("parent_id"))){
                    pr.setState(false);
                    pr.setMessage("参数缺失，分组的父id为空");
                    return pr;
                }
            	if(productGroupDao.update(paramMap)>0){
            		pr.setState(true);
                    pr.setMessage("排序字段修改成功");
            	}else{
            		pr.setState(false);
                    pr.setMessage("排序字段修改失败");
            	}
            }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 是否启用
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult editProductGroupByState(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(paramMap.size() == 0) {
            	pr.setState(false);
            	pr.setMessage("参数缺失");
                return pr;
            }
	        //更新分组状态
	        if(productGroupDao.update(paramMap)>0){
                pr.setState(true);
                pr.setMessage("更新成功");
            } else {
                pr.setState(false);
                pr.setMessage("更新失败");
            }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
        }

        return pr;
	}
	/**
	 * 批量更新商品分组
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ProcessResult batchEditProductGroup(HttpServletRequest request) {
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
                for(Map<String, Object> map:list){//重名判断
                    if(productGroupDao.isExist(map)>0){
                        pr.setState(false);
                        pr.setMessage("分组名称["+map.get("group_name")+"]已存在");
                        return pr;
                    }
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
                    if(productGroupDao.insert(map)>0){
                    	returlList.add(map);
                    }
                }
            }
            if(updateList.size()>0)
            	productGroupDao.batchUpdate(updateList);
            returlList.addAll(updateList);
            pr.setState(true);
            pr.setMessage("批量编辑商品分组成功");
            pr.setObj(returlList);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage("批量编辑商品分组失败："+e.getMessage());
            logger.error("批量编辑商品分组失败："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
	}
	
	
	/**
	 * 是否展开
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProcessResult updateIsDisplay(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
	        String json = HttpUtil.getRequestInputStream(request);

	        if(!StringUtils.isEmpty(json)) {
	        	paramMap = (Map<String, Object>) Transform.GetPacket(json, HashMap.class);
	        }
	        if(StringUtils.isEmpty(paramMap.get("id"))||StringUtils.isEmpty(paramMap.get("is_display"))) {
            	pr.setState(false);
            	pr.setMessage("参数必要缺失");
                return pr;
            }
	        //更新分组状态
	        if(productGroupDao.updateIsDisplay(paramMap)>0){
                pr.setState(true);
                pr.setMessage("更新成功");
            } else {
                pr.setState(false);
                pr.setMessage("更新失败");
            }
		} catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            logger.error("更新失败："+e.getMessage());
        }

        return pr;
	}

}
