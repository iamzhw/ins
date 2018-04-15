
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../../util/head.jsp"%>
<title></title>
</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<div>提示:只选择首点，新增加的点默认为第一个点；只选择末点，则新增的点默认为最后一个点。</div>
	<table id="dg" title="【】" style="width: 100%; height: 450px">
	</table>
	<div id="tb" style="padding: 5px; height: auto">
		
		<div class="btn-operation-container">
			<div class="btn-operation" id ='commit'>确定</div>
		</div>
	</div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			//var data ='${jsonstring}';
			searchData(pointsListJson);
			$("#commit").click(function(){
				//insertSite('${pointInLine}');
				insertSite();
			});
		});
		
		function searchData(data) {
			
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				//autoSize : true,
				width : 600,
				height : 400,
				toolbar : '#tb',
				data :eval(data) ,
				//queryParams : obj,
				method : 'post',
				
				//loadMsg:'数据加载中.....',
				rownumbers : true,
			
				singleSelect : false,
				columns : [ [ {
					field : 'POINT_SEQ',
					title : '序号',
					checkbox : true
				},
				/* {
					field : 'POINT_IN_LINE_SEQ',
					title : '序号',
					formatter: function(value,row,index){
						return index;
					}
				}, 	 */			
				{
					field : 'POINT_TYPE',
					title : '点类型',
					width : "8%",
					align : 'center',
					formatter: function(value,row,index){
						if(value==-1)
						{
							return '非关键点';
						}
						else
						{
							return '关键点';
						}
					}
				},{
					field : 'POINT_NO',
					title : '点编码',
					width : "10%",
					align : 'center'
				}, {
					field : 'POINT_NAME',
					title : '点名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'LONGITUDE',
					title : '经度',
					width : "10%",
					align : 'center'
				}
				, {
					field : 'LATITUDE',
					title : '纬度',
					width : "10%",
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
