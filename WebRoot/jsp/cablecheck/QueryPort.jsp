<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head xmlns="http://www.w3.org/1999/xhtml">
		<%@include file="../util/head.jsp"%>
		<script type="text/javascript" src="<%=path%>/js/common.js"></script>
		<%-- <script type="text/javascript" src="<%=path%>/js/donetask/donetask-index.js"></script> --%>
		<title>设备详情</title>
	</head>
	<body style="padding: 3px; border: 0px">
	<form id="form" action="" method="post">
		<input name="areaId" value="${areaId}" id="areaId" type="hidden"/>
		<input name="parent_area_id" value="${parent_area_id}" id="parent_area_id" type="hidden"/>
		<input name="type" value="${type}" id="type" type="hidden"/>
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
			var WLJB_ID = $("input[name='WLJB_ID']").val();
			var obj ={
					START_TIME : START_TIME,
					COMPLETE_TIME:COMPLETE_TIME,
					AREAID:AREAID,
					parent_area_id:parent_area_id,
				      type:type,
				      WLJB_ID:WLJB_ID
					
			};
			$('#dg').datagrid({
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "CheckQualityReport/portQuery.do",
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
					title : '区域',
					width : "8%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'PORT_ID',
					title : '端子ID',
					width : "8%",
					rowspan : '2',
					align : 'center',
					sortable : true
				},{
					field : 'PORT_NO',
					title : '端子编码',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'EQP_NAME',
					title : '设备名称',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'EQP_NO',
					title : '设备编码',
					width : "9%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'TASK_NAME',
					title : '任务名称',
					width : "9%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{ 
					field : 'STAFF_NAME',
					title : '检查人',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'CHECK_TIME',
					title : '检查时间',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'DESCRIPT',
					title : '问题描述',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'ISCHECKOK',
					title : '是否合格',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'CHECK_FLAG',
					title : '来源',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true,
					hidden:true
				},{
					field : 'GLMC',
					title : '光路名称',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true				
				},{
					field : 'GLBM',
					title : '光路编码',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}
				] ],
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
					+ "CheckQualityReport/portexportExcel.do"
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