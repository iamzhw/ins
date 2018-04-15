<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>设备</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_task" action="" method="post">
		<input name="selected_staffId" type="hidden" />
		<input name="selected_planId" type="hidden"/>
		<input name="inspector_type" type="hidden" />
	</form>
	<table id="dg_plan" title="设备" style="width:100%;height:480px">
	</table>
	<div id="tb_staff" style="padding:5px;height:auto">
	<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<input type="hidden" id="plan_id" value="${PLAN_ID}">
				<input type="hidden" id="check_staff" value="${CHECK_STAFF}">
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="doSelect()" style="float: right;">确定</div>
			<div class="btn-operation" onClick="deleteEq()" style="float: right;">删除</div>
		</div>
	</div>
    <div id="win_staff" ></div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchEqData();
		});
		function searchEqData() {
			var plan_id = $("#plan_id").val();
			var check_staff = $("#check_staff").val();
			var obj = {
					plan_id : plan_id,
					check_staff : check_staff
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 600,
				height : 450,
				toolbar : '#tb_staff',
				url : webPath + "patrolPlan/queryEq.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 10, 20 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'ID',
					title : 'ID',
					checkbox : true
				}, {
					field : 'EQ_ID',
					title : '设备ID',
					width : "20%",
					align : 'center',
					hidden : true
				} ,{
					field : 'EQ_CODE',
					title : '设备编码',
					width : "20%",
					align : 'center'
				}, {
					field : 'EQ_NAME',
					title : '设备名称',
					width : "28%",
					align : 'center'
				}
				] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				//onClickRow:onClickRow,
				//onCheck:onCheck,
				//onSelect:onSelect,
				//onSelectAll:onSelectAll,
				onLoadSuccess : function(data) {
					<%--var rows = $("#dg_plan").datagrid("getRows"); 
					var roles = ${roles};
					alert(roles);
					for(var i=0;i<rows.length;i++){
					    var role_id = rows[i].STAFF_ID;
					    var flag = 0;
						for(var j=0;j<roles.length;j++){
							if(role_id==roles[j]){
								flag = 1;
							}
						}
						if(flag == 1){
							$('#dg_plan').datagrid('selectRow',i);
						}
					}
					--%>
				}
			});
			
		}
		
		function doSelect(){
			$('#win_staff').window("close");
			searchData();
		}
		
		function deleteEq() {
		    var selected = $('#dg_plan').datagrid('getChecked');
		    var count = selected.length;
		    if (count == 0) {
		        $.messager.show({
		            title: '提  示',
		            msg: '请选择一条记录!',
		            showType: 'show'
		        });
		        return;
		    } else {
		    	$.messager.confirm('系统提示', '您确定要删除设备吗?', function(r) {
					if(r){
						var ids;
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].ID;
							if(i==0){
								ids = value;
							}else{
								ids += ","+value;
							}
						}
						$.messager.progress();
						$.ajax({
							type : 'POST',
							url : webPath + "patrolPlan/deleteEq.do",
							data : {
								ids : ids
							},
							dataType : 'json',
							success : function(json) 
							{
								$.messager.progress('close');
								var result = json.result;
								if('success' == result){
									$.messager.show({
										title : '提  示',
										msg : '删除设备成功!',
										showType : 'show'
									});
									searchEqData();
								}else if("fail" == result){
									$.messager.show({
										title : '提  示',
										msg : '删除设备失败!',
										showType : 'show'
									});
								}
							}
						});
					}
					
				})
		       
		    }
		}

	</script>
</body>
</html>