package com.qishiyi.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.qishiyi.domain.Product;
import com.qishiyi.service.AdminProductService;
@SuppressWarnings("all")
public class AdminAddProServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/htm;charset=UTF-8");
		//adminAddProServlet
		//获取页面数据，封装一个Product
		Map<String,Object> map=new HashMap<>();
		//创建Product
		Product pro=new Product();
		//创建磁盘文件项工厂
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//创建上传文件核心类
		ServletFileUpload upload=new ServletFileUpload(factory);
		//设置文件名编码问题
		upload.setHeaderEncoding("UTF-8");
		//获取文件项集合
		List<FileItem> fileItemList=null;
		try {
			fileItemList = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//遍历&&判断是否是文件上传项
		if(fileItemList!=null){
			for(FileItem item:fileItemList){
				//是普通表单项
				if(item.isFormField()){
					String fieldName=item.getFieldName(); //key
					String value=item.getString("UTF-8"); //value
					map.put(fieldName, value); //put到map中
				}
				//是上传文件项
				else{
					//获取文件名（绝对路径，需要截取）
					String fileName=item.getName();
					//截取
					String[] str=fileName.split("\\\\");
					//获取存储所上传的文件的路径
					String path=this.getServletContext().getRealPath("products");
					//获取InputStream
					InputStream in=item.getInputStream();
					//创建OutputStream
					OutputStream out=new FileOutputStream(path+"/"+str[str.length-1]);
					pro.setPimage("products/"+str[str.length-1]);
					int count=0;
					byte[] buf=new byte[1024];
					while((count=in.read(buf))!=-1){
						out.write(buf);
						out.flush();
					}
					in.close();
					out.close();
				}
			}
		}
		try {
			BeanUtils.populate(pro,map);//将获取到的数据封装到Product对象中
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pro.setPid(UUID.randomUUID().toString());
		SimpleDateFormat format=new SimpleDateFormat("yy-MM-dd");
		String pdate=format.format(new Date());
		pro.setPdate(pdate);
		pro.setPflag(0);
		try {
			new AdminProductService().addNewPro(pro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/adminProduct?methodName=adminShowProductList").forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}