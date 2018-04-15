<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>日常巡检任务详情</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">		
		<div class="condition-container" style="height:80px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="taskId" value="${taskId}" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">资源编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="equipment_code" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">资源名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="equipment_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="back()">返回</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【日常巡检任务详情】" style="width:100%;height:480px"></table>

	<script type="text/javascript">
	$(document).ready(function() {
			searchData();
		});
		
		function searchData() {
			var taskId = $("input[name='taskId']").val();
			var equipment_code = $("input[name='equipment_code']").val();
			var equipment_name = $("input[name='equipment_name']").val();
			var obj = {
					taskId : taskId,
					equipment_code : equipment_code,
					equipment_name : equipment_name
					
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "insTask/getTaskDetailList.do",
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
					field : 'EQUIPMENT_CODE',
					title : '资源编码',
					width : "10%",
					align : 'center',
					styler:function(value,rowData,rowIndex){
						return 'word-break:break-all;word-wrap:break-word';
					}
				},{
					field : 'EQUIPMENT_NAME',
					title : '资源名称',
					width : "20%",
					align : 'center'
				}, {
					field : 'RES_TYPE',
					title : '资源类别',
					width : "10%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '地址',
					width : "10%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '区域',
					width : "10%",
					align : 'center'
				}, {
					field : 'MANAGE_AREA',
					title : '电信管理区',
					width : "10%",
					align : 'center'
				}, {
					field : 'MANAGE_TYPE',
					title : '管理方式',
					width : "10%",
					align : 'center'
				}, {
					field : 'EQUP_ADDRESS',
					title : '设备位置',
					width : "10%",
					align : 'center'
				}, {
					field : 'CREATE_DATE',
					title : '创建时间',
					width : "10%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
		
		function back(){
			location.href = webPath + "insTask/index.do";
		}
	</script>
</body>
</html>