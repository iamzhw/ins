
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>看护时间段模板管理</title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<table id="dg_kanhu_shijian" title="【看护时间段模板管理】" style="width: 100%; height: 480px">
	</table>

	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {

			searchData_kanhu_shijian();
		});

		function searchData_kanhu_shijian() {
			var plan_id = $("input[name='plan_id']").val();
			var obj = {
				plan_id : plan_id
			};
			$('#dg_kanhu_shijian').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				width : 480,
				height : 280,
				toolbar : '#tb_kanhu_shijian',
				url : webPath + "outsitePlanManage/look_time.do",
				queryParams : obj,
				method : 'post',
				pagination : false,
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [{
					field : 'START_TIME',
					title : '开始时间',
					width : "20%",
					align : 'center'
				}, {
					field : 'END_TIME',
					title : '结束时间',
					width : "20%",
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}

	</script>
</body>
</html>
