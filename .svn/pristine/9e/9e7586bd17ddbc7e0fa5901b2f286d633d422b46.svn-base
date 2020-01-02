package com.tk.oms.demo.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tk.oms.demo.dao.TestUserDao;
import com.tk.sys.util.ProcessResult;

@Service("TestUserService")
public class TestUserService {

	@Resource
	private TestUserDao testUserDao;

	/**
	 * 测试
	 */
	public ProcessResult queryList(HttpServletRequest request) {
		ProcessResult pr = new ProcessResult();
		try {
			List<Map<String, Object>> list = testUserDao.queryTestUserList();
			if (list != null && list.size() > 0) {
				pr.setState(true);
				pr.setMessage("获取数据成功");
				pr.setObj(list);
			} else {
				pr.setState(false);
				pr.setMessage("无数据");
			}
		} catch (Exception e) {
			pr.setState(false);
			pr.setMessage(e.getMessage());
		}
		return pr;
	}

}