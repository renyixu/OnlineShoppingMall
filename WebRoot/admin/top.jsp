
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'MyJsp.jsp' starting page</title>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
  </head>
  <body>
<table width="100%">
<tr>
<td><img src="${pageContext.request.contextPath}/images/top_01.jpg" /></td>
<td><img src="${pageContext.request.contextPath}/images/top_100.jpg"/></td>
</tr>

</table>

  </body>
</html>
