<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>gis坐标校对</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 60px;">
				<form id="form" action="" method="post">
					<table class="condition">
						<tr>
							<td width="8%">地市：</td>
							<td width="16%">
								<select name="AREA_ID" id="area" class="condition-select" onchange="getSonAreaList()" >
									<option value="">请选择</option>
								</select>
							</td>
							<td width="10%">区域：</td>
							<td width="20%">
								<select name="son_area" id="son_area" class="condition-select" onchange="getGridList()">
									<option value="">请选择</option>
								</select>
							</td>
							<td width="10%">
									所属维护网格：
								</td>
							<td width="20%">
									<select name="WHWG" id="whwg" class="condition-select" onfocus="getGridList()">
									<option value="">
										请选择
									</option>
								</select>
							</td>													
						</tr>
						<tr>
							<td width="10%">
								设备类型：
							</td>
							<td width="20%">
								<select name="RES_TYPE_ID" id="sblx" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="411">
										ODF
									</option>
									<!-- <option value="2530">
										分光器
									</option> -->
									<option value="703">
										光交接箱
									</option>
									<option value="704">
										光分纤箱
									</option>
									<option value="414">
										综合配线箱
									</option>
								</select>
							</td>						
							<td align="left" width="15%">
								设备编码：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="EQUIPMENT_CODE" id="EQUIPMENT_CODE" class="condition-text" />
								</div>
							</td>
							<td align="left" width="15%">
								设备名称：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="EQUIPMENT_NAME" id="EQUIPMENT_NAME" class="condition-text" />
								</div>
							</td>							
						</tr>					
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float: left;" class="btn-operation" onClick="reform()">重置</div>
				<div style="float: right;" class="btn-operation" onClick="exportGIS()">导出</div>			   
				<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
			</div>
		</div>
		<table id="dg" title="【gis坐标校对】" style="width: 100%; height: 480px"></table>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
	    	getAreaList();
	    	isSonAreaAdmin();
		});
		function searchData() {
			var son_area_id = $("#son_area").val();
			var area_id = $("#area").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
			var EQUIPMENT_NAME = $("#EQUIPMENT_NAME").val();
			var whwg = $("#whwg").val();
			var sblx = $("#sblx").val();
			
			if(area_id==null || ""==area_id){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}
				if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 area_id=${areaId};
				}
				if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 area_id=${areaId};
					 son_area_id =${sonAreaId};
				}			
			}	
			var obj = {					
					son_area_id:son_area_id,
					whwg:whwg,
					equipmentCode:equipmentCode,
					EQUIPMENT_NAME:EQUIPMENT_NAME,
					area_id:area_id,
					sblx:sblx
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "gisCompare/query.do",
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
					field : 'AREA',
					title : '地市',
					width : "4%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区域',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'ZHHWHWG',
					title : '综合化维护网格',
					width : "8%",
					rowspan : '2',
					align : 'center',
					sortable : true
				},{
					field : 'SBID',
					title : '设备ID',
					width : "10%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'SBBM',
					title : '设备编码',
					width : "10%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'SBMC',
					title : '设备名称',
					width : "10%",
					rowspan : '2',
					align : 'center'
				},{
					field : 'ADDRESS',
					title : '设备地址',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'RES_TYPE',
					title : '设备类型',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'LONGITUDE',
					title : '设备经度',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'LATITUDE',
					title : '设备纬度',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},
				{
					field : 'LONGITUDE_INSPECT',
					title : '设备采集经度',
					width : "10%",
					rowspan : '2',
					align : 'center'
				}, {
					field : 'LATITUDE_INSPECT',
					title : '设备采集纬度',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'STAFFID_INSPECTOR',
					title : '修改人',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'STAFF_LONG_LATI_TIME',
					title : '修改时间',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
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
				$("select[name='AREA_ID'] option").remove();
				$("select[name='AREA_ID']").append("<option value=''>请选择</option>");
				if( true ==isAdmin1 || isAdmin1 =='true'){
					for ( var i = 0; i < result.length; i++) {
							$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true') {
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}					
					}
				}else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${areaId}){
							$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}						
					}
				}	
			}
		});
	}
	
	//获取区域
	function getSonAreaList() {
		 var areaId = $("#area").val(); 	
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
				$("select[name='son_area'] option").remove();
				$("select[name='son_area']").append("<option value=''>请选择</option>");
				if(true ==isAdmin1 || isAdmin1 =='true'){	
					for ( var i = 0; i < result.length; i++) {					
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");												
					}
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 == 'true'){
					for ( var i = 0; i < result.length; i++) {
						$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");					
					}
				}else{
					for ( var i = 0; i < result.length; i++) {
						if(result[i].AREA_ID==${sonAreaId}){
						$("select[name='son_area'] option").remove();
							$("select[name='son_area']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
						}						
					}
				}
			}
		});
	}
		
		//TODO 获取综合化维护网格
		function getGridList() {
			/* var areaId = $("#area").val();
			var sonAreaId = $("select[name='son_area']").val(); */
			var sonAreaId = $("select[name='son_area']").val();
			var areaId = $("select[name='AREA_ID']").val();
			$.ajax({
				type : 'POST',
				url : webPath + "General/getGridList.do",
				data : {
					areaId : areaId,
					sonAreaId : sonAreaId
				},
				dataType : 'json',
				success : function(json) 
				{
					var result = json.gridList;
					$("select[name='WHWG'] option").remove();
					$("select[name='WHWG']").append("<option value=''>请选择</option>");
					for ( var i = 0; i < result.length; i++) {
						$("select[name='WHWG']").append("<option value=" + result[i].GRID_ID + ">"+ result[i].GRID_NAME + "</option>");
					}
				}
			});
		}
		
		function reform(){
			$("#son_area").val('');
			$("#area").val('');
			$("#EQUIPMENT_CODE").val('');
			$("#EQUIPMENT_NAME").val('');
			$("#whwg").val('');
			$("#sblx").val('');
		};
		

		function exportGIS(){
			var SON_AREA_ID = $("#son_area").val();
			var AREA_ID = $("#area").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
			var EQUIPMENT_NAME = $("#EQUIPMENT_NAME").val();
			var whwg = $("#whwg").val();
			var sblx = $("#sblx").val();
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
					 SON_AREA_ID =${sonAreaId};
				}			
			}
			
      		window.open(webPath+ "gisCompare/exportGis.do?AREA_ID=" + AREA_ID+"&SON_AREA_ID="+SON_AREA_ID+"&whwg="+whwg+"&equipmentCode="+equipmentCode+"&EQUIPMENT_NAME="+EQUIPMENT_NAME+"&sblx="+sblx);
        	
		}
				
					
		function isSonAreaAdmin(){
			if(true == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='true'||'${CABLE_ADMIN}'==true||'${CABLE_ADMIN}'=='true'){		
				$("#whwg").removeAttr("onfocus");
			}else{
				$("#son_area").removeAttr("onchange");
			}
			
		}
	</script>
</html>