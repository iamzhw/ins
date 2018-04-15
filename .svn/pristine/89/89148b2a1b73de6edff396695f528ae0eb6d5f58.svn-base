<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>外力点新增</title>


</head>
<body>
	<div style="padding:20px 0 10px 0px">
		<form id="ac" method="post">
				<table>
				<c:forEach items="${RelayList}" var="res">
					<tr>
						<td><input type="radio" name="tbrelay" value="${res.RELAY_ID}:${res.RELAY_NAME}" onclick="showTheCheckedRelay()"/>${res.RELAY_NAME}</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>
