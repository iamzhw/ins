<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>关键点签到情况</title>
</head>
<body style="padding: 3px; border: 0px; overflow: auto;">
	<table class="main_table" border="1" style="border-color: #8DB2E3;" style="width: 100%">
		<tr>
			<td class="head">&nbsp;&nbsp;序号&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;缆线名称&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;关键点名称&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;是否已签到&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;签到时间&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td class="head">&nbsp;&nbsp;&nbsp;&nbsp;签到人&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<c:forEach items="${keyPointsArrivaledList }" var="keyPoint" varStatus="status">
		<tr>
			<td>${status.index + 1}</td>
			<td class="textleft">${keyPoint.LINE_NAME }</td>
			<td class="textleft">${keyPoint.POINT_NAME }</td>
			<td class="textleft">${keyPoint.ISARRIVALED }</td>
			<td class="textleft">${keyPoint.CREATE_TIME }</td>
			<td class="textleft">${keyPoint.STAFF_NAME }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>