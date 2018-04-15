<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="condition-container">
			<form id="addForm"  method="post">
				<table class="condition">
					<tr id="tr_cityName">
						<td width="10%">填报单位：</td>
						<td width="20%">
							<div>
								<select name="city_name"  class="condition-select" onchange="getCableByAdd(this.value)">
									<option value="${cityModel[0].AREA_ID }">${cityModel[0].NAME }</option>
								</select>
							</div>
						</td>
						<td width="10%">线路段名：</td>
						<td width="20%">
							<div>
								<select name="cable_name"  class="condition-select" onchange="getRelayByAdd(this.value)">
								</select>
							</div>
						</td>
						<td width="10%">中继段名：</td>
						<td width="20%">
							<div>
								<select name="relay_name"  class="condition-select">
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>报表期间:</td>
						<td width="20%">
							<div class="condition-text-container">
							<input class="condition-text condition easyui-validatebox" name="yearpart" onClick="WdatePicker({skin:'default',dateFmt:'yyyy'});"
								data-options="required:true"	 />
							</div>
						</td>
						<td width="20%">
							<div>
								<select name="upOrDown"  class="condition-select">
									<option value='01'>--上半年--</option>	
									<option value='02'>--下半年--</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>测试日期:</td>
						<td width="20%">
							<div class="condition-text-container ">
							<input class="condition-text condition easyui-validatebox" name="testdate" onClick="WdatePicker();"
								data-options="required:true" />
							</div>
						</td>
						<td>天气:</td>
						<td width="20%">
							<div class="condition-text-container ">
							<input class="condition-text condition " name="weather"  />
							</div>
						</td>
						<td>杆号:</td>
						<td width="20%">
							<div class="condition-text-container ">
							<input class="condition-text condition " name="polenum"   />
							</div>
						</td>
						<td>接地电阻值:</td>
						<td width="20%">
							<div class="condition-text-container ">
							<input class="condition-text condition easyui-validatebox" name="resistance"  data-options="validType:'doubleTest'" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	<div class="btn-operation-container">
		<div style="float: left;" class="btn-operation" onClick="addReport204()">增加</div>
	</div>	
	<div id="dg" style="width: 98%; height: 80%;"></div>
	<div id="win"></div>
	<script type="text/javascript">
	$(function(){
		if($("select[name='city_name']").val()){
			getCableByAdd($("select[name='city_name']").val());
		}
	});
	
			
		
		//根据地区获得地区光缆
		function getCableByAdd(areaId){
			$("select[name='cable_name']").find("option").remove();
			$("select[name='relay_name']").find("option").remove();
			if(!areaId) return;
			$.ajax({
				url : webPath + "/CutAndConnOfFiberController/getCable.do",// 跳转到 action    
				data : {
					areaId : areaId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='cable_name']").append("<option value="+this.CABLE_ID+">"+this.CABLE_NAME+"</option>");
					});
					getRelayByAdd($("select[name='cable_name']").val());
				}
			});
		}
		
		//根据光缆获得中继段
		function getRelayByAdd(cableId){
			$("select[name='relay_name']").find("option").remove();
			if(!cableId) return;
			$.ajax({
				url : webPath + "/CutAndConnOfFiberController/getRelay.do",// 跳转到 action    
				data : {
					cableId : cableId
				},
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$(data).each(function(){
						$("select[name='relay_name']").append("<option value="+this.RELAY_ID+">"+this.RELAY_NAME+"</option>");
					});
				}
			});
		}
		
		
		
		function addReport204(){
			$("#addForm").form('submit', {
				url : webPath + "/CableStatementsController/addReport204Info.do",
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
						$.messager.alert("提示","新增成功");
					}else{
						$.messager.alert("提示","新增失败");
					}
				}
			});
		}
		
	</script>
