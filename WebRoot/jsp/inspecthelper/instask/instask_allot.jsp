<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="../../util/head.jsp"%>
		<title>任务分配</title>
	</head>
<body style="padding: 3px; border: 0px">
	<div class="pageContent">
			<form id="ff">
			<input type="hidden" name="flag" value="${flag}">
			<div class="pageFormContent" >
				<table>
					<c:if test="${flag eq '0'}">
					<tr >
						<td>巡检人员：</td>
						<td>
							<div class="condition-text-container">
							<input type="hidden" name="staffId" id="staffId"/>
							<input id="staffName"  name="staffName" class="condition-text easyui-validatebox condition" readonly="readonly"/> 
							</div>
						</td>
						<td colspan="2">
						<input type="button" id="btnChoose"  value="选择人员" />
						</td>
					</tr>
					</c:if>
					<tr>
						<td>任务名称：</td>
						<td >
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name=taskCode id="taskCode" />
							</div>
						</td>
					</tr>
					<tr>
						<td>周期：</td>
						<td >
							<select class="condition-select" name="cycle" id="cycle">
								<option value="3">每月</option>
								<option value="5">每日</option>
								<option value="4">每周</option>
								<option value="6">两月</option>
								<option value="7">六月</option>
								<option value="1">每年</option>
							</select>
						</td>
						<td>频率：</td>
						<td colspan="3">
							<select class="condition-select" name="frequency" id="frequency">
								<option value='1'>1次</option>
								<option value='2'>2次</option>
								<option value='3'>3次</option>
							</select>
						</td>
					</tr>
					 <tr>
					 	<td>开始时间：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name=startDate id="startDate"
									required="true"
									onClick="WdatePicker({minDate:'%y-%M-{%d+1}'});" />
							</div>
						</td>
						<td>结束时间：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name=endDate id="endDate"
									required="true"
									onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'});" />
							</div>
						</td>
					 </tr>
				</table>
				</div>
			</form>
	
		<div style="height: 35px; border-bottom: 1px solid #d2d2d2;;">
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="saveTask();">保存</div>
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="back();">返回</div>
		</div>
		<input id="areaName" type="hidden" value="${AREA_NAME}" />
	</div>
	<div id="win_staff"></div>
	
<script type="text/javascript">
	function saveTask(){
		var pass = $("#ff").form('validate');
		if (pass) {
			var startDate = $("input[name='startDate']").val();
			var endDate = $("input[name='endDate']").val();
			if(startDate > endDate){
				alert("结束时间不能小于开始时间！");
				return;
			}
			$.messager.confirm('系统提示', '您确定要完成任务分配吗?', function(r) {
				if (r) {
					//智能分配和指定分配共用条件
					var taskCode = $("input[name='taskCode']").val();
					var cycle = $("select[name='cycle']").val();
					var frequency = $("select[name='frequency']").val();
					var flag = $("input[name='flag']").val();
					
					//智能分配条件
					var resType = $("select[name='resType']").val();
					var staffType = $("select[name='staffType']").val();
					var manaArea = $("input[name='manaArea']").val();
					var ownCompany = $("select[name='ownCompany']").val();
					var manaType = $("select[name='manaType']").val();
					var areaName = $("select[name='areaName']").val();
					var staffName = $("input[name='staffName']").val();
					var equpAddress = $("select[name='equpAddress']").val();
					var equCode = $("input[name='equCode']").val();
					var state = $("select[name='state']").val();
					
					//指定分配条件
					var staffId = $("input[name='staffId']").val();
					var equipIds = $("input[name='equipIds']").val();
					$.ajax({
						type : 'POST',
						url : webPath + "insTask/allotTask.do",
						data : {
							flag : flag,
							resType : resType,
							staffType : staffType,
							manaArea : manaArea,
							ownCompany : ownCompany,
							manaType : manaType,
							areaName : areaName,
							staffName : staffName,
							equpAddress : equpAddress,
							equCode : equCode,
							state : state,
							staffId : staffId,
							taskCode : taskCode,
							cycle : cycle,
							frequency : frequency,
							startDate : startDate,
							endDate : endDate,
							equipIds : equipIds
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '任务分配成功!',
									showType : 'show'
								});
							}
							$('#win_equip').window('close');
						}
					})
				}
			});
		}
		
	}
	function back(){
		$('#win_equip').window('close');
	}
	
	$("#btnChoose").click(function(){
		$('#win_staff').window({
			title : "【人员列表】",
			href : webPath + "insTask/staffIndex.do",
			width : 950,
			height : 500,
			zIndex : 2,
			region : "center",
			collapsible : false,
			cache : false,
			modal : true
		});
		
	});
	
</script>
</body>
</html>