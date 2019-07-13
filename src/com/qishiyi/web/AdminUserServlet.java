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
		// 获取methodName
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

	// 登录后台系统
	public void adminLogin(HttpServletRequest request,
			HttpServletResponse response, AdminUserService adminUserService) throws IOException {
		
		
		// 获取登录参数
		String adminUserName = request.getParameter("adminusername");
		String adminPwd = request.getParameter("adminpwd");
		AdminUser adminUser = null;
		try {
			adminUser = adminUserService.adminLogin(adminUserName, adminPwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 登录后台成功
		if (adminUser != null) {
			// 创建cookie
			Cookie name_cookie = new Cookie("admin_username", adminUserName);
			Cookie pwd_cookie = new Cookie("admin_pwd", adminPwd);
			name_cookie.setMaxAge(24 * 60 * 60); // 单位是秒 设置一天
			pwd_cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(name_cookie);
			response.addCookie(pwd_cookie);
			//重定向到后台页面
			response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
		}
		// 登录后台失败
		else{
			HttpSession session=request.getSession();
			session.setAttribute("adminLoginFail","用户名或密码错误");
			response.sendRedirect(request.getContextPath()+"/adminlogin.jsp");
		}
	}

	public AdminUser adminLogin(String username, String pwd)
			throws SQLException {
		System.out.println("khgvkuyv u");
		return new AdminUserService().adminLogin(username, pwd);
	}
}