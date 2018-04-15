<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<style>
a{text-decoration:none;}
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
				<div style="float: right;" class="btn-operation" onClick="reform()">重置</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
				<input name="area_id" type="hidden" value="" />
			</div>
		</div>
		<table id="dg" title="【市对县检查报告】" style="width: 100%; height: 480px"></table>
		
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
				url : webPath + "CityCheckTownReport/cityCheckTownReport.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				remoteSort: false,
				columns : [ [ {
					field : 'NAME',
					title : '分公司',
					//width : "5%",
					rowspan : '2', 
				    align : 'center'
				},{title:'省公司现场不预告检查',align:'center',colspan:2},
				{title:'省级远程抽检（电子档案库）',align:'center',colspan:2},
				{title:'市对县的检查工作量达标',align:'center',colspan:6},
				{
					field : 'N1',
					title : '月度资源类通报内容缺失项数',
					//width : "14%",
					rowspan : '2', 
				    align : 'center'
				},{title:'省公司抽检客响定单验收情况',align:'center',colspan:2},
				{title:'省公司复核资源整治类工单',align:'center',colspan:2},
				{title:'省公司复核市公司自查准确率',align:'center',colspan:2}
				],[{
					field : 'NUM1',
					title : '检查端子数量',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM2',
					title : '光路标签不规范数量（处）',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM3',
					title : '检查设备数量',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM4',
					title : '发现标签不规范数量（处）',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM5',
					title : '未整治光交检查量不达标的区县数',
					//width : "16%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM6',
					title : '已整治光交检查量不达标的区县数',
					//width : "16%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM7',
					title : '未整治ODF检查量不达标的区县数',
					//width : "16%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM8',
					title : '已整治ODF检查量不达标的区县数',
					//width : "16%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM9',
					title : '已整治小区检查量不达标的区县数',
					//width : "16%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM10',
					title : '远程检查量是否达标',
					//width : "20%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM11',
					title : '检查总数',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM12',
					title : '验收未留痕数量',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM13',
					title : '检查总数',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM14',
					title : '未整治或整治不达标数量',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM15',
					title : '检查总数',
					//width : "50%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NUM16',
					title : '分公司自查不准确的数量',
				    //width : "50%",
					rowspan : '2', 
				    align : 'center'
				}] ],
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
		
		//默认派发结束时间
		var endYear = now.getFullYear();
		var endMonth = now.getMonth();
		if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		 var myDate = new Date(endYear, endMonth, 0);
        var endDay =  myDate.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#p_end_time").val(endTime);
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
				url : webPath + "CityCheckTownReport/getSonAreaList.do",
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
					+ "CityCheckTownReport/exportExcel.do?AREA_ID="+AREA_ID+"&son_area="+son_area+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&static_month="+static_month
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