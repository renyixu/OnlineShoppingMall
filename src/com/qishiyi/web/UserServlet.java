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
		//��ȡmethodName
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

	// ע��ʱ����û����Ƿ����
	public void checkUserName(HttpServletRequest request,
			HttpServletResponse response,UserService userService) throws IOException {
		// ��ȡ����
		String userName = request.getParameter("username");
		boolean flag = false;
		try {
			flag = userService.checkUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resultData = "{\"isExist\":" + flag + "}";
		response.getWriter().write(resultData);// ��ҳ��д����
	}

	// ��¼
	public void login(HttpServletRequest request, HttpServletResponse response,UserService userService)
			throws IOException, ServletException {
		// ��ȡҳ������
		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		String autologin = request.getParameter("autologin");// �Ƿ��Զ���¼
		User user = null;
		try {
			user = userService.login(username, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		if (user != null) {
			// ���û������붼��ȷ�����Զ���¼
			if (autologin != null) {
				String name_code = URLEncoder.encode(username, "UTF-8");// �����Ľ��б��룬cookie������������Ҫ����
				Cookie username_cookie = new Cookie("auto_username", name_code);
				Cookie pw_cookie = new Cookie("auto_pw", user.getPw());
				// ����cookie�־û�ʱ��
				username_cookie.setMaxAge(120);// ��λ����
				pw_cookie.setMaxAge(120);
				// ����Cookie
				response.addCookie(username_cookie);
				response.addCookie(pw_cookie);
			}
			session.setAttribute("login", user);
			response.sendRedirect(request.getContextPath() + "/default.jsp");// �ض���
		} else {
			request.setAttribute("loginfail", "�û������������");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);// ת��
		}
	}
	
	//ע��
	public void register(HttpServletRequest request, HttpServletResponse response,UserService userService) throws IOException{
		Map<String,String[]> map=request.getParameterMap();
		User user=new User();
		//����ȡ��������ӳ�䵽User����
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
		user.setSta(0);//��ʼ״̬��Ϊ0
		user.setCode(CommonUtils.setId());//״̬����
		boolean flag=false;
		try {
			flag=userService.register(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==true){
			//ע��ɹ�
			//��ת��ע��ɹ�ҳ�棬���伤��
			ResourceBundle resourceBundle =ResourceBundle.getBundle("ip");//��ȡ�����ļ�
			String ip=resourceBundle.getString("ip");
			String emailMsg="��ϲ����ע��ɹ���������Ӽ����˻�<a href='http://"+ip+request.getContextPath()+"/user?methodName=activeAccount&activeCode="+user.getCode()+"'>"
					+ "http://"+ip+request.getContextPath()+"/user?methodName=activeAccount&activeCode="+user.getCode()+""
					+ "</a>";
			try {
				//���ͼ����ʼ�
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��ת��ע��ɹ�ҳ��
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}
		else{
			//ע��ʧ��
			response.sendRedirect(request.getContextPath()+"/register.jsp");
		}
	}
	
	//�����û�
	public void activeAccount(HttpServletRequest request, HttpServletResponse response,UserService userService) throws IOException{
		String activeCode=request.getParameter("activeCode");//��ȡ״̬���־
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
	
	//�˳���¼
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//��session�е�userɾ��
		HttpSession session=request.getSession();
		session.removeAttribute("login");
		//�����¼ʱѡ���Զ���¼����ô��Ҫ��cookie�е��û�&����ɾ��
		Cookie user_cookie=new Cookie("auto_username","");
		Cookie pw_cookie=new Cookie("auto_pw","");
		user_cookie.setMaxAge(0);//����cookie�ڿͻ��˳־õ�ʱ��
		pw_cookie.setMaxAge(0);
		response.addCookie(user_cookie);
		response.addCookie(pw_cookie);
		//�ض��򵽵�¼ҳ��
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}