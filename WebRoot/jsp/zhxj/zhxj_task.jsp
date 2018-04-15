<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/jsp/util/head.jsp"%>
<title>我的任务</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selectedBill" value="" />
				<table class="condition">
					<tr>
						<td width="10%" nowrap="nowrap">任务编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">任务名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="task_name" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">任务开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=start_time id="start_time" 
								onClick="WdatePicker();" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">任务结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name=complete_time id="complete_time"
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%">区域：</td>
						<td width="2s0%">
							<select name="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
							</select>
						</td>
						<td width="10%">任务类型：</td>
						<td width="20%">
							<select name="task_type" class="condition-select">
								<option value="">请选择</option>
								<c:forEach items="${types}" var="types">
									<option value="${types.TYPE_ID}">${types.TYPE_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td width="10%">任务来源：</td>
						<td width="20%">
							<select name="task_orgin" class="condition-select">
							<option value="">请选择</option>
							<option value="1">干线巡检</option>
							<option value="2">缆线巡检</option>
							<option value="3">光网助手</option>
							</select>
						</td>
					</tr>
						
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="addTask()">新增</div>
			<div id="buttion"></div>
			<c:if test="${isAreaAdmin||admin}">
			<div class="btn-operation" onClick="deleteTask()">删除</div>
			</c:if>
			<!--  
			<div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			-->
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【我的任务】" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>
	<div id="win_bill"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			//searchData();
			getSonAreaList();
		});
		function getSonAreaList() {
		var areaId='${areaId}';
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : areaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.sonAreaList;
				$("select[name='son_area'] option").remove();
				var GLY ='${GLY}';
				var LXXJ_ADMIN ='${LXXJ_ADMIN}';
				var LXXJ_ADMIN_AREA ='${LXXJ_ADMIN_AREA}';
				if (GLY == 'true' || LXXJ_ADMIN =='true' || LXXJ_ADMIN_AREA=='true')
				{
					$("select[name='son_area']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
					}
					
				}
				else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"
										+ result[i].NAME + "</option>");
						}
					}
				}
			}
		});
	}
	var buttons;
	$.ajax({
			type : 'POST',
			url : webPath + "ZhxjPlan/getButtons.do",
			dataType : 'json',
			success : function(json) {
				buttons=json;
			}
		});
		function searchData() {
			var task_no = $("input[name='task_no']").val();
			var task_name = $("input[name='task_name']").val();
			var start_time = $("input[name='start_time']").val();
			var complete_time = $("input[name='complete_time']").val();
			var son_area_id = $("select[name='son_area']").val();
			var task_type = $("select[name='task_type']").val();
			var obj = {
				task_no : task_no,
				task_name : task_name,
				task_start_time : start_time,
				task_end_time : complete_time,
				son_area_id:son_area_id,
				task_type:task_type
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "ZhxjPlan/task_query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit:true,
				singleSelect : true,
				columns : [ [ {
					field : 'TASK_ID',
					title : '任务ID',
					checkbox : true
				}, {
					field : 'TASK_NO',
					title : '任务编码',
					width : "15%",
					align : 'center'
				}, {
					field : 'TASK_NAME',
					title : '任务名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'TASK_START_TIME',
					title : '任务开始时间',
					width : "9%",
					align : 'center'
				}, {
					field : 'TASK_END_TIME',
					title : '任务结束时间',
					width : "9%",
					align : 'center'
				}, {
					field : 'TASK_INSPECT_NAME',
					title : '巡检人',
					width : "6%",
					align : 'center'
				}, {
					field : 'TASK_STATUS',
					title : '状态',
					width : "6%",
					align : 'center'
				}, {
					field : 'TYPE_NAME',
					title : '任务类型',
					width : "7%",
					align : 'center'
				} , {
					field : 'TASK_ORGIN_NAME',
					title : '任务来源',
					width : "10%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '地市',
					width : "6%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区域',
					width : "6%",
					align : 'center'
				}, {
					field : 'GRID_NAME',
					title : '网格',
					width : "6%",
					align : 'center'
				}, {
					field : 'PLAN_ID',
					title : '操作',
					width : "6%",
					align : 'center',
					formatter:function(value,rowData,rowIndex){
						if(rowData.TASK_TYPE==201||rowData.TASK_TYPE==202||rowData.TASK_TYPE==203){
							return "<a href=\"javascript:openMap(" + rowData.PLAN_ID + ");\">地图</a>";
						}else if(rowData.TASK_TYPE==204){
							return "<a href=\"javascript:showPlan(" + rowData.PLAN_ID + ");\">地图</a>";
						}else if(rowData.TASK_TYPE==205){
							return "<a href=\"javascript:billshow('info', " + rowData.TASK_ID + ");\">详情</a>"
                    		+ "&nbsp;&nbsp;<a href=\"javascript:billshow('flow', " + rowData.TASK_ID + ");\">流程</a>";  
						}
	                } 
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					$("body").resize();
				}
			});
		}
		
		function openMap(planId) {
			addTab("查看缆线任务", webPath+"CablePlan/show.do?planId="+planId);
		}
		
		function showPlan(plan_id) {
			addTab("查看外力点任务", webPath+"Care/show.do?plan_id="+plan_id);
		}
		
		function billshow(flag, billId){
			if(flag == 'info'){
				$('#win_bill').window({
					title : "【工单详情】",
					href : webPath + "Care/bill_info.do?billId=" + billId,
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			} else if(flag == 'flow'){
				$('#win_bill').window({
					title : "【流程跟踪】",
					href : webPath + "Care/bill_flow.do?billId=" + billId,
					width : 600,
					height : 300,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		/**选择行触发**/
		var buttonIds=[];
		function onClickRow(index, row) {
			var str="";
			if(buttonIds.length>0){
				for ( var i = 0; i < buttonIds.length; i++) {
					$("#"+buttonIds[i]).remove();
				}
			}
			buttonIds=[];
			$.each(buttons,function(i,item){
				if(row.TASK_TYPE==item.TASK_TYPE_ID&&item.BUTTON_ID!=undefined){	
					str+="<div class='btn-operation' id='"+item.BUTTON_ID+"' onClick='"+item.FUNCTION+"'>"+item.BUTTON_NAME+"</div>";
					buttonIds.push(item.BUTTON_ID);
				};
			});
			str.replace("undefined","");
			$(".btn-operation-container").append(str);
			//alert();
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		function clearCondition(form_id) {
			$("input[name='task_no']").val("");
			$("input[name='task_name']").val("");
			$("input[name='start_time']").val("");
			$("input[name='complete_time']").val("");
		}
		
		function deleteTask() {
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要删除任务吗?', function(r) {
					if (r) {
						var taskId;
						var taskType;
						for ( var i = 0; i < selected.length; i++) {
							taskId = selected[i].TASK_ID;
							taskType =  selected[i].TASK_TYPE;
							zhxj_id=selected[i].ZHXJ_TASK_ID;
						}
						$('#form').form('submit', {
							url : webPath + "ZhxjPlan/taskDelete.do?taskId="+taskId+"&taskType="+taskType+"&zhxj_id="+zhxj_id,
							onSubmit : function() {
								$.messager.progress();
							},
							success : function(json) {
								var rs=eval('('+json+')');
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : rs.msg,
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
		
		//缆线巡检隐患工单
		function doBill(operate){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择工单!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				var zhxj_id='';
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TASK_ID;
					arr[arr.length] = value;
					zhxj_id=selected[i].ZHXJ_TASK_ID;
				}
				//remark
				$("input[name='selectedBill']").val(arr);
				//var remark = $("input[name='remark']").val();
				if(operate == 'return' || operate == 'archive' || operate == 'audit' 
						|| operate == 'receipt' || operate == 'audit_error'){
					$.ajax({
						type : 'POST',
						url : webPath + "Care/bill_handle.do?operate=" + operate + "&ids=" + arr,
						dataType : 'json',
						data:'',
						success : function(json) {
							if(json.status){
								$.messager.show({
									title : '提  示',
									msg : '操作成功!',
									showType : 'show'
								});
								searchData();
							} else {
								$.messager.show({
									title : '提  示',
									msg : json.message,
									showType : 'show'
								});
							}
						}
					});
				} else {
					//openWindow('w','选择完成时间');
					$('#win_staff').window({
						title : "【选择人员】",
						href : webPath + "Care/bill_selectStaff.do?operate=" + operate + "&billIds=" + arr,
						width : 500,
						height : 500,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
				}
				$.ajax({
						type : 'POST',
						url : webPath + "ZhxjPlan/taskReceipt.do?operate=" + operate + "&zhxjId=" + zhxj_id,
						dataType : 'json',
						data:'',
						success : function(json) {
						}
				});
			}
		}
	</script>
</body>
</html>