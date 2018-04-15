<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="condition-container">
	<form id="addform"  method="post">
		<table class="condition">
			<tr id="tr_cityName">
				<td width="15%" style="display: none;">地区名：</td>
				<td width="20%" style="display: none;">
					<div>
						<select name="city_name" class="condition-select"
							onchange="getCable(this.value)">
							<option value=''>--请选择--</option>
							<c:forEach var="city" items="${cityModel }">
								<option value="${city.AREA_ID }">${city.NAME }</option>
							</c:forEach>
						</select>
					</div>
				</td>
				<td width="15%">线路段名：</td>
				<td width="20%">
					<div>
						<select name="cable_name" class="condition-select easyui-validatebox"
							onchange="getRelay(this.value)" data-options="required:true" >
							<option value=''>--请选择--</option>
						</select>
					</div>
				</td>
				<td width="15%">中继段名：</td>
				<td width="20%">
					<div>
						<select name="relay_name" class="condition-select easyui-validatebox" data-options="required:true">
							<option value=''>--请选择--</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%">测试日期：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="testdate" id="testdate" type="text"
							class="condition-text easyui-validatebox" data-options="required:true"
							onClick="WdatePicker();" />
					</div>
				</td>
				<td width="15%">中继段全长：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="relaydistance" id="relaydistance" type="text"
							class="condition-text easyui-validatebox"  data-options="validType:'doubleTest'" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%">测试地点1：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="place1" id="place1" type="text"
							class="condition-text easyui-validatebox" data-options="required:true"/>
					</div>
				</td>
				<td width="15%">测试人1：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="tester1" id="tester1" type="text"
							class="condition-text easyui-validatebox" data-options="required:true" />
					</div>
				</td>
				<td width="15%">测试仪表1：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="testmeter1" id="testmeter1" type="text"
							class="condition-text"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%">测试地点2：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="place2" id="place2" type="text"
							class="condition-text"  />
					</div>
				</td>
				<td width="15%">测试人2：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="tester2" id="tester2" type="text"
							class="condition-text" />
					</div>
				</td>
				<td width="15%">测试仪表2：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="testmeter2" id="testmeter2" type="text"
							class="condition-text"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="15%">测试窗口：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="testform" id="testform" type="text"
							class="condition-text"  />
					</div>
				</td>
				<td width="15%">折射率：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input name="refraction" id="refraction" type="text"
							class="condition-text"  />
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="btn-operation-container" style="margin: 0px auto; width: 130px;">
	<div style="float: left;" class="btn-operation"
		onClick="addTestInfo()">新增</div>
</div>
<script type="text/javascript">
$(function(){
	if(!("${localId}")){
		$("#tr_cityName td").css("display",'');
	}else{
		getCable("${localId}");
	}
});



function addTestInfo() {
	$("#addform").form('submit', {
		url : webPath + "/CutAndConnOfFiberController/addTestInfo.do",
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			}
			return isValid;	// 返回false终止表单提交

		},
		success : function(data) {
			var json = eval("("+data+")");
			if(json.status){
				$('#win').window('close');
				$("#dg").datagrid('reload');
				$.messager.alert("提示","新增成功");
			}else{
				$.messager.alert("提示","新增失败");
			}
		}
	});
}


	
</script>
