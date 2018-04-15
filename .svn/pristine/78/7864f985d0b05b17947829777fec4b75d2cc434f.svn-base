<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>修改设备</title>
</head>
<body>
<div style="padding:20px 0 10px 50px">
	<form id="modifyEquipmentId">
		<input type="hidden"  name="modify_equipIds" id="modify_equipIds" value="${equipIds}" />
		<table>
			<tr>
				<td><label>区域：</label> </td>
				<td>
					<select name="modify_sonAreaName" id="modify_sonAreaName" class="condition-select">
							<option value="">${equ.SON_AREA }</option>
							<option value="">----------请选择----------</option>
							<c:forEach items="${sonAreaList}" var="v">
							<option value="${v.NAME}">${v.NAME}</option>
							</c:forEach>
					</select>
				</td>	
				<td><label>管理模式：</label></td>
				<td>
					<select name="modify_manaType" id="modify_manaType" class="condition-select">
						<option value="">${equ.MANAGE_TYPE}</option>
						<option value="">----------请选择----------</option>
						<option value="光交接箱">光交接箱</option>
						<option value="ODF">ODF</option>
						<option value="光分纤箱">光分纤箱</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>设备位置：</label></td>
				<td>
					<select name="modify_equpAddress" id="modify_equpAddress" class="condition-select">
						<option value="">${equ.EQUP_ADDRESS }</option>
						<option value="">----------请选择----------</option>
						<option value="路边">路边</option>
						<option value="小区">小区</option>
						<option value="交接间">交接间</option>
						<option value="网监">网监</option>
						<option value="其他">其他</option>
					</select>
				</td>
				<td><label>是否健康：</label></td>
				<td>
					<select name="modify_state" id="modify_state" class="condition-select">
						<c:if test="${equ.STATE=='1'}"><option value="">是</option></c:if>
						<c:if test="${equ.STATE=='0'}"><option value="">否</option></c:if>
						<option value="">----------请选择----------</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>设备状态：</label></td>
				<td>
					<select name="modify_state_" id="modify_state_" class="condition-select">
						<c:if test="${equ.STATE_=='1'}"><option value="">是</option></c:if>
						<c:if test="${equ.STATE_=='0'}"><option value="">否</option></c:if>
						<option value="">----------请选择----------</option>
						<option value="0">失效</option>
						<option value="1">有效</option>
					</select>
				</td>
				<td><label>地区：</label></td>
				<td>
					<select name="modify_areaName" id="modify_areaName" class="condition-select">
							<option value="">${equ.AREA }</option>
							<option value="">----------请选择----------</option>
							<c:forEach items="${areaList}" var="v">
							<option value="${v.NAME}">${v.NAME}</option>
							</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<div style="text-align:left;padding:10px 0 10px 90px">
		<div class="btn-operation" onClick="saveEdit()">保存</div>
		<div class="btn-operation" onClick="cancelEdit()">返回</div>
	</div>
</div>

<script type="text/javascript">

	function cancelEdit(){
		$('#win_equip').window('close');
	}

	function saveEdit(){
	
		if($("#modifyEquipmentId").form('validate')){
			$.messager.confirm('系统提示', '您确定要修改资源吗?', function(r) {
				if(r){
					var equipIds = $("input[name='modify_equipIds']").val();
					var sonAreaName = $("select[name='modify_sonAreaName']").val();
					var manaType = $("select[name='modify_manaType']").val();
					var equpAddress = $("select[name='modify_equpAddress']").val();
					var state = $("select[name='modify_state']").val();
					var state_ = $("select[name='modify_state_']").val();
					var areaName = $("select[name='modify_areaName']").val();
					
					$.ajax({
						type : 'POST',
						url : webPath + "equip/modifyEquip.do",
						data : {
							equipIds:equipIds,
							sonAreaName : sonAreaName,
							manaType : manaType,
							equpAddress : equpAddress,
							state : state,
							state_ :state_,
							areaName : areaName
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '修改资源成功!',
									showType : 'show'
								});
							}
							
							//修改完成后关闭修改窗口并重新查询数据
							$('#win_equip').window('close');
							searchData();
						}
					});
				}
			});
		}
	}
</script>
</body>
</html>
