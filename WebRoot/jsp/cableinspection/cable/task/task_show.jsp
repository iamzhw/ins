<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../../util/head.jsp"%>
<style type="text/css">
body,html,#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

#l-map {
	height: 95%;
	width: 95%;
	float: left;
	border: 2px solid #bcbcbc;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<title>计划查询</title>
</head>
<body>
	<div id="l-map"></div>
	<input id="taskId" type="hidden" value="${taskId}" />
</body>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("l-map");
	map.enableScrollWheelZoom(); //启用滚轮放大缩小
	$.ajax({
		url : "getTaskCable.do",
		contentType : "application/json",
		type : "get",
		dataType : "json",
		data : {
			"taskId" : $("#taskId").val()
		},
		success : function(datas) {
			var pointList = data.pointMode;
			var linePoints = new Array();
			$.each(datas, function(index, points1) {
				linePoints[linePoints.length] = new BMap.Point(
						points1.LATITUDE, points1.LONGITUDE);
			});
			map.centerAndZoom(linePoints[0], 14);
			var polyline1 = new BMap.Polyline(linePoints, {
				strokeColor : "red",
				strokeWeight : 6,
				strokeOpacity : 0.5
			});
			map.addOverlay(polyline1);
			var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
					+ "<br/>缆线名称：" + data.lineName + "<br/>创建时间："
					+ data.createTime + "<br/>创建者：" + data.createStaff
					+ "</div>";
			var title = "<div class='map_bubble_title'>" + data.lineName
					+ "</div>";
			var opts = {
				width : 250, // 信息窗口宽度
				height : 0, // 信息窗口高度
				title : title, // 信息窗口标题
				enableMessage : false
			//设置允许信息窗发送短息
			};
			var infoWindow = new BMap.InfoWindow(info, opts);
			polyline1.addEventListener("mouseover", function(e) {
				if (map.getInfoWindow() == null || map.getInfoWindow() != infoWindow) {
					var point = new BMap.Point(e.point.lng, e.point.lat);
					map.openInfoWindow(infoWindow, point);
				}
			});
			polyline1.addEventListener("mouseout", function() {
				//map.closeInfoWindow();
			});
		}
	});

	</html>
</script>