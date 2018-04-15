<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<style type="text/css">
.table_border td {
	border-top: 1px #DDD solid;
	border-right: 1px #DDD solid;
	border-color: initial;
}

.table_border {
	border-bottom: 1px #DDD solid;
	border-left: 1px #DDD solid;
	border-color: initial;
	width: 100%
}

.label_custom {
	font-weight: blod;
	font-size: large;
}
</style>

<head>
<%@include file="../../util/head.jsp"%>

<script type="text/javascript">
	
  	$(document).ready(function(){
  		
  		$("#tabs").tabs({
  			 onSelect:function(title){    
  			 
  			 	if(title == "基本信息"){
  			 		
  			 		
  			 		
  			 	}else if(title == "机械操作手信息"){
  			 		searchData(1);
  			 	} else if(title == "检查记录"){
  			 		searchData(3);
  			 	}else if(title=="签到信息"){
  			 		searchData(4);
  			 	}else if(title=="停留信息"){
  			 		searchData(5);
  			 	}else if(title="埋深探位"){
  			 		searchData(6);
  			 	}
  			 	
  			 }
  		});
  		
  	});
	
  	
  	function searchData(type){
  		if(type==1){
            $('#jxs').datagrid({
				
				autoTabSize : true,
				toolbar : '#tb1',
				url : webPath + "mainOutSiteController/getCzs.do",
				queryParams: {
					out_site_id:${OUT_SITE_ID}
				},
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ 
					{
						field : 'OPERATOR_ID',
						title : 'ID',
						checkbox : true
					}, {
						field : 'OPERATOR_NAME',
						title : '姓名',
						width : "10%",
						align : 'center'
					},{
						field : 'CAR_NO',
						title : '车牌号码',
						width : "10%",
						align : 'center'
					}, {
						field : 'CAR_TYPE',
						title : '车辆类型',
						width : "10%",
						align : 'center'
					},{
						field : 'MOBILE',
						title : '手机号码',
						width : "10%",
						align : 'center'
					},  {
						field : 'TEL1',
						title : '小灵通号码',
						width : "10%",
						align : 'center'
					}, {
						field : 'TEL2',
						title : '固定电话号码',
						width : "10%",
						align : 'center'
					},{
						field : 'INFO',
						title : '外力影响信息',
						width : "10%",
						align : 'center'
					}, {
						field : 'UPLOAD_TIME',
						title : '填写时间',
						width : "10%",
						align : 'center'
					}, {
						field : 'USER_ID',
						title : '填写人员',
						width : "10%",
						align : 'center'
					}
							  
				] ],
				width : 'auto',
				rowStyler: function(index,row){
					
				},			
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					
				}
			});
  		}else if(type==3){
  			 $('#checkRecord').datagrid({
 				
 				autoTabSize : true,
 				toolbar : '#tb3',
 				url : webPath + "mainOutSiteController/getCheckRecord.do",
 				queryParams: {
 					out_site_id:${OUT_SITE_ID}
 				},
 				method : 'post',
 				//pagination : true,
 				//pageNumber : 1,
 				//pageSize : 10,
 				//pageList : [ 5, 10, 20, 50 ],
 				loadMsg:'数据加载中.....',
 				rownumbers : true,
 				singleSelect : true,
 				columns : [ [ 
          		{
 						field : 'OUT_RECORD_ID',
 						title : 'ID',
 						checkbox : true
 					}, {
 						field : 'CHECK_TIME',
 						title : '检查时间',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'CREATE_PERSON',
 						title : '检查人',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'OUT_SITE_NAME',
 						title : '看护现场名称',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'IS_ON_OUT',
 						title : '看护人员在场情况',
 						width : "10%",
 						align : 'center',
 						formatter : function(value, rec,
								index) {
							if (value=='1') {
								return "是";
							} else {
								return "否";
							}
 							
						}
 					},{
 						field : 'IS_CONSTRUCTION',
 						title : '现场是否施工',
 						width : "10%",
 						align : 'center',
 						formatter : function(value, rec,
								index) {
							if (value=='1') {
								return "是";
							} else {
								return "否";
							}
 							
						}
 					},{
 						field : 'IS_COMPLETE_EQUIP',
 						title : '看护人员防护用品是否完整',
 						width : "10%",
 						align : 'center',
 						formatter : function(value, rec,
								index) {
							if (value=='1') {
								return "是";
							} else {
								return "否";
							}
 							
						}
 					},{
 						field : 'MISS_ITEM_NAME',
 						title : '缺项名称',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'IS_COMPLETE_LOOK',   
 						title : '看护记录是否完整',
 						width : "10%",
 						align : 'center',
 						formatter : function(value, rec,
								index) {
							if (value=='1') {
								return "是";
							} else {
								return "否";
							}
 							
						}
 					},{
 						field : 'MISS_ITEM_RECORD',
 						title : '看护记录缺项名',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'IS_KNOW_CON',
 						title : '现场施工计划知否',
 						width : "10%",
 						align : 'center',
 						formatter : function(value, rec,
								index) {
							if (value=='1') {
								return "是";
							} else {
								return "否";
							}
 							
						}
 					},{
 						field : 'IS_THAN_TEN_MAC',
 						title : '现场施工机械是否在10M范围内',
 						width : "10%",
 						align : 'center',
 						formatter : function(value, rec,
								index) {
							if (value=='1') {
								return "是";
							} else {
								return "否";
							}
 							
						}
 					},{
 						field : 'MISS_ITEM_MAC',
 						title : '施工机械缺项名称',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'IS_KNOW',
 						title : '线路位置和埋深熟悉情况',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'LEADER_TYPE',
 						title : '领导人类型',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'LEADER_NAME',
 						title : '领导人名称',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'PROBLEM_MES',
 						title : '发现问题',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'SIDE_PROGRESS',
 						title : '施工进度',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'RECTIFICATION',
 						title : '整改情况',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'ASSESS',
 						title : '考核情况',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'SUB_COMPARY_NO',
 						title : '分公司编号',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'SUB_COMPARY_NAME',
 						title : '分公司名称',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'AREA_NAME',
 						title : '所属地市',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'CREATION_TIME',
 						title : '创建时间',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'UPDATE_TIME',
 						title : '修改时间',
 						width : "10%",
 						align : 'center'
 					},{
 						field : 'UPDATED_PERSON',
 						title : '修改人',
 						width : "10%",
 						align : 'center'
 					}
 							  
 				] ],
 				width : 'auto',
 				rowStyler: function(index,row){
 					
 				},			
 				nowrap : false,
 				striped : true,
 				onLoadSuccess : function(data) {
 					
 				}
 			});
  		}else if(type==4){
  			
  			var obj=makeParamJson('signform');
  			obj.out_site_id=${OUT_SITE_ID}
  			
			$('#signInfo').datagrid({
				
				autoTabSize : true,
				toolbar : '#tb4',
				url : webPath + "mainOutSiteController/getSignInfo.do",
				queryParams:obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ 
					{
						field : 'SIGN_ID',
						title : 'ID',
						checkbox : true
					}, {
						field : 'SIGN_TIME',
						title : '签到时间',
						width : "20%",
						align : 'center'
					}, {
						field : 'STAFF_NAME',
						title : '签到人',
						width : "20%",
						align : 'center'
					},{
						field : 'SITE_DISTANCE',
						title : '误差距离',
						width : "20%",
						align : 'center'
					}
					
							  
				] ],
				width : 'auto',
				rowStyler: function(index,row){
					
				},			
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					
				}
			});
  		}else if(type==5){
  			var obj=makeParamJson('stayform');
  			obj.out_site_id=${OUT_SITE_ID}
  			
			$('#stayInfo').datagrid({
				
				autoTabSize : true,
				toolbar : '#tb5',
				url : webPath + "mainOutSiteController/getStayInfo.do",
				queryParams:obj,
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ 
					{
						field : 'STAFF_NAME',
						title : '签到人',
						width : "20%",
						align : 'center'
					},{
						field : 'MIN_SIGN_TIME',
						title : '到达时间',
						width : "20%",
						align : 'center'
					},{
						field : 'MAX_SIGN_TIME',
						title : '离开时间',
						width : "20%",
						align : 'center'
					},{
						field : 'STAY_TIME',
						title : '停留时间',
						width : "20%",
						align : 'center',
						formatter : function(value, rec,
								index) {
							return value+"分";
						}
					}
					
							  
				] ],
				width : 'auto',
				rowStyler: function(index,row){
					
				},			
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					
				}
			});
  		}else if(type==6){
  			$('#mstw').datagrid({
				
				autoTabSize : true,
				toolbar : '#tb6',
				url : webPath + "mainOutSiteController/getMstwInfo.do",
				queryParams:{
					out_site_id:${OUT_SITE_ID}
				},
				method : 'post',
				//pagination : true,
				//pageNumber : 1,
				//pageSize : 10,
				//pageList : [ 5, 10, 20, 50 ],
				loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : true,
				columns : [ [ 
					{
						field : 'PROBE_ID',
						title : 'id',
						checkbox : true
					},{
						field : 'UPLOAD_TIME',
						title : '日期',
						width : "10%",
						align : 'center'
					},{
						field : 'USER_NAME',
						title : '填写人',
						width : "10%",
						align : 'center'
					},{
						field : 'MARKSTONE',
						title : '标石号',
						width : "10%",
						align : 'center'
						
					},{
						field : 'STAY_TIME',
						title : '位置',
						width : "10%",
						align : 'center'
						
					},{
						field : 'MSTW_DEPTH',
						title : '深度',
						width : "10%",
						align : 'center'
						
					},{
						field : 'REMARK',
						title : '备注',
						width : "20%",
						align : 'center'
						
					}
					
							  
				] ],
				width : 'auto',
				rowStyler: function(index,row){
					
				},			
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
					
				}
			});
  		}
  	}
	
		function getCable() {

			$('#win_affactedCable').window({
				title : "【选择光缆】",
				href : webPath + "mainOutSiteController/getCableByAreaId.do?affected_fiber="+'${AFFECTED_FIBER}',
				width : 250,
				height : 300,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
		}

		
		
		function showTheCheckedCable() {
			var affected_fiber = '';
			var theCheckedCableNames = '';

			$("input[name='tbcable']").each(function() {
					if (this.checked) {
					
						var v = this.value.split(":");
						affected_fiber = affected_fiber + v[0] + ',';
						theCheckedCableNames = theCheckedCableNames + v[1] + ',';
					}

				});

			$("#affactedCable").val("");
			$("#affactedCable").val(theCheckedCableNames);
			if(affected_fiber!=''){
				affected_fiber=affected_fiber.substring(0,affected_fiber.length-1);
			}
			$("#affected_fiber").val(affected_fiber);

		};
		
		
		function csjMakesure2(){
			var date=new Date();
			var time=date.toLocaleString();
			$("#csjMakesure").val($("#staffNo").val()+" "+time);
		}
		
		function updateForm() {
			var pass = $("#formEdit").form('validate');
			if (pass) {
				$.messager.confirm(
								'系统提示',
								'您确定要更新外力点吗?',
								function(r) {
									if (r) {
										var obj = makeParamJson('formEdit');
										
										if($("#is_plan").attr("checked")){
											obj.is_plan='1';
										}else{
											obj.is_plan='0';
										}
										
										$.ajax({
													type : 'POST',
													url : webPath
															+ "mainOutSiteController/mainOutSiteUpdate.do",
													data : obj,
													dataType : 'json',
													success : function(json) {
														if (json.status) {
															$.messager
																	.show({
																		title : '提  示',
																		msg : '更新外力点成功!',
																		showType : 'show',
																		timeout : '1000'//ms
																	});
														}
														
														
													}
												})
									}
								});
			}
		}
		
		
		function setEponsible(v){
			var va=v.split(":");
			
			$("#fiber_uid").val(va[0]);
			$("#fiber_eponsible_by").val(va[1]);
		}
		
		
		function czsEditUI(){
			var selected = $('#jxs').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选取一条数据!',
					showType : 'show',
					 timeout:'1000'//ms
				});
				return;
			} else if (count > 1) {
				$.messager.show({
					title : '提  示',
					msg : '仅能选取一条数据!',
					showType : 'show',
					timeout:'1000'//ms
				});
				return;
			} else {
				var operator_id = selected[0].OPERATOR_ID;
			
				$('#win_jxs').window({
					title : "【机械手编辑】",
					href : webPath + "mainOutSiteController/jxsEditUI.do?operator_id=" + operator_id,
					width : 400,
					height : 400,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
				
				
			}

		}
		
		 function updateJxs(){
			   var pass = $("#jxsUpdate").form('validate');
				if (pass) {
					$.messager.confirm(
									'系统提示',
									'您确定要更机械手信息吗?',
									function(r) {
										if (r) {
											var obj = makeParamJson('jxsUpdate');
											
											
											
											$.ajax({
														type : 'POST',
														url : webPath
																+ "mainOutSiteController/jxsUpdate.do",
														data : obj,
														dataType : 'json',
														success : function(json) {
															if (json.status) {
																$.messager
																		.show({
																			title : '提  示',
																			msg : '更新机械手信息成功!',
																			showType : 'show',
																			timeout : '1000'//ms
																		});
																$("#win_jxs").window('close');
																searchData(1);
															}
															
															
														}
													})
										}
									});
				}
		   }
		 
		 
		 
		 function mstwEditUI(){
				var selected = $('#mstw').datagrid('getChecked');
				var count = selected.length;
				if (count == 0) {
					$.messager.show({
						title : '提  示',
						msg : '请选取一条数据!',
						showType : 'show',
						 timeout:'1000'//ms
					});
					return;
				} else if (count > 1) {
					$.messager.show({
						title : '提  示',
						msg : '仅能选取一条数据!',
						showType : 'show',
						timeout:'1000'//ms
					});
					return;
				} else {
					var probe_id = selected[0].PROBE_ID;
				
					$('#win_mstw').window({
						title : "【埋深探位编辑】",
						href : webPath + "mainOutSiteController/mstwEditUI.do?probe_id=" + probe_id,
						width : 400,
						height : 400,
						zIndex : 2,
						region : "center",
						collapsible : false,
						cache : false,
						modal : true
					});
					
					
				}

			}
		 
		 
		 function updateMstw(){
			 var pass = $("#mstwUpdate").form('validate');
				if (pass) {
					$.messager.confirm(
									'系统提示',
									'您确定要更新埋深探位信息吗?',
									function(r) {
										if (r) {
											var obj = makeParamJson('mstwUpdate');
											
											
											
											$.ajax({
														type : 'POST',
														url : webPath
																+ "mainOutSiteController/mstwUpdate.do",
														data : obj,
														dataType : 'json',
														success : function(json) {
															if (json.status) {
																$.messager
																		.show({
																			title : '提  示',
																			msg : '更新埋深探位信息成功!',
																			showType : 'show',
																			timeout : '1000'//ms
																		});
																$("#win_mstw").window('close');
																searchData(6);
															}
															
															
														}
													})
										}
									});
				}
		 }
	</script>

<title>外力点编辑</title>


</head>
<body class="easyui-tabs" id="tabs" style="border-style: none;">
	<!-- style="padding:20px 50px 10px 50px"   -->
	<div title="基本信息" data-options="closable:false,selected: true"
		style="overflow:auto;">

		<form id="formEdit" method="post">
			<input type="hidden" id="out_site_id" name="out_site_id"
				value="${OUT_SITE_ID}"> <input type="hidden"
				id="affected_fiber" name="affected_fiber" value=""> <input
				type="hidden" id="staffNo" name="staffNo" value="${staffNo}">


			<table class="table_border">

				<tr>
					<td>外力施工点名称：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="site_name" id="site_name" value="${SITE_NAME}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />

						</div></td>
					<td><div class="">
							施工开始日期： <input type="text" class='condition-text-container'
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
								value="${CON_STARTDATE}" name="con_startdate" id="con_startdate"
								style="width:70px">
						</div></td>
					<td><div class="">
							预计结束： <input type="text" class='condition-text-container'
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd'});"
								value="${PRE_ENDDATE}" name="pre_enddate" id="pre_enddate"
								style="width:70px">
						</div></td>
					<td>施工单位 ：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="con_company" id="con_company"
								value="${CON_COMPANY }"
								/>
						</div></td>



				</tr>
				<tr>
					<td>外力施工状态：</td>
					<td>
						<div class="">
							<select id="con_status" name="con_status"
								class="condition-select">
								<option value="1"
									<c:if test="${CON_STATUS=='1'}">selected</c:if>>外力点施工中</option>
								<option value="2"
									<c:if test="${CON_STATUS=='2'}">selected</c:if>>外力点施工结束</option>


							</select>
						</div></td>

					<td>施工结束日期：</td>
					<td>
						<div class="">--</div></td>
					<td>施工地址：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="con_address" id="con_address"
								value="${CON_ADDRESS}"
								 />
						</div></td>
				</tr>
				<tr>

					<td><div class="">
							经度： <input type="text" class='condition-text-container'
								value="${X}" name="x" name="x" style="width:80px">
						</div></td>
					<td><div class="">
							纬度： <input type="text" class='condition-text-container'
								value="${Y}" name="y" name="x" style="width:80px">
						</div></td>

					<td>施工方现场负责人：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" value="${CON_REPONSIBLE_BY}" id="con_reponsible_by"
								name="con_reponsible_by"
								 />
						</div></td>
					<td>施工内容：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="con_content" id="con_content"
								value="${CON_CONTENT}"
								 />
						</div></td>
				</tr>
				<tr>
					<td>信息来源：</td>
					<td>
						<div class="">
							<select name="info_source" id="info_source"
								class="condition-select">
								<option value="1"
									<c:if test="${INFO_SOURCE=='1'}">selected</c:if>>日常巡回时现场发现</option>
								<option value="2"
									<c:if test="${INFO_SOURCE=='2'}">selected</c:if>>对外联系宣传时得知</option>
								<option value="3"
									<c:if test="${INFO_SOURCE=='3'}">selected</c:if>>施工/建设方主动得知</option>
								<option value="4"
									<c:if test="${INFO_SOURCE=='4'}">selected</c:if>>其他途径（电话800）</option>
							</select>
						</div></td>

					<td>施工方负责人联系电话：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" value="${CON_REPONSIBLE_BY_TEL}"
								id="con_reponsible_by_tel" name="con_reponsible_by_tel"
								 />
						</div></td>
					<td>现场采取的措施：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="scene_measure" id="scene_measure"
								value="${SCENE_MEASURE}"
								 />
						</div></td>
				</tr>
				<tr>
					<td>维护单位及班组：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" 
								value="${orgName}"  readonly="readonly"/>

						</div></td>

					<td>电信方现场负责人：</td>
					<td>
						<div class="">
							<input type="hidden" id="fiber_uid" name="fiber_uid"
								value="${FIBER_UID}"> <input type="hidden"
								id="fiber_eponsible_by" name="fiber_eponsible_by"
								value="${FIBER_EPONSIBLE_BY}"> <select
								class="condition-select" onchange="setEponsible(this.value)" readonly="readonly">
								<option value="${FIBER_UID}:${FIBER_EPONSIBLE_BY}">${FIBER_UID}</option>
								

							</select>
						</div></td>
					<td>处理结果：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" type="text" />
						</div></td>
				</tr>
				<tr>
					<td>受影响光缆：</td>
					<td rowspan="2" colspan="1">
						<div class="">

							<textarea class="" rows="2" cols="10" name="affactedCable"
								id="affactedCable">${AFFECTED_FIBER_NAMES}</textarea>
							
						</div></td>

					<td>电信方现场负责人联系电话：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="${fiber_eponsible_tel}"
								id="${fiber_eponsible_tel}" value="${FIBER_EPONSIBLE_TEL }"
								 />
						</div></td>
					<td>检查人：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" type="text" />
						</div></td>
				</tr>
				<tr>


					<td></td>



					<%--<td>现场第一监护人：</td>
					<td>

						<div class="condition-text-container">
							<input class="condition-text  condition" type="text"
								name="guardian" id="guardian" value="${GUARDIAN}" />
						</div></td>
					--%><td>是否签有协议：</td>
					<td>
						<div class="">
							是<input type="radio" name="is_agreement" value="1"
								<c:if test="${IS_AGREEMENT=='1'}">checked='checked'</c:if> /> 否<input
								type="radio" name="is_agreement" value="0"
								<c:if test="${IS_AGREEMENT=='0'}">checked='checked'</c:if> />
						</div></td>
				</tr>
				<tr>
					<td>干线等级：</td>
					<td><div class="">
							<select ame="fiber_level" id="fiber_level"
								class="condition-select">
								<option value="1"
									<c:if test="${FIBER_LEVEL=='1'}">selected</c:if>>一级</option>
								<option value="2"
									<c:if test="${FIBER_LEVEL=='2'}">selected</c:if>>二级</option>
							</select>
						</div></td>

					<%--<td>第一监护人负责时段：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="stay_time_part" id="stay_time_part"
								value="${STAY_TIME_PART}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
					--%><td>填报时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" type="text" />
						</div></td>
				</tr>
				<tr>
					<td>中继段 ：</td>
					<td>
						<div class="">
							<input name="relay_part" id="relay_part" value="${RELAY_PART}" />
						</div></td>

					<%--<td>第一监护人联系电话：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="guardian_tel" id="guardian_tel"
								value="${GUARDIAN_TEL}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>
					--%><td>外力影响等级：</td>

					<td>
						<div class="">
							<select name="site_danger_level" id="site_danger_level"
								class="condition-select">
								<option value="1"
									<c:if test="${SITE_DANGER_LEVEL=='1'}">selected</c:if>>Ⅰ</option>
								<option value="2"
									<c:if test="${SITE_DANGER_LEVEL=='2'}">selected</c:if>>Ⅱ</option>
								<option value="3"
									<c:if test="${SITE_DANGER_LEVEL=='3'}">selected</c:if>>Ⅲ</option>
								<option value="4"
									<c:if test="${SITE_DANGER_LEVEL=='4'}">selected</c:if>>隐患</option>


							</select>
						</div></td>
				</tr>
				<tr>
					<td>光缆等级：<select style="width: 80px" id="FIBER_LEVEL"
						name="FIBER_LEVEL">
							<option value="1"
								<c:if test="${FIBER_LEVEL=='1'}">selected</c:if>>T</option>
							<option value="2"
								<c:if test="${FIBER_LEVEL=='2'}">selected</c:if>>A</option>
							<option value="3"
								<c:if test="${FIBER_LEVEL=='3'}">selected</c:if>>B</option>
							<option value="4"
								<c:if test="${FIBER_LEVEL=='4'}">selected</c:if>>C</option>
					</select></td>
					<td><div class="">
							纤芯数： <input type="text" class='condition-text-container'
								style="width:80px">
						</div></td>

					<%--<td>现场其他监护人：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" type="text" name=""
								id="" />
						</div></td>
					--%><td colspan="2" rowspan="1">传输局确认： <input class="" type="text"
						id="csjMakesure" name="csjMakesure" style="width:200px;border:0" />
					</td>
				</tr>
				<tr>
					<td>线务段：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="line_part" id="line_part" value="${LINE_PART}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
						</div></td>

					<%--<td>其他监护人负责时段：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" type="text" />
						</div></td>
					--%><td>施工地点示意图：</td>
					<td>
						<div class="">
							
						</div></td>
				</tr>
				<tr>
					<td>标石号（人孔号）：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition"
								type="text" name="landmarkno" id="landmarkno"
								value="${LANDMARKNO }"
								 />
						</div></td>

					<%--<td>其他监护人联系电话：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text  condition" type="text" />
						</div></td>
					--%><%--<td>关联照片：<c:if test="${IMAGE_PATH==null}">0</c:if> <c:if
							test="${IMAGE_PATH!=''}">1</c:if>
					</td>
					--%><td>
						<div class="">
							<input type="hidden" onclick="" value="上传" />
						</div></td>
				</tr>

				<tr>
					<td>看护计划：</td>
					<td>
						<div class="">
							<input type="checkbox" id="is_plan" name="is_plan"
								<c:if test="${IS_PLAN=='0'}">checked='checked'</c:if> />不自动下发看护任务
						</div></td>
				</tr>





			</table>
		</form>


		

		<div id="win_affactedCable"></div>
	</div>


	<div title="机械操作手信息" data-options="closable:false"
		style="overflow:auto;">

		<table id="jxs" title="【】" style="width:100%;height:480px"></table>

		<div id="tb1" style="padding:5px;height:auto">

			<div class="btn-operation-container">

				


			</div>
		</div>
		<div id="win_jxs"></div>
	</div>

	<div title="填报时现场图片" data-options="closable:false"
		style="overflow:auto;">
		<c:forEach items="${photoList}" var="p">
<%--			<a href="${p.PHOTO_PATH}" title="照片预览" class="nyroModal">--%>
			<img src="${p.MICRO_PHOTO}" style="width: 100px;"/>
		</c:forEach>
<%--		<table width="100%" border="0" cellspacing="1" cellpadding="3" bgcolor="#CCCCCC">--%>
<%--					<tr height="100px" bgcolor="#FFFFFF">--%>
<%--						<td align="right" class="form_01b"><strong></strong>&nbsp;&nbsp;</td>--%>
<%--						<td colspan="4" align="left" class="list_pd1">--%>
<%--							<c:forEach items="${photoList}" var="p">--%>
<%--							<a href="${p.PHOTO_PATH}" title="照片预览" class="nyroModal">--%>
<%--							<img src="${p.MICRO_PHOTO}" style="width: 100px;"/></a>  --%>
<%--						</c:forEach>--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--		</table>--%>
	</div>
	<%--<div title="检查记录" data-options="closable:false" style="overflow:auto;">
		<table id="checkRecord" title="【】" style="width:100%;height:480px"></table>

		<div id="tb3" style="padding:5px;height:auto">

			<div class="btn-operation-container">

				<div style="float:left;" class="btn-operation"
					onClick="searchData(3)">查询</div>


			</div>
		</div>


	</div>
	--%><div title="上传文件" data-options="closable:false" style="overflow:auto;">


	</div>
	<div title="签到信息" data-options="closable:false" style="overflow:auto;">
		<div class="condition-container">
			<form id="signform" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" type="text"
									class="condition-text"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							</div></td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" type="text"
									class="condition-text"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							</div></td>

					</tr>

				</table>
			</form>
		</div>
		<div id="tb4" style="padding:5px;height:auto">
			<div class="btn-operation-container">

				<div style="float:left;" class="btn-operation"
					onClick="searchData(4)">查询</div>


			</div>
		</div>
		<table id="signInfo" title="【】" style="width:100%;height:480px"></table>



	</div>
	<div title="停留信息" data-options="closable:false" style="overflow:auto;">
		<div class="condition-container">
			<form id="stayform" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">开始时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="start_time" id="start_time" type="text"
									class="condition-text"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							</div></td>
						<td width="10%">结束时间：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="end_time" id="end_time" type="text"
									class="condition-text"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							</div></td>
						<td width="10%">停留时间超过：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="over_time" id="over_time" type="text"
									class="condition-text" />
							</div></td>

					</tr>

				</table>
			</form>
		</div>
		<div id="tb5" style="padding:5px;height:auto">
			<div class="btn-operation-container">

				<div style="float:left;" class="btn-operation"
					onClick="searchData(5)">查询</div>


			</div>
		</div>
		<table id="stayInfo" title="【】" style="width:100%;height:480px"></table>


	</div>
	<div title="埋深探位" data-options="closable:false" style="overflow:auto;">


		<div id="tb6" style="padding:5px;height:auto">

			<div class="btn-operation-container">

				

				



			</div>
		
		</div>
		<table id="mstw" title="【】" style="width:100%;height:480px"></table>
		<div id="win_mstw"></div>

	</div>





</body>
</html>
