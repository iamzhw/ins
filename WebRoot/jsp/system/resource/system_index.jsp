<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>资源巡检</title>
</head>
<body style="padding:3px;border:0px">
	<form id="from1" action="" target="_blank" method="post" >
	<input type="hidden" name="staffNo" value="${sessionScope.staffNo}"/>
	<input type="hidden" name="password" value="${sessionScope.passWord}"/>
	</form>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		var staffNO="${sessionScope.staffNo}";
		var passWord="${sessionScope.passWord}";
		var url=window.location.href+"";
		var h=url.indexOf("132.228.125.45");
		var url1;
		if(h!=-1){
		url1="http://132.228.125.45:9080/radio/login!checkStaff";
		}else{
		url1="http://61.160.128.47:9080/radio/login!checkStaff";
		}
		//var url1="http://61.160.128.47:9080/radio/login!checkStaff";
		
		$("#from1").attr("action", url1);
		$("#from1").submit();
		//window.open("http://61.160.128.47:9080/radio/login!checkStaff?staffNo="+staffNO+"&password="+passWord);
		closeTab();
	})

	
</script>
