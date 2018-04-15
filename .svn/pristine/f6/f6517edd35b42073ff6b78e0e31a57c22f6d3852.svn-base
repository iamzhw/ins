<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>编辑代维公司</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<td width="10px">地市：</td>
					<td width="200px">
						<select name="AREA_ID_EDIT" id="AREA_ID_EDIT" class="condition-select">
							<option value="">
								请选择
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>公司姓名：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" 
							value="${comName}"
							type="text" name="edit_company_name" 
							data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
					<td><input name="com_id" hidden="true" value="${comId}"/></td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="closeForm()">取消</div>
		</div>
	</div>
</body>
</html>