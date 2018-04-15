<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<div class="condition-container">
			<form id="updForm"  method="post">
				<input name="rowIndex" value="${rows[0].ROWINDEX }" type="hidden"/>
				<input name="cityKey" value="${rows[0].CITYKEY }" type="hidden"/>
				<input name="yearPart" value="${rows[0].YEARPART }" type="hidden"/>
				<table class="condition">
					<tr>
						<td width="15%">局名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="name"  type="text" class="condition-text" value="${rows[0].NAME }" readonly="readonly" />
							</div>
						</td>
						<td width="15%">光缆名：</td>
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
						<td width="15%">纤芯号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="xinNumber"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].XINNUMBER }" data-options="validType:'integer'"/>
							</div>
						</td>
					
					
						<td width="15%">每公里衰耗值dB/km：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="oneNumber"  type="text" class="condition-text easyui-validatebox" value="${rows[0].ONENUMBER }" data-options="validType:'doubleTest'"/>
							</div>
						</td>
						<td width="15%">竣工衰耗基准值dB/km：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="junNumber" type="text" class="condition-text easyui-validatebox" value="${rows[0].JUNNUMBER }" data-options="validType:'doubleTest'"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" style="margin: 0px auto;width: 130px;">
			<div class="btn-operation" 
				onClick="updReport203Info()">修改</div>
	</div>
	
<script type="text/javascript">
function updReport203Info() {
	$("#updForm").form('submit', {
		url : webPath + "/CableStatementsController/updReport203Info.do",
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

