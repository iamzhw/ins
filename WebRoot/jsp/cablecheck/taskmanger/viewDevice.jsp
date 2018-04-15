
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>设备信息</title>
</head>
<script type="text/javascript">

	$(document).ready(function() {
		getAreaList();
		searchData();
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
				var result = json.sonAreaList;
				$("select[name='AREA_ID'] option").remove();
				$("select[name='AREA_ID']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
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
				var result = json.sonAreaList;
				$("select[name='SON_AREA_ID'] option").remove();
				$("select[name='SON_AREA_ID']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='SON_AREA_ID']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
				}
			}
		});
	}
	function searchData() {
		var deviceId = $("#deviceId").val();
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var sfpf = $("#sfpf").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var whwg = $("#whwg").val();
		var mange_id = $("#mange_id").val();
		var sblx = $("#sblx").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		
		var obj = {
			EQUIPMENT_ID:deviceId,
			dz_start_time:dz_start_time,
			dz_end_time:dz_end_time,
			sfpf:sfpf,
			area:area,
			son_area:son_area,
			whwg:whwg,
			mange_id:mange_id,
			sblx:sblx,
			equipmentCode:equipmentCode
		};
		//return;
		$('#dg').datagrid(
				{
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					toolbar : '#tb',
					url : webPath + "CableTaskManger/equipmentQuery.do",
					queryParams : obj,
					method : 'post',
					pagination : true,
					pageNumber : 1,
					pageSize : 10,
					pageList : [ 50, 100 ],
					rownumbers : true,
					singleSelect : false,
					columns : [ [
							{
								field : 'EQUIPMENT_ID',
								title : '设备ID',
								rowspan : '2',
								checkbox : true
							},
							{
								field : 'EQUIPMENT_NAME',
								title : '设备名称',
								width : "13%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'EQUIPMENT_CODE',
								title : '设备编码',
								width : "11%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'RES_TYPE',
								title : '设备类型',
								width : "8%",
								rowspan : '2',
								align : 'center'
							},{
								field : 'WLJB',
								title : '网络级别',
								width : "8%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'MANAGE_AREA',
								title : '管理区名称',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'MANAGE_AREA_ID',
								title : '管理区编码',
								width : "8%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'STAFF_NAME',
								title : '检查员',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'CREATE_TIME',
								title : '上次检查时间',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'LAST_UPDATE_TIME',
								title : '最近一次更新时间',
								width : "12%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'SFPF',
								title : '是否派发',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'DZNUM',
								title : '端子变动数',
								width : "9%",
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
									return "<a href=\"javascript:viewDuanZi("+
											row.EQUIPMENT_ID+");\">查看端子信息</a>";
								}
							} ] ],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$(this).datagrid("fixRownumber");
					}
				});
	}


	function clearForm() {

		$("select[name='STATUS_ID']").val('');
		$("input[name='TASK_NAME']").val('');
		$("input[name='START_TIME']").val('');
		$("select[name='TASK_TYPE']").val('');
		$("input[name='COMPLETE_TIME']").val('');
		$("select[name='son_area']").val('');

	}
	
	function myCloseTab() {  
		if(parent.$('#tabs').tabs('exists',"端子信息")){
			parent.$('#tabs').tabs('close',"端子信息");
		}
    } 
	
	function viewDuanZi(EQUIPMENT_ID){
		myCloseTab();
		var areaId = $("#areaId").val();
		var dz_start_time = $("#dz_start_time").val();
		var dz_end_time = $("#dz_end_time").val();
		var  url="<%=path%>/CableTaskManger/viewDuanZi.do?dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&areaId="+areaId+"&EQUIPMENT_ID="+EQUIPMENT_ID;
		addTab("端子信息", url);
	}															
	
	function exportExcel(){
		
	window.open(webPath
					+ "CableTaskManger/downEquipmentQuery.do"
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
		<input name="deviceId" value="${EQUIPMENT_ID}" id="deviceId" type="hidden"/>
		<input name="areaId" value="${area}" id="areaId" type="hidden"/>
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 100px;">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="EQUIPMENT_ID" id="EQUIPMENT_ID"  value="${EQUIPMENT_ID}"/>
					<table class="condition">
						<tr>
							<td width="10%" nowrap="nowrap">
								端子动态开始时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=DZ_START_TIME id="dz_start_time" onClick="WdatePicker();" value="${dz_start_time}"/>
								</div>
							</td>
							<td width="10%" nowrap="nowrap">
								端子动态结束时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=DZ_END_TIME id="dz_end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'dz_start_time\')}'});" value="${dz_end_time}"/>
								</div>
							</td>
							<td width="10%">
								是否派发：
							</td>
							<td width="20%">
								<select name="SFPF" id="sfpf" class="condition-select">
									<option value="">
										请选择
									</option>
									<option value="0">
										否
									</option>
									<option value="1">
										是
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="10%">
								地市：
							</td>
							<td width="20%">
								<select name="AREA" id="area" class="condition-select" onchange="getSonAreaList()">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10%">
								区域：
							</td>
							<td width="20%">
								<select name="SON_AREA" id="son_area" class="condition-select">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10%">
								所属维护网格：
							</td>
							<td width="20%">
								<select name="WHWG" id="whwg" class="condition-select">
									<option value="">
										请选择
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="left" width="15%">
								管理区编码：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="MANAGE_AREA_ID" id="mange_id" class="condition-text" />
								</div>
							</td>
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
									<option value="2530">
										分光器
									</option>
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
							<td align="left" width="15%">
								设备编码：
							</td>
							<td width="25%" align="left">
								<div class="condition-text-container">
									<input name="EQUIPMENT_CODE" id="EQUIPMENT_CODE" class="condition-text" />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<!-- 排序规格
				<input type="checkbox" value="0" name="selectType" style="margin-left: 40px;">按端子
				<input type="checkbox" value="1" name="selectType" style="margin-left: 40px;">Radio Button
				<input type="checkbox" value="2" name="selectType" style="margin-left: 40px;">Radio Button -->
				<div style="float:right;" class="btn-operation" onClick="searchData()">
					查询
				</div>
				<div class="btn-operation" onClick="viewDevice()">
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
