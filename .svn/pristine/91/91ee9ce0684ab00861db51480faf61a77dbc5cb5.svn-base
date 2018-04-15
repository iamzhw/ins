<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>选择缆线</title>
</head>
<body style="padding:3px;border:0px" >
	<div class="btn-operation-container" >
			<div class="btn-operation" onClick="queryWell()" >查看</div>
			<div class="btn-operation" onClick="exportWell()" >导出</div>
			<div class="btn-operation" onClick="cancelOssCablePage()">取消</div>
	</div>
	<form id="form_c" action="" method="post"></form>
	<table id="dg_osscable" title="【选择缆线】" style="width:480px;height:480px">
	</table>
	<div id="tb_osscable" style="padding:5px;height:auto">
<div id="win_staff" ></div>
	<script type="text/javascript">
		$(document).ready(function() {
			queryCable();
		});
		function queryCable() {
			var name = $("input[name='cable_name']").val();
			var queryType= $('#query_type').val();
			var parentCableName = $("input[name='parent_cable_name']").val();
			var obj = {
				name : name,
				queryType :queryType,
				parentCableName:parentCableName
			};
			//return;
			$('#dg_osscable').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 500,
				height : 500,
				toolbar : '#tb_osscable',
				url : webPath + "Cable/queryOssCable.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 500, 1000 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'CABLE_ID',
					title : '缆线ID',
					checkbox : true
				}, {
					field : 'NAME',
					title : '缆线名称',
					width : "15%",
					align : 'center'
				}, {
					field : 'NO',
					title : '缆线编码',
					width : "15%",
					align : 'center'
				}, {
					field : 'STATION',
					title : '所属局站',
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

				}
			});
			
		}
	function exportWell(){
			var name = $("input[name='cable_name']").val();
			var queryType= $('#query_type').val();
			var parentCableName = $("input[name='parent_cable_name']").val(); 
			var obj = {
				name : name,
				queryType :queryType,
				parentCableName:parentCableName
			};
			$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            if (r) {
                
                $('#form_c').form('submit', {
                    url: webPath + "Cable/exportOssCable.do",
                    queryParams : obj,
                    onSubmit: function () {
                      //  $.messager.progress();
                    },
                    success: function () {
                      //  $.messager.progress('close'); // 如果提交成功则隐藏进度条
                        $.messager.show({
                            title: '提  示',
                            msg: '导出成功!',
                            showType: 'show'
                        });
                        
                    }
                });
            }
        });
	}
	
	function getCableSection(){
					$('#win_cable_section').window({
					title : "【选择缆线】",
					href : webPath + "Cable/cableSectionSelectPage.do",
					width : 480,
					height : 480,
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