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
		//��ȡҳ�����ݣ���װһ��Product
		Map<String,Object> map=new HashMap<>();
		//����Product
		Product pro=new Product();
		//���������ļ����
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//�����ϴ��ļ�������
		ServletFileUpload upload=new ServletFileUpload(factory);
		//�����ļ�����������
		upload.setHeaderEncoding("UTF-8");
		//��ȡ�ļ����
		List<FileItem> fileItemList=null;
		try {
			fileItemList = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//����&&�ж��Ƿ����ļ��ϴ���
		if(fileItemList!=null){
			for(FileItem item:fileItemList){
				//����ͨ����
				if(item.isFormField()){
					String fieldName=item.getFieldName(); //key
					String value=item.getString("UTF-8"); //value
					map.put(fieldName, value); //put��map��
				}
				//���ϴ��ļ���
				else{
					//��ȡ�ļ���������·������Ҫ��ȡ��
					String fileName=item.getName();
					//��ȡ
					String[] str=fileName.split("\\\\");
					//��ȡ�洢���ϴ����ļ���·��
					String path=this.getServletContext().getRealPath("products");
					//��ȡInputStream
					InputStream in=item.getInputStream();
					//����OutputStream
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
			BeanUtils.populate(pro,map);//����ȡ�������ݷ�װ��Product������
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