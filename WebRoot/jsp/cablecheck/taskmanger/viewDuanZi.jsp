
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>端子信息</title>
</head>
<script type="text/javascript">

	$(document).ready(function() {
		searchData();
	});
	function searchData() {
		var EQUIPMENT_ID = $("#EQUIPMENT_ID").val();
		var GLMC = $("#GLMC").val(); 
		var GLBH = $("#GLBH").val();
		var areaId = $("#areaId").val();
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		
		var obj = {
			EQUIPMENT_ID : EQUIPMENT_ID,
			GLMC : GLMC,
			GLBH : GLBH,
			areaId : areaId,
			dz_start_time:dz_start_time,
			dz_end_time:dz_end_time
		};
		//return;
		$('#dg').datagrid(
				{
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					toolbar : '#tb',
					url : webPath + "CableTaskManger/terminalQuery.do",
					queryParams : obj,
					method : 'post',
					pagination : true,
					pageNumber : 1,
					pageSize : 10,
					pageList : [ 20, 50 ],
					//loadMsg:'数据加载中.....',
					rownumbers : true,
					fitColumns : false,
					singleSelect : false,
					columns : [ [
							{
								field : 'ID',
								title : '任务ID',
								rowspan : '2',
								checkbox : true
							},
							{
								field : 'SBBM',
								title : '设备编码',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'SBMC',
								title : '设备名称',
								rowspan : '2',
								width : '10%',
								align : 'center'
							},
							{
								field : 'DZBM',
								title : '端子',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'OSSGLBH',
								title : 'OSS光路编码',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'GLBH',
								title : '光路编码',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'GLMC',
								title : '光路名称',
								rowspan : '2',
								width : '15%',
								align : 'center'
							},
							{
								field : 'OSSGLMC',
								title : 'OSS光路名称',
								rowspan : '2',
								width : '15%',
								align : 'center'
							},
							{
								field : 'H',
								title : '是否H光路',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'INSTALL_SBBM',
								title : '涉及节点设备编码',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'GDJGSJ',
								title : '工单竣工时间',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'GXSJ',
								title : '最近一次更新时间',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'IOM_ORDER_NO',
						//		title : '工单编号',
								title : 'IOM定单号',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'XZ',
								title : '工单性质',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'PZGH',
								title : '配置工号',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'SGGH',
								title : '施工工号',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'GQGH',
								title : '更纤工号',
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'DATAFROM',
								title : '数据来源',
								rowspan : '2',
								align : 'center'
							}
							] ],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$(this).datagrid("fixRownumber");
					}
				});
	}

	function clearForm() {

		$("input[name='GLMC']").val('');
		$("input[name='GLBH']").val('');
	}


function exportExcel(){
	
		window.open(webPath
					+ "CableTaskManger/downTerminalQuery.do"
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
	<body style="padding: 3px; border: 0px">
		<input name="EQUIPMENT_ID" value="${EQUIPMENT_ID}" id="EQUIPMENT_ID" type="hidden"/>
		<input name="areaId" value="${areaId}" id="areaId" type="hidden"/>
		<input type="hidden" id="ifGLY" />
		<input name="dz_start_time" value="${dz_start_time}" id="dz_start_time" type="hidden"/>
		<input name="dz_end_time" value="${dz_end_time}" id="dz_end_time" type="hidden"/>
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 40px;">
				<form id="form" action="" method="post">
				<input name="EQUIPMENT_ID" value="${EQUIPMENT_ID}" id="EQUIPMENT_ID" type="hidden"/>
				<input name="AREAID" value="${areaId}" id="areaId" type="hidden"/>
				<input name="DZ_START_TIME" value="${dz_start_time}" id="dz_start_time" type="hidden"/>
		        <input name="DZ_END_TIME" value="${dz_end_time}" id="dz_end_time" type="hidden"/>
					<table class="condition">
						<tr>
							<td width="10%">
								光路名称：
							</td>
							<td width="18%">
								<div class="condition-text-container">
									<input name="GLMC" id="GLMC" class="condition-text" />
								</div>
							</td>
							<td width="10%">
								光路编码：
							</td>
							<td width="18%">
								<div class="condition-text-container">
									<input name="GLBH" id="GLBH" class="condition-text" />
								</div>
							</td>
							<td></td>
							<td></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div class="btn-operation" style="float:right;" onClick="searchData()">
					查询
				</div>
				<div  class="btn-operation" onClick="clearForm()">
					重置
				</div>
				<div style="float:right;" class="btn-operation" onClick="exportExcel()">
					导出
				</div>
			</div>
		</div>
		<table id="dg" title="【任务管理】" style="width: 100%; height: 480px"></table>
	</body>
</html>
