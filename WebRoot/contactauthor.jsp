<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>联系作者</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>

  </head>
  
  <body>
  <jsp:include page="/header.jsp"></jsp:include>
    <div class="container-fluid" style="font-family:微软雅黑;">
    <div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-7" style="margin-top:30px;">
    <font size="9">联系信息</font>
    <hr style="height:2px;border-top:2px dashed #185598">
    <span style="font-size:25px;">简介</span>
    <div style="border:solid 2px grey;border-radius:5px;width:100%;height:50px;text-align:center;padding-top:8px;">
    <font size="5px;">作者为在校大学生，若对此网站有改进意见，可发邮件联系作者。</font>
    </div>
     <hr style="height:2px;border-top:2px dashed #185598">
    <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
    <span style="font-size:23px;">联系邮箱：yctuxry@163.com&nbsp;&nbsp;&nbsp;<a href="http://mail.163.com" text-decoration="none" target="_blank" style="font-size:15px;">去发邮件</a></span><br>
    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
    <font style="font-size:23px">联系微博：<a href="http://weibo.com/u/3637336455" style="text-decoration:none" target="_blank">通过微博联系我</a></font>
     <hr style="height:2px;border-top:2px dashed #185598">
    </div>
    </div>
    </div>
    <jsp:include page="/footer.jsp"></jsp:include>
  </body>
</html>
