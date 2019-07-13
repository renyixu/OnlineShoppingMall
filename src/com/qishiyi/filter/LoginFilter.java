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

import com.qishiyi.domain.User;
import com.qishiyi.service.UserService;

public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//将参数强转，因为只有HttpServletRequest才能获取Cookie对象
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		String username=null;
		String pw=null;
		//获取Cookie对象
		Cookie[] cookies=req.getCookies();
		if(cookies!=null){
		for(Cookie cookie:cookies){
			//auto_username;auto_pw
			if(cookie.getName().equals("auto_username")){
				//从cookie中取出中文时需要进行解码
				username=URLDecoder.decode(cookie.getValue(),"UTF-8");
			}
			if(cookie.getName().equals("auto_pw")){
				pw=cookie.getValue();
			}
		}
		}
		//两者都不为空表示可以自动登录
		if(username!=null&&pw!=null){
			User user=null;
			try {
				user=new UserService().login(username,pw);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user!=null){
			//创建session,表明是登录状态
			HttpSession session=req.getSession();
			session.setAttribute("login",user);
			}
		}
		//放行
		chain.doFilter(request, response);
	}

	public void destroy() {
		
		
	}

}
