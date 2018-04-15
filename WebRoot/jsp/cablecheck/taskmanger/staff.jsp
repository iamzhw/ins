<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>选择任务执行人</title>
</head>

<body>
<input name="selectsbIds" id="selectsbIds" value="${sbIds}" type="hidden"/>
<input name="area" id="area" value="${area}" type="hidden">
<input name="son_area" id="son_area" value="${son_area}" type="hidden">
<table id="dg_soft" title="【选择人员】" style="width: 100%; height: 480px">
</table>
	<div id="tb_soft" style="padding: 5px; height: auto">
	<div class="condition-container" style="height: 50px;">
			<form id="form" action="" method="post">
				<input type="hidden" name="selected" value="" />
				<table class="condition">
					<tr>
						<td width="10%">
							区域：
						</td>
						<td width="20%">
							<select name="sonarea" id="sonarea" class="condition-select">
								<option value="">
									请选择
								</option>
							</select>
						</td>
						<td width="10%">工号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_no" id="staff_no" type="text" class="condition-text" />
							</div>
						</td>
						<td width="10%">姓名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="staff_name" id="staff_name" type="text" class="condition-text" />
							</div>
						</td>
					</tr>
					<tr>
							<td width="12%">检查结束时间：</td>
							<td width="20%">
								<div class="condition-text-container">
									<input class="condition-text condition" type="text"
										name=complete_time id="complete_time" onClick="WdatePicker();"
										readonly="readonly" />
								</div>
							</td>
						</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
		<div class="btn-operation" onClick="saveTask()" style="float: right;">确认</div>
			<div class="btn-operation" onClick="searchData1()" style="float:right;">查询</div>
		</div>
		
	</div>
<script type="text/javascript">


$(document).ready(function() {
	getSonAreaList1();
	searchData1();
	$("select[name='sonarea']").change(function() {
				$("input[name='son_area']").val($("select[name='sonarea']").val());//根据area_id，获取区县
			});
});
//获取区域
function getSonAreaList1() {
	var areaId = $("#area").val();
	var sonAreaId = $("#son_area").val();
	$.ajax({
		type : 'POST',
		url : webPath + "General/getSonAreaList.do",
		data : {
			areaId : areaId
		},
		dataType : 'json',
		success : function(json) {
			var result = json.sonAreaList;
			$("select[name='sonarea'] option").remove();
			var isSonAreaAdmin ='${CABLE_ADMIN_SONAREA}';
					
					if (undefined !=isSonAreaAdmin && isSonAreaAdmin!='true'){
						$("select[name='sonarea']").append("<option value=''>请选择</option>");
						for ( var i = 0; i < result.length; i++) {
							$("select[name='sonarea']").append("<option value=" + result[i].AREA_ID + ">" + result[i].NAME + "</option>");
						}
					}else{
			
			for ( var i = 0; i < result.length; i++) {
					if(result[i].AREA_ID == ${son_area}){
								$("select[name='sonarea']").append("<option value=" + result[i].AREA_ID + ">" + result[i].NAME + "</option>");
							}
			}
		}
		}
	});
}
function searchData1() {
	var staff_name = $("#staff_name").val();
	var staff_no = $("#staff_no").val();
	var areaId = $("#area").val();
	var sonAreaId = $("input[name='son_area']").val();
	var obj = {
		staff_name : staff_name,
		staff_no : staff_no,
		areaId : areaId,
		sonAreaId : sonAreaId
	};
	$('#dg_soft').datagrid({
		//此选项打开，表格会自动适配宽度和高度。
		//autoSize : true,
		width : 840,
		height : 460,
		toolbar : '#tb_soft',
		url : webPath + "CableTaskManger/queryHandler.do",
		queryParams : obj,
		method : 'post',
		pagination : true,
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 20, 30 ],
		loadMsg : '数据加载中.....',
		rownumbers : true,
		singleSelect : true,
		columns : [ [ {
			field : 'STAFF_ID',
			title : '用户ID',
			checkbox : true
		}, {
			field : 'SON_AREA_ID',
			title : '区域',
			width : "250",
			align : 'center'
		}, {
			field : 'STAFF_NO',
			title : '工号',
			width : "250",
			align : 'center'
		}, {
			field : 'STAFF_NAME',
			title : '姓名',
			width : "250",
			align : 'center'
		} ] ],
		nowrap : false,
		striped : true,
		onLoadSuccess : function(data) {

		}
	});
}
function saveTask(){
	var selected = $('#dg_soft').datagrid('getChecked');
	var dz_start_time = $("#dz_start_time").val();
	var dz_end_time = $("#dz_end_time").val();
    var complete_time = $("input[name='complete_time']").val();
	var count = selected.length;
	if (count == 0) {
		$.messager.show({
			title : '提  示',
			msg : '请选取一条数据!',
			showType : 'show'
		});
		return;
	} else if (count > 1) {
		$.messager.show({
			title : '提  示',
			msg : '仅能选取一条数据!',
			showType : 'show'
		});
		return;
	} else {
		doShow();
		var staffId = selected[0].STAFF_ID;
		var sbIds = $("input[name='selectsbIds']").val();
		var area=$("#area").val();
		var son_area=$("#son_area").val();
		var complete_time = $("input[name='complete_time']").val();
		$.ajax({
			type : 'GET',
			url : webPath + "CableTaskManger/saveTask.do?staffId="+staffId+"&sbIds="+sbIds+"&complete_time="+complete_time+"&dz_start_time="+dz_start_time+"&dz_end_time="+dz_end_time+"&area="+area+"&son_area="+son_area,
			dataType : 'json',
			success : function(json) {
				$.messager.progress('close');
				if(json.status){
					$.messager.show({
						title : '提  示',
						msg : '操作成功!',
						showType : 'show'
					});
				} else {
					$.messager.show({
						title : '提  示',
						msg : json.message,
						showType : 'show'
					});
				}
				$('#win_staff').window('close');
			}
		});
	}
}
function doShow(){
	$.messager.progress({
		title:'提示',
		msg:'正在派发中,请稍后....',
		interval:500
	});
}
</script>
</body>
</html>