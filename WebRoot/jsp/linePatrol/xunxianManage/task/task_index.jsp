
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>巡线任务管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【巡线任务管理】" style="width: 100%; height: 480px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">任务名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="param_task_name" id="param_task_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">巡线人：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="param_staff_name" id="param_staff" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">任务类型：</td>
						<td width="20%">
							<select name="param_task_type" id="param_task_type" class="condition-select">
								<option value="">全部</option>
								<option value="1">计划任务</option>
								<option value="2">临时任务</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="param_start_date" id="param_start_date" type="text" class="condition-text" onClick="WdatePicker();"/>
							</div>
						</td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="param_end_date" id="param_end_date" type="text" class="condition-text" onClick="WdatePicker();"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addTask()">新增</div>
			<div class="btn-operation" onClick="editTask()">编辑</div>
			<div class="btn-operation" onClick="stopTask()">暂停</div>
			
			<div style="float:right;" class="btn-operation"
				onClick="clearCondition('form')">重置</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<div id="win_task"></div>
	<div id="win_detail"></div>
	
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		
		function searchData() {
			
			var obj=makeParamJson('form');
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				fitColumns:true,
				toolbar : '#tb',
				url : webPath + "lineTask/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				//
				columns : [ [ {
					field : 'TASK_ID',
					title : '计划ID',
					checkbox : true
				}, {
					field : 'TASK_NAME',
					title : '任务名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'START_DATE',
					title : '开始时间',
					width : "15%",
					align : 'center'
				}, {
					field : 'END_DATE',
					title : '结束时间',
					width : "12%",
					align : 'center'
				}, {
					field : 'TASK_TYPE',
					title : '任务类型',
					width : "12%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡检人',
					width : "15%",
					align : 'center'
				},{
					field : 'STATUS',
					title : '任务状态',
					width : "10%",
					align : 'center'
				},{
					field : 'TASK_DETAIL_ID',
					title : '详情',
					width : "15%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
                     return  "<a onclick='viewOutSiteDetail("+value+")' style='cursor: pointer;color:blue;'>外力点</a>&nbsp; &nbsp; " + "<a onclick='viewDetail("+value+")' style='cursor: pointer;color:blue;'>巡线段</a>";  
                    } 
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}

		function clearCondition(form_id) {
			$("#"+form_id+"").form('reset', 'none');
		}
		
		function addTask() {
			location.href = "toAdd.do";
	}
		
		function editTask() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				var task_id = selected[0].TASK_ID;
				location.href = webPath + "lineTask/toUpdate.do?task_id=" + task_id;
			}
		}
		
		
		function stopTask() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取暂停的任务!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要暂停任务吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].TASK_ID;
							arr[arr.length] = value;
						}
						$("input[name='selected']").val(arr);
						$.ajax({async:false,
							  type:"post",
							  url : webPath + "lineTask/stopTask.do",
							  data:{selected:$("input[name='selected']").val()},
							  dataType:"json",
							  success:function(data){
								  $.messager.progress('close');
								  if(data.status){
										
										searchData();
										$.messager.show({
											title : '提  示',
											msg : '成功停止任务!',
											showType : 'show',
											timeout:'1000'//ms
										   
										});
									}else{
										alert("停止计划失败");
									}
							  }
						  });
						
					}
				});
			}
		}
		
		function viewDetail(value)
		{
			$('#win_detail').window({
				title : "【任务详情】",
				href : webPath + "lineTask/detail.do?task_id=" + value,
				width : 400,
				height : 300,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}
		
		function viewOutSiteDetail(value)
		{
			$('#win_detail').window({
				title : "【外力点检查任务详情】",
				href : webPath + "lineTask/outSiteDetail.do?task_id=" + value,
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
