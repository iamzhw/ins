
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡线报表</title>
</head>
<body style="padding: 3px; border: 0px">
	
	
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td >巡线时间：</td>
						<td >
							<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="line_date" id="line_date" 
								onClick="WdatePicker();"/>
							</div>
						</td>
						<td >
							<input type="hidden" id="up" name="up"/>
							<input type="hidden" id="down" name="down" />
							<div  class="btn-operation" onClick="searchData()">查询</div>
							<div  class="btn-operation" onClick="up()">上午</div>
							<div  class="btn-operation" onClick="down()">下午</div>
							<div  class="btn-operation" onClick="allday()">全天</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div>
	<table id="dg_kanhu_shijian" title="【巡线报表】" style="width: 100%; height: 480px">
	</table>
	</div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
		$(document).ready(function() {
			var now_year = new Date().getFullYear();//获取当前年份
	    	var now_month = new Date().getMonth()+1;//获取当前月份
	    
	    	var today = new Date();
	    	var day = today.getDate()-1;
		    	day = day<10?"0"+day:day;
			var month = today.getMonth()+1;
		   	 	month = month<10?"0"+month:month;
	    	var timetody = now_year+month+day;
	     	var daty = now_year+'-'+month+'-'+day;
	     	$("#line_date").val(daty);
			searchData();
		});
function up(){
	$("#up").val("up");
	$("#down").val("");
	searchData();
	
}
function down(){
	$("#up").val("");
	$("#down").val("down");
	searchData();
}
function allday(){
	$("#up").val("");
	$("#down").val("");
	searchData();
}
		
		function searchData() {
			var up = $("#up").val();
			var down = $("#down").val();
			var line_date =$("#line_date").val();
			var obj = {
				up : up,
				down:down,
				line_date:line_date
			};
			$('#dg_kanhu_shijian').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				width : 790,
				height : 570,
				toolbar : '#tb_kanhu_shijian',
				url : webPath + "outsitePlanManage/test.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'MATCH_ID',
					title : 'ID',
					checkbox : true
				}, {
					field : 'STAFF_NAME',
					title : '巡线员',
					width : "10%",
					align : 'center'
				}, {
					field : 'SITE_NAME',
					title : '巡线点',
					width : "28%",
					align : 'center'
				}, {
					field : 'NO_TOTAL',
					title : '无效时长',
					width : "15%",
					align : 'center'
				}, {
					field : 'YES_TOTAL',
					title : '有效时长',
					width : "15%",
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
