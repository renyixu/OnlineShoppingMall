package com.qishiyi.utils;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	private static String maxIdle;//最大闲置数
	private static String minIdle;//最小闲置数
	private static String maxTotal;//总的连接数
	private static String ip;//连接的主机地址
	private static String port;//端口
	public static JedisPool jedisPool=null;
	//加载配置文件
	static{
		ResourceBundle bundle=ResourceBundle.getBundle("jedis_en");
		maxIdle=bundle.getString("maxIdle");
		minIdle=bundle.getString("minIdle");
		maxTotal=bundle.getString("maxTotal");
		ip=bundle.getString("ip");
		port=bundle.getString("port");
		JedisPoolConfig config=new JedisPoolConfig();
		config.setMaxIdle(Integer.parseInt(maxIdle));
		config.setMinIdle(Integer.parseInt(minIdle));
		config.setMaxTotal(Integer.parseInt(maxTotal));
		jedisPool=new JedisPool(config,ip,6379);//创建Jedis连接池
	}
	public static Jedis getJedis(){
		return jedisPool.getResource();
	}
	public static void test(){
		System.out.println("this is test");
	}

}
