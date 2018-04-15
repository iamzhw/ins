
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>我的任务</title>
	</head>

	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />

		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 110px;">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="selected" value="" />
					<%--				<input type="hidden" name="count" value="" />--%>
					<%--				<input type="hidden" name="type" value="" />--%>
					<%--				<input type="hidden" name="taskIds" value="" />--%>
					<table class="condition">
						<tr>
							<td width="8%">地市：</td>
							<td width="18%">
								<select name="AREA_ID" id="area" class="condition-select" onchange="getSonAreaList()" >
									<option value="">请选择</option>
								</select>
							</td>
							<td width="10%">
								区域：
							</td>
							<td width="20%">
								<select name="son_area" id="son_area" class="condition-select" onchange="getGridList()">
									<option value="">
										请选择
									</option>
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
								任务状态：
							</td>
							<td width="20%">
								<select name="STATUS_ID" class="condition-select">
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
									<!-- <option value="8">
										已归档
									</option> -->
								</select>
							</td>
							<td align="left" width="15%">
								任务名称：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="TASK_NAME" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								任务来源：
							</td>
							<td width="20%">
								<select name="TASK_TYPE" class="condition-select">
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
									<option value="6">资源整治单</option>
								</select>
							</td>							
						</tr>
						<tr>
							<td width="10%" nowrap="nowrap">
								任务开始时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text" value="" 
										name=START_TIME id="task_start_time" onClick="WdatePicker({startDate:'%y-%M-{%d-7}'});" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">
								结束时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=COMPLETE_TIME id="task_end_time"
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'task_start_time\')}'});" />
								</div>
							</td>							
							<td align="left" width="15%">
								检查员：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="INSPECTOR" id="INSPECTOR" class="condition-text" />
								</div>
							</td>							
						</tr>
						<tr>
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
				<!-- </form> -->
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation"  id = "doTask" onClick="doTask('distributeBill')">
					派单
				</div>
				<!-- <div class="btn-operation" onClick="doTask('audit')">
					审核
				</div> -->
				<div class="btn-operation"  id = "audit" onClick="intoAudit()">
					审核
				</div>
				<div class="btn-operation"  id = "transmit" onClick="transmit('transmit')">
					转派
				</div> 
				<div class="btn-operation" id="finish"  onClick="intoFinish()">
					归档
				</div> 
			    <div class="btn-operation" id="revoke"  onClick="revoke('revoke')">
					撤单
				</div> 
				<div class="btn-operation" id="cancel"  onClick="cancelByNJTYJDG1()">
					退单
				</div>
				<div class="btn-operation" id="zhuanFa"  onClick="zhuanFa()">
					转发
				</div>
				<div style="float: right;" class="btn-operation"  
					onClick="exportExcel()">
					导出
				</div>
				<div style="float: right;" class="btn-operation"  
					onClick="searchData()">
					查询
				</div>
				<div  class="btn-operation"
					onClick="clearForm()">
					重置
				</div>
				<!-- <div class="btn-operation" onClick="doTask('receipt')">
					回单
				</div> -->
				<!--<div class="btn-operation" onClick="openConfirmWindow('receipt','确认回单吗？')">
					回单
				</div>
				<div class="btn-operation" onClick="openWindow('remarkW','请选择退单说明')">
					退单
				</div> -->
				

				<input name="area_id" type="hidden" value="" />
				<input name="son_area_id" type="hidden" value="" />
			</div>
		</div>
		<table id="dg" title="【我的任务】" style="width: 100%; height: 480px"></table>
		<div id="win_equip"></div>
		<div id="win_staff"></div>
		<div id="zf_staff"></div>
		<div id="win_port"></div>
		<div id="remarkW" style="padding: 20px 0 10px 50px;">
			<form id="ff" method="post">
				<table>
					<tr>
						<td width="10%">
							备注：
						</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="remark" type="remark" class="condition-text" />
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div style="text-align: left; padding: 10px 0 10px 50px">
				<div class="btn-operation" id="btnEp">
					确定
				</div>
				<div class="btn-operation" id="btnCancel">
					取消
				</div>
			</div>
		</div>
			<!-- 退单说明 -->
		<div id="cancelWindow" style="padding: 20px 0 10px 50px;">
			<form id="cancelReason" method="post">
				<table>
					<tr>
						<td width="10%">
							请输入退单原因：
						</td>						
					</tr>
					<tr>
						<td width="10%">				
							<textarea rows="3" cols="20" id="cancalReasonContent"></textarea>					
						</td>						
					</tr>
				</table>
			</form>
			<div style="text-align: left; padding: 10px 0 10px 20px">
				<div class="btn-operation" id="btnCancelTask" onclick="btnCancelTask()">
					确定
				</div>
				<div class="btn-operation" id="btnSaveTask" onclick="btnSaveTask()">
					取消
				</div>
		   </div>
		</div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
	<script type="text/javascript">
	
	$(document).ready(function() {
		$("#remarkW").hide();
		initParameters();
		displayHideUI();
		$("#cancelWindow").hide();
		openCancelWindow();
		//is_CABLE_SHY();	
		isSonAreaAdmin();
		selectSelected();
		getAreaList(); 
	    searchData(); 
		/* getSonAreaList();       
        $("select[name='son_area']").change(function() {
			//根据area_id，获取区县
			$("input[name='son_area_id']").val($("select[name='son_area']").val());
		}); 页面初始化查询待办任务 */
		$('#btnEp').click(function() {
			doTask('return');
			closleWindow('remarkW');
		});
		$('#btnCancel').click(function() {
			closleWindow('remarkW');
		});
	});
	function openConfirmWindow(operate,message){
		$.messager.confirm('请确认',message,function(r){
			if(r){
				if('receipt'==operate){
					doTask('receipt');
				}else if('audit'==operate){
					doTask('audit');
				}
			}
		});
	}
	function initParameters(){//初始化查询参数
		var now = new Date();
		//默认任务结束时间
		var end = new Date();
       end.setDate(end.getDate()+1);//now=加了就不对了，直接设置就OK了
      var endYear = end.getFullYear();
      var month = now.getMonth();
      var endMonth = end.getMonth()+1; 
       endMonth = endMonth>9?endMonth:"0"+endMonth;
      var endDay = end.getDate();
		endDay = endDay>9?endDay:"0"+endDay;
		var endTime = endYear+"-"+endMonth+"-"+endDay;
		$("#task_end_time").val(endTime);
		//默认任务开始时
		var start = new Date();
		var startYear = start.getFullYear();
		var month = now.getMonth();
		var startMonth = start.getMonth()+1;
		startMonth = startMonth>9?startMonth:"0"+startMonth;
		var startDay = '01';
		var startTime = startYear+"-"+startMonth+"-"+startDay;
		$("#task_start_time").val(startTime);
	}	
	
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
			/* var areaId = $("input[name='area_id']").val();
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
		function searchData() {
			var STATUS_ID = $("select[name='STATUS_ID']").val();
			var TASK_NAME = $("input[name='TASK_NAME']").val();
			var son_area_id = $("select[name='son_area']").val();
			var area_id = $("select[name='AREA_ID']").val();
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
			var TASK_TYPE = $("select[name='TASK_TYPE']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
			
			var equipmentName = $("#EQUIPMENT_NAME").val();
			var inspector = $("#INSPECTOR").val();
			
			
			var whwg = $("#whwg").val();
			var obj = {
					STATUS_ID : STATUS_ID,
					TASK_NAME : TASK_NAME,
					TASK_TYPE : TASK_TYPE,
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					son_area_id:son_area_id,
					whwg:whwg,
					equipmentCode:equipmentCode,
					equipmentName:equipmentName,
					inspector:inspector,
					area_id:area_id
			};
			/* if(area_id == ''){
				alert("请选择地市！！！");
				return;
			} */
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "CableCheck/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
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
					field : 'STATUS',
					title : '状态ID',
					width : "7%",
					rowspan : '2',
					hidden : true
				},{
					field : 'RESULT_STATUS',
					title : 'isSuccessStatus',
					width : "7%",
					rowspan : '2',
					hidden : true
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
					sortable : true,
					nowrap:false
					//fixed: false
				},{
					field : 'TASK_NAME',
					title : '任务名称',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable:true
				}, {
					field : 'TASK_TYPE',
					title : '任务来源',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable:true
				},{
					field : 'TASK_TYPE_ID',
					title : '任务来源ID',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					hidden:true
				}, {
					field : 'STATUS_ID',
					title : '任务状态',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'STATUS',
					title : '任务状态ID',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					hidden:true
				},{
					field : 'INSPECTOR',
					title : '检查员',
					width : "3%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'MAINTOR',
					title : '维护员',
					width : "3%",
					rowspan : '2', 
					align : 'center'
				}, {
					field : 'AUDITOR',
					title : '审核员',
					width : "3%",
					rowspan : '2', 
					align : 'center'
				},{
			        field : 'EQUIPMENT_CODE',
			        title : '设备编码',
					width : "10%",
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
					field : 'INFO',
					title : '整改要求',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'SFYPD',
					title : '是否已派单',
					width : "4%",
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
					width : "7%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'COMPLETE_TIME',
					title : '结束时间',
					width : "7%",
					rowspan : '2', 
					align : 'center',
					sortable:true
				}, {
					field : 'LAST_UPDATE_TIME',
					title : '上次更新时间',
					width : "7%",
					rowspan : '2', 
					align : 'center',
					sortable:true
				}, {
					field : 'TASKID',
					title : '操作',
					width : "3%",
					rowspan : '2', 
					align : 'center',
					formatter:function(value,row,Index){
                		 return "<a href=\"javascript:show("+ row.TASK_ID + ");\">详情</a>";
                		//+ "&nbsp;&nbsp;<a href=\"javascript:show('flow', " + rowData.BUSINESS_ID + ");\">流程</a>";  
                } 
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					$(this).datagrid("fixRownumber");
					$("body").resize();
	                //遍历结果  
	                for ( var i = 0; i < data.rows.length; i++) {  
	                    if (data.rows[i].RESULT_STATUS == "0" && data.rows[i].STATUS == "6") {  
	                    	$("input[type='checkbox']")[i + 1].disabled = "disabled";
	                    	//$("input[type='checkbox']")[i+1].style.display = "none";
	                    }  
	                }  
					
				},
				onClickRow : function(rowIndex, rowData) {  
	                if (rowData.RESULT_STATUS == "0" && rowData.STATUS=="6") {  
	                    $('#dg').datagrid('unselectRow', rowIndex);  
            		}
            	},
            	onSelectAll : function(rows) {  
	                $.each(rows, function(i, n) {
	                    if (n.RESULT_STATUS == "0" && n.STATUS == "6") { 
		                    $('#dg').datagrid('unselectRow', i); 
	            		}
                	}); 
            	},
            	rowStyler:function(index,row){  
			        if (row.RESULT_STATUS == "0"&&row.STATUS == "6"){  
			            return 'background-color:#B8C8C9;';  
			        }  
			    }   
			});
		}

		/**选择行触发**/
		function onClickRow(index, row) {
			alert("onClickRow");
		}
		/**点击checkbox触发**/
		function onCheck(index, row) {
			alert("onCheck");
		}

		function onSelect(index, row) {
			alert("onSelect");
		}

		function onSelectAll(rows) {
			alert(rows);
			alert("onSelectAll");
		}

		function clearForm(){	
			$("select[name='STATUS_ID']").val('');
			$("input[name='TASK_NAME']").val('');
			$("input[name='START_TIME']").val('');
			$("select[name='TASK_TYPE']").val('');
			$("input[name='COMPLETE_TIME']").val('');
			$("select[name='son_area']").val('');
			$("select[name='AREA_ID']").val('');				
			$("#EQUIPMENT_CODE").val('');
			$("#EQUIPMENT_NAME").val('');
			$("#INSPECTOR").val('');			
		}


	     function myCloseTab() {  
			if(parent.$('#tabs').tabs('exists',"任务详情")){
				parent.$('#tabs').tabs('close',"任务详情");
			}
         } 
		 function show(TASK_ID){
		 	myCloseTab();
			addTab("任务详情", "<%=path%>/CableCheck/intoShowEquip.do?TASK_ID="+TASK_ID);
		}


		function openWindow(id,title)
		{
			$('#'+id).window({
							title : title,
							width : 350,
							modal : true,
							shadow : true,
							closed : true,
							height : 250,
							collapsible:false,
							resizable : false,
							closed:true
						});
						$('#'+id).show();
					$('#'+id).window('open');
		}
		
		function closleWindow(id)
		{
			$('#'+id).window('close');
		}
		function doTask(operate){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择工单!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TASK_ID;
					arr[arr.length] = value;
				}
				//remark
				$("input[name='selected']").val(arr);
				var remark = $("input[name='remark']").val();
				if(operate == 'return' || operate == 'archive' || operate == 'audit' 
						|| operate == 'receipt'){
					$.ajax({
						type : 'POST',
						url : webPath + "CableCheck/updateTask.do?operate=" + operate + "&ids=" + arr,
						dataType : 'json',
						data:{remark:remark},
						success : function(json) {
							if(json.status){
								$.messager.show({
									title : '提  示',
									msg : '操作成功!',
									showType : 'show'
								});
								searchData();
							} else {
								$.messager.show({
									title : '提  示',
									msg : json.message,
									showType : 'show'
								});
							}
						}
					});
				} else {
					var status_right = "";//判断是否是待派单的任务
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].STATUS;
						if('4' == value || '5' == value){
							status_right = "OK";
						}else{
							status_right = "NO";//选中的多条任务，有一条状态不是待派单的即提示错误信息
							break;
						}
						
					}
					if("OK" == status_right){
					
					var task_right =0;
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].TASK_TYPE_ID;
						if('1' == value || '2' == value|| '3' == value){
							task_right = task_right+1;
						}else{
							task_right = 0;//选中的多条任务，有一条状态不是整改单
						}
						
					}
					if(selected.length == task_right){
					
						//openWindow('w','选择完成时间');
						$('#win_staff').window({
							title : "【选择人员】",
							href : webPath + "CableCheck/bill_selectStaff.do?operate=" + operate + "&billIds=" + arr,
							width : 460,
							height : 480,
							zIndex : 2,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					}else if (task_right==0){
					 var operate = 'distributeZqBill'; 
					$('#win_staff').window({
							title : "【选择人员】",
							href : webPath + "CableCheck/bill_selectStaff.do?operate=" + operate + "&billIds=" + arr,
							width : 460,
							height : 480,
							zIndex : 2,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					
					}
					else{
					$.messager.show({
							title : '提  示',
							msg : '请全部选择整改单或者全部为检查任务!',
							showType : 'show'
						});
						return;
					
					};
					
					
					
					
					}else{
						$.messager.show({
							title : '提  示',
							msg : '请选择任务状态为待派单的工单!',
							showType : 'show'
						});
						return;
					};
				};
			};
		}

		function transmit(operate){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择工单!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TASK_ID;
					arr[arr.length] = value;
				}
				//remark
				$("input[name='selected']").val(arr);
				var remark = $("input[name='remark']").val();
				if(operate == 'return' || operate == 'archive' || operate == 'audit' 
						|| operate == 'receipt'){
					$.ajax({
						type : 'POST',
						url : webPath + "CableCheck/updateTask.do?operate=" + operate + "&ids=" + arr,
						dataType : 'json',
						data:{remark:remark},
						success : function(json) {
							if(json.status){
								$.messager.show({
									title : '提  示',
									msg : '操作成功!',
									showType : 'show'
								});
								searchData();
							} else {
								$.messager.show({
									title : '提  示',
									msg : json.message,
									showType : 'show'
								});
							}
						}
					});
				} else {
					
					
					
					
					
					
					var task_right =0;
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].TASK_TYPE_ID;
						if('1' == value || '2' == value|| '3' == value){
							task_right = task_right+1;
						}else{
							task_right = 0;//选中的多条任务，有一条状态不是整改单
						}
						
					}
					if(selected.length == task_right){
					
					var status_right = "";//判断是否是待审核的任务
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].STATUS;
						if('7' == value||'6' == value){
							status_right = "OK";
						}else{
							status_right = "NO";//选中的多条任务，有一条状态不是待派单的即提示错误信息
							break;
						}
						
					}
					if("OK" == status_right){
					
						//openWindow('w','选择完成时间');
						$('#win_staff').window({
							title : "【选择人员】",
							href : webPath + "CableCheck/bill_selectStaff.do?operate=" + operate + "&billIds=" + arr,
							width : 460,
							height : 480,
							zIndex : 2,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					}else{
						$.messager.show({
							title : '提  示',
							msg : '请选择任务状态为待审核或待回单的整改单或待回单的检查单!',
							showType : 'show'
						});
						return;
					};
					} else if(task_right == 0){
					
					
						var status_right = "";//判断是否是待回单的任务
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].STATUS;
						if('6' == value){
							status_right = "OK";
						}else{
							status_right = "NO";//选中的多条任务有一条状态不是待派单的即提示错误信息
							break;
						}
						
					}
					if("OK" == status_right){
					
						//openWindow('w','选择完成时间');
						var operate = 'transmitZq'; 
						$('#win_staff').window({
							title : "【选择人员】",
							href : webPath + "CableCheck/bill_selectStaff.do?operate=" + operate + "&billIds=" + arr,
							width : 460,
							height : 480,
							zIndex : 2,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					}
					else{
					$.messager.show({
							title : '提  示',
							msg : '请选择任务状态为待审核的整改单或待回单的检查单!!',
							showType : 'show'
						});
						return;
					
					};
					
					
					
					
					}else{$.messager.show({
							title : '提  示',
							msg : '请选择全部为整改单或全部为检查单!',
							showType : 'show'
						});
						return;
					
					};
					
					
			
				};
			};
		}
		
	//进入审核页面
	function intoAudit(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count != 1) {
			$.messager.show({
				title : '提  示',
				msg : '请选择一条工单!',
				showType : 'show'
			});
			return;
		}else {
			var status = selected[0].STATUS;
			if(status != '7'){
				$.messager.show({
					title : '提  示',
					msg : '请选择待审核工单!',
					showType : 'show'
				});
				return;
			}else{
				var TASK_ID = selected[0].TASK_ID;
				var TASK_TYPE = selected[0].TASK_TYPE_ID;
				$("input[name='selected']").val(TASK_ID);
				addTab("工单审核", "<%=path%>/CableCheck/intoAudit.do?TASK_ID="+TASK_ID+"&TASK_TYPE="+TASK_TYPE);
			}
		}
	}
	//归档
	function intoFinish(){
		var selected = $('#dg').datagrid('getChecked');
		var count = selected.length;
		if (count != 1) {
			$.messager.show({
				title : '提  示',
				msg : '请选择一条工单!',
				showType : 'show'
			});
			return;
		}else {
			var status = selected[0].STATUS;
			if(status != '4'){
				$.messager.show({
					title : '提  示',
					msg : '请选择问题上报、待派单工单!',
					showType : 'show'
				});
				return;
			}else{
				$.messager.confirm('请确认','是否确认归档？',function(r){
					if(r){
						var TASK_ID = selected[0].TASK_ID;
						$.ajax({
								type : 'POST',
								url : webPath + "CableCheck/intoFinish.do?TASK_ID=" + TASK_ID,
								dataType : 'json',
								success : function(json) {
									if(json.status){
										$.messager.show({
											title : '提  示',
											msg : '操作成功!',
											showType : 'show'
										});
										searchData();
									} else {
										$.messager.show({
											title : '提  示',
											msg : '操作失败!',
											showType : 'show'
										});
									}
								}
						});						
					}
				});								
			}
		}
	}
	
	
	function revoke(operate){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择工单!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].TASK_ID;
					arr[arr.length] = value;
				}

				$("input[name='selected']").val(arr);
				var remark = $("input[name='remark']").val();
			
					var task_right =0;
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].TASK_TYPE_ID;
						if('1' == value || '2' == value|| '3' == value){
							task_right = task_right+1;
						}else{
							task_right = 0;//选中的多条任务，有一条状态不是整改单
						}
						
					}
					 if(task_right == 0){
					
					
						var status_right = "";//判断是否是待回单的任务
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].STATUS;
						if('6' == value||'5'==value){
							status_right = "OK";
						}else{
							status_right = "NO";//选中的多条任务有一条状态不是待派单的即提示错误信息
							break;
						}
						
					}
					if("OK" == status_right){
					
						//openWindow('w','选择完成时间');
						 
						$.messager.confirm('请确认','是否确认撤单？',function(r){
						if(r){
						var TASK_ID = selected[0].TASK_ID;
						$.ajax({
								type : 'POST',
								url : webPath + "CableCheck/updateTask.do?operate=" + operate + "&ids=" + arr,
								dataType : 'json',
								success : function(json) {
									if(json.status){
										$.messager.show({
											title : '提  示',
											msg : '操作成功!',
											showType : 'show'
										});
										searchData();
									} else {
										$.messager.show({
											title : '提  示',
											msg : '操作失败!',
											showType : 'show'
										});
									}
								}
						});						
					}
						
						
						});
					}
					else{
					$.messager.show({
							title : '提  示',
							msg : '请选择任务状态为待回单或已退单的检查单!!',
							showType : 'show'
						});
						return;
					
					};
					
					
					
					
					}else{$.messager.show({
							title : '提  示',
							msg : '请选择检查单!',
							showType : 'show'
						});
						return;
					
					};
					
					
			
				
			};
		}
	
	function exportExcel() {
		var STATUS_ID = $("select[name='STATUS_ID']").val();
			var TASK_NAME = $("input[name='TASK_NAME']").val();
			var son_area_id = $("select[name='son_area']").val();
			var area_id = $("select[name='AREA_ID']").val();
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
			var TASK_TYPE = $("select[name='TASK_TYPE']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			var equipmentCode = $("#EQUIPMENT_CODE").val();
			
			var equipmentName = $("#EQUIPMENT_NAME").val();
			var inspector = $("#INSPECTOR").val();
			var whwg = $("#whwg").val();

			 window.open(webPath
					+ "CableCheck/exportExcel.do?area_id="+area_id+"&son_area_id="+son_area_id+"&STATUS_ID="+STATUS_ID+"&TASK_NAME="+TASK_NAME+"&TASK_TYPE="+TASK_TYPE
					+"&START_TIME="+START_TIME+"&COMPLETE_TIME="+COMPLETE_TIME+"&whwg="+whwg+"&equipmentCode="+equipmentCode+"&equipmentName="+equipmentName+"&inspector="+encodeURI(inspector)
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
			//问题上报待派单任务退单，即检查人员检查错了，退单，让其重新检查
			//新的要求是一键反馈、不预告、问题上报任务检查均要有，如果是问题上报则直接退回给原来的检查员，如果是一键反馈或者不预告，则直接将任务删除
			function cancel(){
				var selected = $('#dg').datagrid('getChecked');
				var count = selected.length;
				if (count == 0) {
					$.messager.show({
						title : '提  示',
						msg : '请选择问题上报、待派单工单!',
						showType : 'show'
					});
					return;
				} else {
					var arr = new Array();
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].TASK_ID;
						arr[arr.length] = value;
					}
			
					var status_right = "";//判断是否是问题上报待派单的任务(问题上报，任务检查)
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].STATUS;
						var taskType=selected[i].TASK_TYPE_ID;
						if('4' == value ){//问题上报，任务检查 '3'== taskType
							status_right = "OK";//选中的多条任务都为任务上报待派单
						}else{
							status_right = "NO";//选中的多条任务有一条状态不是问题上报待派单的即提示错误信息
							break;
						}			
					}
					
					if("OK" == status_right){						 
						$.messager.confirm('请确认','是否确认退单？',function(r){
							if(r){
								$.ajax({
										type : 'POST',
										url : webPath + "CableCheck/cancelTask.do?ids=" + arr,
										dataType : 'json',
										success : function(json) {
											if(json.status){
												$.messager.show({
													title : '提  示',
													msg : '操作成功!',
													showType : 'show'
												});
												searchData();
											} else {
												$.messager.show({
													title : '提  示',
													msg : '操作失败!',
													showType : 'show'
												});
											}
										}
								});		
																				
							}
						});
					}else{
						$.messager.show({
							title : '提  示',
							msg : '请选择问题上报、待派单任务!!',
							showType : 'show'
						});
						return;
					}	
				}
			} 
			//判断是否是审核人员
			 function is_CABLE_SHY(){
				if('${CABLE_SHY}'==true||'${CABLE_SHY}'=='true'){
					$("#finish").css('display','');
					$("#revoke").css('display','');
				}
			 } 
			
			function displayHideUI() 
				{ 
				
				if(('${CABLE_WHY}'==true||'${CABLE_WHY}'=='true'||'${CABLE_XJY}'==true||'${CABLE_XJY}'=='true')&&
						((false == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='false')&&('${CABLE_ADMIN}'==false||'${CABLE_ADMIN}'=='false')&&('${CABLE_ADMIN_SONAREA}'==false||'false'=='${CABLE_ADMIN_SONAREA}')&&('${CABLE_SHY}'==false||'false'=='${CABLE_SHY}')
								&&(false == '${CABLE_NJ_SHY_B}'|| '${CABLE_NJ_SHY_B}' =='false')	
						)
				){
					var doTask = document.getElementById("doTask"); 
						doTask.style.display="none"; 
						var audit = document.getElementById("audit"); 
						audit.style.display="none"; 
						var transmit = document.getElementById("transmit"); 
						transmit.style.display="none"; 
							var revoke = document.getElementById("revoke"); 
						revoke.style.display="none";
						var finish = document.getElementById("finish"); 
						finish.style.display="none";  
						
						}  
			 }
			function isSonAreaAdmin(){	
				if(true == '${CABLE_ADMIN_AREA}'|| '${CABLE_ADMIN_AREA}' =='true'||'${CABLE_ADMIN}'==true||'${CABLE_ADMIN}'=='true'){		
					$("#whwg").removeAttr("onfocus");
				}else{
					$("#son_area").removeAttr("onchange");
				}
			}
			
			
			 //退单窗口
			 function openCancelWindow(){
				$('#cancelWindow').window({
					title : '退单',
					width : 360,
					modal : true,
					shadow : true,
					closed : true,
					height : 300,
					resizable : false,
					closable:false
				});
			}
			//关闭退单窗口
			function closeCancelWindow(){
				$('#cancelWindow').window('close');
			}
					
			function cancelByNJTYJDG1(){
				$('#cancelWindow').show();
				$('#cancelWindow').window('open');
			}
			
			function btnCancelTask(){
				var selected = $('#dg').datagrid('getChecked');
				var content=$("#cancalReasonContent").val();
				var count = selected.length;
				if (count == 0) {
					$.messager.show({
						title : '提  示',
						msg : '请选择问题上报、待回单工单!',
						showType : 'show'
					});
					return;
				} else {
					var arr = new Array();
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].TASK_ID;
						arr[arr.length] = value;
					}
			
					var status_right = "";//判断是否是问题上报待派单的任务(问题上报，任务检查)
					for ( var i = 0; i < selected.length; i++) {
						var value = selected[i].STATUS;
						var taskType=selected[i].TASK_TYPE_ID;
						if('6' == value && ('3'== taskType || '2'== taskType || '1'== taskType)){//问题上报3,1,2  自动派发 任务状态 为 6
							status_right = "OK";//选中的多条任务都为任务上报待派单
						}else{
							status_right = "NO";//选中的多条任务有一条状态不是问题上报待派单的即提示错误信息
							break;
						}			
					}
					
					if("OK" == status_right){
						doShow();						 
						$.ajax({
								type : 'POST',
								url : webPath + "CableCheck/cancelTask.do?ids=" + arr+"&content=" + content,
								dataType : 'json',
								success : function(json) {
									closeCancelWindow();
									$.messager.progress('close');
									if(json.status){
										$.messager.show({
											title : '提  示',
											msg : '操作成功!',
											showType : 'show'
										});
										searchData();
									} else {
										$.messager.show({
											title : '提  示',
											msg : '操作失败!',
											showType : 'show'
										});
									}
								}
						});																				
					}else{
						$.messager.show({
							title : '提  示',
							msg : '请选择问题上报、待回单任务!!',
							showType : 'show'
						});
						return;
					}	
				}
			}
			
			function btnSaveTask(){
				closeCancelWindow();
			}
			
			function doShow(){
				$.messager.progress({
					title:'提示',
					msg:'正在退单中,请稍后....',
					interval:500
				});
			}
			
			function zhuanFa(){
				var area=$("#area").val();
				var son_area=$("#son_area").val();
				var selected = $('#dg').datagrid('getChecked');
				var count=selected.length;
				if(count==0){
					$.messager.show({
						title : '提  示',
						msg : '请选择问题上报、待回单工单!',
						showType : 'show'
					});
				}else{
					var mark=0;
					var arr=new Array();
					for(var i=0;i<selected.length;i++){
						var value = selected[i].TASK_ID;
						arr[arr.length] = value;
						var value = selected[i].STATUS;
						var taskType=selected[i].TASK_TYPE_ID;
						if(value== '6' && (taskType=='1'||taskType=='2'||taskType=='3')){
							mark++;
						}
					}
					if(selected.length==mark){
						$('#win_staff').window({
							title : "【选择人员】",
							href : webPath + "CableCheck/selectDouDiStaff.do?billIds="+arr+"&area="+area+"&son_area="+son_area,
							width : 460,
							height : 480,
							zIndex : 4,
							region : "center",
							collapsible : false,
							cache : false,
							modal : true
						});
					}else{
						$.messager.show({
							title : '提  示',
							msg : '请选择问题上报、待回单工单!',
							showType : 'show'
						});
					}
				}
			}
			 
		</script>
	</body>
</html>
