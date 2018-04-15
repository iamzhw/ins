<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=4E1fb44d64c4f75d50a0ec54abff48ff"></script>

	<title>添加动画标注点</title>
</head>
  
  <body>
	<div id="allmap"></div>
</body>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(${list[0].X}, ${list[0].Y});
	map.centerAndZoom(point, 15);
	var marker = new BMap.Marker(point);  // 创建标注
	map.addOverlay(marker);               // 将标注添加到地图中
	//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	var opts = {
	  width : 200,     // 信息窗口宽度
	  height: 150,     // 信息窗口高度
	  title : '${list[0].SITE_NAME}' // 信息窗口标题
	}
	var infoWindow = new BMap.InfoWindow("施工单位 : ${list[0].CON_COMPANY}  <br/>施工地址 : ${list[0].CON_ADDRESS}",opts);  // 创建信息窗口对象 
	marker.addEventListener("click", function(){          
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	});
	
</script>
</html>


