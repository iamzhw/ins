<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>

<title>新增外力点计划</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="formAdd" method="post">
			<table>
				<tr>
					<td>计划名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="job_name" id="job_name"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="start_date" id="start_date"
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
								type="text" name="end_date" id="end_date"
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
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'">
								<option value='1'>天</option>
								<option value='2'>两天</option>
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
								<option value='1'>一级</option>
								<option value='2'>二级</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>创建者：</td>
					<td>
						<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
								type="text" name="creator" id="creator" disabled="disabled" value="${staffNo}"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:left;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			
		</div>
	</div>
</body>
</html>