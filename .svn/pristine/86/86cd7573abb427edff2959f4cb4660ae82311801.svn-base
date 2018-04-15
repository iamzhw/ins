<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>首页</title>
		<%@include file="../../util/head.jsp"%>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>
		<style type="text/css">
			body, html,#smartMap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
		</style>
	</head>
	<body>
		<div id="smartMap"></div>
	</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("smartMap");
	map.enableScrollWheelZoom(true);
	var point = new BMap.Point(120.625729, 31.336337);
	map.centerAndZoom(point, 15);
	
	
	
	
	// 随机向地图添加25个标注
	var bounds = map.getBounds();
	var sw = bounds.getSouthWest();
	var ne = bounds.getNorthEast();
	var lngSpan = Math.abs(sw.lng - ne.lng);
	var latSpan = Math.abs(ne.lat - sw.lat);
	for (var i = 0; i < 25; i ++) {
		var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
		addMarker(point);
	}
	
	//左上角，添加默认缩放平移控件
	var top_left_navigation = new BMap.NavigationControl();
	map.addControl(top_left_navigation);
	
	//右上角，自定义控件
	function ZoomControl(){
		this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;// 默认停靠位置和偏移量
		this.defaultOffset = new BMap.Size(10, 10);
	}
	ZoomControl.prototype = new BMap.Control();
	ZoomControl.prototype.initialize = function(map){
		// 创建一个DOM元素
		var div = document.createElement("div");
		var textNode = "井盖总数：25 正常井盖：25 异常井盖：0";
		div.appendChild(document.createTextNode(textNode));// 添加文字说明
		// 设置样式
		div.style.backgroundColor = "white";
		// 绑定事件
		div.onclick = function(e){
			addTab("井盖状态", "<%=path%>/smart/toStatePage.do");
		};
		// 添加DOM元素到地图中
		map.getContainer().appendChild(div);
		// 将DOM元素返回
		return div;
	};
	// 创建控件
	var myZoomCtrl = new ZoomControl();
	map.addControl(myZoomCtrl);
	
	
	//右下角，自定义控件
	function ZoomControl1(){
		this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;// 默认停靠位置和偏移量
		this.defaultOffset = new BMap.Size(10, 10);
	}
	ZoomControl1.prototype = new BMap.Control();
	ZoomControl1.prototype.initialize = function(map){
		// 创建一个DOM元素
		var div = document.createElement("div");
		var textNode1 = "<img src='"+webPath+"images/smartcover/icon-status-normal.png'>正常";
		var textNode2 = "<img src='"+webPath+"images/smartcover/icon-status-alarm1.png'>一般";
		var textNode3 = "<img src='"+webPath+"images/smartcover/icon-status-alarm2.png'>严重";
		var textNode4 = "<img src='"+webPath+"images/smartcover/icon-status-loss.png'>不在线";
		div.innerHTML = textNode1+textNode2+textNode3+textNode4;
		div.style.backgroundColor = "white";
		// 添加DOM元素到地图中
		map.getContainer().appendChild(div);
		// 将DOM元素返回
		return div;
	};
	// 创建控件
	var myZoomCtrl1 = new ZoomControl1();
	// 添加到地图当中
	map.addControl(myZoomCtrl1);
	
	//添加覆盖物信息窗口
	function addMarker(point){
		var myIcon = new BMap.Icon(webPath + "images/smartcover/icon-status-normal.png", new BMap.Size(28, 40));
		var marker = new BMap.Marker(point,{icon:myIcon});
		map.addOverlay(marker);
		var opts = {
			width : 200,
			height: 80,
			title : "井盖：0865153030004627",
			enableMessage:false,
		};
		var info = "<div class='map_bubble_info'>编号：" + 0865153030004627 + "<br/>归属单位：" + "南京" + "<br/>井盖状态：" + "翻转"+ "</div>";
		var infoWindow = new BMap.InfoWindow(info, opts);
		marker.addEventListener("click", function(){          
			map.openInfoWindow(infoWindow,point); //开启信息窗口
		});
	}
	
</script>