<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>我的工单</title>
</head>
<body style="padding: 3px; border: 0px">
	<table width="750" border="0" align="center" cellpadding="0"
		cellspacing="0" bgcolor="#f4f4f4">
		<tr>
			<td height="480" align="left" valign="top" bgcolor="#FFFFFF"
				style="padding: 17px;">
				
				<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
				<c:forEach items="${photo }" var="p">
					<tr height="100px" bgcolor="#FFFFFF">
						<td colspan="2" align="left" class="list_pd1">
								<a href="${p.PHOTO_PATH }" title="照片预览" class="nyroModal">
									<img src="${p.MICRO_PHOTO_PATH }" style="width: 100px;" />
								</a>
						</td>
					</tr>
						<td align="right" class="form_01b"><strong>拍摄人员</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${p.STAFF_NAME}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="form_01b"><strong>拍摄时间</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${p.CREATE_TIME}&nbsp;</td>
					</tr>
					</c:forEach>
				</table>
				
			</td>
		</tr>
	</table>
<script type="text/javascript">
$(function(){
	$('.nyroModal').nyroModal();
});
</script>
</body>
</html>