<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'signup.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="signup.css">

  </head>
 

  <body>
    <div id="signup_head">
	<img src="LadyBugLogo1.png">
	<h3>LadyBug</h3>
</div>

<div id="signup_frame">

	<p id="image_logo"><img src=""></p>

	<form name="signupForm" method="post" action="dosignup">

		<pre><p><label class="label_input1"> username: </label><br><input type="text" name="txtUsername" class="text_field1"/></p><p><label class="label_input1"> password: </label><br><input type="text" name="txtPassword" class="text_field1"></p><p><label class="label_input1">  verify:  </label><br><input type="text" name="txtverify_password" class="text_field1"></p><p><label class="label_input1"> invitationCode: </label><br><input type="text" name="txtInvitationCode" class="text_field1"></p></pre>
	<div id="signup_control">

		<!-- <input type="button" id="btn_signup" value="signup" onclick="signup()"/> -->	
		<input type="submit" value="signup" class="input signup">	
	</div>		
	</form>
	
</div>
  </body>
</html>
