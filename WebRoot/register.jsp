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
<title>注册</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<link type="text/css"
	href="${pageContext.request.contextPath }/css/bootstrap.min.css"
	rel="stylesheet">
	<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
</head>
<style>
.error {
	color: red
}
</style>
<script>
	//自定义检查用户名是否存在的校验规则
	$.validator.addMethod("isExist", 
	//校验规则函数
	//value是表单输入的内容
	//element是校验组件的节点对象
	//parames是校验规则的参数
	function(value,element,parames) {
		var flag=false;
		$.ajax({
		async:false,//如果是异步的话，那么时间上会有延迟
		type:"POST",
		url:"${pageContext.request.contextPath}/user?methodName=checkUserName&t="+Math.random(),
		data:{username:value},
		success:function(data){
		flag=data.isExist;
		},
		dataType:"json"
		});
		return flag;//当返回的是flag时，不能通过验证，返回的是true时，通过验证
	});

	$(function() {
		$("#registerForm").validate({
			rules : {
				"username" : {//此处是name，不是id
					required : true,
					isExist:true
				},
				"pw" : {
					required : true,
					rangelength : [ 6, 12 ]
				},
				"sure_pw" : {
					required : true,
					equalTo : "#pw" //此处equalTo等于的是"#id"，不是name
				},
				"email" : {
					required : true,
					email : true
				},
				"name" : {
					required : true
				},
				"gender" : {
					required : true
				},
				"birthday" : {
					required : true,
					date : true
				}
			},
			messages : {
				"username" : {
					required : "用户名不能为空",//错误提示信息是在内存中的，并没有源代码
					isExist:"用户名已存在"
				},
				"pw" : {
					required : "密码不能为空",
					rangelength : "密码长度6-12个字符"
				},
				"sure_pw" : {
					required : "确认密码不能为空",
					equalTo : "两次密码不一致"
				},
				"email" : {
					required : "邮箱不能为空",
					email : "邮箱格式不正确"
				},
				"name" : {
					required : "姓名不能为空"
				},
				"gender" : {
					required : "性别必须选"
				},
				"birthday" : {
					required : "出生日期不能为空",
					date : "日期格式不正确"
				}
			}
		});
	});
</script>
<body>
	<jsp:include page="/header.jsp"></jsp:include>
	<div class="container-fluid"
		style="background:url('${pageContext.request.contextPath }/image/regist_bg.jpg');width:100%;height:600px;font-family:微软雅黑;">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6"
				style="border:2px solid grey;border-radius:7px;margin-top:25px;padding:20px 20px 20px 20px;">
				<font face="微软雅黑" size="5" color="blue">会员注册&nbsp;&nbsp;&nbsp;</font>USER
				REGISTER
				<form id="registerForm" class="form-horizontal" method="post"
					action="${pageContext.request.contextPath }/user?methodName=register">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="username"
								name="username">
						</div>
					</div>
					<div class="form-group">
						<label for="pw" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" id="pw" name="pw">
						</div>
					</div>
					<div class="form-group">
						<label for="sure_pw" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-5">
							<input type="password" class="form-control" id="sure_pw"
								name="sure_pw">
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">邮箱</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="email" name="email">
						</div>
					</div>
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="name" name="name">
						</div>
					</div>
					<div class="form-group">
						<label for="gender" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-5">
							<div class="checkbox">
								<label> <input type="radio" name="gender" id="gender"
									value="male">男
								</label> <label> <input type="radio" name="gender" id="gender"
									value="female">女
								</label> <label class="error" for="gender" style="display:none">性别必须选</label>
							</div>
						</div>
					</div>


					<div class="form-group">
						<label for="birthday" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="birthday"
								name="birthday">
						</div>
					</div>
					<div class="form-group">
						<label for="yanzheng" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" id="yanzheng"
								name="yanzheng">
						</div>
						<div class="col-sm-2">
							<img
								src="${pageContext.request.contextPath }/image/captcha.jhtml">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-default"
								style="background:url('${pageContext.request.contextPath }/images/register.gif');color:white;">注册</button>
						</div>
					</div>
				</form>

			</div>
		</div>


	</div>
</body>
</html>
