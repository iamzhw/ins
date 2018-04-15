<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../../util/head.jsp"%>
<title>关键点修改</title>
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
				<div class="btn-operation" onClick="updatePointInfo();">更新</div>
				<div class="btn-operation" onClick="closeTab();">返回</div>
			</div>
			<div style="overflow:auto; width: 100%;height: 430px;">
			<div id ="details">
			<form action="" id="form" method="post">
					<table id="${POINT_ID}">
						<tr>
							<td style="width: 25%; height: 30px; font-weight: bold;">设备编码：</td>
							<td style="text-align: left; width: 50%; font-weight: bold;">
							${POINT_NO}
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备名称：</td>
							<td style="text-align: left; width: 70%;">
							<input name="POINT_NAME" type="text" class="" value="${POINT_NAME}"/>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备类型：</td>
							<td style="text-align: left; width: 70%;">
							<select name="EQP_TYPE_ID" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${equipTypes}" var="type1">
									<c:if test = "${EQP_TYPE_ID==type1.EQUIPMENT_TYPE_ID}">
										<option value="${type1.EQUIPMENT_TYPE_ID }" selected="selected">${type1.EQUIPMENT_TYPE_NAME }</option>
									</c:if>
									<c:if test = "${EQP_TYPE_ID!=type1.EQUIPMENT_TYPE_ID}">
									<option value="${type1.EQUIPMENT_TYPE_ID }">${type1.EQUIPMENT_TYPE_NAME }</option>
									</c:if>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">点类型：</td>
							<td style="text-align: left; width: 70%;">
							<select name="POINT_TYPE" class="condition-select">
								<option value="">请选择 </option>
								<c:forEach items="${pointTypeList }" var="type">
									<c:if test = "${POINT_TYPE==type.POINT_TYPE_ID}">
										<option value="${type.POINT_TYPE_ID }" selected="selected">${type.POINT_TYPE_NAME}</option>
									</c:if>
									<c:if test = "${POINT_TYPE!=type.POINT_TYPE_ID}">
									<option value="${type.POINT_TYPE_ID }">${type.POINT_TYPE_NAME}</option>
									</c:if>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">维护等级：</td>
							<td style="text-align: left; width: 70%;">
							<select name="POINT_LEVEL" class="condition-select" id="mnt_type">
								<option value="0">请选择 </option>
								<c:forEach items="${mntLevel}" var="mnt">
									<c:if test="${POINT_LEVEL==mnt.LEVEL_ID}">
										<option value="${mnt.LEVEL_ID }" selected="selected">${mnt.LEVEL_NAME }</option>
									</c:if>
									<c:if test="${POINT_LEVEL!=mnt.LEVEL_ID}">
									<option value="${mnt.LEVEL_ID }">${mnt.LEVEL_NAME }</option>
									</c:if>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">设备位置</td>
							<td style="text-align: left; width: 70%;">
							<select name="AREA_TYPE" class="condition-select" id="AREA_TYPE">
								<option value="">请选择 </option>
								<c:forEach items="${areaType}" var="t">
									<c:if test="${AREA_TYPE==t.id}">
										<option value="${t.id }" selected="selected">${t.name }</option>
									</c:if>
									<c:if test="${AREA_TYPE!=t.id}">
									<option value="${t.id }">${t.name }</option>
									</c:if>
								</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">网格</td>
							<td style="text-align: left; width: 70%;">
							<select name="DEPT" class="condition-select" id="DEPT">
								<option value="${DEPT_NO}" >${DEPT_NAME }</option>
								<c:forEach items="${deptList}" var="dept">
									<c:if test="${DEPT_NO==dept.DEPT_NO}">
										<option value="${dept.DEPT_NO}" selected="selected">${dept.DEPT_NAME }</option>
									</c:if>
									<c:if test="${DEPT_NO!=dept.DEPT_NO}">
										<option value="${dept.DEPT_NO}">${dept.DEPT_NAME }</option>
									</c:if>
								</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">区域：</td>
							<td style="text-align: left; width: 70%;">
							<select name="SON_AREA_ID" class="condition-select" id="SON_AREA_ID">
								<c:forEach items="${area}" var="a">
									<c:if test="${SON_AREA_ID==a.AREA_ID}">
										<option value="${a.AREA_ID }" selected="selected">${a.NAME }</option>
									</c:if>
									<c:if test="${SON_AREA_ID!=a.AREA_ID}">
									<option value="${a.AREA_ID}">${a.NAME }</option>
									</c:if>
								</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">所属区域：</td>
							<td style="text-align: left; width: 70%;">${SON_AREA}</td>
						</tr>
						<tr>
							<td style="width: 30%; height: 30px;">坐标：</td>
							<td style="text-align: left; width: 70%;">${LONGITUDE}</br>${LATITUDE}</td>
						</tr>
					</table>
				</div>
		</div>
		</div>
		<input name="POINT_ID" type=hidden class="" value="${POINT_ID}"/>
		</form>
	<script type="text/javascript">
	//var recordIds = $("input[name='recordIds']").val();
	
	var recordIds = '${POINT_ID}';
	var LONGITUDE = '${LONGITUDE}';
	var LATITUDE = '${LATITUDE}';
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
		if(typeof (LONGITUDE) != 'undefined'
				&& typeof (LATITUDE) != 'undefined' && LONGITUDE > 0
				&& LATITUDE > 0) {
			var point = new BMap.Point(LONGITUDE,LATITUDE);
			var point_no='${POINT_NO}';
			map.centerAndZoom(point, 14);
			var marker = new BMap.Marker(point, {
				icon : fireIcon
			});
			var label = new BMap.Label(point_no, {
				position : point,
				offset : new BMap.Size(0, 40)
			});
			label.setStyle( {
				border : "0",
				color : "#FF1111",
				backgroundColor : "0.00"
			});
			marker.setLabel(label);
			map.addOverlay(marker);
		}

		$('.nyroModal').nyroModal();
	});

	
	function updatePointInfo(){
		$.messager.confirm('系统提示', '确认修改?', function(r) {
			if (r) {
				$('#form').form('submit', {
					url : webPath + "Lxxj/point/manage/updateCoordinate.do",
					onSubmit : function() {
						$.messager.progress();
					},
					success : function(data) {
					var flag = eval("("+data+")");
						if (flag.flag) {
							$.messager.progress('close'); // 如果提交成功则隐藏进度条
					$.messager.alert('提示', "更新成功！", 'info', 
					function(r) {
					//location.href = webPath + "Lxxj/point/manage/index.do";
					closeTab();
					}		
					);
						} else {
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