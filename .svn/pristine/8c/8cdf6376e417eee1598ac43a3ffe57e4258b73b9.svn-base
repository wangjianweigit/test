package com.tk.oms.oauth2.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.tk.oms.oauth2.entity.UserAuthor;
import com.tk.oms.sysuser.entity.SysUserInfo;

@Repository
public interface UserAuthorDao {
	/**
	 * 创建ERP用户与OA用户的关联关系
	 */
	int insertForOA(UserAuthor userAuthor);
	/**
	 * 根据OA系统用户的openId查询，ERP中的用户是否已经与OA用户关联
	 */
	int getByOAOpenId(@Param("oa_open_id")String oa_open_id);
	/**
	 * 根据OA系统用户的openId查询，ERP中的用户是否已经与OA用户关联
	 */
	int countByOAOpenId(@Param("oa_open_id")String oa_open_id, @Param("user_id") Long userId);
	/**
     * 通过OA的openID查询关联的用户信息
     * @param userinfo
     * @return
     */
    public SysUserInfo querySysUserInfoByOAOpenId(@Param("open_id")String open_id);
}
