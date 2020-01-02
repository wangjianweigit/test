package com.tk.oms.analysis.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 频道热度分析
 * @author zhenghui
 */
@Repository
public interface ChannelHeatDao {

    /**
     * 通过页面类型统计用户日志数量
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryLogsNumberByPageType(Map<String,Object> params);
}
