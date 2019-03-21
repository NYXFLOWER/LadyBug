<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'personalpage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="personalpage.css">

  </head>
  
  <body>
    <div id="personalpage_head">
	<img src="LadyBugLogo1.png">
	<h3>LadyBug</h3>
</div>

<div id="personalpage_frame_left">

	<form method="post" action="personalpage.js">

		<p><label class="label_head1"> Upload </label></p>
	<div id="upload_control">
		<input type="button" id="btn_upload" value="Browse From Computer" onclick="upload()"/>		
	</div>
	</form>
</div>
<div id="personalpage_frame_right">

	<form method="post" action="personalpage.js">
		<p><label class="label_head2"> Downloadable </label></p>
	<div id="upload_control">
		<input type="button" id="btn_download" value="Download" onclick="download()"/>		
	</div>
	</form>
</div>
  </body>
</html>
