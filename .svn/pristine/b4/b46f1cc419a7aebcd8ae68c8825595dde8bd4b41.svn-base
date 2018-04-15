<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
a {
	text-decoration: none;
}

.div_content {
	display: none;
	position: absolute;
	/* top: 400px; */
	width: 190px;
	margin-left: 0px;
	height: 100px;
	margin-top: 3px;
	padding: 0;
	background-color: #F5F5F5;
	z-index: 1011;
	overflow: hidden;
	padding-top: 6px;
}
</style>
<html>
<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>即时检查纠错数据分析</title>
	</head>
<body style="padding: 3px; border: 0px">
	<input type="hidden" id="ifGLY" />
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container" style="height: 50px;">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td width="10%">地市：</td>
						<td width="10%">
							<div>
								<select name="area_id" id="area_id" class="condition-select"
									onchange="getSonAreaList()">
									<option value=''>--请选择--</option>
								</select>
							</div>
						</td>
						<td width="10%">区域：</td>
						<td width="10%"><select name="SON_AREA_ID" id="son_area"
							class="condition-select">
								<option value="">请选择</option>
						</select></td>
						<td width="10%" nowrap="nowrap">统计开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text" value=""
									name=START_TIME id="task_start_time"
									onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">统计结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name=COMPLETE_TIME id="task_end_time"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<!-- <div style="float: right;" class="btn-operation"
				onClick="exportExcel()">导出</div> -->
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
			<input name="area_id" type="hidden" value="" />

		</div>
	</div>
	<table id="dg" title="即时检查纠错数据分析" style="width: 100%; height: 480px"></table>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			initbutton();
			getAreaList();
			initParameters();
			searchData();
		});
		function initbutton(){
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				$("#initbutton").show();
			}
		};
		function searchData() {
		    
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var son_area = $("#son_area").val();
			var endDate = $("input[name='COMPLETE_TIME']").val();
			
			var obj ={
					startDate : START_TIME,
					areaId:AREA_ID,
					sonAreaId:son_area,
					endDate:endDate
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "cableCheckStatictis/orderChange.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'AREA_NAME',
					title : '地市',
					width : "15%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'SON_AREA_NAME',
					title : '区县',
					width : "15%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'GRID_NAME',
					title : '网格名称',
					width : "25%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'ORDER_NUM',
					title : '工单数',
					width : "15%",
					rowspan : '2', 
				    align : 'center',
				},{
					field : 'GRID_ID',
					title : '网格id',
					hidden: true
				},{
					field : 'CHANGE_NUM',
					title : '更纤数',
					width : "15%",
					rowspan : '2', 
				    align : 'center',
				},
				/* {
					field : 'ZCOU',
					title : '现场检查工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return "<a href=\"javascript:viewDevice(1,null,"+row.TEAM_ID+");\">"+value+"</a>";
					}
				}, */
				{
					field : 'RATE',
					title : '更纤率(%)',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						if(value == "" || value == null || value == 0){
							return 0;
						}else if (value >= 100){
							return 100; 
						}else{
							return (value*100).toFixed(2);
						}
					}
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$("body").resize();
				}
			});
		};
		
		//初始化时间參數，将开始时间和结束时间赋值进来
		function initParameters(){
			//默认统计开始时间：当月第一天
			var now = new Date();
			var endYear = now.getFullYear();
			var endMonth = now.getMonth()-2;
			/* if(endMonth==0){endMonth=12;endYear=endYear-1;} */
			endMonth = endMonth>9?endMonth:"0"+(endMonth<0?"1":endMonth);
			/* var myDate = new Date(endYear, endMonth, 0);
	        var endDay =  myDate.getDate(); 
			endDay = endDay>9?endDay:"0"+endDay;*/
			endDay = "01";
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#task_start_time").val(endTime);
			
			//默认统计结束时间：当天
			var endYear = now.getFullYear();
			var endMonth = now.getMonth()+1;
			/* if(endMonth==0){endMonth=12;endYear=endYear-1;} */
			endMonth = endMonth>9?endMonth:"0"+endMonth;
			/* var myDate = new Date(endYear, endMonth, 0);
	        var endDay =  myDate.getDate(); */
	        var endDay = now.getDate()-1;
			endDay = endDay>9?endDay:"0"+endDay;
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#task_end_time").val(endTime);
		}
		
		//获取地市
		function getAreaList() {
			 var areaId=2; 
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{
					var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
					var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
					var isAdmin1 ='${CABLE_ADMIN}';
					var result = json.sonAreaList;
					$("select[name='area_id'] option").remove();
					$("select[name='area_id']").append("<option value=''>请选择</option>");
					if( true ==isAdmin1 || isAdmin1 =='true'){
						for ( var i = 0; i < result.length; i++) {
								$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}					
						}
					} else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${areaId}){
								$("select[name='area_id']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}	
				}
			});
		}
		
		     	//获取区域
		function getSonAreaList() {
			var areaId = $("#area_id").val();
			$.ajax({
				type : 'POST',
				url : webPath + "General/getSonAreaList.do",
				data : {
					areaId : areaId
				},
				dataType : 'json',
				success : function(json) 
				{
					var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
					var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
					var isAdmin1 ='${CABLE_ADMIN}';
					var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					if(true ==isAdmin1 || isAdmin1 =='true'){	
						for ( var i = 0; i < result.length; i++) {					
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");												
						}
					}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true'){
						for ( var i = 0; i < result.length; i++) {
							$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
						}
					}else{
						for ( var i = 0; i < result.length; i++) {
							if(result[i].AREA_ID==${sonAreaId}){
							$("select[name='SON_AREA_ID'] option").remove();
								$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
							}						
						}
					}
				}
			});
		}
		
		function reform(){			
			$("input[name='START_TIME']").val('');
			$("select[name='SON_AREA_ID']").val('');
			$("input[name='COMPLETE_TIME']").val('');
			$("select[name='area_id']").val('');
		};
		
		
		
		function exportExcel() {
		
			if($("#task_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#task_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			
			var AREA_ID = $("select[name='area_id']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var son_area = $("#son_area").val();
			var endDate = $("input[name='COMPLETE_TIME']").val();
			
			window.open(webPath
					+ "cableCheckStatictis/exportExcel.do?randomPara=1 "
					+ "&areaId="+AREA_ID+"&sonAreaId="+son_area
					+ "&startDate="+START_TIME+"&endDate="+endDate+"&page=1&rows=9999999");
		}
		
		
		
		function myCloseTab() {  
			if(parent.$('#tabs').tabs('exists',"设备信息")){
				parent.$('#tabs').tabs('close',"设备信息");
			}
	    }
		
		function viewDevice(type,orderType,teamId){
			myCloseTab();
			var AREA_ID = $("select[name='area_id']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var son_area = $("input[name='SON_AREA_ID']").val();;
			var team_id = teamId;
			var endDate = $("input[name='COMPLETE_TIME']").val();
			var  url="<%=path%>/cableCheckStatictis/orderIndex.do?start_time="+START_TIME
					+"&end_time="+endDate
					+"&type="+type
					+"&order_type="+orderType
					+"&team_id="+teamId;
			addTab("工单信息", url);
		}
			
	</script>
</html>