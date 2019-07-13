<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="/header.jsp"></jsp:include>
	<div class="container-fluid"
		style="margin:0 auto;background:url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat #FF2C4C ;width:100%;height:480px;font-family:微软雅黑;">
		<div class="row">
			<div class="col-md-7"></div>
			<div class="col-md-5"
				style="width:400px;padding:20px 20px 20px 20px;background:#fff;margin-top:20px;border:1px solid grey;border-radius:5px;">
				<font>用户登录&nbsp;&nbsp;USERLOGIN</font>
				<div class="col-md-12">
				<span style="color:red">${requestScope.loginfail }</span>
				</div>
				
				<form class="form-horizontal" method="post" action="${pageContext.request.contextPath }/user?methodName=login">
					<div class="form-group">
						<label for="username" class="col-sm-3 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="username"
								id="username">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-3 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" name="pw"
								id="password">
						</div>
					</div>
					<div class="form-group">
						<label for="yanzheng" class="col-sm-3 control-label">验证码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="yanzheng">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<div class="checkbox">
								<label> <input type="checkbox" name="autologin">自动登录&nbsp;&nbsp;<label><input
										type="checkbox">记住用户名</label>
								</label>

							</div>
						</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-default"
									style="background:url('${pageContext.request.contextPath}/images/login.gif');color:white;">登录</button>
							</div>
						</div>
				</form>
			</div>
		</div>
	</div>
	
		<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>
