<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>地图展示</title>
		<%@include file="../../../util/head.jsp"%>
		<style type="text/css">
			body,html,#mapShow {width: 100%;height: 100%;overflow: hidden;margin: 0;}
		</style>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>
	</head>
	<body>
		<div id="mapShow"></div>
		<input id="siteList" value="${siteInfo }" type="hidden">
	</body>
</html>
<script type="text/javascript">
	//百度地图初始化
	var map = new BMap.Map("mapShow");
	map.enableScrollWheelZoom(true);
	
	//初始化
	$(function(){
		var siteInfo=JSON.parse('${siteInfo}');
		//设置地图中心为获取到的第一个巡线点
		var longitude = siteInfo.siteList[0].LONGITUDE;
		var latitude = siteInfo.siteList[0].LATITUDE;
		map.centerAndZoom(new BMap.Point(longitude, latitude), 15);
		
		//遍历巡线段
		$.each(siteInfo.lineList,function(i,lineItem){
			var pointArr = new Array();
			var k = 0;
			//遍历巡线点
			$.each(siteInfo.siteList,function(j,item){
				if(lineItem.LINE_ID == item.LINE_ID){
					pointArr[k]= new BMap.Point(item.LONGITUDE, item.LATITUDE);
					//选第一个点添加文字标注
					if(0 == k){
						var marker = new BMap.Marker(new BMap.Point(item.LONGITUDE, item.LATITUDE));
						map.addOverlay(marker);
						var label = new BMap.Label("【"+lineItem.LINE_NAME + "】巡线段");
						marker.setLabel(label);
					}
					k++;
				}
			});
			
			//添加巡线段到地图中
			var lineColor = "red";//一干
			if("2" == lineItem.FIBER_GRADE){
				lineColor = "blue";//二干
			}
			var polyline = new BMap.Polyline(pointArr,{strokeColor:lineColor, strokeWeight:3, strokeOpacity:0.5, enableClicking:true});
			map.addOverlay(polyline);
			
			//添加点击事件
			polyline.addEventListener("click", showInfo);
			function showInfo(){
				var info =  "光缆段："+ lineItem.CABLE_NAME +"<br/>" + 
							"中继段："+ lineItem.RELAY_NAME +"<br/>" +
							"巡线段："+ lineItem.LINE_NAME +"<br/>";
				$.messager.show({
					title : '详情',
					msg : info,
					showType : 'show',
					width: '250',
					height: '150'
				});

			}
			
		});
		
		
		
	});
	
	
	
	
	
	
	
	
</script>
