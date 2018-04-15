<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="addStepDataForm" method="post">
		<table class="condition" style="width: 100% ;margin: 0px auto;" >
			<tr id="tr_cityName">
				<td width="20%" style="display: none;">地区名：</td>
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
				<td width="20%" >*干线光缆名称：</td>
				<td width="20%">
					<div>
						<select name="cable_name" class="condition-select"
							onchange="getRelay(this.value)">
							<option value=''>--请选择--</option>
						</select>
					</div>
				</td>
				<td width="20%">*中继段：</td>
				<td width="20%">
					<div>
						<select name="relay_name" class="condition-select">
							<option value=''>--请选择--</option>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">测试方向：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="direction"  />
					</div>
				</td>
				<td width="20%">纤号：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="fiberno"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">台阶位置：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="stepplace"  />
					</div>
				</td>
				<td width="20%">损耗：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="loss"  />
					</div>
				</td>
			</tr>
			<tr>
				<td width="20%">计划整改时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="plandate" onClick="WdatePicker();" />
					</div>
				</td>
				<td width="20%">实际整改时间：</td>
				<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text condition" type="text" name="realdate" onClick="WdatePicker();" />
					</div>
				</td>
			</tr>
		</table>
	<div class="btn-operation-container" style="width:10%;margin: 0px auto;">
		<div style="float: left;" class="btn-operation" onClick="addStepData()" >新增</div>
	</div>
</form>
	<iframe src="" style="display: none;" id="hiddenIframe"></iframe>
<script type="text/javascript">
	$(function(){
		if("${localId}"){
			getCable("${localId}");
		}else{
			$("#tr_cityName td").css("display",'');
		}
	});

	//根据地区获得地区光缆
	function getCable(areaId){
		$("select[name='cable_name']").find("option:not(:first)").remove();
		$("select[name='relay_name']").find("option:not(:first)").remove();
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
			}
		});
	}
	
	//根据光缆获得中继段
	function getRelay(cableId){
		$("select[name='relay_name']").find("option:not(:first)").remove();
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
	
	function addStepData(){
		$("#addStepDataForm").form('submit', {
			url : webPath + "/CutAndConnOfFiberController/addStepData.do",
			onSubmit : function() {
				// do some check    
				// return false to prevent submit;    
			},
			success : function(data) {
				var json=$.parseJSON(data);
				if(json.status){
					$('#win').window('close');
					$('#dg').datagrid('reload');
					$.messager.alert("提示","新增成功");
				}else{
					$.messager.alert("提示","新增失败");
				}
			}
		});
	}
	
</script>
