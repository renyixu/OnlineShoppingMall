<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>默认页面</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">

  </head>
  
  <body>
    <%
    //在默认页面写一个重定向，向/findIndexPro请求
    //response是内置对象
    response.sendRedirect(request.getContextPath()+"/product?methodName=findIndexPro");
     %>
  </body>
</html>
