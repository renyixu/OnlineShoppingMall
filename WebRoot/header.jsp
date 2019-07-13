<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	//页面加载完毕后使用ajax去数据库中查找所有的category
	<%--
	$(function() {
		$
				.post(
						"${pageContext.request.contextPath}/product?methodName=findAllCategory&t="
								+ Math.random(),
						function(data) {
							var content = "";
							for (var i = 0; i < data.length; i++) {
								content = content
										+ "<li><a href='${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid="
										+ data[i].cid + "&currentPage=1'>"
										+ data[i].cname + "</a></li>";
							}
							$("#categoryUl").html(content);
						}, "json");
	});
	--%>
	function show_searchinfo(obj) {
		$("#searchinfo").css("display", "block");
		//1.获取输入的内容
		var searchContent = $(obj).val();
		//2.将输入的内容发送到数据库中查找
		$
				.post(
						"${pageContext.request.contextPath}/product?methodName=searchInfoMenu&t="
								+ Math.random(),
						{
							"searchContent" : searchContent
						},
						function(data) {
							//3.显示查找的内容
							var content = "";
							for (var i = 0; i < data.length; i++) {
								content = content
										+ "<div style='background:#fff;padding:5px;cursor:pointer;' onmouseover='over(this)' onmouseout='out(this)' onclick='clickFn(this)'>"
										+ data[i] + "</div>";
							}
							$("#searchinfo").html(content);
						}, "json");
	}

	function over(objover) {
		$(objover).css("background", "#808080");
	}
	function out(objout) {
		$(objout).css("background", "#fff");
	}

	function clickFn(objclick) {
		var con = $(objclick).html();
		$("#searchinput").val(con);
		$("#searchinfo").css("display", "none");
	}
</script>
<div class="container-fluid" style="font-family:微软雅黑;">
	<!--首行-->
	<div class="row">
		<div class="col-md-4">
			<img src="images/logo2.png" />
		</div>
		<div class="col-md-4">
			<img src="images/header.png" />
		</div>
			<!-- 未登录状态 -->
			<c:if test="${empty sessionScope.login }">
			<div class="col-md-4" style="padding-top:15px;padding-left:50px;">
				<a href="${pageContext.request.contextPath }/login.jsp">登录</a>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/register.jsp">注册</a>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/product?methodName=showCartList">购物车</a><span id="cartItemCount" style="color:red;font-size:28px"></span>&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath }/product?methodName=lookRecord">浏览记录</a>&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath }/admin/home.jsp" target="_blank">后台系统</a>
		</div>
		</c:if>
		<!-- 登录状态 -->
		<c:if test="${!empty sessionScope.login }">
		<div class="col-md-4" style="padding-top:15px;padding-left:50px;">
		欢迎您，${sessionScope.login.username }&nbsp;&nbsp;
		<a href="#"></a>&nbsp;&nbsp;<a href="#">退出</a>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/product?methodName=showCartList">购物车</a><span id="cartItemCount" style="color:red;font-size:28px"></span>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/product?methodName=lookRecord">浏览记录</a>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/admin/home.jsp" target="_blank">后台系统</a>
	</div>
	</c:if>
</div>
</div>
<!--导航栏-->
<div class="container-fluid" style="font-family:微软雅黑;">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"
					href="${pageContext.request.contextPath }/default.jsp">首页</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=1&currentPage=1">手机数码</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=2&currentPage=1">电脑办公</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=3&currentPage=1">海鲜果蔬</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=4&currentPage=1">家具家居</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=5&currentPage=1">靴鞋箱包</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=6&currentPage=1">图书音像</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=7&currentPage=1">虚拟货币</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=8&currentPage=1">母婴孕婴</a></li>
				<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=9&currentPage=1">汽车用品</a></li>
				</ul>
				<form class="navbar-form navbar-right" action="#" method="post">
					<div class="form-group" style="position:relative">
						<input type="text" class="form-control" placeholder="输入搜索内容"
							onkeyup="show_searchinfo(this)" id="searchinput">
						<div
							style="border:1px solid #808080;width:180px;z-index:1;position:absolute;"
							id="searchinfo" background="#fff" display="none"></div>
					</div>
					<button type="submit" class="btn btn-default">搜索</button>
				</form>

			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>