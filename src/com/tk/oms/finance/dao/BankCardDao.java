package com.tk.oms.finance.dao;


import com.tk.sys.common.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 *
 * Copyright (c), 2017, Tongku
 * FileName : BankCardDao
 * 银行卡数据访问接口
 *
 * @author wangjianwei
 * @version 1.00
 * @date 2017/6/13 15:57
 */
@Repository
public interface BankCardDao extends BaseDao<Map<String, Object>> {

    /**
     * 查询已绑定银行卡记录数
     * @param params
     * @return
     */
    public int queryBankCardCount(Map<String, Object> params);

    /**
     * 查询已绑定银行卡列表
     * @param params
     * @return 返回银行卡列表
     */
    public List<Map<String,Object>> queryBankCardList(Map<String, Object> params);


    /**
     * 查询服务公司银行卡列表
     * @return 返回银行卡列表
     */
    public List<Map<String,Object>> queryBankCardListForServerCompany();

    /**
     * 查询仓储公司银行卡列表
     * @return 返回银行卡列表
     */
    public List<Map<String,Object>> queryBankCardListForStorageCompany();


    /**
     * 通过银行卡号获取银行卡信息
     * @param params
     * @return
     */
    public Map<String,Object> queryBankCardByName(Map<String, Object> params);

    /**
     * 获取银行信息（短信）
     * @param param
     * @return 返回银行信息
     */
    public Map<String,Object> queryBankForNote(String param);

    /**
     * 获取银行列表信息
     * @return
     */
    public List<Map<String,Object>> queryBankList();

    /**
     * 获取银行信息
     * @param param
     * @return 返回银行信息
     */
    public Map<String,Object> queryBankByCode(String param);

    /**
     * 设置默认银行卡
     * @param params
     * @return 返回成功记录条数
     */
    public int updateDefaultFlag(Map<String, Object> params);

    /**
     * 修改绑定状态
     * @param params
     * @return 返回成功记录条数
     */
    public int updateBindState(Map<String, Object> params);

    /**
     * 获取默认的银行卡信息
     * @param param
     * @return
     */
    public Map<String,Object> queryDefaultForBankCard(Map params);

    /**
     * 查询所有的省
     * @return
     */
    public List<Map<String,Object>> queryPayProvince();

    /**
     * 通过城市代码查询相对应的市
     * @return
     */
    public List<Map<String,Object>> queryPayCity(String param);

    /**
     * 通过城市代码查询相对应的市或县
     * @return
     */
    public List<Map<String,Object>> queryPayArea(String param);

    /**
     * 获取银行列表（精确每个位置）
     * @param param
     * @return
     */
    public List<Map<String,Object>> queryCnapsBankList(Map<String, Object> param);

    /**
     * 通过参数key获取服务、仓储公司参数信息
     * @param param
     * @return
     */
    Map<String,Object> queryCompanyParamByKey(Map<String, Object> param);

    /**
     * 获取开户银行
     *
     * @param bankClscode
     * @return
     */
    Map<String,Object> queryBankByClscode(@Param("bank_clscode") String bankClscode);

    /**
     * 修改银行卡綁卡验证状态
     * @param paramMap
     * @return
     */
    int updateBankCardByPhoneCheckState(Map<String, Object> paramMap);

}
