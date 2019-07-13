package com.qishiyi.web;

import java.io.IOException;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qishiyi.domain.AdminUser;
import com.qishiyi.service.AdminUserService;

public class AdminUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		AdminUserService adminUserService = new AdminUserService();
		// ��ȡmethodName
		String methodName = request.getParameter("methodName");
		switch (methodName) {
		case "adminLogin":
			adminLogin(request, response, adminUserService);
			break;
		default:
			break;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// ��¼��̨ϵͳ
	public void adminLogin(HttpServletRequest request,
			HttpServletResponse response, AdminUserService adminUserService) throws IOException {
		
		
		// ��ȡ��¼����
		String adminUserName = request.getParameter("adminusername");
		String adminPwd = request.getParameter("adminpwd");
		AdminUser adminUser = null;
		try {
			adminUser = adminUserService.adminLogin(adminUserName, adminPwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��¼��̨�ɹ�
		if (adminUser != null) {
			// ����cookie
			Cookie name_cookie = new Cookie("admin_username", adminUserName);
			Cookie pwd_cookie = new Cookie("admin_pwd", adminPwd);
			name_cookie.setMaxAge(24 * 60 * 60); // ��λ���� ����һ��
			pwd_cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(name_cookie);
			response.addCookie(pwd_cookie);
			//�ض��򵽺�̨ҳ��
			response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
		}
		// ��¼��̨ʧ��
		else{
			HttpSession session=request.getSession();
			session.setAttribute("adminLoginFail","�û������������");
			response.sendRedirect(request.getContextPath()+"/adminlogin.jsp");
		}
	}

	public AdminUser adminLogin(String username, String pwd)
			throws SQLException {
		System.out.println("khgvkuyv u");
		return new AdminUserService().adminLogin(username, pwd);
	}
}