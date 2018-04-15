<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<title>导入</title>
<%@include file="../../../util/head.jsp"%>
</head>
<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="sv" method="post" enctype ="multipart/form-data" >
			<table style="line-height: 30px;">
				<tr>
					<td>导入：</td>
					<td>
						<input type="file" name="file" id="excel"/>
					</td>
				</tr>			
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="editLinePoint();">确定</div>
		</div>
	</div>
</body>
</html>