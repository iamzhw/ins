<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>编辑班组</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<input type="hidden" name="vclass_id" value="${class.CLASS_ID}" />
			<table>
				<tr>
					<td>班组名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vclass_name" value="${class.CLASS_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>地市：</td>
					<td><select name="varea" class="condition-select condition"
						onchange="getSonAreaList('add')"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" 
						<c:if test="${ifGly=='0'}">disabled</c:if>>							
							<c:forEach items="${areaList}" var="al">
								<option
									<c:if test="${al.AREA_ID==class.AREA_ID}">selected</c:if>
									value="${al.AREA_ID}">${al.NAME}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>区县：</td>
					<td><select name="vson_area"
						class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
							<c:forEach items="${sonAreaList}" var="al">
								<option value="${al.AREA_ID}"
									<c:if test="${al.AREA_ID==class.SON_AREA_ID}">selected</c:if>>${al.NAME}</option>
							</c:forEach>
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
