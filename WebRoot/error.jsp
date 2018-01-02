<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
    <head>
        <title>基于相似度计算的主观题阅卷系统设计</title>
    </head>
    <body>
        <h2>
            <s:property value="message" />
        </h2>
	<form action="Login!login" method="post">
		<input type="submit" value="重新登陆" />
	</form>
    </body>
</html>
