package com.qishiyi.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
@SuppressWarnings("all")
public class DataSourceUtils {
	private static ComboPooledDataSource dataSource=new ComboPooledDataSource();
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
	
	//获得connection对象
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	//获得当前线程的Connection对象
	public static Connection getCurrentConnection() throws SQLException{
		Connection connection=threadLocal.get();
		if(connection==null){
			connection=getConnection();
			threadLocal.set(connection);
		}
		return connection;
	}
	
	//开启事务
	public static void startTransaction() throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.setAutoCommit(false);
		}
	}
	
	//回滚
	public static void rollBack() throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.rollback();
		}
	}
	
	//提交事务、释放资源、将connection对象从ThreadLocal中移除
	public static void commitAndRelease() throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.commit();
			connection.close();
			threadLocal.remove();
		}
	}
	
	//关闭资源
	public static void closeConnection()throws SQLException{
		Connection connection=getCurrentConnection();
		if(connection!=null){
			connection.close();
		}
	}

}
