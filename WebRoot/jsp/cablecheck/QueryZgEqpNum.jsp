<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html style="height:100%">
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<title>设备详情</title>
	</head>
	<body style="padding: 3px; border: 0px;">
		 <!-- <div style="width:100%;height:100%;">
		
			<div style="height:100%;">
			   <table id="dg" title="【设备详情】" style="width: 100%; height: 480px"></table> 
			</div>
		 
	     </div> -->
	     
	     <table id="dg" title="【设备详情】" style="width: 100%; height: 480px"></table>
	 
	    <form id="form" action="" method="post">
			<input name="areaId" value="${areaId}" id="areaId" type="hidden"/>
			<input name="parent_area_id" value="${parent_area_id}" id="parent_area_id" type="hidden"/>
			<input name="type" value="${type}" id="type" type="hidden"/>
			<input name="task_start_time" value="${task_start_time}" id="task_start_time" type="hidden"/>
			<input name="task_end_time" value="${task_end_time}" id="task_end_time" type="hidden"/>
			<input name="task_pstart_time" value="${task_pstart_time}" id="task_pstart_time" type="hidden"/>
			<input name="task_pend_time" value="${task_pend_time}" id="task_pend_time" type="hidden"/>
			<input name="task_mstart_time" value="${task_mstart_time}" id="task_mstart_time" type="hidden"/>
			<input name="task_mend_time" value="${task_mend_time}" id="task_mend_time" type="hidden"/>
			<input name="task_astart_time" value="${task_astart_time}" id="task_astart_time" type="hidden"/>
			<input name="task_aend_time" value="${task_aend_time}" id="task_aend_time" type="hidden"/>
			<input name="WLJB_ID" value="${WLJB_ID}" id="WLJB_ID" type="hidden"/>
		</form>
		
		<div id="tb" style="padding: 5px; height: auto">		
			<div class="btn-operation-container">
				<div style="float: right;" class="btn-operation" onClick="exportExcel()">导出</div>
				
			</div>
		</div>  		
		 
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
		  //  searchEqp();
			searchData();
		});
	
		function searchData() {		
			var areaId=$('#areaId').val();
			var parent_area_id=$('#parent_area_id').val();
			var type=$('#type').val();
			var task_start_time=$('#task_start_time').val();
			var task_end_time=$('#task_end_time').val();			
			var task_pstart_time=$('#task_pstart_time').val();
			var task_pend_time=$('#task_pend_time').val();
			var task_mstart_time=$('#task_mstart_time').val();
			var task_mend_time=$('#task_mend_time').val();
			var task_astart_time=$('#task_astart_time').val();
			var task_aend_time=$('#task_aend_time').val();
			var WLJB_ID=$('#WLJB_ID').val();
			if(task_pstart_time=="undefined"){
				task_pstart_time="";
			}
			if(task_pend_time=="undefined"){
				task_pend_time="";
			}
			if(task_mstart_time=="undefined"){
				task_mstart_time="";
			}
			if(task_mend_time=="undefined"){
				task_mend_time="";
			}
			if(task_astart_time=="undefined"){
				task_astart_time="";
			}
			if(task_aend_time=="undefined"){
				task_aend_time="";
			}
			
			var obj ={
					AREAID:areaId,
					parent_area_id:parent_area_id,
					type:type,
					task_start_time:task_start_time,
				    task_end_time:task_end_time,
				    task_pstart_time:task_pstart_time,
				    task_pend_time:task_pend_time,
				    task_mstart_time:task_mstart_time,
				    task_mend_time:task_mend_time,
				    task_astart_time:task_astart_time,
				    task_aend_time:task_aend_time,
				    WLJB_ID:WLJB_ID				
			};
				
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
					field : 'EQUIPMENT_NAME',
					title : '设备名称',
					width : "14%",
					rowspan : '2',
					align : 'center',
					sortable : true
					
				},{
					field : 'EQUIPMENT_CODE',
					title : '设备编码',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'ADDRESS',
					title : '设备地址',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'RES_TYPE',
					title : '设备类型',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'MANAGE_AREA',
					title : '管理区域名称',
					width : "14%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}
				];	
	
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "ReformOrderReport/zgEquipmentQuery.do",
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
		
		
		
		function exportExcel() {
		
	
		
			window.open(webPath
					+ "ReformOrderReport/equipmentexportExcel.do"
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