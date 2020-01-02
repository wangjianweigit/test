package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.AddressLibraryManageDao;
import com.tk.oms.oss.server.GeneralExportService;
import com.tk.sys.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Copyright (c), 2018,  TongKu
 * FileName : AddressLibraryManageService
 *
 * @author: zhengfy
 * @version: 1.00
 * @date: 2018/6/15
 */
@Service("AddressLibraryManageService")
public class AddressLibraryManageService {

    @Resource
    private AddressLibraryManageDao addressLibraryManageDao;
    @Resource
    private GeneralExportService generalExportService;

    /**ERP服务地址*/
    @Value("${erp_service_url}")
    private String erp_service_url;
    /**ERP地址库同步*/
    @Value("${common_sync_region}")
    private String common_sync_region;

    /**OA服务地址*/
    @Value("${oa_service_url}")
    private String oa_service_url;
    /**OA地址库同步*/
    @Value("${sys_dictionary_sync_region}")
    private String sys_dictionary_sync_region;

    private static final String PATH = new StringBuffer(System.getProperty("user.dir")).append(File.separator).append("tempExportFile").toString();
    private static final String FILE_NAME = "address";

    /**
     * 批量编辑省市县数据
     * @param request
     * @return
     */
    public ProcessResult edit(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (!params.containsKey("list") || StringUtils.isEmpty(params.get("list"))) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).containsKey("name") || StringUtils.isEmpty(list.get(i).get("name"))) {
                    pr.setState(false);
                    pr.setMessage("请填写地址名称");
                    return pr;
                }
                if (!list.get(i).containsKey("code") || StringUtils.isEmpty(list.get(i).get("code"))) {
                    pr.setState(false);
                    pr.setMessage("请填写行政编码");
                    return pr;
                }
                for (int j = 0; i < list.size(); i++) {
                    if (list.get(i).get("parent_id").toString().equals(list.get(j).get("parent_id").toString()) && i != j) {
                        if (list.get(i).get("name").toString().equals(list.get(j).get("name").toString())) {
                            pr.setState(false);
                            pr.setMessage("名称" + list.get(i).get("name").toString() + "重复");
                            return pr;
                        }
                        if (list.get(i).get("code").toString().equals(list.get(j).get("code").toString())) {
                            pr.setState(false);
                            pr.setMessage("行政编码" + list.get(i).get("code").toString() + "重复");
                            return pr;
                        }

                    }
                }
            }
            if(this.addressLibraryManageDao.isExistCode(params) > 0){
                pr.setState(false);
                pr.setMessage("行政编码数据重复");
                return pr;
            }
            List<Map<String,Object>> res = addressLibraryManageDao.isExist(params);
            if(res.size() > 0) {
                pr.setObj(res);
                pr.setMessage("省市县数据存在重复");
                pr.setState(false);
                return pr;
            }
            addressLibraryManageDao.deleteByNameOrCode(params);
            if(addressLibraryManageDao.batchInsertOrUpdate(params) > 0) {
                pr.setState(true);
                pr.setMessage("保存草稿成功");
            } else {
                pr.setState(false);
                pr.setMessage("保存草稿失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
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
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if(StringUtils.isEmpty(params.get("type"))){
                if(StringUtils.isEmpty(params.get("id1"))||StringUtils.isEmpty(params.get("id2"))){
                    pr.setState(false);
                    pr.setMessage("参数错误，需要两个交换的id（id1、id2）");
                    return pr;
                }
                Map<String, Object> t1 = addressLibraryManageDao.queryById(Long.parseLong(params.get("id1").toString()));
                Map<String, Object> t2 = addressLibraryManageDao.queryById(Long.parseLong(params.get("id2").toString()));
                t1.put("id", params.get("id1"));
                t1.put("sort", t2.get("SORT"));
                t2.put("id", params.get("id2"));
                t2.put("sort", t1.get("SORT"));
                if(addressLibraryManageDao.updateSort(t1)>0&&addressLibraryManageDao.updateSort(t2)>0){
                    pr.setState(true);
                    pr.setMessage("排序字段修改成功");
                    pr.setObj(null);
                } else {
                    pr.setState(false);
                    pr.setMessage("排序字段修改失败");
                }
            }else{
                if(StringUtils.isEmpty(params.get("id"))){
                    pr.setState(false);
                    pr.setMessage("参数缺失，分组ID为空");
                    return pr;
                }
                if(StringUtils.isEmpty(params.get("parent_id"))){
                    pr.setState(false);
                    pr.setMessage("参数缺失，分组的父id为空");
                    return pr;
                }
                if(addressLibraryManageDao.updateSort(params)>0){
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
        }

        return pr;
    }


    /**
     * 单个删除省市县数据
     * @param request
     * @return
     */
    public ProcessResult remove(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);

            params.put("parent_id", params.get("id"));
            //判断是否存在子节点
            List<Map<String, Object>> list = addressLibraryManageDao.queryList(params);
            if(list.size() > 0) {
                pr.setState(false);
                pr.setMessage("存在子节点，不允许删除");
                return pr;
            }
            //逻辑删除省市县数据
            if(addressLibraryManageDao.delete(params) > 0) {
                pr.setState(true);
                pr.setMessage("删除成功");
            } else {
                pr.setState(false);
                pr.setMessage("删除失败");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }


    /**
     * 查询省市县数据列表
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	public ProcessResult queryList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //获取省市县列表
            List<Map<String, Object>> list = addressLibraryManageDao.queryList(params);
            if(StringUtils.isEmpty(params.get("id"))) {
                String last_release_date = addressLibraryManageDao.queryReleaseDate();
                resMap.put("last_release_date", last_release_date);
            }
            if (list != null) {
                pr.setMessage("获取省市县数据列表成功");
                resMap.put("list", list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setObj(resMap);
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 发布数据
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessResult release(HttpServletRequest request)  throws Exception{
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            //查询更新过的区域列表
            List<Map<String, Object>> updateRegionList = this.addressLibraryManageDao.queryUpdateRegionList();
            this.addressLibraryManageDao.batchInsertOrUpdateRegion();
            if (updateRegionList != null && updateRegionList.size() > 0) {
            	//查询已发布的数据信息
            	List<Map<String, Object>> dataList = addressLibraryManageDao.queryRelease();
                File file = new File(PATH);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdir();
                }
                File jsonFile = createJson(dataList);//创建JSON文件
                String jsonUrl = generalExportService.uploadFile(params.get("create_user_name").toString(), jsonFile, 2);//上传文件
                
                File jsFile = createJs(dataList);//创建JS文件
                String jsUrl = generalExportService.uploadFile(params.get("create_user_name").toString(), jsFile, 1);//上传文件

                List<String> list = new ArrayList<String>();
                if (!StringUtils.isEmpty(jsUrl)) {
                	list.add(jsUrl);
                }
                if (!StringUtils.isEmpty(jsonUrl)) {
                	list.add(jsonUrl);
                }
                if (list.size() > 0) {
                    params.put("list", list);
                    if (addressLibraryManageDao.insertReleaseLog(params) <= 0) {
                        throw new RuntimeException("文件导出失败");
                    }
                }
                /**
                 * 同步地址库
                 */
                Map<String, Object> updateRegionMap = new HashMap<String, Object>(16);
                updateRegionMap.put("updateRegionList", updateRegionList);
                //调用erp同步接口
                ProcessResult erpPr = HttpClientUtil.post(erp_service_url + common_sync_region, updateRegionMap);
                if (!erpPr.getState()) {
                    throw new RuntimeException("ERP系统地址库同步失败");
                }
                //调用oa同步接口
                ProcessResult oAPr = HttpClientUtil.postOaSync(oa_service_url + sys_dictionary_sync_region, updateRegionMap);
                if (!oAPr.getState()) {
                    throw new RuntimeException("OA系统地址库同步失败");
                }
            }
            pr.setState(true);
            pr.setMessage("地址库发布成功");
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return pr;
    }

    /**
     * 生成.json格式文件
     */
    public File createJson(List<Map<String, Object>> dataList) {
        // 标记文件生成是否成功
        File createFile = null;
        // 拼接文件完整路径
        String fullPath = PATH + File.separator + FILE_NAME + ".json";

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            createFile = new File(fullPath);
            // 格式化json字符串
            Map<String,Object> resultMap = new HashMap<String, Object>();
            resultMap.put("create_date",DateUtils.format(new Date(), DateUtils.DATE_FORMAT_YYYYMMDDHHMMSS));//发布时间
            resultMap.put("region", dataList);//发布数据
            String jsonString = Jackson.writeObject2Json(resultMap);
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(createFile), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回
        return createFile;
    }
    /**
     * 创建JS文件
     * @return
     */
    @SuppressWarnings("unchecked")
    public File createJs(List<Map<String, Object>> dataList) {
        // 拼接文件完整路径
        File createFile = null;
        String fullPath = PATH + File.separator + FILE_NAME + ".js";
        Map<String, Object> province_list = new TreeMap<String, Object>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 升序排序
                return obj1.compareTo(obj2);
            }
        });
        Map<String, Object> province_map;
        Map<String, Object> city_list;
        Map<String, Object> city_map;
        Map<String, Object> area_list;
        Map<String, Object> area_map;

        try {
            List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> node : dataList) {
                if ("0".equals(node.get("parent_id").toString())) { //找到父级为0， 即省份
                    treeList.add(node);
                    initChild(node, dataList);
                }
            }
            if (treeList != null) {
                for (Map<String, Object> province : treeList) {
                    String province_code = province.get("code").toString();
                    List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
                    if(!StringUtils.isEmpty(province.get("children"))) {
                        cityList = (List<Map<String, Object>>) province.get("children");
                    }
                    province_map = new HashMap<String, Object>();
                    city_list = new TreeMap<String, Object>(new Comparator<String>() {
                        public int compare(String obj1, String obj2) {
                            // 升序排序
                            return obj1.compareTo(obj2);
                        }
                    });
                    for (Map<String, Object> city : cityList) {
                        String city_code = city.get("code").toString();
                        List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
                        if(!StringUtils.isEmpty(city.get("children"))) {
                            areaList = (List<Map<String, Object>>) city.get("children");
                        }
                        city_map = new HashMap<String, Object>();
                        area_list = new TreeMap<String, Object>(new Comparator<String>() {
                            public int compare(String obj1, String obj2) {
                                // 升序排序
                                return obj1.compareTo(obj2);
                            }
                        });
                        for (Map<String, Object> area : areaList) {
                            String area_code = area.get("code").toString();
                            area_map = new HashMap<String, Object>();
                            area_map.put("name", area.get("name").toString());
                            if ("0".equals(area.get("is_display").toString())) {//是否显示（0：不显示，1显示）
                                area_map.put("del", 1);
                            }
                            area_list.put(area_code, area_map);
                        }
                        city_map.put("name", city.get("name").toString());
                        city_map.put("area", area_list.isEmpty()?null:area_list);
                        if ("0".equals(city.get("is_display").toString())) {////是否显示（0：不显示，1显示）
                            city_map.put("del", 1);
                        }
                        city_list.put(city_code, city_map);
                    }
                    province_map.put("name", province.get("name").toString());
                    province_map.put("city", city_list.isEmpty()?null:city_list);
                    if ("0".equals(province.get("is_display").toString())) {
                        province_map.put("del", 1);
                    }
                    province_list.put(province_code, province_map);
                }
            }

            createFile = new File(fullPath);
            // 格式化json字符串
            String jsonString = Jackson.writeObject2Json(province_list);
            jsonString = "const ADDRESS_DATA =" + jsonString;
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(createFile), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }

        return createFile;
    }

    @SuppressWarnings("unchecked")
	public void initChild(Map<String, Object> root, List<Map<String, Object>> list) {
        String rootId = root.get("id").toString();
        for (Map<String, Object> node : list) {
            if (node.get("parent_id").toString().equals(rootId)) {
                initChild(node, list);
                List<Map<String, Object>> tempList = (List<Map<String, Object>>) root.get("children");
                if (tempList == null) {
                    tempList = new ArrayList<Map<String, Object>>();
                    root.put("children", tempList);
                }
                tempList.add(node);
            }
        }
    }
}
    
