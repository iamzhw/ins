<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<style type="text/css">
	.condition-container {
		height: 48px;
		padding-left: 26px;
		padding-top: 15px;
		width: 80%;
	}
	
	.btn-operation-container {
		height: 42px;
		padding-left: 26px;
		padding-right: 36px;
		padding-top: 10px;
		width: 80%;
	}

	.datagrid-pager {
		border-style: solid;
		border-width: 0px;
		margin: 0px 20px;
		width:80%;
	}
</style>
<title>新增机房</title>
</head>
<body>
	<div style="padding:20px 0 10px 50px">
		<form id="roomId" >
			<input type="hidden" id="ifGLY" />
			<table style="width: 100%;">
				<tr>
					<td width="10%;"><label>机房名称：</label> </td>
					<td width="20%">
						<div class="condition-text-container">
						<input type="hidden" name="room_id" value="${room.ROOM_ID}"/>
						<input name="room_name" class="condition-text easyui-validatebox condition"
						type="text" data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${room.ROOM_NAME}" />
						</div>
					</td>
					<td width="10%;"><label>机房类型：</label></td>
					<td width="20%">
					<select name="room_type_id" class="condition-select condition" disabled="disabled"
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" >
						<c:forEach items="${roomTypes}" var="v">
							<option <c:if test="${v.ROOM_TYPE_ID==room.ROOM_TYPE_ID}">selected</c:if> value="${v.ROOM_TYPE_ID}">${v.ROOM_TYPE_NAME}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td width="10%;"><label>巡检人：</label></td>
					<td width="20%">
						<div class="condition-text-container">
							<input id="CHECK_STAFF_ID" name="check_staff_id" type="hidden" value="${room.CHECK_STAFF_ID}"/>
							<input id="CHECK_STAFF_NAME" name="check_staff" type="text" class="condition-text easyui-validatebox condition" style="cursor: pointer;"
								data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" readonly="readonly" value="${room.STAFF_NAME}" onclick="queryStaff()"/>
						 </div>
					</td>
					<td width="10%;"><label>地址：</label></td>
					<td width="20%">
						<div class="condition-text-container">
							<input type="text" name="address" id="address" class="condition-text easyui-validatebox condition"
							 	data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${room.ADDRESS}"/>
					 	</div>
					</td>
				</tr>
				<tr>
					<td width="10%;"><label>经度：</label></td>
					<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition" type="text" name="longitude"
							data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${room.LONGITUDE}" />
					</div>
					</td>
					<td width="10%;"><label>纬度：</label></td>
					<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition"  type="text" name="latitude" 
							data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${room.LATITUDE}"/>
					</div>
					</td>
				</tr>
				<tr>
					<td width="10%">
						地市：
					</td>
					<td width="20%">
						<input type="hidden" id="areaId" value="${room.AREA_ID}"/>
						<select name="room_area" class="condition-select">
							<c:forEach items="${areaList}" var="al">
								<option value="${al.AREA_ID}">
									${al.NAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td width="10%">
						区县：
					</td>
					<td width="20%">
						<input type="hidden" id="sonAreaId" value="${room.SON_AREA_ID}"/>
						<select name="room_son_area" class="condition-select condition" 
						data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" >
							<c:forEach items="${sonAreaList}" var="al">
								<option value="${al.AREA_ID}"
									<c:if test="${al.AREA_ID==room.SON_AREA_ID}">selected</c:if>>${al.NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%;"><label>修改者：</label></td>
					<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition" type="text" name="modify_by"  width="100%;" readonly="readonly" value="${staffNo}"/>
					</div>
					</td>
					<td width="10%;"><label>修改时间：</label></td>
					<td width="20%">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition" type="text" name="modify_date" readonly="readonly"  value="${currentDate}"/>
					</div>
					</td>
				</tr>
				
				<tr>
					<td width="10%;"><label>机房编码：</label></td>
					<td colspan="3" width="20%;">
					<div class="condition-text-container">
						<input class="condition-text easyui-validatebox condition" type="text" name="room_code" 
							data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项'" value="${room.ROOM_CODE}"/>
					</div>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align:center;padding:10px 0 10px 50px">
			<div class="btn-operation" onClick="saveForm()">保存</div>
			<div class="btn-operation" onClick="history.back();">返回</div>
		</div>
	</div>
	<div id="win_staff"></div>
	<script type="text/javascript">
	
		$(document).ready(function() {
			
			selectSelected();
			
			//查询
			if($("#ifGLY").val()=="1"){
				getRoomSonAreaList();
			}else{
				getRoomSonAreaList();
				$("select[name='room_area']").change(function() {
					//根据area_id，获取区县
					getRoomSonAreaList();
				});
			}
		});
		
		//判断用户是否为超级管理员,超级管理员可以新增所有地区的机房
		function selectSelected(){
			$.ajax({
					type : 'POST',
					url : webPath + "Staff/selectSelected.do",
					dataType : 'json',
					async:false,
					success : function(json) {
						
						$("select[name='room_area']").val($("#areaId").val());
						
						if(json[0].ifGly==1){			
							$("#ifGLY").val("0");
						}else{
							$("select[name='room_area']").attr("disabled","disabled");
							$("#ifGLY").val("1");
						}
					
					}
				});
		}
	
		function queryStaff()
		{
			$('#win_staff').window({
				title : "【人员列表】",
				href : webPath + "Staff/lookup.do",
				width : 900,
				height : 500,
				zIndex : 2,
				region : "center",
				collapsible : false,
				cache : false,
				modal : true
			});
			
		}
		
		function saveForm(){
	
		if($("#roomId").form('validate')){
			$.messager.confirm('系统提示', '您确定要保存该机房吗?', function(r) {
				if(r){
					var room_id = $("input[name='room_id']").val();
					var room_name = $("input[name='room_name']").val();
					var room_type_id = $("select[name='room_type_id']").val();
					var check_staff_id = $("input[name='check_staff_id']").val();
					var address = $("input[name='address']").val();
					var longitude = $("input[name='longitude']").val();
					var latitude = $("input[name='latitude']").val();
					var modify_by = $("input[name='modify_by']").val();
					var modify_date = $("input[name='modify_date']").val();
					var room_code = $("input[name='room_code']").val();
					var area_id = $("select[name='room_area']").val();
					var son_area_id = $("select[name='room_son_area']").val();
					
					$.ajax({
						type : 'POST',
						url : webPath + "/jfxjRoom/update.do",
						data : {
							room_id	: room_id,
							room_name : room_name,
							room_type_id : room_type_id,
							check_staff_id : check_staff_id,
							room_type_id : room_type_id,
							address : address,
							longitude : longitude,
							latitude : latitude,
							modify_by : modify_by,
							modify_date : modify_date,
							room_code : room_code,
							area_id : area_id,
							son_area_id : son_area_id
						},
						dataType : 'json',
						success : function(json) {
							if (json.status) {
								$.messager.show({
									title : '提  示',
									msg : '保存机房成功!',
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
		
	function getRoomSonAreaList() {
		var area_id = $("select[name='room_area']").find("option:selected").val();
		
		$.ajax({
			type : 'POST',
			url : webPath + "General/getSonAreaList.do",
			data : {
				areaId : area_id
			},
			dataType : 'json',
			success : function(json) {
				var result = json.sonAreaList;
				$("select[name='room_son_area'] option").remove();
				for ( var i = 0; i < result.length; i++) {
					$("select[name='room_son_area']").append(
							"<option value=" + result[i].AREA_ID + ">"+ result[i].NAME + "</option>");
				}
			}
		});
	}

</script>
</body>
</html>