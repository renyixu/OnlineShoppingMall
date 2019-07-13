package com.qishiyi.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.qishiyi.domain.Category;
import com.qishiyi.domain.Order;
import com.qishiyi.domain.Product;
import com.qishiyi.service.AdminProductService;
import com.qishiyi.vo.Condition;

public class AdminProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		AdminProductService adminProductService=new AdminProductService();
		//��ȡmethodName����
		String methodName=request.getParameter("methodName");
		switch(methodName){
		case "adminCategoryList":
			adminCategoryList(request, response, adminProductService);
			break;
		case "adminShowProductList":
			adminShowProductList(request, response, adminProductService);
			break;
		case "adminUpdatePro":
			adminUpdatePro(request, response, adminProductService);
			break;
		case "adminDelPro":
			adminDelPro(request, response, adminProductService);
			break;
		case "adminFindProInfoWhenUpdatePro":
			adminFindProInfoWhenUpdatePro(request, response, adminProductService);
			break;
		case "adminSearchPro":
			adminSearchProduct(request, response, adminProductService);
			break;
		case "findAllCategory":
			findAllCategory(request, response, adminProductService);
			break;
		case "adminFindAllOrder":
			adminFindAllOrder(request, response, adminProductService);
			break;
		case "findOrderInfoByOid":
			findOrderInfoByOid(request, response, adminProductService);
			break;
		default:
			break;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	//��ȡ��̨���з���
	public void adminCategoryList(HttpServletRequest request, HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		List<Category> list=null;
		try {
			list=adminProductService.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("categoryList", list);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}
	
	//��̨ɾ����Ʒ
	public void adminDelPro(HttpServletRequest request, HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		String pid=request.getParameter("pid");
		try {
			adminProductService.delPro(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/adminProduct?methodName=adminShowProductList").forward(request,response);
	}
	
	
	
	//��̨��ʾ��Ʒ�б�
	public void adminShowProductList(HttpServletRequest request, HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		List<Product> listPro=null;
		List<Category> listCate=null;
		try {
			listPro = adminProductService.findAllPro();
			listCate=adminProductService.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("adminAllPro",listPro);
		request.setAttribute("searchCateList",listCate);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request,response);
	}
	
	//��̨������Ʒ
	public void adminSearchProduct(HttpServletRequest request, HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		Map<String,String[]> map=request.getParameterMap();
		Condition con=new Condition();
		try {
			BeanUtils.populate(con,map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		List<Product> listPro=null;
		List<Category> listCate=null;
		try {
			listPro=adminProductService.searchPro(con);
			listCate=adminProductService.findAllCategory();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		request.setAttribute("searchcondition",con);
		request.setAttribute("listSearchPro",listPro);
		request.setAttribute("searchCateList", listCate);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	
	//��̨�޸���Ʒ
	public void adminUpdatePro(HttpServletRequest request, HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		Map<String,String[]> map=request.getParameterMap();
		Product pro=new Product();
		try {
			BeanUtils.populate(pro,map);//����ȡ�������ݷ�װ��Product������
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pro.setPimage("products/1/c_0001.jpg");
		SimpleDateFormat format=new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		String pdate=format.format(new Date());
		pro.setPdate(pdate);
		pro.setPflag(0);
		try {
			adminProductService.updatePro(pro);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/adminProduct?methodName=adminShowProductList").forward(request, response);
	}
	
	//��̨�޸���Ʒʱ�������еķ���
	public void adminFindProInfoWhenUpdatePro(HttpServletRequest request, HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		String pid=request.getParameter("pid");
		Product pro=null;
		List<Category> list=null;
		try {
			list=adminProductService.findAllCategory();
			pro=adminProductService.updateProUi(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("updateuicate",list);
		request.setAttribute("updateui",pro);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request,response);
	}
	
	public void findAllCategory(HttpServletRequest request,HttpServletResponse response,AdminProductService adminProductService) throws IOException{
		List<Category> categoryList=null;
		try {
			categoryList=adminProductService.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String str=gson.toJson(categoryList);
		response.getWriter().write(str);
	}
	
	//�������еĶ���
	public void adminFindAllOrder(HttpServletRequest request,HttpServletResponse response,AdminProductService adminProductService) throws ServletException, IOException{
		List<Order> orderList=null;
		try {
			orderList=adminProductService.findAllOrder();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("orderList",orderList);
		//ת��
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request,response);
	}
	
	//����orderId��ѯ������ϸ��Ϣ
	public void findOrderInfoByOid(HttpServletRequest request,HttpServletResponse response,AdminProductService adminProductService) throws IOException{
		try {
			Thread.sleep(2000); //���̳߳�˯2�룬��ʾ����Ч��
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		//��ȡ����-->orderId
		String orderId=request.getParameter("orderId");
		System.out.println("-->"+orderId);
		//���ڶ���ѯ��������ֱ�ӷ�װ��һ��������������ý����װ��map���ϣ��������ڻ�ȡ����
		List<Map<String,Object>> map=null;
		try {
			map=adminProductService.findOrderInfoByOid(orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��mapת����json��ʽ������
		Gson gson=new Gson();
		String str=gson.toJson(map);
		System.out.println(str);
		response.getWriter().write(str);
	}
}