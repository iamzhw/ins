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
		<form id="lineTaskEdit" method="post">
			<table id="taskEditTab" style="width: 100%;">
				<tr>
					<td>任务名称：</td>
					<td>
						<div class="condition-text-container">
							<input type="hidden" name="area_id" value="${AREA_ID}"/>
							<input type="hidden" name="task_id" value="${TASK_ID}"/>
							<input class="condition-text easyui-validatebox condition"
								type="text" name="task_name" id="task_name"  value="${TASK_NAME}" readonly="readonly"/>
						</div>
					</td>
					<td>巡线人：</td>
					<td>
						<select name="inspect_id" class="condition-select condition" 
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" >
							<c:forEach items="${inspects}" var="al">
								<option value="${al.STAFF_ID}" 
								<c:if test="${al.STAFF_ID==INSPECT_ID}">selected</c:if>>${al.STAFF_NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>开始时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="start_date" id="start_date" value="${START_DATE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " disabled="disabled"/>
						</div>
					</td>
					<td>结束时间：</td>
					<td>
						<div class="condition-text-container">
							<input class="condition-text easyui-validatebox condition"
								type="text" name="end_date" id="end_date" value="${END_DATE}"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " 
								onClick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'});"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>巡线段：</td>
					<td colspan="3">
						<div class="condition-textarea-container ">
							<input id="lineIds" name="lineIds" type="hidden" value="${lineIds}"/>
							<textarea class="condition-textarea" name="lineNames"  id="lineNames"  readonly="readonly">${lineNames}</textarea>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="updateForm()">保存</div>
			<div class="btn-operation" onClick="history.back();">返回</div>
		</div>
	</div>
	<div id="win_task"></div>
	<script type="text/javascript">
	
	function updateForm(){
		
		if($("#lineTaskEdit").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存任务吗?', function(r) {
			if(r){
				var data=makeParamJson('lineTaskEdit');
				$.ajax({
					type : 'POST',
					url : webPath + "/lineTask/updateTask.do",
					data :data,
					dataType : 'json',
					success : function(json) {
						if (json.status) {
							$.messager.show({
								title : '提  示',
								msg : '修改任务成功!',
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