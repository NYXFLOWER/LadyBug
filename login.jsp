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
	<img src=""/>
	<h3>LadyBug</h3>	
</div>

<div id="login_2_frame">

	<p id="image_logo"><img src=""></p>

	<form action="jump.jsp" method="get">
		
		<pre><p><label class="label_input"> username: </label> <input type="text" id="username" class="text_field"/></p>
		<p><label class="label_input"> password: </label> <input type="text" id="password" class="text_field"></p></pre>
			

	<div id="login_2_control">
		<button type="submit" id="btn_login_2" value="login" onclick="login()" >login</button>		
		<a href="signup.html" id="create_newuser">	create a new user</a>
	</div>
	</form>
	<form action="login.jsp" method="get">
	
	</form>
	
</div>
  </body>
</html>
