<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增版本</title>
<%@include file="../../util/head.jsp"%>
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
			<table>
				<tr>
						<td width="10%">应用名称：</td>
						<td width="20%"><select name="software_id_add" id="software_id_add" class="condition-select" required="true">
								<option value="">请选择</option>
								<c:forEach items="${softwareList}" var="sl">
									<option value="${sl.SOFTWARE_ID}">${sl.SOFTWARE_NAME}</option>
								</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td>版本号：</td>					
					<td>
					<div class="condition-text-container">
						<input name="version_no_add" id="version_no_add" type="text" 
						class="condition-text easyui-validatebox condition" 
						required="true"  missingMessage='必填项' />
							</div>
					</td>				
				</tr>
				<tr>
					<td>版本名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="version_info_add" id="version_info_add" required="true"
								 />
						</div></td>
				</tr>
				<tr>
					<td>适配尺寸：</td>
					<td>
					<select name="drivate_type_add" id="drivate_type_add" class="condition-select" required="true">
								<option value="">请选择</option>
								<option value="4.5" selected>4.5寸</option>
								<option value="5">5寸</option>
								<option value="6">6寸</option>
								<option value="7">7寸</option>
								<option value="8">8寸</option>
								<option value="9">9寸</option>
								<option value="10">10寸</option>
								<option value="11">11寸</option>
								<option value="12">12寸</option>
						</select>
                   </td>
				</tr>
				<tr>
					<td>上传文件：</td>
					<td>
					<div class="condition-text-container">
						<input type="file" name="file" id="excel"/>
					</div>
					</td>
				</tr>			
				<tr>
					<td>状态：</td>
					<td><select name="state_add" id="state_add"class="condition-select condition">
							<option value="1" selected>可用</option>
							<option value="0">不可用</option>
					</select>
					</td>
				</tr>
				<tr>
				<td>强制更新：</td>
					<td><select name="if_force_update_add"  id="if_force_update_add" class="condition-select condition">
							<option value="1" selected>强制</option>
							<option value="0">不强制</option>
					</select>
				</td>
				</tr>
				<tr>
				<td>版本说明：</td>
					<td><textarea name="comments"  id="comments" class="condition-select condition"></textarea>
				</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm();">保存</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">


</script>