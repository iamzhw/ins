<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>设备详情</title>
	</head>
	<body style="padding: 3px; border: 0px">
	  <form id="form" action="" method="post">
		<input name="areaId" value="${areaId}" id="areaId" type="hidden"/>
		<input name="parent_area_id" value="${parent_area_id}" id="parent_area_id" type="hidden"/>
		<input name="type" value="${type}" id="type" type="hidden"/>
		<input name="PCOMPLETE_TIME" value="${PCOMPLETE_TIME}" id="PCOMPLETE_TIME" type="hidden"/>
		<input name="PSTART_TIME" value="${PSTART_TIME}" id="PSTART_TIME" type="hidden"/>
		<input name="static_month" value="${static_month}" id="static_month" type="hidden"/>
		<input name="START_TIME" value="${START_TIME}" id="START_TIME" type="hidden"/>
		<input name="COMPLETE_TIME" value="${COMPLETE_TIME}" id="COMPLETE_TIME" type="hidden"/>	
			<input name="WLJB_ID" value="${WLJB_ID}" id="WLJB_ID" type="hidden"/>

		</form>
		<div id="tb" style="padding: 5px; height: auto">
		
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">导出</div>
				
			</div>
		</div>
		<table id="dg" title="【设备详情】" style="width: 100%; height: 480px"></table>
		
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			searchData();
		});
		function searchData() {
		    
		
			var AREAID = $("input[name='areaId']").val();
			var parent_area_id = $("input[name='parent_area_id']").val();
	     	var type = $("input[name='type']").val();
			var START_TIME = $("input[name='START_TIME']").val();
			var COMPLETE_TIME = $("input[name='COMPLETE_TIME']").val();
			
			var static_month = $("input[name='static_month']").val();
		
			var PSTART_TIME = $("input[name='PSTART_TIME']").val();
			var PCOMPLETE_TIME = $("input[name='PCOMPLETE_TIME']").val();
			var WLJB_ID = $("input[name='WLJB_ID']").val();
			
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREAID:AREAID,
					static_month:static_month,
					PSTART_TIME:PSTART_TIME,
				    type:type,
					PCOMPLETE_TIME:PCOMPLETE_TIME,
					WLJB_ID:WLJB_ID,
					parent_area_id:parent_area_id
			};
			if (type==7||type==8||type==9||type==10||type==11||type==12||type==13||type==14||type==15||type==16){
				var columns=[ {
					field : 'TASK_ID',
					title : 'ID',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				},{
					field : 'NAME',
					title : '区域',
					width : "14%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'IOM_ORDER_ID',
					title : 'IOM工单号',
					width : "14%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'ORDER_CODE',
					title : '工单编码',
					width : "14%",
					rowspan : '2',
					align : 'center',
					sortable : true
					
				},{
					field : 'CREATE_TIME',
					title : '检查时间',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'STAFF_NAME',
					title : '检查人',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}
				];

				}else if(type==27){

				
										
					var columns=[ {
						field : 'TASK_ID',
						title : 'ID',
						width : "14%",
						rowspan : '2', 
					    align : 'center',
					    hidden:true
					},{
						field : 'AREA_NAME',
						title : '区域',
						width : "14%",
						rowspan : '2', 
					    align : 'center'
					},{
						field : 'ORDER_CODE',
						title : '定单编码',
						width : "14%",
						rowspan : '2', 
					    align : 'center'
					},{
						field : 'ORDER_TITLE',
						title : '定单主题',
						width : "14%",
						rowspan : '2',
						align : 'center',
						sortable : true
						
					},{
						field : 'ORDER_TYPE',
						title : '定单类型',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}, {
						field : 'SERVICE_NAME',
						title : '产品服务',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}, {
						field : 'FINISH_DATE',
						title : '竣工时间',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}, {
						field : 'DEL_OPT_CODE',
						title : '光路编码(拆机、移机中的拆)',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}, {
						field : 'ACTION_TYPE_NAME',
						title : '动作类型名称',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}, {
						field : 'POST_NAME',
						title : '施工岗',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}, {
						field : 'PARTY_NAME',
						title : '施工人',
						width : "14%",
						rowspan : '2', 
						align : 'center',
						sortable : true
					}
					];
                          
                          









					}else if(type==38){

						var columns=[ {
							field : 'TASK_ID',
							title : 'ID',
							width : "8%",
							rowspan : '2', 
						    align : 'center',
						    hidden:true
						},{
							field : 'AREA_NAME',
							title : '区域',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						},{
							field : 'ID',
							title : '工单ID',
							width : "8%",
							rowspan : '2',
							align : 'center',
							sortable : true
							
						},{
							field : 'ORDER_CODE',
							title : '工单编码',
							width : "10%",
							rowspan : '2', 
							align : 'center',
							sortable : true
						}, {
							field : 'ORDER_TYPE',
							title : '工单类型',
							width : "10%",
							rowspan : '2', 
							align : 'center',
							sortable : true
						}
						,{
							field : 'ACCEPT_DATE',
							title : '接入时间',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						},{
							field : 'FINISH_DATE',
							title : '竣工时间',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						},{
							field : 'PSO_ID',
							title : '流程ID',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						},{
							field : 'COMPLETE_DATE',
							title : '完成时间',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						},{
							field : 'OPT_CODE',
							title : '光路名称',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						},{
							field : 'XUQIU',
							title : '需求名称',
							width : "8%",
							rowspan : '2', 
						    align : 'center'
						}
						];
                          
                          









					}else{
			var columns=[ {
					field : 'TASK_ID',
					title : 'ID',
					width : "14%",
					rowspan : '2', 
				    align : 'center',
				    hidden:true
				},{
					field : 'NAME',
					title : '区域',
					width : "14%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'TASK_NAME',
					title : '任务名称',
					width : "14%",
					rowspan : '2',
					align : 'center',
					sortable : true
					
				},{
					field : 'TASK_NO',
					title : '任务编码',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'STATUS_ID',
					title : '任务状态',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'START_TIME',
					title : '检查时间',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'STAFF_NAME',
					title : '检查人',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}
				];}
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "SpecialCheckReport/equipmentQuery.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				rownumbers : true,
				singleSelect : false,
				remoteSort: false,
				columns : [  columns ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				console.log('obj:'+obj);
					$(this).datagrid("fixRownumber");
				}
			});
		};
		
		function show(TASK_ID){
			addTab("设备信息", "<%=path%>/SpecialCheckReport/intoShowEquip.do?TASK_ID="+TASK_ID);
		};
	
	
		
		function exportExcel() {
		
	
		
			window.open(webPath
					+ "SpecialCheckReport/equipmentexportExcel.do"
					+ getParamsForDownloadLocal('form'));
		}
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