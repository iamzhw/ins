<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../util/head.jsp"%>
	<title>选择任务执行人</title>
</head>
<body>
	<input name="billIds" value="${billIds}" type="hidden"/>
	<input name="operate" value="${operate}" type="hidden"/>
	<div id="tb_soft" style="padding: 5px; height: auto">
		<div class="condition-container" style="height: auto;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<c:if test="${operate == 'distributeBill' or operate == 'transmit' }">
						<tr>
							<td width="12%">整改结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=complete_time id="complete_time" onClick="WdatePicker();"
										readonly="readonly" />
								</div>
							</td>
						</tr>
						<tr>
							<td width="12%">整改要求：</td>
							<td width="20%"><textarea id="reform_demand" rows="3"
									cols="20"></textarea></td>
						</tr>
					</c:if>
					<c:if test="${operate == 'distributeZqBill' or operate == 'transmitZq' }">
						<tr>
							<td width="12%">检查结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=complete_time id="complete_time" onClick="WdatePicker();"
										readonly="readonly" />
								</div>
							</td>
						</tr>
					</c:if>

					
					<tr>
						<td width="12%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="clearCondition()"
				style="float: right;">重置</div>
			<div class="btn-operation" onClick="searchData1()"
				style="float: right;">查询</div>
			<div class="btn-operation" onClick="saveTask()" style="float: right;">确定</div>
		</div>

	</div>
	<table id="dg_soft" title="【选择人员】" style="width: 100%; height: 480px">
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData1();
		});
		function searchData1(){
			var staff_name = $("input[name='staff_name']").val();
			var operate = $("input[name='operate']").val();
			var billIds = $("input[name='billIds']").val();
			//var complete_time = $("input[name='complete_time']").val();
			var obj = {
				staff_name:staff_name,
				operate:operate,
				billIds:billIds
				//complete_time:complete_time
			};
			$('#dg_soft').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 440,
				height : 460,
				toolbar : '#tb_soft',
				url : webPath + "CableCheck/queryHandler.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20,30 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '用户ID',
					checkbox : true
				},  {
					field : 'STAFF_NO',
					title : '工号',
					width : "180",
					align : 'center'
				},  {
					field : 'STAFF_NAME',
					title : '姓名',
					width : "200",
					align : 'center'
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					
				}
			});
		}
		function clearCondition() {
			$("input[name='staff_name']").val("");
		}
		function saveTask(){
			var selected = $('#dg_soft').datagrid('getChecked');
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
				var receiver = selected[0].STAFF_ID;
				var billIds = $("input[name='billIds']").val();
				var operate = $("input[name='operate']").val();
				var complete_time = $("input[name='complete_time']").val();
				var reform_demand = $("#reform_demand").val();
				$.ajax({
					type : 'POST',
					//url : webPath + "CableCheck/updateTask.do?operate=" + operate + "&ids=" + billIds + "&receiver=" + receiver + "&complete_time=" + complete_time+"&reform_demand="+reform_demand,
					url : webPath + "CableCheck/updateTask.do",
					dataType : 'json',
					data : {
						operate : operate,
						ids : billIds,
						receiver : receiver,
						complete_time : complete_time,
						reform_demand : reform_demand
					},
					success : function(json) {
						if(json.status){
							$.messager.show({
								title : '提  示',
								msg : '操作成功!',
								showType : 'show'
							});
						} else {
							$.messager.show({
								title : '提  示',
								msg : json.message,
								showType : 'show'
							});
						}
						$('#win_staff').window('close');
						searchData();
					}
				});
			}
		}
	</script>
</body>
</html>