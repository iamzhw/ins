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
						<td>工单性质：</td>
						<td>
							<select id="order_type" name="order_type" class="condition-select" style="width:96%">
									<option value="">
										请选择
									</option>
									<option value="建设单（拆）">
										建设单（拆）
									</option>
									<option value="建设单（新装）">
										建设单（新装）
									</option>
							</select>
						</td>
						<td>工单来源：</td>
						<td>
							<select id="order_from" name="order_from" class="condition-select" style="width:96%">
									<option value="">
										请选择
									</option>
									<option value="1">
										综调
									</option>
									<option value="2">
										IOM
									</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>准确率:</td>
						<td><input type="number" id="rate_start"/></td>
						<td><label value="至">至</label></td>
						<td><input type="number" id="rate_end"/></td>
						<td width="8%">班组名称：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input name="team_name" type="text" class="condition-text" />
							</div>
						</td>
						
						<td width="10%">代维公司名称：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input name="company" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<td><label>时间：&nbsp;&nbsp;&nbsp;</label>  </td>
						<td colspan=3>
						
						<select id="date_year" name="date_year" class="condition-select" 
						style="width:80px;background-color:#EEEEEE" disabled="true">
								<option value="2018">2018年</option>
								<option value="2017">2017年</option>
						</select>
						
						<input type="radio" name="radio" value="threeMonth"/>季度&nbsp;&nbsp;&nbsp;
						<select id="date_jidu" name="date_jidu" class="condition-select" 
						style="width:80px;background-color:#EEEEEE" disabled="true">
							<option value="0">请选择</option>
							<option value="1">
								一季度
							</option>
							<option value="2">
								二季度
							</option>
							<option value="3">
								三季度
							</option>
							<option value="4">
								四季度
							</option>
						</select>
						&nbsp;&nbsp;&nbsp;
						<input type="radio" name="radio" value="oneMonth"/>月份
						<select id="date_month" name="date_month" class="condition-select" 
						style="width:80px;background-color:#EEEEEE" disabled="true">
							<option value="0">请选择</option>
							<option value="01">1月</option>
							<option value="02">2月</option>
							<option value="03">3月</option>
							<option value="04">4月</option>
							<option value="05">5月</option>
							<option value="06">6月</option>
							<option value="07">7月</option>
							<option value="08">8月</option>
							<option value="09">9月</option>
							<option value="10">10月</option>
							<option value="11">11月</option>
							<option value="12">12月</option>
						</select>
						&nbsp;&nbsp;&nbsp;
						<input type="radio" name="radio" value="bySelf" checked/>自定义
						</td>
						<td width="10%" nowrap="nowrap">开始时间：</td>
						<td width="10%">
							<div class="condition-text-container">
								<input class="condition-text condition" type="text" value=""
									name=START_TIME id="task_start_time"
									onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
							</div>
						</td>
						<td width="10%" nowrap="nowrap">结束时间：</td>
						<td width="10%">
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
			<!-- <div style="float: right;" class="btn-operation" onClick="reform()">重置</div> -->
			<!-- <div style="float: right;" class="btn-operation"
				onClick="exportExcel()">导出</div> -->
			<div style="float: right;" class="btn-operation"
				onClick="searchData()">查询</div>
			<input name="area_id" type="hidden" value="" />

		</div>
	</div>
	<table id="dg" title="【班组检查统计】" style="width: 80%; height: 480px"></table>
	<div>
		<ul>
			<li>设备标识(无/手写) : 光设施无标签或标签手写;子框无标签或标签手写</li>
			<li>光路标签(无/手写) : 光路无标签或标签手写</li>
			<li>现场与系统端子不一致 : [新增]现场与系统不一致;[存量]现场与系统不一致;现场无跳纤,系统有光路;检查人员已将现场问题整改</li>
			<li>光路未拆 : 现场有跳纤,系统无光路</li>
			<li>已整改数的最后统计时间，是按页面结束时间往后延7天</li>
		</ul>
	</div>
</body>
<script type="text/javascript">

		var startTime="";
		var endTime="";

		$(document).ready(function() {
			initbutton();
			getAreaList();
			initParameters();
			/* searchData(); */
			
			
			$('input[type=radio][name=radio]').change(function() {
		        if (this.value == 'threeMonth') {
		            $('#task_start_time').attr("disabled","disabled");
		            $('#task_end_time').attr("disabled","disabled");
		            $('#date_month').attr("disabled","disabled");
		            $('#date_jidu').attr("disabled",false);
		            $('#date_year').attr("disabled",false);
		            
		            $('#date_month').css("background-color","#EEEEEE");
		            $('#date_jidu').css("background-color","");
		            $('#date_year').css("background-color","");
		        }
		        else if (this.value == 'oneMonth') {
		        	$('#task_start_time').attr("disabled","disabled");
		        	$('#task_end_time').attr("disabled","disabled");
		        	$('#date_jidu').attr("disabled","disabled");
		        	$('#date_month').attr("disabled",false);
		        	$('#date_year').attr("disabled",false);
		        	
		        	$('#date_jidu').css("background-color","#EEEEEE");
		        	$('#date_month').css("background-color","");
		        	$('#date_year').css("background-color","");
		        }
		        else if (this.value == 'bySelf') {
		        	$('#date_jidu').attr("disabled","disabled");
		        	$('#date_month').attr("disabled","disabled");
		        	$('#date_year').attr("disabled","disabled");
		        	$('#date_jidu').css("background-color","#EEEEEE");
		        	$('#date_month').css("background-color","#EEEEEE");
		        	$('#date_year').css("background-color","#EEEEEE");
		        	$('#task_start_time').attr("disabled",false);
		        	$('#task_end_time').attr("disabled",false);
		        }
		    });
			
			
			$("#date_jidu").change(function(){
				var jidu_val = $("#date_jidu").val();
				var year_val = $('#date_year').val();
				if(jidu_val==0){
					startTime="";
					endTime="";	
				}else if(jidu_val==1){
					startTime=year_val+"-01-01";
					endTime=year_val+"-03-31";
				}else if(jidu_val==2){
					startTime=year_val+"-04-01";
					endTime=year_val+"-06-30";	
				}else if(jidu_val==3){
					startTime=year_val+"-07-01";
					endTime=year_val+"-09-30";
				}else{
					startTime=year_val+"-10-01";
					endTime=year_val+"-12-31";
				}
		    });
			
			$("#date_month").change(function(){
				var month_val = $("#date_month").val();
				if(month_val==0){
					startTime="";
					endTime="";	
				}else{
					setMonthDay(month_val);
				}
		    });
		});
		
		
		function setMonthDay(month){
			var year_val = $('#date_year').val();
			startTime=year_val+"-"+month+"-01";
			if(month=="02"){
				endTime=year_val+"-"+month+"-28";
			}else if( month=="01" || month=="03" ||month=="05" ||month=="07" ||month=="08" ||month=="10"){
				endTime=year_val+"-"+month+"-31";	
			}else{
				endTime=year_val+"-"+month+"-30";
			}
		}
		
		function initbutton(){
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				$("#initbutton").show();
			}
		};
		
		//通过传递的参数计算占比率
		function columnRate(cou,allCou){
			if(!cou || !allCou || cou == 0 || allCou == 0){
				return 0;
			}else if ( cou >= allCou ){
				return "100%"; 
			}else{
				return ((cou/allCou)*100).toFixed(2)+"%";
			}
		}
		
		function searchData() {
			
			var val=$('input:radio[name="radio"]:checked').val();

			if(val=='oneMonth'||val=='threeMonth'){
				var START_TIME = startTime;
				var endDate = endTime; 		
				if(START_TIME==''){
					alert("开始日期不能为空！");
					return false;
				}
				if(endDate==''){
					alert("结束日期不能为空！");
					return false;
				}
			}else{
				if($("#task_start_time").val()==''){
					alert("开始日期不能为空！");
					return false;
				}
				if($("#task_end_time").val()==''){
					alert("结束日期不能为空！");
					return false;
				}
				var START_TIME = $("input[name='START_TIME']").val();
				var endDate = $("input[name='COMPLETE_TIME']").val();
			}
			
			var AREA_ID = $("select[name='area_id']").val();
			var son_area = $("#son_area").val();
			
			var rate_start = $("#rate_start").val();
			var rate_end = $("#rate_end").val();
			
			var rate_end = $("#rate_end").val();
			var team_name = $("input[name='team_name']").val();
			var company = $("input[name='company']").val();
			
			var action_type = $("#order_type").val();
			var mark = $("#order_from").val();
			
			var obj ={
					startDate : START_TIME,
					areaId:AREA_ID,
					sonAreaId:son_area,
					endDate:endDate,
					rate_start:rate_start,
					rate_end:rate_end,
					team_name:team_name,
					company:company,
					action_type:action_type,
					mark:mark
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "teamCheckStatictis/statictis.do",
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
					width : "5%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'SON_AREA_NAME',
					title : '区域',
					width : "5%",
					rowspan : '2', 
				    align : 'center',
				},{
					field : 'COMPANY_NAME',
					title : '代维公司',
					width : "5%",
					rowspan : '2', 
				    align : 'center',
				},{
					field : 'TEAM_NAME',
					title : '班组',
					width : "8%",
					rowspan : '2', 
				    align : 'center',
				},{
					field : 'CHECK_ALL_NUM',
					title : '检查端子总数',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},{
					field : 'CHECK_RIGHT_NUM',
					title : '正确数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
				{
					field : 'CHECK_ERROR_NUM',
					title : '错误数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
				{
					field : 'CHECK_RATE',
					title : '准确率(%)',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
					    if(!row.CHECK_RIGHT_NUM || !row.CHECK_ALL_NUM || row.CHECK_RIGHT_NUM == 0 || row.CHECK_ALL_NUM == 0){
							return 0;
						}else if ( row.CHECK_RIGHT_NUM >= row.CHECK_ALL_NUM ){
							return "100%"; 
						}else{
							return ((row.CHECK_RIGHT_NUM/row.CHECK_ALL_NUM)*100).toFixed(2)+"%";
						}
					}
				},
				{title:'设备标识(无/手写)',width:"8%",align:'center',colspan:2},
				{title:'光路标签(无/手写)',width:"9%",align:'center',colspan:2},
				{title:'现场与系统端子不一致',width:"10%",align:'center',colspan:2},
				{title:'光路未拆',width:"5%",align:'center',colspan:2},
				/* {
					field : 'ERROR_EQP_NUM',
					title : '设备标识(无/手写)',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
				{
					field : 'ERROR_NO_TAG_NUM',
					title : '光路标签(无/手写)',
					width : "9%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
			    {
					field : 'ERROR_NO_APPEND_ORDER_NUM',
					title : '现场与系统标签不一致',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
				{
					field : 'ERROR_ROAD_NO_DESTORY_NUM',
					title : '光路未拆',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				}, */
				/* {
					field : 'ERROR_ALL_NUM',
					title : '错误总计',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				}, */
				{
					field : 'ZG_NUM',
					title : '整改总数',
					width : "4%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
				{
					field : 'ZG_READY_NUM',
					title : '已整改数',
					width : "4%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						return !value?"0":value;
					}
				},
				{
					field : 'ZG_NO_READY_NUM',
					title : '未整改数',
					width : "4%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						if(!row.ZG_READY_NUM || !row.ZG_NUM || row.ZG_READY_NUM == 0 || row.ZG_NUM == 0){
							return 0;
						}else if ( row.ZG_READY_NUM >= row.ZG_NUM ){
							return 0; 
						}else{
							return (row.ZG_NUM - row.ZG_READY_NUM);
						}
					}
				},
				{
					field : 'ZG_READY_NUM_rate',
					title : '整改率(%)',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					formatter : function(value, row, Index) {
						if(!row.ZG_READY_NUM || !row.ZG_NUM || row.ZG_READY_NUM == 0 || row.ZG_NUM == 0){
							return 0;
						}else if ( row.ZG_READY_NUM >= row.ZG_NUM ){
							return "100%"; 
						}else{
							return ((row.ZG_READY_NUM/row.ZG_NUM)*100).toFixed(2)+"%";
						}
					}
				}
				],
				[
					{
						field : 'ERROR_EQP_NUM',
						title : '数量',
						width : 57,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							return !value?"0":value;
						}
					},
					{
						field : 'ERROR_EQP_NUM_rate',
						title : '占比',
						width : 56,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							if(!row.ERROR_EQP_NUM || !row.CHECK_ERROR_NUM || row.ERROR_EQP_NUM == 0 || row.CHECK_ERROR_NUM == 0){
								return 0;
							}else if ( row.ERROR_EQP_NUM >= row.CHECK_ERROR_NUM ){
								return "100%"; 
							}else{
								return ((row.ERROR_EQP_NUM/row.CHECK_ERROR_NUM)*100).toFixed(2)+"%";
							}
						}
					},
					{
						field : 'ERROR_NO_TAG_NUM',
						title : '数量',
						width : 57,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							return !value?"0":value;
						}
					},
					{
						field : 'ERROR_NO_TAG_NUM_rate',
						title : '占比',
						width : 56,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							if(!row.ERROR_NO_TAG_NUM || !row.CHECK_ERROR_NUM || row.ERROR_NO_TAG_NUM == 0 || row.CHECK_ERROR_NUM == 0){
								return 0;
							}else if ( row.ERROR_NO_TAG_NUM >= row.CHECK_ERROR_NUM ){
								return "100%"; 
							}else{
								return ((row.ERROR_NO_TAG_NUM/row.CHECK_ERROR_NUM)*100).toFixed(2)+"%";
							}
						}
					},
					{
						field : 'ERROR_NO_APPEND_ORDER_NUM',
						title : '数量',
						width : 76,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							return !value?"0":value;
						}
					},
					{
						field : 'ERROR_NO_APPEND_ORDER_NUM_rate',
						title : '占比',
						width : 62.5,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							if(!row.ERROR_NO_APPEND_ORDER_NUM || !row.CHECK_ERROR_NUM || row.ERROR_NO_APPEND_ORDER_NUM == 0 || row.CHECK_ERROR_NUM == 0){
								return 0;
							}else if ( row.ERROR_NO_APPEND_ORDER_NUM >= row.CHECK_ERROR_NUM ){
								return "100%"; 
							}else{
								return ((row.ERROR_NO_APPEND_ORDER_NUM/row.CHECK_ERROR_NUM)*100).toFixed(2)+"%";
							}
						}
					},
					{
						field : 'ERROR_ROAD_NO_DESTORY_NUM',
						title : '数量',
						width : 57,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							return !value?"0":value;
						}
					},
					{
						field : 'ERROR_ROAD_NO_DESTORY_NUM_rate',
						title : '占比',
						width : 56,
						rowspan : '2', 
						align : 'center',
						formatter : function(value, row, Index) {
							if(!row.ERROR_ROAD_NO_DESTORY_NUM || !row.CHECK_ERROR_NUM || row.ERROR_ROAD_NO_DESTORY_NUM == 0 || row.CHECK_ERROR_NUM == 0){
								return 0;
							}else if ( row.ERROR_ROAD_NO_DESTORY_NUM >= row.CHECK_ERROR_NUM ){
								return "100%"; 
							}else{
								return ((row.ERROR_ROAD_NO_DESTORY_NUM/row.CHECK_ERROR_NUM)*100).toFixed(2)+"%";
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
			/* var endMonth = now.getMonth()-1; */
			var endMonth = now.getMonth()+1;
			if(endMonth == -2){
				endYear = endYear-1;
				endMonth = 12;
			}else{
				endMonth = endMonth>9?endMonth:"0"+(endMonth<0?"1":endMonth);	
			}
			endDay = "01";
			var endTime = endYear+"-"+endMonth+"-"+endDay;
			$("#task_start_time").val(endTime);
			
			//默认统计结束时间：当天
			var endYear = now.getFullYear();
			var endMonth = now.getMonth()+1;
			if(endMonth == 0){
				endYear = endYear-1;
				endMonth = 12;
			}else{
				endMonth = endMonth>9?endMonth:"0"+(endMonth<0?"1":endMonth);	
			}
	        var endDay = now.getDate();
	        if(endDay==1){
	        	//如果是当月第一天则不减去一天
	        }else{
	        	endDay = endDay-1;	
	        }
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
			
			var check_START_TIME = $("input[name='CHECK_START_TIME']").val();
			var check_end_time = $("input[name='CHECK_COMPLETE_TIME']").val();
			
			
			window.open(webPath
					+ "teamCheckStatictis/exportExcel.do?randomPara=1 "
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
		
			
	</script>
</html>