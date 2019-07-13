package com.qishiyi.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

//HttpServletRequestWrapper是专门用于装饰者模式的；wrapper是包装的意思
public class EnhanceRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	public EnhanceRequest(HttpServletRequest request) {
		super(request);
		request=this.request;
	}
	public String getParameter(String name){
		String parameter=null;
		try {
			parameter= new String(request.getParameter(name).getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parameter;
	}

}
