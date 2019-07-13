package com.qishiyi.utils;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	private static String maxIdle;//���������
	private static String minIdle;//��С������
	private static String maxTotal;//�ܵ�������
	private static String ip;//���ӵ�������ַ
	private static String port;//�˿�
	public static JedisPool jedisPool=null;
	//���������ļ�
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
		jedisPool=new JedisPool(config,ip,6379);//����Jedis���ӳ�
	}
	public static Jedis getJedis(){
		return jedisPool.getResource();
	}
	public static void test(){
		System.out.println("this is test");
	}

}
