package com.tk.oms.test;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tk.oms.demo.dao.TestUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/resources/applicationContext.xml" })
public class Junit {

	@Resource
	private TestUserDao testUserDao;

	@Test
	public void test() {
		List<Map<String, Object>> list = testUserDao.queryTestUserList();
		for (Map<String, Object> map : list) {
			System.out.println(String.valueOf(map.get("USER_NAME")));
		}
	}

}