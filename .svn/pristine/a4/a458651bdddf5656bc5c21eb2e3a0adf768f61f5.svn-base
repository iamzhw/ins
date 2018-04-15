<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@include file="../../../util/head.jsp"%>
<style type="text/css">
.condition-textarea-container {
	background: none repeat scroll 0 0 #FFFFFF;
    border-color: #C9D3E1;
    border-style: solid;
    border-width: 1px;
    color: #000000;
    font-size: 12px;
    height: 100px;
    line-height: 22px;
    width: 150px;
    padding-left: 3px;
}

.condition-textarea {
	width: 145px;
	height:95px;
	font-size: 12px;
	border: 0px;
	margin-left: 3px;
}

</style>

<title>巡线任务新增</title>

</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="lineTaskAdd" method="post">
			<table id="taskAddTab" style="width: 100%;">
				<tr>
					<td>任务名称：</td>
					<td>
						<div class="condition-text-container">
							<input type="hidden" name="area_id" value="${AREA_ID}"/>
							<input class="condition-text easyui-validatebox condition"
								type="text" name="task_name" id="task_name" />
						</div>
					</td>
					<td>巡线人：</td>
					<td>
						<select name="inspect_id" class="condition-select condition" 
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" >
							<c:forEach items="${inspects}" var="al">
								<option value="${al.STAFF_ID}">${al.STAFF_NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="start_date" id="start_date" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});"/>
						</div>
					</td>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="end_date" id="end_date" 
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>巡线段：</td>
					<td colspan="3">
						<div class="condition-textarea-container ">
							<input id="lineIds" name="lineIds" type="hidden" />
							<textarea class="condition-textarea" name="lineNames"  id="lineNames"  onclick="queryLines()" readonly="readonly"></textarea>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="history.back();">返回</div>
		</div>
	</div>
	<div id="win_task"></div>
	<script type="text/javascript">
	
	function queryLines()
	{
		$('#win_task').window({
			title : "【巡线段列表】",
			href : webPath + "lineTask/getLineList.do",
			width : 300,
			height : 400,
			zIndex : 2,
			region : "center",
			collapsible : false,
			cache : false,
			modal : true
		});
	}
	
	
	function saveForm(){
		
		if($("#lineTaskAdd").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存任务吗?', function(r) {
			if(r){
				var data=makeParamJson('lineTaskAdd');
				data.lineIds=$("#lineIds").val();
				$.ajax({
					type : 'POST',
					url : webPath + "/lineTask/addTask.do",
					data :data,
					dataType : 'json',
					success : function(json) {
						if (json.status) {
							$.messager.show({
								title : '提  示',
								msg : '保存任务成功!',
								showType : 'show'
							});
						}
						location.href = 'index.do';
					}
				});
			}
		});
		}
	}
	</script>
</body>
</html>