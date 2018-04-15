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
		<title>光缆段查询</title>
	</head>
	<body>
		<div id="l-map"></div>
		<input id="lineId" type="hidden" value="${LINE_ID}"/>
		<input id="areaName" type="hidden" value="${AREA_NAME}" />
	</body>
</html>
<script type="text/javascript">
// 百度地图API功能
var areaname=$("#areaName").val().trim();
// 百度地图API功能
var map = new BMap.Map("l-map");
/*if(areaname!=null){
map.centerAndZoom(areaname, 15);
}else{
map.centerAndZoom("南京", 15);
}*/
map.enableScrollWheelZoom(); //启用滚轮放大缩小
var lineId=$("#lineId").val();
$.ajax({
	url:"getCable.do",
   	contentType:"application/json",
    type:"get",
    dataType:"json",
    data:{"line_id":lineId},
    success:function(datas){
    var linePoints = new Array();
    $.each(datas, function(i, data){
    	var pointList=data.pointMode;
    	$.each(pointList,function(j,points1){
    		linePoints[linePoints.length] = new BMap.Point(points1.latitude,points1.longitude);
    		//alert(points.longitude+":"+points.latitude);
    	});
    	var polyline1 = new BMap.Polyline(linePoints, {strokeColor:"red", strokeWeight:6, strokeOpacity:0.5});
    	map.addOverlay(polyline1);
    	//alert(polyline1.getPath().length);
	  	var info = "<div class='map_bubble_info'>缆线ID：" + data.lineId
        						+ "<br/>缆线编码：" + data.lineNo
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
   // alert(linePoints.length);
    map.setViewport(linePoints);
    }
});
<%--
$.ajax({
	url:"getPoints.do",
   	contentType:"application/json",
    type:"get",
    dataType:"json",
    //data:{"userId":item3.client_id},
    success:function(datas){
    $.each(datas, function(i, data){
        			var POINT_ID = data["POINT_ID"];
        			var POINT_NO = data["POINT_NO"];
        			var POINT_NAME = data["POINT_NAME"];
        			//var POINT_LEVEL = data["POINT_LEVEL"];
        			var POINT_TYPE = data["POINT_TYPE"];
        			var LONGITUDE = data["LONGITUDE"];
        			var LATITUDE = data["LATITUDE"];
        			var CREATE_TIME = data["CREATE_TIME"];
        			var STAFF_NAME = data["STAFF_NAME"];
        			//var radius = data["radius"];
        			
        			var info = "<div class='map_bubble_info'>编码：" + POINT_NO
        						+ "<br/>经度：" + LONGITUDE
        						+ "<br/>纬度：" + LATITUDE 
        						+ "<br/>创建时间：" + CREATE_TIME 
        						+ "<br/>创建者：" + STAFF_NAME 
        						+ "</div>";
        			var point = new BMap.Point(LONGITUDE, LATITUDE);
        			var marker = new BMap.Marker(point);
        			
        			title = "<div class='map_bubble_title'>" + POINT_NAME + "</div>";
        			
        			map.addOverlay(marker); 
        			var opts = {
        					  width : 250,     // 信息窗口宽度
        					  height: 0,     // 信息窗口高度
        					  title : title, // 信息窗口标题
        					  enableMessage:false//设置允许信息窗发送短息
        					};
        			var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
        			marker.addEventListener("click", function(){map.openInfoWindow(infoWindow,point);
        						  //points[points.length] = new BMap.Point(point.lng, point.lat);
        						  //clearAll(); //清除建筑物
        						  //var polyline = new BMap.Polyline(points, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
        						 // map.addOverlay(polyline);
        						  //overlays.push(polyline);
        						  if(POINT_TYPE == 2){
        							  addPoint(POINT_ID,POINT_NAME,POINT_NO, POINT_TYPE);
        						  }
        			});
        		});
    }
});
 --%>


</script>
