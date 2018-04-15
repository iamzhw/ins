<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>新增代维公司</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="ff" method="post">
			<table>
				<tr>
					<td>公司名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="vcompany_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				
				<tr>
					<td>地市：</td>
					<td><select name="varea" class="condition-select condition"
						onchange="getSonAreaList('add')"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
							<c:forEach items="${areaList}" var="al" varStatus="vs">
								<option value="${al.AREA_ID}"
									<c:if test="${vs.index==0}">selected</c:if>>${al.NAME}</option>
							</c:forEach>
					</select> <script>
						$(function() {
							getSonAreaList('add');
						})
					</script></td>
				</tr>
				<tr>
					<td>区县：</td>
					<td><select name="vson_area"
						class="condition-select condition"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
					</select>
					</td>
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