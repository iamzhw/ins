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
		    
		var static_month = $("input[name='static_month']").val();
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
					static_month:static_month,
					parent_area_id:parent_area_id,
					WLJB_ID:WLJB_ID,
				      type:type
					
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
					field : 'CITY',
					title : '地市',
					width : "5%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'TOWN',
					title : '区域',
					width : "6%",
					rowspan : '2', 
				    align : 'center'
				},{
					field : 'DZID',
					title : '端子ID',
					width : "8%",
					rowspan : '2',
					align : 'center',
					sortable : true
				},{
					field : 'DZBM',
					title : '端子编码',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'SBMC',
					title : '设备名称',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				}, {
					field : 'SBBM',
					title : '设备编码',
					width : "9%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'SBLX',
					title : '设备类型',
					width : "5%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'INSTALL_SBBM',
					title : '箱子编码',
					width : "10%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'BDSJ',
					title : '变动时间',
					width : "7%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{ 
					field : 'GDJGSJ',
					title : '工单竣工时间',
					width : "7%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'XZ',
					title : '性质',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'GLBH',
					title : '光路编号',
					width : "7%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'GLMC',
					title : '光路名称',
					width : "8%",
					rowspan : '2', 
					align : 'center',
					sortable : true
				},{
					field : 'PZGH',
					title : '配置工号',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					sortable : true
					
				},{
					field : 'SGGH',
					title : '施工工号',
					width : "6%",
					rowspan : '2', 
					align : 'center',
					sortable : true				
				},{
					field : 'GQGH',
					title : '更纤工号',
					width : "6%",
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
					+ "CheckQualityReport/dtsjexportExcel.do"
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