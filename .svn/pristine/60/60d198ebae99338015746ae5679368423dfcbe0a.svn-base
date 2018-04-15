<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>修该外力点计划</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formEdit" method="post">
			<table>
				<tr>
					<td>名称：</td>
					<td>
						<input type="hidden" name="job_id" value="${JOB_ID}"/>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="job_name" id="job_name" value="${JOB_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="start_date" id="start_date" value="${START_DATE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="end_date" id="end_date" value="${END_DATE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>计划周期：</td>
					<td>
						<div>
							<select name="circle_id" id="circle_id" class="condition-select easyui-validatebox condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" >
								<option value='1' <c:if test="${CIRCLE_ID==1}">selected</c:if>>天</option>
								<option value='2' <c:if test="${CIRCLE_ID==2}">selected</c:if>>两天</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>干线等级：</td>
					<td>
						<div>
							<select name="fiber_grade" id="fiber_grade" class="condition-select easyui-validatebox condition"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								<option value='1' <c:if test="${FIBER_GRADE==1}">selected</c:if>>一级</option>
								<option value='2' <c:if test="${FIBER_GRADE==2}">selected</c:if>>二级</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>修改者：</td>
					<td>
						<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
								type="text" name="update" id="update" disabled="disabled" value="${staffNo}"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateForm()">保存</div>
			
		</div>
	</div>
</body>
</html>