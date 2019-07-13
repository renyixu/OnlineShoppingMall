package com.qishiyi.service;

import java.sql.SQLException;

import com.qishiyi.dao.AdminUserDao;
import com.qishiyi.domain.AdminUser;

public class AdminUserService {

	public AdminUser adminLogin(String adminUserName, String adminPwd) throws SQLException {
		return new AdminUserDao().adminLogin(adminUserName,adminPwd);
		
	}

}
