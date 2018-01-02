<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="jQuery.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <!-- 
  <script type="text/javascript">
  	$(document).ready(function(){
		$("#answer1").mouseover(function() {
            $("#answer").show('slow');});
            
        $("#answer1").mouseout(function() {
            $("#answer").hide('slow');});

	});
  </script>
  -->
  <body>
    问题：什么是实体？ <br>
    <form action="HelloWorld" method="post">
    	学生答案：<br />
    	<textarea name="ans" cols="20" rows="5"></textarea>
    	<input type="submit" value="提交" />
    </form>
  </body>
</html>
