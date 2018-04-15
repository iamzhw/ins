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
		<title>市对县检查报告</title>
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
						<td width="10%" nowrap="nowrap">变动开始时间：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text" value=""
									name=START_TIME id="task_start_time"
									onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">变动结束时间：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name=COMPLETE_TIME id="task_end_time"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="10%" nowrap="nowrap">检查开始时间：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text" value=""
									name=CHECK_START_TIME id="check_start_time"
									onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">检查结束时间：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text"
									name=CHECK_COMPLETE_TIME id="check_end_time"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<!-- <div style="float: right;" class="btn-operation" onClick="reform()">重置</div> -->
			<div style="float: right;" class="btn-operation"
				onClick="exportExcel()">导出</div>
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
			<input name="area_id" type="hidden" value="" />

		</div>
	</div>
	<table id="dg" title="【整治单数据报告】" style="width: 100%; height: 480px"></table>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			initbutton();
			getAreaList();
			initParameters();
			initParameters_check();
			/* searchData(); */
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
			
			
			var check_START_TIME = $("input[name='CHECK_START_TIME']").val();
			var check_end_time = $("input[name='CHECK_COMPLETE_TIME']").val();
			
			var obj ={
					startDate : START_TIME,
					areaId:AREA_ID,
					sonAreaId:son_area,
					endDate:endDate,
					checkStartTime:check_START_TIME,
					checkEndTime:check_end_time
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "cableCheckStatictis/statictis.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : true,
				columns : [ [ {
					field : 'AREANAME',
					title : '地市',
					width : "10%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'SONAREANAME',
					title : '区域',
					width : "15%",
					rowspan : '2', 
				    align : 'center',
				},{
					field : 'SON_AREA_ID',
					title : '区域id',
					hidden: true
				},{
					field : 'TEAM_NAME',
					title : '班组',
					width : "15%",
					rowspan : '2', 
				    align : 'center',
				},{
					field : 'TEAM_ID',
					title : '班组id',
					hidden: true
				},{
					field : 'TOTALCOUNT',
					title : '总工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'ZCOU',
					title : '现场检查工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return value;
						/* return "<a href=\"javascript:viewDevice(1,null,"+row.TEAM_ID+");\">"+value+"</a>"; */
					}
				},
				/* {
					field : 'MARKONE',
					title : '装工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return "<a href=\"javascript:viewDevice(2,17,"+row.TEAM_ID+");\">"+value+"</a>";
					}
				},{
					field : 'MARKTWO',
					title : '拆工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return "<a href=\"javascript:viewDevice(3,18,"+row.TEAM_ID+");\">"+value+"</a>";
					}
				},{
					field : 'MARKTHREE',
					title : '移工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return "<a href=\"javascript:viewDevice(4,3,"+row.TEAM_ID+");\">"+value+"</a>";
					}
				},{
					field : 'MARKFOUR',
					title : '维工单数',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return "<a href=\"javascript:viewDevice(5,4,"+row.TEAM_ID+");\">"+value+"</a>";
					}
				}, */
				{
					field : 'RATE',
					title : '检查占比(%)',
					width : "15%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						if(row.ZCOU == 0 || row.TOTALCOUNT == 0){
							return 0;
						}else if ( row.ZCOU >= row.TOTALCOUNT ){
							return 100; 
						}else{
							return ((row.ZCOU/row.TOTALCOUNT)*100).toFixed(2);
						}
					}
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$("body").resize();
					
		            var totalcou = 0,zcouu = 0;
		            if(data.totalCount!=null&&data.totalCount!=""){
		            	totalcou = data.totalCount[0].TOTALCOUNT;
		            }
		            if(data.ZCount!=null&&data.ZCount!=""){
		            	zcouu = data.ZCount[0].ZCOU;
		            }
		            $('#dg').datagrid('appendRow', { itemid: '<b>统计：</b>', AREANAME:'合计',TOTALCOUNT:totalcou, ZCOU: zcouu});
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
			
			if(endMonth == -2){
				endYear = endYear-1;
				endMonth = 12;
			}else{
				endMonth = endMonth>9?endMonth:"0"+(endMonth<0?"1":endMonth);	
			}
			/* var myDate = new Date(endYear, endMonth, 0);
	        var endDay =  myDate.getDate(); 
			endDay = endDay>9?endDay:"0"+endDay;*/
			endDay = "01";
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#task_start_time").val(endTime);
			
			//默认统计结束时间：当天
			var endYear = now.getFullYear();
			var endMonth = now.getMonth();
			/* if(endMonth==0){endMonth=12;endYear=endYear-1;} */
			if(endMonth == 0){
				endYear = endYear-1;
				endMonth = 12;
			}else{
				endMonth = endMonth>9?endMonth:"0"+(endMonth<0?"1":endMonth);	
			}
			/* var myDate = new Date(endYear, endMonth, 0);
	        var endDay =  myDate.getDate(); */
	        var endDay = now.getDate();
	        
	        endDay = 30;
	        if(endMonth==1||endMonth==3||endMonth==5||endMonth==7||endMonth==8||endMonth==10||endMonth==12){
	        	endDay = 31;
	        }
	        
			/* endDay = endDay>9?endDay:"0"+endDay; */
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#task_end_time").val(endTime);
		}
		
		
		//初始化时间參數，将检查开始时间和检查结束时间赋值进来
		function initParameters_check(){
			//默认统计开始时间：当月第一天
			var now = new Date();
			var endYear = now.getFullYear();
			var endMonth = now.getMonth()-1;
			/* if(endMonth==0){endMonth=12;endYear=endYear-1;} */
			endMonth = endMonth>9?endMonth:"0"+(endMonth<0?"1":endMonth);
			/* var myDate = new Date(endYear, endMonth, 0);
	        var endDay =  myDate.getDate(); 
			endDay = endDay>9?endDay:"0"+endDay;*/
			endDay = "01";
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#check_start_time").val(endTime);
			
			//默认统计结束时间：当天
			var endYear = now.getFullYear();
			var endMonth = now.getMonth()+1;
			/* if(endMonth==0){endMonth=12;endYear=endYear-1;} */
			endMonth = endMonth>9?endMonth:"0"+endMonth;
			/* var myDate = new Date(endYear, endMonth, 0);
	        var endDay =  myDate.getDate(); */
	        var endDay = now.getDate();
			endDay = endDay>9?endDay:"0"+endDay;
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#check_end_time").val(endTime);
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
			
			var check_START_TIME = $("input[name='CHECK_START_TIME']").val();
			var check_end_time = $("input[name='CHECK_COMPLETE_TIME']").val();
			
			
			window.open(webPath
					+ "cableCheckStatictis/exportExcel.do?randomPara=1 "
					+ "&areaId="+AREA_ID+"&sonAreaId="+son_area
					+ "&startDate="+START_TIME+"&endDate="+endDate
					+ "&checkStartTime="+check_START_TIME
					+ "&checkEndTime="+check_end_time
					+ "&page=1&rows=9999999");
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
			
			var check_START_TIME = $("input[name='CHECK_START_TIME']").val();
			var check_end_time = $("input[name='CHECK_COMPLETE_TIME']").val();
			
			var  url="<%=path%>/cableCheckStatictis/orderIndex.do?start_time="+START_TIME
					+"&end_time="+endDate
					+"&type="+type
					+"&order_type="+orderType
					+"&check_start_time="+check_START_TIME
					+"&check_end_time="+check_end_time
					+"&team_id="+teamId;
			addTab("工单信息", url);
		}
			
	</script>
</html>