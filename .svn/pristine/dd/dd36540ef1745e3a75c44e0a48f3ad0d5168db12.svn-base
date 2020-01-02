package com.tk.oms.basicinfo.service;

import com.tk.oms.basicinfo.dao.ColorsDao;
import com.tk.sys.util.GridResult;
import com.tk.sys.util.HttpUtil;
import com.tk.sys.util.ProcessResult;
import com.tk.sys.util.Transform;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品颜色管理
 * @author zhenghui
 */
@Service("ColorsService")
public class ColorsService {

    @Resource
    private ColorsDao colorsDao;

    /**
     * 获取色系列表信息
     * @param request
     * @return
     */
    public ProcessResult queryColorsGroupList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取色系列表
            List<Map<String, Object>> list = this.colorsDao.queryColorsGroupList();
            if (list != null) {
                pr.setMessage("获取色系列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 获取颜色列表信息
     * @param request
     * @return
     */
    public ProcessResult queryColorsList(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            String json = HttpUtil.getRequestInputStream(request);
            if (StringUtils.isEmpty(json)) {
                pr.setState(false);
                pr.setMessage("缺少参数");
                return pr;
            }
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("parent_id"))) {
                pr.setState(false);
                pr.setMessage("缺少parent_id参数");
                return pr;
            }
            long parent_id = Long.parseLong(params.get("parent_id").toString());
            //获取颜色分组
            List<Map<String, Object>> list = this.colorsDao.queryColorsList(parent_id);
            if (list != null) {
                pr.setMessage("获取颜色列表成功");
                pr.setObj(list);
            } else {
                pr.setMessage("无数据");
            }
            pr.setState(true);
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加色系
     * @param request
     * @return
     */
    public ProcessResult addColorsGroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("color_name"))) {
                pr.setState(false);
                pr.setMessage("缺少color_name参数");
                return pr;
            }
            //判断添加的色系名称是否重复
            if (this.colorsDao.queryColorsGroupCountByName(params) > 0) {
                pr.setState(false);
                pr.setMessage("色系名称已存在");
                return pr;
            }
            if (this.colorsDao.insert(params) > 0) {
                pr.setState(true);
                pr.setMessage("色系添加成功");
                pr.setObj(params);
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 添加颜色
     * @param request
     * @return
     */
    public ProcessResult addColors(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("parent_id"))) {
                pr.setState(false);
                pr.setMessage("缺少parent_id参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("color_name"))) {
                pr.setState(false);
                pr.setMessage("缺少color_name参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("color_number"))) {
                pr.setState(false);
                pr.setMessage("缺少color_number参数");
                return pr;
            }
            if (this.colorsDao.queryColorCountByNumber(params) > 0) {
                pr.setState(false);
                pr.setMessage("颜色编码已存在");
                return pr;
            }
            if (this.colorsDao.queryColorsCountByName(params) > 0) {
                pr.setState(false);
                pr.setMessage("颜色名称已存在");
                return pr;
            }
            if (!StringUtils.isEmpty(params.get("color_code"))) {
                if (this.colorsDao.queryColorsCountByCode(params) > 0) {
                    pr.setState(false);
                    pr.setMessage("颜色代码已存在");
                    return pr;
                }
            }
            if (this.colorsDao.insert(params) > 0) {
                pr.setState(true);
                pr.setMessage("颜色添加成功");
                pr.setObj(params);
            }

        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 编辑色系
     * @param request
     * @return
     */
    public ProcessResult editColorsGroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("color_name"))) {
                pr.setState(false);
                pr.setMessage("缺少color_name参数");
                return pr;
            }
            if (this.colorsDao.isExist(params) == 0) {
                pr.setState(false);
                pr.setMessage("记录不存在");
                return pr;
            }
            if (this.colorsDao.queryColorsGroupCountByName(params) > 0) {
                pr.setState(false);
                pr.setMessage("色系名称已存在");
                return pr;
            }
            if (this.colorsDao.update(params) > 0) {
                pr.setState(true);
                pr.setMessage("色系修改成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 更新商品颜色
     * @param request
     * @return
     */
    public ProcessResult editColors(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if (StringUtils.isEmpty(params.get("color_name"))) {
                pr.setState(false);
                pr.setMessage("缺少color_name参数");
                return pr;
            }
            if (this.colorsDao.isExist(params) == 0) {
                pr.setState(false);
                pr.setMessage("记录不存在");
                return pr;
            }
            //判断颜色是否有商品使用
//            if (this.colorsDao.querySkuColorCountByName(params) > 0) {
//                pr.setState(false);
//                pr.setMessage("颜色已被占用");
//                return pr;
//            }
            if (this.colorsDao.queryColorsCountByName(params) > 0) {
                pr.setState(false);
                pr.setMessage("颜色名称已存在");
                return pr;
            }
            if (!StringUtils.isEmpty(params.get("color_code"))) {
                if (this.colorsDao.queryColorsCountByCode(params) > 0) {
                    pr.setState(false);
                    pr.setMessage("颜色代码已存在");
                    return pr;
                }
            }

            if (this.colorsDao.queryColorCountByNumber(params) > 0) {
                pr.setState(false);
                pr.setMessage("颜色编码已存在");
                return pr;
            }
            if (this.colorsDao.update(params) > 0) {
                pr.setState(true);
                pr.setMessage("颜色修改成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除商品颜色
     * @param request
     * @return
     */
    public ProcessResult removeColors(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if (this.colorsDao.isExist(params) == 0) {
                pr.setState(false);
                pr.setMessage("记录不存在");
                return pr;
            }
            if (this.colorsDao.querySkuColorCountByName(params) > 0) {
                pr.setState(false);
                pr.setMessage("颜色已被占用");
                return pr;
            }

            if (this.colorsDao.delete(params) > 0) {
                pr.setState(true);
                pr.setMessage("商品颜色删除成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
        }
        return pr;
    }

    /**
     * 删除色系
     * @param request
     * @return
     */
    @Transactional
    public ProcessResult removeColorsGroup(HttpServletRequest request) {
        ProcessResult pr = new ProcessResult();
        try {
            //获取参数
            String json = HttpUtil.getRequestInputStream(request);
            Map<String, Object> params = (Map<String, Object>) Transform.GetPacket(json, Map.class);
            if (StringUtils.isEmpty(params.get("id"))) {
                pr.setState(false);
                pr.setMessage("缺少id参数");
                return pr;
            }
            if (this.colorsDao.isExist(params) == 0) {
                pr.setState(false);
                pr.setMessage("记录不存在");
                return pr;
            }
            if (this.colorsDao.querySkuColorCountByNames(params) > 0) {
                pr.setState(false);
                pr.setMessage("色系已有颜色被占用");
                return pr;
            }
            if (this.colorsDao.delete(params) > 0) {
                this.colorsDao.deleteByParentId(params);
                pr.setState(true);
                pr.setMessage("色系删除成功");
            }
        } catch (Exception e) {
            pr.setState(false);
            pr.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return pr;
    }
}
