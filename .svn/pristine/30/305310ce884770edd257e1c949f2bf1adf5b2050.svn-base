<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../../util/head.jsp"%>
		<title>选择员工</title>
	</head>
	<body style="padding: 3px; border: 0px">
		
		<div id="tb_plan" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%">
								帐号：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="staff_no" type="text" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								姓名：
							</td>
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
				<div class="btn-operation" onClick="queryStaff()">
					查询
				</div>
				<div class="btn-operation" onClick="submitStaff()">
					确定
				</div>
			</div>
		</div>
		<table id="dg_plan" title="【选择员工】" style="width: 100%; height: 480px">
		</table>
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
			//alert(staff_name+":"+staff_no);
			var obj = {
				staff_name : staff_name,
				staff_no : staff_no
			};
			//return;
			$('#dg_plan').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 550,
				height : 380,
				toolbar : '#tb_plan',
				url : webPath + "gaotieautoTrackController/getInspactPerson.do",
				queryParams : obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NO',
					title : '员工帐号',
					width : "15%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '员工名称',
					width : "25%",
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
		
function submitStaff() {
    var selected = $('#dg_plan').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取查询员工!',
            showType: 'show'
        });
        return;
    } else {
        $("input[name='inspact_id']").val(selected[0].STAFF_ID);
        $("input[name='inspact_name']").val(selected[0].STAFF_NAME);
        $('#win_staff').window('close');
    }
}



		
	</script>
	</body>
</html>