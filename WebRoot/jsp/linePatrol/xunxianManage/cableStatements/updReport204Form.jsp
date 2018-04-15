<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<div class="condition-container">
			<form id="updForm"  method="post">
				<input type="hidden" name="r_id" value="${rows[0].R_ID }"/>
				<table class="condition">
					<tr>
						<td width="15%">填报单位：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="unit"  type="text" class="condition-text" value="${rows[0].UNIT }" readonly="readonly" />
							</div>
						</td>
						<td width="15%">干线：</td>
						<td width="15%">
							<div class="condition-text-container">
								<input name="cable_name"  type="text" class="condition-text" value="${rows[0].CABLE_NAME }" readonly="readonly"/>
							</div>
						</td>
						<td width="15%">中继段名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="relay_name"  type="text" class="condition-text easyui-validatebox" 
								 value="${rows[0].RELAY_NAME }" readonly="readonly"/>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">测试日期：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="testDate"  onClick="WdatePicker();" value="${rows[0].TESTDATE }"/>
							</div>
						</td>
					
					
						<td width="15%">天气：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="weather"  type="text" class="condition-text easyui-validatebox" value="${rows[0].WEATHER }" />
							</div>
						</td>
						<td width="15%">杆号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="poleNum" type="text" class="condition-text easyui-validatebox" value="${rows[0].POLENUM }" />
							</div>
						</td>
						<td width="15%">接地电阻值：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="resistance" type="text" class="condition-text easyui-validatebox" value="${rows[0].RESISTANCE }" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" style="margin: 0px auto;width: 130px;">
			<div class="btn-operation" 
				onClick="updReport204Info()">修改</div>
	</div>
	
<script type="text/javascript">
function updReport204Info() {
	$("#updForm").form('submit', {
		url : webPath + "/CableStatementsController/updReport204Info.do",
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			}
			return isValid;	// 返回false终止表单提交
		},
		success : function(data) {
			var json=$.parseJSON(data);
			if(json.status){
				$("#win").window('close');
				$("#dg").datagrid('reload');
				$.messager.alert("提示","修改成功");
			}else{
				$.messager.alert("提示","修改失败");
			}
		}
	});
}
</script>

