
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>代巡休假详细</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="tb" style="padding: 5px; height: auto">
		<div class="condition-container">
			<form id="form" action="" method="post">
				<table class="condition">
					<tr>
						<td width="10%">选择日期：</td>
						<td>
							<div class="condition-text-container">
								<input name="param_date" id="param_date" type="text" class="condition-text" onClick="WdatePicker({dateFmt:'yyyy-MM'});" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container">
			<div style="float:left;" class="btn-operation"
				onClick="serchData()">查询</div>
		</div>
	</div>
	
	<div id="dg" style="width: 98%"></div>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
</body>
	<script type="text/javascript">
	var d = new Date();
    function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
    var val = d.getFullYear().toString() +'-'+ addzero(d.getMonth() + 1)
    document.getElementById('param_date').value=val;
	
	function serchData(){
		$('#dg').datagrid('load',{
			param_date:$("input[name='param_date']").val()
		});
	}
	$(function(){
		var obj=makeParamJson('form');
		$('#dg').datagrid({
			autoSize : true,
			queryParams: obj,
			url : webPath + "/replaceHolidayController/showDetailInfo.do",
			fitColumns:true,
			method : 'post',
			rownumbers : true,
			singleSelect : true,
			nowrap : false,
			striped : true,
			columns : [
				[ 
					{field:'USER_NAME',title:'休假人员',width:50,align:'center'},
					{field:'HOLIDAY_TYPE',title:'休假类型',width:50,align:'center',
						formatter: function(value,row,index){
							if (value==0){
								return "休息";
							} else {
								return "代巡";
							}
						}
					},
					{field:'REPLACE_NAME',title:'代巡人员',width:50,align:'center',
						formatter:function(value,row,index){
							if(row.HOLIDAY_TYPE==0){
								return '';
							}else{
								return value;
							}
						}	
					},
					{field:'REST_TYPE',title:'休息类型',width:50,align:'center',
						formatter: function(value,row,index){
							if (value==0){
								return "事假";
							} else {
								return "调休";
							}
						}
					},
					{field:'MODEL_HOLIDAY_DATE',title:'休假日期',width:50,align:'center'},
					{field:'HOLIDAY_DESC',title:'休假原因',width:50,align:'center'}
				]
			]
		});
	});
		
	</script>
</html>
