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
		<input id="planId" type="hidden" value="${PLAN_ID}"/>
		<input id="areaName" type="hidden" value="${AREA_NAME}" />
	</body>
</html>
<script type="text/javascript">
// 百度地图API功能
var areaname=$("#areaName").val().trim();
// 百度地图API功能
var map = new BMap.Map("l-map");
map.enableScrollWheelZoom(); //启用滚轮放大缩小
 var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
			new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
	var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", 
			new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)}); 
	
var planId=$("#planId").val();
var obj ={"planId":planId};
var checkedIcon = new BMap.Icon(webPath + "images/icon_checked.png", 
		new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
var fireIcon = new BMap.Icon(webPath + "images/icon_fire.png", 
		new BMap.Size(28, 40), {anchor: new BMap.Size(14, 40), infoWindowAnchor: new BMap.Size(14, 0)});
var greenIcon = new BMap.Icon(webPath + "images/icon_sign.png", 
		new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)});
var normalIcon = new BMap.Icon(webPath + "images/feiguanjian.png", 
		new BMap.Size(21, 25), {anchor: new BMap.Size(10, 21), infoWindowAnchor: new BMap.Size(10, 0)});
$(function(){
	getPlanCable(obj);
	
});
function getPlanCable(obj){
	
	  $.ajax({
			url:"getPlanCable.do",
		   	contentType:"application/json",
		    type:"get",
		    dataType:"json",
		    data:obj,
		    success:function(datas){
		    $.each(datas, function(i, data){
		    	var pointList=data.pointMode;
		    	var linePoints = new Array();
		    	$.each(pointList,function(j,points1){
					var point = new BMap.Point(points1.latitude,points1.longitude);
					if (points1.point_type == -1) {
						marker = new BMap.Marker(point,{icon : normalIcon});
						labelColor = "gray";
					} else {
						marker = new BMap.Marker(point, {icon : checkedIcon});
						labelColor = "#097F09";
					}
					var label = new BMap.Label(points1.point_name =points1.point_name, {position:point,offset:new BMap.Size(0, 20)});
					label.setStyle({
						offset : new BMap.Size(-30,5),
						position:point
					});
					label.setTitle(points1.point_name);
					map.addOverlay(label);
		    		linePoints[linePoints.length] = new BMap.Point(points1.latitude,points1.longitude);

		  			map.addOverlay(marker);
		    		//alert(points.longitude+":"+points.latitude);
		    	});
		    	map.centerAndZoom(linePoints[0], 14); 
		    	var polyline1 = new BMap.Polyline(linePoints, {strokeColor:"red", strokeWeight:6, strokeOpacity:0.5});
		    	if(data.lineType==2){
			    	  var parentId = data.parentId;
			    	  obj={"planId":planId,"parentId":parentId};
			    	  getPlanCable(obj)
		    	}
		    	else if(data.lineType != 2)
		   		{
		    		map.addOverlay(polyline1);
		   	}
			  	var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
		        						+ "<br/>缆线名称：" + data.lineName 
		        						+ "<br/>创建时间：" + data.createTime 
		        						+ "<br/>创建者：" + data.createStaff 
		        						+ "</div>";
		       	var title = "<div class='map_bubble_title'>" + data.lineName + "</div>";
		        var opts = {
		        	width : 250,     // 信息窗口宽度
		        	height: 0,     // 信息窗口高度
		        	title : title, // 信息窗口标题
		        	enableMessage:false//设置允许信息窗发送短息
		        	};
		        var infoWindow = new BMap.InfoWindow(info, opts);
		        polyline1.addEventListener("click", function(e){
		        	var point = new BMap.Point(e.point.lng,e.point.lat);
		        	map.openInfoWindow(infoWindow,point);
		        	});
		    });
		    }
		});
}


</script>
