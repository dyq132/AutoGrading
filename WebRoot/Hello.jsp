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
    学生答案和标准答案的相似度为：
        <h2>
            <s:property value="resultScore" />
        </h2>
    </body>
</html>
