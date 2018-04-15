
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">

	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">

				<table class="condition">

					<c:forEach items="${photoList}" var="res">
						<tr>
							<td><img src="${res.PHOTO_PATH}" />
							</td>

						</tr>

					</c:forEach>






				</table>
			</form>
		</div>

	</div>



	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>


</body>
</html>
