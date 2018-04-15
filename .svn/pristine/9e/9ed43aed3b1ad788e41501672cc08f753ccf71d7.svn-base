<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<div class="condition-container">
			<form id="updForm"  method="post">
				<input name="relayinfoId" value="${model[0].RELAYINFOID }" type="hidden"/>
				<table class="condition">
					<tr>
						<td width="15%">线路名称：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="cable_name" id="cable_name" type="text" class="condition-text" value="${model[0].CABLE_NAME }" readonly="readonly" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">中继段：</td>
						<td width="15%">
							<div class="condition-text-container">
								<input name="relay_name" id="relay_name" type="text" class="condition-text" value="${model[0].RELAY_NAME }" readonly="readonly"/>
							</div>
						</td>
						<td width="15%">中继段全长：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="relaydistance" id="relaydistance" type="text" class="condition-text easyui-validatebox" 
								 value="${model[0].RELAYDISTANCE }" data-options="validType:'doubleTest'"/>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">测试日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testdate" id="testdate" type="text" class="condition-text easyui-validatebox" 
									value="${model[0].TESTDATE }" onClick="WdatePicker();" data-options="required:true"/>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">测试地点1：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="place1" id="place1" type="text" class="condition-text easyui-validatebox" value="${model[0].PLACE1 }" data-options="required:true"/>
							</div>
						</td>
						<td width="15%">测试人1：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="tester1" id="tester1" type="text" class="condition-text easyui-validatebox" value="${model[0].TESTER1 }" data-options="required:true"/>
							</div>
						</td>
						<td width="15%">测试仪表1：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testmeter1" id="testmeter1" type="text" class="condition-text" value="${model[0].TESTMETER1 }"/>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">测试地点2：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="place2" id="place2" type="text" class="condition-text" value="${model[0].PLACE2 }"/>
							</div>
						</td>
						<td width="15%">测试人2：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="tester2" id="tester2" type="text" class="condition-text" value="${model[0].TESTER2 }"/>
							</div>
						</td>
						<td width="15%">测试仪表2：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testmeter2" id="testmeter2" type="text" class="condition-text" value="${model[0].TESTMETER2 }"/>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">测试窗口：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testform" id="testform" type="text" class="condition-text" value="${model[0].TESTFORM }"/>
							</div>
						</td>
						<td width="15%">折射率：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="refraction" id="refraction" type="text" class="condition-text" value="${model[0].REFRACTION }"/>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" style="margin: 0px auto;width: 130px;">
			<div class="btn-operation" 
				onClick="updTestInfo()">修改</div>
	</div>
	
<script type="text/javascript">
function updTestInfo() {
	$("#updForm").form('submit', {
		url : webPath + "/CutAndConnOfFiberController/updTestInfo.do",
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

