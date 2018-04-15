<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../util/head.jsp"%>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 100%;
	width: 300px;
	float: left;
}
</style>
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<title>人员定位</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<div id="r-result">
		<div>
			<form id="ff">
				<table id="tbl1">
					<tr>
						<td>范围：</td>
						<td>
							<div class="condition-text-container">
								<input class="condition-text easyui-validatebox condition"
									type="text" name="distance" id="distance"
									data-options="required:true,missingMessage:'必填项',invalidMessage:'必填项' " />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	
			<div style="margin-left: 10px;" class="btn-operation"
				onClick="queryPoint();">查询</div>
			<input id="areaName" type="hidden" value="${AREA_NAME}" />
		<div id="win_staff"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	//开始绘图
	var areaname = $("#areaName").val().trim();
	// 百度地图API功能
	$("#l-map").css("width", document.body.clientWidth - 290);
	$("#ff").css("max-height", $("#l-map").height() - 50);
	var map = new BMap.Map("l-map");
	if (areaname != null) {
		map.centerAndZoom(areaname, 15);
	} else {
		map.centerAndZoom("南京", 15);
	}	
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	
	var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", 
			new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)});

	
	function queryPoint() {
		map.clearOverlays();
		var distance=$("input[name='distance']").val().trim();
		if(distance==null||distance==""){
			$.messager.show({
						title : '提  示',
						msg : '请输入查询范围',
						showType : 'show'
					});
			return;
		}
		$.messager.progress();
		
		$.ajax({
			url : webPath +"StaffLocation/query.do",
			//contentType:"application/json",
			type : "POST",
			dataType : "json",
			data : {
				distance : distance
			},
			success : function(datas) {
				$.messager.progress('close'); 
				var eqplist= new Array();
				var stafflist=new Array();
				eqplist=datas.eqpList;
				stafflist=datas.staffList;
				if(eqplist != null && eqplist.length > 0) {
					var vi = new Array();
					$.each(eqplist, function(i, data) {
						var POINT_NO = data["POINT_NO"];
						var POINT_NAME = data["POINT_NAME"];
						var LONGITUDE = data["LONGITUDE"];
						var LATITUDE = data["LATITUDE"];
						var EQP_TYPE = data["EQP_TYPE"];

						var info = "<div class='map_bubble_info'>编码：" + POINT_NO
									+ "<br/>类型：" + EQP_TYPE;
						var point = new BMap.Point(LONGITUDE, LATITUDE);
						vi[vi.length] = point;
						var marker;
						var labelColor;
						marker = new BMap.Marker(point);
						labelColor = "#FF1111";

						var label = new BMap.Label(POINT_NAME, {position:point,offset:new BMap.Size(0, 20)});
						label.setStyle({
							border : "0",
							color : labelColor,
							offset : new BMap.Size(0, -30),
							backgroundColor :"0.00"
						});
						marker.setLabel(label);
						title = "<div class='map_bubble_title'>" + POINT_NAME
								+ "</div>";

						map.addOverlay(marker);
						var opts = {
							width : 250, // 信息窗口宽度
							height : 0, // 信息窗口高度
							title : title, // 信息窗口标题
							offset : new BMap.Size(0, -30),
							enableMessage : false
						//设置允许信息窗发送短息
						};
						var infoWindow = new BMap.InfoWindow(info, opts); // 创建信息窗口对象

						marker.addEventListener("mouseover", function(e) {
						//如 果右键处在开始-结束之间，则不展示点的详细信息
							if (showInfoWindowFlag==0){
								if (map.getInfoWindow() == null || map.getInfoWindow() != infoWindow) {
									map.openInfoWindow(infoWindow, point);
								}
							}
						});
						marker.addEventListener("mouseout", function() {
							//map.closeInfoWindow();
						});
					});
					map.setViewport(vi);
				} else {
					$.messager.show({
						title : '提  示',
						msg : '没有检索到关键点!',
						showType : 'show'
					});
				}
				if(stafflist != null && stafflist.length > 0){
					var vii = new Array();
					$.each(eqplist, function(i, data) {
						var STAFF_ID = data["UPLOAD_STAFF"];
						var STAFF_NAME = data["STAFF_NAME"];
						var LONGITUDE = data["LONGITUDE"];
						var LATITUDE = data["LATITUDE"];
						var DEPT_NAME = data["DEPT_NAME"];

						var info = "<div class='map_bubble_info'>编码：" + DEPT_NAME
									+ "</div>"
						var point = new BMap.Point(LONGITUDE, LATITUDE);
						vii[vii.length] = point;
						var marker;
						var labelColor;
						marker = new BMap.Marker(point,{icon : fireIcon});

						var label = new BMap.Label(STAFF_NAME, {position:point,offset:new BMap.Size(0, 20)});
						label.setStyle({
							border : "0",
							offset : new BMap.Size(0, -30),
							backgroundColor :"0.00"
						});
						marker.setLabel(label);
						title = "<div class='map_bubble_title'>" + STAFF_NAME
								+ "</div>";

						map.addOverlay(marker);
						var opts = {
							width : 250, // 信息窗口宽度
							height : 0, // 信息窗口高度
							title : title, // 信息窗口标题
							offset : new BMap.Size(0, -30),
							enableMessage : false
						//设置允许信息窗发送短息
						};
						var infoWindow = new BMap.InfoWindow(info, opts); // 创建信息窗口对象
						marker.addEventListener("mouseout", function() {
							//map.closeInfoWindow();
						});
					});
					map.setViewport(vii);
				}else{
				$.messager.show({
						title : '提  示',
						msg : '没有人员在线!',
						showType : 'show'
					});
				}
			}
		});
		$.messager.progress('close');
	}
	
</script>
