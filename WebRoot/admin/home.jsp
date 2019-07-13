<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>后台管理系统</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
</head>
<frameset rows="15%,*" frameborder=0 border="0" framespacing="0">
	<frame src="${pageContext.request.contextPath}/admin/top.jsp"
		scrolling="NO" noresize />
	<frameset cols="16%,*" frameborder=0 border="0" framespacing="0">
		<frame src="${pageContext.request.contextPath }/admin/left.jsp" />
		<frame
			src="${pageContext.request.contextPath }/admin/category/list.jsp"
			name="mainFrame" />
	</frameset>
</frameset>
</html>
