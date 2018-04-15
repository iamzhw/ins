<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>修该迁移日志</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="form_update" method="post">
			<table>
				<tr>
					<td>外力点名称：</td>
					<td>
						<div class="condition-text-container">
							<input type="hidden" id="move_id" name="move_id" value="${MOVE_ID}" />
							<input type="hidden" id="out_site_id" name="out_site_id" value="${OUT_SITE_ID}" />
							<input class="condition-text easyui-validatebox condition"
								type="text" name="outsite_name" id="outsite_name" value="${SITE_NAME}"
								disabled="disabled" />
						</div>
					</td>
				</tr>
				
				
				<tr>
					<td>原经度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="old_longitude" id="old_longitude" value="${OLD_LONGITUDE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				
				<tr>
					<td>原纬度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="old_latitude" id="old_latitude" value="${OLD_LATITUDE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>现经度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="new_longitude" id="new_longitude" value="${NEW_LONGITUDE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>现纬度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="new_latitude" id="new_latitude" value="${NEW_LATITUDE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm_update_movelog()">保存</div>
			
		</div>
	</div>
<script type="text/javascript">
		$(document).ready(function() {
			
		});
</script>
</body>
</html>