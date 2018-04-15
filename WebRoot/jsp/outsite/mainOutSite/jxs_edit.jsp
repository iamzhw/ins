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
		<form id="jxsUpdate" method="post">
            <input type="hidden" id="operator_id" name="operator_id" value="${OPERATOR_ID}">
           
			<table>
				
				<tr>
					<td>姓名：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="operator_name" id="operator_name" value="${OPERATOR_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>车牌号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="car_no" id="car_no" value="${CAR_NO}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>车辆类型：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="car_type" id="car_type" value="${CAR_TYPE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>手机号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="mobile" id="mobile" value="${MOBILE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>小灵通号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="tel1" id="tel1" value="${TEL1}"
								/>
						</div></td>
				</tr>
				<tr>
					<td>固定电话号码：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="tel2" id="tel2" value="${TEL2}"
								/>
						</div></td>
				</tr>
				<tr>
					<td>外力影响信息：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="info" id="info" value="${INFO}"
								/>
						</div></td>
				</tr>
				<tr>
					<td>填写时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="" id="" value="${UPLOAD_TIME}" readonly="readonly"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
				<tr>
					<td>填写人员：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition" readonly="readonly"
								type="text" name="user_name" id="user_name" value="${USER_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
				</tr>
			
				
				
				

			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateJxs()">保存</div>
			
		</div>
	</div>
</body>
</html>
