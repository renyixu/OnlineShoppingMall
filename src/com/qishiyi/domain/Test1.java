package com.qishiyi.domain;

import org.junit.Test;



//�Ѿ�����ȫ�ֱ����ɷ�������ͬ���ľֲ�����--->���ԡ�
//��Ϊȫ�ֱ������������Ǵ���������һ�㿪ʼֱ���������ļ�������
//���ֲ��������������Ǵ�����������һ�㿪ʼֱ�������ڵĿ�(һ����{})������
public class Test1 {
	public int abc=1;
	@Test
	public void test(){
		int abc=2;
		System.out.println(abc);
	}

}
