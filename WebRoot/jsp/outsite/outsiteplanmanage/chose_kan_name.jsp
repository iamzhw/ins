<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>选择看护人员</title>
	</head>
	<body style="padding: 3px; border: 0px">
		
		<div id="tb_plan" style="padding: 5px; height: auto">
			<div class="condition-container">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%">
								看护人员：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="select_kan_name" type="text" class="condition-text" />
								</div>
							</td>
							
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" onClick="query_kan_name()">
					查询
				</div>
				<div class="btn-operation" onClick="submit_kan_name()">
					确定
				</div>
			</div>
		</div>
		<table id="dg_plan_kan_name" title="【选择看护人员】" style="width: 100%; height: 480px">
		</table>
		<script type="text/javascript">
		$(document).ready(function() {
			query_kan_name();
		});
		
		function query_kan_name() {
			var select_kan_name = $("input[name='select_kan_name']").val().trim();
			var obj = {
				site_name : select_kan_name
			};
			$('#dg_plan_kan_name').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 730,
				height : 380,
				toolbar : '#tb_plan',
				url : webPath + "outsitePlanManage/kan_name_data.do",
				queryParams : obj,
				method : 'post',
				pagination : false,
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				border : true,
				columns : [ [ {
					field : 'STAFF_ID',
					title : '员工ID',
					checkbox : true
				}, {
					field : 'STAFF_NAME',
					title : '姓名',
					width : "15%",
					align : 'center'
				}, {
					field : 'STAFF_NO',
					title : '账号',
					width : "25%",
					align : 'center'
				}, {
					field : 'TEL',
					title : '电话',
					width : "25%",
					align : 'center'
				}] ],
				//width : 'auto',
				nowrap : false,
				striped : true,
				fit : true,
				fitColumns : true,
				onLoadSuccess : function(data) {
				}
			});
			
		}
		
function submit_kan_name() {
    var selected = $('#dg_plan_kan_name').datagrid('getChecked');
    var count = selected.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取查询看护人员!',
            showType: 'show'
        });
        return;
    } else {
    			$("input[name='kan_name_id']").val(selected[0].STAFF_ID);
                $("input[name='kan_no']").val(selected[0].STAFF_NO);
                $("input[name='kan_name']").val(selected[0].STAFF_NAME);
                $('#win_kan_name').window('close');
    }
}

	</script>
	</body>
</html>