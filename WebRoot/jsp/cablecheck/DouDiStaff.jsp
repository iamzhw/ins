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
	<input name="area" value="${area}" type="hidden"/>
	<input name="son_area" value="${son_area}" type="hidden"/>
	<div id="tb_soft" style="padding: 5px; height: auto">
		<div class="condition-container" style="height: auto;">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td width="12%">角色：</td>
						<td width="20%">
							<select name="role_id" id="role_id" class="condition-select" onchange="getChoice()" >
								<option value="2">
									维护员
								</option>
								<option value="1">
									兜底岗
								</option>
							</select>
						</td>
					</tr>
					<tr class="hid">
						<td width="12%">整改结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name=complete_time id="complete_time" onClick="WdatePicker();"
									readonly="readonly" />
							</div>
						</td>
					</tr>
					<tr class="hid">
						<td width="12%">整改要求：</td>
						<td width="20%"><textarea id="reform_demand" rows="2"
								cols="20"></textarea></td>
					</tr>
					<tr>
						<td width="12%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="12%">账号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_no" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="clearCondition()"
				style="float: right;">重置</div>
			<div class="btn-operation" onClick="searchStaffNo()"
				style="float: right;">查询</div>
			<div class="btn-operation" onClick="saveTask()" style="float: right;">确定</div>
		</div>

	</div>
	<table id="dg_soft" title="【选择人员】" style="width: 100%; height: 480px">
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
			searchStaffNo();
		});
		function searchStaffNo(){
			var staff_name = $("input[name='staff_name']").val();
			var area = $("input[name='area']").val();
			var son_area = $("input[name='son_area']").val();
			var staff_no = $("input[name='staff_no']").val();
			var billIds = $("input[name='billIds']").val();
			var complete_time = $("input[name='complete_time']").val();
			var reform_demand = $("#reform_demand").val();
			var role_id = $("#role_id").val();
			var obj = {
				staff_name:staff_name,
				staff_no:staff_no,
				area:area,
				son_area:son_area,
				reform_demand:reform_demand,
				billIds:billIds,
				complete_time:complete_time,
				role_id:role_id
			};
			$('#dg_soft').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 440,
				height : 460,
				toolbar : '#tb_soft',
				url : webPath + "CableCheck/queryDouDi.do",
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
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					
				}
			});
		}
		function clearCondition() {
			$("input[name='staff_name']").val("");
			$("input[name='staff_no']").val("");
			$("input[name='complete_time']").val("");
			$("#reform_demand").val("");
		}
		
		function getChoice(){
			//获取下拉框的值
			var value=$("#role_id").val();
			if(value=='1'){
				var trs = $("tr[class='hid']"); 
				for(i = 0; i < trs.length; i++){ 
				    trs[i].style.display = "none"; //这里获取的trs[i]是DOM对象而不是jQuery对象，因此不能直接使用hide()方法 
				}	
			}
			if(value=='2'){
				var trs = $("tr[class='hid']"); 
				for(i = 0; i < trs.length; i++){ 
				    trs[i].style.display = ""; //这里获取的trs[i]是DOM对象而不是jQuery对象，因此不能直接使用hide()方法 
				}	
			}
			searchStaffNo();
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
				var complete_time = $("input[name='complete_time']").val();
				var reform_demand = $("#reform_demand").val();
			    var role_id = $("#role_id").val();
			    if(role_id=='2'){
			    	if(""==complete_time||""==reform_demand){
			    		$.messager.show({
							title : '提  示',
							msg : '整改时间或整改要求不能为空!',
							showType : 'show'
						});
						return;
			    	}
			    }
				$.ajax({
					type : 'POST',
					url : webPath + "CableCheck/updateTaskNew.do",
					dataType : 'json',
					data : {
						role_id:role_id,
						ids:billIds,
						receiver:receiver,
						complete_time:complete_time,
						reform_demand:reform_demand
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