package com.qishiyi.service;

import java.sql.SQLException;

import com.qishiyi.dao.UserDao;
import com.qishiyi.domain.User;

public class UserService {
	private UserDao userDao=null;
	public UserService(){
		userDao=new UserDao();
	}
	// 激活用户
	public boolean activeAccount(String activeCode) throws SQLException {
		int isActive = userDao.activeAccount(activeCode);
		return isActive > 0 ? true : false;
	}

	// 注册用户时检查用户名是否已被注册
	public boolean checkUserName(String username) throws SQLException {
		User user = userDao.checkUserName(username);
		return user != null ? false : true;// isExist>0表示此用户名已经存在，返回false表示不能通过校验
	}
	// 登录
	public User login(String username, String pw) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.login(username, pw);
	}
	
	//注册
	public boolean register(User user) throws SQLException {
		int row=userDao.register(user);
		return row>0?true:false;//若row>0表示插入数据成功，否则插入数据失败
	}
}
