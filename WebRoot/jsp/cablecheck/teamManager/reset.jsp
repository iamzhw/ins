<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
</style>
<head>
<%@include file="../../util/head.jsp"%>
<title>删除审核人/接单人/兜底岗</title>
</head>
<body style="padding:3px;border:0px" >
<div style="width: 100%;height:100%;float:left">
	<div id="tb" style="padding: 5px; height: auto; width:100%">
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="ensureDel()">确定</div>
			<div class="btn-operation" onClick="closeDeleteWin()">取消</div>
		</div>
	</div>
	<table id=dg_role_del title="【删除审核人/接单人/兜底岗】" style="width:auto;height:480px">
	</table>

	<script type="text/javascript">
		$(document).ready(function() {
			searchRole();
		});
		
		function searchRole(){
			var select_teamId = returnSelect();
			$('#dg_role_del').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				url : webPath + "teamManager/listUserRole.do?team_id="+select_teamId,
				method : 'post',
				/* pagination : true, */
				pageNumber : 1,
				pageSize : 100,
				pageList : [20,50,100],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit : true,
				singleSelect : false,
				remoteSort: false,
				columns : [[
			            {
			            	field : 'UNIQUEID',
							title : '唯一主键',
							checkbox : true
			            },{
							field : 'TEAM_NAME',
							title : '班组名称',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
					 	},{
							field : 'STAFF_NO',
							title : '人员账户',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'STAFF_NAME',
							title : '人员名称',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'ROLE_NAME',
							title : '角色名称',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						}]],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$("body").resize();
						}
			});	
		}
		
	</script>
</body>
</html>