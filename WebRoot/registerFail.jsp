<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>注册失败</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">

  </head>
  
  <body>
   <h3 style="font-family:微软雅黑;">抱歉，注册失败，点击<a href="${pageContext.request.contextPath }/register.jsp" target="_self" style="text-decoration:none"><font size="2">重新注册</font></a></h3>
  </body>
</html>
