<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title></title>
	</head>
	<body>
		<center>
			Now time is : <%=new java.util.Date() %>
			<hr>
		</center>
		<center>
			<button onClick="openMQClient()">启动客户端</button>
			<br>
			<b>Server:132.228.125.45</b>
			<br>
			<b>Tomcat：/var/tomcat-7/apache-tomcat-7.0.82</b>
		</center>
	</body>
</html>
<script type="text/javascript">
	function openMQClient(){
		$.messager.confirm('警告', '多个服务器只能同时运行一个ActiveMQ客户端', function(r){
			if (r){
				$.ajax({
					type: "POST",
					url: webPath + "activemq/receive.do",
					success:function(result){
						if("0"==result){
							$.messager.alert('tip','ActiveMQ client start success!','info');
						}else{
							$.messager.alert('tip','ActiveMQ client start failed!','warning');
						}
					}
				});
			}
		});
	}

</script>