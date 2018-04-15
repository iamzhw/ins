<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<div class="condition-container">
			<form id="updForm"  method="post">
				<input type="hidden" name="r_id" value="${rows[0].SUBID }"/>
				<table class="condition">
					<tr>
						<td width="15%">线路名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="cable_name"  type="text" class="condition-text" value="${rows[0].CABLE_NAME }" readonly="readonly" />
							</div>
						</td>
						<td width="15%">中继段名：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="relay_name"  type="text" class="condition-text easyui-validatebox" 
								 value="${rows[0].RELAY_NAME }" readonly="readonly"/>
							</div>
						</td>
						<td width="15%">金属护套对地绝缘电阻：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="resistance"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].RESISTANCE }" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">接头盒检测电极间绝缘电阻：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="resistanceset"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].RESISTANCESET }" />
							</div>
						</td>
						<td width="15%">测试日期：</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition"
								type="text" name="testdate"  onClick="WdatePicker();" value="${rows[0].TESTDATE }"/>
							</div>
						</td>
						<td width="15%">测试仪器：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testmeter"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].TESTMETER }" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">天气：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="weather"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].WEATHER }" />
							</div>
						</td>
						<td width="15%">中继段长度(KM)：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="fiberlengthatnow"  type="text" class="condition-text easyui-validatebox" readonly="readonly"
									value="${rows[0].FIBERLENGTHATNOW }" />
							</div>
						</td>
						<td width="15%">监测段缆长(KM)：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testlinelength"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].TESTLINELENGTH }" />
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">检测标石号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="testnumber"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].TESTNUMBER }" />
							</div>
						</td>
						<td width="15%">起/止接头标石号：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="teststartend"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].TESTSTARTEND }" />
							</div>
						</td>
						<td width="15%">填报日期：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="editdate"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].EDITDATE }" onClick="WdatePicker();"/>
							</div>
						</td>
						<td width="15%">备注：</td>
						<td width="20%">
							<div class="condition-text-container">
								<input name="mome"  type="text" class="condition-text easyui-validatebox" 
									value="${rows[0].MOME }" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-operation-container" style="margin: 0px auto;width: 130px;">
			<div class="btn-operation" 
				onClick="updReport207Info()">修改</div>
	</div>
	
<script type="text/javascript">
function updReport207Info() {
	$("#updForm").form('submit', {
		url : webPath + "/CableStatementsController/updReport207Info.do",
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

