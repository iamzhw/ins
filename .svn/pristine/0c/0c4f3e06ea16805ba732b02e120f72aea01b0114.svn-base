<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡检计划</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<form id="form" action="" method="post">
			<div class="btn-operation-container">
				<input id="start_time" type="hidden" value="${start_time}"/>
				<input id="end_time" type="hidden" value="${end_time}"/>
				<input id="area" type="hidden" value="${area}"/>
				<input id="type" type="hidden" value="${type}"/>
				<input id="order_type" type="hidden" value="${order_type}"/>
				<input id="teamId" type="hidden" value="${teamId}"/>
				<input id="check_start_time" type="hidden" value="${check_start_time}"/>
				<input id="check_end_time" type="hidden" value="${check_end_time}"/>
			</div>
		</form>
		<div class="btn-operation-container">
			<div style="float: right;" class="btn-operation"
					onClick="exportExcel()">导出</div>
			</div>
	</div>
	<table id="dg" title="工单列表" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>
	<div id="win_Plan"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
	
		function searchData() {
			var obj = {
				startDate:$("#start_time").val(),
				endDate:$("#end_time").val(),
				sonAreaId:$("#area").val(),
				type:$("#type").val(),
				order_type:$("#order_type").val(),
				team_id:$("#teamId").val(),
				checkStartDate:$("#check_start_time").val(),
				checkEndDate:$("#check_end_time").val()
			};
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "cableCheckStatictis/showStatictisOrder.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				/* fit:true, */
				singleSelect : true,
				columns : [ [{
					field : 'AREANAME',
					title : '地市',
					width : "10%",
					align : 'center'
				}, {
					field : 'SONAREANAME',
					title : '区县',
					width : "10%",
					align : 'center'
				}, {
					field : 'TEAMNAME',
					title : '班组',
					width : "15%",
					align : 'center'
				}, {
					field : 'ORDER_NO',
					title : '工单编号',
					width : "15%",
					align : 'center'
				}, {
					field : 'OPT_CODE',
					title : '设备编号',
					width : "15%",
					align : 'center'
				}, {
					field : 'PHY_PORT_SPEC_NO',
					title : '端子编号',
					width : "15%",
					align : 'center'
				}, {
					field : 'ORDER_CHECK_TIME',
					title : '检查时间',
					width : "15%",
					align : 'center'
				}] ],
				//前面的checkBox列
				/*frozenColumns:[[  
				 	 {field:'PLAN_ID',checkbox:true}  
			    ]],  */
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$("body").resize();
				},
				onClickRow: function(rowIndex, rowData){
					 
		         }
			});
		}
		
		
		function exportExcel() {
			
			var startDate = $("#start_time").val(),
				endDate = $("#end_time").val(),
				sonAreaId = $("#area").val(),
				type = $("#type").val(),
				order_type = $("#order_type").val(),
				team_id = $("#teamId").val();
			
			
			window.open(webPath
					+ "cableCheckStatictis/exportExcelByDetail.do?randomPara=1 "
					+ "&startDate="+startDate+"&endDate="+endDate
					+ "&order_type="+order_type+"&team_id="+team_id
					+ "&sonAreaId="+sonAreaId+"&type="+type+"&page=1&rows=9999999");
		}
		
	</script>
</body>
</html>