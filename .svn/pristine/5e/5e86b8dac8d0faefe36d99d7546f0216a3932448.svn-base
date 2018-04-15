<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>中继段编辑</title>

<script type="text/javascript">
  
</script>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="mstwUpdate" method="post">
            <input type="hidden" id="probe_id" name="probe_id" value="${PROBE_ID}">
           
			<table>
				
				<tr>
					<td>日期 ：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id="" value="${UPLOAD_TIME}" disabled="disabled"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>填写人：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="car_no" id="car_no" value="${USER_NAME}" disabled="disabled"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>标石号：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="markstone" id="markstone" value="${MARKSTONE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>深度：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="mstw_depth" id="mstw_depth" value="${MSTW_DEPTH}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td>
						<div class="">
							
								<textarea rows="3" cols="20" id="remark" name="remark">${REMARK}</textarea>
						</div></td>
				</tr>
				
			
				
				
				

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateMstw()">保存</div>
			
		</div>
	</div>
</body>
</html>
