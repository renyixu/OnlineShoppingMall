
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <table bgColor="#f5fafe" width="100%" style="font-size:12px;">
<tbody>
<tr>
<td  colspan="4" height="20px" style="background:#afd1f3;text-align:center"><strong>分类列表</strong></td>
</tr>
<tr>
<td align="right" colspan="4"><button style="height:20px;width:60px;" type="button" value="添加" name="add_ca" id="add_ca" onclick="add_ca()">添加</button></td>
</tr>
<tr bgcolor="#afd1f3">
<td  align="center" height="20px"><strong>序号</strong></td>
<td  align="center" height="20px"><strong>分类名称</strong></td>
<td  align="center" height="20px"><strong>编辑</strong></td>
<td  align="center" height="20px"><strong>删除</strong></td>
</tr>
<c:forEach items="${requestScope.categoryList }" var="cate" varStatus="vs">
<tr>
<td  align="center" height="25px"><strong></strong>${vs.count}</td>
<td align="center" height="25px"><strong>${cate.cname }</strong></td>
<td  align="center" height="25px"><strong><a href="#"><img src="${pageContext.request.contextPath}/images/i_edit.gif" /></a></strong></td>
<td  align="center" height="25px"><a href="#"><img src="${pageContext.request.contextPath}/images/i_del.gif" /></a></td>
</tr>

</c:forEach>
</tbody>
</table>
  </body>
</html>
