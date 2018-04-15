<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>任务详情</title>
</head>

<body style="padding:3px;border:0px">
	
	<div id="tb_detail" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form_detail" action="" method="post">
				<input type="hidden" name="plan_id" value="${plan_id}" />
				<input type="hidden" name="arr_selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">看护日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input  class="condition-text easyui-validatebox condition"
								type="text" name="kan_time" id="kan_time"
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});"/>
							</div>
						</td>
						<td width="10%" nowrap="nowrap">执行结果：</td>
						<td width="20%">
							<select name="state_detail" class="condition-select">
								<option value="">
									全部
								</option>
								<option value="0">工作</option>
								<option value="1">休息</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation" onClick="stop_detail_plan()">休息</div>
		<div class="btn-operation" onClick="start_detail_plan()">工作</div>
		<div class="btn-operation" onClick="look_shijian()">查看看护时间</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation" onClick="searchData_detail()">查询</div>
			<div style="float:right;" class="btn-operation" onClick="history.back()">返回</div>
		</div>
	</div>
	<div id="win_staff_detail"></div>
	<div id="win_look_shijian"></div>
	<table id="dg_detail" title="【任务详情】" style="width:100%;height:480px">
	</table>
	<font face="Arabic Typesetting"><script type="text/javascript">
		$(document).ready(function() {
			searchData_detail();
		});
		
		function searchData_detail() {
			var kan_time = $("input[name='kan_time']").val();
			var plan_id = $("input[name='plan_id']").val();
			var state_detail = $("select[name='state_detail']").val();
			var obj = {
				kan_time : kan_time,
				plan_id : plan_id,
				state_detail : state_detail
			};
			$('#dg_detail').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb_detail',
				url : webPath + "outsitePlanManage/query_detail_plan_data.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'PLAN_ID',
					title : '计划ID',
					width : "15%",
					align : 'center',
					checkbox : true
				},{
					field : 'PLAN_DATE',
					title : '看护日期',
					width : "20%",
					align : 'center'
				}, {
					field : 'SITE_NAME',
					title : '外力点',
					width : "30%",
					align : 'center'
				}, {
					field : 'UNAME',
					title : '看护员',
					width : "15%",
					align : 'center'
				},{
					field : 'STATUS',
					title : '执行状态',
					width : "20%",
					align : 'center',
					formatter : function(value, rowData, index){
								if(value == "1"){
									return "工作";
								}else {
									return "休息";
								}
							}
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}
//暂停flag=0或者启动flag=1
function stop_detail_plan(){
		var selected = $('#dg_detail').datagrid('getChecked');
		var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			}else {
				
				var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].PLAN_DATE;
							arr[arr.length] = value;
						}
			}
			$("input[name='arr_selected']").val(arr);
					
					$.ajax({
							type : 'POST',
							url : webPath + "outsitePlanManage/start_detail_plan.do?flag=0&plan_id="+$("input[name='plan_id']").val(),
							data : {time_detail:$("input[name='arr_selected']").val()},
							dataType : 'json',
							success : function(json) {
								
									$.messager.show({
										title : '提  示',
										msg : '暂停执行成功！',
										showType : 'show',
										timeout:'2000'//ms
									});
								
								searchData_detail();
							}
						});
		
}
//暂停flag=0或者启动flag=1
function start_detail_plan(){
		var selected = $('#dg_detail').datagrid('getChecked');
		var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			}else {
				
				var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].PLAN_DATE;
							arr[arr.length] = value;
						}
			}
			$("input[name='arr_selected']").val(arr);
					//var time_detail = selected[0].TIME_DETAIL;
					var now_state = selected[0].STATE;
					//if(now_state==0){
					//	$.messager.show({
					//		title : '提  示',
					//		msg : '该计划已经启动!',
					//		showType : 'show',
					// 		timeout:'2000'//ms
					//	});
					//	return;
					//}
					$.ajax({
							type : 'POST',
							url : webPath + "outsitePlanManage/start_detail_plan.do?flag=1&plan_id="+$("input[name='plan_id']").val(),
							data : {time_detail:$("input[name='arr_selected']").val()},
							dataType : 'json',
							success : function(json) {
								 
									$.messager.show({
										title : '提  示',
										msg : '启动执行成功！',
										showType : 'show',
										timeout:'2000'//ms
									   
									});
								
								searchData_detail();
							}
						});
		
}
function look_shijian() {
			$('#win_look_shijian').window(
							{
								title : "【计划看护时间】",
								href : webPath
										+ "outsitePlanManage/get_shijian.do",
								width : 500,
								height : 300,
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