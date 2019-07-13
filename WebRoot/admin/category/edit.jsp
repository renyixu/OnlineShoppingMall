
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <body>
    <form>
<table width="100%" bgColor="#f5fafe" style="font-size:12px;">
<tbody>
<tr bgcolor="#afd1f3">
<td align="center"><strong>添加分类</strong></td>
</tr>
<tr>
<td style="padding-left:35px;">分类名称:<input type="text" /></td>
</tr>
<tr>
<td align="center"><input type="submit" value="确定"/>&nbsp;&nbsp;&nbsp;<input type="reset" value="重置" />&nbsp;&nbsp;&nbsp;<input type="button" value="返回" /></td>
</tr>
</tbody>

</table>
</form>
  </body>
</html>
