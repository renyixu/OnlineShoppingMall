package com.qishiyi.service;

import java.sql.SQLException;

import com.qishiyi.dao.UserDao;
import com.qishiyi.domain.User;

public class UserService {
	private UserDao userDao=null;
	public UserService(){
		userDao=new UserDao();
	}
	// �����û�
	public boolean activeAccount(String activeCode) throws SQLException {
		int isActive = userDao.activeAccount(activeCode);
		return isActive > 0 ? true : false;
	}

	// ע���û�ʱ����û����Ƿ��ѱ�ע��
	public boolean checkUserName(String username) throws SQLException {
		User user = userDao.checkUserName(username);
		return user != null ? false : true;// isExist>0��ʾ���û����Ѿ����ڣ�����false��ʾ����ͨ��У��
	}
	// ��¼
	public User login(String username, String pw) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.login(username, pw);
	}
	
	//ע��
	public boolean register(User user) throws SQLException {
		int row=userDao.register(user);
		return row>0?true:false;//��row>0��ʾ�������ݳɹ��������������ʧ��
	}
}
