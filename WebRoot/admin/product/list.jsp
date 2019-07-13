
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#searchishot option[value='${searchcondition.isHot}']").attr("selected",true);
	$("#searchcategory option[value='${searchcondition.category}']").attr("selected",true);
});
function addPro(){
window.location.href="${pageContext.request.contextPath}/adminProduct?methodName=adminFindAllCateWhenAddPro";
}
function delPro(pid,pname){
var isDel=window.confirm("您确定要删除 "+pname+"?");
if(isDel==true){
	window.location.href="${pageContext.request.contextPath}/adminProduct?methodName=adminDelPro&pid="+pid;
}
}
function updatePro(pid){
	window.location.href="${pageContext.request.contextPath}/adminProduct?methodName=adminFindAllCateWhenUpdatePro&pid="+pid;
}

</script>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'MyJsp.jsp' starting page</title>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
  </head>
  <body>
  <table width="100%" bgcolor="#f5fafe" style="font-size:12px;">
<tbody>
<tr >
<td colspan="7">
<form method="post" action="${pageContext.request.contextPath }/adminProduct?methodName=adminSearchPro">
商品名称:<input type="search" name="name" placeholder="请输入商品名称" id="searchname" value="${searchcondition.name}"/>&nbsp;&nbsp;&nbsp;是否热门:
<select name="isHot" id="searchishot">
<option value="">不限</option>
<option value="1">是</option>
<option value="0">否</option>
</select>
&nbsp;&nbsp;&nbsp;
商品类别:
<select name="category" id="searchcategory">
<option value="">不限</option>
<c:forEach items="${searchCateList }" var="cate">
<option value="${cate.cid }">${cate.cname }</option>
</c:forEach>
</select>
<input type="submit" value="搜索" ">
</form>
</td>
</tr>
<tr >
<td colspan="7" bgcolor="#afd1f3" align="center" height="20px"><strong>商品列表</strong></td>
</tr>
<tr>
<td colspan="7" align="right"><button type="button" id="add_pro" value="添加" style="width:60px;height:20px;" onclick="addPro()">添加</button></td>
</tr>
<tr align="center" bgcolor="#afd1f3">
<td height="20px"><strong>序号</strong></td>
<td height="20px"><strong>商品图片</strong></td>
<td height="20px"><strong>商品名称</strong></td>
<td height="20px"><strong>商品价格</strong></td>
<td height="20px"><strong>是否热门</strong></td>
<td height="20px"><strong>编辑</strong></td>
<td height="20px"><strong>删除</strong></td>
</tr>
<c:forEach items="${adminAllPro }" var="pro" varStatus="vs">
<tr align="center" onmouseover="this.style.background='#EEE8AA'" onmouseout="this.style.background='#f5fafe'">
<td height="25px">${vs.count}</td>
<td height="25px"><img src="${pro.pimage }" width="40" height="45"></td>
<td height="25px">${pro.pname }</td>
<td height="25px">&yen;${pro.shop_price }</td>
<td height="25px">
<c:if test="${pro.is_hot==1 }">是</c:if>
<c:if test="${pro.is_hot==0 }">否</c:if>
</td>
<td height="25px"><a href="javascript:void(0)" onclick="updatePro('${pro.pid}')"><img src="${pageContext.request.contextPath}/images/i_edit.gif" /></a></td>
<td height="25px"><a href="javascript:void(0)" onclick="delPro('${pro.pid}','${pro.pname }')"><img src="${pageContext.request.contextPath}/images/i_del.gif" /></a></td>
</tr>
</c:forEach>

<c:forEach items="${listSearchPro }" var="pro" varStatus="vs">
<tr align="center" onmouseover="this.style.background='#EEE8AA'" onmouseout="this.style.background='#f5fafe'">
<td height="25px">${vs.count}</td>
<td height="25px"><img src="${pro.pimage }" width="40" height="45"></td>
<td height="25px">${pro.pname }</td>
<td height="25px">&yen;${pro.shop_price }</td>
<td height="25px">
<c:if test="${pro.is_hot==1 }">是</c:if>
<c:if test="${pro.is_hot==0 }">否</c:if>
</td>
<td height="25px"><a href="javascript:void(0)" onclick="updatePro('${pro.pid}')"><img src="${pageContext.request.contextPath}/images/i_edit.gif" /></a></td>
<td height="25px"><a href="javascript:void(0)" onclick="delPro('${pro.pid}','${pro.pname }')"><img src="${pageContext.request.contextPath}/images/i_del.gif" /></a></td>
</tr>
</c:forEach>

</tbody>

</table>
  </body>
</html>
