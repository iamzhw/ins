<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style>
a{text-decoration:none;}
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>错误端子检查报告</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 55px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>	
							<td width="10%">地市：</td>
							<td width="20%">
								<div>
									<select name="area_id" id="area_id" class="condition-select" onchange="getSonAreaList()">
										<option value=''>--请选择--</option>																		
										<%-- <c:forEach items="${area}" var="areaInfo"> 
												<option value='${areaInfo.AREA_ID}'>${areaInfo.NAME}</option>										
										</c:forEach> --%>
									</select>
								</div>
							</td>
							<td width="10%">
								区域：
							</td>
							<td width="20%">
								<select name="SON_AREA_ID" id="son_area" class="condition-select" >
									<option value="">
										请选择
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="10%">端子变动月份：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input name="static_month" id="static_month"
										onFocus="WdatePicker({dateFmt:'yyyy-MM'})" type="text"
										class="condition-text" />
								</div>
							</td>
                            <td width="10%" nowrap="nowrap">检查开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=PSTART_TIME id="p_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">检查结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=PCOMPLETE_TIME id="p_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'p_start_time\')}'});" />
								</div>
							</td>
						</tr>					
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">导出</div>
				<div class="btn-operation" onClick="reform()">重置</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value="" />
			</div>
		</div>
		<table id="dg" title="【错误端子检查报告】" style="width: 100%; height: 480px"></table>
		
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
		initbutton();
		initParameters();
		getAreaList();
		/* searchData(); */
		});
		function initbutton(){
		var isAdmin1 ='${CABLE_ADMIN}';
		if(true ==isAdmin1 || isAdmin1 =='true'){
		
		$("#initbutton").show();
					
				}
		
		};
		function searchData() {
		    
			if($("#p_start_time").val()==''){
				alert("开始日期不能为空！");
				return false;
			}
			if($("#p_end_time").val()==''){
				alert("结束日期不能为空！");
				return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
		
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			
			var static_month = $("input[name='static_month']").val();
		
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var son_area = $("#son_area").val();
			
			if(AREA_ID==null || ""==AREA_ID){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
					 son_area =${sonAreaId};
				}			
			}		
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREA_ID:AREA_ID,
					static_month:static_month,
					PSTART_TIME:PSTART_TIME,
					son_area:son_area,
					PCOMPLETE_TIME:PCOMPLETE_TIME
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "WrongPortReport/wrongPortReport.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				fit : true,
				singleSelect : false,
				remoteSort: false,
				columns : [ [ {
					field : 'CITY',
					title : '地市',
					width : "5%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'AREAID',
					title : '区域',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				},{
					field : 'TOWN',
					title : '区域',
					width : "5%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'EQP_NO',
					title : '设备编码',
					width : "8%",
					rowspan : '2',
					align : 'center'
					
				},{
					field : 'PORT_NO',
					title : '端子编码',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				
				}, {
					field : 'DESCRIPT',
					title : '上报描述',
					width : "10%",
					rowspan : '2', 
					align : 'center'
		        	
		       
				}, {
					field : 'CREATE_TIME',
					title : '检查时间',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'BDSJ',
					title : '端子变动时间',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'GLBH',
					title : '光路编号',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'GLMC',
					title : '光路名称',
					width : "10%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'SGGH',
					title : '施工工号',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				},  {
					field : 'NAME',
					title : '光路性质',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}
				] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
					$("body").resize();
				}
			});
		};
		
		  
		function initParameters(){//初始化查询参数
		var now = new Date();
		//默认设备变动月份
		var changYear = now.getFullYear();
		var changeMonth = now.getMonth();
		if(changeMonth==0){changeMonth=12;changYear=changYear-1;}
		changeMonth = changeMonth>9?changeMonth:"0"+changeMonth;
		var changeTime = changYear+"-"+changeMonth;
		$("#static_month").val(changeTime);
		//默认派发结束时间
		var pendYear = now.getFullYear();
		var pendMonth = now.getMonth()+1;
		pendMonth = pendMonth>9?pendMonth:"0"+pendMonth;
		var pendDay = now.getDate();
		pendDay = pendDay>9?pendDay:"0"+pendDay;
		var pendTime = pendYear+"-"+pendMonth+"-"+pendDay;
		$("#p_end_time").val(pendTime);
		//默认派发任务开始时
		var start = new Date();
		var pstartYear = start.getFullYear();
		var pmonth = now.getMonth();
		var pstartMonth = start.getMonth();
		if(pstartMonth==0){pstartMonth=12;pstartYear=pstartYear-1;}
		pstartMonth = pstartMonth>9?pstartMonth:"0"+pstartMonth;
		var pstartDay = '01';
		var pstartTime = pstartYear+"-"+pstartMonth+"-"+pstartDay;
		$("#p_start_time").val(pstartTime);
		//默认任务结束时间
		var endYear = now.getFullYear();
		var endMonth = now.getMonth()+1;
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		var endDay = now.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_end_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
		if(startMonth==0){startMonth=12;startYear=startYear-1;}
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_start_time").val(startTime);
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
				}else{
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
					/* var result = json.sonAreaList;
					$("select[name='SON_AREA_ID'] option").remove();
					$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
					} */
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
			$("input[name='PSTART_TIME']").val('');
			$("select[name='SON_AREA_ID']").val('');
			$("input[name='PCOMPLETE_TIME']").val('');
			$("select[name='area_id']").val('');
		};
		
		
		
		function exportExcel() {
		
			if($("#p_start_time").val()==''){
					alert("开始日期不能为空！");
					return false;
			}
			if($("#p_end_time").val()==''){
					alert("结束日期不能为空！");
					return false;
			}
			var AREA_ID = $("select[name='area_id']").val();
			var son_area = $("#son_area").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var static_month = $("input[name='static_month']").val();
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			
			if(AREA_ID==null || ""==AREA_ID){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 AREA_ID=${areaId};
					 son_area =${sonAreaId};
				}		
			}	
			 window.open(webPath
					+ "WrongPortReport/exportExcel.do?AREA_ID="+AREA_ID+"&son_area="+son_area+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month
					+"&PSTART_TIME="+PSTART_TIME+"&PCOMPLETE_TIME="+PCOMPLETE_TIME
			); 
		}
		    /* window.open(webPath
					+ "CheckQualityReport/exportExcel.do"
					+ getParamsForDownloadLocal('form'));
			}  */		
			function getParamsForDownloadLocal(idOrDom) {
				if (!idOrDom) {
					return;
				}
				var o;
				if (typeof idOrDom == "string") {
					o = $("#" + idOrDom);
				} else {
					o = $(idOrDom);
				}
				var res = "?randomPara=1";
				o
						.find("input,select")
						.each(
								function() {
									var o = $(this);
									var tag = this.tagName.toLowerCase();
									var name = o.attr("name");
									if (name) {
										if (tag == "select") {
											if (o.find("option:selected").val() == 'all'
													|| o.find("option:selected")
															.val() == '') {
												res = res + "&" + name + "=";
											} else {
												res = res
														+ "&"
														+ name
														+ "="
														+ o.find("option:selected")
																.val();
											}
	
										} else if (tag == "input") {
											res = res + "&" + name + "=" + o.val();
										}
									}
								});					
				return res;
			}
			
			
			
	
		
		
	</script>
</html>