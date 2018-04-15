
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>新增任务</title>
</head>
<style type="text/css">
   .div_content{
        display:none;
        position: absolute;
        /* top: 400px; */
        width: 190px;
        margin-left:0px;
        height: 150px;
        margin-top:3px;
        padding: 0;
        background-color: #F5F5F5; 
        z-index:1011;
        overflow: hidden;
        padding-top: 6px;
	}
	.res_type{
		margin-top: 10px;
	}
	.button {
		display: inline-block;
		position: relative;
		margin: 8px;
		margin-left: 14px;
		padding: 0 6px;
		text-align: center;
		text-decoration: none;		
		text-shadow: 1px 1px 1px rgba(255,255,255, .22);
		-webkit-border-radius: 8px;
		-moz-border-radius: 8px;
		border-radius: 8px;
		-webkit-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		-moz-box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);
		box-shadow: 1px 1px 1px rgba(0,0,0, .29), inset 1px 1px 1px rgba(255,255,255, .44);

		-webkit-transition: all 0.15s ease;
		-moz-transition: all 0.15s ease;
		-o-transition: all 0.15s ease;
		-ms-transition: all 0.15s ease;
		transition: all 0.15s ease;
	}
</style>
<script type="text/javascript">

	$(document).ready(function() {
	initParameters();
		getAreaList();
		isSonAreaAdmin();
		/* $('#choices').css('display', 'none'); */
	});
	
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
	
	//获取综合化维护网格
	function getGridList() {
		var areaId = $("#area").val();
		var sonAreaId = $("#son_area").val();
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
	
	
	function initParameters(){//初始化查询参数
		var now = new Date();
		
		//默认任务结束时间
		var endYear = now.getFullYear();
		var endMonth = now.getMonth();
		if(endMonth==0){endMonth=12;endYear=endYear-1;}
		endMonth = endMonth>9?endMonth:"0"+endMonth;
		 var myDate = new Date(endYear, endMonth, 0);
        var endDay =  myDate.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#end_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth();
		if(startMonth==0){startMonth=12;startYear=startYear-1;}
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#start_time").val(startTime);
	}
	function searchData() {
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var sblx = $("#sblx").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var equipmentName = $("#EQUIPMENT_NAME").val();
		var equipmentAddress = $("#EQUIPMENT_ADDRESS").val();
		var ish = $("#ISH").val();
		var isxz = $("#ISXZ").val();
		var is_deal = $("#is_deal").val();
	
	    var RES_TYPE_ID = ''; 
		
	   /*  var RES_TYPE_ID1 = '';
		var RES_TYPE_ID2 = '';
		var RES_TYPE_ID3 = '';
		var RES_TYPE_ID4 = '';  */ 
		
	
		
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
	/* 	if(area == ''){
			alert("请选择地市！！！");
			return;
		} */		
		if(area==null || ""==area){
			var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
			var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				
			}
			if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
			 	 area=${areaId};
			}
			if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
			 	 area=${areaId};
				 son_area =${sonAreaId};
		    }		    
		}
	   /*  var type1 = document.getElementsByName('RES_TYPE_ID1');
		var type2 = document.getElementsByName('RES_TYPE_ID2');
		var type3 = document.getElementsByName('RES_TYPE_ID3');
		var type4 = document.getElementsByName('RES_TYPE_ID4');  */
						
     	/* for(var i=0;i<type1.length;i++){
			if(type1[i].checked){
				RES_TYPE_ID1+=type1[i].value;
			}
		}
		for(var i=0;i<type2.length;i++){
			if(type2[i].checked){
				RES_TYPE_ID2+=type2[i].value;
			}
		}
		for(var i=0;i<type3.length;i++){
			if(type3[i].checked){
				RES_TYPE_ID3+=type3[i].value;
			}
		}
		for(var i=0;i<type4.length;i++){
			if(type4[i].checked){
				RES_TYPE_ID4+=type4[i].value;
			}
		} */  
		
		
		
		

		var obj = {
			start_time:start_time,
			end_time:end_time,
			area:area,
			son_area:son_area,
			whwg:whwg,
			mange_id:mange_id,
			sblx:sblx,
			equipmentCode:equipmentCode,
			equipmentName:equipmentName,
			equipmentAddress:equipmentAddress,
			
	      is_deal:is_deal,
		   /*  RES_TYPE_ID1:RES_TYPE_ID1,
			RES_TYPE_ID2:RES_TYPE_ID2,
			RES_TYPE_ID3:RES_TYPE_ID3,
			RES_TYPE_ID4:RES_TYPE_ID4 */
		    RES_TYPE_ID:RES_TYPE_ID
		};
		
	//无分组
			$('#dg').datagrid(
					{
						//此选项打开，表格会自动适配宽度和高度。
						autoSize : true,
						toolbar : '#tb',
						url : webPath + "CableTaskManger/electronArchives.do",
						queryParams : obj,
						method : 'post',
						pagination : true,
						pageNumber : 1,
						pageSize : 10,
						pageList : [200,500,1000,5000,10000, 20000],
						//loadMsg:'数据加载中.....',
						rownumbers : true,
						fit : true,
						singleSelect : false,
						remoteSort: false,
						columns : [ [
								{
									field : 'SBIDS',
									title : '设备ID',
									rowspan : '2',
									checkbox : true
								},
								{
									field : 'AREA',
									title : '地市',
									width : "5%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'SON_AREA',
									title : '区域',
									width : "5%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQUIPMENT_NAME',
									title : '设备名称',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQUIPMENT_CODE',
									title : '设备编码',
									width : "12%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQUIPMENT_ADDRESS',
									title : '设备地址',
									width : "12%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'ZHHWHWG',
									title : '综合化维护网格',
									width : "8%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'MANAGE_AREA_ID',
									title : '管理区编码',
									width : "10%",
									rowspan : '2',
									align : 'center',
									sortable : true
								},
								{
									field : 'RES_TYPE',
									title : '设备类型',
									width : "7%",
									rowspan : '2',
									align : 'center'
								},{
								field : 'ISDEAL',
								title : '是否整治',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'DEAL_TIME',
								title : '整治时间',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
								{
									field : 'TASKID',
									title : '操作',
									width : "10%",
									rowspan : '2',
									align : 'center',
									formatter : function(value, row, Index) {
										return "<a href=\"javascript:viewDevice('"
												+ row.SBIDS + "');\">查看设备</a>";
									}
								} ] ],
						nowrap : false,
						striped : true,
						onLoadSuccess : function(data) {
							$(this).datagrid("fixRownumber");
							$("body").resize();
						}
					});
		
		
	}

	function doTask(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		
		if(son_area == ''){
			alert("请选择区域！！！");
			return;
		}
		
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选择设备!',
				showType : 'show'
			});
			return;
		} else {
			var arr = new Array();
			/* var pf = $("#pfgz").val(); */
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].SBIDS;
				arr[arr.length] = value;
			}
				$('#win_staff').window({
					title : "【选择人员】",
					href : webPath + "CableTaskManger/electronArchivesSelectStaff.do?sbIds=" + arr + "&area="+ area + "&son_area="+son_area,
					width : 860,
					height : 480,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
	}
  
	function myCloseTab() {  
		if(parent.$('#tabs').tabs('exists',"查看设备")){
			parent.$('#tabs').tabs('close',"查看设备");
		}
    } 
	
	function viewDevice(SBIDS){
		myCloseTab();
		var area = $("#area").val();
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		//var  url=webPath +"CableTaskManger/viewDevices.do?start_time="+start_time+"&end_time="+end_time+"&area="+area+"&EQUIPMENT_ID="+SBIDS;
		var  url="<%=path%>/CableTaskManger/viewDevice.do?dz_start_time="+start_time+"&dz_end_time="+end_time+"&area="+area+"&EQUIPMENT_ID="+SBIDS;
		
		addTab("查看设备",url);
	}
	
	function exportExcel(){
		/* var RES_TYPE_ID1 = '';
		var RES_TYPE_ID2 = '';
		var RES_TYPE_ID3 = '';
		var RES_TYPE_ID4 = '';
		var type1 = document.getElementsByName('RES_TYPE_ID1');
		var type2 = document.getElementsByName('RES_TYPE_ID2');
		var type3 = document.getElementsByName('RES_TYPE_ID3');
		var type4 = document.getElementsByName('RES_TYPE_ID4');  */
		var RES_TYPE_ID='';
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
		var is_deal = $("#is_deal").val();
	
	

	   /*  for(var i=0;i<type1.length;i++){
			if(type1[i].checked){
				RES_TYPE_ID1+=type1[i].value;
			}
		}
		for(var i=0;i<type2.length;i++){
			if(type2[i].checked){
				RES_TYPE_ID2+=type2[i].value;
			}
		}
		for(var i=0;i<type3.length;i++){
			if(type3[i].checked){
				RES_TYPE_ID3+=type3[i].value;
			}
		}
		for(var i=0;i<type4.length;i++){
			if(type4[i].checked){
				RES_TYPE_ID4+=type4[i].value;
			}
		}  */
	
	
	
		var start_time = $("#start_time").val();
		var end_time = $("#end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var sblx = $("#sblx").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		
	/* if(area == ''){
			alert("请选择地市！！！");
			return;
		} */
		if(area==null || ""==area){
			var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
			var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
			var isAdmin1 ='${CABLE_ADMIN}';
			if(true ==isAdmin1 || isAdmin1 =='true'){
				
			}
			if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
			 	 area=${areaId};
			}
			if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
			 	 area=${areaId};
				 son_area =${sonAreaId};
		    }		    
		}
		window.open(webPath
			+ "CableTaskManger/exportExcelByElectron.do?start_time="+start_time+"&end_time="+end_time+"&area="+area+"&son_area="+son_area+"&whwg="+whwg+"&mange_id="+mange_id+"&sblx="+sblx
			+"&equipmentCode="+equipmentCode+"&RES_TYPE_ID="+RES_TYPE_ID+"&is_deal="+is_deal);					
	}
	
	
	
	function showDiv(){
		$('#exchange').toggle();
	}
	function getChoice(){
		var arr=document.getElementsByName("checkbox");
		var values="";
		var contents="";
		for(i=0;i<arr.length;i++){
			if(arr[i].checked){
			var value = arr[i].value;
			var content= arr[i].nextSibling.nodeValue;
			values=values+value+",";
			contents=contents+content+",";			
			}
		}
		values=values.substring(0,values.length-1);
		contents=contents.substring(0,contents.length-1);
		$('#res_type_id').val(values);
		$('#res_type_content').val(contents);
		$('#exchange').css('display', 'none');
	    
	}
	function removeChoice(){
		$("[name='checkbox']").removeAttr('checked');
		$('#res_type_id').val('');
		$('#res_type_content').val('');
	}
	
	function isSonAreaAdmin(){
		//如果是区管理员---保留onfocus事件,则onchange事件删除，这样会导致纯粹的维护员、审核员、巡检员看不到维护网格
		/* if(true =='${CABLE_ADMIN_SONAREA}' || '${CABLE_ADMIN_SONAREA}' =='true'){
			$("#son_area").removeAttr("onchange");
		}else{
			$("#whwg").removeAttr("onfocus");
		} */
		//如果是省管理员或市管理员----保留onchange事件，则onfocus事件删除，这样就可以避免纯粹是维护员、审核员、巡检员看不到维护网格的情况
		if(true == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='true'||'${CABLE_ADMIN}'==true||'${CABLE_ADMIN}'=='true'){		
			$("#whwg").removeAttr("onfocus");
		}else{
			$("#son_area").removeAttr("onchange");
		}
	}
	
</script>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
	    <!-- <div id="1" style="width:1750px; height: 818px;overflow: auto;"> -->
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 120px;">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="selected" value="" />
					<table class="condition" style="width:98%;">
						 <tr>
							<td width="3%"  >
								地市：
							</td>
							<td width="20%"  >
								<select name="AREA_ID" id="area" class="condition-select" onchange="getSonAreaList()" style="width:94%">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="3%"   >
								区域：
							</td>
							<td width="20%" >
								<select name="SON_AREA_ID" id="son_area" class="condition-select" onchange="getGridList()" style="width:96%">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="19%" >
								所属维护网格：
							</td>
							<td width="20%" >
								<select name="WHWG" id="whwg" class="condition-select" onfocus="getGridList()" style="width:96%">
									<option value="">
										请选择
									</option>
								</select>
							</td>			
						</tr>						
						<tr>
							<td width="1%" nowrap="nowrap"   >
								开始时间：
							</td>
							<td width="6%" >
								<div class="condition-text-container" style="width:92%">
									<input class="condition-text condition" type="text"
										name="START_TIME" id="start_time" onClick="WdatePicker();" style="width:97%" />
								</div>
							</td>
							<td width="3%" nowrap="nowrap">
								结束时间：
							</td>
							<td width="5%" >
								<div class="condition-text-container" style="width:94%"  >
									<input class="condition-text condition" type="text"
										name="END_TIME" id="end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}'});" />
								</div>
							</td>
							<td align="left" width="8%">
								管理区编码：
							</td>
							<td width="10%" align="left">
								<div class="condition-text-container" style="width:92%">
									<input name="MANAGE_AREA_ID" id="mange_id" class="condition-text" style="width:97%" />
								</div>
							</td>
						</tr>
					
						<tr>
							<td align="left" width="1%" >设备编码：</td>
							<td width="6%" align="left" >
								<div class="condition-text-container" style="width:92%">
									<input name="EQUIPMENT_CODE" id="EQUIPMENT_CODE" class="condition-text"style="width:97%"  />
								</div>
							</td>
							<td align="left" width="3%">设备名称：</td>
							<td width="5%" align="left">
								<div class="condition-text-container" style="width:94%">
									<input name="EQUIPMENT_NAME" id="EQUIPMENT_NAME" class="condition-text" />
								</div>
							</td>
							<td align="left" width="8%">设备地址：</td>
							<td width="10%" align="left">
								<div class="condition-text-container" style="width:92%">
									<input name="EQUIPMENT_ADDRESS" id="EQUIPMENT_ADDRESS" class="condition-text"style="width:97%" />
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" width="1%"  >设备类型：</td>
							<td align="left" width="6%"> 
								<input type="hidden" id="res_type_id"  />
								<div class="div1 condition-text-container" style="width:92%" >
									<input type="text" id="res_type_content" class="condition-text" onclick="showDiv()"style="width:97%" />
								</div>	
								<div id="exchange" class="div_content">	
									<input type="checkbox" id="res_type_id1" class="res_type" name="checkbox" value="411">ODF<br/>
									<input type="checkbox" id="res_type_id2" class="res_type" name="checkbox" value="703">光交接箱<br/>
									<input type="checkbox" id="res_type_id3" class="res_type" name="checkbox" value="704">光分纤箱<br/>
									<input type="checkbox" id="res_type_id4" class="res_type" name="checkbox" value="414">综合配线箱<br/>
									<input type="button" id="confirm" class="res_type button " value="确定"onclick=" getChoice()">  <input type="button" id="reset" class="res_type button "value="重置"onclick="removeChoice()"> 		
								</div>	
							</td>
							<!-- <td width="25%" align="left">
								<select name="RES_TYPE_ID" id="res_type_id" class="condition-select" multiple="multiple"size="1">
									<option value=""selected="selected">所有</option>						
									<option value="411">ODF</option>
									<option value="703">光交接箱</option>
									<option value="704">光分纤箱</option>
									<option value="414">综合配线箱</option>
								</select>
							</td> -->
						
							<!-- <td width="18%" align="left">
								<div>
									<input type="checkbox" value="411" name="RES_TYPE_ID1"  >ODF
									<input type="checkbox" value="703" name="RES_TYPE_ID2" style="margin-left: 10px;  ">光交接箱
									<input type="checkbox" value="704" name="RES_TYPE_ID3" style="margin-left: 10px;">光分纤箱
								    <input type="checkbox" value="414" name="RES_TYPE_ID4" style="margin-left: 10px;">综合配线箱														
								</div>
							</td> -->
							<td width="3%"   >
								是否整治：
							</td>
							<td width="20%" >
								<select name="is_deal" id="is_deal" class="condition-select"  style="width:96%">
									<option value="1">
										已整治
									</option>
									<option value="0">
										未整治
									</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div class="btn-operation-container">
					
				
				<div style="float:right;margin-right:30px" class="btn-operation" onClick="searchData()">查询</div>
				<div style="float:right;" class="btn-operation" onClick="doTask()">派发</div> 
				<div style="float:right;" class="btn-operation" onClick="exportExcel()">导出</div>
				
			</div>
		</div>
		<table id="dg" title="【设备管理】" style="width: 100%; height: 480px"></table>
		<!-- </div> -->
		<div id="win_staff"></div>
	</body>
</html>
