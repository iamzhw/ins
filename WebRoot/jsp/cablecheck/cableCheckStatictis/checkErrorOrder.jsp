<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡检计划</title>

</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<form id="form" action="" method="post">
			<div class="btn-operation-container">
				<input id="start_time" type="hidden" value="${start_time}"/>
				<input id="end_time" type="hidden" value="${end_time}"/>
				<input id="staffId" type="hidden" value="${staffId}"/>
				<input id="rowType" type="hidden" value="${rowType}"/>
			</div>
		</form>
		<!-- <div class="btn-operation-container">
			<div style="float: right;" class="btn-operation"
					onClick="exportExcel()">导出</div>
			</div> -->
	</div>
	<table id="dg" title="工单列表" style="width:98%;height:480px">
	</table>
	<div id="win_staff"></div>
	<div id="win_Plan"></div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
	
		function searchData() {
			var obj = {
				startDate:$("#start_time").val(),
				endDate:$("#end_time").val(),
				staffId:$("#staffId").val(),
				rowType:$("#rowType").val()
			};
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "cableCheckStatictis/showCheckErrorOrder.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				/* fit:true, */
				singleSelect : true,
				columns : [ [{
					field : 'AREA_NAME',
					title : '地市',
					width : "10%",
					align : 'center'
				}, {
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "10%",
					align : 'center'
				}, {
					field : 'TASK_NO',
					title : '任务编号',
					width : "10%",
					align : 'center'
				}, {
					field : 'TASK_NAME',
					title : '任务名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'EQP_NO',
					title : '设备编号',
					width : "10%",
					align : 'center'
				},  {
					field : 'EQP_NAME',
					title : '设备名称',
					width : "10%",
					align : 'center'
				}, {
					field : 'EQPADDRESS',
					title : '设备地址',
					width : "10%",
					align : 'center'
				}, {
					field : 'PORT_NO',
					title : '端子编号',
					width : "10%",
					align : 'center'
				}, {
					field : 'DESCRIPT',
					title : '描述',
					width : "10%",
					align : 'center'
				}
				/* , {
					field : 'CREATE_TIME',
					title : '时间',
					width : "10%",
					align : 'center',
					formatter : function(value, row, Index) {
						  return getMyDate(value.time);
					}
				} */] ],
				//前面的checkBox列
				/*frozenColumns:[[  
				 	 {field:'PLAN_ID',checkbox:true}  
			    ]],  */
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$("body").resize();
				}
			});
		}
		
		
		function exportExcel() {
			
			var startDate = $("#start_time").val(),
				endDate = $("#end_time").val(),
				sonAreaId = $("#area").val(),
				type = $("#type").val(),
				order_type = $("#order_type").val(),
				team_id = $("#teamId").val();
			
			
			window.open(webPath
					+ "cableCheckStatictis/exportExcelByDetail.do?randomPara=1 "
					+ "&startDate="+startDate+"&endDate="+endDate
					+ "&order_type="+order_type+"&team_id="+team_id
					+ "&sonAreaId="+sonAreaId+"&type="+type+"&page=1&rows=9999999");
		}
		
		
        function getMyDate(str){  
            var oDate = new Date(str),  
            oYear = oDate.getFullYear(),  
            oMonth = oDate.getMonth()+1,  
            oDay = oDate.getDate(),  
            oHour = oDate.getHours(),  
            oMin = oDate.getMinutes(),  
            oSen = oDate.getSeconds(),  
            oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
            return oTime;  
        };  
        //补0操作  
        function getzf(num){  
            if(parseInt(num) < 10){  
                num = '0'+num;  
            }  
            return num;  
        }  
		
	</script>
</body>
</html>