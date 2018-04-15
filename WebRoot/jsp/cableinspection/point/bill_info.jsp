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
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="background: url(../images/detail_04.jpg) repeat-x left top">
					<tr>
						<td width="4" align="left"><img
							src="../images/detail_03.jpg" width="4" height="32" /></td>
						<td width="31" align="center"><img
							src="../images/detail_09.jpg" width="15" height="15" /></td>
						<td width="198" align="left"
							style="font-weight: bold; font-size: 14px;">详细信息</td>
						<td align="right" style="padding-right: 20px;"></td>
						<td width="4" align="right"><img
							src="../images/detail_06.jpg" width="4" height="32" /></td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" cellpadding="3"
					bgcolor="#CCCCCC">
					<!--beginunit maildetail-->
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>隐患点编码</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.POINT_NO}&nbsp;</td>
						<td align="right" class="form_01b"><strong>隐患点名称</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.POINT_NAME}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>隐患类型</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2" colspan="3">${billMap.TYPE_NAME}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>上报人员</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.STAFF_NAME}&nbsp;</td>
						<td align="right" class="form_01b"><strong>上报时间</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.CREATE_TIME}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>维护人员</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.MAINTOR}&nbsp;</td>
						<td align="right" class="form_01b"><strong>审核人员</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.AUDITOR}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>问题描述</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2" colspan="3">${billMap.DESCRP}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>地市</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.AREA}&nbsp;</td>
						<td align="right" class="form_01b"><strong>区县</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.SON_AREA}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>光缆等级</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.CABLE_LEVEL}&nbsp;</td>
						<td align="right" class="form_01b"><strong>光缆名称</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.CABLE_NAME}&nbsp;</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>施工单位</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.CONS_UNIT}&nbsp;</td>
						<td align="right" class="form_01b"><strong>施工内容</strong>&nbsp;&nbsp;</td>
						<td align="left" class="list_pd2">${billMap.CONS_CONTENT}&nbsp;</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="background: url(../images/detail_23.jpg) repeat-x left top; border: solid 1px #ccc; border-top: none; border-bottom: none;">
					<tr>
						<td width="36" height="31" align="center"><img
							src="../images/detail_25.jpg" width="15" height="15" /></td>
						<td style="font-weight: bold; font-size: 14px;">照片查看</td>
					</tr>
				</table>
				<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">
					<tr height="100px" bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>整改前</strong>&nbsp;&nbsp;</td>
						<td colspan="4" align="left" class="list_pd1">
							<c:forEach items="${photoBefore }" var="b">
								<a href="${b.PHOTO_PATH }" title="照片预览" class="nyroModal">
									<img src="${b.MICRO_PHOTO_PATH }" style="width: 100px;"/></a>  
							</c:forEach>
						</td>
					</tr>
					<tr height="100px" bgcolor="#FFFFFF">
						<td align="right" class="form_01b"><strong>整改后</strong>&nbsp;&nbsp;</td>
						<td colspan="4" align="left" class="list_pd1">
							<c:forEach items="${photoAfter }" var="p">
								<a href="${p.PHOTO_PATH }" title="照片预览" class="nyroModal">
									<img src="${p.MICRO_PHOTO_PATH }" style="width: 100px;"/></a>  
							</c:forEach>
						</td>
					</tr>
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