<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>任务分派</title>
</head>
<body style="padding:3px;border:0px" >
	<form id="form_edit_parent_line" action="" method="post">
	<input name="selected_parent_line" type="hidden"/>
	<input name="selected_line" type="hidden"/>
	</form>
	<table id="dg_p_cable" title="【选择缆线】" style="width:100%;height:480px">
	</table>
	<div id="dg_p_cable_bar" style="padding:5px;height:auto">
	<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
					<td width="10%">编码：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="q_p_c_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="q_p_c_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" >
			<div class="btn-operation" onClick="editParentLine()" >确定</div>
			<div class="btn-operation" onClick="queryParentLine()" >查询</div>
			<div class="btn-operation" onClick="addNewParentLine()" >新增缆线</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			queryParentLine();
		});
		function queryParentLine() {
			var line_name = $("input[name='q_p_c_name']").val().trim();
			var line_no = $("input[name='q_p_c_no']").val().trim();
			var obj = {
				line_name : line_name,
				line_no : line_no
			};
			//return;
			$('#dg_p_cable').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 500,
				height : 500,
				toolbar : '#dg_p_cable_bar',
				url : webPath + "Cable/queryParentLine.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 50, 200 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'LINE_ID',
					title : 'ID',
					width : "2%",
					checkbox : true
				}, {
					field : 'LINE_NO',
					title : '缆线编码',
					width : "8%",
					align : 'center'
				}, {
					field : 'LINE_NAME',
					title : '缆线名称',
					width : "10%",
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
function editParentLine(){
 var parent_line = $('#dg_p_cable').datagrid('getChecked');
 var line = $('#dg').datagrid('getChecked');
    var count = parent_line.length;
    if (count == 0) {
        $.messager.show({
            title: '提  示',
            msg: '请选取一条数据!',
            showType: 'show'
        });
        return;
    } else {
        $.messager.confirm('系统提示', '您确定要修改吗?', function (r) {
            if (r) {
            	var parentId;
            	var arr = new Array();
                for (var i = 0; i < parent_line.length; i++) {
                    parentId = parent_line[i].LINE_ID;
                }
                for (var i = 0; i < line.length; i++) {
                    var value = line[i].CABLE_ID;
                    arr[arr.length] = value;
                }
                $("input[name='selected_parent_line']").val(parentId);
                $("input[name='selected_line']").val(arr);
                $('#form_edit_parent_line').form('submit', {
                    url: webPath + "Cable/editParentLine.do",
                    onSubmit: function () {
                        $.messager.progress();
                    },
                    success: function () {
                        $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '修改成功!',
                            showType: 'show'
                        });
                        $('#parent_line').window('close');
                        searchData();
                    }
                });
            }
        });
    }
}
		
function addNewParentLine(){
	$('#add_parent_line').window({
		title : "【新增缆线】",
		href : webPath + "Cable/addNewParentLinePage.do",
		width : 500,
		height : 460,
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