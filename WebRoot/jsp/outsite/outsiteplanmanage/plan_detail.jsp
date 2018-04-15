<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../util/head.jsp"%>
</head>
<body >
	
	<div >
	<h4 style="color:#0099FF;">看护时间段：</h4>
   	<table id="fragment_dg" title="看护时间段" ></table>
	</div>
	<div >
	<h4 style="color:#0099FF;">任务列表：</h4>
	<table id="tasklist_dg" title="任务列表" ></table>
	</div>
<script type="text/javascript">
		$(document).ready(function(){
		searchTimeFragment();
		});
		
		function searchTasklist() {
			var PLAN_ID =$("#PLAN_ID").val();
			$('#tasklist_dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				url : webPath + "outsitePlanManage/queryPlans.do",
				queryParams : {PLAN_ID:PLAN_ID},
				method : 'post',
				autoRowHeight:true,
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				//
				columns : [ [ {
					field : 'PLAN_ID2',
					title : '计划ID2',
					checkbox : true
				},{
					field : 'PLAN_ID',
					title : '计划ID',
					width : '12%',
					align : 'center'
				},{
					field : 'START_DATE',
					title : '开始时间',
					width : '12%',
					align : 'center'
				}, {
					field : 'END_DATE',
					title : '结束时间',
					width : "12%",
					align : 'center'
				}, {
					field : 'USER_ID',
					title : '看护人ID',
					width : "12%",
					align : 'center'
				},{
					field : 'STAFF_NAME',
					title : '看护人',
					width : "12%",
					align : 'center'
				},{
					field : 'OUT_SITE_ID',
					title : '外力点ID',
					width : "10%",
					align : 'center'
				},{
					field : 'SITE_NAME',
					title : '外力点',
					width : "10%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
				
				
			});
		}
		
	function searchTimeFragment() {
			var PLAN_ID =${PLAN_ID};
			$('#fragment_dg').datagrid({
				autoSize : true,
				url : webPath + "outsitePlanManage/queryTimeFregment.do",
				queryParams : {PLAN_ID:PLAN_ID},
				method : 'post',
				autoRowHeight:true,
				pagination : false,
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'PLAN_TIME_ID',
					title : 'ID',
					width : '12%',
					align : 'center'
				},{
					field : 'PLAN_ID',
					title : '计划ID',
					width : '12%',
					align : 'center'
				},{
					field : 'PART_TIME_ID',
					title : '时间模板ID',
					width : "12%",
					align : 'center'
				},{
					field : 'START_TIME',
					title : '开始时间',
					width : '12%',
					align : 'center'
				}, {
					field : 'END_TIME',
					title : '结束时间',
					width : "12%",
					align : 'center'
				}, {
					field : 'CREATION_TIME',
					title : '创建时间',
					width : "12%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
       			 
				
				}
				
				
			});
		}
</script>
</body>
</html>