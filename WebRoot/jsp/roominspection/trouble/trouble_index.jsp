<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>工单管理</title>
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
						<td width="10%" nowrap="nowrap">机房名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="room_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">巡检人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="check_staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【工单管理】" style="width:100%;height:480px"></table>
	<div id="win_staff"></div>

	<font face="Arabic Typesetting"><script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var task_name = $("input[name='task_name']").val();
			var room_name = $("input[name='room_name']").val();
			var check_staff_name = $("input[name='check_staff_name']").val();
			
			var obj = {
				task_name : task_name,
				room_name : room_name,
				check_staff_name : check_staff_name
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "JfxjTrouble/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [{
					field : 'TASK_NAME',
					title : '任务名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'ROOM_NAME',
					title : '机房名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'IS_TEMP_STR',
					title : '填报类型',
					width : "10%",
					align : 'center'
				}, {
					field : 'CHECK_ITEM_NAME',
					title : '检查项',
					width : "15%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡检人',
					width : "10%",
					align : 'center'
				},{
					field : 'CREATE_DATE_STR',
					title : '创建时间',
					width : "15%",
					align : 'center'
				},{
					field : 'CHECK_DETAIL_ID',
					title : '操作',
					width : "15%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                     return "<a onclick='viewDetail("+value+")' style='cursor: pointer;'>详情</a>";  
                    } 
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}

		//清空查询条件
		function clearCondition(form_id) {
			$("input[name='task_name']").val('');
			$("input[name='room_name']").val('');
			$("input[name='check_staff_name']").val('');
		}
		
		function viewDetail(value)
		{
			$('#win_staff').window({
				title : "【工单详情】",
				href : webPath + "JfxjTrouble/detail.do?check_detail_id=" + value,
				width : 800,
				height : 500,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
	</script></font>
</body>
</html>