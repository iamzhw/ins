<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/highcharts.js"></script>
<script type="text/javascript" src="<%=path%>/js/swfobject.js"></script>
<script type="text/javascript" src="<%=path%>/js/json2.js"></script>
<script type="text/javascript" src="home.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/default.css"></link>
<link rel="stylesheet" type="text/css" href="style.css"></link>
</head>
<body>
	<div class="content">
		<div class="province">
			<div id="province"></div>
		</div>
		<input id="chart" type="button" onClick="cableChart()" title="点击查看缆线巡检图表" value="点击查看缆线巡检图表">
		<div class="charts">
			<div class="top">
				<div class="left" id="left"></div>
				<div class="right" id="right"></div>
			</div>
			<div class="bottom" id="bottom"></div>
		</div>
	</div>
</body>
</html>
