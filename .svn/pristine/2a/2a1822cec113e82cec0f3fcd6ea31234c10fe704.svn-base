<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../../util/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<head>
<style>
	tr,td {
		padding: 10px 10px 10px 10px;
	}
	.custom_field{
		font-size:14px;
	}
</style>
</head>
<body style="padding: 3px; border: 0px">
<div>
	<form id="jobId">
		<input type="hidden" name="check_detail_id" value="${CHECK_DETAIL_ID}" />
		<div>
			<table width="750" border="0" align="center" cellpadding="0" cellspacing="20px" bgcolor="#f4f4f4">
			<tr>
			<td><label>任务名称：</label> </td>
			<td><label class="custom_field">${TASK_NAME}</label></td>	
			<td><label>机房名称：</label> </td>
			<td><label class="custom_field">${ROOM_NAME}</label></td>	
			</tr>
			<tr>
			<td><label>巡检人：</label> </td>
			<td><label class="custom_field">${STAFF_NAME}</label></td>	
			<td><label>填报类型：</label> </td>
			<td><label class="custom_field">${IS_TEMP_STR}</label></td>	
			</tr>
			<tr>
			<td><label>检查项：</label> </td>
			<td><label class="custom_field">${CHECK_ITEM_NAME}</label></td>	
			<td><label>检查项类型：</label> </td>
			<td><label class="custom_field">${CHECK_TYPE}</label></td>	
			</tr>
			<tr>
			<td><label>开始时间：</label> </td>
			<td><label class="custom_field">${BEGIN_TIME_STR}</label></td>	
			<td><label>结束时间：</label> </td>
			<td><label class="custom_field">${END_TIME_STR}</label></td>	
			</tr>
			<tr>
			<td><label>隐患类型：</label> </td>
			<td><label class="custom_field">${TROUBLE_TYPE_NAME}</label></td>	
			<td><label>检查描述：</label> </td>
			<td><label class="custom_field">${DESCRIPTION}</label></td>	
			</tr>
			<tr>
			<td><label>检查图片：</label> </td>
			<td colspan="3">
			<c:forEach items="${pictures}" var="v">
				<a href="${v.PIC_PATH}" class="nyroModal"><img src="${v.PIC_PATH }" width="60" height="40"/></a>
			</c:forEach> 
			</td>
			</tr>
			<tr>
			<td><label>检查结果：</label> </td>
			<td><label class="custom_field">${CHECK_RESULT}</label></td>	
			<td><label>创建时间：</label> </td>
			<td><label class="custom_field">${CREATE_DATE_STR}</label></td>	
			</tr>
			</table>
		</div>
	</form>
</div>
<script>
$(function(){
	$('.nyroModal').nyroModal();
});
</script>
</body>