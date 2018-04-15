<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<script type="text/javascript">

</script>
<title>编辑外力点权限</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formEdit" method="post">
			<input type="hidden"  id="staff_id"  name="staff_id"  value="${staff_id}" >
		
			<table>
				<tr>
					<td>分公司：</td>
					<td>
						<div>
							<select name="area_level" id="area_level" class="condition-select e"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								<option value='' <c:if test="${''==AREA_LEVEL}">selected</c:if>>--请选择--</option>
								<option value='0' <c:if test="${'0'==AREA_LEVEL}">selected</c:if>>市公司</option>
								<option value='1' <c:if test="${'1'==AREA_LEVEL}">selected</c:if>>县公司</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>管理级别：</td>
					<td>
						<select name="manage_level" id="manage_level" class="condition-select  condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								<option value='' <c:if test="${''==MANAGE_LEVEL}">selected</c:if>>--请选择--</option>
								<option value='0' <c:if test="${'0'==MANAGE_LEVEL}">selected</c:if>>分管领导</option>
								<option value='1' <c:if test="${'1'==MANAGE_LEVEL}">selected</c:if>>主要管理和技术人员</option>
								<option value='2' <c:if test="${'2'==MANAGE_LEVEL}">selected</c:if>>班组长</option>
							</select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 90px">
			<div class="btn-operation" onClick="updateAsssignOutSite()">保存</div>
		</div>
	</div>
</body>
</html>
