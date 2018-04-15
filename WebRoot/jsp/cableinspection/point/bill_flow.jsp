<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>我的工单</title>
</head>
<body style="padding: 3px; border: 0px; overflow: auto;">
	<table class="main_table" border="1" style="border-color: #8DB2E3;" style="width: 100%">
		<tr>
			<td class="head">&nbsp;&nbsp;序号&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;操作人&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;操作描述&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;目标岗位&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;操作时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<c:forEach items="${flowList }" var="flow" varStatus="status">
		<tr>
			<td>${status.index + 1}</td>
			<td class="textleft">${flow.OPERATOR }</td>
			<td class="textleft">${flow.OPERATE_INFO }</td>
			<td class="textleft">${flow.RECEIVOR }</td>
			<td class="textleft">${flow.OPERATE_TIME }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>