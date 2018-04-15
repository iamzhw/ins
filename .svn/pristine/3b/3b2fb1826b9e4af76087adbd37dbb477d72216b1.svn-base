<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>编辑角色</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<input type="hidden" name="vrole_id" value="${role.ROLE_ID}" />
			<table>
				<tr>
					<td>角色名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vrole_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' "
								value="${role.ROLE_NAME}" />
						</div>
					</td>
				</tr>
				<tr>
					<td>角色编码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vrole_no" id="role_no" required="true"
							    missingMessage='必填项' value="${role.ROLE_NO}"
								validType="Unique_role['${role.ROLE_NO}']" />
						</div>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="vstatus" class="condition-select condition">
							<option value="0" <c:if test="${role.STATUS==0}">selected</c:if>>正常</option>
							<option value="1" <c:if test="${role.STATUS==1}">selected</c:if>>停用</option>
					</select>
					</td>
				</tr>

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 90px">
			<div class="btn-operation" onClick="updateForm()">更新</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	
</script>