package com.qishiyi.domain;

import org.junit.Test;



//已经命名全局变量可否再命名同名的局部变量--->可以。
//因为全局变量的作用域是从命名的那一点开始直到其所在文件结束；
//而局部变量的作用域是从其命名的那一点开始直到其所在的块(一般以{})结束；
public class Test1 {
	public int abc=1;
	@Test
	public void test(){
		int abc=2;
		System.out.println(abc);
	}

}
