package com.qishiyi.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.qishiyi.domain.User;
import com.qishiyi.service.UserService;
import com.qishiyi.utils.CommonUtils;
import com.qishiyi.utils.MailUtils;
@SuppressWarnings("all")
public class UserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		UserService userService=new UserService();
		//获取methodName
		String methodName=request.getParameter("methodName");
		switch(methodName){
		case "login":
			login(request,response,userService);
			break;
		case "checkUserName":
			checkUserName(request,response,userService);
			break;
		case "register":
			register(request,response,userService);
			break;
		case "activeAccount":
			activeAccount(request, response, userService);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			break;
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// 注册时检查用户名是否存在
	public void checkUserName(HttpServletRequest request,
			HttpServletResponse response,UserService userService) throws IOException {
		// 获取参数
		String userName = request.getParameter("username");
		boolean flag = false;
		try {
			flag = userService.checkUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resultData = "{\"isExist\":" + flag + "}";
		response.getWriter().write(resultData);// 往页面写数据
	}

	// 登录
	public void login(HttpServletRequest request, HttpServletResponse response,UserService userService)
			throws IOException, ServletException {
		// 获取页面数据
		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		String autologin = request.getParameter("autologin");// 是否自动登录
		User user = null;
		try {
			user = userService.login(username, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		if (user != null) {
			// 当用户名密码都正确并且自动登录
			if (autologin != null) {
				String name_code = URLEncoder.encode(username, "UTF-8");// 对中文进行编码，cookie保存中文是需要编码
				Cookie username_cookie = new Cookie("auto_username", name_code);
				Cookie pw_cookie = new Cookie("auto_pw", user.getPw());
				// 设置cookie持久化时间
				username_cookie.setMaxAge(120);// 单位是秒
				pw_cookie.setMaxAge(120);
				// 发送Cookie
				response.addCookie(username_cookie);
				response.addCookie(pw_cookie);
			}
			session.setAttribute("login", user);
			response.sendRedirect(request.getContextPath() + "/default.jsp");// 重定向
		} else {
			request.setAttribute("loginfail", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);// 转发
		}
	}
	
	//注册
	public void register(HttpServletRequest request, HttpServletResponse response,UserService userService) throws IOException{
		Map<String,String[]> map=request.getParameterMap();
		User user=new User();
		//将获取到的数据映射到User对象
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setId(CommonUtils.setId());
		user.setSta(0);//初始状态码为0
		user.setCode(CommonUtils.setId());//状态代码
		boolean flag=false;
		try {
			flag=userService.register(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			//注册成功
			//跳转到注册成功页面，邮箱激活
			ResourceBundle resourceBundle =ResourceBundle.getBundle("ip");//获取配置文件
			String ip=resourceBundle.getString("ip");
			String emailMsg="恭喜您，注册成功，点击链接激活账户<a href='http://"+ip+request.getContextPath()+"/user?methodName=activeAccount&activeCode="+user.getCode()+"'>"
					+ "http://"+ip+request.getContextPath()+"/user?methodName=activeAccount&activeCode="+user.getCode()+""
					+ "</a>";
			try {
				//发送激活邮件
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//跳转到注册成功页面
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}
		else{
			//注册失败
			response.sendRedirect(request.getContextPath()+"/register.jsp");
		}
	}
	
	//激活用户
	public void activeAccount(HttpServletRequest request, HttpServletResponse response,UserService userService) throws IOException{
		String activeCode=request.getParameter("activeCode");//获取状态码标志
		boolean flag=false;
		try {
			flag=userService.activeAccount(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
	}
	
	//退出登录
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//将session中的user删除
		HttpSession session=request.getSession();
		session.removeAttribute("login");
		//如果登录时选了自动登录，那么还要将cookie中的用户&密码删除
		Cookie user_cookie=new Cookie("auto_username","");
		Cookie pw_cookie=new Cookie("auto_pw","");
		user_cookie.setMaxAge(0);//设置cookie在客户端持久的时间
		pw_cookie.setMaxAge(0);
		response.addCookie(user_cookie);
		response.addCookie(pw_cookie);
		//重定向到登录页面
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}