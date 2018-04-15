<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>

<title>局部签到人列表</title>
</head>

<body>

<table id="dg_soft" title="【选择人员】" style="width: 100%; height: 480px">
</table>
	<div id="tb_soft" style="padding: 5px; height: auto">
	<div class="condition-container" style="height: 50px;">
			<form id="staffFrom" action="" method="post">
				<input name="AREA_ID" id="AREA_ID" value="${AREA_ID}" type="hidden"/>
				<input name="SEARCH_AREA_ID" id="SEARCH_AREA_ID" value="${SEARCH_AREA_ID}" type="hidden"/>
				<input name="RECORD_DAY" id="RECORD_DAY" value="${RECORD_DAY}" type="hidden">
				<input name="RECORD_MONTH" id="RECORD_MONTH" value="${RECORD_MONTH}" type="hidden">
				<input name="RECORD_YEAR" id="RECORD_YEAR" value="${RECORD_YEAR}" type="hidden">
			</form>
		</div>
		<div class="btn-operation-container">
			<div class="btn-operation" onClick="exportRecordStaff()">
					导出
			</div>
		</div>
	</div>
<script type="text/javascript">


$(document).ready(function() {
	searchRecordStaff();
});

function exportRecordStaff() {
	var AREA_ID = $("#AREA_ID").val();
	var RECORD_DAY = $("#RECORD_DAY").val();
	var RECORD_MONTH = $("#RECORD_MONTH").val();
	var SEARCH_AREA_ID = $("#SEARCH_AREA_ID").val();
	var RECORD_YEAR = $("#RECORD_YEAR").val();
	var params = {
			AREA_ID : AREA_ID,
			RECORD_MONTH : RECORD_MONTH,
			SEARCH_AREA_ID : SEARCH_AREA_ID,
			RECORD_DAY : RECORD_DAY,
			RECORD_YEAR:RECORD_YEAR
	};
	$.messager.confirm('系统提示', '您确定要导出信息吗?', function (r) {
        if (r) {
            $('#staffFrom').form('submit', {
                url: webPath + "Record/exportRecordStaff.do",
                queryParams : params,
                onSubmit: function () {
                  //$.messager.progress();
                },
                success: function () {
                  	//$.messager.progress('close'); // 如果提交成功则隐藏进度条
                    $.messager.show({
                        title: '提  示',
                        msg: '导出成功!',
                        showType: 'show'
                    });
                }
            });
        }
    });
 }

function searchRecordStaff() {
	var AREA_ID = $("#AREA_ID").val();
	var RECORD_DAY = $("#RECORD_DAY").val();
	var RECORD_MONTH = $("#RECORD_MONTH").val();
	var SEARCH_AREA_ID = $("#SEARCH_AREA_ID").val();
	var RECORD_YEAR = $("#RECORD_YEAR").val();
	var searchDate = {
			AREA_ID : AREA_ID,
			RECORD_DAY : RECORD_DAY,
			RECORD_MONTH : RECORD_MONTH,
			SEARCH_AREA_ID : SEARCH_AREA_ID,
			RECORD_YEAR:RECORD_YEAR
	};
	$('#dg_soft').datagrid({
		//此选项打开，表格会自动适配宽度和高度。
		autoSize : true,
		width : 1800,
		height : 800,
		toolbar : '#tb_soft',
		url : webPath + "Record/queryRecordStaff.do",
		queryParams : searchDate,
		method : 'post',
		pagination : true,
		pageNumber : 1,
		pageSize : 10,
		pageList : [ 20, 50 ,500 ],
		loadMsg : '数据加载中.....',
		rownumbers : true,
		
		fit:true,
		singleSelect : false,
		
		
		
		columns : [ [ 
		{
			field : 'RECORD_TIME',
			title : '签到时间',
			width : "5%",
			align : 'center'
		},{
			field : 'DESCRP',
			title : '签到描述',
			width : "5%",
			align : 'center'
		},{
			field : 'POINT_NAME',
			title : '关键点名称',
			width : "10%",
			align : 'center'
		},{
			field : 'TASK_NAME',
			title : '任务名称',
			width : "10%",
			align : 'center'
		},{
			field : 'STAFF_AREA_NAME',
			title : '区域',
			width : "5%",
			align : 'center'
		},{
			field : 'STAFF_NO',
			title : '工号',
			width : "5%",
			align : 'center'
		},{
			field : 'STAFF_NAME',
			title : '姓名',
			width : "5%",
			align : 'center'
		} ] ],
		nowrap : false,
		striped : true,
		onLoadSuccess : function(data) {

		}
	});
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