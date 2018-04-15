<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String serverName = request.getServerName();
	String serverPort = ""+request.getServerPort();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"></meta>
<script>
	var serverName = "<%=serverName%>";
	var serverPort = "<%=serverPort%>";
	//console.log(serverName + ":"+serverPort);
	if("61.160.128.47" == serverName && "8080"==serverPort){
		window.location.href="http://zhxj.telecomjs.com:9081/ins/jsp/frame/login_new/login.jsp";
	}else if("zhxj.telecomjs.com" == serverName && "8080"==serverPort){
		window.location.href="http://zhxj.telecomjs.com:9081/ins/jsp/frame/login_new/login.jsp";
	}else if("132.228.237.107" == serverName && "8080"==serverPort){
		window.location.href="http://132.228.125.45:9081/ins/jsp/frame/login_new/login.jsp";
	}else if("132.252.6.38" ==serverName && "8080"==serverPort){
		window.location.href="http://132.228.125.45:9081/ins/jsp/frame/login_new/login.jsp";
	}
	else if("132.228.176.96" ==serverName && "8080"==serverPort){
		window.location.href="http://132.228.125.45:9081/ins/jsp/frame/login_new/login.jsp";
	}
	else{
		window.location.href="<%=path%>/jsp/frame/login_new/login.jsp";
	}
</script>
</head>
</html>
