<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>选择任务执行人</title>
</head>
<body>
<input name="billIds" value="${billIds}" type="hidden"/>
<input name="operate" value="${operate}" type="hidden"/>
	<table id="dg_soft" title="【选择人员】" style="width: 100%; height: 500px">
	</table>
	<div id="tb_soft" style="padding: 5px; height: auto">
	<div class="condition-container" style="height: 100px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
				<c:if test="${operate == 'distributeBill'}">
							<tr>
								<td width="10%">结束时间：</td>
								<td width="20%">
									<div class="condition-text-container">
									<input class="condition-text condition"
										type="text" name=complete_time id="complete_time" 
										onClick="WdatePicker();" readonly="readonly"/>
									</div>
								</td>
								<td width="70px">网格：</td>
						<td width="100px">
							<div class="condition-text-container">
								<input name="grid_name" type="text" class="condition-text" />
							</div>
						</td>
							</tr>
						</c:if>
					<tr>
						<td width="70px">姓名：</td>
						<td width="130px">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
						
					<c:if test="${operate == 'forward'}">
						<td width="70px">区县：</td>
						<td width="130px">
							<select name="son_area" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${sonAreaList}" var="area">
									<option value="${area.AREA_ID }">${area.NAME} </option>
								</c:forEach>
							</select>
						</td>
					</c:if>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="clearCondition()">重置</div>
			<div class="btn-operation" onClick="searchData1()">查询</div>
			<div class="btn-operation" onClick="saveTask()" style="float: right;">确定</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData1();
		});
		function searchData1(){
			var staff_name = $("input[name='staff_name']").val();
			var son_area = $("select[name='son_area']").val();
			var operate = $("input[name='operate']").val();
			var billIds = $("input[name='billIds']").val();
			var grid_name = $("input[name='grid_name']").val();
			var obj = {
				staff_name:staff_name,
				grid_name:grid_name,
				son_area : son_area,
				operate:operate,
				billIds:billIds,
				type:'1'
			};
			$('#dg_soft').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 440,
				height : 460,
				toolbar : '#tb_soft',
				url : webPath + "Staff/queryHandler.do",
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
				}, {
					field : 'STAFF_NO',
					title : '登录名',
					width : "8%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '姓名',
					width : "8%",
					align : 'center'
				}, {
					field : 'GRID_NAME',
					title : '网格',
					width : "8%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '区县',
					width : "7%",
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
			$("select[name='son_area']").val("");
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
				var name = selected[0].STAFF_NAME;
				var billIds = $("input[name='billIds']").val();
				var operate = $("input[name='operate']").val();
				var complete_time = $("input[name='complete_time']").val();
				$.ajax({
					type : 'GET',
					url : webPath + "Care/bill_handle.do?operate=" + operate + "&ids=" + billIds + "&receiver=" + receiver + "&complete_time=" + complete_time+"&staff_name="+encodeURI(encodeURI(name)),
					dataType : 'json',
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