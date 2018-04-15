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
	height: 99%;
	width: 99%;
	float: left;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
<title>计划查询</title>
</head>
<body style="padding: 3px; border: 0px">
	<div id="l-map"></div>
	<input id="planId" type="hidden" value="${plan_id}" />
	<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("l-map");
		map.enableScrollWheelZoom(); //启用滚轮放大缩小
		var planId = $("#planId").val();
		var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png",
				new BMap.Size(28, 40), {
					anchor : new BMap.Size(14, 40),
					infoWindowAnchor : new BMap.Size(14, 0)
				});
		$.ajax({
			url : "plan_getPlanDetail.do",
			contentType : "application/json",
			type : "get",
			dataType : "json",
			data : {
				"plan_id" : planId
			},
			success : function(datas) {
				$.each(datas.datas, function(i, data) {
					var POINT_ID = data["POINT_ID"];
					var POINT_NO = data["POINT_NO"];
					var POINT_NAME = data["POINT_NAME"];
					//var POINT_LEVEL = data["POINT_LEVEL"];
					var POINT_TYPE = data["POINT_TYPE"];
					var LONGITUDE = data["LONGITUDE"];
					var LATITUDE = data["LATITUDE"];
					var CREATE_TIME = data["CREATE_TIME"];
					var STAFF_NAME = undefined==data["STAFF_NAME"]?"":data["STAFF_NAME"];
					var ADDRESS = undefined==data["ADDRESS"]?"":data["ADDRESS"];
					
					var info = "<div class='map_bubble_info'>编码：" + POINT_NO
							+ "<br/>名称：" + POINT_NAME + "<br/>地址：" + ADDRESS
							+ "<br/>创建时间：" + CREATE_TIME + "<br/>创建者："
							+ STAFF_NAME + "</div>";
					var point = new BMap.Point(LONGITUDE, LATITUDE);
					var marker = new BMap.Marker(point, {
						icon : fireIcon
					});
					if (i == 0) {
						map.centerAndZoom(point, 14);
					}
					title = "<div class='map_bubble_title'>" + POINT_NAME
							+ "</div>";
					var label = new BMap.Label(POINT_NAME, {
						position : point,
						offset : new BMap.Size(0, 40)
					});
					label.setStyle({
						border : "0",
						color : "#FF1111",
						backgroundColor : "0.00"
					});
					marker.setLabel(label);
					var opts = {
						width : 250, // 信息窗口宽度
						height : 0, // 信息窗口高度
						title : title, // 信息窗口标题
						enableMessage : false//设置允许信息窗发送短息
					};
					var infoWindow = new BMap.InfoWindow(info, opts); // 创建信息窗口对象
					marker.addEventListener("mouseover", function() {
						map.openInfoWindow(infoWindow, point);
					});
					marker.addEventListener("mouseout", function() {
						map.closeInfoWindow();
					});
					map.addOverlay(marker);
				});
				if(datas.list!=null&&datas.list!=""){
					var overlays = [];
					$.each(datas.list, function(i, data) {
						overlays.push(new BMap.Point(data.LONGITUDE,data.LATITUDE));
					})
					var polygon = new BMap.Polygon(overlays, {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
					map.addOverlay(polygon); 
				}
			}
		});
	</script>
</body>
</html>
