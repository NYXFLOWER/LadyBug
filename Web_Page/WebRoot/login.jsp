<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="login.css">
	

  </head>
  
  <body>
    <div id="login_2_head">
	<img src="LadyBugLogo1.png"/>
	<h3>LadyBug</h3>	
</div>

<div id="login_2_frame">

	<p id="image_logo"><img src=""></p>

	<!-- <form name="loginForm1" action="dologin" method="post"> --> <!-- 调用java连数据库 -->
	<!-- <form name="loginForm1" action="loginCheck.jsp" method="post" enctype="multipart/form-data"> -->	<!-- 尝试用jsp连接数据库(突然失效) -->
 	<form ="loginForm1" action="LoginServlet" method="post"> <!-- 调用servlet session存储登陆用户 -->
		<pre><p><label class="label_input"> username: </label> <input type="text" name="txtUsername" class="text_field"/></p>
		<p><label class="label_input"> password: </label> <input type="text" name="txtPassword" class="text_field"></p></pre>
		<%String txtUsername=request.getParameter("txtUsername");
		request.getSession().setAttribute("txtUsername",txtUsername); %>	

		<div id="login_2_control">

			<input type="submit" id="btn_login_2" value="login" class="input login" />
			<a href="signup.jsp" id="create_newuser">	create a new user</a>
	</div>
	</form>
	<form action="login.jsp" method="get">
	
	</form>
	
</div>
  </body> 
  
  
</html>
