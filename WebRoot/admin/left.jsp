<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>My JSP 'MyJsp.jsp' starting page</title>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <body>
   <div class="dtree" style="font-size:12px;">
  <a style="text-decoration:none;"  href="javascript:d.openAll()">展开所有</a>|<a href="javascript:d.closeAll()" style="text-decoration:none;">关闭所有

</a>
  <script src="${pageContext.request.contextPath }/js/dtree.js" 

type="text/javascript"></script>
  <script type="text/javascript">
  d=new dTree('d');
  d.add('01', -1, '系统菜单树');
  d.add('0101', '01', '分类管理', '', '', 'mainFrame');
  d.add('010101','0101','分类管理','${pageContext.request.contextPath }/adminProduct?methodName=adminCategoryList','','mainFrame');
  d.add('0102', '01', '商品管理', '', '', 'mainFrame');
  d.add('010201','0102','商品管理','${pageContext.request.contextPath }/adminProduct?methodName=adminShowProductList','','mainFrame');
 document.write(d);
  </script>
  
  </div>
  </body>
</html>
