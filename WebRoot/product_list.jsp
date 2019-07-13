<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>商品列表</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/icon.ico">
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="row" style="width: 1210px; margin: 0 auto;font-family:微软雅黑;">
		<c:forEach items="${pageBean.list }" var="pro">
			<div class="col-md-2" style="height:250px">
				<a href="${pageContext.request.contextPath }/product?methodName=findProInfo&pid=${pro.pid}" target="_blank"> <img
					src="${pageContext.request.contextPath }/${pro.pimage}" width="170"
					height="170" style="display: inline-block;">
				</a>
				<p>
					<a href="${pageContext.request.contextPath }/product?methodName=findProInfo&pid=${pro.pid}" target="_blank" style='color: green'>${pro.pname }</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${pro.shop_price }</font>
				</p>
			</div>
		</c:forEach>
	</div>

	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
		<!-- 上一页 -->
		<c:if test="${pageBean.currentPage==1 }">
		<li class="disabled"><a href="javascript:void(0)" aria-label="Previous"><span
					aria-hidden="true">&laquo;</span></a></li>
		</c:if>
			<c:if test="${pageBean.currentPage!=1 }">
		<li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=${cid }&currentPage=${pageBean.currentPage-1}" aria-label="Previous"><span
					aria-hidden="true">&laquo;</span></a></li>
		</c:if>
		<!-- 循环1 2 3 4... -->
		<c:forEach begin="1" end="${pageBean.totalPage }" var="i">
		<c:if test="${pageBean.currentPage==i }"><li class="active"><a href="javascript:void(0)">${i }</a></li></c:if>
		<c:if test="${pageBean.currentPage!=i }"><li><a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=${cid }&currentPage=${i}">${i }</a></li></c:if>
		</c:forEach>
		<!-- 下一页 -->	
		<c:if test="${pageBean.currentPage==pageBean.totalPage }">
		<li class="disabled">
		<a href="javascript:void(0)" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</li>
		</c:if>
		<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
		<li>
		<a href="${pageContext.request.contextPath}/product?methodName=findProByMenuPage&cid=${cid }&currentPage=${pageBean.currentPage+1}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</li>
		</c:if>
		</ul>
	</div>
	<!-- 分页结束 -->
	
	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>