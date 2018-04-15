<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>巡检设备维护</title>
</head>

<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container" style="height:140px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="equipIds" value="" />
				<input type="hidden" name="count" value="" />
				<input type="hidden" name="type" value="" />
				<input name="equpAreaId" id="equpAreaId" value="${equpAreaId}" type="hidden"/>
				<table class="condition">
					<tr>
						<td align="left" width="15%">
							是否已分配：
						</td>
						 <td>
							<select name="staff"  class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="null" selected="selected">否</option>
								<option value="not null">是</option>
							</select>
						</td> 
						<td align="left" width="15%">
							电信管理区：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">
							<input name="manaArea" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							设备位置：
						</td>
						<td width="25%" align="left">
							<select name="equpAddress" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="路边">路边</option>
								<option value="小区">小区</option>
								<option value="交接间">交接间</option>
								<option value="网监">网监</option>
								<option value="其他">其他</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							资源类别：
						</td>
						<td align="left" width="25%">
							<select name="resType" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="光交接箱">光交接箱</option>
								<option value="ODF">ODF</option>
							</select>
						</td>
						<td align="left" width="15%">
							资源编码：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="equCode" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							地区：
						</td>
						 <td>
							<select name="areaName" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${areaList}" var="v">
								<option value="${v.NAME}">${v.NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							巡检公司：
						</td>
						<td>
							<select name="ownCompany" id="ownCompany" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${companyList}" var="v">
								<option value="${v.COMPANY_NAME}">${v.COMPANY_NAME}</option>
								</c:forEach>
							</select>
						</td>
						<td align="left" width="15%">
							巡检人员：
						</td>
						<td width="25%" align="left">
							<div class="condition-text-container">	
							<input name="staffName" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							管理模式：
						</td>
						 <td>
							<select name="manaType" class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="光交接箱">光交接箱</option>
								<option value="ODF">ODF</option>
								<option value="光分纤箱">光分纤箱</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="left" width="15%">
							是否有问题：
						</td>
						<td>
							<select name="troubleState" class="condition-select">
								<option value="2">----------请选择----------</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
						<td align="left" width="15%">
							资源名称：
						</td>
						<td width="20%" align="left">
							<div class="condition-text-container">
							<input name="equName" class="condition-text" />
							</div>
						</td>
						<td align="left" width="15%">
							是否健康：
						</td>
						 <td>
							<select name="state"  class="condition-select">
								<option value="">----------请选择----------</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</td> 
					</tr>
					<c:if test="${equpAreaId eq '20'}">
					<tr>
						<td align="left" width="15%">
							区域：
						</td>
						 <td>
							<select name="sonAreaName" class="condition-select">
								<option value="">----------请选择----------</option>
								<c:forEach items="${sonAreaList}" var="v">
								<option value="${v.NAME}">${v.NAME}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					</c:if>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="editEquipment()">修改</div>
			<div class="btn-operation" onClick="btnallot('')">分配巡检人</div>
			<div class="btn-operation" onClick="btnallot('ODF')">分配管控人</div>
			<div class="btn-operation" onClick="btnhealthy()">批量设置健康</div>
			<div class="btn-operation" onClick="btnclearallot('')">清除巡检人</div>
			<div class="btn-operation" onClick="btnclearallot('ODF')">清除管控人</div>
			<div style="float:right;" class="btn-operation" onClick="searchData()">查询</div>
		</div>
	</div>
	<table id="dg" title="【巡检设备管理】" style="width:100%;height:480px"></table>
	<div id="win_equip"></div>

	<script type="text/javascript">
	$(document).ready(function() {
			//searchData();
		});
		
		function searchData() {
			var staff = $("select[name='staff']").val();
			var manaArea = $("input[name='manaArea']").val();
			var equpAddress = $("select[name='equpAddress']").val();
			var resType = $("select[name='resType']").val();
			var equCode = $("input[name='equCode']").val();
			var sonAreaName = $("select[name='sonAreaName']").val();
			var ownCompany = $("select[name='ownCompany']").val();
			var manaType = $("select[name='manaType']").val();
			var troubleState = $("select[name='troubleState']").val();
			var equName = $("input[name='equName']").val();
			var state = $("select[name='state']").val();
			var areaName = $("select[name='areaName']").val();
			var staffName = $("input[name='staffName']").val();
			var equpAreaId = $("input[name='equpAreaId']").val();
			
			
			var obj = {
					staff : staff,
					manaArea : manaArea,
					equpAddress:equpAddress,
					resType:resType,
					equCode:equCode,
					sonAreaName:sonAreaName,
					ownCompany:ownCompany,
					manaType:manaType,
					troubleState:troubleState,
					equName:equName,
					state:state,
					areaName:areaName,
					staffName:staffName,
					equpAreaId : equpAreaId
			};
			//return;
			$('#dg').datagrid({
				//此选项打开，表格会自动适配宽度和高度。
				autoSize : true,
				toolbar : '#tb',
				url : webPath + "equip/query.do",
				queryParams : obj,
				method : 'post',
				pagination : true,
				pageNumber : 1,
				pageSize : 10,
				pageList : [ 20, 50 ],
				//loadMsg:'数据加载中.....',
				rownumbers : true,
				singleSelect : false,
				columns : [ [ {
					field : 'EQUIPMENT_ID',
					title : '巡检设备ID',
					checkbox : true
				},{
					field : 'EQUIPMENT_CODE',
					title : '资源编码',
					width : "10%",
					align : 'center',
					styler:function(value,rowData,rowIndex){
						return 'word-break:break-all;word-wrap:break-word';
					}
					
				}, {
					field : 'EQUIPMENT_NAME',
					title : '资源名称',
					width : "8%",
					align : 'center'
				}, {
					field : 'RES_TYPE',
					title : '资源类别',
					width : "6%",
					align : 'center'
				}, {
					field : 'ADDRESS',
					title : '资源地址',
					width : "8%",
					align : 'center'
				}, {
					field : 'AREA',
					title : '地区',
					width : "5%",
					align : 'center'
				}, {
					field : 'SON_AREA',
					title : '区域',
					width : "5%",
					align : 'center'
				}, {
					field : 'MANAGE_AREA',
					title : '电信管理区',
					width : "8%",
					align : 'center'
				}, {
					field : 'MANAGE_TYPE',
					title : '管理模式',
					width : "6%",
					align : 'center'
				}, {
					field : 'OWN_COMPANY',
					title : '巡检公司',
					width : "6%",
					align : 'center'
				}, {
					field : 'STAFF_NAME',
					title : '巡检人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'CHECKCOMPANY',
					title : '管控组',
					width : "6%",
					align : 'center'
				}, {
					field : 'CHECKSTAFFNAME',
					title : '管控人员',
					width : "6%",
					align : 'center'
				}, {
					field : 'EQUP_ADDRESS',
					title : '设备位置',
					width : "6%",
					align : 'center'
				}, {
					field : 'STATE',
					title : '是否健康',
					width : "6%",
					align : 'center'
				}, {
					field : 'CREATE_DATE',
					title : '创建时间',
					width : "8%",
					align : 'center'
				}] ],
				nowrap : false,
				striped : true,
				onLoadSuccess : function(data) {
				}
			});
		}

		//清空查询条件
		function clearCondition(form_id) {
			$("input[name='check_item_name']").val("");
			$("select[name='room_type_id']").val("");
		}

		function editEquipment(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择要修改资源!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].EQUIPMENT_ID;
					arr[arr.length] = value;
				}
				$("input[name='equipIds']").val(arr);
				var equipIds = $("input[name='equipIds']").val();
				$('#win_equip').window({
					title : "【修改资源】",
					href : webPath + "equip/equipModifyIndex.do?equipIds=" + equipIds + "&count=" + count,
					width : 800,
					height : 500,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		function btnhealthy(){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择要修改资源!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要修改资源吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].EQUIPMENT_ID;
							arr[arr.length] = value;
						}
						$("input[name='equipIds']").val(arr);
						$("input[name='count']").val(count);
						$('#form').form('submit', {
							url : webPath + "equip/btnhealthy.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功修改资源!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
		
		function btnallot(type){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择要分配的资源!',
					showType : 'show'
				});
				return;
			} else {
				var arr = new Array();
				for ( var i = 0; i < selected.length; i++) {
					var value = selected[i].EQUIPMENT_ID;
					arr[arr.length] = value;
				}
				$("input[name='equipIds']").val(arr);
				var equipIds = $("input[name='equipIds']").val();
				$('#win_equip').window({
					title : "【修改资源】",
					href : webPath + "equip/staffIndex.do?equipIds=" + equipIds + "&type=" + type,
					width : 1000,
					height : 550,
					zIndex : 2,
					region : "center",
					collapsible : false,
					cache : false,
					modal : true
				});
			}
		}
		
		function btnclearallot(type){
			var selected = $('#dg').datagrid('getChecked');
			var count = selected.length;
			if (count == 0) {
				$.messager.show({
					title : '提  示',
					msg : '请选择要修改资源!',
					showType : 'show'
				});
				return;
			} else {
				$.messager.confirm('系统提示', '您确定要清除吗?', function(r) {
					if (r) {
						var arr = new Array();
						for ( var i = 0; i < selected.length; i++) {
							var value = selected[i].EQUIPMENT_ID;
							arr[arr.length] = value;
						}
						$("input[name='equipIds']").val(arr);
						$("input[name='type']").val(type);
						$('#form').form('submit', {
							url : webPath + "equip/btnclearallot.do",
							onSubmit : function() {
								$.messager.progress();
							},
							success : function() {
								$.messager.progress('close'); // 如果提交成功则隐藏进度条
								searchData();
								$.messager.show({
									title : '提  示',
									msg : '成功修改资源!',
									showType : 'show'
								});
							}
						});
					}
				});
			}
		}
	</script>
</body>
</html>