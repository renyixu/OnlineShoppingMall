<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>浏览记录</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/bootstrap.min.css" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">
	<script src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
 	<script src="${pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
  </head>
 
  <body>
 <jsp:include page="/header.jsp"></jsp:include>
    <div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999;font-family:微软雅黑;">

		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div style="clear: both;"></div>
		<div style="overflow: hidden;">
			<ul style="list-style: none;">
			<c:if test="${!empty lookRecord }">
			<c:forEach items="${lookRecord }" var="pro">
				<li
					style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;"><img
					src="${pageContext.request.contextPath }/${pro.pimage}" width="130px" height="130px" /><span>${pro.pname }</span></li>
					</c:forEach>
					</c:if>
					<c:if test="${empty lookRecord }">
					抱歉，暂无浏览记录
					</c:if>
			</ul>
		</div>
	</div>
	<jsp:include page="/footer.jsp"></jsp:include>
  </body>
</html>
