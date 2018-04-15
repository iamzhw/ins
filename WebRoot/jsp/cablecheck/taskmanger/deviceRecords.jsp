
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../../util/head.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<title>设备检查记录</title>
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
				$("select[name='AREA'] option").remove();
				$("select[name='AREA']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='AREA']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
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
				$("select[name='SON_AREA'] option").remove();
				$("select[name='SON_AREA']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='SON_AREA']").append("<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
				}
			}
		});
	}
	
	//获取人员
	function getStaff() {
		var areaId = $("#areaId").val();
		var sonAreaId = $("#son_area").val();
		if(sonAreaId == ''){
			$("select[name='STAFF'] option").remove();
			$("select[name='STAFF']").append("<option value=''>请选择</option>");
			return;
		}
		$.ajax({
			type : 'POST',
			url : webPath + "CableTaskManger/queryHandler.do",
			data : {
				areaId : areaId,
				sonAreaId : sonAreaId
			},
			dataType : 'json',
			success : function(json) 
			{
				var result = json.rows;
				$("select[name='STAFF'] option").remove();
				$("select[name='STAFF']").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.length; i++) {
					$("select[name='STAFF']").append("<option value=" + result[i].STAFF_ID + ">"+ result[i].STAFF_NAME + "</option>");
				}
			}
		});
	}
	
	function searchData() {
		
		var cStartTime = $("#create_start_time").val();
		var cEndTime = $("#create_end_time").val();
		var area = $("#area").val();
		var son_area = $("#son_area").val();
		var staff = $("#staff").val();
		var equipmentCode = $("#EQUIPMENT_CODE").val();
		var recordType = $("#record_type").val();
		
		var obj = {
				cStartTime : cStartTime,
				cEndTime : cEndTime,
				area : area,
				son_area : son_area,
				staff : staff,
				equipmentCode : equipmentCode,
				recordType : recordType
		};
		$('#dg').datagrid(
				{
					//此选项打开，表格会自动适配宽度和高度。
					autoSize : true,
					toolbar : '#tb',
					url : webPath + "CableTaskManger/queryDeviceRecords.do",
					queryParams : obj,
					method : 'post',
					pagination : true,
					pageNumber : 1,
					pageSize : 10,
					pageList : [ 20, 50 ],
					//loadMsg:'数据加载中.....',
					rownumbers : true,
					singleSelect : false,
					columns : [ [
							{
								field : 'RECORD_ID',
								title : '记录ID',
								rowspan : '2',
								checkbox : true
							},
							{
								field : 'AREA',
								title : '地市',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'SON_AREA',
								title : '区域',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'EQP_NO',
								title : '设备编码',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'EQP_NAME',
								title : '设备名称',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'EQPADDRESS',
								title : '设备地址',
								width : "17%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'STAFF_NAME',
								title : '创建人',
								width : "7%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'CREATE_TIME',
								title : '创建时间',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},{
								field : 'REMARK',
								title : '备注',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'INFO',
								title : '信息',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'DESCRIPT',
								title : '描述',
								width : "10%",
								rowspan : '2',
								align : 'center'
							},
							{
								field : 'RECORD_TYPE',
								title : '状态',
								width : "7%",
								rowspan : '2',
								align : 'center'
							}] ],
					nowrap : false,
					striped : true,
					onLoadSuccess : function(data) {
						$(this).datagrid("fixRownumber");
					}
				});
	}

	function clearForm() {
		$("#create_start_time").val('');
		$("#create_end_time").val('');
		$("#area").val('');
		$("#son_area").val('');
		$("#staff").val('');
		$("EQUIPMENT_CODE").val('');
		$("record_type").val('');
	}
</script>
	<body style="padding: 3px; border: 0px">
		<input type="hidden" id="ifGLY" />
		<div id="tb" style="padding: 5px; height: auto">
			<div class="condition-container" style="height: 90px;">
				<form id="form" action="" method="post">
					<input type="hidden" name="selected" value="" />
					<input type="hidden" name="selected" value="" />
					<table class="condition">
						<tr>
							<td width="10%" nowrap="nowrap">
								创建开始时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name="CREATE_START_TIME" id="create_start_time" onClick="WdatePicker();" />
								</div>
							</td>
							<td width="10%" nowrap="nowrap">
								创建结束时间：
							</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name="CREATE_END_TIME" id="create_end_time" 
										onClick="WdatePicker({minDate:'#F{$dp.$D(\'create_start_time\')}'});" />
								</div>
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
								<select name="SON_AREA" id="son_area" class="condition-select" onchange="getStaff()">
									<option value="">
										请选择
									</option>
								</select>
							</td>
							<td width="10%">
								创建人：
							</td>
							<td width="20%">
								<select name="STAFF" id="staff" class="condition-select">
									<option value="">
										请选择
									</option>
								</select>
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
							<td width="10%">
								状态：
							</td>
							<td width="20%">
								<select name="RECORD_TYPE" id="record_type" class="condition-select">
									<option value="">
										请选择
									</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="btn-operation-container">
				<div style="float:right;" class="btn-operation" onClick="searchData()">
					查询
				</div>
				<div style="float:right;"  class="btn-operation" onClick="clearForm()">
					重置
				</div>
			</div>
		</div>
		<table id="dg" title="【任务管理】" style="width: 100%; height: 480px"></table>
	</body>
</html>
