<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../util/head.jsp"%>
<title>坐标采集详情</title>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 100%;
	width:300px;
	float: left;
}

#r-result {
	height: 100%;
	width: 280px;
	float: right;
}
.map_bubble_title{
	font-weight: bold;
	font-size: 15px;
	float: left;
	line-height: 25px;
	border-bottom: 1px;
	border-bottom-color:#ccc; 
	margin-left:10px;
	padding-bottom: 8px;
	cursor: pointer;
}
.map_bubble_info{
	line-height: 25px;
}
</style>
</head>
<body style="padding: 3px; border: 0px">
<div id="l-map"></div>
		<div id="r-result">
			<div style="height: 35px; border-bottom: 1px solid #d2d2d2;">
				<div class="btn-operation" onClick="update();">更新坐标</div>
				<div class="btn-operation" onClick="history.back();">返回</div>
			</div>
			<div style="overflow:auto; width: 100%;height: 430px;">
			<div id ="details">
				<c:forEach items="${detailList }" var="detail">
				<div>
					<table id="${detail.RECORD_ID}">
						<tr>
							<td style="width: 25%; height: 30px; font-weight: bold;">设备编码：</td>
							<td style="text-align: left; width: 50%; font-weight: bold;">
							<input name="record_id" type=hidden class="" value="${detail.RECORD_ID}"/>
							<input name="equip_code" type="text" class="" value="${detail.EQUIP_CODE}"/>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备名称：</td>
							<td style="text-align: left; width: 70%;">
							<input name="equip_name" type="text" class="" value="${detail.EQUIP_NAME }"/>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备类型：</td>
							<td style="text-align: left; width: 70%;">
							<select name="equip_type" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${equipTypes }" var="type1">
								<option value="${type1.EQUIPMENT_TYPE_ID }"
								<c:if test = "${detail.EQUIPMENT_TYPE_ID ==  type1.EQUIPMENT_TYPE_ID}">selected</c:if>>
								${type1.EQUIPMENT_TYPE_NAME }
								</option>
							</c:forEach>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">维护等级：</td>
							<td style="text-align: left; width: 70%;">
							<select name="mnt_type" class="condition-select" id="mnt_type">
								<option value="">请选择 </option>
								<c:forEach items="${mntLevel}" var="mnt">
								<c:if test="${detail.MNT_LEVEL_ID==mnt.LEVEL_ID}">
								<option value="${mnt.LEVEL_ID }" selected="selected">${mnt.LEVEL_NAME }</option>
								</c:if>
								<option value="${mnt.LEVEL_ID }">${mnt.LEVEL_NAME }</option>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">所属区域：</td>
							<td style="text-align: left; width: 70%;">${detail.SON_AREA_NAME }</td>
						</tr>
					</table>
				</div>
				<div style="border-bottom: 1px solid #d2d2d2;">
					<c:forEach items="${detail.photos }" var="p">
						<a href="${p.PHOTO_PATH }" title="照片预览" class="nyroModal">
							<img src="${p.PHOTO_PATH }" style="width: 50px;"/></a>  
					</c:forEach>
				</div>
				</c:forEach>
			</div>
			<div style="text-align: center; font-weight: bold; font-size:14px; border-bottom: 1px solid #d2d2d2;" >历史检查记录</div>
			<c:if test = "${fn:length(checkPointRecordList) <1}"><div style="text-align: center;font-color:#d2d2d2;">空</div></c:if>
				 <c:forEach items="${checkPointRecordList }" var="checkPointRecord">
				<div>
					<table id="${checkPointRecord.RECORD_ID}">
						<tr>
							<td style=" width: 30%; font-weight: bold; font-size:14px; ">${checkPointRecord.CHECKNAME}</td>
							<td style="text-align: left; width: 30%; height: 20px; font-size:12px;">${checkPointRecord.CREATE_TIME}</td>
							
						</tr>
						<tr>
							<td style="width: 30%; height: 20px; ">计划名称：</td>
							<td style="text-align: left; width: 50%;">
							${checkPointRecord.PLAN_NO}
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 20px;">任务名称：</td>
							<td style="text-align: left; width: 70%;">
							${checkPointRecord.TASK_NO}
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 20px;">检查描述：</td>
							<td style="text-align: left; width: 70%;">
							${checkPointRecord.DESCRP}
							</td>
						</tr>
					</table>
				</div>
				<div style="border-bottom: 1px solid #d2d2d2;">
					<c:forEach items="${checkPointRecord.checkPointRecordPhotos }" var="cp">
						<a href="${cp.PHOTO_PATH }" title="照片预览" class="nyroModal">
							<img src="${cp.PHOTO_PATH }" style="width: 50px;"/></a>  
					</c:forEach>
				</div>
				</c:forEach> 
			</div>
		</div>
		<form action="" id="form" method="post">
		<input type="hidden" name="recordIds" value=""/>
		<input type="hidden" name="equip_codes" value=""/>
		<input type="hidden" name="equip_names" value=""/>
		<input type="hidden" name="equip_types" value=""/>
		<input type="hidden" name="mnt_types" value=""/>
		</form>
	<script type="text/javascript">
	//var recordIds = $("input[name='recordIds']").val();
	
	var recordIds = '${recordIds}';
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	$(function(){
		$("#l-map").css("width", document.body.clientWidth - 290);
		$("#details").css("max-height", $("#l-map").height() - 35);
		$(window).resize(function(){
			$("#l-map").css("width", document.body.clientWidth - 290);
			$("#details").css("max-height", $("#l-map").height() - 35);
		});
		//初始化地图
		var map = new BMap.Map("l-map");
		map.enableScrollWheelZoom(); //启用滚轮放大缩小
		
		//加载点
		$.ajax({
			type : 'POST',
			data : {
				recordIds : recordIds
			},
			url : webPath + "Lxxj/coordinate/getPoints.do",
			dataType : 'json',
			success : function(datas) {
				$.each(datas, function(index, data) {
					if (typeof(data.LONGITUDE) != 'undefined' 
							&& typeof(data.LATITUDE) != 'undefined'
							&& data.LONGITUDE > 0
							&& data.LATITUDE > 0) {
						var point = new BMap.Point(data.LONGITUDE, data.LATITUDE);
						map.centerAndZoom(point, 14);
						var marker = new BMap.Marker(point, {icon: fireIcon});
						var label = new BMap.Label(data.EQUIP_CODE, {position:point, offset:new BMap.Size(0, 40)});
						label.setStyle({
							border : "0",
							color : "#FF1111",
							backgroundColor :"0.00"
						});
						marker.setLabel(label);
						map.addOverlay(marker);
					}
				});
			}
		});
		$('.nyroModal').nyroModal();
	});
	
	function update(){
		$.messager.confirm('系统提示', '如果修改设备编码，会删除修改之前设备的所有坐标采集信息和关键点信息，您确定要更新坐标吗?', function(r) {
			if (r) {
				var equip_codesArr = new Array();
				var equip_namesArr = new Array();
				var equip_typesArr = new Array();
				var record_idArr = new Array();
				var mnt_typesArr = new Array();
				var equip_codeNullFlag = 0;
				$("input[name='equip_code']").each(function(i){
					if($.trim(this.value)==null || ''==$.trim(this.value))
					{
						//$.messager.alert('提示', "批量更新的列表中有设备编码为空，请检查！", 'info');
						$.messager.alert('提示', "批量更新的列表中有设备编码为空，请检查！", 'info');
						return;
					}
  				 	equip_codesArr[equip_codesArr.length] = encodeURI($.trim(this.value)); 
 				});
 				$("input[name='record_id']").each(function(i){
  				 	record_idArr[record_idArr.length] = this.value; 
 				});
 				$("input[name='equip_name']").each(function(i){
  				 	equip_namesArr[equip_namesArr.length] = encodeURI($.trim(this.value)); 
 				});
 				$("select[name='equip_type']").each(function(i){
  				 	equip_typesArr[equip_typesArr.length] = this.value; 
 				});
 				
 				$("select[name='mnt_type']").each(function(i){
  				 	mnt_typesArr[mnt_typesArr.length] = this.value; 
 				});
			
				$("input[name='equip_codes']").val(equip_codesArr);
				$("input[name='equip_names']").val(equip_namesArr);
				$("input[name='equip_types']").val(equip_typesArr);
				$("input[name='recordIds']").val(record_idArr);
				$("input[name='mnt_types']").val(mnt_typesArr);
				$('#form').form('submit', {
					url : webPath + "Lxxj/coordinate/update.do",
					onSubmit : function() {
						$.messager.progress();
					},
					success : function(data) {
						if(data==1){
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
							$.messager.alert('提示', "更新成功！", 'info',function(r){
								location.href = webPath + "Lxxj/coordinate/index.do";
							});
						}
						else{
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
							$.messager.alert('提示', "更新失败，请检查！", 'info');
						}
					}
				});
			}
		});
	}
	</script>
</body>
</html>