<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>任务详情</title>
</head>

<body style="padding:3px;border:0px">
	<table id="dg" title="【任务详情】" style="width:100%;height:480px">
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="task_id" value="${taskId}" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">机房名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="room_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">执行结果：</td>
						<td width="20%">
							<select name="status" class="condition-select">
								<option value="">
									全部
								</option>
								<option value="0">未执行</option>
								<option value="1">已执行</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
			<div style="float:right;" class="btn-operation" onClick="history.back()">返回</div>
		</div>
	</div>
	<div id="win_staff"></div>

	<font face="Arabic Typesetting"><script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var room_name = $("input[name='room_name']").val();
			var task_id = $("input[name='task_id']").val();
			var status = $("select[name='status']").val();
			var obj = {
				room_name : room_name,
				task_id : task_id,
				status : status
				
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "JfxjTask/queryDetailTask.do",
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
					field : 'ROOM_NAME',
					title : '机房名称',
					width : "40%",
					align : 'center'
				}, {
					field : 'ROOM_TYPE_NAME',
					title : '机房类型',
					width : "20%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡检人',
					width : "20%",
					align : 'center'
				},{
					field : 'STATUS',
					title : '执行状态',
					width : "20%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
		
	</script></font>
</body>
</html>