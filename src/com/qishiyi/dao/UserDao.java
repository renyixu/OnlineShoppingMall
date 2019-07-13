package com.qishiyi.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.qishiyi.domain.User;

public class UserDao {
	//激活用户
	public int activeAccount(String activeCode) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="update people set sta=1 where code=?";
		return qr.update(sql, activeCode);
	}
	
	//检查用户名是否已存在
	public User checkUserName(String username) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select username from people where username=?";
		User user= qr.query(sql, new BeanHandler<User>(User.class),username);
		return user;
	}
	
	//登录
	public User login(String username, String pw) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="select * from people where username=? and pw=?";
		return qr.query(sql,new BeanHandler<User>(User.class),username,pw);
	}
	
	//注册
	public int register(User user) throws SQLException {
		ComboPooledDataSource ds=new ComboPooledDataSource();
		QueryRunner qr=new QueryRunner(ds,true);
		String sql="insert into people values(?,?,?,?,?,?,?,?,?)";
		Object[] obj={user.getId(),user.getUsername(),user.getPw(),user.getEmail(),user.getName(),user.getGender(),user.getBirthday(),user.getSta(),user.getCode()};
		int row=0;
		row=qr.update(sql,obj);
		return row;
	}
}
