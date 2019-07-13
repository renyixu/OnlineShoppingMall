package com.qishiyi.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qishiyi.domain.AdminUser;

public class AdminUserDao {

	public AdminUser adminLogin(String adminUserName, String adminPwd) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select * from adminUser where adminmail=? and adminpwd=?";
		return qr.query(sql, new BeanHandler<AdminUser>(AdminUser.class),adminUserName,adminPwd);
		
	}

}
