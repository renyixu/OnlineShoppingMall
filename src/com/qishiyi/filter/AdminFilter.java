package com.qishiyi.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.qishiyi.domain.AdminUser;
import com.qishiyi.web.AdminUserServlet;

public class AdminFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		Cookie[] cookies=req.getCookies();
		String username=null;
		String pwd=null;
		if(cookies!=null){
		for(Cookie cookie:cookies){
			if("admin_username".equals(cookie.getName())){
				username=URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
			if("admin_pwd".equals(cookie.getName())){
				pwd=URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
		}
		}
		//登录后台系统
		if(username!=null&&pwd!=null){
			AdminUser adminUser=null;
			try {
				adminUser=new AdminUserServlet().adminLogin(username,pwd);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(adminUser!=null){
				//创建session
				HttpSession session=req.getSession();
				session.setAttribute("adminUser",adminUser);
				//放行
				chain.doFilter(request, response);
			}
		}
		else{
			//重定向到后台登录页面
			res.sendRedirect(req.getContextPath()+"/adminlogin.jsp");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
