
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
//window.onload=function(){
//var cid="${updateui.cid}";
//var options=document.getElementById("category").getElementsByTagName("option");
//for(var i=0;i<options.length;i++){
	//if(options[i].value==cid){
		//options[i].selected=true;
	//}
//}
//}

$(function(){
	$("#category option[value='${updateui.cid}']").attr("selected",true);
	$("#is_hot option[value='${updateui.is_hot}']").attr("selected",true);
});
</script>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'MyJsp.jsp' starting page</title>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <body>
    <form action="${pageContext.request.contextPath }/adminProduct?methodName=adminUpdatePro" method="post">
    <input type="hidden" value="${updateui.pid }" name="pid">
  <table style="font-size:12px;" bgColor="#f5fafe" width="100%">
    <tbody>
      <tr>
        <td colspan="4" bgcolor="#afd1f3" align="center" height="25px"><strong>添加商品</strong></td>
      </tr>
      <tr>
        <td width="18%" height="25px" align="center">商品名称:</td>
        <td bgcolor="#ffffff" height="25px" align="left"><input type="text" name="pname" value="${updateui.pname }"/></td>
        <td width="18%" height="25px" alidn="center">是否热门:</td>
        <td align="left" bgcolor="#ffffff" height="25px"><select name="is_hot" id="is_hot">
            <option value="1">是</option>
            <option value="0">否</option>
          </select></td>
      </tr>
      <tr>
        <td width="18%" height="25px" align="center">市场价格:</td>
        <td bgcolor="#ffffff" height="25px" align="left"><input type="text" name="market_price" value="${updateui.market_price }"/></td>
        <td width="18%" height="25px" alidn="center">商城价格:</td>
        <td align="left" bgcolor="#ffffff" height="25px"><input type="text" name="shop_price" value="${updateui.shop_price }"/></td>
      </tr>
      <tr>
        <td height="25px" align="center" width="18%">商品图片:</td>
        <td height="25px" bgcolor="#ffffff" align="left" colspan="3"><input type="file" name="upload"/></td>
      </tr>
      <tr>
        <td width="18%" height="25px" align="center">所属分类:</td>
        <td bgcolor="#ffffff" height=""25px colspan="3"><select name="cid" id="category">
            <c:forEach items="${updatecateui }" var="cate">
            <option value="${cate.cid }">${cate.cname}</option>
            </c:forEach>
          </select></td>
      </tr>
      <tr>
      <td align="center" width="18%">商品描述:</td>
      <td align="left" colspan="3"><textarea rows="5" cols="30" name="pdesc">${updateui.pdesc }</textarea></td>
      </tr>
      <tr>
      <td align="center" colspan="4">
      <input type="submit" value="确定" />&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"/>&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="" >返回</button>
      </td>
      </tr>
    </tbody>
  </table>
</form>
  </body>
</html>
