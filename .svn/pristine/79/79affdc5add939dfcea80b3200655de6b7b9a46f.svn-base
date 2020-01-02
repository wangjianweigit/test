package com.tk.oms.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface VerifyCodeDao {
	/**
     * 查询验证码信息
     *
     * @return
     */
    public List<Map<String,Object>> queryAllVerifyCode();


    /**
     * 更新验证码
     *
     * @param verifyCode
     * @return
     */
    public int updateVerifyCode(Map<String,Object> paramMap);
    
    /**
     * 获取查询总数
     * @param request
     * @return
     */
	public int queryVerifyCodeCount();
}
