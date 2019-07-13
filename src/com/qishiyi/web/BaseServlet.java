package com.qishiyi.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
		//使用反射来通过获取methodName从而调用相应的方法
		//获取methodName
		String methodName=request.getParameter("methodName");
		Class clazz=this.getClass();
		//Method method=clazz.getMethod(methodName, parameterTypes);
		
		
	}
}