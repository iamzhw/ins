<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
/* .datagrid-body .datagrid-editable table {
  width: 110%;
  height: 100%;
} */
</style>
<head>
<%@include file="../../util/head.jsp"%>
<title>导入记录</title>
</head>
<body style="padding:3px;border:0px" >
	<table id=dg_import title="【导入记录】" style="width:auto;height:480px">
	</table>

	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		
		function searchData(){
			$('#dg_import').datagrid({
				/* 此选项打开，表格会自动适配宽度和高度。 */
				autoSize : true,
				/* toolbar : '#tb', */
				url : webPath + "teamManager/query_import_log.do",
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 50,
				pageList : [20,50,100],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				fit : true,
				singleSelect : true,
				remoteSort: false,
				columns : [[
			           /*  {
			            	field : 'IMPORT_ID',
							title : '代维公司ID',
							rowspan : '2', 
							checkbox : true	
			            }, */
			            {
							field : 'AREA_NAME',
							title : '地市',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'TEAM_NAME',
							title : '班组名称',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'COMPANY',
							title : '代维公司名称',
							width : "10%",
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
							title : '人员姓名',
							width : "10%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'FAIL_DESC',
							title : '状态描述',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						},{
							field : 'CREATE_TIME',
							title : '导入时间',
							width : "15%",
							rowspan : '2',
							sortable : true,
							align : 'center'
						}]],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							/* $("body").resize(); */
						}
			});	
		}
		
	</script>
</body>
</html>