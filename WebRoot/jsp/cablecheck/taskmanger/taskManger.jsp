
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>任务管理</title>
</head>
<script type="text/javascript">

	$(document).ready(function() {
		searchData();
	});
	function searchData() {
		var task_name = $("#task_name").val();
		var task_type = $("#task_type").val();
		var task_start_time = $("#task_start_time").val();
		var task_end_time = $("#task_end_time").val();
		var finsh_start_time = $("#finsh_start_time").val();
		var finsh_end_time = $("#finsh_end_time").val();
		var if_finshed = $("#if_finshed").val();
		var if_filed = $("#if_filed").val();
		var obj = {
			task_name : task_name,
			task_type : task_type,
			task_start_time : task_start_time,
			task_end_time : task_end_time,
			finsh_start_time : finsh_start_time,
			finsh_end_time : finsh_end_time,
			if_finshed : if_finshed,
			if_filed : if_filed
		};
		$('#dg').datagrid(
				{
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					toolbar : '#tb',
					url : webPath + "CableTaskManger/query.do",
					queryParams : obj,
					method : 'post',
					pagination : true,
					pageNumber : 1,
					pageSize : 10,
					pageList : [ 20, 50 ],
					//loadMsg:'数据加载中.....',
					rownumbers : true,
					singleSelect : false,
					columns : [ [
							{
								field : 'TASK_ID',
								title : '任务ID',
								rowspan : '2',
								checkbox : true
							},
							{
								field : 'TASK_NAME',
								title : '任务名称',
								width : "20%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'TASK_TYPE',
								title : '任务类型',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'INSPECTOR',
								title : '检查员',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'CREATE_TIME',
								title : '派发时间',
								width : "13%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'COMPLETE_TIME',
								title : '任务截止时间',
								width : "13%",
								rowspan : '2',
								align : 'center'
							},{
								field : 'ACTUAL_COMPLETE_TIME',
								title : '任务实际完成时间',
								width : "12%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'IFFINSHED',
								title : '是否完成',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'PERCENT',
								title : '完成比例',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'TASKID',
								title : '操作',
								width : "10%",
								rowspan : '2',
								align : 'center'/* ,
								formatter : function(value, row, Index) {
									return "<a href=\"javascript:show("
											+ row.TASK_ID + ");\">详情</a>";
								} */
							} ] ],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$(this).datagrid("fixRownumber");
					}
				});
	}

	function clearForm() {

		$("#task_name").val('');
		$("#task_type").val('');
		$("#task_start_time").val('');
		$("#task_end_time").val('');
		$("#finsh_start_time").val('');
		$("#finsh_end_time").val('');
		$("#if_finshed").val('');
		$("#if_filed").val('');

	}

	function addTask(){
		addTab("新增任务", "<%=path%>/CableTaskManger/addTask.do");
	}
</script>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 100px;">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%" nowrap="nowrap">
								任务派发开始时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name="TASK_START_TIME" id="task_start_time" onClick="WdatePicker();" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">
								任务派发结束时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name="TASK_END_TIME" id="task_end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>
							<td align="left" width="15%">
								任务名称：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="TASK_NAME" id="task_name" class="condition-text" />
								</div>
							</td>
						</tr>
						<tr>
							<td width="10%" nowrap="nowrap">
								要求完成开始时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name="FINSH_START_TIME" id="finsh_start_time" onClick="WdatePicker();" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">
								要求完成结束时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name="FINSH_END_TIME" id="finsh_end_time"
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'finsh_start_time\')}'});" />
								</div>
							</td>
							<td width="10%">
								任务类型：
							</td>
							<td width="20%">
								<select name="TASK_TYPE" id="task_type" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">
										周期性任务
									</option>
									<option value="1">
										隐患任务
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="10%">
								是否完成：
							</td>
							<td width="20%">
								<select name="IF_FINSHED" id="if_finshed" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">
										是
									</option>
									<option value="1">
										否
									</option>
								</select>
							</td>
							<td width="10%">
								是否归档：
							</td>
							<td width="20%">
								<select name="IF_FILED" id="if_filed" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">
										是
									</option>
									<option value="1">
										否
									</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="addTask()">
					新增任务
				</div>
				<div class="btn-operation" onClick="doTask('audit')">
					归档任务
				</div>

				<div class="btn-operation" onClick="doTask('receipt')">
					删除任务
				</div>
				<div style="float:right;" class="btn-operation" onClick="searchData()">
					查询
				</div>
				<div style="float:right;"  class="btn-operation" onClick="clearForm()">
					重置
				</div>
			</div>
		</div>
		<table id="dg" title="【任务管理】" style="width: 100%; height: 480px"></table>
	</body>
</html>
