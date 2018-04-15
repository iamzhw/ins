<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>新增角色</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>角色名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vrole_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>角色编号：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vrole_no" id="role_no" required="true"
								validType="Unique_role['']" missingMessage='必填项' />
						</div>
					</td>

				</tr>
				<tr>
					<td>状态：</td>
					<td><select name="vstatus" class="condition-select condition">
							<option value="0" selected>正常</option>
							<option value="1">停用</option>
					</select></td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="clearForm()">清空</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	
</script>