<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>任务分派</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_task" action="" method="post">
		<input name="selected_staffId" type="hidden" />
		<input name="selected_planId" type="hidden"/>
		<input name="inspector_type" type="hidden" />
	</form>
	<table id="dg_plan" title="【任务分派】" style="width:100%;height:480px">
	</table>
	<div id="tb_plan" style="padding:5px;height:auto">
	<div class="condition-container">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
					<td width="10%">帐号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="queryStaffby()" >查询</div>
			<div class="btn-operation" onClick="saveTask()">分配</div>
		</div>
	</div>
<div id="win_staff" ></div>
	<script type="text/javascript">
		$(document).ready(function() {
			queryStaff();
		});
		function queryStaffby(){
			queryStaff();
		}
		function queryStaff() {
			var staff_name = $("input[name='staff_name']").val().trim();
			var staff_no = $("input[name='staff_no']").val().trim();
			var obj = {
				staff_name : staff_name,
				staff_no : staff_no
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 500,
				height : 500,
				toolbar : '#tb_plan',
				url : webPath + "patrolPlan/query_staff.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '员工编码',
					width : "11.5%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "10%",
					align : 'center'
				},
				{
					field : 'STAFF_TYPE_NAME',
					title : '人员类型',
					width : "15%",
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
		
function saveTask() {
    var selected = $('#dg_plan').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取员工分配!',
            showType: 'show'
        });
        return;
    } else {
        $.messager.confirm('系统提示', '您确定要分配任务吗?', function (r) {
            if (r) {
                var arr = new Array();
                var inspector_type;
                var contractor;
                for (var i = 0; i < selected.length; i++) {
                    var value = selected[i].STAFF_ID;
                    inspector_type = selected[i].STAFF_TYPE;
                    arr[arr.length] = value;
                }
                $("input[name='selected_staffId']").val(arr);
                $("input[name='inspector_type']").val(inspector_type);
                $("input[name='selected_planId']").val($("input[name='selected']").val());
                $('#form_task').form('submit', {
                    url: webPath + "patrolPlan/save_task.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '任务分配成功!',
                            showType: 'show'
                        });
                        $('#win_staff').window('close');
                        searchData();
                    }
                });
            }
        });
    }
}
		
	</script>
</body>
</html>