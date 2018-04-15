<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>日常巡检任务管理</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height:80px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="count" value="" />
				<input type="hidden" name="type" value="" />
				<input type="hidden" name="taskIds" value="" />
				<table class="condition">
					<tr>
						<td align="left" width="15%">
							任务ID：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="taskId" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							任务名称：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="taskCode" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							资源编码：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="equCode" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							巡检公司：
						</td>
						<td align="left" width="25%">
							<select name="ownCompany" id="ownCompany" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${companyList}" var="v">
								<option value="${v.COMPANY_NAME}">${v.COMPANY_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="15%">
							巡检人员工号：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="staffNo" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							巡检人员名称：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="staffName" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							任务状态：
						</td>
						<td>
							<select name="stateId" id="stateId" class="condition-select">
								<option value="">----------请选择----------</option>
									<option value="0">已完成</option>
									<option value="1">进行中</option>
								<option value="2">未开始</option>
							</select>
						</td>
						<td align="left" width="15%">
							地区：
						</td>
						<td width="25%" align="left">
							<select name="areaName" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${areaList}" var="v">
								<option value="${v.NAME}">${v.NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="btnallot()">任务分配</div>
			<div class="btn-operation" onClick="btAdjust()">任务调整</div>
			<div class="btn-operation" onClick="deleteTask()">删除</div>
				<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【日常巡检任务管理】" style="width:100%;height:480px"></table>
	<div id="win_equip"></div>

	<script type="text/javascript">
	$(document).ready(function() {
			//searchData();
		});
		
		function searchData() {
			var taskId = $("input[name='taskId']").val();
			var taskCode = $("input[name='taskCode']").val();
			var equCode = $("input[name='equCode']").val();
			var ownCompany = $("select[name='ownCompany']").val();
			var staffNo = $("input[name='staffNo']").val();
			var staffName = $("input[name='staffName']").val();
			var stateId = $("select[name='stateId']").val();
			var areaName = $("select[name='areaName']").val();
			
			
			var obj = {
					taskId : taskId,
					taskCode : taskCode,
					equCode : equCode,
					ownCompany : ownCompany,
					staffNo : staffNo,
					staffName : staffName,
					stateId : stateId,
					areaName : areaName
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "insTask/query.do",
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
					field : 'STATE',
					title : '状态',
					checkbox : true
				},{
					field : 'TASK_ID',
					title : '任务ID',
					width : "10%",
					align : 'center'
				},{
					field : 'TASK_CODE',
					title : '任务编码',
					width : "15%",
					align : 'center'
				}, {
					field : 'COUNT',
					title : '资源数量',
					width : "7%",
					align : 'center'
				}, {
					field : 'CYCLE',
					title : '周期',
					width : "10%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '执行人',
					width : "10%",
					align : 'center'
				}, {
					field : 'OWN_COMPANY',
					title : '巡检公司',
					width : "10%",
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
					field : 'TASKSTATE',
					title : '状态',
					width : "10%",
					align : 'center'
				}, {
					field : 'DETAIL_TASK_ID',
					title : '资源详情',
					width : "8%",
					align : 'center',
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

		//清空查询条件
		function clearCondition(form_id) {
			$("input[name='check_item_name']").val("");
			$("select[name='room_type_id']").val("");
		}
		
		function btnallot(){
			addTab('任务分配',webPath+'/insTask/addTaskIndex.do');
		}
		
		function btAdjust(){
			var selected = $("#dg").datagrid("getChecked");
			var count = selected.length;
			if(count==0){
				$.messager.show({
					title : '提  示',
					msg : '请选择需要调整的任务!',
					showType : 'show'
				});
				return;
			}else if(count>1){
				$.messager.show({
					title : '提  示',
					msg : '一次只能选择一条任务!',
					showType : 'show'
				});
				return;
			}
			else{
				var state = selected[0].STATE;
				var taskId = selected[0].TASK_ID;
				$('#win_equip').window({
					title : "【任务调整】",
					href : webPath + "insTask/modifyTaskIndex.do?state=" + state + "&taskId=" + taskId,
					width : 950,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		function deleteTask(){
			var selected = $("#dg").datagrid("getChecked");
			var count = selected.length;
			if(count==0){
				$.messager.show({
					title : '提  示',
					msg : '请选择需要删除的任务!',
					showType : 'show'
				});
				return;
			}else{
				$.messager.confirm("系统提示","你确定要删除任务吗",function(r){
					if(r){
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].TASK_ID;
							arr[arr.length] = value;
						}
						$("input[name='taskIds']").val(arr);
						$("#form").form('submit',{
							url: webPath + "insTask/deleteTask.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功删除任务!',
									showType : 'show'
								});
							}	
						});
					}
				});
			}
		}

		function detailTask(value)
		{
			location.href = webPath + "insTask/detailTaskIndex.do?taskId=" + value;
		}
	
	</script>
</body>
</html>