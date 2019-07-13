<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>注册成功</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">
<style>
a{text-decoration:none;}
</style>
  </head>
  
  <body>
    <h2 style="font-family:微软雅黑;">恭喜您，注册成功，请立即去邮箱激活</h2>
    <a href="http://mail.163.com/">163网易邮箱</a>&nbsp;&nbsp;&nbsp;<a href="http://mail.126.com/">126网易邮箱</a>&nbsp;&nbsp;&nbsp;
    <a href="http://mail.qq.com">QQ邮箱</a>
  </body>
</html>
