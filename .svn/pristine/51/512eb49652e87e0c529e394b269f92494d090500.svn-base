<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>选择缆线短信</title>
</head>
<body style="padding:3px;border:0px" >
	<div class="btn-operation-container" >
			<div class="btn-operation" onClick="queryWell()" >确定</div>
			<div class="btn-operation" onClick="cancelOssCablePage()">取消</div>
	</div>
	<form id="form_c" action="" method="post"></form>
	<table id="dg_osscable" title="【选择缆线段】" style="width:480px;height:480px">
	</table>
	<div id="tb_osscable_Section" style="padding:5px;height:auto">
	<script type="text/javascript">
		$(document).ready(function() {
			queryCableSection();
		});
		function queryCableSection() {
			var cable_id = $('#dg_osscable').datagrid('getChecked')[0].CABLE_ID;
			var obj = {
				cable_id : cable_id
			};
			//return;
			$('#dg_osscable_section').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 500,
				height : 500,
				toolbar : '#tb_osscable_section',
				url : webPath + "Cable/queryOssCableSection.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 500, 1000 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CBL_SECT_ID',
					title : '缆线段ID',
					checkbox : true
				}, {
					field : 'NAME',
					title : '缆线段名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'NO',
					title : '缆线段编码',
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
					$('#dg_osscable_section').datagrid('selectAll')
				}
			});
			
		}
	</script>
</body>
</html>