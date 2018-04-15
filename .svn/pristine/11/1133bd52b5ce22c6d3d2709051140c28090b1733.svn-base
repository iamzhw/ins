<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
a{text-decoration:none;}
</style>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>现场复查统计</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 30px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>	
							<td width="10%">地市：</td>
							<td width="20%">
								<div>
									<select name="area_id" id="area_id" class="condition-select" onchange="getSonAreaList()">
										<option value=''>--请选择--</option>																		
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
							<td width="10%" nowrap="nowrap">复查开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=REVIEW_START_TIME id="review_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">复查结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=REVIEW_COMPLETE_TIME id="review_complete_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'review_start_time\')}'});" />
								</div>
							</td>	
						</tr>
						<tr>
							<td width="10%">检查员：</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="INSPECTOR" id="INSPECTOR" class="condition-text" />
								</div>
							</td>
						</tr>				
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="reform()">重置</div>
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">统计导出</div>
				<div style="float: right;" class="btn-operation" onClick="exportDetailExcel()">清单导出</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">统计查询</div>	
				<div style="float: right;" class="btn-operation" onClick="searchDataByPortRecords()">清单查询</div>			
			</div>

		</div>
		<table id="dg" title="【现场复查统计】" style="width: 100%; height: 480px"></table>
		
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			getAreaList();
		
		});
		//统计列表
		function searchData() {
		    
			if($("#review_start_time").val()==''){
				alert("复查开始日期不能为空！");
				return false;
			}
			if($("#review_complete_time").val()==''){
				alert("复查结束日期不能为空！");
				return false;
			} 
			
			var AREA_ID = $("select[name='area_id']").val();
			var SON_AREA_ID = $("select[name='SON_AREA_ID']").val();		
			var REVIEW_START_TIME = $("input[name='REVIEW_START_TIME']").val();
			var REVIEW_COMPLETE_TIME = $("input[name='REVIEW_COMPLETE_TIME']").val();
			var INSPECTOR = $("input[name='INSPECTOR']").val();
			
			var obj ={
					AREA_ID:AREA_ID,
					SON_AREA_ID:SON_AREA_ID,
					AREA_ID:AREA_ID,
					REVIEW_START_TIME:REVIEW_START_TIME,
					REVIEW_COMPLETE_TIME:REVIEW_COMPLETE_TIME,
					INSPECTOR:INSPECTOR
			};
			
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Review/reviewRecords.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CITYNAME',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NAME',
					title : '区域',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '检查员',
					width : "8%",
					rowspan : '2', 
					align : 'center'
		        },{
					field : 'REVIEWRECORDS',
					title : '复查端子数',
					width : "8%",
					rowspan : '2', 
					align : 'center'
		        },{
					field : 'SAMERECORDS',
					title : '复查一致端子数',
					width : "10%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'CHECK_REVIEW',
					title : '自查准确率',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$(this).datagrid("fixRownumber");
					$("body").resize();
				}
			});
		}
		//清单列表
		function searchDataByPortRecords() {
		    
			if($("#review_start_time").val()==''){
				alert("复查开始日期不能为空！");
				return false;
			}
			if($("#review_complete_time").val()==''){
				alert("复查结束日期不能为空！");
				return false;
			}
			
			var AREA_ID = $("select[name='area_id']").val();
			var SON_AREA_ID = $("select[name='SON_AREA_ID']").val();		
			var REVIEW_START_TIME = $("input[name='REVIEW_START_TIME']").val();
			var REVIEW_COMPLETE_TIME = $("input[name='REVIEW_COMPLETE_TIME']").val();
			var INSPECTOR = $("input[name='INSPECTOR']").val();

			var obj ={
					AREA_ID:AREA_ID,
					SON_AREA_ID:SON_AREA_ID,
					AREA_ID:AREA_ID,
					REVIEW_START_TIME:REVIEW_START_TIME,
					REVIEW_COMPLETE_TIME:REVIEW_COMPLETE_TIME,
					INSPECTOR:INSPECTOR
			};
			
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "Review/reviewDetailRecords.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'CITYNAME',
					title : '地市',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'NAME',
					title : '区域',
					width : "7%",
					rowspan : '2', 
				    align : 'center'
				}, {
					field : 'EQPNAME',
					title : '设备名称',
					width : "8%",
					rowspan : '2', 
					align : 'center'
		        },{
					field : 'EQPNO',
					title : '设备编码',
					width : "8%",
					rowspan : '2', 
					align : 'center'
		        },{
					field : 'SBLX',
					title : '设备类型',
					width : "10%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'DZNO',
					title : '端子编码',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'CHECKSTAFF',
					title : '检查人员',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'CHECKTIME',
					title : '检查时间',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'CHECK_ISCHECKOK',
					title : '检查结果',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'CHECK_DESCRIPT',
					title : '不合格原因',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'REVIEWSTAFF',
					title : '复查人员',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'REVIEWTIME',
					title : '复查时间',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'REVIEW_ISCHECKOK',
					title : '复查结果',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				},{
					field : 'CHECK_REVIEW_RESULT',
					title : '是否一致',
					width : "10%",
					rowspan : '2', 
					align : 'center'					
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$(this).datagrid("fixRownumber");
					$("body").resize();
				}
			});
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
		    $("select[name='area_id']").val('');
			$("select[name='SON_AREA_ID']").val('');		
		    $("input[name='REVIEW_START_TIME']").val('');
			$("input[name='REVIEW_COMPLETE_TIME']").val('');
			$("input[name='INSPECTOR']").val('');
		};
		
		function exportExcel(){
			if($("#review_start_time").val()==''){
				alert("复查开始日期不能为空！");
				return false;
			}
			if($("#review_complete_time").val()==''){
				alert("复查结束日期不能为空！");
				return false;
			}
			
			var AREA_ID = $("select[name='area_id']").val();
			var SON_AREA_ID = $("select[name='SON_AREA_ID']").val();		
			var REVIEW_START_TIME = $("input[name='REVIEW_START_TIME']").val();
			var REVIEW_COMPLETE_TIME = $("input[name='REVIEW_COMPLETE_TIME']").val();
			var REVIEW_COMPLETE_TIME = $("input[name='REVIEW_COMPLETE_TIME']").val();
			var INSPECTOR = $("input[name='INSPECTOR']").val();
			 window.open(webPath
					+ "Review/exportExcel.do?AREA_ID="+AREA_ID+"&SON_AREA_ID="+SON_AREA_ID+"&REVIEW_START_TIME="+REVIEW_START_TIME+"&REVIEW_COMPLETE_TIME="+REVIEW_COMPLETE_TIME+"&REVIEW_COMPLETE_TIME="+REVIEW_COMPLETE_TIME+"&INSPECTOR="+INSPECTOR							
		     );
		
		}
		
		function exportDetailExcel(){
			if($("#review_start_time").val()==''){
				alert("复查开始日期不能为空！");
				return false;
			}
			if($("#review_complete_time").val()==''){
				alert("复查结束日期不能为空！");
				return false;
			}
			
			var AREA_ID = $("select[name='area_id']").val();
			var SON_AREA_ID = $("select[name='SON_AREA_ID']").val();		
			var REVIEW_START_TIME = $("input[name='REVIEW_START_TIME']").val();
			var REVIEW_COMPLETE_TIME = $("input[name='REVIEW_COMPLETE_TIME']").val();
			var INSPECTOR = $("input[name='INSPECTOR']").val();
			
			 window.open(webPath
					+ "Review/exportDetailExcel.do?AREA_ID="+AREA_ID+"&SON_AREA_ID="+SON_AREA_ID+"&REVIEW_START_TIME="+REVIEW_START_TIME+"&REVIEW_COMPLETE_TIME="+REVIEW_COMPLETE_TIME+"&INSPECTOR="+INSPECTOR						
		     );
		
		}
				
	</script>
</html>