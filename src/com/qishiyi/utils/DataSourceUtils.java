package com.qishiyi.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
@SuppressWarnings("all")
public class DataSourceUtils {
	private static ComboPooledDataSource dataSource=new ComboPooledDataSource();
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
	
	//���connection����
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	//��õ�ǰ�̵߳�Connection����
	public static Connection getCurrentConnection() throws SQLException{
		Connection connection=threadLocal.get();
		if(connection==null){
			connection=getConnection();
			threadLocal.set(connection);
		}
		return connection;
	}
	
	//��������
	public static void startTransaction() throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.setAutoCommit(false);
		}
	}
	
	//�ع�
	public static void rollBack() throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.rollback();
		}
	}
	
	//�ύ�����ͷ���Դ����connection�����ThreadLocal���Ƴ�
	public static void commitAndRelease() throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.commit();
			connection.close();
			threadLocal.remove();
		}
	}
	
	//�ر���Դ
	public static void closeConnection()throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.close();
		}
	}

}
