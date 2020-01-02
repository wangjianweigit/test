package com.tk.oms.notice.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c), 2017, Tongku
 * FileName : ActivityNoticeDao
 * 活动通知Dao接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/4/13 17:50
 */
@Repository
public interface ActivityNoticeDao {


    /**
     * 查询活动通知内容列表
     *
     * @param params 查询参数
     * @return
     */
    public List<Map<String, Object>> queryActivityNoticeContextList(Map params);

    /**
     * 查询活动通知内容记录数
     *
     * @param param 查询条件
     * @return
     */
    public int queryActivityNoticeContextCount(Map<String, Object> param);

    /**
     * 新增活动通知内容
     *
     * @param params 添加内容
     * @return
     */
    public int insertActivityNoticeContext(Map params);

    /**
     * 根据id查询活动通知内容
     *
     * @param id 通知内容id
     * @return
     */
    public Map<String, Object> queryActivityNoticeContextById(long id);

    /**
     * 查询所有活动内容
     * @return
     */
    public List<Map<String, Object>> queryActivityNoticeContextSelect(Map paramMap);

    /**
     * 修改活动通知内容、启停用状态
     *
     * @param params 修改内容
     * @return
     */
    public int updateActivityNoticeContext(Map params);

    /**
     * 删除活动通知内容
     *
     * @param id 活动通知内容id
     * @return
     */
    public int deleteActivityNoticeContext(long id);

    /**
     * 查询活动通知发送信息列表
     *
     * @param params 查询参数
     * @return
     */
    public List<Map<String, Object>> queryActivityNoticeSendInfoList(Map params);

    /**
     * 查询活动通知发送信息记录数
     *
     * @param params 查询条件
     * @return
     */
    public int queryActivityNoticeSendInfoCount(Map params);

    /**
     * 新增活动通知发送信息
     *
     * @param params
     * @return
     */
    public int insertActivityNoticeSendInfo(Map params);

    /**
     * 修改活动通知发送信息
     *
     * @param params
     * @return
     */
    public int updateActivityNoticeSendInfo(Map params);

    /**
     * 根据id查询活动通知信息
     *
     * @param id
     * @return
     */
    public Map<String, Object> queryActivityNoticeSendInfoById(long id);

    /**
     * 根据活动通知id查询通知发送明细
     *
     * @param notice_id
     * @return
     */
    public List<Map<String, Object>> queryActivityNoticeSendDetailByNoticeId(Long notice_id);

    /**
     * 批量新增活动通知发送明细
     *
     * @param list 添加内容
     * @return
     */
    public int insertActivityNoticeSendDetailByBatch(List<Map<String, Object>> list);

    /**
     * (根据查询条件查询发送用户)批量新增活动通知发送明细
     * @param param
     * @return
     */
    public int insertActivityNoticeSendDetailByCheckAll(Map<String, Object> param);

    /**
     * 查询发送活动通知明细记录数
     *
     * @param notice_id
     * @return
     */
    public int queryActivityNoticeSendDetailCountByNoticeId(Long notice_id);

    /**
     * 根据活动通知id删除发送明细
     *
     * @param notice_id
     * @return
     */
    public int deleteActivityNoticeSendDetailByNoticeId(Long notice_id);


    /**
     * 根据活动通知id删除通知信息
     *
     * @param notice_id
     * @return
     */
    public int deleteActivityNoticeInfoByNoticeId(Long notice_id);
    /**
     * 根据活动通知信息id查询活动通知发送详细
     *
     * @param id 活动通知信息id
     * @return
     */
    public List<Map<String, Object>> queryActivityNoticeDetailById(long id);

    /**
     * 根据Id取消活动通知发送
     *
     * @param ids 取消通知发送id
     * @return
     */
    public int updateActivityNoticeInfoByCancelSend(Map ids);

    /**
     * 分页查询会员列表记录数
     * @param param
     * @return
     */
    public int queryMemberListCount(Map param);

    /**
     * 分页查询会员列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> queryMemberList(Map param);


}
