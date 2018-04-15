<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>端子信息</title>
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
    <body style="padding: 3px; border: 0px">
  		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 84px;">
				<form id="form" action="" method="post">
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
							<td width="3%" >
								区域：
							</td>
							<td width="20%" >
								<select name="SON_AREA_ID" id="son_area" class="condition-select" onchange="getGridList()" style="width:96%">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10%" >
								所属维护网格：
							</td>
							<td width="20%" >
								<select name="WHWG" id="whwg" class="condition-select" onfocus="getGridList()" style="width:84%">
									<option value="">
										请选择
									</option>
								</select>
							</td>	
							
							
						</tr>						
						<tr>
							<td width="1%" nowrap="nowrap" >
								端子动态开始时间：
							</td>
							<td width="6%" >
								<div class="condition-text-container" style="width:92%">
									<input class="condition-text condition" type="text"
										name="DZ_START_TIME" id="dz_start_time" onClick="WdatePicker();" style="width:97%" onblur="addDisabled()" onmouseover="dropDisabled()"/>
								</div>
							</td>
							<td width="3%" nowrap="nowrap">
								端子动态结束时间：
							</td>
							<td width="6%" >
								<div class="condition-text-container" style="width:94%"  >
									<input class="condition-text condition" type="text"
										name="DZ_END_TIME" id="dz_end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'dz_start_time\')}'});" style="width:97%" onblur="addDisabled()"  onmouseover="dropDisabled()"  />
								</div>
							</td>
							
							<td width="10%"  >端子变动月份：</td>
							<td width="20%"  >
								<div class="condition-text-container" style="width:82%">
									<input name="static_month" id="static_month"
										onClick="WdatePicker({dateFmt:'yyyy-MM'})" type="text"
										class="condition-text" onblur="addDisabled()" onmouseover="dropDisabled()" style="width:96%"/>
								</div>
							</td>
							
						</tr>				
						<tr>
							<td width="1%" nowrap="nowrap"   >
								检查开始时间：
							</td>
							<td width="6%" >
								<div class="condition-text-container" style="width:92%">
									<input class="condition-text condition" type="text"
										name="check_start_time" id="check_start_time" onClick="WdatePicker();" style="width:97%" />
								</div>
							</td>
							<td width="3%" nowrap="nowrap">
								检查结束时间：
							</td>
							<td width="5%" >
								<div class="condition-text-container" style="width:94%"  >
									<input class="condition-text condition" type="text"
										name="check_end_time" id="check_end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'dz_start_time\')}'});" />
								</div>
							</td>
							<td align="left" width="1%"  >设备类型：</td>
							<td align="left" width="6%"> 
								<input type="hidden" id="res_type_id"  />
								<div class="div1 condition-text-container" style="width:82%" >
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
						</tr>
						<!-- <tr>
							<td width="10%">任务状态：</td>
							<td width="20%">
								<select name="STATUS_ID" id="STATUS_ID" class="condition-select" style="width:94%">
								<option value="">
										请选择
									</option>
									<option value="4">
										问题上报,待派单
									</option>
									<option value="5">
										退单,待派单
									</option>
									<option value="6">
										待回单
									</option>
									<option value="7">
										已整改回单,待审核
									</option>
									<option value="8">已归档</option>
								</select>
							</td>
							
							<td width="10%">
								任务来源：
							</td>
							<td width="20%">
								<select name="TASK_TYPE" id="task_type" class="condition-select" style="width:97%">
									<option value="">
										请选择
									</option>
									<option value="0">
										周期性检查
									</option>
									<option value="1">
										问题上报（一键反馈）
									</option>
									<option value="2">
										问题上报（不可预告抽查）
									</option>
									<option value="3">
										问题上报（任务检查）
									</option>
									<option value="4">二次检查</option>
								</select>
							</td>	
						</tr> -->
					</table>
				</form>
			</div>
			
			<div class="btn-operation-container">
				<div style="float:right;margin-right:30px" class="btn-operation" onClick="searchData()">查询</div>
				<div style="float:right;" class="btn-operation" onClick="exportPortsRecord()">导出</div>
			</div>
		</div>
		<table id="dg" title="【端子所在设备记录】" style="width: 100%; height: 480px"></table>
	</body>
	<script type="text/javascript">
		$(document).ready(function(){
			getAreaList();
		    isSonAreaAdmin();
			//searchData();
		});
		
		function searchData() {
			var area = $("#area").val();
			var son_area = $("#son_area").val();
			var whwg = $("#whwg").val();
			var static_month = $("#static_month").val();//端子变动月份
			var dz_start_time = $("#dz_start_time").val();//端子动态开始时间
			var dz_end_time = $("#dz_end_time").val();//端子结束开始时间  
			//var STATUS_ID = $("#STATUS_ID").val();//任务状态  check_start_time
			var check_start_time = $("#check_start_time").val();//检查开始时间
			var check_end_time = $("#check_end_time").val();//检查结束时间
		    //var task_type = $("#task_type").val();
		    var RES_TYPE_ID = ''; 		
			var res_type_id=$("#res_type_id").val();
			if(res_type_id!=""){
				RES_TYPE_ID=RES_TYPE_ID+res_type_id;
			}
		    
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
			
			var obj = {
				area:area,
				son_area:son_area,
				whwg:whwg,
				static_month:static_month,
				dz_start_time:dz_start_time,
				dz_end_time:dz_end_time,
				//STATUS_ID:STATUS_ID,
				check_start_time:check_start_time,
				check_end_time:check_end_time,
			    RES_TYPE_ID:RES_TYPE_ID
			   // task_type:task_type
			};
			$('#dg').datagrid(
					{
						//此选项打开，表格会自动适配宽度和高度。
						autoSize : true,
						toolbar : '#tb',
						url : webPath + "CheckRecord/queryCheckRecord.do",
						queryParams : obj,
						method : 'post',
						pagination : true,
						pageNumber : 1,
						pageSize : 10,
						pageList : [20,50],
						rownumbers : true,
						fit : true,
						singleSelect : false,
						remoteSort: false,
						columns : [ [								
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
									field : 'GRID_NAME',
									title : '综合化维护网格',
									width : "5%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQP_ID',
									title : '设备ID',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQP_NO',
									title : '设备编码',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'EQP_NAME',
									title : '设备名称',
									width : "10%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'PORT_ID',
									title : '端子ID',
									width : "12%",
									rowspan : '2',
									align : 'center'
								},{
									field : 'PORT_NO',
									title : '端子编码',
									width : "12%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'GLBH',
									title : '光路编号',
									width : "7%",
									rowspan : '2',
									align : 'center'
								},
								{
									field : 'BDSJ',
									title : '变动时间',
									width : "7%",
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
										return "<a href=\"javascript:showPorts('"
												+ row.PORT_ID + "');\">端子记录</a>";
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
		
	function exportPortsRecord(){
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var static_month = $("#static_month").val();//端子变动月份
		var dz_start_time = $("#dz_start_time").val();//端子动态开始时间
		var dz_end_time = $("#dz_end_time").val();//端子结束开始时间  
		//var STATUS_ID = $("#STATUS_ID").val();//任务状态  check_start_time
		var check_start_time = $("#check_start_time").val();//检查开始时间
		var check_end_time = $("#check_end_time").val();//检查结束时间
	    //var task_type = $("#task_type").val();
	    var RES_TYPE_ID = ''; 		
		var res_type_id=$("#res_type_id").val();
		if(res_type_id!=""){
			RES_TYPE_ID=RES_TYPE_ID+res_type_id;
		}
		window.open(webPath+"CheckRecord/exportPortsRecord.do?area=" + area+"&son_area="+son_area+"&whwg="+whwg+"&static_month="+static_month
			+"&dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&check_start_time="+check_start_time
			+"&check_end_time="+check_end_time+"&RES_TYPE_ID="+RES_TYPE_ID)   
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
	
	function showPorts(POTR_ID){
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var static_month = $("#static_month").val();//端子变动月份
		var dz_start_time = $("#dz_start_time").val();//端子动态开始时间
		var dz_end_time = $("#dz_end_time").val();//端子动态结束时间  
		var check_start_time = $("#check_start_time").val();//检查开始时间
		var check_end_time = $("#check_end_time").val();//检查结束时间		
		var  url="<%=path%>/CheckRecord/portsRecord.do?dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&POTR_ID="+POTR_ID+"&static_month="+static_month+"&check_start_time="+check_start_time+"&check_end_time="+check_end_time+"&son_area="+son_area+"&whwg="+whwg;
		addTab("端子信息", url);
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
		//如果是省管理员或市管理员----保留onchange事件，则onfocus事件删除，这样就可以避免纯粹是维护员、审核员、巡检员看不到维护网格的情况
		if(true == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='true'||'${CABLE_ADMIN}'==true||'${CABLE_ADMIN}'=='true'){		
			$("#whwg").removeAttr("onfocus");
		}else{
			$("#son_area").removeAttr("onchange");
		}
	}
	
	function addDisabled(){
		var static_month = $('#static_month').val();
		var dz_start_time = $('#dz_start_time').val();
		var dz_end_time = $('#dz_end_time').val();
		if(static_month!=""){
			document.getElementById("dz_start_time").disabled =true;
			document.getElementById("dz_end_time").disabled =true;
		}
		if(dz_start_time!=""&&dz_end_time!=""){
			document.getElementById("static_month").disabled =true;
		}
	}
	
	function dropDisabled(){
		var static_month = $('#static_month').val();
		var dz_start_time = $('#dz_start_time').val();
		var dz_end_time = $('#dz_end_time').val();
		if(static_month==""){
			document.getElementById("dz_start_time").disabled =false;
			document.getElementById("dz_end_time").disabled =false;
		}
		if(dz_start_time==""&&dz_end_time==""){
			document.getElementById("static_month").disabled =false;
		}
	}	
		
	</script>
</html>
