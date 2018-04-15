
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg" title="【】" style="width: 100%; height: 450px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="insertSite()">确定</div>
		</div>
	</div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
		
			searchData();
		});
		
		
		function searchData() {
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 600,
				height : 400,
				toolbar : '#tb',
				url : webPath + "lineInfoController/getSitesByIds.do",
				//queryParams : obj,
				method : 'post',
				
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				columns : [ [ {
					field : 'SITE_ID',
					title : '巡线点ID',
					checkbox : true
				}, {
					field : 'SITE_ID2',
					title : '编号',
					width : "5%",
					align : 'center'
				},{
					field : 'SITE_NAME',
					title : '巡线点名称',
					width : "25%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '巡线点地址',
					width : "20%",
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
       			// $(this).datagrid("fixRownumber");
       			 
				
				}
				
				
			});
		}
		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		

		

		function clearCondition(form_id) {
			
			$("#"+form_id+"").form('reset', 'none');
			
		}


</script>

</body>
</html>
