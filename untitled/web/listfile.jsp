<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>下载文件显示页面</title>
</head>
<body>
<!-- 遍历Map集合 -->
<c:forEach var="me" items="${fileNameMap}">
<c:url value="/servlet/DownLoadServlet" var="downurl">
<c:param name="filename" value="${me.key}"></c:param>
</c:url>
${me.value}<a href="${downurl}">下载</a>
<br/>
</c:forEach>
</body>
</html>