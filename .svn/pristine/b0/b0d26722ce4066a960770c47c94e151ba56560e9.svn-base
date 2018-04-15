<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>已办任务</title>
	</head>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 135px;">
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
							<td width="10%">任务状态：</td>
							<td width="20%">
								<select name="STATUS_ID" class="condition-select" >
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
							<td align="left" width="15%">任务名称：</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="TASK_NAME" class="condition-text" />
								</div>
							</td>
							<td width="10%">任务来源：</td>
							<td width="20%">
								<select name="TASK_TYPE" class="condition-select">
									<option value="">请选择</option>
									<option value="0">周期性检查</option>
									<option value="1">问题上报（一键反馈）</option>
									<option value="2">问题上报（不预告抽查）</option>
									<option value="3">问题上报（周期性任务检查）</option>
									<option value="4">二次检查</option>
									<option value="6">资源整治单</option>
								</select>
							</td>							
						</tr>
						<tr>
							<td width="10%" nowrap="nowrap">任务开始时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=START_TIME id="task_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=COMPLETE_TIME id="task_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>							
							<td align="left" width="15%">
								设备编码：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="EQUIPMENT_CODE" id="EQUIPMENT_CODE" class="condition-text" />
								</div>
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
							<td width="10%" nowrap="nowrap">检查完成时间起始：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" name=C_START_TIME id="c_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">检查完成时间终止：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" name=C_COMPLETE_TIME id="c_end_time" onClick="WdatePicker({minDate:'#F{$dp.$D(\'c_start_time\')}'});" />
								</div>
							</td>													
						</tr>
						<tr>
							<td align="left" width="15%">
								检查员：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="INSPECTOR" id="INSPECTOR" class="condition-text" />
								</div>
							</td>
							<td align="left" width="15%">
								分光器设备编码：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="pos_eqpCode" id="pos_eqpCode" class="condition-text" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
			    <div style="float: right;" class="btn-operation" onClick="exportExcelByPOrder()">按工单导出</div>
			    <div style="float: right;" class="btn-operation" onClick="exportExcelByEqp()">按设备导出</div>
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">按端子导出</div>
				<div style="float: right;" class="btn-operation" onClick="searchData()">查询</div>
				<div class="btn-operation" onClick="reform()">重置</div>
				
				<div style="float:right;" id = 'doTask' class="btn-operation" onClick="doTask()">
					二次复查派发
				</div>
				<input name="area_id" type="hidden" value="" />
				<input name="son_area_id" type="hidden" value="" />
			</div>
		</div>
		<table id="dg" title="【已办任务】" style="width: 100%; height: 480px"></table>
		<div id="win_staff"></div>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
		    selectSelected();
		    displayHideUI();
	    	getAreaList();
	    	isSonAreaAdmin();
			/* searchData(); */
		/* 	$("select[name='son_area']").change(function() {
				$("input[name='son_area_id']").val($("select[name='son_area']").val());//根据area_id，获取区县
			});  */
			
			
		});
		function searchData() {
			var STATUS_ID = $("select[name='STATUS_ID']").val();
			var TASK_NAME = $("input[name='TASK_NAME']").val();
			var son_area_id = $("select[name='son_area']").val();
			var area_id = $("select[name='AREA_ID']").val();
			var TASK_TYPE = $("select[name='TASK_TYPE']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var C_START_TIME = $("input[name='C_START_TIME']").val();
			var C_COMPLETE_TIME = $("input[name='C_COMPLETE_TIME']").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
			var whwg = $("#whwg").val();
			var sblx = $("#sblx").val();
			var INSPECTOR = $("#INSPECTOR").val();
			var pos_eqpCode = $("#pos_eqpCode").val();
			
			if(area_id==null || ""==area_id){
				var isSonAreaAdmin1 ='${CABLE_ADMIN_SONAREA}';
				var isAreaAdmin1 ='${CABLE_ADMIN_AREA}';
				var isAdmin1 ='${CABLE_ADMIN}';
				if(true ==isAdmin1 || isAdmin1 =='true'){
					
				}else if(true ==isAreaAdmin1 || isAreaAdmin1 =='true'){
				 	 area_id=${areaId};
				}else{
					 area_id=${areaId};
					 son_area_id =${sonAreaId};
				}
				/* if(true ==isSonAreaAdmin1 || isSonAreaAdmin1 =='true'){
				 	 area_id=${areaId};
					 son_area_id =${sonAreaId};
				} */			
			}	
			var obj = {
					STATUS_ID : STATUS_ID,
					TASK_NAME : TASK_NAME,
					TASK_TYPE : TASK_TYPE,
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					C_COMPLETE_TIME:C_COMPLETE_TIME,
					C_START_TIME:C_START_TIME,
					son_area_id:son_area_id,
					whwg:whwg,
					equipmentCode:equipmentCode,
					area_id:area_id,
					sblx:sblx,
					INSPECTOR:INSPECTOR,
					pos_eqpCode:pos_eqpCode
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "DoneTask/queryDoneTask.do",
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
					field : 'TASK_ID',
					title : '任务ID',
					rowspan : '2', 
					checkbox : true
				},{
					field : 'AREA_ID',
					title : '地市',
					width : "3%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'SON_AREA_ID',
					title : '区域',
					width : "4%",
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
					field : 'TASK_NAME',
					title : '任务名称',
					width : "10%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'TASK_TYPE',
					title : '任务来源',
					width : "8%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'STATUS_ID',
					title : '任务状态',
					width : "6%",
					rowspan : '2',
					align : 'center'
				},{
					field : 'INSPECTOR',
					title : '检查员',
					width : "3%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'MAINTOR',
					title : '维护员',
					width : "3%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'AUDITOR',
					title : '审核员',
					width : "3%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'EQUIPMENT_CODE',
					title : '设备编码',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},
				{
					field : 'RES_TYPE',
					title : '设备类型',
					width : "4%",
					rowspan : '2',
					align : 'center'
				}, {
					field : 'BEFORE_CHECK',
					title : '派发端子数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'BEFORE_CHECK_H',
					title : '派发H端子数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'CHECKED',
					title : '检查端子数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'CHECKED_H',
					title : '检查H端子数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'ERROE_CHECK',
					title : '错误端子数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'ERROE_CHECK_H',
					title : '错误H端子数',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'CHECKED_ERROE_CHECK',
					title : '端子检查准确率',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'REMARK',
					title : '现场规范',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'SFZG',
					title : '是否已整改',
					width : "4%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'START_TIME',
					title : '开始时间',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'COMPLETE_TIME',
					title : '结束时间',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'CHECK_COMPLETE_TIME',
					title : '检查完成时间',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'LAST_UPDATE_TIME',
					title : '上次更新时间',
					width : "6%",
					rowspan : '2', 
					align : 'center'
				},{
					field : 'TASKID',
					title : '操作',
					width : "3%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
		        	return "<a href=\"javascript:show("+ row.TASK_ID + ");\">详情</a>";
		        } 
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$(this).datagrid("fixRownumber");
					$("body").resize();
				}
			});
		};
		
		function selectSelected(){
		$.ajax({
			type : 'POST',
			url : webPath + "Staff/selectSelected.do",
			dataType : 'json',
			async:false,
			success : function(json) {	
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
			$("input[name='TASK_NAME']").val('');
			$("input[name='START_TIME']").val('');
			$("select[name='TASK_TYPE']").val('');
			$("input[name='COMPLETE_TIME']").val('');
			$("select[name='son_area']").val('');
			$("select[name='AREA_ID']").val('');
			$("#INSPECTOR").val('');
			$("#pos_eqpCode").val('');
		};
		
		function myCloseTab() {  
			if(parent.$('#tabs').tabs('exists',"设备信息")){
				parent.$('#tabs').tabs('close',"设备信息");
			}
        } 
         
		function show(TASK_ID){
			myCloseTab();
			addTab("设备信息", "<%=path%>/DoneTask/intoShowEquip.do?TASK_ID="+TASK_ID);
		};
		
		function doTask(){
		var selected = $('#dg').datagrid('getChecked');
		
		var count = selected.length;
		var area = $("input[name='area_id']").val();
		var son_area = $("input[name='son_area_id']").val();
		
		if (count == 0) {
			$.messager.show({
				title : '提  示',
				msg : '请选择任务!',
				showType : 'show'
			});
			return;
		} else {
			var arr = new Array();
			//var arrTaskType = new Array();
			/* var pf = $("#pfgz").val(); */
			 var taskTypes =  0;
			for ( var i = 0; i < selected.length; i++) {
				var value = selected[i].TASK_ID;
				var taskType = selected[i].TASK_TYPE;
				arr[arr.length] = value;
				//arrTaskType.push(taskType);
				if(taskType=="周期性任务"){
				taskTypes=taskTypes;
				}
				else{
				taskTypes+=1;
				}
				
			}
			   
			    if(taskTypes>0){
			    $.messager.show({
				title : '提  示',
				msg : '请选择周期性任务!',
				showType : 'show'
			});
			    }else{
				$('#win_staff').window({
					title : "【选择人员】",
					href : webPath + "DoneTask/selectStaff.do?taskIds=" + arr + "&area="+ area + "&son_area="+son_area,
					width : 860,
					height : 480,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
		}
		}}
		function exportExcel(){
			var STATUS_ID = $("select[name='STATUS_ID']").val();
			var TASK_NAME = $("input[name='TASK_NAME']").val();
			var AREA_ID = $("select[name='AREA_ID']").val();
			var SON_AREA_ID = $("select[name='son_area']").val();
			var TASK_TYPE = $("select[name='TASK_TYPE']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
            var sblx = $("#sblx").val();
			var whwg = $("#whwg").val();
			var C_START_TIME = $("input[name='C_START_TIME']").val();
			var C_COMPLETE_TIME = $("input[name='C_COMPLETE_TIME']").val();
			var INSPECTOR = $("input[name='INSPECTOR']").val();
			var pos_eqpCode = $("#pos_eqpCode").val();
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
			var rows = $("#dg").datagrid('getSelections');
			var taskIds = "";
			for(var i=0; i<rows.length; i++){
				taskIds += rows[i].TASK_ID + ",";
			}
			taskIds = taskIds.substring(0,taskIds.length-1);
			var obj = {
					taskIds : taskIds
			};
		/* 	$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {	
            	if (r) {    
                 	$('#form').form('submit', {
	                      url: webPath + "DoneTask/exportExcel.do?taskIds=" + taskIds+"&sblx="+sblx+"&whwg="+whwg+"&equipmentCode="+equipmentCode+"&STATUS_ID="+STATUS_ID+"&TASK_NAME="+TASK_NAME+"&SON_AREA_ID="+SON_AREA_ID+"&TASK_TYPE="+TASK_TYPE+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&AREA_ID="+AREA_ID+"&C_START_TIME="+C_START_TIME+"&C_COMPLETE_TIME="+C_COMPLETE_TIME,
	                      onSubmit: function () {
							    $.messager.progress({							
								title:'提示',
								msg:'正在导出'
   	                 		   }); 
   	                 	  },
	                      success:function () {
	                    	   $.messager.progress('close');
	                           $.messager.show({
	                              title: '提  示',
	                              msg: '导出成功!',
	                              showType: 'show' 
	                       	   }); 
	                      } 
                	}); 
            	}
      		}); */
      		window.open(webPath+ "DoneTask/exportExcel.do?taskIds=" + taskIds+"&sblx="+sblx+"&whwg="+whwg+"&equipmentCode="+equipmentCode+"&STATUS_ID="+STATUS_ID+"&TASK_NAME="+TASK_NAME+"&SON_AREA_ID="+SON_AREA_ID+"&TASK_TYPE="+TASK_TYPE+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&AREA_ID="+AREA_ID+"&C_START_TIME="+
      			C_START_TIME+"&C_COMPLETE_TIME="+C_COMPLETE_TIME+"&INSPECTOR="+encodeURI(INSPECTOR)+"&pos_eqpCode="+pos_eqpCode);
        	
		}
		/*按工单导出*/
		function exportExcelByPOrder(){
			var STATUS_ID = $("select[name='STATUS_ID']").val();
			var TASK_NAME = $("input[name='TASK_NAME']").val();
			var AREA_ID = $("select[name='AREA_ID']").val();
			var SON_AREA_ID = $("select[name='son_area']").val();
			var TASK_TYPE = $("select[name='TASK_TYPE']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
            var sblx = $("#sblx").val();
			var whwg = $("#whwg").val();
			var C_START_TIME = $("input[name='C_START_TIME']").val();
			var C_COMPLETE_TIME = $("input[name='C_COMPLETE_TIME']").val();
			var INSPECTOR = $("input[name='INSPECTOR']").val();
			var pos_eqpCode = $("#pos_eqpCode").val();
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
			var rows = $("#dg").datagrid('getSelections');
			var taskIds = "";
			for(var i=0; i<rows.length; i++){
				taskIds += rows[i].TASK_ID + ",";
			}
			taskIds = taskIds.substring(0,taskIds.length-1);
			var obj = {
					taskIds : taskIds
			};
      		window.open(webPath+ "DoneTask/exportExcelByPOrder.do?taskIds=" + taskIds+"&sblx="+sblx+"&whwg="+whwg+"&equipmentCode="+equipmentCode+"&STATUS_ID="+STATUS_ID+"&TASK_NAME="+TASK_NAME+"&SON_AREA_ID="+SON_AREA_ID+"&TASK_TYPE="+TASK_TYPE+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&AREA_ID="+AREA_ID+"&C_START_TIME="+
      			C_START_TIME+"&C_COMPLETE_TIME="+C_COMPLETE_TIME+"&INSPECTOR="+encodeURI(INSPECTOR)+"&pos_eqpCode="+pos_eqpCode);
        	
		}
		function exportExcelByEqp(){
			var STATUS_ID = $("select[name='STATUS_ID']").val();
			var TASK_NAME = $("input[name='TASK_NAME']").val();
			var area_id = $("select[name='AREA_ID']").val();
			var SON_AREA_ID = $("select[name='son_area']").val();
			var TASK_TYPE = $("select[name='TASK_TYPE']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
			var whwg = $("#whwg").val();
			var sblx = $("#sblx").val();
			var rows = $("#dg").datagrid('getSelections');
			var taskIds = "";
			var C_START_TIME = $("input[name='C_START_TIME']").val();
			var C_COMPLETE_TIME = $("input[name='C_COMPLETE_TIME']").val();
			var INSPECTOR = $("#INSPECTOR").val();
			var pos_eqpCode = $("#pos_eqpCode").val();
			
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
					 SON_AREA_ID =${sonAreaId};
				}			
			}
						
			for(var i=0; i<rows.length; i++){
				taskIds += rows[i].TASK_ID + ",";
			}
			taskIds = taskIds.substring(0,taskIds.length-1);
			var obj = {
					taskIds : taskIds
			};
		/* 	$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
            	if (r) {
                	$('#form').form('submit', {
	                    url: webPath + "DoneTask/exportExcelByEqp.do?taskIds=" + taskIds+"&sblx="+sblx+"&whwg="+whwg+"&area_id="+area_id+"&equipmentCode="+equipmentCode+"&STATUS_ID="+STATUS_ID+"&TASK_NAME="+TASK_NAME+"&SON_AREA_ID="+SON_AREA_ID+"&TASK_TYPE="+TASK_TYPE+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&C_START_TIME="+C_START_TIME+"&C_COMPLETE_TIME="+C_COMPLETE_TIME,
	                    onSubmit: function () {
							$.messager.progress();
   	                 	},
	                    success: function () {
	                    	$.messager.progress('close');
	                         $.messager.show({
	                            title: '提  示',
	                            msg: '导出成功!',
	                            showType: 'show'
	                        }); 
	                    }
                	});
				}
        	}); */
        	window.open(webPath+"DoneTask/exportExcelByEqp.do?taskIds=" + taskIds+"&sblx="+sblx+"&whwg="+whwg+"&area_id="+area_id+"&equipmentCode="+equipmentCode+"&STATUS_ID="+STATUS_ID+"&TASK_NAME="+TASK_NAME
        	+"&SON_AREA_ID="+SON_AREA_ID+"&TASK_TYPE="+TASK_TYPE+"&START_TIME="+START_TIME
        	+"&COMPLETE_TIME="+COMPLETE_TIME+"&C_START_TIME="+C_START_TIME+"&C_COMPLETE_TIME="+C_COMPLETE_TIME+"&INSPECTOR="+encodeURI(INSPECTOR)+"&pos_eqpCode="+pos_eqpCode);
		}
					
		function isSonAreaAdmin(){
			if(true == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='true'||'${CABLE_ADMIN}'==true||'${CABLE_ADMIN}'=='true'){		
				$("#whwg").removeAttr("onfocus");
			}else{
				$("#son_area").removeAttr("onchange");
			}
			
		}
		
		function displayHideUI() 
				{ if(('${CABLE_WHY}'==true||'${CABLE_WHY}'=='true'||'${CABLE_XJY}'==true||'${CABLE_XJY}'=='true')&&((false == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='false')&&('${CABLE_ADMIN}'==false||'${CABLE_ADMIN}'=='false')&&('${CABLE_ADMIN_SONAREA}'==false||'false'=='${CABLE_ADMIN_SONAREA}'))){
					var doTask = document.getElementById("doTask"); 
						doTask.style.display="none"; 
						
						
						}  
			 }
	</script>
</html>