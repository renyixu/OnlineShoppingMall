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
		//ʹ�÷�����ͨ����ȡmethodName�Ӷ�������Ӧ�ķ���
		//��ȡmethodName
		String methodName=request.getParameter("methodName");
		Class clazz=this.getClass();
		//Method method=clazz.getMethod(methodName, parameterTypes);
		
		
	}
}