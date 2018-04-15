<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>任务管理</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">任务名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">机房类型：</td>
						<td width="20%">
							<select name="room_type_id" class="condition-select">
								<option value="">
									全部
								</option>
								<c:forEach items="${roomTypes}" var="al">
									<option value="${al.ROOM_TYPE_ID}">
										${al.ROOM_TYPE_NAME}
									</option>
								</c:forEach>
							</select>
						</td>
						<td width="10%" nowrap="nowrap">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_date" type="text" class="condition-text" onClick="WdatePicker();"/>
							</div>
						</td>
						<td width="10%" nowrap="nowrap">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_date" type="text" class="condition-text" onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【任务管理】" style="width:100%;height:480px"></table>
	<div id="win_staff"></div>

	<font face="Arabic Typesetting"><script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var task_name = $("input[name='task_name']").val();
			var room_type_id = $("select[name='room_type_id']").val();
			var start_date = $("input[name='start_date']").val();
			var end_date = $("input[name='end_date']").val();
			var obj = {
				task_name : task_name,
				room_type_id : room_type_id,
				start_date : start_date,
				end_date : end_date
				
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "JfxjTask/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'TASK_NAME',
					title : '任务名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '负责人',
					width : "10%",
					align : 'center'
				}, {
					field : 'JOB_NAME',
					title : '计划名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'START_DATE',
					title : '开始时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'END_DATE',
					title : '结束时间',
					width : "10%",
					align : 'center'
				}, {
					field : 'ROOM_TYPE_NAME',
					title : '机房类型',
					width : "10%",
					align : 'center'
				},{
					field : 'CREATE_DATE',
					title : '创建时间',
					width : "10%",
					align : 'center'
				},{field : 'TASK_ID',
					title : '操作',
					width: "10%",
					formatter:function(value,rowData,rowIndex){
                     return "<a onclick='detailTask("+value+")' style='cursor: pointer;'>详情</a>";  
                    } 
					
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
		
				
		function detailTask(value)
		{
			location.href = webPath + "JfxjTask/detailTask.do?task_id=" + value;
		}

		//清空查询条件
		function clearCondition(form_id) {
			$("input[name='task_name']").val("");
			$("select[name='room_type_id']").val("");
			$("input[name='start_date']").val("");
			$("input[name='end_date']").val("");
		}
		
	</script></font>
</body>
</html>