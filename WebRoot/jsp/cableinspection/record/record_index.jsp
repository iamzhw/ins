<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
		<title>外力点检查记录</title>
	</head>
<body style="padding:3px;border:0px">
	<div id="tb" style="padding:5px;height:auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
					<td width="10%">开始时间：</td>
						<td width="40%">
							<div class="condition-text-container">
							<input type="text" name=start_time  class="condition-text" 
								onClick="WdatePicker({maxDate:'%y-%M-{%d}'});" />
							</div></td>
						<td width="10%">结束时间：</td>
						<td width="40%">
							<div class="condition-text-container">
								<input name="end_time" type="text" onClick="WdatePicker({maxDate:'%y-%M-{%d}'});" class="condition-text" />
							</div></td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<!--<div class="btn-operation" onClick="exportStaff();">
					导出
				</div>
			--><div style="float:right;" class="btn-operation" onClick="clearCondition('form')">重置</div>
			<div style="float:right;" class="btn-operation"
				onClick="searchData()">查询</div>
		</div>
	</div>
<table id="dg" title="【外力点检查记录】" style="width:100%;height:480px">
	</table>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function() {
		//searchData();
	});
	
	function searchData() {
		var start_time = $("input[name='start_time']").val().trim();
		var end_time = $("input[name='end_time']").val().trim();
		//var area_id=$("input[name='area_id']").val().trim();
		//var son_area_id=$("input[name='son_area_id']").val().trim();
		var obj = {
			start_time : start_time,
			end_time : end_time
		};
		//return;
		$('#dg').datagrid({
			//此选项打开，表格会自动适配宽度和高度。
			autoSize : true,
			toolbar : '#tb',
			url : webPath + "Record/query.do",
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
				field : 'RECORD_ID',
				title : '记录id',
				width : "10%",
				hidden : true
				//checkbox : true
			}, {
				field : 'AREA_NAME',
				title : '城市',
				width : "10%",
				align : 'center'
			} ,{
				field : 'SON_AREA_NAME',
				title : '地区',
				width : "10%",
				align : 'center'
			},{
				field : 'STAFF_NAME',
				title : '检查人员',
				width : "10%",
				align : 'center'
			},{
				field : 'TASK_NAME',
				title : '任务名称',
				width : "10%",
				align : 'center'
			},{
				field : 'PLAN_NAME',
				title : '计划名称',
				width : "10%",
				align : 'center'
			},{
				field : 'SUMPHOTO',
				title : '拍摄照片',
				width : "10%",
				align : 'center',
					formatter:function(value,rowData,rowIndex){

                            //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）

                            //alert(rowData.username);  
							//alert(value);
							var reid=rowData.RECORD_ID;
							if(value==0){
							return value;  
							}
                            return "<a href='javascript:queryPhoto("+reid+")' >"+value+"</a>";  
							
                       } 
			},{
				field : 'CREATE_TIME',
				title : '检查时间',
				width : "15%",
				align : 'center'
			},{
				field : 'DESCRP',
				title : '描述',
				width : "15%",
				align : 'center',
				resizable:true
			},{
				field : 'CABLE_LEVEL',
				title : '光缆级别',
				width : "10%",
				align : 'center'
			},{
				field : 'ISKEEP',
				title : '是否需要看护',
				width : "10%",
				align : 'center'
			},{
				field : 'EQUIP_CODE',
				title : '设备编码',
				width : "10%",
				align : 'center'
			},{
				field : 'EQUIPMENT_TYPE_NAME',
				title : '设备类型',
				width : "10%",
				align : 'center'
			}
			
			] ],
			//width : 'auto',
			nowrap : false,
			striped : true,
			//onClickRow:onClickRow,
			//onCheck:onCheck,
			//onSelect:onSelect,
			//onSelectAll:onSelectAll,
			onLoadSuccess : function(data) {
			}
		});
	}
	function clearCondition(form_id) {
		$("input[name='start_time']").val('');
		$("input[name='end_time']").val('')
	}

	
	/**点击checkbox触发**/
	function onCheck(index, row) {
		alert("onCheck");
	}

	function onSelect(index, row) {
		alert("onSelect");
	}

	function onSelectAll(rows) {
		alert(rows);
		alert("onSelectAll");
	}
	function queryPhoto(reid) {
	addTab("查看图片",webPath+"Record/queryPhoto.do?record_id="+reid);
		//location.href = "show.do?cableId="+row.CABLE_ID;
		<%--
		var record_id=reid;
		$.ajax({
			type : 'POST',
			url : webPath + "Record/queryPhoto.do",
			data : {
				record_id : record_id
			},
			dataType : 'json',
			success : function(json) {

			}
		})--%>
        
}
	
</script>
