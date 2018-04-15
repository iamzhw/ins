<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>步巡任务管理</title>
</head>

<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<c:if test="${isAdmin==0}">
						  <td width="10%">地区：</td>
							<td width="20%">
								<div>
									<select name="area_id" id="area_id" class="condition-select">
										<option value=''>--请选择--</option>
										<c:forEach items="${areaList}" var="res">
											<option value='${res.AREA_ID}'>${res.NAME}</option>
										</c:forEach>
									</select>
								</div>
							</td>
						</c:if>
						<td width="10%">任务名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_name" id="task_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">巡线人：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input name="inspect_name" id="inspect_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">周期：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input name="circle" id="circle" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_date" id="start_date" type="text" class="condition-text" onClick="WdatePicker();"/>
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_date" id="end_date" type="text" class="condition-text" onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【步巡段管理】" style="width: 100%; height: 480px">
	</table>
	<div id="win_part"></div>

	<script type="text/javascript">
		$(document).ready(function() {
// 			searchData();
		});
		
		function searchData() {
			
			if('${isAdmin}'=='0'){
				if($("#area_id").val()==''){
					$.messager.alert("提示","地区不能为空");
					return false;
				}
			}
			var obj=makeParamJson('form');
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "StepPartTask/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : true,
				//
				columns : [ [ {
					field : 'TASK_ID',
					title : '步巡任务ID',
					checkbox : true
				}, {
					field : 'TASK_NAME',
					title : '步巡任务名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'START_TIME',
					title : '开始时间',
					width : "20%",
					align : 'center'
				}, {
					field : 'END_TIME',
					title : '结束时间',
					width : "20%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡检人员',
					width : "15%",
					align : 'center'
				},{
					field : 'CIRCLE_ID',title : '周期',width : "10%",align : 'center',
					formatter: function(value,row,index){
						return "<p>"+row.CIRCLE_ID+"个月</p>";
					}
				},{
					field : 'OPERATION',title : '操作',width : "10%",align : 'center',
					formatter: function(value,row,index){
					    return "<a href='javascript:void(0)' onclick='showStepPart("+row.TASK_ID+")'>步巡段</a>";
					}
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
// 				onClickRow:onClickRow,
// 				onCheck:onCheck,
// 				onSelect:onSelect,
// 				onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
       			// $(this).datagrid("fixRownumber");
       			 
				
				}	
			});
		}
		
		function clearCondition(form_id) {
			
			$("#"+form_id+"").form('reset', 'none');
			
		}
		
		function showStepPart(task_id)
		{
			$('#win_part').window({
				title : "【步巡段】",
				href : webPath + "StepPartTask/selStepPartByTaskID.do?task_id=" + task_id,
				width : 400,
				height : 300,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
	</script>
</body>
</html>